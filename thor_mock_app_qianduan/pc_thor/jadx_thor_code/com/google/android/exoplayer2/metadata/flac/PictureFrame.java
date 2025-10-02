package com.google.android.exoplayer2.metadata.flac;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class PictureFrame implements Metadata.Entry {
    public static final Parcelable.Creator<PictureFrame> CREATOR = new Parcelable.Creator<PictureFrame>() { // from class: com.google.android.exoplayer2.metadata.flac.PictureFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureFrame createFromParcel(Parcel in) {
            return new PictureFrame(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureFrame[] newArray(int size) {
            return new PictureFrame[size];
        }
    };
    public final int colors;
    public final int depth;
    public final String description;
    public final int height;
    public final String mimeType;
    public final byte[] pictureData;
    public final int pictureType;
    public final int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PictureFrame(int pictureType, String mimeType, String description, int width, int height, int depth, int colors, byte[] pictureData) {
        this.pictureType = pictureType;
        this.mimeType = mimeType;
        this.description = description;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.colors = colors;
        this.pictureData = pictureData;
    }

    PictureFrame(Parcel in) {
        this.pictureType = in.readInt();
        this.mimeType = (String) Util.castNonNull(in.readString());
        this.description = (String) Util.castNonNull(in.readString());
        this.width = in.readInt();
        this.height = in.readInt();
        this.depth = in.readInt();
        this.colors = in.readInt();
        this.pictureData = (byte[]) Util.castNonNull(in.createByteArray());
    }

    @Override // com.google.android.exoplayer2.metadata.Metadata.Entry
    public void populateMediaMetadata(MediaMetadata.Builder builder) {
        builder.maybeSetArtworkData(this.pictureData, this.pictureType);
    }

    public String toString() {
        String str = this.mimeType;
        String str2 = this.description;
        return new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(str2).length()).append("Picture: mimeType=").append(str).append(", description=").append(str2).toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PictureFrame pictureFrame = (PictureFrame) obj;
        return this.pictureType == pictureFrame.pictureType && this.mimeType.equals(pictureFrame.mimeType) && this.description.equals(pictureFrame.description) && this.width == pictureFrame.width && this.height == pictureFrame.height && this.depth == pictureFrame.depth && this.colors == pictureFrame.colors && Arrays.equals(this.pictureData, pictureFrame.pictureData);
    }

    public int hashCode() {
        return ((((((((((((((527 + this.pictureType) * 31) + this.mimeType.hashCode()) * 31) + this.description.hashCode()) * 31) + this.width) * 31) + this.height) * 31) + this.depth) * 31) + this.colors) * 31) + Arrays.hashCode(this.pictureData);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pictureType);
        dest.writeString(this.mimeType);
        dest.writeString(this.description);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.depth);
        dest.writeInt(this.colors);
        dest.writeByteArray(this.pictureData);
    }
}
