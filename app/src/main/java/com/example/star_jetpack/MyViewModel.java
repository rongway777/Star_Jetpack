package com.example.star_jetpack;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel:
 * LiveData:
 *      当数据源发生改变的时候，通知观察者更新UI
 *      如果观察者处于PAUSED或者destroy状态，不会收到通知，不用担心内存泄露（刷新ui,但是activity或者fragment是pause或者销毁）
 *      随时监听数据的变化，数据变化既产生相应回调。
 *      保证发生在LifecycleOwner的可利用空间内，避免内存泄露。
 *      保证主线程（UI线程当中执行回调）而设置  数据改变的时候不限定线程。
 *
 *      1.getLiveData()
 *      2.observer();
 *      3.Transformations.map/switchMap ?
 *      4.MediatorLiveData ?
 *
 *
 *      好奇：
 *      如果act是paused或者stopped状态，刷新UI会发生什么。通过eventbus。
 */
public class MyViewModel extends ViewModel {

    public static final String TAG = "JETPACK_MAIN_VIEWMODEL";

    private final MutableLiveData<Integer> mIntLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<String> mStringLiveData = new MutableLiveData<>("who");

    public MyViewModel() {
        Log.i(TAG, "MyViewModel constructor: ");
    }


    public LiveData<Integer> getLiveData() {
        return mIntLiveData;
    }

    //主线程用set
    public void addCountInMain(){
        mIntLiveData.setValue(mIntLiveData.getValue().intValue() + 1);
    }

    //子线程用post,但是通知UI更新是在主线程
    public void subCountInSubThread(){
        new Thread(){
            @Override
            public void run() {
                mIntLiveData.postValue(mIntLiveData.getValue().intValue() -1);
            }
        }.start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: ");
    }
}
