package org.mapstruct.ap.internal.model.source;

import java.util.Optional;
import java.util.function.Function;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.MapMappingGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/* loaded from: classes3.dex */
public class MapMappingOptions extends DelegatingOptions {
    private final FormattingParameters keyFormattingParameters;
    private final SelectionParameters keySelectionParameters;
    private final MapMappingGem mapMapping;
    private final FormattingParameters valueFormattingParameters;
    private final SelectionParameters valueSelectionParameters;

    public static MapMappingOptions fromGem(MapMappingGem mapMappingGem, MapperOptions mapperOptions, ExecutableElement executableElement, FormattingMessager formattingMessager, Types types) {
        if (mapMappingGem == null || !isConsistent(mapMappingGem, executableElement, formattingMessager)) {
            return new MapMappingOptions(null, null, null, null, null, mapperOptions);
        }
        return new MapMappingOptions(new FormattingParameters(mapMappingGem.keyDateFormat().get(), mapMappingGem.keyNumberFormat().get(), mapMappingGem.mirror(), mapMappingGem.keyDateFormat().getAnnotationValue(), executableElement), new SelectionParameters(mapMappingGem.keyQualifiedBy().get(), mapMappingGem.keyQualifiedByName().get(), mapMappingGem.keyTargetType().getValue(), types), new FormattingParameters(mapMappingGem.valueDateFormat().get(), mapMappingGem.valueNumberFormat().get(), mapMappingGem.mirror(), mapMappingGem.valueDateFormat().getAnnotationValue(), executableElement), new SelectionParameters(mapMappingGem.valueQualifiedBy().get(), mapMappingGem.valueQualifiedByName().get(), mapMappingGem.valueTargetType().getValue(), types), mapMappingGem, mapperOptions);
    }

    private static boolean isConsistent(MapMappingGem mapMappingGem, ExecutableElement executableElement, FormattingMessager formattingMessager) {
        if (mapMappingGem.keyDateFormat().hasValue() || mapMappingGem.keyNumberFormat().hasValue() || mapMappingGem.keyQualifiedBy().hasValue() || mapMappingGem.keyQualifiedByName().hasValue() || mapMappingGem.valueDateFormat().hasValue() || mapMappingGem.valueNumberFormat().hasValue() || mapMappingGem.valueQualifiedBy().hasValue() || mapMappingGem.valueQualifiedByName().hasValue() || mapMappingGem.keyTargetType().hasValue() || mapMappingGem.valueTargetType().hasValue() || mapMappingGem.nullValueMappingStrategy().hasValue()) {
            return true;
        }
        formattingMessager.printMessage(executableElement, Message.MAPMAPPING_NO_ELEMENTS, new Object[0]);
        return false;
    }

    private MapMappingOptions(FormattingParameters formattingParameters, SelectionParameters selectionParameters, FormattingParameters formattingParameters2, SelectionParameters selectionParameters2, MapMappingGem mapMappingGem, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.keyFormattingParameters = formattingParameters;
        this.keySelectionParameters = selectionParameters;
        this.valueFormattingParameters = formattingParameters2;
        this.valueSelectionParameters = selectionParameters2;
        this.mapMapping = mapMappingGem;
    }

    public FormattingParameters getKeyFormattingParameters() {
        return this.keyFormattingParameters;
    }

    public SelectionParameters getKeySelectionParameters() {
        return this.keySelectionParameters;
    }

    public FormattingParameters getValueFormattingParameters() {
        return this.valueFormattingParameters;
    }

    public SelectionParameters getValueSelectionParameters() {
        return this.valueSelectionParameters;
    }

    public AnnotationMirror getMirror() {
        return (AnnotationMirror) Optional.ofNullable(this.mapMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MapMappingOptions$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MapMappingGem) obj).mirror();
            }
        }).orElse(null);
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return (NullValueMappingStrategyGem) Optional.ofNullable(this.mapMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MapMappingOptions$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MapMappingGem) obj).nullValueMappingStrategy();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda15()).map(new BeanMappingOptions$$ExternalSyntheticLambda16()).orElse(next().getNullValueMappingStrategy());
    }

    public MappingControl getKeyMappingControl(final Elements elements) {
        return (MappingControl) Optional.ofNullable(this.mapMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MapMappingOptions$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MapMappingGem) obj).keyMappingControl();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda10()).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MapMappingOptions$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingControl.fromTypeMirror((TypeMirror) obj, elements);
            }
        }).orElse(next().getMappingControl(elements));
    }

    public MappingControl getValueMappingControl(final Elements elements) {
        return (MappingControl) Optional.ofNullable(this.mapMapping).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MapMappingOptions$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MapMappingGem) obj).valueMappingControl();
            }
        }).filter(new BeanMappingOptions$$ExternalSyntheticLambda9()).map(new BeanMappingOptions$$ExternalSyntheticLambda10()).map(new Function() { // from class: org.mapstruct.ap.internal.model.source.MapMappingOptions$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MappingControl.fromTypeMirror((TypeMirror) obj, elements);
            }
        }).orElse(next().getMappingControl(elements));
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return this.mapMapping != null;
    }
}
