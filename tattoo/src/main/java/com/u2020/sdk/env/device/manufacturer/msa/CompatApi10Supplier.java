package com.u2020.sdk.env.device.manufacturer.msa;

import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.AndroidDeviceUtil;
import com.u2020.sdk.env.device.Logger;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * oaid sdk 1.0.10 兼容设备符提供者
 */
public final class CompatApi10Supplier implements IdSupplier {
    private final Context context;
    private boolean supply = false;

    public CompatApi10Supplier(Context context) {
        this.context = context;
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return "Api10Supplier";
    }

    @Override
    public boolean supply() {
        if (!AndroidDeviceUtil.hasLocalAssetsFile(context, "supplierconfig.json") ||
                !AndroidDeviceUtil.hasLocalSoFile(context, "libA3AEECD8.so")) {
            return false;
        }

        try {
            Class.forName("com.bun.miitmdid.core.IIdentifierListener");
            Class.forName("com.bun.miitmdid.utils.SupplierListener");
            supply = true;
            CompatApi13Supplier.i.ii(context);
        } catch (Throwable ignored) {
        }

        return supply;
    }

    @Override
    public void get(final Tattoo.O o) {
        try {
            Class<?> cls = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            Class<?> callback = Class.forName("com.bun.miitmdid.core.IIdentifierListener");
            final Method initSdk = cls.getMethod("InitSdk", Context.class, boolean.class, callback);
            final Object callbackObj = Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    new Class[]{callback},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
                            if (method != null && "OnSupport".equals(method.getName()) && args != null) {
                                try {
                                    Object supplier = args[1];
                                    if (supplier != null) {
                                        Class<?> cls = Class.forName("com.bun.miitmdid.supplier.IdSupplier");
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
                                    Logger.e(getName() + " OnSupport Exception " + e.getMessage());
                                }
                            }
                            return null;
                        }
                    });
            @SuppressWarnings("ConstantConditions")
            int code = (int) initSdk.invoke(null, context, true, callbackObj);
            Logger.d(getName() + " InitSdk Code " + code);
        } catch (Throwable e) {
            Logger.d(getName() + " get Exception ", e);
            throw new CompatException(e);
        }
    }
}
