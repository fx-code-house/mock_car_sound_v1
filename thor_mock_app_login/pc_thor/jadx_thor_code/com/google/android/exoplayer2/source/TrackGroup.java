package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class TrackGroup implements Parcelable {
    public static final Parcelable.Creator<TrackGroup> CREATOR = new Parcelable.Creator<TrackGroup>() { // from class: com.google.android.exoplayer2.source.TrackGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackGroup createFromParcel(Parcel in) {
            return new TrackGroup(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TrackGroup[] newArray(int size) {
            return new TrackGroup[size];
        }
    };
    private static final String TAG = "TrackGroup";
    private final Format[] formats;
    private int hashCode;
    public final int length;

    private static int normalizeRoleFlags(int roleFlags) {
        return roleFlags | 16384;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TrackGroup(Format... formats) {
        Assertions.checkState(formats.length > 0);
        this.formats = formats;
        this.length = formats.length;
        verifyCorrectness();
    }

    TrackGroup(Parcel in) {
        int i = in.readInt();
        this.length = i;
        this.formats = new Format[i];
        for (int i2 = 0; i2 < this.length; i2++) {
            this.formats[i2] = (Format) in.readParcelable(Format.class.getClassLoader());
        }
    }

    public Format getFormat(int index) {
        return this.formats[index];
    }

    public int indexOf(Format format) {
        int i = 0;
        while (true) {
            Format[] formatArr = this.formats;
            if (i >= formatArr.length) {
                return -1;
            }
            if (format == formatArr[i]) {
                return i;
            }
            i++;
        }
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 527 + Arrays.hashCode(this.formats);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackGroup trackGroup = (TrackGroup) obj;
        return this.length == trackGroup.length && Arrays.equals(this.formats, trackGroup.formats);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.length);
        for (int i = 0; i < this.length; i++) {
            dest.writeParcelable(this.formats[i], 0);
        }
    }

    private void verifyCorrectness() {
        String strNormalizeLanguage = normalizeLanguage(this.formats[0].language);
        int iNormalizeRoleFlags = normalizeRoleFlags(this.formats[0].roleFlags);
        int i = 1;
        while (true) {
            Format[] formatArr = this.formats;
            if (i >= formatArr.length) {
                return;
            }
            if (!strNormalizeLanguage.equals(normalizeLanguage(formatArr[i].language))) {
                logErrorMessage("languages", this.formats[0].language, this.formats[i].language, i);
                return;
            } else {
                if (iNormalizeRoleFlags != normalizeRoleFlags(this.formats[i].roleFlags)) {
                    logErrorMessage("role flags", Integer.toBinaryString(this.formats[0].roleFlags), Integer.toBinaryString(this.formats[i].roleFlags), i);
                    return;
                }
                i++;
            }
        }
    }

    private static String normalizeLanguage(String language) {
        return (language == null || language.equals(C.LANGUAGE_UNDETERMINED)) ? "" : language;
    }

    private static void logErrorMessage(String mismatchField, String valueIndex0, String otherValue, int otherIndex) {
        Log.e(TAG, "", new IllegalStateException(new StringBuilder(String.valueOf(mismatchField).length() + 78 + String.valueOf(valueIndex0).length() + String.valueOf(otherValue).length()).append("Different ").append(mismatchField).append(" combined in one TrackGroup: '").append(valueIndex0).append("' (track 0) and '").append(otherValue).append("' (track ").append(otherIndex).append(")").toString()));
    }
}
