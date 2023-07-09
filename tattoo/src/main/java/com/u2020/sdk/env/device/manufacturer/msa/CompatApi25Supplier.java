package com.u2020.sdk.env.device.manufacturer.msa;

import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.AndroidDeviceUtil;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * oaid sdk 1.0.22~1.0.25 兼容设备符提供者
 */
public final class CompatApi25Supplier implements IdSupplier {
    private final Context context;
    private boolean supply = false;

    public CompatApi25Supplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "Api25Supplier";
    }

    @Override
    public boolean supply() {
        if(supply) return true;

        if (!AndroidDeviceUtil.hasLocalAssetsFile(context, "supplierconfig.json") ||
                !AndroidDeviceUtil.hasLocalAssetsFile(context, "zlsioh.dat")
                        && !AndroidDeviceUtil.hasLocalAssetsFile(context, "niihhh.dat") ||
                !AndroidDeviceUtil.hasLocalSoFile(context, "libsecsdk.so")) {
            Logger.e(getName() + " wrong config file ");
            return false;
        }

        try {
            Class.forName("com.bun.miitmdid.interfaces.IIdentifierListener");
            Class<?> cls = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            Object obj = cls.newInstance();
            @SuppressWarnings("JavaReflectionMemberAccess")
            Field field = cls.getDeclaredField("sdk_date");
            field.setAccessible(true);
            String date = (String) field.get(obj);
            supply = "20200702".equals(date);
        } catch (Throwable ignored) {
        }

        return supply;
    }

    @Override
    public void get(final Tattoo.O o) {
        try {
            Class<?> cls = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            Class<?> callback = Class.forName("com.bun.miitmdid.interfaces.IIdentifierListener");
            final Method initSdk = cls.getMethod("InitSdk", Context.class, boolean.class, callback);
            final Object callbackObj = Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{callback},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
                            if (method != null && "OnSupport".equals(method.getName()) && args != null) {
                                try {
                                    // boolean isSupport = (boolean) args[0];
                                    Object supplier = args[1];
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
                                            Logger.e(getName() + " OnSupport with nothing");
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
            int code = (int) initSdk.invoke(null, context, true, callbackObj);
            String message = getName() + " InitSdk Code " + code;
            Logger.d(message);
            switch (code) {//README:InitSdk Code
                case 1008611:
                case 1008612:
                case 1008613:
                case 1008615:
                case 1008616:
                    throw new CompatException(new Throwable(message));
            }
        } catch (Exception e) {
            Logger.d(getName() + " get Exception ", e);
            throw new CompatException(e);
        }
    }
}
