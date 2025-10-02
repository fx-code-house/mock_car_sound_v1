package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
class OverloadedVarArgsMethods extends OverloadedMethodsSubset {
    OverloadedVarArgsMethods(boolean z) {
        super(z);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsSubset
    Class[] preprocessParameterTypes(CallableMemberDescriptor callableMemberDescriptor) {
        Class[] clsArr = (Class[]) callableMemberDescriptor.getParamTypes().clone();
        int length = clsArr.length - 1;
        Class<?> componentType = clsArr[length].getComponentType();
        if (componentType == null) {
            throw new BugException("Only varargs methods should be handled here");
        }
        clsArr[length] = componentType;
        return clsArr;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsSubset
    void afterWideningUnwrappingHints(Class[] clsArr, int[] iArr) {
        Class[] clsArr2;
        int length = clsArr.length;
        Class[][] unwrappingHintsByParamCount = getUnwrappingHintsByParamCount();
        int i = length - 1;
        int i2 = i;
        while (true) {
            if (i2 < 0) {
                break;
            }
            Class[] clsArr3 = unwrappingHintsByParamCount[i2];
            if (clsArr3 != null) {
                widenHintsToCommonSupertypes(length, clsArr3, getTypeFlags(i2));
                break;
            }
            i2--;
        }
        int i3 = length + 1;
        if (i3 < unwrappingHintsByParamCount.length && (clsArr2 = unwrappingHintsByParamCount[i3]) != null) {
            widenHintsToCommonSupertypes(length, clsArr2, getTypeFlags(i3));
        }
        while (i3 < unwrappingHintsByParamCount.length) {
            widenHintsToCommonSupertypes(i3, clsArr, iArr);
            i3++;
        }
        if (length > 0) {
            widenHintsToCommonSupertypes(i, clsArr, iArr);
        }
    }

    private void widenHintsToCommonSupertypes(int i, Class[] clsArr, int[] iArr) {
        Class[] clsArr2 = getUnwrappingHintsByParamCount()[i];
        if (clsArr2 == null) {
            return;
        }
        int length = clsArr2.length;
        int length2 = clsArr.length;
        int iMin = Math.min(length2, length);
        for (int i2 = 0; i2 < iMin; i2++) {
            clsArr2[i2] = getCommonSupertypeForUnwrappingHint(clsArr2[i2], clsArr[i2]);
        }
        if (length > length2) {
            Class cls = clsArr[length2 - 1];
            while (length2 < length) {
                clsArr2[length2] = getCommonSupertypeForUnwrappingHint(clsArr2[length2], cls);
                length2++;
            }
        }
        if (this.bugfixed) {
            mergeInTypesFlags(i, iArr);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethodsSubset
    MaybeEmptyMemberAndArguments getMemberAndArguments(List list, BeansWrapper beansWrapper) throws Throwable {
        List list2 = list == null ? Collections.EMPTY_LIST : list;
        int size = list2.size();
        Class[][] unwrappingHintsByParamCount = getUnwrappingHintsByParamCount();
        Object[] objArr = new Object[size];
        int iMin = Math.min(size + 1, unwrappingHintsByParamCount.length - 1);
        int[] typeFlags = null;
        loop0: while (iMin >= 0) {
            Class[] clsArr = unwrappingHintsByParamCount[iMin];
            if (clsArr != null) {
                typeFlags = getTypeFlags(iMin);
                if (typeFlags == ALL_ZEROS_ARRAY) {
                    typeFlags = null;
                }
                Iterator it = list2.iterator();
                int i = 0;
                while (i < size) {
                    int i2 = i < iMin ? i : iMin - 1;
                    Object objTryUnwrap = beansWrapper.tryUnwrap((TemplateModel) it.next(), clsArr[i2], typeFlags != null ? typeFlags[i2] : 0);
                    if (objTryUnwrap == BeansWrapper.CAN_NOT_UNWRAP) {
                        break;
                    }
                    objArr[i] = objTryUnwrap;
                    i++;
                }
                break loop0;
            }
            if (iMin == 0) {
                return EmptyMemberAndArguments.WRONG_NUMBER_OF_ARGUMENTS;
            }
            iMin--;
        }
        MaybeEmptyCallableMemberDescriptor memberDescriptorForArgs = getMemberDescriptorForArgs(objArr, true);
        if (memberDescriptorForArgs instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptorForArgs;
            Object objReplaceVarargsSectionWithArray = replaceVarargsSectionWithArray(objArr, list2, callableMemberDescriptor, beansWrapper);
            if (objReplaceVarargsSectionWithArray instanceof Object[]) {
                Object[] objArr2 = (Object[]) objReplaceVarargsSectionWithArray;
                if (!this.bugfixed) {
                    BeansWrapper.coerceBigDecimals(callableMemberDescriptor.getParamTypes(), objArr2);
                } else if (typeFlags != null) {
                    forceNumberArgumentsToParameterTypes(objArr2, callableMemberDescriptor.getParamTypes(), typeFlags);
                }
                return new MemberAndArguments(callableMemberDescriptor, objArr2);
            }
            return EmptyMemberAndArguments.noCompatibleOverload(((Integer) objReplaceVarargsSectionWithArray).intValue());
        }
        return EmptyMemberAndArguments.from((EmptyCallableMemberDescriptor) memberDescriptorForArgs, objArr);
    }

    private Object replaceVarargsSectionWithArray(Object[] objArr, List list, CallableMemberDescriptor callableMemberDescriptor, BeansWrapper beansWrapper) throws TemplateModelException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Class[] paramTypes = callableMemberDescriptor.getParamTypes();
        int length = paramTypes.length;
        int i = length - 1;
        Class<?> componentType = paramTypes[i].getComponentType();
        int length2 = objArr.length;
        if (objArr.length != length) {
            Object[] objArr2 = new Object[length];
            System.arraycopy(objArr, 0, objArr2, 0, i);
            Object objNewInstance = Array.newInstance(componentType, length2 - i);
            for (int i2 = i; i2 < length2; i2++) {
                Object objTryUnwrap = beansWrapper.tryUnwrap((TemplateModel) list.get(i2), componentType);
                if (objTryUnwrap == BeansWrapper.CAN_NOT_UNWRAP) {
                    return new Integer(i2 + 1);
                }
                Array.set(objNewInstance, i2 - i, objTryUnwrap);
            }
            objArr2[i] = objNewInstance;
            return objArr2;
        }
        Object objTryUnwrap2 = beansWrapper.tryUnwrap((TemplateModel) list.get(i), componentType);
        if (objTryUnwrap2 == BeansWrapper.CAN_NOT_UNWRAP) {
            return new Integer(i + 1);
        }
        Object objNewInstance2 = Array.newInstance(componentType, 1);
        Array.set(objNewInstance2, 0, objTryUnwrap2);
        objArr[i] = objNewInstance2;
        return objArr;
    }
}
