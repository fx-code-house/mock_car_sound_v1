package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.Version;

/* loaded from: classes3.dex */
final class ClassIntrospectorBuilder implements Cloneable {
    private static final Map INSTANCE_CACHE = new HashMap();
    private static final ReferenceQueue INSTANCE_CACHE_REF_QUEUE = new ReferenceQueue();
    private final boolean bugfixed;
    private boolean exposeFields;
    private int exposureLevel;
    private MethodAppearanceFineTuner methodAppearanceFineTuner;
    private MethodSorter methodSorter;

    ClassIntrospectorBuilder(ClassIntrospector classIntrospector) {
        this.exposureLevel = 1;
        this.bugfixed = classIntrospector.bugfixed;
        this.exposureLevel = classIntrospector.exposureLevel;
        this.exposeFields = classIntrospector.exposeFields;
        this.methodAppearanceFineTuner = classIntrospector.methodAppearanceFineTuner;
        this.methodSorter = classIntrospector.methodSorter;
    }

    ClassIntrospectorBuilder(Version version) {
        this.exposureLevel = 1;
        this.bugfixed = BeansWrapper.is2321Bugfixed(version);
    }

    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone ClassIntrospectorBuilder", e);
        }
    }

    public int hashCode() {
        return (((((((((this.bugfixed ? 1231 : 1237) + 31) * 31) + (this.exposeFields ? 1231 : 1237)) * 31) + this.exposureLevel) * 31) + System.identityHashCode(this.methodAppearanceFineTuner)) * 31) + System.identityHashCode(this.methodSorter);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassIntrospectorBuilder classIntrospectorBuilder = (ClassIntrospectorBuilder) obj;
        return this.bugfixed == classIntrospectorBuilder.bugfixed && this.exposeFields == classIntrospectorBuilder.exposeFields && this.exposureLevel == classIntrospectorBuilder.exposureLevel && this.methodAppearanceFineTuner == classIntrospectorBuilder.methodAppearanceFineTuner && this.methodSorter == classIntrospectorBuilder.methodSorter;
    }

    public int getExposureLevel() {
        return this.exposureLevel;
    }

    public void setExposureLevel(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException(new StringBuffer("Illegal exposure level: ").append(i).toString());
        }
        this.exposureLevel = i;
    }

    public boolean getExposeFields() {
        return this.exposeFields;
    }

    public void setExposeFields(boolean z) {
        this.exposeFields = z;
    }

    public MethodAppearanceFineTuner getMethodAppearanceFineTuner() {
        return this.methodAppearanceFineTuner;
    }

    public void setMethodAppearanceFineTuner(MethodAppearanceFineTuner methodAppearanceFineTuner) {
        this.methodAppearanceFineTuner = methodAppearanceFineTuner;
    }

    public MethodSorter getMethodSorter() {
        return this.methodSorter;
    }

    public void setMethodSorter(MethodSorter methodSorter) {
        this.methodSorter = methodSorter;
    }

    private static void removeClearedReferencesFromInstanceCache() {
        while (true) {
            Reference referencePoll = INSTANCE_CACHE_REF_QUEUE.poll();
            if (referencePoll == null) {
                return;
            }
            Map map = INSTANCE_CACHE;
            synchronized (map) {
                Iterator it = map.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (it.next() == referencePoll) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }

    static void clearInstanceCache() {
        Map map = INSTANCE_CACHE;
        synchronized (map) {
            map.clear();
        }
    }

    static Map getInstanceCache() {
        return INSTANCE_CACHE;
    }

    ClassIntrospector build() {
        MethodSorter methodSorter;
        ClassIntrospector classIntrospector;
        MethodAppearanceFineTuner methodAppearanceFineTuner = this.methodAppearanceFineTuner;
        if ((methodAppearanceFineTuner == null || (methodAppearanceFineTuner instanceof SingletonCustomizer)) && ((methodSorter = this.methodSorter) == null || (methodSorter instanceof SingletonCustomizer))) {
            Map map = INSTANCE_CACHE;
            synchronized (map) {
                Reference reference = (Reference) map.get(this);
                classIntrospector = reference != null ? (ClassIntrospector) reference.get() : null;
                if (classIntrospector == null) {
                    ClassIntrospectorBuilder classIntrospectorBuilder = (ClassIntrospectorBuilder) clone();
                    ClassIntrospector classIntrospector2 = new ClassIntrospector(classIntrospectorBuilder, new Object(), true, true);
                    map.put(classIntrospectorBuilder, new WeakReference(classIntrospector2, INSTANCE_CACHE_REF_QUEUE));
                    classIntrospector = classIntrospector2;
                }
            }
            removeClearedReferencesFromInstanceCache();
            return classIntrospector;
        }
        return new ClassIntrospector(this, new Object(), true, false);
    }

    public boolean isBugfixed() {
        return this.bugfixed;
    }
}
