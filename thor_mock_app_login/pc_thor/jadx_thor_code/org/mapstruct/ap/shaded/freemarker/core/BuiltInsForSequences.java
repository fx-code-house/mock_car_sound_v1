package org.mapstruct.ap.shaded.freemarker.core;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.ext.beans.CollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelListSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.Constants;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
class BuiltInsForSequences {

    static class chunkBI extends BuiltInForSequence {
        chunkBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private final TemplateSequenceModel tsm;

            private BIMethod(TemplateSequenceModel templateSequenceModel) {
                this.tsm = templateSequenceModel;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                chunkBI.this.checkMethodArgCount(list, 1, 2);
                return new ChunkedSequence(this.tsm, chunkBI.this.getNumberMethodArg(list, 0).intValue(), list.size() > 1 ? (TemplateModel) list.get(1) : null);
            }
        }

        private static class ChunkedSequence implements TemplateSequenceModel {
            private final int chunkSize;
            private final TemplateModel fillerItem;
            private final int numberOfChunks;
            private final TemplateSequenceModel wrappedTsm;

            private ChunkedSequence(TemplateSequenceModel templateSequenceModel, int i, TemplateModel templateModel) throws TemplateModelException {
                if (i < 1) {
                    throw new _TemplateModelException(new Object[]{"The 1st argument to ?', key, ' (...) must be at least 1."});
                }
                this.wrappedTsm = templateSequenceModel;
                this.chunkSize = i;
                this.fillerItem = templateModel;
                this.numberOfChunks = ((templateSequenceModel.size() + i) - 1) / i;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
            public TemplateModel get(int i) throws TemplateModelException {
                if (i >= this.numberOfChunks) {
                    return null;
                }
                return new TemplateSequenceModel(i) { // from class: org.mapstruct.ap.shaded.freemarker.core.BuiltInsForSequences.chunkBI.ChunkedSequence.1
                    private final int baseIndex;
                    private final /* synthetic */ int val$chunkIndex;

                    {
                        this.val$chunkIndex = i;
                        this.baseIndex = i * ChunkedSequence.this.chunkSize;
                    }

                    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
                    public TemplateModel get(int i2) throws TemplateModelException {
                        int i3 = this.baseIndex + i2;
                        if (i3 < ChunkedSequence.this.wrappedTsm.size()) {
                            return ChunkedSequence.this.wrappedTsm.get(i3);
                        }
                        if (i3 < ChunkedSequence.this.numberOfChunks * ChunkedSequence.this.chunkSize) {
                            return ChunkedSequence.this.fillerItem;
                        }
                        return null;
                    }

                    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
                    public int size() throws TemplateModelException {
                        return (ChunkedSequence.this.fillerItem != null || this.val$chunkIndex + 1 < ChunkedSequence.this.numberOfChunks) ? ChunkedSequence.this.chunkSize : ChunkedSequence.this.wrappedTsm.size() - this.baseIndex;
                    }
                };
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
            public int size() throws TemplateModelException {
                return this.numberOfChunks;
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForSequence
        TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) throws TemplateModelException {
            return new BIMethod(templateSequenceModel);
        }
    }

    static class firstBI extends BuiltInForSequence {
        firstBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForSequence
        TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) throws TemplateModelException {
            if (templateSequenceModel.size() == 0) {
                return null;
            }
            return templateSequenceModel.get(0);
        }
    }

    static class joinBI extends BuiltIn {
        joinBI() {
        }

        private class BIMethodForCollection implements TemplateMethodModelEx {
            private final TemplateCollectionModel coll;
            private final Environment env;

