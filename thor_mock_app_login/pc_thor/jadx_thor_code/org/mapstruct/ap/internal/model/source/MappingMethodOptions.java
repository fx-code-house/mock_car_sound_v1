package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/* loaded from: classes3.dex */
public class MappingMethodOptions {
    private static final MappingMethodOptions EMPTY = new MappingMethodOptions(null, Collections.emptySet(), null, null, null, null, Collections.emptyList());
    private BeanMappingOptions beanMapping;
    private EnumMappingOptions enumMappingOptions;
    private boolean fullyInitialized;
    private IterableMappingOptions iterableMapping;
    private MapMappingOptions mapMapping;
    private MapperOptions mapper;
    private Set<MappingOptions> mappings;
    private List<ValueMappingOptions> valueMappings;

    public MappingMethodOptions(MapperOptions mapperOptions, Set<MappingOptions> set, IterableMappingOptions iterableMappingOptions, MapMappingOptions mapMappingOptions, BeanMappingOptions beanMappingOptions, EnumMappingOptions enumMappingOptions, List<ValueMappingOptions> list) {
        this.mapper = mapperOptions;
        this.mappings = set;
        this.iterableMapping = iterableMappingOptions;
        this.mapMapping = mapMappingOptions;
        this.beanMapping = beanMappingOptions;
        this.enumMappingOptions = enumMappingOptions;
        this.valueMappings = list;
    }

    public static MappingMethodOptions empty() {
        return EMPTY;
    }

    public Set<MappingOptions> getMappings() {
        return this.mappings;
    }

    public IterableMappingOptions getIterableMapping() {
        return this.iterableMapping;
    }

    public MapMappingOptions getMapMapping() {
        return this.mapMapping;
    }

    public BeanMappingOptions getBeanMapping() {
        return this.beanMapping;
    }

    public EnumMappingOptions getEnumMappingOptions() {
        return this.enumMappingOptions;
    }

    public List<ValueMappingOptions> getValueMappings() {
        return this.valueMappings;
    }

    public void setIterableMapping(IterableMappingOptions iterableMappingOptions) {
        this.iterableMapping = iterableMappingOptions;
    }

    public void setMapMapping(MapMappingOptions mapMappingOptions) {
        this.mapMapping = mapMappingOptions;
    }

    public void setBeanMapping(BeanMappingOptions beanMappingOptions) {
        this.beanMapping = beanMappingOptions;
    }

    public void setEnumMappingOptions(EnumMappingOptions enumMappingOptions) {
        this.enumMappingOptions = enumMappingOptions;
    }

    public void setValueMappings(List<ValueMappingOptions> list) {
        this.valueMappings = list;
    }

    public MapperOptions getMapper() {
        return this.mapper;
    }

    public boolean isFullyInitialized() {
        return this.fullyInitialized;
    }

    public void markAsFullyInitialized() {
        this.fullyInitialized = true;
    }

