package com.uvg.expo.snow.adapters;

/**
 * Created by josejoescobar on 4/25/2017.
 */

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.uvg.expo.snow.volley.LruBitmapCache;


import android.app.Application;
import android.text.TextUtils;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.TrackerConfig;
import org.piwik.sdk.extra.DownloadTracker;
import org.piwik.sdk.extra.TrackHelper;


public class AppController extends android.support.multidex.MultiDexApplication{

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    LruBitmapCache mLruBitmapCache;
    private Tracker mPiwikTracker;
    private static AppController mInstance;

    public TrackerConfig onCreateTrackerConfig() {
        return new TrackerConfig("http://ec2-34-209-62-118.us-west-2.compute.amazonaws.com/piwik", 2, "Experiencia UVG");
    }

    public synchronized Tracker getTracker() {
        if (mPiwikTracker == null) mPiwikTracker = Piwik.getInstance(this).newTracker(onCreateTrackerConfig());
        return mPiwikTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // track app installation
        TrackHelper.track().download().identifier(new DownloadTracker.Extra.ApkChecksum(this)).with(getTracker());
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}