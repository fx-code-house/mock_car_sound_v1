package org.mapstruct.ap.internal.processor;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Manifest;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import org.apache.commons.lang3.ClassUtils;
import org.mapstruct.ap.internal.version.VersionInformation;

/* loaded from: classes3.dex */
public class DefaultVersionInformation implements VersionInformation {
    private static final String COMPILER_NAME_ECLIPSE_JDT = "Eclipse JDT";
    private static final String COMPILER_NAME_JAVAC = "javac";
    private static final String JAVAC_PE_CLASS = "com.sun.tools.javac.processing.JavacProcessingEnvironment";
    private static final String JDT_BATCH_PE_CLASS = "org.eclipse.jdt.internal.compiler.apt.dispatch.BatchProcessingEnvImpl";
    private static final String JDT_IDE_PE_CLASS = "org.eclipse.jdt.internal.apt.pluggable.core.dispatch.IdeBuildProcessingEnvImpl";
    private static final String MAP_STRUCT_VERSION = initMapStructVersion();
    private final String compiler;
    private final boolean eclipseJDT;
    private final boolean javac;
    private final String runtimeVendor;
    private final String runtimeVersion;
    private final boolean sourceVersionAtLeast9;

    DefaultVersionInformation(String str, String str2, String str3, SourceVersion sourceVersion) {
        this.runtimeVersion = str;
        this.runtimeVendor = str2;
        this.compiler = str3;
        this.eclipseJDT = str3.startsWith(COMPILER_NAME_ECLIPSE_JDT);
        this.javac = str3.startsWith(COMPILER_NAME_JAVAC);
        this.sourceVersionAtLeast9 = sourceVersion.compareTo(SourceVersion.RELEASE_6) > 2;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public String getRuntimeVersion() {
        return this.runtimeVersion;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public String getRuntimeVendor() {
        return this.runtimeVendor;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public String getMapStructVersion() {
        return MAP_STRUCT_VERSION;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public String getCompiler() {
        return this.compiler;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public boolean isSourceVersionAtLeast9() {
        return this.sourceVersionAtLeast9;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public boolean isEclipseJDTCompiler() {
        return this.eclipseJDT;
    }

    @Override // org.mapstruct.ap.internal.version.VersionInformation
    public boolean isJavacCompiler() {
        return this.javac;
    }

    static DefaultVersionInformation fromProcessingEnvironment(ProcessingEnvironment processingEnvironment) {
        return new DefaultVersionInformation(System.getProperty("java.version"), System.getProperty("java.vendor"), getCompiler(processingEnvironment), processingEnvironment.getSourceVersion());
    }

    private static String getCompiler(ProcessingEnvironment processingEnvironment) throws IllegalArgumentException {
        String name;
        if (Proxy.isProxyClass(processingEnvironment.getClass())) {
            String string = processingEnvironment.toString();
            if (string.contains(COMPILER_NAME_JAVAC)) {
                name = JAVAC_PE_CLASS;
            } else {
                if (!string.startsWith(JDT_BATCH_PE_CLASS)) {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(processingEnvironment);
                    return "Proxy handler " + invocationHandler.getClass() + " from " + getLibraryName(invocationHandler.getClass(), false);
                }
                name = JDT_BATCH_PE_CLASS;
            }
        } else {
            name = processingEnvironment.getClass().getName();
        }
        if (name.equals(JAVAC_PE_CLASS)) {
            return COMPILER_NAME_JAVAC;
        }
        if (name.equals(JDT_IDE_PE_CLASS)) {
            return "Eclipse JDT (IDE) " + getLibraryName(processingEnvironment.getTypeUtils().getClass(), true);
        }
        if (name.equals(JDT_BATCH_PE_CLASS)) {
            return "Eclipse JDT (Batch) " + getLibraryName(processingEnvironment.getClass(), true);
        }
        return processingEnvironment.getClass().getSimpleName() + " from " + getLibraryName(processingEnvironment.getClass(), false);
    }

    private static String getLibraryName(Class<?> cls, boolean z) {
        String value;
        String strAsClassFileName = asClassFileName(cls.getName());
        URL resource = cls.getClassLoader().getResource(strAsClassFileName);
        Manifest manifestOpenManifest = openManifest(strAsClassFileName, resource);
        if (z && manifestOpenManifest != null && (value = manifestOpenManifest.getMainAttributes().getValue("Bundle-Version")) != null) {
            return value;
        }
        if (resource == null) {
            return "";
        }
        if ("jar".equals(resource.getProtocol())) {
            return extractJarFileName(resource.getFile());
        }
        if ("jrt".equals(resource.getProtocol())) {
            return extractJrtModuleName(resource);
        }
        if ("bundleresource".equals(resource.getProtocol()) && manifestOpenManifest != null) {
            return extractBundleName(manifestOpenManifest);
        }
        return resource.toExternalForm();
    }

    private static Manifest openManifest(String str, URL url) {
        if (url == null) {
            return null;
        }
        try {
            return new Manifest(createManifestUrl(str, url).openStream());
        } catch (IOException unused) {
            return null;
        }
    }

    private static String extractBundleName(Manifest manifest) {
        String value = manifest.getMainAttributes().getValue("Bundle-Version");
        String value2 = manifest.getMainAttributes().getValue("Bundle-SymbolicName");
        int iIndexOf = value2.indexOf(59);
        if (iIndexOf > 0) {
            value2 = value2.substring(0, iIndexOf);
        }
        return value2 + "_" + value;
    }

    private static String extractJrtModuleName(URL url) {
        int iIndexOf = url.getFile().indexOf(47, 1);
        if (iIndexOf > 1) {
            return url.getFile().substring(1, iIndexOf);
        }
        return url.toExternalForm();
    }

    private static URL createManifestUrl(String str, URL url) throws MalformedURLException {
        String externalForm = url.toExternalForm();
        return new URL(externalForm.substring(0, externalForm.length() - str.length()) + "META-INF/MANIFEST.MF");
    }

    private static String asClassFileName(String str) {
        return str.replace(ClassUtils.PACKAGE_SEPARATOR_CHAR, '/') + ".class";
    }

    private static String extractJarFileName(String str) {
        String strSubstring;
        int iLastIndexOf;
        int iIndexOf = str.indexOf(33);
        return (iIndexOf <= 0 || (iLastIndexOf = (strSubstring = str.substring(0, iIndexOf)).lastIndexOf(47)) <= 0 || iLastIndexOf >= strSubstring.length()) ? str : strSubstring.substring(iLastIndexOf + 1);
    }

    private static String initMapStructVersion() {
        String value;
        String strAsClassFileName = asClassFileName(DefaultVersionInformation.class.getName());
        Manifest manifestOpenManifest = openManifest(strAsClassFileName, DefaultVersionInformation.class.getClassLoader().getResource(strAsClassFileName));
        return (manifestOpenManifest == null || (value = manifestOpenManifest.getMainAttributes().getValue("Implementation-Version")) == null) ? "" : value;
    }
}
