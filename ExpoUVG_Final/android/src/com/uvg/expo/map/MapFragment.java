package com.uvg.expo.map;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.uvg.expo.ModelUVG;
import com.uvg.expo.R;


public class MapFragment extends Fragment {

    ContentFrameLayout surface;
    FloatingActionButton qrfab;
    SearchView searchView;
    ModelUVG modelUVG;

    private String mostrar;
    private SharedPreferences preferences;
    private String defaultLoc = "F";

    TextView debbug;//Para debuggear unicamente
    TextView info;

    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_ui, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        preferences = getActivity().getSharedPreferences(getString(R.string.shared_prefs), getActivity().MODE_PRIVATE);
        preferences.edit().putString("Lugar", "Empty").apply();

        searchView = (SearchView) getView().findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);


        modelUVG = RenderCreation.uvgModel;
        modelUVG.setAdonde(null);
        modelUVG.setEstoy(null);
        RenderCreation creation = new RenderCreation();
        View render = RenderCreation.renderView;

        surface = (ContentFrameLayout) getView().findViewById(R.id.surface);
        ViewGroup group = (ViewGroup) surface.getParent();
        int index = group.indexOfChild(surface);
        group.removeView(surface);
        if(render.getParent() != null){
            ViewGroup parent = (ViewGroup) render.getParent();
            parent.removeView(render);
        }
        group.addView(render, index);

        qrfab = (FloatingActionButton) getView().findViewById(R.id.QRFAB);
        qrfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QRReader.class);
                startActivity(intent);
            }
        });

        info = (TextView) getView().findViewById(R.id.txtInfo);

        info.setVisibility(View.INVISIBLE);

        //Textview para debuggear
        debbug = (TextView) getView().findViewById(R.id.debugg);

        //Listener para las busquedas
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String busqueda = searchView.getQuery().toString();
                modelUVG.setAdonde(busqueda);
                busqueda = busqueda.toUpperCase();

                Path path = new Path();

                if (!defaultLoc.equals(busqueda)){

                    modelUVG.mapear(path.findPath(defaultLoc,busqueda));
                    String prueba = path.findPath(defaultLoc,busqueda);
                    Log.d("BRO", prueba);

                }

                debbug.setText(busqueda);
                //Para ocultar el teclado al realizar la busqueda:
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        setMostrar(preferences.getString("Lugar","Empty"));
        cambiarPosicion();
    }

    public void setMostrar(String qranswer){
        mostrar = qranswer;
    }

    public void cambiarPosicion(){
        modelUVG.irActual(mostrar);
    }
}
