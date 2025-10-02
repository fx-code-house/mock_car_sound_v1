package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleCollection;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
class DefaultToExpression extends Expression {
    private static final TemplateCollectionModel EMPTY_COLLECTION = new SimpleCollection(new ArrayList(0));
    static final TemplateModel EMPTY_STRING_AND_SEQUENCE = new EmptyStringAndSequence();
    private final Expression lho;
    private final Expression rho;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "...!...";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return false;
    }

    private static class EmptyStringAndSequence implements TemplateScalarModel, TemplateSequenceModel, TemplateHashModelEx {
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public TemplateModel get(int i) {
            return null;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) {
            return null;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
        public String getAsString() {
            return "";
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() {
            return true;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public int size() {
            return 0;
        }

        private EmptyStringAndSequence() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel keys() {
            return DefaultToExpression.EMPTY_COLLECTION;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel values() {
            return DefaultToExpression.EMPTY_COLLECTION;
        }
    }

    DefaultToExpression(Expression expression, Expression expression2) {
        this.lho = expression;
        this.rho = expression2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        TemplateModel templateModelEval;
        Expression expression = this.lho;
        if (expression instanceof ParentheticalExpression) {
            boolean fastInvalidReferenceExceptions = environment.setFastInvalidReferenceExceptions(true);
            try {
                templateModelEval = this.lho.eval(environment);
                environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
            } catch (InvalidReferenceException unused) {
                environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                templateModelEval = null;
            } catch (Throwable th) {
                environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                throw th;
            }
        } else {
            templateModelEval = expression.eval(environment);
        }
        if (templateModelEval != null) {
            return templateModelEval;
        }
        Expression expression2 = this.rho;
        return expression2 == null ? EMPTY_STRING_AND_SEQUENCE : expression2.eval(environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        Expression expressionDeepCloneWithIdentifierReplaced = this.lho.deepCloneWithIdentifierReplaced(str, expression, replacemenetState);
        Expression expression2 = this.rho;
        return new DefaultToExpression(expressionDeepCloneWithIdentifierReplaced, expression2 != null ? expression2.deepCloneWithIdentifierReplaced(str, expression, replacemenetState) : null);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        if (this.rho == null) {
            return new StringBuffer().append(this.lho.getCanonicalForm()).append('!').toString();
        }
        return new StringBuffer().append(this.lho.getCanonicalForm()).append('!').append(this.rho.getCanonicalForm()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.lho;
        }
        if (i == 1) {
            return this.rho;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return ParameterRole.forBinaryOperatorOperand(i);
    }
}
