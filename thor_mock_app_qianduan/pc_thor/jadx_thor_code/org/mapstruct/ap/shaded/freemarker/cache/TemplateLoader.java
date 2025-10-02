package org.mapstruct.ap.shaded.freemarker.cache;

import java.io.IOException;
import java.io.Reader;

/* loaded from: classes3.dex */
public interface TemplateLoader {
    void closeTemplateSource(Object obj) throws IOException;

    Object findTemplateSource(String str) throws IOException;

    long getLastModified(Object obj);

    Reader getReader(Object obj, String str) throws IOException;
}
