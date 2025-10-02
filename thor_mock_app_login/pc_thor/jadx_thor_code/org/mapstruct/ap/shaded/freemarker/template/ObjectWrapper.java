package org.mapstruct.ap.shaded.freemarker.template;

import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;

/* loaded from: classes3.dex */
public interface ObjectWrapper {
    public static final ObjectWrapper BEANS_WRAPPER = BeansWrapper.getDefaultInstance();
    public static final ObjectWrapper DEFAULT_WRAPPER = DefaultObjectWrapper.instance;
    public static final ObjectWrapper SIMPLE_WRAPPER = SimpleObjectWrapper.instance;

    TemplateModel wrap(Object obj) throws TemplateModelException;
}
