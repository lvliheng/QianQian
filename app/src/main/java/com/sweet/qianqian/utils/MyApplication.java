package com.sweet.qianqian.utils;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by lvliheng on 16/11/24.
 */
public class MyApplication extends Application {

    //LEAN CLOUD
    private String LEAN_APP_ID = "";
    private String LEAN_APP_KEY = "";
    private String LEAN_MASTER_KEY = "";



    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, LEAN_APP_ID, LEAN_APP_KEY);
    }
}
