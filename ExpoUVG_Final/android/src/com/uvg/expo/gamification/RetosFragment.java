package com.uvg.expo.gamification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RetosFragment extends Fragment implements View.OnClickListener {

    JSONObject object, prueba1, prueba2, prueba3;
    Handler customHandler = new Handler();
    ImageView r1i1,r1i2, r1i3, r2i1,r2i2, r2i3, r3i1,r3i2, r3i3, r4i1,r4i2, r4i3, r5i1,r5i2, r5i3;
    ImageView r6i1, r6i2, r6i3, r7i1, r7i2, r7i3, r8i1, r8i2, r8i3, r9i1, r9i2, r9i3, r10i1, r10i2, r10i3, r11i1, r11i2, r11i3;
    int prueba;
    View view;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstantState){
        return inflater.inflate(R.layout.activity_retos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //crear los objetos
        object = new JSONObject();
        Handler customHandler = new Handler();


        btn1 = (Button) getView().findViewById(R.id.button1);
        btn1.setOnClickListener(this);

        btn2 = (Button) getView().findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        btn3 = (Button) getView().findViewById(R.id.button3);
        btn3.setOnClickListener(this);

        btn4 = (Button) getView().findViewById(R.id.button4);
        btn4.setOnClickListener(this);

        btn5 = (Button) getView().findViewById(R.id.button5);
        btn5.setOnClickListener(this);

        btn6 = (Button) getView().findViewById(R.id.button6);
        btn6.setOnClickListener(this);

        btn7 = (Button) getView().findViewById(R.id.button7);
        btn7.setOnClickListener(this);

        btn8 = (Button) getView().findViewById(R.id.button8);
        btn8.setOnClickListener(this);

        btn9 = (Button) getView().findViewById(R.id.button9);
        btn9.setOnClickListener(this);

        btn10 = (Button) getView().findViewById(R.id.button10);
        btn10.setOnClickListener(this);

        btn11 = (Button) getView().findViewById(R.id.button11);
        btn11.setOnClickListener(this);

        r1i1 = (ImageView) getView().findViewById(R.id.reto1Img1);
        r1i1.setVisibility(View.INVISIBLE);
        r1i2 = (ImageView) getView().findViewById(R.id.reto1Img2);
        r1i3 = (ImageView) getView().findViewById(R.id.reto1Img3);
        r2i1 = (ImageView) getView().findViewById(R.id.reto2Img1);
        r2i2 = (ImageView) getView().findViewById(R.id.reto2Img2);
        r2i3 = (ImageView) getView().findViewById(R.id.reto2Img3);
        r3i1 = (ImageView) getView().findViewById(R.id.reto3Img1);
        r3i2 = (ImageView) getView().findViewById(R.id.reto3Img2);
        r3i3 = (ImageView) getView().findViewById(R.id.reto3Img3);
        r4i1 = (ImageView) getView().findViewById(R.id.reto4Img1);
        r4i2 = (ImageView) getView().findViewById(R.id.reto4Img2);
        r4i3 = (ImageView) getView().findViewById(R.id.reto4Img3);
        r5i1 = (ImageView) getView().findViewById(R.id.reto5Img1);
        r5i2 = (ImageView) getView().findViewById(R.id.reto5Img2);
        r5i3 = (ImageView) getView().findViewById(R.id.reto5Img3);
        r6i1 = (ImageView) getView().findViewById(R.id.reto6Img1);
        r6i2 = (ImageView) getView().findViewById(R.id.reto6Img2);
        r6i3 = (ImageView) getView().findViewById(R.id.reto6Img3);
        r7i1 = (ImageView) getView().findViewById(R.id.reto7Img1);
        r7i2 = (ImageView) getView().findViewById(R.id.reto7Img2);
        r7i3 = (ImageView) getView().findViewById(R.id.reto7Img3);
        r8i1 = (ImageView) getView().findViewById(R.id.reto8Img1);
        r8i2 = (ImageView) getView().findViewById(R.id.reto8Img2);
        r8i3 = (ImageView) getView().findViewById(R.id.reto8Img3);
        r9i1 = (ImageView) getView().findViewById(R.id.reto9Img1);
        r9i2 = (ImageView) getView().findViewById(R.id.reto9Img2);
        r9i3 = (ImageView) getView().findViewById(R.id.reto9Img3);
        r10i1 = (ImageView) getView().findViewById(R.id.reto10Img1);
        r10i2 = (ImageView) getView().findViewById(R.id.reto10Img2);
        r10i3 = (ImageView) getView().findViewById(R.id.reto10Img3);
        r11i1 = (ImageView) getView().findViewById(R.id.reto11Img1);
        r11i2 = (ImageView) getView().findViewById(R.id.reto11Img2);
        r11i3 = (ImageView) getView().findViewById(R.id.reto11Img3);

        prueba = 1;

        prueba1 = new JSONObject();
        prueba2 = new JSONObject();
        prueba3 = new JSONObject();

        startTask();

        super.onActivityCreated(savedInstanceState);
    }

    public void startTask(){
        updateTimerThread.run();
    }

    Runnable updateTimerThread = new Runnable() {
        public void run() {

            int call1 = 1;
            int call2 = 1;
            int call3 = 1;


            if(prueba == 1){
                prueba = 2;
            }
            else if(prueba == 2){
                prueba = 3;
            }
            else if (prueba == 3){
                prueba = 1;
            }

//put int in JSON
            try {
                prueba1.put("a", prueba);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//get int from JSON
            try {
                call1 = prueba1.getInt("a");
            } catch (JSONException e) {
                e.printStackTrace();
            }

//primera fila de estrellas
            if (call1 == 1){
                r1i1.setVisibility(View.VISIBLE);
                r1i2.setVisibility(View.INVISIBLE);
                r1i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 2){
                r1i1.setVisibility(View.VISIBLE);
                r1i2.setVisibility(View.VISIBLE);
                r1i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 3){
                r1i1.setVisibility(View.VISIBLE);
                r1i2.setVisibility(View.VISIBLE);
                r1i3.setVisibility(View.VISIBLE);
            }

            //segunda fila de estrellas
            if (call1 == 1){
                r2i1.setVisibility(View.VISIBLE);
                r2i2.setVisibility(View.INVISIBLE);
                r2i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 2){
                r2i1.setVisibility(View.VISIBLE);
                r2i2.setVisibility(View.VISIBLE);
                r2i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 3){
                r2i1.setVisibility(View.VISIBLE);
                r2i2.setVisibility(View.VISIBLE);
                r2i3.setVisibility(View.VISIBLE);
            }

            //Tercera fila de estrellas
            if (call1 == 1){
                r3i1.setVisibility(View.VISIBLE);
                r3i2.setVisibility(View.INVISIBLE);
                r3i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 2){
                r3i1.setVisibility(View.VISIBLE);
                r3i2.setVisibility(View.VISIBLE);
                r3i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 3){
                r3i1.setVisibility(View.VISIBLE);
                r3i2.setVisibility(View.VISIBLE);
                r3i3.setVisibility(View.VISIBLE);
            }
            //cuarta fila de estrellas
            if (call1 == 1){
                r4i1.setVisibility(View.VISIBLE);
                r4i2.setVisibility(View.INVISIBLE);
                r4i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 2){
                r4i1.setVisibility(View.VISIBLE);
                r4i2.setVisibility(View.VISIBLE);
                r4i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 3){
                r4i1.setVisibility(View.VISIBLE);
                r4i2.setVisibility(View.VISIBLE);
                r4i3.setVisibility(View.VISIBLE);
            }
            //quinta fila de estrellas
            if (call1 == 1){
                r5i1.setVisibility(View.VISIBLE);
                r5i2.setVisibility(View.INVISIBLE);
                r5i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 2){
                r5i1.setVisibility(View.VISIBLE);
                r5i2.setVisibility(View.VISIBLE);
                r5i3.setVisibility(View.INVISIBLE);
            }
            else if (call1 == 3){
                r5i1.setVisibility(View.VISIBLE);
                r5i2.setVisibility(View.VISIBLE);
                r5i3.setVisibility(View.VISIBLE);
            }
            //Repetimos la busqueda de JSONs
            customHandler.postDelayed(this, 1000);


        }
    };

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
                Intent intRetos = new Intent(RetosFragment.this, RetosFragment.class);
                startActivity(intRetos);
                break;
            // cambiar a la activity del usuario
            case R.id.btnCard:
                Intent intUser = new Intent(RetosFragment.this, Usuario.class);
                startActivity(intUser);
            break;
            // cambiar a la activity del usuario
            case R.id.btnLeaderBoard:
                Intent intLeader = new Intent(RetosFragment.this, LeaderboardFragment.class);
                startActivity(intLeader);
                break;
        }

        return true;
    }
     */
    @Override
    // botones de retos
    public void onClick(View v) {
        switch (v.getId()) {
            // muestra el reto

           case R.id.button1:

                AlertDialog.Builder  Reto1 = new AlertDialog.Builder(getActivity());
                Reto1.setView(R.layout.reto);
                Reto1.setMessage(R.string.Reto1);
                Reto1.setTitle("Expo UVG");
                Reto1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intQr = new Intent(thisd, RetosFragment.class);
                        //startActivity(intQr);
                    }
                });
                Reto1.show();

                break;

