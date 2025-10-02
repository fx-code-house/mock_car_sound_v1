package com.thor.networkmodule.model.responses.versions;

import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Versions.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/Versions;", "", AbstractSpiCall.ANDROID_CLIENT_TYPE, "", "ios", "software", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAndroid", "()Ljava/lang/String;", "getIos", "getSoftware", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class Versions {
    private final String android;
    private final String ios;
    private final String software;

    public static /* synthetic */ Versions copy$default(Versions versions, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = versions.android;
        }
        if ((i & 2) != 0) {
            str2 = versions.ios;
        }
        if ((i & 4) != 0) {
            str3 = versions.software;
        }
        return versions.copy(str, str2, str3);
    }

    /* renamed from: component1, reason: from getter */
    public final String getAndroid() {
        return this.android;
    }

    /* renamed from: component2, reason: from getter */
    public final String getIos() {
        return this.ios;
    }

    /* renamed from: component3, reason: from getter */
    public final String getSoftware() {
        return this.software;
    }

    public final Versions copy(String android2, String ios, String software) {
        Intrinsics.checkNotNullParameter(android2, "android");
        Intrinsics.checkNotNullParameter(ios, "ios");
        Intrinsics.checkNotNullParameter(software, "software");
        return new Versions(android2, ios, software);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Versions)) {
            return false;
        }
        Versions versions = (Versions) other;
        return Intrinsics.areEqual(this.android, versions.android) && Intrinsics.areEqual(this.ios, versions.ios) && Intrinsics.areEqual(this.software, versions.software);
    }

    public int hashCode() {
        return (((this.android.hashCode() * 31) + this.ios.hashCode()) * 31) + this.software.hashCode();
    }

    public String toString() {
        return "Versions(android=" + this.android + ", ios=" + this.ios + ", software=" + this.software + ")";
    }

    public Versions(String android2, String ios, String software) {
        Intrinsics.checkNotNullParameter(android2, "android");
        Intrinsics.checkNotNullParameter(ios, "ios");
        Intrinsics.checkNotNullParameter(software, "software");
        this.android = android2;
        this.ios = ios;
        this.software = software;
    }

    public final String getAndroid() {
        return this.android;
    }

    public final String getIos() {
        return this.ios;
    }

    public final String getSoftware() {
        return this.software;
    }
}
