package org.mapstruct.ap.shaded.freemarker.core;

import android.support.v4.media.session.PlaybackStateCompat;
import com.carsystems.thor.app.BuildConfig;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.basemodule.gui.view.CircleBarView;
import com.thor.businessmodule.settings.Constants;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.ws.WebSocketProtocol;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;

/* loaded from: classes3.dex */
class FMParserTokenManager implements FMParserConstants {
    boolean autodetectTagSyntax;
    private int bracketNesting;
    protected char curChar;
    int curLexState;
    public PrintStream debugStream;
    int defaultLexState;
    boolean directiveSyntaxEstablished;
    private int hashLiteralNesting;
    StringBuffer image;
    private boolean inFTLHeader;
    boolean inInvocation;
    int incompatibleImprovements;
    protected SimpleCharStream input_stream;
    int jjimageLen;
    int jjmatchedKind;
    int jjmatchedPos;
    int jjnewStateCnt;
    int jjround;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    int lengthOfMatch;
    String noparseTag;
    boolean onlyTextOutput;
    private int parenthesisNesting;
    private FMParser parser;
    boolean squBracTagSyntax;
    boolean strictEscapeSyntax;
    static final long[] jjbitVec0 = {-2, -1, -1, -1};
    static final long[] jjbitVec2 = {0, 0, -1, -1};
    static final long[] jjbitVec3 = {2301339413881290750L, -16384, 4294967295L, 432345564227567616L};
    static final long[] jjbitVec4 = {0, 0, 0, -36028797027352577L};
    static final long[] jjbitVec5 = {0, -1, -1, -1};
    static final long[] jjbitVec6 = {-1, -1, WebSocketProtocol.PAYLOAD_SHORT_MAX, 0};
    static final long[] jjbitVec7 = {-1, -1, 0, 0};
    static final long[] jjbitVec8 = {70368744177663L, 0, 0, 0};
    static final int[] jjnextStates = {10, 12, 4, 5, 3, 4, 5, 557, 566, 306, StatusLine.HTTP_TEMP_REDIRECT, StatusLine.HTTP_PERM_REDIRECT, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 336, 337, 345, Constants.DRIVE_SELECT_FIRMWARE_MIN_VERSION, 357, 358, 369, 370, 381, 382, Constants.DEVICE_NATIVE_SETTINGS_FIRMWARE_MIN_VERSION, 392, WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE, 403, WalletConstants.ERROR_CODE_UNKNOWN, 414, 426, 427, 436, 437, 449, 450, 463, 464, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 501, 502, 503, 515, 516, 521, 527, 528, 530, 12, 21, 24, 31, 36, 44, 51, 56, 63, 70, 76, 84, 91, 100, 106, 116, 122, 127, 134, 139, 147, 157, 166, 175, 182, 190, 199, 206, 214, 215, 223, 228, 233, 242, 251, 258, 268, 276, 287, 294, 304, 5, 6, 14, 15, 149, 150, 159, 160, 168, 169, 177, 178, 179, 184, 185, 186, PsExtractor.AUDIO_STREAM, 193, 194, 201, 202, 203, 208, 209, 210, 216, 217, 218, 220, 221, 222, 225, 226, 227, 230, 231, 232, 235, 236, 244, 245, 246, 260, 261, 262, 278, 279, 280, 296, 297, 332, BuildConfig.VERSION_CODE, 339, 340, 348, 349, CircleBarView.FULL, 361, 372, 373, 384, 385, 394, Constants.AUTO_ADDING_OWN_PRESET_FIRMWARE_MIN_VERSION, WalletConstants.ERROR_CODE_MERCHANT_ACCOUNT_ERROR, WalletConstants.ERROR_CODE_SPENDING_LIMIT_EXCEEDED, 416, 417, 429, 430, 439, 440, 452, 453, 466, 467, 493, 494, 505, 506, 560, 561, 564, 565, 561, 563, 564, 565, 306, StatusLine.HTTP_TEMP_REDIRECT, StatusLine.HTTP_PERM_REDIRECT, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 544, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490, 545, 502, 546, 516, 549, 552, 528, 553, 523, 524, 559, 564, 565, 47, 48, 49, 67, 70, 73, 77, 78, 43, 45, 39, 40, 13, 14, 16, 6, 7, 9, 56, 58, 60, 63, 20, 23, 8, 10, 15, 17, 21, 22, 24, 25, 44, 45, 46, 64, 67, 70, 74, 75, 40, 42, 53, 55, 57, 60, 3, 5, 43, 44, 45, 63, 66, 69, 73, 74, 39, 41, 35, 36, 8, 9, 11, 1, 2, 4, 52, 54, 56, 59, 15, 18, 16, 17, 19, 20, 49, 50, 51, 69, 72, 75, 79, 80, 45, 47, 41, 42, 58, 60, 62, 65};
    public static final String[] jjstrLiteralImages = {"", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "${", "#{", null, null, null, null, null, null, null, null, null, null, BooleanUtils.FALSE, BooleanUtils.TRUE, null, null, ".", "..", null, "..*", "?", "??", "=", "==", "!=", null, null, null, null, "+", "-", "*", "**", "...", "/", "%", null, null, "!", ",", ";", ":", "[", "]", "(", ")", "{", "}", "in", "as", "using", null, null, null, null, ">", null, ">", ">=", null, null, null, null, null, null};
    public static final String[] lexStateNames = {"DEFAULT", "NODIRECTIVE", "FM_EXPRESSION", "IN_PAREN", "NAMED_PARAMETER_EXPRESSION", "EXPRESSION_COMMENT", "NO_SPACE_EXPRESSION", "NO_PARSE"};
    public static final int[] jjnewLexState = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 2, -1, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 2, -1, -1, -1, -1};
    static final long[] jjtoToken = {-63, -3458764513820671489L, 255};
    static final long[] jjtoSkip = {0, 65024, 0};

    private final int jjStopStringLiteralDfa_5(int i, long j, long j2) {
        return -1;
    }

    void setParser(FMParser fMParser) {
        this.parser = fMParser;
    }

    Template getTemplate() {
        FMParser fMParser = this.parser;
        if (fMParser != null) {
            return fMParser.getTemplate();
        }
        return null;
    }

    private void strictSyntaxCheck(Token token, int i) {
        if (this.onlyTextOutput) {
            token.kind = 69;
            return;
        }
        char cCharAt = token.image.charAt(0);
        if (this.autodetectTagSyntax && !this.directiveSyntaxEstablished) {
            this.squBracTagSyntax = cCharAt == '[';
        }
        if ((cCharAt == '[' && !this.squBracTagSyntax) || (cCharAt == '<' && this.squBracTagSyntax)) {
            token.kind = 69;
            return;
        }
        if (!this.strictEscapeSyntax) {
            SwitchTo(i);
            return;
        }
        if (!this.squBracTagSyntax && !token.image.startsWith("<#") && !token.image.startsWith("</#")) {
            token.kind = 69;
        } else {
            this.directiveSyntaxEstablished = true;
            SwitchTo(i);
        }
    }

    private void unifiedCall(Token token) {
        char cCharAt = token.image.charAt(0);
        if (this.autodetectTagSyntax && !this.directiveSyntaxEstablished) {
            this.squBracTagSyntax = cCharAt == '[';
        }
        boolean z = this.squBracTagSyntax;
        if (z && cCharAt == '<') {
            token.kind = 69;
        } else if (!z && cCharAt == '[') {
            token.kind = 69;
        } else {
            this.directiveSyntaxEstablished = true;
            SwitchTo(6);
        }
    }

    private void unifiedCallEnd(Token token) {
        char cCharAt = token.image.charAt(0);
        boolean z = this.squBracTagSyntax;
        if (z && cCharAt == '<') {
            token.kind = 69;
        } else {
            if (z || cCharAt != '[') {
                return;
            }
            token.kind = 69;
        }
    }

    private void closeBracket(Token token) {
        int i = this.bracketNesting;
        if (i > 0) {
            this.bracketNesting = i - 1;
            return;
        }
        token.kind = 126;
        if (this.inFTLHeader) {
            eatNewline();
            this.inFTLHeader = false;
        }
        SwitchTo(0);
    }

    private void eatNewline() {
        char c;
        int i = 0;
        do {
            try {
                c = this.input_stream.readChar();
                i++;
                if (!Character.isWhitespace(c)) {
                    this.input_stream.backup(i);
                    return;
                } else if (c == '\r') {
                    int i2 = i + 1;
                    if (this.input_stream.readChar() != '\n') {
                        this.input_stream.backup(1);
                        return;
                    }
                    return;
                }
            } catch (IOException unused) {
                this.input_stream.backup(i);
                return;
            }
        } while (c != '\n');
    }

    private void ftlHeader(Token token) {
        if (!this.directiveSyntaxEstablished) {
            this.squBracTagSyntax = token.image.charAt(0) == '[';
            this.directiveSyntaxEstablished = true;
            this.autodetectTagSyntax = false;
        }
        String str = token.image;
        char cCharAt = str.charAt(0);
        char cCharAt2 = str.charAt(str.length() - 1);
        if ((cCharAt == '[' && !this.squBracTagSyntax) || (cCharAt == '<' && this.squBracTagSyntax)) {
            token.kind = 69;
        }
        if (token.kind != 69) {
            if (cCharAt2 != '>' && cCharAt2 != ']') {
                SwitchTo(2);
                this.inFTLHeader = true;
            } else {
                eatNewline();
            }
        }
    }

    public void setDebugStream(PrintStream printStream) {
        this.debugStream = printStream;
    }

    private final int jjMoveStringLiteralDfa0_7() {
        return jjMoveNfa_7(0, 0);
    }

    private final void jjCheckNAdd(int i) {
        int[] iArr = this.jjrounds;
        int i2 = iArr[i];
        int i3 = this.jjround;
        if (i2 != i3) {
            int[] iArr2 = this.jjstateSet;
            int i4 = this.jjnewStateCnt;
            this.jjnewStateCnt = i4 + 1;
            iArr2[i4] = i;
            iArr[i] = i3;
        }
    }

    private final void jjAddStates(int i, int i2) {
        while (true) {
            int[] iArr = this.jjstateSet;
            int i3 = this.jjnewStateCnt;
            this.jjnewStateCnt = i3 + 1;
            iArr[i3] = jjnextStates[i];
            int i4 = i + 1;
            if (i == i2) {
                return;
            } else {
                i = i4;
            }
        }
    }

    private final void jjCheckNAddTwoStates(int i, int i2) {
        jjCheckNAdd(i);
        jjCheckNAdd(i2);
    }

    private final void jjCheckNAddStates(int i, int i2) {
        while (true) {
            jjCheckNAdd(jjnextStates[i]);
            int i3 = i + 1;
            if (i == i2) {
                return;
            } else {
                i = i3;
            }
        }
    }

    private final void jjCheckNAddStates(int i) {
        int[] iArr = jjnextStates;
        jjCheckNAdd(iArr[i]);
        jjCheckNAdd(iArr[i + 1]);
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_7(int r30, int r31) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_7(int, int):int");
    }

    private final int jjStopStringLiteralDfa_1(int i, long j, long j2) {
        if (i == 0 && (j2 & 384) != 0) {
            this.jjmatchedKind = 70;
        }
        return -1;
    }

    private final int jjStartNfa_1(int i, long j, long j2) {
        return jjMoveNfa_1(jjStopStringLiteralDfa_1(i, j, j2), i + 1);
    }

    private final int jjStopAtPos(int i, int i2) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        return i + 1;
    }

    private final int jjStartNfaWithStates_1(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_1(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_1() {
        char c = this.curChar;
        if (c == '#') {
            return jjMoveStringLiteralDfa1_1(256L);
        }
        if (c == '$') {
            return jjMoveStringLiteralDfa1_1(128L);
        }
        return jjMoveNfa_1(2, 0);
    }

    private final int jjMoveStringLiteralDfa1_1(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == '{') {
                if ((128 & j) != 0) {
                    return jjStopAtPos(1, 71);
                }
                if ((256 & j) != 0) {
                    return jjStopAtPos(1, 72);
                }
            }
            return jjStartNfa_1(0, 0L, j);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_1(0, 0L, j);
            return 1;
        }
    }

    private final int jjMoveNfa_1(int i, int i2) {
        this.jjnewStateCnt = 3;
        int i3 = 0;
        this.jjstateSet[0] = i;
        int i4 = i2;
        int i5 = 1;
        int i6 = 0;
        int i7 = Integer.MAX_VALUE;
        while (true) {
            int i8 = this.jjround + 1;
            this.jjround = i8;
            if (i8 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            char c = this.curChar;
            if (c < '@') {
                long j = 1 << c;
                do {
                    i5--;
                    int i9 = this.jjstateSet[i5];
                    if (i9 != 0) {
                        if (i9 != 1) {
                            if (i9 == 2) {
                                if ((j & (-1152921611981039105L)) != 0) {
                                    if (i7 > 69) {
                                        i7 = 69;
                                    }
                                    jjCheckNAdd(1);
                                } else if ((j & 4294977024L) != 0) {
                                    if (i7 > 68) {
                                        i7 = 68;
                                    }
                                    jjCheckNAdd(i3);
                                } else if ((j & 1152921607686062080L) != 0 && i7 > 70) {
                                    i7 = 70;
                                }
                            }
                        } else if ((j & (-1152921611981039105L)) != 0) {
                            jjCheckNAdd(1);
                            i7 = 69;
                        }
                    } else if ((j & 4294977024L) != 0) {
                        jjCheckNAdd(i3);
                        i7 = 68;
                    }
                } while (i5 != i6);
            } else if (c < 128) {
                long j2 = 1 << (c & '?');
                do {
                    i5--;
                    int i10 = this.jjstateSet[i5];
                    if (i10 != 1) {
                        if (i10 == 2) {
                            if ((j2 & (-576460752437641217L)) != 0) {
                                if (i7 > 69) {
                                    i7 = 69;
                                }
                                jjCheckNAdd(1);
                            } else if ((j2 & 576460752437641216L) != 0 && i7 > 70) {
                                i7 = 70;
                            }
                        }
                    } else if ((j2 & (-576460752437641217L)) != 0) {
                        jjCheckNAdd(1);
                        i7 = 69;
                    }
                } while (i5 != i6);
            } else {
                int i11 = c >> '\b';
                int i12 = i11 >> 6;
                long j3 = 1 << (i11 & 63);
                int i13 = (c & 255) >> 6;
                long j4 = 1 << (c & '?');
                do {
                    i5--;
                    int i14 = this.jjstateSet[i5];
                    if ((i14 == 1 || i14 == 2) && jjCanMove_0(i11, i12, i13, j3, j4)) {
                        if (i7 > 69) {
                            i7 = 69;
                        }
                        jjCheckNAdd(1);
                    }
                } while (i5 != i6);
            }
            if (i7 != Integer.MAX_VALUE) {
                this.jjmatchedKind = i7;
                this.jjmatchedPos = i4;
                i7 = Integer.MAX_VALUE;
            }
            i4++;
            i5 = this.jjnewStateCnt;
            this.jjnewStateCnt = i6;
            i6 = 3 - i6;
            if (i5 == i6) {
                return i4;
            }
            try {
                this.curChar = this.input_stream.readChar();
                i3 = 0;
            } catch (IOException unused) {
                return i4;
            }
        }
    }

    private final int jjStopStringLiteralDfa_0(int i, long j, long j2) {
        if (i == 0 && (j2 & 384) != 0) {
            this.jjmatchedKind = 70;
        }
        return -1;
    }

    private final int jjStartNfa_0(int i, long j, long j2) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(i, j, j2), i + 1);
    }

    private final int jjStartNfaWithStates_0(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_0(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_0() {
        char c = this.curChar;
        if (c == '#') {
            return jjMoveStringLiteralDfa1_0(256L);
        }
        if (c == '$') {
            return jjMoveStringLiteralDfa1_0(128L);
        }
        return jjMoveNfa_0(2, 0);
    }

    private final int jjMoveStringLiteralDfa1_0(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c == '{') {
                if ((128 & j) != 0) {
                    return jjStopAtPos(1, 71);
                }
                if ((256 & j) != 0) {
                    return jjStopAtPos(1, 72);
                }
            }
            return jjStartNfa_0(0, 0L, j);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_0(0, 0L, j);
            return 1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:1638:0x1ae0 A[PHI: r4
      0x1ae0: PHI (r4v7 int) = 
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v16 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v18 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v20 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v22 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v23 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v25 int)
      (r4v6 int)
      (r4v6 int)
     binds: [B:807:0x0b30, B:1859:0x1fa4, B:1860:0x1fa6, B:1856:0x1f94, B:1857:0x1f96, B:1853:0x1f82, B:1854:0x1f84, B:1850:0x1f70, B:1851:0x1f72, B:1847:0x1f60, B:1848:0x1f62, B:1844:0x1f50, B:1845:0x1f52, B:1841:0x1f3e, B:1842:0x1f40, B:1838:0x1f2e, B:1839:0x1f30, B:1835:0x1f1c, B:1836:0x1f1e, B:1832:0x1f0a, B:1833:0x1f0c, B:1829:0x1efa, B:1830:0x1efc, B:1826:0x1eea, B:1827:0x1eec, B:1823:0x1eda, B:1824:0x1edc, B:1820:0x1ec8, B:1821:0x1eca, B:1817:0x1eb6, B:1818:0x1eb8, B:1814:0x1ea6, B:1815:0x1ea8, B:1811:0x1e94, B:1812:0x1e96, B:1808:0x1e84, B:1809:0x1e86, B:1805:0x1e72, B:1806:0x1e74, B:1802:0x1e62, B:1803:0x1e64, B:1799:0x1e50, B:1800:0x1e52, B:1796:0x1e40, B:1797:0x1e42, B:1793:0x1e2e, B:1794:0x1e30, B:1790:0x1e1c, B:1791:0x1e1e, B:1787:0x1e0c, B:1788:0x1e0e, B:1784:0x1dfc, B:1785:0x1dfe, B:1781:0x1dec, B:1782:0x1dee, B:1778:0x1dda, B:1779:0x1ddc, B:1775:0x1dca, B:1776:0x1dcc, B:1772:0x1db8, B:1773:0x1dba, B:1769:0x1da8, B:1770:0x1daa, B:1766:0x1d96, B:1767:0x1d98, B:1763:0x1d88, B:1764:0x1d8a, B:1760:0x1d7a, B:1761:0x1d7c, B:1757:0x1d68, B:1758:0x1d6a, B:1754:0x1d56, B:1755:0x1d58, B:1751:0x1d44, B:1752:0x1d46, B:1748:0x1d34, B:1749:0x1d36, B:1745:0x1d22, B:1746:0x1d24, B:1742:0x1d10, B:1743:0x1d12, B:1739:0x1d02, B:1740:0x1d04, B:1736:0x1cf4, B:1737:0x1cf6, B:1733:0x1ce2, B:1734:0x1ce4, B:1730:0x1cd2, B:1731:0x1cd4, B:1727:0x1cc2, B:1728:0x1cc4, B:1724:0x1cb0, B:1725:0x1cb2, B:1721:0x1ca0, B:1722:0x1ca2, B:1718:0x1c90, B:1719:0x1c92, B:1715:0x1c7e, B:1716:0x1c80, B:1712:0x1c6c, B:1713:0x1c6e, B:1709:0x1c5a, B:1710:0x1c5c, B:1706:0x1c48, B:1707:0x1c4a, B:1703:0x1c36, B:1704:0x1c38, B:1700:0x1c26, B:1701:0x1c28, B:1697:0x1c16, B:1698:0x1c18, B:1694:0x1c04, B:1695:0x1c06, B:1691:0x1bf4, B:1692:0x1bf6, B:1688:0x1be2, B:1689:0x1be4, B:1685:0x1bd0, B:1686:0x1bd2, B:1682:0x1bc0, B:1683:0x1bc2, B:1679:0x1bb0, B:1680:0x1bb2, B:1676:0x1b9e, B:1677:0x1ba0, B:1673:0x1b8e, B:1674:0x1b90, B:1670:0x1b7e, B:1671:0x1b80, B:1667:0x1b6c, B:1668:0x1b6e, B:1664:0x1b5a, B:1665:0x1b5c, B:1661:0x1b49, B:1662:0x1b4b, B:1658:0x1b3a, B:1659:0x1b3c, B:1655:0x1b29, B:1656:0x1b2b, B:1652:0x1b1a, B:1653:0x1b1c, B:1649:0x1b0b, B:1650:0x1b0d, B:1646:0x1afc, B:1647:0x1afe, B:1643:0x1aee, B:1644:0x1af0, B:1633:0x1ad7, B:1635:0x1adb, B:1630:0x1ac8, B:1631:0x1aca, B:1627:0x1ab7, B:1628:0x1ab9, B:1624:0x1aa8, B:1625:0x1aaa, B:1621:0x1a97, B:1622:0x1a99, B:1618:0x1a86, B:1619:0x1a88, B:1615:0x1a75, B:1616:0x1a77, B:1612:0x1a65, B:1613:0x1a67, B:1609:0x1a56, B:1610:0x1a58, B:1604:0x1a4a, B:1606:0x1a4e, B:1601:0x1a3a, B:1602:0x1a3c, B:1598:0x1a2a, B:1599:0x1a2c, B:1595:0x1a1a, B:1596:0x1a1c, B:1592:0x1a08, B:1593:0x1a0a, B:1589:0x19f6, B:1590:0x19f8, B:1586:0x19e6, B:1587:0x19e8, B:1583:0x19d7, B:1584:0x19d9, B:1578:0x19cb, B:1580:0x19cf, B:1575:0x19bb, B:1576:0x19bd, B:1572:0x19a9, B:1573:0x19ab, B:1569:0x1997, B:1570:0x1999, B:1566:0x1985, B:1567:0x1987, B:1563:0x1973, B:1564:0x1975, B:1560:0x1963, B:1561:0x1965, B:1557:0x1956, B:1558:0x1958, B:1552:0x194a, B:1554:0x194e, B:1549:0x193a, B:1550:0x193c, B:1546:0x1928, B:1547:0x192a, B:1543:0x1918, B:1544:0x191a, B:1540:0x190b, B:1541:0x190d, B:1535:0x18fd, B:1537:0x1901, B:1532:0x18ed, B:1533:0x18ef, B:1529:0x18db, B:1530:0x18dd, B:1526:0x18cb, B:1527:0x18cd, B:1523:0x18b9, B:1524:0x18bb, B:1520:0x18aa, B:1521:0x18ac, B:1515:0x189e, B:1517:0x18a2, B:1512:0x188e, B:1513:0x1890, B:1509:0x187c, B:1510:0x187e, B:1506:0x186a, B:1507:0x186c, B:1503:0x185a, B:1504:0x185c, B:1500:0x184a, B:1501:0x184c, B:1497:0x183b, B:1498:0x183d, B:1491:0x182a, B:1494:0x1830, B:1488:0x181a, B:1489:0x181c, B:1485:0x180a, B:1486:0x180c, B:1482:0x17fa, B:1483:0x17fc, B:1479:0x17eb, B:1480:0x17ed, B:1474:0x17dd, B:1476:0x17e1, B:1471:0x17cd, B:1472:0x17cf, B:1468:0x17bb, B:1469:0x17bd, B:1465:0x17a9, B:1466:0x17ab, B:1462:0x1799, B:1463:0x179b, B:1459:0x178c, B:1460:0x178e, B:1454:0x1780, B:1456:0x1784, B:1451:0x1773, B:1452:0x1775, B:1446:0x1767, B:1448:0x176b, B:1443:0x1757, B:1444:0x1759, B:1440:0x174a, B:1441:0x174c, B:1435:0x173e, B:1437:0x1742, B:1432:0x172e, B:1433:0x1730, B:1429:0x171f, B:1430:0x1721, B:1424:0x1713, B:1426:0x1717, B:1421:0x1703, B:1422:0x1705, B:1418:0x16f6, B:1419:0x16f8, B:1413:0x16ea, B:1415:0x16ee, B:1410:0x16da, B:1411:0x16dc, B:1407:0x16ca, B:1408:0x16cc, B:1404:0x16b8, B:1405:0x16ba, B:1401:0x16a6, B:1402:0x16a8, B:1398:0x1696, B:1399:0x1698, B:1395:0x1686, B:1396:0x1688, B:1392:0x1677, B:1393:0x1679, B:1387:0x1669, B:1389:0x166d, B:1384:0x1659, B:1385:0x165b, B:1381:0x1649, B:1382:0x164b, B:1378:0x1639, B:1379:0x163b, B:1375:0x1627, B:1376:0x1629, B:1372:0x1617, B:1373:0x1619, B:1369:0x1607, B:1370:0x1609, B:1366:0x15f5, B:1367:0x15f7, B:1363:0x15e5, B:1364:0x15e7, B:1360:0x15d5, B:1361:0x15d7, B:1357:0x15c3, B:1358:0x15c5, B:1354:0x15b3, B:1355:0x15b5, B:1351:0x15a6, B:1352:0x15a8, B:1346:0x159a, B:1348:0x159e, B:1343:0x158a, B:1344:0x158c, B:1340:0x1578, B:1341:0x157a, B:1337:0x1566, B:1338:0x1568, B:1334:0x1554, B:1335:0x1556, B:1331:0x1542, B:1332:0x1544, B:1328:0x1532, B:1329:0x1534, B:1325:0x1520, B:1326:0x1522, B:1322:0x1510, B:1323:0x1512, B:1319:0x14fe, B:1320:0x1500, B:1316:0x14ec, B:1317:0x14ee, B:1313:0x14da, B:1314:0x14dc, B:1310:0x14c8, B:1311:0x14ca, B:1307:0x14b8, B:1308:0x14ba, B:1304:0x14a9, B:1305:0x14ab, B:1299:0x149b, B:1301:0x149f, B:1296:0x148b, B:1297:0x148d, B:1293:0x1479, B:1294:0x147b, B:1290:0x1467, B:1291:0x1469, B:1287:0x1455, B:1288:0x1457, B:1284:0x1445, B:1285:0x1447, B:1281:0x1435, B:1282:0x1437, B:1278:0x1423, B:1279:0x1425, B:1275:0x1413, B:1276:0x1415, B:1272:0x1403, B:1273:0x1405, B:1269:0x13f1, B:1270:0x13f3, B:1266:0x13df, B:1267:0x13e1, B:1263:0x13cd, B:1264:0x13cf, B:1260:0x13bb, B:1261:0x13bd, B:1257:0x13ae, B:1258:0x13b0, B:1252:0x13a2, B:1254:0x13a6, B:1249:0x1392, B:1250:0x1394, B:1246:0x1380, B:1247:0x1382, B:1243:0x136e, B:1244:0x1370, B:1240:0x135c, B:1241:0x135e, B:1237:0x134a, B:1238:0x134c, B:1234:0x133a, B:1235:0x133c, B:1231:0x132a, B:1232:0x132c, B:1228:0x131d, B:1229:0x131f, B:1223:0x1311, B:1225:0x1315, B:1220:0x1301, B:1221:0x1303, B:1217:0x12f2, B:1218:0x12f4, B:1212:0x12e6, B:1214:0x12ea, B:1209:0x12d6, B:1210:0x12d8, B:1206:0x12c4, B:1207:0x12c6, B:1203:0x12b2, B:1204:0x12b4, B:1200:0x12a5, B:1201:0x12a7, B:1195:0x1297, B:1197:0x129b, B:1192:0x1287, B:1193:0x1289, B:1189:0x1277, B:1190:0x1279, B:1186:0x1265, B:1187:0x1267, B:1183:0x1255, B:1184:0x1257, B:1180:0x1243, B:1181:0x1245, B:1177:0x1233, B:1178:0x1235, B:1174:0x1224, B:1175:0x1226, B:1169:0x1218, B:1171:0x121c, B:1166:0x1208, B:1167:0x120a, B:1163:0x11f6, B:1164:0x11f8, B:1160:0x11e4, B:1161:0x11e6, B:1157:0x11d4, B:1158:0x11d6, B:1154:0x11c4, B:1155:0x11c6, B:1151:0x11b4, B:1152:0x11b6, B:1148:0x11a5, B:1149:0x11a7, B:1142:0x1191, B:1145:0x1197, B:1139:0x1181, B:1140:0x1183, B:1136:0x116f, B:1137:0x1171, B:1133:0x115d, B:1134:0x115f, B:1130:0x114d, B:1131:0x114f, B:1127:0x113b, B:1128:0x113d, B:1124:0x112b, B:1125:0x112d, B:1121:0x111e, B:1122:0x1120, B:1116:0x1112, B:1118:0x1116, B:1113:0x1102, B:1114:0x1104, B:1110:0x10f0, B:1111:0x10f2, B:1107:0x10de, B:1108:0x10e0, B:1104:0x10ce, B:1105:0x10d0, B:1101:0x10c1, B:1102:0x10c3, B:1096:0x10b5, B:1098:0x10b9, B:1093:0x10a5, B:1094:0x10a7, B:1090:0x1093, B:1091:0x1095, B:1087:0x1081, B:1088:0x1083, B:1084:0x1071, B:1085:0x1073, B:1081:0x1061, B:1082:0x1063, B:1078:0x1052, B:1079:0x1054, B:1073:0x1046, B:1075:0x104a, B:1070:0x1036, B:1071:0x1038, B:1067:0x1024, B:1068:0x1026, B:1064:0x1012, B:1065:0x1014, B:1061:0x1000, B:1062:0x1002, B:1058:0x0fee, B:1059:0x0ff0, B:1055:0x0fdf, B:1056:0x0fe1, B:1050:0x0fd3, B:1052:0x0fd7, B:1047:0x0fc3, B:1048:0x0fc5, B:1044:0x0fb3, B:1045:0x0fb5, B:1041:0x0fa1, B:1042:0x0fa3, B:1038:0x0f91, B:1039:0x0f93, B:1035:0x0f7f, B:1036:0x0f81, B:1032:0x0f6f, B:1033:0x0f71, B:1029:0x0f5d, B:1030:0x0f5f, B:1026:0x0f50, B:1027:0x0f52, B:1021:0x0f44, B:1023:0x0f48, B:1018:0x0f34, B:1019:0x0f36, B:1015:0x0f22, B:1016:0x0f24, B:1012:0x0f10, B:1013:0x0f12, B:1009:0x0efe, B:1010:0x0f00, B:1006:0x0eef, B:1007:0x0ef1, B:1001:0x0ee1, B:1003:0x0ee5, B:998:0x0ed1, B:999:0x0ed3, B:995:0x0ebf, B:996:0x0ec1, B:992:0x0eaf, B:993:0x0eb1, B:989:0x0e9d, B:990:0x0e9f, B:986:0x0e8b, B:987:0x0e8d, B:983:0x0e79, B:984:0x0e7b, B:980:0x0e69, B:981:0x0e6b, B:977:0x0e5a, B:978:0x0e5c, B:972:0x0e4c, B:974:0x0e50, B:969:0x0e3c, B:970:0x0e3e, B:966:0x0e2a, B:967:0x0e2c, B:963:0x0e1a, B:964:0x0e1c, B:960:0x0e0a, B:961:0x0e0c, B:957:0x0df8, B:958:0x0dfa, B:954:0x0de8, B:955:0x0dea, B:951:0x0dd6, B:952:0x0dd8, B:948:0x0dc4, B:949:0x0dc6, B:945:0x0db7, B:946:0x0db9, B:940:0x0da9, B:942:0x0dad, B:937:0x0d99, B:938:0x0d9b, B:934:0x0d87, B:935:0x0d89, B:931:0x0d77, B:932:0x0d79, B:928:0x0d65, B:929:0x0d67, B:925:0x0d53, B:926:0x0d55, B:922:0x0d44, B:923:0x0d46, B:916:0x0d30, B:919:0x0d36, B:913:0x0d20, B:914:0x0d22, B:910:0x0d0e, B:911:0x0d10, B:907:0x0cfc, B:908:0x0cfe, B:904:0x0cea, B:905:0x0cec, B:901:0x0cd8, B:902:0x0cda, B:898:0x0ccb, B:899:0x0ccd, B:892:0x0cbb, B:895:0x0cc1, B:889:0x0cab, B:890:0x0cad, B:886:0x0c99, B:887:0x0c9b, B:883:0x0c87, B:884:0x0c89, B:880:0x0c75, B:881:0x0c77, B:877:0x0c63, B:878:0x0c65, B:874:0x0c53, B:875:0x0c55, B:871:0x0c43, B:872:0x0c45, B:866:0x0c37, B:868:0x0c3b, B:863:0x0c25, B:864:0x0c27, B:860:0x0c1a, B:861:0x0c1c, B:857:0x0c0a, B:858:0x0c0c, B:854:0x0bfd, B:855:0x0bff, B:849:0x0bf1, B:851:0x0bf5, B:846:0x0be6, B:847:0x0be8, B:843:0x0bd6, B:844:0x0bd8, B:836:0x0bbe, B:841:0x0bc8, B:833:0x0baa, B:834:0x0bac, B:830:0x0b98, B:831:0x0b9a, B:827:0x0b88, B:828:0x0b8a, B:824:0x0b7c, B:825:0x0b7e, B:821:0x0b6d, B:822:0x0b6f, B:818:0x0b5e, B:819:0x0b60, B:815:0x0b4a, B:816:0x0b4c, B:1637:0x1adf, B:810:0x0b37, B:812:0x0b3b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:2083:0x2556  */
    /* JADX WARN: Removed duplicated region for block: B:2090:0x2569 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:2092:0x2568 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_0(int r30, int r31) {
        /*
            Method dump skipped, instructions count: 11646
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_0(int, int):int");
    }

    private final int jjStopStringLiteralDfa_2(int i, long j, long j2) {
        if (i == 0) {
            if ((562949953421312L & j2) != 0) {
                return 2;
            }
            if ((2199023255552L & j2) != 0) {
                return 39;
            }
            if ((252201579134320640L & j2) == 0) {
                return (1099603902464L & j2) != 0 ? 43 : -1;
            }
            this.jjmatchedKind = 122;
            return 34;
        }
        if (i == 1) {
            if ((108086391056891904L & j2) != 0) {
                return 34;
            }
            if ((j2 & 144115188077428736L) == 0) {
                return (1099595513856L & j2) != 0 ? 42 : -1;
            }
            if (this.jjmatchedPos != 1) {
                this.jjmatchedKind = 122;
                this.jjmatchedPos = 1;
            }
            return 34;
        }
        if (i == 2) {
            if ((j2 & 144115188077428736L) == 0) {
                return -1;
            }
            this.jjmatchedKind = 122;
            this.jjmatchedPos = 2;
            return 34;
        }
        if (i != 3) {
            return -1;
        }
        if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j2) != 0) {
            return 34;
        }
        if ((j2 & 144115188076380160L) == 0) {
            return -1;
        }
        this.jjmatchedKind = 122;
        this.jjmatchedPos = 3;
        return 34;
    }

    private final int jjStartNfa_2(int i, long j, long j2) {
        return jjMoveNfa_2(jjStopStringLiteralDfa_2(i, j, j2), i + 1);
    }

    private final int jjStartNfaWithStates_2(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_2(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_2() {
        char c = this.curChar;
        if (c == '!') {
            this.jjmatchedKind = 109;
            return jjMoveStringLiteralDfa1_2(2147483648L);
        }
        if (c == '%') {
            return jjStopAtPos(0, 106);
        }
        if (c == '[') {
            return jjStartNfaWithStates_2(0, 113, 2);
        }
        if (c == ']') {
            return jjStopAtPos(0, 114);
        }
        if (c == 'a') {
            return jjMoveStringLiteralDfa1_2(72057594037927936L);
        }
        if (c == 'f') {
            return jjMoveStringLiteralDfa1_2(PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
        }
        if (c == 'i') {
            return jjMoveStringLiteralDfa1_2(36028797018963968L);
        }
        if (c == '{') {
            return jjStopAtPos(0, 117);
        }
        if (c == '}') {
            return jjStopAtPos(0, 118);
        }
        if (c == ':') {
            return jjStopAtPos(0, 112);
        }
        if (c == ';') {
            return jjStopAtPos(0, 111);
        }
        if (c == 't') {
            return jjMoveStringLiteralDfa1_2(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
        }
        if (c != 'u') {
            switch (c) {
                case '(':
                    return jjStopAtPos(0, 115);
                case ')':
                    return jjStopAtPos(0, 116);
                case '*':
                    this.jjmatchedKind = 102;
                    return jjMoveStringLiteralDfa1_2(549755813888L);
                case '+':
                    return jjStopAtPos(0, 100);
                case ',':
                    return jjStopAtPos(0, 110);
                case '-':
                    return jjStopAtPos(0, 101);
                case '.':
                    this.jjmatchedKind = 87;
                    return jjMoveStringLiteralDfa1_2(1099595513856L);
                case '/':
                    return jjStartNfaWithStates_2(0, 105, 39);
                default:
                    switch (c) {
                        case '=':
                            this.jjmatchedKind = 93;
                            return jjMoveStringLiteralDfa1_2(1073741824L);
                        case '>':
                            return jjStopAtPos(0, 126);
                        case '?':
                            this.jjmatchedKind = 91;
                            return jjMoveStringLiteralDfa1_2(268435456L);
                        default:
                            return jjMoveNfa_2(1, 0);
                    }
            }
        }
        return jjMoveStringLiteralDfa1_2(144115188075855872L);
    }

    private final int jjMoveStringLiteralDfa1_2(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c == '.') {
                    if ((16777216 & j) != 0) {
                        this.jjmatchedKind = 88;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_2(j, 1099578736640L);
                }
                if (c != '=') {
                    if (c != '?') {
                        if (c == 'a') {
                            return jjMoveStringLiteralDfa2_2(j, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                        }
                        if (c != 'n') {
                            if (c == 'r') {
                                return jjMoveStringLiteralDfa2_2(j, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                            }
                            if (c == 's') {
                                if ((72057594037927936L & j) != 0) {
                                    return jjStartNfaWithStates_2(1, 120, 34);
                                }
                                return jjMoveStringLiteralDfa2_2(j, 144115188075855872L);
                            }
                        } else if ((36028797018963968L & j) != 0) {
                            return jjStartNfaWithStates_2(1, 119, 34);
                        }
                    } else if ((268435456 & j) != 0) {
                        return jjStopAtPos(1, 92);
                    }
                } else {
                    if ((1073741824 & j) != 0) {
                        return jjStopAtPos(1, 94);
                    }
                    if ((2147483648L & j) != 0) {
                        return jjStopAtPos(1, 95);
                    }
                }
            } else if ((549755813888L & j) != 0) {
                return jjStopAtPos(1, 103);
            }
            return jjStartNfa_2(0, 0L, j);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_2(0, 0L, j);
            return 1;
        }
    }

    private final int jjMoveStringLiteralDfa2_2(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_2(0, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c != '.') {
                    if (c == 'i') {
                        return jjMoveStringLiteralDfa3_2(j3, 144115188075855872L);
                    }
                    if (c == 'l') {
                        return jjMoveStringLiteralDfa3_2(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                    }
                    if (c == 'u') {
                        return jjMoveStringLiteralDfa3_2(j3, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                    }
                } else if ((1099511627776L & j3) != 0) {
                    return jjStopAtPos(2, 104);
                }
            } else if ((67108864 & j3) != 0) {
                return jjStopAtPos(2, 90);
            }
            return jjStartNfa_2(1, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_2(1, 0L, j3);
            return 2;
        }
    }

    private final int jjMoveStringLiteralDfa3_2(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_2(1, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'n') {
                    return jjMoveStringLiteralDfa4_2(j3, 144115188075855872L);
                }
                if (c == 's') {
                    return jjMoveStringLiteralDfa4_2(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_2(3, 84, 34);
            }
            return jjStartNfa_2(2, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_2(2, 0L, j3);
            return 3;
        }
    }

    private final int jjMoveStringLiteralDfa4_2(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_2(2, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'g' && (144115188075855872L & j3) != 0) {
                    return jjStartNfaWithStates_2(4, 121, 34);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_2(4, 83, 34);
            }
            return jjStartNfa_2(3, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_2(3, 0L, j3);
            return 4;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x01c8, code lost:
    
        if (r5 > 82) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x01f2, code lost:
    
        if (r5 > 82) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01f4, code lost:
    
        r1 = 82;
     */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01f7 A[PHI: r5
      0x01f7: PHI (r5v20 int) = 
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v39 int)
      (r5v19 int)
      (r5v41 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v43 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v44 int)
      (r5v19 int)
      (r5v46 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v48 int)
      (r5v19 int)
      (r5v19 int)
      (r5v50 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v51 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
      (r5v19 int)
     binds: [B:10:0x0047, B:169:0x0272, B:170:0x0274, B:166:0x0268, B:167:0x026a, B:163:0x025a, B:164:0x025c, B:160:0x0243, B:161:0x0245, B:153:0x022a, B:150:0x021c, B:151:0x021e, B:147:0x020e, B:148:0x0210, B:144:0x0202, B:145:0x0204, B:137:0x01ee, B:139:0x01f2, B:134:0x01e2, B:135:0x01e4, B:131:0x01d4, B:132:0x01d6, B:126:0x01c4, B:128:0x01c8, B:122:0x01ba, B:123:0x01bc, B:117:0x01af, B:119:0x01b3, B:114:0x01a3, B:115:0x01a5, B:109:0x0198, B:111:0x019c, B:103:0x0188, B:107:0x018f, B:97:0x0178, B:101:0x017f, B:94:0x0168, B:95:0x016a, B:91:0x015d, B:92:0x015f, B:88:0x0150, B:89:0x0152, B:83:0x0142, B:85:0x0146, B:80:0x0133, B:81:0x0135, B:76:0x012c, B:77:0x012e, B:74:0x0127, B:70:0x0113, B:71:0x0115, B:75:0x0129, B:64:0x0100, B:65:0x0102, B:61:0x00ee, B:62:0x00f0, B:55:0x00dc, B:59:0x00e3, B:49:0x00ca, B:53:0x00d1, B:46:0x00bb, B:47:0x00bd, B:43:0x00ae, B:44:0x00b0, B:36:0x009c, B:41:0x00a5, B:33:0x008d, B:34:0x008f, B:124:0x01be, B:29:0x0083, B:30:0x0085, B:26:0x0078, B:27:0x007a, B:21:0x006c, B:23:0x0070, B:141:0x01f6, B:16:0x0060, B:18:0x0064, B:13:0x004e, B:14:0x0050] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:430:0x06ba  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0129  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_2(int r28, int r29) {
        /*
            Method dump skipped, instructions count: 2218
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_2(int, int):int");
    }

    private final int jjStopStringLiteralDfa_3(int i, long j, long j2, long j3) {
        if (i == 0) {
            if ((562949953421312L & j2) != 0) {
                return 2;
            }
            if ((252201579134320640L & j2) == 0) {
                return (1099603902464L & j2) != 0 ? 40 : -1;
            }
            this.jjmatchedKind = 122;
            return 34;
        }
        if (i == 1) {
            if ((108086391056891904L & j2) != 0) {
                return 34;
            }
            if ((j2 & 144115188077428736L) == 0) {
                return (1099595513856L & j2) != 0 ? 39 : -1;
            }
            if (this.jjmatchedPos != 1) {
                this.jjmatchedKind = 122;
                this.jjmatchedPos = 1;
            }
            return 34;
        }
        if (i == 2) {
            if ((j2 & 144115188077428736L) == 0) {
                return -1;
            }
            this.jjmatchedKind = 122;
            this.jjmatchedPos = 2;
            return 34;
        }
        if (i != 3) {
            return -1;
        }
        if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j2) != 0) {
            return 34;
        }
        if ((j2 & 144115188076380160L) == 0) {
            return -1;
        }
        this.jjmatchedKind = 122;
        this.jjmatchedPos = 3;
        return 34;
    }

    private final int jjStartNfa_3(int i, long j, long j2, long j3) {
        return jjMoveNfa_3(jjStopStringLiteralDfa_3(i, j, j2, j3), i + 1);
    }

    private final int jjStartNfaWithStates_3(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_3(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_3() {
        char c = this.curChar;
        if (c == '!') {
            this.jjmatchedKind = 109;
            return jjMoveStringLiteralDfa1_3(2147483648L, 0L);
        }
        if (c == '%') {
            return jjStopAtPos(0, 106);
        }
        if (c == '[') {
            return jjStartNfaWithStates_3(0, 113, 2);
        }
        if (c == ']') {
            return jjStopAtPos(0, 114);
        }
        if (c == 'a') {
            return jjMoveStringLiteralDfa1_3(72057594037927936L, 0L);
        }
        if (c == 'f') {
            return jjMoveStringLiteralDfa1_3(PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, 0L);
        }
        if (c == 'i') {
            return jjMoveStringLiteralDfa1_3(36028797018963968L, 0L);
        }
        if (c == '{') {
            return jjStopAtPos(0, 117);
        }
        if (c == '}') {
            return jjStopAtPos(0, 118);
        }
        if (c == ':') {
            return jjStopAtPos(0, 112);
        }
        if (c == ';') {
            return jjStopAtPos(0, 111);
        }
        if (c == 't') {
            return jjMoveStringLiteralDfa1_3(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED, 0L);
        }
        if (c != 'u') {
            switch (c) {
                case '(':
                    return jjStopAtPos(0, 115);
                case ')':
                    return jjStopAtPos(0, 116);
                case '*':
                    this.jjmatchedKind = 102;
                    return jjMoveStringLiteralDfa1_3(549755813888L, 0L);
                case '+':
                    return jjStopAtPos(0, 100);
                case ',':
                    return jjStopAtPos(0, 110);
                case '-':
                    return jjStopAtPos(0, 101);
                case '.':
                    this.jjmatchedKind = 87;
                    return jjMoveStringLiteralDfa1_3(1099595513856L, 0L);
                case '/':
                    return jjStopAtPos(0, 105);
                default:
                    switch (c) {
                        case '=':
                            this.jjmatchedKind = 93;
                            return jjMoveStringLiteralDfa1_3(1073741824L, 0L);
                        case '>':
                            this.jjmatchedKind = 128;
                            return jjMoveStringLiteralDfa1_3(0L, 2L);
                        case '?':
                            this.jjmatchedKind = 91;
                            return jjMoveStringLiteralDfa1_3(268435456L, 0L);
                        default:
                            return jjMoveNfa_3(1, 0);
                    }
            }
        }
        return jjMoveStringLiteralDfa1_3(144115188075855872L, 0L);
    }

    private final int jjMoveStringLiteralDfa1_3(long j, long j2) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c == '.') {
                    if ((16777216 & j) != 0) {
                        this.jjmatchedKind = 88;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_3(j, 1099578736640L, j2, 0L);
                }
                if (c != '=') {
                    if (c != '?') {
                        if (c == 'a') {
                            return jjMoveStringLiteralDfa2_3(j, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED, j2, 0L);
                        }
                        if (c != 'n') {
                            if (c == 'r') {
                                return jjMoveStringLiteralDfa2_3(j, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED, j2, 0L);
                            }
                            if (c == 's') {
                                if ((72057594037927936L & j) != 0) {
                                    return jjStartNfaWithStates_3(1, 120, 34);
                                }
                                return jjMoveStringLiteralDfa2_3(j, 144115188075855872L, j2, 0L);
                            }
                        } else if ((36028797018963968L & j) != 0) {
                            return jjStartNfaWithStates_3(1, 119, 34);
                        }
                    } else if ((268435456 & j) != 0) {
                        return jjStopAtPos(1, 92);
                    }
                } else {
                    if ((1073741824 & j) != 0) {
                        return jjStopAtPos(1, 94);
                    }
                    if ((2147483648L & j) != 0) {
                        return jjStopAtPos(1, 95);
                    }
                    if ((2 & j2) != 0) {
                        return jjStopAtPos(1, 129);
                    }
                }
            } else if ((549755813888L & j) != 0) {
                return jjStopAtPos(1, 103);
            }
            return jjStartNfa_3(0, 0L, j, j2);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_3(0, 0L, j, j2);
            return 1;
        }
    }

    private final int jjMoveStringLiteralDfa2_3(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        if (((j4 & j3) | j5) == 0) {
            return jjStartNfa_3(0, 0L, j, j3);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c != '.') {
                    if (c == 'i') {
                        return jjMoveStringLiteralDfa3_3(j5, 144115188075855872L);
                    }
                    if (c == 'l') {
                        return jjMoveStringLiteralDfa3_3(j5, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                    }
                    if (c == 'u') {
                        return jjMoveStringLiteralDfa3_3(j5, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                    }
                } else if ((1099511627776L & j5) != 0) {
                    return jjStopAtPos(2, 104);
                }
            } else if ((67108864 & j5) != 0) {
                return jjStopAtPos(2, 90);
            }
            return jjStartNfa_3(1, 0L, j5, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_3(1, 0L, j5, 0L);
            return 2;
        }
    }

    private final int jjMoveStringLiteralDfa3_3(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_3(1, 0L, j, 0L);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'n') {
                    return jjMoveStringLiteralDfa4_3(j3, 144115188075855872L);
                }
                if (c == 's') {
                    return jjMoveStringLiteralDfa4_3(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_3(3, 84, 34);
            }
            return jjStartNfa_3(2, 0L, j3, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_3(2, 0L, j3, 0L);
            return 3;
        }
    }

    private final int jjMoveStringLiteralDfa4_3(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_3(2, 0L, j, 0L);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'g' && (144115188075855872L & j3) != 0) {
                    return jjStartNfaWithStates_3(4, 121, 34);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_3(4, 83, 34);
            }
            return jjStartNfa_3(3, 0L, j3, 0L);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_3(3, 0L, j3, 0L);
            return 4;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    /* JADX WARN: Failed to find 'out' block for switch in B:38:0x007e. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_3(int r31, int r32) {
        /*
            Method dump skipped, instructions count: 2264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_3(int, int):int");
    }

    private final int jjStartNfa_5(int i, long j, long j2) {
        return jjMoveNfa_5(jjStopStringLiteralDfa_5(i, j, j2), i + 1);
    }

    private final int jjStartNfaWithStates_5(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_5(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_5() {
        if (this.curChar == '-') {
            return jjStartNfaWithStates_5(0, 78, 3);
        }
        return jjMoveNfa_5(1, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x011f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x011e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_5(int r27, int r28) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_5(int, int):int");
    }

    private final int jjStopStringLiteralDfa_6(int i, long j, long j2) {
        if (i == 0) {
            if ((2199023255552L & j2) != 0) {
                return 35;
            }
            if ((1099603902464L & j2) != 0) {
                return 39;
            }
            if ((j2 & 252201579134320640L) == 0) {
                return -1;
            }
            this.jjmatchedKind = 122;
            return 29;
        }
        if (i == 1) {
            if ((1099595513856L & j2) != 0) {
                return 38;
            }
            if ((144115188077428736L & j2) == 0) {
                return (108086391056891904L & j2) != 0 ? 29 : -1;
            }
            if (this.jjmatchedPos != 1) {
                this.jjmatchedKind = 122;
                this.jjmatchedPos = 1;
            }
            return 29;
        }
        if (i == 2) {
            if ((j2 & 144115188077428736L) == 0) {
                return -1;
            }
            this.jjmatchedKind = 122;
            this.jjmatchedPos = 2;
            return 29;
        }
        if (i != 3) {
            return -1;
        }
        if ((144115188076380160L & j2) == 0) {
            return (PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j2) != 0 ? 29 : -1;
        }
        this.jjmatchedKind = 122;
        this.jjmatchedPos = 3;
        return 29;
    }

    private final int jjStartNfa_6(int i, long j, long j2) {
        return jjMoveNfa_6(jjStopStringLiteralDfa_6(i, j, j2), i + 1);
    }

    private final int jjStartNfaWithStates_6(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_6(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_6() {
        char c = this.curChar;
        if (c == '!') {
            this.jjmatchedKind = 109;
            return jjMoveStringLiteralDfa1_6(2147483648L);
        }
        if (c == '%') {
            return jjStopAtPos(0, 106);
        }
        if (c == '[') {
            return jjStopAtPos(0, 113);
        }
        if (c == ']') {
            return jjStopAtPos(0, 114);
        }
        if (c == 'a') {
            return jjMoveStringLiteralDfa1_6(72057594037927936L);
        }
        if (c == 'f') {
            return jjMoveStringLiteralDfa1_6(PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
        }
        if (c == 'i') {
            return jjMoveStringLiteralDfa1_6(36028797018963968L);
        }
        if (c == '{') {
            return jjStopAtPos(0, 117);
        }
        if (c == '}') {
            return jjStopAtPos(0, 118);
        }
        if (c == ':') {
            return jjStopAtPos(0, 112);
        }
        if (c == ';') {
            return jjStopAtPos(0, 111);
        }
        if (c == 't') {
            return jjMoveStringLiteralDfa1_6(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
        }
        if (c != 'u') {
            switch (c) {
                case '(':
                    return jjStopAtPos(0, 115);
                case ')':
                    return jjStopAtPos(0, 116);
                case '*':
                    this.jjmatchedKind = 102;
                    return jjMoveStringLiteralDfa1_6(549755813888L);
                case '+':
                    return jjStopAtPos(0, 100);
                case ',':
                    return jjStopAtPos(0, 110);
                case '-':
                    return jjStopAtPos(0, 101);
                case '.':
                    this.jjmatchedKind = 87;
                    return jjMoveStringLiteralDfa1_6(1099595513856L);
                case '/':
                    return jjStartNfaWithStates_6(0, 105, 35);
                default:
                    switch (c) {
                        case '=':
                            this.jjmatchedKind = 93;
                            return jjMoveStringLiteralDfa1_6(1073741824L);
                        case '>':
                            return jjStopAtPos(0, 126);
                        case '?':
                            this.jjmatchedKind = 91;
                            return jjMoveStringLiteralDfa1_6(268435456L);
                        default:
                            return jjMoveNfa_6(0, 0);
                    }
            }
        }
        return jjMoveStringLiteralDfa1_6(144115188075855872L);
    }

    private final int jjMoveStringLiteralDfa1_6(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c == '.') {
                    if ((16777216 & j) != 0) {
                        this.jjmatchedKind = 88;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_6(j, 1099578736640L);
                }
                if (c != '=') {
                    if (c != '?') {
                        if (c == 'a') {
                            return jjMoveStringLiteralDfa2_6(j, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                        }
                        if (c != 'n') {
                            if (c == 'r') {
                                return jjMoveStringLiteralDfa2_6(j, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                            }
                            if (c == 's') {
                                if ((72057594037927936L & j) != 0) {
                                    return jjStartNfaWithStates_6(1, 120, 29);
                                }
                                return jjMoveStringLiteralDfa2_6(j, 144115188075855872L);
                            }
                        } else if ((36028797018963968L & j) != 0) {
                            return jjStartNfaWithStates_6(1, 119, 29);
                        }
                    } else if ((268435456 & j) != 0) {
                        return jjStopAtPos(1, 92);
                    }
                } else {
                    if ((1073741824 & j) != 0) {
                        return jjStopAtPos(1, 94);
                    }
                    if ((2147483648L & j) != 0) {
                        return jjStopAtPos(1, 95);
                    }
                }
            } else if ((549755813888L & j) != 0) {
                return jjStopAtPos(1, 103);
            }
            return jjStartNfa_6(0, 0L, j);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_6(0, 0L, j);
            return 1;
        }
    }

    private final int jjMoveStringLiteralDfa2_6(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_6(0, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c != '.') {
                    if (c == 'i') {
                        return jjMoveStringLiteralDfa3_6(j3, 144115188075855872L);
                    }
                    if (c == 'l') {
                        return jjMoveStringLiteralDfa3_6(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                    }
                    if (c == 'u') {
                        return jjMoveStringLiteralDfa3_6(j3, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                    }
                } else if ((1099511627776L & j3) != 0) {
                    return jjStopAtPos(2, 104);
                }
            } else if ((67108864 & j3) != 0) {
                return jjStopAtPos(2, 90);
            }
            return jjStartNfa_6(1, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_6(1, 0L, j3);
            return 2;
        }
    }

    private final int jjMoveStringLiteralDfa3_6(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_6(1, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'n') {
                    return jjMoveStringLiteralDfa4_6(j3, 144115188075855872L);
                }
                if (c == 's') {
                    return jjMoveStringLiteralDfa4_6(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_6(3, 84, 29);
            }
            return jjStartNfa_6(2, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_6(2, 0L, j3);
            return 3;
        }
    }

    private final int jjMoveStringLiteralDfa4_6(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_6(2, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'g' && (144115188075855872L & j3) != 0) {
                    return jjStartNfaWithStates_6(4, 121, 29);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_6(4, 83, 29);
            }
            return jjStartNfa_6(3, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_6(3, 0L, j3);
            return 4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x02a3 A[PHI: r3
      0x02a3: PHI (r3v158 int) = 
      (r3v94 int)
      (r3v109 int)
      (r3v115 int)
      (r3v118 int)
      (r3v134 int)
      (r3v67 int)
      (r3v67 int)
      (r3v67 int)
      (r3v157 int)
      (r3v161 int)
     binds: [B:178:0x02a1, B:147:0x0214, B:127:0x01d3, B:119:0x01ba, B:86:0x0146, B:77:0x012c, B:74:0x0126, B:65:0x0103, B:24:0x0074, B:19:0x0066] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x064a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_6(int r28, int r29) {
        /*
            Method dump skipped, instructions count: 2088
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_6(int, int):int");
    }

    private final int jjStopStringLiteralDfa_4(int i, long j, long j2) {
        if (i == 0) {
            if ((562949953421312L & j2) != 0) {
                return 2;
            }
            if ((252201579134320640L & j2) != 0) {
                this.jjmatchedKind = 122;
                return 34;
            }
            if ((1099603902464L & j2) != 0) {
                return 45;
            }
            if ((35186519572480L & j2) != 0) {
                return 39;
            }
            return (2199023255552L & j2) != 0 ? 41 : -1;
        }
        if (i == 1) {
            if ((108086391056891904L & j2) != 0) {
                return 34;
            }
            if ((j2 & 144115188077428736L) == 0) {
                return (1099595513856L & j2) != 0 ? 44 : -1;
            }
            if (this.jjmatchedPos != 1) {
                this.jjmatchedKind = 122;
                this.jjmatchedPos = 1;
            }
            return 34;
        }
        if (i == 2) {
            if ((j2 & 144115188077428736L) == 0) {
                return -1;
            }
            this.jjmatchedKind = 122;
            this.jjmatchedPos = 2;
            return 34;
        }
        if (i != 3) {
            return -1;
        }
        if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j2) != 0) {
            return 34;
        }
        if ((j2 & 144115188076380160L) == 0) {
            return -1;
        }
        this.jjmatchedKind = 122;
        this.jjmatchedPos = 3;
        return 34;
    }

    private final int jjStartNfa_4(int i, long j, long j2) {
        return jjMoveNfa_4(jjStopStringLiteralDfa_4(i, j, j2), i + 1);
    }

    private final int jjStartNfaWithStates_4(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_4(i3, i + 1);
        } catch (IOException unused) {
            return i + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_4() {
        char c = this.curChar;
        if (c == '!') {
            this.jjmatchedKind = 109;
            return jjMoveStringLiteralDfa1_4(2147483648L);
        }
        if (c == '%') {
            return jjStopAtPos(0, 106);
        }
        if (c == '[') {
            return jjStartNfaWithStates_4(0, 113, 2);
        }
        if (c == ']') {
            return jjStopAtPos(0, 114);
        }
        if (c == 'a') {
            return jjMoveStringLiteralDfa1_4(72057594037927936L);
        }
        if (c == 'f') {
            return jjMoveStringLiteralDfa1_4(PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
        }
        if (c == 'i') {
            return jjMoveStringLiteralDfa1_4(36028797018963968L);
        }
        if (c == '{') {
            return jjStopAtPos(0, 117);
        }
        if (c == '}') {
            return jjStopAtPos(0, 118);
        }
        if (c == ':') {
            return jjStopAtPos(0, 112);
        }
        if (c == ';') {
            return jjStopAtPos(0, 111);
        }
        if (c == 't') {
            return jjMoveStringLiteralDfa1_4(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
        }
        if (c != 'u') {
            switch (c) {
                case '(':
                    return jjStopAtPos(0, 115);
                case ')':
                    return jjStopAtPos(0, 116);
                case '*':
                    this.jjmatchedKind = 102;
                    return jjMoveStringLiteralDfa1_4(549755813888L);
                case '+':
                    return jjStopAtPos(0, 100);
                case ',':
                    return jjStopAtPos(0, 110);
                case '-':
                    return jjStopAtPos(0, 101);
                case '.':
                    this.jjmatchedKind = 87;
                    return jjMoveStringLiteralDfa1_4(1099595513856L);
                case '/':
                    return jjStartNfaWithStates_4(0, 105, 41);
                default:
                    switch (c) {
                        case '=':
                            this.jjmatchedKind = 93;
                            return jjMoveStringLiteralDfa1_4(1073741824L);
                        case '>':
                            return jjStopAtPos(0, 126);
                        case '?':
                            this.jjmatchedKind = 91;
                            return jjMoveStringLiteralDfa1_4(268435456L);
                        default:
                            return jjMoveNfa_4(1, 0);
                    }
            }
        }
        return jjMoveStringLiteralDfa1_4(144115188075855872L);
    }

    private final int jjMoveStringLiteralDfa1_4(long j) {
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c == '.') {
                    if ((16777216 & j) != 0) {
                        this.jjmatchedKind = 88;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_4(j, 1099578736640L);
                }
                if (c != '=') {
                    if (c != '?') {
                        if (c == 'a') {
                            return jjMoveStringLiteralDfa2_4(j, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                        }
                        if (c != 'n') {
                            if (c == 'r') {
                                return jjMoveStringLiteralDfa2_4(j, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                            }
                            if (c == 's') {
                                if ((72057594037927936L & j) != 0) {
                                    return jjStartNfaWithStates_4(1, 120, 34);
                                }
                                return jjMoveStringLiteralDfa2_4(j, 144115188075855872L);
                            }
                        } else if ((36028797018963968L & j) != 0) {
                            return jjStartNfaWithStates_4(1, 119, 34);
                        }
                    } else if ((268435456 & j) != 0) {
                        return jjStopAtPos(1, 92);
                    }
                } else {
                    if ((1073741824 & j) != 0) {
                        return jjStopAtPos(1, 94);
                    }
                    if ((2147483648L & j) != 0) {
                        return jjStopAtPos(1, 95);
                    }
                }
            } else if ((549755813888L & j) != 0) {
                return jjStopAtPos(1, 103);
            }
            return jjStartNfa_4(0, 0L, j);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_4(0, 0L, j);
            return 1;
        }
    }

    private final int jjMoveStringLiteralDfa2_4(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_4(0, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != '*') {
                if (c != '.') {
                    if (c == 'i') {
                        return jjMoveStringLiteralDfa3_4(j3, 144115188075855872L);
                    }
                    if (c == 'l') {
                        return jjMoveStringLiteralDfa3_4(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                    }
                    if (c == 'u') {
                        return jjMoveStringLiteralDfa3_4(j3, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
                    }
                } else if ((1099511627776L & j3) != 0) {
                    return jjStopAtPos(2, 104);
                }
            } else if ((67108864 & j3) != 0) {
                return jjStopAtPos(2, 90);
            }
            return jjStartNfa_4(1, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_4(1, 0L, j3);
            return 2;
        }
    }

    private final int jjMoveStringLiteralDfa3_4(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_4(1, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'n') {
                    return jjMoveStringLiteralDfa4_4(j3, 144115188075855872L);
                }
                if (c == 's') {
                    return jjMoveStringLiteralDfa4_4(j3, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_4(3, 84, 34);
            }
            return jjStartNfa_4(2, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_4(2, 0L, j3);
            return 3;
        }
    }

    private final int jjMoveStringLiteralDfa4_4(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_4(2, 0L, j);
        }
        try {
            char c = this.input_stream.readChar();
            this.curChar = c;
            if (c != 'e') {
                if (c == 'g' && (144115188075855872L & j3) != 0) {
                    return jjStartNfaWithStates_4(4, 121, 34);
                }
            } else if ((PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED & j3) != 0) {
                return jjStartNfaWithStates_4(4, 83, 34);
            }
            return jjStartNfa_4(3, 0L, j3);
        } catch (IOException unused) {
            jjStopStringLiteralDfa_4(3, 0L, j3);
            return 4;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x06db, code lost:
    
        if (r6 != 34) goto L447;
     */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0570 A[PHI: r4
      0x0570: PHI (r4v7 int) = 
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v12 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v13 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v15 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v16 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
      (r4v6 int)
     binds: [B:269:0x03fd, B:401:0x0632, B:402:0x0634, B:398:0x0621, B:399:0x0623, B:395:0x0612, B:396:0x0614, B:392:0x05fc, B:393:0x05fe, B:389:0x05ed, B:390:0x05ef, B:386:0x05db, B:387:0x05dd, B:383:0x05cb, B:384:0x05cd, B:380:0x05bd, B:381:0x05bf, B:377:0x05a8, B:378:0x05aa, B:374:0x059a, B:375:0x059c, B:371:0x0588, B:372:0x058a, B:369:0x057c, B:368:0x0574, B:364:0x056b, B:365:0x056d, B:366:0x056f, B:361:0x055a, B:362:0x055c, B:353:0x0543, B:359:0x0552, B:347:0x0531, B:349:0x0535, B:342:0x0524, B:344:0x0528, B:339:0x0516, B:340:0x0518, B:335:0x050e, B:336:0x0510, B:337:0x0512, B:330:0x0503, B:332:0x0507, B:327:0x04f6, B:328:0x04f8, B:324:0x04e9, B:325:0x04eb, B:321:0x04dc, B:322:0x04de, B:318:0x04ce, B:319:0x04d0, B:313:0x04c2, B:315:0x04c6, B:310:0x04b9, B:311:0x04bb, B:351:0x0539, B:305:0x04ad, B:307:0x04b1, B:302:0x04a0, B:303:0x04a2, B:299:0x0490, B:300:0x0492, B:296:0x0480, B:297:0x0482, B:293:0x0470, B:294:0x0472, B:290:0x0460, B:291:0x0462, B:287:0x0450, B:288:0x0452, B:284:0x0440, B:285:0x0442, B:281:0x0430, B:282:0x0432, B:278:0x0420, B:279:0x0422, B:275:0x0410, B:276:0x0412, B:272:0x0404] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:474:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x078d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:483:0x078c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:579:0x03e2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int jjMoveNfa_4(int r30, int r31) {
        /*
            Method dump skipped, instructions count: 2280
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.jjMoveNfa_4(int, int):int");
    }

    private static final boolean jjCanMove_0(int i, int i2, int i3, long j, long j2) {
        return i != 0 ? (jjbitVec0[i2] & j) != 0 : (jjbitVec2[i3] & j2) != 0;
    }

    private static final boolean jjCanMove_1(int i, int i2, int i3, long j, long j2) {
        return i != 0 ? i != 51 ? i != 61 ? i != 48 ? i != 49 ? (jjbitVec3[i2] & j) != 0 : (jjbitVec6[i3] & j2) != 0 : (jjbitVec5[i3] & j2) != 0 : (jjbitVec8[i3] & j2) != 0 : (jjbitVec7[i3] & j2) != 0 : (jjbitVec4[i3] & j2) != 0;
    }

    public FMParserTokenManager(SimpleCharStream simpleCharStream) {
        this.debugStream = System.out;
        this.jjrounds = new int[567];
        this.jjstateSet = new int[1134];
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.input_stream = simpleCharStream;
    }

    public FMParserTokenManager(SimpleCharStream simpleCharStream, int i) {
        this(simpleCharStream);
        SwitchTo(i);
    }

    public void ReInit(SimpleCharStream simpleCharStream) {
        this.jjnewStateCnt = 0;
        this.jjmatchedPos = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = simpleCharStream;
        ReInitRounds();
    }

    private final void ReInitRounds() {
        this.jjround = -2147483647;
        int i = 567;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            this.jjrounds[i2] = Integer.MIN_VALUE;
            i = i2;
        }
    }

    public void ReInit(SimpleCharStream simpleCharStream, int i) {
        ReInit(simpleCharStream);
        SwitchTo(i);
    }

    public void SwitchTo(int i) {
        if (i >= 8 || i < 0) {
            throw new TokenMgrError(new StringBuffer("Error: Ignoring invalid lexical state : ").append(i).append(". State unchanged.").toString(), 2);
        }
        this.curLexState = i;
    }

    protected Token jjFillToken() {
        Token tokenNewToken = Token.newToken(this.jjmatchedKind);
        tokenNewToken.kind = this.jjmatchedKind;
        String strGetImage = jjstrLiteralImages[this.jjmatchedKind];
        if (strGetImage == null) {
            strGetImage = this.input_stream.GetImage();
        }
        tokenNewToken.image = strGetImage;
        tokenNewToken.beginLine = this.input_stream.getBeginLine();
        tokenNewToken.beginColumn = this.input_stream.getBeginColumn();
        tokenNewToken.endLine = this.input_stream.getEndLine();
        tokenNewToken.endColumn = this.input_stream.getEndColumn();
        return tokenNewToken;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00db A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.mapstruct.ap.shaded.freemarker.core.Token getNextToken() {
        /*
            Method dump skipped, instructions count: 340
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParserTokenManager.getNextToken():org.mapstruct.ap.shaded.freemarker.core.Token");
    }

    void SkipLexicalActions(Token token) {
        if (this.jjmatchedKind != 79) {
            return;
        }
        StringBuffer stringBuffer = this.image;
        if (stringBuffer == null) {
            SimpleCharStream simpleCharStream = this.input_stream;
            int i = this.jjimageLen;
            int i2 = this.jjmatchedPos + 1;
            this.lengthOfMatch = i2;
            this.image = new StringBuffer(new String(simpleCharStream.GetSuffix(i + i2)));
        } else {
            SimpleCharStream simpleCharStream2 = this.input_stream;
            int i3 = this.jjimageLen;
            int i4 = this.jjmatchedPos + 1;
            this.lengthOfMatch = i4;
            stringBuffer.append(new String(simpleCharStream2.GetSuffix(i3 + i4)));
        }
        if (this.parenthesisNesting > 0) {
            SwitchTo(3);
        } else if (this.inInvocation) {
            SwitchTo(4);
        } else {
            SwitchTo(2);
        }
    }

    void TokenLexicalActions(Token token) {
        String string;
        int i = this.jjmatchedKind;
        if (i == 123) {
            StringBuffer stringBuffer = this.image;
            if (stringBuffer == null) {
                SimpleCharStream simpleCharStream = this.input_stream;
                int i2 = this.jjimageLen;
                int i3 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i3;
                this.image = new StringBuffer(new String(simpleCharStream.GetSuffix(i2 + i3)));
            } else {
                SimpleCharStream simpleCharStream2 = this.input_stream;
                int i4 = this.jjimageLen;
                int i5 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i5;
                stringBuffer.append(new String(simpleCharStream2.GetSuffix(i4 + i5)));
            }
            char cCharAt = token.image.charAt(0);
            throw new TokenMgrError(new StringBuffer("You can't use \"").append(cCharAt).append("{\" here as you are already in FreeMarker-expression-mode. Thus, instead of ").append(cCharAt).append("{myExpression}, just write myExpression. (").append(cCharAt).append("{...} is only needed where otherwise static text is expected, i.e, outside FreeMarker tags and ${...}-s.)").toString(), 0, token.beginLine, token.beginColumn, token.endLine, token.endColumn);
        }
        if (i == 126) {
            StringBuffer stringBuffer2 = this.image;
            if (stringBuffer2 == null) {
                this.image = new StringBuffer(jjstrLiteralImages[126]);
            } else {
                stringBuffer2.append(jjstrLiteralImages[126]);
            }
            if (this.inFTLHeader) {
                eatNewline();
            }
            this.inFTLHeader = false;
            if (this.squBracTagSyntax) {
                token.kind = 128;
                return;
            } else {
                SwitchTo(0);
                return;
            }
        }
        if (i == 127) {
            StringBuffer stringBuffer3 = this.image;
            if (stringBuffer3 == null) {
                SimpleCharStream simpleCharStream3 = this.input_stream;
                int i6 = this.jjimageLen;
                int i7 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i7;
                this.image = new StringBuffer(new String(simpleCharStream3.GetSuffix(i6 + i7)));
            } else {
                SimpleCharStream simpleCharStream4 = this.input_stream;
                int i8 = this.jjimageLen;
                int i9 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i9;
                stringBuffer3.append(new String(simpleCharStream4.GetSuffix(i8 + i9)));
            }
            if (this.inFTLHeader) {
                eatNewline();
            }
            this.inFTLHeader = false;
            SwitchTo(0);
            return;
        }
        if (i == 132) {
            StringBuffer stringBuffer4 = this.image;
            if (stringBuffer4 == null) {
                SimpleCharStream simpleCharStream5 = this.input_stream;
                int i10 = this.jjimageLen;
                int i11 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i11;
                this.image = new StringBuffer(new String(simpleCharStream5.GetSuffix(i10 + i11)));
            } else {
                SimpleCharStream simpleCharStream6 = this.input_stream;
                int i12 = this.jjimageLen;
                int i13 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i13;
                stringBuffer4.append(new String(simpleCharStream6.GetSuffix(i12 + i13)));
            }
            if (this.noparseTag.equals("-->")) {
                boolean zEndsWith = token.image.endsWith("]");
                boolean z = this.squBracTagSyntax;
                if (!(z && zEndsWith) && (z || zEndsWith)) {
                    return;
                }
                token.image = new StringBuffer().append(token.image).append(";").toString();
                SwitchTo(0);
                return;
            }
            return;
        }
        if (i != 133) {
            switch (i) {
                case 6:
                    StringBuffer stringBuffer5 = this.image;
                    if (stringBuffer5 == null) {
                        SimpleCharStream simpleCharStream7 = this.input_stream;
                        int i14 = this.jjimageLen;
                        int i15 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i15;
                        this.image = new StringBuffer(new String(simpleCharStream7.GetSuffix(i14 + i15)));
                    } else {
                        SimpleCharStream simpleCharStream8 = this.input_stream;
                        int i16 = this.jjimageLen;
                        int i17 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i17;
                        stringBuffer5.append(new String(simpleCharStream8.GetSuffix(i16 + i17)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 7:
                    StringBuffer stringBuffer6 = this.image;
                    if (stringBuffer6 == null) {
                        SimpleCharStream simpleCharStream9 = this.input_stream;
                        int i18 = this.jjimageLen;
                        int i19 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i19;
                        this.image = new StringBuffer(new String(simpleCharStream9.GetSuffix(i18 + i19)));
                    } else {
                        SimpleCharStream simpleCharStream10 = this.input_stream;
                        int i20 = this.jjimageLen;
                        int i21 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i21;
                        stringBuffer6.append(new String(simpleCharStream10.GetSuffix(i20 + i21)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 8:
                    StringBuffer stringBuffer7 = this.image;
                    if (stringBuffer7 == null) {
                        SimpleCharStream simpleCharStream11 = this.input_stream;
                        int i22 = this.jjimageLen;
                        int i23 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i23;
                        this.image = new StringBuffer(new String(simpleCharStream11.GetSuffix(i22 + i23)));
                    } else {
                        SimpleCharStream simpleCharStream12 = this.input_stream;
                        int i24 = this.jjimageLen;
                        int i25 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i25;
                        stringBuffer7.append(new String(simpleCharStream12.GetSuffix(i24 + i25)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 9:
                    StringBuffer stringBuffer8 = this.image;
                    if (stringBuffer8 == null) {
                        SimpleCharStream simpleCharStream13 = this.input_stream;
                        int i26 = this.jjimageLen;
                        int i27 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i27;
                        this.image = new StringBuffer(new String(simpleCharStream13.GetSuffix(i26 + i27)));
                    } else {
                        SimpleCharStream simpleCharStream14 = this.input_stream;
                        int i28 = this.jjimageLen;
                        int i29 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i29;
                        stringBuffer8.append(new String(simpleCharStream14.GetSuffix(i28 + i29)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 10:
                    StringBuffer stringBuffer9 = this.image;
                    if (stringBuffer9 == null) {
                        SimpleCharStream simpleCharStream15 = this.input_stream;
                        int i30 = this.jjimageLen;
                        int i31 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i31;
                        this.image = new StringBuffer(new String(simpleCharStream15.GetSuffix(i30 + i31)));
                    } else {
                        SimpleCharStream simpleCharStream16 = this.input_stream;
                        int i32 = this.jjimageLen;
                        int i33 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i33;
                        stringBuffer9.append(new String(simpleCharStream16.GetSuffix(i32 + i33)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 11:
                    StringBuffer stringBuffer10 = this.image;
                    if (stringBuffer10 == null) {
                        SimpleCharStream simpleCharStream17 = this.input_stream;
                        int i34 = this.jjimageLen;
                        int i35 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i35;
                        this.image = new StringBuffer(new String(simpleCharStream17.GetSuffix(i34 + i35)));
                    } else {
                        SimpleCharStream simpleCharStream18 = this.input_stream;
                        int i36 = this.jjimageLen;
                        int i37 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i37;
                        stringBuffer10.append(new String(simpleCharStream18.GetSuffix(i36 + i37)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 12:
                    StringBuffer stringBuffer11 = this.image;
                    if (stringBuffer11 == null) {
                        SimpleCharStream simpleCharStream19 = this.input_stream;
                        int i38 = this.jjimageLen;
                        int i39 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i39;
                        this.image = new StringBuffer(new String(simpleCharStream19.GetSuffix(i38 + i39)));
                    } else {
                        SimpleCharStream simpleCharStream20 = this.input_stream;
                        int i40 = this.jjimageLen;
                        int i41 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i41;
                        stringBuffer11.append(new String(simpleCharStream20.GetSuffix(i40 + i41)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 13:
                    StringBuffer stringBuffer12 = this.image;
                    if (stringBuffer12 == null) {
                        SimpleCharStream simpleCharStream21 = this.input_stream;
                        int i42 = this.jjimageLen;
                        int i43 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i43;
                        this.image = new StringBuffer(new String(simpleCharStream21.GetSuffix(i42 + i43)));
                    } else {
                        SimpleCharStream simpleCharStream22 = this.input_stream;
                        int i44 = this.jjimageLen;
                        int i45 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i45;
                        stringBuffer12.append(new String(simpleCharStream22.GetSuffix(i44 + i45)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 14:
                    StringBuffer stringBuffer13 = this.image;
                    if (stringBuffer13 == null) {
                        SimpleCharStream simpleCharStream23 = this.input_stream;
                        int i46 = this.jjimageLen;
                        int i47 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i47;
                        this.image = new StringBuffer(new String(simpleCharStream23.GetSuffix(i46 + i47)));
                    } else {
                        SimpleCharStream simpleCharStream24 = this.input_stream;
                        int i48 = this.jjimageLen;
                        int i49 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i49;
                        stringBuffer13.append(new String(simpleCharStream24.GetSuffix(i48 + i49)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 15:
                    StringBuffer stringBuffer14 = this.image;
                    if (stringBuffer14 == null) {
                        SimpleCharStream simpleCharStream25 = this.input_stream;
                        int i50 = this.jjimageLen;
                        int i51 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i51;
                        this.image = new StringBuffer(new String(simpleCharStream25.GetSuffix(i50 + i51)));
                    } else {
                        SimpleCharStream simpleCharStream26 = this.input_stream;
                        int i52 = this.jjimageLen;
                        int i53 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i53;
                        stringBuffer14.append(new String(simpleCharStream26.GetSuffix(i52 + i53)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 16:
                    StringBuffer stringBuffer15 = this.image;
                    if (stringBuffer15 == null) {
                        SimpleCharStream simpleCharStream27 = this.input_stream;
                        int i54 = this.jjimageLen;
                        int i55 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i55;
                        this.image = new StringBuffer(new String(simpleCharStream27.GetSuffix(i54 + i55)));
                    } else {
                        SimpleCharStream simpleCharStream28 = this.input_stream;
                        int i56 = this.jjimageLen;
                        int i57 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i57;
                        stringBuffer15.append(new String(simpleCharStream28.GetSuffix(i56 + i57)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 17:
                    StringBuffer stringBuffer16 = this.image;
                    if (stringBuffer16 == null) {
                        SimpleCharStream simpleCharStream29 = this.input_stream;
                        int i58 = this.jjimageLen;
                        int i59 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i59;
                        this.image = new StringBuffer(new String(simpleCharStream29.GetSuffix(i58 + i59)));
                    } else {
                        SimpleCharStream simpleCharStream30 = this.input_stream;
                        int i60 = this.jjimageLen;
                        int i61 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i61;
                        stringBuffer16.append(new String(simpleCharStream30.GetSuffix(i60 + i61)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 18:
                    StringBuffer stringBuffer17 = this.image;
                    if (stringBuffer17 == null) {
                        SimpleCharStream simpleCharStream31 = this.input_stream;
                        int i62 = this.jjimageLen;
                        int i63 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i63;
                        this.image = new StringBuffer(new String(simpleCharStream31.GetSuffix(i62 + i63)));
                    } else {
                        SimpleCharStream simpleCharStream32 = this.input_stream;
                        int i64 = this.jjimageLen;
                        int i65 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i65;
                        stringBuffer17.append(new String(simpleCharStream32.GetSuffix(i64 + i65)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 19:
                    StringBuffer stringBuffer18 = this.image;
                    if (stringBuffer18 == null) {
                        SimpleCharStream simpleCharStream33 = this.input_stream;
                        int i66 = this.jjimageLen;
                        int i67 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i67;
                        this.image = new StringBuffer(new String(simpleCharStream33.GetSuffix(i66 + i67)));
                    } else {
                        SimpleCharStream simpleCharStream34 = this.input_stream;
                        int i68 = this.jjimageLen;
                        int i69 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i69;
                        stringBuffer18.append(new String(simpleCharStream34.GetSuffix(i68 + i69)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 20:
                    StringBuffer stringBuffer19 = this.image;
                    if (stringBuffer19 == null) {
                        SimpleCharStream simpleCharStream35 = this.input_stream;
                        int i70 = this.jjimageLen;
                        int i71 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i71;
                        this.image = new StringBuffer(new String(simpleCharStream35.GetSuffix(i70 + i71)));
                    } else {
                        SimpleCharStream simpleCharStream36 = this.input_stream;
                        int i72 = this.jjimageLen;
                        int i73 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i73;
                        stringBuffer19.append(new String(simpleCharStream36.GetSuffix(i72 + i73)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 21:
                    StringBuffer stringBuffer20 = this.image;
                    if (stringBuffer20 == null) {
                        SimpleCharStream simpleCharStream37 = this.input_stream;
                        int i74 = this.jjimageLen;
                        int i75 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i75;
                        this.image = new StringBuffer(new String(simpleCharStream37.GetSuffix(i74 + i75)));
                    } else {
                        SimpleCharStream simpleCharStream38 = this.input_stream;
                        int i76 = this.jjimageLen;
                        int i77 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i77;
                        stringBuffer20.append(new String(simpleCharStream38.GetSuffix(i76 + i77)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 22:
                    StringBuffer stringBuffer21 = this.image;
                    if (stringBuffer21 == null) {
                        SimpleCharStream simpleCharStream39 = this.input_stream;
                        int i78 = this.jjimageLen;
                        int i79 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i79;
                        this.image = new StringBuffer(new String(simpleCharStream39.GetSuffix(i78 + i79)));
                    } else {
                        SimpleCharStream simpleCharStream40 = this.input_stream;
                        int i80 = this.jjimageLen;
                        int i81 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i81;
                        stringBuffer21.append(new String(simpleCharStream40.GetSuffix(i80 + i81)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 23:
                    StringBuffer stringBuffer22 = this.image;
                    if (stringBuffer22 == null) {
                        SimpleCharStream simpleCharStream41 = this.input_stream;
                        int i82 = this.jjimageLen;
                        int i83 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i83;
                        this.image = new StringBuffer(new String(simpleCharStream41.GetSuffix(i82 + i83)));
                    } else {
                        SimpleCharStream simpleCharStream42 = this.input_stream;
                        int i84 = this.jjimageLen;
                        int i85 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i85;
                        stringBuffer22.append(new String(simpleCharStream42.GetSuffix(i84 + i85)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 24:
                    StringBuffer stringBuffer23 = this.image;
                    if (stringBuffer23 == null) {
                        SimpleCharStream simpleCharStream43 = this.input_stream;
                        int i86 = this.jjimageLen;
                        int i87 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i87;
                        this.image = new StringBuffer(new String(simpleCharStream43.GetSuffix(i86 + i87)));
                    } else {
                        SimpleCharStream simpleCharStream44 = this.input_stream;
                        int i88 = this.jjimageLen;
                        int i89 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i89;
                        stringBuffer23.append(new String(simpleCharStream44.GetSuffix(i88 + i89)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 25:
                    StringBuffer stringBuffer24 = this.image;
                    if (stringBuffer24 == null) {
                        SimpleCharStream simpleCharStream45 = this.input_stream;
                        int i90 = this.jjimageLen;
                        int i91 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i91;
                        this.image = new StringBuffer(new String(simpleCharStream45.GetSuffix(i90 + i91)));
                    } else {
                        SimpleCharStream simpleCharStream46 = this.input_stream;
                        int i92 = this.jjimageLen;
                        int i93 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i93;
                        stringBuffer24.append(new String(simpleCharStream46.GetSuffix(i92 + i93)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 26:
                    StringBuffer stringBuffer25 = this.image;
                    if (stringBuffer25 == null) {
                        SimpleCharStream simpleCharStream47 = this.input_stream;
                        int i94 = this.jjimageLen;
                        int i95 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i95;
                        this.image = new StringBuffer(new String(simpleCharStream47.GetSuffix(i94 + i95)));
                    } else {
                        SimpleCharStream simpleCharStream48 = this.input_stream;
                        int i96 = this.jjimageLen;
                        int i97 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i97;
                        stringBuffer25.append(new String(simpleCharStream48.GetSuffix(i96 + i97)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 27:
                    StringBuffer stringBuffer26 = this.image;
                    if (stringBuffer26 == null) {
                        SimpleCharStream simpleCharStream49 = this.input_stream;
                        int i98 = this.jjimageLen;
                        int i99 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i99;
                        this.image = new StringBuffer(new String(simpleCharStream49.GetSuffix(i98 + i99)));
                    } else {
                        SimpleCharStream simpleCharStream50 = this.input_stream;
                        int i100 = this.jjimageLen;
                        int i101 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i101;
                        stringBuffer26.append(new String(simpleCharStream50.GetSuffix(i100 + i101)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 28:
                    StringBuffer stringBuffer27 = this.image;
                    if (stringBuffer27 == null) {
                        SimpleCharStream simpleCharStream51 = this.input_stream;
                        int i102 = this.jjimageLen;
                        int i103 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i103;
                        this.image = new StringBuffer(new String(simpleCharStream51.GetSuffix(i102 + i103)));
                    } else {
                        SimpleCharStream simpleCharStream52 = this.input_stream;
                        int i104 = this.jjimageLen;
                        int i105 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i105;
                        stringBuffer27.append(new String(simpleCharStream52.GetSuffix(i104 + i105)));
                    }
                    strictSyntaxCheck(token, 7);
                    this.noparseTag = "comment";
                    return;
                case 29:
                    StringBuffer stringBuffer28 = this.image;
                    if (stringBuffer28 == null) {
                        SimpleCharStream simpleCharStream53 = this.input_stream;
                        int i106 = this.jjimageLen;
                        int i107 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i107;
                        this.image = new StringBuffer(new String(simpleCharStream53.GetSuffix(i106 + i107)));
                    } else {
                        SimpleCharStream simpleCharStream54 = this.input_stream;
                        int i108 = this.jjimageLen;
                        int i109 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i109;
                        stringBuffer28.append(new String(simpleCharStream54.GetSuffix(i108 + i109)));
                    }
                    this.noparseTag = "-->";
                    strictSyntaxCheck(token, 7);
                    return;
                case 30:
                    StringBuffer stringBuffer29 = this.image;
                    if (stringBuffer29 == null) {
                        SimpleCharStream simpleCharStream55 = this.input_stream;
                        int i110 = this.jjimageLen;
                        int i111 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i111;
                        this.image = new StringBuffer(new String(simpleCharStream55.GetSuffix(i110 + i111)));
                    } else {
                        SimpleCharStream simpleCharStream56 = this.input_stream;
                        int i112 = this.jjimageLen;
                        int i113 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i113;
                        stringBuffer29.append(new String(simpleCharStream56.GetSuffix(i112 + i113)));
                    }
                    strictSyntaxCheck(token, 7);
                    this.noparseTag = "noparse";
                    return;
                case 31:
                    StringBuffer stringBuffer30 = this.image;
                    if (stringBuffer30 == null) {
                        SimpleCharStream simpleCharStream57 = this.input_stream;
                        int i114 = this.jjimageLen;
                        int i115 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i115;
                        this.image = new StringBuffer(new String(simpleCharStream57.GetSuffix(i114 + i115)));
                    } else {
                        SimpleCharStream simpleCharStream58 = this.input_stream;
                        int i116 = this.jjimageLen;
                        int i117 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i117;
                        stringBuffer30.append(new String(simpleCharStream58.GetSuffix(i116 + i117)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 32:
                    StringBuffer stringBuffer31 = this.image;
                    if (stringBuffer31 == null) {
                        SimpleCharStream simpleCharStream59 = this.input_stream;
                        int i118 = this.jjimageLen;
                        int i119 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i119;
                        this.image = new StringBuffer(new String(simpleCharStream59.GetSuffix(i118 + i119)));
                    } else {
                        SimpleCharStream simpleCharStream60 = this.input_stream;
                        int i120 = this.jjimageLen;
                        int i121 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i121;
                        stringBuffer31.append(new String(simpleCharStream60.GetSuffix(i120 + i121)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 33:
                    StringBuffer stringBuffer32 = this.image;
                    if (stringBuffer32 == null) {
                        SimpleCharStream simpleCharStream61 = this.input_stream;
                        int i122 = this.jjimageLen;
                        int i123 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i123;
                        this.image = new StringBuffer(new String(simpleCharStream61.GetSuffix(i122 + i123)));
                    } else {
                        SimpleCharStream simpleCharStream62 = this.input_stream;
                        int i124 = this.jjimageLen;
                        int i125 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i125;
                        stringBuffer32.append(new String(simpleCharStream62.GetSuffix(i124 + i125)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 34:
                    StringBuffer stringBuffer33 = this.image;
                    if (stringBuffer33 == null) {
                        SimpleCharStream simpleCharStream63 = this.input_stream;
                        int i126 = this.jjimageLen;
                        int i127 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i127;
                        this.image = new StringBuffer(new String(simpleCharStream63.GetSuffix(i126 + i127)));
                    } else {
                        SimpleCharStream simpleCharStream64 = this.input_stream;
                        int i128 = this.jjimageLen;
                        int i129 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i129;
                        stringBuffer33.append(new String(simpleCharStream64.GetSuffix(i128 + i129)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 35:
                    StringBuffer stringBuffer34 = this.image;
                    if (stringBuffer34 == null) {
                        SimpleCharStream simpleCharStream65 = this.input_stream;
                        int i130 = this.jjimageLen;
                        int i131 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i131;
                        this.image = new StringBuffer(new String(simpleCharStream65.GetSuffix(i130 + i131)));
                    } else {
                        SimpleCharStream simpleCharStream66 = this.input_stream;
                        int i132 = this.jjimageLen;
                        int i133 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i133;
                        stringBuffer34.append(new String(simpleCharStream66.GetSuffix(i132 + i133)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 36:
                    StringBuffer stringBuffer35 = this.image;
                    if (stringBuffer35 == null) {
                        SimpleCharStream simpleCharStream67 = this.input_stream;
                        int i134 = this.jjimageLen;
                        int i135 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i135;
                        this.image = new StringBuffer(new String(simpleCharStream67.GetSuffix(i134 + i135)));
                    } else {
                        SimpleCharStream simpleCharStream68 = this.input_stream;
                        int i136 = this.jjimageLen;
                        int i137 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i137;
                        stringBuffer35.append(new String(simpleCharStream68.GetSuffix(i136 + i137)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 37:
                    StringBuffer stringBuffer36 = this.image;
                    if (stringBuffer36 == null) {
                        SimpleCharStream simpleCharStream69 = this.input_stream;
                        int i138 = this.jjimageLen;
                        int i139 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i139;
                        this.image = new StringBuffer(new String(simpleCharStream69.GetSuffix(i138 + i139)));
                    } else {
                        SimpleCharStream simpleCharStream70 = this.input_stream;
                        int i140 = this.jjimageLen;
                        int i141 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i141;
                        stringBuffer36.append(new String(simpleCharStream70.GetSuffix(i140 + i141)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 38:
                    StringBuffer stringBuffer37 = this.image;
                    if (stringBuffer37 == null) {
                        SimpleCharStream simpleCharStream71 = this.input_stream;
                        int i142 = this.jjimageLen;
                        int i143 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i143;
                        this.image = new StringBuffer(new String(simpleCharStream71.GetSuffix(i142 + i143)));
                    } else {
                        SimpleCharStream simpleCharStream72 = this.input_stream;
                        int i144 = this.jjimageLen;
                        int i145 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i145;
                        stringBuffer37.append(new String(simpleCharStream72.GetSuffix(i144 + i145)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 39:
                    StringBuffer stringBuffer38 = this.image;
                    if (stringBuffer38 == null) {
                        SimpleCharStream simpleCharStream73 = this.input_stream;
                        int i146 = this.jjimageLen;
                        int i147 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i147;
                        this.image = new StringBuffer(new String(simpleCharStream73.GetSuffix(i146 + i147)));
                    } else {
                        SimpleCharStream simpleCharStream74 = this.input_stream;
                        int i148 = this.jjimageLen;
                        int i149 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i149;
                        stringBuffer38.append(new String(simpleCharStream74.GetSuffix(i148 + i149)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 40:
                    StringBuffer stringBuffer39 = this.image;
                    if (stringBuffer39 == null) {
                        SimpleCharStream simpleCharStream75 = this.input_stream;
                        int i150 = this.jjimageLen;
                        int i151 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i151;
                        this.image = new StringBuffer(new String(simpleCharStream75.GetSuffix(i150 + i151)));
                    } else {
                        SimpleCharStream simpleCharStream76 = this.input_stream;
                        int i152 = this.jjimageLen;
                        int i153 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i153;
                        stringBuffer39.append(new String(simpleCharStream76.GetSuffix(i152 + i153)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 41:
                    StringBuffer stringBuffer40 = this.image;
                    if (stringBuffer40 == null) {
                        SimpleCharStream simpleCharStream77 = this.input_stream;
                        int i154 = this.jjimageLen;
                        int i155 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i155;
                        this.image = new StringBuffer(new String(simpleCharStream77.GetSuffix(i154 + i155)));
                    } else {
                        SimpleCharStream simpleCharStream78 = this.input_stream;
                        int i156 = this.jjimageLen;
                        int i157 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i157;
                        stringBuffer40.append(new String(simpleCharStream78.GetSuffix(i156 + i157)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 42:
                    StringBuffer stringBuffer41 = this.image;
                    if (stringBuffer41 == null) {
                        SimpleCharStream simpleCharStream79 = this.input_stream;
                        int i158 = this.jjimageLen;
                        int i159 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i159;
                        this.image = new StringBuffer(new String(simpleCharStream79.GetSuffix(i158 + i159)));
                    } else {
                        SimpleCharStream simpleCharStream80 = this.input_stream;
                        int i160 = this.jjimageLen;
                        int i161 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i161;
                        stringBuffer41.append(new String(simpleCharStream80.GetSuffix(i160 + i161)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 43:
                    StringBuffer stringBuffer42 = this.image;
                    if (stringBuffer42 == null) {
                        SimpleCharStream simpleCharStream81 = this.input_stream;
                        int i162 = this.jjimageLen;
                        int i163 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i163;
                        this.image = new StringBuffer(new String(simpleCharStream81.GetSuffix(i162 + i163)));
                    } else {
                        SimpleCharStream simpleCharStream82 = this.input_stream;
                        int i164 = this.jjimageLen;
                        int i165 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i165;
                        stringBuffer42.append(new String(simpleCharStream82.GetSuffix(i164 + i165)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 44:
                    StringBuffer stringBuffer43 = this.image;
                    if (stringBuffer43 == null) {
                        SimpleCharStream simpleCharStream83 = this.input_stream;
                        int i166 = this.jjimageLen;
                        int i167 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i167;
                        this.image = new StringBuffer(new String(simpleCharStream83.GetSuffix(i166 + i167)));
                    } else {
                        SimpleCharStream simpleCharStream84 = this.input_stream;
                        int i168 = this.jjimageLen;
                        int i169 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i169;
                        stringBuffer43.append(new String(simpleCharStream84.GetSuffix(i168 + i169)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 45:
                    StringBuffer stringBuffer44 = this.image;
                    if (stringBuffer44 == null) {
                        SimpleCharStream simpleCharStream85 = this.input_stream;
                        int i170 = this.jjimageLen;
                        int i171 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i171;
                        this.image = new StringBuffer(new String(simpleCharStream85.GetSuffix(i170 + i171)));
                    } else {
                        SimpleCharStream simpleCharStream86 = this.input_stream;
                        int i172 = this.jjimageLen;
                        int i173 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i173;
                        stringBuffer44.append(new String(simpleCharStream86.GetSuffix(i172 + i173)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 46:
                    StringBuffer stringBuffer45 = this.image;
                    if (stringBuffer45 == null) {
                        SimpleCharStream simpleCharStream87 = this.input_stream;
                        int i174 = this.jjimageLen;
                        int i175 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i175;
                        this.image = new StringBuffer(new String(simpleCharStream87.GetSuffix(i174 + i175)));
                    } else {
                        SimpleCharStream simpleCharStream88 = this.input_stream;
                        int i176 = this.jjimageLen;
                        int i177 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i177;
                        stringBuffer45.append(new String(simpleCharStream88.GetSuffix(i176 + i177)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 47:
                    StringBuffer stringBuffer46 = this.image;
                    if (stringBuffer46 == null) {
                        SimpleCharStream simpleCharStream89 = this.input_stream;
                        int i178 = this.jjimageLen;
                        int i179 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i179;
                        this.image = new StringBuffer(new String(simpleCharStream89.GetSuffix(i178 + i179)));
                    } else {
                        SimpleCharStream simpleCharStream90 = this.input_stream;
                        int i180 = this.jjimageLen;
                        int i181 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i181;
                        stringBuffer46.append(new String(simpleCharStream90.GetSuffix(i180 + i181)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 48:
                    StringBuffer stringBuffer47 = this.image;
                    if (stringBuffer47 == null) {
                        SimpleCharStream simpleCharStream91 = this.input_stream;
                        int i182 = this.jjimageLen;
                        int i183 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i183;
                        this.image = new StringBuffer(new String(simpleCharStream91.GetSuffix(i182 + i183)));
                    } else {
                        SimpleCharStream simpleCharStream92 = this.input_stream;
                        int i184 = this.jjimageLen;
                        int i185 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i185;
                        stringBuffer47.append(new String(simpleCharStream92.GetSuffix(i184 + i185)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 49:
                    StringBuffer stringBuffer48 = this.image;
                    if (stringBuffer48 == null) {
                        SimpleCharStream simpleCharStream93 = this.input_stream;
                        int i186 = this.jjimageLen;
                        int i187 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i187;
                        this.image = new StringBuffer(new String(simpleCharStream93.GetSuffix(i186 + i187)));
                    } else {
                        SimpleCharStream simpleCharStream94 = this.input_stream;
                        int i188 = this.jjimageLen;
                        int i189 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i189;
                        stringBuffer48.append(new String(simpleCharStream94.GetSuffix(i188 + i189)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 50:
                    StringBuffer stringBuffer49 = this.image;
                    if (stringBuffer49 == null) {
                        SimpleCharStream simpleCharStream95 = this.input_stream;
                        int i190 = this.jjimageLen;
                        int i191 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i191;
                        this.image = new StringBuffer(new String(simpleCharStream95.GetSuffix(i190 + i191)));
                    } else {
                        SimpleCharStream simpleCharStream96 = this.input_stream;
                        int i192 = this.jjimageLen;
                        int i193 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i193;
                        stringBuffer49.append(new String(simpleCharStream96.GetSuffix(i192 + i193)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 51:
                    StringBuffer stringBuffer50 = this.image;
                    if (stringBuffer50 == null) {
                        SimpleCharStream simpleCharStream97 = this.input_stream;
                        int i194 = this.jjimageLen;
                        int i195 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i195;
                        this.image = new StringBuffer(new String(simpleCharStream97.GetSuffix(i194 + i195)));
                    } else {
                        SimpleCharStream simpleCharStream98 = this.input_stream;
                        int i196 = this.jjimageLen;
                        int i197 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i197;
                        stringBuffer50.append(new String(simpleCharStream98.GetSuffix(i196 + i197)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 52:
                    StringBuffer stringBuffer51 = this.image;
                    if (stringBuffer51 == null) {
                        SimpleCharStream simpleCharStream99 = this.input_stream;
                        int i198 = this.jjimageLen;
                        int i199 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i199;
                        this.image = new StringBuffer(new String(simpleCharStream99.GetSuffix(i198 + i199)));
                    } else {
                        SimpleCharStream simpleCharStream100 = this.input_stream;
                        int i200 = this.jjimageLen;
                        int i201 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i201;
                        stringBuffer51.append(new String(simpleCharStream100.GetSuffix(i200 + i201)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 53:
                    StringBuffer stringBuffer52 = this.image;
                    if (stringBuffer52 == null) {
                        SimpleCharStream simpleCharStream101 = this.input_stream;
                        int i202 = this.jjimageLen;
                        int i203 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i203;
                        this.image = new StringBuffer(new String(simpleCharStream101.GetSuffix(i202 + i203)));
                    } else {
                        SimpleCharStream simpleCharStream102 = this.input_stream;
                        int i204 = this.jjimageLen;
                        int i205 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i205;
                        stringBuffer52.append(new String(simpleCharStream102.GetSuffix(i204 + i205)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 54:
                    StringBuffer stringBuffer53 = this.image;
                    if (stringBuffer53 == null) {
                        SimpleCharStream simpleCharStream103 = this.input_stream;
                        int i206 = this.jjimageLen;
                        int i207 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i207;
                        this.image = new StringBuffer(new String(simpleCharStream103.GetSuffix(i206 + i207)));
                    } else {
                        SimpleCharStream simpleCharStream104 = this.input_stream;
                        int i208 = this.jjimageLen;
                        int i209 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i209;
                        stringBuffer53.append(new String(simpleCharStream104.GetSuffix(i208 + i209)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 55:
                    StringBuffer stringBuffer54 = this.image;
                    if (stringBuffer54 == null) {
                        SimpleCharStream simpleCharStream105 = this.input_stream;
                        int i210 = this.jjimageLen;
                        int i211 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i211;
                        this.image = new StringBuffer(new String(simpleCharStream105.GetSuffix(i210 + i211)));
                    } else {
                        SimpleCharStream simpleCharStream106 = this.input_stream;
                        int i212 = this.jjimageLen;
                        int i213 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i213;
                        stringBuffer54.append(new String(simpleCharStream106.GetSuffix(i212 + i213)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 56:
                    StringBuffer stringBuffer55 = this.image;
                    if (stringBuffer55 == null) {
                        SimpleCharStream simpleCharStream107 = this.input_stream;
                        int i214 = this.jjimageLen;
                        int i215 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i215;
                        this.image = new StringBuffer(new String(simpleCharStream107.GetSuffix(i214 + i215)));
                    } else {
                        SimpleCharStream simpleCharStream108 = this.input_stream;
                        int i216 = this.jjimageLen;
                        int i217 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i217;
                        stringBuffer55.append(new String(simpleCharStream108.GetSuffix(i216 + i217)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 57:
                    StringBuffer stringBuffer56 = this.image;
                    if (stringBuffer56 == null) {
                        SimpleCharStream simpleCharStream109 = this.input_stream;
                        int i218 = this.jjimageLen;
                        int i219 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i219;
                        this.image = new StringBuffer(new String(simpleCharStream109.GetSuffix(i218 + i219)));
                    } else {
                        SimpleCharStream simpleCharStream110 = this.input_stream;
                        int i220 = this.jjimageLen;
                        int i221 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i221;
                        stringBuffer56.append(new String(simpleCharStream110.GetSuffix(i220 + i221)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 58:
                    StringBuffer stringBuffer57 = this.image;
                    if (stringBuffer57 == null) {
                        SimpleCharStream simpleCharStream111 = this.input_stream;
                        int i222 = this.jjimageLen;
                        int i223 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i223;
                        this.image = new StringBuffer(new String(simpleCharStream111.GetSuffix(i222 + i223)));
                    } else {
                        SimpleCharStream simpleCharStream112 = this.input_stream;
                        int i224 = this.jjimageLen;
                        int i225 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i225;
                        stringBuffer57.append(new String(simpleCharStream112.GetSuffix(i224 + i225)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 59:
                    StringBuffer stringBuffer58 = this.image;
                    if (stringBuffer58 == null) {
                        SimpleCharStream simpleCharStream113 = this.input_stream;
                        int i226 = this.jjimageLen;
                        int i227 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i227;
                        this.image = new StringBuffer(new String(simpleCharStream113.GetSuffix(i226 + i227)));
                    } else {
                        SimpleCharStream simpleCharStream114 = this.input_stream;
                        int i228 = this.jjimageLen;
                        int i229 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i229;
                        stringBuffer58.append(new String(simpleCharStream114.GetSuffix(i228 + i229)));
                    }
                    strictSyntaxCheck(token, 2);
                    return;
                case 60:
                    StringBuffer stringBuffer59 = this.image;
                    if (stringBuffer59 == null) {
                        SimpleCharStream simpleCharStream115 = this.input_stream;
                        int i230 = this.jjimageLen;
                        int i231 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i231;
                        this.image = new StringBuffer(new String(simpleCharStream115.GetSuffix(i230 + i231)));
                    } else {
                        SimpleCharStream simpleCharStream116 = this.input_stream;
                        int i232 = this.jjimageLen;
                        int i233 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i233;
                        stringBuffer59.append(new String(simpleCharStream116.GetSuffix(i232 + i233)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 61:
                    StringBuffer stringBuffer60 = this.image;
                    if (stringBuffer60 == null) {
                        SimpleCharStream simpleCharStream117 = this.input_stream;
                        int i234 = this.jjimageLen;
                        int i235 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i235;
                        this.image = new StringBuffer(new String(simpleCharStream117.GetSuffix(i234 + i235)));
                    } else {
                        SimpleCharStream simpleCharStream118 = this.input_stream;
                        int i236 = this.jjimageLen;
                        int i237 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i237;
                        stringBuffer60.append(new String(simpleCharStream118.GetSuffix(i236 + i237)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 62:
                    StringBuffer stringBuffer61 = this.image;
                    if (stringBuffer61 == null) {
                        SimpleCharStream simpleCharStream119 = this.input_stream;
                        int i238 = this.jjimageLen;
                        int i239 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i239;
                        this.image = new StringBuffer(new String(simpleCharStream119.GetSuffix(i238 + i239)));
                    } else {
                        SimpleCharStream simpleCharStream120 = this.input_stream;
                        int i240 = this.jjimageLen;
                        int i241 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i241;
                        stringBuffer61.append(new String(simpleCharStream120.GetSuffix(i240 + i241)));
                    }
                    strictSyntaxCheck(token, 0);
                    return;
                case 63:
                    StringBuffer stringBuffer62 = this.image;
                    if (stringBuffer62 == null) {
                        SimpleCharStream simpleCharStream121 = this.input_stream;
                        int i242 = this.jjimageLen;
                        int i243 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i243;
                        this.image = new StringBuffer(new String(simpleCharStream121.GetSuffix(i242 + i243)));
                    } else {
                        SimpleCharStream simpleCharStream122 = this.input_stream;
                        int i244 = this.jjimageLen;
                        int i245 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i245;
                        stringBuffer62.append(new String(simpleCharStream122.GetSuffix(i244 + i245)));
                    }
                    unifiedCall(token);
                    return;
                case 64:
                    StringBuffer stringBuffer63 = this.image;
                    if (stringBuffer63 == null) {
                        SimpleCharStream simpleCharStream123 = this.input_stream;
                        int i246 = this.jjimageLen;
                        int i247 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i247;
                        this.image = new StringBuffer(new String(simpleCharStream123.GetSuffix(i246 + i247)));
                    } else {
                        SimpleCharStream simpleCharStream124 = this.input_stream;
                        int i248 = this.jjimageLen;
                        int i249 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i249;
                        stringBuffer63.append(new String(simpleCharStream124.GetSuffix(i248 + i249)));
                    }
                    unifiedCallEnd(token);
                    return;
                case 65:
                    StringBuffer stringBuffer64 = this.image;
                    if (stringBuffer64 == null) {
                        SimpleCharStream simpleCharStream125 = this.input_stream;
                        int i250 = this.jjimageLen;
                        int i251 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i251;
                        this.image = new StringBuffer(new String(simpleCharStream125.GetSuffix(i250 + i251)));
                    } else {
                        SimpleCharStream simpleCharStream126 = this.input_stream;
                        int i252 = this.jjimageLen;
                        int i253 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i253;
                        stringBuffer64.append(new String(simpleCharStream126.GetSuffix(i252 + i253)));
                    }
                    ftlHeader(token);
                    return;
                case 66:
                    StringBuffer stringBuffer65 = this.image;
                    if (stringBuffer65 == null) {
                        SimpleCharStream simpleCharStream127 = this.input_stream;
                        int i254 = this.jjimageLen;
                        int i255 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i255;
                        this.image = new StringBuffer(new String(simpleCharStream127.GetSuffix(i254 + i255)));
                    } else {
                        SimpleCharStream simpleCharStream128 = this.input_stream;
                        int i256 = this.jjimageLen;
                        int i257 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i257;
                        stringBuffer65.append(new String(simpleCharStream128.GetSuffix(i256 + i257)));
                    }
                    ftlHeader(token);
                    return;
                case 67:
                    StringBuffer stringBuffer66 = this.image;
                    if (stringBuffer66 == null) {
                        SimpleCharStream simpleCharStream129 = this.input_stream;
                        int i258 = this.jjimageLen;
                        int i259 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i259;
                        this.image = new StringBuffer(new String(simpleCharStream129.GetSuffix(i258 + i259)));
                    } else {
                        SimpleCharStream simpleCharStream130 = this.input_stream;
                        int i260 = this.jjimageLen;
                        int i261 = this.jjmatchedPos + 1;
                        this.lengthOfMatch = i261;
                        stringBuffer66.append(new String(simpleCharStream130.GetSuffix(i260 + i261)));
                    }
                    if (!this.directiveSyntaxEstablished && this.incompatibleImprovements < _TemplateAPI.VERSION_INT_2_3_19) {
                        token.kind = 69;
                        return;
                    }
                    char cCharAt2 = token.image.charAt(0);
                    if (!this.directiveSyntaxEstablished && this.autodetectTagSyntax) {
                        this.squBracTagSyntax = cCharAt2 == '[';
                        this.directiveSyntaxEstablished = true;
                    }
                    if (cCharAt2 == '<' && this.squBracTagSyntax) {
                        token.kind = 69;
                        return;
                    }
                    if (cCharAt2 == '[' && !this.squBracTagSyntax) {
                        token.kind = 69;
                        return;
                    }
                    if (this.strictEscapeSyntax) {
                        String str = token.image;
                        String strSubstring = str.substring(str.indexOf(35) + 1);
                        if (_CoreAPI.BUILT_IN_DIRECTIVE_NAMES.contains(strSubstring)) {
                            throw new TokenMgrError(new StringBuffer("#").append(strSubstring).append(" is an existing directive, but the tag is malformed.").toString(), 0, token.beginLine, token.beginColumn + 1, token.endLine, token.endColumn);
                        }
                        if (!strSubstring.toLowerCase().equals(strSubstring)) {
                            string = "Directive names are all-lower-case.";
                        } else if (strSubstring.equals("set")) {
                            string = "Use #assign or #local or #global, depending on the intented scope (#assign is template-scope).";
                        } else if (strSubstring.equals("else_if")) {
                            string = "Use #elseif.";
                        } else if (strSubstring.equals("no_escape")) {
                            string = "Use #noescape instead.";
                        } else if (strSubstring.equals(FirebaseAnalytics.Param.METHOD)) {
                            string = "Use #function instead.";
                        } else if (strSubstring.equals(TtmlNode.TAG_HEAD) || strSubstring.equals("template") || strSubstring.equals("fm")) {
                            string = "You may meant #ftl.";
                        } else if (strSubstring.equals("try") || strSubstring.equals("atempt")) {
                            string = "You may meant #attempt.";
                        } else {
                            string = (strSubstring.equals("for") || strSubstring.equals("each") || strSubstring.equals("iterate") || strSubstring.equals("iterator")) ? "You may meant #list (http://freemarker.org/docs/ref_directive_list.html)." : new StringBuffer("Help (latest version): http://freemarker.org/docs/ref_directive_alphaidx.html; you're using FreeMarker ").append(Configuration.getVersion()).append(".").toString();
                        }
                        throw new TokenMgrError(new StringBuffer("Unknown directive: #").append(strSubstring).append(string != null ? new StringBuffer(". ").append(string).toString() : "").toString(), 0, token.beginLine, token.beginColumn + 1, token.endLine, token.endColumn);
                    }
                    return;
                default:
                    switch (i) {
                        case 113:
                            StringBuffer stringBuffer67 = this.image;
                            if (stringBuffer67 == null) {
                                this.image = new StringBuffer(jjstrLiteralImages[113]);
                            } else {
                                stringBuffer67.append(jjstrLiteralImages[113]);
                            }
                            this.bracketNesting++;
                            return;
                        case 114:
                            StringBuffer stringBuffer68 = this.image;
                            if (stringBuffer68 == null) {
                                this.image = new StringBuffer(jjstrLiteralImages[114]);
                            } else {
                                stringBuffer68.append(jjstrLiteralImages[114]);
                            }
                            closeBracket(token);
                            return;
                        case 115:
                            StringBuffer stringBuffer69 = this.image;
                            if (stringBuffer69 == null) {
                                this.image = new StringBuffer(jjstrLiteralImages[115]);
                            } else {
                                stringBuffer69.append(jjstrLiteralImages[115]);
                            }
                            int i262 = this.parenthesisNesting + 1;
                            this.parenthesisNesting = i262;
                            if (i262 == 1) {
                                SwitchTo(3);
                                return;
                            }
                            return;
                        case 116:
                            StringBuffer stringBuffer70 = this.image;
                            if (stringBuffer70 == null) {
                                this.image = new StringBuffer(jjstrLiteralImages[116]);
                            } else {
                                stringBuffer70.append(jjstrLiteralImages[116]);
                            }
                            int i263 = this.parenthesisNesting - 1;
                            this.parenthesisNesting = i263;
                            if (i263 == 0) {
                                if (this.inInvocation) {
                                    SwitchTo(4);
                                    return;
                                } else {
                                    SwitchTo(2);
                                    return;
                                }
                            }
                            return;
                        case 117:
                            StringBuffer stringBuffer71 = this.image;
                            if (stringBuffer71 == null) {
                                this.image = new StringBuffer(jjstrLiteralImages[117]);
                            } else {
                                stringBuffer71.append(jjstrLiteralImages[117]);
                            }
                            this.hashLiteralNesting++;
                            return;
                        case 118:
                            StringBuffer stringBuffer72 = this.image;
                            if (stringBuffer72 == null) {
                                this.image = new StringBuffer(jjstrLiteralImages[118]);
                            } else {
                                stringBuffer72.append(jjstrLiteralImages[118]);
                            }
                            int i264 = this.hashLiteralNesting;
                            if (i264 == 0) {
                                SwitchTo(0);
                                return;
                            } else {
                                this.hashLiteralNesting = i264 - 1;
                                return;
                            }
                        default:
                            return;
                    }
            }
        }
        StringBuffer stringBuffer73 = this.image;
        if (stringBuffer73 == null) {
            SimpleCharStream simpleCharStream131 = this.input_stream;
            int i265 = this.jjimageLen;
            int i266 = this.jjmatchedPos + 1;
            this.lengthOfMatch = i266;
            this.image = new StringBuffer(new String(simpleCharStream131.GetSuffix(i265 + i266)));
        } else {
            SimpleCharStream simpleCharStream132 = this.input_stream;
            int i267 = this.jjimageLen;
            int i268 = this.jjmatchedPos + 1;
            this.lengthOfMatch = i268;
            stringBuffer73.append(new String(simpleCharStream132.GetSuffix(i267 + i268)));
        }
        if (new StringTokenizer(this.image.toString(), " \t\n\r<>[]/#", false).nextToken().equals(this.noparseTag)) {
            token.image = new StringBuffer().append(token.image).append(";").toString();
            SwitchTo(0);
        }
    }
}
