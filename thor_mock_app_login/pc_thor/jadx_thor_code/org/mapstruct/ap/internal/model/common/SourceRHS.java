package org.mapstruct.ap.internal.model.common;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class SourceRHS extends ModelElement implements Assignment {
    private final Set<String> existingVariableNames;
    private final String sourceErrorMessagePart;
    private String sourceLocalVarName;
    private String sourceLoopVarName;
    private final String sourceParameterName;
    private final String sourcePresenceCheckerReference;
    private final String sourceReference;
    private final Type sourceType;
    private boolean useElementAsSourceTypeForMatching;

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isCallingUpdateMethod() {
        return false;
    }

    public SourceRHS(String str, Type type, Set<String> set, String str2) {
        this(str, str, null, type, set, str2);
    }

    public SourceRHS(String str, String str2, String str3, Type type, Set<String> set, String str4) {
        this.useElementAsSourceTypeForMatching = false;
        this.sourceReference = str2;
        this.sourceType = type;
        this.existingVariableNames = set;
        this.sourceErrorMessagePart = str4;
        this.sourcePresenceCheckerReference = str3;
        this.sourceParameterName = str;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceReference() {
        return this.sourceReference;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isSourceReferenceParameter() {
        return this.sourceReference.equals(this.sourceParameterName);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourcePresenceCheckerReference() {
        return this.sourcePresenceCheckerReference;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Type getSourceType() {
        return this.sourceType;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String createUniqueVarName(String str) {
        String safeVariableName = Strings.getSafeVariableName(str, this.existingVariableNames);
        this.existingVariableNames.add(safeVariableName);
        return safeVariableName;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLocalVarName() {
        return this.sourceLocalVarName;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLocalVarName(String str) {
        this.sourceLocalVarName = str;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLoopVarName() {
        return this.sourceLoopVarName;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLoopVarName(String str) {
        this.sourceLoopVarName = str;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public List<Type> getThrownTypes() {
        return Collections.emptyList();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setAssignment(Assignment assignment) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Assignment.AssignmentType getType() {
        return Assignment.AssignmentType.DIRECT;
    }

    public String toString() {
        return this.sourceReference;
    }

    public String getSourceErrorMessagePart() {
        return this.sourceErrorMessagePart;
    }

    public Type getSourceTypeForMatching() {
        if (this.useElementAsSourceTypeForMatching) {
            if (this.sourceType.isCollectionType()) {
                return (Type) org.mapstruct.ap.internal.util.Collections.first(this.sourceType.determineTypeArguments(Collection.class));
            }
            if (this.sourceType.isStreamType()) {
                return (Type) org.mapstruct.ap.internal.util.Collections.first(this.sourceType.determineTypeArguments(Stream.class));
            }
        }
        return this.sourceType;
    }

    public void setUseElementAsSourceTypeForMatching(boolean z) {
        this.useElementAsSourceTypeForMatching = z;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceParameterName() {
        return this.sourceParameterName;
    }
}
