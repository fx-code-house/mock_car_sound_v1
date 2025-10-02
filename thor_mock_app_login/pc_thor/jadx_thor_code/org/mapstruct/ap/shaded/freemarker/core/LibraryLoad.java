package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public final class LibraryLoad extends TemplateElement {
    private String namespace;
    private Expression templateName;
    private final String templatePath;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#import";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    LibraryLoad(Template template, Expression expression, String str) {
        this.namespace = str;
        String name = template.getName();
        name = name == null ? "" : name;
        int iLastIndexOf = name.lastIndexOf(47);
        this.templatePath = iLastIndexOf != -1 ? name.substring(0, iLastIndexOf + 1) : "";
        this.templateName = expression;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        String strSubstring;
        String strEvalAndCoerceToString = this.templateName.evalAndCoerceToString(environment);
        try {
            if (!environment.isClassicCompatible() && strEvalAndCoerceToString.indexOf("://") <= 0) {
                if (strEvalAndCoerceToString.length() > 0 && strEvalAndCoerceToString.charAt(0) == '/') {
                    int iIndexOf = this.templatePath.indexOf("://");
                    if (iIndexOf > 0) {
                        strSubstring = new StringBuffer().append(this.templatePath.substring(0, iIndexOf + 2)).append(strEvalAndCoerceToString).toString();
                    } else {
                        strSubstring = strEvalAndCoerceToString.substring(1);
                    }
                    strEvalAndCoerceToString = strSubstring;
                } else {
                    strEvalAndCoerceToString = new StringBuffer().append(this.templatePath).append(strEvalAndCoerceToString).toString();
                }
            }
            environment.importLib(environment.getTemplateForImporting(strEvalAndCoerceToString), this.namespace);
        } catch (ParseException e) {
            throw new _MiscTemplateException(e, environment, new Object[]{"Error parsing imported template ", new _DelayedJQuote(strEvalAndCoerceToString), ":\n", new _DelayedGetMessage(e)});
        } catch (IOException e2) {
            throw new _MiscTemplateException(e2, environment, new Object[]{"Error reading imported template ", strEvalAndCoerceToString});
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        stringBuffer.append(this.templateName);
        stringBuffer.append(" as ");
        stringBuffer.append(this.namespace);
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.templateName;
        }
        if (i == 1) {
            return this.namespace;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.TEMPLATE_NAME;
        }
        if (i == 1) {
            return ParameterRole.NAMESPACE;
        }
        throw new IndexOutOfBoundsException();
    }

    public String getTemplateName() {
        return this.templateName.toString();
    }
}
