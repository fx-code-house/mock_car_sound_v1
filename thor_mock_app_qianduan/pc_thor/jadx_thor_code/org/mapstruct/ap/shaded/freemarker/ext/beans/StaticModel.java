package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
final class StaticModel implements TemplateHashModelEx {
    private static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.beans");
    private final Class clazz;
    private final Map map = new HashMap();
    private final BeansWrapper wrapper;

    StaticModel(Class cls, BeansWrapper beansWrapper) throws TemplateModelException, SecurityException {
        this.clazz = cls;
        this.wrapper = beansWrapper;
        populate();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        Object obj = this.map.get(str);
        if (obj instanceof TemplateModel) {
            return (TemplateModel) obj;
        }
        if (obj instanceof Field) {
            try {
                return this.wrapper.getOuterIdentity().wrap(((Field) obj).get(null));
            } catch (IllegalAccessException unused) {
                throw new TemplateModelException(new StringBuffer("Illegal access for field ").append(str).append(" of class ").append(this.clazz.getName()).toString());
            }
        }
        throw new TemplateModelException(new StringBuffer("No such key: ").append(str).append(" in class ").append(this.clazz.getName()).toString());
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
    public TemplateCollectionModel keys() throws TemplateModelException {
        return (TemplateCollectionModel) this.wrapper.getOuterIdentity().wrap(this.map.keySet());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel values() throws TemplateModelException {
        return (TemplateCollectionModel) this.wrapper.getOuterIdentity().wrap(this.map.values());
    }

    private void populate() throws TemplateModelException, SecurityException {
        if (!Modifier.isPublic(this.clazz.getModifiers())) {
            throw new TemplateModelException(new StringBuffer("Can't wrap the non-public class ").append(this.clazz.getName()).toString());
        }
        if (this.wrapper.getExposureLevel() == 3) {
            return;
        }
        for (Field field : this.clazz.getFields()) {
            int modifiers = field.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                if (Modifier.isFinal(modifiers)) {
                    try {
                        this.map.put(field.getName(), this.wrapper.getOuterIdentity().wrap(field.get(null)));
                    } catch (IllegalAccessException unused) {
                    }
                } else {
                    this.map.put(field.getName(), field);
                }
            }
        }
        if (this.wrapper.getExposureLevel() < 2) {
            for (Method method : this.clazz.getMethods()) {
                int modifiers2 = method.getModifiers();
                if (Modifier.isPublic(modifiers2) && Modifier.isStatic(modifiers2) && this.wrapper.getClassIntrospector().isAllowedToExpose(method)) {
                    String name = method.getName();
                    Object obj = this.map.get(name);
                    if (obj instanceof Method) {
                        OverloadedMethods overloadedMethods = new OverloadedMethods(this.wrapper.is2321Bugfixed());
                        overloadedMethods.addMethod((Method) obj);
                        overloadedMethods.addMethod(method);
                        this.map.put(name, overloadedMethods);
                    } else if (obj instanceof OverloadedMethods) {
                        ((OverloadedMethods) obj).addMethod(method);
                    } else {
                        if (obj != null) {
                            Logger logger2 = logger;
                            if (logger2.isInfoEnabled()) {
                                logger2.info(new StringBuffer("Overwriting value [").append(obj).append("] for  key '").append(name).append("' with [").append(method).append("] in static model for ").append(this.clazz.getName()).toString());
                            }
                        }
                        this.map.put(name, method);
                    }
                }
            }
            for (Map.Entry entry : this.map.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Method) {
                    Method method2 = (Method) value;
                    entry.setValue(new SimpleMethodModel(null, method2, method2.getParameterTypes(), this.wrapper));
                } else if (value instanceof OverloadedMethods) {
                    entry.setValue(new OverloadedMethodsModel(null, (OverloadedMethods) value, this.wrapper));
                }
            }
        }
    }
}
