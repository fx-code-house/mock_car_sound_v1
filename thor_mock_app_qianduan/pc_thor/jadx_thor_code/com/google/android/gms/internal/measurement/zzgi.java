package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzgg;
import com.google.android.gms.internal.measurement.zzgi;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
public abstract class zzgi<MessageType extends zzgg<MessageType, BuilderType>, BuilderType extends zzgi<MessageType, BuilderType>> implements zzji {
    protected abstract BuilderType zza(MessageType messagetype);

    public abstract BuilderType zza(zzhb zzhbVar, zzhl zzhlVar) throws IOException;

    @Override // 
    /* renamed from: zzt, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    public BuilderType zza(byte[] bArr, int i, int i2) throws zzij {
        try {
            zzhb zzhbVarZza = zzhb.zza(bArr, 0, i2, false);
            zza(zzhbVarZza, zzhl.zza());
            zzhbVarZza.zza(0);
            return this;
        } catch (zzij e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(zza("byte array"), e2);
        }
    }

    public BuilderType zza(byte[] bArr, int i, int i2, zzhl zzhlVar) throws zzij {
        try {
            zzhb zzhbVarZza = zzhb.zza(bArr, 0, i2, false);
            zza(zzhbVarZza, zzhlVar);
            zzhbVarZza.zza(0);
            return this;
        } catch (zzij e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException(zza("byte array"), e2);
        }
    }

    private final String zza(String str) {
        String name = getClass().getName();
        return new StringBuilder(String.valueOf(name).length() + 60 + String.valueOf(str).length()).append("Reading ").append(name).append(" from a ").append(str).append(" threw an IOException (should never happen).").toString();
    }

    @Override // com.google.android.gms.internal.measurement.zzji
    public final /* synthetic */ zzji zza(zzjj zzjjVar) {
        if (!zzbv().getClass().isInstance(zzjjVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzgi<MessageType, BuilderType>) zzjjVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzji
    public final /* synthetic */ zzji zza(byte[] bArr, zzhl zzhlVar) throws zzij {
        return zza(bArr, 0, bArr.length, zzhlVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzji
    public final /* synthetic */ zzji zza(byte[] bArr) throws zzij {
        return zza(bArr, 0, bArr.length);
    }
}
