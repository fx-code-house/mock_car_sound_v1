package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
public class MapModel extends StringModel implements TemplateMethodModelEx {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.MapModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new MapModel((Map) obj, (BeansWrapper) objectWrapper);
        }
    };

    public MapModel(Map map, BeansWrapper beansWrapper) {
        super(map, beansWrapper);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
    public Object exec(List list) throws TemplateModelException {
        return wrap(((Map) this.object).get(unwrap((TemplateModel) list.get(0))));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel
    protected TemplateModel invokeGenericGet(Map map, Class cls, String str) throws TemplateModelException {
        Map map2 = (Map) this.object;
        Object obj = map2.get(str);
        if (obj == null) {
            if (str.length() == 1) {
                Character ch = new Character(str.charAt(0));
                Object obj2 = map2.get(ch);
                if (obj2 == null && !map2.containsKey(str) && !map2.containsKey(ch)) {
                    return UNKNOWN;
                }
                obj = obj2;
            } else if (!map2.containsKey(str)) {
                return UNKNOWN;
            }
        }
        return wrap(obj);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return ((Map) this.object).isEmpty() && super.isEmpty();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return keySet().size();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel
    protected Set keySet() {
        Set setKeySet = super.keySet();
        setKeySet.addAll(((Map) this.object).keySet());
        return setKeySet;
    }
}
