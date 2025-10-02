package org.mapstruct.ap.internal.model.source;

import java.util.Map;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.internal.gem.EnumMappingGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/* loaded from: classes3.dex */
public class EnumMappingOptions extends DelegatingOptions {
    private final EnumMappingGem enumMapping;
    private final boolean inverse;
    private final boolean valid;

    private EnumMappingOptions(EnumMappingGem enumMappingGem, boolean z, boolean z2, DelegatingOptions delegatingOptions) {
        super(delegatingOptions);
        this.enumMapping = enumMappingGem;
        this.inverse = z;
        this.valid = z2;
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public boolean hasAnnotation() {
        return this.enumMapping != null;
    }

    public boolean isValid() {
        return this.valid;
    }

    public boolean hasNameTransformationStrategy() {
        return hasAnnotation() && Strings.isNotEmpty(getNameTransformationStrategy());
    }

    public String getNameTransformationStrategy() {
        return this.enumMapping.nameTransformationStrategy().getValue();
    }

    public String getNameTransformationConfiguration() {
        return this.enumMapping.configuration().getValue();
    }

    @Override // org.mapstruct.ap.internal.model.source.DelegatingOptions
    public TypeMirror getUnexpectedValueMappingException() {
        EnumMappingGem enumMappingGem = this.enumMapping;
        if (enumMappingGem != null && enumMappingGem.unexpectedValueMappingException().hasValue()) {
            return this.enumMapping.unexpectedValueMappingException().getValue();
        }
        return next().getUnexpectedValueMappingException();
    }

    public boolean isInverse() {
        return this.inverse;
    }

    public EnumMappingOptions inverse() {
        return new EnumMappingOptions(this.enumMapping, true, this.valid, next());
    }

    public static EnumMappingOptions getInstanceOn(ExecutableElement executableElement, MapperOptions mapperOptions, Map<String, EnumTransformationStrategy> map, FormattingMessager formattingMessager) {
        EnumMappingGem enumMappingGemInstanceOn = EnumMappingGem.instanceOn((Element) executableElement);
        if (enumMappingGemInstanceOn == null) {
            return new EnumMappingOptions(null, false, true, mapperOptions);
        }
        if (!isConsistent(enumMappingGemInstanceOn, executableElement, map, formattingMessager)) {
            return new EnumMappingOptions(null, false, false, mapperOptions);
        }
        return new EnumMappingOptions(enumMappingGemInstanceOn, false, true, mapperOptions);
    }

    private static boolean isConsistent(EnumMappingGem enumMappingGem, ExecutableElement executableElement, Map<String, EnumTransformationStrategy> map, FormattingMessager formattingMessager) {
        boolean z;
        String value = enumMappingGem.nameTransformationStrategy().getValue();
        String value2 = enumMappingGem.configuration().getValue();
        boolean z2 = true;
        if (!Strings.isNotEmpty(value) && !Strings.isNotEmpty(value2)) {
            z = false;
        } else {
            if (!map.containsKey(value)) {
                formattingMessager.printMessage(executableElement, enumMappingGem.mirror(), enumMappingGem.nameTransformationStrategy().getAnnotationValue(), Message.ENUMMAPPING_INCORRECT_TRANSFORMATION_STRATEGY, value, Strings.join(map.keySet(), ", "));
                return false;
            }
            if (Strings.isEmpty(value2)) {
                formattingMessager.printMessage(executableElement, enumMappingGem.mirror(), enumMappingGem.configuration().getAnnotationValue(), Message.ENUMMAPPING_MISSING_CONFIGURATION, new Object[0]);
                return false;
            }
            z = true;
        }
        if (!z && !enumMappingGem.unexpectedValueMappingException().hasValue()) {
            z2 = false;
        }
        if (!z2) {
            formattingMessager.printMessage(executableElement, enumMappingGem.mirror(), Message.ENUMMAPPING_NO_ELEMENTS, new Object[0]);
        }
        return z2;
    }
}
