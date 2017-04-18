package com.uvg.expo;

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

import java.io.Serializable;

public class RenderCreation extends FragmentActivity implements AndroidFragmentApplication.Callbacks{

    public static View renderView;
    public static ModelUVG uvgModel;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        GameFragment fragment = new GameFragment();
    }


    public static class GameFragment extends AndroidFragmentApplication
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {  return initializeForView(new ModelUVG());   }
    }


    @Override
    public void exit() {

    }
}
