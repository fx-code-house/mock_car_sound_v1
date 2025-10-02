package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.List;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.CollectionAndSequence;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.WrappingTemplateModel;

/* loaded from: classes3.dex */
public class SimpleMapModel extends WrappingTemplateModel implements TemplateHashModelEx, TemplateMethodModelEx, AdapterTemplateModel, WrapperTemplateModel {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMapModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new SimpleMapModel((Map) obj, (BeansWrapper) objectWrapper);
        }
    };
    private final Map map;

    public SimpleMapModel(Map map, BeansWrapper beansWrapper) {
        super(beansWrapper);
        this.map = map;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        Object obj = this.map.get(str);
        if (obj == null) {
            if (str.length() == 1) {
                Character ch = new Character(str.charAt(0));
                Object obj2 = this.map.get(ch);
                if (obj2 == null && !this.map.containsKey(str) && !this.map.containsKey(ch)) {
                    return null;
                }
                obj = obj2;
            } else if (!this.map.containsKey(str)) {
                return null;
            }
        }
        return wrap(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
    public Object exec(List list) throws Throwable {
        Object objUnwrap = ((BeansWrapper) getObjectWrapper()).unwrap((TemplateModel) list.get(0));
        Object obj = this.map.get(objUnwrap);
        if (obj != null || this.map.containsKey(objUnwrap)) {
            return wrap(obj);
        }
        return null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return this.map.size();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel keys() {
        return new CollectionAndSequence(new SimpleSequence(this.map.keySet(), getObjectWrapper()));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel values() {
        return new CollectionAndSequence(new SimpleSequence(this.map.values(), getObjectWrapper()));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel
    public Object getAdaptedObject(Class cls) {
        return this.map;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel
    public Object getWrappedObject() {
        return this.map;
    }
}
