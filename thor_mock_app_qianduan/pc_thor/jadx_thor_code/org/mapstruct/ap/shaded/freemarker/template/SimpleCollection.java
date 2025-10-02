package org.mapstruct.ap.shaded.freemarker.template;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class SimpleCollection extends WrappingTemplateModel implements TemplateCollectionModel, Serializable {
    private final Collection collection;
    private final Iterator iterator;
    private boolean iteratorDirty;

    public SimpleCollection(Iterator it) {
        this.iterator = it;
        this.collection = null;
    }

    public SimpleCollection(Collection collection) {
        this.collection = collection;
        this.iterator = null;
    }

    public SimpleCollection(Iterator it, ObjectWrapper objectWrapper) {
        super(objectWrapper);
        this.iterator = it;
        this.collection = null;
    }

    public SimpleCollection(Collection collection, ObjectWrapper objectWrapper) {
        super(objectWrapper);
        this.collection = collection;
        this.iterator = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
    public TemplateModelIterator iterator() {
        SimpleTemplateModelIterator simpleTemplateModelIterator;
        if (this.iterator != null) {
            return new SimpleTemplateModelIterator(this.iterator, true);
        }
        synchronized (this.collection) {
            simpleTemplateModelIterator = new SimpleTemplateModelIterator(this.collection.iterator(), false);
        }
        return simpleTemplateModelIterator;
    }

    private class SimpleTemplateModelIterator implements TemplateModelIterator {
        private Iterator iterator;
        private boolean iteratorShared;

        SimpleTemplateModelIterator(Iterator it, boolean z) {
            this.iterator = it;
            this.iteratorShared = z;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public TemplateModel next() throws TemplateModelException {
            if (this.iteratorShared) {
                makeIteratorDirty();
            }
            if (!this.iterator.hasNext()) {
                throw new TemplateModelException("The collection has no more elements.");
            }
            Object next = this.iterator.next();
            if (next instanceof TemplateModel) {
                return (TemplateModel) next;
            }
            return SimpleCollection.this.wrap(next);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public boolean hasNext() throws TemplateModelException {
            if (this.iteratorShared) {
                makeIteratorDirty();
            }
            return this.iterator.hasNext();
        }

        private void makeIteratorDirty() throws TemplateModelException {
            synchronized (SimpleCollection.this) {
                if (!SimpleCollection.this.iteratorDirty) {
                    SimpleCollection.this.iteratorDirty = true;
                    this.iteratorShared = false;
                } else {
                    throw new TemplateModelException("This collection variable wraps a java.util.Iterator, thus it can be <list>-ed or <foreach>-ed only once");
                }
            }
        }
    }
}
