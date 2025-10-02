package com.google.android.exoplayer2.source.dash.manifest;

/* loaded from: classes.dex */
public final class UtcTimingElement {
    public final String schemeIdUri;
    public final String value;

    public UtcTimingElement(String schemeIdUri, String value) {
        this.schemeIdUri = schemeIdUri;
        this.value = value;
    }

    public String toString() {
        String str = this.schemeIdUri;
        String str2 = this.value;
        return new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(str2).length()).append(str).append(", ").append(str2).toString();
    }
}
