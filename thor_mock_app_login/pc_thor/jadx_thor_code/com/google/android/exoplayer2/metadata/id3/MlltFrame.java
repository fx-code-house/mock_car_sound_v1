package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class MlltFrame extends Id3Frame {
    public static final Parcelable.Creator<MlltFrame> CREATOR = new Parcelable.Creator<MlltFrame>() { // from class: com.google.android.exoplayer2.metadata.id3.MlltFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MlltFrame createFromParcel(Parcel in) {
            return new MlltFrame(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MlltFrame[] newArray(int size) {
            return new MlltFrame[size];
        }
    };
    public static final String ID = "MLLT";
    public final int bytesBetweenReference;
    public final int[] bytesDeviations;
    public final int millisecondsBetweenReference;
    public final int[] millisecondsDeviations;
    public final int mpegFramesBetweenReference;

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Frame, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MlltFrame(int mpegFramesBetweenReference, int bytesBetweenReference, int millisecondsBetweenReference, int[] bytesDeviations, int[] millisecondsDeviations) {
        super(ID);
        this.mpegFramesBetweenReference = mpegFramesBetweenReference;
        this.bytesBetweenReference = bytesBetweenReference;
        this.millisecondsBetweenReference = millisecondsBetweenReference;
        this.bytesDeviations = bytesDeviations;
        this.millisecondsDeviations = millisecondsDeviations;
    }

    MlltFrame(Parcel in) {
        super(ID);
        this.mpegFramesBetweenReference = in.readInt();
        this.bytesBetweenReference = in.readInt();
        this.millisecondsBetweenReference = in.readInt();
        this.bytesDeviations = (int[]) Util.castNonNull(in.createIntArray());
        this.millisecondsDeviations = (int[]) Util.castNonNull(in.createIntArray());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MlltFrame mlltFrame = (MlltFrame) obj;
        return this.mpegFramesBetweenReference == mlltFrame.mpegFramesBetweenReference && this.bytesBetweenReference == mlltFrame.bytesBetweenReference && this.millisecondsBetweenReference == mlltFrame.millisecondsBetweenReference && Arrays.equals(this.bytesDeviations, mlltFrame.bytesDeviations) && Arrays.equals(this.millisecondsDeviations, mlltFrame.millisecondsDeviations);
    }

    public int hashCode() {
        return ((((((((527 + this.mpegFramesBetweenReference) * 31) + this.bytesBetweenReference) * 31) + this.millisecondsBetweenReference) * 31) + Arrays.hashCode(this.bytesDeviations)) * 31) + Arrays.hashCode(this.millisecondsDeviations);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mpegFramesBetweenReference);
        dest.writeInt(this.bytesBetweenReference);
        dest.writeInt(this.millisecondsBetweenReference);
        dest.writeIntArray(this.bytesDeviations);
        dest.writeIntArray(this.millisecondsDeviations);
    }
}
