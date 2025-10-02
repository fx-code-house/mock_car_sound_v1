package org.mapstruct.ap.internal.option;

import org.mapstruct.ap.internal.gem.ReportingPolicyGem;

/* loaded from: classes3.dex */
public class Options {
    private final boolean alwaysGenerateSpi;
    private final String defaultComponentModel;
    private final String defaultInjectionStrategy;
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final ReportingPolicyGem unmappedTargetPolicy;
    private final boolean verbose;

    public Options(boolean z, boolean z2, ReportingPolicyGem reportingPolicyGem, String str, String str2, boolean z3, boolean z4) {
        this.suppressGeneratorTimestamp = z;
        this.suppressGeneratorVersionComment = z2;
        this.unmappedTargetPolicy = reportingPolicyGem;
        this.defaultComponentModel = str;
        this.defaultInjectionStrategy = str2;
        this.alwaysGenerateSpi = z3;
        this.verbose = z4;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return this.suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return this.suppressGeneratorVersionComment;
    }

    public ReportingPolicyGem getUnmappedTargetPolicy() {
        return this.unmappedTargetPolicy;
    }

    public String getDefaultComponentModel() {
        return this.defaultComponentModel;
    }

    public String getDefaultInjectionStrategy() {
        return this.defaultInjectionStrategy;
    }

    public boolean isAlwaysGenerateSpi() {
        return this.alwaysGenerateSpi;
    }

    public boolean isVerbose() {
        return this.verbose;
    }
}