            private BIMethodForCollection(Environment environment, TemplateCollectionModel templateCollectionModel) {
                this.env = environment;
                this.coll = templateCollectionModel;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                joinBI.this.checkMethodArgCount(list, 1, 3);
                String stringMethodArg = joinBI.this.getStringMethodArg(list, 0);
                String optStringMethodArg = joinBI.this.getOptStringMethodArg(list, 1);
                String optStringMethodArg2 = joinBI.this.getOptStringMethodArg(list, 2);
                StringBuffer stringBuffer = new StringBuffer();
                TemplateModelIterator it = this.coll.iterator();
                boolean z = false;
                int i = 0;
                while (it.hasNext()) {
                    TemplateModel next = it.next();
                    if (next != null) {
                        if (z) {
                            stringBuffer.append(stringMethodArg);
                        } else {
                            z = true;
                        }
                        try {
                            stringBuffer.append(EvalUtil.coerceModelToString(next, null, null, this.env));
                        } catch (TemplateException e) {
                            throw new _TemplateModelException(e, new Object[]{"\"?", joinBI.this.key, "\" failed at index ", new Integer(i), " with this error:\n\n", "---begin-message---\n", new _DelayedGetMessageWithoutStackTop(e), "\n---end-message---"});
                        }
                    }
                    i++;
                }
                if (z) {
                    if (optStringMethodArg2 != null) {
                        stringBuffer.append(optStringMethodArg2);
                    }
                } else if (optStringMethodArg != null) {
                    stringBuffer.append(optStringMethodArg);
                }
                return new SimpleScalar(stringBuffer.toString());
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            if (templateModelEval instanceof TemplateCollectionModel) {
                if (templateModelEval instanceof RightUnboundedRangeModel) {
                    throw new _TemplateModelException("The sequence to join was right-unbounded numerical range, thus it's infinitely long.");
                }
                return new BIMethodForCollection(environment, (TemplateCollectionModel) templateModelEval);
            }
            if (templateModelEval instanceof TemplateSequenceModel) {
                return new BIMethodForCollection(environment, new CollectionAndSequence((TemplateSequenceModel) templateModelEval));
            }
            throw new NonSequenceOrCollectionException(this.target, templateModelEval, environment);
        }
    }

    static class lastBI extends BuiltInForSequence {
        lastBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForSequence
        TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) throws TemplateModelException {
            if (templateSequenceModel.size() == 0) {
                return null;
            }
            return templateSequenceModel.get(templateSequenceModel.size() - 1);
        }
    }

    static class reverseBI extends BuiltInForSequence {

        private static class ReverseSequence implements TemplateSequenceModel {
            private final TemplateSequenceModel seq;

            ReverseSequence(TemplateSequenceModel templateSequenceModel) {
                this.seq = templateSequenceModel;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
            public TemplateModel get(int i) throws TemplateModelException {
                return this.seq.get((r0.size() - 1) - i);
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
            public int size() throws TemplateModelException {
                return this.seq.size();
            }
        }

        reverseBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForSequence
        TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) {
            if (templateSequenceModel instanceof ReverseSequence) {
                return ((ReverseSequence) templateSequenceModel).seq;
            }
            return new ReverseSequence(templateSequenceModel);
        }
    }

    static class seq_containsBI extends BuiltIn {
        seq_containsBI() {
        }

        private class BIMethodForCollection implements TemplateMethodModelEx {
            private TemplateCollectionModel m_coll;
            private Environment m_env;

            private BIMethodForCollection(TemplateCollectionModel templateCollectionModel, Environment environment) {
                this.m_coll = templateCollectionModel;
                this.m_env = environment;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                seq_containsBI.this.checkMethodArgCount(list, 1);
                int i = 0;
                TemplateModel templateModel = (TemplateModel) list.get(0);
                TemplateModelIterator it = this.m_coll.iterator();
                while (it.hasNext()) {
                    if (BuiltInsForSequences.modelsEqual(i, it.next(), templateModel, this.m_env)) {
                        return TemplateBooleanModel.TRUE;
                    }
                    i++;
                }
                return TemplateBooleanModel.FALSE;
            }
        }

        private class BIMethodForSequence implements TemplateMethodModelEx {
            private Environment m_env;
            private TemplateSequenceModel m_seq;

