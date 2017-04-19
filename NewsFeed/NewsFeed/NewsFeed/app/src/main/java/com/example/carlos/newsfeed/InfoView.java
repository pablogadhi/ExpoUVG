package com.example.carlos.newsfeed;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

/**
 * Created by Carlos on 6/04/2017.
 */
@Layout(R.layout.feed_item)
public class InfoView {
    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.captionTxt)
    private TextView captionTxt;


    @View(R.id.imageView)
    private ImageView imageView;

    private Info mInfo;
    private Context mContext;

    public InfoView(Context context, Info info) {
        mContext = context;
        mInfo = info;
    }

    @Resolve
    private void onResolved() {
        titleTxt.setText(mInfo.getTitle());
        captionTxt.setText(mInfo.getCaption());
        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
    }



}
