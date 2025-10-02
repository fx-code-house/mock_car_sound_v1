package org.mapstruct.ap.shaded.freemarker.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities;

/* loaded from: classes3.dex */
public class FileTemplateLoader implements TemplateLoader {
    private static final boolean SEP_IS_SLASH;
    public final File baseDir;
    private final String canonicalPath;

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public void closeTemplateSource(Object obj) {
    }

    static {
        SEP_IS_SLASH = File.separatorChar == '/';
    }

    public FileTemplateLoader() throws IOException {
        this(new File(SecurityUtilities.getSystemProperty("user.dir")));
    }

    public FileTemplateLoader(File file) throws IOException {
        this(file, false);
    }

    public FileTemplateLoader(final File file, final boolean z) throws IOException {
        try {
            Object[] objArr = (Object[]) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.mapstruct.ap.shaded.freemarker.cache.FileTemplateLoader.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    if (!file.exists()) {
                        throw new FileNotFoundException(new StringBuffer().append(file).append(" does not exist.").toString());
                    }
                    if (!file.isDirectory()) {
                        throw new IOException(new StringBuffer().append(file).append(" is not a directory.").toString());
                    }
                    Object[] objArr2 = new Object[2];
                    if (z) {
                        objArr2[0] = file;
                        objArr2[1] = null;
                    } else {
                        File canonicalFile = file.getCanonicalFile();
                        objArr2[0] = canonicalFile;
                        String path = canonicalFile.getPath();
                        if (!path.endsWith(File.separator)) {
                            path = new StringBuffer().append(path).append(File.separatorChar).toString();
                        }
                        objArr2[1] = path;
                    }
                    return objArr2;
                }
            });
            this.baseDir = (File) objArr[0];
            this.canonicalPath = (String) objArr[1];
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public Object findTemplateSource(final String str) throws IOException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.mapstruct.ap.shaded.freemarker.cache.FileTemplateLoader.2
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    File file = new File(FileTemplateLoader.this.baseDir, FileTemplateLoader.SEP_IS_SLASH ? str : str.replace('/', File.separatorChar));
                    if (!file.isFile()) {
                        return null;
                    }
                    if (FileTemplateLoader.this.canonicalPath != null) {
                        String canonicalPath = file.getCanonicalPath();
                        if (!canonicalPath.startsWith(FileTemplateLoader.this.canonicalPath)) {
                            throw new SecurityException(new StringBuffer().append(file.getAbsolutePath()).append(" resolves to ").append(canonicalPath).append(" which  doesn't start with ").append(FileTemplateLoader.this.canonicalPath).toString());
                        }
                    }
                    return file;
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public long getLastModified(final Object obj) {
        return ((Long) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.mapstruct.ap.shaded.freemarker.cache.FileTemplateLoader.3
            @Override // java.security.PrivilegedAction
            public Object run() {
                return new Long(((File) obj).lastModified());
            }
        })).longValue();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public Reader getReader(final Object obj, final String str) throws IOException {
        try {
            return (Reader) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.mapstruct.ap.shaded.freemarker.cache.FileTemplateLoader.4
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    if (!(obj instanceof File)) {
                        throw new IllegalArgumentException(new StringBuffer("templateSource wasn't a File, but a: ").append(obj.getClass().getName()).toString());
                    }
                    return new InputStreamReader(new FileInputStream((File) obj), str);
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    public File getBaseDirectory() {
        return this.baseDir;
    }

    public String toString() {
        return new StringBuffer("FileTemplateLoader(baseDir=\"").append(this.baseDir).append("\", canonicalPath=\"").append(this.canonicalPath).append("\")").toString();
    }
}
