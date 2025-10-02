package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-fido@@20.1.0 */
/* loaded from: classes2.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();
    private final boolean zza;
    private final byte[] zzb;

    public zzh(boolean z, byte[] bArr) {
        this.zza = z;
        this.zzb = bArr;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzhVar = (zzh) obj;
        return this.zza == zzhVar.zza && Arrays.equals(this.zzb, zzhVar.zzb);
    }

    public final int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.zza), this.zzb);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.zza);
        SafeParcelWriter.writeByteArray(parcel, 2, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final JSONObject zza() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("enabled", this.zza);
            JSONObject jSONObject2 = new JSONObject();
            byte[] bArr = this.zzb;
            if (bArr != null) {
                jSONObject2.put("first", Base64.encodeToString(Arrays.copyOfRange(bArr, 0, 31), 11));
                byte[] bArr2 = this.zzb;
                if (bArr2.length == 64) {
                    jSONObject2.put("second", Base64.encodeToString(Arrays.copyOfRange(bArr2, 32, 64), 11));
                }
            }
            jSONObject.put("results", jSONObject2);
            return jSONObject;
        } catch (JSONException e) {
            throw new RuntimeException("Error encoding AuthenticationExtensionsPrfOutputs to JSON object", e);
        }
    }
}
