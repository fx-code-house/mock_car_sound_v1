package com.google.android.exoplayer2.util;

import android.net.Uri;
import android.text.TextUtils;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class UriUtil {
    private static final int FRAGMENT = 3;
    private static final int INDEX_COUNT = 4;
    private static final int PATH = 1;
    private static final int QUERY = 2;
    private static final int SCHEME_COLON = 0;

    private UriUtil() {
    }

    public static Uri resolveToUri(String baseUri, String referenceUri) {
        return Uri.parse(resolve(baseUri, referenceUri));
    }

    public static String resolve(String baseUri, String referenceUri) {
        StringBuilder sb = new StringBuilder();
        if (baseUri == null) {
            baseUri = "";
        }
        if (referenceUri == null) {
            referenceUri = "";
        }
        int[] uriIndices = getUriIndices(referenceUri);
        if (uriIndices[0] != -1) {
            sb.append(referenceUri);
            removeDotSegments(sb, uriIndices[1], uriIndices[2]);
            return sb.toString();
        }
        int[] uriIndices2 = getUriIndices(baseUri);
        if (uriIndices[3] == 0) {
            return sb.append((CharSequence) baseUri, 0, uriIndices2[3]).append(referenceUri).toString();
        }
        if (uriIndices[2] == 0) {
            return sb.append((CharSequence) baseUri, 0, uriIndices2[2]).append(referenceUri).toString();
        }
        int i = uriIndices[1];
        if (i != 0) {
            int i2 = uriIndices2[0] + 1;
            sb.append((CharSequence) baseUri, 0, i2).append(referenceUri);
            return removeDotSegments(sb, uriIndices[1] + i2, i2 + uriIndices[2]);
        }
        if (referenceUri.charAt(i) == '/') {
            sb.append((CharSequence) baseUri, 0, uriIndices2[1]).append(referenceUri);
            int i3 = uriIndices2[1];
            return removeDotSegments(sb, i3, uriIndices[2] + i3);
        }
        int i4 = uriIndices2[0] + 2;
        int i5 = uriIndices2[1];
        if (i4 < i5 && i5 == uriIndices2[2]) {
            sb.append((CharSequence) baseUri, 0, i5).append('/').append(referenceUri);
            int i6 = uriIndices2[1];
            return removeDotSegments(sb, i6, uriIndices[2] + i6 + 1);
        }
        int iLastIndexOf = baseUri.lastIndexOf(47, uriIndices2[2] - 1);
        int i7 = iLastIndexOf == -1 ? uriIndices2[1] : iLastIndexOf + 1;
        sb.append((CharSequence) baseUri, 0, i7).append(referenceUri);
        return removeDotSegments(sb, uriIndices2[1], i7 + uriIndices[2]);
    }

    public static boolean isAbsolute(String uri) {
        return (uri == null || getUriIndices(uri)[0] == -1) ? false : true;
    }

    public static Uri removeQueryParameter(Uri uri, String queryParameterName) {
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.clearQuery();
        for (String str : uri.getQueryParameterNames()) {
            if (!str.equals(queryParameterName)) {
                Iterator<String> it = uri.getQueryParameters(str).iterator();
                while (it.hasNext()) {
                    builderBuildUpon.appendQueryParameter(str, it.next());
                }
            }
        }
        return builderBuildUpon.build();
    }

    private static String removeDotSegments(StringBuilder uri, int offset, int limit) {
        int i;
        int iLastIndexOf;
        if (offset >= limit) {
            return uri.toString();
        }
        if (uri.charAt(offset) == '/') {
            offset++;
        }
        int i2 = offset;
        int i3 = i2;
        while (i2 <= limit) {
            if (i2 == limit) {
                i = i2;
            } else if (uri.charAt(i2) == '/') {
                i = i2 + 1;
            } else {
                i2++;
            }
            int i4 = i3 + 1;
            if (i2 == i4 && uri.charAt(i3) == '.') {
                uri.delete(i3, i);
                limit -= i - i3;
            } else {
                if (i2 == i3 + 2 && uri.charAt(i3) == '.' && uri.charAt(i4) == '.') {
                    iLastIndexOf = uri.lastIndexOf("/", i3 - 2) + 1;
                    int i5 = iLastIndexOf > offset ? iLastIndexOf : offset;
                    uri.delete(i5, i);
                    limit -= i - i5;
                } else {
                    iLastIndexOf = i2 + 1;
                }
                i3 = iLastIndexOf;
            }
            i2 = i3;
        }
        return uri.toString();
    }

    private static int[] getUriIndices(String uriString) {
        int iIndexOf;
        int[] iArr = new int[4];
        if (TextUtils.isEmpty(uriString)) {
            iArr[0] = -1;
            return iArr;
        }
        int length = uriString.length();
        int iIndexOf2 = uriString.indexOf(35);
        if (iIndexOf2 != -1) {
            length = iIndexOf2;
        }
        int iIndexOf3 = uriString.indexOf(63);
        if (iIndexOf3 == -1 || iIndexOf3 > length) {
            iIndexOf3 = length;
        }
        int iIndexOf4 = uriString.indexOf(47);
        if (iIndexOf4 == -1 || iIndexOf4 > iIndexOf3) {
            iIndexOf4 = iIndexOf3;
        }
        int iIndexOf5 = uriString.indexOf(58);
        if (iIndexOf5 > iIndexOf4) {
            iIndexOf5 = -1;
        }
        int i = iIndexOf5 + 2;
        if (i < iIndexOf3 && uriString.charAt(iIndexOf5 + 1) == '/' && uriString.charAt(i) == '/') {
            iIndexOf = uriString.indexOf(47, iIndexOf5 + 3);
            if (iIndexOf == -1 || iIndexOf > iIndexOf3) {
                iIndexOf = iIndexOf3;
            }
        } else {
            iIndexOf = iIndexOf5 + 1;
        }
        iArr[0] = iIndexOf5;
        iArr[1] = iIndexOf;
        iArr[2] = iIndexOf3;
        iArr[3] = length;
        return iArr;
    }
}
