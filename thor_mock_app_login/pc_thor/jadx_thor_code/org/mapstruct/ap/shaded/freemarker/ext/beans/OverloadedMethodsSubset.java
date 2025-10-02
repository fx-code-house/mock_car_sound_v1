package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;

/* loaded from: classes3.dex */
abstract class OverloadedMethodsSubset {
    static final int[] ALL_ZEROS_ARRAY;
    private static final int[][] ZERO_PARAM_COUNT_TYPE_FLAGS_ARRAY;
    static /* synthetic */ Class class$java$io$Serializable;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Cloneable;
    static /* synthetic */ Class class$java$lang$Comparable;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Number;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$Short;
    private final Map argTypesToMemberDescCache;
    protected final boolean bugfixed;
    private final boolean isArgTypesToMemberDescCacheConcurrentMap;
    private final List memberDescs;
    private int[][] typeFlagsByParamCount;
    private Class[][] unwrappingHintsByParamCount;

    abstract void afterWideningUnwrappingHints(Class[] clsArr, int[] iArr);

    abstract MaybeEmptyMemberAndArguments getMemberAndArguments(List list, BeansWrapper beansWrapper) throws TemplateModelException;

    abstract Class[] preprocessParameterTypes(CallableMemberDescriptor callableMemberDescriptor);

    static {
        int[] iArr = new int[0];
        ALL_ZEROS_ARRAY = iArr;
        ZERO_PARAM_COUNT_TYPE_FLAGS_ARRAY = new int[][]{iArr};
    }

    OverloadedMethodsSubset(boolean z) {
        Map mapNewMaybeConcurrentHashMap = _ConcurrentMapFactory.newMaybeConcurrentHashMap(6, 0.75f, 1);
        this.argTypesToMemberDescCache = mapNewMaybeConcurrentHashMap;
        this.isArgTypesToMemberDescCacheConcurrentMap = _ConcurrentMapFactory.isConcurrent(mapNewMaybeConcurrentHashMap);
        this.memberDescs = new LinkedList();
        this.bugfixed = z;
    }

    void addCallableMemberDescriptor(ReflectionCallableMemberDescriptor reflectionCallableMemberDescriptor) {
        this.memberDescs.add(reflectionCallableMemberDescriptor);
        Class[] clsArrPreprocessParameterTypes = preprocessParameterTypes(reflectionCallableMemberDescriptor);
        int length = clsArrPreprocessParameterTypes.length;
        Class[][] clsArr = this.unwrappingHintsByParamCount;
        if (clsArr == null) {
            Class[][] clsArr2 = new Class[length + 1][];
            this.unwrappingHintsByParamCount = clsArr2;
            clsArr2[length] = (Class[]) clsArrPreprocessParameterTypes.clone();
        } else if (clsArr.length <= length) {
            Class[][] clsArr3 = new Class[length + 1][];
            System.arraycopy(clsArr, 0, clsArr3, 0, clsArr.length);
            this.unwrappingHintsByParamCount = clsArr3;
            clsArr3[length] = (Class[]) clsArrPreprocessParameterTypes.clone();
        } else {
            Class[] clsArr4 = clsArr[length];
            if (clsArr4 == null) {
                clsArr[length] = (Class[]) clsArrPreprocessParameterTypes.clone();
            } else {
                for (int i = 0; i < clsArrPreprocessParameterTypes.length; i++) {
                    clsArr4[i] = getCommonSupertypeForUnwrappingHint(clsArr4[i], clsArrPreprocessParameterTypes[i]);
                }
            }
        }
        int[] iArr = ALL_ZEROS_ARRAY;
        if (this.bugfixed) {
            for (int i2 = 0; i2 < length; i2++) {
                int iClassToTypeFlags = TypeFlags.classToTypeFlags(clsArrPreprocessParameterTypes[i2]);
                if (iClassToTypeFlags != 0) {
                    if (iArr == ALL_ZEROS_ARRAY) {
                        iArr = new int[length];
                    }
                    iArr[i2] = iClassToTypeFlags;
                }
            }
            mergeInTypesFlags(length, iArr);
        }
        if (!this.bugfixed) {
            clsArrPreprocessParameterTypes = this.unwrappingHintsByParamCount[length];
        }
        afterWideningUnwrappingHints(clsArrPreprocessParameterTypes, iArr);
    }

    Class[][] getUnwrappingHintsByParamCount() {
        return this.unwrappingHintsByParamCount;
    }

    final MaybeEmptyCallableMemberDescriptor getMemberDescriptorForArgs(Object[] objArr, boolean z) {
        ArgumentTypes argumentTypes = new ArgumentTypes(objArr, this.bugfixed);
        MaybeEmptyCallableMemberDescriptor mostSpecific = this.isArgTypesToMemberDescCacheConcurrentMap ? (MaybeEmptyCallableMemberDescriptor) this.argTypesToMemberDescCache.get(argumentTypes) : null;
        if (mostSpecific == null) {
            synchronized (this.argTypesToMemberDescCache) {
                mostSpecific = (MaybeEmptyCallableMemberDescriptor) this.argTypesToMemberDescCache.get(argumentTypes);
                if (mostSpecific == null) {
                    mostSpecific = argumentTypes.getMostSpecific(this.memberDescs, z);
                    this.argTypesToMemberDescCache.put(argumentTypes, mostSpecific);
                }
            }
        }
        return mostSpecific;
    }

