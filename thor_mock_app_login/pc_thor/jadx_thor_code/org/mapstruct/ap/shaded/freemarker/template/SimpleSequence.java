package org.mapstruct.ap.shaded.freemarker.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;

/* loaded from: classes3.dex */
public class SimpleSequence extends WrappingTemplateModel implements TemplateSequenceModel, Serializable {
    protected final List list;
    private List unwrappedList;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SimpleSequence() {
        this((ObjectWrapper) null);
    }

    public SimpleSequence(int i) {
        this.list = new ArrayList(i);
    }

    public SimpleSequence(Collection collection) {
        this(collection, (ObjectWrapper) null);
    }

    public SimpleSequence(TemplateCollectionModel templateCollectionModel) throws TemplateModelException {
        ArrayList arrayList = new ArrayList();
        TemplateModelIterator it = templateCollectionModel.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        arrayList.trimToSize();
        this.list = arrayList;
    }

    public SimpleSequence(ObjectWrapper objectWrapper) {
        super(objectWrapper);
        this.list = new ArrayList();
    }

    public SimpleSequence(int i, ObjectWrapper objectWrapper) {
        super(objectWrapper);
        this.list = new ArrayList(i);
    }

    public SimpleSequence(Collection collection, ObjectWrapper objectWrapper) {
        super(objectWrapper);
        this.list = new ArrayList(collection);
    }

    public void add(Object obj) {
        this.list.add(obj);
        this.unwrappedList = null;
    }

    public void add(boolean z) {
        if (z) {
            add(TemplateBooleanModel.TRUE);
        } else {
            add(TemplateBooleanModel.FALSE);
        }
    }

    public List toList() throws Throwable {
        if (this.unwrappedList == null) {
            Class<?> cls = this.list.getClass();
            try {
                List list = (List) cls.newInstance();
                BeansWrapper defaultInstance = BeansWrapper.getDefaultInstance();
                for (int i = 0; i < this.list.size(); i++) {
                    Object objUnwrap = this.list.get(i);
                    if (objUnwrap instanceof TemplateModel) {
                        objUnwrap = defaultInstance.unwrap((TemplateModel) objUnwrap);
                    }
                    list.add(objUnwrap);
                }
                this.unwrappedList = list;
            } catch (Exception e) {
                throw new TemplateModelException(new StringBuffer("Error instantiating an object of type ").append(cls.getName()).append(StringUtils.LF).append(e.getMessage()).toString());
            }
        }
        return this.unwrappedList;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        try {
            Object obj = this.list.get(i);
            if (obj instanceof TemplateModel) {
                return (TemplateModel) obj;
            }
            TemplateModel templateModelWrap = wrap(obj);
            this.list.set(i, templateModelWrap);
            return templateModelWrap;
        } catch (IndexOutOfBoundsException unused) {
            return null;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() {
        return this.list.size();
    }

    public SimpleSequence synchronizedWrapper() {
        return new SynchronizedSequence();
    }

    public String toString() {
        return this.list.toString();
    }

    private class SynchronizedSequence extends SimpleSequence {
        private SynchronizedSequence() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleSequence
        public void add(Object obj) {
            synchronized (SimpleSequence.this) {
                SimpleSequence.this.add(obj);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleSequence, org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public TemplateModel get(int i) throws TemplateModelException {
            TemplateModel templateModel;
            synchronized (SimpleSequence.this) {
                templateModel = SimpleSequence.this.get(i);
            }
            return templateModel;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleSequence, org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public int size() {
            int size;
            synchronized (SimpleSequence.this) {
                size = SimpleSequence.this.size();
            }
            return size;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.SimpleSequence
        public List toList() throws TemplateModelException {
            List list;
            synchronized (SimpleSequence.this) {
                list = SimpleSequence.this.toList();
            }
            return list;
        }
    }
}
