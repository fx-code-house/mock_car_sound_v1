package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
public class XmlEscape implements TemplateTransformModel {
    private static final char[] LT = "&lt;".toCharArray();
    private static final char[] GT = "&gt;".toCharArray();
    private static final char[] AMP = "&amp;".toCharArray();
    private static final char[] QUOT = "&quot;".toCharArray();
    private static final char[] APOS = "&apos;".toCharArray();

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
    public Writer getWriter(final Writer writer, Map map) {
        return new Writer() { // from class: org.mapstruct.ap.shaded.freemarker.template.utility.XmlEscape.1
            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.Writer
            public void write(int i) throws IOException {
                if (i == 34) {
                    writer.write(XmlEscape.QUOT, 0, 6);
                    return;
                }
                if (i == 60) {
                    writer.write(XmlEscape.LT, 0, 4);
                    return;
                }
                if (i == 62) {
                    writer.write(XmlEscape.GT, 0, 4);
                    return;
                }
                if (i == 38) {
                    writer.write(XmlEscape.AMP, 0, 5);
                } else if (i == 39) {
                    writer.write(XmlEscape.APOS, 0, 6);
                } else {
                    writer.write(i);
                }
            }

            @Override // java.io.Writer
            public void write(char[] cArr, int i, int i2) throws IOException {
                int i3 = i2 + i;
                int i4 = i;
                while (i < i3) {
                    char c = cArr[i];
                    if (c == '\"') {
                        writer.write(cArr, i4, i - i4);
                        writer.write(XmlEscape.QUOT, 0, 6);
                    } else if (c == '<') {
                        writer.write(cArr, i4, i - i4);
                        writer.write(XmlEscape.LT, 0, 4);
                    } else if (c == '>') {
                        writer.write(cArr, i4, i - i4);
                        writer.write(XmlEscape.GT, 0, 4);
                    } else if (c == '&') {
                        writer.write(cArr, i4, i - i4);
                        writer.write(XmlEscape.AMP, 0, 5);
                    } else if (c != '\'') {
                        i++;
                    } else {
                        writer.write(cArr, i4, i - i4);
                        writer.write(XmlEscape.APOS, 0, 6);
                    }
                    i4 = i + 1;
                    i++;
                }
                int i5 = i3 - i4;
                if (i5 > 0) {
                    writer.write(cArr, i4, i5);
                }
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() throws IOException {
                writer.flush();
            }
        };
    }
}
