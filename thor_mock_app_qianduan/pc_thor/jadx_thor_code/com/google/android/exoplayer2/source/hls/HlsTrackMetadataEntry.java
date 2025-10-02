package com.google.android.exoplayer2.source.hls;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.Metadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsTrackMetadataEntry implements Metadata.Entry {
    public static final Parcelable.Creator<HlsTrackMetadataEntry> CREATOR = new Parcelable.Creator<HlsTrackMetadataEntry>() { // from class: com.google.android.exoplayer2.source.hls.HlsTrackMetadataEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HlsTrackMetadataEntry createFromParcel(Parcel in) {
            return new HlsTrackMetadataEntry(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HlsTrackMetadataEntry[] newArray(int size) {
            return new HlsTrackMetadataEntry[size];
        }
    };
    public final String groupId;
    public final String name;
    public final List<VariantInfo> variantInfos;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class VariantInfo implements Parcelable {
        public static final Parcelable.Creator<VariantInfo> CREATOR = new Parcelable.Creator<VariantInfo>() { // from class: com.google.android.exoplayer2.source.hls.HlsTrackMetadataEntry.VariantInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VariantInfo createFromParcel(Parcel in) {
                return new VariantInfo(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public VariantInfo[] newArray(int size) {
                return new VariantInfo[size];
            }
        };
        public final String audioGroupId;
        public final int averageBitrate;
        public final String captionGroupId;
        public final int peakBitrate;
        public final String subtitleGroupId;
        public final String videoGroupId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public VariantInfo(int averageBitrate, int peakBitrate, String videoGroupId, String audioGroupId, String subtitleGroupId, String captionGroupId) {
            this.averageBitrate = averageBitrate;
            this.peakBitrate = peakBitrate;
            this.videoGroupId = videoGroupId;
            this.audioGroupId = audioGroupId;
            this.subtitleGroupId = subtitleGroupId;
            this.captionGroupId = captionGroupId;
        }

        VariantInfo(Parcel in) {
            this.averageBitrate = in.readInt();
            this.peakBitrate = in.readInt();
            this.videoGroupId = in.readString();
            this.audioGroupId = in.readString();
            this.subtitleGroupId = in.readString();
            this.captionGroupId = in.readString();
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            VariantInfo variantInfo = (VariantInfo) other;
            return this.averageBitrate == variantInfo.averageBitrate && this.peakBitrate == variantInfo.peakBitrate && TextUtils.equals(this.videoGroupId, variantInfo.videoGroupId) && TextUtils.equals(this.audioGroupId, variantInfo.audioGroupId) && TextUtils.equals(this.subtitleGroupId, variantInfo.subtitleGroupId) && TextUtils.equals(this.captionGroupId, variantInfo.captionGroupId);
        }

        public int hashCode() {
            int i = ((this.averageBitrate * 31) + this.peakBitrate) * 31;
            String str = this.videoGroupId;
            int iHashCode = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.audioGroupId;
            int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.subtitleGroupId;
            int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
            String str4 = this.captionGroupId;
            return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.averageBitrate);
            dest.writeInt(this.peakBitrate);
            dest.writeString(this.videoGroupId);
            dest.writeString(this.audioGroupId);
            dest.writeString(this.subtitleGroupId);
            dest.writeString(this.captionGroupId);
        }
    }

    public HlsTrackMetadataEntry(String groupId, String name, List<VariantInfo> variantInfos) {
        this.groupId = groupId;
        this.name = name;
        this.variantInfos = Collections.unmodifiableList(new ArrayList(variantInfos));
    }

    HlsTrackMetadataEntry(Parcel in) {
        this.groupId = in.readString();
        this.name = in.readString();
        int i = in.readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add((VariantInfo) in.readParcelable(VariantInfo.class.getClassLoader()));
        }
        this.variantInfos = Collections.unmodifiableList(arrayList);
    }

    public String toString() {
        String string;
        String str = this.groupId;
        if (str != null) {
            String str2 = this.name;
            string = new StringBuilder(String.valueOf(str).length() + 5 + String.valueOf(str2).length()).append(" [").append(str).append(", ").append(str2).append("]").toString();
        } else {
            string = "";
        }
        String strValueOf = String.valueOf(string);
        return strValueOf.length() != 0 ? "HlsTrackMetadataEntry".concat(strValueOf) : new String("HlsTrackMetadataEntry");
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        HlsTrackMetadataEntry hlsTrackMetadataEntry = (HlsTrackMetadataEntry) other;
        return TextUtils.equals(this.groupId, hlsTrackMetadataEntry.groupId) && TextUtils.equals(this.name, hlsTrackMetadataEntry.name) && this.variantInfos.equals(hlsTrackMetadataEntry.variantInfos);
    }

    public int hashCode() {
        String str = this.groupId;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.name;
        return ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + this.variantInfos.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupId);
        dest.writeString(this.name);
        int size = this.variantInfos.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            dest.writeParcelable(this.variantInfos.get(i), 0);
        }
    }
}
