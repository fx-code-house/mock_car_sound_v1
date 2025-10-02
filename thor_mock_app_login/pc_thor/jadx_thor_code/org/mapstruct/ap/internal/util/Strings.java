package org.mapstruct.ap.internal.util;

import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public class Strings {
    private static final Set<String> KEYWORDS = Collections.asSet("abstract", "continue", "for", AppSettingsData.STATUS_NEW, "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while");
    private static final char UNDERSCORE = '_';

    private Strings() {
    }

    public static String capitalize(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    public static String decapitalize(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(0, 1).toLowerCase(Locale.ROOT) + str.substring(1);
    }

    public static String join(Iterable<?> iterable, String str) {
        return join(iterable, str, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> String join(Iterable<T> iterable, String str, Extractor<T, String> extractor) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Object objApply : iterable) {
            if (z) {
                z = false;
            } else {
                sb.append(str);
            }
            if (extractor != 0) {
                objApply = extractor.apply(objApply);
            }
            sb.append(objApply);
        }
        return sb.toString();
    }

    public static String joinAndCamelize(Iterable<?> iterable) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Object obj : iterable) {
            if (!z) {
                sb.append(capitalize(obj.toString()));
            } else {
                sb.append(obj);
                z = false;
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getSafeVariableName(String str, String... strArr) {
        return getSafeVariableName(str, Arrays.asList(strArr));
    }

    public static String getSafeVariableName(String str, Collection<String> collection) {
        String strJoinAndCamelize = joinAndCamelize(extractParts(decapitalize(sanitizeIdentifierName(str))));
        HashSet hashSet = new HashSet(KEYWORDS);
        hashSet.addAll(collection);
        if (!hashSet.contains(strJoinAndCamelize)) {
            return strJoinAndCamelize;
        }
        int i = 1;
        String str2 = Character.isDigit(strJoinAndCamelize.charAt(strJoinAndCamelize.length() - 1)) ? "_" : "";
        while (hashSet.contains(strJoinAndCamelize + str2 + i)) {
            i++;
        }
        return strJoinAndCamelize + str2 + i;
    }

    public static String sanitizeIdentifierName(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        int i = 0;
        while (i < str.length() && (str.charAt(i) == '_' || Character.isDigit(str.charAt(i)))) {
            i++;
        }
        if (i < str.length()) {
            return str.substring(i).replace(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, "Array");
        }
        return str.replace(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, "Array");
    }

    public static String stubPropertyName(String str) {
        return decapitalize(str.substring(str.lastIndexOf(46) + 1));
    }

    static Iterable<String> extractParts(String str) {
        return Arrays.asList(str.split("\\."));
    }

    public static String getMostSimilarWord(String str, Collection<String> collection) {
        int i = Integer.MAX_VALUE;
        String str2 = null;
        for (String str3 : collection) {
            int iLevenshteinDistance = levenshteinDistance(str3, str);
            if (iLevenshteinDistance < i) {
                str2 = str3;
                i = iLevenshteinDistance;
            }
        }
        return str2;
    }

    private static int levenshteinDistance(String str, String str2) {
        int length = str.length() + 1;
        int length2 = str2.length() + 1;
        int[][] iArr = new int[length2][];
        for (int i = 0; i < length2; i++) {
            iArr[i] = new int[length];
        }
        for (int i2 = 0; i2 < length2; i2++) {
            iArr[i2][0] = i2;
        }
        for (int i3 = 0; i3 < length; i3++) {
            iArr[0][i3] = i3;
        }
        for (int i4 = 1; i4 < length2; i4++) {
            for (int i5 = 1; i5 < length; i5++) {
                int i6 = i5 - 1;
                int i7 = i4 - 1;
                int i8 = str.charAt(i6) == str2.charAt(i7) ? 0 : 1;
                int[] iArr2 = iArr[i4];
                iArr2[i5] = Math.min(Math.min(iArr[i7][i5] + 1, iArr2[i6] + 1), iArr[i7][i6] + i8);
            }
        }
        return iArr[length2 - 1][length - 1];
    }
}
