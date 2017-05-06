package com.uvg.expo.gamification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.uvg.expo.Global;
import com.uvg.expo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RetosFragment extends Fragment implements View.OnClickListener {

    JSONObject object, prueba1, prueba2, prueba3;
    Handler customHandler = new Handler();
    ImageView  r2i1,r2i2, r2i3, r3i1,r3i2, r3i3, r4i1,r4i2, r4i3, r5i1,r5i2, r5i3;
    ImageView r6i1, r6i2, r6i3, r7i1, r7i2, r7i3, r8i1, r8i2, r8i3, r9i1, r9i2, r9i3, r10i1, r11i1, r11i2, r11i3;
    int prueba;
    View view;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn11;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstantState){
        return inflater.inflate(R.layout.activity_retos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //crear los objetos
        object = new JSONObject();
        Handler customHandler = new Handler();


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

        btn11 = (Button) getView().findViewById(R.id.button11);
        btn11.setOnClickListener(this);

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
        r11i1 = (ImageView) getView().findViewById(R.id.reto11Img1);
        r11i2 = (ImageView) getView().findViewById(R.id.reto11Img2);
        r11i3 = (ImageView) getView().findViewById(R.id.reto11Img3);


        JSONObject jsonObject = new JSONObject();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("id", Global.getUserId());
        client.get("https://experiencia-uvg.azurewebsites.net:443/api/GameAchievements/{id}", params, new  JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("ERROR", "ERROR");
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                JSONArray jsonArray = response;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = jsonArray.getJSONObject(i);
                        Log.d("JSONACHIVE", jsonobject.toString());
                        String gameId = jsonobject.getString("gameId");
                        Log.d("gameId", gameId);
                        String points = jsonobject.getString("points");
                        Log.d("points", points);
                        switch (gameId){
                            case "16":
                                if (points.equals("1000")){
                                    r2i1.setImageResource(R.drawable.star_verde);
                                    r2i2.setImageResource(R.drawable.star_verde);
                                    r2i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "17":
                                if (points.equals("100")){
                                    r3i1.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("200")){
                                    r3i1.setImageResource(R.drawable.star_verde);
                                    r3i2.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("300")){
                                    r3i1.setImageResource(R.drawable.star_verde);
                                    r3i2.setImageResource(R.drawable.star_verde);
                                    r3i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "18":
                                if (points.equals("200")){
                                    r4i1.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("400")){
                                    r4i1.setImageResource(R.drawable.star_verde);
                                    r4i2.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("600")){
                                    r4i1.setImageResource(R.drawable.star_verde);
                                    r4i2.setImageResource(R.drawable.star_verde);
                                    r4i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "19":
                                if (points.equals("100")){
                                    r5i1.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("300")){
                                    r5i1.setImageResource(R.drawable.star_verde);
                                    r5i2.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("500")){
                                    r5i1.setImageResource(R.drawable.star_verde);
                                    r5i2.setImageResource(R.drawable.star_verde);
                                    r5i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "20":
                                if (points.equals("100")){
                                    r6i1.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("200")){
                                    r6i1.setImageResource(R.drawable.star_verde);
                                    r6i2.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("300")){
                                    r6i1.setImageResource(R.drawable.star_verde);
                                    r6i2.setImageResource(R.drawable.star_verde);
                                    r6i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "21":
                                if (points.equals("100")){
                                    r7i1.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("200")){
                                    r7i1.setImageResource(R.drawable.star_verde);
                                    r7i2.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("300")){
                                    r7i1.setImageResource(R.drawable.star_verde);
                                    r7i2.setImageResource(R.drawable.star_verde);
                                    r7i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "22":
                                if (points.equals("200")){
                                    r8i1.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("400")){
                                    r8i1.setImageResource(R.drawable.star_verde);
                                    r8i2.setImageResource(R.drawable.star_verde);
                                }
                                else if (points.equals("600")){
                                    r8i1.setImageResource(R.drawable.star_verde);
                                    r8i2.setImageResource(R.drawable.star_verde);
                                    r8i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "23":
                                if (points.equals("1000")){
                                    r9i1.setImageResource(R.drawable.star_verde);
                                    r9i2.setImageResource(R.drawable.star_verde);
                                    r9i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                            case "25":
                                if (points.equals("1000")){
                                    r11i1.setImageResource(R.drawable.star_verde);
                                    r11i2.setImageResource(R.drawable.star_verde);
                                    r11i3.setImageResource(R.drawable.star_verde);
                                }
                                break;
                        }

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d("waddup", jsonobject.toString());
                }
                //Log.d("matemnempls", jsonArray.toString());
            }
        });


        super.onActivityCreated(savedInstanceState);
    }


    @Override
    // botones de retos
    public void onClick(View v) {
        switch (v.getId()) {

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
