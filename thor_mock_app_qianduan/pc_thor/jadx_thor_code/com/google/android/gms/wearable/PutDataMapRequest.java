package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.internal.Asserts;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public class PutDataMapRequest {
    private final PutDataRequest zza;
    private final DataMap zzb;

    private PutDataMapRequest(PutDataRequest putDataRequest, DataMap dataMap) {
        this.zza = putDataRequest;
        DataMap dataMap2 = new DataMap();
        this.zzb = dataMap2;
        if (dataMap != null) {
            dataMap2.putAll(dataMap);
        }
    }

    public static PutDataMapRequest create(String str) {
        Asserts.checkNotNull(str, "path must not be null");
        return new PutDataMapRequest(PutDataRequest.create(str), null);
    }

    public static PutDataMapRequest createFromDataMapItem(DataMapItem dataMapItem) {
        Asserts.checkNotNull(dataMapItem, "source must not be null");
        return new PutDataMapRequest(PutDataRequest.zza(dataMapItem.getUri()), dataMapItem.getDataMap());
    }

    public static PutDataMapRequest createWithAutoAppendedId(String str) {
        Asserts.checkNotNull(str, "pathPrefix must not be null");
        return new PutDataMapRequest(PutDataRequest.createWithAutoAppendedId(str), null);
    }

    public PutDataRequest asPutDataRequest() {
        com.google.android.gms.internal.wearable.zzj zzjVarZza = com.google.android.gms.internal.wearable.zzk.zza(this.zzb);
        this.zza.setData(zzjVarZza.zza.zzI());
        int size = zzjVarZza.zzb.size();
        for (int i = 0; i < size; i++) {
            String string = Integer.toString(i);
            Asset asset = zzjVarZza.zzb.get(i);
            if (string == null) {
                String strValueOf = String.valueOf(asset);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 26);
                sb.append("asset key cannot be null: ");
                sb.append(strValueOf);
                throw new IllegalStateException(sb.toString());
            }
            if (asset == null) {
                throw new IllegalStateException(string.length() != 0 ? "asset cannot be null: key=".concat(string) : new String("asset cannot be null: key="));
            }
            if (Log.isLoggable(DataMap.TAG, 3)) {
                String strValueOf2 = String.valueOf(asset);
                StringBuilder sb2 = new StringBuilder(string.length() + 33 + String.valueOf(strValueOf2).length());
                sb2.append("asPutDataRequest: adding asset: ");
                sb2.append(string);
                sb2.append(StringUtils.SPACE);
                sb2.append(strValueOf2);
                Log.d(DataMap.TAG, sb2.toString());
            }
            this.zza.putAsset(string, asset);
        }
        return this.zza;
    }

    public DataMap getDataMap() {
        return this.zzb;
    }

    public Uri getUri() {
        return this.zza.getUri();
    }

    public boolean isUrgent() {
        return this.zza.isUrgent();
    }

    public PutDataMapRequest setUrgent() {
        this.zza.setUrgent();
        return this;
    }
}
