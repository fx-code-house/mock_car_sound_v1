package com.google.android.exoplayer2.video;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes.dex */
public final class ColorInfo implements Parcelable {
    public static final Parcelable.Creator<ColorInfo> CREATOR = new Parcelable.Creator<ColorInfo>() { // from class: com.google.android.exoplayer2.video.ColorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ColorInfo createFromParcel(Parcel in) {
            return new ColorInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ColorInfo[] newArray(int size) {
            return new ColorInfo[size];
        }
    };
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    private int hashCode;
    public final byte[] hdrStaticInfo;

    @Pure
    public static int isoColorPrimariesToColorSpace(int isoColorPrimaries) {
        if (isoColorPrimaries == 1) {
            return 1;
        }
        if (isoColorPrimaries != 9) {
            return (isoColorPrimaries == 4 || isoColorPrimaries == 5 || isoColorPrimaries == 6 || isoColorPrimaries == 7) ? 2 : -1;
        }
        return 6;
    }

    @Pure
    public static int isoTransferCharacteristicsToColorTransfer(int isoTransferCharacteristics) {
        if (isoTransferCharacteristics == 1) {
            return 3;
        }
        if (isoTransferCharacteristics == 16) {
            return 6;
        }
        if (isoTransferCharacteristics != 18) {
            return (isoTransferCharacteristics == 6 || isoTransferCharacteristics == 7) ? 3 : -1;
        }
        return 7;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ColorInfo(int colorSpace, int colorRange, int colorTransfer, byte[] hdrStaticInfo) {
        this.colorSpace = colorSpace;
        this.colorRange = colorRange;
        this.colorTransfer = colorTransfer;
        this.hdrStaticInfo = hdrStaticInfo;
    }

    ColorInfo(Parcel in) {
        this.colorSpace = in.readInt();
        this.colorRange = in.readInt();
        this.colorTransfer = in.readInt();
        this.hdrStaticInfo = Util.readBoolean(in) ? in.createByteArray() : null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColorInfo colorInfo = (ColorInfo) obj;
        return this.colorSpace == colorInfo.colorSpace && this.colorRange == colorInfo.colorRange && this.colorTransfer == colorInfo.colorTransfer && Arrays.equals(this.hdrStaticInfo, colorInfo.hdrStaticInfo);
    }

    public String toString() {
        int i = this.colorSpace;
        int i2 = this.colorRange;
        return new StringBuilder(55).append("ColorInfo(").append(i).append(", ").append(i2).append(", ").append(this.colorTransfer).append(", ").append(this.hdrStaticInfo != null).append(")").toString();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((((527 + this.colorSpace) * 31) + this.colorRange) * 31) + this.colorTransfer) * 31) + Arrays.hashCode(this.hdrStaticInfo);
        }
        return this.hashCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.colorSpace);
        dest.writeInt(this.colorRange);
        dest.writeInt(this.colorTransfer);
        Util.writeBoolean(dest, this.hdrStaticInfo != null);
        byte[] bArr = this.hdrStaticInfo;
        if (bArr != null) {
            dest.writeByteArray(bArr);
        }
    }
}
