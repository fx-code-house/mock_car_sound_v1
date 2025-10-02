package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.clearcut.zzha;
import com.google.android.gms.internal.clearcut.zzr;
import com.google.android.gms.phenotype.ExperimentTokens;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class zze extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zze> CREATOR = new zzf();
    public final zzha zzaa;
    public zzr zzag;
    public byte[] zzah;
    private int[] zzai;
    private String[] zzaj;
    private int[] zzak;
    private byte[][] zzal;
    private ExperimentTokens[] zzam;
    public final ClearcutLogger.zzb zzan;
    public final ClearcutLogger.zzb zzt;
    private boolean zzz;

    public zze(zzr zzrVar, zzha zzhaVar, ClearcutLogger.zzb zzbVar, ClearcutLogger.zzb zzbVar2, int[] iArr, String[] strArr, int[] iArr2, byte[][] bArr, ExperimentTokens[] experimentTokensArr, boolean z) {
        this.zzag = zzrVar;
        this.zzaa = zzhaVar;
        this.zzt = zzbVar;
        this.zzan = null;
        this.zzai = iArr;
        this.zzaj = null;
        this.zzak = iArr2;
        this.zzal = null;
        this.zzam = null;
        this.zzz = z;
    }

    zze(zzr zzrVar, byte[] bArr, int[] iArr, String[] strArr, int[] iArr2, byte[][] bArr2, boolean z, ExperimentTokens[] experimentTokensArr) {
        this.zzag = zzrVar;
        this.zzah = bArr;
        this.zzai = iArr;
        this.zzaj = strArr;
        this.zzaa = null;
        this.zzt = null;
        this.zzan = null;
        this.zzak = iArr2;
        this.zzal = bArr2;
        this.zzam = experimentTokensArr;
        this.zzz = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zze) {
            zze zzeVar = (zze) obj;
            if (Objects.equal(this.zzag, zzeVar.zzag) && Arrays.equals(this.zzah, zzeVar.zzah) && Arrays.equals(this.zzai, zzeVar.zzai) && Arrays.equals(this.zzaj, zzeVar.zzaj) && Objects.equal(this.zzaa, zzeVar.zzaa) && Objects.equal(this.zzt, zzeVar.zzt) && Objects.equal(this.zzan, zzeVar.zzan) && Arrays.equals(this.zzak, zzeVar.zzak) && Arrays.deepEquals(this.zzal, zzeVar.zzal) && Arrays.equals(this.zzam, zzeVar.zzam) && this.zzz == zzeVar.zzz) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzag, this.zzah, this.zzai, this.zzaj, this.zzaa, this.zzt, this.zzan, this.zzak, this.zzal, this.zzam, Boolean.valueOf(this.zzz));
    }

    public final String toString() {
        return "LogEventParcelable[" + this.zzag + ", LogEventBytes: " + (this.zzah == null ? null : new String(this.zzah)) + ", TestCodes: " + Arrays.toString(this.zzai) + ", MendelPackages: " + Arrays.toString(this.zzaj) + ", LogEvent: " + this.zzaa + ", ExtensionProducer: " + this.zzt + ", VeProducer: " + this.zzan + ", ExperimentIDs: " + Arrays.toString(this.zzak) + ", ExperimentTokens: " + Arrays.toString(this.zzal) + ", ExperimentTokensParcelables: " + Arrays.toString(this.zzam) + ", AddPhenotypeExperimentTokens: " + this.zzz + "]";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzag, i, false);
        SafeParcelWriter.writeByteArray(parcel, 3, this.zzah, false);
        SafeParcelWriter.writeIntArray(parcel, 4, this.zzai, false);
        SafeParcelWriter.writeStringArray(parcel, 5, this.zzaj, false);
        SafeParcelWriter.writeIntArray(parcel, 6, this.zzak, false);
        SafeParcelWriter.writeByteArrayArray(parcel, 7, this.zzal, false);
        SafeParcelWriter.writeBoolean(parcel, 8, this.zzz);
        SafeParcelWriter.writeTypedArray(parcel, 9, this.zzam, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
