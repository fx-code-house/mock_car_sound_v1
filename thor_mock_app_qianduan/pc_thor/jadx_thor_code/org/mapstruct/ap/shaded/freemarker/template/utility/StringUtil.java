package org.mapstruct.ap.shaded.freemarker.template.utility;

import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.StringTokenizer;
import kotlin.text.Typography;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core.ParseException;
import org.mapstruct.ap.shaded.freemarker.template.Version;

/* loaded from: classes3.dex */
public class StringUtil {
    private static final char[] ESCAPES = createEscapes();
    private static final int ESC_BACKSLASH = 3;
    private static final int ESC_HEXA = 1;
    private static final int NO_ESC = 0;

    private static boolean safeInURL(char c, boolean z) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || ((c >= '0' && c <= '9') || c == '_' || c == '-' || c == '.' || c == '!' || c == '~' || ((c >= '\'' && c <= '*') || (z && c == '/')));
    }

    private static char toHexDigit(int i) {
        return (char) (i < 10 ? i + 48 : (i - 10) + 65);
    }

    public static String HTMLEnc(String str) {
        return XMLEncNA(str);
    }

    public static String XMLEnc(String str) {
        return XMLOrXHTMLEnc(str, "&apos;");
    }

    public static String XHTMLEnc(String str) {
        return XMLOrXHTMLEnc(str, "&#39;");
    }

    private static String XMLOrXHTMLEnc(String str, String str2) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '<' || cCharAt == '>' || cCharAt == '&' || cCharAt == '\"' || cCharAt == '\'') {
                StringBuffer stringBuffer = new StringBuffer(str.substring(0, i));
                if (cCharAt == '\"') {
                    stringBuffer.append("&quot;");
                } else if (cCharAt == '<') {
                    stringBuffer.append("&lt;");
                } else if (cCharAt == '>') {
                    stringBuffer.append("&gt;");
                } else if (cCharAt == '&') {
                    stringBuffer.append("&amp;");
                } else if (cCharAt == '\'') {
                    stringBuffer.append(str2);
                }
                int i2 = i + 1;
                int i3 = i2;
                while (i2 < length) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 == '<' || cCharAt2 == '>' || cCharAt2 == '&' || cCharAt2 == '\"' || cCharAt2 == '\'') {
                        stringBuffer.append(str.substring(i3, i2));
                        if (cCharAt2 == '\"') {
                            stringBuffer.append("&quot;");
                        } else if (cCharAt2 == '<') {
                            stringBuffer.append("&lt;");
                        } else if (cCharAt2 == '>') {
                            stringBuffer.append("&gt;");
                        } else if (cCharAt2 == '&') {
                            stringBuffer.append("&amp;");
                        } else if (cCharAt2 == '\'') {
                            stringBuffer.append(str2);
                        }
                        i3 = i2 + 1;
                    }
                    i2++;
                }
                if (i3 < length) {
                    stringBuffer.append(str.substring(i3));
                }
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String XMLEncNA(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '<' || cCharAt == '>' || cCharAt == '&' || cCharAt == '\"') {
                StringBuffer stringBuffer = new StringBuffer(str.substring(0, i));
                if (cCharAt == '\"') {
                    stringBuffer.append("&quot;");
                } else if (cCharAt == '&') {
                    stringBuffer.append("&amp;");
                } else if (cCharAt == '<') {
                    stringBuffer.append("&lt;");
                } else if (cCharAt == '>') {
                    stringBuffer.append("&gt;");
                }
                int i2 = i + 1;
                int i3 = i2;
                while (i2 < length) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 == '<' || cCharAt2 == '>' || cCharAt2 == '&' || cCharAt2 == '\"') {
                        stringBuffer.append(str.substring(i3, i2));
                        if (cCharAt2 == '\"') {
                            stringBuffer.append("&quot;");
                        } else if (cCharAt2 == '&') {
                            stringBuffer.append("&amp;");
                        } else if (cCharAt2 == '<') {
                            stringBuffer.append("&lt;");
                        } else if (cCharAt2 == '>') {
                            stringBuffer.append("&gt;");
                        }
                        i3 = i2 + 1;
                    }
                    i2++;
                }
                if (i3 < length) {
                    stringBuffer.append(str.substring(i3));
                }
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String XMLEncQAttr(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '<' || cCharAt == '&' || cCharAt == '\"') {
                StringBuffer stringBuffer = new StringBuffer(str.substring(0, i));
                if (cCharAt == '\"') {
                    stringBuffer.append("&quot;");
                } else if (cCharAt == '&') {
                    stringBuffer.append("&amp;");
                } else if (cCharAt == '<') {
                    stringBuffer.append("&lt;");
                }
                int i2 = i + 1;
                int i3 = i2;
                while (i2 < length) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 == '<' || cCharAt2 == '&' || cCharAt2 == '\"') {
                        stringBuffer.append(str.substring(i3, i2));
                        if (cCharAt2 == '\"') {
                            stringBuffer.append("&quot;");
                        } else if (cCharAt2 == '&') {
                            stringBuffer.append("&amp;");
                        } else if (cCharAt2 == '<') {
                            stringBuffer.append("&lt;");
                        }
                        i3 = i2 + 1;
                    }
                    i2++;
                }
                if (i3 < length) {
                    stringBuffer.append(str.substring(i3));
                }
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String XMLEncNQG(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '<' || ((cCharAt == '>' && i > 1 && str.charAt(i - 1) == ']' && str.charAt(i - 2) == ']') || cCharAt == '&')) {
                StringBuffer stringBuffer = new StringBuffer(str.substring(0, i));
                if (cCharAt == '&') {
                    stringBuffer.append("&amp;");
                } else if (cCharAt == '<') {
                    stringBuffer.append("&lt;");
                } else if (cCharAt == '>') {
                    stringBuffer.append("&gt;");
                } else {
                    throw new BugException();
                }
                int i2 = i + 1;
                int i3 = i2;
                while (i2 < length) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 == '<' || ((cCharAt2 == '>' && i2 > 1 && str.charAt(i2 - 1) == ']' && str.charAt(i2 - 2) == ']') || cCharAt2 == '&')) {
                        stringBuffer.append(str.substring(i3, i2));
                        if (cCharAt2 == '&') {
                            stringBuffer.append("&amp;");
                        } else if (cCharAt2 == '<') {
                            stringBuffer.append("&lt;");
                        } else if (cCharAt2 == '>') {
                            stringBuffer.append("&gt;");
                        } else {
                            throw new BugException();
                        }
                        i3 = i2 + 1;
                    }
                    i2++;
                }
                if (i3 < length) {
                    stringBuffer.append(str.substring(i3));
                }
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String RTFEnc(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\' || cCharAt == '{' || cCharAt == '}') {
                StringBuffer stringBuffer = new StringBuffer(str.substring(0, i));
                if (cCharAt == '\\') {
                    stringBuffer.append("\\\\");
                } else if (cCharAt == '{') {
                    stringBuffer.append("\\{");
                } else if (cCharAt == '}') {
                    stringBuffer.append("\\}");
                }
                int i2 = i + 1;
                int i3 = i2;
                while (i2 < length) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 == '\\' || cCharAt2 == '{' || cCharAt2 == '}') {
                        stringBuffer.append(str.substring(i3, i2));
                        if (cCharAt2 == '\\') {
                            stringBuffer.append("\\\\");
                        } else if (cCharAt2 == '{') {
                            stringBuffer.append("\\{");
                        } else if (cCharAt2 == '}') {
                            stringBuffer.append("\\}");
                        }
                        i3 = i2 + 1;
                    }
                    i2++;
                }
                if (i3 < length) {
                    stringBuffer.append(str.substring(i3));
                }
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String URLEnc(String str, String str2) throws UnsupportedEncodingException {
        return URLEnc(str, str2, false);
    }

    public static String URLPathEnc(String str, String str2) throws UnsupportedEncodingException {
        return URLEnc(str, str2, true);
    }

    private static String URLEnc(String str, String str2, boolean z) throws UnsupportedEncodingException {
        int length = str.length();
        int i = 0;
        while (i < length && safeInURL(str.charAt(i), z)) {
            i++;
        }
        if (i == length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer((length / 3) + length + 2);
        stringBuffer.append(str.substring(0, i));
        int i2 = i + 1;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (safeInURL(cCharAt, z)) {
                if (i != -1) {
                    for (byte b : str.substring(i, i2).getBytes(str2)) {
                        stringBuffer.append('%');
                        int i3 = b & Ascii.SI;
                        int i4 = (b >> 4) & 15;
                        stringBuffer.append((char) (i4 < 10 ? i4 + 48 : (i4 - 10) + 65));
                        stringBuffer.append((char) (i3 < 10 ? i3 + 48 : (i3 - 10) + 65));
                    }
                    i = -1;
                }
                stringBuffer.append(cCharAt);
            } else if (i == -1) {
                i = i2;
            }
            i2++;
        }
        if (i != -1) {
            for (byte b2 : str.substring(i, i2).getBytes(str2)) {
                stringBuffer.append('%');
                int i5 = b2 & Ascii.SI;
                int i6 = (b2 >> 4) & 15;
                stringBuffer.append((char) (i6 < 10 ? i6 + 48 : (i6 - 10) + 65));
                stringBuffer.append((char) (i5 < 10 ? i5 + 48 : (i5 - 10) + 65));
            }
        }
        return stringBuffer.toString();
    }

    private static char[] createEscapes() {
        char[] cArr = new char[93];
        for (int i = 0; i < 32; i++) {
            cArr[i] = 1;
        }
        cArr[92] = '\\';
        cArr[39] = '\'';
        cArr[34] = Typography.quote;
        cArr[60] = 'l';
        cArr[62] = 'g';
        cArr[38] = 'a';
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[12] = 'f';
        cArr[13] = 'r';
        cArr[36] = '$';
        return cArr;
    }

    public static String FTLStringLiteralEnc(String str) {
        int length = str.length();
        int length2 = ESCAPES.length;
        StringBuffer stringBuffer = null;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < length2) {
                char c = ESCAPES[cCharAt];
                if (c != 0) {
                    if (c == 1) {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer(str.length() + 3);
                            stringBuffer.append(str.substring(0, i));
                        }
                        stringBuffer.append("\\x00");
                        int i2 = (cCharAt >> 4) & 15;
                        char c2 = (char) (cCharAt & 15);
                        stringBuffer.append((char) (i2 < 10 ? i2 + 48 : (i2 - 10) + 65));
                        stringBuffer.append((char) (c2 < '\n' ? c2 + '0' : (c2 - '\n') + 65));
                    } else {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer(str.length() + 2);
                            stringBuffer.append(str.substring(0, i));
                        }
                        stringBuffer.append('\\');
                        stringBuffer.append(c);
                    }
                } else if (stringBuffer != null) {
                    stringBuffer.append(cCharAt);
                }
            } else if (stringBuffer != null) {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer == null ? str : stringBuffer.toString();
    }

    public static String FTLStringLiteralDec(String str) throws ParseException {
        int i;
        int i2;
        int i3;
        int iIndexOf = str.indexOf(92);
        if (iIndexOf == -1) {
            return str;
        }
        int length = str.length() - 1;
        StringBuffer stringBuffer = new StringBuffer(length);
        int i4 = 0;
        do {
            stringBuffer.append(str.substring(i4, iIndexOf));
            if (iIndexOf >= length) {
                throw new ParseException("The last character of string literal is backslash", 0, 0);
            }
            char cCharAt = str.charAt(iIndexOf + 1);
            if (cCharAt == '\"') {
                stringBuffer.append(Typography.quote);
            } else if (cCharAt == '\'') {
                stringBuffer.append('\'');
            } else if (cCharAt == '\\') {
                stringBuffer.append('\\');
            } else if (cCharAt == 'l') {
                stringBuffer.append(Typography.less);
            } else if (cCharAt == 'n') {
                stringBuffer.append('\n');
            } else if (cCharAt == 'r') {
                stringBuffer.append(CharUtils.CR);
            } else if (cCharAt == 't') {
                stringBuffer.append('\t');
            } else if (cCharAt == 'x') {
                int i5 = iIndexOf + 2;
                int i6 = i5 + 3;
                if (length <= i6) {
                    i6 = length;
                }
                int i7 = i5;
                int i8 = 0;
                while (i7 <= i6) {
                    char cCharAt2 = str.charAt(i7);
                    if (cCharAt2 < '0' || cCharAt2 > '9') {
                        if (cCharAt2 >= 'a' && cCharAt2 <= 'f') {
                            i = i8 << 4;
                            i2 = cCharAt2 - 'a';
                        } else {
                            if (cCharAt2 < 'A' || cCharAt2 > 'F') {
                                break;
                            }
                            i = i8 << 4;
                            i2 = cCharAt2 - 'A';
                        }
                        i3 = i2 + 10;
                    } else {
                        i = i8 << 4;
                        i3 = cCharAt2 - '0';
                    }
                    i8 = i + i3;
                    i7++;
                }
                if (i5 < i7) {
                    stringBuffer.append((char) i8);
                    i4 = i7;
                    iIndexOf = str.indexOf(92, i4);
                } else {
                    throw new ParseException("Invalid \\x escape in a string literal", 0, 0);
                }
            } else if (cCharAt == '{') {
                stringBuffer.append('{');
            } else if (cCharAt == 'a') {
                stringBuffer.append(Typography.amp);
            } else if (cCharAt == 'b') {
                stringBuffer.append('\b');
            } else if (cCharAt == 'f') {
                stringBuffer.append('\f');
            } else if (cCharAt == 'g') {
                stringBuffer.append(Typography.greater);
            } else {
                throw new ParseException(new StringBuffer("Invalid escape sequence (\\").append(cCharAt).append(") in a string literal").toString(), 0, 0);
            }
            i4 = iIndexOf + 2;
            iIndexOf = str.indexOf(92, i4);
        } while (iIndexOf != -1);
        stringBuffer.append(str.substring(i4));
        return stringBuffer.toString();
    }

    public static Locale deduceLocale(String str) {
        if (str == null) {
            return null;
        }
        Locale.getDefault();
        if (str.length() > 0 && str.charAt(0) == '\"') {
            str = str.substring(1, str.length() - 1);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",_ ");
        String strNextToken = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
        String strNextToken2 = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken() : "";
        if (!stringTokenizer.hasMoreTokens()) {
            return new Locale(strNextToken, strNextToken2);
        }
        return new Locale(strNextToken, strNextToken2, stringTokenizer.nextToken());
    }

    public static String capitalize(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, " \t\r\n", true);
        StringBuffer stringBuffer = new StringBuffer(str.length());
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            stringBuffer.append(strNextToken.substring(0, 1).toUpperCase());
            stringBuffer.append(strNextToken.substring(1).toLowerCase());
        }
        return stringBuffer.toString();
    }

    public static boolean getYesNo(String str) {
        if (str.startsWith("\"")) {
            str = str.substring(1, str.length() - 1);
        }
        if (str.equalsIgnoreCase("n") || str.equalsIgnoreCase(BooleanUtils.NO) || str.equalsIgnoreCase("f") || str.equalsIgnoreCase(BooleanUtils.FALSE)) {
            return false;
        }
        if (str.equalsIgnoreCase("y") || str.equalsIgnoreCase(BooleanUtils.YES) || str.equalsIgnoreCase("t") || str.equalsIgnoreCase(BooleanUtils.TRUE)) {
            return true;
        }
        throw new IllegalArgumentException(new StringBuffer("Illegal boolean value: ").append(str).toString());
    }

    public static String[] split(String str, char c) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        int i3 = 1;
        while (true) {
            int iIndexOf = str.indexOf(c, i2);
            if (iIndexOf == -1) {
                break;
            }
            i3++;
            i2 = iIndexOf + 1;
        }
        String[] strArr = new String[i3];
        int i4 = 0;
        while (i <= length) {
            int iIndexOf2 = str.indexOf(c, i);
            if (iIndexOf2 == -1) {
                iIndexOf2 = length;
            }
            strArr[i4] = str.substring(i, iIndexOf2);
            i = iIndexOf2 + 1;
            i4++;
        }
        return strArr;
    }

    public static String[] split(String str, String str2, boolean z) {
        String lowerCase = z ? str2.toLowerCase() : str2;
        String lowerCase2 = z ? str.toLowerCase() : str;
        int length = str.length();
        int length2 = str2.length();
        if (length2 == 0) {
            throw new IllegalArgumentException("The separator string has 0 length");
        }
        int i = 1;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int iIndexOf = lowerCase2.indexOf(lowerCase, i3);
            if (iIndexOf == -1) {
                break;
            }
            i++;
            i3 = iIndexOf + length2;
        }
        String[] strArr = new String[i];
        int i4 = 0;
        while (i2 <= length) {
            int iIndexOf2 = lowerCase2.indexOf(lowerCase, i2);
            if (iIndexOf2 == -1) {
                iIndexOf2 = length;
            }
            strArr[i4] = str.substring(i2, iIndexOf2);
            i2 = iIndexOf2 + length2;
            i4++;
        }
        return strArr;
    }

    public static String replace(String str, String str2, String str3) {
        return replace(str, str2, str3, false, false);
    }

    public static String replace(String str, String str2, String str3, boolean z, boolean z2) {
        int length = str2.length();
        int i = 0;
        if (length == 0) {
            int length2 = str3.length();
            if (length2 == 0) {
                return str;
            }
            if (z2) {
                return new StringBuffer().append(str3).append(str).toString();
            }
            int length3 = str.length();
            StringBuffer stringBuffer = new StringBuffer(((length3 + 1) * length2) + length3);
            stringBuffer.append(str3);
            while (i < length3) {
                stringBuffer.append(str.charAt(i));
                stringBuffer.append(str3);
                i++;
            }
            return stringBuffer.toString();
        }
        if (z) {
            str2 = str2.toLowerCase();
        }
        String lowerCase = z ? str.toLowerCase() : str;
        int iIndexOf = lowerCase.indexOf(str2);
        if (iIndexOf == -1) {
            return str;
        }
        StringBuffer stringBuffer2 = new StringBuffer(str.length() + (Math.max(str3.length() - length, 0) * 3));
        do {
            stringBuffer2.append(str.substring(i, iIndexOf));
            stringBuffer2.append(str3);
            i = iIndexOf + length;
            iIndexOf = lowerCase.indexOf(str2, i);
            if (iIndexOf == -1) {
                break;
            }
        } while (!z2);
        stringBuffer2.append(str.substring(i));
        return stringBuffer2.toString();
    }

    public static String chomp(String str) {
        return str.endsWith("\r\n") ? str.substring(0, str.length() - 2) : (str.endsWith(StringUtils.CR) || str.endsWith(StringUtils.LF)) ? str.substring(0, str.length() - 1) : str;
    }

    public static String emptyToNull(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return str;
    }

    public static String jQuote(Object obj) {
        return jQuote(obj != null ? obj.toString() : null);
    }

    public static String jQuote(String str) {
        if (str == null) {
            return "null";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append(Typography.quote);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                stringBuffer.append("\\\"");
            } else if (cCharAt == '\\') {
                stringBuffer.append("\\\\");
            } else if (cCharAt >= ' ') {
                stringBuffer.append(cCharAt);
            } else if (cCharAt == '\n') {
                stringBuffer.append("\\n");
            } else if (cCharAt == '\r') {
                stringBuffer.append("\\r");
            } else if (cCharAt == '\f') {
                stringBuffer.append("\\f");
            } else if (cCharAt == '\b') {
                stringBuffer.append("\\b");
            } else if (cCharAt == '\t') {
                stringBuffer.append("\\t");
            } else {
                stringBuffer.append("\\u00");
                stringBuffer.append(toHexDigit(cCharAt / 16));
                stringBuffer.append(toHexDigit(cCharAt & 15));
            }
        }
        stringBuffer.append(Typography.quote);
        return stringBuffer.toString();
    }

    public static String jQuoteNoXSS(Object obj) {
        return jQuoteNoXSS(obj != null ? obj.toString() : null);
    }

    public static String jQuoteNoXSS(String str) {
        if (str == null) {
            return "null";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append(Typography.quote);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                stringBuffer.append("\\\"");
            } else if (cCharAt == '\\') {
                stringBuffer.append("\\\\");
            } else if (cCharAt == '<') {
                stringBuffer.append("\\u003C");
            } else if (cCharAt >= ' ') {
                stringBuffer.append(cCharAt);
            } else if (cCharAt == '\n') {
                stringBuffer.append("\\n");
            } else if (cCharAt == '\r') {
                stringBuffer.append("\\r");
            } else if (cCharAt == '\f') {
                stringBuffer.append("\\f");
            } else if (cCharAt == '\b') {
                stringBuffer.append("\\b");
            } else if (cCharAt == '\t') {
                stringBuffer.append("\\t");
            } else {
                stringBuffer.append("\\u00");
                stringBuffer.append(toHexDigit(cCharAt / 16));
                stringBuffer.append(toHexDigit(cCharAt & 15));
            }
        }
        stringBuffer.append(Typography.quote);
        return stringBuffer.toString();
    }

    public static String javaStringEnc(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"' || cCharAt == '\\' || cCharAt < ' ') {
                StringBuffer stringBuffer = new StringBuffer(length + 4);
                stringBuffer.append(str.substring(0, i));
                while (true) {
                    if (cCharAt == '\"') {
                        stringBuffer.append("\\\"");
                    } else if (cCharAt == '\\') {
                        stringBuffer.append("\\\\");
                    } else if (cCharAt >= ' ') {
                        stringBuffer.append(cCharAt);
                    } else if (cCharAt == '\n') {
                        stringBuffer.append("\\n");
                    } else if (cCharAt == '\r') {
                        stringBuffer.append("\\r");
                    } else if (cCharAt == '\f') {
                        stringBuffer.append("\\f");
                    } else if (cCharAt == '\b') {
                        stringBuffer.append("\\b");
                    } else if (cCharAt == '\t') {
                        stringBuffer.append("\\t");
                    } else {
                        stringBuffer.append("\\u00");
                        int i2 = cCharAt / 16;
                        stringBuffer.append((char) (i2 < 10 ? i2 + 48 : (i2 - 10) + 97));
                        int i3 = cCharAt & 15;
                        stringBuffer.append((char) (i3 < 10 ? i3 + 48 : (i3 - 10) + 97));
                    }
                    i++;
                    if (i >= length) {
                        return stringBuffer.toString();
                    }
                    cCharAt = str.charAt(i);
                }
            } else {
                i++;
            }
        }
        return str;
    }

    public static String javaScriptStringEnc(String str) {
        return jsStringEnc(str, false);
    }

    public static String jsonStringEnc(String str) {
        return jsStringEnc(str, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String jsStringEnc(java.lang.String r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil.jsStringEnc(java.lang.String, boolean):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a3, code lost:
    
        throw new java.text.ParseException(new java.lang.StringBuffer("Expecting \":\" here, but found ").append(jQuote(java.lang.String.valueOf(r2))).append(" at position ").append(r4).append(".").toString(), r4);
     */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0118 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.Map parseNameValuePairList(java.lang.String r12, java.lang.String r13) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 412
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil.parseNameValuePairList(java.lang.String, java.lang.String):java.util.Map");
    }

    public static boolean isXMLID(String str) {
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (i == 0 && (cCharAt == '-' || cCharAt == '.' || Character.isDigit(cCharAt))) {
                return false;
            }
            if (!Character.isLetterOrDigit(cCharAt) && cCharAt != ':' && cCharAt != '_' && cCharAt != '-' && cCharAt != '.') {
                return false;
            }
        }
        return true;
    }

    public static boolean matchesName(String str, String str2, String str3, Environment environment) {
        String defaultNS = environment.getDefaultNS();
        if (defaultNS != null && defaultNS.equals(str3)) {
            return str.equals(str2) || str.equals(new StringBuffer("D:").append(str2).toString());
        }
        if ("".equals(str3)) {
            if (defaultNS != null) {
                return str.equals(new StringBuffer("N:").append(str2).toString());
            }
            return str.equals(str2) || str.equals(new StringBuffer("N:").append(str2).toString());
        }
        String prefixForNamespace = environment.getPrefixForNamespace(str3);
        if (prefixForNamespace == null) {
            return false;
        }
        return str.equals(new StringBuffer().append(prefixForNamespace).append(":").append(str2).toString());
    }

    public static String leftPad(String str, int i) {
        return leftPad(str, i, ' ');
    }

    public static String leftPad(String str, int i, char c) {
        int length = str.length();
        if (i <= length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        int i2 = i - length;
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append(c);
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public static String leftPad(String str, int i, String str2) {
        int length = str.length();
        if (i <= length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        int i2 = i - length;
        int length2 = str2.length();
        if (length2 == 0) {
            throw new IllegalArgumentException("The \"filling\" argument can't be 0 length string.");
        }
        int i3 = i2 / length2;
        for (int i4 = 0; i4 < i3; i4++) {
            stringBuffer.append(str2);
        }
        int i5 = i2 % length2;
        for (int i6 = 0; i6 < i5; i6++) {
            stringBuffer.append(str2.charAt(i6));
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public static String rightPad(String str, int i) {
        return rightPad(str, i, ' ');
    }

    public static String rightPad(String str, int i, char c) {
        int length = str.length();
        if (i <= length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int i2 = i - length;
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public static String rightPad(String str, int i, String str2) {
        int length = str.length();
        if (i <= length) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int i2 = i - length;
        int length2 = str2.length();
        if (length2 == 0) {
            throw new IllegalArgumentException("The \"filling\" argument can't be 0 length string.");
        }
        int i3 = length % length2;
        int i4 = length2 - i3 <= i2 ? length2 : i3 + i2;
        for (int i5 = i3; i5 < i4; i5++) {
            stringBuffer.append(str2.charAt(i5));
        }
        int i6 = i2 - (i4 - i3);
        int i7 = i6 / length2;
        for (int i8 = 0; i8 < i7; i8++) {
            stringBuffer.append(str2);
        }
        int i9 = i6 % length2;
        for (int i10 = 0; i10 < i9; i10++) {
            stringBuffer.append(str2.charAt(i10));
        }
        return stringBuffer.toString();
    }

    public static int versionStringToInt(String str) {
        return new Version(str).intValue();
    }

    public static String tryToString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj.toString();
        } catch (Throwable th) {
            return failedToStringSubstitute(th);
        }
    }

    private static String failedToStringSubstitute(Throwable th) {
        String shortClassNameOfObject;
        try {
            shortClassNameOfObject = th.toString();
        } catch (Throwable unused) {
            shortClassNameOfObject = ClassUtil.getShortClassNameOfObject(th);
        }
        return new StringBuffer("[toString() failed: ").append(shortClassNameOfObject).append("]").toString();
    }
}
