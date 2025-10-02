package com.google.android.exoplayer2.ui;

import android.graphics.Color;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class HtmlUtils {
    private HtmlUtils() {
    }

    public static String toCssRgba(int color) {
        return Util.formatInvariant("rgba(%d,%d,%d,%.3f)", Integer.valueOf(Color.red(color)), Integer.valueOf(Color.green(color)), Integer.valueOf(Color.blue(color)), Double.valueOf(Color.alpha(color) / 255.0d));
    }

    public static String cssAllClassDescendantsSelector(String className) {
        return new StringBuilder(String.valueOf(className).length() + 5 + String.valueOf(className).length()).append(".").append(className).append(",.").append(className).append(" *").toString();
    }
}
