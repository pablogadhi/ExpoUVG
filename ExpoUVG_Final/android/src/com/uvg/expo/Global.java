package com.uvg.expo;

import android.app.Application;

/**
 * Created by ychav on 3/05/2017.
 */

public class Global extends Application  {

    public static String userName = "";
    public static String userId = "";
    public static String noticias = "";
    public static String cueva = "";
    public static String encuestas = "";
    public static String lugares = "";
    public static String publicaciones = "";
    public static String recomendar = "";
    public static String stands = "";
    public static String mapa = "";
    public static String douglas = "";
    public static String busqueda = "";
    public static String libro = "";

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }


}

