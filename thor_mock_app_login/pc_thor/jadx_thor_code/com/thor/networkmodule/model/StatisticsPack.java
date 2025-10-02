package com.thor.networkmodule.model;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StatisticsPack.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\n\n\u0000\n\u0002\u0010\t\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001b\u001a\u00020\bHÆ\u0003J1\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001e\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006#"}, d2 = {"Lcom/thor/networkmodule/model/StatisticsPack;", "", "groupIdHex", "", "paramIdHex", "value", "", "time", "", "(Ljava/lang/String;Ljava/lang/String;SJ)V", "getGroupIdHex", "()Ljava/lang/String;", "setGroupIdHex", "(Ljava/lang/String;)V", "getParamIdHex", "setParamIdHex", "getTime", "()J", "setTime", "(J)V", "getValue", "()S", "setValue", "(S)V", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class StatisticsPack {

    @SerializedName("id_group")
    private String groupIdHex;

    @SerializedName("id_param")
    private String paramIdHex;

    @SerializedName("time")
    private long time;

    @SerializedName("value")
    private short value;

    public static /* synthetic */ StatisticsPack copy$default(StatisticsPack statisticsPack, String str, String str2, short s, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = statisticsPack.groupIdHex;
        }
        if ((i & 2) != 0) {
            str2 = statisticsPack.paramIdHex;
        }
        String str3 = str2;
        if ((i & 4) != 0) {
            s = statisticsPack.value;
        }
        short s2 = s;
        if ((i & 8) != 0) {
            j = statisticsPack.time;
        }
        return statisticsPack.copy(str, str3, s2, j);
    }

    /* renamed from: component1, reason: from getter */
    public final String getGroupIdHex() {
        return this.groupIdHex;
    }

    /* renamed from: component2, reason: from getter */
    public final String getParamIdHex() {
        return this.paramIdHex;
    }

    /* renamed from: component3, reason: from getter */
    public final short getValue() {
        return this.value;
    }

    /* renamed from: component4, reason: from getter */
    public final long getTime() {
        return this.time;
    }

    public final StatisticsPack copy(String groupIdHex, String paramIdHex, short value, long time) {
        Intrinsics.checkNotNullParameter(groupIdHex, "groupIdHex");
        Intrinsics.checkNotNullParameter(paramIdHex, "paramIdHex");
        return new StatisticsPack(groupIdHex, paramIdHex, value, time);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StatisticsPack)) {
            return false;
        }
        StatisticsPack statisticsPack = (StatisticsPack) other;
        return Intrinsics.areEqual(this.groupIdHex, statisticsPack.groupIdHex) && Intrinsics.areEqual(this.paramIdHex, statisticsPack.paramIdHex) && this.value == statisticsPack.value && this.time == statisticsPack.time;
    }

    public int hashCode() {
        return (((((this.groupIdHex.hashCode() * 31) + this.paramIdHex.hashCode()) * 31) + Short.hashCode(this.value)) * 31) + Long.hashCode(this.time);
    }

    public String toString() {
        return "StatisticsPack(groupIdHex=" + this.groupIdHex + ", paramIdHex=" + this.paramIdHex + ", value=" + ((int) this.value) + ", time=" + this.time + ")";
    }

    public StatisticsPack(String groupIdHex, String paramIdHex, short s, long j) {
        Intrinsics.checkNotNullParameter(groupIdHex, "groupIdHex");
        Intrinsics.checkNotNullParameter(paramIdHex, "paramIdHex");
        this.groupIdHex = groupIdHex;
        this.paramIdHex = paramIdHex;
        this.value = s;
        this.time = j;
    }

    public final String getGroupIdHex() {
        return this.groupIdHex;
    }

    public final void setGroupIdHex(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.groupIdHex = str;
    }

    public final String getParamIdHex() {
        return this.paramIdHex;
    }

    public final void setParamIdHex(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paramIdHex = str;
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
