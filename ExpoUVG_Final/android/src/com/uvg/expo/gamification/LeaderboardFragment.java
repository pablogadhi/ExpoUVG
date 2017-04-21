package com.uvg.expo.gamification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badlogic.gdx.utils.Json;
import com.loopj.android.http.AsyncHttpClient;
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


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_leaderboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

	/**
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