            private BIMethodForSequence(TemplateSequenceModel templateSequenceModel, Environment environment) {
                this.m_seq = templateSequenceModel;
                this.m_env = environment;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                seq_containsBI.this.checkMethodArgCount(list, 1);
                TemplateModel templateModel = (TemplateModel) list.get(0);
                int size = this.m_seq.size();
                for (int i = 0; i < size; i++) {
                    if (BuiltInsForSequences.modelsEqual(i, this.m_seq.get(i), templateModel, this.m_env)) {
                        return TemplateBooleanModel.TRUE;
                    }
                }
                return TemplateBooleanModel.FALSE;
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            TemplateModel templateModelEval = this.target.eval(environment);
            if ((templateModelEval instanceof TemplateSequenceModel) && !BuiltInsForSequences.isBuggySeqButGoodCollection(templateModelEval)) {
                return new BIMethodForSequence((TemplateSequenceModel) templateModelEval, environment);
            }
            if (templateModelEval instanceof TemplateCollectionModel) {
                return new BIMethodForCollection((TemplateCollectionModel) templateModelEval, environment);
            }
            throw new NonSequenceOrCollectionException(this.target, templateModelEval, environment);
        }
    }

    static class seq_index_ofBI extends BuiltIn {
        private int m_dir;

        private class BIMethod implements TemplateMethodModelEx {
            protected final TemplateCollectionModel m_col;
            protected final Environment m_env;
            protected final TemplateSequenceModel m_seq;

            private BIMethod(Environment environment) throws TemplateException {
                TemplateModel templateModelEval = seq_index_ofBI.this.target.eval(environment);
                TemplateCollectionModel templateCollectionModel = null;
                TemplateSequenceModel templateSequenceModel = (!(templateModelEval instanceof TemplateSequenceModel) || BuiltInsForSequences.isBuggySeqButGoodCollection(templateModelEval)) ? null : (TemplateSequenceModel) templateModelEval;
                this.m_seq = templateSequenceModel;
                if (templateSequenceModel == null && (templateModelEval instanceof TemplateCollectionModel)) {
                    templateCollectionModel = (TemplateCollectionModel) templateModelEval;
                }
                this.m_col = templateCollectionModel;
                if (templateSequenceModel == null && templateCollectionModel == null) {
                    throw new NonSequenceOrCollectionException(seq_index_ofBI.this.target, templateModelEval, environment);
                }
                this.m_env = environment;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public final Object exec(List list) throws TemplateModelException {
                int iFindInCol;
                int size = list.size();
                seq_index_ofBI.this.checkMethodArgCount(size, 1, 2);
                TemplateModel templateModel = (TemplateModel) list.get(0);
                if (size > 1) {
                    int iIntValue = seq_index_ofBI.this.getNumberMethodArg(list, 1).intValue();
                    if (this.m_seq != null) {
                        iFindInCol = findInSeq(templateModel, iIntValue);
                    } else {
                        iFindInCol = findInCol(templateModel, iIntValue);
                    }
                } else if (this.m_seq != null) {
                    iFindInCol = findInSeq(templateModel);
                } else {
                    iFindInCol = findInCol(templateModel);
                }
                return iFindInCol == -1 ? Constants.MINUS_ONE : new SimpleNumber(iFindInCol);
            }

            int findInCol(TemplateModel templateModel) throws TemplateModelException {
                return findInCol(templateModel, 0, Integer.MAX_VALUE);
            }

            protected int findInCol(TemplateModel templateModel, int i) throws TemplateModelException {
                if (seq_index_ofBI.this.m_dir == 1) {
                    return findInCol(templateModel, i, Integer.MAX_VALUE);
                }
                return findInCol(templateModel, 0, i);
            }

            protected int findInCol(TemplateModel templateModel, int i, int i2) throws TemplateModelException {
                int i3 = -1;
                if (i2 < 0) {
                    return -1;
                }
                TemplateModelIterator it = this.m_col.iterator();
                for (int i4 = 0; it.hasNext() && i4 <= i2; i4++) {
                    TemplateModel next = it.next();
                    if (i4 >= i && BuiltInsForSequences.modelsEqual(i4, next, templateModel, this.m_env)) {
                        if (seq_index_ofBI.this.m_dir == 1) {
                            return i4;
                        }
                        i3 = i4;
                    }
                }
                return i3;
            }

            int findInSeq(TemplateModel templateModel) throws TemplateModelException {
                int size = this.m_seq.size();
                return findInSeq(templateModel, seq_index_ofBI.this.m_dir == 1 ? 0 : size - 1, size);
            }

            private int findInSeq(TemplateModel templateModel, int i) throws TemplateModelException {
                int size = this.m_seq.size();
                if (seq_index_ofBI.this.m_dir != 1) {
                    if (i >= size) {
                        i = size - 1;
                    }
                    if (i < 0) {
                        return -1;
                    }
                } else {
                    if (i >= size) {
                        return -1;
                    }
                    if (i < 0) {
                        i = 0;
                    }
                }
                return findInSeq(templateModel, i, size);
            }

            private int findInSeq(TemplateModel templateModel, int i, int i2) throws TemplateModelException {
                if (seq_index_ofBI.this.m_dir == 1) {
                    while (i < i2) {
                        if (BuiltInsForSequences.modelsEqual(i, this.m_seq.get(i), templateModel, this.m_env)) {
                            return i;
                        }
                        i++;
                    }
                    return -1;
                }
                while (i >= 0) {
                    if (BuiltInsForSequences.modelsEqual(i, this.m_seq.get(i), templateModel, this.m_env)) {
                        return i;
                    }
                    i--;
                }
                return -1;
            }
        }

