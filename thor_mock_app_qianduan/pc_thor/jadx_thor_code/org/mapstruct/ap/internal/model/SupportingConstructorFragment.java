package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInConstructorFragment;

/* loaded from: classes3.dex */
public class SupportingConstructorFragment extends ModelElement {
    private final SupportingMappingMethod definingMethod;
    private final String templateName;

    public SupportingConstructorFragment(SupportingMappingMethod supportingMappingMethod, BuiltInConstructorFragment builtInConstructorFragment) {
        this.templateName = getTemplateNameForClass(builtInConstructorFragment.getClass());
        this.definingMethod = supportingMappingMethod;
    }

    @Override // org.mapstruct.ap.internal.writer.FreeMarkerWritable
    public String getTemplateName() {
        return this.templateName;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    public SupportingMappingMethod getDefiningMethod() {
        return this.definingMethod;
    }

    public int hashCode() {
        String str = this.templateName;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && Objects.equals(this.templateName, ((SupportingConstructorFragment) obj).templateName);
    }

    public static void addAllFragmentsIn(Set<SupportingMappingMethod> set, Set<SupportingConstructorFragment> set2) {
        for (SupportingMappingMethod supportingMappingMethod : set) {
            if (supportingMappingMethod.getSupportingConstructorFragment() != null) {
                set2.add(supportingMappingMethod.getSupportingConstructorFragment());
            }
        }
    }
}
