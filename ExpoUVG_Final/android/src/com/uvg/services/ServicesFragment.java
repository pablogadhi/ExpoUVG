package com.uvg.services;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


public class ServicesFragment extends Fragment {
    TextView results;
    NetworkUtils network;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.services, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        results = (TextView) getView().findViewById(R.id.result);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.app_bar_search){
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("username", "pablo");
                jsonObject.put("ponts", 0);
            }catch (JSONException exception){
                Log.d("Error", "No hubo respuesta");
            }
            URL SearchUrl = network.buildUrl("rod");
            new QueryTask().execute(SearchUrl);
        }

        return super.onOptionsItemSelected(item);
    }

    public class QueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {

            String resultado = "";
            try {
                resultado = network.getResponseFromHttpUrl(params[0]);
            }catch (Exception e){
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")){
                try{

                    results.setText(s);

                }catch (Exception exception){
                    Log.d("Error","Datos no encontrados!");
                }

            }

            super.onPostExecute(s);
        }
    }

}


