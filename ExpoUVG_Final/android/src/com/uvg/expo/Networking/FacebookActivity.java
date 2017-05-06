package com.uvg.expo.Networking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.uvg.expo.Global;
import com.uvg.expo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.Header;


public class FacebookActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_activity);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        AppEventsLogger.activateApp(getApplication());
        FacebookSdk.sdkInitialize(getApplicationContext());

        final Bitmap image;
        image = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);

        Button btShare, btLink, btLike;

        btShare = (Button) findViewById(R.id.btShare);
        btLink = (Button) findViewById(R.id.btLink);
        btLike = (Button) findViewById(R.id.btLike);

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Compartir fotos en Facebook
                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();

                    //#ExpoUVG
                    ShareLinkContent.Builder content2 = new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://www.facebook.com/universidaddelvallegt/"))
                            .setShareHashtag(new ShareHashtag.Builder().setHashtag("#ExpoUVG").build());

                    shareDialog.show(content);

                    JSONObject jsonParams = new JSONObject();
                    AsyncHttpClient client2 = new AsyncHttpClient();

                    try {
                        jsonParams.put("points", "100");
                        jsonParams.put("GameId", Global.getPublicaciones());
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
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                }
                            });
                }
            }
        });
        btLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Compartir el link de la pagina de la UVG
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(" Universidad del Valle de Guatemala")
                            .setContentDescription("Conoce los servicios que tenemos para ti.")
                            .setContentUrl(Uri.parse("http://www.uvg.edu.gt/")).build();
                    shareDialog.show(linkContent);

                    JSONObject jsonParams = new JSONObject();
                    AsyncHttpClient client2 = new AsyncHttpClient();

                    try {
                        jsonParams.put("points", "100");
                        jsonParams.put("GameId", Global.getRecomendar());
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
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                }
                            });

                }
            }
        });
        btLike.setOnClickListener (new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Compartir el perfil de la pagina en Facebook de la UVG
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Fanpage Universidad del Valle de Guatemala")
                            .setContentDescription("Sigue de cerca las actividades de la universidad.")
                            .setContentUrl(Uri.parse("https://www.facebook.com/universidaddelvallegt/")).build();
                    shareDialog.show(linkContent);

                    JSONObject jsonParams = new JSONObject();
                    AsyncHttpClient client2 = new AsyncHttpClient();

                    try {
                        jsonParams.put("points", "100");
                        jsonParams.put("GameId", Global.getRecomendar());
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
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                }
                            });
                }
            }
        });
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void onNumeral(View v) {
            ImageButton entry = (ImageButton) findViewById(R.id.imExpo);
            entry.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("https://www.facebook.com/hashtag/expouvg");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }


    }
