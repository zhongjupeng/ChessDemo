package com.demo.iryving.util;

import com.iflytek.cloud.ErrorCode;

/**
 * Created by zjp on 2016/8/4.
 */
public class ErrorCodeParser {

    public static String parserErroeCodeResult(int errorCode){
        String str = "" + errorCode;
        switch (errorCode){
            case ErrorCode.ERROR_NO_NETWORK:
                str = "找不到网络哦";
                return str;
            case ErrorCode.ERROR_NETWORK_TIMEOUT:
                str = "网络不给力哦";
                return str;
            case ErrorCode.ERROR_INVALID_RESULT:
                str = "无效的结果";
                return str;
            case ErrorCode.ERROR_NO_MATCH:
                str = "无匹配的结果";
                return str;
            case ErrorCode.ERROR_AUDIO_RECORD:
                str = "录音失败";
                return str;
            case ErrorCode.ERROR_SPEECH_TIMEOUT:
                str = "音频输入超时";
                return str;
            case ErrorCode.ERROR_EMPTY_UTTERANCE:
                str = "无效的文本输入";
                return str;
            case ErrorCode.ERROR_FILE_ACCESS:
                str = "文件读写失败";
                return str;
            case ErrorCode.ERROR_PLAY_MEDIA:
                str = "音频播放失败";
                return str;
            case ErrorCode.ERROR_INVALID_PARAM:
                str = "无效的参数";
                return str;
            case ErrorCode.ERROR_TEXT_OVERFLOW:
                str  = "文本溢出";
                return str;
            case ErrorCode.ERROR_INVALID_DATA:
                str = "无效数据";
                return str;
            case ErrorCode.ERROR_LOGIN:
                str = "用户未登陆";
                return str;
            case ErrorCode.ERROR_PERMISSION_DENIED:
                str = "无效授权";
                return str;
            case ErrorCode.ERROR_INTERRUPT:
                str = "被异常打断";
                return str;
            case ErrorCode.ERROR_UNKNOWN:
                str = "未知错误";
                return str;
            case ErrorCode.ERROR_COMPONENT_NOT_INSTALLED:
                str = "没有安装语音组件";
                return str;
            case ErrorCode.ERROR_ENGINE_NOT_SUPPORTED:
                str = "引擎不支持";
                return str;
            case ErrorCode.ERROR_ENGINE_INIT_FAIL:
                str = "初始化失败";
                return str;
            case ErrorCode.ERROR_ENGINE_CALL_FAIL:
                str = "调用失败";
                return str;
            case ErrorCode.ERROR_ENGINE_BUSY:
                str = "引擎繁忙";
                return str;
            case ErrorCode.ERROR_LOCAL_NO_INIT:
                str = "本地引擎未初始化";
                return str;
            case ErrorCode.ERROR_LOCAL_RESOURCE:
                str = "本地无资源";
                return str;
            case ErrorCode.ERROR_LOCAL_ENGINE:
                str = "本地引擎内部错误";
                return str;
            case ErrorCode.ERROR_IVW_INTERRUPT:
                str = "本地唤醒引擎被异常打断";
                return str;
            case ErrorCode.ERROR_VERSION_LOWER:
                str = "版本过低";
                return str;
            default:
                return str;
        }
    }
}
