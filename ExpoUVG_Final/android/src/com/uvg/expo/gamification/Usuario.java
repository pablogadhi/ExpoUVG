package com.uvg.expo.gamification;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uvg.expo.R;

import static android.app.Activity.RESULT_OK;

public class Usuario extends android.support.v4.app.Fragment implements View.OnClickListener{

    //Instanciar objetos

    TextView puntos; // un get del webservices para saber los puntos y colocarlos aca
    ProgressBar bar;    // Con base al get realizado previamente para determinar el valor
    TextView nombre;  /// con base al get realizado obtener el nombre del usuario

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
    /**

    // visibilidad del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
        return true;
    }

    // eventos en el menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            // cambiar a la activity del usuario
            case R.id.btnRetos:
                Intent intRetos = new Intent(Usuario.this, GamificationMain.class);
                startActivity(intRetos);
                break;
            // cambiar a la activity del usuario
            case R.id.btnCard:
                Intent intUser = new Intent(Usuario.this, Usuario.class);
                startActivity(intUser);
                break;
            // cambiar a la activity del usuario
            case R.id.btnLeaderBoard:
                Intent intLeader = new Intent(Usuario.this, LeaderboardFragment.class);
                startActivity(intLeader);
                break;
        }

        return true;
    }
    */
}
