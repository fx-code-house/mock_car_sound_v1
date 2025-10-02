package com.google.android.exoplayer2.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.text.cea.Cea708Decoder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.text.Typography;
import okio.Utf8;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class Cea708Decoder extends CeaDecoder {
    private static final int CC_VALID_FLAG = 4;
    private static final int CHARACTER_BIG_CARONS = 42;
    private static final int CHARACTER_BIG_OE = 44;
    private static final int CHARACTER_BOLD_BULLET = 53;
    private static final int CHARACTER_CLOSE_DOUBLE_QUOTE = 52;
    private static final int CHARACTER_CLOSE_SINGLE_QUOTE = 50;
    private static final int CHARACTER_DIAERESIS_Y = 63;
    private static final int CHARACTER_ELLIPSIS = 37;
    private static final int CHARACTER_FIVE_EIGHTHS = 120;
    private static final int CHARACTER_HORIZONTAL_BORDER = 125;
    private static final int CHARACTER_LOWER_LEFT_BORDER = 124;
    private static final int CHARACTER_LOWER_RIGHT_BORDER = 126;
    private static final int CHARACTER_MN = 127;
    private static final int CHARACTER_NBTSP = 33;
    private static final int CHARACTER_ONE_EIGHTH = 118;
    private static final int CHARACTER_OPEN_DOUBLE_QUOTE = 51;
    private static final int CHARACTER_OPEN_SINGLE_QUOTE = 49;
    private static final int CHARACTER_SEVEN_EIGHTHS = 121;
    private static final int CHARACTER_SM = 61;
    private static final int CHARACTER_SMALL_CARONS = 58;
    private static final int CHARACTER_SMALL_OE = 60;
    private static final int CHARACTER_SOLID_BLOCK = 48;
    private static final int CHARACTER_THREE_EIGHTHS = 119;
    private static final int CHARACTER_TM = 57;
    private static final int CHARACTER_TSP = 32;
    private static final int CHARACTER_UPPER_LEFT_BORDER = 127;
    private static final int CHARACTER_UPPER_RIGHT_BORDER = 123;
    private static final int CHARACTER_VERTICAL_BORDER = 122;
    private static final int COMMAND_BS = 8;
    private static final int COMMAND_CLW = 136;
    private static final int COMMAND_CR = 13;
    private static final int COMMAND_CW0 = 128;
    private static final int COMMAND_CW1 = 129;
    private static final int COMMAND_CW2 = 130;
    private static final int COMMAND_CW3 = 131;
    private static final int COMMAND_CW4 = 132;
    private static final int COMMAND_CW5 = 133;
    private static final int COMMAND_CW6 = 134;
    private static final int COMMAND_CW7 = 135;
    private static final int COMMAND_DF0 = 152;
    private static final int COMMAND_DF1 = 153;
    private static final int COMMAND_DF2 = 154;
    private static final int COMMAND_DF3 = 155;
    private static final int COMMAND_DF4 = 156;
    private static final int COMMAND_DF5 = 157;
    private static final int COMMAND_DF6 = 158;
    private static final int COMMAND_DF7 = 159;
    private static final int COMMAND_DLC = 142;
    private static final int COMMAND_DLW = 140;
    private static final int COMMAND_DLY = 141;
    private static final int COMMAND_DSW = 137;
    private static final int COMMAND_ETX = 3;
    private static final int COMMAND_EXT1 = 16;
    private static final int COMMAND_EXT1_END = 23;
    private static final int COMMAND_EXT1_START = 17;
    private static final int COMMAND_FF = 12;
    private static final int COMMAND_HCR = 14;
    private static final int COMMAND_HDW = 138;
    private static final int COMMAND_NUL = 0;
    private static final int COMMAND_P16_END = 31;
    private static final int COMMAND_P16_START = 24;
    private static final int COMMAND_RST = 143;
    private static final int COMMAND_SPA = 144;
    private static final int COMMAND_SPC = 145;
    private static final int COMMAND_SPL = 146;
    private static final int COMMAND_SWA = 151;
    private static final int COMMAND_TGW = 139;
    private static final int DTVCC_PACKET_DATA = 2;
    private static final int DTVCC_PACKET_START = 3;
    private static final int GROUP_C0_END = 31;
    private static final int GROUP_C1_END = 159;
    private static final int GROUP_C2_END = 31;
    private static final int GROUP_C3_END = 159;
    private static final int GROUP_G0_END = 127;
    private static final int GROUP_G1_END = 255;
    private static final int GROUP_G2_END = 127;
    private static final int GROUP_G3_END = 255;
    private static final int NUM_WINDOWS = 8;
    private static final String TAG = "Cea708Decoder";
    private final CueInfoBuilder[] cueInfoBuilders;
    private List<Cue> cues;
    private CueInfoBuilder currentCueInfoBuilder;
    private DtvCcPacket currentDtvCcPacket;
    private int currentWindow;
    private final boolean isWideAspectRatio;
    private List<Cue> lastCues;
    private final int selectedServiceNumber;
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final ParsableBitArray serviceBlockPacket = new ParsableBitArray();
    private int previousSequenceNumber = -1;

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer inputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(inputBuffer);
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.text.SubtitleDecoder
    public /* bridge */ /* synthetic */ void setPositionUs(long positionUs) {
        super.setPositionUs(positionUs);
    }

    public Cea708Decoder(int accessibilityChannel, List<byte[]> initializationData) {
        this.selectedServiceNumber = accessibilityChannel == -1 ? 1 : accessibilityChannel;
        this.isWideAspectRatio = initializationData != null && CodecSpecificDataUtil.parseCea708InitializationData(initializationData);
        this.cueInfoBuilders = new CueInfoBuilder[8];
        for (int i = 0; i < 8; i++) {
            this.cueInfoBuilders[i] = new CueInfoBuilder();
        }
        this.currentCueInfoBuilder = this.cueInfoBuilders[0];
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        this.currentWindow = 0;
        this.currentCueInfoBuilder = this.cueInfoBuilders[0];
        resetCueBuilders();
        this.currentDtvCcPacket = null;
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

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    protected void decode(SubtitleInputBuffer inputBuffer) {
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(inputBuffer.data);
        this.ccData.reset(byteBuffer.array(), byteBuffer.limit());
        while (this.ccData.bytesLeft() >= 3) {
            int unsignedByte = this.ccData.readUnsignedByte() & 7;
            int i = unsignedByte & 3;
            boolean z = (unsignedByte & 4) == 4;
            byte unsignedByte2 = (byte) this.ccData.readUnsignedByte();
            byte unsignedByte3 = (byte) this.ccData.readUnsignedByte();
            if (i == 2 || i == 3) {
                if (z) {
                    if (i == 3) {
                        finalizeCurrentPacket();
                        int i2 = (unsignedByte2 & 192) >> 6;
                        int i3 = this.previousSequenceNumber;
                        if (i3 != -1 && i2 != (i3 + 1) % 4) {
                            resetCueBuilders();
                            Log.w(TAG, new StringBuilder(71).append("Sequence number discontinuity. previous=").append(this.previousSequenceNumber).append(" current=").append(i2).toString());
                        }
                        this.previousSequenceNumber = i2;
                        int i4 = unsignedByte2 & Utf8.REPLACEMENT_BYTE;
                        if (i4 == 0) {
                            i4 = 64;
                        }
                        DtvCcPacket dtvCcPacket = new DtvCcPacket(i2, i4);
                        this.currentDtvCcPacket = dtvCcPacket;
                        byte[] bArr = dtvCcPacket.packetData;
                        DtvCcPacket dtvCcPacket2 = this.currentDtvCcPacket;
                        int i5 = dtvCcPacket2.currentIndex;
                        dtvCcPacket2.currentIndex = i5 + 1;
                        bArr[i5] = unsignedByte3;
                    } else {
                        Assertions.checkArgument(i == 2);
                        DtvCcPacket dtvCcPacket3 = this.currentDtvCcPacket;
                        if (dtvCcPacket3 == null) {
                            Log.e(TAG, "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                        } else {
                            byte[] bArr2 = dtvCcPacket3.packetData;
                            DtvCcPacket dtvCcPacket4 = this.currentDtvCcPacket;
                            int i6 = dtvCcPacket4.currentIndex;
                            dtvCcPacket4.currentIndex = i6 + 1;
                            bArr2[i6] = unsignedByte2;
                            byte[] bArr3 = this.currentDtvCcPacket.packetData;
                            DtvCcPacket dtvCcPacket5 = this.currentDtvCcPacket;
                            int i7 = dtvCcPacket5.currentIndex;
                            dtvCcPacket5.currentIndex = i7 + 1;
                            bArr3[i7] = unsignedByte3;
                        }
                    }
                    if (this.currentDtvCcPacket.currentIndex == (this.currentDtvCcPacket.packetSize * 2) - 1) {
                        finalizeCurrentPacket();
                    }
                }
            }
        }
    }

    private void finalizeCurrentPacket() {
        if (this.currentDtvCcPacket == null) {
            return;
        }
        processCurrentPacket();
        this.currentDtvCcPacket = null;
    }

    @RequiresNonNull({"currentDtvCcPacket"})
    private void processCurrentPacket() {
        if (this.currentDtvCcPacket.currentIndex != (this.currentDtvCcPacket.packetSize * 2) - 1) {
            int i = (this.currentDtvCcPacket.packetSize * 2) - 1;
            Log.d(TAG, new StringBuilder(115).append("DtvCcPacket ended prematurely; size is ").append(i).append(", but current index is ").append(this.currentDtvCcPacket.currentIndex).append(" (sequence number ").append(this.currentDtvCcPacket.sequenceNumber).append(");").toString());
        }
        this.serviceBlockPacket.reset(this.currentDtvCcPacket.packetData, this.currentDtvCcPacket.currentIndex);
        int bits = this.serviceBlockPacket.readBits(3);
        int bits2 = this.serviceBlockPacket.readBits(5);
        if (bits == 7) {
            this.serviceBlockPacket.skipBits(2);
            bits = this.serviceBlockPacket.readBits(6);
            if (bits < 7) {
                Log.w(TAG, new StringBuilder(44).append("Invalid extended service number: ").append(bits).toString());
            }
        }
        if (bits2 == 0) {
            if (bits != 0) {
                Log.w(TAG, new StringBuilder(59).append("serviceNumber is non-zero (").append(bits).append(") when blockSize is 0").toString());
                return;
            }
            return;
        }
        if (bits != this.selectedServiceNumber) {
            return;
        }
        boolean z = false;
        while (this.serviceBlockPacket.bitsLeft() > 0) {
            int bits3 = this.serviceBlockPacket.readBits(8);
            if (bits3 == 16) {
                int bits4 = this.serviceBlockPacket.readBits(8);
                if (bits4 <= 31) {
                    handleC2Command(bits4);
                } else {
                    if (bits4 <= 127) {
                        handleG2Character(bits4);
                    } else if (bits4 <= 159) {
                        handleC3Command(bits4);
                    } else if (bits4 <= 255) {
                        handleG3Character(bits4);
                    } else {
                        Log.w(TAG, new StringBuilder(37).append("Invalid extended command: ").append(bits4).toString());
                    }
                    z = true;
                }
            } else if (bits3 <= 31) {
                handleC0Command(bits3);
            } else {
                if (bits3 <= 127) {
                    handleG0Character(bits3);
                } else if (bits3 <= 159) {
                    handleC1Command(bits3);
                } else if (bits3 <= 255) {
                    handleG1Character(bits3);
                } else {
                    Log.w(TAG, new StringBuilder(33).append("Invalid base command: ").append(bits3).toString());
                }
                z = true;
            }
        }
        if (z) {
            this.cues = getDisplayCues();
        }
    }

    private void handleC0Command(int command) {
        if (command != 0) {
            if (command == 3) {
                this.cues = getDisplayCues();
            }
            if (command == 8) {
                this.currentCueInfoBuilder.backspace();
                return;
            }
            switch (command) {
                case 12:
                    resetCueBuilders();
                    break;
                case 13:
                    this.currentCueInfoBuilder.append('\n');
                    break;
                case 14:
                    break;
                default:
                    if (command >= 17 && command <= 23) {
                        Log.w(TAG, new StringBuilder(55).append("Currently unsupported COMMAND_EXT1 Command: ").append(command).toString());
                        this.serviceBlockPacket.skipBits(8);
                        break;
                    } else if (command >= 24 && command <= 31) {
                        Log.w(TAG, new StringBuilder(54).append("Currently unsupported COMMAND_P16 Command: ").append(command).toString());
                        this.serviceBlockPacket.skipBits(16);
                        break;
                    } else {
                        Log.w(TAG, new StringBuilder(31).append("Invalid C0 command: ").append(command).toString());
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void handleC1Command(int command) {
        int i = 1;
        switch (command) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                int i2 = command - 128;
                if (this.currentWindow != i2) {
                    this.currentWindow = i2;
                    this.currentCueInfoBuilder = this.cueInfoBuilders[i2];
                    break;
                }
                break;
            case COMMAND_CLW /* 136 */:
                while (i <= 8) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueInfoBuilders[8 - i].clear();
                    }
                    i++;
                }
                break;
            case COMMAND_DSW /* 137 */:
                for (int i3 = 1; i3 <= 8; i3++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueInfoBuilders[8 - i3].setVisibility(true);
                    }
                }
                break;
            case 138:
                while (i <= 8) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueInfoBuilders[8 - i].setVisibility(false);
                    }
                    i++;
                }
                break;
            case COMMAND_TGW /* 139 */:
                for (int i4 = 1; i4 <= 8; i4++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueInfoBuilders[8 - i4].setVisibility(!r0.isVisible());
                    }
                }
                break;
            case COMMAND_DLW /* 140 */:
                while (i <= 8) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueInfoBuilders[8 - i].reset();
                    }
                    i++;
                }
                break;
            case COMMAND_DLY /* 141 */:
                this.serviceBlockPacket.skipBits(8);
                break;
            case COMMAND_DLC /* 142 */:
                break;
            case COMMAND_RST /* 143 */:
                resetCueBuilders();
                break;
            case 144:
                if (!this.currentCueInfoBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(16);
                    break;
                } else {
                    handleSetPenAttributes();
                    break;
                }
            case COMMAND_SPC /* 145 */:
                if (!this.currentCueInfoBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(24);
                    break;
                } else {
                    handleSetPenColor();
                    break;
                }
            case COMMAND_SPL /* 146 */:
                if (!this.currentCueInfoBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(16);
                    break;
                } else {
                    handleSetPenLocation();
                    break;
                }
            case 147:
            case 148:
            case 149:
            case 150:
            default:
                Log.w(TAG, new StringBuilder(31).append("Invalid C1 command: ").append(command).toString());
                break;
            case COMMAND_SWA /* 151 */:
                if (!this.currentCueInfoBuilder.isDefined()) {
                    this.serviceBlockPacket.skipBits(32);
                    break;
                } else {
                    handleSetWindowAttributes();
                    break;
                }
            case COMMAND_DF0 /* 152 */:
            case COMMAND_DF1 /* 153 */:
            case COMMAND_DF2 /* 154 */:
            case COMMAND_DF3 /* 155 */:
            case COMMAND_DF4 /* 156 */:
            case COMMAND_DF5 /* 157 */:
            case COMMAND_DF6 /* 158 */:
            case 159:
                int i5 = command - 152;
                handleDefineWindow(i5);
                if (this.currentWindow != i5) {
                    this.currentWindow = i5;
                    this.currentCueInfoBuilder = this.cueInfoBuilders[i5];
                    break;
                }
                break;
        }
    }

    private void handleC2Command(int command) {
        if (command <= 7) {
            return;
        }
        if (command <= 15) {
            this.serviceBlockPacket.skipBits(8);
        } else if (command <= 23) {
            this.serviceBlockPacket.skipBits(16);
        } else if (command <= 31) {
            this.serviceBlockPacket.skipBits(24);
        }
    }

    private void handleC3Command(int command) {
        if (command <= 135) {
            this.serviceBlockPacket.skipBits(32);
            return;
        }
        if (command <= COMMAND_RST) {
            this.serviceBlockPacket.skipBits(40);
        } else if (command <= 159) {
            this.serviceBlockPacket.skipBits(2);
            this.serviceBlockPacket.skipBits(this.serviceBlockPacket.readBits(6) * 8);
        }
    }

    private void handleG0Character(int characterCode) {
        if (characterCode == 127) {
            this.currentCueInfoBuilder.append((char) 9835);
        } else {
            this.currentCueInfoBuilder.append((char) (characterCode & 255));
        }
    }

    private void handleG1Character(int characterCode) {
        this.currentCueInfoBuilder.append((char) (characterCode & 255));
    }

    private void handleG2Character(int characterCode) {
        if (characterCode == 32) {
            this.currentCueInfoBuilder.append(' ');
            return;
        }
        if (characterCode == 33) {
            this.currentCueInfoBuilder.append(Typography.nbsp);
            return;
        }
        if (characterCode == 37) {
            this.currentCueInfoBuilder.append(Typography.ellipsis);
            return;
        }
        if (characterCode == 42) {
            this.currentCueInfoBuilder.append((char) 352);
            return;
        }
        if (characterCode == 44) {
            this.currentCueInfoBuilder.append((char) 338);
            return;
        }
        if (characterCode == 63) {
            this.currentCueInfoBuilder.append((char) 376);
            return;
        }
        if (characterCode == 57) {
            this.currentCueInfoBuilder.append(Typography.tm);
            return;
        }
        if (characterCode == 58) {
            this.currentCueInfoBuilder.append((char) 353);
            return;
        }
        if (characterCode == 60) {
            this.currentCueInfoBuilder.append((char) 339);
            return;
        }
        if (characterCode != 61) {
            switch (characterCode) {
                case 48:
                    this.currentCueInfoBuilder.append((char) 9608);
                    break;
                case 49:
                    this.currentCueInfoBuilder.append(Typography.leftSingleQuote);
                    break;
                case 50:
                    this.currentCueInfoBuilder.append(Typography.rightSingleQuote);
                    break;
                case 51:
                    this.currentCueInfoBuilder.append(Typography.leftDoubleQuote);
                    break;
                case 52:
                    this.currentCueInfoBuilder.append(Typography.rightDoubleQuote);
                    break;
                case 53:
                    this.currentCueInfoBuilder.append(Typography.bullet);
                    break;
                default:
                    switch (characterCode) {
                        case 118:
                            this.currentCueInfoBuilder.append((char) 8539);
                            break;
                        case 119:
                            this.currentCueInfoBuilder.append((char) 8540);
                            break;
                        case 120:
                            this.currentCueInfoBuilder.append((char) 8541);
                            break;
                        case 121:
                            this.currentCueInfoBuilder.append((char) 8542);
                            break;
                        case 122:
                            this.currentCueInfoBuilder.append((char) 9474);
                            break;
                        case 123:
                            this.currentCueInfoBuilder.append((char) 9488);
                            break;
                        case 124:
                            this.currentCueInfoBuilder.append((char) 9492);
                            break;
                        case 125:
                            this.currentCueInfoBuilder.append((char) 9472);
                            break;
                        case 126:
                            this.currentCueInfoBuilder.append((char) 9496);
                            break;
                        case 127:
                            this.currentCueInfoBuilder.append((char) 9484);
                            break;
                        default:
                            Log.w(TAG, new StringBuilder(33).append("Invalid G2 character: ").append(characterCode).toString());
                            break;
                    }
            }
            return;
        }
        this.currentCueInfoBuilder.append((char) 8480);
    }

    private void handleG3Character(int characterCode) {
        if (characterCode == 160) {
            this.currentCueInfoBuilder.append((char) 13252);
        } else {
            Log.w(TAG, new StringBuilder(33).append("Invalid G3 character: ").append(characterCode).toString());
            this.currentCueInfoBuilder.append('_');
        }
    }

    private void handleSetPenAttributes() {
        this.currentCueInfoBuilder.setPenAttributes(this.serviceBlockPacket.readBits(4), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private void handleSetPenColor() {
        int argbColorFromCeaColor = CueInfoBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int argbColorFromCeaColor2 = CueInfoBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        this.serviceBlockPacket.skipBits(2);
        this.currentCueInfoBuilder.setPenColor(argbColorFromCeaColor, argbColorFromCeaColor2, CueInfoBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2)));
    }

    private void handleSetPenLocation() {
        this.serviceBlockPacket.skipBits(4);
        int bits = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        this.currentCueInfoBuilder.setPenLocation(bits, this.serviceBlockPacket.readBits(6));
    }

    private void handleSetWindowAttributes() {
        int argbColorFromCeaColor = CueInfoBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int bits = this.serviceBlockPacket.readBits(2);
        int argbColorFromCeaColor2 = CueInfoBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        if (this.serviceBlockPacket.readBit()) {
            bits |= 4;
        }
        boolean bit = this.serviceBlockPacket.readBit();
        int bits2 = this.serviceBlockPacket.readBits(2);
        int bits3 = this.serviceBlockPacket.readBits(2);
        int bits4 = this.serviceBlockPacket.readBits(2);
        this.serviceBlockPacket.skipBits(8);
        this.currentCueInfoBuilder.setWindowAttributes(argbColorFromCeaColor, argbColorFromCeaColor2, bit, bits, bits2, bits3, bits4);
    }

    private void handleDefineWindow(int window) {
        CueInfoBuilder cueInfoBuilder = this.cueInfoBuilders[window];
        this.serviceBlockPacket.skipBits(2);
        boolean bit = this.serviceBlockPacket.readBit();
        boolean bit2 = this.serviceBlockPacket.readBit();
        boolean bit3 = this.serviceBlockPacket.readBit();
        int bits = this.serviceBlockPacket.readBits(3);
        boolean bit4 = this.serviceBlockPacket.readBit();
        int bits2 = this.serviceBlockPacket.readBits(7);
        int bits3 = this.serviceBlockPacket.readBits(8);
        int bits4 = this.serviceBlockPacket.readBits(4);
        int bits5 = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        int bits6 = this.serviceBlockPacket.readBits(6);
        this.serviceBlockPacket.skipBits(2);
        cueInfoBuilder.defineWindow(bit, bit2, bit3, bits, bit4, bits2, bits3, bits5, bits6, bits4, this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private List<Cue> getDisplayCues() {
        Cea708CueInfo cea708CueInfoBuild;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 8; i++) {
            if (!this.cueInfoBuilders[i].isEmpty() && this.cueInfoBuilders[i].isVisible() && (cea708CueInfoBuild = this.cueInfoBuilders[i].build()) != null) {
                arrayList.add(cea708CueInfoBuild);
            }
        }
        Collections.sort(arrayList, Cea708CueInfo.LEAST_IMPORTANT_FIRST);
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(((Cea708CueInfo) arrayList.get(i2)).cue);
        }
        return Collections.unmodifiableList(arrayList2);
    }

    private void resetCueBuilders() {
        for (int i = 0; i < 8; i++) {
            this.cueInfoBuilders[i].reset();
        }
    }

    private static final class DtvCcPacket {
        int currentIndex = 0;
        public final byte[] packetData;
        public final int packetSize;
        public final int sequenceNumber;

        public DtvCcPacket(int sequenceNumber, int packetSize) {
            this.sequenceNumber = sequenceNumber;
            this.packetSize = packetSize;
            this.packetData = new byte[(packetSize * 2) - 1];
        }
    }

    private static final class CueInfoBuilder {
        private static final int BORDER_AND_EDGE_TYPE_NONE = 0;
        private static final int BORDER_AND_EDGE_TYPE_UNIFORM = 3;
        public static final int COLOR_SOLID_BLACK;
        public static final int COLOR_SOLID_WHITE = getArgbColorFromCeaColor(2, 2, 2, 0);
        public static final int COLOR_TRANSPARENT;
        private static final int DEFAULT_PRIORITY = 4;
        private static final int DIRECTION_BOTTOM_TO_TOP = 3;
        private static final int DIRECTION_LEFT_TO_RIGHT = 0;
        private static final int DIRECTION_RIGHT_TO_LEFT = 1;
        private static final int DIRECTION_TOP_TO_BOTTOM = 2;
        private static final int HORIZONTAL_SIZE = 209;
        private static final int JUSTIFICATION_CENTER = 2;
        private static final int JUSTIFICATION_FULL = 3;
        private static final int JUSTIFICATION_LEFT = 0;
        private static final int JUSTIFICATION_RIGHT = 1;
        private static final int MAXIMUM_ROW_COUNT = 15;
        private static final int PEN_FONT_STYLE_DEFAULT = 0;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITHOUT_SERIFS = 3;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITH_SERIFS = 1;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITHOUT_SERIFS = 4;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITH_SERIFS = 2;
        private static final int PEN_OFFSET_NORMAL = 1;
        private static final int PEN_SIZE_STANDARD = 1;
        private static final int[] PEN_STYLE_BACKGROUND;
        private static final int[] PEN_STYLE_EDGE_TYPE;
        private static final int[] PEN_STYLE_FONT_STYLE;
        private static final int RELATIVE_CUE_SIZE = 99;
        private static final int VERTICAL_SIZE = 74;
        private static final int[] WINDOW_STYLE_FILL;
        private static final int[] WINDOW_STYLE_JUSTIFICATION;
        private static final int[] WINDOW_STYLE_PRINT_DIRECTION;
        private static final int[] WINDOW_STYLE_SCROLL_DIRECTION;
        private static final boolean[] WINDOW_STYLE_WORD_WRAP;
        private int anchorId;
        private int backgroundColor;
        private int backgroundColorStartPosition;
        private boolean defined;
        private int foregroundColor;
        private int foregroundColorStartPosition;
        private int horizontalAnchor;
        private int italicsStartPosition;
        private int justification;
        private int penStyleId;
        private int priority;
        private boolean relativePositioning;
        private int row;
        private int rowCount;
        private boolean rowLock;
        private int underlineStartPosition;
        private int verticalAnchor;
        private boolean visible;
        private int windowFillColor;
        private int windowStyleId;
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        private final SpannableStringBuilder captionStringBuilder = new SpannableStringBuilder();

        static {
            int argbColorFromCeaColor = getArgbColorFromCeaColor(0, 0, 0, 0);
            COLOR_SOLID_BLACK = argbColorFromCeaColor;
            int argbColorFromCeaColor2 = getArgbColorFromCeaColor(0, 0, 0, 3);
            COLOR_TRANSPARENT = argbColorFromCeaColor2;
            WINDOW_STYLE_JUSTIFICATION = new int[]{0, 0, 0, 0, 0, 2, 0};
            WINDOW_STYLE_PRINT_DIRECTION = new int[]{0, 0, 0, 0, 0, 0, 2};
            WINDOW_STYLE_SCROLL_DIRECTION = new int[]{3, 3, 3, 3, 3, 3, 1};
            WINDOW_STYLE_WORD_WRAP = new boolean[]{false, false, false, true, true, true, false};
            WINDOW_STYLE_FILL = new int[]{argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor, argbColorFromCeaColor};
            PEN_STYLE_FONT_STYLE = new int[]{0, 1, 2, 3, 4, 3, 4};
            PEN_STYLE_EDGE_TYPE = new int[]{0, 0, 0, 0, 0, 3, 3};
            PEN_STYLE_BACKGROUND = new int[]{argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor, argbColorFromCeaColor2, argbColorFromCeaColor2};
        }

        public CueInfoBuilder() {
            reset();
        }

        public boolean isEmpty() {
            return !isDefined() || (this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0);
        }

        public void reset() {
            clear();
            this.defined = false;
            this.visible = false;
            this.priority = 4;
            this.relativePositioning = false;
            this.verticalAnchor = 0;
            this.horizontalAnchor = 0;
            this.anchorId = 0;
            this.rowCount = 15;
            this.rowLock = true;
            this.justification = 0;
            this.windowStyleId = 0;
            this.penStyleId = 0;
            int i = COLOR_SOLID_BLACK;
            this.windowFillColor = i;
            this.foregroundColor = COLOR_SOLID_WHITE;
            this.backgroundColor = i;
        }

        public void clear() {
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.italicsStartPosition = -1;
            this.underlineStartPosition = -1;
            this.foregroundColorStartPosition = -1;
            this.backgroundColorStartPosition = -1;
            this.row = 0;
        }

        public boolean isDefined() {
            return this.defined;
        }

        public void setVisibility(boolean visible) {
            this.visible = visible;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void defineWindow(boolean visible, boolean rowLock, boolean columnLock, int priority, boolean relativePositioning, int verticalAnchor, int horizontalAnchor, int rowCount, int columnCount, int anchorId, int windowStyleId, int penStyleId) {
            this.defined = true;
            this.visible = visible;
            this.rowLock = rowLock;
            this.priority = priority;
            this.relativePositioning = relativePositioning;
            this.verticalAnchor = verticalAnchor;
            this.horizontalAnchor = horizontalAnchor;
            this.anchorId = anchorId;
            int i = rowCount + 1;
            if (this.rowCount != i) {
                this.rowCount = i;
                while (true) {
                    if ((!rowLock || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        break;
                    } else {
                        this.rolledUpCaptions.remove(0);
                    }
                }
            }
            if (windowStyleId != 0 && this.windowStyleId != windowStyleId) {
                this.windowStyleId = windowStyleId;
                int i2 = windowStyleId - 1;
                setWindowAttributes(WINDOW_STYLE_FILL[i2], COLOR_TRANSPARENT, WINDOW_STYLE_WORD_WRAP[i2], 0, WINDOW_STYLE_PRINT_DIRECTION[i2], WINDOW_STYLE_SCROLL_DIRECTION[i2], WINDOW_STYLE_JUSTIFICATION[i2]);
            }
            if (penStyleId == 0 || this.penStyleId == penStyleId) {
                return;
            }
            this.penStyleId = penStyleId;
            int i3 = penStyleId - 1;
            setPenAttributes(0, 1, 1, false, false, PEN_STYLE_EDGE_TYPE[i3], PEN_STYLE_FONT_STYLE[i3]);
            setPenColor(COLOR_SOLID_WHITE, PEN_STYLE_BACKGROUND[i3], COLOR_SOLID_BLACK);
        }

        public void setWindowAttributes(int fillColor, int borderColor, boolean wordWrapToggle, int borderType, int printDirection, int scrollDirection, int justification) {
            this.windowFillColor = fillColor;
            this.justification = justification;
        }

        public void setPenAttributes(int textTag, int offset, int penSize, boolean italicsToggle, boolean underlineToggle, int edgeType, int fontStyle) {
            if (this.italicsStartPosition != -1) {
                if (!italicsToggle) {
                    this.captionStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, this.captionStringBuilder.length(), 33);
                    this.italicsStartPosition = -1;
                }
            } else if (italicsToggle) {
                this.italicsStartPosition = this.captionStringBuilder.length();
            }
            if (this.underlineStartPosition == -1) {
                if (underlineToggle) {
                    this.underlineStartPosition = this.captionStringBuilder.length();
                }
            } else {
                if (underlineToggle) {
                    return;
                }
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                this.underlineStartPosition = -1;
            }
        }

        public void setPenColor(int foregroundColor, int backgroundColor, int edgeColor) {
            if (this.foregroundColorStartPosition != -1 && this.foregroundColor != foregroundColor) {
                this.captionStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (foregroundColor != COLOR_SOLID_WHITE) {
                this.foregroundColorStartPosition = this.captionStringBuilder.length();
                this.foregroundColor = foregroundColor;
            }
            if (this.backgroundColorStartPosition != -1 && this.backgroundColor != backgroundColor) {
                this.captionStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (backgroundColor != COLOR_SOLID_BLACK) {
                this.backgroundColorStartPosition = this.captionStringBuilder.length();
                this.backgroundColor = backgroundColor;
            }
        }

        public void setPenLocation(int row, int column) {
            if (this.row != row) {
                append('\n');
            }
            this.row = row;
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public void append(char text) {
            if (text == '\n') {
                this.rolledUpCaptions.add(buildSpannableString());
                this.captionStringBuilder.clear();
                if (this.italicsStartPosition != -1) {
                    this.italicsStartPosition = 0;
                }
                if (this.underlineStartPosition != -1) {
                    this.underlineStartPosition = 0;
                }
                if (this.foregroundColorStartPosition != -1) {
                    this.foregroundColorStartPosition = 0;
                }
                if (this.backgroundColorStartPosition != -1) {
                    this.backgroundColorStartPosition = 0;
                }
                while (true) {
                    if ((!this.rowLock || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        return;
                    } else {
                        this.rolledUpCaptions.remove(0);
                    }
                }
            } else {
                this.captionStringBuilder.append(text);
            }
        }

        public SpannableString buildSpannableString() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.italicsStartPosition != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, length, 33);
                }
                if (this.underlineStartPosition != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
                }
                if (this.foregroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, length, 33);
                }
                if (this.backgroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        public Cea708CueInfo build() {
            Layout.Alignment alignment;
            float f;
            float f2;
            int i;
            int i2;
            if (isEmpty()) {
                return null;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i3 = 0; i3 < this.rolledUpCaptions.size(); i3++) {
                spannableStringBuilder.append((CharSequence) this.rolledUpCaptions.get(i3));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append((CharSequence) buildSpannableString());
            int i4 = this.justification;
            if (i4 == 0) {
                alignment = Layout.Alignment.ALIGN_NORMAL;
            } else if (i4 == 1) {
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
            } else if (i4 != 2) {
                if (i4 != 3) {
                    throw new IllegalArgumentException(new StringBuilder(43).append("Unexpected justification value: ").append(this.justification).toString());
                }
                alignment = Layout.Alignment.ALIGN_NORMAL;
            } else {
                alignment = Layout.Alignment.ALIGN_CENTER;
            }
            Layout.Alignment alignment2 = alignment;
            if (this.relativePositioning) {
                f = this.horizontalAnchor / 99.0f;
                f2 = this.verticalAnchor / 99.0f;
            } else {
                f = this.horizontalAnchor / 209.0f;
                f2 = this.verticalAnchor / 74.0f;
            }
            float f3 = (f * 0.9f) + 0.05f;
            float f4 = (f2 * 0.9f) + 0.05f;
            int i5 = this.anchorId;
            if (i5 / 3 == 0) {
                i = 0;
            } else {
                i = i5 / 3 == 1 ? 1 : 2;
            }
            if (i5 % 3 == 0) {
                i2 = 0;
            } else {
                i2 = i5 % 3 == 1 ? 1 : 2;
            }
            return new Cea708CueInfo(spannableStringBuilder, alignment2, f4, 0, i, f3, i2, -3.4028235E38f, this.windowFillColor != COLOR_SOLID_BLACK, this.windowFillColor, this.priority);
        }

        public static int getArgbColorFromCeaColor(int red, int green, int blue) {
            return getArgbColorFromCeaColor(red, green, blue, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static int getArgbColorFromCeaColor(int r4, int r5, int r6, int r7) {
            /*
                r0 = 0
                r1 = 4
                com.google.android.exoplayer2.util.Assertions.checkIndex(r4, r0, r1)
                com.google.android.exoplayer2.util.Assertions.checkIndex(r5, r0, r1)
                com.google.android.exoplayer2.util.Assertions.checkIndex(r6, r0, r1)
                com.google.android.exoplayer2.util.Assertions.checkIndex(r7, r0, r1)
                r1 = 1
                r2 = 255(0xff, float:3.57E-43)
                if (r7 == 0) goto L21
                if (r7 == r1) goto L21
                r3 = 2
                if (r7 == r3) goto L1e
                r3 = 3
                if (r7 == r3) goto L1c
                goto L21
            L1c:
                r7 = r0
                goto L22
            L1e:
                r7 = 127(0x7f, float:1.78E-43)
                goto L22
            L21:
                r7 = r2
            L22:
                if (r4 <= r1) goto L26
                r4 = r2
                goto L27
            L26:
                r4 = r0
            L27:
                if (r5 <= r1) goto L2b
                r5 = r2
                goto L2c
            L2b:
                r5 = r0
            L2c:
                if (r6 <= r1) goto L2f
                r0 = r2
            L2f:
                int r4 = android.graphics.Color.argb(r7, r4, r5, r0)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea708Decoder.CueInfoBuilder.getArgbColorFromCeaColor(int, int, int, int):int");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Cea708CueInfo {
        private static final Comparator<Cea708CueInfo> LEAST_IMPORTANT_FIRST = new Comparator() { // from class: com.google.android.exoplayer2.text.cea.Cea708Decoder$Cea708CueInfo$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Integer.compare(((Cea708Decoder.Cea708CueInfo) obj2).priority, ((Cea708Decoder.Cea708CueInfo) obj).priority);
            }
        };
        public final Cue cue;
        public final int priority;

        public Cea708CueInfo(CharSequence text, Layout.Alignment textAlignment, float line, int lineType, int lineAnchor, float position, int positionAnchor, float size, boolean windowColorSet, int windowColor, int priority) {
            Cue.Builder size2 = new Cue.Builder().setText(text).setTextAlignment(textAlignment).setLine(line, lineType).setLineAnchor(lineAnchor).setPosition(position).setPositionAnchor(positionAnchor).setSize(size);
            if (windowColorSet) {
                size2.setWindowColor(windowColor);
            }
            this.cue = size2.build();
            this.priority = priority;
        }
    }
}
