/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.u2020.sdk.env.device.manufacturer.brand.zui;

public interface IDeviceIdInterface extends android.os.IInterface {

    String getOAID() throws android.os.RemoteException;

    String getUDID() throws android.os.RemoteException;

    boolean isSupport() throws android.os.RemoteException;

    String getVAID(String str) throws android.os.RemoteException;

    String getAAID(String str) throws android.os.RemoteException;

    String createAAIDForPackageName(String str) throws android.os.RemoteException;

    abstract class Stub extends android.os.Binder implements IDeviceIdInterface {
        static final int TRANSACTION_getOAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getUDID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_isSupport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_getVAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_getAAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
        static final int TRANSACTION_createAAIDForPackageName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
        private static final String DESCRIPTOR = "com.zui.deviceidservice.IDeviceidInterface";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDeviceIdInterface asInterface(android.os.IBinder obj) {
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
                case TRANSACTION_getUDID: {
                    data.enforceInterface(descriptor);
                    String _result = this.getUDID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_isSupport: {
                    data.enforceInterface(descriptor);
                    boolean _result = this.isSupport();
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                case TRANSACTION_getVAID: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    String _result = this.getVAID(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_getAAID: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    String _result = this.getAAID(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_createAAIDForPackageName: {
                    data.enforceInterface(descriptor);
                    String _arg0;
                    _arg0 = data.readString();
                    String _result = this.createAAIDForPackageName(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IDeviceIdInterface {
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
                    token.transact(Stub.TRANSACTION_getOAID, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String getUDID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_getUDID, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public boolean isSupport() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_isSupport, _data, _reply, 0);
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String getVAID(String str) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(str);
                    token.transact(Stub.TRANSACTION_getVAID, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String getAAID(String str) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(str);
                    token.transact(Stub.TRANSACTION_getAAID, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String createAAIDForPackageName(String str) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(str);
                    token.transact(Stub.TRANSACTION_createAAIDForPackageName, _data, _reply, 0);
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
