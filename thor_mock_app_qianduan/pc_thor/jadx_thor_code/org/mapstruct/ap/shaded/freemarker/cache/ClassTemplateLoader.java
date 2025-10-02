package org.mapstruct.ap.shaded.freemarker.cache;

import java.net.URL;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class ClassTemplateLoader extends URLTemplateLoader {
    private Class baseClass;
    private String packagePath;

    public ClassTemplateLoader() {
        setFields(getClass(), "/");
    }

    public ClassTemplateLoader(Class cls) {
        setFields(cls, "");
    }

    public ClassTemplateLoader(Class cls, String str) {
        setFields(cls, str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.URLTemplateLoader
    protected URL getURL(String str) {
        String string = new StringBuffer().append(this.packagePath).append(str).toString();
        if (!this.packagePath.equals("/") || isSchemeless(string)) {
            return this.baseClass.getResource(string);
        }
        return null;
    }

    private static boolean isSchemeless(String str) {
        int length = str.length();
        for (int i = (length <= 0 || str.charAt(0) != '/') ? 0 : 1; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/') {
                return true;
            }
            if (cCharAt == ':') {
                return false;
            }
        }
        return true;
    }

    private void setFields(Class cls, String str) {
        if (cls == null) {
            throw new IllegalArgumentException("baseClass == null");
        }
        if (str == null) {
            throw new IllegalArgumentException("path == null");
        }
        this.baseClass = cls;
        this.packagePath = canonicalizePrefix(str);
    }

    public String toString() {
        return new StringBuffer("ClassTemplateLoader(baseClass=").append(this.baseClass.getName()).append(", packagePath=").append(StringUtil.jQuote(this.packagePath)).append(")").toString();
    }
}
