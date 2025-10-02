package org.mapstruct.ap.shaded.freemarker.cache;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class MultiTemplateLoader implements StatefulTemplateLoader {
    private final Map lastLoaderForName = Collections.synchronizedMap(new HashMap());
    private final TemplateLoader[] loaders;

    private Object modifyForIcI(Object obj) {
        return null;
    }

    public MultiTemplateLoader(TemplateLoader[] templateLoaderArr) {
        this.loaders = (TemplateLoader[]) templateLoaderArr.clone();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public Object findTemplateSource(String str) throws IOException {
        Object objFindTemplateSource;
        TemplateLoader templateLoader = (TemplateLoader) this.lastLoaderForName.get(str);
        if (templateLoader != null && (objFindTemplateSource = templateLoader.findTemplateSource(str)) != null) {
            return new MultiSource(objFindTemplateSource, templateLoader);
        }
        int i = 0;
        while (true) {
            TemplateLoader[] templateLoaderArr = this.loaders;
            if (i < templateLoaderArr.length) {
                TemplateLoader templateLoader2 = templateLoaderArr[i];
                Object objFindTemplateSource2 = templateLoader2.findTemplateSource(str);
                if (objFindTemplateSource2 != null) {
                    this.lastLoaderForName.put(str, templateLoader2);
                    return new MultiSource(objFindTemplateSource2, templateLoader2);
                }
                i++;
            } else {
                this.lastLoaderForName.remove(str);
                return null;
            }
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public long getLastModified(Object obj) {
        return ((MultiSource) obj).getLastModified();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public Reader getReader(Object obj, String str) throws IOException {
        return ((MultiSource) obj).getReader(str);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
    public void closeTemplateSource(Object obj) throws IOException {
        ((MultiSource) obj).close();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.cache.StatefulTemplateLoader
    public void resetState() {
        this.lastLoaderForName.clear();
        int i = 0;
        while (true) {
            TemplateLoader[] templateLoaderArr = this.loaders;
            if (i >= templateLoaderArr.length) {
                return;
            }
            TemplateLoader templateLoader = templateLoaderArr[i];
            if (templateLoader instanceof StatefulTemplateLoader) {
                ((StatefulTemplateLoader) templateLoader).resetState();
            }
            i++;
        }
    }

    static final class MultiSource {
        private final TemplateLoader loader;
        private final Object source;

        MultiSource(Object obj, TemplateLoader templateLoader) {
            this.source = obj;
            this.loader = templateLoader;
        }

        long getLastModified() {
            return this.loader.getLastModified(this.source);
        }

        Reader getReader(String str) throws IOException {
            return this.loader.getReader(this.source, str);
        }

        void close() throws IOException {
            this.loader.closeTemplateSource(this.source);
        }

        Object getWrappedSource() {
            return this.source;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MultiSource)) {
                return false;
            }
            MultiSource multiSource = (MultiSource) obj;
            return multiSource.loader.equals(this.loader) && multiSource.source.equals(this.source);
        }

        public int hashCode() {
            return this.loader.hashCode() + (this.source.hashCode() * 31);
        }

        public String toString() {
            return this.source.toString();
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("MultiTemplateLoader(");
        int i = 0;
        while (i < this.loaders.length) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            int i2 = i + 1;
            stringBuffer.append("loader").append(i2).append(" = ").append(this.loaders[i]);
            i = i2;
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
}
