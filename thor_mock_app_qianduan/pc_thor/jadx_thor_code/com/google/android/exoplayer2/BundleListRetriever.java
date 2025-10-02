package com.google.android.exoplayer2;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public final class BundleListRetriever extends Binder {
    private static final int REPLY_BREAK = 2;
    private static final int REPLY_CONTINUE = 1;
    private static final int REPLY_END_OF_LIST = 0;
    private static final int SUGGESTED_MAX_IPC_SIZE;
    private final ImmutableList<Bundle> list;

    static {
        SUGGESTED_MAX_IPC_SIZE = Util.SDK_INT >= 30 ? IBinder.getSuggestedMaxIpcSizeBytes() : 65536;
    }

    public BundleListRetriever(List<Bundle> list) {
        this.list = ImmutableList.copyOf((Collection) list);
    }

    @Override // android.os.Binder
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code != 1) {
            return super.onTransact(code, data, reply, flags);
        }
        if (reply == null) {
            return false;
        }
        int size = this.list.size();
        int i = data.readInt();
        while (i < size && reply.dataSize() < SUGGESTED_MAX_IPC_SIZE) {
            reply.writeInt(1);
            reply.writeBundle(this.list.get(i));
            i++;
        }
        reply.writeInt(i < size ? 2 : 0);
        return true;
    }

    public static ImmutableList<Bundle> getList(IBinder binder) {
        int i;
        ImmutableList.Builder builder = ImmutableList.builder();
        int i2 = 0;
        int i3 = 1;
        while (i3 != 0) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInt(i2);
                try {
                    binder.transact(1, parcelObtain, parcelObtain2, 0);
                    while (true) {
                        i = parcelObtain2.readInt();
                        if (i == 1) {
                            builder.add((ImmutableList.Builder) Assertions.checkNotNull(parcelObtain2.readBundle()));
                            i2++;
                        }
                    }
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    i3 = i;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                throw th;
            }
        }
        return builder.build();
    }
}
