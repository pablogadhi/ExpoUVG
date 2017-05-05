package com.uvg.expo.gamification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.badlogic.gdx.utils.Json;
import com.google.android.gms.vision.text.Text;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uvg.expo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.uvg.expo.gamification.JSONParser.json;

public class LeaderboardFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_leaderboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //TableLayout tbl = (TableLayout) getView().findViewById(R.id.baseLeaderboard);

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
                Log.d("JsonArray", jsonArray.toString());
                JSONArray sortedJsonArray = new JSONArray();

                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        jsonValues.add(jsonArray.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort( jsonValues, new Comparator<JSONObject>() {
                    //You can change "Name" with "ID" if you want to sort by ID
                    private static final String KEY_NAME = "usrPoints";

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();

                        try {
                            valA = (String) a.get(KEY_NAME);
                            valB = (String) b.get(KEY_NAME);
                        }
                        catch (JSONException e) {
                            //do something
                        }

                        return valA.compareTo(valB);
                        //if you want to change the sort order, simply use the following:
                        //return -valA.compareTo(valB);
                    }
                });

                for (int i = 0; i < jsonArray.length(); i++) {
                    sortedJsonArray.put(jsonValues.get(i));
                }
                Log.d("ordenado", sortedJsonArray.toString());

                ArrayList<Integer> posiciones = new ArrayList<Integer>();
                for(int i = 0; i < jsonArray.length(); i++){
                    posiciones.add(i);
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = jsonArray.getJSONObject(i);
                        String ptsU = jsonobject.getString("points");
                        String name = jsonobject.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (final Integer poosiciones: posiciones){

                    }


                }
            }
        });


        super.onActivityCreated(savedInstanceState);
    }


}
