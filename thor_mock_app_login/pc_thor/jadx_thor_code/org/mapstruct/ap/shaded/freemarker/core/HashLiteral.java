package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;

/* loaded from: classes3.dex */
final class HashLiteral extends Expression {
    private final ArrayList keys;
    private final int size;
    private final ArrayList values;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "{...}";
    }

    HashLiteral(ArrayList arrayList, ArrayList arrayList2) {
        this.keys = arrayList;
        this.values = arrayList2;
        this.size = arrayList.size();
        arrayList.trimToSize();
        arrayList2.trimToSize();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    TemplateModel _eval(Environment environment) throws TemplateException {
        return new SequenceHash(environment);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        StringBuffer stringBuffer = new StringBuffer("{");
        for (int i = 0; i < this.size; i++) {
            Expression expression = (Expression) this.keys.get(i);
            Expression expression2 = (Expression) this.values.get(i);
            stringBuffer.append(expression.getCanonicalForm());
            stringBuffer.append(" : ");
            stringBuffer.append(expression2.getCanonicalForm());
            if (i != this.size - 1) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        if (this.constantValue != null) {
            return true;
        }
        for (int i = 0; i < this.size; i++) {
            Expression expression = (Expression) this.keys.get(i);
            Expression expression2 = (Expression) this.values.get(i);
            if (!expression.isLiteral() || !expression2.isLiteral()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        ArrayList arrayList = (ArrayList) this.keys.clone();
        ListIterator listIterator = arrayList.listIterator();
        while (listIterator.hasNext()) {
            listIterator.set(((Expression) listIterator.next()).deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
        }
        ArrayList arrayList2 = (ArrayList) this.values.clone();
        ListIterator listIterator2 = arrayList2.listIterator();
        while (listIterator2.hasNext()) {
            listIterator2.set(((Expression) listIterator2.next()).deepCloneWithIdentifierReplaced(str, expression, replacemenetState));
        }
        return new HashLiteral(arrayList, arrayList2);
    }

    private class SequenceHash implements TemplateHashModelEx {
        private TemplateCollectionModel keyCollection;
        private HashMap map;
        private TemplateCollectionModel valueCollection;

        SequenceHash(Environment environment) throws TemplateException {
            int i = 0;
            if (_TemplateAPI.getTemplateLanguageVersionAsInt(HashLiteral.this) >= _TemplateAPI.VERSION_INT_2_3_21) {
                this.map = new LinkedHashMap();
                while (i < HashLiteral.this.size) {
                    Expression expression = (Expression) HashLiteral.this.keys.get(i);
                    Expression expression2 = (Expression) HashLiteral.this.values.get(i);
                    String strEvalAndCoerceToString = expression.evalAndCoerceToString(environment);
                    TemplateModel templateModelEval = expression2.eval(environment);
                    if (environment == null || !environment.isClassicCompatible()) {
                        expression2.assertNonNull(templateModelEval, environment);
                    }
                    this.map.put(strEvalAndCoerceToString, templateModelEval);
                    i++;
                }
                return;
            }
            this.map = new HashMap();
            ArrayList arrayList = new ArrayList(HashLiteral.this.size);
            ArrayList arrayList2 = new ArrayList(HashLiteral.this.size);
            while (i < HashLiteral.this.size) {
                Expression expression3 = (Expression) HashLiteral.this.keys.get(i);
                Expression expression4 = (Expression) HashLiteral.this.values.get(i);
                String strEvalAndCoerceToString2 = expression3.evalAndCoerceToString(environment);
                TemplateModel templateModelEval2 = expression4.eval(environment);
                if (environment == null || !environment.isClassicCompatible()) {
                    expression4.assertNonNull(templateModelEval2, environment);
                }
                this.map.put(strEvalAndCoerceToString2, templateModelEval2);
                arrayList.add(strEvalAndCoerceToString2);
                arrayList2.add(templateModelEval2);
                i++;
            }
            this.keyCollection = new CollectionAndSequence(new SimpleSequence(arrayList));
            this.valueCollection = new CollectionAndSequence(new SimpleSequence(arrayList2));
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public int size() {
            return HashLiteral.this.size;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel keys() {
            if (this.keyCollection == null) {
                this.keyCollection = new CollectionAndSequence(new SimpleSequence(this.map.keySet()));
            }
            return this.keyCollection;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel values() {
            if (this.valueCollection == null) {
                this.valueCollection = new CollectionAndSequence(new SimpleSequence(this.map.values()));
            }
            return this.valueCollection;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) {
            return (TemplateModel) this.map.get(str);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() {
            return HashLiteral.this.size == 0;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return this.size * 2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        checkIndex(i);
        return (i % 2 == 0 ? this.keys : this.values).get(i / 2);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        checkIndex(i);
        return i % 2 == 0 ? ParameterRole.ITEM_KEY : ParameterRole.ITEM_VALUE;
    }

    private void checkIndex(int i) {
        if (i >= this.size * 2) {
            throw new IndexOutOfBoundsException();
        }
    }
}
