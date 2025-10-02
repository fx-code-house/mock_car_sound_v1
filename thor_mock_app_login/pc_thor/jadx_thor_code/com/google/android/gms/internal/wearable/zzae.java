package com.google.android.gms.internal.wearable;

import com.google.android.gms.internal.wearable.zzae;
import com.google.android.gms.internal.wearable.zzaf;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public abstract class zzae<MessageType extends zzaf<MessageType, BuilderType>, BuilderType extends zzae<MessageType, BuilderType>> implements zzcw {
    @Override // 
    public abstract BuilderType zzo();

    protected abstract BuilderType zzp(MessageType messagetype);

    @Override // com.google.android.gms.internal.wearable.zzcw
    public final /* bridge */ /* synthetic */ zzcw zzq(zzcx zzcxVar) {
        if (zzac().getClass().isInstance(zzcxVar)) {
            return zzp((zzaf) zzcxVar);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
