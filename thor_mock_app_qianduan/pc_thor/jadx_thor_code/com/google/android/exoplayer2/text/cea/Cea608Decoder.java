package com.google.android.exoplayer2.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class Cea608Decoder extends CeaDecoder {
    private static final int CC_FIELD_FLAG = 1;
    private static final byte CC_IMPLICIT_DATA_HEADER = -4;
    private static final int CC_MODE_PAINT_ON = 3;
    private static final int CC_MODE_POP_ON = 2;
    private static final int CC_MODE_ROLL_UP = 1;
    private static final int CC_MODE_UNKNOWN = 0;
    private static final int CC_TYPE_FLAG = 2;
    private static final int CC_VALID_FLAG = 4;
    private static final byte CTRL_BACKSPACE = 33;
    private static final byte CTRL_CARRIAGE_RETURN = 45;
    private static final byte CTRL_DELETE_TO_END_OF_ROW = 36;
    private static final byte CTRL_END_OF_CAPTION = 47;
    private static final byte CTRL_ERASE_DISPLAYED_MEMORY = 44;
    private static final byte CTRL_ERASE_NON_DISPLAYED_MEMORY = 46;
    private static final byte CTRL_RESUME_CAPTION_LOADING = 32;
    private static final byte CTRL_RESUME_DIRECT_CAPTIONING = 41;
    private static final byte CTRL_RESUME_TEXT_DISPLAY = 43;
    private static final byte CTRL_ROLL_UP_CAPTIONS_2_ROWS = 37;
    private static final byte CTRL_ROLL_UP_CAPTIONS_3_ROWS = 38;
    private static final byte CTRL_ROLL_UP_CAPTIONS_4_ROWS = 39;
    private static final byte CTRL_TEXT_RESTART = 42;
    private static final int DEFAULT_CAPTIONS_ROW_COUNT = 4;
    public static final long MIN_DATA_CHANNEL_TIMEOUT_MS = 16000;
    private static final int NTSC_CC_CHANNEL_1 = 0;
    private static final int NTSC_CC_CHANNEL_2 = 1;
    private static final int NTSC_CC_FIELD_1 = 0;
    private static final int NTSC_CC_FIELD_2 = 1;
    private static final int STYLE_ITALICS = 7;
    private static final int STYLE_UNCHANGED = 8;
    private static final String TAG = "Cea608Decoder";
    private int captionMode;
    private int captionRowCount;
    private List<Cue> cues;
    private boolean isCaptionValid;
    private boolean isInCaptionService;
    private long lastCueUpdateUs;
    private List<Cue> lastCues;
    private final int packetLength;
    private byte repeatableControlCc1;
    private byte repeatableControlCc2;
    private boolean repeatableControlSet;
    private final int selectedChannel;
    private final int selectedField;
    private final long validDataChannelTimeoutUs;
    private static final int[] ROW_INDICES = {11, 1, 3, 12, 14, 5, 7, 9};
    private static final int[] COLUMN_INDICES = {0, 4, 8, 12, 16, 20, 24, 28};
    private static final int[] STYLE_COLORS = {-1, -16711936, -16776961, -16711681, SupportMenu.CATEGORY_MASK, InputDeviceCompat.SOURCE_ANY, -65281};
    private static final int[] BASIC_CHARACTER_SET = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, 247, 209, 241, 9632};
    private static final int[] SPECIAL_CHARACTER_SET = {174, 176, PsExtractor.PRIVATE_STREAM_1, 191, 8482, 162, 163, 9834, 224, 32, 232, 226, 234, 238, 244, 251};
    private static final int[] SPECIAL_ES_FR_CHARACTER_SET = {193, 201, 211, 218, 220, 252, 8216, 161, 42, 39, 8212, 169, 8480, 8226, 8220, 8221, PsExtractor.AUDIO_STREAM, 194, 199, 200, 202, 203, 235, 206, 207, 239, 212, 217, 249, 219, 171, 187};
    private static final int[] SPECIAL_PT_DE_CHARACTER_SET = {195, 227, 205, 204, 236, 210, 242, 213, 245, 123, 125, 92, 94, 95, 124, 126, 196, 228, 214, 246, 223, 165, 164, 9474, 197, 229, 216, 248, 9484, 9488, 9492, 9496};
    private static final boolean[] ODD_PARITY_BYTE_TABLE = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final ArrayList<CueBuilder> cueBuilders = new ArrayList<>();
    private CueBuilder currentCueBuilder = new CueBuilder(0, 4);
    private int currentChannel = 0;

    private static int getChannel(byte cc1) {
        return (cc1 >> 3) & 1;
    }

    private static boolean isCtrlCode(byte cc1) {
        return (cc1 & 224) == 0;
    }

    private static boolean isExtendedWestEuropeanChar(byte cc1, byte cc2) {
        return (cc1 & 246) == 18 && (cc2 & 224) == 32;
    }

    private static boolean isMidrowCtrlCode(byte cc1, byte cc2) {
        return (cc1 & 247) == 17 && (cc2 & 240) == 32;
    }

    private static boolean isMiscCode(byte cc1, byte cc2) {
        return (cc1 & 246) == 20 && (cc2 & 240) == 32;
    }

    private static boolean isPreambleAddressCode(byte cc1, byte cc2) {
        return (cc1 & 240) == 16 && (cc2 & 192) == 64;
    }

    private static boolean isRepeatable(byte cc1) {
        return (cc1 & 240) == 16;
    }

    private static boolean isServiceSwitchCommand(byte cc1) {
        return (cc1 & 247) == 20;
    }

    private static boolean isSpecialNorthAmericanChar(byte cc1, byte cc2) {
        return (cc1 & 247) == 17 && (cc2 & 240) == 48;
    }

    private static boolean isTabCtrlCode(byte cc1, byte cc2) {
        return (cc1 & 247) == 23 && cc2 >= 33 && cc2 <= 35;
    }

    private static boolean isXdsControlCode(byte cc1) {
        return 1 <= cc1 && cc1 <= 15;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public void release() {
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer inputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(inputBuffer);
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.text.SubtitleDecoder
    public /* bridge */ /* synthetic */ void setPositionUs(long positionUs) {
        super.setPositionUs(positionUs);
    }

    public Cea608Decoder(String mimeType, int accessibilityChannel, long validDataChannelTimeoutMs) {
        this.validDataChannelTimeoutUs = validDataChannelTimeoutMs > 0 ? validDataChannelTimeoutMs * 1000 : -9223372036854775807L;
        this.packetLength = MimeTypes.APPLICATION_MP4CEA608.equals(mimeType) ? 2 : 3;
        if (accessibilityChannel == 1) {
            this.selectedChannel = 0;
            this.selectedField = 0;
        } else if (accessibilityChannel == 2) {
            this.selectedChannel = 1;
            this.selectedField = 0;
        } else if (accessibilityChannel == 3) {
            this.selectedChannel = 0;
            this.selectedField = 1;
        } else if (accessibilityChannel == 4) {
            this.selectedChannel = 1;
            this.selectedField = 1;
        } else {
            Log.w(TAG, "Invalid channel. Defaulting to CC1.");
            this.selectedChannel = 0;
            this.selectedField = 0;
        }
        setCaptionMode(0);
        resetCueBuilders();
        this.isInCaptionService = true;
        this.lastCueUpdateUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        setCaptionMode(0);
        setCaptionRowCount(4);
        resetCueBuilders();
        this.isCaptionValid = false;
        this.repeatableControlSet = false;
        this.repeatableControlCc1 = (byte) 0;
        this.repeatableControlCc2 = (byte) 0;
        this.currentChannel = 0;
        this.isInCaptionService = true;
        this.lastCueUpdateUs = C.TIME_UNSET;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        SubtitleOutputBuffer availableOutputBuffer;
        SubtitleOutputBuffer subtitleOutputBufferDequeueOutputBuffer = super.dequeueOutputBuffer();
        if (subtitleOutputBufferDequeueOutputBuffer != null) {
            return subtitleOutputBufferDequeueOutputBuffer;
        }
        if (!shouldClearStuckCaptions() || (availableOutputBuffer = getAvailableOutputBuffer()) == null) {
            return null;
        }
        this.cues = Collections.emptyList();
        this.lastCueUpdateUs = C.TIME_UNSET;
        availableOutputBuffer.setContent(getPositionUs(), createSubtitle(), Long.MAX_VALUE);
        return availableOutputBuffer;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    protected boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    protected Subtitle createSubtitle() {
        this.lastCues = this.cues;
        return new CeaSubtitle((List) Assertions.checkNotNull(this.cues));
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0064  */
    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void decode(com.google.android.exoplayer2.text.SubtitleInputBuffer r10) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea608Decoder.decode(com.google.android.exoplayer2.text.SubtitleInputBuffer):void");
    }

    private boolean updateAndVerifyCurrentChannel(byte cc1) {
        if (isCtrlCode(cc1)) {
            this.currentChannel = getChannel(cc1);
        }
        return this.currentChannel == this.selectedChannel;
    }

    private boolean isRepeatedCommand(boolean captionValid, byte cc1, byte cc2) {
        if (captionValid && isRepeatable(cc1)) {
            if (this.repeatableControlSet && this.repeatableControlCc1 == cc1 && this.repeatableControlCc2 == cc2) {
                this.repeatableControlSet = false;
                return true;
            }
            this.repeatableControlSet = true;
            this.repeatableControlCc1 = cc1;
            this.repeatableControlCc2 = cc2;
        } else {
            this.repeatableControlSet = false;
        }
        return false;
    }

    private void handleMidrowCtrl(byte cc2) {
        this.currentCueBuilder.append(' ');
        this.currentCueBuilder.setStyle((cc2 >> 1) & 7, (cc2 & 1) == 1);
    }

    private void handlePreambleAddressCode(byte cc1, byte cc2) {
        int i = ROW_INDICES[cc1 & 7];
        if ((cc2 & 32) != 0) {
            i++;
        }
        if (i != this.currentCueBuilder.row) {
            if (this.captionMode != 1 && !this.currentCueBuilder.isEmpty()) {
                CueBuilder cueBuilder = new CueBuilder(this.captionMode, this.captionRowCount);
                this.currentCueBuilder = cueBuilder;
                this.cueBuilders.add(cueBuilder);
            }
            this.currentCueBuilder.row = i;
        }
        boolean z = (cc2 & Ascii.DLE) == 16;
        boolean z2 = (cc2 & 1) == 1;
        int i2 = (cc2 >> 1) & 7;
        this.currentCueBuilder.setStyle(z ? 8 : i2, z2);
        if (z) {
            this.currentCueBuilder.indent = COLUMN_INDICES[i2];
        }
    }

    private void handleMiscCode(byte cc2) {
        if (cc2 == 32) {
            setCaptionMode(2);
            return;
        }
        if (cc2 != 41) {
            switch (cc2) {
                case 37:
                    setCaptionMode(1);
                    setCaptionRowCount(2);
                    break;
                case 38:
                    setCaptionMode(1);
                    setCaptionRowCount(3);
                    break;
                case 39:
                    setCaptionMode(1);
                    setCaptionRowCount(4);
                    break;
                default:
                    int i = this.captionMode;
                    if (i != 0) {
                        if (cc2 != 33) {
                            switch (cc2) {
                                case 44:
                                    this.cues = Collections.emptyList();
                                    int i2 = this.captionMode;
                                    if (i2 == 1 || i2 == 3) {
                                        resetCueBuilders();
                                        break;
                                    }
                                case 45:
                                    if (i == 1 && !this.currentCueBuilder.isEmpty()) {
                                        this.currentCueBuilder.rollUp();
                                        break;
                                    }
                                    break;
                                case 46:
                                    resetCueBuilders();
                                    break;
                                case 47:
                                    this.cues = getDisplayCues();
                                    resetCueBuilders();
                                    break;
                            }
                        } else {
                            this.currentCueBuilder.backspace();
                            break;
                        }
                    }
                    break;
            }
            return;
        }
        setCaptionMode(3);
    }

    private List<Cue> getDisplayCues() {
        int size = this.cueBuilders.size();
        ArrayList arrayList = new ArrayList(size);
        int iMin = 2;
        for (int i = 0; i < size; i++) {
            Cue cueBuild = this.cueBuilders.get(i).build(Integer.MIN_VALUE);
            arrayList.add(cueBuild);
            if (cueBuild != null) {
                iMin = Math.min(iMin, cueBuild.positionAnchor);
            }
        }
        ArrayList arrayList2 = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            Cue cue = (Cue) arrayList.get(i2);
            if (cue != null) {
                if (cue.positionAnchor != iMin) {
                    cue = (Cue) Assertions.checkNotNull(this.cueBuilders.get(i2).build(iMin));
                }
                arrayList2.add(cue);
            }
        }
        return arrayList2;
    }

    private void setCaptionMode(int captionMode) {
        int i = this.captionMode;
        if (i == captionMode) {
            return;
        }
        this.captionMode = captionMode;
        if (captionMode == 3) {
            for (int i2 = 0; i2 < this.cueBuilders.size(); i2++) {
                this.cueBuilders.get(i2).setCaptionMode(captionMode);
            }
            return;
        }
        resetCueBuilders();
        if (i == 3 || captionMode == 1 || captionMode == 0) {
            this.cues = Collections.emptyList();
        }
    }

    private void setCaptionRowCount(int captionRowCount) {
        this.captionRowCount = captionRowCount;
        this.currentCueBuilder.setCaptionRowCount(captionRowCount);
    }

    private void resetCueBuilders() {
        this.currentCueBuilder.reset(this.captionMode);
        this.cueBuilders.clear();
        this.cueBuilders.add(this.currentCueBuilder);
    }

    private void maybeUpdateIsInCaptionService(byte cc1, byte cc2) {
        if (isXdsControlCode(cc1)) {
            this.isInCaptionService = false;
            return;
        }
        if (isServiceSwitchCommand(cc1)) {
            if (cc2 != 32 && cc2 != 47) {
                switch (cc2) {
                    case 37:
                    case 38:
                    case 39:
                        break;
                    default:
                        switch (cc2) {
                            case 42:
                            case 43:
                                this.isInCaptionService = false;
                                break;
                        }
                }
            }
            this.isInCaptionService = true;
        }
    }

    private static char getBasicChar(byte ccData) {
        return (char) BASIC_CHARACTER_SET[(ccData & Byte.MAX_VALUE) - 32];
    }

    private static char getSpecialNorthAmericanChar(byte ccData) {
        return (char) SPECIAL_CHARACTER_SET[ccData & Ascii.SI];
    }

    private static char getExtendedWestEuropeanChar(byte cc1, byte cc2) {
        if ((cc1 & 1) == 0) {
            return getExtendedEsFrChar(cc2);
        }
        return getExtendedPtDeChar(cc2);
    }

    private static char getExtendedEsFrChar(byte ccData) {
        return (char) SPECIAL_ES_FR_CHARACTER_SET[ccData & Ascii.US];
    }

    private static char getExtendedPtDeChar(byte ccData) {
        return (char) SPECIAL_PT_DE_CHARACTER_SET[ccData & Ascii.US];
    }

    private static final class CueBuilder {
        private static final int BASE_ROW = 15;
        private static final int SCREEN_CHARWIDTH = 32;
        private int captionMode;
        private int captionRowCount;
        private int indent;
        private int row;
        private int tabOffset;
        private final List<CueStyle> cueStyles = new ArrayList();
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        private final StringBuilder captionStringBuilder = new StringBuilder();

        public CueBuilder(int captionMode, int captionRowCount) {
            reset(captionMode);
            this.captionRowCount = captionRowCount;
        }

        public void reset(int captionMode) {
            this.captionMode = captionMode;
            this.cueStyles.clear();
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.setLength(0);
            this.row = 15;
            this.indent = 0;
            this.tabOffset = 0;
        }

        public boolean isEmpty() {
            return this.cueStyles.isEmpty() && this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0;
        }

        public void setCaptionMode(int captionMode) {
            this.captionMode = captionMode;
        }

        public void setCaptionRowCount(int captionRowCount) {
            this.captionRowCount = captionRowCount;
        }

        public void setStyle(int style, boolean underline) {
            this.cueStyles.add(new CueStyle(style, underline, this.captionStringBuilder.length()));
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
                for (int size = this.cueStyles.size() - 1; size >= 0; size--) {
                    if (this.cueStyles.get(size).start != length) {
                        return;
                    }
                    r2.start--;
                }
            }
        }

        public void append(char text) {
            if (this.captionStringBuilder.length() < 32) {
                this.captionStringBuilder.append(text);
            }
        }

        public void rollUp() {
            this.rolledUpCaptions.add(buildCurrentLine());
            this.captionStringBuilder.setLength(0);
            this.cueStyles.clear();
            int iMin = Math.min(this.captionRowCount, this.row);
            while (this.rolledUpCaptions.size() >= iMin) {
                this.rolledUpCaptions.remove(0);
            }
        }

        public Cue build(int forcedPositionAnchor) {
            float f;
            int i = this.indent + this.tabOffset;
            int i2 = 32 - i;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i3 = 0; i3 < this.rolledUpCaptions.size(); i3++) {
                spannableStringBuilder.append(Util.truncateAscii(this.rolledUpCaptions.get(i3), i2));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append(Util.truncateAscii(buildCurrentLine(), i2));
            if (spannableStringBuilder.length() == 0) {
                return null;
            }
            int length = i2 - spannableStringBuilder.length();
            int i4 = i - length;
            if (forcedPositionAnchor == Integer.MIN_VALUE) {
                if (this.captionMode != 2 || (Math.abs(i4) >= 3 && length >= 0)) {
                    forcedPositionAnchor = (this.captionMode != 2 || i4 <= 0) ? 0 : 2;
                } else {
                    forcedPositionAnchor = 1;
                }
            }
            if (forcedPositionAnchor != 1) {
                if (forcedPositionAnchor == 2) {
                    i = 32 - length;
                }
                f = ((i / 32.0f) * 0.8f) + 0.1f;
            } else {
                f = 0.5f;
            }
            int i5 = this.row;
            if (i5 > 7) {
                i5 = (i5 - 15) - 2;
            } else if (this.captionMode == 1) {
                i5 -= this.captionRowCount - 1;
            }
            return new Cue.Builder().setText(spannableStringBuilder).setTextAlignment(Layout.Alignment.ALIGN_NORMAL).setLine(i5, 1).setPosition(f).setPositionAnchor(forcedPositionAnchor).build();
        }

        private SpannableString buildCurrentLine() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            int i = -1;
            int i2 = -1;
            int i3 = -1;
            int i4 = -1;
            int i5 = 0;
            int i6 = 0;
            boolean z = false;
            while (i5 < this.cueStyles.size()) {
                CueStyle cueStyle = this.cueStyles.get(i5);
                boolean z2 = cueStyle.underline;
                int i7 = cueStyle.style;
                if (i7 != 8) {
                    boolean z3 = i7 == 7;
                    if (i7 != 7) {
                        i4 = Cea608Decoder.STYLE_COLORS[i7];
                    }
                    z = z3;
                }
                int i8 = cueStyle.start;
                i5++;
                if (i8 != (i5 < this.cueStyles.size() ? this.cueStyles.get(i5).start : length)) {
                    if (i != -1 && !z2) {
                        setUnderlineSpan(spannableStringBuilder, i, i8);
                        i = -1;
                    } else if (i == -1 && z2) {
                        i = i8;
                    }
                    if (i2 != -1 && !z) {
                        setItalicSpan(spannableStringBuilder, i2, i8);
                        i2 = -1;
                    } else if (i2 == -1 && z) {
                        i2 = i8;
                    }
                    if (i4 != i3) {
                        setColorSpan(spannableStringBuilder, i6, i8, i3);
                        i3 = i4;
                        i6 = i8;
                    }
                }
            }
            if (i != -1 && i != length) {
                setUnderlineSpan(spannableStringBuilder, i, length);
            }
            if (i2 != -1 && i2 != length) {
                setItalicSpan(spannableStringBuilder, i2, length);
            }
            if (i6 != length) {
                setColorSpan(spannableStringBuilder, i6, length, i3);
            }
            return new SpannableString(spannableStringBuilder);
        }

        private static void setUnderlineSpan(SpannableStringBuilder builder, int start, int end) {
            builder.setSpan(new UnderlineSpan(), start, end, 33);
        }

        private static void setItalicSpan(SpannableStringBuilder builder, int start, int end) {
            builder.setSpan(new StyleSpan(2), start, end, 33);
        }

        private static void setColorSpan(SpannableStringBuilder builder, int start, int end, int color) {
            if (color == -1) {
                return;
            }
            builder.setSpan(new ForegroundColorSpan(color), start, end, 33);
        }

        private static class CueStyle {
            public int start;
            public final int style;
            public final boolean underline;

            public CueStyle(int style, boolean underline, int start) {
                this.style = style;
                this.underline = underline;
                this.start = start;
            }
        }
    }

    private boolean shouldClearStuckCaptions() {
        return (this.validDataChannelTimeoutUs == C.TIME_UNSET || this.lastCueUpdateUs == C.TIME_UNSET || getPositionUs() - this.lastCueUpdateUs < this.validDataChannelTimeoutUs) ? false : true;
    }
}
