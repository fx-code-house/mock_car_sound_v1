package com.google.android.exoplayer2.offline;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class StreamKey implements Comparable<StreamKey>, Parcelable {
    public static final Parcelable.Creator<StreamKey> CREATOR = new Parcelable.Creator<StreamKey>() { // from class: com.google.android.exoplayer2.offline.StreamKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamKey createFromParcel(Parcel in) {
            return new StreamKey(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamKey[] newArray(int size) {
            return new StreamKey[size];
        }
    };
    public final int groupIndex;
    public final int periodIndex;
    public final int trackIndex;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StreamKey(int groupIndex, int trackIndex) {
        this(0, groupIndex, trackIndex);
    }

    public StreamKey(int periodIndex, int groupIndex, int trackIndex) {
        this.periodIndex = periodIndex;
        this.groupIndex = groupIndex;
        this.trackIndex = trackIndex;
    }

    StreamKey(Parcel in) {
        this.periodIndex = in.readInt();
        this.groupIndex = in.readInt();
        this.trackIndex = in.readInt();
    }

    public String toString() {
        int i = this.periodIndex;
        int i2 = this.groupIndex;
        return new StringBuilder(35).append(i).append(".").append(i2).append(".").append(this.trackIndex).toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StreamKey streamKey = (StreamKey) o;
        return this.periodIndex == streamKey.periodIndex && this.groupIndex == streamKey.groupIndex && this.trackIndex == streamKey.trackIndex;
    }

    public int hashCode() {
        return (((this.periodIndex * 31) + this.groupIndex) * 31) + this.trackIndex;
    }

    @Override // java.lang.Comparable
    public int compareTo(StreamKey o) {
        int i = this.periodIndex - o.periodIndex;
        if (i != 0) {
            return i;
        }
        int i2 = this.groupIndex - o.groupIndex;
        return i2 == 0 ? this.trackIndex - o.trackIndex : i2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.periodIndex);
        dest.writeInt(this.groupIndex);
        dest.writeInt(this.trackIndex);
    }
}
