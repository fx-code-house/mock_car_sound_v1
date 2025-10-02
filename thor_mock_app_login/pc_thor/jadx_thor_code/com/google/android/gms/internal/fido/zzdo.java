package com.google.android.gms.internal.fido;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzdo extends zzdr {
    private final int zza;
    private final zzbg zzb;

    zzdo(zzbg zzbgVar) throws zzdh {
        zzbgVar.getClass();
        this.zzb = zzbgVar;
        zzcb it = zzbgVar.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            int iZzb = ((zzdr) entry.getKey()).zzb();
            i = i < iZzb ? iZzb : i;
            int iZzb2 = ((zzdr) entry.getValue()).zzb();
            if (i < iZzb2) {
                i = iZzb2;
            }
        }
        int i2 = i + 1;
        this.zza = i2;
        if (i2 > 4) {
            throw new zzdh("Exceeded cutoff limit for max depth of cbor value");
        }
    }

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        int iCompareTo;
        int size;
        int size2;
        zzdr zzdrVar = (zzdr) obj;
        if (zzd((byte) -96) != zzdrVar.zza()) {
            size2 = zzdrVar.zza();
            size = zzd((byte) -96);
        } else {
            zzdo zzdoVar = (zzdo) zzdrVar;
            if (this.zzb.size() == zzdoVar.zzb.size()) {
                zzcb it = this.zzb.entrySet().iterator();
                zzcb it2 = zzdoVar.zzb.entrySet().iterator();
                do {
                    if (!it.hasNext() && !it2.hasNext()) {
                        return 0;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    Map.Entry entry2 = (Map.Entry) it2.next();
                    int iCompareTo2 = ((zzdr) entry.getKey()).compareTo((zzdr) entry2.getKey());
                    if (iCompareTo2 != 0) {
                        return iCompareTo2;
                    }
                    iCompareTo = ((zzdr) entry.getValue()).compareTo((zzdr) entry2.getValue());
                } while (iCompareTo == 0);
                return iCompareTo;
            }
            size = this.zzb.size();
            size2 = zzdoVar.zzb.size();
        }
        return size - size2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return this.zzb.equals(((zzdo) obj).zzb);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(zzd((byte) -96)), this.zzb});
    }

    public final String toString() {
        if (this.zzb.isEmpty()) {
            return "{}";
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        zzcb it = this.zzb.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(((zzdr) entry.getKey()).toString().replace(StringUtils.LF, "\n  "), ((zzdr) entry.getValue()).toString().replace(StringUtils.LF, "\n  "));
        }
        zzag zzagVarZza = zzag.zza(",\n  ");
        StringBuilder sb = new StringBuilder("{\n  ");
        try {
            zzaf.zza(sb, linkedHashMap.entrySet().iterator(), zzagVarZza, " : ");
            sb.append("\n}");
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.google.android.gms.internal.fido.zzdr
    protected final int zza() {
        return zzd((byte) -96);
    }

    @Override // com.google.android.gms.internal.fido.zzdr
    protected final int zzb() {
        return this.zza;
    }

    public final zzbg zzc() {
        return this.zzb;
    }
}
