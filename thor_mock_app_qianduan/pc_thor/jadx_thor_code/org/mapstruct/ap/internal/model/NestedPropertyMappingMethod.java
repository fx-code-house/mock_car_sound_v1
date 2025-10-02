package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.beanmapping.PropertyEntry;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.ValueProvider;

/* loaded from: classes3.dex */
public class NestedPropertyMappingMethod extends MappingMethod {
    private final List<SafePropertyEntry> safePropertyEntries;

    public static class Builder {
        private MappingBuilderContext ctx;
        private ForgedMethod method;
        private List<PropertyEntry> propertyEntries;

        public Builder method(ForgedMethod forgedMethod) {
            this.method = forgedMethod;
            return this;
        }

        public Builder propertyEntries(List<PropertyEntry> list) {
            this.propertyEntries = list;
            return this;
        }

        public Builder mappingContext(MappingBuilderContext mappingBuilderContext) {
            this.ctx = mappingBuilderContext;
            return this;
        }

        public NestedPropertyMappingMethod build() {
            ArrayList arrayList = new ArrayList();
            Iterator<Parameter> it = this.method.getSourceParameters().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (PropertyEntry propertyEntry : this.propertyEntries) {
                String safeVariableName = Strings.getSafeVariableName(propertyEntry.getName(), arrayList);
                arrayList3.add(new SafePropertyEntry(propertyEntry, safeVariableName));
                arrayList.add(safeVariableName);
                arrayList2.addAll(this.ctx.getTypeFactory().getThrownTypes(propertyEntry.getReadAccessor()));
            }
            this.method.addThrownTypes(arrayList2);
            return new NestedPropertyMappingMethod(this.method, arrayList3);
        }
    }

    private NestedPropertyMappingMethod(ForgedMethod forgedMethod, List<SafePropertyEntry> list) {
        super(forgedMethod);
        this.safePropertyEntries = list;
    }

    public Parameter getSourceParameter() {
        for (Parameter parameter : getParameters()) {
            if (!parameter.isMappingTarget() && !parameter.isMappingContext()) {
                return parameter;
            }
        }
        throw new IllegalStateException("Method " + this + " has no source parameter.");
    }

    public List<SafePropertyEntry> getPropertyEntries() {
        return this.safePropertyEntries;
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        Iterator<SafePropertyEntry> it = this.safePropertyEntries.iterator();
        while (it.hasNext()) {
            importTypes.add(it.next().getType());
        }
        return importTypes;
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod
    public int hashCode() {
        return 31 + (getReturnType() == null ? 0 : getReturnType().hashCode());
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NestedPropertyMappingMethod nestedPropertyMappingMethod = (NestedPropertyMappingMethod) obj;
        if (!super.equals(obj) || getSourceParameters().size() != nestedPropertyMappingMethod.getSourceParameters().size()) {
            return false;
        }
        for (int i = 0; i < getSourceParameters().size(); i++) {
            if (!getSourceParameters().get(i).getType().equals(nestedPropertyMappingMethod.getSourceParameters().get(i).getType())) {
                return false;
            }
        }
        return this.safePropertyEntries.equals(nestedPropertyMappingMethod.safePropertyEntries);
    }

    public static class SafePropertyEntry {
        private final String presenceCheckerName;
        private final String readAccessorName;
        private final String safeName;
        private final Type type;

        public SafePropertyEntry(PropertyEntry propertyEntry, String str) {
            this.safeName = str;
            this.readAccessorName = ValueProvider.of(propertyEntry.getReadAccessor()).getValue();
            if (propertyEntry.getPresenceChecker() != null) {
                this.presenceCheckerName = propertyEntry.getPresenceChecker().getSimpleName();
            } else {
                this.presenceCheckerName = null;
            }
            this.type = propertyEntry.getType();
        }

        public String getName() {
            return this.safeName;
        }

        public String getAccessorName() {
            return this.readAccessorName;
        }

        public String getPresenceCheckerName() {
            return this.presenceCheckerName;
        }

        public Type getType() {
            return this.type;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SafePropertyEntry)) {
                return false;
            }
            SafePropertyEntry safePropertyEntry = (SafePropertyEntry) obj;
            return Objects.equals(this.readAccessorName, safePropertyEntry.readAccessorName) && Objects.equals(this.presenceCheckerName, safePropertyEntry.presenceCheckerName) && Objects.equals(this.type, safePropertyEntry.type);
        }

        public int hashCode() {
            String str = this.readAccessorName;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.presenceCheckerName;
            int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            Type type = this.type;
            return iHashCode2 + (type != null ? type.hashCode() : 0);
        }
    }
}
