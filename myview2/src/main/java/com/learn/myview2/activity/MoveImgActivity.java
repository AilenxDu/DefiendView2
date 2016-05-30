package com.learn.myview2.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.learn.myview2.R;
import com.learn.myview2.view.MyViewPager;

public class MoveImgActivity extends Activity {
    private MyViewPager myview;
    private RadioGroup rg_move_checkview;
    private int checkedIndex;

    /**
     * 图片资源
     */
    private int[] ids = { R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6 };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Log", "MainActivity:onCreate()方法调用");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_img);

        myview = (MyViewPager) findViewById(R.id.myvp_move_viewpager);
        rg_move_checkview= (RadioGroup) findViewById(R.id.rg_move_checkview);
        rg_move_checkview.setOnCheckedChangeListener(onCheckedChangeListener);

        //给RadioGroup添加子view
        for (int i = 0; i < ids.length; i++) {

            //添加多个RadioButton做为指示器
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            if(i==0){
                radioButton.setChecked(true);
            }
//            Log.e("Log", "MainActivity:添加View ");
            rg_move_checkview.addView(radioButton);
        }
        //添加一个测试页和单选框
        View testView = View.inflate(this, R.layout.test, null);
        myview.addView(testView);
        RadioButton radioButton = new RadioButton(this);
        radioButton.setId(ids.length);
        rg_move_checkview.addView(radioButton);


        myview.setOnPageChangeListener(onPageChange);


    }

    private MyViewPager.OnPageChangeListener onPageChange=new MyViewPager.OnPageChangeListener() {
        @Override
        public int onpageChange(int index) {
            RadioButton btn = (RadioButton) rg_move_checkview.getChildAt(index);
            btn.setChecked(true);
            return checkedIndex;
        }
    };

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            checkedIndex=checkedId;
            myview.toNextPage(checkedId);
        }
    };


}
