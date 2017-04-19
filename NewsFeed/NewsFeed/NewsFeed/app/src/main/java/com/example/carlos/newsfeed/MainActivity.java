package com.example.carlos.newsfeed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.ExpandablePlaceHolderView;

public class MainActivity extends Fragment {

    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        mExpandableView = (ExpandablePlaceHolderView)getView().findViewById(R.id.expandableView);
        for(Feed feed : Utils.loadFeeds(getActivity().getApplicationContext())){
            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
            for(Info info : feed.getInfoList()){
                mExpandableView.addView(new InfoView(mContext, info));
            }
        }
        super.onActivityCreated(savedInstanceState);
    }
}
