package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
class Interpret extends BuiltIn {
    static /* synthetic */ Class class$freemarker$template$TemplateScalarModel;
    static /* synthetic */ Class class$freemarker$template$TemplateSequenceModel;

    Interpret() {
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws Throwable {
        Expression expression;
        TemplateModel templateModelEval = this.target.eval(environment);
        String strEvalAndCoerceToString = "anonymous_interpreted";
        if (templateModelEval instanceof TemplateSequenceModel) {
            expression = (Expression) new DynamicKeyName(this.target, new NumberLiteral(new Integer(0))).copyLocationFrom(this.target);
            if (((TemplateSequenceModel) templateModelEval).size() > 1) {
                strEvalAndCoerceToString = ((Expression) new DynamicKeyName(this.target, new NumberLiteral(new Integer(1))).copyLocationFrom(this.target)).evalAndCoerceToString(environment);
            }
        } else if (templateModelEval instanceof TemplateScalarModel) {
            expression = this.target;
        } else {
            Expression expression2 = this.target;
            Class[] clsArr = new Class[2];
            Class clsClass$ = class$freemarker$template$TemplateSequenceModel;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel");
                class$freemarker$template$TemplateSequenceModel = clsClass$;
            }
            clsArr[0] = clsClass$;
            Class clsClass$2 = class$freemarker$template$TemplateScalarModel;
            if (clsClass$2 == null) {
                clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel");
                class$freemarker$template$TemplateScalarModel = clsClass$2;
            }
            clsArr[1] = clsClass$2;
            throw new UnexpectedTypeException(expression2, templateModelEval, "sequence or string", clsArr, environment);
        }
        String strEvalAndCoerceToString2 = expression.evalAndCoerceToString(environment);
        Template template = environment.getTemplate();
        try {
            Template template2 = new Template(new StringBuffer().append(template.getName() != null ? template.getName() : "nameless_template").append("->").append(strEvalAndCoerceToString).toString(), strEvalAndCoerceToString2, template.getConfiguration());
            template2.setLocale(environment.getLocale());
            return new TemplateProcessorModel(template2);
        } catch (IOException e) {
            throw new _MiscTemplateException(this, e, environment, new Object[]{"Template parsing with \"?", this.key, "\" has failed with this error:\n\n", "---begin-message---\n", new _DelayedGetMessage(e), "\n---end-message---", "\n\nThe failed expression:"});
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private class TemplateProcessorModel implements TemplateTransformModel {
        private final Template template;

        TemplateProcessorModel(Template template) {
            this.template = template;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
        public Writer getWriter(final Writer writer, Map map) throws TemplateModelException, IOException {
            try {
                Environment currentEnvironment = Environment.getCurrentEnvironment();
                boolean fastInvalidReferenceExceptions = currentEnvironment.setFastInvalidReferenceExceptions(false);
                try {
                    currentEnvironment.include(this.template);
                    return new Writer(writer) { // from class: org.mapstruct.ap.shaded.freemarker.core.Interpret.TemplateProcessorModel.1
                        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
                        public void close() {
                        }

                        @Override // java.io.Writer, java.io.Flushable
                        public void flush() throws IOException {
                            writer.flush();
                        }

                        @Override // java.io.Writer
                        public void write(char[] cArr, int i, int i2) throws IOException {
                            writer.write(cArr, i, i2);
                        }
                    };
                } finally {
                    currentEnvironment.setFastInvalidReferenceExceptions(fastInvalidReferenceExceptions);
                }
            } catch (Exception e) {
                throw new _TemplateModelException(e, new Object[]{"Template created with \"?", Interpret.this.key, "\" has stopped with this error:\n\n", "---begin-message---\n", new _DelayedGetMessage(e), "\n---end-message---"});
            }
        }
    }
}
