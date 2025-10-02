package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class CommentFrame extends Id3Frame {
    public static final Parcelable.Creator<CommentFrame> CREATOR = new Parcelable.Creator<CommentFrame>() { // from class: com.google.android.exoplayer2.metadata.id3.CommentFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommentFrame createFromParcel(Parcel in) {
            return new CommentFrame(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommentFrame[] newArray(int size) {
            return new CommentFrame[size];
        }
    };
    public static final String ID = "COMM";
    public final String description;
    public final String language;
    public final String text;

    public CommentFrame(String language, String description, String text) {
        super(ID);
        this.language = language;
        this.description = description;
        this.text = text;
    }

    CommentFrame(Parcel in) {
        super(ID);
        this.language = (String) Util.castNonNull(in.readString());
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
        CommentFrame commentFrame = (CommentFrame) obj;
        return Util.areEqual(this.description, commentFrame.description) && Util.areEqual(this.language, commentFrame.language) && Util.areEqual(this.text, commentFrame.text);
    }

    public int hashCode() {
        String str = this.language;
        int iHashCode = (527 + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.description;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.text;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Frame
    public String toString() {
        String str = this.id;
        String str2 = this.language;
        String str3 = this.description;
        return new StringBuilder(String.valueOf(str).length() + 25 + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append(": language=").append(str2).append(", description=").append(str3).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.language);
        dest.writeString(this.text);
    }
}
