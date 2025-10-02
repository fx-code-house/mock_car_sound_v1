package org.mapstruct.ap.shaded.freemarker.cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

/* loaded from: classes3.dex */
class URLTemplateSource {
    private URLConnection conn;
    private InputStream inputStream;
    private final URL url;
    private Boolean useCaches;

    URLTemplateSource(URL url, Boolean bool) throws IOException {
        this.url = url;
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        this.conn = uRLConnectionOpenConnection;
        this.useCaches = bool;
        if (bool != null) {
            uRLConnectionOpenConnection.setUseCaches(bool.booleanValue());
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof URLTemplateSource) {
            return this.url.equals(((URLTemplateSource) obj).url);
        }
        return false;
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    public String toString() {
        return this.url.toString();
    }

    long lastModified() throws IOException {
        URLConnection uRLConnection = this.conn;
        if (uRLConnection instanceof JarURLConnection) {
            URL jarFileURL = ((JarURLConnection) uRLConnection).getJarFileURL();
            if (jarFileURL.getProtocol().equals("file")) {
                return new File(jarFileURL.getFile()).lastModified();
            }
            URLConnection uRLConnectionOpenConnection = null;
            try {
                uRLConnectionOpenConnection = jarFileURL.openConnection();
                long lastModified = uRLConnectionOpenConnection.getLastModified();
                if (uRLConnectionOpenConnection != null) {
                    try {
                        uRLConnectionOpenConnection.getInputStream().close();
                    } catch (IOException unused) {
                    }
                }
                return lastModified;
            } catch (IOException unused2) {
                if (uRLConnectionOpenConnection != null) {
                    try {
                        uRLConnectionOpenConnection.getInputStream().close();
                    } catch (IOException unused3) {
                    }
                }
                return -1L;
            } catch (Throwable th) {
                if (uRLConnectionOpenConnection != null) {
                    try {
                        uRLConnectionOpenConnection.getInputStream().close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        }
        long lastModified2 = uRLConnection.getLastModified();
        return (lastModified2 == -1 && this.url.getProtocol().equals("file")) ? new File(this.url.getFile()).lastModified() : lastModified2;
    }

    InputStream getInputStream() throws IOException {
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
            this.conn = this.url.openConnection();
        }
        InputStream inputStream2 = this.conn.getInputStream();
        this.inputStream = inputStream2;
        return inputStream2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void close() throws IOException {
        try {
            InputStream inputStream = this.inputStream;
            if (inputStream != null) {
                inputStream.close();
            } else {
                this.conn.getInputStream().close();
            }
        } finally {
            this.inputStream = null;
            this.conn = null;
        }
    }

    Boolean getUseCaches() {
        return this.useCaches;
    }

    void setUseCaches(boolean z) {
        URLConnection uRLConnection = this.conn;
        if (uRLConnection != null) {
            uRLConnection.setUseCaches(z);
            this.useCaches = Boolean.valueOf(z);
        }
    }
}
