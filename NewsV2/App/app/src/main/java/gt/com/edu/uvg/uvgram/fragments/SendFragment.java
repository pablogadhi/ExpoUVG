package gt.com.edu.uvg.uvgram.fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import gt.com.edu.uvg.uvgram.R;
import gt.com.edu.uvg.uvgram.activities.MainTabbedActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class SendFragment extends Fragment {

    private ImageView imageView;
    private Button btnImagen;
    private EditText editText;
    private Button btnEnviar;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String tweetText;
    private Bitmap bitmap;
    private static final String API1 = "http://ec2-54-213-143-106.us-west-2.compute.amazonaws.com/upload_tweet";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.send_tweet, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        btnImagen = (Button) rootView.findViewById(R.id.btnImagen);
        btnEnviar = (Button) rootView.findViewById(R.id.btnTweetear);
        editText = (EditText) rootView.findViewById(R.id.editText);

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButton();
            }
        });
        return rootView;
    }

    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void sendButton() {

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            final ProgressDialog loading = ProgressDialog.show(getActivity(),"Subiendo...","Espere...");
            tweetText = editText.getText().toString();
            SendTweet sendTweet = new SendTweet(loading);
            sendTweet.execute();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            }catch (Exception ex){
                int x = 1;
            }
        }

    }

    private class SendTweet extends AsyncTask<String, Integer, Integer> {

        OkHttpClient client = new OkHttpClient();
        ProgressDialog diag;

        public SendTweet(ProgressDialog diag) {
            super();
            this.diag = diag;
        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("tweet", tweetText)
                        .addFormDataPart("image", "image.png",
                                RequestBody.create(MEDIA_TYPE_PNG, new File(getRealPathFromUri(getActivity().getBaseContext(), imageUri))))
                        .build();

                Request request = new Request.Builder()
                        .url(API1)
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Errorazo " + response);

                System.out.println(response.body().string());
                return 1;
            }catch (IOException ex){
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            diag.dismiss();
        }


    }



    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



}
