package com.uvg.expo.news;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String BASE_URL =
            "https://experiencia-uvg.azurewebsites.net:443/api/SurveyApi";

    final static String URL_TODO = "https://experiencia-uvg.azurewebsites.net:443/api/Survey/Details";


    /**
     * Builds the URL used to query .
     *
     * The keyword that will be queried for.
     * The URL to use to query.
     */
    public static URL buildUrl(int opcion, String b) {
        URL url = null;
        try {
            if (opcion==0){
                url = new URL(BASE_URL+"/"+b);
            }else if (opcion==1){
                url = new URL(URL_TODO);
            }else{
                url = new URL(BASE_URL);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}