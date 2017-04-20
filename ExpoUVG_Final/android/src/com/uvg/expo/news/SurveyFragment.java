package com.uvg.expo.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidadvance.androidsurvey.SurveyActivity;
import com.uvg.expo.R;

import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class SurveyFragment extends Fragment {

    private static final int SURVEY_REQUEST = 1337;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_survey,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Button button_survey_example_1 = (Button) getView().findViewById(R.id.button_survey_example_1);

        button_survey_example_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i_survey = new Intent(getActivity(), SurveyActivity.class);
                //you have to pass as an extra the json string.
                i_survey.putExtra("json_survey", loadSurveyJson("ejemplo.json"));
                startActivityForResult(i_survey, SURVEY_REQUEST);
            }
        });

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
