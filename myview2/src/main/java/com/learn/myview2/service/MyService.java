package com.learn.myview2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.learn.myview2.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/2/16.
 */
public class MyService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if("wu".equals(intent.getAction())) {
            int sum1 = intent.getIntExtra("add", 0);
            Log.e("Log", "onStartCommand: "+sum1);
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setSum(sum1+34);
            //MyServerThread线程
            messageEvent.setMsg("这是在MyService中的Post的消息");
            EventBus.getDefault().post(messageEvent);
//            EventBus.getDefault().post("这是在MyService中的Post的消息");
//            int sum=sum1+32;
//            EventBus.getDefault().post(sum);
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