    public void applyInheritedOptions(SourceMethod sourceMethod, boolean z) {
        EnumMappingOptions enumMappingOptions;
        MappingMethodOptions options = sourceMethod.getOptions();
        if (options != null) {
            if (!getIterableMapping().hasAnnotation() && options.getIterableMapping().hasAnnotation()) {
                setIterableMapping(options.getIterableMapping());
            }
            if (!getMapMapping().hasAnnotation() && options.getMapMapping().hasAnnotation()) {
                setMapMapping(options.getMapMapping());
            }
            if (!getBeanMapping().hasAnnotation() && options.getBeanMapping().hasAnnotation()) {
                setBeanMapping(BeanMappingOptions.forInheritance(options.getBeanMapping()));
            }
            if (!getEnumMappingOptions().hasAnnotation() && options.getEnumMappingOptions().hasAnnotation()) {
                if (z) {
                    enumMappingOptions = options.getEnumMappingOptions().inverse();
                } else {
                    enumMappingOptions = options.getEnumMappingOptions();
                }
                setEnumMappingOptions(enumMappingOptions);
            }
            if (getValueMappings() == null) {
                if (options.getValueMappings() != null) {
                    setValueMappings(options.getValueMappings());
                } else {
                    setValueMappings(Collections.emptyList());
                }
            } else if (options.getValueMappings() != null) {
                for (ValueMappingOptions valueMappingOptionsInverse : options.getValueMappings()) {
                    if (z) {
                        valueMappingOptionsInverse = valueMappingOptionsInverse.inverse();
                    }
                    if (valueMappingOptionsInverse != null && !getValueMappings().contains(valueMappingOptionsInverse)) {
                        getValueMappings().add(valueMappingOptionsInverse);
                    }
                }
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (MappingOptions mappingOptions : options.getMappings()) {
                if (z) {
                    if (mappingOptions.canInverse()) {
                        linkedHashSet.add(mappingOptions.copyForInverseInheritance(sourceMethod, getBeanMapping()));
                    }
                } else {
                    linkedHashSet.add(mappingOptions.copyForForwardInheritance(sourceMethod, getBeanMapping()));
                }
            }
            addAllNonRedefined(linkedHashSet);
            filterNestedTargetIgnores(this.mappings);
        }
    }

    private void addAllNonRedefined(Set<MappingOptions> set) {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (MappingOptions mappingOptions : this.mappings) {
            if (mappingOptions.getSourceName() != null) {
                hashSet.add(mappingOptions.getSourceName());
            }
            if (mappingOptions.getTargetName() != null) {
                hashSet2.add(mappingOptions.getTargetName());
            }
        }
        for (MappingOptions mappingOptions2 : set) {
            if (mappingOptions2.isIgnored() || (!isRedefined(hashSet, mappingOptions2.getSourceName()) && !isRedefined(hashSet2, mappingOptions2.getTargetName()))) {
                this.mappings.add(mappingOptions2);
            }
        }
    }

    private boolean isRedefined(Set<String> set, String str) {
        if (str == null) {
            return false;
        }
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            if (elementsAreContainedIn(str, it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean elementsAreContainedIn(String str, String str2) {
        return str2 != null && str.startsWith(str2) && str.length() > str2.length() && '.' == str.charAt(str2.length());
    }

    public void applyIgnoreAll(SourceMethod sourceMethod, TypeFactory typeFactory, FormattingMessager formattingMessager) {
        CollectionMappingStrategyGem collectionMappingStrategy = sourceMethod.getOptions().getMapper().getCollectionMappingStrategy();
        Type resultType = sourceMethod.getResultType();
        if (!sourceMethod.isUpdateMethod()) {
            resultType = typeFactory.effectiveResultTypeFor(resultType, sourceMethod.getOptions().getBeanMapping().getBuilder());
        }
        Map<String, Accessor> propertyWriteAccessors = resultType.getPropertyWriteAccessors(collectionMappingStrategy);
        Iterator<MappingOptions> it = this.mappings.iterator();
        while (it.hasNext()) {
            String firstTargetPropertyName = getFirstTargetPropertyName(it.next());
            if (!".".equals(firstTargetPropertyName)) {
                propertyWriteAccessors.remove(firstTargetPropertyName);
            } else {
                formattingMessager.printMessage(sourceMethod.getExecutable(), getBeanMapping().getMirror(), Message.BEANMAPPING_IGNORE_BY_DEFAULT_WITH_MAPPING_TARGET_THIS, new Object[0]);
                return;
            }
        }
        Iterator<String> it2 = propertyWriteAccessors.keySet().iterator();
        while (it2.hasNext()) {
            this.mappings.add(MappingOptions.forIgnore(it2.next()));
        }
    }

    private void filterNestedTargetIgnores(Set<MappingOptions> set) {
        final Set<String> mappingTargetNamesBy = MappingOptions.getMappingTargetNamesBy(new Predicate() { // from class: org.mapstruct.ap.internal.model.source.MappingMethodOptions$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((MappingOptions) obj).isIgnored();
            }
        }, set);
        set.removeIf(new Predicate() { // from class: org.mapstruct.ap.internal.model.source.MappingMethodOptions$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2370xc5c98912(mappingTargetNamesBy, (MappingOptions) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: isToBeIgnored, reason: merged with bridge method [inline-methods] */
    public boolean m2370xc5c98912(Set<String> set, MappingOptions mappingOptions) {
        String[] propertyEntries = getPropertyEntries(mappingOptions);
        return propertyEntries.length > 1 && set.contains(propertyEntries[0]);
    }

    private String[] getPropertyEntries(MappingOptions mappingOptions) {
        return mappingOptions.getTargetName().split("\\.");
    }

    private String getFirstTargetPropertyName(MappingOptions mappingOptions) {
        String targetName = mappingOptions.getTargetName();
        return ".".equals(targetName) ? targetName : getPropertyEntries(mappingOptions)[0];
    }
}
