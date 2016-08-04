package com.demo.iryving.chess;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.demo.iryving.util.CrashHandler;

/**
 * Created by zjp on 2016/7/27.
 */
public class CustomApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=57970704");
    }
}
