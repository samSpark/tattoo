package com.u2020.sdk.env.device.manufacturer.brand;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.IBinder;
import android.os.RemoteException;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.heytap.OpenID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OppoSupplier implements IdSupplier {
    private final Context context;

    public OppoSupplier(Context context) {
        this.context = context.getApplicationContext(); //recall from foreground context
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "OppoSupplier";
    }

    @Override
    public boolean supply() {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo("com.heytap.openid", 0);
        } catch (Exception ignored) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @SuppressLint("PackageManagerGetSignatures")
    @Override
    public void get(Tattoo.O o) {
        Intent intent = new Intent("action.com.heytap.openid.OPEN_ID_SERVICE");
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        new IdSupplierRemoteService(context, o, new IdInterface() {
            @Override
            public String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException {
                String pkgName = context.getPackageName();
                Signature[] signatures = context.getPackageManager().getPackageInfo(pkgName,
                        PackageManager.GET_SIGNATURES).signatures;
                byte[] byteArray = signatures[0].toByteArray();
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                byte[] digest = messageDigest.digest(byteArray);
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                }
                String sign = sb.toString();
                OpenID openID = OpenID.Stub.asInterface(token);
                String oaid = null;
                if (openID != null) {
                    oaid = openID.getSerID(pkgName, sign, "OUID");
                    Logger.d(getName() + " IdentifyService supply");
                }
                return new String[]{oaid};
            }
        }).bind(intent);
    }
}
