package com.u2020.sdk.env.device;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.u2020.sdk.env.jni.EnvUtils;

import java.io.File;


/**
 * java与C ，代理与服务都能被hook，目前还没找不能被篡改的方法，只能将篡改的成本提高
 */


public class AndroidDeviceUtil {

    public static String getModel() {
        return EnvUtils.properties("ro.product.model");
    }

    @SuppressLint("MissingPermission")
    public static String getSerial(Context context) {
        String serial = "unknown";
        try {
            if (Build.VERSION.SDK_INT >= 26 && context.checkPermission(Manifest.permission.READ_PHONE_STATE,
                    android.os.Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
                serial = Build.getSerial();
            } else {
                serial = EnvUtils.properties("ro.serialno");
                if (TextUtils.isEmpty(serial)) {
                    serial = Build.SERIAL;
                }
            }
        } catch (Exception ignore) {
        }
        return serial;
    }

    public static String getCpuAbi() {
        return EnvUtils.properties("ro.product.cpu.abi");
    }

    public static void getMac(IpScanner.OnScanListener listener) {
        IpScanner ipScanner = new IpScanner();
        ipScanner.startScan(listener);
    }

    public static boolean hasLocalAssetsFile(Context context, String name) {
        boolean exits = false;
        try {
            AssetManager assetManager = context.getAssets();
            String[] names = assetManager.list("");
            if (names != null) {
                for (String s : names) {
                    if (s.equals(name.trim())) {
                        exits = true;
                        break;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return exits;
    }

    public static boolean hasLocalSoFile(Context context, String name) {
        boolean exits = false;
        try {
            File file = new File(context.getApplicationInfo().nativeLibraryDir, name);
            if(file.exists()) {
                exits = true;
            }
        } catch (Exception ignored) {
        }
        return exits;
    }
}
