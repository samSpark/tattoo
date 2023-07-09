package com.u2020.sdk.env.device.manufacturer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;


class IdSupplierFactory {

    @NonNull
    @WorkerThread
    public static IdSupplier getIdSupplier(Context context) {//按顺序依次适配
        if (Tattoo.sva(context)) {//模拟器不支持
            return new MuteSupplier();
        }
        IdSupplier idSupplier = new ManufacturerSupplier(context);
        if (idSupplier.supply()) {//本地厂商接口支持
            Logger.d(idSupplier.getName() + " supply");
            return idSupplier;
        }
        if ((idSupplier = new MSASupplier(context)).supply()) {//MSA接口支持
            Logger.d(idSupplier.getName() + " supply");
            return idSupplier;
        }
        return new MuteSupplier();//其他情况
    }

    interface Factory {
        IdSupplier create();
    }
}
