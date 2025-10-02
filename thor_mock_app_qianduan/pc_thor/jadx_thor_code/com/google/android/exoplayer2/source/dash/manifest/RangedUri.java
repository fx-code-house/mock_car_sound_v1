package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import com.google.android.exoplayer2.util.UriUtil;

/* loaded from: classes.dex */
public final class RangedUri {
    private int hashCode;
    public final long length;
    private final String referenceUri;
    public final long start;

    public RangedUri(String referenceUri, long start, long length) {
        this.referenceUri = referenceUri == null ? "" : referenceUri;
        this.start = start;
        this.length = length;
    }

    public Uri resolveUri(String baseUri) {
        return UriUtil.resolveToUri(baseUri, this.referenceUri);
    }

    public String resolveUriString(String baseUri) {
        return UriUtil.resolve(baseUri, this.referenceUri);
    }

    public RangedUri attemptMerge(RangedUri other, String baseUri) {
        String strResolveUriString = resolveUriString(baseUri);
        if (other != null && strResolveUriString.equals(other.resolveUriString(baseUri))) {
            long j = this.length;
            if (j != -1) {
                long j2 = this.start;
                if (j2 + j == other.start) {
                    long j3 = other.length;
                    return new RangedUri(strResolveUriString, j2, j3 != -1 ? j + j3 : -1L);
                }
            }
            long j4 = other.length;
            if (j4 != -1) {
                long j5 = other.start;
                if (j5 + j4 == this.start) {
                    return new RangedUri(strResolveUriString, j5, j != -1 ? j4 + j : -1L);
                }
            }
        }
        return null;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((527 + ((int) this.start)) * 31) + ((int) this.length)) * 31) + this.referenceUri.hashCode();
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RangedUri rangedUri = (RangedUri) obj;
        return this.start == rangedUri.start && this.length == rangedUri.length && this.referenceUri.equals(rangedUri.referenceUri);
    }

    public String toString() {
        String str = this.referenceUri;
        long j = this.start;
        return new StringBuilder(String.valueOf(str).length() + 81).append("RangedUri(referenceUri=").append(str).append(", start=").append(j).append(", length=").append(this.length).append(")").toString();
    }
}
