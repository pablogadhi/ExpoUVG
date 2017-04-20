package com.uvg.expo.news;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 6/04/2017.
 */

public class Utils {

    private static final String TAG = "Utils";



    public static List<Feed> loadFeeds(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context,"news.json"));
            List<Feed> feedList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Feed feed = gson.fromJson(array.getString(i), Feed.class);
                feedList.add(feed);
            }
            return feedList;
        }catch (Exception e){
            Log.d(TAG,"seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }
    }

    private static String loadJSONFromAsset(Context context, String jsonFileName) {

        String json = null;
        InputStream is = null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private Utils(Context context, String json){

        HttpURLConnection connection=null;
        BufferedReader reader=null;
        try{
            URL url = new URL("http://miapp/noticias/getNoticias");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));

            json = "";
            StringBuffer buffer = new StringBuffer();

            while((json = reader.readLine()) !=null){
                buffer.append(json);
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }   finally {
            if(connection != null) {
                connection.disconnect();
            }
            try{
                if(reader != null) {
                    reader.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }
}
