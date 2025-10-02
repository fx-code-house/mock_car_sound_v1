package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString;
import org.mapstruct.ap.shaded.freemarker.core._ErrorDescriptionBuilder;
import org.mapstruct.ap.shaded.freemarker.core._TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
final class OverloadedMethods {
    private final boolean bugfixed;
    private final OverloadedMethodsSubset fixArgMethods;
    private OverloadedMethodsSubset varargMethods;

    OverloadedMethods(boolean z) {
        this.bugfixed = z;
        this.fixArgMethods = new OverloadedFixArgsMethods(z);
    }

    void addMethod(Method method) {
        addCallableMemberDescriptor(new ReflectionCallableMemberDescriptor(method, method.getParameterTypes()));
    }

    void addConstructor(Constructor constructor) {
        addCallableMemberDescriptor(new ReflectionCallableMemberDescriptor(constructor, constructor.getParameterTypes()));
    }

    private void addCallableMemberDescriptor(ReflectionCallableMemberDescriptor reflectionCallableMemberDescriptor) {
        this.fixArgMethods.addCallableMemberDescriptor(reflectionCallableMemberDescriptor);
        if (reflectionCallableMemberDescriptor.isVarargs()) {
            if (this.varargMethods == null) {
                this.varargMethods = new OverloadedVarArgsMethods(this.bugfixed);
            }
            this.varargMethods.addCallableMemberDescriptor(reflectionCallableMemberDescriptor);
        }
    }

    MemberAndArguments getMemberAndArguments(List list, BeansWrapper beansWrapper) throws TemplateModelException {
        MaybeEmptyMemberAndArguments memberAndArguments;
        MaybeEmptyMemberAndArguments memberAndArguments2 = this.fixArgMethods.getMemberAndArguments(list, beansWrapper);
        if (memberAndArguments2 instanceof MemberAndArguments) {
            return (MemberAndArguments) memberAndArguments2;
        }
        OverloadedMethodsSubset overloadedMethodsSubset = this.varargMethods;
        if (overloadedMethodsSubset != null) {
            memberAndArguments = overloadedMethodsSubset.getMemberAndArguments(list, beansWrapper);
            if (memberAndArguments instanceof MemberAndArguments) {
                return (MemberAndArguments) memberAndArguments;
            }
        } else {
            memberAndArguments = null;
        }
        _ErrorDescriptionBuilder _errordescriptionbuilder = new _ErrorDescriptionBuilder(new Object[]{toCompositeErrorMessage((EmptyMemberAndArguments) memberAndArguments2, (EmptyMemberAndArguments) memberAndArguments, list), "\nThe matching overload was searched among these members:\n", memberListToString()});
        if (!this.bugfixed) {
            _errordescriptionbuilder.tip("You seem to use BeansWrapper with incompatibleImprovements set blow 2.3.21. If you think this error is unfounded, enabling 2.3.21 fixes may helps. See version history for more.");
        }
        throw new _TemplateModelException(_errordescriptionbuilder);
    }

    private Object[] toCompositeErrorMessage(EmptyMemberAndArguments emptyMemberAndArguments, EmptyMemberAndArguments emptyMemberAndArguments2, List list) {
        if (emptyMemberAndArguments2 != null) {
            return (emptyMemberAndArguments == null || emptyMemberAndArguments.isNumberOfArgumentsWrong()) ? toErrorMessage(emptyMemberAndArguments2, list) : new Object[]{"When trying to call the non-varargs overloads:\n", toErrorMessage(emptyMemberAndArguments, list), "\nWhen trying to call the varargs overloads:\n", toErrorMessage(emptyMemberAndArguments2, null)};
        }
        return toErrorMessage(emptyMemberAndArguments, list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Object[] toErrorMessage(EmptyMemberAndArguments emptyMemberAndArguments, List list) {
        Object[] unwrappedArguments = emptyMemberAndArguments.getUnwrappedArguments();
        Object[] objArr = new Object[3];
        objArr[0] = emptyMemberAndArguments.getErrorDescription();
        String str = "";
        objArr[1] = list != null ? new Object[]{"\nThe FTL type of the argument values were: ", getTMActualParameterTypes(list), "."} : "";
        String str2 = str;
        if (unwrappedArguments != null) {
            str2 = new Object[]{"\nThe Java type of the argument values were: ", new StringBuffer().append(getUnwrappedActualParameterTypes(unwrappedArguments)).append(".").toString()};
        }
        objArr[2] = str2;
        return objArr;
    }

    private _DelayedConversionToString memberListToString() {
        return new _DelayedConversionToString(null) { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods.1
            @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
            protected String doConversion(Object obj) {
                Iterator memberDescriptors = OverloadedMethods.this.fixArgMethods.getMemberDescriptors();
                Iterator memberDescriptors2 = OverloadedMethods.this.varargMethods != null ? OverloadedMethods.this.varargMethods.getMemberDescriptors() : null;
                if (!(memberDescriptors.hasNext() || (memberDescriptors2 != null && memberDescriptors2.hasNext()))) {
                    return "No members";
                }
                StringBuffer stringBuffer = new StringBuffer();
                HashSet hashSet = new HashSet();
                while (memberDescriptors.hasNext()) {
                    if (stringBuffer.length() != 0) {
                        stringBuffer.append(",\n");
                    }
                    stringBuffer.append("    ");
                    CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) memberDescriptors.next();
                    hashSet.add(callableMemberDescriptor);
                    stringBuffer.append(callableMemberDescriptor.getDeclaration());
                }
                if (memberDescriptors2 != null) {
                    while (memberDescriptors2.hasNext()) {
                        CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) memberDescriptors2.next();
                        if (!hashSet.contains(callableMemberDescriptor2)) {
                            if (stringBuffer.length() != 0) {
                                stringBuffer.append(",\n");
                            }
                            stringBuffer.append("    ");
                            stringBuffer.append(callableMemberDescriptor2.getDeclaration());
                        }
                    }
                }
                return stringBuffer.toString();
            }
        };
    }

    private _DelayedConversionToString getTMActualParameterTypes(List list) {
        String[] strArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strArr[i] = ClassUtil.getFTLTypeDescription((TemplateModel) list.get(i));
        }
        return new DelayedCallSignatureToString(strArr) { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods.2
            @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods.DelayedCallSignatureToString
            String argumentToString(Object obj) {
                return (String) obj;
            }
        };
    }

    private Object getUnwrappedActualParameterTypes(Object[] objArr) {
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            clsArr[i] = obj != null ? obj.getClass() : null;
        }
        return new DelayedCallSignatureToString(clsArr) { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods.3
            @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedMethods.DelayedCallSignatureToString
            String argumentToString(Object obj2) {
                if (obj2 != null) {
                    return ClassUtil.getShortClassName((Class) obj2);
                }
                return ClassUtil.getShortClassNameOfObject(null);
            }
        };
    }

    private abstract class DelayedCallSignatureToString extends _DelayedConversionToString {
        abstract String argumentToString(Object obj);

        public DelayedCallSignatureToString(Object[] objArr) {
            super(objArr);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
        protected String doConversion(Object obj) {
            Object[] objArr = (Object[]) obj;
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < objArr.length; i++) {
                if (i != 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(argumentToString(objArr[i]));
            }
            return stringBuffer.toString();
        }
    }
}
