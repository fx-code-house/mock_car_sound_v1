package org.mapstruct.ap.internal.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.lang.model.type.TypeKind;
import org.apache.commons.lang3.BooleanUtils;

/* loaded from: classes3.dex */
public class NativeTypes {
    private static final Map<String, LiteralAnalyzer> ANALYZERS;
    private static final Map<String, Integer> NARROWING_LUT;
    private static final Set<Class<?>> NUMBER_TYPES;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPES;
    private static final Pattern PTRN_BIN;
    private static final Pattern PTRN_DOUBLE;
    private static final Pattern PTRN_FAULTY_DEC_UNDERSCORE_FLOAT;
    private static final Pattern PTRN_FAULTY_HEX_UNDERSCORE_FLOAT;
    private static final Pattern PTRN_FAULTY_UNDERSCORE_FLOAT;
    private static final Pattern PTRN_FAULTY_UNDERSCORE_INT;
    private static final Pattern PTRN_FLOAT;
    private static final Pattern PTRN_FLOAT_DEC_ZERO;
    private static final Pattern PTRN_FLOAT_HEX_ZERO;
    private static final Pattern PTRN_HEX;
    private static final Pattern PTRN_LONG;
    private static final Pattern PTRN_OCT;
    private static final Pattern PTRN_SIGN;
    private static final Map<TypeKind, String> TYPE_KIND_NAME;
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPES;

    private interface LiteralAnalyzer {
        Class<?> getLiteral();

        void validate(String str);
    }

