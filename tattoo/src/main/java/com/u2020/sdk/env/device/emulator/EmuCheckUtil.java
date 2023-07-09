package com.u2020.sdk.env.device.emulator;

import android.content.Context;
import android.text.TextUtils;

import com.u2020.sdk.env.device.AndroidDeviceUtil;
import com.u2020.sdk.env.device.ShellAdbUtils;
import com.u2020.sdk.env.jni.EnvUtils;

/**
 * 模拟器判断及相关信息获取
 */

public class EmuCheckUtil {

    /**
     * 是否是模拟器
     *
     * @param context
     * @return true 是
     */
    public static boolean mayOnEmulator(Context context) {
        boolean b = mayOnEmulatorViaQEMU(context)
                || isEmulatorFromAbi()
                || isEmulatorViaBuild(context)
                || isEmulatorFromCpu();
        return b;

    }

    /**
     * 是否是模拟器(safe)
     *
     * @param context
     * @return true 是
     */
    public static boolean mayOnEmulator2(Context context) {
        boolean b = mayOnEmulatorViaQEMU(context)
                || isEmulatorFromAbi();
        return b;

    }


    public static boolean isEmulatorViaBuild(Context context) {
        String properties = EnvUtils.properties("ro.product.model");
        if (!TextUtils.isEmpty(properties)
                && properties.toLowerCase().contains("sdk")) {
            return true;
        }

        /**
         * ro.product.manufacturer likes unknown
         */
        if (!TextUtils.isEmpty(EnvUtils.properties("ro.product.manufacturer"))
                && EnvUtils.properties("ro.product.manufacture").toLowerCase().contains("unknown")) {
            return true;
        }

        /**
         * ro.product.device likes generic
         */
        return !TextUtils.isEmpty(EnvUtils.properties("ro.product.device"))
                && EnvUtils.properties("ro.product.device").toLowerCase().contains("generic");
    }


    //  qemu模拟器特征
    public static boolean mayOnEmulatorViaQEMU(Context context) {
        String qemu = EnvUtils.properties("ro.kernel.qemu");
        return "1".equals(qemu);
    }


    // 查杀比较严格，放在最后，直接pass x86
    private static boolean isEmulatorFromCpu() {
        ShellAdbUtils.CommandResult commandResult = ShellAdbUtils.execCommand("cat /proc/cpuinfo", false);
        String cpuInfo = commandResult == null ? "" : commandResult.successMsg;
        return !TextUtils.isEmpty(cpuInfo) && ((cpuInfo.contains("intel") || cpuInfo.contains("amd")));
    }

    //从cpu型号判断是否是模拟器
    private static boolean isEmulatorFromAbi() {

        String abi = AndroidDeviceUtil.getCpuAbi();

        return !TextUtils.isEmpty(abi) && abi.contains("x86");
    }

}
