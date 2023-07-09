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
import com.u2020.sdk.env.device.manufacturer.brand.samsung.IDeviceIdService;

import java.security.NoSuchAlgorithmException;

public class SamsungSupplier implements IdSupplier {
    private final Context context;

    public SamsungSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "SamsungSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.samsung.android.deviceidservice", 0);
        } catch (Exception ignored) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                IDeviceIdService anInterface = IDeviceIdService.Stub.asInterface(token);
                String oaid = null;
                if (anInterface != null) {
                    oaid = anInterface.getOAID();
                    Logger.d(getName() + " deviceidservice supply");
                }
                return new String[]{oaid};
            }
        }).bind(intent);
    }
}
