package org.mapstruct.ap.shaded.freemarker.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans._MethodUtil;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class _ErrorDescriptionBuilder {
    private static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.runtime");
    private Expression blamed;
    private final String description;
    private final Object[] descriptionParts;
    private boolean showBlamer;
    private Template template;
    private Object tip;
    private Object[] tips;

    public _ErrorDescriptionBuilder(String str) {
        this.description = str;
        this.descriptionParts = null;
    }

    public _ErrorDescriptionBuilder(Object[] objArr) {
        this.descriptionParts = objArr;
        this.description = null;
    }

    public String toString() {
        return toString((TemplateElement) null, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String toString(org.mapstruct.ap.shaded.freemarker.core.TemplateElement r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core._ErrorDescriptionBuilder.toString(org.mapstruct.ap.shaded.freemarker.core.TemplateElement, boolean):java.lang.String");
    }

    private boolean containsSingleInterpolatoinLiteral(Expression expression, int i) {
        if (expression == null || i > 20) {
            return false;
        }
        if ((expression instanceof StringLiteral) && ((StringLiteral) expression).isSingleInterpolationLiteral()) {
            return true;
        }
        int parameterCount = expression.getParameterCount();
        for (int i2 = 0; i2 < parameterCount; i2++) {
            Object parameterValue = expression.getParameterValue(i2);
            if ((parameterValue instanceof Expression) && containsSingleInterpolatoinLiteral((Expression) parameterValue, i + 1)) {
                return true;
            }
        }
        return false;
    }

    private Blaming findBlaming(TemplateObject templateObject, Expression expression, int i) {
        Blaming blamingFindBlaming;
        if (i > 50) {
            return null;
        }
        int parameterCount = templateObject.getParameterCount();
        for (int i2 = 0; i2 < parameterCount; i2++) {
            Object parameterValue = templateObject.getParameterValue(i2);
            if (parameterValue == expression) {
                Blaming blaming = new Blaming();
                blaming.blamer = templateObject;
                blaming.roleOfblamed = templateObject.getParameterRole(i2);
                return blaming;
            }
            if ((parameterValue instanceof TemplateObject) && (blamingFindBlaming = findBlaming((TemplateObject) parameterValue, expression, i + 1)) != null) {
                return blamingFindBlaming;
            }
        }
        return null;
    }

    private void appendParts(StringBuffer stringBuffer, Object[] objArr) {
        Template template = this.template;
        if (template == null) {
            Expression expression = this.blamed;
            template = expression != null ? expression.getTemplate() : null;
        }
        for (Object obj : objArr) {
            if (obj instanceof Object[]) {
                appendParts(stringBuffer, (Object[]) obj);
            } else {
                String strTryToString = tryToString(obj);
                if (strTryToString == null) {
                    strTryToString = "null";
                }
                if (template != null) {
                    if (strTryToString.length() > 4 && strTryToString.charAt(0) == '<' && ((strTryToString.charAt(1) == '#' || strTryToString.charAt(1) == '@' || (strTryToString.charAt(1) == '/' && (strTryToString.charAt(2) == '#' || strTryToString.charAt(2) == '@'))) && strTryToString.charAt(strTryToString.length() - 1) == '>')) {
                        if (template.getActualTagSyntax() == 2) {
                            stringBuffer.append('[');
                            stringBuffer.append(strTryToString.substring(1, strTryToString.length() - 1));
                            stringBuffer.append(']');
                        } else {
                            stringBuffer.append(strTryToString);
                        }
                    } else {
                        stringBuffer.append(strTryToString);
                    }
                } else {
                    stringBuffer.append(strTryToString);
                }
            }
        }
    }

    public static String toString(Object obj) {
        return toString(obj, false);
    }

    public static String tryToString(Object obj) {
        return toString(obj, true);
    }

    private static String toString(Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Class) {
            return ClassUtil.getShortClassName((Class) obj);
        }
        if ((obj instanceof Method) || (obj instanceof Constructor)) {
            return _MethodUtil.toString((Member) obj);
        }
        return z ? StringUtil.tryToString(obj) : obj.toString();
    }

    private String[] splitToLines(String str) {
        return StringUtil.split(StringUtil.replace(StringUtil.replace(str, "\r\n", StringUtils.LF), StringUtils.CR, StringUtils.LF), '\n');
    }

    public _ErrorDescriptionBuilder template(Template template) {
        this.template = template;
        return this;
    }

    public _ErrorDescriptionBuilder blame(Expression expression) {
        this.blamed = expression;
        return this;
    }

    public _ErrorDescriptionBuilder showBlamer(boolean z) {
        this.showBlamer = z;
        return this;
    }

    public _ErrorDescriptionBuilder tip(String str) {
        tip((Object) str);
        return this;
    }

    public _ErrorDescriptionBuilder tip(Object[] objArr) {
        tip((Object) objArr);
        return this;
    }

    private _ErrorDescriptionBuilder tip(Object obj) {
        if (this.tip == null) {
            this.tip = obj;
        } else {
            Object[] objArr = this.tips;
            if (objArr == null) {
                this.tips = new Object[]{obj};
            } else {
                int length = objArr.length;
                Object[] objArr2 = new Object[length + 1];
                for (int i = 0; i < length; i++) {
                    objArr2[i] = this.tips[i];
                }
                objArr2[length] = obj;
                this.tips = objArr2;
            }
        }
        return this;
    }

    public _ErrorDescriptionBuilder tips(Object[] objArr) {
        Object[] objArr2 = this.tips;
        if (objArr2 == null) {
            this.tips = objArr;
        } else {
            int length = objArr2.length;
            int length2 = objArr.length;
            Object[] objArr3 = new Object[length + length2];
            for (int i = 0; i < length; i++) {
                objArr3[i] = this.tips[i];
            }
            for (int i2 = 0; i2 < length2; i2++) {
                objArr3[length + i2] = objArr[i2];
            }
            this.tips = objArr3;
        }
        return this;
    }

    private static class Blaming {
        TemplateObject blamer;
        ParameterRole roleOfblamed;

        private Blaming() {
        }
    }
}
