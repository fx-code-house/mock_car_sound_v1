package com.google.android.gms.internal.clearcut;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class zzga {
    public static <T extends zzfz> String zza(T t) throws SecurityException, IllegalArgumentException {
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            String strValueOf = String.valueOf(e.getMessage());
            return strValueOf.length() != 0 ? "Error printing proto: ".concat(strValueOf) : new String("Error printing proto: ");
        } catch (InvocationTargetException e2) {
            String strValueOf2 = String.valueOf(e2.getMessage());
            return strValueOf2.length() != 0 ? "Error printing proto: ".concat(strValueOf2) : new String("Error printing proto: ");
        }
    }

    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (obj != null) {
            if (!(obj instanceof zzfz)) {
                stringBuffer2.append(stringBuffer).append(zzl(str)).append(": ");
                if (obj instanceof String) {
                    String strConcat = (String) obj;
                    if (!strConcat.startsWith("http") && strConcat.length() > 200) {
                        strConcat = String.valueOf(strConcat.substring(0, 200)).concat("[...]");
                    }
                    int length = strConcat.length();
                    StringBuilder sb = new StringBuilder(length);
                    for (int i = 0; i < length; i++) {
                        char cCharAt = strConcat.charAt(i);
                        if (cCharAt < ' ' || cCharAt > '~' || cCharAt == '\"' || cCharAt == '\'') {
                            sb.append(String.format("\\u%04x", Integer.valueOf(cCharAt)));
                        } else {
                            sb.append(cCharAt);
                        }
                    }
                    stringBuffer2.append("\"").append(sb.toString()).append("\"");
                } else if (obj instanceof byte[]) {
                    byte[] bArr = (byte[]) obj;
                    if (bArr == null) {
                        stringBuffer2.append("\"\"");
                    } else {
                        stringBuffer2.append(Typography.quote);
                        for (byte b : bArr) {
                            int i2 = b & 255;
                            if (i2 == 92 || i2 == 34) {
                                stringBuffer2.append('\\').append((char) i2);
                            } else if (i2 < 32 || i2 >= 127) {
                                stringBuffer2.append(String.format("\\%03o", Integer.valueOf(i2)));
                            } else {
                                stringBuffer2.append((char) i2);
                            }
                        }
                        stringBuffer2.append(Typography.quote);
                    }
                } else {
                    stringBuffer2.append(obj);
                }
                stringBuffer2.append(StringUtils.LF);
                return;
            }
            int length2 = stringBuffer.length();
            if (str != null) {
                stringBuffer2.append(stringBuffer).append(zzl(str)).append(" <\n");
                stringBuffer.append("  ");
            }
            Class<?> cls = obj.getClass();
            for (Field field : cls.getFields()) {
                int modifiers = field.getModifiers();
                String name = field.getName();
                if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith("_") && !name.endsWith("_")) {
                    Class<?> type = field.getType();
                    Object obj2 = field.get(obj);
                    if (!type.isArray() || type.getComponentType() == Byte.TYPE) {
                        zza(name, obj2, stringBuffer, stringBuffer2);
                    } else {
                        int length3 = obj2 == null ? 0 : Array.getLength(obj2);
                        for (int i3 = 0; i3 < length3; i3++) {
                            zza(name, Array.get(obj2, i3), stringBuffer, stringBuffer2);
                        }
                    }
                }
            }
            for (Method method : cls.getMethods()) {
                String name2 = method.getName();
                if (name2.startsWith("set")) {
                    String strSubstring = name2.substring(3);
                    try {
                        String strValueOf = String.valueOf(strSubstring);
                        if (((Boolean) cls.getMethod(strValueOf.length() != 0 ? "has".concat(strValueOf) : new String("has"), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                            String strValueOf2 = String.valueOf(strSubstring);
                            zza(strSubstring, cls.getMethod(strValueOf2.length() != 0 ? "get".concat(strValueOf2) : new String("get"), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                        }
                    } catch (NoSuchMethodException unused) {
                    }
                }
            }
            if (str != null) {
                stringBuffer.setLength(length2);
                stringBuffer2.append(stringBuffer).append(">\n");
            }
        }
    }

    private static String zzl(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (i == 0) {
                cCharAt = Character.toLowerCase(cCharAt);
            } else {
                if (Character.isUpperCase(cCharAt)) {
                    stringBuffer.append('_').append(Character.toLowerCase(cCharAt));
                }
            }
            stringBuffer.append(cCharAt);
        }
        return stringBuffer.toString();
    }
}
