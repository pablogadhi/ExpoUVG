package com.uvg.expo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by ychav on 3/05/2017.
 */

public class Global  {

    public static String userName = "";
    public static String userId = "";
    public static String noticias = "15";
    public static String cueva = "16";
    public static String encuestas = "17";
    public static String lugares = "18";
    public static String publicaciones = "19";
    public static String recomendar = "20";
    public static String stands = "21";
    public static String mapa = "22";
    public static String douglas = "23";
    public static String busqueda = "24";
    public static String libro = "25";

    public static void setUserName(String uN){userName = uN;}

    public static String getUserName() {return userName;}

    public static void setUserId(String uId){
        userId = uId;
    }

    public static String getUserId(){ return userId;}

    public static String getNoticias() {return noticias;}

    public static String getCueva() {return  cueva;}

    public static String getEncuestas() {return encuestas;}

    public static String getLugares() {return  lugares;}

    public static String getPublicaciones() {return publicaciones;}

    public static String getRecomendar() {return recomendar;}

    public static String getStands() {return  stands;}

    public static String getMapa() {return  mapa;}

    public static String getDouglas() {return douglas;}

    public static String getBusqueda() {return busqueda;}

    public static String getLibro() {return libro;}

}

