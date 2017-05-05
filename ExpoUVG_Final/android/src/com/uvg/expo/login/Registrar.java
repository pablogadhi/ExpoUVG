package com.uvg.expo.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.uvg.expo.R;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Registrar extends AppCompatActivity implements View.OnClickListener{

    EditText email,pass;
    EditText nomcom,genero;
    Button btn_reg;

    //se llama a la base de datos para registrar al usuario
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        btn_reg  = (Button)findViewById(R.id.btn_reg);
        //edit texts
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        nomcom = (EditText)findViewById(R.id.nomcom);
        genero = (EditText)findViewById(R.id.genero);

        btn_reg.setOnClickListener(this);
        //se llama a una instancia de firebase
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

    }

    //Cuando pulse el boton se registrara el usuario
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_reg){
            String emails = email.getText().toString().trim();
            String password = pass.getText().toString().trim();
            if (TextUtils.isEmpty(emails)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }
            auth.createUserWithEmailAndPassword(emails, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Registrar.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Registrar.this, "Se ha registrado!",
                                        Toast.LENGTH_SHORT).show();
                                String url = "https://experiencia-uvg.azurewebsites.net:443/api/GameUsersApi";
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();

                                JSONObject jsonObject = new JSONObject();
                                //jsonObject.put("UserName")
                                try {
                                    jsonObject.putOpt("UserName", nomcom.getText().toString());
                                    jsonObject.put("Email", email.getText().toString());
                                    jsonObject.put("Password", "");
                                }catch (Exception e) {

                                }
                                params.put("gameUserApi", jsonObject);
                                client.addHeader("Content-Type", "application/x-www-form-urlencoded");
                                        client.post(url, params, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                                                // Handle resulting parsed JSON response here
                                                JSONObject respuesta = response;
                                                Log.d("Json",respuesta.toString());
                                                startActivity(new Intent(Registrar.this,Perfil.class));
                                                finish();


                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                            }
                                        });

                            }
                        }
                    });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
