package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.ArrayUtils;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class LabelValueRow extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LabelValueRow> CREATOR = new zze();

    @Deprecated
    String zzeu;

    @Deprecated
    String zzev;
    ArrayList<LabelValue> zzew;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        @Deprecated
        public final Builder setHexFontColor(String str) {
            LabelValueRow.this.zzeu = str;
            return this;
        }

        @Deprecated
        public final Builder setHexBackgroundColor(String str) {
            LabelValueRow.this.zzev = str;
            return this;
        }

        public final Builder addColumns(Collection<LabelValue> collection) {
            LabelValueRow.this.zzew.addAll(collection);
            return this;
        }

        public final Builder addColumn(LabelValue labelValue) {
            LabelValueRow.this.zzew.add(labelValue);
            return this;
        }

        public final LabelValueRow build() {
            return LabelValueRow.this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzeu, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzev, false);
        SafeParcelWriter.writeTypedList(parcel, 4, this.zzew, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    LabelValueRow(String str, String str2, ArrayList<LabelValue> arrayList) {
        this.zzeu = str;
        this.zzev = str2;
        this.zzew = arrayList;
    }

    LabelValueRow() {
        this.zzew = ArrayUtils.newArrayList();
    }

    @Deprecated
    public final String getHexFontColor() {
        return this.zzeu;
    }

    @Deprecated
    public final String getHexBackgroundColor() {
        return this.zzev;
    }

    public final ArrayList<LabelValue> getColumns() {
        return this.zzew;
    }
}
