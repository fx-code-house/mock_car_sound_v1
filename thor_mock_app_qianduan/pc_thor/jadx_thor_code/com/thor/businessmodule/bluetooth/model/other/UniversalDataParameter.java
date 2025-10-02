package com.thor.businessmodule.bluetooth.model.other;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UniversalDataParameter.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fHÖ\u0003J\t\u0010 \u001a\u00020\u001bHÖ\u0001J\t\u0010!\u001a\u00020\"HÖ\u0001J\u0019\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001bHÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\n\"\u0004\b\u0014\u0010\f¨\u0006("}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/UniversalDataParameter;", "Landroid/os/Parcelable;", "groupId", "", "paramId", "value", "time", "", "(SSSJ)V", "getGroupId", "()S", "setGroupId", "(S)V", "getParamId", "setParamId", "getTime", "()J", "setTime", "(J)V", "getValue", "setValue", "component1", "component2", "component3", "component4", "copy", "describeContents", "", "equals", "", "other", "", "hashCode", "toString", "", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class UniversalDataParameter implements Parcelable {
    public static final Parcelable.Creator<UniversalDataParameter> CREATOR = new Creator();

    @SerializedName("id_group")
    private short groupId;

    @SerializedName("id_param")
    private short paramId;

    @SerializedName("time")
    private long time;

    @SerializedName("value")
    private short value;

    /* compiled from: UniversalDataParameter.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class Creator implements Parcelable.Creator<UniversalDataParameter> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final UniversalDataParameter createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new UniversalDataParameter((short) parcel.readInt(), (short) parcel.readInt(), (short) parcel.readInt(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final UniversalDataParameter[] newArray(int i) {
            return new UniversalDataParameter[i];
        }
    }

    public static /* synthetic */ UniversalDataParameter copy$default(UniversalDataParameter universalDataParameter, short s, short s2, short s3, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            s = universalDataParameter.groupId;
        }
        if ((i & 2) != 0) {
            s2 = universalDataParameter.paramId;
        }
        short s4 = s2;
        if ((i & 4) != 0) {
            s3 = universalDataParameter.value;
        }
        short s5 = s3;
        if ((i & 8) != 0) {
            j = universalDataParameter.time;
        }
        return universalDataParameter.copy(s, s4, s5, j);
    }

    /* renamed from: component1, reason: from getter */
    public final short getGroupId() {
        return this.groupId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getParamId() {
        return this.paramId;
    }

    /* renamed from: component3, reason: from getter */
    public final short getValue() {
        return this.value;
    }

    /* renamed from: component4, reason: from getter */
    public final long getTime() {
        return this.time;
    }

    public final UniversalDataParameter copy(short groupId, short paramId, short value, long time) {
        return new UniversalDataParameter(groupId, paramId, value, time);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UniversalDataParameter)) {
            return false;
        }
        UniversalDataParameter universalDataParameter = (UniversalDataParameter) other;
        return this.groupId == universalDataParameter.groupId && this.paramId == universalDataParameter.paramId && this.value == universalDataParameter.value && this.time == universalDataParameter.time;
    }

    public int hashCode() {
        return (((((Short.hashCode(this.groupId) * 31) + Short.hashCode(this.paramId)) * 31) + Short.hashCode(this.value)) * 31) + Long.hashCode(this.time);
    }

    public String toString() {
        return "UniversalDataParameter(groupId=" + ((int) this.groupId) + ", paramId=" + ((int) this.paramId) + ", value=" + ((int) this.value) + ", time=" + this.time + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "out");
        parcel.writeInt(this.groupId);
        parcel.writeInt(this.paramId);
        parcel.writeInt(this.value);
        parcel.writeLong(this.time);
    }

    public UniversalDataParameter(short s, short s2, short s3, long j) {
        this.groupId = s;
        this.paramId = s2;
        this.value = s3;
        this.time = j;
    }

    public final short getGroupId() {
        return this.groupId;
    }

    public final void setGroupId(short s) {
        this.groupId = s;
    }

    public final short getParamId() {
        return this.paramId;
    }

    public final void setParamId(short s) {
        this.paramId = s;
    }

    public final short getValue() {
        return this.value;
    }

    public final void setValue(short s) {
        this.value = s;
    }

    public final long getTime() {
        return this.time;
    }

    public final void setTime(long j) {
        this.time = j;
    }
}
