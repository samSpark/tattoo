package com.u2020.sdk;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.manufacturer.IdSupplier;
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
import com.u2020.sdk.env.device.manufacturer.msa.CompatApi10Supplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApi13Supplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApi25Supplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatApiOauthSupplier;
import com.u2020.sdk.env.device.manufacturer.msa.CompatException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static com.u2020.sdk.env.device.SystemProperties.prop;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(AndroidJUnit4.class)
public class DevIdUniTest {
    private Context context;
    private String oaid = null;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testRegister() throws InterruptedException {
       Tattoo.o(context, new Tattoo.O() {
           @Override
           public void valid(String o) {
               System.out.println("testRegister " + o);
           }
       });
       Thread.sleep(3000);
    }

    @Test
    public void testSupplierCompatApi25Impl() throws InterruptedException {
        final IdSupplier supplier = new CompatApi25Supplier(context);
        if (supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("testSupplierCompatApi25Impl1 " + o);
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    supplier.get(new Tattoo.O() {
                        @Override
                        public void valid(String o) {
                            System.out.println("testSupplierCompatApi25Impl2 " + o);
                        }
                    });
                }
            }).start();
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("testSupplierCompatApi25Impl3 " + o);
                }
            });
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("testSupplierCompatApi25Impl4 " + o);
                }
            });
            Thread.sleep(1);
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("testSupplierCompatApi25Impl5 " + o);
                }
            });
            Thread.sleep(32000);
        }
    }

    @Test
    public void testSupplierCompatApi13Impl() throws InterruptedException {
        IdSupplier supplier = new CompatApi13Supplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("testSupplierCompatApi13Impl " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testSupplierCompatApi10Impl() throws InterruptedException {
        IdSupplier supplier = new CompatApi10Supplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("testSupplierCompatApi10Impl " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testOppoSupplier() throws InterruptedException {
        IdSupplier supplier = new OppoSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("OppoSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testVivoSupplier() throws InterruptedException {
        IdSupplier supplier = new VivoSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("VivoSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testXiaomiSupplier() throws InterruptedException {
        IdSupplier supplier = new XiaomiSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("XiaomiSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testHmsSupplier() throws InterruptedException {
        IdSupplier supplier = new HuaweiSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("HuaweiSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testSamsungSupplier() throws InterruptedException {
        IdSupplier supplier = new SamsungSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("SamsungSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testMeizuSupplier() throws InterruptedException {
        IdSupplier supplier = new MeizuSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("MeizuSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testNubiyaSupplier() throws InterruptedException {
        IdSupplier supplier = new NubiyaSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("NubiyaSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testLenovoSupplier() throws InterruptedException {
        IdSupplier supplier = new LenovoSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("LenovoSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testCoolPad() {
//        assertFalse(DevIdentification.coolpad(context));
    }

    @Test
    public void testAsus() {
//        assertFalse(DevIdentification.asus());
    }

    @Test
    public void testCoolPadSupplier() throws InterruptedException {
        IdSupplier supplier = new CoolPadSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("CoolPadSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testAsusSupplier() throws InterruptedException {
        IdSupplier supplier = new AsusSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("AsusSupplier " + o);
                }
            });
            Thread.sleep(20000);
        }
    }

    @Test
    public void testMsaKl() throws InterruptedException {
        IdSupplier supplier = new CompatMsaKlSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("CompatMsaKlSupplier " + o);
                }
            });
            Thread.sleep(6000);
        }else {
            System.out.println("CompatMsaKlSupplier supply nothing");
        }
    }

    @Test
    public void testFreemeDev() {
        prop("ro.build.freeme.label", "");
    }

    @Test
    public void testCooseaDev() {
        ("prize").equalsIgnoreCase(prop("ro.odm.manufacturer", ""));
    }

    @Test
    public void testMotoDev() {
        if (!TextUtils.isEmpty(Build.BRAND) && Build.BRAND.toLowerCase().contains("moto")) {

        }
        if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("moto")) {

        }
    }

    @Test
    public void testSsuiDev() {
        if (!TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toLowerCase().contains("ssui")) {

        }
        prop("ro.ssui.product", "");
    }

    @Test
    public void testFreemeSupplier() {
        IdSupplier supplier = new FreemeSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("FreemeSupplier " + o);
                }
            });
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("FreemeSupplier supply nothing");
        }
    }

    @Test
    public void testCooseaSupplier() {
        IdSupplier supplier = new CooseaSupplier(context);
        if(supplier.supply()) {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    System.out.println("CooseaSupplier " + o);
                }
            });
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("CooseaSupplier supply nothing");
        }
    }

    @Test
    public void testSupplierCompatApiOathImpl() throws InterruptedException {
        CompatApiOauthSupplier supplier = new CompatApiOauthSupplier(context);
        assertFalse(supplier.supply());
        try {
            supplier.get(new Tattoo.O() {
                @Override
                public void valid(String o) {
                    oaid = o;
                }
            });
        } catch (CompatException ignored) {
        }
        TimeUnit.SECONDS.sleep(2);
        assertNull(oaid);
        TimeUnit.SECONDS.sleep(2);
    }
}
