package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

public class NubiyaSupplier implements IdSupplier {
    private final Context context;

    public NubiyaSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "NubiyaSupplier";
    }

    @Override
    public boolean supply() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    @Override
    public void get(Tattoo.O o) {
        String oaid = null;
        try {
            Uri uri = Uri.parse("content://cn.nubia.identity/identity");
            ContentProviderClient client = context.getContentResolver().acquireContentProviderClient(uri);
            if (client == null) {
                o.valid(null);
                return;
            }
            Bundle bundle = client.call("getOAID", null, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                client.close();
            } else {
                client.release();
            }
            if (bundle == null) {
                o.valid(null);
                return;
            }
            if (bundle.getInt("code", -1) == 0) {
                oaid = bundle.getString("id");
            }
            if (oaid == null || oaid.length() == 0) {
                o.valid(null);
                return;
            }
            Logger.d(getName() + " identity supply");
            o.valid(oaid);
        } catch (Exception e) {
            Logger.d(getName() + " supply nothing");
        }
    }
}