    Iterator getMemberDescriptors() {
        return this.memberDescs.iterator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Class getCommonSupertypeForUnwrappingHint(Class cls, Class cls2) throws Throwable {
        boolean z;
        boolean z2;
        if (cls == cls2) {
            return cls;
        }
        if (this.bugfixed) {
            if (cls.isPrimitive()) {
                cls = ClassUtil.primitiveClassToBoxingClass(cls);
                z = true;
            } else {
                z = false;
            }
            if (cls2.isPrimitive()) {
                cls2 = ClassUtil.primitiveClassToBoxingClass(cls2);
                z2 = true;
            } else {
                z2 = false;
            }
            if (cls == cls2) {
                return cls;
            }
            Class cls3 = class$java$lang$Number;
            Class cls4 = cls3;
            if (cls3 == null) {
                Class clsClass$ = class$("java.lang.Number");
                class$java$lang$Number = clsClass$;
                cls4 = clsClass$;
            }
            if (cls4.isAssignableFrom(cls)) {
                Class cls5 = class$java$lang$Number;
                Class cls6 = cls5;
                if (cls5 == null) {
                    Class clsClass$2 = class$("java.lang.Number");
                    class$java$lang$Number = clsClass$2;
                    cls6 = clsClass$2;
                }
                if (cls6.isAssignableFrom(cls2)) {
                    Class cls7 = class$java$lang$Number;
                    if (cls7 != null) {
                        return cls7;
                    }
                    Class clsClass$3 = class$("java.lang.Number");
                    class$java$lang$Number = clsClass$3;
                    return clsClass$3;
                }
            }
            if (z || z2) {
                Class cls8 = class$java$lang$Object;
                if (cls8 != null) {
                    return cls8;
                }
                Class clsClass$4 = class$("java.lang.Object");
                class$java$lang$Object = clsClass$4;
                return clsClass$4;
            }
        } else if (cls2.isPrimitive()) {
            if (cls2 == Byte.TYPE) {
                cls2 = class$java$lang$Byte;
                if (cls2 == null) {
                    cls2 = class$("java.lang.Byte");
                    class$java$lang$Byte = cls2;
                }
            } else if (cls2 == Short.TYPE) {
                cls2 = class$java$lang$Short;
                if (cls2 == null) {
                    cls2 = class$("java.lang.Short");
                    class$java$lang$Short = cls2;
                }
            } else if (cls2 == Character.TYPE) {
                cls2 = class$java$lang$Character;
                if (cls2 == null) {
                    cls2 = class$("java.lang.Character");
                    class$java$lang$Character = cls2;
                }
            } else if (cls2 == Integer.TYPE) {
                cls2 = class$java$lang$Integer;
                if (cls2 == null) {
                    cls2 = class$("java.lang.Integer");
                    class$java$lang$Integer = cls2;
                }
            } else if (cls2 == Float.TYPE) {
                cls2 = class$java$lang$Float;
                if (cls2 == null) {
                    cls2 = class$("java.lang.Float");
                    class$java$lang$Float = cls2;
                }
            } else if (cls2 == Long.TYPE) {
                cls2 = class$java$lang$Long;
                if (cls2 == null) {
                    cls2 = class$("java.lang.Long");
                    class$java$lang$Long = cls2;
                }
            } else if (cls2 == Double.TYPE && (cls2 = class$java$lang$Double) == null) {
                cls2 = class$("java.lang.Double");
                class$java$lang$Double = cls2;
            }
        }
        Set<Class> assignables = _MethodUtil.getAssignables(cls, cls2);
        assignables.retainAll(_MethodUtil.getAssignables(cls2, cls));
        if (assignables.isEmpty()) {
            Class cls9 = class$java$lang$Object;
            if (cls9 != null) {
                return cls9;
            }
            Class clsClass$5 = class$("java.lang.Object");
            class$java$lang$Object = clsClass$5;
            return clsClass$5;
        }
        ArrayList arrayList = new ArrayList();
        for (Class cls10 : assignables) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    Class cls11 = (Class) it.next();
                    if (_MethodUtil.isMoreOrSameSpecificParameterType(cls11, cls10, false, 0) != 0) {
                        break;
                    }
                    if (_MethodUtil.isMoreOrSameSpecificParameterType(cls10, cls11, false, 0) != 0) {
                        it.remove();
                    }
                } else {
                    arrayList.add(cls10);
                    break;
                }
            }
        }
        if (arrayList.size() > 1) {
            if (this.bugfixed) {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    Class cls12 = (Class) it2.next();
                    if (!cls12.isInterface()) {
                        Class clsClass$6 = class$java$lang$Object;
                        if (clsClass$6 == null) {
                            clsClass$6 = class$("java.lang.Object");
                            class$java$lang$Object = clsClass$6;
                        }
                        if (cls12 != clsClass$6) {
                            return cls12;
                        }
                        it2.remove();
                    }
                }
                Class clsClass$7 = class$java$lang$Cloneable;
                if (clsClass$7 == null) {
                    clsClass$7 = class$("java.lang.Cloneable");
                    class$java$lang$Cloneable = clsClass$7;
                }
                arrayList.remove(clsClass$7);
                if (arrayList.size() > 1) {
                    Class clsClass$8 = class$java$io$Serializable;
                    if (clsClass$8 == null) {
                        clsClass$8 = class$("java.io.Serializable");
                        class$java$io$Serializable = clsClass$8;
                    }
                    arrayList.remove(clsClass$8);
                    if (arrayList.size() > 1) {
                        Class clsClass$9 = class$java$lang$Comparable;
                        if (clsClass$9 == null) {
                            clsClass$9 = class$("java.lang.Comparable");
                            class$java$lang$Comparable = clsClass$9;
                        }
                        arrayList.remove(clsClass$9);
                        if (arrayList.size() > 1) {
                            Class cls13 = class$java$lang$Object;
                            if (cls13 != null) {
                                return cls13;
                            }
                            Class clsClass$10 = class$("java.lang.Object");
                            class$java$lang$Object = clsClass$10;
                            return clsClass$10;
                        }
                    }
                }
            } else {
                Class cls14 = class$java$lang$Object;
                if (cls14 != null) {
                    return cls14;
                }
                Class clsClass$11 = class$("java.lang.Object");
                class$java$lang$Object = clsClass$11;
                return clsClass$11;
            }
        }
        return (Class) arrayList.get(0);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    protected final int[] getTypeFlags(int i) {
        int[][] iArr = this.typeFlagsByParamCount;
        if (iArr == null || iArr.length <= i) {
            return null;
        }
        return iArr[i];
    }

    protected final void mergeInTypesFlags(int i, int[] iArr) {
        int i2;
        NullArgumentException.check("srcTypesFlagsByParamIdx", iArr);
        int i3 = 0;
        if (i == 0) {
            int[][] iArr2 = this.typeFlagsByParamCount;
            if (iArr2 == null) {
                this.typeFlagsByParamCount = ZERO_PARAM_COUNT_TYPE_FLAGS_ARRAY;
                return;
            } else {
                if (iArr2 != ZERO_PARAM_COUNT_TYPE_FLAGS_ARRAY) {
                    iArr2[0] = ALL_ZEROS_ARRAY;
                    return;
                }
                return;
            }
        }
        int[][] iArr3 = this.typeFlagsByParamCount;
        if (iArr3 == null) {
            this.typeFlagsByParamCount = new int[i + 1][];
        } else if (iArr3.length <= i) {
            int[][] iArr4 = new int[i + 1][];
            System.arraycopy(iArr3, 0, iArr4, 0, iArr3.length);
            this.typeFlagsByParamCount = iArr4;
        }
        int[][] iArr5 = this.typeFlagsByParamCount;
        int[] iArr6 = iArr5[i];
        if (iArr6 == null) {
            int[] iArr7 = ALL_ZEROS_ARRAY;
            if (iArr != iArr7) {
                int length = iArr.length;
                int[] iArr8 = new int[i];
                while (i3 < i) {
                    iArr8[i3] = iArr[i3 < length ? i3 : length - 1];
                    i3++;
                }
                iArr7 = iArr8;
            }
            this.typeFlagsByParamCount[i] = iArr7;
            return;
        }
        if (iArr == iArr6) {
            return;
        }
        if (iArr6 == ALL_ZEROS_ARRAY && i > 0) {
            iArr6 = new int[i];
            iArr5[i] = iArr6;
        }
        int i4 = 0;
        while (i4 < i) {
            if (iArr != ALL_ZEROS_ARRAY) {
                int length2 = iArr.length;
                i2 = iArr[i4 < length2 ? i4 : length2 - 1];
            } else {
                i2 = 0;
            }
            int i5 = iArr6[i4];
            if (i5 != i2) {
                int i6 = i2 | i5;
                if ((i6 & 2044) != 0) {
                    i6 |= 1;
                }
                iArr6[i4] = i6;
            }
            i4++;
        }
    }

    protected void forceNumberArgumentsToParameterTypes(Object[] objArr, Class[] clsArr, int[] iArr) throws Throwable {
        int length = clsArr.length;
        int length2 = objArr.length;
        int i = 0;
        while (i < length2) {
            int i2 = i < length ? i : length - 1;
            if ((iArr[i2] & 1) != 0) {
                Object obj = objArr[i];
                if (obj instanceof Number) {
                    Number numberForceUnwrappedNumberToType = BeansWrapper.forceUnwrappedNumberToType((Number) obj, clsArr[i2], this.bugfixed);
                    if (numberForceUnwrappedNumberToType != null) {
                        objArr[i] = numberForceUnwrappedNumberToType;
                    }
                }
            }
            i++;
        }
    }
}
