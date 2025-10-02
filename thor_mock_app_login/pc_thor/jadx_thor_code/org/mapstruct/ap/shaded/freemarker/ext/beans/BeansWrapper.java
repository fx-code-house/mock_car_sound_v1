package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.core._DelayedFTLTypeDescription;
import org.mapstruct.ap.shaded.freemarker.core._DelayedShortClassName;
import org.mapstruct.ap.shaded.freemarker.core._TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.ext.beans.OverloadedNumberUtil;
import org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelCache;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.Version;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;
import org.mapstruct.ap.shaded.freemarker.template.utility.WriteProtectable;

/* loaded from: classes3.dex */
public class BeansWrapper implements ObjectWrapper, WriteProtectable {
    private static final ModelFactory ENUMERATION_FACTORY;
    private static final Constructor ENUMS_MODEL_CTOR;
    public static final int EXPOSE_ALL = 0;
    public static final int EXPOSE_NOTHING = 3;
    public static final int EXPOSE_PROPERTIES_ONLY = 2;
    public static final int EXPOSE_SAFE = 1;
    private static final Class ITERABLE_CLASS;
    private static final ModelFactory ITERATOR_FACTORY;
    static /* synthetic */ Class class$freemarker$ext$beans$BeansWrapper;
    static /* synthetic */ Class class$freemarker$ext$beans$BeansWrapper$MethodAppearanceDecision;
    static /* synthetic */ Class class$freemarker$ext$beans$HashAdapter;
    static /* synthetic */ Class class$freemarker$ext$beans$SequenceAdapter;
    static /* synthetic */ Class class$freemarker$ext$beans$SetAdapter;
    static /* synthetic */ Class class$freemarker$template$DefaultObjectWrapper;
    static /* synthetic */ Class class$freemarker$template$SimpleObjectWrapper;
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Class;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Number;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$lang$reflect$Method;
    static /* synthetic */ Class class$java$math$BigDecimal;
    static /* synthetic */ Class class$java$math$BigInteger;
    static /* synthetic */ Class class$java$util$Collection;
    static /* synthetic */ Class class$java$util$Date;
    static /* synthetic */ Class class$java$util$Enumeration;
    static /* synthetic */ Class class$java$util$Iterator;
    static /* synthetic */ Class class$java$util$List;
    static /* synthetic */ Class class$java$util$Map;
    static /* synthetic */ Class class$java$util$ResourceBundle;
    static /* synthetic */ Class class$java$util$Set;
    private static volatile boolean ftmaDeprecationWarnLogged;
    private final ModelFactory BOOLEAN_FACTORY;
    private ClassIntrospector classIntrospector;
    private int defaultDateType;
    private final ClassBasedModelFactory enumModels;
    private final BooleanModel falseModel;
    private final Version incompatibleImprovements;
    private boolean methodsShadowItems;
    private final ModelCache modelCache;
    private TemplateModel nullModel;
    private ObjectWrapper outerIdentity;
    private final Object sharedInrospectionLock;
    private boolean simpleMapWrapper;
    private final StaticModels staticModels;
    private boolean strict;
    private final BooleanModel trueModel;
    private volatile boolean writeProtected;
    private static final Logger LOG = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.beans");
    static final Object CAN_NOT_UNWRAP = new Object();

    protected void finetuneMethodAppearance(Class cls, Method method, MethodAppearanceDecision methodAppearanceDecision) {
    }

