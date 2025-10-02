package com.google.android.gms.internal.fido;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
final class zzaw extends zzar {
    private final zzaz zza;

    zzaw(zzaz zzazVar, int i) {
        super(zzazVar.size(), i);
        this.zza = zzazVar;
    }

    @Override // com.google.android.gms.internal.fido.zzar
    protected final Object zza(int i) {
        return this.zza.get(i);
    }
}
