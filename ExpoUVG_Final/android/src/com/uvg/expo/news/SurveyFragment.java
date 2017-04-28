//package com.example.rodrigo.survey;
package com.uvg.expo.news;

import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.androidadvance.androidsurvey.SurveyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class SurveyFragment extends Fragment {

    private static final int SURVEY_REQUEST = 1337;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        RelativeLayout myLayout = (RelativeLayout) getView().findViewById(R.id.d);
        JSONObject j;
        JSONArray ja=null;
        try {
            j = new JSONObject(loadSurveyJson("encuestas.json"));
            ja = j.getJSONArray("Encuestas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int id_count = 0;
        int tmp = 0;

        for (int i = 0; i<ja.length(); i++) {
            try {
                tmp = ja.getJSONObject(i).getInt("EncuestaId");
                id_count = id_count + 1;
                Button btn = new Button(getActivity());
                btn.setId(i+1);
                btn.setText(ja.getJSONObject(i).getString("Nombre"));
                btn.setMaxHeight(120);
                btn.setHeight(80);
                btn.setBackgroundColor(0xFFFFFFFF);
                ViewGroup.LayoutParams s= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                btn.setLayoutParams(s);
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
                myLayout.addView(btn);
            }
            catch (JSONException e) {
            }
        }
        getView().findViewById(R.id.cargar).setVisibility(View.GONE);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.d("****", "*****************************************************");

                //do whatever you want with them...
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getActivity().getAssets().open(filename);
            int size = is.available();
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
