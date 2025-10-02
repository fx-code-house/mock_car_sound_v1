package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public abstract class Expression extends TemplateObject {
    TemplateModel constantValue;

    abstract TemplateModel _eval(Environment environment) throws TemplateException;

    protected abstract Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, ReplacemenetState replacemenetState);

    abstract boolean isLiteral();

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    void setLocation(Template template, int i, int i2, int i3, int i4) throws ParseException {
        super.setLocation(template, i, i2, i3, i4);
        if (isLiteral()) {
            try {
                this.constantValue = _eval(null);
            } catch (Exception unused) {
            }
        }
    }

    public final TemplateModel getAsTemplateModel(Environment environment) throws TemplateException {
        return eval(environment);
    }

    final TemplateModel eval(Environment environment) throws TemplateException {
        TemplateModel templateModel = this.constantValue;
        return templateModel != null ? templateModel : _eval(environment);
    }

    String evalAndCoerceToString(Environment environment) throws TemplateException {
        return EvalUtil.coerceModelToString(eval(environment), this, null, environment);
    }

    String evalAndCoerceToString(Environment environment, String str) throws TemplateException {
        return EvalUtil.coerceModelToString(eval(environment), this, str, environment);
    }

    static String coerceModelToString(TemplateModel templateModel, Expression expression, Environment environment) throws TemplateException {
        return EvalUtil.coerceModelToString(templateModel, expression, null, environment);
    }

    Number evalToNumber(Environment environment) throws TemplateException {
        return modelToNumber(eval(environment), environment);
    }

    Number modelToNumber(TemplateModel templateModel, Environment environment) throws TemplateException {
        if (templateModel instanceof TemplateNumberModel) {
            return EvalUtil.modelToNumber((TemplateNumberModel) templateModel, this);
        }
        throw new NonNumericalException(this, templateModel, environment);
    }

    boolean evalToBoolean(Environment environment) throws TemplateException {
        return evalToBoolean(environment, null);
    }

    boolean evalToBoolean(Configuration configuration) throws TemplateException {
        return evalToBoolean(null, configuration);
    }

    private boolean evalToBoolean(Environment environment, Configuration configuration) throws TemplateException {
        return modelToBoolean(eval(environment), environment, configuration);
    }

    boolean modelToBoolean(TemplateModel templateModel, Environment environment) throws TemplateException {
        return modelToBoolean(templateModel, environment, null);
    }

    boolean modelToBoolean(TemplateModel templateModel, Configuration configuration) throws TemplateException {
        return modelToBoolean(templateModel, null, configuration);
    }

    private boolean modelToBoolean(TemplateModel templateModel, Environment environment, Configuration configuration) throws TemplateException {
        if (templateModel instanceof TemplateBooleanModel) {
            return ((TemplateBooleanModel) templateModel).getAsBoolean();
        }
        if (environment == null ? !configuration.isClassicCompatible() : !environment.isClassicCompatible()) {
            throw new NonBooleanException(this, templateModel, environment);
        }
        return (templateModel == null || isEmpty(templateModel)) ? false : true;
    }

    final Expression deepCloneWithIdentifierReplaced(String str, Expression expression, ReplacemenetState replacemenetState) {
        Expression expressionDeepCloneWithIdentifierReplaced_inner = deepCloneWithIdentifierReplaced_inner(str, expression, replacemenetState);
        if (expressionDeepCloneWithIdentifierReplaced_inner.beginLine == 0) {
            expressionDeepCloneWithIdentifierReplaced_inner.copyLocationFrom(this);
        }
        return expressionDeepCloneWithIdentifierReplaced_inner;
    }

    static class ReplacemenetState {
        boolean replacementAlreadyInUse;

        ReplacemenetState() {
        }
    }

    static boolean isEmpty(TemplateModel templateModel) throws TemplateModelException {
        if (templateModel instanceof BeanModel) {
            return ((BeanModel) templateModel).isEmpty();
        }
        if (templateModel instanceof TemplateSequenceModel) {
            return ((TemplateSequenceModel) templateModel).size() == 0;
        }
        if (templateModel instanceof TemplateScalarModel) {
            String asString = ((TemplateScalarModel) templateModel).getAsString();
            return asString == null || asString.length() == 0;
        }
        if (templateModel == null) {
            return true;
        }
        if (templateModel instanceof TemplateCollectionModel) {
            return !((TemplateCollectionModel) templateModel).iterator().hasNext();
        }
        if (templateModel instanceof TemplateHashModel) {
            return ((TemplateHashModel) templateModel).isEmpty();
        }
        return ((templateModel instanceof TemplateNumberModel) || (templateModel instanceof TemplateDateModel) || (templateModel instanceof TemplateBooleanModel)) ? false : true;
    }

    void assertNonNull(TemplateModel templateModel, Environment environment) throws InvalidReferenceException {
        if (templateModel == null) {
            throw InvalidReferenceException.getInstance(this, environment);
        }
    }
}
