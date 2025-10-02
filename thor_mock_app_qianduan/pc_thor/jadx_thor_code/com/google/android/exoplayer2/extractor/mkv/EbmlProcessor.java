package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public interface EbmlProcessor {
    public static final int ELEMENT_TYPE_BINARY = 4;
    public static final int ELEMENT_TYPE_FLOAT = 5;
    public static final int ELEMENT_TYPE_MASTER = 1;
    public static final int ELEMENT_TYPE_STRING = 3;
    public static final int ELEMENT_TYPE_UNKNOWN = 0;
    public static final int ELEMENT_TYPE_UNSIGNED_INT = 2;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ElementType {
    }

    void binaryElement(int id, int contentsSize, ExtractorInput input) throws IOException;

    void endMasterElement(int id) throws ParserException;

    void floatElement(int id, double value) throws ParserException;

    int getElementType(int id);

    void integerElement(int id, long value) throws ParserException;

    boolean isLevel1Element(int id);

    void startMasterElement(int id, long contentPosition, long contentSize) throws ParserException;

    void stringElement(int id, String value) throws ParserException;
}
