package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class LabelValue extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LabelValue> CREATOR = new zzc();
    private String label;
    private String value;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.label, false);
        SafeParcelWriter.writeString(parcel, 3, this.value, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public LabelValue(String str, String str2) {
        this.label = str;
        this.value = str2;
    }

    LabelValue() {
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getValue() {
        return this.value;
    }
}
