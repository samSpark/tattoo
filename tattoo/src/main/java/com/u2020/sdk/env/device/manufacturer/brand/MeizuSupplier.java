package com.u2020.sdk.env.device.manufacturer.brand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

public class MeizuSupplier implements IdSupplier {
    private final Context context;

    public MeizuSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "MeizuSupplier";
    }

    @Override
    public boolean supply() {
        ProviderInfo pi = null;
        try {
            pi = context.getPackageManager().resolveContentProvider("com.meizu.flyme.openidsdk", 0);
        } catch (Exception ignored) {
            Logger.d(getName() + " supply nothing");
        }
        return pi != null;
    }

    @Override
    public void get(Tattoo.O o) {
        try {
            Uri uri = Uri.parse("content://com.meizu.flyme.openidsdk/");
            Cursor cursor = context.getContentResolver().query(uri, null, null, new String[]{"oaid"}, null);
            cursor.moveToFirst();
            @SuppressLint("Range")
            String oaid = cursor.getString(cursor.getColumnIndex("value"));
            Logger.d(getName() + "OnSupport oaid " + oaid);
            o.valid(oaid);
        } catch (Exception e) {
            Logger.e(getName() + " OnSupport", e);
            o.valid(null);
        }
    }
}
