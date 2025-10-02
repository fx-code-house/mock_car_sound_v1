package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class InternalFrame extends Id3Frame {
    public static final Parcelable.Creator<InternalFrame> CREATOR = new Parcelable.Creator<InternalFrame>() { // from class: com.google.android.exoplayer2.metadata.id3.InternalFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InternalFrame createFromParcel(Parcel in) {
            return new InternalFrame(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InternalFrame[] newArray(int size) {
            return new InternalFrame[size];
        }
    };
    public static final String ID = "----";
    public final String description;
    public final String domain;
    public final String text;

    public InternalFrame(String domain, String description, String text) {
        super("----");
        this.domain = domain;
        this.description = description;
        this.text = text;
    }

    InternalFrame(Parcel in) {
        super("----");
        this.domain = (String) Util.castNonNull(in.readString());
        this.description = (String) Util.castNonNull(in.readString());
        this.text = (String) Util.castNonNull(in.readString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InternalFrame internalFrame = (InternalFrame) obj;
        return Util.areEqual(this.description, internalFrame.description) && Util.areEqual(this.domain, internalFrame.domain) && Util.areEqual(this.text, internalFrame.text);
    }

    public int hashCode() {
        String str = this.domain;
        int iHashCode = (527 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.description;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.text;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Frame
    public String toString() {
        String str = this.id;
        String str2 = this.domain;
        String str3 = this.description;
        return new StringBuilder(String.valueOf(str).length() + 23 + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append(": domain=").append(str2).append(", description=").append(str3).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.domain);
        dest.writeString(this.text);
    }
}