        seq_index_ofBI(int i) {
            this.m_dir = i;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            return new BIMethod(environment);
        }
    }

    static class sort_byBI extends sortBI {
        sort_byBI() {
        }

        class BIMethod implements TemplateMethodModelEx {
            TemplateSequenceModel seq;

            BIMethod(TemplateSequenceModel templateSequenceModel) {
                this.seq = templateSequenceModel;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                String[] strArr;
                if (list.size() < 1) {
                    throw MessageUtil.newArgCntError(new StringBuffer("?").append(sort_byBI.this.key).toString(), list.size(), 1);
                }
                Object obj = list.get(0);
                if (obj instanceof TemplateScalarModel) {
                    strArr = new String[]{((TemplateScalarModel) obj).getAsString()};
                } else if (obj instanceof TemplateSequenceModel) {
                    TemplateSequenceModel templateSequenceModel = (TemplateSequenceModel) obj;
                    int size = templateSequenceModel.size();
                    String[] strArr2 = new String[size];
                    for (int i = 0; i < size; i++) {
                        TemplateModel templateModel = templateSequenceModel.get(i);
                        try {
                            strArr2[i] = ((TemplateScalarModel) templateModel).getAsString();
                        } catch (ClassCastException unused) {
                            if (!(templateModel instanceof TemplateScalarModel)) {
                                throw new _TemplateModelException(new Object[]{"The argument to ?", sort_byBI.this.key, "(key), when it's a sequence, must be a sequence of strings, but the item at index ", new Integer(i), " is not a string."});
                            }
                        }
                    }
                    strArr = strArr2;
                } else {
                    throw new _TemplateModelException(new Object[]{"The argument to ?", sort_byBI.this.key, "(key) must be a string (the name of the subvariable), or a sequence of strings (the \"path\" to the subvariable)."});
                }
                return sortBI.sort(this.seq, strArr);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInsForSequences.sortBI, org.mapstruct.ap.shaded.freemarker.core.BuiltInForSequence
        TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) {
            return new BIMethod(templateSequenceModel);
        }
    }

    static class sortBI extends BuiltInForSequence {
        static final int KEY_TYPE_BOOLEAN = 4;
        static final int KEY_TYPE_DATE = 3;
        static final int KEY_TYPE_NOT_YET_DETECTED = 0;
        static final int KEY_TYPE_NUMBER = 2;
        static final int KEY_TYPE_STRING = 1;

        static Object[] startErrorMessage(int i) {
            Object[] objArr = new Object[2];
            objArr[0] = i == 0 ? "?sort" : "?sort_by(...)";
            objArr[1] = " failed: ";
            return objArr;
        }

        sortBI() {
        }

