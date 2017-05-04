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

    private TextView text;
    private String JsonEntro;
    private String  strmyR;
    private JSONArray rtest;

    private TextView pos1;
    private TextView name1;
    private TextView pts1;
    private ImageView img1;

    private TextView pos2;
    private TextView name2;
    private TextView pts2;
    private ImageView img2;

    private TextView pos3;
    private TextView name3;
    private TextView pts3;
    private ImageView img3;

    private TextView pos4;
    private TextView name4;
    private TextView pts4;
    private ImageView img4;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_leaderboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        pos1 = (TextView) getView().findViewById(R.id.textPos1);
        name1 = (TextView) getView().findViewById(R.id.textUser1);
        pts1 = (TextView) getView().findViewById(R.id.textPts1);
        img1 = (ImageView) getView().findViewById(R.id.estrella1);
        img1.setVisibility(View.INVISIBLE);

        pos2 = (TextView) getView().findViewById(R.id.textPos2);
        name2 = (TextView) getView().findViewById(R.id.textUser2);
        pts2 = (TextView) getView().findViewById(R.id.textPts2);
        img2 = (ImageView) getView().findViewById(R.id.estrella2);
        img2.setVisibility(View.INVISIBLE);

        pos3 = (TextView) getView().findViewById(R.id.textPos3);
        name3 = (TextView) getView().findViewById(R.id.textUser3);
        pts3 = (TextView) getView().findViewById(R.id.textPts3);
        img3 = (ImageView) getView().findViewById(R.id.estrella3);
        img3.setVisibility(View.INVISIBLE);

        pos4 = (TextView) getView().findViewById(R.id.textPos4);
        name4 = (TextView) getView().findViewById(R.id.textUser4);
        pts4 = (TextView) getView().findViewById(R.id.textPts4);
        img4 = (ImageView) getView().findViewById(R.id.estrella4);
        img4.setVisibility(View.INVISIBLE);

        JSONObject jsonObject = new JSONObject();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("userId", "5");
        client.get("https://experiencia-uvg.azurewebsites.net:443/api/GamePointApi/All", params, new  JsonHttpResponseHandler() {
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
                int cont = 1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = jsonArray.getJSONObject(i);
                        String ptsU = jsonobject.getString("usrPoints");
                        String name = jsonobject.getString("usr");
                       // "pos"+String.valueOf(cont).setText(String.valueOf(cont));
                       // "name"+String.valueOf(cont).setText(name);
                        //"pts"+String.valueOf(cont).setText(ptsU);
                        cont++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


/**
        // call al webservice
        JSONObject jsonObject = new JSONObject();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userId", "58f7d855cd7b6c00045c2603");
        client.get("https://expo-uvg.herokuapp.com/api/points/get/all", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                text.setText(response.toString());
               // rtest = response;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                text.setText("no funciono");
            }
        });
/**
        //I assume that we need to create a JSONArray object from the following string
        //String jsonArrStr = "[ { \"ID\": \"135\", \"Name\": \"Fargo Chan\" },{ \"ID\": \"432\", \"Name\": \"Aaron Luke\" },{ \"ID\": \"252\", \"Name\": \"Dilip Singh\" }]";

        //JSONArray jsonArr = new JSONArray(jsonArrStr);

        /**
        JSONArray myR = null;

        try {
            myR = new JSONArray(strmyR);
        } catch (JSONException e) {
            myR = rtest;
            e.printStackTrace();
        }

        JSONArray sortedJsonArray = new JSONArray();

        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < myR.length(); i++) {
            try {
                jsonValues.add(myR.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort( jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private static final String points = "points";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get(points).toString();
                    valB = (String) b.get(points).toString();
                }
                catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < myR.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }

        text.setText(sortedJsonArray.toString());


        text = (TextView) getView().findViewById(R.id.textView);

        String url = "https://expo-uvg.herokuapp.com/api/points/points/get/all";

        String TAG_Users = "username";
        String TAG_ID = "_id";
        String TAG_NAME = "name";
        String TAG_V = "_v";
        String TAG_TIME = "createdAt";
        String TAG_POINTS = "points";

        JSONObject jsonObject = new JSONObject();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userId", "58f7d855cd7b6c00045c2603");
        client.get("https://expo-uvg.herokuapp.com/api/points/get/all", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // JSONObject pnt = new JSONObject(responseString);
                text.setText(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JsonEntro = responseString;
            }
        });

*/

        /**
        try {
            // Getting Array of Contacts
            contacts =  json.getJSONArray(TAG_Users);

            // looping t
            for(int i = 0; i < contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);

                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String email = c.getString(TAG_V);
                String address = c.getString(TAG_TIME);
                String gender = c.getString(TAG_POINTS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (int i = 0; i < contacts.length(); i++) {
            try {
                jsonList.add(contacts.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort( jsonList, new Comparator<JSONObject>() {

            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();

                try {
                    valA = (String) a.get("points");
                    valB = (String) b.get("points");
                }
                catch (JSONException e) {
                    //do something
                }

                return valA.compareTo(valB);
            }
        });

        for (int i = 0; i < contacts.length(); i++) {
            sortedJsonArray.put(jsonList.get(i));
        }

        text.setText(sortedJsonArray.toString());
*/

        super.onActivityCreated(savedInstanceState);
    }


}
