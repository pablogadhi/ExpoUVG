package com.uvg.expo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


public class MapFragment extends Fragment {

    FloatingActionButton qrfab;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_ui, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        View render = RenderCreation.renderView;
        View surface = getView().findViewById(R.id.surface);
        ViewGroup group = (ViewGroup) surface.getParent();
        int index = group.indexOfChild(surface);
        group.removeView(surface);
        group.addView(render, index);

        searchView = (SearchView) getView().findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        qrfab = (FloatingActionButton) getView().findViewById(R.id.QRFAB);
        qrfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QRReader.class);
                startActivity(intent);
            }
        });



        super.onActivityCreated(savedInstanceState);

    }
}
