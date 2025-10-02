package com.google.android.exoplayer2.metadata;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class Metadata implements Parcelable {
    public static final Parcelable.Creator<Metadata> CREATOR = new Parcelable.Creator<Metadata>() { // from class: com.google.android.exoplayer2.metadata.Metadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Metadata createFromParcel(Parcel in) {
            return new Metadata(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Metadata[] newArray(int size) {
            return new Metadata[size];
        }
    };
    private final Entry[] entries;

    public interface Entry extends Parcelable {
        default byte[] getWrappedMetadataBytes() {
            return null;
        }

        default Format getWrappedMetadataFormat() {
            return null;
        }

        default void populateMediaMetadata(MediaMetadata.Builder builder) {
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Metadata(Entry... entries) {
        this.entries = entries;
    }

    public Metadata(List<? extends Entry> entries) {
        this.entries = (Entry[]) entries.toArray(new Entry[0]);
    }

    Metadata(Parcel in) {
        this.entries = new Entry[in.readInt()];
        int i = 0;
        while (true) {
            Entry[] entryArr = this.entries;
            if (i >= entryArr.length) {
                return;
            }
            entryArr[i] = (Entry) in.readParcelable(Entry.class.getClassLoader());
            i++;
        }
    }

    public int length() {
        return this.entries.length;
    }

    public Entry get(int index) {
        return this.entries[index];
    }

    public Metadata copyWithAppendedEntriesFrom(Metadata other) {
        return other == null ? this : copyWithAppendedEntries(other.entries);
    }

    public Metadata copyWithAppendedEntries(Entry... entriesToAppend) {
        return entriesToAppend.length == 0 ? this : new Metadata((Entry[]) Util.nullSafeArrayConcatenation(this.entries, entriesToAppend));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.entries, ((Metadata) obj).entries);
    }

    public int hashCode() {
        return Arrays.hashCode(this.entries);
    }

    public String toString() {
        String strValueOf = String.valueOf(Arrays.toString(this.entries));
        return strValueOf.length() != 0 ? "entries=".concat(strValueOf) : new String("entries=");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.entries.length);
        for (Entry entry : this.entries) {
            dest.writeParcelable(entry, 0);
        }
    }
}
