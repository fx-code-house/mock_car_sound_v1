package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.EnumMappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.ValueMappingOptions;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/* loaded from: classes3.dex */
public class ValueMappingMethod extends MappingMethod {
    private final String defaultTarget;
    private final String nullTarget;
    private final boolean overridden;
    private final Type unexpectedValueMappingException;
    private final List<MappingEntry> valueMappings;

    public static class Builder {
        private MappingBuilderContext ctx;
        private EnumMappingOptions enumMapping;
        private EnumTransformationStrategyInvoker enumTransformationInvoker;
        private Method method;
        private ValueMappings valueMappings;

        public Builder mappingContext(MappingBuilderContext mappingBuilderContext) {
            this.ctx = mappingBuilderContext;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder valueMappings(List<ValueMappingOptions> list) {
            this.valueMappings = new ValueMappings(list);
            return this;
        }

        public Builder enumMapping(EnumMappingOptions enumMappingOptions) {
            this.enumMapping = enumMappingOptions;
            return this;
        }

        public ValueMappingMethod build() {
            if (!this.enumMapping.isValid()) {
                return null;
            }
            initializeEnumTransformationStrategy();
            ArrayList arrayList = new ArrayList();
            Type type = ((Parameter) Collections.first(this.method.getSourceParameters())).getType();
            Type resultType = this.method.getResultType();
            if (resultType.isEnumType() && this.valueMappings.nullTarget == null) {
                this.valueMappings.nullValueTarget = this.ctx.getEnumMappingStrategy().getDefaultNullEnumConstant(resultType.getTypeElement());
            }
            if (type.isEnumType() && resultType.isEnumType()) {
                arrayList.addAll(enumToEnumMapping(this.method, type, resultType));
            } else if (type.isEnumType() && resultType.isString()) {
                arrayList.addAll(enumToStringMapping(this.method, type));
            } else if (type.isString() && resultType.isEnumType()) {
                arrayList.addAll(stringToEnumMapping(this.method, resultType));
            }
            SelectionParameters selectionParameters = getSelectionParameters(this.method, this.ctx.getTypeUtils());
            HashSet hashSet = new HashSet(this.method.getParameterNames());
            return new ValueMappingMethod(this.method, arrayList, this.valueMappings.nullValueTarget, this.valueMappings.defaultTargetValue, determineUnexpectedValueMappingException(), LifecycleMethodResolver.beforeMappingMethods(this.method, selectionParameters, this.ctx, hashSet), LifecycleMethodResolver.afterMappingMethods(this.method, selectionParameters, this.ctx, hashSet));
        }

        private void initializeEnumTransformationStrategy() {
            if (this.enumMapping.hasNameTransformationStrategy()) {
                Map<String, EnumTransformationStrategy> enumTransformationStrategies = this.ctx.getEnumTransformationStrategies();
                String nameTransformationStrategy = this.enumMapping.getNameTransformationStrategy();
                if (enumTransformationStrategies.containsKey(nameTransformationStrategy)) {
                    this.enumTransformationInvoker = new EnumTransformationStrategyInvoker(enumTransformationStrategies.get(nameTransformationStrategy), this.enumMapping.getNameTransformationConfiguration());
                    return;
                }
                return;
            }
            this.enumTransformationInvoker = EnumTransformationStrategyInvoker.DEFAULT;
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x012d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private java.util.List<org.mapstruct.ap.internal.model.ValueMappingMethod.MappingEntry> enumToEnumMapping(org.mapstruct.ap.internal.model.source.Method r10, org.mapstruct.ap.internal.model.common.Type r11, org.mapstruct.ap.internal.model.common.Type r12) {
            /*
                Method dump skipped, instructions count: 338
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.internal.model.ValueMappingMethod.Builder.enumToEnumMapping(org.mapstruct.ap.internal.model.source.Method, org.mapstruct.ap.internal.model.common.Type, org.mapstruct.ap.internal.model.common.Type):java.util.List");
        }

        private List<MappingEntry> enumToStringMapping(Method method, Type type) {
            ArrayList arrayList = new ArrayList();
            ArrayList<String> arrayList2 = new ArrayList(type.getEnumConstants());
            boolean z = !reportErrorIfMappedSourceEnumConstantsDontExist(method, type);
            boolean z2 = !reportErrorIfSourceEnumConstantsContainsAnyRemaining(method);
            if (!z && !z2) {
                for (ValueMappingOptions valueMappingOptions : this.valueMappings.regularValueMappings) {
                    arrayList.add(new MappingEntry(valueMappingOptions.getSource(), valueMappingOptions.getTarget()));
                    arrayList2.remove(valueMappingOptions.getSource());
                }
                if (!this.valueMappings.hasMapAnyUnmapped) {
                    TypeElement typeElement = type.getTypeElement();
                    for (String str : arrayList2) {
                        arrayList.add(new MappingEntry(str, this.enumTransformationInvoker.transform(getEnumConstant(typeElement, str))));
                    }
                }
            }
            return arrayList;
        }

        private List<MappingEntry> stringToEnumMapping(Method method, Type type) {
            ArrayList arrayList = new ArrayList();
            ArrayList<String> arrayList2 = new ArrayList(type.getEnumConstants());
            boolean z = !reportErrorIfMappedTargetEnumConstantsDontExist(method, type);
            boolean z2 = !reportErrorIfAnyRemainingOrAnyUnMappedMissing(method);
            if (!z && !z2) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                for (ValueMappingOptions valueMappingOptions : this.valueMappings.regularValueMappings) {
                    linkedHashSet.add(valueMappingOptions.getSource());
                    arrayList.add(new MappingEntry(valueMappingOptions.getSource(), valueMappingOptions.getTarget()));
                    arrayList2.remove(valueMappingOptions.getSource());
                }
                if (!this.valueMappings.hasMapAnyUnmapped) {
                    linkedHashSet.add("<NULL>");
                    TypeElement typeElement = type.getTypeElement();
                    for (String str : arrayList2) {
                        String strTransform = this.enumTransformationInvoker.transform(getEnumConstant(typeElement, str));
                        if (!linkedHashSet.contains(strTransform)) {
                            arrayList.add(new MappingEntry(strTransform, str));
                        }
                    }
                }
            }
            return arrayList;
        }

        private String getEnumConstant(TypeElement typeElement, String str) {
            return this.ctx.getEnumMappingStrategy().getEnumConstant(typeElement, str);
        }

        private SelectionParameters getSelectionParameters(Method method, Types types) {
            BeanMappingGem beanMappingGemInstanceOn = BeanMappingGem.instanceOn((Element) method.getExecutable());
            if (beanMappingGemInstanceOn != null) {
                return new SelectionParameters(beanMappingGemInstanceOn.qualifiedBy().get(), beanMappingGemInstanceOn.qualifiedByName().get(), beanMappingGemInstanceOn.resultType().get(), types);
            }
            return null;
        }

        private boolean reportErrorIfMappedSourceEnumConstantsDontExist(Method method, Type type) {
            List<String> enumConstants = type.getEnumConstants();
            boolean z = false;
            for (ValueMappingOptions valueMappingOptions : this.valueMappings.regularValueMappings) {
                if (!enumConstants.contains(valueMappingOptions.getSource())) {
                    this.ctx.getMessager().printMessage(method.getExecutable(), valueMappingOptions.getMirror(), valueMappingOptions.getSourceAnnotationValue(), Message.VALUEMAPPING_NON_EXISTING_CONSTANT, valueMappingOptions.getSource(), type);
                    z = true;
                }
            }
            return !z;
        }

        private boolean reportErrorIfSourceEnumConstantsContainsAnyRemaining(Method method) {
            boolean z = false;
            if (this.valueMappings.hasMapAnyRemaining) {
                this.ctx.getMessager().printMessage(method.getExecutable(), this.valueMappings.defaultTarget.getMirror(), this.valueMappings.defaultTarget.getSourceAnnotationValue(), Message.VALUEMAPPING_ANY_REMAINING_FOR_NON_ENUM, method.getResultType());
                z = true;
            }
            return !z;
        }

        private boolean reportErrorIfAnyRemainingOrAnyUnMappedMissing(Method method) {
            boolean z = false;
            if (!this.valueMappings.hasMapAnyUnmapped && !this.valueMappings.hasMapAnyRemaining) {
                this.ctx.getMessager().printMessage(method.getExecutable(), Message.VALUEMAPPING_ANY_REMAINING_OR_UNMAPPED_MISSING, new Object[0]);
                z = true;
            }
            return !z;
        }

        private boolean reportErrorIfMappedTargetEnumConstantsDontExist(Method method, Type type) {
            List<String> enumConstants = type.getEnumConstants();
            boolean z = false;
            for (ValueMappingOptions valueMappingOptions : this.valueMappings.regularValueMappings) {
                if (!"<NULL>".equals(valueMappingOptions.getTarget()) && !enumConstants.contains(valueMappingOptions.getTarget())) {
                    this.ctx.getMessager().printMessage(method.getExecutable(), valueMappingOptions.getMirror(), valueMappingOptions.getTargetAnnotationValue(), Message.VALUEMAPPING_NON_EXISTING_CONSTANT, valueMappingOptions.getTarget(), method.getReturnType());
                    z = true;
                }
            }
            if (this.valueMappings.defaultTarget != null && !"<NULL>".equals(this.valueMappings.defaultTarget.getTarget()) && !enumConstants.contains(this.valueMappings.defaultTarget.getTarget())) {
                this.ctx.getMessager().printMessage(method.getExecutable(), this.valueMappings.defaultTarget.getMirror(), this.valueMappings.defaultTarget.getTargetAnnotationValue(), Message.VALUEMAPPING_NON_EXISTING_CONSTANT, this.valueMappings.defaultTarget.getTarget(), method.getReturnType());
                z = true;
            }
            if (this.valueMappings.nullTarget != null && "<NULL>".equals(this.valueMappings.nullTarget.getTarget()) && !enumConstants.contains(this.valueMappings.nullTarget.getTarget())) {
                this.ctx.getMessager().printMessage(method.getExecutable(), this.valueMappings.nullTarget.getMirror(), this.valueMappings.nullTarget.getTargetAnnotationValue(), Message.VALUEMAPPING_NON_EXISTING_CONSTANT, this.valueMappings.nullTarget.getTarget(), method.getReturnType());
                z = true;
            } else if (this.valueMappings.nullTarget == null && this.valueMappings.nullValueTarget != null && !enumConstants.contains(this.valueMappings.nullValueTarget)) {
                this.ctx.getMessager().printMessage(method.getExecutable(), Message.VALUEMAPPING_NON_EXISTING_CONSTANT_FROM_SPI, this.valueMappings.nullValueTarget, method.getReturnType(), this.ctx.getEnumMappingStrategy());
            }
            return !z;
        }

        private Type determineUnexpectedValueMappingException() {
            if (this.valueMappings.hasDefaultValue) {
                return null;
            }
            TypeMirror unexpectedValueMappingException = this.enumMapping.getUnexpectedValueMappingException();
            if (unexpectedValueMappingException != null) {
                return this.ctx.getTypeFactory().getType(unexpectedValueMappingException);
            }
            return this.ctx.getTypeFactory().getType(this.ctx.getEnumMappingStrategy().getUnexpectedValueMappingExceptionType());
        }
    }

    private static class EnumTransformationStrategyInvoker {
        private static final EnumTransformationStrategyInvoker DEFAULT = new EnumTransformationStrategyInvoker(null, null);
        private final String configuration;
        private final EnumTransformationStrategy transformationStrategy;

        private EnumTransformationStrategyInvoker(EnumTransformationStrategy enumTransformationStrategy, String str) {
            this.transformationStrategy = enumTransformationStrategy;
            this.configuration = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String transform(String str) {
            EnumTransformationStrategy enumTransformationStrategy = this.transformationStrategy;
            return enumTransformationStrategy == null ? str : enumTransformationStrategy.transform(str, this.configuration);
        }
    }

    private static class ValueMappings {
        ValueMappingOptions defaultTarget;
        String defaultTargetValue;
        boolean hasDefaultValue;
        boolean hasMapAnyRemaining;
        boolean hasMapAnyUnmapped;
        boolean hasNullValue;
        ValueMappingOptions nullTarget;
        String nullValueTarget;
        List<ValueMappingOptions> regularValueMappings = new ArrayList();

        ValueMappings(List<ValueMappingOptions> list) {
            this.defaultTarget = null;
            this.defaultTargetValue = null;
            this.nullTarget = null;
            this.nullValueTarget = null;
            this.hasMapAnyUnmapped = false;
            this.hasMapAnyRemaining = false;
            this.hasDefaultValue = false;
            this.hasNullValue = false;
            for (ValueMappingOptions valueMappingOptions : list) {
                if ("<ANY_REMAINING>".equals(valueMappingOptions.getSource())) {
                    this.defaultTarget = valueMappingOptions;
                    this.defaultTargetValue = getValue(valueMappingOptions);
                    this.hasDefaultValue = true;
                    this.hasMapAnyRemaining = true;
                } else if ("<ANY_UNMAPPED>".equals(valueMappingOptions.getSource())) {
                    this.defaultTarget = valueMappingOptions;
                    this.defaultTargetValue = getValue(valueMappingOptions);
                    this.hasDefaultValue = true;
                    this.hasMapAnyUnmapped = true;
                } else if ("<NULL>".equals(valueMappingOptions.getSource())) {
                    this.nullTarget = valueMappingOptions;
                    this.nullValueTarget = getValue(valueMappingOptions);
                    this.hasNullValue = true;
                } else {
                    this.regularValueMappings.add(valueMappingOptions);
                }
            }
        }

        String getValue(ValueMappingOptions valueMappingOptions) {
            if ("<NULL>".equals(valueMappingOptions.getTarget())) {
                return null;
            }
            return valueMappingOptions.getTarget();
        }
    }

    private ValueMappingMethod(Method method, List<MappingEntry> list, String str, String str2, Type type, List<LifecycleCallbackMethodReference> list2, List<LifecycleCallbackMethodReference> list3) {
        super(method, list2, list3);
        this.valueMappings = list;
        this.nullTarget = str;
        this.defaultTarget = str2;
        this.unexpectedValueMappingException = type;
        this.overridden = method.overridesMethod();
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        Type type = this.unexpectedValueMappingException;
        if (type != null && !type.isJavaLangType()) {
            importTypes.addAll(this.unexpectedValueMappingException.getImportTypes());
        }
        return importTypes;
    }

    public List<MappingEntry> getValueMappings() {
        return this.valueMappings;
    }

    public String getDefaultTarget() {
        return this.defaultTarget;
    }

    public String getNullTarget() {
        return this.nullTarget;
    }

    public Type getUnexpectedValueMappingException() {
        return this.unexpectedValueMappingException;
    }

    public Parameter getSourceParameter() {
        return (Parameter) Collections.first(getSourceParameters());
    }

    public boolean isOverridden() {
        return this.overridden;
    }

    public static class MappingEntry {
        private final String source;
        private final String target;

        MappingEntry(String str, String str2) {
            this.source = str;
            if (!"<NULL>".equals(str2)) {
                this.target = str2;
            } else {
                this.target = null;
            }
        }

        public String getSource() {
            return this.source;
        }

        public String getTarget() {
            return this.target;
        }
    }
}
