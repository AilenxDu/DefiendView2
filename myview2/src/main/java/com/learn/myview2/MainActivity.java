package com.learn.myview2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn.myview2.activity.EventBusActivity;
import com.learn.myview2.activity.FlowLayoutActivity;
import com.learn.myview2.activity.LoadingCircleActivity;
import com.learn.myview2.activity.MoveImgActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //自定义ViewGroup实现图片循环切换的效果
    public void movieChangeView(View view){
        Intent intent = new Intent(this,MoveImgActivity.class);
        startActivity(intent);
    }
    //自定义ViewGroup实现流式布局
    public void flowLayout(View view){
        Intent intent = new Intent(this,FlowLayoutActivity.class);
        startActivity(intent);
    }
    //消息传递之EventBus的使用
    public void eventBusDemo(View view){
        Intent intent = new Intent(this,EventBusActivity.class);
        startActivity(intent);
    }

    //正在加载中的旋转圆
    public void onLoadingCircle(View view){
        Intent intent = new Intent(this,LoadingCircleActivity.class);
        startActivity(intent);

    }




}
