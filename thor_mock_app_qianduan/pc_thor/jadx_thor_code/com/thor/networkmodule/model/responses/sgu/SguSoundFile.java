package com.thor.networkmodule.model.responses.sgu;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundFile.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0005HÖ\u0001J\u0019\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u001d"}, d2 = {"Lcom/thor/networkmodule/model/responses/sgu/SguSoundFile;", "Landroid/os/Parcelable;", TtmlNode.ATTR_ID, "", "file", "", "version", "(ILjava/lang/String;I)V", "getFile", "()Ljava/lang/String;", "getId", "()I", "getVersion", "component1", "component2", "component3", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguSoundFile implements Parcelable {
    public static final Parcelable.Creator<SguSoundFile> CREATOR = new Creator();

    @SerializedName("file")
    private final String file;

    @SerializedName(TtmlNode.ATTR_ID)
    private final int id;

    @SerializedName("version")
    private final int version;

    /* compiled from: SguSoundFile.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SguSoundFile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundFile createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new SguSoundFile(parcel.readInt(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SguSoundFile[] newArray(int i) {
            return new SguSoundFile[i];
        }
    }

    public static /* synthetic */ SguSoundFile copy$default(SguSoundFile sguSoundFile, int i, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = sguSoundFile.id;
        }
        if ((i3 & 2) != 0) {
            str = sguSoundFile.file;
        }
        if ((i3 & 4) != 0) {
            i2 = sguSoundFile.version;
        }
        return sguSoundFile.copy(i, str, i2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String getFile() {
        return this.file;
    }

    /* renamed from: component3, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final SguSoundFile copy(int id, String file, int version) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new SguSoundFile(id, file, version);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguSoundFile)) {
            return false;
        }
        SguSoundFile sguSoundFile = (SguSoundFile) other;
        return this.id == sguSoundFile.id && Intrinsics.areEqual(this.file, sguSoundFile.file) && this.version == sguSoundFile.version;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.id) * 31) + this.file.hashCode()) * 31) + Integer.hashCode(this.version);
    }

    public String toString() {
        return "SguSoundFile(id=" + this.id + ", file=" + this.file + ", version=" + this.version + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.id);
        parcel.writeString(this.file);
        parcel.writeInt(this.version);
    }

    public SguSoundFile(int i, String file, int i2) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.id = i;
        this.file = file;
        this.version = i2;
    }

    public final int getId() {
        return this.id;
    }

    public final String getFile() {
        return this.file;
    }

    public final int getVersion() {
        return this.version;
    }
}