    static {
        HashSet hashSet = new HashSet();
        NUMBER_TYPES = hashSet;
        EnumMap enumMap = new EnumMap(TypeKind.class);
        TYPE_KIND_NAME = enumMap;
        PTRN_HEX = Pattern.compile("^0[x|X].*");
        PTRN_OCT = Pattern.compile("^0_*[0-7].*");
        PTRN_BIN = Pattern.compile("^0[b|B].*");
        PTRN_FLOAT_DEC_ZERO = Pattern.compile("^[^eE]*[1-9].*[eE]?.*");
        PTRN_FLOAT_HEX_ZERO = Pattern.compile("^[^pP]*[1-9a-fA-F].*[pP]?.*");
        PTRN_SIGN = Pattern.compile("^[\\+|-]");
        PTRN_LONG = Pattern.compile("[l|L]$");
        PTRN_FLOAT = Pattern.compile("[f|F]$");
        PTRN_DOUBLE = Pattern.compile("[d|D]$");
        PTRN_FAULTY_UNDERSCORE_INT = Pattern.compile("^_|_$|-_|_-|\\+_|_\\+");
        PTRN_FAULTY_UNDERSCORE_FLOAT = Pattern.compile("^_|_$|-_|_-|\\+_|_\\+|\\._|_\\.");
        PTRN_FAULTY_DEC_UNDERSCORE_FLOAT = Pattern.compile("_e|_E|e_|E_");
        PTRN_FAULTY_HEX_UNDERSCORE_FLOAT = Pattern.compile("_p|_P|p_|P_");
        HashMap map = new HashMap();
        map.put(Byte.class, Byte.TYPE);
        map.put(Short.class, Short.TYPE);
        map.put(Integer.class, Integer.TYPE);
        map.put(Long.class, Long.TYPE);
        map.put(Float.class, Float.TYPE);
        map.put(Double.class, Double.TYPE);
        map.put(Boolean.class, Boolean.TYPE);
        map.put(Character.class, Character.TYPE);
        WRAPPER_TO_PRIMITIVE_TYPES = java.util.Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put(Byte.TYPE, Byte.class);
        map2.put(Short.TYPE, Short.class);
        map2.put(Integer.TYPE, Integer.class);
        map2.put(Long.TYPE, Long.class);
        map2.put(Float.TYPE, Float.class);
        map2.put(Double.TYPE, Double.class);
        map2.put(Boolean.TYPE, Boolean.class);
        map2.put(Character.TYPE, Character.class);
        PRIMITIVE_TO_WRAPPER_TYPES = java.util.Collections.unmodifiableMap(map2);
        hashSet.add(Byte.TYPE);
        hashSet.add(Short.TYPE);
        hashSet.add(Integer.TYPE);
        hashSet.add(Long.TYPE);
        hashSet.add(Float.TYPE);
        hashSet.add(Double.TYPE);
        hashSet.add(Byte.class);
        hashSet.add(Short.class);
        hashSet.add(Integer.class);
        hashSet.add(Long.class);
        hashSet.add(Float.class);
        hashSet.add(Double.class);
        hashSet.add(BigInteger.class);
        hashSet.add(BigDecimal.class);
        HashMap map3 = new HashMap();
        map3.put(Boolean.TYPE.getCanonicalName(), new BooleanAnalyzer());
        map3.put(Boolean.class.getCanonicalName(), new BooleanAnalyzer());
        map3.put(Character.TYPE.getCanonicalName(), new CharAnalyzer());
        map3.put(Character.class.getCanonicalName(), new CharAnalyzer());
        map3.put(Byte.TYPE.getCanonicalName(), new ByteAnalyzer());
        map3.put(Byte.class.getCanonicalName(), new ByteAnalyzer());
        map3.put(Double.TYPE.getCanonicalName(), new DoubleAnalyzer());
        map3.put(Double.class.getCanonicalName(), new DoubleAnalyzer());
        map3.put(Float.TYPE.getCanonicalName(), new FloatAnalyzer());
        map3.put(Float.class.getCanonicalName(), new FloatAnalyzer());
        map3.put(Integer.TYPE.getCanonicalName(), new IntAnalyzer());
        map3.put(Integer.class.getCanonicalName(), new IntAnalyzer());
        map3.put(Long.TYPE.getCanonicalName(), new LongAnalyzer());
        map3.put(Long.class.getCanonicalName(), new LongAnalyzer());
        map3.put(Short.TYPE.getCanonicalName(), new ShortAnalyzer());
        map3.put(Short.class.getCanonicalName(), new ShortAnalyzer());
        ANALYZERS = java.util.Collections.unmodifiableMap(map3);
        enumMap.put((EnumMap) TypeKind.BOOLEAN, (TypeKind) "boolean");
        enumMap.put((EnumMap) TypeKind.BYTE, (TypeKind) "byte");
        enumMap.put((EnumMap) TypeKind.SHORT, (TypeKind) "short");
        enumMap.put((EnumMap) TypeKind.INT, (TypeKind) "int");
        enumMap.put((EnumMap) TypeKind.LONG, (TypeKind) "long");
        enumMap.put((EnumMap) TypeKind.CHAR, (TypeKind) "char");
        enumMap.put((EnumMap) TypeKind.FLOAT, (TypeKind) "float");
        enumMap.put((EnumMap) TypeKind.DOUBLE, (TypeKind) "double");
        HashMap map4 = new HashMap();
        map4.put(Byte.TYPE.getName(), 1);
        map4.put(Byte.class.getName(), 1);
        map4.put(Short.TYPE.getName(), 2);
        map4.put(Short.class.getName(), 2);
        map4.put(Integer.TYPE.getName(), 3);
        map4.put(Integer.class.getName(), 3);
        map4.put(Long.TYPE.getName(), 4);
        map4.put(Long.class.getName(), 4);
        map4.put(Float.TYPE.getName(), 5);
        map4.put(Float.class.getName(), 5);
        map4.put(Double.TYPE.getName(), 6);
        map4.put(Double.class.getName(), 6);
        map4.put(BigInteger.class.getName(), 50);
        map4.put(BigDecimal.class.getName(), 51);
        NARROWING_LUT = java.util.Collections.unmodifiableMap(map4);
    }

    private static abstract class NumberRepresentation {
        boolean isFloat;
        boolean isIntegralType;
        boolean isLong;
        int radix;
        String val;

        abstract void parse(String str, int i);

        NumberRepresentation(String str, boolean z, boolean z2, boolean z3) {
            this.isLong = z2;
            this.isFloat = z3;
            this.isIntegralType = z;
            boolean zStartsWith = str.startsWith("-");
            str = NativeTypes.PTRN_SIGN.matcher(str).find() ? str.substring(1) : str;
            if (!NativeTypes.PTRN_HEX.matcher(str).matches()) {
                if (!NativeTypes.PTRN_BIN.matcher(str).matches()) {
                    if (NativeTypes.PTRN_OCT.matcher(str).matches()) {
                        this.radix = 8;
                        this.val = (zStartsWith ? "-" : "") + str.substring(1);
                        return;
                    } else {
                        this.radix = 10;
                        this.val = (zStartsWith ? "-" : "") + str;
                        return;
                    }
                }
                this.radix = 2;
                this.val = (zStartsWith ? "-" : "") + str.substring(2);
                return;
            }
            this.radix = 16;
            this.val = (zStartsWith ? "-" : "") + str.substring(2);
        }

        void validate() {
            strip();
            parse(this.val, this.radix);
        }

