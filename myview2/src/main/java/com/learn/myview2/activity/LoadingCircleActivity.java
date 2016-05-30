package com.learn.myview2.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.learn.myview2.R;

public class LoadingCircleActivity extends Activity {
private ImageView iv_loading;
    private static final int E=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_circle);

        iv_loading = (ImageView) findViewById(R.id.iv_loading);


        handler.sendEmptyMessageDelayed(E,5000);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

                    startAnimation();

        }
    };

    public void startAnimation(){
//        iv_loading.getMeasuredWidth();
        Animation animation=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(100000000);
        iv_loading.startAnimation(animation);

    }
}
