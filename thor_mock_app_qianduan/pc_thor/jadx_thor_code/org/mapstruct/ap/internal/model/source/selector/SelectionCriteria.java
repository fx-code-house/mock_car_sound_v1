package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.source.MappingControl;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

/* loaded from: classes3.dex */
public class SelectionCriteria {
    private final boolean allow2Steps;
    private final boolean allowConversion;
    private final boolean allowDirect;
    private final boolean allowMappingMethod;
    private final boolean lifecycleCallbackRequired;
    private final boolean objectFactoryRequired;
    private boolean preferUpdateMapping;
    private final List<String> qualifiedByNames;
    private final List<TypeMirror> qualifiers;
    private final TypeMirror qualifyingResultType;
    private final SourceRHS sourceRHS;
    private final String targetPropertyName;

    public SelectionCriteria(SelectionParameters selectionParameters, MappingControl mappingControl, String str, boolean z, boolean z2, boolean z3) {
        ArrayList arrayList = new ArrayList();
        this.qualifiers = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.qualifiedByNames = arrayList2;
        if (selectionParameters != null) {
            arrayList.addAll(selectionParameters.getQualifiers());
            arrayList2.addAll(selectionParameters.getQualifyingNames());
            this.qualifyingResultType = selectionParameters.getResultType();
            this.sourceRHS = selectionParameters.getSourceRHS();
        } else {
            this.qualifyingResultType = null;
            this.sourceRHS = null;
        }
        if (mappingControl != null) {
            this.allowDirect = mappingControl.allowDirect();
            this.allowConversion = mappingControl.allowTypeConversion();
            this.allowMappingMethod = mappingControl.allowMappingMethod();
            this.allow2Steps = mappingControl.allowBy2Steps();
        } else {
            this.allowDirect = true;
            this.allowConversion = true;
            this.allowMappingMethod = true;
            this.allow2Steps = true;
        }
        this.targetPropertyName = str;
        this.preferUpdateMapping = z;
        this.objectFactoryRequired = z2;
        this.lifecycleCallbackRequired = z3;
    }

    public boolean isObjectFactoryRequired() {
        return this.objectFactoryRequired;
    }

    public boolean isLifecycleCallbackRequired() {
        return this.lifecycleCallbackRequired;
    }

    public List<TypeMirror> getQualifiers() {
        return this.qualifiers;
    }

    public List<String> getQualifiedByNames() {
        return this.qualifiedByNames;
    }

    public String getTargetPropertyName() {
        return this.targetPropertyName;
    }

    public TypeMirror getQualifyingResultType() {
        return this.qualifyingResultType;
    }

    public boolean isPreferUpdateMapping() {
        return this.preferUpdateMapping;
    }

    public SourceRHS getSourceRHS() {
        return this.sourceRHS;
    }

    public void setPreferUpdateMapping(boolean z) {
        this.preferUpdateMapping = z;
    }

    public boolean hasQualfiers() {
        return (this.qualifiedByNames.isEmpty() && this.qualifiers.isEmpty()) ? false : true;
    }

    public boolean isAllowDirect() {
        return this.allowDirect;
    }

    public boolean isAllowConversion() {
        return this.allowConversion;
    }

    public boolean isAllowMappingMethod() {
        return this.allowMappingMethod;
    }

    public boolean isAllow2Steps() {
        return this.allow2Steps;
    }

    public static SelectionCriteria forMappingMethods(SelectionParameters selectionParameters, MappingControl mappingControl, String str, boolean z) {
        return new SelectionCriteria(selectionParameters, mappingControl, str, z, false, false);
    }

    public static SelectionCriteria forFactoryMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria(selectionParameters, null, null, false, true, false);
    }

    public static SelectionCriteria forLifecycleMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria(selectionParameters, null, null, false, false, true);
    }
}
