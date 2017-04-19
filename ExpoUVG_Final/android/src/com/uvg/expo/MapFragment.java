package com.uvg.expo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;


public class MapFragment extends Fragment {

    FloatingActionButton qrfab;
    SearchView searchView;
    ModelUVG modelUVG;
    TextView debbug;
    String mostrar;

    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_ui, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        prefs = getActivity().getSharedPreferences(getString(R.string.shared_prefs), getActivity().MODE_PRIVATE);
        prefs.edit().putString("Lugar", "Empty").apply();

        modelUVG = RenderCreation.uvgModel;
        modelUVG.setAdonde(null);
        modelUVG.setEstoy(null);

        View render = RenderCreation.renderView;
        View surface = getView().findViewById(R.id.surface);
        ViewGroup group = (ViewGroup) surface.getParent();
        int index = group.indexOfChild(surface);
        group.removeView(surface);
        if(render.getParent()!=null){
            ViewGroup parent = (ViewGroup) render.getParent();
            parent.removeView(render);
        }
        group.addView(render, index);

        searchView = (SearchView) getView().findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        qrfab = (FloatingActionButton) getView().findViewById(R.id.QRFAB);
        qrfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QRReader.class);
                startActivity(intent);
            }
        });


        debbug = (TextView) getView().findViewById(R.id.debugg);

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        setMostrar(prefs.getString("Lugar","Empty"));
        debbug.setText(mostrar);
    }

    public void setMostrar(String qranswer){
        mostrar = qranswer;
    }

    public void cambiarPosicion(){
        modelUVG.irActual(mostrar);
    }
}
