package com.uvg.expo.login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Gender;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.uvg.expo.Global;
import com.uvg.expo.R;
import com.uvg.expo.map.RenderCreation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static com.loopj.android.http.AsyncHttpClient.HEADER_CONTENT_TYPE;

public class LoginActivity extends AppCompatActivity {

    //--------------Base de datos, Informacion del usuario-----------//
    private String nombre;
    private String apellido;
    private String edad;
    private String Genero;
    private String Email;


    //---------------Correo-----------------------//
    TextView registrarse;
    Button btnreg;
    EditText emails,pass;
    
    //---------------GooglePlus, WebService---------------//
    String names;
    String mail;
    
    // ------------facebook------------------//

    //El boton para ingresar
    private LoginButton btn_login;
    //callback de facebook
    private CallbackManager callbackManager;

    //------------Google-------------------//
    private SignInButton goo_button;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Peoples App";

    //------------firebase------------------//

    //referencia  firebase
    private FirebaseAuth mAuth;
    //cambios de sesion
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    //Base de datos
    DatabaseReference databaseUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //--------------------Firebase---------------//
        //se realiza una instacia con firebase
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        //Determinar si el usuario ha ingresado o no
        firebaseAuthListner = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //cuando el usuario vuelva a ingresar, ya no sera necesario que se registre,
                    //la aplicacion determina automaticamente si esta conectado
                    //goMainScreen();
                }
            }
        };
        //se genera una instacia der la base de datos
        databaseUsuarios = FirebaseDatabase.getInstance().getReference("Usuario");

        //-------------------Correo --------------------//
        pass = (EditText)findViewById(R.id.pass);
        btnreg = (Button)findViewById(R.id.btnreg);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(emails.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(emails.getText().toString(), pass.getText().toString());
            }
        });
        registrarse = (TextView)findViewById(R.id.register);
        registrarse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registrar.class);
                startActivity(intent);
            }
        });
        emails = (EditText)findViewById(R.id.emails);

        //------------------facebook-----------------
        callbackManager = CallbackManager.Factory.create();
        //el boton para ingresar
        btn_login = (LoginButton) findViewById(R.id.btn_login);
        //se piden los permisos para obtener la informacion que queramos
        btn_login.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        //Se ejecuta el inicio de sesion
        btn_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //si es satisfactorio se obtiene el token
            public void onSuccess(LoginResult loginResult) {
                setFacebookData(loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                //si cancela, solo aparece un mensaje y regresa a la pantalla de login
                Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                //Si existe algun error unicamente aparece un mensaje de error
                Toast.makeText(LoginActivity.this, "error to Login Facebook", Toast.LENGTH_SHORT).show();
            }
        });

        //----------------------Google Plus--------------------------//
        // Configure Google Sign In
        goo_button = (SignInButton)findViewById(R.id.goo_button);
        //Sew utiliza para pedir la informacion del usuario
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.clientID))
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestProfile()
                .requestEmail()
                .build();

        //Utilizado para conectarse a la api de google
        mGoogleApiClient  = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        //Realiza el login cuando pulsan el boton
        goo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    //--------------------Autenticacion, guardar informacion del usuario en firebase------------------------
    private void handleFacebookAccessToken(final AccessToken accessToken) {
        //Credenciales del usuario, para guardarlas
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //si existe un error, el usuario no va a poder ingresar, y aparecera un mensaje de error
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
                    // Si logra guardar la informacion en firebase, se abre la otra pestaña donde se envuentra la informacion de su perfil
                }else{
                    Toast.makeText(LoginActivity.this,"Se ha registrado correctacmente",Toast.LENGTH_SHORT).show();
                    goMainScreen();

                }
            }
        });
    }
    //Obtener respuesta de facebook y google+
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                new PeoplesAsync().execute(acct.getServerAuthCode());
                firebaseAuthWithGoogle(account);
            } else {
            }
        }
    }

    //control de proceso de firebase
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListner);
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListner);
    }

    //----------------------login correo --------------
    private void loginUser(String email, final String password) {
        //verifica con firebase si el usuario ya esta registrado en ola base de datos
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //si el usuario no se encuentra registrado en la base de datos,
                        // no podra ingresar y aparecera un error
                        if(!task.isSuccessful())
                        {
                             Toast.makeText(LoginActivity.this,"Este usuario no se encuentra registrado",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //si logra ingresar, el usuario podra segui a la siguiente pestaña
                            Toast.makeText(LoginActivity.this,"Se ha registrado correctacmente",Toast.LENGTH_SHORT).show();
                            goMainScreen();
                        }
                    }
                });
    }

    //-------------------------Google plus Sign In---------------------------//
    private void signIn() {
        // Realiza un intent para poder ingresar a google+
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Ingresa al usuario utilizando firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //si nose completa, aparecera un error
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Error, no se pudo registrar",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //si se completa el usuario puede seguir a la siguiente pestaña
                        else{
                            Toast.makeText(LoginActivity.this,"Se ha registrado correctacmente",Toast.LENGTH_SHORT).show();
                            addUserWebService(mail,names);
                            goMainScreen();
                        }
                    }
                });
    }



    //envia al usuario a la siguiente pestaña
    public void goMainScreen(){
        Intent intent = new Intent(this, RenderCreation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Email = response.getJSONObject().getString("email");
                            nombre = response.getJSONObject().getString("first_name");
                            apellido  = response.getJSONObject().getString("last_name");
                            Genero = response.getJSONObject().getString("gender");
                            edad = response.getJSONObject().getString("age_range");

                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();


                            JSONObject jsonParams = new JSONObject();
                            AsyncHttpClient client2 = new AsyncHttpClient();

                            try {
                                jsonParams.put("UserName", nombre);
                                jsonParams.put("Email", Email);
                                jsonParams.put("Password", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            StringEntity entity = null;
                            try {
                                entity = new StringEntity(jsonParams.toString());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            String restApiUrl = "https://experiencia-uvg.azurewebsites.net:443/api/GameUsersApi";
                            client2.post(getApplicationContext(), restApiUrl, entity, "application/json",
                                    new  JsonHttpResponseHandler(){

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                                            // Handle resulting parsed JSON response here
                                            JSONObject respuesta = response;
                                            Log.d("Json",respuesta.toString());
                                            try {
                                                String id = respuesta.getString("ID");
                                                Log.d("id", id);
                                                String name = respuesta.getString("username");
                                                Log.d("name", name);

                                            } catch (JSONException e) {
                                                //onFailure(statusCode, headers, e, (JSONObject)null);
                                                e.printStackTrace();
                                            }


                                        }

                                    });

                            addUser(nombre,apellido,edad,Genero,Email);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender,age_range");
        request.setParameters(parameters);
        request.executeAsync();
    }
    class PeoplesAsync extends AsyncTask< String, Void,List<String>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> nameList = new ArrayList<>();

            try {

                People peopleService = PeopleHelper.setUp(LoginActivity.this,params[0]);
                Person profiles = peopleService.people().get("people/me").execute();
                String gender = null;
                List<Gender> genders = profiles.getGenders();
                List<Name> name = profiles.getNames();
                if(name != null && name.size() > 0) {
                    for(Name personName: name) {
                        names = personName.getDisplayName();
                    }}
                String age = profiles.getAgeRange();
                List<EmailAddress> email = profiles.getEmailAddresses();
                mail = email.get(0).toString();

                if (genders != null && genders.size() > 0) {
                    gender = genders.get(0).getValue();
                }
                
                addUser(names, names, age, gender, emails);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return nameList;
        }

    }
    private void addUser(String nombre,String apellido,String Edad,String Genero,String Email ) {
        String id = databaseUsuarios.push().getKey();
        User usuario = new User(id, nombre, apellido, Edad, Genero, Email);
        databaseUsuarios.child(id).setValue(usuario);

    }
    
    private void addUserWebService(String email,String user){
        JSONObject jsonParams = new JSONObject();
        AsyncHttpClient client2 = new AsyncHttpClient();

        try {
            jsonParams.put("UserName", user);
            jsonParams.put("Email", email );
            jsonParams.put("Password", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String restApiUrl = "https://experiencia-uvg.azurewebsites.net:443/api/GameUsersApi";
        client2.post(getApplicationContext(), restApiUrl, entity, "application/json",
                new  JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                        // Handle resulting parsed JSON response here
                        JSONObject respuesta = response;
                        Log.d("Json",respuesta.toString());
                        try {
                            String id = respuesta.getString("ID");
                            Log.d("id", id);
                            String name = respuesta.getString("username");
                            Log.d("name", name);

                        } catch (JSONException e) {
                            //onFailure(statusCode, headers, e, (JSONObject)null);
                            e.printStackTrace();
                        }


                    }

                });

    }

}
