package com.thor.networkmodule.model.responses.versions;

import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CurrentVersionApp.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/CurrentVersionApp;", "", AbstractSpiCall.ANDROID_CLIENT_TYPE, "", "huawei", "ios", "software", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAndroid", "()Ljava/lang/String;", "getHuawei", "getIos", "getSoftware", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CurrentVersionApp {
    private final String android;
    private final String huawei;
    private final String ios;
    private final String software;

    public static /* synthetic */ CurrentVersionApp copy$default(CurrentVersionApp currentVersionApp, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = currentVersionApp.android;
        }
        if ((i & 2) != 0) {
            str2 = currentVersionApp.huawei;
        }
        if ((i & 4) != 0) {
            str3 = currentVersionApp.ios;
        }
        if ((i & 8) != 0) {
            str4 = currentVersionApp.software;
        }
        return currentVersionApp.copy(str, str2, str3, str4);
    }

    /* renamed from: component1, reason: from getter */
    public final String getAndroid() {
        return this.android;
    }

    /* renamed from: component2, reason: from getter */
    public final String getHuawei() {
        return this.huawei;
    }

    /* renamed from: component3, reason: from getter */
    public final String getIos() {
        return this.ios;
    }

    /* renamed from: component4, reason: from getter */
    public final String getSoftware() {
        return this.software;
    }

    public final CurrentVersionApp copy(String android2, String huawei, String ios, String software) {
        Intrinsics.checkNotNullParameter(android2, "android");
        Intrinsics.checkNotNullParameter(huawei, "huawei");
        Intrinsics.checkNotNullParameter(ios, "ios");
        Intrinsics.checkNotNullParameter(software, "software");
        return new CurrentVersionApp(android2, huawei, ios, software);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CurrentVersionApp)) {
            return false;
        }
        CurrentVersionApp currentVersionApp = (CurrentVersionApp) other;
        return Intrinsics.areEqual(this.android, currentVersionApp.android) && Intrinsics.areEqual(this.huawei, currentVersionApp.huawei) && Intrinsics.areEqual(this.ios, currentVersionApp.ios) && Intrinsics.areEqual(this.software, currentVersionApp.software);
    }

    public int hashCode() {
        return (((((this.android.hashCode() * 31) + this.huawei.hashCode()) * 31) + this.ios.hashCode()) * 31) + this.software.hashCode();
    }

    public String toString() {
        return "CurrentVersionApp(android=" + this.android + ", huawei=" + this.huawei + ", ios=" + this.ios + ", software=" + this.software + ")";
    }

    public CurrentVersionApp(String android2, String huawei, String ios, String software) {
        Intrinsics.checkNotNullParameter(android2, "android");
        Intrinsics.checkNotNullParameter(huawei, "huawei");
        Intrinsics.checkNotNullParameter(ios, "ios");
        Intrinsics.checkNotNullParameter(software, "software");
        this.android = android2;
        this.huawei = huawei;
        this.ios = ios;
        this.software = software;
    }

    public final String getAndroid() {
        return this.android;
    }

    public final String getHuawei() {
        return this.huawei;
    }

    public final String getIos() {
        return this.ios;
    }

    public final String getSoftware() {
        return this.software;
    }
}
