package com.google.android.exoplayer2.device;

@Deprecated
/* loaded from: classes.dex */
public interface DeviceListener {
    default void onDeviceInfoChanged(DeviceInfo deviceInfo) {
    }

    default void onDeviceVolumeChanged(int volume, boolean muted) {
    }
}
