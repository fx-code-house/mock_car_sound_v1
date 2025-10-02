package org.mapstruct.ap.shaded.freemarker.core;

import java.util.List;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class ExistenceBuiltins {
    private ExistenceBuiltins() {
    }

    private static abstract class ExistenceBuiltIn extends BuiltIn {
        private ExistenceBuiltIn() {
        }

        protected TemplateModel evalMaybeNonexistentTarget(Environment environment) throws TemplateException {
            if (this.target instanceof ParentheticalExpression) {
                boolean fastInvalidReferenceExceptions = environment.setFastInvalidReferenceExceptions(true);
                try {
                    TemplateModel templateModelEval = this.target.eval(environment);
                    environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                    return templateModelEval;
                } catch (InvalidReferenceException unused) {
                    environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                    return null;
                } catch (Throwable th) {
                    environment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                    throw th;
                }
            }
            return this.target.eval(environment);
        }
    }

    static class defaultBI extends ExistenceBuiltIn {
        private static final TemplateMethodModelEx FIRST_NON_NULL_METHOD = new TemplateMethodModelEx() { // from class: org.mapstruct.ap.shaded.freemarker.core.ExistenceBuiltins.defaultBI.1
            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                int size = list.size();
                if (size == 0) {
                    throw MessageUtil.newArgCntError("?default", size, 1, Integer.MAX_VALUE);
                }
                for (int i = 0; i < size; i++) {
                    TemplateModel templateModel = (TemplateModel) list.get(i);
                    if (templateModel != null) {
                        return templateModel;
                    }
                }
                return null;
            }
        };

        defaultBI() {
            super();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEvalMaybeNonexistentTarget = evalMaybeNonexistentTarget(environment);
            return templateModelEvalMaybeNonexistentTarget == null ? FIRST_NON_NULL_METHOD : new ConstantMethod(templateModelEvalMaybeNonexistentTarget);
        }

        private static class ConstantMethod implements TemplateMethodModelEx {
            private final TemplateModel constant;

            ConstantMethod(TemplateModel templateModel) {
                this.constant = templateModel;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) {
                return this.constant;
            }
        }
    }

    static class existsBI extends ExistenceBuiltIn {
        existsBI() {
            super();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            return evalMaybeNonexistentTarget(environment) == null ? TemplateBooleanModel.FALSE : TemplateBooleanModel.TRUE;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        boolean evalToBoolean(Environment environment) throws TemplateException {
            return _eval(environment) == TemplateBooleanModel.TRUE;
        }
    }

    static class has_contentBI extends ExistenceBuiltIn {
        has_contentBI() {
            super();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            return Expression.isEmpty(evalMaybeNonexistentTarget(environment)) ? TemplateBooleanModel.FALSE : TemplateBooleanModel.TRUE;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        boolean evalToBoolean(Environment environment) throws TemplateException {
            return _eval(environment) == TemplateBooleanModel.TRUE;
        }
    }

    static class if_existsBI extends ExistenceBuiltIn {
        if_existsBI() {
            super();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEvalMaybeNonexistentTarget = evalMaybeNonexistentTarget(environment);
            return templateModelEvalMaybeNonexistentTarget == null ? TemplateModel.NOTHING : templateModelEvalMaybeNonexistentTarget;
        }
    }
}
