package com.example.star_jetpack.diy;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    public static final String TAG = "JETPACK_VIEW_MODEL";
    private int count = -1;

    public MyViewModel() {
        count = 0;
        Log.i(TAG, "MyViewModel init");
    }

    public void addCount(){
        Log.i(TAG, "addCount: ");
        count = count + 1;
    }

    public int getCount(){
        Log.i(TAG, "getCount: ");
        return count;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: ");
    }

}
