package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.msakl.MsaIdInterface;

import java.security.NoSuchAlgorithmException;

//获取设备上的 MSA 远程服务
public class CompatMsaKlSupplier implements IdSupplier {
    private final Context context;

    public CompatMsaKlSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "CompatMsaSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.mdid.msa", 0);
        } catch (Exception e) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        //try to start msaKl service
        try {
            Intent intent = new Intent("com.bun.msa.action.start.service");
            intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaKlService");
            intent.putExtra("com.bun.msa.param.pkgname", context.getPackageName());
            if (Build.VERSION.SDK_INT < 26) {
                context.startService(intent);
            } else {
                context.startForegroundService(intent);
            }
        } catch (Exception e) {
            Logger.e(getName(), e);
        }
        //try to get msa id service
        Intent intent = new Intent("com.bun.msa.action.bindto.service");
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.putExtra("com.bun.msa.param.pkgname", context.getPackageName());
        IdSupplierRemoteService service = new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                MsaIdInterface msaIdInterface = MsaIdInterface.Stub.asInterface(token);
                String oaid = null;
                if (msaIdInterface != null) {
                    oaid = msaIdInterface.getOAID();
                }
                return new String[]{oaid};
            }
        });
        service.bind(intent);
    }
}
