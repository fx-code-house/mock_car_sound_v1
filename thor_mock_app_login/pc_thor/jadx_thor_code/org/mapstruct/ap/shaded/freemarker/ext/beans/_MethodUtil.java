package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.ClassUtils;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString;
import org.mapstruct.ap.shaded.freemarker.core._DelayedJQuote;
import org.mapstruct.ap.shaded.freemarker.core._TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
public class _MethodUtil {
    private static final Method CONSTRUCTOR_IS_VARARGS;
    private static final Method METHOD_IS_VARARGS;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Number;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$reflect$Constructor;
    static /* synthetic */ Class class$java$lang$reflect$Method;

    static {
        Class clsClass$ = class$java$lang$reflect$Method;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.reflect.Method");
            class$java$lang$reflect$Method = clsClass$;
        }
        METHOD_IS_VARARGS = getIsVarArgsMethod(clsClass$);
        Class clsClass$2 = class$java$lang$reflect$Constructor;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.lang.reflect.Constructor");
            class$java$lang$reflect$Constructor = clsClass$2;
        }
        CONSTRUCTOR_IS_VARARGS = getIsVarArgsMethod(clsClass$2);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static int isMoreOrSameSpecificParameterType(Class cls, Class cls2, boolean z, int i) throws Throwable {
        if (i >= 4) {
            return 0;
        }
        if (cls2.isAssignableFrom(cls)) {
            return cls2 == cls ? 1 : 4;
        }
        boolean zIsPrimitive = cls.isPrimitive();
        boolean zIsPrimitive2 = cls2.isPrimitive();
        if (!zIsPrimitive) {
            if (i >= 3 || !z || zIsPrimitive2) {
                return 0;
            }
            Class clsClass$ = class$java$lang$Number;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Number");
                class$java$lang$Number = clsClass$;
            }
            if (!clsClass$.isAssignableFrom(cls)) {
                return 0;
            }
            Class clsClass$2 = class$java$lang$Number;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.Number");
                class$java$lang$Number = clsClass$2;
            }
            return (clsClass$2.isAssignableFrom(cls2) && isWideningBoxedNumberConversion(cls, cls2)) ? 3 : 0;
        }
        if (zIsPrimitive2) {
            return (i < 3 && isWideningPrimitiveNumberConversion(cls, cls2)) ? 3 : 0;
        }
        if (!z) {
            return 0;
        }
        Class<?> clsPrimitiveClassToBoxingClass = ClassUtil.primitiveClassToBoxingClass(cls);
        if (clsPrimitiveClassToBoxingClass == cls2) {
            return 2;
        }
        if (cls2.isAssignableFrom(clsPrimitiveClassToBoxingClass)) {
            return 4;
        }
        if (i >= 3) {
            return 0;
        }
        Class clsClass$3 = class$java$lang$Number;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.lang.Number");
            class$java$lang$Number = clsClass$3;
        }
        if (!clsClass$3.isAssignableFrom(clsPrimitiveClassToBoxingClass)) {
            return 0;
        }
        Class clsClass$4 = class$java$lang$Number;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.lang.Number");
            class$java$lang$Number = clsClass$4;
        }
        return (clsClass$4.isAssignableFrom(cls2) && isWideningBoxedNumberConversion(clsPrimitiveClassToBoxingClass, cls2)) ? 3 : 0;
    }

    private static boolean isWideningPrimitiveNumberConversion(Class cls, Class cls2) {
        if (cls2 == Short.TYPE && cls == Byte.TYPE) {
            return true;
        }
        if (cls2 == Integer.TYPE && (cls == Short.TYPE || cls == Byte.TYPE)) {
            return true;
        }
        if (cls2 == Long.TYPE && (cls == Integer.TYPE || cls == Short.TYPE || cls == Byte.TYPE)) {
            return true;
        }
        if (cls2 == Float.TYPE && (cls == Long.TYPE || cls == Integer.TYPE || cls == Short.TYPE || cls == Byte.TYPE)) {
            return true;
        }
        if (cls2 == Double.TYPE) {
            return cls == Float.TYPE || cls == Long.TYPE || cls == Integer.TYPE || cls == Short.TYPE || cls == Byte.TYPE;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0042, code lost:
    
        if (r7 == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0075, code lost:
    
        if (r7 == r0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b4, code lost:
    
        if (r7 == r0) goto L62;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean isWideningBoxedNumberConversion(java.lang.Class r7, java.lang.Class r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 260
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans._MethodUtil.isWideningBoxedNumberConversion(java.lang.Class, java.lang.Class):boolean");
    }

    public static Set getAssignables(Class cls, Class cls2) {
        HashSet hashSet = new HashSet();
        collectAssignables(cls, cls2, hashSet);
        return hashSet;
    }

    private static void collectAssignables(Class cls, Class cls2, Set set) {
        if (cls.isAssignableFrom(cls2)) {
            set.add(cls);
        }
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            collectAssignables(superclass, cls2, set);
        }
        for (Class<?> cls3 : cls.getInterfaces()) {
            collectAssignables(cls3, cls2, set);
        }
    }

    public static Class[] getParameterTypes(Member member) {
        if (member instanceof Method) {
            return ((Method) member).getParameterTypes();
        }
        if (member instanceof Constructor) {
            return ((Constructor) member).getParameterTypes();
        }
        throw new IllegalArgumentException("\"member\" must be Method or Constructor");
    }

    public static boolean isVarargs(Member member) {
        if (member instanceof Method) {
            return isVarargs(member, METHOD_IS_VARARGS);
        }
        if (member instanceof Constructor) {
            return isVarargs(member, CONSTRUCTOR_IS_VARARGS);
        }
        throw new BugException();
    }

    private static boolean isVarargs(Member member, Method method) {
        if (method == null) {
            return false;
        }
        try {
            return ((Boolean) method.invoke(member, null)).booleanValue();
        } catch (Exception e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    private static Method getIsVarArgsMethod(Class cls) {
        try {
            return cls.getMethod("isVarArgs", null);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static String toString(Member member) {
        if (!(member instanceof Method) && !(member instanceof Constructor)) {
            throw new IllegalArgumentException("\"member\" must be a Method or Constructor");
        }
        StringBuffer stringBuffer = new StringBuffer();
        if ((member.getModifiers() & 8) != 0) {
            stringBuffer.append("static ");
        }
        String shortClassName = ClassUtil.getShortClassName(member.getDeclaringClass());
        if (shortClassName != null) {
            stringBuffer.append(shortClassName);
            stringBuffer.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
        }
        stringBuffer.append(member.getName());
        stringBuffer.append('(');
        Class[] parameterTypes = getParameterTypes(member);
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            String shortClassName2 = ClassUtil.getShortClassName(parameterTypes[i]);
            if (i == parameterTypes.length - 1 && shortClassName2.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI) && isVarargs(member)) {
                stringBuffer.append(shortClassName2.substring(0, shortClassName2.length() - 2));
                stringBuffer.append("...");
            } else {
                stringBuffer.append(shortClassName2);
            }
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public static Object[] invocationErrorMessageStart(Member member) {
        return invocationErrorMessageStart(member, member instanceof Constructor);
    }

    private static Object[] invocationErrorMessageStart(Object obj, boolean z) {
        Object[] objArr = new Object[3];
        objArr[0] = "Java ";
        objArr[1] = z ? "constructor " : "method ";
        objArr[2] = new _DelayedJQuote(obj);
        return objArr;
    }

    public static TemplateModelException newInvocationTemplateModelException(Object obj, Member member, Throwable th) {
        return newInvocationTemplateModelException(obj, member, (member.getModifiers() & 8) != 0, member instanceof Constructor, th);
    }

    public static TemplateModelException newInvocationTemplateModelException(Object obj, CallableMemberDescriptor callableMemberDescriptor, Throwable th) {
        return newInvocationTemplateModelException(obj, new _DelayedConversionToString(callableMemberDescriptor) { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans._MethodUtil.1
            @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
            protected String doConversion(Object obj2) {
                return ((CallableMemberDescriptor) obj2).getDeclaration();
            }
        }, callableMemberDescriptor.isStatic(), callableMemberDescriptor.isConstructor(), th);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static TemplateModelException newInvocationTemplateModelException(Object obj, Object obj2, boolean z, boolean z2, Throwable th) {
        Throwable targetException;
        while ((th instanceof InvocationTargetException) && (targetException = ((InvocationTargetException) th).getTargetException()) != null) {
            th = targetException;
        }
        Object[] objArr = new Object[4];
        objArr[0] = invocationErrorMessageStart(obj2, z2);
        objArr[1] = " threw an exception";
        objArr[2] = (z || z2) ? "" : new Object[]{" when invoked on ", obj.getClass(), " object ", new _DelayedJQuote(obj)};
        objArr[3] = "; see cause exception in the Java stack trace.";
        return new _TemplateModelException(th, objArr);
    }
}
