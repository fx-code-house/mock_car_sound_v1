package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue;

/* loaded from: classes3.dex */
public class BeanMappingOptions extends DelegatingOptions {
    private final BeanMappingGem beanMapping;
    private final SelectionParameters selectionParameters;

    public static BeanMappingOptions forInheritance(BeanMappingOptions beanMappingOptions) {
        return new BeanMappingOptions(SelectionParameters.forInheritance(beanMappingOptions.selectionParameters), beanMappingOptions.beanMapping, beanMappingOptions);
    }

    public static BeanMappingOptions getInstanceOn(BeanMappingGem beanMappingGem, MapperOptions mapperOptions, ExecutableElement executableElement, FormattingMessager formattingMessager, Types types, TypeFactory typeFactory) {
        if (beanMappingGem == null || !isConsistent(beanMappingGem, executableElement, formattingMessager)) {
            return new BeanMappingOptions(null, null, mapperOptions);
        }
        Objects.requireNonNull(executableElement);
        Objects.requireNonNull(formattingMessager);
        Objects.requireNonNull(executableElement);
        Objects.requireNonNull(types);
        Objects.requireNonNull(typeFactory);
        return new BeanMappingOptions(new SelectionParameters(beanMappingGem.qualifiedBy().get(), beanMappingGem.qualifiedByName().get(), beanMappingGem.resultType().getValue(), types), beanMappingGem, mapperOptions);
    }

    private static boolean isConsistent(BeanMappingGem beanMappingGem, ExecutableElement executableElement, FormattingMessager formattingMessager) {
        if (beanMappingGem.resultType().hasValue() || beanMappingGem.qualifiedBy().hasValue() || beanMappingGem.qualifiedByName().hasValue() || beanMappingGem.ignoreUnmappedSourceProperties().hasValue() || beanMappingGem.nullValueCheckStrategy().hasValue() || beanMappingGem.nullValuePropertyMappingStrategy().hasValue() || beanMappingGem.nullValueMappingStrategy().hasValue() || beanMappingGem.ignoreByDefault().hasValue() || beanMappingGem.builder().hasValue()) {
            return true;
        }
        formattingMessager.printMessage(executableElement, Message.BEANMAPPING_NO_ELEMENTS, new Object[0]);
        return false;
    }

    private BeanMappingOptions(SelectionParameters selectionParameters, BeanMappingGem beanMappingGem, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.selectionParameters = selectionParameters;
        this.beanMapping = beanMappingGem;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return (NullValueCheckStrategyGem) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).nullValueCheckStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda3()).orElse(next().getNullValueCheckStrategy());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return (NullValuePropertyMappingStrategyGem) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).nullValuePropertyMappingStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda1()).orElse(next().getNullValuePropertyMappingStrategy());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return (NullValueMappingStrategyGem) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).nullValueMappingStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda16()).orElse(next().getNullValueMappingStrategy());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public BuilderGem getBuilder() {
        return (BuilderGem) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).builder();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (BuilderGem) ((GemValue) obj).getValue();
            }
        }).orElse(next().getBuilder());
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public MappingControl getMappingControl(final Elements elements) {
        return (MappingControl) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).mappingControl();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda10()).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingControl.fromTypeMirror((TypeMirror) obj, elements);
            }
        }).orElse(next().getMappingControl(elements));
    }

    public SelectionParameters getSelectionParameters() {
        return this.selectionParameters;
    }

    public boolean isignoreByDefault() {
        return ((Boolean) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).ignoreByDefault();
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Boolean) ((GemValue) obj).get();
            }
        }).orElse(false)).booleanValue();
    }

    public List<String> getIgnoreUnmappedSourceProperties() {
        return (List) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).ignoreUnmappedSourceProperties();
            }
        }).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (List) ((GemValue) obj).get();
            }
        }).orElse(Collections.emptyList());
    }

    public AnnotationMirror getMirror() {
        return (AnnotationMirror) Optional.ofNullable(this.beanMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.BeanMappingOptions$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BeanMappingGem) obj).mirror();
            }
        }).orElse(null);
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return this.beanMapping != null;
    }
}