    static {
        Class<?> cls;
        try {
            cls = Class.forName("java.lang.Iterable");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        ITERABLE_CLASS = cls;
        ENUMS_MODEL_CTOR = enumsModelCtor();
        ITERATOR_FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper.4
            @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
            public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
                return new IteratorModel((Iterator) obj, (BeansWrapper) objectWrapper);
            }
        };
        ENUMERATION_FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper.5
            @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
            public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
                return new EnumerationModel((Enumeration) obj, (BeansWrapper) objectWrapper);
            }
        };
    }

    public BeansWrapper() {
        this(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    }

    public BeansWrapper(Version version) {
        this(new BeansWrapperConfiguration(version) { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper.1
        }, false);
    }

    protected BeansWrapper(BeansWrapperConfiguration beansWrapperConfiguration, boolean z) throws Throwable {
        boolean z2;
        this.nullModel = null;
        this.outerIdentity = this;
        this.methodsShadowItems = true;
        this.BOOLEAN_FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper.3
            @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
            public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
                return ((Boolean) obj).booleanValue() ? BeansWrapper.this.trueModel : BeansWrapper.this.falseModel;
            }
        };
        if (beansWrapperConfiguration.getMethodAppearanceFineTuner() == null) {
            Class<?> superclass = getClass();
            boolean z3 = false;
            while (!z3) {
                try {
                    Class<?> clsClass$ = class$freemarker$template$DefaultObjectWrapper;
                    if (clsClass$ == null) {
                        clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.DefaultObjectWrapper");
                        class$freemarker$template$DefaultObjectWrapper = clsClass$;
                    }
                    if (superclass != clsClass$) {
                        Class<?> clsClass$2 = class$freemarker$ext$beans$BeansWrapper;
                        if (clsClass$2 == null) {
                            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper");
                            class$freemarker$ext$beans$BeansWrapper = clsClass$2;
                        }
                        if (superclass != clsClass$2) {
                            Class<?> clsClass$3 = class$freemarker$template$SimpleObjectWrapper;
                            if (clsClass$3 == null) {
                                clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.SimpleObjectWrapper");
                                class$freemarker$template$SimpleObjectWrapper = clsClass$3;
                            }
                            if (superclass == clsClass$3) {
                                break;
                            }
                            try {
                                Class<?>[] clsArr = new Class[3];
                                Class<?> clsClass$4 = class$java$lang$Class;
                                if (clsClass$4 == null) {
                                    clsClass$4 = class$("java.lang.Class");
                                    class$java$lang$Class = clsClass$4;
                                }
                                clsArr[0] = clsClass$4;
                                Class<?> clsClass$5 = class$java$lang$reflect$Method;
                                if (clsClass$5 == null) {
                                    clsClass$5 = class$("java.lang.reflect.Method");
                                    class$java$lang$reflect$Method = clsClass$5;
                                }
                                clsArr[1] = clsClass$5;
                                Class<?> clsClass$6 = class$freemarker$ext$beans$BeansWrapper$MethodAppearanceDecision;
                                if (clsClass$6 == null) {
                                    clsClass$6 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper$MethodAppearanceDecision");
                                    class$freemarker$ext$beans$BeansWrapper$MethodAppearanceDecision = clsClass$6;
                                }
                                clsArr[2] = clsClass$6;
                                superclass.getDeclaredMethod("finetuneMethodAppearance", clsArr);
                                z3 = true;
                            } catch (NoSuchMethodException unused) {
                                superclass = superclass.getSuperclass();
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (Throwable th) {
                    LOG.info(new StringBuffer("Failed to check if finetuneMethodAppearance is overidden in ").append(superclass.getName()).append("; acting like if it was, but this way it won't utilize the shared class introspection cache.").toString(), th);
                    z2 = true;
                    z3 = true;
                }
            }
            z2 = false;
            if (z3) {
                if (!z2 && !ftmaDeprecationWarnLogged) {
                    Logger logger = LOG;
                    StringBuffer stringBuffer = new StringBuffer("Overriding ");
                    Class clsClass$7 = class$freemarker$ext$beans$BeansWrapper;
                    if (clsClass$7 == null) {
                        clsClass$7 = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper");
                        class$freemarker$ext$beans$BeansWrapper = clsClass$7;
                    }
                    logger.warn(stringBuffer.append(clsClass$7.getName()).append(".finetuneMethodAppearance is deprecated and will be banned sometimes in the future. Use setMethodAppearanceFineTuner instead.").toString());
                    ftmaDeprecationWarnLogged = true;
                }
                beansWrapperConfiguration = (BeansWrapperConfiguration) beansWrapperConfiguration.clone(false);
                beansWrapperConfiguration.setMethodAppearanceFineTuner(new MethodAppearanceFineTuner() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper.2
                    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.MethodAppearanceFineTuner
                    public void process(MethodAppearanceDecisionInput methodAppearanceDecisionInput, MethodAppearanceDecision methodAppearanceDecision) {
                        BeansWrapper.this.finetuneMethodAppearance(methodAppearanceDecisionInput.getContainingClass(), methodAppearanceDecisionInput.getMethod(), methodAppearanceDecision);
                    }
                });
            }
        }
        this.incompatibleImprovements = beansWrapperConfiguration.getIncompatibleImprovements();
        this.simpleMapWrapper = beansWrapperConfiguration.isSimpleMapWrapper();
        this.defaultDateType = beansWrapperConfiguration.getDefaultDateType();
        this.outerIdentity = beansWrapperConfiguration.getOuterIdentity() != null ? beansWrapperConfiguration.getOuterIdentity() : this;
        this.strict = beansWrapperConfiguration.isStrict();
        if (!z) {
            Object obj = new Object();
            this.sharedInrospectionLock = obj;
            this.classIntrospector = new ClassIntrospector(beansWrapperConfiguration.classIntrospectorFactory, obj);
        } else {
            ClassIntrospector classIntrospectorBuild = beansWrapperConfiguration.classIntrospectorFactory.build();
            this.classIntrospector = classIntrospectorBuild;
            this.sharedInrospectionLock = classIntrospectorBuild.getSharedLock();
        }
        this.falseModel = new BooleanModel(Boolean.FALSE, this);
        this.trueModel = new BooleanModel(Boolean.TRUE, this);
        this.staticModels = new StaticModels(this);
        this.enumModels = createEnumModels(this);
        this.modelCache = new BeansModelCache(this);
        setUseCache(beansWrapperConfiguration.getUseModelCache());
        if (z) {
            writeProtect();
        }
        registerModelFactories();
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.utility.WriteProtectable
    public void writeProtect() {
        this.writeProtected = true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.utility.WriteProtectable
    public boolean isWriteProtected() {
        return this.writeProtected;
    }

    Object getSharedInrospectionLock() {
        return this.sharedInrospectionLock;
    }

    protected void checkModifiable() {
        if (this.writeProtected) {
            throw new IllegalStateException(new StringBuffer("Can't modify the ").append(getClass().getName()).append(" object, as it was write protected.").toString());
        }
    }

    public boolean isStrict() {
        return this.strict;
    }

    public void setStrict(boolean z) {
        checkModifiable();
        this.strict = z;
    }

    public void setOuterIdentity(ObjectWrapper objectWrapper) {
        checkModifiable();
        this.outerIdentity = objectWrapper;
    }

    public ObjectWrapper getOuterIdentity() {
        return this.outerIdentity;
    }

    public void setSimpleMapWrapper(boolean z) {
        checkModifiable();
        this.simpleMapWrapper = z;
    }

    public boolean isSimpleMapWrapper() {
        return this.simpleMapWrapper;
    }

    public void setExposureLevel(int i) {
        checkModifiable();
        if (this.classIntrospector.getExposureLevel() != i) {
            ClassIntrospectorBuilder propertyAssignments = this.classIntrospector.getPropertyAssignments();
            propertyAssignments.setExposureLevel(i);
            replaceClassIntrospector(propertyAssignments);
        }
    }

    public int getExposureLevel() {
        return this.classIntrospector.getExposureLevel();
    }

    public void setExposeFields(boolean z) {
        checkModifiable();
        if (this.classIntrospector.getExposeFields() != z) {
            ClassIntrospectorBuilder propertyAssignments = this.classIntrospector.getPropertyAssignments();
            propertyAssignments.setExposeFields(z);
            replaceClassIntrospector(propertyAssignments);
        }
    }

    public boolean isExposeFields() {
        return this.classIntrospector.getExposeFields();
    }

    public MethodAppearanceFineTuner getMethodAppearanceFineTuner() {
        return this.classIntrospector.getMethodAppearanceFineTuner();
    }

    public void setMethodAppearanceFineTuner(MethodAppearanceFineTuner methodAppearanceFineTuner) {
        checkModifiable();
        if (this.classIntrospector.getMethodAppearanceFineTuner() != methodAppearanceFineTuner) {
            ClassIntrospectorBuilder propertyAssignments = this.classIntrospector.getPropertyAssignments();
            propertyAssignments.setMethodAppearanceFineTuner(methodAppearanceFineTuner);
            replaceClassIntrospector(propertyAssignments);
        }
    }

    MethodSorter getMethodSorter() {
        return this.classIntrospector.getMethodSorter();
    }

    void setMethodSorter(MethodSorter methodSorter) {
        checkModifiable();
        if (this.classIntrospector.getMethodSorter() != methodSorter) {
            ClassIntrospectorBuilder propertyAssignments = this.classIntrospector.getPropertyAssignments();
            propertyAssignments.setMethodSorter(methodSorter);
            replaceClassIntrospector(propertyAssignments);
        }
    }

    public boolean isClassIntrospectionCacheRestricted() {
        return this.classIntrospector.getHasSharedInstanceRestrictons();
    }

    private void replaceClassIntrospector(ClassIntrospectorBuilder classIntrospectorBuilder) {
        checkModifiable();
        ClassIntrospector classIntrospector = new ClassIntrospector(classIntrospectorBuilder, this.sharedInrospectionLock);
        synchronized (this.sharedInrospectionLock) {
            ClassIntrospector classIntrospector2 = this.classIntrospector;
            if (classIntrospector2 != null) {
                StaticModels staticModels = this.staticModels;
                if (staticModels != null) {
                    classIntrospector2.unregisterModelFactory((ClassBasedModelFactory) staticModels);
                    this.staticModels.clearCache();
                }
                ClassBasedModelFactory classBasedModelFactory = this.enumModels;
                if (classBasedModelFactory != null) {
                    classIntrospector2.unregisterModelFactory(classBasedModelFactory);
                    this.enumModels.clearCache();
                }
                ModelCache modelCache = this.modelCache;
                if (modelCache != null) {
                    classIntrospector2.unregisterModelFactory(modelCache);
                    this.modelCache.clearCache();
                }
                BooleanModel booleanModel = this.trueModel;
                if (booleanModel != null) {
                    booleanModel.clearMemberCache();
                }
                BooleanModel booleanModel2 = this.falseModel;
                if (booleanModel2 != null) {
                    booleanModel2.clearMemberCache();
                }
            }
            this.classIntrospector = classIntrospector;
            registerModelFactories();
        }
    }

    private void registerModelFactories() {
        StaticModels staticModels = this.staticModels;
        if (staticModels != null) {
            this.classIntrospector.registerModelFactory((ClassBasedModelFactory) staticModels);
        }
        ClassBasedModelFactory classBasedModelFactory = this.enumModels;
        if (classBasedModelFactory != null) {
            this.classIntrospector.registerModelFactory(classBasedModelFactory);
        }
        ModelCache modelCache = this.modelCache;
        if (modelCache != null) {
            this.classIntrospector.registerModelFactory(modelCache);
        }
    }

    public void setMethodsShadowItems(boolean z) {
        synchronized (this) {
            checkModifiable();
            this.methodsShadowItems = z;
        }
    }

    boolean isMethodsShadowItems() {
        return this.methodsShadowItems;
    }

    public void setDefaultDateType(int i) {
        synchronized (this) {
            checkModifiable();
            this.defaultDateType = i;
        }
    }

    public int getDefaultDateType() {
        return this.defaultDateType;
    }

    public void setUseCache(boolean z) {
        checkModifiable();
        this.modelCache.setUseCache(z);
    }

    public boolean getUseCache() {
        return this.modelCache.getUseCache();
    }

    public void setNullModel(TemplateModel templateModel) {
        checkModifiable();
        this.nullModel = templateModel;
    }

    public Version getIncompatibleImprovements() {
        return this.incompatibleImprovements;
    }

    boolean is2321Bugfixed() {
        return is2321Bugfixed(getIncompatibleImprovements());
    }

    static boolean is2321Bugfixed(Version version) {
        return version.intValue() >= _TemplateAPI.VERSION_INT_2_3_21;
    }

    protected static Version normalizeIncompatibleImprovementsVersion(Version version) {
        NullArgumentException.check("version", version);
        if (version.intValue() >= _TemplateAPI.VERSION_INT_2_3_0) {
            return is2321Bugfixed(version) ? Configuration.VERSION_2_3_21 : Configuration.VERSION_2_3_0;
        }
        throw new IllegalArgumentException("Version must be at least 2.3.0.");
    }

    public static final BeansWrapper getDefaultInstance() {
        return BeansWrapperSingletonHolder.INSTANCE;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper
    public TemplateModel wrap(Object obj) throws TemplateModelException {
        if (obj == null) {
            return this.nullModel;
        }
        return this.modelCache.getInstance(obj);
    }

    protected TemplateModel getInstance(Object obj, ModelFactory modelFactory) {
        return modelFactory.create(obj, this);
    }

    protected ModelFactory getModelFactory(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$Map;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.Map");
            class$java$util$Map = clsClass$;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            return this.simpleMapWrapper ? SimpleMapModel.FACTORY : MapModel.FACTORY;
        }
        Class clsClass$2 = class$java$util$Collection;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.util.Collection");
            class$java$util$Collection = clsClass$2;
        }
        if (clsClass$2.isAssignableFrom(cls)) {
            return CollectionModel.FACTORY;
        }
        Class clsClass$3 = class$java$lang$Number;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.lang.Number");
            class$java$lang$Number = clsClass$3;
        }
        if (clsClass$3.isAssignableFrom(cls)) {
            return NumberModel.FACTORY;
        }
        Class clsClass$4 = class$java$util$Date;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.util.Date");
            class$java$util$Date = clsClass$4;
        }
        if (clsClass$4.isAssignableFrom(cls)) {
            return DateModel.FACTORY;
        }
        Class clsClass$5 = class$java$lang$Boolean;
        if (clsClass$5 == null) {
            clsClass$5 = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$5;
        }
        if (clsClass$5 == cls) {
            return this.BOOLEAN_FACTORY;
        }
        Class clsClass$6 = class$java$util$ResourceBundle;
        if (clsClass$6 == null) {
            clsClass$6 = class$("java.util.ResourceBundle");
            class$java$util$ResourceBundle = clsClass$6;
        }
        if (clsClass$6.isAssignableFrom(cls)) {
            return ResourceBundleModel.FACTORY;
        }
        Class clsClass$7 = class$java$util$Iterator;
        if (clsClass$7 == null) {
            clsClass$7 = class$("java.util.Iterator");
            class$java$util$Iterator = clsClass$7;
        }
        if (clsClass$7.isAssignableFrom(cls)) {
            return ITERATOR_FACTORY;
        }
        Class clsClass$8 = class$java$util$Enumeration;
        if (clsClass$8 == null) {
            clsClass$8 = class$("java.util.Enumeration");
            class$java$util$Enumeration = clsClass$8;
        }
        if (clsClass$8.isAssignableFrom(cls)) {
            return ENUMERATION_FACTORY;
        }
        if (cls.isArray()) {
            return ArrayModel.FACTORY;
        }
        return StringModel.FACTORY;
    }

    public Object unwrap(TemplateModel templateModel) throws Throwable {
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.Object");
            class$java$lang$Object = clsClass$;
        }
        return unwrap(templateModel, clsClass$);
    }

    public Object unwrap(TemplateModel templateModel, Class cls) throws TemplateModelException {
        Object objTryUnwrap = tryUnwrap(templateModel, cls);
        if (objTryUnwrap != CAN_NOT_UNWRAP) {
            return objTryUnwrap;
        }
        throw new TemplateModelException(new StringBuffer("Can not unwrap model of type ").append(templateModel.getClass().getName()).append(" to type ").append(cls.getName()).toString());
    }

    Object tryUnwrap(TemplateModel templateModel, Class cls) throws TemplateModelException {
        return tryUnwrap(templateModel, cls, 0);
    }

    Object tryUnwrap(TemplateModel templateModel, Class cls, int i) throws Throwable {
        Object objTryUnwrap = tryUnwrap(templateModel, cls, i, null);
        return ((i & 1) == 0 || !(objTryUnwrap instanceof Number)) ? objTryUnwrap : OverloadedNumberUtil.addFallbackType((Number) objTryUnwrap, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x0189, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01a3, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x01ef, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0216, code lost:
    
        return java.lang.Boolean.valueOf(((org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel) r6).getAsBoolean());
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x023e, code lost:
    
        return new org.mapstruct.ap.shaded.freemarker.ext.beans.HashAdapter((org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel) r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0265, code lost:
    
        return new org.mapstruct.ap.shaded.freemarker.ext.beans.SequenceAdapter((org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel) r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x028c, code lost:
    
        return new org.mapstruct.ap.shaded.freemarker.ext.beans.SetAdapter((org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel) r6, r5);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object tryUnwrap(org.mapstruct.ap.shaded.freemarker.template.TemplateModel r6, java.lang.Class r7, int r8, java.util.Map r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 734
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper.tryUnwrap(org.mapstruct.ap.shaded.freemarker.template.TemplateModel, java.lang.Class, int, java.util.Map):java.lang.Object");
    }

    Object unwrapSequenceToArray(TemplateSequenceModel templateSequenceModel, Class cls, boolean z, Map map) throws TemplateModelException, NegativeArraySizeException {
        if (map != null) {
            Object obj = map.get(templateSequenceModel);
            if (obj != null) {
                return obj;
            }
        } else {
            map = new IdentityHashMap();
        }
        Class<?> componentType = cls.getComponentType();
        Object objNewInstance = Array.newInstance(componentType, templateSequenceModel.size());
        map.put(templateSequenceModel, objNewInstance);
        try {
            int size = templateSequenceModel.size();
            for (int i = 0; i < size; i++) {
                TemplateModel templateModel = templateSequenceModel.get(i);
                Object objTryUnwrap = tryUnwrap(templateModel, componentType, 0, map);
                Object obj2 = CAN_NOT_UNWRAP;
                if (objTryUnwrap == obj2) {
                    if (z) {
                        return obj2;
                    }
                    throw new _TemplateModelException(new Object[]{"Failed to convert ", new _DelayedFTLTypeDescription(templateSequenceModel), " object to ", new _DelayedShortClassName(objNewInstance.getClass()), ": Problematic sequence item at index ", new Integer(i), " with value type: ", new _DelayedFTLTypeDescription(templateModel)});
                }
                Array.set(objNewInstance, i, objTryUnwrap);
            }
            return objNewInstance;
        } finally {
            map.remove(templateSequenceModel);
        }
    }

    Object listToArray(List list, Class cls, Map map) throws TemplateModelException, NegativeArraySizeException {
        if (list instanceof SequenceAdapter) {
            return unwrapSequenceToArray(((SequenceAdapter) list).getTemplateSequenceModel(), cls, false, map);
        }
        if (map != null) {
            Object obj = map.get(list);
            if (obj != null) {
                return obj;
            }
        } else {
            map = new IdentityHashMap();
        }
        Class<?> componentType = cls.getComponentType();
        Object objNewInstance = Array.newInstance(componentType, list.size());
        map.put(list, objNewInstance);
        try {
            Iterator it = list.iterator();
            boolean z = false;
            boolean zIsNumerical = false;
            boolean zIsAssignableFrom = false;
            int i = 0;
            while (it.hasNext()) {
                Object next = it.next();
                if (next != null && !componentType.isInstance(next)) {
                    if (!z) {
                        zIsNumerical = ClassUtil.isNumerical(componentType);
                        Class clsClass$ = class$java$util$List;
                        if (clsClass$ == null) {
                            clsClass$ = class$("java.util.List");
                            class$java$util$List = clsClass$;
                        }
                        zIsAssignableFrom = clsClass$.isAssignableFrom(componentType);
                        z = true;
                    }
                    if (zIsNumerical && (next instanceof Number)) {
                        next = forceUnwrappedNumberToType((Number) next, componentType, true);
                    } else {
                        Class<?> clsClass$2 = class$java$lang$String;
                        if (clsClass$2 == null) {
                            clsClass$2 = class$("java.lang.String");
                            class$java$lang$String = clsClass$2;
                        }
                        if (componentType == clsClass$2 && (next instanceof Character)) {
                            next = String.valueOf(((Character) next).charValue());
                        } else {
                            Class<?> clsClass$3 = class$java$lang$Character;
                            if (clsClass$3 == null) {
                                clsClass$3 = class$("java.lang.Character");
                                class$java$lang$Character = clsClass$3;
                            }
                            if ((componentType == clsClass$3 || componentType == Character.TYPE) && (next instanceof String)) {
                                String str = (String) next;
                                if (str.length() == 1) {
                                    next = new Character(str.charAt(0));
                                }
                            } else if (componentType.isArray()) {
                                if (next instanceof List) {
                                    next = listToArray((List) next, componentType, map);
                                } else if (next instanceof TemplateSequenceModel) {
                                    next = unwrapSequenceToArray((TemplateSequenceModel) next, componentType, false, map);
                                }
                            } else if (zIsAssignableFrom && next.getClass().isArray()) {
                                next = arrayToList(next);
                            }
                        }
                    }
                }
                try {
                    Array.set(objNewInstance, i, next);
                    i++;
                } catch (IllegalArgumentException e) {
                    throw new TemplateModelException(new StringBuffer().append("Failed to convert ").append(ClassUtil.getShortClassNameOfObject(list)).append(" object to ").append(ClassUtil.getShortClassNameOfObject(objNewInstance)).append(": Problematic List item at index ").append(i).append(" with value type: ").append(ClassUtil.getShortClassNameOfObject(next)).toString(), (Exception) e);
                }
            }
            return objNewInstance;
        } finally {
            map.remove(list);
        }
    }

    List arrayToList(Object obj) throws TemplateModelException {
        if (!(obj instanceof Object[])) {
            return Array.getLength(obj) == 0 ? Collections.EMPTY_LIST : new PrimtiveArrayBackedReadOnlyList(obj);
        }
        Object[] objArr = (Object[]) obj;
        return objArr.length == 0 ? Collections.EMPTY_LIST : new NonPrimitiveArrayBackedReadOnlyList(objArr);
    }

    static Number forceUnwrappedNumberToType(Number number, Class cls, boolean z) throws Throwable {
        if (cls == number.getClass()) {
            return number;
        }
        if (cls != Integer.TYPE) {
            Class clsClass$ = class$java$lang$Integer;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$;
            }
            if (cls != clsClass$) {
                if (cls != Long.TYPE) {
                    Class clsClass$2 = class$java$lang$Long;
                    if (clsClass$2 == null) {
                        clsClass$2 = class$("java.lang.Long");
                        class$java$lang$Long = clsClass$2;
                    }
                    if (cls != clsClass$2) {
                        if (cls != Double.TYPE) {
                            Class clsClass$3 = class$java$lang$Double;
                            if (clsClass$3 == null) {
                                clsClass$3 = class$("java.lang.Double");
                                class$java$lang$Double = clsClass$3;
                            }
                            if (cls != clsClass$3) {
                                Class clsClass$4 = class$java$math$BigDecimal;
                                if (clsClass$4 == null) {
                                    clsClass$4 = class$("java.math.BigDecimal");
                                    class$java$math$BigDecimal = clsClass$4;
                                }
                                if (cls == clsClass$4) {
                                    if (number instanceof BigDecimal) {
                                        return number;
                                    }
                                    if (number instanceof BigInteger) {
                                        return new BigDecimal((BigInteger) number);
                                    }
                                    if (number instanceof Long) {
                                        return BigDecimal.valueOf(number.longValue());
                                    }
                                    return new BigDecimal(number.doubleValue());
                                }
                                if (cls != Float.TYPE) {
                                    Class clsClass$5 = class$java$lang$Float;
                                    if (clsClass$5 == null) {
                                        clsClass$5 = class$("java.lang.Float");
                                        class$java$lang$Float = clsClass$5;
                                    }
                                    if (cls != clsClass$5) {
                                        if (cls != Byte.TYPE) {
                                            Class clsClass$6 = class$java$lang$Byte;
                                            if (clsClass$6 == null) {
                                                clsClass$6 = class$("java.lang.Byte");
                                                class$java$lang$Byte = clsClass$6;
                                            }
                                            if (cls != clsClass$6) {
                                                if (cls != Short.TYPE) {
                                                    Class clsClass$7 = class$java$lang$Short;
                                                    if (clsClass$7 == null) {
                                                        clsClass$7 = class$("java.lang.Short");
                                                        class$java$lang$Short = clsClass$7;
                                                    }
                                                    if (cls != clsClass$7) {
                                                        Class clsClass$8 = class$java$math$BigInteger;
                                                        if (clsClass$8 == null) {
                                                            clsClass$8 = class$("java.math.BigInteger");
                                                            class$java$math$BigInteger = clsClass$8;
                                                        }
                                                        if (cls == clsClass$8) {
                                                            if (number instanceof BigInteger) {
                                                                return number;
                                                            }
                                                            if (z) {
                                                                if (number instanceof OverloadedNumberUtil.IntegerBigDecimal) {
                                                                    return ((OverloadedNumberUtil.IntegerBigDecimal) number).bigIntegerValue();
                                                                }
                                                                if (number instanceof BigDecimal) {
                                                                    return ((BigDecimal) number).toBigInteger();
                                                                }
                                                                return BigInteger.valueOf(number.longValue());
                                                            }
                                                            return new BigInteger(number.toString());
                                                        }
                                                        if (number instanceof OverloadedNumberUtil.NumberWithFallbackType) {
                                                            number = ((OverloadedNumberUtil.NumberWithFallbackType) number).getSourceNumber();
                                                        }
                                                        if (cls.isInstance(number)) {
                                                            return number;
                                                        }
                                                        return null;
                                                    }
                                                }
                                                return number instanceof Short ? (Short) number : new Short(number.shortValue());
                                            }
                                        }
                                        return number instanceof Byte ? (Byte) number : new Byte(number.byteValue());
                                    }
                                }
                                return number instanceof Float ? (Float) number : new Float(number.floatValue());
                            }
                        }
                        return number instanceof Double ? (Double) number : new Double(number.doubleValue());
                    }
                }
                return number instanceof Long ? (Long) number : new Long(number.longValue());
            }
        }
        return number instanceof Integer ? (Integer) number : new Integer(number.intValue());
    }

    TemplateModel invokeMethod(Object obj, Method method, Object[] objArr) throws IllegalAccessException, TemplateModelException, InvocationTargetException {
        return method.getReturnType() == Void.TYPE ? TemplateModel.NOTHING : getOuterIdentity().wrap(method.invoke(obj, objArr));
    }

    public TemplateHashModel getStaticModels() {
        return this.staticModels;
    }

    public TemplateHashModel getEnumModels() {
        ClassBasedModelFactory classBasedModelFactory = this.enumModels;
        if (classBasedModelFactory != null) {
            return classBasedModelFactory;
        }
        throw new UnsupportedOperationException("Enums not supported before J2SE 5.");
    }

    ModelCache getModelCache() {
        return this.modelCache;
    }

    public Object newInstance(Class cls, List list) throws TemplateModelException {
        try {
            try {
                Object obj = this.classIntrospector.get(cls).get(ClassIntrospector.CONSTRUCTORS_KEY);
                if (obj == null) {
                    throw new TemplateModelException(new StringBuffer("Class ").append(cls.getName()).append(" has no public constructors.").toString());
                }
                if (obj instanceof SimpleMethod) {
                    SimpleMethod simpleMethod = (SimpleMethod) obj;
                    Constructor constructor = (Constructor) simpleMethod.getMember();
                    try {
                        return constructor.newInstance(simpleMethod.unwrapArguments(list, this));
                    } catch (Exception e) {
                        if (e instanceof TemplateModelException) {
                            throw ((TemplateModelException) e);
                        }
                        throw _MethodUtil.newInvocationTemplateModelException((Object) null, constructor, e);
                    }
                }
                if (obj instanceof OverloadedMethods) {
                    MemberAndArguments memberAndArguments = ((OverloadedMethods) obj).getMemberAndArguments(list, this);
                    try {
                        return memberAndArguments.invokeConstructor(this);
                    } catch (Exception e2) {
                        if (e2 instanceof TemplateModelException) {
                            throw ((TemplateModelException) e2);
                        }
                        throw _MethodUtil.newInvocationTemplateModelException((Object) null, memberAndArguments.getCallableMemberDescriptor(), e2);
                    }
                }
                throw new BugException();
            } catch (Exception e3) {
                throw new TemplateModelException(new StringBuffer("Error while creating new instance of class ").append(cls.getName()).append("; see cause exception").toString(), e3);
            }
        } catch (TemplateModelException e4) {
            throw e4;
        }
    }

    public void removeFromClassIntrospectionCache(Class cls) {
        this.classIntrospector.remove(cls);
    }

    public void clearClassIntrospecitonCache() {
        this.classIntrospector.clearCache();
    }

    ClassIntrospector getClassIntrospector() {
        return this.classIntrospector;
    }

    public static void coerceBigDecimals(AccessibleObject accessibleObject, Object[] objArr) {
        Class<?>[] parameterTypes = null;
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj instanceof BigDecimal) {
                if (parameterTypes == null) {
                    if (accessibleObject instanceof Method) {
                        parameterTypes = ((Method) accessibleObject).getParameterTypes();
                    } else if (accessibleObject instanceof Constructor) {
                        parameterTypes = ((Constructor) accessibleObject).getParameterTypes();
                    } else {
                        throw new IllegalArgumentException(new StringBuffer("Expected method or  constructor; callable is ").append(accessibleObject.getClass().getName()).toString());
                    }
                }
                objArr[i] = coerceBigDecimal((BigDecimal) obj, parameterTypes[i]);
            }
        }
    }

    public static void coerceBigDecimals(Class[] clsArr, Object[] objArr) {
        int length = clsArr.length;
        int length2 = objArr.length;
        int iMin = Math.min(length, length2);
        for (int i = 0; i < iMin; i++) {
            Object obj = objArr[i];
            if (obj instanceof BigDecimal) {
                objArr[i] = coerceBigDecimal((BigDecimal) obj, clsArr[i]);
            }
        }
        if (length2 > length) {
            Class cls = clsArr[length - 1];
            while (length < length2) {
                Object obj2 = objArr[length];
                if (obj2 instanceof BigDecimal) {
                    objArr[length] = coerceBigDecimal((BigDecimal) obj2, cls);
                }
                length++;
            }
        }
    }

    public static Object coerceBigDecimal(BigDecimal bigDecimal, Class cls) throws Throwable {
        if (cls != Integer.TYPE) {
            Class clsClass$ = class$java$lang$Integer;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Integer");
                class$java$lang$Integer = clsClass$;
            }
            if (cls != clsClass$) {
                if (cls != Double.TYPE) {
                    Class clsClass$2 = class$java$lang$Double;
                    if (clsClass$2 == null) {
                        clsClass$2 = class$("java.lang.Double");
                        class$java$lang$Double = clsClass$2;
                    }
                    if (cls != clsClass$2) {
                        if (cls != Long.TYPE) {
                            Class clsClass$3 = class$java$lang$Long;
                            if (clsClass$3 == null) {
                                clsClass$3 = class$("java.lang.Long");
                                class$java$lang$Long = clsClass$3;
                            }
                            if (cls != clsClass$3) {
                                if (cls != Float.TYPE) {
                                    Class clsClass$4 = class$java$lang$Float;
                                    if (clsClass$4 == null) {
                                        clsClass$4 = class$("java.lang.Float");
                                        class$java$lang$Float = clsClass$4;
                                    }
                                    if (cls != clsClass$4) {
                                        if (cls != Short.TYPE) {
                                            Class clsClass$5 = class$java$lang$Short;
                                            if (clsClass$5 == null) {
                                                clsClass$5 = class$("java.lang.Short");
                                                class$java$lang$Short = clsClass$5;
                                            }
                                            if (cls != clsClass$5) {
                                                if (cls != Byte.TYPE) {
                                                    Class clsClass$6 = class$java$lang$Byte;
                                                    if (clsClass$6 == null) {
                                                        clsClass$6 = class$("java.lang.Byte");
                                                        class$java$lang$Byte = clsClass$6;
                                                    }
                                                    if (cls != clsClass$6) {
                                                        Class clsClass$7 = class$java$math$BigInteger;
                                                        if (clsClass$7 == null) {
                                                            clsClass$7 = class$("java.math.BigInteger");
                                                            class$java$math$BigInteger = clsClass$7;
                                                        }
                                                        return clsClass$7.isAssignableFrom(cls) ? bigDecimal.toBigInteger() : bigDecimal;
                                                    }
                                                }
                                                return new Byte(bigDecimal.byteValue());
                                            }
                                        }
                                        return new Short(bigDecimal.shortValue());
                                    }
                                }
                                return new Float(bigDecimal.floatValue());
                            }
                        }
                        return new Long(bigDecimal.longValue());
                    }
                }
                return new Double(bigDecimal.doubleValue());
            }
        }
        return new Integer(bigDecimal.intValue());
    }

    public String toString() {
        return new StringBuffer().append(ClassUtil.getShortClassNameOfObject(this)).append("@").append(System.identityHashCode(this)).append("(").append(this.incompatibleImprovements).append(") { simpleMapWrapper = ").append(this.simpleMapWrapper).append(", exposureLevel = ").append(this.classIntrospector.getExposureLevel()).append(", exposeFields = ").append(this.classIntrospector.getExposeFields()).append(", sharedClassIntrospCache = ").append(this.classIntrospector.isShared() ? new StringBuffer("@").append(System.identityHashCode(this.classIntrospector)).toString() : "none").append(", ...  }").toString();
    }

    private static ClassBasedModelFactory createEnumModels(BeansWrapper beansWrapper) {
        Constructor constructor = ENUMS_MODEL_CTOR;
        if (constructor == null) {
            return null;
        }
        try {
            return (ClassBasedModelFactory) constructor.newInstance(beansWrapper);
        } catch (Exception e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    private static Constructor enumsModelCtor() throws Throwable {
        try {
            Class.forName("java.lang.Enum");
            Class<?> cls = Class.forName("org.mapstruct.ap.shaded.freemarker.ext.beans._EnumModels");
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$ = class$freemarker$ext$beans$BeansWrapper;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper");
                class$freemarker$ext$beans$BeansWrapper = clsClass$;
            }
            clsArr[0] = clsClass$;
            return cls.getDeclaredConstructor(clsArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static final class MethodAppearanceDecision {
        private PropertyDescriptor exposeAsProperty;
        private String exposeMethodAs;
        private boolean methodShadowsProperty;

        void setDefaults(Method method) {
            this.exposeAsProperty = null;
            this.exposeMethodAs = method.getName();
            this.methodShadowsProperty = true;
        }

        public PropertyDescriptor getExposeAsProperty() {
            return this.exposeAsProperty;
        }

        public void setExposeAsProperty(PropertyDescriptor propertyDescriptor) {
            this.exposeAsProperty = propertyDescriptor;
        }

        public String getExposeMethodAs() {
            return this.exposeMethodAs;
        }

        public void setExposeMethodAs(String str) {
            this.exposeMethodAs = str;
        }

        public boolean getMethodShadowsProperty() {
            return this.methodShadowsProperty;
        }

        public void setMethodShadowsProperty(boolean z) {
            this.methodShadowsProperty = z;
        }
    }

    public static final class MethodAppearanceDecisionInput {
        private Class containingClass;
        private Method method;

        void setMethod(Method method) {
            this.method = method;
        }

        void setContainingClass(Class cls) {
            this.containingClass = cls;
        }

        public Method getMethod() {
            return this.method;
        }

        public Class getContainingClass() {
            return this.containingClass;
        }
    }
}
