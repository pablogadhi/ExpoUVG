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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import cz.msebera.android.httpclient.Header;


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

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("userId", "58f7d855cd7b6c00045c2603");
            client.post("https://expo-uvg.herokuapp.com/api/points/add", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    results.setText(responseString);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    results.setText(responseString);
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    public class QueryTask extends AsyncTask<URL, Void, String> {
        private String entrada;
        @Override
        protected String doInBackground(URL... params) {

            String resultado = "";
            try {
                resultado = network.postOnHttpUrl(params[0], entrada);
            }catch (Exception e){
                e.printStackTrace();
            }

            return resultado;
        }

        protected void setEntrada(String entrada){
            this.entrada = entrada;
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


