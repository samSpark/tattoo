package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.coolpad.IDeviceIdManager;

import java.security.NoSuchAlgorithmException;

public class CoolPadSupplier implements IdSupplier {
    private final Context context;

    public CoolPadSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "CoolPadSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.coolpad.deviceidsupport", 0);
        } catch (Exception e) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.coolpad.deviceidsupport", "com.coolpad.deviceidsupport.DeviceIdService"));
        new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                IDeviceIdManager iDeviceIdManager = IDeviceIdManager.Stub.asInterface(token);
                String oaid = null;
                if (iDeviceIdManager != null) {
                    oaid = iDeviceIdManager.getOAID(context.getPackageName());
                    Logger.d(getName() + " DeviceIdService supply");
                }
                return new String[]{oaid};
            }
        }).bind(intent);
    }
}