        private static class BooleanKVPComparator implements Comparator, Serializable {
            private BooleanKVPComparator() {
            }

            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                boolean zBooleanValue = ((Boolean) ((KVP) obj).key).booleanValue();
                boolean zBooleanValue2 = ((Boolean) ((KVP) obj2).key).booleanValue();
                return zBooleanValue ? !zBooleanValue2 ? 1 : 0 : zBooleanValue2 ? -1 : 0;
            }
        }

        private static class DateKVPComparator implements Comparator, Serializable {
            private DateKVPComparator() {
            }

            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return ((Date) ((KVP) obj).key).compareTo((Date) ((KVP) obj2).key);
            }
        }

        private static class KVP {
            private Object key;
            private Object value;

            private KVP(Object obj, Object obj2) {
                this.key = obj;
                this.value = obj2;
            }
        }

        private static class LexicalKVPComparator implements Comparator {
            private Collator collator;

            LexicalKVPComparator(Collator collator) {
                this.collator = collator;
            }

            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return this.collator.compare(((KVP) obj).key, ((KVP) obj2).key);
            }
        }

        private static class NumericalKVPComparator implements Comparator {
            private ArithmeticEngine ae;

            private NumericalKVPComparator(ArithmeticEngine arithmeticEngine) {
                this.ae = arithmeticEngine;
            }

            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                try {
                    return this.ae.compareNumbers((Number) ((KVP) obj).key, (Number) ((KVP) obj2).key);
                } catch (TemplateException e) {
                    throw new ClassCastException(new StringBuffer("Failed to compare numbers: ").append(e).toString());
                }
            }
        }

        static TemplateModelException newInconsistentSortKeyTypeException(int i, String str, String str2, int i2, TemplateModel templateModel) {
            String str3;
            String str4;
            if (i == 0) {
                str3 = "value";
                str4 = "values";
            } else {
                str3 = "key value";
                str4 = "key values";
            }
            return new _TemplateModelException(new Object[]{startErrorMessage(i, i2), "All ", str4, " in the sequence must be ", str2, ", because the first ", str3, " was that. However, the ", str3, " of the current item isn't a ", str, " but a ", new _DelayedFTLTypeDescription(templateModel), "."});
        }

