package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.InvocationTargetException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class MemberAndArguments extends MaybeEmptyMemberAndArguments {
    private final Object[] args;
    private final CallableMemberDescriptor callableMemberDesc;

    MemberAndArguments(CallableMemberDescriptor callableMemberDescriptor, Object[] objArr) {
        this.callableMemberDesc = callableMemberDescriptor;
        this.args = objArr;
    }

    Object[] getArgs() {
        return this.args;
    }

    TemplateModel invokeMethod(BeansWrapper beansWrapper, Object obj) throws IllegalAccessException, TemplateModelException, InvocationTargetException {
        return this.callableMemberDesc.invokeMethod(beansWrapper, obj, this.args);
    }

    Object invokeConstructor(BeansWrapper beansWrapper) throws IllegalAccessException, InstantiationException, TemplateModelException, IllegalArgumentException, InvocationTargetException {
        return this.callableMemberDesc.invokeConstructor(beansWrapper, this.args);
    }

    CallableMemberDescriptor getCallableMemberDescriptor() {
        return this.callableMemberDesc;
    }
}
