/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.u2020.sdk.env.device.manufacturer.brand.samsung;

public interface IDeviceIdService extends android.os.IInterface {
    String getOAID() throws android.os.RemoteException;

    abstract class Stub extends android.os.Binder implements IDeviceIdService {
        static final int TRANSACTION_getOAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        private static final String DESCRIPTOR = "com.samsung.android.deviceidservice.IDeviceIdService";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDeviceIdService asInterface(android.os.IBinder obj) {
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
                case TRANSACTION_getOAID: {
                    data.enforceInterface(descriptor);
                    String _result = this.getOAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDeviceIdService {
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
            public String getOAID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_getOAID, _data, _reply, 0);//boolean
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }

    }
}
