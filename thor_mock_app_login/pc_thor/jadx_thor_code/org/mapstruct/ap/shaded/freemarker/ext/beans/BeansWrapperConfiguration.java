package org.mapstruct.ap.shaded.freemarker.ext.beans;

import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.Version;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;

/* loaded from: classes3.dex */
public abstract class BeansWrapperConfiguration implements Cloneable {
    protected ClassIntrospectorBuilder classIntrospectorFactory;
    private final Version incompatibleImprovements;
    private boolean simpleMapWrapper = false;
    private int defaultDateType = 0;
    private ObjectWrapper outerIdentity = null;
    private boolean strict = false;
    private boolean useModelCache = false;

    protected BeansWrapperConfiguration(Version version) {
        _TemplateAPI.checkVersionNotNullAndSupported(version);
        Version versionNormalizeIncompatibleImprovementsVersion = BeansWrapper.normalizeIncompatibleImprovementsVersion(version);
        this.incompatibleImprovements = versionNormalizeIncompatibleImprovementsVersion;
        this.classIntrospectorFactory = new ClassIntrospectorBuilder(versionNormalizeIncompatibleImprovementsVersion);
    }

    public int hashCode() {
        int iHashCode = (((((this.incompatibleImprovements.hashCode() + 31) * 31) + (this.simpleMapWrapper ? 1231 : 1237)) * 31) + this.defaultDateType) * 31;
        ObjectWrapper objectWrapper = this.outerIdentity;
        return ((((((iHashCode + (objectWrapper != null ? objectWrapper.hashCode() : 0)) * 31) + (this.strict ? 1231 : 1237)) * 31) + (this.useModelCache ? 1231 : 1237)) * 31) + this.classIntrospectorFactory.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BeansWrapperConfiguration beansWrapperConfiguration = (BeansWrapperConfiguration) obj;
        return this.incompatibleImprovements.equals(beansWrapperConfiguration.incompatibleImprovements) && this.simpleMapWrapper == beansWrapperConfiguration.simpleMapWrapper && this.defaultDateType == beansWrapperConfiguration.defaultDateType && this.outerIdentity == beansWrapperConfiguration.outerIdentity && this.strict == beansWrapperConfiguration.strict && this.useModelCache == beansWrapperConfiguration.useModelCache && this.classIntrospectorFactory.equals(beansWrapperConfiguration.classIntrospectorFactory);
    }

    protected Object clone(boolean z) {
        try {
            BeansWrapperConfiguration beansWrapperConfiguration = (BeansWrapperConfiguration) super.clone();
            if (z) {
                beansWrapperConfiguration.classIntrospectorFactory = (ClassIntrospectorBuilder) this.classIntrospectorFactory.clone();
            }
            return beansWrapperConfiguration;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone BeansWrapperConfiguration", e);
        }
    }

    public boolean isSimpleMapWrapper() {
        return this.simpleMapWrapper;
    }

    public void setSimpleMapWrapper(boolean z) {
        this.simpleMapWrapper = z;
    }

    public int getDefaultDateType() {
        return this.defaultDateType;
    }

    public void setDefaultDateType(int i) {
        this.defaultDateType = i;
    }

    public ObjectWrapper getOuterIdentity() {
        return this.outerIdentity;
    }

    public void setOuterIdentity(ObjectWrapper objectWrapper) {
        this.outerIdentity = objectWrapper;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public void setStrict(boolean z) {
        this.strict = z;
    }

    public boolean getUseModelCache() {
        return this.useModelCache;
    }

    public void setUseModelCache(boolean z) {
        this.useModelCache = z;
    }

    public Version getIncompatibleImprovements() {
        return this.incompatibleImprovements;
    }

    public int getExposureLevel() {
        return this.classIntrospectorFactory.getExposureLevel();
    }

    public void setExposureLevel(int i) {
        this.classIntrospectorFactory.setExposureLevel(i);
    }

    public boolean getExposeFields() {
        return this.classIntrospectorFactory.getExposeFields();
    }

    public void setExposeFields(boolean z) {
        this.classIntrospectorFactory.setExposeFields(z);
    }

    public MethodAppearanceFineTuner getMethodAppearanceFineTuner() {
        return this.classIntrospectorFactory.getMethodAppearanceFineTuner();
    }

    public void setMethodAppearanceFineTuner(MethodAppearanceFineTuner methodAppearanceFineTuner) {
        this.classIntrospectorFactory.setMethodAppearanceFineTuner(methodAppearanceFineTuner);
    }

    MethodSorter getMethodSorter() {
        return this.classIntrospectorFactory.getMethodSorter();
    }

    void setMethodSorter(MethodSorter methodSorter) {
        this.classIntrospectorFactory.setMethodSorter(methodSorter);
    }
}
