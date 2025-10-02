package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
class OverloadedFixArgsMethods extends OverloadedMethodsSubset {
    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsSubset
    void afterWideningUnwrappingHints(Class[] clsArr, int[] iArr) {
    }

    OverloadedFixArgsMethods(boolean z) {
        super(z);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsSubset
    Class[] preprocessParameterTypes(CallableMemberDescriptor callableMemberDescriptor) {
        return callableMemberDescriptor.getParamTypes();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsSubset
    MaybeEmptyMemberAndArguments getMemberAndArguments(List list, BeansWrapper beansWrapper) throws Throwable {
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
        int size = list.size();
        Class[][] unwrappingHintsByParamCount = getUnwrappingHintsByParamCount();
        if (unwrappingHintsByParamCount.length <= size) {
            return EmptyMemberAndArguments.WRONG_NUMBER_OF_ARGUMENTS;
        }
        Class[] clsArr = unwrappingHintsByParamCount[size];
        if (clsArr == null) {
            return EmptyMemberAndArguments.WRONG_NUMBER_OF_ARGUMENTS;
        }
        Object[] objArr = new Object[size];
        int[] typeFlags = getTypeFlags(size);
        if (typeFlags == ALL_ZEROS_ARRAY) {
            typeFlags = null;
        }
        Iterator it = list.iterator();
        for (int i = 0; i < size; i++) {
            Object objTryUnwrap = beansWrapper.tryUnwrap((TemplateModel) it.next(), clsArr[i], typeFlags != null ? typeFlags[i] : 0);
            if (objTryUnwrap == BeansWrapper.CAN_NOT_UNWRAP) {
                return EmptyMemberAndArguments.noCompatibleOverload(i + 1);
            }
            objArr[i] = objTryUnwrap;
        }
        MaybeEmptyCallableMemberDescriptor memberDescriptorForArgs = getMemberDescriptorForArgs(objArr, false);
        if (memberDescriptorForArgs instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptorForArgs;
            if (!this.bugfixed) {
                BeansWrapper.coerceBigDecimals(callableMemberDescriptor.getParamTypes(), objArr);
            } else if (typeFlags != null) {
                forceNumberArgumentsToParameterTypes(objArr, callableMemberDescriptor.getParamTypes(), typeFlags);
            }
            return new MemberAndArguments(callableMemberDescriptor, objArr);
        }
        return EmptyMemberAndArguments.from((EmptyCallableMemberDescriptor) memberDescriptorForArgs, objArr);
    }
}
