package com.learn.myview2.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.learn.myview2.R;
import com.learn.myview2.event.MessageEvent;
import com.learn.myview2.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*
第一步：Define events:
public class MessageEvent {  Additional fields if needed }

        Prepare subscribers
        Register your subscriber (in your onCreate or in a constructor):
        eventBus.register(this);
第二步：Prepare subscribers：注册一个在后台线程执行的方法,用于接收事件
        Declare your subscribing method:
@Subscribe
public void onEvent(AnyEventType event) {Do something };

 第三步： Post events: 发送事件
        eventBus.post(event);
Subscriber以onEvent开头的4个函数区别：
onEvent(MessageEvent event)：事件的处理在和事件的发送在相同的线程，所以事件处理时间不应太长，不然影响事件的发送线程。
onEventMainThread(MessageEvent event): 事件的处理会在UI线程中执行。事件处理时间不能太长，长了会出现臭名远之的ANR。
onEventBackgroundThread(MessageEvent event)：事件的处理会在一个后台线程中执行。虽然名字是BackgroundThread，事件处理是在后台线程，但事件处理时间还是不应该太长，因为如果发送事件的线程是后台线程，会直接在当前后台线程执行事件；如果当前线程是UI线程，事件会被加到一个队列中，由一个线程依次处理这些事件，如果某个事件处理时间太长，会阻塞后面的事件的派发或处理。
onEventAsync：事件处理会在单独的线程中执行，主要用于在后台线程中执行耗时操作，每个事件会开启一个线程，但最好限制线程的数目。
 */
public class EventBusActivity extends Activity {
private EditText et_main_main;
private EditText et_child_main;
private EditText et_main_maintype;
private EditText et_main_service;
private EditText et_main_receiver;
    private EventBus eventBus;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        //第1步: 注册
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        initview();
    }

    private void initview() {
        et_main_main = (EditText) findViewById(R.id.et_main_main);
        et_child_main = (EditText) findViewById(R.id.et_child_main);
        et_main_maintype = (EditText) findViewById(R.id.et_main_maintype);
        et_main_service = (EditText) findViewById(R.id.et_main_service);
        et_main_receiver = (EditText) findViewById(R.id.et_main_receiver);
    }

    //第2步:注册一个方法,用于接收事件处理：
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onevent(String event){
//        et_main_maintype.setText(event);
        et_main_service.setText(event);
    }

    //事件2接收者：在主线程接收自定义MsgBean消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onevent(MessageEvent event){

//        et_main_main.setText(event.getMsg());
        String msg = event.getMsg();
        int sum = event.getSum();
        et_main_service.setText(msg+sum);

    }

    //事件3接收者：在主线程接收
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(int event) {
        et_main_maintype.setText(event+"");
        String s = et_main_service.getText().toString();
        et_main_service.setText(s+event);

    }

    //默认方式, 在发送线程执行

//    public void onEventInPostThread(MessageEvent event){
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventInPostThread(MessageEvent event){
        int x=event.getAge();
//        et_child_main.setText(event+"");
        Log.e("Log", " float x: "+x);
    }

    //在后台线程执行
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onUserEvent(MessageEvent event) {
        Log.e("Log", " BACKGROUND x: "+event.getAge());
//        et_main_maintype.setText(event.getAge()+"");
    }


    //主线程发主线程收
    public void mainSendOfmainSave(View v){
        eventBus.post(new MessageEvent("主线程发送123"));
    }

    //子线程发主线程接收

    public void childSendOfmainSave(View v){
        new Thread(){
            @Override
            public void run() {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setAge(263);
                EventBus.getDefault().post(messageEvent);
            }


        }.start();

    }

    //主线程发主线程收TYPE消息
    public void mainSendOfmainSaveType(View v){
        et_main_maintype.setText("");
//        MessageEvent messageEvent = new MessageEvent();
//        messageEvent.setAge(26);
        eventBus.post("26岁了！");
    }


    //服务中发消息主线程收消息
    public void serviceSendOfmainSave(View v){
        intent = new Intent(this, MyService.class);
        intent.setAction("wu");
        intent.putExtra("add",8);
        startService(intent);
    }


    //广播接收器中发消息主线程收消息
    public void receiverSendOfmainSave(View v){
        et_main_maintype.setText("");
        eventBus.post("26岁了！");
    }




    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if(intent!=null) {
            stopService(intent);
            intent=null;
        }

        super.onDestroy();
    }
}
