package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;

/* loaded from: classes.dex */
public interface ContentMetadata {
    public static final String KEY_CONTENT_LENGTH = "exo_len";
    public static final String KEY_CUSTOM_PREFIX = "custom_";
    public static final String KEY_REDIRECTED_URI = "exo_redir";

    boolean contains(String key);

    long get(String key, long defaultValue);

    String get(String key, String defaultValue);

    byte[] get(String key, byte[] defaultValue);

    static long getContentLength(ContentMetadata contentMetadata) {
        return contentMetadata.get(KEY_CONTENT_LENGTH, -1L);
    }

    static Uri getRedirectedUri(ContentMetadata contentMetadata) {
        String str = contentMetadata.get(KEY_REDIRECTED_URI, (String) null);
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }
}
