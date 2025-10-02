package org.mapstruct.ap.shaded.freemarker.core;

import java.util.HashSet;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
final class AddConcatExpression extends Expression {
    private final Expression left;
    private final Expression right;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "+";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    AddConcatExpression(Expression expression, Expression expression2) {
        this.left = expression;
        this.right = expression2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws Throwable {
        ArithmeticEngine arithmeticEngine;
        TemplateModel templateModelEval = this.left.eval(environment);
        TemplateModel templateModelEval2 = this.right.eval(environment);
        if ((templateModelEval instanceof TemplateNumberModel) && (templateModelEval2 instanceof TemplateNumberModel)) {
            Number numberModelToNumber = EvalUtil.modelToNumber((TemplateNumberModel) templateModelEval, this.left);
            Number numberModelToNumber2 = EvalUtil.modelToNumber((TemplateNumberModel) templateModelEval2, this.right);
            if (environment != null) {
                arithmeticEngine = environment.getArithmeticEngine();
            } else {
                arithmeticEngine = getTemplate().getArithmeticEngine();
            }
            return new SimpleNumber(arithmeticEngine.add(numberModelToNumber, numberModelToNumber2));
        }
        if ((templateModelEval instanceof TemplateSequenceModel) && (templateModelEval2 instanceof TemplateSequenceModel)) {
            return new ConcatenatedSequence((TemplateSequenceModel) templateModelEval, (TemplateSequenceModel) templateModelEval2);
        }
        try {
            String strCoerceModelToString = Expression.coerceModelToString(templateModelEval, this.left, environment);
            String str = "null";
            if (strCoerceModelToString == null) {
                strCoerceModelToString = "null";
            }
            String strCoerceModelToString2 = Expression.coerceModelToString(templateModelEval2, this.right, environment);
            if (strCoerceModelToString2 != null) {
                str = strCoerceModelToString2;
            }
            return new SimpleScalar(strCoerceModelToString.concat(str));
        } catch (NonStringException e) {
            if ((templateModelEval instanceof TemplateHashModel) && (templateModelEval2 instanceof TemplateHashModel)) {
                if ((templateModelEval instanceof TemplateHashModelEx) && (templateModelEval2 instanceof TemplateHashModelEx)) {
                    TemplateHashModelEx templateHashModelEx = (TemplateHashModelEx) templateModelEval;
                    TemplateHashModelEx templateHashModelEx2 = (TemplateHashModelEx) templateModelEval2;
                    return templateHashModelEx.size() == 0 ? templateHashModelEx2 : templateHashModelEx2.size() == 0 ? templateHashModelEx : new ConcatenatedHashEx(templateHashModelEx, templateHashModelEx2);
                }
                return new ConcatenatedHash((TemplateHashModel) templateModelEval, (TemplateHashModel) templateModelEval2);
            }
            throw e;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return this.constantValue != null || (this.left.isLiteral() && this.right.isLiteral());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        return new AddConcatExpression(this.left.deepCloneWithIdentifierReplaced(str, expression, replacemenetState), this.right.deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.left.getCanonicalForm()).append(" + ").append(this.right.getCanonicalForm()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        return i == 0 ? this.left : this.right;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        return ParameterRole.forBinaryOperatorOperand(i);
    }

    private static final class ConcatenatedSequence implements TemplateSequenceModel {
        private final TemplateSequenceModel left;
        private final TemplateSequenceModel right;

        ConcatenatedSequence(TemplateSequenceModel templateSequenceModel, TemplateSequenceModel templateSequenceModel2) {
            this.left = templateSequenceModel;
            this.right = templateSequenceModel2;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public int size() throws TemplateModelException {
            return this.left.size() + this.right.size();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public TemplateModel get(int i) throws TemplateModelException {
            int size = this.left.size();
            return i < size ? this.left.get(i) : this.right.get(i - size);
        }
    }

    private static class ConcatenatedHash implements TemplateHashModel {
        protected final TemplateHashModel left;
        protected final TemplateHashModel right;

        ConcatenatedHash(TemplateHashModel templateHashModel, TemplateHashModel templateHashModel2) {
            this.left = templateHashModel;
            this.right = templateHashModel2;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            TemplateModel templateModel = this.right.get(str);
            return templateModel != null ? templateModel : this.left.get(str);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() throws TemplateModelException {
            return this.left.isEmpty() && this.right.isEmpty();
        }
    }

    private static final class ConcatenatedHashEx extends ConcatenatedHash implements TemplateHashModelEx {
        private CollectionAndSequence keys;
        private int size;
        private CollectionAndSequence values;

        ConcatenatedHashEx(TemplateHashModelEx templateHashModelEx, TemplateHashModelEx templateHashModelEx2) {
            super(templateHashModelEx, templateHashModelEx2);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public int size() throws TemplateModelException {
            initKeys();
            return this.size;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel keys() throws TemplateModelException {
            initKeys();
            return this.keys;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel values() throws TemplateModelException {
            initValues();
            return this.values;
        }

        private void initKeys() throws TemplateModelException {
            if (this.keys == null) {
                HashSet hashSet = new HashSet();
                SimpleSequence simpleSequence = new SimpleSequence(32);
                addKeys(hashSet, simpleSequence, (TemplateHashModelEx) this.left);
                addKeys(hashSet, simpleSequence, (TemplateHashModelEx) this.right);
                this.size = hashSet.size();
                this.keys = new CollectionAndSequence(simpleSequence);
            }
        }

        private static void addKeys(Set set, SimpleSequence simpleSequence, TemplateHashModelEx templateHashModelEx) throws TemplateModelException {
            TemplateModelIterator it = templateHashModelEx.keys().iterator();
            while (it.hasNext()) {
                TemplateScalarModel templateScalarModel = (TemplateScalarModel) it.next();
                if (set.add(templateScalarModel.getAsString())) {
                    simpleSequence.add(templateScalarModel);
                }
            }
        }

        private void initValues() throws TemplateModelException {
            if (this.values == null) {
                SimpleSequence simpleSequence = new SimpleSequence(size());
                int size = this.keys.size();
                for (int i = 0; i < size; i++) {
                    simpleSequence.add(get(((TemplateScalarModel) this.keys.get(i)).getAsString()));
                }
                this.values = new CollectionAndSequence(simpleSequence);
            }
        }
    }
}
