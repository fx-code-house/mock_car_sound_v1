package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.cache.TemplateCache;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
final class Include extends TemplateElement {
    private final String baseDirectoryPath;
    private final String encoding;
    private final Expression encodingExp;
    private final Boolean ignoreMissing;
    private final Expression ignoreMissingExp;
    private final Expression includedTemplatePathExp;
    private final Boolean parse;
    private final Expression parseExp;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#include";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 3;
    }

    Include(Template template, Expression expression, Expression expression2, Expression expression3, Expression expression4) throws ParseException {
        this.baseDirectoryPath = getBaseDirectoryPath(template);
        this.includedTemplatePathExp = expression;
        this.encodingExp = expression2;
        if (expression2 != null && expression2.isLiteral()) {
            try {
                TemplateModel templateModelEval = expression2.eval(null);
                if (!(templateModelEval instanceof TemplateScalarModel)) {
                    throw new ParseException("Expected a string as the value of the \"encoding\" argument", expression2);
                }
                this.encoding = ((TemplateScalarModel) templateModelEval).getAsString();
            } catch (TemplateException e) {
                throw new BugException(e);
            }
        } else {
            this.encoding = null;
        }
        this.parseExp = expression3;
        if (expression3 == null) {
            this.parse = Boolean.TRUE;
        } else if (expression3.isLiteral()) {
            try {
                if (expression3 instanceof StringLiteral) {
                    this.parse = Boolean.valueOf(StringUtil.getYesNo(expression3.evalAndCoerceToString(null)));
                } else {
                    try {
                        this.parse = Boolean.valueOf(expression3.evalToBoolean(template.getConfiguration()));
                    } catch (NonBooleanException e2) {
                        throw new ParseException("Expected a boolean or string as the value of the parse attribute", expression3, e2);
                    }
                }
            } catch (TemplateException e3) {
                throw new BugException(e3);
            }
        } else {
            this.parse = null;
        }
        this.ignoreMissingExp = expression4;
        if (expression4 == null) {
            this.ignoreMissing = Boolean.FALSE;
            return;
        }
        if (expression4.isLiteral()) {
            try {
                try {
                    this.ignoreMissing = Boolean.valueOf(expression4.evalToBoolean(template.getConfiguration()));
                    return;
                } catch (NonBooleanException e4) {
                    throw new ParseException("Expected a boolean as the value of the \"ignore_missing\" attribute", expression4, e4);
                }
            } catch (TemplateException e5) {
                throw new BugException(e5);
            }
        }
        this.ignoreMissing = null;
    }

    private String getBaseDirectoryPath(Template template) {
        int iLastIndexOf;
        String name = template.getName();
        return (name == null || (iLastIndexOf = name.lastIndexOf(47)) == -1) ? "" : name.substring(0, iLastIndexOf + 1);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        boolean zModelToBoolean;
        boolean zEvalToBoolean;
        String strEvalAndCoerceToString = this.includedTemplatePathExp.evalAndCoerceToString(environment);
        String strEvalAndCoerceToString2 = this.encoding;
        if (strEvalAndCoerceToString2 == null) {
            Expression expression = this.encodingExp;
            strEvalAndCoerceToString2 = expression != null ? expression.evalAndCoerceToString(environment) : null;
        }
        Boolean bool = this.parse;
        if (bool != null) {
            zModelToBoolean = bool.booleanValue();
        } else {
            TemplateModel templateModelEval = this.parseExp.eval(environment);
            if (templateModelEval instanceof TemplateScalarModel) {
                Expression expression2 = this.parseExp;
                zModelToBoolean = getYesNo(expression2, EvalUtil.modelToString((TemplateScalarModel) templateModelEval, expression2, environment));
            } else {
                zModelToBoolean = this.parseExp.modelToBoolean(templateModelEval, environment);
            }
        }
        Boolean bool2 = this.ignoreMissing;
        if (bool2 != null) {
            zEvalToBoolean = bool2.booleanValue();
        } else {
            zEvalToBoolean = this.ignoreMissingExp.evalToBoolean(environment);
        }
        String fullTemplatePath = TemplateCache.getFullTemplatePath(environment, this.baseDirectoryPath, strEvalAndCoerceToString);
        try {
            Template templateForInclusion = environment.getTemplateForInclusion(fullTemplatePath, strEvalAndCoerceToString2, zModelToBoolean, zEvalToBoolean);
            if (templateForInclusion != null) {
                environment.include(templateForInclusion);
            }
        } catch (ParseException e) {
            throw new _MiscTemplateException(e, environment, new Object[]{"Error parsing included template ", new _DelayedJQuote(fullTemplatePath), ":\n", new _DelayedGetMessage(e)});
        } catch (IOException e2) {
            throw new _MiscTemplateException(e2, environment, new Object[]{"Error reading included file ", new _DelayedJQuote(fullTemplatePath), ":\n", new _DelayedGetMessage(e2)});
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
        stringBuffer.append(this.includedTemplatePathExp.getCanonicalForm());
        if (this.encodingExp != null) {
            stringBuffer.append(" encoding=").append(this.encodingExp.getCanonicalForm());
        }
        if (this.parseExp != null) {
            stringBuffer.append(" parse=").append(this.parseExp.getCanonicalForm());
        }
        if (this.ignoreMissingExp != null) {
            stringBuffer.append(" ignore_missing=").append(this.ignoreMissingExp.getCanonicalForm());
        }
        if (z) {
            stringBuffer.append("/>");
        }
        return stringBuffer.toString();
    }

    private boolean getYesNo(Expression expression, String str) throws TemplateException {
        try {
            return StringUtil.getYesNo(str);
        } catch (IllegalArgumentException unused) {
            throw new _MiscTemplateException(expression, new Object[]{"Value must be boolean (or one of these strings: \"n\", \"no\", \"f\", \"false\", \"y\", \"yes\", \"t\", \"true\"), but it was ", new _DelayedJQuote(str), "."});
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.includedTemplatePathExp;
        }
        if (i == 1) {
            return this.parseExp;
        }
        if (i == 2) {
            return this.encodingExp;
        }
        if (i == 3) {
            return this.ignoreMissingExp;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.TEMPLATE_NAME;
        }
        if (i == 1) {
            return ParameterRole.PARSE_PARAMETER;
        }
        if (i == 2) {
            return ParameterRole.ENCODING_PARAMETER;
        }
        if (i == 3) {
            return ParameterRole.IGNORE_MISSING_PARAMETER;
        }
        throw new IndexOutOfBoundsException();
    }
}
