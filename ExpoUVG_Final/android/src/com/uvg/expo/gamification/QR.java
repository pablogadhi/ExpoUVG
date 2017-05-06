package com.uvg.expo.gamification;


import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.uvg.expo.Global;
import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;



public class QR extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {

    private Button botonScan;
    private ZXingScannerView scanner;
    private boolean seBloqueoPantalla;
    private boolean cambioAplicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        //crear los objetos
        botonScan = (Button) findViewById(R.id.btnscan);
        botonScan.setOnClickListener(this);
    }

    // Inicia la camara para leer el QR
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnscan :{
                scanner = new ZXingScannerView(getApplicationContext());
                setContentView(scanner);
                scanner.setResultHandler(this);
                scanner.startCamera();
                break;
            }
        }
    }

    // permite detener sin error
    protected void onPause(){
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn = powerManager.isScreenOn();
        if (!isScreenOn) {
            seBloqueoPantalla = true;
        }

        try {
            super.onPause();
            scanner.stopCamera();
        } catch (NullPointerException exception) {
            super.onPause();
        }
    }

    // si el dispositivo se para la aplicacion no se cierra
    @Override
    protected void onStop() {
        cambioAplicacion = true;
        super.onStop();
    }

    // la aplicacion no se detiene sise camabia de aplicacion
    @Override
    protected void onResume() {
        if(seBloqueoPantalla && cambioAplicacion) {
            //Redirecciona a la actividad Login!.
            Intent myIntent = new Intent(this, RetosFragment.class);
            startActivity(myIntent);
            //reiniciamos valores.
            seBloqueoPantalla = false;
            cambioAplicacion = false;
        }
        super.onResume();
    }

    // Devuelve la informacion del QR
    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
        scanner.resumeCameraPreview(this);

        int contC = Global.getContC();
        int contD = Global.getContD();
        int contL = Global.getContL();

        if (result.getText().equals("visito la cueva")) {
            Global.setContC(Global.getContC()+1);
            Log.d("CantidadCueva", String.valueOf(Global.getContC()));
            if (contC == 1) {
                JSONObject jsonParams = new JSONObject();
                AsyncHttpClient client2 = new AsyncHttpClient();

                try {
                    jsonParams.put("points", "1000");
                    jsonParams.put("GameId", Global.getCueva());
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
                client2.post(getApplicationContext(), restApiUrl, entity, "application/json",
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            }
                        });
            }
        }

        if (result.getText().equals("conocio a Douglas")) {
            Global.setContD(Global.getContD()+1);
            Log.d("CantidadDouglas", String.valueOf(Global.getContD()));
            if (contD == 1) {
                JSONObject jsonParams = new JSONObject();
                AsyncHttpClient client2 = new AsyncHttpClient();

                try {
                    jsonParams.put("points", "1000");
                    jsonParams.put("GameId", Global.getDouglas());
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
                client2.post(getApplicationContext(), restApiUrl, entity, "application/json",
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            }
                        });
            }
        }

        if (result.getText().equals("encontro el libro Don Quijote de la Mancha")) {
            Global.setContL(Global.getContL()+1);
            Log.d("CantidadLibros", String.valueOf(Global.getContL()));
            if (contL == 1) {
                JSONObject jsonParams = new JSONObject();
                AsyncHttpClient client2 = new AsyncHttpClient();

                try {
                    jsonParams.put("points", "1000");
                    jsonParams.put("GameId", Global.getLibro());
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
                client2.post(getApplicationContext(), restApiUrl, entity, "application/json",
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            }
                        });
            }
        }

    }
}
