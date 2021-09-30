package com.example.star_jetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.star_jetpack.databinding.ActivityMainBinding;

/**
 * 架构：
 * DataBinding *
 *
 *
 * Lifecycles *
 *      一个类，用于存储有关组件（Activity和Fragment）生命周期状态的信息，并且允许其他对象观察。
 *      帮助管理Activity和Fragment的生命周期。
 *      反转：控件去监听act生命周期，而不是在act的生命周期监听当中去 控制控件
 *
 *          LifecycleObserver:观察者。实现接口，并且去添加注解。
 *          LifecycleOwner:持有者。接口。由activity或fragment去实现。
 *          LifecycleRegistry:Lifecycle实现类。
 *
 *      源码：
 *      1.初始化LifecycleRegistry需要一个实现了LifecycleOwner接口(返回Lifecycle对象（Lifecycle的子类，Lifecycle是抽象类）)的实现类
 *      2.同时LifecycleRegistry本身继承了Lifecycle，是子类。act实现了接口，返回本身。
 *
 *      public interface LifecycleOwner{
 *          Lifecycle getLifecycle();
 *      }
 *
 *      public class LifecycleRegistry extends Lifecycle{
 *          public LifecycleRegistry(LifecycleOwner lifecycleOwner){
 *              super(lifecycleOwner,true);
 *          }
 *      }
 *
 *      public class ComponentActivity implement LifecycleOwner{
 *
 *          private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
 *
 *          @override
 *          public Lifecycle getLifecycle(){
 *              return mLifecycleRegistry;
 *          }
 *
 *          @override
 *          protected void onDestroy(){
 *              super.onDestroy();
 *
 *          }
 *
 *      }
 *
 *      官方源码：代码极简到极致
 *      4.自己实现:和源码的意思是一样的，更容易阅读。
 *
 *          //官方的LifecycleRegistry已经继承实现了三个方法(不能拷代码，依赖其他方法)。
 *          1.class MyLifecycle extends Lifecycle{
 *              //实现三个静态方法
 *          }
 *          2.class MyActivity extends Activity implements LifecycleOwner{
 *
 *              private final MyLifecycle mLifecycle = new MyLifecycle();
 *              private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
 *
 *              @override
 *              public Lifecycle getLifecycle(){
 *                  //源码是return mLifecycleRegistry。一样。
 *                  return mLifecycle;
 *              }
 *
 *              //处理观察事件
 *              protected void onCreate(Bundle savedInstanceState){
 *                  super(saveInstanceState);
 *                  mLifecycleRegistry.handle
 *              }
 *
 *          }
 *
 *      但是ComponentActivity实现Owner是在<API26>之后才有的 ==>自己实现一下
 *
 *
 * LiveData *
 *
 *      具有生命周期感知能力。这种感知能力可以确保LiveData仅更新处于活跃生命周期状态的组件观察者。
 *          getValue
 *          setValue-->子类
 *          observer()
 *
 *      数据转换：
 *
 * Navigation
 * Paging
 * Room
 * ViewModel *
 *
 *      完全独立于activity
 *      onSaveInstanceState()恢复少量的数据
 *      activity和fragment可以共享数据，进行通信。
 *      获取viewModel是需要owner的，fragment和activity传参都是activity，所以获取到的是同一个viewModel对象
 *      传入的是同一个owner，获取到的就是同一个ViewModel
 *      ViewModel不能new，只能通过Provider获取。
 *      ViewModel不要传入任何的context对象，防止内存泄露。
 *
 *
 * dataBinding:
 *      1.dataBinding enabled = true;
 *      2.xml
 *          <layout>
 *              <data>
 *                  <import  />
 *
 *                  <variable
 *                      name = "viewModel"
 *                      type = ""/>
 *
 *              </data>
 *
 *              <LinerLayout>
 *
 *                  <TextView
 *                      text = "@{viewModel.xxx}"
 *                      onClick = "@{()->viewModel.xxx}"
 *                  >
 *              </LinerLayout>
 *          </layout>
 *
 *      3.code
 *          取代setContentView()
 *
 * WorkManager
 *
 * Flow........ktlin...
 *
 * UI：
 * TV / wear...
 * Animation....
 */
public class MainActivity extends AppCompatActivity {

    private static String TAG = "JETPACK_MAIN_ACTIVITY";
    private MyViewModel viewModel;
    //xml当中的data标签
    private ActivityMainBinding mViewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        //setContentView(R.layout.activity_main);

        //初始化DataBinding
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewDataBinding.setViewModel(viewModel);

        //给activity添加观察
        getLifecycle().addObserver(new MyLifecycleObServer());

        //获取ViewModel
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        //给LiveData添加观察
        viewModel.getLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                ((TextView)findViewById(R.id.textViewMain)).setText(integer + "");
            }
        });

    }

    public void onClick(View view) {
        if(view.getId() == R.id.buttonAdd){
            viewModel.addCountInMain();
        }else if(view.getId() == R.id.buttonSub){
            viewModel.subCountInSubThread();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: state is " + getLifecycle().getCurrentState());
    }


}