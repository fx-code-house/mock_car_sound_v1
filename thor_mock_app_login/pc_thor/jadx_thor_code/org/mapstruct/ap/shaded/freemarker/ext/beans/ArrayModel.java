package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Array;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public class ArrayModel extends BeanModel implements TemplateCollectionModel, TemplateSequenceModel {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.ArrayModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new ArrayModel(obj, (BeansWrapper) objectWrapper);
        }
    };
    private final int length;

    public ArrayModel(Object obj, BeansWrapper beansWrapper) {
        super(obj, beansWrapper);
        if (!obj.getClass().isArray()) {
            throw new IllegalArgumentException(new StringBuffer("Object is not an array, it's ").append(obj.getClass().getName()).toString());
        }
        this.length = Array.getLength(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
    public TemplateModelIterator iterator() {
        return new Iterator();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        try {
            return wrap(Array.get(this.object, i));
        } catch (IndexOutOfBoundsException unused) {
            return null;
        }
    }

    private class Iterator implements TemplateSequenceModel, TemplateModelIterator {
        private int position;

        private Iterator() {
            this.position = 0;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public boolean hasNext() {
            return this.position < ArrayModel.this.length;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public TemplateModel get(int i) throws TemplateModelException {
            return ArrayModel.this.get(i);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public TemplateModel next() throws TemplateModelException {
            if (this.position >= ArrayModel.this.length) {
                return null;
            }
            int i = this.position;
            this.position = i + 1;
            return get(i);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public int size() {
            return ArrayModel.this.size();
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return this.length;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return this.length == 0;
    }
}
