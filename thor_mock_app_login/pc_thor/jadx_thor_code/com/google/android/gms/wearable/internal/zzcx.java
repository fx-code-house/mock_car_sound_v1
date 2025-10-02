package com.google.android.gms.wearable.internal;

import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzcx implements DataEvent {
    private final int zza;
    private final DataItem zzb;

    public zzcx(DataEvent dataEvent) {
        this.zza = dataEvent.getType();
        this.zzb = new zzdc(dataEvent.getDataItem());
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* bridge */ /* synthetic */ DataEvent freeze() {
        return this;
    }

    @Override // com.google.android.gms.wearable.DataEvent
    public final DataItem getDataItem() {
        return this.zzb;
    }

    @Override // com.google.android.gms.wearable.DataEvent
    public final int getType() {
        return this.zza;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        int i = this.zza;
        String str = i == 1 ? "changed" : i == 2 ? "deleted" : EnvironmentCompat.MEDIA_UNKNOWN;
        String strValueOf = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(str.length() + 35 + String.valueOf(strValueOf).length());
        sb.append("DataEventEntity{ type=");
        sb.append(str);
        sb.append(", dataitem=");
        sb.append(strValueOf);
        sb.append(" }");
        return sb.toString();
    }
}
