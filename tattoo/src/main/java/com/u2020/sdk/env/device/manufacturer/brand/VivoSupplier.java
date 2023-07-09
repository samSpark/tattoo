package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.SystemProperties;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

public class VivoSupplier implements IdSupplier {
    private final Context context;

    public VivoSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "VivoSupplier";
    }

    @Override
    public boolean supply() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
                ("1").equals(SystemProperties.prop("persist.sys.identifierid.supported", "0"))) {
            return true;
        } else {
            Logger.d(getName() + " supply nothing");
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void get(Tattoo.O o) {
        Uri uri = Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID");
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
            cursor.moveToFirst();
            String oaid = cursor.getString(cursor.getColumnIndex("value"));
            Logger.d(getName() + "OnSupport oaid " + oaid);
            o.valid(oaid);
        } catch (Exception e) {
            Logger.e(getName() + " OnSupport", e);
            o.valid(null);
        }
    }
}
