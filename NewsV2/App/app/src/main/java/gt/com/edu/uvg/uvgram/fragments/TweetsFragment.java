package gt.com.edu.uvg.uvgram.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import gt.com.edu.uvg.uvgram.R;
import gt.com.edu.uvg.uvgram.adapters.AppController;
import gt.com.edu.uvg.uvgram.adapters.FeedItem;
import gt.com.edu.uvg.uvgram.adapters.FeedListAdapter;


public class TweetsFragment extends Fragment {

    private static final String TAG = TweetsFragment.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private FloatingActionButton foab;
    private String URL_FEED = "http://ec2-54-213-143-106.us-west-2.compute.amazonaws.com/tweets";
    private static final String URL_IMG = "http://ec2-54-213-143-106.us-west-2.compute.amazonaws.com/images/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.view_tweets, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_aa);
        foab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        foab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        refresh();

        return rootView;
    }

    private void refresh(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            if (!feedItems.isEmpty()) feedItems.clear();
            JSONArray feedArray = response.getJSONArray("data");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setTweetID(feedObj.getString("tweet_id"));
                item.setTweetText(feedObj.getString("tweet_text"));
                item.setExt(feedObj.getString("image_ext"));
                item.setTime(feedObj.getString("date_posted"));
                item.setImageUrl(URL_IMG + item.getTweetID() + item.getExt());
                feedItems.add(item);
            }

            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}