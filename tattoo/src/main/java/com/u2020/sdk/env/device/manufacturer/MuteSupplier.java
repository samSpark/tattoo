package com.u2020.sdk.env.device.manufacturer;

import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;

/**
 * 当其他设备符获取接口不支持的时候
 * 这里默认返回一个默认的IdSupplier
 */
class MuteSupplier implements IdSupplier {
    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean supply() {
        return false;
    }

    @Override
    public void get(Tattoo.O o) {
        Logger.d("MuteSupplier valid null");
        o.valid(null);
    }
}
