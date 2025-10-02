package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class ParseException extends IOException implements FMParserConstants {
    static /* synthetic */ Class class$freemarker$core$ParseException;
    private static volatile Boolean jbossToolsMode;
    public int columnNumber;
    public Token currentToken;
    private String description;
    public int endColumnNumber;
    public int endLineNumber;
    protected String eol;
    public int[][] expectedTokenSequences;
    public int lineNumber;
    private String message;
    private boolean messageAndDescriptionRendered;
    protected boolean specialConstructor;
    private String templateName;
    public String[] tokenImage;

    public ParseException(Token token, int[][] iArr, String[] strArr) {
        super("");
        this.eol = SecurityUtilities.getSystemProperty("line.separator", StringUtils.LF);
        this.currentToken = token;
        this.specialConstructor = true;
        this.expectedTokenSequences = iArr;
        this.tokenImage = strArr;
        this.lineNumber = token.next.beginLine;
        this.columnNumber = this.currentToken.next.beginColumn;
        this.endLineNumber = this.currentToken.next.endLine;
        this.endColumnNumber = this.currentToken.next.endColumn;
    }

    protected ParseException() {
        this.eol = SecurityUtilities.getSystemProperty("line.separator", StringUtils.LF);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ParseException(String str, int i, int i2) {
        this(str, null, i, i2, null);
    }

    public ParseException(String str, Template template, int i, int i2, int i3, int i4) {
        this(str, template, i, i2, i3, i4, (Throwable) null);
    }

    public ParseException(String str, Template template, int i, int i2, int i3, int i4, Throwable th) {
        this(str, template == null ? null : template.getName(), i, i2, i3, i4, th);
    }

    public ParseException(String str, Template template, int i, int i2) {
        this(str, template, i, i2, null);
    }

    public ParseException(String str, Template template, int i, int i2, Throwable th) {
        this(str, template == null ? null : template.getName(), i, i2, 0, 0, th);
    }

    public ParseException(String str, Template template, Token token) {
        this(str, template, token, (Throwable) null);
    }

    public ParseException(String str, Template template, Token token, Throwable th) {
        this(str, template == null ? null : template.getName(), token.beginLine, token.beginColumn, token.endLine, token.endColumn, th);
    }

    public ParseException(String str, TemplateObject templateObject) {
        this(str, templateObject, (Throwable) null);
    }

    public ParseException(String str, TemplateObject templateObject, Throwable th) {
        this(str, templateObject.getTemplate() == null ? null : templateObject.getTemplate().getName(), templateObject.beginLine, templateObject.beginColumn, templateObject.endLine, templateObject.endColumn, th);
    }

    private ParseException(String str, String str2, int i, int i2, int i3, int i4, Throwable th) {
        super(str);
        this.eol = SecurityUtilities.getSystemProperty("line.separator", StringUtils.LF);
        this.description = str;
        this.templateName = str2;
        this.lineNumber = i;
        this.columnNumber = i2;
        this.endLineNumber = i3;
        this.endColumnNumber = i4;
    }

    public void setTemplateName(String str) {
        this.templateName = str;
        synchronized (this) {
            this.messageAndDescriptionRendered = false;
            this.message = null;
        }
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String str;
        synchronized (this) {
            if (this.messageAndDescriptionRendered) {
                return this.message;
            }
            renderMessageAndDescription();
            synchronized (this) {
                str = this.message;
            }
            return str;
        }
    }

    private String getDescription() {
        String str;
        synchronized (this) {
            if (this.messageAndDescriptionRendered) {
                return this.description;
            }
            renderMessageAndDescription();
            synchronized (this) {
                str = this.description;
            }
            return str;
        }
    }

    public String getEditorMessage() {
        return getDescription();
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public int getEndLineNumber() {
        return this.endLineNumber;
    }

    public int getEndColumnNumber() {
        return this.endColumnNumber;
    }

    private void renderMessageAndDescription() {
        String string;
        String orRenderDescription = getOrRenderDescription();
        if (!isInJBossToolsMode()) {
            string = new StringBuffer("Syntax error ").append(MessageUtil.formatLocationForSimpleParsingError(this.templateName, this.lineNumber, this.columnNumber)).append(":\n").toString();
        } else {
            string = new StringBuffer("[col. ").append(this.columnNumber).append("] ").toString();
        }
        String string2 = new StringBuffer().append(string).append(orRenderDescription).toString();
        String strSubstring = string2.substring(string.length());
        synchronized (this) {
            this.message = string2;
            this.description = strSubstring;
            this.messageAndDescriptionRendered = true;
        }
    }

    private boolean isInJBossToolsMode() {
        if (jbossToolsMode == null) {
            try {
                Class clsClass$ = class$freemarker$core$ParseException;
                if (clsClass$ == null) {
                    clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.core.ParseException");
                    class$freemarker$core$ParseException = clsClass$;
                }
                jbossToolsMode = Boolean.valueOf(clsClass$.getClassLoader().toString().indexOf("[org.jboss.ide.eclipse.freemarker:") != -1);
            } catch (Throwable unused) {
                jbossToolsMode = Boolean.FALSE;
            }
        }
        return jbossToolsMode.booleanValue();
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private String getOrRenderDescription() {
        String string;
        synchronized (this) {
            String str = this.description;
            if (str != null) {
                return str;
            }
            if (this.currentToken == null) {
                return null;
            }
            String customTokenErrorDescription = getCustomTokenErrorDescription();
            if (customTokenErrorDescription != null) {
                return customTokenErrorDescription;
            }
            StringBuffer stringBuffer = new StringBuffer();
            int length = 0;
            for (int i = 0; i < this.expectedTokenSequences.length; i++) {
                if (i != 0) {
                    stringBuffer.append(this.eol);
                }
                stringBuffer.append("    ");
                int[] iArr = this.expectedTokenSequences[i];
                if (length < iArr.length) {
                    length = iArr.length;
                }
                for (int i2 = 0; i2 < this.expectedTokenSequences[i].length; i2++) {
                    if (i2 != 0) {
                        stringBuffer.append(' ');
                    }
                    stringBuffer.append(this.tokenImage[this.expectedTokenSequences[i][i2]]);
                }
            }
            String string2 = "Encountered \"";
            Token token = this.currentToken.next;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                if (i3 != 0) {
                    string2 = new StringBuffer().append(string2).append(StringUtils.SPACE).toString();
                }
                if (token.kind == 0) {
                    string2 = new StringBuffer().append(string2).append(this.tokenImage[0]).toString();
                    break;
                }
                string2 = new StringBuffer().append(string2).append(add_escapes(token.image)).toString();
                token = token.next;
                i3++;
            }
            String string3 = new StringBuffer().append(string2).append("\", but ").toString();
            if (this.expectedTokenSequences.length == 1) {
                string = new StringBuffer().append(string3).append("was expecting:").append(this.eol).toString();
            } else {
                string = new StringBuffer().append(string3).append("was expecting one of:").append(this.eol).toString();
            }
            return new StringBuffer().append(string).append((Object) stringBuffer).toString();
        }
    }

    private String getCustomTokenErrorDescription() {
        Token token = this.currentToken.next;
        int i = token.kind;
        if (i != 0) {
            if (i == 31 || i == 9 || i == 44) {
                return new StringBuffer("Unexpected directive, ").append(StringUtil.jQuote(token)).append(". Check whether you have a valid #if-#elseif-#else structure.").toString();
            }
            return null;
        }
        HashSet hashSet = new HashSet();
        int i2 = 0;
        while (true) {
            int[][] iArr = this.expectedTokenSequences;
            if (i2 >= iArr.length) {
                return new StringBuffer("Unexpected end of file reached.").append(hashSet.size() == 0 ? "" : new StringBuffer(" You have an unclosed ").append(concatWithOrs(hashSet)).append(".").toString()).toString();
            }
            for (int i3 : iArr[i2]) {
                if (i3 == 31) {
                    hashSet.add("#if");
                } else if (i3 == 32) {
                    hashSet.add("#list");
                } else if (i3 == 60) {
                    hashSet.add("#escape");
                } else if (i3 == 62) {
                    hashSet.add("#noescape");
                } else if (i3 == 64) {
                    hashSet.add("@...");
                } else if (i3 == 114) {
                    hashSet.add("\"[\"");
                } else if (i3 == 116) {
                    hashSet.add("\"(\"");
                } else if (i3 != 118) {
                    switch (i3) {
                        case 34:
                            hashSet.add("#attempt");
                            break;
                        case 35:
                            hashSet.add("#foreach");
                            break;
                        case 36:
                            hashSet.add("#local");
                            break;
                        case 37:
                            hashSet.add("#global");
                            break;
                        case 38:
                            hashSet.add("#assign");
                            break;
                        case 40:
                            hashSet.add("#macro");
                        case 39:
                            hashSet.add("#function");
                            break;
                        case 41:
                            hashSet.add("#compress");
                            break;
                        case 42:
                            hashSet.add("#transform");
                            break;
                        case 43:
                            hashSet.add("#switch");
                            break;
                    }
                } else {
                    hashSet.add("\"{\"");
                }
            }
            i2++;
        }
    }

    private String concatWithOrs(Set set) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (stringBuffer.length() != 0) {
                stringBuffer.append(" or ");
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    protected String add_escapes(String str) {
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
}
