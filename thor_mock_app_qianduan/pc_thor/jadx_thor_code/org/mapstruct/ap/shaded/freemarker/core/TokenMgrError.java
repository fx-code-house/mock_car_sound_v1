package org.mapstruct.ap.shaded.freemarker.core;

import org.mapstruct.ap.shaded.freemarker.template.Template;

/* loaded from: classes3.dex */
public class TokenMgrError extends Error {
    static final int INVALID_LEXICAL_STATE = 2;
    static final int LEXICAL_ERROR = 0;
    static final int LOOP_DETECTED = 3;
    static final int STATIC_LEXER_ERROR = 1;
    private Integer columnNumber;
    private String detail;
    private Integer endColumnNumber;
    private Integer endLineNumber;
    int errorCode;
    private Integer lineNumber;

    protected static final String addEscapes(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != 0) {
                if (cCharAt == '\"') {
                    stringBuffer.append("\\\"");
                } else if (cCharAt == '\'') {
                    stringBuffer.append("\\'");
                } else if (cCharAt == '\\') {
                    stringBuffer.append("\\\\");
                } else if (cCharAt == '\f') {
                    stringBuffer.append("\\f");
                } else if (cCharAt != '\r') {
                    switch (cCharAt) {
                        case '\b':
                            stringBuffer.append("\\b");
                            break;
                        case '\t':
                            stringBuffer.append("\\t");
                            break;
                        case '\n':
                            stringBuffer.append("\\n");
                            break;
                        default:
                            char cCharAt2 = str.charAt(i);
                            if (cCharAt2 < ' ' || cCharAt2 > '~') {
                                String string = new StringBuffer("0000").append(Integer.toString(cCharAt2, 16)).toString();
                                stringBuffer.append(new StringBuffer("\\u").append(string.substring(string.length() - 4, string.length())).toString());
                                break;
                            } else {
                                stringBuffer.append(cCharAt2);
                                break;
                            }
                            break;
                    }
                } else {
                    stringBuffer.append("\\r");
                }
            }
        }
        return stringBuffer.toString();
    }

    protected static String LexicalError(boolean z, int i, int i2, int i3, String str, char c) {
        return new StringBuffer("Lexical error: encountered ").append(z ? "<EOF> " : new StringBuffer("\"").append(addEscapes(String.valueOf(c))).append("\" (").append((int) c).append("), ").toString()).append("after \"").append(addEscapes(str)).append("\".").toString();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return super.getMessage();
    }

    public TokenMgrError() {
    }

    public TokenMgrError(String str, int i) {
        super(str);
        this.detail = str;
        this.errorCode = i;
    }

    public TokenMgrError(String str, int i, int i2, int i3) {
        this(str, i, i2, i3, 0, 0);
        this.endLineNumber = null;
        this.endColumnNumber = null;
    }

    public TokenMgrError(String str, int i, int i2, int i3, int i4, int i5) {
        super(str);
        this.detail = str;
        this.errorCode = i;
        this.lineNumber = new Integer(i2);
        this.columnNumber = new Integer(i3);
        this.endLineNumber = new Integer(i4);
        this.endColumnNumber = new Integer(i5);
    }

    public TokenMgrError(boolean z, int i, int i2, int i3, String str, char c, int i4) {
        this(LexicalError(z, i, i2, i3, str, c), i4);
        this.lineNumber = new Integer(i2);
        Integer num = new Integer(i3);
        this.columnNumber = num;
        this.endLineNumber = this.lineNumber;
        this.endColumnNumber = num;
    }

    public Integer getLineNumber() {
        return this.lineNumber;
    }

    public Integer getColumnNumber() {
        return this.columnNumber;
    }

    public Integer getEndLineNumber() {
        return this.endLineNumber;
    }

    public Integer getEndColumnNumber() {
        return this.endColumnNumber;
    }

    public String getDetail() {
        return this.detail;
    }

    public ParseException toParseException(Template template) {
        return new ParseException(getDetail(), template, getLineNumber() != null ? getLineNumber().intValue() : 0, getColumnNumber() != null ? getColumnNumber().intValue() : 0, getEndLineNumber() != null ? getEndLineNumber().intValue() : 0, getEndColumnNumber() != null ? getEndColumnNumber().intValue() : 0);
    }
}