        static TemplateSequenceModel sort(TemplateSequenceModel templateSequenceModel, String[] strArr) throws TemplateModelException {
            int size = templateSequenceModel.size();
            if (size == 0) {
                return templateSequenceModel;
            }
            ArrayList arrayList = new ArrayList(size);
            int length = strArr == null ? 0 : strArr.length;
            char c = 0;
            Comparator booleanKVPComparator = null;
            for (int i = 0; i < size; i++) {
                TemplateModel templateModel = templateSequenceModel.get(i);
                int i2 = 0;
                TemplateModel templateModel2 = templateModel;
                while (i2 < length) {
                    try {
                        templateModel2 = ((TemplateHashModel) templateModel2).get(strArr[i2]);
                        if (templateModel2 == null) {
                            throw new _TemplateModelException(new Object[]{startErrorMessage(length, i), new StringBuffer("The ").append(StringUtil.jQuote(strArr[i2])).toString(), " subvariable was not found."});
                        }
                        i2++;
                    } catch (ClassCastException e) {
                        if (!(templateModel2 instanceof TemplateHashModel)) {
                            Object[] objArr = new Object[6];
                            objArr[0] = startErrorMessage(length, i);
                            objArr[1] = i2 == 0 ? "Sequence items must be hashes when using ?sort_by. " : new StringBuffer("The ").append(StringUtil.jQuote(strArr[i2 - 1])).toString();
                            objArr[2] = " subvariable is not a hash, so ?sort_by ";
                            objArr[3] = "can't proceed with getting the ";
                            objArr[4] = new _DelayedJQuote(strArr[i2]);
                            objArr[5] = " subvariable.";
                            throw new _TemplateModelException(objArr);
                        }
                        throw e;
                    }
                }
                if (c == 0) {
                    if (templateModel2 instanceof TemplateScalarModel) {
                        booleanKVPComparator = new LexicalKVPComparator(Environment.getCurrentEnvironment().getCollator());
                        c = 1;
                    } else if (templateModel2 instanceof TemplateNumberModel) {
                        booleanKVPComparator = new NumericalKVPComparator(Environment.getCurrentEnvironment().getArithmeticEngine());
                        c = 2;
                    } else {
                        if (templateModel2 instanceof TemplateDateModel) {
                            booleanKVPComparator = new DateKVPComparator();
                            c = 3;
                        } else if (templateModel2 instanceof TemplateBooleanModel) {
                            booleanKVPComparator = new BooleanKVPComparator();
                            c = 4;
                        } else {
                            throw new _TemplateModelException(new Object[]{startErrorMessage(length, i), "Values used for sorting must be numbers, strings, date/times or booleans."});
                        }
                    }
                }
                if (c == 1) {
                    try {
                        arrayList.add(new KVP(((TemplateScalarModel) templateModel2).getAsString(), templateModel));
                    } catch (ClassCastException e2) {
                        if (!(templateModel2 instanceof TemplateScalarModel)) {
                            throw newInconsistentSortKeyTypeException(length, "string", "strings", i, templateModel2);
                        }
                        throw e2;
                    }
                } else if (c == 2) {
                    try {
                        arrayList.add(new KVP(((TemplateNumberModel) templateModel2).getAsNumber(), templateModel));
                    } catch (ClassCastException unused) {
                        if (!(templateModel2 instanceof TemplateNumberModel)) {
                            throw newInconsistentSortKeyTypeException(length, "number", "numbers", i, templateModel2);
                        }
                    }
                } else if (c == 3) {
                    try {
                        arrayList.add(new KVP(((TemplateDateModel) templateModel2).getAsDate(), templateModel));
                    } catch (ClassCastException unused2) {
                        if (!(templateModel2 instanceof TemplateDateModel)) {
                            throw newInconsistentSortKeyTypeException(length, "date/time", "date/times", i, templateModel2);
                        }
                    }
                } else if (c == 4) {
                    try {
                        arrayList.add(new KVP(Boolean.valueOf(((TemplateBooleanModel) templateModel2).getAsBoolean()), templateModel));
                    } catch (ClassCastException unused3) {
                        if (!(templateModel2 instanceof TemplateBooleanModel)) {
                            throw newInconsistentSortKeyTypeException(length, "boolean", "booleans", i, templateModel2);
                        }
                    }
                } else {
                    throw new BugException("Unexpected key type");
                }
            }
            try {
                Collections.sort(arrayList, booleanKVPComparator);
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.set(i3, ((KVP) arrayList.get(i3)).value);
                }
                return new TemplateModelListSequence(arrayList);
            } catch (Exception e3) {
                throw new _TemplateModelException(e3, new Object[]{startErrorMessage(length), new StringBuffer("Unexpected error while sorting:").append(e3).toString()});
            }
        }

        static Object[] startErrorMessage(int i, int i2) {
            Object[] objArr = new Object[4];
            objArr[0] = i == 0 ? "?sort" : "?sort_by(...)";
            objArr[1] = " failed at sequence index ";
            objArr[2] = new Integer(i2);
            objArr[3] = i2 == 0 ? ": " : " (0-based): ";
            return objArr;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForSequence
        TemplateModel calculateResult(TemplateSequenceModel templateSequenceModel) throws TemplateModelException {
            return sort(templateSequenceModel, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isBuggySeqButGoodCollection(TemplateModel templateModel) {
        return (templateModel instanceof CollectionModel) && !((CollectionModel) templateModel).getSupportsIndexedAccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean modelsEqual(int i, TemplateModel templateModel, TemplateModel templateModel2, Environment environment) throws TemplateModelException {
        try {
            return EvalUtil.compare(templateModel, null, 1, null, templateModel2, null, null, true, true, true, environment);
        } catch (TemplateException e) {
            throw new _TemplateModelException(e, new Object[]{"This error has occurred when comparing sequence item at 0-based index ", new Integer(i), " to the searched item:\n", new _DelayedGetMessage(e)});
        }
    }

    private BuiltInsForSequences() {
    }
}
