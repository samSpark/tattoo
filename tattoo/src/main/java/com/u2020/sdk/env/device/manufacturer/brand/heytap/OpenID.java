/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.u2020.sdk.env.device.manufacturer.brand.heytap;

public interface OpenID extends android.os.IInterface {

    String getSerID(String pkgName, String sign, String type) throws android.os.RemoteException;

    abstract class Stub extends android.os.Binder implements OpenID {
        private static final String DESCRIPTOR = "com.heytap.openid.IOpenID";
        private static final int TRANSACTION_getSerID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static OpenID asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            return new Stub.Proxy(obj);
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
                case TRANSACTION_getSerID: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    String _arg1;
                    _arg1 = data.readString();
                    String _arg2;
                    _arg2 = data.readString();
                    String _result = this.getSerID(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }


        private static class Proxy implements OpenID {
            private final android.os.IBinder token;

            Proxy(android.os.IBinder token) {
                this.token = token;
            }

            @Override
            public android.os.IBinder asBinder() {
                return token;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public String getSerID(String pkgName, String sign, String type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeString(sign);
                    _data.writeString(type);
                    token.transact(Stub.TRANSACTION_getSerID, _data, _reply, 0);//boolean
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
