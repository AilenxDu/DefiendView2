package com.learn.myview2.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learn.myview2.R;
import com.learn.myview2.utils.DensityUtil;
import com.learn.myview2.view.FlowLayout;

public class FlowLayoutActivity extends Activity {
    private FlowLayout flay_groupview;
    String [] texts=new String[]{
            "Java","Android","Goodmoring","haoare you","is",
            "Java","AndroidAnd","Goodmoring","haoare you","is",
            "Jav","Android","Goodmoring","haoare you Fine","is",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        flay_groupview = (FlowLayout) findViewById(R.id.flay_groupview);
        initview();
    }

    private void initview() {
        TextView textView;
        int pading = DensityUtil.dip2px(this, 5);
        for (int i = 0; i < texts.length; i++) {
            textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.btn_item);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            marginLayoutParams.leftMargin=DensityUtil.dip2px(this,8);
            marginLayoutParams.rightMargin=DensityUtil.dip2px(this,8);
            marginLayoutParams.topMargin=DensityUtil.dip2px(this,6);
            marginLayoutParams.bottomMargin=DensityUtil.dip2px(this,6);
//            textView.setPadding(pading,pading,pading,pading);
            textView.setLayoutParams(marginLayoutParams);
            textView.setTextColor(Color.GREEN);
            textView.setTextSize(23);
            textView.setText(texts[i]);

            flay_groupview.addView(textView);
        }
    }


}
