package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.zui.IDeviceIdInterface;

import java.security.NoSuchAlgorithmException;

public class LenovoSupplier implements IdSupplier {
    private final Context context;

    public LenovoSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "LenovoSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.zui.deviceidservice", 0);
        } catch (Exception ignored) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                IDeviceIdInterface idInterface = IDeviceIdInterface.Stub.asInterface(token);
                String oaid = null;
                if (idInterface != null && idInterface.isSupport()) {
                    oaid = idInterface.getOAID();
                    Logger.d(getName() + " deviceidservice supply");
                }
                return new String[]{oaid};
            }
        }).bind(intent);
    }
}
