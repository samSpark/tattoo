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
import com.u2020.sdk.env.device.manufacturer.brand.asus.IDidAidlInterface;

import java.security.NoSuchAlgorithmException;

public class AsusSupplier implements IdSupplier {
    private final Context context;

    public AsusSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "AsusSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.asus.msa.SupplementaryDID", 0);
        } catch (Exception e) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        Intent intent = new Intent("com.asus.msa.action.ACCESS_DID");
        ComponentName componentName = new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService");
        intent.setComponent(componentName);
        new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                IDidAidlInterface iDidAidlInterface = IDidAidlInterface.Stub.asInterface(token);
                String oaid = null;
                if (iDidAidlInterface != null && iDidAidlInterface.isSupport()) {
                    oaid = iDidAidlInterface.getOAID();
                }
                return new String[]{oaid};
            }
        }).bind(intent);
    }
}
