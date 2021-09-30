package com.example.star_jetpack;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


public class MyLifecycleObServer implements LifecycleObserver {

    private static String TAG = "JETPACK_MAIN_OBSERVER";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void onCreate(){
        Log.i(TAG, "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void  onStart(){
        Log.i(TAG, "onStart: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume(){
        Log.i(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void  onPause(){
        Log.i(TAG, "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onStop(){
        Log.i(TAG, "onStop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void  onDestroy(){
        Log.i(TAG, "onDestroy: ");
    }

}
