package com.sweet.qianqian.utils;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by lvliheng on 16/11/24.
 */
public class MyApplication extends Application {

    //LEAN CLOUD
    

    private String LEAN_APP_ID = "UAG63CXoxjt7UPCEgH0pHWK9-gzGzoHsz";
    private String LEAN_APP_KEY = "J3lgOrverE9HDoSM2ymHOla0";
    private String LEAN_MASTER_KEY = "LffgV05krkIFkMCntBl3wIRs";



    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, LEAN_APP_ID, LEAN_APP_KEY);
    }
}
