package com.learn.myview2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.learn.myview2.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/6.
 */
public class FlowLayout extends ViewGroup {
    private List<List<View>> viewList=new ArrayList<List<View>>();
    private List<Integer> lineHeightList=new ArrayList<Integer>();


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        //FlowLayout的宽度和高度
        int width=0;
        int height=0;
        //记录一行的宽度和高度
        int lineWidth=0;
        int lineHeight=0;

        int childCount = getChildCount();
        if(childCount==0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            //得到子View
            View child = getChildAt(i);

            //测量子View
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //获得子View的宽
            MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            //获得子View的高
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            Log.e("Log", "childWidth: "+childWidth+" childHeight"+childHeight);

            //如果宽度超出了测量的一行宽度:需要换行，总高：高度叠加 总宽：记录的宽度和当前的行宽，取大值
            if(lineWidth+childWidth>widthSize - getPaddingLeft() - getPaddingRight()) {
                width=Math.max(width,lineWidth);
                lineHeight=childHeight;
                height +=lineHeight;
            }else{//不用换行的情况，在一行内
                lineWidth += childWidth;
                lineHeight=Math.max(lineHeight,childHeight);
//                height=lineHeight;
            }
            // 最后一个控件
            if(i==childCount-1){
                width=Math.max(width,lineWidth);
                height=Math.max(height,lineHeight);
            }

        }
        Log.e("Log", "widthSize: "+widthSize+" heightSize:"+heightSize+" width:"+width+" height:"+height);

        setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:width,heightMode==MeasureSpec.EXACTLY?heightSize:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //List<List<View>> viewList：全部View的集合：其中，外集合每一条数据代表一行的View集合，内集合的一条数据代表一个子View
        //List<Integer> lineHeightList：每一行的行高集合，每一行对应viewList的每一行
        viewList.clear();
        lineHeightList.clear();
        //行与行的间隔
        int linepadding = DensityUtil.dip2px(getContext(), 8);

        int childCount = getChildCount();
        int lineWidth=0;
        int lineHeight=0;
        int width=getWidth();
        int height=getHeight();
        if(childCount==0) {
            return;
        }
        List<View> lineviewList=new ArrayList<View>();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
            //子View的宽高：
            int childWidth=child.getMeasuredWidth();
            int childHeight=child.getMeasuredHeight();

            //如果宽度超出了测量的一行宽度:需要换行，总高：高度叠加 总宽：记录的宽度和当前的行宽，取大值
            if(lineWidth+childWidth+lp.leftMargin+lp.rightMargin>width - getPaddingLeft() - getPaddingRight()) {
                //记录当前行的高度
                lineHeightList.add(lineHeight+linepadding);
                //集合记录当前行的view
                viewList.add(lineviewList);
                //下一行的
                //重置行宽：lineWidth和行高：lineHeight和lineviewList
                lineWidth=0;
                lineHeight=childHeight+lp.topMargin+lp.bottomMargin;
                lineviewList=new ArrayList<View>();
            }
                lineWidth += childWidth+lp.leftMargin+lp.rightMargin;
                lineHeight=Math.max(lineHeight,childHeight+lp.topMargin+lp.bottomMargin);
                lineviewList.add(child);
        }
        //A.处理最后一个子View:上面如是换行，还没有记录换行后最后一个子View,如是没换行，
        // B.viewList和lineHeightList也没有记录数据
        viewList.add(lineviewList);
        lineHeightList.add(lineHeight+linepadding);


        //对子View进行布局
        //初始值：
        int left=getPaddingLeft();
        int top=getPaddingTop();

        for (int i = 0; i < viewList.size(); i++) {
            List<View> lineviews = viewList.get(i);
            lineHeight=lineHeightList.get(i);
            for (int j = 0; j < lineviews.size(); j++) {
                View view = lineviews.get(j);
                MarginLayoutParams lp = (MarginLayoutParams)view.getLayoutParams();
                if(view.getVisibility()==GONE){
                    continue;
                }
                int leftc=left+lp.leftMargin;
                int topc=top+lp.topMargin;
                int rightc=leftc+view.getMeasuredWidth();
                int bottomc=topc+view.getMeasuredHeight();

                //对子View进行布局
                view.layout(leftc,topc,rightc,bottomc);

                left += view.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            }
            //换行后重置
            left=getPaddingLeft();
            top += lineHeight;
        }



    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
