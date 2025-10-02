package com.thor.networkmodule.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChangeCarInfo.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b#\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 -2\u00020\u0001:\u0001-Bq\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010'\u001a\u00020\u0005HÖ\u0001J\u0019\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0014\"\u0004\b\u001a\u0010\u0016R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0010\"\u0004\b\u001c\u0010\u0012R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0014\"\u0004\b\u001e\u0010\u0016R\u001a\u0010\f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0010\"\u0004\b \u0010\u0012R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0014\"\u0004\b\"\u0010\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0014\"\u0004\b$\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0010\"\u0004\b&\u0010\u0012¨\u0006."}, d2 = {"Lcom/thor/networkmodule/model/ChangeCarInfo;", "Landroid/os/Parcelable;", "token", "", "userId", "", "carMarkId", "carMarkName", "carModelId", "carModelName", "carGenerationId", "carGenerationName", "carSeriesId", "carSeriesName", "(Ljava/lang/String;IILjava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;)V", "getCarGenerationId", "()I", "setCarGenerationId", "(I)V", "getCarGenerationName", "()Ljava/lang/String;", "setCarGenerationName", "(Ljava/lang/String;)V", "getCarMarkId", "setCarMarkId", "getCarMarkName", "setCarMarkName", "getCarModelId", "setCarModelId", "getCarModelName", "setCarModelName", "getCarSeriesId", "setCarSeriesId", "getCarSeriesName", "setCarSeriesName", "getToken", "setToken", "getUserId", "setUserId", "describeContents", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ChangeCarInfo implements Parcelable {
    public static final String BUNDLE_NAME;
    private int carGenerationId;
    private String carGenerationName;
    private int carMarkId;
    private String carMarkName;
    private int carModelId;
    private String carModelName;
    private int carSeriesId;
    private String carSeriesName;
    private String token;
    private int userId;
    public static final Parcelable.Creator<ChangeCarInfo> CREATOR = new Creator();

    /* compiled from: ChangeCarInfo.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<ChangeCarInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ChangeCarInfo createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ChangeCarInfo(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ChangeCarInfo[] newArray(int i) {
            return new ChangeCarInfo[i];
        }
    }

    public ChangeCarInfo() {
        this(null, 0, 0, null, 0, null, 0, null, 0, null, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeString(this.token);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.carMarkId);
        parcel.writeString(this.carMarkName);
        parcel.writeInt(this.carModelId);
        parcel.writeString(this.carModelName);
        parcel.writeInt(this.carGenerationId);
        parcel.writeString(this.carGenerationName);
        parcel.writeInt(this.carSeriesId);
        parcel.writeString(this.carSeriesName);
    }

    public ChangeCarInfo(String token, int i, int i2, String str, int i3, String str2, int i4, String str3, int i5, String str4) {
        Intrinsics.checkNotNullParameter(token, "token");
        this.token = token;
        this.userId = i;
        this.carMarkId = i2;
        this.carMarkName = str;
        this.carModelId = i3;
        this.carModelName = str2;
        this.carGenerationId = i4;
        this.carGenerationName = str3;
        this.carSeriesId = i5;
        this.carSeriesName = str4;
    }

    public /* synthetic */ ChangeCarInfo(String str, int i, int i2, String str2, int i3, String str3, int i4, String str4, int i5, String str5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? "" : str, (i6 & 2) != 0 ? 0 : i, (i6 & 4) != 0 ? 0 : i2, (i6 & 8) != 0 ? null : str2, (i6 & 16) != 0 ? 0 : i3, (i6 & 32) != 0 ? null : str3, (i6 & 64) != 0 ? 0 : i4, (i6 & 128) != 0 ? null : str4, (i6 & 256) == 0 ? i5 : 0, (i6 & 512) == 0 ? str5 : null);
    }

    public final String getToken() {
        return this.token;
    }

    public final void setToken(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.token = str;
    }

    public final int getUserId() {
        return this.userId;
    }

    public final void setUserId(int i) {
        this.userId = i;
    }

    public final int getCarMarkId() {
        return this.carMarkId;
    }

    public final void setCarMarkId(int i) {
        this.carMarkId = i;
    }

    public final String getCarMarkName() {
        return this.carMarkName;
    }

    public final void setCarMarkName(String str) {
        this.carMarkName = str;
    }

    public final int getCarModelId() {
        return this.carModelId;
    }

    public final void setCarModelId(int i) {
        this.carModelId = i;
    }

    public final String getCarModelName() {
        return this.carModelName;
    }

    public final void setCarModelName(String str) {
        this.carModelName = str;
    }

    public final int getCarGenerationId() {
        return this.carGenerationId;
    }

    public final void setCarGenerationId(int i) {
        this.carGenerationId = i;
    }

    public final String getCarGenerationName() {
        return this.carGenerationName;
    }

    public final void setCarGenerationName(String str) {
        this.carGenerationName = str;
    }

    public final int getCarSeriesId() {
        return this.carSeriesId;
    }

    public final void setCarSeriesId(int i) {
        this.carSeriesId = i;
    }

    public final String getCarSeriesName() {
        return this.carSeriesName;
    }

    public final void setCarSeriesName(String str) {
        this.carSeriesName = str;
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ChangeCarInfo", "ChangeCarInfo::class.java.simpleName");
        BUNDLE_NAME = "ChangeCarInfo";
    }
}
