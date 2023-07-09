package com.u2020.sdk.env.device.manufacturer.brand;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

//酷赛
public class CooseaSupplier implements IdSupplier {
    private final Context context;
    private final String SysService = "keyguard";
    private KeyguardManager keyguardManager;

    @SuppressLint("WrongConstant")
    public CooseaSupplier(Context context) {
        this.context = context;
        try {
            keyguardManager = (KeyguardManager) context.getSystemService(SysService);
        } catch (Exception ignored) {
            keyguardManager = null;
        }
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "CooseaSupplier";
    }

    @Override
    public boolean supply() {
        boolean supply = false;
        if (keyguardManager != null) {
            try {
                Object obj = keyguardManager.getClass().getDeclaredMethod("isSupported").invoke(keyguardManager);
                supply = obj != null;
            } catch (Exception ignored) {
                Logger.d(getName() + " supply nothing");
            }
        }
        return supply;
    }

    @Override
    public void get(Tattoo.O o) {
        String oaid = null;
        if (keyguardManager != null) {
            try {
                Object obj = keyguardManager.getClass().getDeclaredMethod("obtainOaid").invoke(keyguardManager);
                if (obj != null) {
                    oaid = obj.toString();
                }
            } catch (Exception e) {
                Logger.e(getName() + " OnSupport", e);
            }
        }
        o.valid(oaid);
    }
}
