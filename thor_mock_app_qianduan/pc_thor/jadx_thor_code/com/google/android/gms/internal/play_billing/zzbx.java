package com.google.android.gms.internal.play_billing;

import com.google.android.gms.internal.play_billing.zzbx;
import com.google.android.gms.internal.play_billing.zzcb;

/* compiled from: com.android.billingclient:billing@@6.0.1 */
/* loaded from: classes2.dex */
public class zzbx<MessageType extends zzcb<MessageType, BuilderType>, BuilderType extends zzbx<MessageType, BuilderType>> extends zzaj<MessageType, BuilderType> {
    protected zzcb zza;
    private final zzcb zzb;

    protected zzbx(MessageType messagetype) {
        this.zzb = messagetype;
        if (messagetype.zzt()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.zza = messagetype.zzi();
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj
    /* renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzbx clone() {
        zzbx zzbxVar = (zzbx) this.zzb.zzu(5, null, null);
        zzbxVar.zza = zze();
        return zzbxVar;
    }

    public final MessageType zzc() {
        MessageType messagetype = (MessageType) zze();
        if (messagetype.zzs()) {
            return messagetype;
        }
        throw new zzef(messagetype);
    }

    @Override // com.google.android.gms.internal.play_billing.zzde
    /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public MessageType zze() {
        if (!this.zza.zzt()) {
            return (MessageType) this.zza;
        }
        this.zza.zzn();
        return (MessageType) this.zza;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdg
    public final /* bridge */ /* synthetic */ zzdf zzf() {
        throw null;
    }

    protected final void zzg() {
        if (this.zza.zzt()) {
            return;
        }
        zzh();
    }

    protected void zzh() {
        zzcb zzcbVarZzi = this.zzb.zzi();
        zzdn.zza().zzb(zzcbVarZzi.getClass()).zzg(zzcbVarZzi, this.zza);
        this.zza = zzcbVarZzi;
    }
}
