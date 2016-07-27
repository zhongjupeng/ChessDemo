package com.demo.iryving.chess;

import android.app.Application;
import android.speech.SpeechRecognizer;

/**
 * Created by zjp on 2016/7/27.
 */
public class CustomApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=57970704");
    }
}
