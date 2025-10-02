package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
final class ArgumentTypes {
    private static final int CONVERSION_DIFFICULTY_FREEMARKER = 1;
    private static final int CONVERSION_DIFFICULTY_IMPOSSIBLE = 2;
    private static final int CONVERSION_DIFFICULTY_REFLECTION = 0;
    static /* synthetic */ Class class$freemarker$ext$beans$ArgumentTypes$Null;
    static /* synthetic */ Class class$freemarker$ext$beans$CharacterOrString;
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Number;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$math$BigDecimal;
    static /* synthetic */ Class class$java$util$Collection;
    static /* synthetic */ Class class$java$util$List;
    private final boolean bugfixed;
    private final Class[] types;

    ArgumentTypes(Object[] objArr, boolean z) throws Throwable {
        Class<?> clsClass$;
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i = 0; i < length; i++) {
            Object obj = objArr[i];
            if (obj != null) {
                clsClass$ = obj.getClass();
            } else if (z) {
                clsClass$ = class$freemarker$ext$beans$ArgumentTypes$Null;
                if (clsClass$ == null) {
                    clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes$Null");
                    class$freemarker$ext$beans$ArgumentTypes$Null = clsClass$;
                }
            } else {
                clsClass$ = class$java$lang$Object;
                if (clsClass$ == null) {
                    clsClass$ = class$("java.lang.Object");
                    class$java$lang$Object = clsClass$;
                }
            }
            clsArr[i] = clsClass$;
        }
        this.types = clsArr;
        this.bugfixed = z;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public int hashCode() {
        int i = 0;
        int iHashCode = 0;
        while (true) {
            Class[] clsArr = this.types;
            if (i >= clsArr.length) {
                return iHashCode;
            }
            iHashCode ^= clsArr[i].hashCode();
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ArgumentTypes)) {
            return false;
        }
        ArgumentTypes argumentTypes = (ArgumentTypes) obj;
        if (argumentTypes.types.length != this.types.length) {
            return false;
        }
        int i = 0;
        while (true) {
            Class[] clsArr = this.types;
            if (i >= clsArr.length) {
                return true;
            }
            if (argumentTypes.types[i] != clsArr[i]) {
                return false;
            }
            i++;
        }
    }

    MaybeEmptyCallableMemberDescriptor getMostSpecific(List list, boolean z) {
        LinkedList applicables = getApplicables(list, z);
        if (applicables.isEmpty()) {
            return EmptyCallableMemberDescriptor.NO_SUCH_METHOD;
        }
        if (applicables.size() == 1) {
            return (CallableMemberDescriptor) applicables.getFirst();
        }
        LinkedList linkedList = new LinkedList();
        Iterator it = applicables.iterator();
        while (it.hasNext()) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) it.next();
            Iterator it2 = linkedList.iterator();
            boolean z2 = false;
            while (it2.hasNext()) {
                int iCompareParameterListPreferability = compareParameterListPreferability(callableMemberDescriptor.getParamTypes(), ((CallableMemberDescriptor) it2.next()).getParamTypes(), z);
                if (iCompareParameterListPreferability > 0) {
                    it2.remove();
                } else if (iCompareParameterListPreferability < 0) {
                    z2 = true;
                }
            }
            if (!z2) {
                linkedList.addLast(callableMemberDescriptor);
            }
        }
        if (linkedList.size() > 1) {
            return EmptyCallableMemberDescriptor.AMBIGUOUS_METHOD;
        }
        return (CallableMemberDescriptor) linkedList.getFirst();
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00e1, code lost:
    
        if (r2.isAssignableFrom(r1) != false) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x014f  */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int compareParameterListPreferability(java.lang.Class[] r22, java.lang.Class[] r23, boolean r24) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 575
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.compareParameterListPreferability(java.lang.Class[], java.lang.Class[], boolean):int");
    }

    private int compareParameterListPreferability_cmpTypeSpecificty(Class cls, Class cls2) throws Throwable {
        Class clsPrimitiveClassToBoxingClass = cls.isPrimitive() ? ClassUtil.primitiveClassToBoxingClass(cls) : cls;
        Class<?> clsPrimitiveClassToBoxingClass2 = cls2.isPrimitive() ? ClassUtil.primitiveClassToBoxingClass(cls2) : cls2;
        if (clsPrimitiveClassToBoxingClass == clsPrimitiveClassToBoxingClass2) {
            return clsPrimitiveClassToBoxingClass != cls ? clsPrimitiveClassToBoxingClass2 != cls2 ? 0 : 1 : clsPrimitiveClassToBoxingClass2 != cls2 ? -1 : 0;
        }
        if (clsPrimitiveClassToBoxingClass2.isAssignableFrom(clsPrimitiveClassToBoxingClass)) {
            return 2;
        }
        if (clsPrimitiveClassToBoxingClass.isAssignableFrom(clsPrimitiveClassToBoxingClass2)) {
            return -2;
        }
        Class clsClass$ = class$java$lang$Character;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Character");
            class$java$lang$Character = clsClass$;
        }
        if (clsPrimitiveClassToBoxingClass == clsClass$) {
            Class<?> clsClass$2 = class$java$lang$String;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.String");
                class$java$lang$String = clsClass$2;
            }
            if (clsPrimitiveClassToBoxingClass2.isAssignableFrom(clsClass$2)) {
                return 2;
            }
        }
        Class<?> clsClass$3 = class$java$lang$Character;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.lang.Character");
            class$java$lang$Character = clsClass$3;
        }
        if (clsPrimitiveClassToBoxingClass2 == clsClass$3) {
            Class<?> clsClass$4 = class$java$lang$String;
            if (clsClass$4 == null) {
                clsClass$4 = class$("java.lang.String");
                class$java$lang$String = clsClass$4;
            }
            if (clsPrimitiveClassToBoxingClass.isAssignableFrom(clsClass$4)) {
                return -2;
            }
        }
        return 0;
    }

    private static Class getParamType(Class[] clsArr, int i, int i2, boolean z) {
        int i3;
        return (!z || i2 < (i3 = i + (-1))) ? clsArr[i2] : clsArr[i3].getComponentType();
    }

    LinkedList getApplicables(List list, boolean z) throws Throwable {
        LinkedList linkedList = new LinkedList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ReflectionCallableMemberDescriptor reflectionCallableMemberDescriptor = (ReflectionCallableMemberDescriptor) it.next();
            int iIsApplicable = isApplicable(reflectionCallableMemberDescriptor, z);
            if (iIsApplicable != 2) {
                if (iIsApplicable == 0) {
                    linkedList.add(reflectionCallableMemberDescriptor);
                } else if (iIsApplicable == 1) {
                    linkedList.add(new SpecialConversionCallableMemberDescriptor(reflectionCallableMemberDescriptor));
                } else {
                    throw new BugException();
                }
            }
        }
        return linkedList;
    }

    private int isApplicable(ReflectionCallableMemberDescriptor reflectionCallableMemberDescriptor, boolean z) throws Throwable {
        Class[] paramTypes = reflectionCallableMemberDescriptor.getParamTypes();
        int length = this.types.length;
        int length2 = paramTypes.length - (z ? 1 : 0);
        if (z) {
            if (length < length2) {
                return 2;
            }
        } else if (length != length2) {
            return 2;
        }
        int i = 0;
        for (int i2 = 0; i2 < length2; i2++) {
            int iIsMethodInvocationConvertible = isMethodInvocationConvertible(paramTypes[i2], this.types[i2]);
            if (iIsMethodInvocationConvertible == 2) {
                return 2;
            }
            if (i < iIsMethodInvocationConvertible) {
                i = iIsMethodInvocationConvertible;
            }
        }
        if (z) {
            Class<?> componentType = paramTypes[length2].getComponentType();
            while (length2 < length) {
                int iIsMethodInvocationConvertible2 = isMethodInvocationConvertible(componentType, this.types[length2]);
                if (iIsMethodInvocationConvertible2 == 2) {
                    return 2;
                }
                if (i < iIsMethodInvocationConvertible2) {
                    i = iIsMethodInvocationConvertible2;
                }
                length2++;
            }
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:112:0x014b, code lost:
    
        if (r11 == r0) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0174, code lost:
    
        if (r11 == r0) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x01a9, code lost:
    
        if (r11 == r0) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x01ea, code lost:
    
        if (r11 == r0) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x022b, code lost:
    
        if (r11 == r0) goto L197;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int isMethodInvocationConvertible(java.lang.Class r10, java.lang.Class r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 584
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.isMethodInvocationConvertible(java.lang.Class, java.lang.Class):int");
    }

    private static class Null {
        private Null() {
        }
    }

    private static final class SpecialConversionCallableMemberDescriptor extends CallableMemberDescriptor {
        private final ReflectionCallableMemberDescriptor callableMemberDesc;

        SpecialConversionCallableMemberDescriptor(ReflectionCallableMemberDescriptor reflectionCallableMemberDescriptor) {
            this.callableMemberDesc = reflectionCallableMemberDescriptor;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        TemplateModel invokeMethod(BeansWrapper beansWrapper, Object obj, Object[] objArr) throws Throwable {
            convertArgsToReflectionCompatible(beansWrapper, objArr);
            return this.callableMemberDesc.invokeMethod(beansWrapper, obj, objArr);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        Object invokeConstructor(BeansWrapper beansWrapper, Object[] objArr) throws Throwable {
            convertArgsToReflectionCompatible(beansWrapper, objArr);
            return this.callableMemberDesc.invokeConstructor(beansWrapper, objArr);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        String getDeclaration() {
            return this.callableMemberDesc.getDeclaration();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        boolean isConstructor() {
            return this.callableMemberDesc.isConstructor();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        boolean isStatic() {
            return this.callableMemberDesc.isStatic();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        boolean isVarargs() {
            return this.callableMemberDesc.isVarargs();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        Class[] getParamTypes() {
            return this.callableMemberDesc.getParamTypes();
        }

        @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.CallableMemberDescriptor
        String getName() {
            return this.callableMemberDesc.getName();
        }

        /* JADX WARN: Removed duplicated region for block: B:44:0x0096  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private void convertArgsToReflectionCompatible(org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper r8, java.lang.Object[] r9) throws java.lang.Throwable {
            /*
                r7 = this;
                org.mapstruct.ap.shaded.freemarker.ext.beans.ReflectionCallableMemberDescriptor r0 = r7.callableMemberDesc
                java.lang.Class[] r0 = r0.getParamTypes()
                int r1 = r0.length
                r2 = 0
            L8:
                if (r2 >= r1) goto La7
                r3 = r0[r2]
                r4 = r9[r2]
                if (r4 != 0) goto L12
                goto La3
            L12:
                boolean r5 = r3.isArray()
                if (r5 == 0) goto L26
                boolean r5 = r4 instanceof java.util.List
                if (r5 == 0) goto L26
                r5 = r4
                java.util.List r5 = (java.util.List) r5
                r6 = 0
                java.lang.Object r5 = r8.listToArray(r5, r3, r6)
                r9[r2] = r5
            L26:
                java.lang.Class r5 = r4.getClass()
                boolean r5 = r5.isArray()
                if (r5 == 0) goto L4b
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$util$List
                if (r5 != 0) goto L3d
                java.lang.String r5 = "java.util.List"
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$(r5)
                org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$util$List = r5
                goto L3f
            L3d:
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$util$List
            L3f:
                boolean r5 = r3.isAssignableFrom(r5)
                if (r5 == 0) goto L4b
                java.util.List r5 = r8.arrayToList(r4)
                r9[r2] = r5
            L4b:
                boolean r5 = r4 instanceof org.mapstruct.ap.shaded.freemarker.ext.beans.CharacterOrString
                if (r5 == 0) goto La3
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$Character
                java.lang.String r6 = "java.lang.Character"
                if (r5 != 0) goto L5c
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$(r6)
                org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$Character = r5
                goto L5e
            L5c:
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$Character
            L5e:
                if (r3 == r5) goto L96
                java.lang.Class r5 = java.lang.Character.TYPE
                if (r3 == r5) goto L96
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$String
                if (r5 != 0) goto L71
                java.lang.String r5 = "java.lang.String"
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$(r5)
                org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$String = r5
                goto L73
            L71:
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$String
            L73:
                boolean r5 = r3.isAssignableFrom(r5)
                if (r5 != 0) goto L8d
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$Character
                if (r5 != 0) goto L84
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$(r6)
                org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$Character = r5
                goto L86
            L84:
                java.lang.Class r5 = org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.class$java$lang$Character
            L86:
                boolean r3 = r3.isAssignableFrom(r5)
                if (r3 == 0) goto L8d
                goto L96
            L8d:
                org.mapstruct.ap.shaded.freemarker.ext.beans.CharacterOrString r4 = (org.mapstruct.ap.shaded.freemarker.ext.beans.CharacterOrString) r4
                java.lang.String r3 = r4.getAsString()
                r9[r2] = r3
                goto La3
            L96:
                java.lang.Character r3 = new java.lang.Character
                org.mapstruct.ap.shaded.freemarker.ext.beans.CharacterOrString r4 = (org.mapstruct.ap.shaded.freemarker.ext.beans.CharacterOrString) r4
                char r4 = r4.getAsChar()
                r3.<init>(r4)
                r9[r2] = r3
            La3:
                int r2 = r2 + 1
                goto L8
            La7:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans.ArgumentTypes.SpecialConversionCallableMemberDescriptor.convertArgsToReflectionCompatible(org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper, java.lang.Object[]):void");
        }
    }
}
