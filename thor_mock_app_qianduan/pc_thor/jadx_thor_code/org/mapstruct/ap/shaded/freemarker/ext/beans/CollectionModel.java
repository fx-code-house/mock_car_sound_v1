package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.Collection;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public class CollectionModel extends StringModel implements TemplateCollectionModel, TemplateSequenceModel {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.CollectionModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new CollectionModel((Collection) obj, (BeansWrapper) objectWrapper);
        }
    };

    public CollectionModel(Collection collection, BeansWrapper beansWrapper) {
        super(collection, beansWrapper);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        if (this.object instanceof List) {
            try {
                return wrap(((List) this.object).get(i));
            } catch (IndexOutOfBoundsException unused) {
                return null;
            }
        }
        throw new TemplateModelException(new StringBuffer("Underlying collection is not a list, it's ").append(this.object.getClass().getName()).toString());
    }

    public boolean getSupportsIndexedAccess() {
        return this.object instanceof List;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
    public TemplateModelIterator iterator() {
        return new IteratorModel(((Collection) this.object).iterator(), this.wrapper);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return ((Collection) this.object).size();
    }
}
