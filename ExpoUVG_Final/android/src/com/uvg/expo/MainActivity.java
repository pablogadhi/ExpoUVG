package com.uvg.expo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.vision.face.Face;
import com.uvg.expo.Networking.FacebookActivity;
import com.uvg.expo.Networking.tab1;
import com.uvg.expo.gamification.LeaderboardFragment;
import com.uvg.expo.gamification.RetosFragment;
import com.uvg.expo.gamification.Usuario;
import com.uvg.expo.map.MapFragment;
import com.uvg.expo.map.RenderCreation;
import com.uvg.expo.news.SurveyFragment;
import com.uvg.expo.snow.activities.MainTabbedActivity;
import com.uvg.expo.snow.activities.NewsTweetFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import app.Config;
import utils.NotificationUtils;

// tracking modules
import org.piwik.sdk.Tracker;
import org.piwik.sdk.extra.TrackHelper;

import com.uvg.expo.snow.adapters.AppController;
import com.uvg.expo.snow.fragments.TweetsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private boolean regresar;
    private Fragment loadfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TrackHelper.track().screen("/").title("La app de aquellos").with(getTracker());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainer, new NewsTweetFragment());
        transaction.commit();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private Tracker getTracker(){
        return ((AppController) getApplication()).getTracker();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(regresar == true){
            //cambiarEstadoDrawer(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.nav_usuario){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Usuario usuario = new Usuario();
            transaction.replace(R.id.fragmentContainer, usuario);
            transaction.commit();
        }

        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        loadfragment = new Fragment();
        // VARIABLE PARA MONITOREAR USO DE APLICACION
        String flag = null; 

        if (id == R.id.uvgmap) {
            MapFragment mapFragment = new MapFragment();
            loadfragment = mapFragment;
            flag = "Map"; 
        } else if (id == R.id.leaderboard) {
            LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
            loadfragment = leaderboardFragment;
            flag = "LeaderBoard"; 
        } else if (id == R.id.nav_retos){
            RetosFragment retosFragment = new RetosFragment();
            loadfragment = retosFragment;
            flag = "Retos"; 
        } else if (id == R.id.nav_survey){
            SurveyFragment surveyFragment = new SurveyFragment();
            loadfragment = surveyFragment;
            flag = "Survey"; 
        } else if (id == R.id.nav_feed){
            NewsTweetFragment tweetsFragment = new NewsTweetFragment();
            loadfragment = tweetsFragment;
            flag = "News";
        } else if (id == R.id.nav_compartir){
            Intent intent = new Intent(this, FacebookActivity.class);
            startActivity(intent);
            flag = "Facebook";
            /*
        } else if (id == R.id.nav_rating){
            tab1 tab1fragment = new tab1();
            loadfragment = tab1fragment;
            flag = "Rating"; */
        }

        //ENVIO DE DATOS PARA MONITOREO DE USO DE APP
        if (!flag.equals(null)) {
            TrackHelper.track().screen("/").title(flag).with(getTracker());
        }
        transaction.replace(R.id.fragmentContainer, loadfragment);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }



    @Override
    public void cambiarEstadoDrawer(boolean estado) {
        if(estado){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();
            regresar = false;
            ((MapFragment) loadfragment).changeUiVisivility(true);
            RenderCreation.uvgModel.reset();
        }else{
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            regresar = true;
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cambiarEstadoDrawer(true);
                }
            });

        }
    }
}
