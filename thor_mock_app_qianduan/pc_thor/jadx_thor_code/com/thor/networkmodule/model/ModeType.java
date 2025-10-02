package com.thor.networkmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.gson.annotations.SerializedName;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ModeType.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 \"2\u00020\u0001:\u0002\"#B'\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0005HÆ\u0003J+\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u0005HÖ\u0001J\u0019\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0003HÖ\u0001R \u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR \u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006$"}, d2 = {"Lcom/thor/networkmodule/model/ModeType;", "Landroid/os/Parcelable;", SessionDescription.ATTR_TYPE, "", "image", "", "autoImage", "(ILjava/lang/String;Ljava/lang/String;)V", "getAutoImage", "()Ljava/lang/String;", "setAutoImage", "(Ljava/lang/String;)V", "getImage", "setImage", "getType", "()I", "setType", "(I)V", "component1", "component2", "component3", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "Type", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ModeType implements Parcelable {
    private static final String BUNDLE_NAME;
    public static final int MODE_TYPE_CITY = 1;
    public static final int MODE_TYPE_OWN = 3;
    public static final int MODE_TYPE_SPORT = 2;

    @SerializedName("android_auto_image")
    private String autoImage;

    @SerializedName("image")
    private String image;

    @SerializedName("mode_type")
    private int type;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final Parcelable.Creator<ModeType> CREATOR = new Creator();

    /* compiled from: ModeType.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ModeType> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ModeType createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ModeType(parcel.readInt(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ModeType[] newArray(int i) {
            return new ModeType[i];
        }
    }

    /* compiled from: ModeType.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lcom/thor/networkmodule/model/ModeType$Type;", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface Type {
    }

    public ModeType() {
        this(0, null, null, 7, null);
    }

    public static /* synthetic */ ModeType copy$default(ModeType modeType, int i, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = modeType.type;
        }
        if ((i2 & 2) != 0) {
            str = modeType.image;
        }
        if ((i2 & 4) != 0) {
            str2 = modeType.autoImage;
        }
        return modeType.copy(i, str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* renamed from: component2, reason: from getter */
    public final String getImage() {
        return this.image;
    }

    /* renamed from: component3, reason: from getter */
    public final String getAutoImage() {
        return this.autoImage;
    }

    public final ModeType copy(int type, String image, String autoImage) {
        return new ModeType(type, image, autoImage);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModeType)) {
            return false;
        }
        ModeType modeType = (ModeType) other;
        return this.type == modeType.type && Intrinsics.areEqual(this.image, modeType.image) && Intrinsics.areEqual(this.autoImage, modeType.autoImage);
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.type) * 31;
        String str = this.image;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.autoImage;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "ModeType(type=" + this.type + ", image=" + this.image + ", autoImage=" + this.autoImage + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.type);
        parcel.writeString(this.image);
        parcel.writeString(this.autoImage);
    }

    public ModeType(int i, String str, String str2) {
        this.type = i;
        this.image = str;
        this.autoImage = str2;
    }

    public /* synthetic */ ModeType(int i, String str, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2);
    }

    public final int getType() {
        return this.type;
    }

    public final void setType(int i) {
        this.type = i;
    }

    public final String getImage() {
        return this.image;
    }

    public final void setImage(String str) {
        this.image = str;
    }

    public final String getAutoImage() {
        return this.autoImage;
    }

    public final void setAutoImage(String str) {
        this.autoImage = str;
    }

    /* compiled from: ModeType.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/thor/networkmodule/model/ModeType$Companion;", "", "()V", "BUNDLE_NAME", "", "getBUNDLE_NAME", "()Ljava/lang/String;", "MODE_TYPE_CITY", "", "MODE_TYPE_OWN", "MODE_TYPE_SPORT", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getBUNDLE_NAME() {
            return ModeType.BUNDLE_NAME;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ModeType", "ModeType::class.java.simpleName");
        BUNDLE_NAME = "ModeType";
    }
}
