package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.hms.AdIdClient;

import java.security.NoSuchAlgorithmException;

public class HuaweiSupplier implements IdSupplier {
    private final Context context;
    private String service;

    public HuaweiSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "HuaweiSupplier";
    }

    @Override
    public boolean supply() {
        boolean ret = false;
        try {
            PackageManager pm = context.getPackageManager();
            if (pm.getPackageInfo("com.huawei.hwid", 0) != null) {
                service = "com.huawei.hwid";
                ret = true;
            } else if (pm.getPackageInfo("com.huawei.hwid.tv", 0) != null) {
                service = "com.huawei.hwid.tv";
                ret = true;
            } else {
                service = "com.huawei.hms";
                ret = pm.getPackageInfo(service, 0) != null;
            }
        } catch (Exception e) {
            Logger.d(getName() + " supply nothing");
        }
        return ret;
    }

    @Override
    public void get(final Tattoo.O o) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                String oaid = Settings.Global.getString(context.getContentResolver(), "pps_oaid");
                if (!TextUtils.isEmpty(oaid)) {
                    o.valid(oaid);
                    Logger.d("pps_oaid supply");
                    return;
                }
            } catch (Throwable t) {
                Logger.e("pps_oaid supply nothing", t);
            }
        }
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage(service);
        new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                AdIdClient client = AdIdClient.Stub.asInterface(token);
                String oaid = null;
                if (client.isOaidTrackLimited()) {
                    oaid = "00000000-0000-0000-0000-000000000000";
                } else {
                    oaid = client.getOaid();
                    Logger.d("hms openids servcie supply");
                }
                return new String[]{oaid};
            }
        }).bind(intent);
    }
}
