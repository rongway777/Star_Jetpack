package com.example.star_jetpack.diy;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class VideoPlayer implements LifecycleObserver {


    public static final String TAG = "JETPACK_VIDEO_PLAYER";

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void play(){
        Log.i(TAG, "play: (after onResume())");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pause(){
        Log.i(TAG, "pause: (before onPause())");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void release(){
        Log.i(TAG, "release: (before onDestroy())");
    }

}
