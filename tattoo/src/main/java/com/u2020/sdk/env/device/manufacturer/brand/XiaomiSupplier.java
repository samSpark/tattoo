package com.u2020.sdk.env.device.manufacturer.brand;

import android.annotation.SuppressLint;
import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

import java.lang.reflect.Method;

public class XiaomiSupplier implements IdSupplier {
    private final Context context;
    private Class<?> cls;
    private Object idProviderImpl;

    @SuppressLint("PrivateApi")
    public XiaomiSupplier(Context context) {
        this.context = context;
        try {
            cls = Class.forName("com.android.id.impl.IdProviderImpl");
            idProviderImpl = cls.newInstance();
        } catch (Exception e) {
            Logger.e(e.getMessage());
        }
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "XiaomiSupplier";
    }

    @Override
    public boolean supply() {
        if (idProviderImpl == null) {
            Logger.d(getName() + " supply nothing");
        }
        return idProviderImpl != null;
    }

    @Override
    public void get(Tattoo.O o) {
        String oaid = null;
        try {
            Method getOAID = cls.getMethod("getOAID", Context.class);
            oaid = (String) getOAID.invoke(idProviderImpl, context);
            Logger.d(getName() + "OnSupport oaid " + oaid);
        } catch (Exception e) {
            Logger.e(getName() + " OnSupport", e);
        }
        o.valid(oaid);
    }
}
