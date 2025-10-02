package org.mapstruct.ap.internal.version;

/* loaded from: classes3.dex */
public interface VersionInformation {
    String getCompiler();

    String getMapStructVersion();

    String getRuntimeVendor();

    String getRuntimeVersion();

    boolean isEclipseJDTCompiler();

    boolean isJavacCompiler();

    boolean isSourceVersionAtLeast9();
}
