package org.mapstruct.ap.internal.gem;

import javax.tools.Diagnostic;

/* loaded from: classes3.dex */
public enum ReportingPolicyGem {
    IGNORE(null, false, false),
    WARN(Diagnostic.Kind.WARNING, true, false),
    ERROR(Diagnostic.Kind.ERROR, true, true);

    private final Diagnostic.Kind diagnosticKind;
    private final boolean failsBuild;
    private final boolean requiresReport;

    ReportingPolicyGem(Diagnostic.Kind kind, boolean z, boolean z2) {
        this.requiresReport = z;
        this.diagnosticKind = kind;
        this.failsBuild = z2;
    }

    public Diagnostic.Kind getDiagnosticKind() {
        return this.diagnosticKind;
    }

    public boolean requiresReport() {
        return this.requiresReport;
    }

    public boolean failsBuild() {
        return this.failsBuild;
    }
}
