package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core._DelayedFTLTypeDescription;
import org.mapstruct.ap.shaded.freemarker.core._DelayedOrdinal;
import org.mapstruct.ap.shaded.freemarker.core._TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
class SimpleMethod {
    private final Class[] argTypes;
    private final Member member;

    protected SimpleMethod(Member member, Class[] clsArr) {
        this.member = member;
        this.argTypes = clsArr;
    }

    Object[] unwrapArguments(List list, BeansWrapper beansWrapper) throws TemplateModelException {
        List list2 = list == null ? Collections.EMPTY_LIST : list;
        boolean zIsVarargs = _MethodUtil.isVarargs(this.member);
        int length = this.argTypes.length;
        if (zIsVarargs) {
            int i = length - 1;
            if (i > list2.size()) {
                Object[] objArr = new Object[7];
                objArr[0] = _MethodUtil.invocationErrorMessageStart(this.member);
                objArr[1] = " takes at least ";
                objArr[2] = new Integer(i);
                objArr[3] = i != 1 ? " arguments" : " argument";
                objArr[4] = ", but ";
                objArr[5] = new Integer(list2.size());
                objArr[6] = " was given.";
                throw new _TemplateModelException(objArr);
            }
        } else if (length != list2.size()) {
            Object[] objArr2 = new Object[7];
            objArr2[0] = _MethodUtil.invocationErrorMessageStart(this.member);
            objArr2[1] = " takes ";
            objArr2[2] = new Integer(length);
            objArr2[3] = length != 1 ? " arguments" : " argument";
            objArr2[4] = ", but ";
            objArr2[5] = new Integer(list2.size());
            objArr2[6] = " was given.";
            throw new _TemplateModelException(objArr2);
        }
        return unwrapArguments(list2, this.argTypes, zIsVarargs, beansWrapper);
    }

    private Object[] unwrapArguments(List list, Class[] clsArr, boolean z, BeansWrapper beansWrapper) throws TemplateModelException, ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Object objTryUnwrap;
        if (list == null) {
            return null;
        }
        int length = clsArr.length;
        int size = list.size();
        Object[] objArr = new Object[length];
        Iterator it = list.iterator();
        int i = z ? length - 1 : length;
        int i2 = 0;
        int i3 = 0;
        while (i3 < i) {
            Class cls = clsArr[i3];
            TemplateModel templateModel = (TemplateModel) it.next();
            Object objTryUnwrap2 = beansWrapper.tryUnwrap(templateModel, cls);
            if (objTryUnwrap2 == BeansWrapper.CAN_NOT_UNWRAP) {
                throw createArgumentTypeMismarchException(i3, templateModel, cls);
            }
            if (objTryUnwrap2 == null && cls.isPrimitive()) {
                throw createNullToPrimitiveArgumentException(i3, cls);
            }
            objArr[i3] = objTryUnwrap2;
            i3++;
        }
        if (z) {
            Class cls2 = clsArr[length - 1];
            Class<?> componentType = cls2.getComponentType();
            if (!it.hasNext()) {
                objArr[i3] = Array.newInstance(componentType, 0);
            } else {
                TemplateModel templateModel2 = (TemplateModel) it.next();
                int i4 = size - i3;
                if (i4 == 1 && (objTryUnwrap = beansWrapper.tryUnwrap(templateModel2, cls2)) != BeansWrapper.CAN_NOT_UNWRAP) {
                    objArr[i3] = objTryUnwrap;
                } else {
                    Object objNewInstance = Array.newInstance(componentType, i4);
                    while (i2 < i4) {
                        TemplateModel templateModel3 = (TemplateModel) (i2 == 0 ? templateModel2 : it.next());
                        Object objTryUnwrap3 = beansWrapper.tryUnwrap(templateModel3, componentType);
                        if (objTryUnwrap3 == BeansWrapper.CAN_NOT_UNWRAP) {
                            throw createArgumentTypeMismarchException(i3 + i2, templateModel3, componentType);
                        }
                        if (objTryUnwrap3 == null && componentType.isPrimitive()) {
                            throw createNullToPrimitiveArgumentException(i3 + i2, componentType);
                        }
                        Array.set(objNewInstance, i2, objTryUnwrap3);
                        i2++;
                    }
                    objArr[i3] = objNewInstance;
                }
            }
        }
        return objArr;
    }

    private TemplateModelException createArgumentTypeMismarchException(int i, TemplateModel templateModel, Class cls) {
        return new _TemplateModelException(new Object[]{_MethodUtil.invocationErrorMessageStart(this.member), " couldn't be called: Can't convert the ", new _DelayedOrdinal(new Integer(i + 1)), " argument's value to the target Java type, ", ClassUtil.getShortClassName(cls), ". The type of the actual value was: ", new _DelayedFTLTypeDescription(templateModel)});
    }

    private TemplateModelException createNullToPrimitiveArgumentException(int i, Class cls) {
        return new _TemplateModelException(new Object[]{_MethodUtil.invocationErrorMessageStart(this.member), " couldn't be called: The value of the ", new _DelayedOrdinal(new Integer(i + 1)), " argument was null, but the target Java parameter type (", ClassUtil.getShortClassName(cls), ") is primitive and so can't store null."});
    }

    protected Member getMember() {
        return this.member;
    }
}
