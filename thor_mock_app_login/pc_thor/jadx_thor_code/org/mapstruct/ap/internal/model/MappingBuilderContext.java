package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Services;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.spi.EnumTransformationStrategy;
import org.mapstruct.ap.spi.MappingExclusionProvider;

/* loaded from: classes3.dex */
public class MappingBuilderContext {
    private static final MappingExclusionProvider SUB_MAPPING_EXCLUSION_PROVIDER = (MappingExclusionProvider) Services.get(MappingExclusionProvider.class, new DefaultMappingExclusionProvider());
    private final AccessorNamingUtils accessorNaming;
    private final Elements elementUtils;
    private final EnumMappingStrategy enumMappingStrategy;
    private final Map<String, EnumTransformationStrategy> enumTransformationStrategies;
    private final List<MapperReference> mapperReferences;
    private final TypeElement mapperTypeElement;
    private final MappingResolver mappingResolver;
    private final FormattingMessager messager;
    private final Options options;
    private final List<SourceMethod> sourceModel;
    private final TypeFactory typeFactory;
    private final Types typeUtils;
    private final List<MappingMethod> mappingsToGenerate = new ArrayList();
    private final Map<ForgedMethod, ForgedMethod> forgedMethodsUnderCreation = new HashMap();

    public interface MappingResolver {
        Assignment getTargetAssignment(Method method, ForgedMethodHistory forgedMethodHistory, Type type, FormattingParameters formattingParameters, SelectionCriteria selectionCriteria, SourceRHS sourceRHS, AnnotationMirror annotationMirror, Supplier<Assignment> supplier);

        Set<SupportingMappingMethod> getUsedSupportedMappings();
    }

    public MappingBuilderContext(TypeFactory typeFactory, Elements elements, Types types, FormattingMessager formattingMessager, AccessorNamingUtils accessorNamingUtils, EnumMappingStrategy enumMappingStrategy, Map<String, EnumTransformationStrategy> map, Options options, MappingResolver mappingResolver, TypeElement typeElement, List<SourceMethod> list, List<MapperReference> list2) {
        this.typeFactory = typeFactory;
        this.elementUtils = elements;
        this.typeUtils = types;
        this.messager = formattingMessager;
        this.accessorNaming = accessorNamingUtils;
        this.enumMappingStrategy = enumMappingStrategy;
        this.enumTransformationStrategies = map;
        this.options = options;
        this.mappingResolver = mappingResolver;
        this.mapperTypeElement = typeElement;
        this.sourceModel = list;
        this.mapperReferences = list2;
    }

    public Map<ForgedMethod, ForgedMethod> getForgedMethodsUnderCreation() {
        return this.forgedMethodsUnderCreation;
    }

    public TypeElement getMapperTypeElement() {
        return this.mapperTypeElement;
    }

    public List<SourceMethod> getSourceModel() {
        return this.sourceModel;
    }

    public List<MapperReference> getMapperReferences() {
        return this.mapperReferences;
    }

    public TypeFactory getTypeFactory() {
        return this.typeFactory;
    }

    public Elements getElementUtils() {
        return this.elementUtils;
    }

    public Types getTypeUtils() {
        return this.typeUtils;
    }

    public FormattingMessager getMessager() {
        return this.messager;
    }

    public AccessorNamingUtils getAccessorNaming() {
        return this.accessorNaming;
    }

    public EnumMappingStrategy getEnumMappingStrategy() {
        return this.enumMappingStrategy;
    }

    public Map<String, EnumTransformationStrategy> getEnumTransformationStrategies() {
        return this.enumTransformationStrategies;
    }

    public Options getOptions() {
        return this.options;
    }

    public MappingResolver getMappingResolver() {
        return this.mappingResolver;
    }

    public List<MappingMethod> getMappingsToGenerate() {
        return this.mappingsToGenerate;
    }

    public List<String> getReservedNames() {
        HashSet hashSet = new HashSet();
        Iterator<MappingMethod> it = this.mappingsToGenerate.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getName());
        }
        for (SourceMethod sourceMethod : this.sourceModel) {
            if (sourceMethod.isAbstract()) {
                hashSet.add(sourceMethod.getName());
            }
        }
        return new ArrayList(hashSet);
    }

    public MappingMethod getExistingMappingMethod(MappingMethod mappingMethod) {
        for (MappingMethod mappingMethod2 : this.mappingsToGenerate) {
            if (mappingMethod.equals(mappingMethod2)) {
                return mappingMethod2;
            }
        }
        return null;
    }

    public Set<SupportingMappingMethod> getUsedSupportedMappings() {
        return this.mappingResolver.getUsedSupportedMappings();
    }

    public boolean canGenerateAutoSubMappingBetween(Type type, Type type2) {
        return canGenerateAutoSubMappingFor(type) && canGenerateAutoSubMappingFor(type2);
    }

    private boolean canGenerateAutoSubMappingFor(Type type) {
        return (type.getTypeElement() == null || SUB_MAPPING_EXCLUSION_PROVIDER.isExcluded(type.getTypeElement())) ? false : true;
    }

    public boolean isErroneous() {
        return this.messager.isErroneous();
    }
}
