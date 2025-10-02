package com.google.android.exoplayer2;

/* loaded from: classes.dex */
public final class RendererConfiguration {
    public static final RendererConfiguration DEFAULT = new RendererConfiguration(false);
    public final boolean tunneling;

    public RendererConfiguration(boolean tunneling) {
        this.tunneling = tunneling;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.tunneling == ((RendererConfiguration) obj).tunneling;
    }

    public int hashCode() {
        return !this.tunneling ? 1 : 0;
    }
}
