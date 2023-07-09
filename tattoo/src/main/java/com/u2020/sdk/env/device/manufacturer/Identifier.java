package com.u2020.sdk.env.device.manufacturer;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Identifier implements Runnable, Tattoo.O {
    @SuppressLint("StaticFieldLeak")
    private static Identifier identifier;
    private static volatile boolean called;
    private static volatile String _o;//oaid
    private static final Object lock = new Object();
    private final Set<Tattoo.O> o = Collections.synchronizedSet(new HashSet<Tattoo.O>());
    private Context context;

    private Identifier() {
        if (identifier != null) {
            throw new RuntimeException("no more Identifier's entity!");
        }
    }

    private Identifier(Context context) {
        this.context = context.getApplicationContext();
    }

    public static void register(Context context, Tattoo.O o) {
        if (context == null) {
            throw new RuntimeException("Context is null!");
        }
        if (!TextUtils.isEmpty(Identifier._o) || called) {
            o.valid(Identifier._o);
        } else if (identifier != null) {
            add(o);
        } else synchronized (Identifier.class) {
            if (identifier == null) {
                identifier = new Identifier(context.getApplicationContext());
                add(o);
                Thread deviceThread = new Thread(identifier);
                deviceThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {//for save
                    @Override
                    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                        Logger.d("tattoo thread err", e);
                    }
                });
                deviceThread.start();
            } else {
                add(o);
            }
        }
    }

    private static void add(Tattoo.O o) {
        if (o != null) {
            identifier.o.add(o);
        }
    }

    private static void clear() {
        identifier.o.clear();
    }


    private synchronized void setOaid(String oaid) {
        Log.d("tattoo", "setup anonymous id=" + oaid + ", _o=" + _o, null);
        if (!TextUtils.isEmpty(oaid) && !oaid.equals(_o)) {
            _o = oaid;
        }
    }

    private void waitTimeLock() {
        try {
            lock.wait(2000);
        } catch (Exception ignored) {
        }
    }

    private static void notifyTimeLock() {
        synchronized (lock){
            try {
                lock.notify();
            } catch (Exception ignored) {
            }
        }
    }

    private static final Runnable R = new Runnable() {
        @Override
        public void run() {
            notifyTimeLock();
        }
    };

    @Override
    public void run() {
        synchronized (lock) {
            IdSupplierFactory.getIdSupplier(context).get(new Tattoo.O() {
                @Override
                public void valid(String oo) {
                    setOaid(oo);
                    new Handler(Looper.getMainLooper()).post(R);//free the lock quickly
                }
            });
            waitTimeLock();//2s
            called = true;
            valid(_o);
        }
    }


    @Override
    public void valid(String o) {
        for (Tattoo.O obs : identifier.o) {
            obs.valid(o);
        }
        clear();
    }
}
