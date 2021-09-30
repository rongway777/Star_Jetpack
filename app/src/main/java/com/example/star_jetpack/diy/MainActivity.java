package com.example.star_jetpack.diy;

import android.os.Bundle;
import com.example.star_jetpack.R;

public class MainActivity extends LifecycleBaseActivity{

    private static final String TAG = "JETPACK_MainActivity";

    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_main);
        getLifecycle().addObserver(new VideoPlayer());
        getLifecycle().addObserver(new MyLifecycleObserver());
    }
}
