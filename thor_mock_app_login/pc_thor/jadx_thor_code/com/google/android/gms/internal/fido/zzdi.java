package com.google.android.gms.internal.fido;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzdi extends zzdr {
    private final zzaz zza;
    private final int zzb;

    zzdi(zzaz zzazVar) throws zzdh {
        zzazVar.getClass();
        this.zza = zzazVar;
        int i = 0;
        int i2 = 0;
        while (true) {
            zzaz zzazVar2 = this.zza;
            if (i >= zzazVar2.size()) {
                break;
            }
            int iZzb = ((zzdr) zzazVar2.get(i)).zzb();
            if (i2 < iZzb) {
                i2 = iZzb;
            }
            i++;
        }
        int i3 = i2 + 1;
        this.zzb = i3;
        if (i3 > 4) {
            throw new zzdh("Exceeded cutoff limit for max depth of cbor value");
        }
    }

    @Override // java.lang.Comparable
    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        zzdr zzdrVar = (zzdr) obj;
        if (zzd(Byte.MIN_VALUE) != zzdrVar.zza()) {
            return zzd(Byte.MIN_VALUE) - zzdrVar.zza();
        }
        zzdi zzdiVar = (zzdi) zzdrVar;
        zzaz zzazVar = this.zza;
        int size = zzazVar.size();
        zzaz zzazVar2 = zzdiVar.zza;
        if (size != zzazVar2.size()) {
            return zzazVar.size() - zzazVar2.size();
        }
        int i = 0;
        while (true) {
            zzaz zzazVar3 = this.zza;
            if (i >= zzazVar3.size()) {
                return 0;
            }
            int iCompareTo = ((zzdr) zzazVar3.get(i)).compareTo((zzdr) zzdiVar.zza.get(i));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            i++;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return this.zza.equals(((zzdi) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(zzd(Byte.MIN_VALUE)), this.zza});
    }

    public final String toString() {
        if (this.zza.isEmpty()) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        ArrayList arrayList = new ArrayList();
        zzaz zzazVar = this.zza;
        int size = zzazVar.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(((zzdr) zzazVar.get(i)).toString().replace(StringUtils.LF, "\n  "));
        }
        zzag zzagVarZza = zzag.zza(",\n  ");
        StringBuilder sb = new StringBuilder("[\n  ");
        try {
            zzagVarZza.zzb(sb, arrayList.iterator());
            sb.append("\n]");
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.google.android.gms.internal.fido.zzdr
    protected final int zza() {
        return zzd(Byte.MIN_VALUE);
    }

    @Override // com.google.android.gms.internal.fido.zzdr
    protected final int zzb() {
        return this.zzb;
    }
}
