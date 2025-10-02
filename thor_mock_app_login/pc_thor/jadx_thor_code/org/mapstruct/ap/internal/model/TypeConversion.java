package org.mapstruct.ap.internal.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class TypeConversion extends ModelElement implements Assignment {
    private static final String SOURCE_REFERENCE_PATTERN = "<SOURCE>";
    private Assignment assignment;
    private final String closeExpression;
    private final Set<Type> importTypes;
    private final String openExpression;
    private final List<Type> thrownTypes;

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isCallingUpdateMethod() {
        return false;
    }

    public TypeConversion(Set<Type> set, List<Type> list, String str) {
        HashSet hashSet = new HashSet(set);
        this.importTypes = hashSet;
        hashSet.addAll(list);
        this.thrownTypes = list;
        int iIndexOf = str.indexOf(SOURCE_REFERENCE_PATTERN);
        this.openExpression = str.substring(0, iIndexOf);
        this.closeExpression = str.substring(iIndexOf + 8);
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return this.importTypes;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public List<Type> getThrownTypes() {
        return this.thrownTypes;
    }

    public String getOpenExpression() {
        return this.openExpression;
    }

    public String getCloseExpression() {
        return this.closeExpression;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceReference() {
        return this.assignment.getSourceReference();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isSourceReferenceParameter() {
        return this.assignment.isSourceReferenceParameter();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourcePresenceCheckerReference() {
        return this.assignment.getSourcePresenceCheckerReference();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Type getSourceType() {
        return this.assignment.getSourceType();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String createUniqueVarName(String str) {
        return this.assignment.createUniqueVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLocalVarName() {
        return this.assignment.getSourceLocalVarName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLocalVarName(String str) {
        this.assignment.setSourceLocalVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLoopVarName() {
        return this.assignment.getSourceLoopVarName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLoopVarName(String str) {
        this.assignment.setSourceLoopVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceParameterName() {
        return this.assignment.getSourceParameterName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    /* renamed from: org.mapstruct.ap.internal.model.TypeConversion$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType;

        static {
            int[] iArr = new int[Assignment.AssignmentType.values().length];
            $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType = iArr;
            try {
                iArr[Assignment.AssignmentType.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType[Assignment.AssignmentType.MAPPED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Assignment.AssignmentType getType() {
        int i = AnonymousClass1.$SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType[this.assignment.getType().ordinal()];
        if (i == 1) {
            return Assignment.AssignmentType.TYPE_CONVERTED;
        }
        if (i != 2) {
            return null;
        }
        return Assignment.AssignmentType.MAPPED_TYPE_CONVERTED;
    }

    public String toString() {
        return this.openExpression + (getAssignment() != null ? getAssignment().toString() : getSourceReference()) + this.closeExpression;
    }
}
