package org.mapstruct.ap.shaded.freemarker.cache;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/* loaded from: classes3.dex */
public abstract class URLTemplateLoader implements TemplateLoader {
    private Boolean urlConnectionUsesCaches;

    protected abstract URL getURL(String str);

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public Object findTemplateSource(String str) throws IOException {
        URL url = getURL(str);
        if (url == null) {
            return null;
        }
        return new URLTemplateSource(url, getURLConnectionUsesCaches());
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public long getLastModified(Object obj) {
        return ((URLTemplateSource) obj).lastModified();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public Reader getReader(Object obj, String str) throws IOException {
        return new InputStreamReader(((URLTemplateSource) obj).getInputStream(), str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public void closeTemplateSource(Object obj) throws IOException {
        ((URLTemplateSource) obj).close();
    }

    protected static String canonicalizePrefix(String str) {
        String strReplace = str.replace('\\', '/');
        return (strReplace.length() <= 0 || strReplace.endsWith("/")) ? strReplace : new StringBuffer().append(strReplace).append("/").toString();
    }

    public Boolean getURLConnectionUsesCaches() {
        return this.urlConnectionUsesCaches;
    }

    public void setURLConnectionUsesCaches(Boolean bool) {
        this.urlConnectionUsesCaches = bool;
    }
}
