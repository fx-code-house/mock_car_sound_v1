package org.mapstruct.ap.internal.model;

import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInFieldReference;

/* loaded from: classes3.dex */
public class SupportingField extends Field {
    private final SupportingMappingMethod definingMethod;
    private final String templateName;

    public SupportingField(SupportingMappingMethod supportingMappingMethod, BuiltInFieldReference builtInFieldReference, String str) {
        super(builtInFieldReference.getType(), str, true);
        this.templateName = getTemplateNameForClass(builtInFieldReference.getClass());
        this.definingMethod = supportingMappingMethod;
    }

    @Override // org.mapstruct.ap.internal.writer.FreeMarkerWritable
    public String getTemplateName() {
        return this.templateName;
    }

    public SupportingMappingMethod getDefiningMethod() {
        return this.definingMethod;
    }

    @Override // org.mapstruct.ap.internal.model.Field
    public int hashCode() {
        String str = this.templateName;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    @Override // org.mapstruct.ap.internal.model.Field
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && Objects.equals(this.templateName, ((SupportingField) obj).templateName);
    }

    public static void addAllFieldsIn(Set<SupportingMappingMethod> set, Set<Field> set2) {
        for (SupportingMappingMethod supportingMappingMethod : set) {
            if (supportingMappingMethod.getSupportingField() != null) {
                set2.add(supportingMappingMethod.getSupportingField());
            }
        }
    }
}
