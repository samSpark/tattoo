package com.u2020.sdk.env.jni;


import androidx.annotation.Keep;

import com.u2020.sdk.env.TattooService;

@Keep
public class EnvUtils {

    static {
        System.loadLibrary("tattoo");
    }

    /**
     * 如果是x86内核的模拟器，可能会报错，所以需要别开服务处理以免影响主进程{@link TattooService}
     */
    @Keep
    public static native boolean detect();

    @Keep
    public static native String properties(String key);


}
