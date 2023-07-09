package com.u2020.sdk;

import android.content.Context;

import com.u2020.sdk.env.device.AndroidDeviceUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AndroidDeviceUtilTest {

    @Test
    public void checkAssetsFile() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        boolean exits = !AndroidDeviceUtil.hasLocalAssetsFile(appContext, "supplierconfig.json") ||
                !AndroidDeviceUtil.hasLocalAssetsFile(appContext, "zlsioh.dat")
                        && !AndroidDeviceUtil.hasLocalAssetsFile(appContext, "niihhh.dat");
        //assertTrue(AndroidDeviceUtil.checkAssetsFile(appContext, "supplierconfig.json"));
        //assertTrue(AndroidDeviceUtil.checkAssetsFile(appContext, "niihhh.dat"));
        assertFalse(exits);
    }

    @Test
    public void checkSoFile () {
        Context appContext = InstrumentationRegistry.getTargetContext();
        //boolean exits = AndroidDeviceUtil.hasLocalSoFile(appContext, "libtattoo.so");
        boolean exits = AndroidDeviceUtil.hasLocalSoFile(appContext, "libsecsdk.so");
        //boolean exits = AndroidDeviceUtil.hasLocalSoFile(appContext, "lib39285EFA.so");
        //boolean exits = AndroidDeviceUtil.hasLocalSoFile(appContext, "libA3AEECD8.so");
        assertTrue(exits);
    }
}
