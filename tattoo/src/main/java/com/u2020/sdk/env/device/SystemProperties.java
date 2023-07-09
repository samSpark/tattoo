package com.u2020.sdk.env.device;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class SystemProperties {
    public static String getProp(String command) {
        String result = "";
        BufferedReader bufferedReader;
        try {
            Process exec = Runtime.getRuntime().exec("getprop " + command);
            bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()), 1024);
            try {
                result = bufferedReader.readLine();
                exec.destroy();
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        return result;
    }

    public static String sysProp(String key, String defValue) {
        String res = defValue;
        try {
            @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class, String.class);
            res = (String) method.invoke(clazz, new Object[]{key, defValue});
        } catch (Exception e) {
            Log.e("tattoo", "sysProp: " + e);
        }
        return res;
    }

    public static String prop(String key, String defValue) {
        String prop = sysProp(key, defValue);
        if (TextUtils.isEmpty(prop)) {
            prop = getProp(key);
        }
        return prop;
    }
}
