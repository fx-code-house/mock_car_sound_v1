package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.InvocationTargetException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
abstract class CallableMemberDescriptor extends MaybeEmptyCallableMemberDescriptor {
    abstract String getDeclaration();

    abstract String getName();

    abstract Class[] getParamTypes();

    abstract Object invokeConstructor(BeansWrapper beansWrapper, Object[] objArr) throws IllegalAccessException, InstantiationException, TemplateModelException, IllegalArgumentException, InvocationTargetException;

    abstract TemplateModel invokeMethod(BeansWrapper beansWrapper, Object obj, Object[] objArr) throws IllegalAccessException, TemplateModelException, InvocationTargetException;

    abstract boolean isConstructor();

    abstract boolean isStatic();

    abstract boolean isVarargs();

    CallableMemberDescriptor() {
    }
}
