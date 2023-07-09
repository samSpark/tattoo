package com.u2020.sdk.env.device.manufacturer;

import android.content.Context;

import com.u2020.sdk.env.Tattoo;

/**
 * 设备符提供者
 */
public interface IdSupplier {
    Context getApplicationContext();

    String getName();

    boolean supply();

    void get(Tattoo.O o);


}
