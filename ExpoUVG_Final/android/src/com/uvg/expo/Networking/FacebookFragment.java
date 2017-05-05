package com.uvg.expo.Networking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.uvg.expo.R;


public class FacebookFragment extends Fragment {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.facebook_activity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        AppEventsLogger.activateApp(getActivity().getApplication());
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        final Bitmap image;
        image = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), R.mipmap.ic_launcher);

        Button btShare, btLink, btLike;

        btShare = (Button) getView().findViewById(R.id.btShare);
        btLink = (Button) getView().findViewById(R.id.btLink);
        btLike = (Button) getView().findViewById(R.id.btLike);

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
                }
            }
        });


        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void onNumeral(View v) {
            ImageButton entry = (ImageButton) getView().findViewById(R.id.imExpo);
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
