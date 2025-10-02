package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.beanmapping.TargetReference;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.util.FormattingMessager;

/* loaded from: classes3.dex */
public class MappingReferences {
    private static final MappingReferences EMPTY = new MappingReferences(Collections.emptySet(), false);
    private final boolean forForgedMethods;
    private final Set<MappingReference> mappingReferences;
    private final boolean restrictToDefinedMappings;
    private final List<MappingReference> targetThisReferences;

    public static MappingReferences empty() {
        return EMPTY;
    }

    public static MappingReferences forSourceMethod(SourceMethod sourceMethod, Type type, Set<String> set, FormattingMessager formattingMessager, TypeFactory typeFactory) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        for (MappingOptions mappingOptions : sourceMethod.getOptions().getMappings()) {
            MappingReference mappingReference = new MappingReference(mappingOptions, new TargetReference.Builder().mapping(mappingOptions).method(sourceMethod).messager(formattingMessager).typeFactory(typeFactory).targetProperties(set).targetType(type).build(), new SourceReference.BuilderFromMapping().mapping(mappingOptions).method(sourceMethod).messager(formattingMessager).typeFactory(typeFactory).build());
            if (isValidWhenInversed(mappingReference)) {
                if (".".equals(mappingOptions.getTargetName())) {
                    arrayList.add(mappingReference);
                } else {
                    linkedHashSet.add(mappingReference);
                }
            }
        }
        return new MappingReferences((Set<MappingReference>) linkedHashSet, (List<MappingReference>) arrayList, false);
    }

    public MappingReferences(Set<MappingReference> set, List<MappingReference> list, boolean z) {
        this.mappingReferences = set;
        this.restrictToDefinedMappings = z;
        this.forForgedMethods = z;
        this.targetThisReferences = list;
    }

    public MappingReferences(Set<MappingReference> set, boolean z) {
        this.mappingReferences = set;
        this.restrictToDefinedMappings = z;
        this.forForgedMethods = z;
        this.targetThisReferences = Collections.emptyList();
    }

    public MappingReferences(Set<MappingReference> set, boolean z, boolean z2) {
        this.mappingReferences = set;
        this.restrictToDefinedMappings = z;
        this.forForgedMethods = z2;
        this.targetThisReferences = Collections.emptyList();
    }

    public Set<MappingReference> getMappingReferences() {
        return this.mappingReferences;
    }

    public boolean isRestrictToDefinedMappings() {
        return this.restrictToDefinedMappings;
    }

    public boolean isForForgedMethods() {
        return this.forForgedMethods;
    }

    public Set<String> collectNestedDependsOn() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<MappingReference> it = getMappingReferences().iterator();
        while (it.hasNext()) {
            linkedHashSet.addAll(it.next().getMapping().getDependsOn());
        }
        return linkedHashSet;
    }

    public boolean hasNestedTargetReferences() {
        Iterator<MappingReference> it = this.mappingReferences.iterator();
        while (it.hasNext()) {
            if (it.next().getTargetReference().isNested()) {
                return true;
            }
        }
        return false;
    }

    public List<MappingReference> getTargetThisReferences() {
        return this.targetThisReferences;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MappingReferences)) {
            return false;
        }
        MappingReferences mappingReferences = (MappingReferences) obj;
        return this.restrictToDefinedMappings == mappingReferences.restrictToDefinedMappings && this.forForgedMethods == mappingReferences.forForgedMethods && Objects.equals(this.mappingReferences, mappingReferences.mappingReferences) && !Objects.equals(this.targetThisReferences, mappingReferences.targetThisReferences);
    }

    public int hashCode() {
        Set<MappingReference> set = this.mappingReferences;
        if (set != null) {
            return set.hashCode();
        }
        return 0;
    }

    private static boolean isValidWhenInversed(MappingReference mappingReference) {
        MappingOptions mapping = mappingReference.getMapping();
        return mapping.getInheritContext() == null || !mapping.getInheritContext().isReversed() || mappingReference.getSourceReference() == null || mappingReference.getSourceReference().isValid();
    }
}
