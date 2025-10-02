package org.mapstruct.ap.shaded.freemarker.core;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ClassUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.Version;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.WriteProtectable;

/* loaded from: classes3.dex */
public class _ObjectBuilderSettingEvaluator {
    private static final String BUILDER_CLASS_POSTFIX = "Builder";
    private static final String BUILD_METHOD_NAME = "build";
    private static final String INSTANCE_FIELD_NAME = "INSTANCE";
    private static Map SHORTHANDS;
    static /* synthetic */ Class class$freemarker$ext$beans$BeansWrapper;
    static /* synthetic */ Class class$freemarker$template$DefaultObjectWrapper;
    static /* synthetic */ Class class$freemarker$template$SimpleObjectWrapper;
    private final _SettingEvaluationEnvironment env;
    private final Class expectedClass;
    private int pos;
    private BuilderExpression rootExp;
    private final String src;
    private boolean v2321Mode = false;

    private boolean isASCIIDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private _ObjectBuilderSettingEvaluator(String str, Class cls, _SettingEvaluationEnvironment _settingevaluationenvironment) {
        this.src = str;
        this.expectedClass = cls;
        this.env = _settingevaluationenvironment;
    }

    public static Object eval(String str, Class cls, _SettingEvaluationEnvironment _settingevaluationenvironment) throws _ObjectBuilderSettingEvaluationException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        return new _ObjectBuilderSettingEvaluator(str, cls, _settingevaluationenvironment).eval();
    }

    private Object eval() throws _ObjectBuilderSettingEvaluationException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        parse();
        return execute();
    }

    private void parse() throws _ObjectBuilderSettingEvaluationException {
        skipWS();
        this.rootExp = fetchBuilderCall(true, false);
        skipWS();
        if (this.pos != this.src.length()) {
            throw new _ObjectBuilderSettingEvaluationException("end-of-expression", this.src, this.pos);
        }
    }

    private Object execute() throws _ObjectBuilderSettingEvaluationException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (this.v2321Mode) {
            return this.rootExp.eval();
        }
        return ClassUtil.forName(this.rootExp.className).newInstance();
    }

    private Object eval(Object obj) throws _ObjectBuilderSettingEvaluationException {
        return obj instanceof SettingExpression ? ((SettingExpression) obj).eval() : obj;
    }

    private BuilderExpression fetchBuilderCall(boolean z, boolean z2) throws _ObjectBuilderSettingEvaluationException {
        int i = this.pos;
        BuilderExpression builderExpression = new BuilderExpression();
        String strFetchClassName = fetchClassName(z2);
        if (strFetchClassName == null) {
            return null;
        }
        builderExpression.className = shorthandToFullQualified(strFetchClassName);
        if (!strFetchClassName.equals(builderExpression.className)) {
            this.v2321Mode = true;
        }
        skipWS();
        char cFetchOptionalChar = fetchOptionalChar("(");
        if (cFetchOptionalChar == 0 && !z) {
            if (!z2) {
                throw new _ObjectBuilderSettingEvaluationException("(", this.src, this.pos);
            }
            this.pos = i;
            return null;
        }
        if (cFetchOptionalChar != 0) {
            this.v2321Mode = true;
            skipWS();
            if (fetchOptionalChar(")") != ')') {
                do {
                    skipWS();
                    Object objFetchValueOrName = fetchValueOrName(false);
                    if (objFetchValueOrName != null) {
                        skipWS();
                        if (objFetchValueOrName instanceof ParameterName) {
                            builderExpression.namedParamNames.add(((ParameterName) objFetchValueOrName).name);
                            skipWS();
                            fetchRequiredChar("=");
                            skipWS();
                            int i2 = this.pos;
                            Object objFetchValueOrName2 = fetchValueOrName(false);
                            if (objFetchValueOrName2 instanceof ParameterName) {
                                throw new _ObjectBuilderSettingEvaluationException("concrete value", this.src, i2);
                            }
                            builderExpression.namedParamValues.add(eval(objFetchValueOrName2));
                        } else {
                            if (!builderExpression.namedParamNames.isEmpty()) {
                                throw new _ObjectBuilderSettingEvaluationException("Positional parameters must precede named parameters");
                            }
                            builderExpression.positionalParamValues.add(eval(objFetchValueOrName));
                        }
                        skipWS();
                    }
                } while (fetchRequiredChar(",)") == ',');
            }
        }
        return builderExpression;
    }

    private Object fetchValueOrName(boolean z) throws _ObjectBuilderSettingEvaluationException {
        if (this.pos < this.src.length()) {
            Object objFetchNumberLike = fetchNumberLike(true);
            if (objFetchNumberLike != null) {
                return objFetchNumberLike;
            }
            Object objFetchStringLiteral = fetchStringLiteral(true);
            if (objFetchStringLiteral != null) {
                return objFetchStringLiteral;
            }
            BuilderExpression builderExpressionFetchBuilderCall = fetchBuilderCall(false, true);
            if (builderExpressionFetchBuilderCall != null) {
                return builderExpressionFetchBuilderCall;
            }
            String strFetchSimpleName = fetchSimpleName(true);
            if (strFetchSimpleName != null) {
                return strFetchSimpleName.equals(BooleanUtils.TRUE) ? Boolean.TRUE : strFetchSimpleName.equals(BooleanUtils.FALSE) ? Boolean.FALSE : strFetchSimpleName.equals("null") ? NullExpression.INSTANCE : new ParameterName(strFetchSimpleName);
            }
        }
        if (z) {
            return null;
        }
        throw new _ObjectBuilderSettingEvaluationException("value or name", this.src, this.pos);
    }

    private String fetchSimpleName(boolean z) throws _ObjectBuilderSettingEvaluationException {
        if (!isIdentifierStart(this.pos < this.src.length() ? this.src.charAt(this.pos) : (char) 0)) {
            if (z) {
                return null;
            }
            throw new _ObjectBuilderSettingEvaluationException("class name", this.src, this.pos);
        }
        int i = this.pos;
        this.pos = i + 1;
        while (this.pos != this.src.length() && isIdentifierMiddle(this.src.charAt(this.pos))) {
            this.pos++;
        }
        return this.src.substring(i, this.pos);
    }

    private String fetchClassName(boolean z) throws _ObjectBuilderSettingEvaluationException {
        int i = this.pos;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String strFetchSimpleName = fetchSimpleName(true);
            if (strFetchSimpleName == null) {
                if (!z) {
                    throw new _ObjectBuilderSettingEvaluationException(AppMeasurementSdk.ConditionalUserProperty.NAME, this.src, this.pos);
                }
                this.pos = i;
                return null;
            }
            stringBuffer.append(strFetchSimpleName);
            skipWS();
            if (this.pos >= this.src.length() || this.src.charAt(this.pos) != '.') {
                break;
            }
            stringBuffer.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            this.pos++;
            skipWS();
        }
        return stringBuffer.toString();
    }

    private Object fetchNumberLike(boolean z) throws _ObjectBuilderSettingEvaluationException {
        int i = this.pos;
        boolean z2 = false;
        boolean z3 = false;
        while (this.pos != this.src.length()) {
            char cCharAt = this.src.charAt(this.pos);
            if (cCharAt != '.') {
                if (!isASCIIDigit(cCharAt) && cCharAt != '-') {
                    break;
                }
            } else if (z2) {
                z3 = true;
            } else {
                z2 = true;
            }
            this.pos++;
        }
        int i2 = this.pos;
        if (i == i2) {
            if (z) {
                return null;
            }
            throw new _ObjectBuilderSettingEvaluationException("number-like", this.src, this.pos);
        }
        String strSubstring = this.src.substring(i, i2);
        if (z3) {
            try {
                return new Version(strSubstring);
            } catch (IllegalArgumentException e) {
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Malformed version number: ").append(strSubstring).toString(), e);
            }
        }
        try {
            if (strSubstring.endsWith(".")) {
                throw new NumberFormatException("A number can't end with a dot");
            }
            if (strSubstring.startsWith(".") || strSubstring.startsWith("-.") || strSubstring.startsWith("+.")) {
                throw new NumberFormatException("A number can't start with a dot");
            }
            return new BigDecimal(strSubstring);
        } catch (NumberFormatException e2) {
            throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Malformed number: ").append(strSubstring).toString(), e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0063, code lost:
    
        r1 = r8.pos;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0065, code lost:
    
        if (r0 != r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0067, code lost:
    
        if (r9 == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0069, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0076, code lost:
    
        throw new org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluationException("string literal", r8.src, r8.pos);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0077, code lost:
    
        r9 = r8.src;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0079, code lost:
    
        if (r4 == false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x007b, code lost:
    
        r2 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x007d, code lost:
    
        r2 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007e, code lost:
    
        r9 = r9.substring(r0 + r2, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0083, code lost:
    
        r8.pos++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0088, code lost:
    
        if (r4 == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x008f, code lost:
    
        return org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil.FTLStringLiteralDec(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0090, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a5, code lost:
    
        throw new org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluationException(new java.lang.StringBuffer("Malformed string literal: ").append(r9).toString(), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:?, code lost:
    
        return r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object fetchStringLiteral(boolean r9) throws org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluationException {
        /*
            Method dump skipped, instructions count: 204
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluator.fetchStringLiteral(boolean):java.lang.Object");
    }

    private void skipWS() {
        while (this.pos != this.src.length() && Character.isWhitespace(this.src.charAt(this.pos))) {
            this.pos++;
        }
    }

    private char fetchOptionalChar(String str) throws _ObjectBuilderSettingEvaluationException {
        return fetchChar(str, true);
    }

    private char fetchRequiredChar(String str) throws _ObjectBuilderSettingEvaluationException {
        return fetchChar(str, false);
    }

    private char fetchChar(String str, boolean z) throws _ObjectBuilderSettingEvaluationException {
        int i = 0;
        char cCharAt = this.pos < this.src.length() ? this.src.charAt(this.pos) : (char) 0;
        if (str.indexOf(cCharAt) != -1) {
            this.pos++;
            return cCharAt;
        }
        if (z) {
            return (char) 0;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (i < str.length()) {
            if (i != 0) {
                stringBuffer.append(" or ");
            }
            int i2 = i + 1;
            stringBuffer.append(StringUtil.jQuote(str.substring(i, i2)));
            i = i2;
        }
        if (z) {
            stringBuffer.append(" or end-of-string");
        }
        throw new _ObjectBuilderSettingEvaluationException(stringBuffer.toString(), this.src, this.pos);
    }

    private boolean isIdentifierStart(char c) {
        return Character.isLetter(c) || c == '_' || c == '$';
    }

    private boolean isIdentifierMiddle(char c) {
        return isIdentifierStart(c) || isASCIIDigit(c);
    }

    private static synchronized String shorthandToFullQualified(String str) {
        if (SHORTHANDS == null) {
            HashMap map = new HashMap();
            SHORTHANDS = map;
            Class clsClass$ = class$freemarker$template$DefaultObjectWrapper;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.DefaultObjectWrapper");
                class$freemarker$template$DefaultObjectWrapper = clsClass$;
            }
            map.put("DefaultObjectWrapper", clsClass$.getName());
            Map map2 = SHORTHANDS;
            Class clsClass$2 = class$freemarker$ext$beans$BeansWrapper;
            if (clsClass$2 == null) {
                clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper");
                class$freemarker$ext$beans$BeansWrapper = clsClass$2;
            }
            map2.put("BeansWrapper", clsClass$2.getName());
            Map map3 = SHORTHANDS;
            Class clsClass$3 = class$freemarker$template$SimpleObjectWrapper;
            if (clsClass$3 == null) {
                clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.SimpleObjectWrapper");
                class$freemarker$template$SimpleObjectWrapper = clsClass$3;
            }
            map3.put("SimpleObjectWrapper", clsClass$3.getName());
        }
        String str2 = (String) SHORTHANDS.get(str);
        if (str2 != null) {
            str = str2;
        }
        return str;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static class ParameterName {
        private final String name;

        public ParameterName(String str) {
            this.name = str;
        }
    }

    private static abstract class SettingExpression {
        abstract Object eval() throws _ObjectBuilderSettingEvaluationException;

        private SettingExpression() {
        }
    }

    private class BuilderExpression extends SettingExpression {
        private String className;
        private List namedParamNames;
        private List namedParamValues;
        private List positionalParamValues;

        private BuilderExpression() {
            super();
            this.positionalParamValues = new ArrayList();
            this.namedParamNames = new ArrayList();
            this.namedParamValues = new ArrayList();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluator.SettingExpression
        Object eval() throws _ObjectBuilderSettingEvaluationException, NoSuchFieldException {
            boolean z;
            try {
                Class clsForName = ClassUtil.forName(this.className);
                try {
                    clsForName = ClassUtil.forName(new StringBuffer().append(clsForName.getName()).append(_ObjectBuilderSettingEvaluator.BUILDER_CLASS_POSTFIX).toString());
                    z = true;
                } catch (ClassNotFoundException unused) {
                    z = false;
                }
                if (!z && hasNoParameters()) {
                    try {
                        Field field = clsForName.getField(_ObjectBuilderSettingEvaluator.INSTANCE_FIELD_NAME);
                        if ((field.getModifiers() & 9) == 9) {
                            return field.get(null);
                        }
                    } catch (NoSuchFieldException unused2) {
                    } catch (Exception e) {
                        throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Error when trying to access ").append(StringUtil.jQuote(this.className)).append(".INSTANCE").toString(), e);
                    }
                }
                Object objCallConstructor = callConstructor(clsForName);
                setJavaBeanProperties(objCallConstructor);
                if (z) {
                    objCallConstructor = callBuild(objCallConstructor);
                } else if (objCallConstructor instanceof WriteProtectable) {
                    ((WriteProtectable) objCallConstructor).writeProtect();
                }
                if (_ObjectBuilderSettingEvaluator.this.expectedClass.isInstance(objCallConstructor)) {
                    return objCallConstructor;
                }
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("The resulting object (of class ").append(objCallConstructor.getClass()).append(") is not a(n) ").append(_ObjectBuilderSettingEvaluator.this.expectedClass.getName()).append(".").toString());
            } catch (Exception e2) {
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to get class ").append(StringUtil.jQuote(this.className)).append(".").toString(), e2);
            }
        }

        private Object callConstructor(Class cls) throws _ObjectBuilderSettingEvaluationException {
            if (!hasNoParameters()) {
                BeansWrapper objectWrapper = _ObjectBuilderSettingEvaluator.this.env.getObjectWrapper();
                ArrayList arrayList = new ArrayList(this.positionalParamValues.size());
                for (int i = 0; i < this.positionalParamValues.size(); i++) {
                    try {
                        arrayList.add(objectWrapper.wrap(this.positionalParamValues.get(i)));
                    } catch (TemplateModelException e) {
                        throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to wrap arg #").append(i + 1).toString(), e);
                    }
                }
                try {
                    return objectWrapper.newInstance(cls, arrayList);
                } catch (Exception e2) {
                    throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to call ").append(cls.getName()).append(" constructor").toString(), e2);
                }
            }
            try {
                return cls.newInstance();
            } catch (Exception e3) {
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to call ").append(cls.getName()).append(" constructor").toString(), e3);
            }
        }

        private void setJavaBeanProperties(Object obj) throws _ObjectBuilderSettingEvaluationException {
            if (this.namedParamNames.isEmpty()) {
                return;
            }
            Class<?> cls = obj.getClass();
            try {
                PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(cls).getPropertyDescriptors();
                HashMap map = new HashMap((propertyDescriptors.length * 4) / 3, 1.0f);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    if (writeMethod != null) {
                        map.put(propertyDescriptor.getName(), writeMethod);
                    }
                }
                TemplateHashModel templateHashModel = null;
                for (int i = 0; i < this.namedParamNames.size(); i++) {
                    String str = (String) this.namedParamNames.get(i);
                    if (!map.containsKey(str)) {
                        throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("The ").append(cls.getName()).append(" class has no writeable JavaBeans property called ").append(StringUtil.jQuote(str)).append(".").toString());
                    }
                    Method method = (Method) map.put(str, null);
                    if (method == null) {
                        throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("JavaBeans property ").append(StringUtil.jQuote(str)).append(" is set twice.").toString());
                    }
                    if (templateHashModel == null) {
                        try {
                            TemplateModel templateModelWrap = _ObjectBuilderSettingEvaluator.this.env.getObjectWrapper().wrap(obj);
                            if (!(templateModelWrap instanceof TemplateHashModel)) {
                                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer().append("The ").append(cls.getName()).append(" class is not a wrapped as TemplateHashModel.").toString());
                            }
                            templateHashModel = (TemplateHashModel) templateModelWrap;
                        } catch (Exception e) {
                            throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to set ").append(StringUtil.jQuote(str)).toString(), e);
                        }
                    }
                    TemplateModel templateModel = templateHashModel.get(method.getName());
                    if (templateModel == null) {
                        throw new _ObjectBuilderSettingEvaluationException(new StringBuffer().append("Can't find ").append(method).append(" as FreeMarker method.").toString());
                    }
                    if (!(templateModel instanceof TemplateMethodModelEx)) {
                        throw new _ObjectBuilderSettingEvaluationException(new StringBuffer().append(StringUtil.jQuote(method.getName())).append(" wasn't a TemplateMethodModelEx.").toString());
                    }
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(_ObjectBuilderSettingEvaluator.this.env.getObjectWrapper().wrap(this.namedParamValues.get(i)));
                    ((TemplateMethodModelEx) templateModel).exec(arrayList);
                }
            } catch (Exception e2) {
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to inspect ").append(cls.getName()).append(" class").toString(), e2);
            }
        }

        private Object callBuild(Object obj) throws _ObjectBuilderSettingEvaluationException {
            Class<?> cls = obj.getClass();
            try {
                try {
                    return obj.getClass().getMethod(_ObjectBuilderSettingEvaluator.BUILD_METHOD_NAME, null).invoke(obj, null);
                } catch (Exception e) {
                    e = e;
                    if (e instanceof InvocationTargetException) {
                        e = ((InvocationTargetException) e).getTargetException();
                    }
                    throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to call build() method on ").append(cls.getName()).append(" instance").toString(), e);
                }
            } catch (NoSuchMethodException e2) {
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("The ").append(cls.getName()).append(" builder class must have a public build() method").toString(), e2);
            } catch (Exception e3) {
                throw new _ObjectBuilderSettingEvaluationException(new StringBuffer("Failed to get the build() method of the ").append(cls.getName()).append(" builder class").toString(), e3);
            }
        }

        private boolean hasNoParameters() {
            return this.positionalParamValues.isEmpty() && this.namedParamValues.isEmpty();
        }
    }

    private static class NullExpression extends SettingExpression {
        static final NullExpression INSTANCE = new NullExpression();

        @Override // org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluator.SettingExpression
        Object eval() throws _ObjectBuilderSettingEvaluationException {
            return null;
        }

        private NullExpression() {
            super();
        }
    }
}
