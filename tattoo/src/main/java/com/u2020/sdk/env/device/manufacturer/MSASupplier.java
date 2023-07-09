package com.u2020.sdk.env.device.manufacturer;

import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApi10Supplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApi13Supplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApi25Supplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApiOauthSupplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatException;

class MSASupplier implements IdSupplier, IdSupplierFactory.Factory {
    private Context context;
    private IdSupplier idSupplier;

    private MSASupplier() {
    }

    public MSASupplier(Context context) {
        this.context = context;
        idSupplier = create();
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return idSupplier.getName();
    }

    @Override
    public boolean supply() {
        //oaid sdk 1.0.25 & oaid sdk 1.0.23
        //oaid sdk 1.0.13
        //oaid sdk 1.0.10
        return idSupplier.supply();
    }

    @Override
    public void get(Tattoo.O o) {
        try {
            idSupplier.get(o);
        } catch (CompatException e) {//若有适配异常，兜底处理
            Logger.d("CompatException", e);
            o.valid(null);
        }
    }

    @Override
    public IdSupplier create() {
        //优先从高版本到低版本,1026及以上因授权暂不考虑
        IdSupplier supplier = new CompatApiOauthSupplier(context);
        if (supplier.supply()) {
            return supplier;
        }
        if ((supplier = new CompatApi25Supplier(context)).supply()) {
            return supplier;
        }
        if ((supplier = new CompatApi13Supplier(context)).supply()) {
            return supplier;
        }
        if ((supplier = new CompatApi10Supplier(context)).supply()) {
            return supplier;
        }
        return new MuteSupplier();
    }
}
