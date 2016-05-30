package com.learn.myview2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.learn.myview2.R;

/**
 * Created by Administrator on 2015/12/18.
 */
public class MyViewPager extends ViewGroup {
    private int imgwidth;
    private int imgheight;
    private int countwidth;
    private int leftWidth;

    private int currentPage;
    private Scroller scroller;
    /**
     * 图片资源
     */
    private int[] ids = { R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6 };



    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addimgView(context);
        scroller=new Scroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec,heightMeasureSpec);
        }
       /* ImageView imgview = (ImageView) getChildAt(0);
        measureChild(imgview,widthMeasureSpec,heightMeasureSpec);
        imgwidth = imgview.getMeasuredWidth();
        imgheight=imgview.getMeasuredHeight();
        countwidth += imgwidth;
*/
    }
    //对子View进行定位布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //让图片充满屏幕剩余空间
        imgwidth = getWidth();
        imgheight = getHeight();

        //获得所有子对象
        int count = getChildCount();
        countwidth = imgwidth*(count-1);
        for (int i = 0; i < count; i++) {
           getChildAt(i).layout(i*imgwidth,0,(i+1)*imgwidth,imgheight);

        }

    }

    int lastX,startX;
    int startScrollX;
    /**
     * 手指滑动图片，切换图片
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);
        int eventX= (int) event.getRawX();

         switch (event.getAction()){
                     case MotionEvent.ACTION_DOWN:
                         startScrollX= getScrollX();
                        lastX=eventX;
                         startX=eventX;
                         break;
                     case MotionEvent.ACTION_MOVE:
                        int dx=eventX-lastX;
                         int scroolx=eventX-startX;

                         int scrollToX=getScrollX()-dx;
                         if(scrollToX<-10){
                             scrollToX=0;
                         }
                         if(scrollToX>countwidth+20){
                             scrollToX=countwidth;
                         }
                         scrollTo(scrollToX,0);

                         lastX=eventX;
                         break;
                     case MotionEvent.ACTION_UP://离开，判断位置
                         int distance=Math.abs(eventX-startX);
//                         Log.e("Log", "distance:"+distance+"startX:"+startX+"imgwidth/2:"+imgwidth/2);
//                         Log.e("Log", "getScrollX: "+getScrollX()+"startScrollX:"+startScrollX);
                         //判断
                         //1.如果背景向右移动的距离超过屏幕的一半，则到下一页
                         if(distance>imgwidth/2 && getScrollX()>startScrollX){
                             Log.e("Log", "下一页: ");
                             toNextPage(currentPage+1);
                         }else if(distance>imgwidth/2 && getScrollX()<startScrollX){
                             Log.e("Log", "上一页: ");
                             toNextPage(currentPage-1);
                         }else{
                             Log.e("Log", "原点: ");
                             scrollTo(startScrollX,0);
                         }
                         int getscrollX = getScrollX();

                         break;

                 }

        return true;
    }
    //到下一页的方法
    public void toNextPage(int toIndex){
        //限制Position : [0, childCount-1]
        if (toIndex < 0) {
            toIndex=0;
        }else if(toIndex>getChildCount()-1){
            toIndex=getChildCount()-1;
        }
        //更新下标
        currentPage=toIndex;
        //调用监听器方法：给Activity传递信息
       if(onPageChangeListener!=null) {
           onPageChangeListener.onpageChange(currentPage);
       }
        int dx=toIndex*imgwidth;
        scroller.startScroll(getScrollX(),0,dx-getScrollX(),0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
//            Log.e("Log", "新的移动距离: "+scroller.getCurrX());
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    private OnPageChangeListener onPageChangeListener;

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public interface OnPageChangeListener{
        public int onpageChange(int index);
    }

    private void addimgView(Context context) {
        //给RadioGroup添加子view
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(ids[i]);
            addView(imageView);
        }
    }


}
