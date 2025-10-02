package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder;
import org.mapstruct.ap.internal.model.PropertyMapping;
import org.mapstruct.ap.internal.model.beanmapping.MappingReference;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.beanmapping.PropertyEntry;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/* loaded from: classes3.dex */
public class NestedTargetPropertyMappingHolder {
    private final boolean errorOccurred;
    private final Set<String> handledTargets;
    private final List<Parameter> processedSourceParameters;
    private final List<PropertyMapping> propertyMappings;
    private final Map<String, Set<MappingReference>> unprocessedDefinedTarget;

    public NestedTargetPropertyMappingHolder(List<Parameter> list, Set<String> set, List<PropertyMapping> list2, Map<String, Set<MappingReference>> map, boolean z) {
        this.processedSourceParameters = list;
        this.handledTargets = set;
        this.propertyMappings = list2;
        this.unprocessedDefinedTarget = map;
        this.errorOccurred = z;
    }

    public List<Parameter> getProcessedSourceParameters() {
        return this.processedSourceParameters;
    }

    public Set<String> getHandledTargets() {
        return this.handledTargets;
    }

    public List<PropertyMapping> getPropertyMappings() {
        return this.propertyMappings;
    }

    public Map<String, Set<MappingReference>> getUnprocessedDefinedTarget() {
        return this.unprocessedDefinedTarget;
    }

    public boolean hasErrorOccurred() {
        return this.errorOccurred;
    }

    public static class Builder {
        private boolean errorOccurred;
        private Set<String> existingVariableNames;
        private Set<String> handledTargets;
        private MappingBuilderContext mappingContext;
        private MappingReferences mappingReferences;
        private Method method;
        private List<PropertyMapping> propertyMappings;
        private Map<String, Accessor> targetPropertiesWriteAccessors;
        private Type targetType;

