package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.core.CollectionAndSequence;
import org.mapstruct.ap.shaded.freemarker.core._DelayedFTLTypeDescription;
import org.mapstruct.ap.shaded.freemarker.core._DelayedJQuote;
import org.mapstruct.ap.shaded.freemarker.core._TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class BeanModel implements TemplateHashModelEx, AdapterTemplateModel, WrapperTemplateModel {
    private HashMap memberMap;
    protected final Object object;
    protected final BeansWrapper wrapper;
    private static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.beans");
    static final TemplateModel UNKNOWN = new SimpleScalar("UNKNOWN");
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new BeanModel(obj, (BeansWrapper) objectWrapper);
        }
    };

    public BeanModel(Object obj, BeansWrapper beansWrapper) {
        this(obj, beansWrapper, true);
    }

    BeanModel(Object obj, BeansWrapper beansWrapper, boolean z) {
        this.object = obj;
        this.wrapper = beansWrapper;
        if (!z || obj == null) {
            return;
        }
        beansWrapper.getClassIntrospector().get(obj.getClass());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public TemplateModel get(String str) throws TemplateModelException {
        TemplateModel templateModelInvokeGenericGet;
        Class<?> cls = this.object.getClass();
        Map map = this.wrapper.getClassIntrospector().get(cls);
        try {
            if (this.wrapper.isMethodsShadowItems()) {
                Object obj = map.get(str);
                if (obj != null) {
                    templateModelInvokeGenericGet = invokeThroughDescriptor(obj, map);
                } else {
                    templateModelInvokeGenericGet = invokeGenericGet(map, cls, str);
                }
            } else {
                TemplateModel templateModelInvokeGenericGet2 = invokeGenericGet(map, cls, str);
                TemplateModel templateModelWrap = this.wrapper.wrap(null);
                if (templateModelInvokeGenericGet2 != templateModelWrap && templateModelInvokeGenericGet2 != UNKNOWN) {
                    return templateModelInvokeGenericGet2;
                }
                Object obj2 = map.get(str);
                if (obj2 != null) {
                    TemplateModel templateModelInvokeThroughDescriptor = invokeThroughDescriptor(obj2, map);
                    templateModelInvokeGenericGet = (templateModelInvokeThroughDescriptor == UNKNOWN && templateModelInvokeGenericGet2 == templateModelWrap) ? templateModelWrap : templateModelInvokeThroughDescriptor;
                } else {
                    templateModelInvokeGenericGet = null;
                }
            }
            if (templateModelInvokeGenericGet != UNKNOWN) {
                return templateModelInvokeGenericGet;
            }
            if (this.wrapper.isStrict()) {
                throw new InvalidPropertyException(new StringBuffer("No such bean property: ").append(str).toString());
            }
            if (logger.isDebugEnabled()) {
                logNoSuchKey(str, map);
            }
            return this.wrapper.wrap(null);
        } catch (TemplateModelException e) {
            throw e;
        } catch (Exception e2) {
            throw new _TemplateModelException(e2, new Object[]{"An error has occurred when reading existing sub-variable ", new _DelayedJQuote(str), "; see cause exception! The type of the containing value was: ", new _DelayedFTLTypeDescription(this)});
        }
    }

    private void logNoSuchKey(String str, Map map) {
        logger.debug(new StringBuffer("Key ").append(StringUtil.jQuoteNoXSS(str)).append(" was not found on instance of ").append(this.object.getClass().getName()).append(". Introspection information for the class is: ").append(map).toString());
    }

    protected boolean hasPlainGetMethod() {
        return this.wrapper.getClassIntrospector().get(this.object.getClass()).get(ClassIntrospector.GENERIC_GET_KEY) != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.mapstruct.ap.shaded.freemarker.template.TemplateModel invokeThroughDescriptor(java.lang.Object r5, java.util.Map r6) throws java.lang.IllegalAccessException, org.mapstruct.ap.shaded.freemarker.template.TemplateModelException, java.lang.reflect.InvocationTargetException {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.HashMap r0 = r4.memberMap     // Catch: java.lang.Throwable -> L96
            r1 = 0
            if (r0 == 0) goto Ld
            java.lang.Object r0 = r0.get(r5)     // Catch: java.lang.Throwable -> L96
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r0 = (org.mapstruct.ap.shaded.freemarker.template.TemplateModel) r0     // Catch: java.lang.Throwable -> L96
            goto Le
        Ld:
            r0 = r1
        Le:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L96
            if (r0 == 0) goto L12
            return r0
        L12:
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r2 = org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel.UNKNOWN
            boolean r3 = r5 instanceof java.beans.IndexedPropertyDescriptor
            if (r3 == 0) goto L2f
            r0 = r5
            java.beans.IndexedPropertyDescriptor r0 = (java.beans.IndexedPropertyDescriptor) r0
            java.lang.reflect.Method r0 = r0.getIndexedReadMethod()
            org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMethodModel r1 = new org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMethodModel
            java.lang.Object r2 = r4.object
            java.lang.Class[] r6 = org.mapstruct.ap.shaded.freemarker.ext.beans.ClassIntrospector.getArgTypes(r6, r0)
            org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper r3 = r4.wrapper
            r1.<init>(r2, r0, r6, r3)
        L2c:
            r0 = r1
        L2d:
            r2 = r0
            goto L7d
        L2f:
            boolean r3 = r5 instanceof java.beans.PropertyDescriptor
            if (r3 == 0) goto L43
            r6 = r5
            java.beans.PropertyDescriptor r6 = (java.beans.PropertyDescriptor) r6
            org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper r2 = r4.wrapper
            java.lang.Object r3 = r4.object
            java.lang.reflect.Method r6 = r6.getReadMethod()
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r2 = r2.invokeMethod(r3, r6, r1)
            goto L7d
        L43:
            boolean r1 = r5 instanceof java.lang.reflect.Field
            if (r1 == 0) goto L57
            org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper r6 = r4.wrapper
            r1 = r5
            java.lang.reflect.Field r1 = (java.lang.reflect.Field) r1
            java.lang.Object r2 = r4.object
            java.lang.Object r1 = r1.get(r2)
            org.mapstruct.ap.shaded.freemarker.template.TemplateModel r2 = r6.wrap(r1)
            goto L7d
        L57:
            boolean r1 = r5 instanceof java.lang.reflect.Method
            if (r1 == 0) goto L6c
            r0 = r5
            java.lang.reflect.Method r0 = (java.lang.reflect.Method) r0
            org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMethodModel r1 = new org.mapstruct.ap.shaded.freemarker.ext.beans.SimpleMethodModel
            java.lang.Object r2 = r4.object
            java.lang.Class[] r6 = org.mapstruct.ap.shaded.freemarker.ext.beans.ClassIntrospector.getArgTypes(r6, r0)
            org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper r3 = r4.wrapper
            r1.<init>(r2, r0, r6, r3)
            goto L2c
        L6c:
            boolean r6 = r5 instanceof org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods
            if (r6 == 0) goto L7d
            org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsModel r0 = new org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsModel
            java.lang.Object r6 = r4.object
            r1 = r5
            org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods r1 = (org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods) r1
            org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper r2 = r4.wrapper
            r0.<init>(r6, r1, r2)
            goto L2d
        L7d:
            if (r0 == 0) goto L95
            monitor-enter(r4)
            java.util.HashMap r6 = r4.memberMap     // Catch: java.lang.Throwable -> L92
            if (r6 != 0) goto L8b
            java.util.HashMap r6 = new java.util.HashMap     // Catch: java.lang.Throwable -> L92
            r6.<init>()     // Catch: java.lang.Throwable -> L92
            r4.memberMap = r6     // Catch: java.lang.Throwable -> L92
        L8b:
            java.util.HashMap r6 = r4.memberMap     // Catch: java.lang.Throwable -> L92
            r6.put(r5, r0)     // Catch: java.lang.Throwable -> L92
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L92
            goto L95
        L92:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L92
            throw r5
        L95:
            return r2
        L96:
            r5 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L96
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel.invokeThroughDescriptor(java.lang.Object, java.util.Map):org.mapstruct.ap.shaded.freemarker.template.TemplateModel");
    }

    void clearMemberCache() {
        synchronized (this) {
            this.memberMap = null;
        }
    }

    protected TemplateModel invokeGenericGet(Map map, Class cls, String str) throws IllegalAccessException, TemplateModelException, InvocationTargetException {
        Method method = (Method) map.get(ClassIntrospector.GENERIC_GET_KEY);
        return method == null ? UNKNOWN : this.wrapper.invokeMethod(this.object, method, new Object[]{str});
    }

    protected TemplateModel wrap(Object obj) throws TemplateModelException {
        return this.wrapper.getOuterIdentity().wrap(obj);
    }

    protected Object unwrap(TemplateModel templateModel) throws TemplateModelException {
        return this.wrapper.unwrap(templateModel);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        Object obj = this.object;
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return obj == null || Boolean.FALSE.equals(this.object);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.AdapterTemplateModel
    public Object getAdaptedObject(Class cls) {
        return this.object;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.util.WrapperTemplateModel
    public Object getWrappedObject() {
        return this.object;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return this.wrapper.getClassIntrospector().keyCount(this.object.getClass());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel keys() {
        return new CollectionAndSequence(new SimpleSequence(keySet(), this.wrapper));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public TemplateCollectionModel values() throws TemplateModelException {
        ArrayList arrayList = new ArrayList(size());
        TemplateModelIterator it = keys().iterator();
        while (it.hasNext()) {
            arrayList.add(get(((TemplateScalarModel) it.next()).getAsString()));
        }
        return new CollectionAndSequence(new SimpleSequence(arrayList, this.wrapper));
    }

    String getAsClassicCompatibleString() {
        Object obj = this.object;
        return obj == null ? "null" : obj.toString();
    }

    public String toString() {
        return this.object.toString();
    }

    protected Set keySet() {
        return this.wrapper.getClassIntrospector().keySet(this.object.getClass());
    }
}
