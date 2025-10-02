package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.SignedBytes;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class DefaultTsPayloadReaderFactory implements TsPayloadReader.Factory {
    private static final int DESCRIPTOR_TAG_CAPTION_SERVICE = 134;
    public static final int FLAG_ALLOW_NON_IDR_KEYFRAMES = 1;
    public static final int FLAG_DETECT_ACCESS_UNITS = 8;
    public static final int FLAG_ENABLE_HDMV_DTS_AUDIO_STREAMS = 64;
    public static final int FLAG_IGNORE_AAC_STREAM = 2;
    public static final int FLAG_IGNORE_H264_STREAM = 4;
    public static final int FLAG_IGNORE_SPLICE_INFO_STREAM = 16;
    public static final int FLAG_OVERRIDE_CAPTION_DESCRIPTORS = 32;
    private final List<Format> closedCaptionFormats;
    private final int flags;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public DefaultTsPayloadReaderFactory() {
        this(0);
    }

    public DefaultTsPayloadReaderFactory(int flags) {
        this(flags, ImmutableList.of());
    }

    public DefaultTsPayloadReaderFactory(int flags, List<Format> closedCaptionFormats) {
        this.flags = flags;
        this.closedCaptionFormats = closedCaptionFormats;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory
    public SparseArray<TsPayloadReader> createInitialPayloadReaders() {
        return new SparseArray<>();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory
    public TsPayloadReader createPayloadReader(int streamType, TsPayloadReader.EsInfo esInfo) {
        if (streamType == 2) {
            return new PesReader(new H262Reader(buildUserDataReader(esInfo)));
        }
        if (streamType == 3 || streamType == 4) {
            return new PesReader(new MpegAudioReader(esInfo.language));
        }
        if (streamType == 21) {
            return new PesReader(new Id3Reader());
        }
        if (streamType == 27) {
            if (isSet(4)) {
                return null;
            }
            return new PesReader(new H264Reader(buildSeiReader(esInfo), isSet(1), isSet(8)));
        }
        if (streamType == 36) {
            return new PesReader(new H265Reader(buildSeiReader(esInfo)));
        }
        if (streamType != 89) {
            if (streamType != 138) {
                if (streamType == 172) {
                    return new PesReader(new Ac4Reader(esInfo.language));
                }
                if (streamType != 257) {
                    if (streamType != 129) {
                        if (streamType != 130) {
                            if (streamType == 134) {
                                if (isSet(16)) {
                                    return null;
                                }
                                return new SectionReader(new PassthroughSectionPayloadReader(MimeTypes.APPLICATION_SCTE35));
                            }
                            if (streamType != 135) {
                                switch (streamType) {
                                    case 15:
                                        if (isSet(2)) {
                                            return null;
                                        }
                                        return new PesReader(new AdtsReader(false, esInfo.language));
                                    case 16:
                                        return new PesReader(new H263Reader(buildUserDataReader(esInfo)));
                                    case 17:
                                        if (isSet(2)) {
                                            return null;
                                        }
                                        return new PesReader(new LatmReader(esInfo.language));
                                    default:
                                        return null;
                                }
                            }
                        } else if (!isSet(64)) {
                            return null;
                        }
                    }
                    return new PesReader(new Ac3Reader(esInfo.language));
                }
                return new SectionReader(new PassthroughSectionPayloadReader(MimeTypes.APPLICATION_AIT));
            }
            return new PesReader(new DtsReader(esInfo.language));
        }
        return new PesReader(new DvbSubtitleReader(esInfo.dvbSubtitleInfos));
    }

    private SeiReader buildSeiReader(TsPayloadReader.EsInfo esInfo) {
        return new SeiReader(getClosedCaptionFormats(esInfo));
    }

    private UserDataReader buildUserDataReader(TsPayloadReader.EsInfo esInfo) {
        return new UserDataReader(getClosedCaptionFormats(esInfo));
    }

    private List<Format> getClosedCaptionFormats(TsPayloadReader.EsInfo esInfo) {
        String str;
        int i;
        List<byte[]> listBuildCea708InitializationData;
        if (isSet(32)) {
            return this.closedCaptionFormats;
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(esInfo.descriptorBytes);
        List<Format> arrayList = this.closedCaptionFormats;
        while (parsableByteArray.bytesLeft() > 0) {
            int unsignedByte = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
            if (unsignedByte == 134) {
                arrayList = new ArrayList<>();
                int unsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
                for (int i2 = 0; i2 < unsignedByte2; i2++) {
                    String string = parsableByteArray.readString(3);
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    boolean z = (unsignedByte3 & 128) != 0;
                    if (z) {
                        i = unsignedByte3 & 63;
                        str = MimeTypes.APPLICATION_CEA708;
                    } else {
                        str = MimeTypes.APPLICATION_CEA608;
                        i = 1;
                    }
                    byte unsignedByte4 = (byte) parsableByteArray.readUnsignedByte();
                    parsableByteArray.skipBytes(1);
                    if (z) {
                        listBuildCea708InitializationData = CodecSpecificDataUtil.buildCea708InitializationData((unsignedByte4 & SignedBytes.MAX_POWER_OF_TWO) != 0);
                    } else {
                        listBuildCea708InitializationData = null;
                    }
                    arrayList.add(new Format.Builder().setSampleMimeType(str).setLanguage(string).setAccessibilityChannel(i).setInitializationData(listBuildCea708InitializationData).build());
                }
            }
            parsableByteArray.setPosition(position);
        }
        return arrayList;
    }

    private boolean isSet(int flag) {
        return (flag & this.flags) != 0;
    }
}