        void strip() {
            if (this.isIntegralType) {
                removeAndValidateIntegerLiteralSuffix();
                removeAndValidateIntegerLiteralUnderscore();
            } else {
                removeAndValidateFloatingPointLiteralSuffix();
                removeAndValidateFloatingPointLiteralUnderscore();
            }
        }

        void removeAndValidateIntegerLiteralUnderscore() {
            if (NativeTypes.PTRN_FAULTY_UNDERSCORE_INT.matcher(this.val).find()) {
                throw new NumberFormatException("improperly placed underscores");
            }
            this.val = this.val.replace("_", "");
        }

        void removeAndValidateFloatingPointLiteralUnderscore() {
            boolean z = this.radix == 16;
            if (NativeTypes.PTRN_FAULTY_UNDERSCORE_FLOAT.matcher(this.val).find() || ((!z && NativeTypes.PTRN_FAULTY_DEC_UNDERSCORE_FLOAT.matcher(this.val).find()) || (z && NativeTypes.PTRN_FAULTY_HEX_UNDERSCORE_FLOAT.matcher(this.val).find()))) {
                throw new NumberFormatException("improperly placed underscores");
            }
            this.val = this.val.replace("_", "");
        }

        void removeAndValidateIntegerLiteralSuffix() {
            boolean zFind = NativeTypes.PTRN_LONG.matcher(this.val).find();
            if (zFind && !this.isLong) {
                throw new NumberFormatException("L/l not allowed for non-long types");
            }
            if (!zFind && this.isLong) {
                throw new NumberFormatException("L/l mandatory for long types");
            }
            if (zFind) {
                this.val = this.val.substring(0, r0.length() - 1);
            }
        }

        void removeAndValidateFloatingPointLiteralSuffix() {
            boolean zFind = NativeTypes.PTRN_LONG.matcher(this.val).find();
            boolean zFind2 = NativeTypes.PTRN_FLOAT.matcher(this.val).find();
            boolean zFind3 = NativeTypes.PTRN_DOUBLE.matcher(this.val).find();
            if (this.isFloat && zFind3) {
                throw new NumberFormatException("Assiging double to a float");
            }
            if (zFind || zFind2 || zFind3) {
                this.val = this.val.substring(0, r0.length() - 1);
            }
        }

        boolean floatHasBecomeZero(float f) {
            if (f == 0.0f) {
                return floatHasBecomeZero();
            }
            return false;
        }

        boolean doubleHasBecomeZero(double d) {
            if (d == 0.0d) {
                return floatHasBecomeZero();
            }
            return false;
        }

        private boolean floatHasBecomeZero() {
            return this.radix == 10 ? NativeTypes.PTRN_FLOAT_DEC_ZERO.matcher(this.val).matches() : NativeTypes.PTRN_FLOAT_HEX_ZERO.matcher(this.val).matches();
        }
    }

