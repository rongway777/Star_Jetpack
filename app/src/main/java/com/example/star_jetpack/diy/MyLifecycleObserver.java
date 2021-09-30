package com.example.star_jetpack.diy;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLifecycleObserver implements LifecycleObserver {

    private static String TAG = "JETPACK_MY_OBSERVER";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onActCreate(){
        Log.i(TAG, "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void  onActStart(){
        Log.i(TAG, "onStart: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onActResume(){
        Log.i(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void  onActPause(){
        Log.i(TAG, "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onActStop(){
        Log.i(TAG, "onStop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void  onActDestroy(){
        Log.i(TAG, "onDestroy: ");
    }
}
