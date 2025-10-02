package com.google.android.exoplayer2.metadata.mp4;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.common.primitives.Longs;

/* loaded from: classes.dex */
public final class MotionPhotoMetadata implements Metadata.Entry {
    public static final Parcelable.Creator<MotionPhotoMetadata> CREATOR = new Parcelable.Creator<MotionPhotoMetadata>() { // from class: com.google.android.exoplayer2.metadata.mp4.MotionPhotoMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MotionPhotoMetadata createFromParcel(Parcel in) {
            return new MotionPhotoMetadata(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MotionPhotoMetadata[] newArray(int size) {
            return new MotionPhotoMetadata[size];
        }
    };
    public final long photoPresentationTimestampUs;
    public final long photoSize;
    public final long photoStartPosition;
    public final long videoSize;
    public final long videoStartPosition;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MotionPhotoMetadata(long photoStartPosition, long photoSize, long photoPresentationTimestampUs, long videoStartPosition, long videoSize) {
        this.photoStartPosition = photoStartPosition;
        this.photoSize = photoSize;
        this.photoPresentationTimestampUs = photoPresentationTimestampUs;
        this.videoStartPosition = videoStartPosition;
        this.videoSize = videoSize;
    }

    private MotionPhotoMetadata(Parcel in) {
        this.photoStartPosition = in.readLong();
        this.photoSize = in.readLong();
        this.photoPresentationTimestampUs = in.readLong();
        this.videoStartPosition = in.readLong();
        this.videoSize = in.readLong();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MotionPhotoMetadata motionPhotoMetadata = (MotionPhotoMetadata) obj;
        return this.photoStartPosition == motionPhotoMetadata.photoStartPosition && this.photoSize == motionPhotoMetadata.photoSize && this.photoPresentationTimestampUs == motionPhotoMetadata.photoPresentationTimestampUs && this.videoStartPosition == motionPhotoMetadata.videoStartPosition && this.videoSize == motionPhotoMetadata.videoSize;
    }

    public int hashCode() {
        return ((((((((527 + Longs.hashCode(this.photoStartPosition)) * 31) + Longs.hashCode(this.photoSize)) * 31) + Longs.hashCode(this.photoPresentationTimestampUs)) * 31) + Longs.hashCode(this.videoStartPosition)) * 31) + Longs.hashCode(this.videoSize);
    }

    public String toString() {
        long j = this.photoStartPosition;
        long j2 = this.photoSize;
        long j3 = this.photoPresentationTimestampUs;
        long j4 = this.videoStartPosition;
        return new StringBuilder(218).append("Motion photo metadata: photoStartPosition=").append(j).append(", photoSize=").append(j2).append(", photoPresentationTimestampUs=").append(j3).append(", videoStartPosition=").append(j4).append(", videoSize=").append(this.videoSize).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.photoStartPosition);
        dest.writeLong(this.photoSize);
        dest.writeLong(this.photoPresentationTimestampUs);
        dest.writeLong(this.videoStartPosition);
        dest.writeLong(this.videoSize);
    }
}
