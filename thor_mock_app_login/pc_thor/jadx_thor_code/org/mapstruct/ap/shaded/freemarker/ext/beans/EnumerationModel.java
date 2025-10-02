package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;

/* loaded from: classes3.dex */
public class EnumerationModel extends BeanModel implements TemplateModelIterator, TemplateCollectionModel {
    private boolean accessed;

    public EnumerationModel(Enumeration enumeration, BeansWrapper beansWrapper) {
        super(enumeration, beansWrapper);
        this.accessed = false;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
    public TemplateModelIterator iterator() throws TemplateModelException {
        synchronized (this) {
            if (this.accessed) {
                throw new TemplateModelException("This collection is stateful and can not be iterated over the second time.");
            }
            this.accessed = true;
        }
        return this;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
    public boolean hasNext() {
        return ((Enumeration) this.object).hasMoreElements();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
    public TemplateModel next() throws TemplateModelException {
        try {
            return wrap(((Enumeration) this.object).nextElement());
        } catch (NoSuchElementException unused) {
            throw new TemplateModelException("No more elements in the enumeration.");
        }
    }

    public boolean getAsBoolean() {
        return hasNext();
    }
}
