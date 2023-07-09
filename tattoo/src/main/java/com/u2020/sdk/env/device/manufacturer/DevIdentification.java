package com.u2020.sdk.env.device.manufacturer;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import static com.u2020.sdk.env.device.SystemProperties.prop;

/**
 * 判断设备所属厂商
 */
final class DevIdentification {
    @Deprecated
    static final CharSequence AMIGO = "amigo";
    static final CharSequence FUNTOUCH = "funtouch";

    static boolean hawed() {
        boolean tag = false;
        if (!TextUtils.isEmpty(Build.BRAND)) {
            tag = Build.BRAND.toLowerCase().contains("huawei") || Build.BRAND.toLowerCase().contains("honor");
        }
        if (tag) {
            return true;
        } else {
            if (!TextUtils.isEmpty(Build.MANUFACTURER)) {
                tag = Build.MANUFACTURER.toLowerCase().contains("huawei");
            }
            if (tag) {
                return true;
            } else {
                String prop = prop("ro.build.version.emui", "");
                return !TextUtils.isEmpty(prop);// && prop.toLowerCase().startsWith("emotionui");
            }
        }
    }

    static boolean oppo() {
        boolean tag = false;
        if (!TextUtils.isEmpty(Build.MANUFACTURER)) {
            tag = Build.MANUFACTURER.toLowerCase().contains("oppo");
        }
        if (tag) {
            return true;
        } else {
            if (!TextUtils.isEmpty(Build.BRAND)) {
                tag = Build.BRAND.toLowerCase().contains("oppo") || Build.BRAND.toLowerCase().contains("realme");
            }
            if (tag) {
                return true;
            } else return !TextUtils.isEmpty(prop("ro.build.version.opporom", ""));
        }

    }

    static boolean vivo() {
        boolean tag = false;
        if (!TextUtils.isEmpty(Build.BRAND)) {
            tag = Build.BRAND.toLowerCase().contains("vivo");
        }
        if (tag) {
            return true;
        } else {
            if (!TextUtils.isEmpty(Build.MANUFACTURER)) {
                tag = Build.MANUFACTURER.toLowerCase().contains("vivo");
            }
            if (tag) {
                return true;
            } else return !TextUtils.isEmpty(prop("ro.vivo.os.version", ""));
        }
    }

    static boolean meizu() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("meizu")) {
            return true;
        } else if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("meizu")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.DISPLAY) && Build.DISPLAY.toLowerCase().contains("flyme");
    }

    static boolean lenovo() {
        if (!TextUtils.isEmpty(Build.BRAND) && (Build.BRAND.toLowerCase().contains("lenovo") || Build.BRAND.toLowerCase().contains("zuk"))) {
            return true;
        } else if(!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.contains("zuk")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("lenovo");
    }

    static boolean mi() {
        boolean tag = false;
        try {
            tag = Class.forName("miui.os.Build").getName().length() > 0;
        } catch (Exception ignored) {
        }
        if (tag) {
            return true;
        } else {
            if (!TextUtils.isEmpty(Build.BRAND)) {
                tag = Build.BRAND.toLowerCase().contains("redmi") || Build.BRAND.toLowerCase().contains("xiaomi");
            }
            if (tag) {
                return true;
            } else if (!TextUtils.isEmpty(Build.MANUFACTURER)) {
                tag = Build.MANUFACTURER.toLowerCase().contains("xiaomi");
            }
            if (tag) {
                return true;
            } else return !TextUtils.isEmpty(prop("ro.miui.ui.version.name", ""));
        }
    }

    static boolean heishi() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("blackshark")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("blackshark");
    }

    static boolean onePlus() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("oneplus")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("oneplus");
    }

    static boolean asus() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("asus")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("asus");
    }

    static boolean samsung() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("samsung")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("samsung");
    }

    static boolean nubiya() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("nubia")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("nubia");
    }

    //中兴
    static boolean zte() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("zte")) {
            return true;
        } else
            return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("zte");
    }

    //卓易
    static boolean freeme() {
        if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("freeme")) {
            return true;
        }
        return !TextUtils.isEmpty(prop("ro.build.freeme.label", ""));
    }

    static boolean coolpad(Context context) {
        boolean tag = false;
        try {
            context.getPackageManager().getPackageInfo("com.coolpad.deviceidsupport", 0);
            tag = true;
        } catch (Exception igonored) {
        }
        return tag;
    }

    //酷塞
    static boolean coosea() {
        return ("prize").equalsIgnoreCase(prop("ro.odm.manufacturer", ""));
    }

    @Deprecated
    static boolean amigo() {
        return !TextUtils.isEmpty(Build.DISPLAY) && Build.DISPLAY.toLowerCase().contains(DevIdentification.AMIGO);
    }

    //奇酷 360 TODO
    static boolean qiku() {
        String brand;
        return !TextUtils.isEmpty(brand = Build.MANUFACTURER + Build.BRAND) && ((brand = brand.toLowerCase()).contains("360") || brand.contains("qiku"));
    }

    //摩托罗拉
    static boolean motolora() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("moto")) {
            return true;
        }
        return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("moto");
    }

    //SSUI OS
    static boolean ssui() {
        if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("ssui")) {
            return true;
        }
        return !TextUtils.isEmpty(prop("ro.ssui.product", ""));
    }
}