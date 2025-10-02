package org.mapstruct.ap.internal.model.source;

import java.util.List;
import java.util.Objects;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.internal.gem.ValueMappingGem;
import org.mapstruct.ap.internal.gem.ValueMappingsGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/* loaded from: classes3.dex */
public class ValueMappingOptions {
    private final AnnotationMirror mirror;
    private final String source;
    private final AnnotationValue sourceAnnotationValue;
    private final String target;
    private final AnnotationValue targetAnnotationValue;

    public static void fromMappingsGem(ValueMappingsGem valueMappingsGem, ExecutableElement executableElement, FormattingMessager formattingMessager, List<ValueMappingOptions> list) {
        boolean z = false;
        for (ValueMappingGem valueMappingGem : valueMappingsGem.value().get()) {
            ValueMappingOptions valueMappingOptionsFromMappingGem = fromMappingGem(valueMappingGem);
            if (valueMappingOptionsFromMappingGem != null) {
                if (!list.contains(valueMappingOptionsFromMappingGem)) {
                    list.add(valueMappingOptionsFromMappingGem);
                } else {
                    formattingMessager.printMessage(executableElement, valueMappingGem.mirror(), valueMappingGem.target().getAnnotationValue(), Message.VALUEMAPPING_DUPLICATE_SOURCE, valueMappingGem.source().get());
                }
                if ("<ANY_REMAINING>".equals(valueMappingOptionsFromMappingGem.source) || "<ANY_UNMAPPED>".equals(valueMappingOptionsFromMappingGem.source)) {
                    if (z) {
                        formattingMessager.printMessage(executableElement, valueMappingGem.mirror(), valueMappingGem.target().getAnnotationValue(), Message.VALUEMAPPING_ANY_AREADY_DEFINED, valueMappingGem.source().get());
                    }
                    z = true;
                }
            }
        }
    }

    public static ValueMappingOptions fromMappingGem(ValueMappingGem valueMappingGem) {
        return new ValueMappingOptions(valueMappingGem.source().get(), valueMappingGem.target().get(), valueMappingGem.mirror(), valueMappingGem.source().getAnnotationValue(), valueMappingGem.target().getAnnotationValue());
    }

    private ValueMappingOptions(String str, String str2, AnnotationMirror annotationMirror, AnnotationValue annotationValue, AnnotationValue annotationValue2) {
        this.source = str;
        this.target = str2;
        this.mirror = annotationMirror;
        this.sourceAnnotationValue = annotationValue;
        this.targetAnnotationValue = annotationValue2;
    }

    public String getSource() {
        return this.source;
    }

    public String getTarget() {
        return this.target;
    }

    public AnnotationMirror getMirror() {
        return this.mirror;
    }

    public AnnotationValue getSourceAnnotationValue() {
        return this.sourceAnnotationValue;
    }

    public AnnotationValue getTargetAnnotationValue() {
        return this.targetAnnotationValue;
    }

    public ValueMappingOptions inverse() {
        if ("<ANY_REMAINING>".equals(this.source) || "<ANY_UNMAPPED>".equals(this.source)) {
            return null;
        }
        return new ValueMappingOptions(this.target, this.source, this.mirror, this.sourceAnnotationValue, this.targetAnnotationValue);
    }

    public int hashCode() {
        String str = this.source;
        return 485 + (str != null ? str.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            return Objects.equals(this.source, ((ValueMappingOptions) obj).source);
        }
        return false;
    }
}
