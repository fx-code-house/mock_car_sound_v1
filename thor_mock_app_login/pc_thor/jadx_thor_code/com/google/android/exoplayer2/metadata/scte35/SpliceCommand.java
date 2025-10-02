package com.google.android.exoplayer2.metadata.scte35;

import com.google.android.exoplayer2.metadata.Metadata;

/* loaded from: classes.dex */
public abstract class SpliceCommand implements Metadata.Entry {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        String strValueOf = String.valueOf(getClass().getSimpleName());
        return strValueOf.length() != 0 ? "SCTE-35 splice command: type=".concat(strValueOf) : new String("SCTE-35 splice command: type=");
    }
}
