package com.u2020.sdk.env.device.manufacturer.msa;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.AndroidDeviceUtil;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * oaid sdk 1.0.26及以上MSA通信院设备符提供者。
 * 需配置证书在Android assets文件夹。
 * 证书可命名为packageName.cert.pem(包名.cert.pem)
 * 或在meta-data自定义指定证书名称:OauthSupplierCert(key):证书名称(value)。
 * 指定证书二选一方式，两者存在优先取meta-data。
 */
public final class CompatApiOauthSupplier implements IdSupplier {
    private final Context context;
    private boolean supply = false;
    private int version;

    public CompatApiOauthSupplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "OauthSupplier";
    }

    @Override
    public boolean supply() {
        if (supply) return true;

        String cert;
        if (!AndroidDeviceUtil.hasLocalAssetsFile(context, "supplierconfig.json") ||
                !AndroidDeviceUtil.hasLocalSoFile(context, "libmsaoaidauth.so") ||
                !AndroidDeviceUtil.hasLocalAssetsFile(context, TextUtils.isEmpty(cert = getValue(context))
                        ? cert = (context.getPackageName() + ".cert.pem") : cert)) {
            Logger.e(getName() + " wrong config file or cert");
            return false;
        }

        try {
            if (AndroidDeviceUtil.hasLocalSoFile(context, "libmsaoaidsec.so")) {
                System.loadLibrary("msaoaidsec");
            } else if (AndroidDeviceUtil.hasLocalSoFile(context, "libnllvm1632808251147706677.so")) {
                System.loadLibrary("nllvm1632808251147706677");
            } else if (AndroidDeviceUtil.hasLocalSoFile(context, "libnllvm1630571663641560568.so")) {
                System.loadLibrary("nllvm1630571663641560568");
            } else if (AndroidDeviceUtil.hasLocalSoFile(context, "libnllvm1623827671.so")) {
                System.loadLibrary("nllvm1623827671");
            }
            Class.forName("com.bun.miitmdid.interfaces.IIdentifierListener");
            Class<?> cls = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");

            //noinspection JavaReflectionMemberAccess
            Field field = cls.getDeclaredField("SDK_VERSION_CODE");
            field.setAccessible(true);
            version = field.getInt(cls.newInstance());
            //noinspection JavaReflectionMemberAccess
            Method method = cls.getMethod("InitCert", Context.class, String.class);
            //noinspection ConstantConditions
            supply = (boolean) method.invoke(null, context, loadPemFromAssetFile(context, cert));

            Logger.d(version + " " + getName() + " CertInitState " + supply);
        } catch (Throwable e) {//must be Throwable
            Logger.e(getName(), e);
        }

        return supply;
    }

    @SuppressWarnings({"JavaReflectionMemberAccess", "ConstantConditions"})
    @Override
    public void get(Tattoo.O o) {
        if (!supply) {
            Logger.e(getName() + " supply nothing");
            o.valid(null);
            return;
        }
        try {
            Class<?> cls = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            Class<?> callback = Class.forName("com.bun.miitmdid.interfaces.IIdentifierListener");

            final Method initSdk = version >= 20220420 ?
                    cls.getMethod("InitSdk", Context.class, boolean.class,
                            boolean.class, boolean.class, boolean.class, callback) :
                    cls.getMethod("InitSdk", Context.class, boolean.class, callback);
            final Object callbackObj = Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{callback},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
                            if (method != null && "onSupport".equals(method.getName()) && args != null) {
                                try {
                                    Object supplier = args[0];
                                    if (supplier != null) {
                                        Class<?> cls = Class.forName("com.bun.miitmdid.interfaces.IdSupplier");
                                        Method support = cls.getMethod("isSupported");
                                        boolean isSupported = (boolean) support.invoke(supplier);
                                        if (isSupported) {
                                            Method getOAID = cls.getMethod("getOAID");
                                            String oaid = (String) getOAID.invoke(supplier);
                                            Logger.d(getName() + " OnSupport oaid " + oaid);
                                            o.valid(oaid);
                                        } else {
                                            o.valid(null);
                                            Logger.e(getName() + " onSupport with nothing");
                                        }
                                    }
                                } catch (Exception e) {
                                    o.valid(null);
                                    Logger.e(getName() + " OnSupport Exception ", e);
                                }
                            }
                            return null;
                        }
                    });
            int code = version >= 20220420 ?
                    (int) initSdk.invoke(null, context, Tattoo.DEBUG, true, false, false, callbackObj) :
                    (int) initSdk.invoke(null, context, Tattoo.DEBUG, callbackObj);
            String message = getName() + " InitSdk Code " + code;
            Logger.d(message);
            switch (code) {//README:InitSdk Code
                case 1008611:
                case 1008612:
                case 1008613:
                case 1008615:
                case 1008616:
                    throw new CompatException(message);
            }
        } catch (Throwable e) {
            Logger.d(getName() + " get Exception ", e);
            throw new CompatException(e);
        }
    }

    private String getValue(Context context) {
        String value = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            Object obj = bundle.get("OauthSupplierCert");
            value = obj == null ? null : obj.toString();
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return value;
    }

    /**
     * 从asset文件读取证书内容
     *
     * @param context       上下文
     * @param assetFileName Assets目录下证书名
     * @return 证书字符串
     */
    public String loadPemFromAssetFile(Context context, String assetFileName) {
        try {
            InputStream is = context.getAssets().open(assetFileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            return builder.toString();
        } catch (IOException e) {
            Logger.e(getName(), e);
        }
        return "";
    }
}
