package com.google.android.exoplayer2.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class RepeatModeUtil {
    public static final int REPEAT_TOGGLE_MODE_ALL = 2;
    public static final int REPEAT_TOGGLE_MODE_NONE = 0;
    public static final int REPEAT_TOGGLE_MODE_ONE = 1;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatToggleModes {
    }

    public static boolean isRepeatModeEnabled(int repeatMode, int enabledModes) {
        if (repeatMode != 0) {
            return repeatMode != 1 ? repeatMode == 2 && (enabledModes & 2) != 0 : (enabledModes & 1) != 0;
        }
        return true;
    }

    private RepeatModeUtil() {
    }

    public static int getNextRepeatMode(int currentMode, int enabledModes) {
        for (int i = 1; i <= 2; i++) {
            int i2 = (currentMode + i) % 3;
            if (isRepeatModeEnabled(i2, enabledModes)) {
                return i2;
            }
        }
        return currentMode;
    }
}
