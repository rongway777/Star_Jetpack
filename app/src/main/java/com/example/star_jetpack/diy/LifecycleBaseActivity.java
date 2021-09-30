package com.example.star_jetpack.diy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * 注意：
 * [*] 1.handle：event_start,如果create写了（注解）但没有被handle，那么event_start的时候先handle_create.
 * 2.handle_event_xxxx之后，会修改STATE.也可以自己覆盖！
 * [*] 3.当调用handleEvent的时候，如果event是pause stop destroy，那么在调用到handle之后，直接执行observer。在回来继续执行onPause onStop onDestroy
 *    如果event是create start resume，那么只有当onCreate onStart onResume 执行完成以后，才会执行observer
 *    意思就是pause stop destroy handle到立马处理 而create start resume 执行完act的方法才会执行
 *
 * [*]4.handleEvent或者setState()都会去通知observer。但是具体是怎么个调的，还不清楚。
 *    Activity Lifecycle : onCreate onStart onResume 是事前调 onPause onStop onDestroy是事后调
 *
 *
 *    state场景：当APP进入后台，通过lifecycle的state可以判断具体的状态。
 */
public class LifecycleBaseActivity extends Activity implements LifecycleOwner {

    private static final String TAG = "JETPACK_BaseAct";

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        //调用完成之后，会修改Lifecycle的状态、
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        super.onStart();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

        //随便设置什么都行，但是没必要
        //mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        //mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
