package com.sweet.qianqian.utils;

import android.util.Log;

/**
 * Created by lvliheng on 16/8/4.
 */
public class LogUtils {

    static String TAG = "QianQian";
    static boolean D = true;

    public static void d(String name, String info) {
        if(D) Log.e(TAG, name + " : " + info);
    }

    public static void d(boolean d, String name, String info) {
        if(d) Log.e(TAG, name + " : " + info);
    }

    public static void d(String tag, String name, String info) {
        if(D) Log.e(tag, name + " : " + info);
    }

    public static void d(boolean d, String tag, String name, String info) {
        if(d) Log.e(tag, name + " : " + info);
    }

    public static void e(String name, String info) {
        if(D) Log.e(TAG, name + " : " + info);
    }

    public static void e(boolean d, String name, String info) {
        if(d) Log.e(TAG, name + " : " + info);
    }

    public static void e(String tag, String name, String info) {
        if(D) Log.e(tag, name + " : " + info);
    }

    public static void e(boolean d, String tag, String name, String info) {
        if(d) Log.e(tag, name + " : " + info);
    }

    public static void i(String name, String info) {
        if(D) Log.e(TAG, name + " : " + info);
    }

    public static void i(boolean d, String name, String info) {
        if(d) Log.e(TAG, name + " : " + info);
    }

    public static void i(String tag, String name, String info) {
        if(D) Log.e(tag, name + " : " + info);
    }

    public static void i(boolean d, String tag, String name, String info) {
        if(d) Log.e(tag, name + " : " + info);
    }
}
