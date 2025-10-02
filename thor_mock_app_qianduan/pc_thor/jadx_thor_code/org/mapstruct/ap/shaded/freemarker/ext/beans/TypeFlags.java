package org.mapstruct.ap.shaded.freemarker.ext.beans;

/* loaded from: classes3.dex */
class TypeFlags {
    static final int ACCEPTS_ANY_OBJECT = 522240;
    static final int ACCEPTS_ARRAY = 262144;
    static final int ACCEPTS_BOOLEAN = 16384;
    static final int ACCEPTS_DATE = 4096;
    static final int ACCEPTS_LIST = 65536;
    static final int ACCEPTS_MAP = 32768;
    static final int ACCEPTS_NUMBER = 2048;
    static final int ACCEPTS_SET = 131072;
    static final int ACCEPTS_STRING = 8192;
    static final int BIG_DECIMAL = 512;
    static final int BIG_INTEGER = 256;
    static final int BYTE = 4;
    static final int CHARACTER = 524288;
    static final int DOUBLE = 128;
    static final int FLOAT = 64;
    static final int INTEGER = 16;
    static final int LONG = 32;
    static final int MASK_ALL_KNOWN_NUMERICALS = 1020;
    static final int MASK_ALL_NUMERICALS = 2044;
    static final int MASK_KNOWN_INTEGERS = 316;
    static final int MASK_KNOWN_NONINTEGERS = 704;
    static final int SHORT = 8;
    static final int UNKNOWN_NUMERICAL_TYPE = 1024;
    static final int WIDENED_NUMERICAL_UNWRAPPING_HINT = 1;
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Number;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$math$BigDecimal;
    static /* synthetic */ Class class$java$math$BigInteger;
    static /* synthetic */ Class class$java$util$Date;
    static /* synthetic */ Class class$java$util$List;
    static /* synthetic */ Class class$java$util$Map;
    static /* synthetic */ Class class$java$util$Set;

    TypeFlags() {
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static int classToTypeFlags(Class cls) throws Throwable {
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Object");
            class$java$lang$Object = clsClass$;
        }
        if (cls == clsClass$) {
            return ACCEPTS_ANY_OBJECT;
        }
        Class clsClass$2 = class$java$lang$String;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.lang.String");
            class$java$lang$String = clsClass$2;
        }
        if (cls == clsClass$2) {
            return 8192;
        }
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                return 2064;
            }
            if (cls == Long.TYPE) {
                return 2080;
            }
            if (cls == Double.TYPE) {
                return 2176;
            }
            if (cls == Float.TYPE) {
                return 2112;
            }
            if (cls == Byte.TYPE) {
                return 2052;
            }
            if (cls == Short.TYPE) {
                return 2056;
            }
            if (cls == Character.TYPE) {
                return 524288;
            }
            return cls == Boolean.TYPE ? 16384 : 0;
        }
        Class clsClass$3 = class$java$lang$Number;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.lang.Number");
            class$java$lang$Number = clsClass$3;
        }
        if (clsClass$3.isAssignableFrom(cls)) {
            Class clsClass$4 = class$java$lang$Integer;
            if (clsClass$4 == null) {
                clsClass$4 = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$4;
            }
            if (cls == clsClass$4) {
                return 2064;
            }
            Class clsClass$5 = class$java$lang$Long;
            if (clsClass$5 == null) {
                clsClass$5 = class$("java.lang.Long");
                class$java$lang$Long = clsClass$5;
            }
            if (cls == clsClass$5) {
                return 2080;
            }
            Class clsClass$6 = class$java$lang$Double;
            if (clsClass$6 == null) {
                clsClass$6 = class$("java.lang.Double");
                class$java$lang$Double = clsClass$6;
            }
            if (cls == clsClass$6) {
                return 2176;
            }
            Class clsClass$7 = class$java$lang$Float;
            if (clsClass$7 == null) {
                clsClass$7 = class$("java.lang.Float");
                class$java$lang$Float = clsClass$7;
            }
            if (cls == clsClass$7) {
                return 2112;
            }
            Class clsClass$8 = class$java$lang$Byte;
            if (clsClass$8 == null) {
                clsClass$8 = class$("java.lang.Byte");
                class$java$lang$Byte = clsClass$8;
            }
            if (cls == clsClass$8) {
                return 2052;
            }
            Class clsClass$9 = class$java$lang$Short;
            if (clsClass$9 == null) {
                clsClass$9 = class$("java.lang.Short");
                class$java$lang$Short = clsClass$9;
            }
            if (cls == clsClass$9) {
                return 2056;
            }
            Class clsClass$10 = class$java$math$BigDecimal;
            if (clsClass$10 == null) {
                clsClass$10 = class$("java.math.BigDecimal");
                class$java$math$BigDecimal = clsClass$10;
            }
            if (clsClass$10.isAssignableFrom(cls)) {
                return 2560;
            }
            Class clsClass$11 = class$java$math$BigInteger;
            if (clsClass$11 == null) {
                clsClass$11 = class$("java.math.BigInteger");
                class$java$math$BigInteger = clsClass$11;
            }
            return clsClass$11.isAssignableFrom(cls) ? 2304 : 3072;
        }
        if (cls.isArray()) {
            return 262144;
        }
        Class<?> clsClass$12 = class$java$lang$String;
        if (clsClass$12 == null) {
            clsClass$12 = class$("java.lang.String");
            class$java$lang$String = clsClass$12;
        }
        int i = cls.isAssignableFrom(clsClass$12) ? 8192 : 0;
        Class<?> clsClass$13 = class$java$util$Date;
        if (clsClass$13 == null) {
            clsClass$13 = class$("java.util.Date");
            class$java$util$Date = clsClass$13;
        }
        if (cls.isAssignableFrom(clsClass$13)) {
            i |= 4096;
        }
        Class<?> clsClass$14 = class$java$lang$Boolean;
        if (clsClass$14 == null) {
            clsClass$14 = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$14;
        }
        if (cls.isAssignableFrom(clsClass$14)) {
            i |= 16384;
        }
        Class<?> clsClass$15 = class$java$util$Map;
        if (clsClass$15 == null) {
            clsClass$15 = class$("java.util.Map");
            class$java$util$Map = clsClass$15;
        }
        if (cls.isAssignableFrom(clsClass$15)) {
            i |= 32768;
        }
        Class<?> clsClass$16 = class$java$util$List;
        if (clsClass$16 == null) {
            clsClass$16 = class$("java.util.List");
            class$java$util$List = clsClass$16;
        }
        if (cls.isAssignableFrom(clsClass$16)) {
            i |= 65536;
        }
        Class<?> clsClass$17 = class$java$util$Set;
        if (clsClass$17 == null) {
            clsClass$17 = class$("java.util.Set");
            class$java$util$Set = clsClass$17;
        }
        if (cls.isAssignableFrom(clsClass$17)) {
            i |= 131072;
        }
        Class clsClass$18 = class$java$lang$Character;
        if (clsClass$18 == null) {
            clsClass$18 = class$("java.lang.Character");
            class$java$lang$Character = clsClass$18;
        }
        return cls == clsClass$18 ? i | 524288 : i;
    }
}
