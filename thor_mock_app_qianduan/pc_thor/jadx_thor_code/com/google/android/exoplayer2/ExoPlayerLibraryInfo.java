package com.google.android.exoplayer2;

import android.os.Build;
import java.util.HashSet;

/* loaded from: classes.dex */
public final class ExoPlayerLibraryInfo {
    public static final boolean ASSERTIONS_ENABLED = true;

    @Deprecated
    public static final String DEFAULT_USER_AGENT;
    public static final boolean GL_ASSERTIONS_ENABLED = false;
    public static final String TAG = "ExoPlayer";
    public static final boolean TRACE_ENABLED = true;
    public static final String VERSION = "2.15.0";
    public static final int VERSION_INT = 2015000;
    public static final String VERSION_SLASHY = "ExoPlayerLib/2.15.0";
    private static final HashSet<String> registeredModules;
    private static String registeredModulesString;

    static {
        String str = Build.VERSION.RELEASE;
        DEFAULT_USER_AGENT = new StringBuilder(String.valueOf(str).length() + 57).append("ExoPlayerLib/2.15.0 (Linux; Android ").append(str).append(") ExoPlayerLib/2.15.0").toString();
        registeredModules = new HashSet<>();
        registeredModulesString = "goog.exo.core";
    }

    private ExoPlayerLibraryInfo() {
    }

    public static synchronized String registeredModules() {
        return registeredModulesString;
    }

    public static synchronized void registerModule(String name) {
        if (registeredModules.add(name)) {
            String str = registeredModulesString;
            registeredModulesString = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(name).length()).append(str).append(", ").append(name).toString();
        }
    }
}
