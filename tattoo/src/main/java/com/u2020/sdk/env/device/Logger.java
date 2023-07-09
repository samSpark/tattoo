package com.u2020.sdk.env.device;

import android.util.Log;

import com.u2020.sdk.env.Tattoo;


public final class Logger {

    private final static String TAG = "Tattoo";

    public static void d(String log) {
        if (Tattoo.DEBUG) {
            Log.d(TAG, log + "");
        } 
    }

    public static void d(String log, Throwable t) {
        if (Tattoo.DEBUG) {
            Log.d(TAG, log + "", t);
        }
    }

    public static void e(String log) {
        if (Tattoo.DEBUG) {
            Log.e(TAG, log + "");
        }
    }

    public static void e(String log, Throwable t) {
        if (Tattoo.DEBUG) {
            Log.e(TAG, log + "", t);
        }
    }
}