        public Builder mappingReferences(MappingReferences mappingReferences) {
            this.mappingReferences = mappingReferences;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder mappingContext(MappingBuilderContext mappingBuilderContext) {
            this.mappingContext = mappingBuilderContext;
            return this;
        }

        public Builder existingVariableNames(Set<String> set) {
            this.existingVariableNames = set;
            return this;
        }

        public Builder targetPropertiesWriteAccessors(Map<String, Accessor> map) {
            this.targetPropertiesWriteAccessors = map;
            return this;
        }

        public Builder targetPropertyType(Type type) {
            this.targetType = type;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v0 */
        /* JADX WARN: Type inference failed for: r10v1 */
        /* JADX WARN: Type inference failed for: r10v2 */
        public NestedTargetPropertyMappingHolder build() {
            boolean z;
            ArrayList arrayList = new ArrayList();
            this.handledTargets = new HashSet();
            this.propertyMappings = new ArrayList();
            GroupedTargetReferences groupedTargetReferencesGroupByTargetReferences = groupByTargetReferences();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry entry : groupedTargetReferencesGroupByTargetReferences.poppedTargetReferences.entrySet()) {
                String str = (String) entry.getKey();
                GroupedBySourceParameters groupedBySourceParametersGroupBySourceParameter = groupBySourceParameter((Set) entry.getValue(), (Set) groupedTargetReferencesGroupByTargetReferences.singleTargetReferences.get(str));
                ?? r10 = 1;
                boolean z2 = groupedBySourceParametersGroupBySourceParameter.groupedBySourceParameter.keySet().size() > 1;
                linkedHashMap.put(str, groupedBySourceParametersGroupBySourceParameter.notProcessedAppliesToAll);
                for (Map.Entry<Parameter, Set<MappingReference>> entry2 : groupedBySourceParametersGroupBySourceParameter.groupedBySourceParameter.entrySet()) {
                    Parameter key = entry2.getKey();
                    GroupedSourceReferences groupedSourceReferencesGroupByPoppedSourceReferences = groupByPoppedSourceReferences(entry2, (Set) groupedTargetReferencesGroupByTargetReferences.singleTargetReferences.get(str));
                    boolean z3 = (((z2 || groupedSourceReferencesGroupByPoppedSourceReferences.groupedBySourceReferences.size() > r10) ? r10 : false) || !groupedSourceReferencesGroupByPoppedSourceReferences.nonNested.isEmpty()) ? r10 : false;
                    for (Map.Entry entry3 : groupedSourceReferencesGroupByPoppedSourceReferences.groupedBySourceReferences.entrySet()) {
                        PropertyEntry propertyEntry = (PropertyEntry) entry3.getKey();
                        PropertyMapping propertyMappingCreatePropertyMappingForNestedTarget = createPropertyMappingForNestedTarget(new MappingReferences((Set<MappingReference>) entry3.getValue(), z2, z3), str, new SourceReference.BuilderFromProperty().sourceParameter(key).type(propertyEntry.getType()).readAccessor(propertyEntry.getReadAccessor()).presenceChecker(propertyEntry.getPresenceChecker()).name(str).build(), z3);
                        if (propertyMappingCreatePropertyMappingForNestedTarget != null) {
                            this.propertyMappings.add(propertyMappingCreatePropertyMappingForNestedTarget);
                        }
                        this.handledTargets.add(entry.getKey());
                    }
                    if (groupedSourceReferencesGroupByPoppedSourceReferences.nonNested.isEmpty()) {
                        z = true;
                    } else {
                        z = true;
                        PropertyMapping propertyMappingCreatePropertyMappingForNestedTarget2 = createPropertyMappingForNestedTarget(new MappingReferences(groupedSourceReferencesGroupByPoppedSourceReferences.nonNested, true), str, new SourceReference.BuilderFromProperty().sourceParameter(key).name(str).build(), z2 || !groupedSourceReferencesGroupByPoppedSourceReferences.groupedBySourceReferences.isEmpty());
                        if (propertyMappingCreatePropertyMappingForNestedTarget2 != null) {
                            this.propertyMappings.add(propertyMappingCreatePropertyMappingForNestedTarget2);
                        }
                        this.handledTargets.add(entry.getKey());
                    }
                    handleSourceParameterMappings(groupedSourceReferencesGroupByPoppedSourceReferences.sourceParameterMappings, str, key, z2);
                    linkedHashMap.put(str, groupedSourceReferencesGroupByPoppedSourceReferences.notProcessedAppliesToAll);
                    r10 = z;
                }
            }
            return new NestedTargetPropertyMappingHolder(arrayList, this.handledTargets, this.propertyMappings, linkedHashMap, this.errorOccurred);
        }

        private void handleSourceParameterMappings(Set<MappingReference> set, String str, Parameter parameter, boolean z) {
            if (set.isEmpty()) {
                return;
            }
            PropertyMapping propertyMappingCreatePropertyMappingForNestedTarget = createPropertyMappingForNestedTarget(new MappingReferences((Set<MappingReference>) Collections.emptySet(), false, true), str, new SourceReference.BuilderFromProperty().sourceParameter(parameter).name(str).build(), z);
            if (propertyMappingCreatePropertyMappingForNestedTarget != null) {
                this.propertyMappings.add(propertyMappingCreatePropertyMappingForNestedTarget);
            }
            this.handledTargets.add(str);
        }

        private GroupedTargetReferences groupByTargetReferences() {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (MappingReference mappingReference : this.mappingReferences.getMappingReferences()) {
                String str = (String) org.mapstruct.ap.internal.util.Collections.first(mappingReference.getTargetReference().getPropertyEntries());
                MappingReference mappingReferencePopTargetReference = mappingReference.popTargetReference();
                if (mappingReferencePopTargetReference != null) {
                    ((Set) linkedHashMap.computeIfAbsent(str, new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return NestedTargetPropertyMappingHolder.Builder.lambda$groupByTargetReferences$0((String) obj);
                        }
                    })).add(mappingReferencePopTargetReference);
                } else {
                    ((Set) linkedHashMap2.computeIfAbsent(str, new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda4
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return NestedTargetPropertyMappingHolder.Builder.lambda$groupByTargetReferences$1((String) obj);
                        }
                    })).add(mappingReference);
                }
            }
            return new GroupedTargetReferences(linkedHashMap, linkedHashMap2);
        }

        static /* synthetic */ Set lambda$groupByTargetReferences$0(String str) {
            return new LinkedHashSet();
        }

        static /* synthetic */ Set lambda$groupByTargetReferences$1(String str) {
            return new LinkedHashSet();
        }

        private GroupedBySourceParameters groupBySourceParameter(Set<MappingReference> set, Set<MappingReference> set2) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (MappingReference mappingReference : set) {
                if (mappingReference.getSourceReference() != null && mappingReference.getSourceReference().isValid()) {
                    ((Set) linkedHashMap.computeIfAbsent(mappingReference.getSourceReference().getParameter(), new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return NestedTargetPropertyMappingHolder.Builder.lambda$groupBySourceParameter$2((Parameter) obj);
                        }
                    })).add(mappingReference);
                } else {
                    linkedHashSet.add(mappingReference);
                }
            }
            populateWithSingleTargetReferences(linkedHashMap, set2, new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((SourceReference) obj).getParameter();
                }
            });
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                ((Set) ((Map.Entry) it.next()).getValue()).addAll(linkedHashSet);
            }
            if (!linkedHashMap.isEmpty()) {
                linkedHashSet = new LinkedHashSet();
            }
            return new GroupedBySourceParameters(linkedHashMap, linkedHashSet);
        }

        static /* synthetic */ Set lambda$groupBySourceParameter$2(Parameter parameter) {
            return new LinkedHashSet();
        }

        private GroupedSourceReferences groupByPoppedSourceReferences(Map.Entry<Parameter, Set<MappingReference>> entry, Set<MappingReference> set) {
            Set<MappingReference> value = entry.getValue();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            LinkedHashSet linkedHashSet3 = new LinkedHashSet();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (MappingReference mappingReference : value) {
                MappingReference mappingReferencePopSourceReference = mappingReference.popSourceReference();
                if (mappingReferencePopSourceReference != null) {
                    ((Set) linkedHashMap.computeIfAbsent((PropertyEntry) org.mapstruct.ap.internal.util.Collections.first(mappingReference.getSourceReference().getPropertyEntries()), new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda5
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return NestedTargetPropertyMappingHolder.Builder.lambda$groupByPoppedSourceReferences$3((PropertyEntry) obj);
                        }
                    })).add(mappingReferencePopSourceReference);
                } else if (mappingReference.getSourceReference() == null) {
                    linkedHashSet2.add(mappingReference);
                } else {
                    linkedHashSet.add(mappingReference);
                }
            }
            populateWithSingleTargetReferences(linkedHashMap, extractSingleTargetReferencesToUseAndPopulateSourceParameterMappings(set, linkedHashSet3, linkedHashMap.isEmpty() && linkedHashSet.isEmpty(), entry.getKey()), new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return NestedTargetPropertyMappingHolder.Builder.lambda$groupByPoppedSourceReferences$4((SourceReference) obj);
                }
            });
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                ((Set) ((Map.Entry) it.next()).getValue()).addAll(linkedHashSet2);
            }
            LinkedHashSet linkedHashSet4 = new LinkedHashSet();
            if (linkedHashMap.isEmpty() && !linkedHashSet.isEmpty()) {
                linkedHashSet.addAll(linkedHashSet2);
            } else if (linkedHashMap.isEmpty() && linkedHashSet.isEmpty()) {
                linkedHashSet4.addAll(linkedHashSet2);
            }
            return new GroupedSourceReferences(linkedHashMap, linkedHashSet, linkedHashSet4, linkedHashSet3);
        }

        static /* synthetic */ Set lambda$groupByPoppedSourceReferences$3(PropertyEntry propertyEntry) {
            return new LinkedHashSet();
        }

        static /* synthetic */ PropertyEntry lambda$groupByPoppedSourceReferences$4(SourceReference sourceReference) {
            if (sourceReference.getPropertyEntries().isEmpty()) {
                return null;
            }
            return (PropertyEntry) org.mapstruct.ap.internal.util.Collections.first(sourceReference.getPropertyEntries());
        }

        private Set<MappingReference> extractSingleTargetReferencesToUseAndPopulateSourceParameterMappings(Set<MappingReference> set, Set<MappingReference> set2, boolean z, Parameter parameter) {
            if (set == null) {
                return null;
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet(set.size());
            for (MappingReference mappingReference : set) {
                if (mappingReference.getSourceReference() != null && mappingReference.getSourceReference().isValid() && parameter.equals(mappingReference.getSourceReference().getParameter())) {
                    if (z && mappingReference.getSourceReference().getPropertyEntries().isEmpty()) {
                        set2.add(mappingReference);
                    } else {
                        linkedHashSet.add(mappingReference);
                    }
                }
            }
            return linkedHashSet;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private PropertyMapping createPropertyMappingForNestedTarget(MappingReferences mappingReferences, String str, SourceReference sourceReference, boolean z) {
            Accessor accessor = this.targetPropertiesWriteAccessors.get(str);
            Accessor accessor2 = this.targetType.getPropertyReadAccessors().get(str);
            if (accessor == null) {
                String mostSimilarWord = Strings.getMostSimilarWord(str, this.targetType.getPropertyReadAccessors().keySet());
                for (MappingReference mappingReference : mappingReferences.getMappingReferences()) {
                    MappingOptions mapping = mappingReference.getMapping();
                    ArrayList arrayList = new ArrayList(mappingReference.getTargetReference().getPathProperties());
                    if (!arrayList.isEmpty()) {
                        arrayList.set(arrayList.size() - 1, mostSimilarWord);
                    }
                    this.mappingContext.getMessager().printMessage(mapping.getElement(), mapping.getMirror(), mapping.getTargetAnnotationValue(), Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_TYPE, str, this.targetType.describe(), mapping.getTargetName(), Strings.join(arrayList, "."));
                }
                this.errorOccurred = true;
                return null;
            }
            return ((PropertyMapping.PropertyMappingBuilder) new PropertyMapping.PropertyMappingBuilder().mappingContext(this.mappingContext)).sourceMethod(this.method).target(str, accessor2, accessor).sourceReference(sourceReference).existingVariableNames(this.existingVariableNames).dependsOn(mappingReferences.collectNestedDependsOn()).forgeMethodWithMappingReferences(mappingReferences).forceUpdateMethod(z).forgedNamedBased(false).options(this.method.getOptions().getBeanMapping()).build();
        }

        private <K> void populateWithSingleTargetReferences(Map<K, Set<MappingReference>> map, Set<MappingReference> set, Function<SourceReference, K> function) {
            K kApply;
            if (set != null) {
                for (MappingReference mappingReference : set) {
                    if (mappingReference.getSourceReference() != null && mappingReference.getSourceReference().isValid() && (kApply = function.apply(mappingReference.getSourceReference())) != null) {
                        map.computeIfAbsent(kApply, new Function() { // from class: org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder$Builder$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return NestedTargetPropertyMappingHolder.Builder.lambda$populateWithSingleTargetReferences$5(obj);
                            }
                        });
                    }
                }
            }
        }

        static /* synthetic */ Set lambda$populateWithSingleTargetReferences$5(Object obj) {
            return new LinkedHashSet();
        }
    }

    private static class GroupedBySourceParameters {
        private final Map<Parameter, Set<MappingReference>> groupedBySourceParameter;
        private final Set<MappingReference> notProcessedAppliesToAll;

        private GroupedBySourceParameters(Map<Parameter, Set<MappingReference>> map, Set<MappingReference> set) {
            this.groupedBySourceParameter = map;
            this.notProcessedAppliesToAll = set;
        }
    }

    private static class GroupedTargetReferences {
        private final Map<String, Set<MappingReference>> poppedTargetReferences;
        private final Map<String, Set<MappingReference>> singleTargetReferences;

        private GroupedTargetReferences(Map<String, Set<MappingReference>> map, Map<String, Set<MappingReference>> map2) {
            this.poppedTargetReferences = map;
            this.singleTargetReferences = map2;
        }

        public String toString() {
            return "GroupedTargetReferences{poppedTargetReferences=" + this.poppedTargetReferences + ", singleTargetReferences=" + this.singleTargetReferences + "}";
        }
    }

    private static class GroupedSourceReferences {
        private final Map<PropertyEntry, Set<MappingReference>> groupedBySourceReferences;
        private final Set<MappingReference> nonNested;
        private final Set<MappingReference> notProcessedAppliesToAll;
        private final Set<MappingReference> sourceParameterMappings;

        private GroupedSourceReferences(Map<PropertyEntry, Set<MappingReference>> map, Set<MappingReference> set, Set<MappingReference> set2, Set<MappingReference> set3) {
            this.groupedBySourceReferences = map;
            this.nonNested = set;
            this.notProcessedAppliesToAll = set2;
            this.sourceParameterMappings = set3;
        }

        public String toString() {
            return "GroupedSourceReferences{groupedBySourceReferences=" + this.groupedBySourceReferences + ", nonNested=" + this.nonNested + ", notProcessedAppliesToAll=" + this.notProcessedAppliesToAll + ", sourceParameterMappings=" + this.sourceParameterMappings + '}';
        }
    }
}
