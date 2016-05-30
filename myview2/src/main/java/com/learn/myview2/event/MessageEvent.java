package com.learn.myview2.event;

/**
 * Created by Administrator on 2016/2/9.
 */
public class MessageEvent  {

    private String msg;
    private int age;
    private int sum;

    public MessageEvent() {
    }

    public MessageEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
