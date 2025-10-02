package org.mapstruct.ap.internal.model;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInFieldReference;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class SupportingMappingMethod extends MappingMethod {
    private final Set<Type> importTypes;
    private final SupportingConstructorFragment supportingConstructorFragment;
    private final Field supportingField;
    private final String templateName;

    public SupportingMappingMethod(BuiltInMethod builtInMethod, Set<Field> set) {
        super(builtInMethod);
        this.importTypes = builtInMethod.getImportTypes();
        this.templateName = getTemplateNameForClass(builtInMethod.getClass());
        if (builtInMethod.getFieldReference() != null) {
            this.supportingField = getSafeField(builtInMethod.getFieldReference(), set);
        } else {
            this.supportingField = null;
        }
        if (builtInMethod.getConstructorFragment() != null) {
            this.supportingConstructorFragment = new SupportingConstructorFragment(this, builtInMethod.getConstructorFragment());
        } else {
            this.supportingConstructorFragment = null;
        }
    }

    private Field getSafeField(BuiltInFieldReference builtInFieldReference, Set<Field> set) {
        String variableName = builtInFieldReference.getVariableName();
        for (Field field : set) {
            if (field.getType().equals(builtInFieldReference.getType())) {
                return field;
            }
        }
        Iterator<Field> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().getVariableName().equals(builtInFieldReference.getVariableName())) {
                variableName = Strings.getSafeVariableName(variableName, Field.getFieldNames(set));
            }
        }
        return new SupportingField(this, builtInFieldReference, variableName);
    }

    public SupportingMappingMethod(HelperMethod helperMethod) {
        super(helperMethod);
        this.importTypes = helperMethod.getImportTypes();
        this.templateName = getTemplateNameForClass(helperMethod.getClass());
        this.supportingField = null;
        this.supportingConstructorFragment = null;
    }

    @Override // org.mapstruct.ap.internal.writer.FreeMarkerWritable
    public String getTemplateName() {
        return this.templateName;
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return this.importTypes;
    }

    public Type findType(String str) {
        Iterator<Type> it = this.importTypes.iterator();
        while (it.hasNext()) {
            Type next = it.next();
            if (next.getFullyQualifiedName().contentEquals(str) || next.getName().contentEquals(str)) {
                return next;
            }
        }
        throw new IllegalArgumentException("No type for given name '" + str + "' found in 'importTypes'.");
    }

    public Field getSupportingField() {
        return this.supportingField;
    }

    public SupportingConstructorFragment getSupportingConstructorFragment() {
        return this.supportingConstructorFragment;
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod
    public int hashCode() {
        String str = this.templateName;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && Objects.equals(this.templateName, ((SupportingMappingMethod) obj).templateName);
    }
}
