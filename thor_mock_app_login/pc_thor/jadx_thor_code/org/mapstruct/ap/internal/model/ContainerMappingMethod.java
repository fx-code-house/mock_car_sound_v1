package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class ContainerMappingMethod extends NormalTypeMappingMethod {
    private final Assignment elementAssignment;
    private final String index1Name;
    private final String index2Name;
    private IterableCreation iterableCreation;
    private final String loopVariableName;
    private final SelectionParameters selectionParameters;

    public abstract Type getResultElementType();

    ContainerMappingMethod(Method method, Collection<String> collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2, SelectionParameters selectionParameters) {
        super(method, collection, methodReference, z, list, list2);
        this.elementAssignment = assignment;
        this.loopVariableName = str;
        this.selectionParameters = selectionParameters;
        this.index1Name = Strings.getSafeVariableName("i", collection);
        this.index2Name = Strings.getSafeVariableName("j", collection);
    }

    public Parameter getSourceParameter() {
        for (Parameter parameter : getParameters()) {
            if (!parameter.isMappingTarget() && !parameter.isMappingContext()) {
                return parameter;
            }
        }
        throw new IllegalStateException("Method " + this + " has no source parameter.");
    }

    public IterableCreation getIterableCreation() {
        if (this.iterableCreation == null) {
            this.iterableCreation = IterableCreation.create(this, getSourceParameter());
        }
        return this.iterableCreation;
    }

    public Assignment getElementAssignment() {
        return this.elementAssignment;
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        Assignment assignment = this.elementAssignment;
        if (assignment != null) {
            importTypes.addAll(assignment.getImportTypes());
        }
        IterableCreation iterableCreation = this.iterableCreation;
        if (iterableCreation != null) {
            importTypes.addAll(iterableCreation.getImportTypes());
        }
        return importTypes;
    }

    public String getLoopVariableName() {
        return this.loopVariableName;
    }

    public String getIndex1Name() {
        return this.index1Name;
    }

    public String getIndex2Name() {
        return this.index2Name;
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod
    public int hashCode() {
        return super.hashCode();
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && super.equals(obj) && Objects.equals(this.selectionParameters, ((ContainerMappingMethod) obj).selectionParameters);
    }
}
