package org.mapstruct.ap.shaded.freemarker.template;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapperConfiguration;
import org.mapstruct.ap.shaded.freemarker.ext.dom.NodeModel;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.w3c.dom.Node;

/* loaded from: classes3.dex */
public class DefaultObjectWrapper extends BeansWrapper {
    private static final Class JYTHON_OBJ_CLASS;
    private static final ObjectWrapper JYTHON_WRAPPER;
    static final DefaultObjectWrapper instance = new DefaultObjectWrapper();

    static {
        ObjectWrapper objectWrapper;
        Class<?> cls = null;
        try {
            Class<?> cls2 = Class.forName("org.python.core.PyObject");
            objectWrapper = (ObjectWrapper) Class.forName("org.mapstruct.ap.shaded.freemarker.ext.jython.JythonWrapper").getField("INSTANCE").get(null);
            cls = cls2;
        } catch (Throwable th) {
            if (!(th instanceof ClassNotFoundException)) {
                try {
                    Logger.getLogger("org.mapstruct.ap.shaded.freemarker.template.DefaultObjectWrapper").error("Failed to init Jython support, so it was disabled.", th);
                } catch (Throwable unused) {
                }
            }
            objectWrapper = null;
        }
        JYTHON_OBJ_CLASS = cls;
        JYTHON_WRAPPER = objectWrapper;
    }

    public DefaultObjectWrapper() {
    }

    public DefaultObjectWrapper(Version version) {
        super(version);
    }

    protected DefaultObjectWrapper(BeansWrapperConfiguration beansWrapperConfiguration, boolean z) {
        super(beansWrapperConfiguration, z);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper, org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper
    public TemplateModel wrap(Object obj) throws TemplateModelException {
        if (obj == null) {
            return super.wrap(null);
        }
        if (obj instanceof TemplateModel) {
            return (TemplateModel) obj;
        }
        if (obj instanceof String) {
            return new SimpleScalar((String) obj);
        }
        if (obj instanceof Number) {
            return new SimpleNumber((Number) obj);
        }
        if (obj instanceof Date) {
            if (obj instanceof java.sql.Date) {
                return new SimpleDate((java.sql.Date) obj);
            }
            if (obj instanceof Time) {
                return new SimpleDate((Time) obj);
            }
            if (obj instanceof Timestamp) {
                return new SimpleDate((Timestamp) obj);
            }
            return new SimpleDate((Date) obj, getDefaultDateType());
        }
        if (obj.getClass().isArray()) {
            obj = convertArray(obj);
        }
        if (obj instanceof Collection) {
            return new SimpleSequence((Collection) obj, this);
        }
        if (obj instanceof Map) {
            return new SimpleHash((Map) obj, this);
        }
        if (obj instanceof Boolean) {
            return obj.equals(Boolean.TRUE) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
        if (obj instanceof Iterator) {
            return new SimpleCollection((Iterator) obj, this);
        }
        return handleUnknownType(obj);
    }

    protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException {
        if (obj instanceof Node) {
            return wrapDomNode(obj);
        }
        ObjectWrapper objectWrapper = JYTHON_WRAPPER;
        if (objectWrapper != null && JYTHON_OBJ_CLASS.isInstance(obj)) {
            return objectWrapper.wrap(obj);
        }
        return super.wrap(obj);
    }

    public TemplateModel wrapDomNode(Object obj) {
        return NodeModel.wrap((Node) obj);
    }

    protected Object convertArray(Object obj) {
        int length = Array.getLength(obj);
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            arrayList.add(Array.get(obj, i));
        }
        return arrayList;
    }
}
