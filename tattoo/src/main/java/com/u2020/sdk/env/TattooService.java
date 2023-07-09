package com.u2020.sdk.env;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.u2020.sdk.env.internal.IACheck;
import com.u2020.sdk.env.jni.EnvUtils;

@Keep
public class TattooService extends Service {

    Handler mHandler = new Handler();

    @Keep
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IACheck.Stub() {

            @Override
            public boolean a() throws RemoteException {
                return EnvUtils.detect();
            }

            @Override
            public void kill() throws RemoteException {
                stopSelf();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                }, 500);
            }
        };
    }

    @Keep
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Keep
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}
