package com.uvg.expo.gamification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.uvg.expo.R;

public class LeaderboardFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_leaderboard, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    // eventos en el menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            // cambiar a la activity del usuario
            case R.id.btnRetos:
                //Intent intRetos = new Intent(LeaderboardFragment.this, GamificationMain.class);
                //startActivity(intRetos);
                break;
            // cambiar a la activity del usuario
            case R.id.btnCard:
                //Intent intUser = new Intent(LeaderboardFragment.this, Usuario.class);
                //startActivity(intUser);
                break;
            // cambiar a la activity del usuario
            case R.id.btnLeaderBoard:
                //Intent intLeader = new Intent(LeaderboardFragment.this, LeaderboardFragment.class);
                //startActivity(intLeader);
                break;
        }

        return true;
    }


}
