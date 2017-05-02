package com.uvg.expo.map;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by pablo on 4/28/2017.
 */

public class Gestos extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("Prueba", "Funciona");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("Funciona", "Long");
        super.onLongPress(e);
    }
}
