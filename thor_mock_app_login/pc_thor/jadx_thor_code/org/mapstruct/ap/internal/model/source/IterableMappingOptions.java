package org.mapstruct.ap.internal.model.source;

import java.util.Optional;
import java.util.function.Function;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.IterableMappingGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/* loaded from: classes3.dex */
public class IterableMappingOptions extends DelegatingOptions {
    private final FormattingParameters formattingParameters;
    private final IterableMappingGem iterableMapping;
    private final SelectionParameters selectionParameters;

    public static IterableMappingOptions fromGem(IterableMappingGem iterableMappingGem, MapperOptions mapperOptions, ExecutableElement executableElement, FormattingMessager formattingMessager, Types types) {
        if (iterableMappingGem == null || !isConsistent(iterableMappingGem, executableElement, formattingMessager)) {
            return new IterableMappingOptions(null, null, null, mapperOptions);
        }
        return new IterableMappingOptions(new FormattingParameters(iterableMappingGem.dateFormat().get(), iterableMappingGem.numberFormat().get(), iterableMappingGem.mirror(), iterableMappingGem.dateFormat().getAnnotationValue(), executableElement), new SelectionParameters(iterableMappingGem.qualifiedBy().get(), iterableMappingGem.qualifiedByName().get(), iterableMappingGem.elementTargetType().getValue(), types), iterableMappingGem, mapperOptions);
    }

    private static boolean isConsistent(IterableMappingGem iterableMappingGem, ExecutableElement executableElement, FormattingMessager formattingMessager) {
        if (iterableMappingGem.dateFormat().hasValue() || iterableMappingGem.numberFormat().hasValue() || iterableMappingGem.qualifiedBy().hasValue() || iterableMappingGem.qualifiedByName().hasValue() || iterableMappingGem.elementTargetType().hasValue() || iterableMappingGem.nullValueMappingStrategy().hasValue()) {
            return true;
        }
        formattingMessager.printMessage(executableElement, Message.ITERABLEMAPPING_NO_ELEMENTS, new Object[0]);
        return false;
    }

    private IterableMappingOptions(FormattingParameters formattingParameters, SelectionParameters selectionParameters, IterableMappingGem iterableMappingGem, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.iterableMapping = iterableMappingGem;
    }

    public SelectionParameters getSelectionParameters() {
        return this.selectionParameters;
    }

    public FormattingParameters getFormattingParameters() {
        return this.formattingParameters;
    }

    public AnnotationMirror getMirror() {
        return (AnnotationMirror) Optional.ofNullable(this.iterableMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.IterableMappingOptions$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IterableMappingGem) obj).mirror();
            }
        }).orElse(null);
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return (NullValueMappingStrategyGem) Optional.ofNullable(this.iterableMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.IterableMappingOptions$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IterableMappingGem) obj).nullValueMappingStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda16()).orElse(next().getNullValueMappingStrategy());
    }

    public MappingControl getElementMappingControl(final Elements elements) {
        return (MappingControl) Optional.ofNullable(this.iterableMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.IterableMappingOptions$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((IterableMappingGem) obj).elementMappingControl();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda10()).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.IterableMappingOptions$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingControl.fromTypeMirror((TypeMirror) obj, elements);
            }
        }).orElse(next().getMappingControl(elements));
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return this.iterableMapping != null;
    }
}
