package com.example.qqlist.base;


import org.litepal.LitePalApplication;



//import cn.jpush.android.api.JPushInterface;




public class MyApplication extends LitePalApplication {

    public static final String TAG = "-----------";
    public static final String TAG2 = "++++++++++";
    public static final String TAG_finger = "finger-----------";
    public static String imgPath;//拍照的img路径
    private static MyApplication singleton;

    public static String curUser;

    //单例模式获取MyApplication
    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

    }



}
