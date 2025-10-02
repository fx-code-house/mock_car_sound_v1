package org.mapstruct.ap.internal.writer;

import java.io.Writer;

/* loaded from: classes3.dex */
public interface Writable {

    public interface Context {
        <T> T get(Class<T> cls);
    }

    void write(Context context, Writer writer) throws Exception;
}
