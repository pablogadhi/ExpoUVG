package com.uvg.expo.gamification;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;

public class Usuario extends Fragment implements View.OnClickListener{

    //Instanciar objetos

    TextView puntos; // un get del webservices para saber los puntos y colocarlos aca
    ProgressBar bar;    // Con base al get realizado previamente para determinar el valor
    TextView nombre;  /// con base al get realizado obtener el nombre del usuario
    int cant;


    Button btnfoto;
    private ImageView imvFoto;
    private static final int PICK_IMAGE = 100;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_usuario, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        btnfoto = (Button) getView().findViewById(R.id.btnfoto);
        btnfoto.setOnClickListener(this);
        imvFoto = (ImageView) getView().findViewById(R.id.imvFoto);
        bar = (ProgressBar) getView().findViewById(R.id.pgbLogros);
        puntos = (TextView) getView().findViewById(R.id.textpuntos);
        nombre = (TextView) getView().findViewById(R.id.txtNombre);
        super.onActivityCreated(savedInstanceState);

        bar.setMax(2400);
        bar.setProgress(1000);

        JSONObject jsonObject = new JSONObject();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userId", "58f7d855cd7b6c00045c2603");
        client.get("https://expo-uvg.herokuapp.com/api/points/get", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                puntos.setText(responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                puntos.setText(responseString + " Puntos");
            }
        });

        cant = 500;
        bar.setMax(2400);
        bar.setProgress(cant);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // cambiar la foto
            case R.id.btnfoto: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }

        }
    }

    // permite accesar a la galeria del telefono
    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    //cambio de la imagen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imvFoto.setImageURI(imageUri);
        }
    }
}
