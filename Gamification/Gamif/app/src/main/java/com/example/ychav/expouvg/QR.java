package com.example.ychav.expouvg;


import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import org.json.JSONObject;
import org.json.JSONException;



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
            Intent myIntent = new Intent(this, MainActivity.class);
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
        JSONObject json = new JSONObject();
        // estructura del json
        // {usuario: "name", id: "23", reto: "result.getText(), "puntos", +200"}
        try {
            //se debe de obtener del webservices con los usuarios
            //json.put("usuario", user );
            //json.put("id", 23)
            //json.put("puntos", 100);
            json.put("reto", result.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
