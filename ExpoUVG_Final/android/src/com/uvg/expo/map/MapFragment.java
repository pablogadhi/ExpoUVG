package com.uvg.expo.map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uvg.expo.DrawerHandler;
import com.uvg.expo.Global;
import com.uvg.expo.ModelUVG;
import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class MapFragment extends Fragment {

    ContentFrameLayout surface;
    FloatingActionButton qrfab;
    SearchView searchView;
    CardView cardView;
    ModelUVG modelUVG;
    ProgressBar bar;

    private String mostrar;
    private SharedPreferences preferences;
    private String defaultLoc = "F";
    private  boolean cargar;
    private JSONObject jsonParams;

    TextView info;
    TextView clima;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_ui, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences(getString(R.string.shared_prefs), getActivity().MODE_PRIVATE);
        preferences.edit().putString("Lugar", "A").apply();
        preferences.edit().putBoolean("Cambio", false).apply();

        cardView = (CardView) getView().findViewById(R.id.cardView);

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
                modelUVG.setEstoyVer("");
                Intent intent = new Intent(getActivity(), QRReader.class);
                startActivity(intent);
            }
        });

        info = (TextView) getView().findViewById(R.id.txtInfo);
        clima = (TextView) getView().findViewById(R.id.climatxt);

        info.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);
        qrfab.setVisibility(View.INVISIBLE);
        clima.setVisibility(View.INVISIBLE);
        modelUVG.setViewTrue();

        //Textview para debuggear

        bar = (ProgressBar) getView().findViewById(R.id.prgLoad);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        if(cargar){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // update TextView here!
                                    if (!modelUVG.getLoading()){
                                        bar.setVisibility(View.INVISIBLE);
<<<<<<< HEAD
                                        cortina.setVisibility(View.INVISIBLE);
                                        surface.setVisibility(View.VISIBLE);
=======
>>>>>>> 59b77946649d330013cabd05980fec41a5a36f0a
                                        cardView.setVisibility(View.VISIBLE);
                                        searchView.setVisibility(View.VISIBLE);
                                        qrfab.setVisibility(View.VISIBLE);
                                        clima.setVisibility(View.VISIBLE);
                                        cargar = false;
                                        modelUVG.setViewFalse();

                                    }
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        cargar =true;
        t.start();

        //Listener para las busquedas
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String busqueda = searchView.getQuery().toString();
                modelUVG.setAdonde(busqueda);
                busqueda = busqueda.toUpperCase();

                //comienzan cambios

                if (!modelUVG.getEstoy().equals("")){
                    defaultLoc = modelUVG.getEstoy();
                }else{
                    modelUVG.allFalse();
                }

                try {
                    if (busqueda.length() != 1 && !busqueda.equals("II")){

                        busqueda = busqueda + "          ";

                        if (busqueda.substring(0,8).equals("EDIFICIO")){

                            if (busqueda.substring(9,11).equals("II")){

                                busqueda = busqueda.substring(9,11);

                            } else {

                                busqueda = busqueda.substring(9,10);

                            }
                        }

                        else if (busqueda.substring(1,2).equals(" ")){
                            busqueda = busqueda.substring(0,1);
                        }

                        else {
                            try {

                                Integer.parseInt(busqueda.substring(2,3));
                                busqueda = busqueda.substring(0,1);

                            } catch (Exception e) {

                                switch (busqueda.substring(0, 12)) {
                                    case "BEAGLES     ":
                                        busqueda = "A";
                                        break;
                                    case "GO GREEN    ":
                                        busqueda = "A";
                                        break;
                                    case "INTERCAMBIOS":
                                        busqueda = "J";
                                        break;
                                    case "LA CUEVA    ":
                                        busqueda = "J";
                                        break;
                                    case "CUEVA       ":
                                        busqueda = "J";
                                        break;
                                    case "NUBE        ":
                                        busqueda = "J";
                                        break;
                                    case "CAFETERIA   ":
                                        busqueda = "H";
                                        break;
                                    case "CAFE        ":
                                        busqueda = "H";
                                        break;
                                    case "BIBLIOTECA  ":
                                        busqueda = "B";
                                        break;
                                    case "BIBLIO      ":
                                        busqueda = "B";
                                        break;
                                    case "LABORATORIOS":
                                        busqueda = "C";
                                        break;
                                    case "LABS        ":
                                        busqueda = "C";
                                        break;
                                    case "SECRETARIA  ":
                                        busqueda = "F";
                                        break;
                                    case "Secre       ":
                                        busqueda = "F";
                                        break;
                                    case "RED         ":
                                        busqueda = "F";
                                        break;
                                }

                            }
                        }

                    }
                } catch (Exception e){
                    Log.d("BRO", e.toString());
                }

                Log.d("BRO", busqueda);

                // Terminan cambios

                Path path = new Path();

                if (!defaultLoc.equals(busqueda)){

                    modelUVG.mapear(path.findPath(defaultLoc,busqueda));
                    String prueba = path.findPath(defaultLoc,busqueda);
                    Log.d("BRO", prueba);

                }

                else {
                    modelUVG.mapear(busqueda);
                    modelUVG.allFalse();
                }

                //Para ocultar el teclado al realizar la busqueda:
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                jsonParams = new JSONObject();
                AsyncHttpClient client2 = new AsyncHttpClient();

                try {
                    jsonParams.put("points", "100");
                    jsonParams.put("GameId", Global.getLugares());
                    jsonParams.put("GameUserId", Global.getUserId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StringEntity entity = null;
                try {
                    entity = new StringEntity(jsonParams.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String restApiUrl = "https://experiencia-uvg.azurewebsites.net:443/api/addGamePoint";
                client2.post(getActivity().getApplicationContext(), restApiUrl, entity, "application/json",
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            }
                        });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        final DecimalFormat format = new DecimalFormat("0.0");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get("http://api.openweathermap.org/data/2.5/weather?appid=cc0540be06f088078fe5e86c5f703ea5&q=Guatemala", null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    Double celcius = Double.parseDouble(new JSONObject(jsonObject.getString("main")).getString("temp"));
                    celcius = celcius-273;
                    clima.setText(format.format(celcius)+"°C");

                }catch (JSONException ex){

                }


            }
        });

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        if (modelUVG.getLoading()){
            bar.setVisibility(View.INVISIBLE);
        }
        setMostrar(preferences.getString("Lugar","A"));
        modelUVG.setEstoy(mostrar);
        if(preferences.getBoolean("Cambio", false) == true){
            changeUiVisivility(false);
            cambiarPosicion();
            ((DrawerHandler) getActivity()).cambiarEstadoDrawer(false);
            preferences.edit().putBoolean("Cambio", false).apply();

            JSONObject jsonParams = new JSONObject();
            AsyncHttpClient client2 = new AsyncHttpClient();

            try {
                jsonParams.put("points", "100");
                jsonParams.put("GameId", Global.getMapa());
                jsonParams.put("GameUserId", Global.getUserId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringEntity entity = null;
            try {
                entity = new StringEntity(jsonParams.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String restApiUrl = "https://experiencia-uvg.azurewebsites.net:443/api/addGamePoint";
            client2.post(getActivity().getApplicationContext(), restApiUrl, entity, "application/json",
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        }
                    });

        }
        super.onResume();
    }

    public void setMostrar(String qranswer){
        mostrar = qranswer;
    }

    public void cambiarPosicion(){
        modelUVG.setEstoy(mostrar);
        modelUVG.irActual(mostrar);
        if (modelUVG.getisIrActual()){

            info.setVisibility(View.VISIBLE);
            // Se coloca la información del QR leído. JAVIER!!!
            informacion();
        }
    }

    public void informacion() {
        if (modelUVG.getEstoy().equals("A")) {
            String a = "Edificio A: Este es el edificio en el que más clases se imparten (tanto en primer año como en segundo).";
            info.setText(a);
        }
        else
        if(modelUVG.getEstoy().equals("B")){
            String b = "Edificio B: En este edificio encontrarás la biblioteca, el departamento de matemáticas, las oficinas de ayuda financiera y de bienestar estudiantil.";
            info.setText(b);
        }
        else
        if(modelUVG.getEstoy().equals("C")){
            String c = "Edificio C: En este edificio se encuentran todos los laboratorios (química, biología, física), también las oficias de los departamentos de física y química pura.";
            info.setText(c);
        }
        else
        if(modelUVG.getEstoy().equals("E")){
            String e = "Edificio E: En este edificio se encuentran tanto los laboratorios que utilizan los estudiantes de Ingeniería en Ciencia de Alimentos, como algunos salones de clases.";
            info.setText(e);
        }
        else
        if(modelUVG.getEstoy().equals("F")){
            String f = "Edificio F: Aquí se encuentra el auditorio F-103. También encontrarás el banco y las oficinas de secretaría.";
            info.setText(f);
        }
        else
        if(modelUVG.getEstoy().equals("G")){
            String g = "Edificio G: En este edificio se reciben clases dinámicas. Puedes pasar a visitar el G-204";
            info.setText(g);
        }
        else
        if(modelUVG.getEstoy().equals("H")){
            String h = "Edificio H: En este edificio se tiene aulas especiales, ¡puedes pasar a conocer H201!";
            info.setText(h);
        }
        else
        if(modelUVG.getEstoy().equals("I")){
            String i = "Edificio I: Se encuentra el auditorio (I-100). También encontrarás la cafetería y las decanaturas de educación y antropología.";
            info.setText(i);
        }
        else
        if(modelUVG.getEstoy().equals("J")){
            String j = "Edificio J: En este edificio se imparten clases de las carreras de: mecatrónica, electrónica y computación. También se pueden encontrar los laboratorios de computación (cueva) y de mecatrónica – electrónica (nube).";
            info.setText(j);
        }
        else
        if(modelUVG.getEstoy().equals("K")){
            String k = "Edificio K: Edificio de parqueos (únicamente para primer-segundo-cuarto-quinto) año(s).";
            info.setText(k);
        }
        else
        if(modelUVG.getEstoy().equals("II-1")){
            String ii1 = "Edificio II-1: En este edificio se encuentra el laboratorio de Ingeniería Química y Alimentos.";
            info.setText(ii1);
        }
        else
        if(modelUVG.getEstoy().equals("II-2")){
            String ii2 = "Edificio II-2: Es el centro de investigación de la Universidad del Valle de Guatemala.";
            info.setText(ii2);
        }
    }

    public void setDefaultLoc(String loc){
        defaultLoc = loc;
    }

    public void changeUiVisivility(boolean visible){
        if (visible){
            cardView.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            qrfab.setVisibility(View.VISIBLE);
            clima.setVisibility(View.VISIBLE);
            info.setVisibility(View.INVISIBLE);
        }else {
            cardView.setVisibility(View.INVISIBLE);
            searchView.setVisibility(View.INVISIBLE);
            qrfab.setVisibility(View.INVISIBLE);
            clima.setVisibility(View.INVISIBLE);
        }

    }


}
