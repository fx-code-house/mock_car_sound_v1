package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.AbstractCollection;
import java.util.Iterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
class CollectionAdapter extends AbstractCollection implements TemplateModelAdapter {
    private final TemplateCollectionModel model;
    private final BeansWrapper wrapper;

    CollectionAdapter(TemplateCollectionModel templateCollectionModel, BeansWrapper beansWrapper) {
        this.model = templateCollectionModel;
        this.wrapper = beansWrapper;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelAdapter
    public TemplateModel getTemplateModel() {
        return this.model;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        try {
            return new Iterator() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.CollectionAdapter.1
                final TemplateModelIterator i;

                {
                    this.i = CollectionAdapter.this.model.iterator();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    try {
                        return this.i.hasNext();
                    } catch (TemplateModelException e) {
                        throw new UndeclaredThrowableException(e);
                    }
                }

                @Override // java.util.Iterator
                public Object next() {
                    try {
                        return CollectionAdapter.this.wrapper.unwrap(this.i.next());
                    } catch (TemplateModelException e) {
                        throw new UndeclaredThrowableException(e);
                    }
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        } catch (TemplateModelException e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
