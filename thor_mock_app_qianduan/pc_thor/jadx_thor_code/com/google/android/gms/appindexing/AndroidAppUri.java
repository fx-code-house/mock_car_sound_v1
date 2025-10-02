package com.google.android.gms.appindexing;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
@Deprecated
/* loaded from: classes2.dex */
public final class AndroidAppUri {
    private final Uri uri;

    private AndroidAppUri(Uri uri) {
        this.uri = uri;
    }

    public static AndroidAppUri newAndroidAppUri(Uri uri) {
        AndroidAppUri androidAppUri = new AndroidAppUri(uri);
        if (!"android-app".equals(androidAppUri.uri.getScheme())) {
            throw new IllegalArgumentException("android-app scheme is required.");
        }
        if (TextUtils.isEmpty(androidAppUri.getPackageName())) {
            throw new IllegalArgumentException("Package name is empty.");
        }
        if (androidAppUri.uri.equals(newAndroidAppUri(androidAppUri.getPackageName(), androidAppUri.getDeepLinkUri()).toUri())) {
            return androidAppUri;
        }
        throw new IllegalArgumentException("URI is not canonical.");
    }

    public static AndroidAppUri newAndroidAppUri(String str, @Nullable Uri uri) {
        Uri.Builder builderAuthority = new Uri.Builder().scheme("android-app").authority(str);
        if (uri != null) {
            builderAuthority.appendPath(uri.getScheme());
            if (uri.getAuthority() != null) {
                builderAuthority.appendPath(uri.getAuthority());
            }
            Iterator<String> it = uri.getPathSegments().iterator();
            while (it.hasNext()) {
                builderAuthority.appendPath(it.next());
            }
            builderAuthority.encodedQuery(uri.getEncodedQuery()).encodedFragment(uri.getEncodedFragment());
        }
        return new AndroidAppUri(builderAuthority.build());
    }

    public final Uri toUri() {
        return this.uri;
    }

    public final String getPackageName() {
        return this.uri.getAuthority();
    }

    public final Uri getDeepLinkUri() {
        List<String> pathSegments = this.uri.getPathSegments();
        if (pathSegments.size() <= 0) {
            return null;
        }
        String str = pathSegments.get(0);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(str);
        if (pathSegments.size() > 1) {
            builder.authority(pathSegments.get(1));
            for (int i = 2; i < pathSegments.size(); i++) {
                builder.appendPath(pathSegments.get(i));
            }
        }
        builder.encodedQuery(this.uri.getEncodedQuery());
        builder.encodedFragment(this.uri.getEncodedFragment());
        return builder.build();
    }

    public final String toString() {
        return this.uri.toString();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof AndroidAppUri) {
            return this.uri.equals(((AndroidAppUri) obj).uri);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.uri);
    }
}
