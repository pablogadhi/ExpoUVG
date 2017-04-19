package  com.example.ychav.expouvg;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;

public class LeaderboardActivity extends Fragment {


    public View setCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstantState){

        View view;

        view = inflater.inflate(R.layout.activity_leaderboard, container);

        return view;
       // Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
      //  getActivity().setSupportActionBar(toolbar);
    }

    /**
    // visibilidad del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
        return true;
    }

    // eventos en el menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            // cambiar a la activity del usuario
            case R.id.btnRetos:
                Intent intRetos = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(intRetos);
                break;
            // cambiar a la activity del usuario
            case R.id.btnCard:
                Intent intUser = new Intent(LeaderboardActivity.this, Usuario.class);
                startActivity(intUser);
                break;
            // cambiar a la activity del usuario
            case R.id.btnLeaderBoard:
                Intent intLeader = new Intent(LeaderboardActivity.this, LeaderboardActivity.class);
                startActivity(intLeader);
                break;
        }

        return true;
    }

    */

}
