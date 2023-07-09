/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.u2020.sdk.env.device.manufacturer.brand.msakl;

public interface MsaIdInterface extends android.os.IInterface {

    boolean isSupported() throws android.os.RemoteException;

    boolean isDataArrived() throws android.os.RemoteException;

    String getOAID() throws android.os.RemoteException;

    String getVAID() throws android.os.RemoteException;

    String getAAID() throws android.os.RemoteException;

    void shutDown() throws android.os.RemoteException;

    /**
     * Local-side IPC implementation stub class.
     */
    abstract class Stub extends android.os.Binder implements MsaIdInterface {
        static final int TRANSACTION_isSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_isDataArrived = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_getOAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_getVAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_getAAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
        static final int TRANSACTION_shutDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
        private static final String DESCRIPTOR = "com.bun.lib.MsaIdInterface";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an repeackage.com.bun.lib.MsaIdInterface interface,
         * generating a proxy if needed.
         */
        public static MsaIdInterface asInterface(android.os.IBinder obj) {
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
                case TRANSACTION_isSupported: {
                    data.enforceInterface(descriptor);
                    boolean _result = this.isSupported();
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                case TRANSACTION_isDataArrived: {
                    data.enforceInterface(descriptor);
                    boolean _result = this.isDataArrived();
                    reply.writeNoException();
                    reply.writeInt(((_result) ? (1) : (0)));
                    return true;
                }
                case TRANSACTION_getOAID: {
                    data.enforceInterface(descriptor);
                    String _result = this.getOAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_getVAID: {
                    data.enforceInterface(descriptor);
                    String _result = this.getVAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_getAAID: {
                    data.enforceInterface(descriptor);
                    String _result = this.getAAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_shutDown: {
                    data.enforceInterface(descriptor);
                    this.shutDown();
                    reply.writeNoException();
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements MsaIdInterface {
            private final android.os.IBinder token;

            Proxy(android.os.IBinder binder) {
                token = binder;
            }

            @Override
            public android.os.IBinder asBinder() {
                return token;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public boolean isSupported() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_isSupported, _data, _reply, 0);
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public boolean isDataArrived() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_isDataArrived, _data, _reply, 0);
                    _reply.readException();
                    _result = (0 != _reply.readInt());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
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
            public String getVAID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
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
            public String getAAID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
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
            public void shutDown() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    token.transact(Stub.TRANSACTION_shutDown, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }


    }
}
