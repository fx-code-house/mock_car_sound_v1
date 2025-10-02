package com.google.android.gms.wearable.internal;

import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzcy extends DataBufferRef implements DataEvent {
    private final int zza;

    public zzcy(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zza = i2;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* bridge */ /* synthetic */ DataEvent freeze() {
        return new zzcx(this);
    }

    @Override // com.google.android.gms.wearable.DataEvent
    public final DataItem getDataItem() {
        return new zzdf(this.mDataHolder, this.mDataRow, this.zza);
    }

    @Override // com.google.android.gms.wearable.DataEvent
    public final int getType() {
        return getInteger("event_type");
    }

    public final String toString() {
        String str = getInteger("event_type") == 1 ? "changed" : getInteger("event_type") == 2 ? "deleted" : EnvironmentCompat.MEDIA_UNKNOWN;
        String strValueOf = String.valueOf(getDataItem());
        StringBuilder sb = new StringBuilder(str.length() + 32 + String.valueOf(strValueOf).length());
        sb.append("DataEventRef{ type=");
        sb.append(str);
        sb.append(", dataitem=");
        sb.append(strValueOf);
        sb.append(" }");
        return sb.toString();
    }
}
