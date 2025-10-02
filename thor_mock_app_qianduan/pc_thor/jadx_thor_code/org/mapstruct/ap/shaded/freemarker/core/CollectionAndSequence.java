package org.mapstruct.ap.shaded.freemarker.core;

import java.io.Serializable;
import java.util.ArrayList;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;

/* loaded from: classes3.dex */
public final class CollectionAndSequence implements TemplateCollectionModel, TemplateSequenceModel, Serializable {
    private TemplateCollectionModel collection;
    private ArrayList data;
    private TemplateSequenceModel sequence;

    public CollectionAndSequence(TemplateCollectionModel templateCollectionModel) {
        this.collection = templateCollectionModel;
    }

    public CollectionAndSequence(TemplateSequenceModel templateSequenceModel) {
        this.sequence = templateSequenceModel;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
    public TemplateModelIterator iterator() throws TemplateModelException {
        TemplateCollectionModel templateCollectionModel = this.collection;
        if (templateCollectionModel != null) {
            return templateCollectionModel.iterator();
        }
        return new SequenceIterator(this.sequence);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public TemplateModel get(int i) throws TemplateModelException {
        TemplateSequenceModel templateSequenceModel = this.sequence;
        if (templateSequenceModel != null) {
            return templateSequenceModel.get(i);
        }
        initSequence();
        return (TemplateModel) this.data.get(i);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
    public int size() throws TemplateModelException {
        TemplateSequenceModel templateSequenceModel = this.sequence;
        if (templateSequenceModel != null) {
            return templateSequenceModel.size();
        }
        initSequence();
        return this.data.size();
    }

    private void initSequence() throws TemplateModelException {
        if (this.data == null) {
            this.data = new ArrayList();
            TemplateModelIterator it = this.collection.iterator();
            while (it.hasNext()) {
                this.data.add(it.next());
            }
        }
    }

    private static class SequenceIterator implements TemplateModelIterator {
        private int index = 0;
        private final TemplateSequenceModel sequence;
        private final int size;

        SequenceIterator(TemplateSequenceModel templateSequenceModel) throws TemplateModelException {
            this.sequence = templateSequenceModel;
            this.size = templateSequenceModel.size();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public TemplateModel next() throws TemplateModelException {
            TemplateSequenceModel templateSequenceModel = this.sequence;
            int i = this.index;
            this.index = i + 1;
            return templateSequenceModel.get(i);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
        public boolean hasNext() {
            return this.index < this.size;
        }
    }
}
