package com.u2020.sdk.env.device.manufacturer.brand.fm;

public interface CreatorInterface extends android.os.IInterface {


    boolean isSupported() throws android.os.RemoteException;

    java.lang.String getUDID(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getOAID() throws android.os.RemoteException;

    java.lang.String getVAID() throws android.os.RemoteException;

    java.lang.String getAAID(java.lang.String str) throws android.os.RemoteException;

    /**
     * Local-side IPC implementation stub class.
     */
    abstract class Stub extends android.os.Binder implements CreatorInterface {
        static final int TRANSACTION_isSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getUDID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_getOAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_getVAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_getAAID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
        private static final java.lang.String DESCRIPTOR = "com.android.creator.CreatorInterface";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an repeackage.com.android.creator.CreatorInterface interface,
         * generating a proxy if needed.
         */
        public static CreatorInterface asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            return new CreatorInterface.Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
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
                case TRANSACTION_getUDID: {
                    data.enforceInterface(descriptor);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    java.lang.String _result = this.getUDID(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_getOAID: {
                    data.enforceInterface(descriptor);
                    java.lang.String _result = this.getOAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_getVAID: {
                    data.enforceInterface(descriptor);
                    java.lang.String _result = this.getVAID();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_getAAID: {
                    data.enforceInterface(descriptor);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    java.lang.String _result = this.getAAID(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements CreatorInterface {
            private final android.os.IBinder token;

            Proxy(android.os.IBinder binder) {
                token = binder;
            }

            @Override
            public android.os.IBinder asBinder() {
                return token;
            }

            public java.lang.String getInterfaceDescriptor() {
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
            public java.lang.String getUDID(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(str);
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
            public java.lang.String getOAID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
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
            public java.lang.String getVAID() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
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
            public java.lang.String getAAID(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
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
        }

    }
}
