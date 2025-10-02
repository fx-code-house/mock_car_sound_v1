package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class Descriptor {
    public final String id;
    public final String schemeIdUri;
    public final String value;

    public Descriptor(String schemeIdUri, String value, String id) {
        this.schemeIdUri = schemeIdUri;
        this.value = value;
        this.id = id;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Descriptor descriptor = (Descriptor) obj;
        return Util.areEqual(this.schemeIdUri, descriptor.schemeIdUri) && Util.areEqual(this.value, descriptor.value) && Util.areEqual(this.id, descriptor.id);
    }

    public int hashCode() {
        int iHashCode = this.schemeIdUri.hashCode() * 31;
        String str = this.value;
        int iHashCode2 = (iHashCode + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.id;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }
}
