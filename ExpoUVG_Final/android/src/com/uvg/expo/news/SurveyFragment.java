package com.uvg.expo.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidadvance.androidsurvey.SurveyActivity;
import com.uvg.expo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

public class SurveyFragment extends Fragment {
    boolean NoTermino=true;
    String jsons="";

    public class QueryTaskBotones extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            Log.d("*********** ","EMPECEMOS********************");
            String resultado="";
            try {
                //ESTA ES LA LINEA CON EL ERROR, COMO ESTO DA ERROR REGRESA EL STRING VACIO
                //CON EL STRING VACIO HACE EL JSON VACIO Y POR ESO DA EL NULLPOINTER DESPUES
                URL url = new URL("https://experiencia-uvg.azurewebsites.net:443/api/Survey/Details");
                resultado = NetworkUtils.getResponseFromHttpUrl(url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("s: ",s);
            RelativeLayout myLayout = (RelativeLayout) getView().findViewById(R.id.d);
            LinearLayout tuamamaLayout = (LinearLayout) getView().findViewById(R.id.tumama);
            JSONObject j;
            JSONArray ja=null;
            try {
                URL SearchURL = NetworkUtils.buildUrl(1,"");
                j = new JSONObject(s);
                ja = j.getJSONArray("Surveys");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int id_count = 0;
            int tmp = 0;
            RelativeLayout.LayoutParams rlp;
            for (int i = 0; i<ja.length(); i++) {
                try {
                    tmp = ja.getJSONObject(i).getInt("SurveyId");
                    id_count = id_count + 1;
                    Button btn = new Button(getContext());
                    btn.setId(i+1);
                    btn.setText(ja.getJSONObject(i).getString("Name"));
                    Log.d("s",ja.getJSONObject(i).getString("Name"));
                    btn.setMinHeight(30);
                    btn.setBackgroundColor(0xFFFFFFFF);
                    LinearLayout.LayoutParams llp= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70,1f);
                    rlp= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    btn.setLayoutParams(llp);
                    final int index = i;
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent i_survey = new Intent(getActivity(), SurveyActivity.class);
                            //you have to pass as an extra the json string.
                            i_survey.putExtra("json_survey", loadSurveyJson("ejemplo.json"));
                            startActivityForResult(i_survey, SURVEY_REQUEST);
                            Log.i("TAG", "The index is" + index);
                        }
                    });
                    tuamamaLayout.addView(btn);
                }
                catch (JSONException e) {
                }
            }
            //getView().findViewById(R.id.cargar).setVisibility(View.GONE);
            super.onPostExecute(s);
        }
    }

    private static final int SURVEY_REQUEST = 1337;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_survey,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        new QueryTaskBotones().execute();
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                //do whatever you want with them...
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getActivity().getAssets().open(filename);
            int size;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
