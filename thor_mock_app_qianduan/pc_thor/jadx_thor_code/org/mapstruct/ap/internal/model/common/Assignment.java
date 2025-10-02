package org.mapstruct.ap.internal.model.common;

import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public interface Assignment {
    String createUniqueVarName(String str);

    Set<Type> getImportTypes();

    String getSourceLocalVarName();

    String getSourceLoopVarName();

    String getSourceParameterName();

    String getSourcePresenceCheckerReference();

    String getSourceReference();

    Type getSourceType();

    List<Type> getThrownTypes();

    AssignmentType getType();

    boolean isCallingUpdateMethod();

    boolean isSourceReferenceParameter();

    void setAssignment(Assignment assignment);

    void setSourceLocalVarName(String str);

    void setSourceLoopVarName(String str);

    public enum AssignmentType {
        DIRECT(true, false),
        TYPE_CONVERTED(false, true),
        MAPPED(false, false),
        MAPPED_TWICE(false, false),
        MAPPED_TYPE_CONVERTED(false, true),
        TYPE_CONVERTED_MAPPED(false, true);

        private final boolean converted;
        private final boolean direct;

        AssignmentType(boolean z, boolean z2) {
            this.direct = z;
            this.converted = z2;
        }

        public boolean isDirect() {
            return this.direct;
        }

        public boolean isConverted() {
            return this.converted;
        }
    }
}
