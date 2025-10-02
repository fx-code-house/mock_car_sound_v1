package com.thor.app.bus.events.shop.main;

import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DownloadSettingsFileSuccessEvent.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/thor/app/bus/events/shop/main/DownloadSettingsFileSuccessEvent;", "", "isSuccess", "", "listPreset", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "(ZLcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;)V", "()Z", "getListPreset", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class DownloadSettingsFileSuccessEvent {
    private final boolean isSuccess;
    private final InstalledPresets listPreset;

    public static /* synthetic */ DownloadSettingsFileSuccessEvent copy$default(DownloadSettingsFileSuccessEvent downloadSettingsFileSuccessEvent, boolean z, InstalledPresets installedPresets, int i, Object obj) {
        if ((i & 1) != 0) {
            z = downloadSettingsFileSuccessEvent.isSuccess;
        }
        if ((i & 2) != 0) {
            installedPresets = downloadSettingsFileSuccessEvent.listPreset;
        }
        return downloadSettingsFileSuccessEvent.copy(z, installedPresets);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsSuccess() {
        return this.isSuccess;
    }

    /* renamed from: component2, reason: from getter */
    public final InstalledPresets getListPreset() {
        return this.listPreset;
    }

    public final DownloadSettingsFileSuccessEvent copy(boolean isSuccess, InstalledPresets listPreset) {
        Intrinsics.checkNotNullParameter(listPreset, "listPreset");
        return new DownloadSettingsFileSuccessEvent(isSuccess, listPreset);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DownloadSettingsFileSuccessEvent)) {
            return false;
        }
        DownloadSettingsFileSuccessEvent downloadSettingsFileSuccessEvent = (DownloadSettingsFileSuccessEvent) other;
        return this.isSuccess == downloadSettingsFileSuccessEvent.isSuccess && Intrinsics.areEqual(this.listPreset, downloadSettingsFileSuccessEvent.listPreset);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.isSuccess;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return (r0 * 31) + this.listPreset.hashCode();
    }

    public String toString() {
        return "DownloadSettingsFileSuccessEvent(isSuccess=" + this.isSuccess + ", listPreset=" + this.listPreset + ")";
    }

    public DownloadSettingsFileSuccessEvent(boolean z, InstalledPresets listPreset) {
        Intrinsics.checkNotNullParameter(listPreset, "listPreset");
        this.isSuccess = z;
        this.listPreset = listPreset;
    }

    public final boolean isSuccess() {
        return this.isSuccess;
    }

    public final InstalledPresets getListPreset() {
        return this.listPreset;
    }
}
