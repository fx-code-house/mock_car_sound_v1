package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.io.Serializable;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public class Constants {
    public static final TemplateCollectionModel EMPTY_COLLECTION;
    public static final TemplateHashModelEx EMPTY_HASH;
    public static final TemplateModelIterator EMPTY_ITERATOR;
    public static final TemplateSequenceModel EMPTY_SEQUENCE;
    public static final TemplateBooleanModel TRUE = TemplateBooleanModel.TRUE;
    public static final TemplateBooleanModel FALSE = TemplateBooleanModel.FALSE;
    public static final TemplateScalarModel EMPTY_STRING = (TemplateScalarModel) TemplateScalarModel.EMPTY_STRING;
    public static final TemplateNumberModel ZERO = new SimpleNumber(0);
    public static final TemplateNumberModel ONE = new SimpleNumber(1);
    public static final TemplateNumberModel MINUS_ONE = new SimpleNumber(-1);

    static {
        EMPTY_ITERATOR = new EmptyIteratorModel();
        EMPTY_COLLECTION = new EmptyCollectionModel();
        EMPTY_SEQUENCE = new EmptySequenceModel();
        EMPTY_HASH = new EmptyHashModel();
    }

    private static class EmptyIteratorModel implements TemplateModelIterator, Serializable {
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public boolean hasNext() throws TemplateModelException {
            return false;
        }

        private EmptyIteratorModel() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public TemplateModel next() throws TemplateModelException {
            throw new TemplateModelException("The collection has no more elements.");
        }
    }

    private static class EmptyCollectionModel implements TemplateCollectionModel, Serializable {
        private EmptyCollectionModel() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
        public TemplateModelIterator iterator() throws TemplateModelException {
            return Constants.EMPTY_ITERATOR;
        }
    }

    private static class EmptySequenceModel implements TemplateSequenceModel, Serializable {
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public TemplateModel get(int i) throws TemplateModelException {
            return null;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public int size() throws TemplateModelException {
            return 0;
        }

        private EmptySequenceModel() {
        }
    }

    private static class EmptyHashModel implements TemplateHashModelEx, Serializable {
        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public TemplateModel get(String str) throws TemplateModelException {
            return null;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
        public boolean isEmpty() throws TemplateModelException {
            return true;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public int size() throws TemplateModelException {
            return 0;
        }

        private EmptyHashModel() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel keys() throws TemplateModelException {
            return Constants.EMPTY_COLLECTION;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
        public TemplateCollectionModel values() throws TemplateModelException {
            return Constants.EMPTY_COLLECTION;
        }
    }
}
