package com.uvg.expo.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.uvg.expo.MainActivity;
import com.uvg.expo.ModelUVG;

import java.io.Serializable;

public class RenderCreation extends AndroidApplication{

    public static View renderView;
    public static ModelUVG uvgModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(RenderCreation.this, MainActivity.class);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        uvgModel = new ModelUVG();
        renderView = initializeForView(uvgModel, config);
        startActivity(intent);
        finish();
    }
}
