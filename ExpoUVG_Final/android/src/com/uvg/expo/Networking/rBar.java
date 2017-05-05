package com.uvg.expo.Networking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uvg.expo.R;

public class rBar extends AppCompatActivity {



    RatingBar ratingBar;
    Button button, savebutton, editbutton;
    ImageButton backbutton;
    ImageView standimage;
    TextView ratingcount;
    EditText comments;
    int standNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_bar);

        standimage = (ImageView) findViewById(R.id.standimage);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.button);
        ratingcount = (TextView) findViewById(R.id.ratingcount);
        savebutton = (Button) findViewById(R.id.savebutton);
        editbutton = (Button) findViewById(R.id.editbutton);
        comments = (EditText) findViewById(R.id.userinput);

        // standNo ES LA VARIABLE QUE GUARDA EL VALOR QUE DEVUELVE EL METODO PARA SABER QUE STAND ES
        // AHORITA SOLO HAY 3  STANDS, PERO SE TIENEN QUE AGREGAR TODAS LAS FOTOS DE LOS STANDS.
        standNo = 3;


        switch (standNo){
            case 1:
                standimage.setImageResource(R.drawable.stand1);
                break;
            case 2:
                standimage.setImageResource(R.drawable.stand2);
                break;
            case 3:
                standimage.setImageResource(R.drawable.stand3);
                break;
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingcount.setText("Rating: "+ (int)ratingBar.getRating());
                Toast.makeText(rBar.this, "stars: " + (int) ratingBar.getRating(),Toast.LENGTH_LONG).show();

            }
        });


        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rBar.this, "COMENTARIOS GUARDADOS", Toast.LENGTH_LONG).show();
                comments.setEnabled(false);
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments.setEnabled(true);
            }
        });

    }

}
