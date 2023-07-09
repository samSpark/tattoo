package com.u2020.sdk.env.device.manufacturer.brand;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.u2020.sdk.env.Tattoo;
import com.u2020.sdk.env.device.Logger;

/**
 * 厂商设备符接口远程连接服务
 */
class IdSupplierRemoteService implements ServiceConnection {
    private final Context context;
    private final IdInterface idInterface;
    private final Tattoo.O o;

    public IdSupplierRemoteService(Context context, Tattoo.O o, IdInterface idInterface) {
        this.context = context;
        this.idInterface = idInterface;
        this.o = o;
    }

    public void bind(Intent intent) {
        try {
            boolean result = context.bindService(intent, this, Context.BIND_AUTO_CREATE);
            if (!result)
                o.valid(null);
        } catch (Throwable e) {
            Logger.e(e.getMessage());
            o.valid(null);
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            String[] ids = idInterface.asIds(iBinder);
            Logger.d("IdSupplierRemoteService OnSupport oaid " + ids[0]);
            o.valid(ids[0]);//oaid
        } catch (Throwable t) {
            Logger.e("onServiceConnected by", t);
            o.valid(null);
        } finally {
            context.unbindService(IdSupplierRemoteService.this);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (componentName != null)
            Logger.d(componentName.getClassName());
    }


}
