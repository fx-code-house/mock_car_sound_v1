package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.wearable.zzcc;
import java.util.ArrayList;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public class DataMapItem {
    private final Uri zza;
    private final DataMap zzb;

    private DataMapItem(DataItem dataItem) {
        DataMap dataMapZzb;
        this.zza = dataItem.getUri();
        DataItem dataItemFreeze = dataItem.freeze();
        if (dataItemFreeze.getData() == null && dataItemFreeze.getAssets().size() > 0) {
            throw new IllegalArgumentException("Cannot create DataMapItem from a DataItem  that wasn't made with DataMapItem.");
        }
        if (dataItemFreeze.getData() == null) {
            dataMapZzb = new DataMap();
        } else {
            try {
                ArrayList arrayList = new ArrayList();
                int size = dataItemFreeze.getAssets().size();
                for (int i = 0; i < size; i++) {
                    DataItemAsset dataItemAsset = dataItemFreeze.getAssets().get(Integer.toString(i));
                    if (dataItemAsset == null) {
                        String strValueOf = String.valueOf(dataItemFreeze);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 64);
                        sb.append("Cannot find DataItemAsset referenced in data at ");
                        sb.append(i);
                        sb.append(" for ");
                        sb.append(strValueOf);
                        throw new IllegalStateException(sb.toString());
                    }
                    arrayList.add(Asset.createFromRef(dataItemAsset.getId()));
                }
                dataMapZzb = com.google.android.gms.internal.wearable.zzk.zzb(new com.google.android.gms.internal.wearable.zzj(com.google.android.gms.internal.wearable.zzw.zzb(dataItemFreeze.getData()), arrayList));
            } catch (zzcc | NullPointerException e) {
                String strValueOf2 = String.valueOf(dataItemFreeze.getUri());
                String strEncodeToString = Base64.encodeToString(dataItemFreeze.getData(), 0);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 50 + String.valueOf(strEncodeToString).length());
                sb2.append("Unable to parse datamap from dataItem. uri=");
                sb2.append(strValueOf2);
                sb2.append(", data=");
                sb2.append(strEncodeToString);
                Log.w("DataItem", sb2.toString());
                String strValueOf3 = String.valueOf(dataItemFreeze.getUri());
                StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf3).length() + 44);
                sb3.append("Unable to parse datamap from dataItem.  uri=");
                sb3.append(strValueOf3);
                throw new IllegalStateException(sb3.toString(), e);
            }
        }
        this.zzb = dataMapZzb;
    }

    public static DataMapItem fromDataItem(DataItem dataItem) {
        Asserts.checkNotNull(dataItem, "dataItem must not be null");
        return new DataMapItem(dataItem);
    }

    public DataMap getDataMap() {
        return this.zzb;
    }

    public Uri getUri() {
        return this.zza;
    }
}
