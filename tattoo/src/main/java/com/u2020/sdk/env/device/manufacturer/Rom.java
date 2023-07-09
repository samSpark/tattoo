package com.u2020.sdk.env.device.manufacturer;

import android.os.Build;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.u2020.sdk.env.device.SystemProperties.getProp;

public class Rom {
    public static JSONObject get(JSONObject var) throws JSONException {
        StringBuilder rom = new StringBuilder();
        String hawed = null;
        boolean miui = false;
        boolean flyme = false;
        if (miui = DevIdentification.mi()) {
            rom.append("MIUI-");
        } else if (flyme = DevIdentification.meizu()) {
            rom.append("FLYME-");
        } else if (TextUtils.isEmpty(hawed = getProp("ro.build.version.emui"))) {
            rom.append("EMUI-");
            if (!TextUtils.isEmpty(hawed)) {
                rom.append(hawed).append("-");
            }
        }
        rom.append(Build.VERSION.INCREMENTAL);
        var.put("f", rom.toString());
        String romVersion;
        if (miui) {
            String pro = getProp("ro.miui.ui.version.name");
            romVersion = TextUtils.isEmpty(pro) ? null : "miui_" + pro + "_" + Build.DISPLAY;
        } else if (flyme) {
            romVersion = Build.DISPLAY;
        } else if (DevIdentification.oppo() && !TextUtils.isEmpty(romVersion = getProp("ro.build.version.opporom"))) {
            romVersion = "coloros_" + romVersion + "_" + Build.DISPLAY;
        } else if (!TextUtils.isEmpty(hawed)) {
            romVersion = hawed + "_" + Build.DISPLAY;
        } else if (!TextUtils.isEmpty(romVersion = getProp("ro.vivo.os.build.display.id")) && romVersion.toLowerCase().contains(DevIdentification.FUNTOUCH)) {
            romVersion = getProp("ro.vivo.product.version");
            romVersion = TextUtils.isEmpty(romVersion) ? null : romVersion + "_" + getProp("ro.vivo.product.version");
        } else if (DevIdentification.amigo() && !TextUtils.isEmpty(romVersion = getProp("ro.gn.sv.version"))) {
            romVersion = Build.DISPLAY + "_" + romVersion;
        } else if (DevIdentification.qiku() && !TextUtils.isEmpty(romVersion = getProp("ro.build.uiversion"))) {
            romVersion = romVersion + "_" + Build.DISPLAY;
        } else if (!TextUtils.isEmpty(romVersion = getProp("ro.letv.release.version"))) {
            romVersion = "eui_" + romVersion + "_" + Build.DISPLAY;
        }
        if (TextUtils.isEmpty(romVersion)) {
            romVersion = Build.DISPLAY;
        }
        if (!TextUtils.isEmpty(romVersion)) {
            var.put("g", romVersion);
        }
        return var;
    }

}