    private static class BooleanAnalyzer implements LiteralAnalyzer {
        private BooleanAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            if (!BooleanUtils.TRUE.equals(str) && !BooleanUtils.FALSE.equals(str)) {
                throw new IllegalArgumentException("only 'true' or 'false' are supported");
            }
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Boolean.TYPE;
        }
    }

    private static class CharAnalyzer implements LiteralAnalyzer {
        private CharAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            if (str.length() != 3 || !str.startsWith("'") || !str.endsWith("'")) {
                throw new NumberFormatException("invalid character literal");
            }
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Character.TYPE;
        }
    }

    private static class ByteAnalyzer implements LiteralAnalyzer {
        private ByteAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            new NumberRepresentation(str, true, false, false) { // from class: org.mapstruct.ap.internal.util.NativeTypes.ByteAnalyzer.1
                @Override // org.mapstruct.ap.internal.util.NativeTypes.NumberRepresentation
                void parse(String str2, int i) throws NumberFormatException {
                    Byte.parseByte(str2, i);
                }
            }.validate();
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Integer.TYPE;
        }
    }

    private static class DoubleAnalyzer implements LiteralAnalyzer {
        private DoubleAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            new NumberRepresentation(str, false, false, false) { // from class: org.mapstruct.ap.internal.util.NativeTypes.DoubleAnalyzer.1
                @Override // org.mapstruct.ap.internal.util.NativeTypes.NumberRepresentation
                void parse(String str2, int i) {
                    if (i == 16) {
                        str2 = "0x" + str2;
                    }
                    Double dValueOf = Double.valueOf(Double.parseDouble(str2));
                    if (doubleHasBecomeZero(dValueOf.doubleValue())) {
                        throw new NumberFormatException("floating point number too small");
                    }
                    if (dValueOf.isInfinite()) {
                        throw new NumberFormatException("infinitive is not allowed");
                    }
                }
            }.validate();
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Float.TYPE;
        }
    }

    private static class FloatAnalyzer implements LiteralAnalyzer {
        private FloatAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            new NumberRepresentation(str, false, false, true) { // from class: org.mapstruct.ap.internal.util.NativeTypes.FloatAnalyzer.1
                @Override // org.mapstruct.ap.internal.util.NativeTypes.NumberRepresentation
                void parse(String str2, int i) {
                    if (i == 16) {
                        str2 = "0x" + str2;
                    }
                    Float fValueOf = Float.valueOf(Float.parseFloat(str2));
                    if (doubleHasBecomeZero(fValueOf.floatValue())) {
                        throw new NumberFormatException("floating point number too small");
                    }
                    if (fValueOf.isInfinite()) {
                        throw new NumberFormatException("infinitive is not allowed");
                    }
                }
            }.validate();
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Float.TYPE;
        }
    }

    private static class IntAnalyzer implements LiteralAnalyzer {
        private IntAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            new NumberRepresentation(str, true, false, false) { // from class: org.mapstruct.ap.internal.util.NativeTypes.IntAnalyzer.1
                @Override // org.mapstruct.ap.internal.util.NativeTypes.NumberRepresentation
                void parse(String str2, int i) throws NumberFormatException {
                    if (i == 10) {
                        Integer.parseInt(str2, i);
                    } else if (new BigInteger(str2, i).bitLength() > 32) {
                        throw new NumberFormatException("integer number too large");
                    }
                }
            }.validate();
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Integer.TYPE;
        }
    }

    private static class LongAnalyzer implements LiteralAnalyzer {
        private LongAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            new NumberRepresentation(str, true, true, false) { // from class: org.mapstruct.ap.internal.util.NativeTypes.LongAnalyzer.1
                @Override // org.mapstruct.ap.internal.util.NativeTypes.NumberRepresentation
                void parse(String str2, int i) throws NumberFormatException {
                    if (i == 10) {
                        Long.parseLong(str2, i);
                    } else if (new BigInteger(str2, i).bitLength() > 64) {
                        throw new NumberFormatException("integer number too large");
                    }
                }
            }.validate();
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Integer.TYPE;
        }
    }

    private static class ShortAnalyzer implements LiteralAnalyzer {
        private ShortAnalyzer() {
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public void validate(String str) {
            new NumberRepresentation(str, true, false, false) { // from class: org.mapstruct.ap.internal.util.NativeTypes.ShortAnalyzer.1
                @Override // org.mapstruct.ap.internal.util.NativeTypes.NumberRepresentation
                void parse(String str2, int i) throws NumberFormatException {
                    Short.parseShort(str2, i);
                }
            }.validate();
        }

        @Override // org.mapstruct.ap.internal.util.NativeTypes.LiteralAnalyzer
        public Class<?> getLiteral() {
            return Integer.TYPE;
        }
    }

    private NativeTypes() {
    }

    public static Class<?> getWrapperType(Class<?> cls) {
        if (!cls.isPrimitive()) {
            throw new IllegalArgumentException(cls + " is no primitive type.");
        }
        return PRIMITIVE_TO_WRAPPER_TYPES.get(cls);
    }

    public static Class<?> getPrimitiveType(Class<?> cls) {
        if (cls.isPrimitive()) {
            throw new IllegalArgumentException(cls + " is no wrapper type.");
        }
        return WRAPPER_TO_PRIMITIVE_TYPES.get(cls);
    }

    public static boolean isNative(String str) {
        return ANALYZERS.containsKey(str);
    }

    public static boolean isNumber(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return NUMBER_TYPES.contains(cls);
    }

    public static Class<?> getLiteral(String str, String str2) {
        LiteralAnalyzer literalAnalyzer = ANALYZERS.get(str);
        if (literalAnalyzer == null) {
            return null;
        }
        literalAnalyzer.validate(str2);
        return literalAnalyzer.getLiteral();
    }

    public static String getName(TypeKind typeKind) {
        return TYPE_KIND_NAME.get(typeKind);
    }

    public static boolean isNarrowing(String str, String str2) {
        Map<String, Integer> map = NARROWING_LUT;
        Integer num = map.get(str);
        Integer num2 = map.get(str2);
        return (num == null || num2 == null || num2.intValue() - num.intValue() >= 0) ? false : true;
    }
}
