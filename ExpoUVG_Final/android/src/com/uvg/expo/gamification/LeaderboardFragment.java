package com.uvg.expo.gamification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.uvg.expo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class LeaderboardFragment extends Fragment{

    private int contador;
    private String ptsU, name;
    private TableLayout lbl;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_leaderboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        contador = 1;

        lbl = (TableLayout) getView().findViewById(R.id.Tabla);

        JSONObject jsonObject = new JSONObject();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("userId", "5");
        client.get("https://experiencia-uvg.azurewebsites.net:443/api/GameLeaderboard", params, new  JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("ERROR", "ERROR");
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray jsonArray = response;

                ArrayList<Integer> posiciones = new ArrayList<Integer>();
                for(int i = 0; i < jsonArray.length(); i++){
                    posiciones.add(i);
                }



                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = jsonArray.getJSONObject(i);
                        ptsU = jsonobject.getString("points");
                        name = jsonobject.getString("username");
                        makeEntry(ptsU, name, contador);
                        contador++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    public void makeEntry(String pts, String nombre, int pos){

        TableRow row = new TableRow(getView().getContext());

        TextView posicion = new TextView(getView().getContext());
        posicion.setText("       "+pos + "                ");
        row.addView(posicion);

        TextView usuario = new TextView(getView().getContext());
        usuario.setText(nombre);
        row.addView(usuario);


        TextView punteo = new TextView(getView().getContext());

        punteo.setText("                              "+pts);

        row.addView(punteo);

        lbl.addView(row);
    }


}
