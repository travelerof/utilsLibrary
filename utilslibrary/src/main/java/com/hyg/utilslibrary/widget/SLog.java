package com.hyg.utilslibrary.widget;

import android.util.Log;

/**
 *
 * 日志打印类
 *
 */
public class SLog {

    private static boolean isDebug = true;

    public static void setDebug(){
        isDebug = true;
    }

    public static void setRelease(){
        isDebug = false;
    }

    public static int i(String tag,String msg){

        return isDebug ? Log.i(tag,msg) : -1;
    }

    public static int e(String tag,String msg){

        return isDebug ? Log.e(tag,msg) : -1;
    }

    public static int d(String tag,String msg){

        return isDebug ? Log.d(tag, msg) : -1;
    }
}
