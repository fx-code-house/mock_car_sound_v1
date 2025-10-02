package org.mapstruct.ap.shaded.freemarker.template.utility;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashSet;
import java.util.Set;
import okhttp3.HttpUrl;
import org.mapstruct.ap.shaded.freemarker.core.Macro;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BooleanModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.CollectionModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.DateModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.EnumerationModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.IteratorModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.MapModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.NumberModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMethodModel;
import org.mapstruct.ap.shaded.freemarker.ext.beans.StringModel;
import org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class ClassUtil {
    static /* synthetic */ Class class$freemarker$core$Environment$Namespace;
    static /* synthetic */ Class class$freemarker$template$TemplateBooleanModel;
    static /* synthetic */ Class class$freemarker$template$TemplateCollectionModel;
    static /* synthetic */ Class class$freemarker$template$TemplateDateModel;
    static /* synthetic */ Class class$freemarker$template$TemplateDirectiveModel;
    static /* synthetic */ Class class$freemarker$template$TemplateHashModel;
    static /* synthetic */ Class class$freemarker$template$TemplateHashModelEx;
    static /* synthetic */ Class class$freemarker$template$TemplateMethodModel;
    static /* synthetic */ Class class$freemarker$template$TemplateMethodModelEx;
    static /* synthetic */ Class class$freemarker$template$TemplateModelIterator;
    static /* synthetic */ Class class$freemarker$template$TemplateNodeModel;
    static /* synthetic */ Class class$freemarker$template$TemplateNumberModel;
    static /* synthetic */ Class class$freemarker$template$TemplateScalarModel;
    static /* synthetic */ Class class$freemarker$template$TemplateSequenceModel;
    static /* synthetic */ Class class$freemarker$template$TemplateTransformModel;
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
    static /* synthetic */ Class class$java$lang$Void;

    private ClassUtil() {
    }

    public static Class forName(String str) throws ClassNotFoundException {
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                return Class.forName(str, true, contextClassLoader);
            }
        } catch (ClassNotFoundException | SecurityException unused) {
        }
        return Class.forName(str);
    }

    public static String getShortClassName(Class cls) {
        return getShortClassName(cls, false);
    }

    public static String getShortClassName(Class cls, boolean z) {
        if (cls == null) {
            return null;
        }
        if (cls.isArray()) {
            return new StringBuffer().append(getShortClassName(cls.getComponentType())).append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI).toString();
        }
        String name = cls.getName();
        if (name.startsWith("java.lang.") || name.startsWith("java.util.")) {
            return name.substring(10);
        }
        if (!z) {
            return name;
        }
        if (name.startsWith("org.mapstruct.ap.shaded.freemarker.template.")) {
            return new StringBuffer("f.t").append(name.substring(19)).toString();
        }
        if (name.startsWith("org.mapstruct.ap.shaded.freemarker.ext.beans.")) {
            return new StringBuffer("f.e.b").append(name.substring(20)).toString();
        }
        if (name.startsWith("org.mapstruct.ap.shaded.freemarker.core.")) {
            return new StringBuffer("f.c").append(name.substring(15)).toString();
        }
        if (name.startsWith("org.mapstruct.ap.shaded.freemarker.ext.")) {
            return new StringBuffer("f.e").append(name.substring(14)).toString();
        }
        return name.startsWith("org.mapstruct.ap.shaded.freemarker.") ? new StringBuffer("f").append(name.substring(10)).toString() : name;
    }

    public static String getShortClassNameOfObject(Object obj) {
        return getShortClassNameOfObject(obj, false);
    }

    public static String getShortClassNameOfObject(Object obj, boolean z) {
        return obj == null ? "Null" : getShortClassName(obj.getClass(), z);
    }

    private static Class getPrimaryTemplateModelInterface(TemplateModel templateModel) throws Throwable {
        if (templateModel instanceof BeanModel) {
            if (templateModel instanceof CollectionModel) {
                Class cls = class$freemarker$template$TemplateSequenceModel;
                if (cls != null) {
                    return cls;
                }
                Class clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
                class$freemarker$template$TemplateSequenceModel = clsClass$;
                return clsClass$;
            }
            if ((templateModel instanceof IteratorModel) || (templateModel instanceof EnumerationModel)) {
                Class cls2 = class$freemarker$template$TemplateCollectionModel;
                if (cls2 != null) {
                    return cls2;
                }
                Class clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel");
                class$freemarker$template$TemplateCollectionModel = clsClass$2;
                return clsClass$2;
            }
            if (templateModel instanceof MapModel) {
                Class cls3 = class$freemarker$template$TemplateHashModelEx;
                if (cls3 != null) {
                    return cls3;
                }
                Class clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx");
                class$freemarker$template$TemplateHashModelEx = clsClass$3;
                return clsClass$3;
            }
            if (templateModel instanceof NumberModel) {
                Class cls4 = class$freemarker$template$TemplateNumberModel;
                if (cls4 != null) {
                    return cls4;
                }
                Class clsClass$4 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
                class$freemarker$template$TemplateNumberModel = clsClass$4;
                return clsClass$4;
            }
            if (templateModel instanceof BooleanModel) {
                Class cls5 = class$freemarker$template$TemplateBooleanModel;
                if (cls5 != null) {
                    return cls5;
                }
                Class clsClass$5 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
                class$freemarker$template$TemplateBooleanModel = clsClass$5;
                return clsClass$5;
            }
            if (templateModel instanceof DateModel) {
                Class cls6 = class$freemarker$template$TemplateDateModel;
                if (cls6 != null) {
                    return cls6;
                }
                Class clsClass$6 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
                class$freemarker$template$TemplateDateModel = clsClass$6;
                return clsClass$6;
            }
            if (!(templateModel instanceof StringModel)) {
                return null;
            }
            if (((BeanModel) templateModel).getWrappedObject() instanceof String) {
                Class cls7 = class$freemarker$template$TemplateScalarModel;
                if (cls7 != null) {
                    return cls7;
                }
                Class clsClass$7 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
                class$freemarker$template$TemplateScalarModel = clsClass$7;
                return clsClass$7;
            }
            if (!(templateModel instanceof TemplateHashModelEx)) {
                return null;
            }
            Class cls8 = class$freemarker$template$TemplateHashModelEx;
            if (cls8 != null) {
                return cls8;
            }
            Class clsClass$8 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx");
            class$freemarker$template$TemplateHashModelEx = clsClass$8;
            return clsClass$8;
        }
        if (!(templateModel instanceof SimpleMethodModel) && !(templateModel instanceof OverloadedMethodsModel)) {
            return null;
        }
        Class cls9 = class$freemarker$template$TemplateMethodModelEx;
        if (cls9 != null) {
            return cls9;
        }
        Class clsClass$9 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx");
        class$freemarker$template$TemplateMethodModelEx = clsClass$9;
        return clsClass$9;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static void appendTemplateModelTypeName(StringBuffer stringBuffer, Set set, Class cls) throws Throwable {
        Class clsClass$ = class$freemarker$template$TemplateNodeModel;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNodeModel");
            class$freemarker$template$TemplateNodeModel = clsClass$;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "node");
        }
        Class clsClass$2 = class$freemarker$template$TemplateDirectiveModel;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDirectiveModel");
            class$freemarker$template$TemplateDirectiveModel = clsClass$2;
        }
        if (clsClass$2.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "directive");
        } else {
            Class clsClass$3 = class$freemarker$template$TemplateTransformModel;
            if (clsClass$3 == null) {
                clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel");
                class$freemarker$template$TemplateTransformModel = clsClass$3;
            }
            if (clsClass$3.isAssignableFrom(cls)) {
                appendTypeName(stringBuffer, set, "transform");
            }
        }
        Class clsClass$4 = class$freemarker$template$TemplateSequenceModel;
        if (clsClass$4 == null) {
            clsClass$4 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
            class$freemarker$template$TemplateSequenceModel = clsClass$4;
        }
        if (clsClass$4.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "sequence");
        } else {
            Class clsClass$5 = class$freemarker$template$TemplateCollectionModel;
            if (clsClass$5 == null) {
                clsClass$5 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel");
                class$freemarker$template$TemplateCollectionModel = clsClass$5;
            }
            if (clsClass$5.isAssignableFrom(cls)) {
                appendTypeName(stringBuffer, set, "collection");
            } else {
                Class clsClass$6 = class$freemarker$template$TemplateModelIterator;
                if (clsClass$6 == null) {
                    clsClass$6 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator");
                    class$freemarker$template$TemplateModelIterator = clsClass$6;
                }
                if (clsClass$6.isAssignableFrom(cls)) {
                    appendTypeName(stringBuffer, set, "iterator");
                }
            }
        }
        Class clsClass$7 = class$freemarker$template$TemplateMethodModel;
        if (clsClass$7 == null) {
            clsClass$7 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel");
            class$freemarker$template$TemplateMethodModel = clsClass$7;
        }
        if (clsClass$7.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, FirebaseAnalytics.Param.METHOD);
        }
        Class clsClass$8 = class$freemarker$core$Environment$Namespace;
        if (clsClass$8 == null) {
            clsClass$8 = class$("org.mapstruct.ap.shaded.freemarker.core.Environment$Namespace");
            class$freemarker$core$Environment$Namespace = clsClass$8;
        }
        if (clsClass$8.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "namespace");
        } else {
            Class clsClass$9 = class$freemarker$template$TemplateHashModelEx;
            if (clsClass$9 == null) {
                clsClass$9 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx");
                class$freemarker$template$TemplateHashModelEx = clsClass$9;
            }
            if (clsClass$9.isAssignableFrom(cls)) {
                appendTypeName(stringBuffer, set, "extended_hash");
            } else {
                Class clsClass$10 = class$freemarker$template$TemplateHashModel;
                if (clsClass$10 == null) {
                    clsClass$10 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel");
                    class$freemarker$template$TemplateHashModel = clsClass$10;
                }
                if (clsClass$10.isAssignableFrom(cls)) {
                    appendTypeName(stringBuffer, set, "hash");
                }
            }
        }
        Class clsClass$11 = class$freemarker$template$TemplateNumberModel;
        if (clsClass$11 == null) {
            clsClass$11 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel");
            class$freemarker$template$TemplateNumberModel = clsClass$11;
        }
        if (clsClass$11.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "number");
        }
        Class clsClass$12 = class$freemarker$template$TemplateDateModel;
        if (clsClass$12 == null) {
            clsClass$12 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel");
            class$freemarker$template$TemplateDateModel = clsClass$12;
        }
        if (clsClass$12.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "date_or_time_or_datetime");
        }
        Class clsClass$13 = class$freemarker$template$TemplateBooleanModel;
        if (clsClass$13 == null) {
            clsClass$13 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel");
            class$freemarker$template$TemplateBooleanModel = clsClass$13;
        }
        if (clsClass$13.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "boolean");
        }
        Class clsClass$14 = class$freemarker$template$TemplateScalarModel;
        if (clsClass$14 == null) {
            clsClass$14 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
            class$freemarker$template$TemplateScalarModel = clsClass$14;
        }
        if (clsClass$14.isAssignableFrom(cls)) {
            appendTypeName(stringBuffer, set, "string");
        }
    }

    private static Class getUnwrappedClass(TemplateModel templateModel) {
        Object adaptedObject;
        if (templateModel instanceof WrapperTemplateModel) {
            adaptedObject = ((WrapperTemplateModel) templateModel).getWrappedObject();
        } else if (templateModel instanceof AdapterTemplateModel) {
            AdapterTemplateModel adapterTemplateModel = (AdapterTemplateModel) templateModel;
            Class clsClass$ = class$java$lang$Object;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Object");
                class$java$lang$Object = clsClass$;
            }
            adaptedObject = adapterTemplateModel.getAdaptedObject(clsClass$);
        } else {
            adaptedObject = null;
        }
        if (adaptedObject != null) {
            return adaptedObject.getClass();
        }
        return null;
    }

    private static void appendTypeName(StringBuffer stringBuffer, Set set, String str) {
        if (set.contains(str)) {
            return;
        }
        if (stringBuffer.length() != 0) {
            stringBuffer.append("+");
        }
        stringBuffer.append(str);
        set.add(str);
    }

    public static String getFTLTypeDescription(TemplateModel templateModel) throws Throwable {
        if (templateModel == null) {
            return "Null";
        }
        HashSet hashSet = new HashSet();
        StringBuffer stringBuffer = new StringBuffer();
        Class primaryTemplateModelInterface = getPrimaryTemplateModelInterface(templateModel);
        if (primaryTemplateModelInterface != null) {
            appendTemplateModelTypeName(stringBuffer, hashSet, primaryTemplateModelInterface);
        }
        if (templateModel instanceof Macro) {
            appendTypeName(stringBuffer, hashSet, ((Macro) templateModel).isFunction() ? "function" : "macro");
        }
        appendTemplateModelTypeName(stringBuffer, hashSet, templateModel.getClass());
        Class unwrappedClass = getUnwrappedClass(templateModel);
        String shortClassName = unwrappedClass != null ? getShortClassName(unwrappedClass, true) : null;
        stringBuffer.append(" (");
        String shortClassName2 = getShortClassName(templateModel.getClass(), true);
        if (shortClassName == null) {
            stringBuffer.append("wrapper: ");
            stringBuffer.append(shortClassName2);
        } else {
            stringBuffer.append(shortClassName);
            stringBuffer.append(" wrapped into ");
            stringBuffer.append(shortClassName2);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public static Class primitiveClassToBoxingClass(Class cls) throws Throwable {
        if (cls == Integer.TYPE) {
            Class cls2 = class$java$lang$Integer;
            if (cls2 != null) {
                return cls2;
            }
            Class clsClass$ = class$("java.lang.Integer");
            class$java$lang$Integer = clsClass$;
            return clsClass$;
        }
        if (cls == Boolean.TYPE) {
            Class cls3 = class$java$lang$Boolean;
            if (cls3 != null) {
                return cls3;
            }
            Class clsClass$2 = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$2;
            return clsClass$2;
        }
        if (cls == Long.TYPE) {
            Class cls4 = class$java$lang$Long;
            if (cls4 != null) {
                return cls4;
            }
            Class clsClass$3 = class$("java.lang.Long");
            class$java$lang$Long = clsClass$3;
            return clsClass$3;
        }
        if (cls == Double.TYPE) {
            Class cls5 = class$java$lang$Double;
            if (cls5 != null) {
                return cls5;
            }
            Class clsClass$4 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$4;
            return clsClass$4;
        }
        if (cls == Character.TYPE) {
            Class cls6 = class$java$lang$Character;
            if (cls6 != null) {
                return cls6;
            }
            Class clsClass$5 = class$("java.lang.Character");
            class$java$lang$Character = clsClass$5;
            return clsClass$5;
        }
        if (cls == Float.TYPE) {
            Class cls7 = class$java$lang$Float;
            if (cls7 != null) {
                return cls7;
            }
            Class clsClass$6 = class$("java.lang.Float");
            class$java$lang$Float = clsClass$6;
            return clsClass$6;
        }
        if (cls == Byte.TYPE) {
            Class cls8 = class$java$lang$Byte;
            if (cls8 != null) {
                return cls8;
            }
            Class clsClass$7 = class$("java.lang.Byte");
            class$java$lang$Byte = clsClass$7;
            return clsClass$7;
        }
        if (cls == Short.TYPE) {
            Class cls9 = class$java$lang$Short;
            if (cls9 != null) {
                return cls9;
            }
            Class clsClass$8 = class$("java.lang.Short");
            class$java$lang$Short = clsClass$8;
            return clsClass$8;
        }
        if (cls != Void.TYPE) {
            return cls;
        }
        Class cls10 = class$java$lang$Void;
        if (cls10 != null) {
            return cls10;
        }
        Class clsClass$9 = class$("java.lang.Void");
        class$java$lang$Void = clsClass$9;
        return clsClass$9;
    }

    public static Class boxingClassToPrimitiveClass(Class cls) throws Throwable {
        Class clsClass$ = class$java$lang$Integer;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Integer");
            class$java$lang$Integer = clsClass$;
        }
        if (cls == clsClass$) {
            return Integer.TYPE;
        }
        Class clsClass$2 = class$java$lang$Boolean;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$2;
        }
        if (cls == clsClass$2) {
            return Boolean.TYPE;
        }
        Class clsClass$3 = class$java$lang$Long;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.lang.Long");
            class$java$lang$Long = clsClass$3;
        }
        if (cls == clsClass$3) {
            return Long.TYPE;
        }
        Class clsClass$4 = class$java$lang$Double;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$4;
        }
        if (cls == clsClass$4) {
            return Double.TYPE;
        }
        Class clsClass$5 = class$java$lang$Character;
        if (clsClass$5 == null) {
            clsClass$5 = class$("java.lang.Character");
            class$java$lang$Character = clsClass$5;
        }
        if (cls == clsClass$5) {
            return Character.TYPE;
        }
        Class clsClass$6 = class$java$lang$Float;
        if (clsClass$6 == null) {
            clsClass$6 = class$("java.lang.Float");
            class$java$lang$Float = clsClass$6;
        }
        if (cls == clsClass$6) {
            return Float.TYPE;
        }
        Class clsClass$7 = class$java$lang$Byte;
        if (clsClass$7 == null) {
            clsClass$7 = class$("java.lang.Byte");
            class$java$lang$Byte = clsClass$7;
        }
        if (cls == clsClass$7) {
            return Byte.TYPE;
        }
        Class clsClass$8 = class$java$lang$Short;
        if (clsClass$8 == null) {
            clsClass$8 = class$("java.lang.Short");
            class$java$lang$Short = clsClass$8;
        }
        if (cls == clsClass$8) {
            return Short.TYPE;
        }
        Class clsClass$9 = class$java$lang$Void;
        if (clsClass$9 == null) {
            clsClass$9 = class$("java.lang.Void");
            class$java$lang$Void = clsClass$9;
        }
        return cls == clsClass$9 ? Void.TYPE : cls;
    }

    public static boolean isNumerical(Class cls) throws Throwable {
        Class clsClass$ = class$java$lang$Number;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Number");
            class$java$lang$Number = clsClass$;
        }
        return clsClass$.isAssignableFrom(cls) || !(!cls.isPrimitive() || cls == Boolean.TYPE || cls == Character.TYPE || cls == Void.TYPE);
    }
}
