package com.google.android.exoplayer2.text.ssa;

import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class SsaStyle {
    public static final int SSA_ALIGNMENT_BOTTOM_CENTER = 2;
    public static final int SSA_ALIGNMENT_BOTTOM_LEFT = 1;
    public static final int SSA_ALIGNMENT_BOTTOM_RIGHT = 3;
    public static final int SSA_ALIGNMENT_MIDDLE_CENTER = 5;
    public static final int SSA_ALIGNMENT_MIDDLE_LEFT = 4;
    public static final int SSA_ALIGNMENT_MIDDLE_RIGHT = 6;
    public static final int SSA_ALIGNMENT_TOP_CENTER = 8;
    public static final int SSA_ALIGNMENT_TOP_LEFT = 7;
    public static final int SSA_ALIGNMENT_TOP_RIGHT = 9;
    public static final int SSA_ALIGNMENT_UNKNOWN = -1;
    private static final String TAG = "SsaStyle";
    public final int alignment;
    public final boolean bold;
    public final float fontSize;
    public final boolean italic;
    public final String name;
    public final Integer primaryColor;
    public final boolean strikeout;
    public final boolean underline;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SsaAlignment {
    }

    private static boolean isValidAlignment(int alignment) {
        switch (alignment) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private SsaStyle(String name, int alignment, Integer primaryColor, float fontSize, boolean bold, boolean italic, boolean underline, boolean strikeout) {
        this.name = name;
        this.alignment = alignment;
        this.primaryColor = primaryColor;
        this.fontSize = fontSize;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.strikeout = strikeout;
    }

    public static SsaStyle fromStyleLine(String styleLine, Format format) {
        Assertions.checkArgument(styleLine.startsWith("Style:"));
        String[] strArrSplit = TextUtils.split(styleLine.substring(6), ",");
        if (strArrSplit.length != format.length) {
            Log.w(TAG, Util.formatInvariant("Skipping malformed 'Style:' line (expected %s values, found %s): '%s'", Integer.valueOf(format.length), Integer.valueOf(strArrSplit.length), styleLine));
            return null;
        }
        try {
            return new SsaStyle(strArrSplit[format.nameIndex].trim(), format.alignmentIndex != -1 ? parseAlignment(strArrSplit[format.alignmentIndex].trim()) : -1, format.primaryColorIndex != -1 ? parseColor(strArrSplit[format.primaryColorIndex].trim()) : null, format.fontSizeIndex != -1 ? parseFontSize(strArrSplit[format.fontSizeIndex].trim()) : -3.4028235E38f, format.boldIndex != -1 && parseBooleanValue(strArrSplit[format.boldIndex].trim()), format.italicIndex != -1 && parseBooleanValue(strArrSplit[format.italicIndex].trim()), format.underlineIndex != -1 && parseBooleanValue(strArrSplit[format.underlineIndex].trim()), format.strikeoutIndex != -1 && parseBooleanValue(strArrSplit[format.strikeoutIndex].trim()));
        } catch (RuntimeException e) {
            Log.w(TAG, new StringBuilder(String.valueOf(styleLine).length() + 36).append("Skipping malformed 'Style:' line: '").append(styleLine).append("'").toString(), e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseAlignment(String alignmentStr) throws NumberFormatException {
        try {
            int i = Integer.parseInt(alignmentStr.trim());
            if (isValidAlignment(i)) {
                return i;
            }
        } catch (NumberFormatException unused) {
        }
        String strValueOf = String.valueOf(alignmentStr);
        Log.w(TAG, strValueOf.length() != 0 ? "Ignoring unknown alignment: ".concat(strValueOf) : new String("Ignoring unknown alignment: "));
        return -1;
    }

    public static Integer parseColor(String ssaColorExpression) throws NumberFormatException {
        long j;
        try {
            if (ssaColorExpression.startsWith("&H")) {
                j = Long.parseLong(ssaColorExpression.substring(2), 16);
            } else {
                j = Long.parseLong(ssaColorExpression);
            }
            Assertions.checkArgument(j <= 4294967295L);
            return Integer.valueOf(Color.argb(Ints.checkedCast(((j >> 24) & 255) ^ 255), Ints.checkedCast(j & 255), Ints.checkedCast((j >> 8) & 255), Ints.checkedCast((j >> 16) & 255)));
        } catch (IllegalArgumentException e) {
            Log.w(TAG, new StringBuilder(String.valueOf(ssaColorExpression).length() + 36).append("Failed to parse color expression: '").append(ssaColorExpression).append("'").toString(), e);
            return null;
        }
    }

    private static float parseFontSize(String fontSize) {
        try {
            return Float.parseFloat(fontSize);
        } catch (NumberFormatException e) {
            Log.w(TAG, new StringBuilder(String.valueOf(fontSize).length() + 29).append("Failed to parse font size: '").append(fontSize).append("'").toString(), e);
            return -3.4028235E38f;
        }
    }

    private static boolean parseBooleanValue(String booleanValue) throws NumberFormatException {
        try {
            int i = Integer.parseInt(booleanValue);
            return i == 1 || i == -1;
        } catch (NumberFormatException e) {
            Log.w(TAG, new StringBuilder(String.valueOf(booleanValue).length() + 33).append("Failed to parse boolean value: '").append(booleanValue).append("'").toString(), e);
            return false;
        }
    }

    static final class Format {
        public final int alignmentIndex;
        public final int boldIndex;
        public final int fontSizeIndex;
        public final int italicIndex;
        public final int length;
        public final int nameIndex;
        public final int primaryColorIndex;
        public final int strikeoutIndex;
        public final int underlineIndex;

        private Format(int nameIndex, int alignmentIndex, int primaryColorIndex, int fontSizeIndex, int boldIndex, int italicIndex, int underlineIndex, int strikeoutIndex, int length) {
            this.nameIndex = nameIndex;
            this.alignmentIndex = alignmentIndex;
            this.primaryColorIndex = primaryColorIndex;
            this.fontSizeIndex = fontSizeIndex;
            this.boldIndex = boldIndex;
            this.italicIndex = italicIndex;
            this.underlineIndex = underlineIndex;
            this.strikeoutIndex = strikeoutIndex;
            this.length = length;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:7:0x002d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static com.google.android.exoplayer2.text.ssa.SsaStyle.Format fromFormatLine(java.lang.String r14) {
            /*
                r0 = 7
                java.lang.String r14 = r14.substring(r0)
                java.lang.String r1 = ","
                java.lang.String[] r14 = android.text.TextUtils.split(r14, r1)
                r1 = -1
                r2 = 0
                r5 = r1
                r6 = r5
                r7 = r6
                r8 = r7
                r9 = r8
                r10 = r9
                r11 = r10
                r12 = r11
                r3 = r2
            L16:
                int r4 = r14.length
                if (r3 >= r4) goto La1
                r4 = r14[r3]
                java.lang.String r4 = r4.trim()
                java.lang.String r4 = com.google.common.base.Ascii.toLowerCase(r4)
                r4.hashCode()
                int r13 = r4.hashCode()
                switch(r13) {
                    case -1178781136: goto L80;
                    case -1026963764: goto L74;
                    case -192095652: goto L68;
                    case -70925746: goto L5c;
                    case 3029637: goto L51;
                    case 3373707: goto L46;
                    case 366554320: goto L3b;
                    case 1767875043: goto L30;
                    default: goto L2d;
                }
            L2d:
                r4 = r1
                goto L8a
            L30:
                java.lang.String r13 = "alignment"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L39
                goto L2d
            L39:
                r4 = r0
                goto L8a
            L3b:
                java.lang.String r13 = "fontsize"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L44
                goto L2d
            L44:
                r4 = 6
                goto L8a
            L46:
                java.lang.String r13 = "name"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L4f
                goto L2d
            L4f:
                r4 = 5
                goto L8a
            L51:
                java.lang.String r13 = "bold"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L5a
                goto L2d
            L5a:
                r4 = 4
                goto L8a
            L5c:
                java.lang.String r13 = "primarycolour"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L66
                goto L2d
            L66:
                r4 = 3
                goto L8a
            L68:
                java.lang.String r13 = "strikeout"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L72
                goto L2d
            L72:
                r4 = 2
                goto L8a
            L74:
                java.lang.String r13 = "underline"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L7e
                goto L2d
            L7e:
                r4 = 1
                goto L8a
            L80:
                java.lang.String r13 = "italic"
                boolean r4 = r4.equals(r13)
                if (r4 != 0) goto L89
                goto L2d
            L89:
                r4 = r2
            L8a:
                switch(r4) {
                    case 0: goto L9c;
                    case 1: goto L9a;
                    case 2: goto L98;
                    case 3: goto L96;
                    case 4: goto L94;
                    case 5: goto L92;
                    case 6: goto L90;
                    case 7: goto L8e;
                    default: goto L8d;
                }
            L8d:
                goto L9d
            L8e:
                r6 = r3
                goto L9d
            L90:
                r8 = r3
                goto L9d
            L92:
                r5 = r3
                goto L9d
            L94:
                r9 = r3
                goto L9d
            L96:
                r7 = r3
                goto L9d
            L98:
                r12 = r3
                goto L9d
            L9a:
                r11 = r3
                goto L9d
            L9c:
                r10 = r3
            L9d:
                int r3 = r3 + 1
                goto L16
            La1:
                if (r5 == r1) goto Lab
                com.google.android.exoplayer2.text.ssa.SsaStyle$Format r0 = new com.google.android.exoplayer2.text.ssa.SsaStyle$Format
                int r13 = r14.length
                r4 = r0
                r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13)
                goto Lac
            Lab:
                r0 = 0
            Lac:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaStyle.Format.fromFormatLine(java.lang.String):com.google.android.exoplayer2.text.ssa.SsaStyle$Format");
        }
    }

    static final class Overrides {
        private static final String TAG = "SsaStyle.Overrides";
        public final int alignment;
        public final PointF position;
        private static final Pattern BRACES_PATTERN = Pattern.compile("\\{([^}]*)\\}");
        private static final String PADDED_DECIMAL_PATTERN = "\\s*\\d+(?:\\.\\d+)?\\s*";
        private static final Pattern POSITION_PATTERN = Pattern.compile(Util.formatInvariant("\\\\pos\\((%1$s),(%1$s)\\)", PADDED_DECIMAL_PATTERN));
        private static final Pattern MOVE_PATTERN = Pattern.compile(Util.formatInvariant("\\\\move\\(%1$s,%1$s,(%1$s),(%1$s)(?:,%1$s,%1$s)?\\)", PADDED_DECIMAL_PATTERN));
        private static final Pattern ALIGNMENT_OVERRIDE_PATTERN = Pattern.compile("\\\\an(\\d+)");

        private Overrides(int alignment, PointF position) {
            this.alignment = alignment;
            this.position = position;
        }

        public static Overrides parseFromDialogue(String text) {
            Matcher matcher = BRACES_PATTERN.matcher(text);
            PointF pointF = null;
            int i = -1;
            while (matcher.find()) {
                String str = (String) Assertions.checkNotNull(matcher.group(1));
                try {
                    PointF position = parsePosition(str);
                    if (position != null) {
                        pointF = position;
                    }
                } catch (RuntimeException unused) {
                }
                try {
                    int alignmentOverride = parseAlignmentOverride(str);
                    if (alignmentOverride != -1) {
                        i = alignmentOverride;
                    }
                } catch (RuntimeException unused2) {
                }
            }
            return new Overrides(i, pointF);
        }

        public static String stripStyleOverrides(String dialogueLine) {
            return BRACES_PATTERN.matcher(dialogueLine).replaceAll("");
        }

        private static PointF parsePosition(String styleOverride) {
            String strGroup;
            String strGroup2;
            Matcher matcher = POSITION_PATTERN.matcher(styleOverride);
            Matcher matcher2 = MOVE_PATTERN.matcher(styleOverride);
            boolean zFind = matcher.find();
            boolean zFind2 = matcher2.find();
            if (zFind) {
                if (zFind2) {
                    Log.i(TAG, new StringBuilder(String.valueOf(styleOverride).length() + 82).append("Override has both \\pos(x,y) and \\move(x1,y1,x2,y2); using \\pos values. override='").append(styleOverride).append("'").toString());
                }
                strGroup = matcher.group(1);
                strGroup2 = matcher.group(2);
            } else {
                if (!zFind2) {
                    return null;
                }
                strGroup = matcher2.group(1);
                strGroup2 = matcher2.group(2);
            }
            return new PointF(Float.parseFloat(((String) Assertions.checkNotNull(strGroup)).trim()), Float.parseFloat(((String) Assertions.checkNotNull(strGroup2)).trim()));
        }

        private static int parseAlignmentOverride(String braceContents) {
            Matcher matcher = ALIGNMENT_OVERRIDE_PATTERN.matcher(braceContents);
            if (matcher.find()) {
                return SsaStyle.parseAlignment((String) Assertions.checkNotNull(matcher.group(1)));
            }
            return -1;
        }
    }
}
