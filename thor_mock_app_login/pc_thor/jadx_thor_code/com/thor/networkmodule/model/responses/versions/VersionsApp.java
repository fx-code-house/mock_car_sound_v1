package com.thor.networkmodule.model.responses.versions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VersionsApp.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lcom/thor/networkmodule/model/responses/versions/VersionsApp;", "", "current", "Lcom/thor/networkmodule/model/responses/versions/CurrentVersionApp;", "minimum", "Lcom/thor/networkmodule/model/responses/versions/MinimumVersionApp;", "devices", "Lcom/thor/networkmodule/model/responses/versions/Devices;", "(Lcom/thor/networkmodule/model/responses/versions/CurrentVersionApp;Lcom/thor/networkmodule/model/responses/versions/MinimumVersionApp;Lcom/thor/networkmodule/model/responses/versions/Devices;)V", "getCurrent", "()Lcom/thor/networkmodule/model/responses/versions/CurrentVersionApp;", "getDevices", "()Lcom/thor/networkmodule/model/responses/versions/Devices;", "getMinimum", "()Lcom/thor/networkmodule/model/responses/versions/MinimumVersionApp;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class VersionsApp {
    private final CurrentVersionApp current;
    private final Devices devices;
    private final MinimumVersionApp minimum;

    public static /* synthetic */ VersionsApp copy$default(VersionsApp versionsApp, CurrentVersionApp currentVersionApp, MinimumVersionApp minimumVersionApp, Devices devices, int i, Object obj) {
        if ((i & 1) != 0) {
            currentVersionApp = versionsApp.current;
        }
        if ((i & 2) != 0) {
            minimumVersionApp = versionsApp.minimum;
        }
        if ((i & 4) != 0) {
            devices = versionsApp.devices;
        }
        return versionsApp.copy(currentVersionApp, minimumVersionApp, devices);
    }

    /* renamed from: component1, reason: from getter */
    public final CurrentVersionApp getCurrent() {
        return this.current;
    }

    /* renamed from: component2, reason: from getter */
    public final MinimumVersionApp getMinimum() {
        return this.minimum;
    }

    /* renamed from: component3, reason: from getter */
    public final Devices getDevices() {
        return this.devices;
    }

    public final VersionsApp copy(CurrentVersionApp current, MinimumVersionApp minimum, Devices devices) {
        Intrinsics.checkNotNullParameter(current, "current");
        Intrinsics.checkNotNullParameter(minimum, "minimum");
        Intrinsics.checkNotNullParameter(devices, "devices");
        return new VersionsApp(current, minimum, devices);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VersionsApp)) {
            return false;
        }
        VersionsApp versionsApp = (VersionsApp) other;
        return Intrinsics.areEqual(this.current, versionsApp.current) && Intrinsics.areEqual(this.minimum, versionsApp.minimum) && Intrinsics.areEqual(this.devices, versionsApp.devices);
    }

    public int hashCode() {
        return (((this.current.hashCode() * 31) + this.minimum.hashCode()) * 31) + this.devices.hashCode();
    }

    public String toString() {
        return "VersionsApp(current=" + this.current + ", minimum=" + this.minimum + ", devices=" + this.devices + ")";
    }

    public VersionsApp(CurrentVersionApp current, MinimumVersionApp minimum, Devices devices) {
        Intrinsics.checkNotNullParameter(current, "current");
        Intrinsics.checkNotNullParameter(minimum, "minimum");
        Intrinsics.checkNotNullParameter(devices, "devices");
        this.current = current;
        this.minimum = minimum;
        this.devices = devices;
    }

    public final CurrentVersionApp getCurrent() {
        return this.current;
    }

    public final MinimumVersionApp getMinimum() {
        return this.minimum;
    }

    public final Devices getDevices() {
        return this.devices;
    }
}
