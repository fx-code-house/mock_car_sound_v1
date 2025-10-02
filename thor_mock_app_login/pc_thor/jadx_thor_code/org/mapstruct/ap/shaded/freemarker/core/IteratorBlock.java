package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
final class IteratorBlock extends TemplateElement {
    private boolean isForEach;
    private Expression listSource;
    private String loopVariableName;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    IteratorBlock(Expression expression, String str, TemplateElement templateElement, boolean z) {
        this.listSource = expression;
        this.loopVariableName = str;
        this.isForEach = z;
        this.nestedBlock = templateElement;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) throws TemplateException, IOException {
        TemplateModel templateModelEval = this.listSource.eval(environment);
        if (templateModelEval == null) {
            if (environment.isClassicCompatible()) {
                return;
            } else {
                this.listSource.assertNonNull(null, environment);
            }
        }
        environment.visitIteratorBlock(new Context(templateModelEval));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        if (this.isForEach) {
            stringBuffer.append(this.loopVariableName);
            stringBuffer.append(" in ");
            stringBuffer.append(this.listSource.getCanonicalForm());
        } else {
            stringBuffer.append(this.listSource.getCanonicalForm());
            stringBuffer.append(" as ");
            stringBuffer.append(this.loopVariableName);
        }
        if (z) {
            stringBuffer.append(">");
            if (this.nestedBlock != null) {
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
            }
            stringBuffer.append("</");
            stringBuffer.append(getNodeTypeSymbol());
            stringBuffer.append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.listSource;
        }
        if (i == 1) {
            return this.loopVariableName;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.LIST_SOURCE;
        }
        if (i == 1) {
            return ParameterRole.TARGET_LOOP_VARIABLE;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return this.isForEach ? "#foreach" : "#list";
    }

    class Context implements LocalContext {
        private boolean hasNext;
        private int index;
        private TemplateModel list;
        private TemplateModel loopVar;
        private Collection variableNames = null;

        Context(TemplateModel templateModel) {
            this.list = templateModel;
        }

        void runLoop(Environment environment) throws TemplateException, IOException {
            TemplateModel templateModel = this.list;
            if (templateModel instanceof TemplateCollectionModel) {
                TemplateModelIterator it = ((TemplateCollectionModel) templateModel).iterator();
                this.hasNext = it.hasNext();
                while (this.hasNext) {
                    this.loopVar = it.next();
                    this.hasNext = it.hasNext();
                    if (IteratorBlock.this.nestedBlock != null) {
                        environment.visit(IteratorBlock.this.nestedBlock);
                    }
                    this.index++;
                }
                return;
            }
            if (templateModel instanceof TemplateSequenceModel) {
                TemplateSequenceModel templateSequenceModel = (TemplateSequenceModel) templateModel;
                int size = templateSequenceModel.size();
                this.index = 0;
                while (true) {
                    int i = this.index;
                    if (i >= size) {
                        return;
                    }
                    this.loopVar = templateSequenceModel.get(i);
                    this.hasNext = size > this.index + 1;
                    if (IteratorBlock.this.nestedBlock != null) {
                        environment.visitByHiddingParent(IteratorBlock.this.nestedBlock);
                    }
                    this.index++;
                }
            } else {
                if (environment.isClassicCompatible()) {
                    this.loopVar = this.list;
                    if (IteratorBlock.this.nestedBlock != null) {
                        environment.visitByHiddingParent(IteratorBlock.this.nestedBlock);
                        return;
                    }
                    return;
                }
                throw new NonSequenceOrCollectionException(IteratorBlock.this.listSource, this.list, environment);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
        public TemplateModel getLocalVariable(String str) {
            if (!str.startsWith(IteratorBlock.this.loopVariableName)) {
                return null;
            }
            int length = str.length() - IteratorBlock.this.loopVariableName.length();
            if (length == 0) {
                return this.loopVar;
            }
            if (length == 6) {
                if (str.endsWith("_index")) {
                    return new SimpleNumber(this.index);
                }
                return null;
            }
            if (length == 9 && str.endsWith("_has_next")) {
                return this.hasNext ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
            }
            return null;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
        public Collection getLocalVariableNames() {
            if (this.variableNames == null) {
                ArrayList arrayList = new ArrayList(3);
                this.variableNames = arrayList;
                arrayList.add(IteratorBlock.this.loopVariableName);
                this.variableNames.add(new StringBuffer().append(IteratorBlock.this.loopVariableName).append("_index").toString());
                this.variableNames.add(new StringBuffer().append(IteratorBlock.this.loopVariableName).append("_has_next").toString());
            }
            return this.variableNames;
        }
    }
}
