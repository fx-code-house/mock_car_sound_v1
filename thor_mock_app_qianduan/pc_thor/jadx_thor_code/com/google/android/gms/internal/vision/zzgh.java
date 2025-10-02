package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzge;
import com.google.android.gms.internal.vision.zzgh;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public abstract class zzgh<MessageType extends zzge<MessageType, BuilderType>, BuilderType extends zzgh<MessageType, BuilderType>> implements zzjm {
    protected abstract BuilderType zza(MessageType messagetype);

    public abstract BuilderType zza(zzhe zzheVar, zzho zzhoVar) throws IOException;

    @Override // 
    /* renamed from: zzeh, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    public BuilderType zza(byte[] bArr, int i, int i2, zzho zzhoVar) throws zzin {
        try {
            zzhe zzheVarZza = zzhe.zza(bArr, 0, i2, false);
            zza(zzheVarZza, zzhoVar);
            zzheVarZza.zzax(0);
            return this;
        } catch (zzin e) {
            throw e;
        } catch (IOException e2) {
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name).length() + 60 + "byte array".length()).append("Reading ").append(name).append(" from a byte array threw an IOException (should never happen).").toString(), e2);
        }
    }

    @Override // com.google.android.gms.internal.vision.zzjm
    public final /* synthetic */ zzjm zza(zzjn zzjnVar) {
        if (!zzgx().getClass().isInstance(zzjnVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzgh<MessageType, BuilderType>) zzjnVar);
    }
}
