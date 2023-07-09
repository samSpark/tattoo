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
import com.u2020.sdk.env.device.manufacturer.brand.fm.CreatorInterface;

import java.security.NoSuchAlgorithmException;

//卓易
public class FreemeSupplier implements IdSupplier {
    private final Context context;

    public FreemeSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "FreemeSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.android.creator", 0);
        } catch (Exception ignored) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        Intent intent = new Intent("android.service.action.msa");
        intent.setPackage("com.android.creator");
        IdSupplierRemoteService service = new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                CreatorInterface creator = CreatorInterface.Stub.asInterface(token);
                String oaid = null;
                if (creator != null) {
                    oaid = creator.getOAID();
                    Logger.d(getName() + " deviceidservice supply");
                }
                return new String[]{oaid};
            }
        });
        service.bind(intent);
    }
}