// muestra el reto
            case R.id.button2:
                AlertDialog.Builder  Reto2 = new AlertDialog.Builder(getActivity());
                Reto2.setView(R.layout.reto);
                Reto2.setMessage(R.string.Reto2);
                Reto2.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto2.setPositiveButton("Validar QR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intQr = new Intent(getActivity(), QR.class);
                        startActivity(intQr);
                    }
                });
                Reto2.show();

                break;
            // muestra el reto
            case R.id.button3:
                AlertDialog.Builder  Reto3 = new AlertDialog.Builder(getActivity());
                Reto3.setView(R.layout.reto);
                Reto3.setMessage(R.string.Reto3);
                Reto3.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Reto3.show();

                break;
            // muestra el reto
            case R.id.button4:
                AlertDialog.Builder  Reto4 = new AlertDialog.Builder(getActivity());
                Reto4.setView(R.layout.reto);
                Reto4.setMessage(R.string.Reto4);
                Reto4.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto4.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Intent intQr = new Intent(getActivity(), RetosFragment.class);
                        //startActivity(intQr);
                    }
                });
                Reto4.show();

                break;

            // muestra el reto
            case R.id.button5:
                AlertDialog.Builder  Reto5 = new AlertDialog.Builder(getActivity());
                Reto5.setView(R.layout.reto);
                Reto5.setMessage(R.string.Reto5);
                Reto5.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto5.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Intent intQr = new Intent(RetosFragment.this, RetosFragment.class);
                        //startActivity(intQr);
                    }
                });
                Reto5.show();

                break;

            // muestra el reto
            case R.id.button6:
                AlertDialog.Builder  Reto6 = new AlertDialog.Builder(getActivity());
                Reto6.setView(R.layout.reto);
                Reto6.setMessage(R.string.Reto6);
                Reto6.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto6.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Intent intQr = new Intent(RetosFragment.this, RetosFragment.class);
                        //startActivity(intQr);
                    }
                });
                Reto6.show();

                break;
            // muestra el reto
            case R.id.button7:
                AlertDialog.Builder  Reto7 = new AlertDialog.Builder(getActivity());
                Reto7.setView(R.layout.reto);
                Reto7.setMessage(R.string.Reto7);
                Reto7.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto7.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  Intent intQr = new Intent(RetosFragment.this, RetosFragment.class);
                       // startActivity(intQr);
                    }
                });
                Reto7.show();

                break;
            // muestra el reto
            case R.id.button8:
                AlertDialog.Builder  Reto8 = new AlertDialog.Builder(getActivity());
                Reto8.setView(R.layout.reto);
                Reto8.setMessage(R.string.Reto8);
                Reto8.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto8.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  Intent intQr = new Intent(RetosFragment.this, RetosFragment.class);
                       // startActivity(intQr);
                    }
                });
                Reto8.show();

                break;
            case R.id.button9:
                AlertDialog.Builder  Reto9 = new AlertDialog.Builder(getActivity());
                Reto9.setView(R.layout.reto);
                Reto9.setMessage(R.string.Reto9);
                Reto9.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto9.setPositiveButton("Validar QR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intQr = new Intent(getActivity() , QR.class);
                        startActivity(intQr);
                    }
                });
                Reto9.show();

                break;
            // muestra el reto
            case R.id.button10:
                AlertDialog.Builder  Reto10 = new AlertDialog.Builder(getActivity());
                Reto10.setView(R.layout.reto);
                Reto10.setMessage(R.string.Reto10);
                Reto10.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto10.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intQr = new Intent(RetosFragment.this, RetosFragment.class);
                        //startActivity(intQr);
                    }
                });
                Reto10.show();

                break;

            case R.id.button11:
                AlertDialog.Builder  Reto11 = new AlertDialog.Builder(getActivity());
                Reto11.setView(R.layout.reto);
                Reto11.setMessage(R.string.Reto11);
                Reto11.setTitle("Expo UVG");
                // al presionar validar te envia al lector de qr
                Reto11.setPositiveButton("Validar QR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intQr = new Intent(getActivity(), QR.class);
                        startActivity(intQr);
                    }
                });
                Reto11.show();

                break;
        }



    }
}
