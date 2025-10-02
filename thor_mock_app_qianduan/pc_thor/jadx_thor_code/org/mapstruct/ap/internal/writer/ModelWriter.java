package org.mapstruct.ap.internal.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.tools.FileObject;
import org.mapstruct.ap.internal.writer.Writable;
import org.mapstruct.ap.shaded.freemarker.cache.StrongCacheStorage;
import org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.DefaultObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;

/* loaded from: classes3.dex */
public class ModelWriter {
    private static final Configuration CONFIGURATION;

    static {
        try {
            Logger.selectLoggerLibrary(0);
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            CONFIGURATION = configuration;
            configuration.setTemplateLoader(new SimpleClasspathLoader());
            configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
            configuration.setSharedVariable("includeModel", (TemplateModel) new ModelIncludeDirective(configuration));
            configuration.setCacheStorage(new StrongCacheStorage());
            configuration.setTemplateUpdateDelay(Integer.MAX_VALUE);
            configuration.setLocalizedLookup(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeModel(FileObject fileObject, Writable writable) throws IOException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new IndentationCorrectingWriter(fileObject.openWriter()));
            try {
                HashMap map = new HashMap();
                map.put(Configuration.class, CONFIGURATION);
                writable.write(new DefaultModelElementWriterContext(map), bufferedWriter);
                bufferedWriter.flush();
                bufferedWriter.close();
            } finally {
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    private static final class SimpleClasspathLoader implements TemplateLoader {
        @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
        public void closeTemplateSource(Object obj) throws IOException {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
        public Object findTemplateSource(String str) throws IOException {
            return str;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
        public long getLastModified(Object obj) {
            return 0L;
        }

        private SimpleClasspathLoader() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader
        public Reader getReader(Object obj, String str) throws IOException {
            URL resource = getClass().getClassLoader().getResource(String.valueOf(obj));
            if (resource == null) {
                throw new IllegalStateException(obj + " not found on classpath");
            }
            URLConnection uRLConnectionOpenConnection = resource.openConnection();
            uRLConnectionOpenConnection.setUseCaches(false);
            return new InputStreamReader(uRLConnectionOpenConnection.getInputStream(), StandardCharsets.UTF_8);
        }
    }

    static class DefaultModelElementWriterContext implements Writable.Context {
        private final Map<Class<?>, Object> values;

        DefaultModelElementWriterContext(Map<Class<?>, Object> map) {
            this.values = new HashMap(map);
        }

        @Override // org.mapstruct.ap.internal.writer.Writable.Context
        public <T> T get(Class<T> cls) {
            return (T) this.values.get(cls);
        }
    }
}
