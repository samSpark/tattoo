package com.u2020.sdk.env.device.manufacturer;

import android.content.Context;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.manufacturer.brand.AsusSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.CompatMsaKlSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.CoolPadSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.CooseaSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.FreemeSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.HuaweiSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.LenovoSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.MeizuSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.NubiyaSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.OppoSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.SamsungSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.VivoSupplier;
import com.u2020.sdk.env.device.manufacturer.brand.XiaomiSupplier;

class ManufacturerSupplier implements IdSupplier, IdSupplierFactory.Factory {
    private Context context;
    private IdSupplier idSupplier;

    private ManufacturerSupplier() {
    }

    public ManufacturerSupplier(Context context) {
        this.context = context;
        idSupplier = create();
    }

    @Override
    public Context getApplicationContext() {
        return context;
    }

    @Override
    public String getName() {
        return idSupplier.getName();
    }

    @Override
    public boolean supply() {
        return idSupplier.supply();
    }

    @Override
    public void get(Tattoo.O o) {
        idSupplier.get(o);
    }

    @Override
    public IdSupplier create() {//cumutative distribution order by desc
        if (DevIdentification.hawed()) {
            return new HuaweiSupplier(context);
        }
        if (DevIdentification.mi() || DevIdentification.heishi()) {
            return new XiaomiSupplier(context);
        }
        if (DevIdentification.oppo() || DevIdentification.onePlus()) {
            return new OppoSupplier(context);
        }
        if (DevIdentification.vivo()) {
            return new VivoSupplier(context);
        }
        if (DevIdentification.samsung()) {
            return new SamsungSupplier(context);
        }
        if (DevIdentification.meizu()) {
            return new MeizuSupplier(context);
        }
        if (DevIdentification.nubiya()) {
            return new NubiyaSupplier(context);
        }
        if (DevIdentification.lenovo()) {
            return new LenovoSupplier(context);
        }
        if (DevIdentification.coolpad(context)) {
            return new CoolPadSupplier(context);
        }
        if (DevIdentification.zte()) {
            return new CompatMsaKlSupplier(context);
        }
        if (DevIdentification.asus()) {
            return new AsusSupplier(context);
        }
        if (DevIdentification.coosea()) {
            return new CooseaSupplier(context);
        }
        if (DevIdentification.freeme()) {
            return new FreemeSupplier(context);
        }
        if (DevIdentification.motolora()) {
            return new LenovoSupplier(context);
        }
        if (DevIdentification.ssui()) {
            return new CompatMsaKlSupplier(context);
        }
        return new MuteSupplier();
    }
}
