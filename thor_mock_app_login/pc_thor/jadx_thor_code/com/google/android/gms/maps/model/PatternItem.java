package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class PatternItem extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PatternItem> CREATOR = new zzi();
    private static final String TAG = "PatternItem";
    private final int type;
    private final Float zzdv;

    public PatternItem(int i, Float f) {
        boolean z = true;
        if (i != 1 && (f == null || f.floatValue() < 0.0f)) {
            z = false;
        }
        String strValueOf = String.valueOf(f);
        Preconditions.checkArgument(z, new StringBuilder(String.valueOf(strValueOf).length() + 45).append("Invalid PatternItem: type=").append(i).append(" length=").append(strValueOf).toString());
        this.type = i;
        this.zzdv = f;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.type);
        SafeParcelWriter.writeFloatObject(parcel, 3, this.zzdv, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.type), this.zzdv);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatternItem)) {
            return false;
        }
        PatternItem patternItem = (PatternItem) obj;
        return this.type == patternItem.type && Objects.equal(this.zzdv, patternItem.zzdv);
    }

    public String toString() {
        int i = this.type;
        String strValueOf = String.valueOf(this.zzdv);
        return new StringBuilder(String.valueOf(strValueOf).length() + 39).append("[PatternItem: type=").append(i).append(" length=").append(strValueOf).append("]").toString();
    }

    static List<PatternItem> zza(List<PatternItem> list) {
        PatternItem dash;
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (PatternItem dot : list) {
            if (dot == null) {
                dot = null;
            } else {
                int i = dot.type;
                if (i == 0) {
                    dash = new Dash(dot.zzdv.floatValue());
                } else if (i == 1) {
                    dot = new Dot();
                } else if (i == 2) {
                    dash = new Gap(dot.zzdv.floatValue());
                } else {
                    Log.w(TAG, new StringBuilder(37).append("Unknown PatternItem type: ").append(i).toString());
                }
                dot = dash;
            }
            arrayList.add(dot);
        }
        return arrayList;
    }
}
