package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
public class NormalizeNewlines implements TemplateTransformModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
    public Writer getWriter(final Writer writer, Map map) {
        final StringBuffer stringBuffer = new StringBuffer();
        return new Writer() { // from class: org.mapstruct.ap.shaded.freemarker.template.utility.NormalizeNewlines.1
            @Override // java.io.Writer
            public void write(char[] cArr, int i, int i2) {
                stringBuffer.append(cArr, i, i2);
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() throws IOException {
                writer.flush();
            }

            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                StringReader stringReader = new StringReader(stringBuffer.toString());
                StringWriter stringWriter = new StringWriter();
                NormalizeNewlines.this.transform(stringReader, stringWriter);
                writer.write(stringWriter.toString());
            }
        };
    }

    public void transform(Reader reader, Writer writer) throws IOException {
        BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        PrintWriter printWriter = writer instanceof PrintWriter ? (PrintWriter) writer : new PrintWriter(writer);
        String line = bufferedReader.readLine();
        if (line != null && line.length() > 0) {
            printWriter.println(line);
        }
        while (true) {
            String line2 = bufferedReader.readLine();
            if (line2 == null) {
                return;
            } else {
                printWriter.println(line2);
            }
        }
    }
}
