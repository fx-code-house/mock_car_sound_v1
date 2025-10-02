package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public final class TextBlock extends TemplateElement {
    static final TextBlock EMPTY_BLOCK;
    private static final char[] EMPTY_CHAR_ARRAY;
    private char[] text;
    private final boolean unparsed;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return "#text";
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 1;
    }

    static {
        char[] cArr = new char[0];
        EMPTY_CHAR_ARRAY = cArr;
        EMPTY_BLOCK = new TextBlock(cArr, false);
    }

    public TextBlock(String str) {
        this(str, false);
    }

    public TextBlock(String str, boolean z) {
        this(str.toCharArray(), z);
    }

    private TextBlock(char[] cArr, boolean z) {
        this.text = cArr;
        this.unparsed = z;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    public void accept(Environment environment) throws IOException {
        environment.getOut().write(this.text);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        if (z) {
            String str = new String(this.text);
            return this.unparsed ? new StringBuffer("<#noparse>").append(str).append("</#noparse>").toString() : str;
        }
        return new StringBuffer("text ").append(StringUtil.jQuote(new String(this.text))).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.text);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i != 0) {
            throw new IndexOutOfBoundsException();
        }
        return ParameterRole.CONTENT;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    TemplateElement postParseCleanup(boolean z) {
        if (this.text.length == 0) {
            return this;
        }
        boolean zDeliberateLeftTrim = deliberateLeftTrim();
        boolean zDeliberateRightTrim = deliberateRightTrim();
        if (!z || this.text.length == 0 || (this.parent.parent == null && previousSibling() == null)) {
            return this;
        }
        int iTrailingCharsToStrip = !zDeliberateLeftTrim ? trailingCharsToStrip() : 0;
        int iOpeningCharsToStrip = !zDeliberateRightTrim ? openingCharsToStrip() : 0;
        if (iOpeningCharsToStrip == 0 && iTrailingCharsToStrip == 0) {
            return this;
        }
        char[] cArr = this.text;
        this.text = substring(cArr, iOpeningCharsToStrip, cArr.length - iTrailingCharsToStrip);
        if (iOpeningCharsToStrip > 0) {
            this.beginLine++;
            this.beginColumn = 1;
        }
        if (iTrailingCharsToStrip > 0) {
            this.endColumn = 0;
        }
        return this;
    }

    private boolean deliberateLeftTrim() {
        boolean z = false;
        for (TemplateElement templateElementNextTerminalNode = nextTerminalNode(); templateElementNextTerminalNode != null && templateElementNextTerminalNode.beginLine == this.endLine; templateElementNextTerminalNode = templateElementNextTerminalNode.nextTerminalNode()) {
            if (templateElementNextTerminalNode instanceof TrimInstruction) {
                TrimInstruction trimInstruction = (TrimInstruction) templateElementNextTerminalNode;
                if (!trimInstruction.left && !trimInstruction.right) {
                    z = true;
                }
                if (trimInstruction.left) {
                    int iLastNewLineIndex = lastNewLineIndex();
                    if (iLastNewLineIndex >= 0 || this.beginColumn == 1) {
                        int i = iLastNewLineIndex + 1;
                        char[] cArrSubstring = substring(this.text, 0, i);
                        char[] cArrSubstring2 = substring(this.text, i);
                        if (trim(cArrSubstring2).length == 0) {
                            this.text = cArrSubstring;
                            this.endColumn = 0;
                        } else {
                            int i2 = 0;
                            while (Character.isWhitespace(cArrSubstring2[i2])) {
                                i2++;
                            }
                            this.text = concat(cArrSubstring, substring(cArrSubstring2, i2));
                        }
                    }
                    z = true;
                }
            }
        }
        return z;
    }

    private boolean deliberateRightTrim() {
        boolean z = false;
        for (TemplateElement templateElementPrevTerminalNode = prevTerminalNode(); templateElementPrevTerminalNode != null && templateElementPrevTerminalNode.endLine == this.beginLine; templateElementPrevTerminalNode = templateElementPrevTerminalNode.prevTerminalNode()) {
            if (templateElementPrevTerminalNode instanceof TrimInstruction) {
                TrimInstruction trimInstruction = (TrimInstruction) templateElementPrevTerminalNode;
                if (!trimInstruction.left && !trimInstruction.right) {
                    z = true;
                }
                if (trimInstruction.right) {
                    int iFirstNewLineIndex = firstNewLineIndex() + 1;
                    if (iFirstNewLineIndex == 0) {
                        return false;
                    }
                    char[] cArr = this.text;
                    if (cArr.length > iFirstNewLineIndex && cArr[iFirstNewLineIndex - 1] == '\r' && cArr[iFirstNewLineIndex] == '\n') {
                        iFirstNewLineIndex++;
                    }
                    char[] cArrSubstring = substring(cArr, iFirstNewLineIndex);
                    char[] cArrSubstring2 = substring(this.text, 0, iFirstNewLineIndex);
                    if (trim(cArrSubstring2).length == 0) {
                        this.text = cArrSubstring;
                        this.beginLine++;
                        this.beginColumn = 1;
                    } else {
                        int length = cArrSubstring2.length - 1;
                        while (Character.isWhitespace(this.text[length])) {
                            length--;
                        }
                        char[] cArrSubstring3 = substring(this.text, 0, length + 1);
                        if (trim(cArrSubstring).length == 0) {
                            TemplateElement templateElementNextTerminalNode = nextTerminalNode();
                            boolean z2 = true;
                            while (true) {
                                if (templateElementNextTerminalNode == null || templateElementNextTerminalNode.beginLine != this.endLine) {
                                    break;
                                }
                                if (templateElementNextTerminalNode.heedsOpeningWhitespace()) {
                                    z2 = false;
                                }
                                if ((templateElementNextTerminalNode instanceof TrimInstruction) && ((TrimInstruction) templateElementNextTerminalNode).left) {
                                    z2 = true;
                                    break;
                                }
                                templateElementNextTerminalNode = templateElementNextTerminalNode.nextTerminalNode();
                            }
                            if (z2) {
                                cArrSubstring = EMPTY_CHAR_ARRAY;
                            }
                        }
                        this.text = concat(cArrSubstring3, cArrSubstring);
                    }
                    z = true;
                } else {
                    continue;
                }
            }
        }
        return z;
    }

    private int firstNewLineIndex() {
        String str = new String(this.text);
        int iIndexOf = str.indexOf(10);
        int iIndexOf2 = str.indexOf(13);
        int i = iIndexOf >= 0 ? iIndexOf : iIndexOf2;
        return (iIndexOf < 0 || iIndexOf2 < 0) ? i : Math.min(iIndexOf, iIndexOf2);
    }

    private int lastNewLineIndex() {
        String str = new String(this.text);
        return Math.max(str.lastIndexOf(13), str.lastIndexOf(10));
    }

    private int openingCharsToStrip() {
        int iFirstNewLineIndex = firstNewLineIndex();
        if (iFirstNewLineIndex == -1 && this.beginColumn != 1) {
            return 0;
        }
        int i = iFirstNewLineIndex + 1;
        char[] cArr = this.text;
        if (cArr.length > i && i > 0 && cArr[i - 1] == '\r' && cArr[i] == '\n') {
            i++;
        }
        if (new String(this.text).substring(0, i).trim().length() > 0) {
            return 0;
        }
        for (TemplateElement templateElementPrevTerminalNode = prevTerminalNode(); templateElementPrevTerminalNode != null && templateElementPrevTerminalNode.endLine == this.beginLine; templateElementPrevTerminalNode = templateElementPrevTerminalNode.prevTerminalNode()) {
            if (templateElementPrevTerminalNode.heedsOpeningWhitespace()) {
                return 0;
            }
        }
        return i;
    }

    private int trailingCharsToStrip() {
        String str = new String(this.text);
        int iLastNewLineIndex = lastNewLineIndex();
        if (iLastNewLineIndex == -1 && this.beginColumn != 1) {
            return 0;
        }
        String strSubstring = str.substring(iLastNewLineIndex + 1);
        if (strSubstring.trim().length() > 0) {
            return 0;
        }
        for (TemplateElement templateElementNextTerminalNode = nextTerminalNode(); templateElementNextTerminalNode != null && templateElementNextTerminalNode.beginLine == this.endLine; templateElementNextTerminalNode = templateElementNextTerminalNode.nextTerminalNode()) {
            if (templateElementNextTerminalNode.heedsTrailingWhitespace()) {
                return 0;
            }
        }
        return strSubstring.length();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
    
        return false;
     */
    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean heedsTrailingWhitespace() {
        /*
            r5 = this;
            boolean r0 = r5.isIgnorable()
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            r0 = r1
        L9:
            char[] r2 = r5.text
            int r3 = r2.length
            r4 = 1
            if (r0 >= r3) goto L25
            char r2 = r2[r0]
            r3 = 10
            if (r2 == r3) goto L24
            r3 = 13
            if (r2 != r3) goto L1a
            goto L24
        L1a:
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 != 0) goto L21
            return r4
        L21:
            int r0 = r0 + 1
            goto L9
        L24:
            return r1
        L25:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.TextBlock.heedsTrailingWhitespace():boolean");
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean heedsOpeningWhitespace() {
        if (isIgnorable()) {
            return false;
        }
        for (int length = this.text.length - 1; length >= 0; length--) {
            char c = this.text[length];
            if (c == '\n' || c == '\r') {
                return false;
            }
            if (!Character.isWhitespace(c)) {
                return true;
            }
        }
        return true;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isIgnorable() {
        char[] cArr = this.text;
        if (cArr == null || cArr.length == 0) {
            return true;
        }
        if (!isWhitespace()) {
            return false;
        }
        boolean z = getParent().getParent() == null;
        TemplateElement templateElementPreviousSibling = previousSibling();
        TemplateElement templateElementNextSibling = nextSibling();
        return ((templateElementPreviousSibling == null && z) || nonOutputtingType(templateElementPreviousSibling)) && ((templateElementNextSibling == null && z) || nonOutputtingType(templateElementNextSibling));
    }

    private boolean nonOutputtingType(TemplateElement templateElement) {
        return (templateElement instanceof Macro) || (templateElement instanceof Assignment) || (templateElement instanceof AssignmentInstruction) || (templateElement instanceof PropertySetting) || (templateElement instanceof LibraryLoad) || (templateElement instanceof Comment);
    }

    private static char[] substring(char[] cArr, int i, int i2) {
        int i3 = i2 - i;
        char[] cArr2 = new char[i3];
        System.arraycopy(cArr, i, cArr2, 0, i3);
        return cArr2;
    }

    private static char[] substring(char[] cArr, int i) {
        return substring(cArr, i, cArr.length);
    }

    private static char[] trim(char[] cArr) {
        return cArr.length == 0 ? cArr : new String(cArr).trim().toCharArray();
    }

    private static char[] concat(char[] cArr, char[] cArr2) {
        char[] cArr3 = new char[cArr.length + cArr2.length];
        System.arraycopy(cArr, 0, cArr3, 0, cArr.length);
        System.arraycopy(cArr2, 0, cArr3, cArr.length, cArr2.length);
        return cArr3;
    }

    boolean isWhitespace() {
        char[] cArr = this.text;
        return cArr == null || trim(cArr).length == 0;
    }
}
