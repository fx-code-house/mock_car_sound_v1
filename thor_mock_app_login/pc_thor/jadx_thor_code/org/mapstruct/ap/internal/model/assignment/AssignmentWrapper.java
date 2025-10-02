package org.mapstruct.ap.internal.model.assignment;

import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public abstract class AssignmentWrapper extends ModelElement implements Assignment {
    private final Assignment decoratedAssignment;
    protected final boolean fieldAssignment;

    public AssignmentWrapper(Assignment assignment, boolean z) {
        this.decoratedAssignment = assignment;
        this.fieldAssignment = z;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return this.decoratedAssignment.getImportTypes();
    }

    public List<Type> getThrownTypes() {
        return this.decoratedAssignment.getThrownTypes();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setAssignment(Assignment assignment) {
        throw new UnsupportedOperationException("deliberately not implemented");
    }

    public Assignment getAssignment() {
        return this.decoratedAssignment;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceReference() {
        return this.decoratedAssignment.getSourceReference();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isSourceReferenceParameter() {
        return this.decoratedAssignment.isSourceReferenceParameter();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourcePresenceCheckerReference() {
        return this.decoratedAssignment.getSourcePresenceCheckerReference();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Type getSourceType() {
        return this.decoratedAssignment.getSourceType();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLocalVarName() {
        return this.decoratedAssignment.getSourceLocalVarName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLocalVarName(String str) {
        this.decoratedAssignment.setSourceLocalVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLoopVarName() {
        return this.decoratedAssignment.getSourceLoopVarName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLoopVarName(String str) {
        this.decoratedAssignment.setSourceLoopVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceParameterName() {
        return this.decoratedAssignment.getSourceParameterName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Assignment.AssignmentType getType() {
        return this.decoratedAssignment.getType();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isCallingUpdateMethod() {
        return this.decoratedAssignment.isCallingUpdateMethod();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String createUniqueVarName(String str) {
        return this.decoratedAssignment.createUniqueVarName(str);
    }

    public boolean isFieldAssignment() {
        return this.fieldAssignment;
    }
}
