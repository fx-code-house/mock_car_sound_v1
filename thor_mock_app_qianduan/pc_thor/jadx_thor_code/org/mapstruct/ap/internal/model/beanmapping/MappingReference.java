package org.mapstruct.ap.internal.model.beanmapping;

import java.util.Objects;
import org.mapstruct.ap.internal.model.source.MappingOptions;

/* loaded from: classes3.dex */
public class MappingReference {
    private MappingOptions mapping;
    private SourceReference sourceReference;
    private TargetReference targetReference;

    public MappingReference(MappingOptions mappingOptions, TargetReference targetReference, SourceReference sourceReference) {
        this.mapping = mappingOptions;
        this.targetReference = targetReference;
        this.sourceReference = sourceReference;
    }

    public MappingOptions getMapping() {
        return this.mapping;
    }

    public SourceReference getSourceReference() {
        return this.sourceReference;
    }

    public void setSourceReference(SourceReference sourceReference) {
        this.sourceReference = sourceReference;
    }

    public TargetReference getTargetReference() {
        return this.targetReference;
    }

    public MappingReference popTargetReference() {
        TargetReference targetReferencePop;
        TargetReference targetReference = this.targetReference;
        if (targetReference == null || (targetReferencePop = targetReference.pop()) == null) {
            return null;
        }
        return new MappingReference(this.mapping, targetReferencePop, this.sourceReference);
    }

    public MappingReference popSourceReference() {
        SourceReference sourceReferencePop;
        SourceReference sourceReference = this.sourceReference;
        if (sourceReference == null || (sourceReferencePop = sourceReference.pop()) == null) {
            return null;
        }
        return new MappingReference(this.mapping, this.targetReference, sourceReferencePop);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mapping.equals(((MappingReference) obj).mapping);
    }

    public int hashCode() {
        return Objects.hash(this.mapping);
    }

    public boolean isValid() {
        SourceReference sourceReference = this.sourceReference;
        return sourceReference == null || sourceReference.isValid();
    }

    public String toString() {
        String string = this.targetReference.toString();
        SourceReference sourceReference = this.sourceReference;
        return "MappingReference {\n    sourceReference='" + (sourceReference != null ? sourceReference.toString() : "null") + "',\n    targetReference='" + string + "',\n}";
    }
}
