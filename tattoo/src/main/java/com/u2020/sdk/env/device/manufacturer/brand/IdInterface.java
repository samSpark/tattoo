package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;

import java.security.NoSuchAlgorithmException;

/**
 * 远程服务ID获取接口
 */
interface IdInterface {
    String[] asIds(IBinder token) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException, RemoteException;
}
