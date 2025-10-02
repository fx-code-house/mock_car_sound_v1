package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzcm;
import com.google.android.gms.internal.icing.zzco;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public abstract class zzco<MessageType extends zzcm<MessageType, BuilderType>, BuilderType extends zzco<MessageType, BuilderType>> implements zzfg {
    protected abstract BuilderType zza(MessageType messagetype);

    @Override // 
    /* renamed from: zzag, reason: merged with bridge method [inline-methods] */
    public abstract BuilderType clone();

    @Override // com.google.android.gms.internal.icing.zzfg
    public final /* synthetic */ zzfg zza(zzfh zzfhVar) {
        if (!zzbr().getClass().isInstance(zzfhVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zzco<MessageType, BuilderType>) zzfhVar);
    }
}
