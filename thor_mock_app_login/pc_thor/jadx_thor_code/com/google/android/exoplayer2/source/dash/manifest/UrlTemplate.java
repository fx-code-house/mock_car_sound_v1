package com.google.android.exoplayer2.source.dash.manifest;

import java.util.Locale;

/* loaded from: classes.dex */
public final class UrlTemplate {
    private static final String BANDWIDTH = "Bandwidth";
    private static final int BANDWIDTH_ID = 3;
    private static final String DEFAULT_FORMAT_TAG = "%01d";
    private static final String ESCAPED_DOLLAR = "$$";
    private static final String NUMBER = "Number";
    private static final int NUMBER_ID = 2;
    private static final String REPRESENTATION = "RepresentationID";
    private static final int REPRESENTATION_ID = 1;
    private static final String TIME = "Time";
    private static final int TIME_ID = 4;
    private final int identifierCount;
    private final String[] identifierFormatTags;
    private final int[] identifiers;
    private final String[] urlPieces;

    public static UrlTemplate compile(String template) {
        String[] strArr = new String[5];
        int[] iArr = new int[4];
        String[] strArr2 = new String[4];
        return new UrlTemplate(strArr, iArr, strArr2, parseTemplate(template, strArr, iArr, strArr2));
    }

    private UrlTemplate(String[] urlPieces, int[] identifiers, String[] identifierFormatTags, int identifierCount) {
        this.urlPieces = urlPieces;
        this.identifiers = identifiers;
        this.identifierFormatTags = identifierFormatTags;
        this.identifierCount = identifierCount;
    }

    public String buildUri(String representationId, long segmentNumber, int bandwidth, long time) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            int i2 = this.identifierCount;
            if (i < i2) {
                sb.append(this.urlPieces[i]);
                int i3 = this.identifiers[i];
                if (i3 == 1) {
                    sb.append(representationId);
                } else if (i3 == 2) {
                    sb.append(String.format(Locale.US, this.identifierFormatTags[i], Long.valueOf(segmentNumber)));
                } else if (i3 == 3) {
                    sb.append(String.format(Locale.US, this.identifierFormatTags[i], Integer.valueOf(bandwidth)));
                } else if (i3 == 4) {
                    sb.append(String.format(Locale.US, this.identifierFormatTags[i], Long.valueOf(time)));
                }
                i++;
            } else {
                sb.append(this.urlPieces[i2]);
                return sb.toString();
            }
        }
    }

    private static int parseTemplate(String template, String[] urlPieces, int[] identifiers, String[] identifierFormatTags) {
        String strSubstring;
        urlPieces[0] = "";
        int length = 0;
        int i = 0;
        while (length < template.length()) {
            int iIndexOf = template.indexOf("$", length);
            if (iIndexOf == -1) {
                String strValueOf = String.valueOf(urlPieces[i]);
                String strValueOf2 = String.valueOf(template.substring(length));
                urlPieces[i] = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                length = template.length();
            } else if (iIndexOf != length) {
                String strValueOf3 = String.valueOf(urlPieces[i]);
                String strValueOf4 = String.valueOf(template.substring(length, iIndexOf));
                urlPieces[i] = strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3);
                length = iIndexOf;
            } else if (template.startsWith(ESCAPED_DOLLAR, length)) {
                urlPieces[i] = String.valueOf(urlPieces[i]).concat("$");
                length += 2;
            } else {
                int i2 = length + 1;
                int iIndexOf2 = template.indexOf("$", i2);
                String strSubstring2 = template.substring(i2, iIndexOf2);
                if (strSubstring2.equals(REPRESENTATION)) {
                    identifiers[i] = 1;
                } else {
                    int iIndexOf3 = strSubstring2.indexOf("%0");
                    if (iIndexOf3 != -1) {
                        strSubstring = strSubstring2.substring(iIndexOf3);
                        if (!strSubstring.endsWith("d") && !strSubstring.endsWith("x")) {
                            strSubstring = String.valueOf(strSubstring).concat("d");
                        }
                        strSubstring2 = strSubstring2.substring(0, iIndexOf3);
                    } else {
                        strSubstring = DEFAULT_FORMAT_TAG;
                    }
                    strSubstring2.hashCode();
                    switch (strSubstring2) {
                        case "Number":
                            identifiers[i] = 2;
                            break;
                        case "Time":
                            identifiers[i] = 4;
                            break;
                        case "Bandwidth":
                            identifiers[i] = 3;
                            break;
                        default:
                            String strValueOf5 = String.valueOf(template);
                            throw new IllegalArgumentException(strValueOf5.length() != 0 ? "Invalid template: ".concat(strValueOf5) : new String("Invalid template: "));
                    }
                    identifierFormatTags[i] = strSubstring;
                }
                i++;
                urlPieces[i] = "";
                length = iIndexOf2 + 1;
            }
        }
        return i;
    }
}
