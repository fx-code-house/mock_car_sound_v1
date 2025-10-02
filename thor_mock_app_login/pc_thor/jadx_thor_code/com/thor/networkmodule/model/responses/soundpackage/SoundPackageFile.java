package com.thor.networkmodule.model.responses.soundpackage;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SoundPackageFile.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \"2\u00020\u0001:\u0001\"B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J)\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001J\u0019\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0003HÖ\u0001R \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000f¨\u0006#"}, d2 = {"Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageFile;", "Landroid/os/Parcelable;", SessionDescription.ATTR_TYPE, "", "file", "", "version", "(ILjava/lang/String;I)V", "getFile", "()Ljava/lang/String;", "setFile", "(Ljava/lang/String;)V", "getType", "()I", "setType", "(I)V", "getVersion", "setVersion", "component1", "component2", "component3", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPackageFile implements Parcelable {
    private static final String BUNDLE_NAME;

    @SerializedName("file")
    private String file;

    @SerializedName(SessionDescription.ATTR_TYPE)
    private int type;

    @SerializedName("ver")
    private int version;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Parcelable.Creator<SoundPackageFile> CREATOR = new Creator();

    /* compiled from: SoundPackageFile.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<SoundPackageFile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SoundPackageFile createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new SoundPackageFile(parcel.readInt(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final SoundPackageFile[] newArray(int i) {
            return new SoundPackageFile[i];
        }
    }

    public SoundPackageFile() {
        this(0, null, 0, 7, null);
    }

    public static /* synthetic */ SoundPackageFile copy$default(SoundPackageFile soundPackageFile, int i, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = soundPackageFile.type;
        }
        if ((i3 & 2) != 0) {
            str = soundPackageFile.file;
        }
        if ((i3 & 4) != 0) {
            i2 = soundPackageFile.version;
        }
        return soundPackageFile.copy(i, str, i2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component2, reason: from getter */
    public final String getFile() {
        return this.file;
    }

    /* renamed from: component3, reason: from getter */
    public final int getVersion() {
        return this.version;
    }

    public final SoundPackageFile copy(int type, String file, int version) {
        return new SoundPackageFile(type, file, version);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPackageFile)) {
            return false;
        }
        SoundPackageFile soundPackageFile = (SoundPackageFile) other;
        return this.type == soundPackageFile.type && Intrinsics.areEqual(this.file, soundPackageFile.file) && this.version == soundPackageFile.version;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.type) * 31;
        String str = this.file;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Integer.hashCode(this.version);
    }

    public String toString() {
        return "SoundPackageFile(type=" + this.type + ", file=" + this.file + ", version=" + this.version + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.type);
        parcel.writeString(this.file);
        parcel.writeInt(this.version);
    }

    public SoundPackageFile(int i, String str, int i2) {
        this.type = i;
        this.file = str;
        this.version = i2;
    }

    public /* synthetic */ SoundPackageFile(int i, String str, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? null : str, (i3 & 4) != 0 ? 0 : i2);
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final String getFile() {
        return this.file;
    }

    public final void setFile(String str) {
        this.file = str;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int i) {
        this.version = i;
    }

    /* compiled from: SoundPackageFile.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageFile$Companion;", "", "()V", "BUNDLE_NAME", "", "getBUNDLE_NAME", "()Ljava/lang/String;", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getBUNDLE_NAME() {
            return SoundPackageFile.BUNDLE_NAME;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("SoundPackageFile", "SoundPackageFile::class.java.simpleName");
        BUNDLE_NAME = "SoundPackageFile";
    }
}
