package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.apache.commons.lang3.CharUtils;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
public class StandardCompress implements TemplateTransformModel {
    private static final String BUFFER_SIZE_KEY = "buffer_size";
    public static final StandardCompress INSTANCE = new StandardCompress();
    private static final String SINGLE_LINE_KEY = "single_line";
    private int defaultBufferSize;

    public StandardCompress() {
        this(2048);
    }

    public StandardCompress(int i) {
        this.defaultBufferSize = i;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
    public Writer getWriter(Writer writer, Map map) throws TemplateModelException {
        int iIntValue = this.defaultBufferSize;
        boolean asBoolean = false;
        if (map != null) {
            try {
                TemplateNumberModel templateNumberModel = (TemplateNumberModel) map.get(BUFFER_SIZE_KEY);
                if (templateNumberModel != null) {
                    iIntValue = templateNumberModel.getAsNumber().intValue();
                }
                try {
                    TemplateBooleanModel templateBooleanModel = (TemplateBooleanModel) map.get(SINGLE_LINE_KEY);
                    if (templateBooleanModel != null) {
                        asBoolean = templateBooleanModel.getAsBoolean();
                    }
                } catch (ClassCastException unused) {
                    throw new TemplateModelException("Expecting boolean argument to single_line");
                }
            } catch (ClassCastException unused2) {
                throw new TemplateModelException("Expecting numerical argument to buffer_size");
            }
        }
        return new StandardCompressWriter(writer, iIntValue, asBoolean);
    }

    private static class StandardCompressWriter extends Writer {
        private static final int AT_BEGINNING = 0;
        private static final int INIT = 2;
        private static final int LINEBREAK_CR = 4;
        private static final int LINEBREAK_CRLF = 5;
        private static final int LINEBREAK_LF = 6;
        private static final int MAX_EOL_LENGTH = 2;
        private static final int SAW_CR = 3;
        private static final int SINGLE_LINE = 1;
        private final char[] buf;
        private final Writer out;
        private final boolean singleLine;
        private int pos = 0;
        private boolean inWhitespace = true;
        private int lineBreakState = 0;

        public StandardCompressWriter(Writer writer, int i, boolean z) {
            this.out = writer;
            this.singleLine = z;
            this.buf = new char[i];
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) throws IOException {
            while (true) {
                int length = (this.buf.length - this.pos) - 2;
                if (length >= i2) {
                    writeHelper(cArr, i, i2);
                    return;
                } else if (length <= 0) {
                    flushInternal();
                } else {
                    writeHelper(cArr, i, length);
                    flushInternal();
                    i += length;
                    i2 -= length;
                }
            }
        }

        private void writeHelper(char[] cArr, int i, int i2) {
            int i3 = i2 + i;
            while (i < i3) {
                char c = cArr[i];
                if (Character.isWhitespace(c)) {
                    this.inWhitespace = true;
                    updateLineBreakState(c);
                } else if (this.inWhitespace) {
                    this.inWhitespace = false;
                    writeLineBreakOrSpace();
                    char[] cArr2 = this.buf;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    cArr2[i4] = c;
                } else {
                    char[] cArr3 = this.buf;
                    int i5 = this.pos;
                    this.pos = i5 + 1;
                    cArr3[i5] = c;
                }
                i++;
            }
        }

        private void updateLineBreakState(char c) {
            int i = this.lineBreakState;
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                if (c == '\n') {
                    this.lineBreakState = 5;
                    return;
                } else {
                    this.lineBreakState = 4;
                    return;
                }
            }
            if (c == '\r') {
                this.lineBreakState = 3;
            } else if (c == '\n') {
                this.lineBreakState = 6;
            }
        }

        private void writeLineBreakOrSpace() {
            switch (this.lineBreakState) {
                case 1:
                case 2:
                    char[] cArr = this.buf;
                    int i = this.pos;
                    this.pos = i + 1;
                    cArr[i] = ' ';
                    break;
                case 3:
                case 4:
                    char[] cArr2 = this.buf;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    cArr2[i2] = CharUtils.CR;
                    break;
                case 5:
                    char[] cArr3 = this.buf;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    cArr3[i3] = CharUtils.CR;
                case 6:
                    char[] cArr4 = this.buf;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    cArr4[i4] = '\n';
                    break;
            }
            this.lineBreakState = this.singleLine ? 1 : 2;
        }

        private void flushInternal() throws IOException {
            this.out.write(this.buf, 0, this.pos);
            this.pos = 0;
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() throws IOException {
            flushInternal();
            this.out.flush();
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            flushInternal();
        }
    }
}
