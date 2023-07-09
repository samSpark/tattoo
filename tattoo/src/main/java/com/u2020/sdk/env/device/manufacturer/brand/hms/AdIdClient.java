/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.u2020.sdk.env.device.manufacturer.brand.hms;

/**
 * This file is auto-generated.  DO NOT MODIFY.
 * 获取OAID信息（AIDL方式）
 * 开发者也可直接调用Ads Kit的AIDL接口获取华为设备上的OAID，
 * 这种集成方式不需要集成任何华为SDK。AIDL接口获取到的OAID与同一台设备上SDK接口获取到的OAID相同。
 * {@linkhttps://developer.huawei.com/consumer/cn/doc/3030305}
 */
public interface AdIdClient extends android.os.IInterface {

    String getOaid() throws android.os.RemoteException;

    boolean isOaidTrackLimited() throws android.os.RemoteException;

    abstract class Stub extends android.os.Binder implements AdIdClient {
        static final int TRANSACTION_getOaid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_isOaidTrackLimited = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        private static final String DESCRIPTOR = "com.uodis.opendevice.aidl.OpenDeviceIdentifierService";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static AdIdClient asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            return new Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getOaid: {
                    data.enforceInterface(descriptor);
                    String _result = this.getOaid();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_isOaidTrackLimited: {
                    data.enforceInterface(descriptor);
                    boolean _result = this.isOaidTrackLimited();
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements AdIdClient {
            private final android.os.IBinder token;

            Proxy(android.os.IBinder remote) {
                token = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return token;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public String getOaid() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_getOaid, _data, _reply, 0);//boolean
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public boolean isOaidTrackLimited() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_isOaidTrackLimited, _data, _reply, 0);//boolean
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
    }
}
