package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
final class ReflectionCallableMemberDescriptor extends CallableMemberDescriptor {
    private final Member member;
    final Class[] paramTypes;

    ReflectionCallableMemberDescriptor(Method method, Class[] clsArr) {
        this.member = method;
        this.paramTypes = clsArr;
    }

    ReflectionCallableMemberDescriptor(Constructor constructor, Class[] clsArr) {
        this.member = constructor;
        this.paramTypes = clsArr;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    TemplateModel invokeMethod(BeansWrapper beansWrapper, Object obj, Object[] objArr) throws IllegalAccessException, TemplateModelException, InvocationTargetException {
        return beansWrapper.invokeMethod(obj, (Method) this.member, objArr);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    Object invokeConstructor(BeansWrapper beansWrapper, Object[] objArr) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
        return ((Constructor) this.member).newInstance(objArr);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    String getDeclaration() {
        return _MethodUtil.toString(this.member);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    boolean isConstructor() {
        return this.member instanceof Constructor;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    boolean isStatic() {
        return (this.member.getModifiers() & 8) != 0;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    boolean isVarargs() {
        return _MethodUtil.isVarargs(this.member);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    Class[] getParamTypes() {
        return this.paramTypes;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
    String getName() {
        return this.member.getName();
    }
}
