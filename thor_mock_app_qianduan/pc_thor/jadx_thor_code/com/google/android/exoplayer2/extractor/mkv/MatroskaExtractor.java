package com.google.android.exoplayer2.extractor.mkv;

import android.util.Pair;
import android.util.SparseArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.thor.basemodule.gui.view.CircleBarView;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.CharUtils;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public class MatroskaExtractor implements Extractor {
    private static final int BLOCK_ADDITIONAL_ID_VP9_ITU_T_35 = 4;
    private static final int BLOCK_ADD_ID_TYPE_DVCC = 1685480259;
    private static final int BLOCK_ADD_ID_TYPE_DVVC = 1685485123;
    private static final int BLOCK_STATE_DATA = 2;
    private static final int BLOCK_STATE_HEADER = 1;
    private static final int BLOCK_STATE_START = 0;
    private static final String CODEC_ID_AAC = "A_AAC";
    private static final String CODEC_ID_AC3 = "A_AC3";
    private static final String CODEC_ID_ACM = "A_MS/ACM";
    private static final String CODEC_ID_ASS = "S_TEXT/ASS";
    private static final String CODEC_ID_AV1 = "V_AV1";
    private static final String CODEC_ID_DTS = "A_DTS";
    private static final String CODEC_ID_DTS_EXPRESS = "A_DTS/EXPRESS";
    private static final String CODEC_ID_DTS_LOSSLESS = "A_DTS/LOSSLESS";
    private static final String CODEC_ID_DVBSUB = "S_DVBSUB";
    private static final String CODEC_ID_E_AC3 = "A_EAC3";
    private static final String CODEC_ID_FLAC = "A_FLAC";
    private static final String CODEC_ID_FOURCC = "V_MS/VFW/FOURCC";
    private static final String CODEC_ID_H264 = "V_MPEG4/ISO/AVC";
    private static final String CODEC_ID_H265 = "V_MPEGH/ISO/HEVC";
    private static final String CODEC_ID_MP2 = "A_MPEG/L2";
    private static final String CODEC_ID_MP3 = "A_MPEG/L3";
    private static final String CODEC_ID_MPEG2 = "V_MPEG2";
    private static final String CODEC_ID_MPEG4_AP = "V_MPEG4/ISO/AP";
    private static final String CODEC_ID_MPEG4_ASP = "V_MPEG4/ISO/ASP";
    private static final String CODEC_ID_MPEG4_SP = "V_MPEG4/ISO/SP";
    private static final String CODEC_ID_OPUS = "A_OPUS";
    private static final String CODEC_ID_PCM_FLOAT = "A_PCM/FLOAT/IEEE";
    private static final String CODEC_ID_PCM_INT_BIG = "A_PCM/INT/BIG";
    private static final String CODEC_ID_PCM_INT_LIT = "A_PCM/INT/LIT";
    private static final String CODEC_ID_PGS = "S_HDMV/PGS";
    private static final String CODEC_ID_SUBRIP = "S_TEXT/UTF8";
    private static final String CODEC_ID_THEORA = "V_THEORA";
    private static final String CODEC_ID_TRUEHD = "A_TRUEHD";
    private static final String CODEC_ID_VOBSUB = "S_VOBSUB";
    private static final String CODEC_ID_VORBIS = "A_VORBIS";
    private static final String CODEC_ID_VP8 = "V_VP8";
    private static final String CODEC_ID_VP9 = "V_VP9";
    private static final String DOC_TYPE_MATROSKA = "matroska";
    private static final String DOC_TYPE_WEBM = "webm";
    private static final int ENCRYPTION_IV_SIZE = 8;
    public static final int FLAG_DISABLE_SEEK_FOR_CUES = 1;
    private static final int FOURCC_COMPRESSION_DIVX = 1482049860;
    private static final int FOURCC_COMPRESSION_H263 = 859189832;
    private static final int FOURCC_COMPRESSION_VC1 = 826496599;
    private static final int ID_AUDIO = 225;
    private static final int ID_AUDIO_BIT_DEPTH = 25188;
    private static final int ID_BLOCK = 161;
    private static final int ID_BLOCK_ADDITIONAL = 165;
    private static final int ID_BLOCK_ADDITIONS = 30113;
    private static final int ID_BLOCK_ADDITION_MAPPING = 16868;
    private static final int ID_BLOCK_ADD_ID = 238;
    private static final int ID_BLOCK_ADD_ID_EXTRA_DATA = 16877;
    private static final int ID_BLOCK_ADD_ID_TYPE = 16871;
    private static final int ID_BLOCK_DURATION = 155;
    private static final int ID_BLOCK_GROUP = 160;
    private static final int ID_BLOCK_MORE = 166;
    private static final int ID_CHANNELS = 159;
    private static final int ID_CLUSTER = 524531317;
    private static final int ID_CODEC_DELAY = 22186;
    private static final int ID_CODEC_ID = 134;
    private static final int ID_CODEC_PRIVATE = 25506;
    private static final int ID_COLOUR = 21936;
    private static final int ID_COLOUR_PRIMARIES = 21947;
    private static final int ID_COLOUR_RANGE = 21945;
    private static final int ID_COLOUR_TRANSFER = 21946;
    private static final int ID_CONTENT_COMPRESSION = 20532;
    private static final int ID_CONTENT_COMPRESSION_ALGORITHM = 16980;
    private static final int ID_CONTENT_COMPRESSION_SETTINGS = 16981;
    private static final int ID_CONTENT_ENCODING = 25152;
    private static final int ID_CONTENT_ENCODINGS = 28032;
    private static final int ID_CONTENT_ENCODING_ORDER = 20529;
    private static final int ID_CONTENT_ENCODING_SCOPE = 20530;
    private static final int ID_CONTENT_ENCRYPTION = 20533;
    private static final int ID_CONTENT_ENCRYPTION_AES_SETTINGS = 18407;
    private static final int ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE = 18408;
    private static final int ID_CONTENT_ENCRYPTION_ALGORITHM = 18401;
    private static final int ID_CONTENT_ENCRYPTION_KEY_ID = 18402;
    private static final int ID_CUES = 475249515;
    private static final int ID_CUE_CLUSTER_POSITION = 241;
    private static final int ID_CUE_POINT = 187;
    private static final int ID_CUE_TIME = 179;
    private static final int ID_CUE_TRACK_POSITIONS = 183;
    private static final int ID_DEFAULT_DURATION = 2352003;
    private static final int ID_DISPLAY_HEIGHT = 21690;
    private static final int ID_DISPLAY_UNIT = 21682;
    private static final int ID_DISPLAY_WIDTH = 21680;
    private static final int ID_DOC_TYPE = 17026;
    private static final int ID_DOC_TYPE_READ_VERSION = 17029;
    private static final int ID_DURATION = 17545;
    private static final int ID_EBML = 440786851;
    private static final int ID_EBML_READ_VERSION = 17143;
    private static final int ID_FLAG_DEFAULT = 136;
    private static final int ID_FLAG_FORCED = 21930;
    private static final int ID_INFO = 357149030;
    private static final int ID_LANGUAGE = 2274716;
    private static final int ID_LUMNINANCE_MAX = 21977;
    private static final int ID_LUMNINANCE_MIN = 21978;
    private static final int ID_MASTERING_METADATA = 21968;
    private static final int ID_MAX_BLOCK_ADDITION_ID = 21998;
    private static final int ID_MAX_CLL = 21948;
    private static final int ID_MAX_FALL = 21949;
    private static final int ID_NAME = 21358;
    private static final int ID_PIXEL_HEIGHT = 186;
    private static final int ID_PIXEL_WIDTH = 176;
    private static final int ID_PRIMARY_B_CHROMATICITY_X = 21973;
    private static final int ID_PRIMARY_B_CHROMATICITY_Y = 21974;
    private static final int ID_PRIMARY_G_CHROMATICITY_X = 21971;
    private static final int ID_PRIMARY_G_CHROMATICITY_Y = 21972;
    private static final int ID_PRIMARY_R_CHROMATICITY_X = 21969;
    private static final int ID_PRIMARY_R_CHROMATICITY_Y = 21970;
    private static final int ID_PROJECTION = 30320;
    private static final int ID_PROJECTION_POSE_PITCH = 30324;
    private static final int ID_PROJECTION_POSE_ROLL = 30325;
    private static final int ID_PROJECTION_POSE_YAW = 30323;
    private static final int ID_PROJECTION_PRIVATE = 30322;
    private static final int ID_PROJECTION_TYPE = 30321;
    private static final int ID_REFERENCE_BLOCK = 251;
    private static final int ID_SAMPLING_FREQUENCY = 181;
    private static final int ID_SEEK = 19899;
    private static final int ID_SEEK_HEAD = 290298740;
    private static final int ID_SEEK_ID = 21419;
    private static final int ID_SEEK_POSITION = 21420;
    private static final int ID_SEEK_PRE_ROLL = 22203;
    private static final int ID_SEGMENT = 408125543;
    private static final int ID_SEGMENT_INFO = 357149030;
    private static final int ID_SIMPLE_BLOCK = 163;
    private static final int ID_STEREO_MODE = 21432;
    private static final int ID_TIMECODE_SCALE = 2807729;
    private static final int ID_TIME_CODE = 231;
    private static final int ID_TRACKS = 374648427;
    private static final int ID_TRACK_ENTRY = 174;
    private static final int ID_TRACK_NUMBER = 215;
    private static final int ID_TRACK_TYPE = 131;
    private static final int ID_VIDEO = 224;
    private static final int ID_WHITE_POINT_CHROMATICITY_X = 21975;
    private static final int ID_WHITE_POINT_CHROMATICITY_Y = 21976;
    private static final int LACING_EBML = 3;
    private static final int LACING_FIXED_SIZE = 2;
    private static final int LACING_NONE = 0;
    private static final int LACING_XIPH = 1;
    private static final int OPUS_MAX_INPUT_SIZE = 5760;
    private static final int SSA_PREFIX_END_TIMECODE_OFFSET = 21;
    private static final String SSA_TIMECODE_FORMAT = "%01d:%02d:%02d:%02d";
    private static final long SSA_TIMECODE_LAST_VALUE_SCALING_FACTOR = 10000;
    private static final int SUBRIP_PREFIX_END_TIMECODE_OFFSET = 19;
    private static final String SUBRIP_TIMECODE_FORMAT = "%02d:%02d:%02d,%03d";
    private static final long SUBRIP_TIMECODE_LAST_VALUE_SCALING_FACTOR = 1000;
    private static final String TAG = "MatroskaExtractor";
    private static final Map<String, Integer> TRACK_NAME_TO_ROTATION_DEGREES;
    private static final int TRACK_TYPE_AUDIO = 2;
    private static final int UNSET_ENTRY_ID = -1;
    private static final int VORBIS_MAX_INPUT_SIZE = 8192;
    private static final int WAVE_FORMAT_EXTENSIBLE = 65534;
    private static final int WAVE_FORMAT_PCM = 1;
    private static final int WAVE_FORMAT_SIZE = 18;
    private final ParsableByteArray blockAdditionalData;
    private int blockAdditionalId;
    private long blockDurationUs;
    private int blockFlags;
    private boolean blockHasReferenceBlock;
    private int blockSampleCount;
    private int blockSampleIndex;
    private int[] blockSampleSizes;
    private int blockState;
    private long blockTimeUs;
    private int blockTrackNumber;
    private int blockTrackNumberLength;
    private long clusterTimecodeUs;
    private LongArray cueClusterPositions;
    private LongArray cueTimesUs;
    private long cuesContentPosition;
    private Track currentTrack;
    private long durationTimecode;
    private long durationUs;
    private final ParsableByteArray encryptionInitializationVector;
    private final ParsableByteArray encryptionSubsampleData;
    private ByteBuffer encryptionSubsampleDataBuffer;
    private ExtractorOutput extractorOutput;
    private boolean haveOutputSample;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private final EbmlReader reader;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private boolean sampleEncodingHandled;
    private boolean sampleInitializationVectorRead;
    private int samplePartitionCount;
    private boolean samplePartitionCountRead;
    private byte sampleSignalByte;
    private boolean sampleSignalByteRead;
    private final ParsableByteArray sampleStrippedBytes;
    private final ParsableByteArray scratch;
    private int seekEntryId;
    private final ParsableByteArray seekEntryIdBytes;
    private long seekEntryPosition;
    private boolean seekForCues;
    private final boolean seekForCuesEnabled;
    private long seekPositionAfterBuildingCues;
    private boolean seenClusterPositionForCurrentCuePoint;
    private long segmentContentPosition;
    private long segmentContentSize;
    private boolean sentSeekMap;
    private final ParsableByteArray subtitleSample;
    private long timecodeScale;
    private final SparseArray<Track> tracks;
    private final VarintReader varintReader;
    private final ParsableByteArray vorbisNumPageSamples;
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return MatroskaExtractor.lambda$static$0();
        }
    };
    private static final byte[] SUBRIP_PREFIX = {49, 10, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 32, 45, 45, 62, 32, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 10};
    private static final byte[] SSA_DIALOGUE_FORMAT = Util.getUtf8Bytes("Format: Start, End, ReadOrder, Layer, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
    private static final byte[] SSA_PREFIX = {68, 105, 97, 108, 111, 103, 117, 101, 58, 32, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44};
    private static final UUID WAVE_SUBFORMAT_PCM = new UUID(72057594037932032L, -9223371306706625679L);

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    protected int getElementType(int id) {
        switch (id) {
            case 131:
            case ID_FLAG_DEFAULT /* 136 */:
            case ID_BLOCK_DURATION /* 155 */:
            case ID_CHANNELS /* 159 */:
            case ID_PIXEL_WIDTH /* 176 */:
            case ID_CUE_TIME /* 179 */:
            case ID_PIXEL_HEIGHT /* 186 */:
            case ID_TRACK_NUMBER /* 215 */:
            case ID_TIME_CODE /* 231 */:
            case ID_BLOCK_ADD_ID /* 238 */:
            case ID_CUE_CLUSTER_POSITION /* 241 */:
            case ID_REFERENCE_BLOCK /* 251 */:
            case ID_BLOCK_ADD_ID_TYPE /* 16871 */:
            case ID_CONTENT_COMPRESSION_ALGORITHM /* 16980 */:
            case ID_DOC_TYPE_READ_VERSION /* 17029 */:
            case ID_EBML_READ_VERSION /* 17143 */:
            case ID_CONTENT_ENCRYPTION_ALGORITHM /* 18401 */:
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE /* 18408 */:
            case ID_CONTENT_ENCODING_ORDER /* 20529 */:
            case ID_CONTENT_ENCODING_SCOPE /* 20530 */:
            case ID_SEEK_POSITION /* 21420 */:
            case ID_STEREO_MODE /* 21432 */:
            case ID_DISPLAY_WIDTH /* 21680 */:
            case ID_DISPLAY_UNIT /* 21682 */:
            case ID_DISPLAY_HEIGHT /* 21690 */:
            case ID_FLAG_FORCED /* 21930 */:
            case ID_COLOUR_RANGE /* 21945 */:
            case ID_COLOUR_TRANSFER /* 21946 */:
            case ID_COLOUR_PRIMARIES /* 21947 */:
            case ID_MAX_CLL /* 21948 */:
            case ID_MAX_FALL /* 21949 */:
            case ID_MAX_BLOCK_ADDITION_ID /* 21998 */:
            case ID_CODEC_DELAY /* 22186 */:
            case ID_SEEK_PRE_ROLL /* 22203 */:
            case ID_AUDIO_BIT_DEPTH /* 25188 */:
            case ID_PROJECTION_TYPE /* 30321 */:
            case ID_DEFAULT_DURATION /* 2352003 */:
            case ID_TIMECODE_SCALE /* 2807729 */:
                return 2;
            case 134:
            case 17026:
            case ID_NAME /* 21358 */:
            case ID_LANGUAGE /* 2274716 */:
                return 3;
            case ID_BLOCK_GROUP /* 160 */:
            case ID_BLOCK_MORE /* 166 */:
            case ID_TRACK_ENTRY /* 174 */:
            case ID_CUE_TRACK_POSITIONS /* 183 */:
            case ID_CUE_POINT /* 187 */:
            case 224:
            case ID_AUDIO /* 225 */:
            case ID_BLOCK_ADDITION_MAPPING /* 16868 */:
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS /* 18407 */:
            case ID_SEEK /* 19899 */:
            case ID_CONTENT_COMPRESSION /* 20532 */:
            case ID_CONTENT_ENCRYPTION /* 20533 */:
            case ID_COLOUR /* 21936 */:
            case ID_MASTERING_METADATA /* 21968 */:
            case ID_CONTENT_ENCODING /* 25152 */:
            case ID_CONTENT_ENCODINGS /* 28032 */:
            case ID_BLOCK_ADDITIONS /* 30113 */:
            case ID_PROJECTION /* 30320 */:
            case ID_SEEK_HEAD /* 290298740 */:
            case 357149030:
            case ID_TRACKS /* 374648427 */:
            case ID_SEGMENT /* 408125543 */:
            case ID_EBML /* 440786851 */:
            case ID_CUES /* 475249515 */:
            case ID_CLUSTER /* 524531317 */:
                return 1;
            case ID_BLOCK /* 161 */:
            case ID_SIMPLE_BLOCK /* 163 */:
            case ID_BLOCK_ADDITIONAL /* 165 */:
            case ID_BLOCK_ADD_ID_EXTRA_DATA /* 16877 */:
            case ID_CONTENT_COMPRESSION_SETTINGS /* 16981 */:
            case ID_CONTENT_ENCRYPTION_KEY_ID /* 18402 */:
            case ID_SEEK_ID /* 21419 */:
            case ID_CODEC_PRIVATE /* 25506 */:
            case ID_PROJECTION_PRIVATE /* 30322 */:
                return 4;
            case ID_SAMPLING_FREQUENCY /* 181 */:
            case ID_DURATION /* 17545 */:
            case ID_PRIMARY_R_CHROMATICITY_X /* 21969 */:
            case ID_PRIMARY_R_CHROMATICITY_Y /* 21970 */:
            case ID_PRIMARY_G_CHROMATICITY_X /* 21971 */:
            case ID_PRIMARY_G_CHROMATICITY_Y /* 21972 */:
            case ID_PRIMARY_B_CHROMATICITY_X /* 21973 */:
            case ID_PRIMARY_B_CHROMATICITY_Y /* 21974 */:
            case ID_WHITE_POINT_CHROMATICITY_X /* 21975 */:
            case ID_WHITE_POINT_CHROMATICITY_Y /* 21976 */:
            case ID_LUMNINANCE_MAX /* 21977 */:
            case ID_LUMNINANCE_MIN /* 21978 */:
            case ID_PROJECTION_POSE_YAW /* 30323 */:
            case ID_PROJECTION_POSE_PITCH /* 30324 */:
            case ID_PROJECTION_POSE_ROLL /* 30325 */:
                return 5;
            default:
                return 0;
        }
    }

    protected boolean isLevel1Element(int id) {
        return id == 357149030 || id == ID_CLUSTER || id == ID_CUES || id == ID_TRACKS;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final void release() {
    }

    static {
        HashMap map = new HashMap();
        map.put("htc_video_rotA-000", 0);
        map.put("htc_video_rotA-090", 90);
        map.put("htc_video_rotA-180", Integer.valueOf(CircleBarView.TWO_QUARTER));
        map.put("htc_video_rotA-270", Integer.valueOf(CircleBarView.THREE_QUARTER));
        TRACK_NAME_TO_ROTATION_DEGREES = Collections.unmodifiableMap(map);
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new MatroskaExtractor()};
    }

    public MatroskaExtractor() {
        this(0);
    }

    public MatroskaExtractor(int flags) {
        this(new DefaultEbmlReader(), flags);
    }

    MatroskaExtractor(EbmlReader reader, int flags) {
        this.segmentContentPosition = -1L;
        this.timecodeScale = C.TIME_UNSET;
        this.durationTimecode = C.TIME_UNSET;
        this.durationUs = C.TIME_UNSET;
        this.cuesContentPosition = -1L;
        this.seekPositionAfterBuildingCues = -1L;
        this.clusterTimecodeUs = C.TIME_UNSET;
        this.reader = reader;
        reader.init(new InnerEbmlProcessor());
        this.seekForCuesEnabled = (flags & 1) == 0;
        this.varintReader = new VarintReader();
        this.tracks = new SparseArray<>();
        this.scratch = new ParsableByteArray(4);
        this.vorbisNumPageSamples = new ParsableByteArray(ByteBuffer.allocate(4).putInt(-1).array());
        this.seekEntryIdBytes = new ParsableByteArray(4);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.sampleStrippedBytes = new ParsableByteArray();
        this.subtitleSample = new ParsableByteArray();
        this.encryptionInitializationVector = new ParsableByteArray(8);
        this.encryptionSubsampleData = new ParsableByteArray();
        this.blockAdditionalData = new ParsableByteArray();
        this.blockSampleSizes = new int[1];
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final boolean sniff(ExtractorInput input) throws IOException {
        return new Sniffer().sniff(input);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final void init(ExtractorOutput output) {
        this.extractorOutput = output;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long position, long timeUs) {
        this.clusterTimecodeUs = C.TIME_UNSET;
        this.blockState = 0;
        this.reader.reset();
        this.varintReader.reset();
        resetWriteSampleData();
        for (int i = 0; i < this.tracks.size(); i++) {
            this.tracks.valueAt(i).reset();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public final int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        this.haveOutputSample = false;
        boolean z = true;
        while (z && !this.haveOutputSample) {
            z = this.reader.read(input);
            if (z && maybeSeekForCues(seekPosition, input.getPosition())) {
                return 1;
            }
        }
        if (z) {
            return 0;
        }
        for (int i = 0; i < this.tracks.size(); i++) {
            Track trackValueAt = this.tracks.valueAt(i);
            trackValueAt.assertOutputInitialized();
            trackValueAt.outputPendingSampleMetadata();
        }
        return -1;
    }

    protected void startMasterElement(int id, long contentPosition, long contentSize) throws ParserException {
        assertInitialized();
        if (id == ID_BLOCK_GROUP) {
            this.blockHasReferenceBlock = false;
            return;
        }
        if (id == ID_TRACK_ENTRY) {
            this.currentTrack = new Track();
            return;
        }
        if (id == ID_CUE_POINT) {
            this.seenClusterPositionForCurrentCuePoint = false;
            return;
        }
        if (id == ID_SEEK) {
            this.seekEntryId = -1;
            this.seekEntryPosition = -1L;
            return;
        }
        if (id == ID_CONTENT_ENCRYPTION) {
            getCurrentTrack(id).hasContentEncryption = true;
            return;
        }
        if (id == ID_MASTERING_METADATA) {
            getCurrentTrack(id).hasColorInfo = true;
            return;
        }
        if (id == ID_SEGMENT) {
            long j = this.segmentContentPosition;
            if (j != -1 && j != contentPosition) {
                throw ParserException.createForMalformedContainer("Multiple Segment elements not supported", null);
            }
            this.segmentContentPosition = contentPosition;
            this.segmentContentSize = contentSize;
            return;
        }
        if (id == ID_CUES) {
            this.cueTimesUs = new LongArray();
            this.cueClusterPositions = new LongArray();
        } else if (id == ID_CLUSTER && !this.sentSeekMap) {
            if (this.seekForCuesEnabled && this.cuesContentPosition != -1) {
                this.seekForCues = true;
            } else {
                this.extractorOutput.seekMap(new SeekMap.Unseekable(this.durationUs));
                this.sentSeekMap = true;
            }
        }
    }

    protected void endMasterElement(int id) throws ParserException {
        assertInitialized();
        if (id == ID_BLOCK_GROUP) {
            if (this.blockState != 2) {
                return;
            }
            int i = 0;
            for (int i2 = 0; i2 < this.blockSampleCount; i2++) {
                i += this.blockSampleSizes[i2];
            }
            Track track = this.tracks.get(this.blockTrackNumber);
            track.assertOutputInitialized();
            for (int i3 = 0; i3 < this.blockSampleCount; i3++) {
                long j = ((track.defaultSampleDurationNs * i3) / 1000) + this.blockTimeUs;
                int i4 = this.blockFlags;
                if (i3 == 0 && !this.blockHasReferenceBlock) {
                    i4 |= 1;
                }
                int i5 = this.blockSampleSizes[i3];
                i -= i5;
                commitSampleToOutput(track, j, i4, i5, i);
            }
            this.blockState = 0;
            return;
        }
        if (id == ID_TRACK_ENTRY) {
            Track track2 = (Track) Assertions.checkStateNotNull(this.currentTrack);
            if (track2.codecId == null) {
                throw ParserException.createForMalformedContainer("CodecId is missing in TrackEntry element", null);
            }
            if (isCodecSupported(track2.codecId)) {
                track2.initializeOutput(this.extractorOutput, track2.number);
                this.tracks.put(track2.number, track2);
            }
            this.currentTrack = null;
            return;
        }
        if (id == ID_SEEK) {
            int i6 = this.seekEntryId;
            if (i6 != -1) {
                long j2 = this.seekEntryPosition;
                if (j2 != -1) {
                    if (i6 == ID_CUES) {
                        this.cuesContentPosition = j2;
                        return;
                    }
                    return;
                }
            }
            throw ParserException.createForMalformedContainer("Mandatory element SeekID or SeekPosition not found", null);
        }
        if (id == ID_CONTENT_ENCODING) {
            assertInTrackEntry(id);
            if (this.currentTrack.hasContentEncryption) {
                if (this.currentTrack.cryptoData == null) {
                    throw ParserException.createForMalformedContainer("Encrypted Track found but ContentEncKeyID was not found", null);
                }
                this.currentTrack.drmInitData = new DrmInitData(new DrmInitData.SchemeData(C.UUID_NIL, MimeTypes.VIDEO_WEBM, this.currentTrack.cryptoData.encryptionKey));
                return;
            }
            return;
        }
        if (id == ID_CONTENT_ENCODINGS) {
            assertInTrackEntry(id);
            if (this.currentTrack.hasContentEncryption && this.currentTrack.sampleStrippedBytes != null) {
                throw ParserException.createForMalformedContainer("Combining encryption and compression is not supported", null);
            }
            return;
        }
        if (id == 357149030) {
            if (this.timecodeScale == C.TIME_UNSET) {
                this.timecodeScale = 1000000L;
            }
            long j3 = this.durationTimecode;
            if (j3 != C.TIME_UNSET) {
                this.durationUs = scaleTimecodeToUs(j3);
                return;
            }
            return;
        }
        if (id != ID_TRACKS) {
            if (id != ID_CUES) {
                return;
            }
            if (!this.sentSeekMap) {
                this.extractorOutput.seekMap(buildSeekMap(this.cueTimesUs, this.cueClusterPositions));
                this.sentSeekMap = true;
            }
            this.cueTimesUs = null;
            this.cueClusterPositions = null;
        } else {
            if (this.tracks.size() == 0) {
                throw ParserException.createForMalformedContainer("No valid tracks were found", null);
            }
            this.extractorOutput.endTracks();
        }
    }

    protected void integerElement(int id, long value) throws ParserException {
        if (id == ID_CONTENT_ENCODING_ORDER) {
            if (value != 0) {
                throw ParserException.createForMalformedContainer(new StringBuilder(55).append("ContentEncodingOrder ").append(value).append(" not supported").toString(), null);
            }
            return;
        }
        if (id == ID_CONTENT_ENCODING_SCOPE) {
            if (value != 1) {
                throw ParserException.createForMalformedContainer(new StringBuilder(55).append("ContentEncodingScope ").append(value).append(" not supported").toString(), null);
            }
            return;
        }
        switch (id) {
            case 131:
                getCurrentTrack(id).type = (int) value;
                return;
            case ID_FLAG_DEFAULT /* 136 */:
                getCurrentTrack(id).flagDefault = value == 1;
                return;
            case ID_BLOCK_DURATION /* 155 */:
                this.blockDurationUs = scaleTimecodeToUs(value);
                return;
            case ID_CHANNELS /* 159 */:
                getCurrentTrack(id).channelCount = (int) value;
                return;
            case ID_PIXEL_WIDTH /* 176 */:
                getCurrentTrack(id).width = (int) value;
                return;
            case ID_CUE_TIME /* 179 */:
                assertInCues(id);
                this.cueTimesUs.add(scaleTimecodeToUs(value));
                return;
            case ID_PIXEL_HEIGHT /* 186 */:
                getCurrentTrack(id).height = (int) value;
                return;
            case ID_TRACK_NUMBER /* 215 */:
                getCurrentTrack(id).number = (int) value;
                return;
            case ID_TIME_CODE /* 231 */:
                this.clusterTimecodeUs = scaleTimecodeToUs(value);
                return;
            case ID_BLOCK_ADD_ID /* 238 */:
                this.blockAdditionalId = (int) value;
                return;
            case ID_CUE_CLUSTER_POSITION /* 241 */:
                if (this.seenClusterPositionForCurrentCuePoint) {
                    return;
                }
                assertInCues(id);
                this.cueClusterPositions.add(value);
                this.seenClusterPositionForCurrentCuePoint = true;
                return;
            case ID_REFERENCE_BLOCK /* 251 */:
                this.blockHasReferenceBlock = true;
                return;
            case ID_BLOCK_ADD_ID_TYPE /* 16871 */:
                getCurrentTrack(id).blockAddIdType = (int) value;
                return;
            case ID_CONTENT_COMPRESSION_ALGORITHM /* 16980 */:
                if (value != 3) {
                    throw ParserException.createForMalformedContainer(new StringBuilder(50).append("ContentCompAlgo ").append(value).append(" not supported").toString(), null);
                }
                return;
            case ID_DOC_TYPE_READ_VERSION /* 17029 */:
                if (value < 1 || value > 2) {
                    throw ParserException.createForMalformedContainer(new StringBuilder(53).append("DocTypeReadVersion ").append(value).append(" not supported").toString(), null);
                }
                return;
            case ID_EBML_READ_VERSION /* 17143 */:
                if (value != 1) {
                    throw ParserException.createForMalformedContainer(new StringBuilder(50).append("EBMLReadVersion ").append(value).append(" not supported").toString(), null);
                }
                return;
            case ID_CONTENT_ENCRYPTION_ALGORITHM /* 18401 */:
                if (value != 5) {
                    throw ParserException.createForMalformedContainer(new StringBuilder(49).append("ContentEncAlgo ").append(value).append(" not supported").toString(), null);
                }
                return;
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE /* 18408 */:
                if (value != 1) {
                    throw ParserException.createForMalformedContainer(new StringBuilder(56).append("AESSettingsCipherMode ").append(value).append(" not supported").toString(), null);
                }
                return;
            case ID_SEEK_POSITION /* 21420 */:
                this.seekEntryPosition = value + this.segmentContentPosition;
                return;
            case ID_STEREO_MODE /* 21432 */:
                int i = (int) value;
                assertInTrackEntry(id);
                if (i == 0) {
                    this.currentTrack.stereoMode = 0;
                    return;
                }
                if (i == 1) {
                    this.currentTrack.stereoMode = 2;
                    return;
                } else if (i == 3) {
                    this.currentTrack.stereoMode = 1;
                    return;
                } else {
                    if (i != 15) {
                        return;
                    }
                    this.currentTrack.stereoMode = 3;
                    return;
                }
            case ID_DISPLAY_WIDTH /* 21680 */:
                getCurrentTrack(id).displayWidth = (int) value;
                return;
            case ID_DISPLAY_UNIT /* 21682 */:
                getCurrentTrack(id).displayUnit = (int) value;
                return;
            case ID_DISPLAY_HEIGHT /* 21690 */:
                getCurrentTrack(id).displayHeight = (int) value;
                return;
            case ID_FLAG_FORCED /* 21930 */:
                getCurrentTrack(id).flagForced = value == 1;
                return;
            case ID_MAX_BLOCK_ADDITION_ID /* 21998 */:
                getCurrentTrack(id).maxBlockAdditionId = (int) value;
                return;
            case ID_CODEC_DELAY /* 22186 */:
                getCurrentTrack(id).codecDelayNs = value;
                return;
            case ID_SEEK_PRE_ROLL /* 22203 */:
                getCurrentTrack(id).seekPreRollNs = value;
                return;
            case ID_AUDIO_BIT_DEPTH /* 25188 */:
                getCurrentTrack(id).audioBitDepth = (int) value;
                return;
            case ID_PROJECTION_TYPE /* 30321 */:
                assertInTrackEntry(id);
                int i2 = (int) value;
                if (i2 == 0) {
                    this.currentTrack.projectionType = 0;
                    return;
                }
                if (i2 == 1) {
                    this.currentTrack.projectionType = 1;
                    return;
                } else if (i2 == 2) {
                    this.currentTrack.projectionType = 2;
                    return;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    this.currentTrack.projectionType = 3;
                    return;
                }
            case ID_DEFAULT_DURATION /* 2352003 */:
                getCurrentTrack(id).defaultSampleDurationNs = (int) value;
                return;
            case ID_TIMECODE_SCALE /* 2807729 */:
                this.timecodeScale = value;
                return;
            default:
                switch (id) {
                    case ID_COLOUR_RANGE /* 21945 */:
                        assertInTrackEntry(id);
                        int i3 = (int) value;
                        if (i3 == 1) {
                            this.currentTrack.colorRange = 2;
                            return;
                        } else {
                            if (i3 != 2) {
                                return;
                            }
                            this.currentTrack.colorRange = 1;
                            return;
                        }
                    case ID_COLOUR_TRANSFER /* 21946 */:
                        assertInTrackEntry(id);
                        int iIsoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer((int) value);
                        if (iIsoTransferCharacteristicsToColorTransfer != -1) {
                            this.currentTrack.colorTransfer = iIsoTransferCharacteristicsToColorTransfer;
                            return;
                        }
                        return;
                    case ID_COLOUR_PRIMARIES /* 21947 */:
                        assertInTrackEntry(id);
                        this.currentTrack.hasColorInfo = true;
                        int iIsoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace((int) value);
                        if (iIsoColorPrimariesToColorSpace != -1) {
                            this.currentTrack.colorSpace = iIsoColorPrimariesToColorSpace;
                            return;
                        }
                        return;
                    case ID_MAX_CLL /* 21948 */:
                        getCurrentTrack(id).maxContentLuminance = (int) value;
                        return;
                    case ID_MAX_FALL /* 21949 */:
                        getCurrentTrack(id).maxFrameAverageLuminance = (int) value;
                        return;
                    default:
                        return;
                }
        }
    }

    protected void floatElement(int id, double value) throws ParserException {
        if (id == ID_SAMPLING_FREQUENCY) {
            getCurrentTrack(id).sampleRate = (int) value;
        }
        if (id == ID_DURATION) {
            this.durationTimecode = (long) value;
            return;
        }
        switch (id) {
            case ID_PRIMARY_R_CHROMATICITY_X /* 21969 */:
                getCurrentTrack(id).primaryRChromaticityX = (float) value;
                break;
            case ID_PRIMARY_R_CHROMATICITY_Y /* 21970 */:
                getCurrentTrack(id).primaryRChromaticityY = (float) value;
                break;
            case ID_PRIMARY_G_CHROMATICITY_X /* 21971 */:
                getCurrentTrack(id).primaryGChromaticityX = (float) value;
                break;
            case ID_PRIMARY_G_CHROMATICITY_Y /* 21972 */:
                getCurrentTrack(id).primaryGChromaticityY = (float) value;
                break;
            case ID_PRIMARY_B_CHROMATICITY_X /* 21973 */:
                getCurrentTrack(id).primaryBChromaticityX = (float) value;
                break;
            case ID_PRIMARY_B_CHROMATICITY_Y /* 21974 */:
                getCurrentTrack(id).primaryBChromaticityY = (float) value;
                break;
            case ID_WHITE_POINT_CHROMATICITY_X /* 21975 */:
                getCurrentTrack(id).whitePointChromaticityX = (float) value;
                break;
            case ID_WHITE_POINT_CHROMATICITY_Y /* 21976 */:
                getCurrentTrack(id).whitePointChromaticityY = (float) value;
                break;
            case ID_LUMNINANCE_MAX /* 21977 */:
                getCurrentTrack(id).maxMasteringLuminance = (float) value;
                break;
            case ID_LUMNINANCE_MIN /* 21978 */:
                getCurrentTrack(id).minMasteringLuminance = (float) value;
                break;
            default:
                switch (id) {
                    case ID_PROJECTION_POSE_YAW /* 30323 */:
                        getCurrentTrack(id).projectionPoseYaw = (float) value;
                        break;
                    case ID_PROJECTION_POSE_PITCH /* 30324 */:
                        getCurrentTrack(id).projectionPosePitch = (float) value;
                        break;
                    case ID_PROJECTION_POSE_ROLL /* 30325 */:
                        getCurrentTrack(id).projectionPoseRoll = (float) value;
                        break;
                }
        }
    }

    protected void stringElement(int id, String value) throws ParserException {
        if (id == 134) {
            getCurrentTrack(id).codecId = value;
            return;
        }
        if (id == 17026) {
            if (!DOC_TYPE_WEBM.equals(value) && !DOC_TYPE_MATROSKA.equals(value)) {
                throw ParserException.createForMalformedContainer(new StringBuilder(String.valueOf(value).length() + 22).append("DocType ").append(value).append(" not supported").toString(), null);
            }
        } else if (id == ID_NAME) {
            getCurrentTrack(id).name = value;
        } else {
            if (id != ID_LANGUAGE) {
                return;
            }
            getCurrentTrack(id).language = value;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x0247, code lost:
    
        throw com.google.android.exoplayer2.ParserException.createForMalformedContainer("EBML lacing sample size out of range.", null);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void binaryElement(int r23, int r24, com.google.android.exoplayer2.extractor.ExtractorInput r25) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 775
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.binaryElement(int, int, com.google.android.exoplayer2.extractor.ExtractorInput):void");
    }

    protected void handleBlockAddIDExtraData(Track track, ExtractorInput input, int contentSize) throws IOException {
        if (track.blockAddIdType == 1685485123 || track.blockAddIdType == 1685480259) {
            track.dolbyVisionConfigBytes = new byte[contentSize];
            input.readFully(track.dolbyVisionConfigBytes, 0, contentSize);
        } else {
            input.skipFully(contentSize);
        }
    }

    protected void handleBlockAdditionalData(Track track, int blockAdditionalId, ExtractorInput input, int contentSize) throws IOException {
        if (blockAdditionalId == 4 && CODEC_ID_VP9.equals(track.codecId)) {
            this.blockAdditionalData.reset(contentSize);
            input.readFully(this.blockAdditionalData.getData(), 0, contentSize);
        } else {
            input.skipFully(contentSize);
        }
    }

    @EnsuresNonNull({"currentTrack"})
    private void assertInTrackEntry(int id) throws ParserException {
        if (this.currentTrack == null) {
            throw ParserException.createForMalformedContainer(new StringBuilder(43).append("Element ").append(id).append(" must be in a TrackEntry").toString(), null);
        }
    }

    @EnsuresNonNull({"cueTimesUs", "cueClusterPositions"})
    private void assertInCues(int id) throws ParserException {
        if (this.cueTimesUs == null || this.cueClusterPositions == null) {
            throw ParserException.createForMalformedContainer(new StringBuilder(37).append("Element ").append(id).append(" must be in a Cues").toString(), null);
        }
    }

    private Track getCurrentTrack(int currentElementId) throws ParserException {
        assertInTrackEntry(currentElementId);
        return this.currentTrack;
    }

    @RequiresNonNull({"#1.output"})
    private void commitSampleToOutput(Track track, long timeUs, int flags, int size, int offset) {
        if (track.trueHdSampleRechunker != null) {
            track.trueHdSampleRechunker.sampleMetadata(track, timeUs, flags, size, offset);
        } else {
            if (CODEC_ID_SUBRIP.equals(track.codecId) || CODEC_ID_ASS.equals(track.codecId)) {
                if (this.blockSampleCount > 1) {
                    Log.w(TAG, "Skipping subtitle sample in laced block.");
                } else if (this.blockDurationUs == C.TIME_UNSET) {
                    Log.w(TAG, "Skipping subtitle sample with no duration.");
                } else {
                    setSubtitleEndTime(track.codecId, this.blockDurationUs, this.subtitleSample.getData());
                    int position = this.subtitleSample.getPosition();
                    while (true) {
                        if (position >= this.subtitleSample.limit()) {
                            break;
                        }
                        if (this.subtitleSample.getData()[position] == 0) {
                            this.subtitleSample.setLimit(position);
                            break;
                        }
                        position++;
                    }
                    TrackOutput trackOutput = track.output;
                    ParsableByteArray parsableByteArray = this.subtitleSample;
                    trackOutput.sampleData(parsableByteArray, parsableByteArray.limit());
                    size += this.subtitleSample.limit();
                }
            }
            if ((268435456 & flags) != 0) {
                if (this.blockSampleCount > 1) {
                    flags &= -268435457;
                } else {
                    int iLimit = this.blockAdditionalData.limit();
                    track.output.sampleData(this.blockAdditionalData, iLimit, 2);
                    size += iLimit;
                }
            }
            track.output.sampleMetadata(timeUs, flags, size, offset, track.cryptoData);
        }
        this.haveOutputSample = true;
    }

    private void readScratch(ExtractorInput input, int requiredLength) throws IOException {
        if (this.scratch.limit() >= requiredLength) {
            return;
        }
        if (this.scratch.capacity() < requiredLength) {
            ParsableByteArray parsableByteArray = this.scratch;
            parsableByteArray.ensureCapacity(Math.max(parsableByteArray.capacity() * 2, requiredLength));
        }
        input.readFully(this.scratch.getData(), this.scratch.limit(), requiredLength - this.scratch.limit());
        this.scratch.setLimit(requiredLength);
    }

    @RequiresNonNull({"#2.output"})
    private int writeSampleData(ExtractorInput input, Track track, int size) throws IOException {
        int i;
        if (CODEC_ID_SUBRIP.equals(track.codecId)) {
            writeSubtitleSampleData(input, SUBRIP_PREFIX, size);
            return finishWriteSampleData();
        }
        if (CODEC_ID_ASS.equals(track.codecId)) {
            writeSubtitleSampleData(input, SSA_PREFIX, size);
            return finishWriteSampleData();
        }
        TrackOutput trackOutput = track.output;
        if (!this.sampleEncodingHandled) {
            if (track.hasContentEncryption) {
                this.blockFlags &= -1073741825;
                if (!this.sampleSignalByteRead) {
                    input.readFully(this.scratch.getData(), 0, 1);
                    this.sampleBytesRead++;
                    if ((this.scratch.getData()[0] & 128) == 128) {
                        throw ParserException.createForMalformedContainer("Extension bit is set in signal byte", null);
                    }
                    this.sampleSignalByte = this.scratch.getData()[0];
                    this.sampleSignalByteRead = true;
                }
                byte b = this.sampleSignalByte;
                if ((b & 1) == 1) {
                    boolean z = (b & 2) == 2;
                    this.blockFlags |= 1073741824;
                    if (!this.sampleInitializationVectorRead) {
                        input.readFully(this.encryptionInitializationVector.getData(), 0, 8);
                        this.sampleBytesRead += 8;
                        this.sampleInitializationVectorRead = true;
                        this.scratch.getData()[0] = (byte) ((z ? 128 : 0) | 8);
                        this.scratch.setPosition(0);
                        trackOutput.sampleData(this.scratch, 1, 1);
                        this.sampleBytesWritten++;
                        this.encryptionInitializationVector.setPosition(0);
                        trackOutput.sampleData(this.encryptionInitializationVector, 8, 1);
                        this.sampleBytesWritten += 8;
                    }
                    if (z) {
                        if (!this.samplePartitionCountRead) {
                            input.readFully(this.scratch.getData(), 0, 1);
                            this.sampleBytesRead++;
                            this.scratch.setPosition(0);
                            this.samplePartitionCount = this.scratch.readUnsignedByte();
                            this.samplePartitionCountRead = true;
                        }
                        int i2 = this.samplePartitionCount * 4;
                        this.scratch.reset(i2);
                        input.readFully(this.scratch.getData(), 0, i2);
                        this.sampleBytesRead += i2;
                        short s = (short) ((this.samplePartitionCount / 2) + 1);
                        int i3 = (s * 6) + 2;
                        ByteBuffer byteBuffer = this.encryptionSubsampleDataBuffer;
                        if (byteBuffer == null || byteBuffer.capacity() < i3) {
                            this.encryptionSubsampleDataBuffer = ByteBuffer.allocate(i3);
                        }
                        this.encryptionSubsampleDataBuffer.position(0);
                        this.encryptionSubsampleDataBuffer.putShort(s);
                        int i4 = 0;
                        int i5 = 0;
                        while (true) {
                            i = this.samplePartitionCount;
                            if (i4 >= i) {
                                break;
                            }
                            int unsignedIntToInt = this.scratch.readUnsignedIntToInt();
                            if (i4 % 2 == 0) {
                                this.encryptionSubsampleDataBuffer.putShort((short) (unsignedIntToInt - i5));
                            } else {
                                this.encryptionSubsampleDataBuffer.putInt(unsignedIntToInt - i5);
                            }
                            i4++;
                            i5 = unsignedIntToInt;
                        }
                        int i6 = (size - this.sampleBytesRead) - i5;
                        if (i % 2 == 1) {
                            this.encryptionSubsampleDataBuffer.putInt(i6);
                        } else {
                            this.encryptionSubsampleDataBuffer.putShort((short) i6);
                            this.encryptionSubsampleDataBuffer.putInt(0);
                        }
                        this.encryptionSubsampleData.reset(this.encryptionSubsampleDataBuffer.array(), i3);
                        trackOutput.sampleData(this.encryptionSubsampleData, i3, 1);
                        this.sampleBytesWritten += i3;
                    }
                }
            } else if (track.sampleStrippedBytes != null) {
                this.sampleStrippedBytes.reset(track.sampleStrippedBytes, track.sampleStrippedBytes.length);
            }
            if (track.maxBlockAdditionId > 0) {
                this.blockFlags |= 268435456;
                this.blockAdditionalData.reset(0);
                this.scratch.reset(4);
                this.scratch.getData()[0] = (byte) ((size >> 24) & 255);
                this.scratch.getData()[1] = (byte) ((size >> 16) & 255);
                this.scratch.getData()[2] = (byte) ((size >> 8) & 255);
                this.scratch.getData()[3] = (byte) (size & 255);
                trackOutput.sampleData(this.scratch, 4, 2);
                this.sampleBytesWritten += 4;
            }
            this.sampleEncodingHandled = true;
        }
        int iLimit = size + this.sampleStrippedBytes.limit();
        if (CODEC_ID_H264.equals(track.codecId) || CODEC_ID_H265.equals(track.codecId)) {
            byte[] data = this.nalLength.getData();
            data[0] = 0;
            data[1] = 0;
            data[2] = 0;
            int i7 = track.nalUnitLengthFieldLength;
            int i8 = 4 - track.nalUnitLengthFieldLength;
            while (this.sampleBytesRead < iLimit) {
                int i9 = this.sampleCurrentNalBytesRemaining;
                if (i9 == 0) {
                    writeToTarget(input, data, i8, i7);
                    this.sampleBytesRead += i7;
                    this.nalLength.setPosition(0);
                    this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
                    this.nalStartCode.setPosition(0);
                    trackOutput.sampleData(this.nalStartCode, 4);
                    this.sampleBytesWritten += 4;
                } else {
                    int iWriteToOutput = writeToOutput(input, trackOutput, i9);
                    this.sampleBytesRead += iWriteToOutput;
                    this.sampleBytesWritten += iWriteToOutput;
                    this.sampleCurrentNalBytesRemaining -= iWriteToOutput;
                }
            }
        } else {
            if (track.trueHdSampleRechunker != null) {
                Assertions.checkState(this.sampleStrippedBytes.limit() == 0);
                track.trueHdSampleRechunker.startSample(input);
            }
            while (true) {
                int i10 = this.sampleBytesRead;
                if (i10 >= iLimit) {
                    break;
                }
                int iWriteToOutput2 = writeToOutput(input, trackOutput, iLimit - i10);
                this.sampleBytesRead += iWriteToOutput2;
                this.sampleBytesWritten += iWriteToOutput2;
            }
        }
        if (CODEC_ID_VORBIS.equals(track.codecId)) {
            this.vorbisNumPageSamples.setPosition(0);
            trackOutput.sampleData(this.vorbisNumPageSamples, 4);
            this.sampleBytesWritten += 4;
        }
        return finishWriteSampleData();
    }

    private int finishWriteSampleData() {
        int i = this.sampleBytesWritten;
        resetWriteSampleData();
        return i;
    }

    private void resetWriteSampleData() {
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        this.sampleEncodingHandled = false;
        this.sampleSignalByteRead = false;
        this.samplePartitionCountRead = false;
        this.samplePartitionCount = 0;
        this.sampleSignalByte = (byte) 0;
        this.sampleInitializationVectorRead = false;
        this.sampleStrippedBytes.reset(0);
    }

    private void writeSubtitleSampleData(ExtractorInput input, byte[] samplePrefix, int size) throws IOException {
        int length = samplePrefix.length + size;
        if (this.subtitleSample.capacity() < length) {
            this.subtitleSample.reset(Arrays.copyOf(samplePrefix, length + size));
        } else {
            System.arraycopy(samplePrefix, 0, this.subtitleSample.getData(), 0, samplePrefix.length);
        }
        input.readFully(this.subtitleSample.getData(), samplePrefix.length, size);
        this.subtitleSample.setPosition(0);
        this.subtitleSample.setLimit(length);
    }

    private static void setSubtitleEndTime(String codecId, long durationUs, byte[] subtitleData) {
        byte[] subtitleTimecode;
        int i;
        codecId.hashCode();
        if (codecId.equals(CODEC_ID_ASS)) {
            subtitleTimecode = formatSubtitleTimecode(durationUs, SSA_TIMECODE_FORMAT, 10000L);
            i = 21;
        } else if (codecId.equals(CODEC_ID_SUBRIP)) {
            subtitleTimecode = formatSubtitleTimecode(durationUs, SUBRIP_TIMECODE_FORMAT, 1000L);
            i = 19;
        } else {
            throw new IllegalArgumentException();
        }
        System.arraycopy(subtitleTimecode, 0, subtitleData, i, subtitleTimecode.length);
    }

    private static byte[] formatSubtitleTimecode(long timeUs, String timecodeFormat, long lastTimecodeValueScalingFactor) {
        Assertions.checkArgument(timeUs != C.TIME_UNSET);
        int i = (int) (timeUs / 3600000000L);
        long j = timeUs - ((i * 3600) * 1000000);
        int i2 = (int) (j / 60000000);
        long j2 = j - ((i2 * 60) * 1000000);
        int i3 = (int) (j2 / 1000000);
        return Util.getUtf8Bytes(String.format(Locale.US, timecodeFormat, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf((int) ((j2 - (i3 * 1000000)) / lastTimecodeValueScalingFactor))));
    }

    private void writeToTarget(ExtractorInput input, byte[] target, int offset, int length) throws IOException {
        int iMin = Math.min(length, this.sampleStrippedBytes.bytesLeft());
        input.readFully(target, offset + iMin, length - iMin);
        if (iMin > 0) {
            this.sampleStrippedBytes.readBytes(target, offset, iMin);
        }
    }

    private int writeToOutput(ExtractorInput input, TrackOutput output, int length) throws IOException {
        int iBytesLeft = this.sampleStrippedBytes.bytesLeft();
        if (iBytesLeft > 0) {
            int iMin = Math.min(length, iBytesLeft);
            output.sampleData(this.sampleStrippedBytes, iMin);
            return iMin;
        }
        return output.sampleData((DataReader) input, length, false);
    }

    private SeekMap buildSeekMap(LongArray cueTimesUs, LongArray cueClusterPositions) {
        int i;
        if (this.segmentContentPosition == -1 || this.durationUs == C.TIME_UNSET || cueTimesUs == null || cueTimesUs.size() == 0 || cueClusterPositions == null || cueClusterPositions.size() != cueTimesUs.size()) {
            return new SeekMap.Unseekable(this.durationUs);
        }
        int size = cueTimesUs.size();
        int[] iArrCopyOf = new int[size];
        long[] jArrCopyOf = new long[size];
        long[] jArrCopyOf2 = new long[size];
        long[] jArrCopyOf3 = new long[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            jArrCopyOf3[i3] = cueTimesUs.get(i3);
            jArrCopyOf[i3] = this.segmentContentPosition + cueClusterPositions.get(i3);
        }
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            int i4 = i2 + 1;
            iArrCopyOf[i2] = (int) (jArrCopyOf[i4] - jArrCopyOf[i2]);
            jArrCopyOf2[i2] = jArrCopyOf3[i4] - jArrCopyOf3[i2];
            i2 = i4;
        }
        iArrCopyOf[i] = (int) ((this.segmentContentPosition + this.segmentContentSize) - jArrCopyOf[i]);
        long j = this.durationUs - jArrCopyOf3[i];
        jArrCopyOf2[i] = j;
        if (j <= 0) {
            Log.w(TAG, new StringBuilder(72).append("Discarding last cue point with unexpected duration: ").append(j).toString());
            iArrCopyOf = Arrays.copyOf(iArrCopyOf, i);
            jArrCopyOf = Arrays.copyOf(jArrCopyOf, i);
            jArrCopyOf2 = Arrays.copyOf(jArrCopyOf2, i);
            jArrCopyOf3 = Arrays.copyOf(jArrCopyOf3, i);
        }
        return new ChunkIndex(iArrCopyOf, jArrCopyOf, jArrCopyOf2, jArrCopyOf3);
    }

    private boolean maybeSeekForCues(PositionHolder seekPosition, long currentPosition) {
        if (this.seekForCues) {
            this.seekPositionAfterBuildingCues = currentPosition;
            seekPosition.position = this.cuesContentPosition;
            this.seekForCues = false;
            return true;
        }
        if (this.sentSeekMap) {
            long j = this.seekPositionAfterBuildingCues;
            if (j != -1) {
                seekPosition.position = j;
                this.seekPositionAfterBuildingCues = -1L;
                return true;
            }
        }
        return false;
    }

    private long scaleTimecodeToUs(long unscaledTimecode) throws ParserException {
        long j = this.timecodeScale;
        if (j == C.TIME_UNSET) {
            throw ParserException.createForMalformedContainer("Can't scale timecode prior to timecodeScale being set.", null);
        }
        return Util.scaleLargeTimestamp(unscaledTimecode, j, 1000L);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static boolean isCodecSupported(String codecId) {
        codecId.hashCode();
        char c = 65535;
        switch (codecId.hashCode()) {
            case -2095576542:
                if (codecId.equals(CODEC_ID_MPEG4_AP)) {
                    c = 0;
                    break;
                }
                break;
            case -2095575984:
                if (codecId.equals(CODEC_ID_MPEG4_SP)) {
                    c = 1;
                    break;
                }
                break;
            case -1985379776:
                if (codecId.equals(CODEC_ID_ACM)) {
                    c = 2;
                    break;
                }
                break;
            case -1784763192:
                if (codecId.equals(CODEC_ID_TRUEHD)) {
                    c = 3;
                    break;
                }
                break;
            case -1730367663:
                if (codecId.equals(CODEC_ID_VORBIS)) {
                    c = 4;
                    break;
                }
                break;
            case -1482641358:
                if (codecId.equals(CODEC_ID_MP2)) {
                    c = 5;
                    break;
                }
                break;
            case -1482641357:
                if (codecId.equals(CODEC_ID_MP3)) {
                    c = 6;
                    break;
                }
                break;
            case -1373388978:
                if (codecId.equals(CODEC_ID_FOURCC)) {
                    c = 7;
                    break;
                }
                break;
            case -933872740:
                if (codecId.equals(CODEC_ID_DVBSUB)) {
                    c = '\b';
                    break;
                }
                break;
            case -538363189:
                if (codecId.equals(CODEC_ID_MPEG4_ASP)) {
                    c = '\t';
                    break;
                }
                break;
            case -538363109:
                if (codecId.equals(CODEC_ID_H264)) {
                    c = '\n';
                    break;
                }
                break;
            case -425012669:
                if (codecId.equals(CODEC_ID_VOBSUB)) {
                    c = 11;
                    break;
                }
                break;
            case -356037306:
                if (codecId.equals(CODEC_ID_DTS_LOSSLESS)) {
                    c = '\f';
                    break;
                }
                break;
            case 62923557:
                if (codecId.equals(CODEC_ID_AAC)) {
                    c = CharUtils.CR;
                    break;
                }
                break;
            case 62923603:
                if (codecId.equals(CODEC_ID_AC3)) {
                    c = 14;
                    break;
                }
                break;
            case 62927045:
                if (codecId.equals(CODEC_ID_DTS)) {
                    c = 15;
                    break;
                }
                break;
            case 82318131:
                if (codecId.equals(CODEC_ID_AV1)) {
                    c = 16;
                    break;
                }
                break;
            case 82338133:
                if (codecId.equals(CODEC_ID_VP8)) {
                    c = 17;
                    break;
                }
                break;
            case 82338134:
                if (codecId.equals(CODEC_ID_VP9)) {
                    c = 18;
                    break;
                }
                break;
            case 99146302:
                if (codecId.equals(CODEC_ID_PGS)) {
                    c = 19;
                    break;
                }
                break;
            case 444813526:
                if (codecId.equals(CODEC_ID_THEORA)) {
                    c = 20;
                    break;
                }
                break;
            case 542569478:
                if (codecId.equals(CODEC_ID_DTS_EXPRESS)) {
                    c = 21;
                    break;
                }
                break;
            case 635596514:
                if (codecId.equals(CODEC_ID_PCM_FLOAT)) {
                    c = 22;
                    break;
                }
                break;
            case 725948237:
                if (codecId.equals(CODEC_ID_PCM_INT_BIG)) {
                    c = 23;
                    break;
                }
                break;
            case 725957860:
                if (codecId.equals(CODEC_ID_PCM_INT_LIT)) {
                    c = 24;
                    break;
                }
                break;
            case 738597099:
                if (codecId.equals(CODEC_ID_ASS)) {
                    c = 25;
                    break;
                }
                break;
            case 855502857:
                if (codecId.equals(CODEC_ID_H265)) {
                    c = 26;
                    break;
                }
                break;
            case 1422270023:
                if (codecId.equals(CODEC_ID_SUBRIP)) {
                    c = 27;
                    break;
                }
                break;
            case 1809237540:
                if (codecId.equals(CODEC_ID_MPEG2)) {
                    c = 28;
                    break;
                }
                break;
            case 1950749482:
                if (codecId.equals(CODEC_ID_E_AC3)) {
                    c = 29;
                    break;
                }
                break;
            case 1950789798:
                if (codecId.equals(CODEC_ID_FLAC)) {
                    c = 30;
                    break;
                }
                break;
            case 1951062397:
                if (codecId.equals(CODEC_ID_OPUS)) {
                    c = 31;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return true;
            default:
                return false;
        }
    }

    private static int[] ensureArrayCapacity(int[] array, int length) {
        if (array == null) {
            return new int[length];
        }
        return array.length >= length ? array : new int[Math.max(array.length * 2, length)];
    }

    @EnsuresNonNull({"extractorOutput"})
    private void assertInitialized() {
        Assertions.checkStateNotNull(this.extractorOutput);
    }

    private final class InnerEbmlProcessor implements EbmlProcessor {
        private InnerEbmlProcessor() {
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public int getElementType(int id) {
            return MatroskaExtractor.this.getElementType(id);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public boolean isLevel1Element(int id) {
            return MatroskaExtractor.this.isLevel1Element(id);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void startMasterElement(int id, long contentPosition, long contentSize) throws ParserException {
            MatroskaExtractor.this.startMasterElement(id, contentPosition, contentSize);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void endMasterElement(int id) throws ParserException {
            MatroskaExtractor.this.endMasterElement(id);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void integerElement(int id, long value) throws ParserException {
            MatroskaExtractor.this.integerElement(id, value);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void floatElement(int id, double value) throws ParserException {
            MatroskaExtractor.this.floatElement(id, value);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void stringElement(int id, String value) throws ParserException {
            MatroskaExtractor.this.stringElement(id, value);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlProcessor
        public void binaryElement(int id, int contentsSize, ExtractorInput input) throws IOException {
            MatroskaExtractor.this.binaryElement(id, contentsSize, input);
        }
    }

    private static final class TrueHdSampleRechunker {
        private int chunkFlags;
        private int chunkOffset;
        private int chunkSampleCount;
        private int chunkSize;
        private long chunkTimeUs;
        private boolean foundSyncframe;
        private final byte[] syncframePrefix = new byte[10];

        public void reset() {
            this.foundSyncframe = false;
            this.chunkSampleCount = 0;
        }

        public void startSample(ExtractorInput input) throws IOException {
            if (this.foundSyncframe) {
                return;
            }
            input.peekFully(this.syncframePrefix, 0, 10);
            input.resetPeekPosition();
            if (Ac3Util.parseTrueHdSyncframeAudioSampleCount(this.syncframePrefix) == 0) {
                return;
            }
            this.foundSyncframe = true;
        }

        @RequiresNonNull({"#1.output"})
        public void sampleMetadata(Track track, long timeUs, int flags, int size, int offset) {
            if (this.foundSyncframe) {
                int i = this.chunkSampleCount;
                int i2 = i + 1;
                this.chunkSampleCount = i2;
                if (i == 0) {
                    this.chunkTimeUs = timeUs;
                    this.chunkFlags = flags;
                    this.chunkSize = 0;
                }
                this.chunkSize += size;
                this.chunkOffset = offset;
                if (i2 >= 16) {
                    outputPendingSampleMetadata(track);
                }
            }
        }

        @RequiresNonNull({"#1.output"})
        public void outputPendingSampleMetadata(Track track) {
            if (this.chunkSampleCount > 0) {
                track.output.sampleMetadata(this.chunkTimeUs, this.chunkFlags, this.chunkSize, this.chunkOffset, track.cryptoData);
                this.chunkSampleCount = 0;
            }
        }
    }

    private static final class Track {
        private static final int DEFAULT_MAX_CLL = 1000;
        private static final int DEFAULT_MAX_FALL = 200;
        private static final int DISPLAY_UNIT_PIXELS = 0;
        private static final int MAX_CHROMATICITY = 50000;
        public int audioBitDepth;
        private int blockAddIdType;
        public int channelCount;
        public long codecDelayNs;
        public String codecId;
        public byte[] codecPrivate;
        public int colorRange;
        public int colorSpace;
        public int colorTransfer;
        public TrackOutput.CryptoData cryptoData;
        public int defaultSampleDurationNs;
        public int displayHeight;
        public int displayUnit;
        public int displayWidth;
        public byte[] dolbyVisionConfigBytes;
        public DrmInitData drmInitData;
        public boolean flagDefault;
        public boolean flagForced;
        public boolean hasColorInfo;
        public boolean hasContentEncryption;
        public int height;
        private String language;
        public int maxBlockAdditionId;
        public int maxContentLuminance;
        public int maxFrameAverageLuminance;
        public float maxMasteringLuminance;
        public float minMasteringLuminance;
        public int nalUnitLengthFieldLength;
        public String name;
        public int number;
        public TrackOutput output;
        public float primaryBChromaticityX;
        public float primaryBChromaticityY;
        public float primaryGChromaticityX;
        public float primaryGChromaticityY;
        public float primaryRChromaticityX;
        public float primaryRChromaticityY;
        public byte[] projectionData;
        public float projectionPosePitch;
        public float projectionPoseRoll;
        public float projectionPoseYaw;
        public int projectionType;
        public int sampleRate;
        public byte[] sampleStrippedBytes;
        public long seekPreRollNs;
        public int stereoMode;
        public TrueHdSampleRechunker trueHdSampleRechunker;
        public int type;
        public float whitePointChromaticityX;
        public float whitePointChromaticityY;
        public int width;

        private Track() {
            this.width = -1;
            this.height = -1;
            this.displayWidth = -1;
            this.displayHeight = -1;
            this.displayUnit = 0;
            this.projectionType = -1;
            this.projectionPoseYaw = 0.0f;
            this.projectionPosePitch = 0.0f;
            this.projectionPoseRoll = 0.0f;
            this.projectionData = null;
            this.stereoMode = -1;
            this.hasColorInfo = false;
            this.colorSpace = -1;
            this.colorTransfer = -1;
            this.colorRange = -1;
            this.maxContentLuminance = 1000;
            this.maxFrameAverageLuminance = 200;
            this.primaryRChromaticityX = -1.0f;
            this.primaryRChromaticityY = -1.0f;
            this.primaryGChromaticityX = -1.0f;
            this.primaryGChromaticityY = -1.0f;
            this.primaryBChromaticityX = -1.0f;
            this.primaryBChromaticityY = -1.0f;
            this.whitePointChromaticityX = -1.0f;
            this.whitePointChromaticityY = -1.0f;
            this.maxMasteringLuminance = -1.0f;
            this.minMasteringLuminance = -1.0f;
            this.channelCount = 1;
            this.audioBitDepth = -1;
            this.sampleRate = 8000;
            this.codecDelayNs = 0L;
            this.seekPreRollNs = 0L;
            this.flagDefault = true;
            this.language = "eng";
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:200:0x041e  */
        /* JADX WARN: Removed duplicated region for block: B:205:0x0439  */
        /* JADX WARN: Removed duplicated region for block: B:206:0x043b  */
        /* JADX WARN: Removed duplicated region for block: B:209:0x0448  */
        /* JADX WARN: Removed duplicated region for block: B:210:0x045a  */
        /* JADX WARN: Removed duplicated region for block: B:257:0x050e  */
        /* JADX WARN: Removed duplicated region for block: B:274:0x055e  */
        /* JADX WARN: Removed duplicated region for block: B:4:0x0015  */
        @org.checkerframework.checker.nullness.qual.EnsuresNonNull({"this.output"})
        @org.checkerframework.checker.nullness.qual.RequiresNonNull({"codecId"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void initializeOutput(com.google.android.exoplayer2.extractor.ExtractorOutput r19, int r20) throws com.google.android.exoplayer2.ParserException {
            /*
                Method dump skipped, instructions count: 1646
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.Track.initializeOutput(com.google.android.exoplayer2.extractor.ExtractorOutput, int):void");
        }

        @RequiresNonNull({"output"})
        public void outputPendingSampleMetadata() {
            TrueHdSampleRechunker trueHdSampleRechunker = this.trueHdSampleRechunker;
            if (trueHdSampleRechunker != null) {
                trueHdSampleRechunker.outputPendingSampleMetadata(this);
            }
        }

        public void reset() {
            TrueHdSampleRechunker trueHdSampleRechunker = this.trueHdSampleRechunker;
            if (trueHdSampleRechunker != null) {
                trueHdSampleRechunker.reset();
            }
        }

        private byte[] getHdrStaticInfo() {
            if (this.primaryRChromaticityX == -1.0f || this.primaryRChromaticityY == -1.0f || this.primaryGChromaticityX == -1.0f || this.primaryGChromaticityY == -1.0f || this.primaryBChromaticityX == -1.0f || this.primaryBChromaticityY == -1.0f || this.whitePointChromaticityX == -1.0f || this.whitePointChromaticityY == -1.0f || this.maxMasteringLuminance == -1.0f || this.minMasteringLuminance == -1.0f) {
                return null;
            }
            byte[] bArr = new byte[25];
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            byteBufferOrder.put((byte) 0);
            byteBufferOrder.putShort((short) ((this.primaryRChromaticityX * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.primaryRChromaticityY * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.primaryGChromaticityX * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.primaryGChromaticityY * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.primaryBChromaticityX * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.primaryBChromaticityY * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.whitePointChromaticityX * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) ((this.whitePointChromaticityY * 50000.0f) + 0.5f));
            byteBufferOrder.putShort((short) (this.maxMasteringLuminance + 0.5f));
            byteBufferOrder.putShort((short) (this.minMasteringLuminance + 0.5f));
            byteBufferOrder.putShort((short) this.maxContentLuminance);
            byteBufferOrder.putShort((short) this.maxFrameAverageLuminance);
            return bArr;
        }

        private static Pair<String, List<byte[]>> parseFourCcPrivate(ParsableByteArray buffer) throws ParserException {
            try {
                buffer.skipBytes(16);
                long littleEndianUnsignedInt = buffer.readLittleEndianUnsignedInt();
                if (littleEndianUnsignedInt == 1482049860) {
                    return new Pair<>(MimeTypes.VIDEO_DIVX, null);
                }
                if (littleEndianUnsignedInt == 859189832) {
                    return new Pair<>(MimeTypes.VIDEO_H263, null);
                }
                if (littleEndianUnsignedInt == 826496599) {
                    byte[] data = buffer.getData();
                    for (int position = buffer.getPosition() + 20; position < data.length - 4; position++) {
                        if (data[position] == 0 && data[position + 1] == 0 && data[position + 2] == 1 && data[position + 3] == 15) {
                            return new Pair<>(MimeTypes.VIDEO_VC1, Collections.singletonList(Arrays.copyOfRange(data, position, data.length)));
                        }
                    }
                    throw ParserException.createForMalformedContainer("Failed to find FourCC VC1 initialization data", null);
                }
                Log.w(MatroskaExtractor.TAG, "Unknown FourCC. Setting mimeType to video/x-unknown");
                return new Pair<>(MimeTypes.VIDEO_UNKNOWN, null);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw ParserException.createForMalformedContainer("Error parsing FourCC private data", null);
            }
        }

        private static List<byte[]> parseVorbisCodecPrivate(byte[] codecPrivate) throws ParserException {
            int i;
            int i2;
            try {
                if (codecPrivate[0] != 2) {
                    throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                }
                int i3 = 0;
                int i4 = 1;
                while (true) {
                    i = codecPrivate[i4];
                    if ((i & 255) != 255) {
                        break;
                    }
                    i3 += 255;
                    i4++;
                }
                int i5 = i4 + 1;
                int i6 = i3 + (i & 255);
                int i7 = 0;
                while (true) {
                    i2 = codecPrivate[i5];
                    if ((i2 & 255) != 255) {
                        break;
                    }
                    i7 += 255;
                    i5++;
                }
                int i8 = i5 + 1;
                int i9 = i7 + (i2 & 255);
                if (codecPrivate[i8] != 1) {
                    throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                }
                byte[] bArr = new byte[i6];
                System.arraycopy(codecPrivate, i8, bArr, 0, i6);
                int i10 = i8 + i6;
                if (codecPrivate[i10] != 3) {
                    throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                }
                int i11 = i10 + i9;
                if (codecPrivate[i11] != 5) {
                    throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
                }
                byte[] bArr2 = new byte[codecPrivate.length - i11];
                System.arraycopy(codecPrivate, i11, bArr2, 0, codecPrivate.length - i11);
                ArrayList arrayList = new ArrayList(2);
                arrayList.add(bArr);
                arrayList.add(bArr2);
                return arrayList;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw ParserException.createForMalformedContainer("Error parsing vorbis codec private", null);
            }
        }

        private static boolean parseMsAcmCodecPrivate(ParsableByteArray buffer) throws ParserException {
            try {
                int littleEndianUnsignedShort = buffer.readLittleEndianUnsignedShort();
                if (littleEndianUnsignedShort == 1) {
                    return true;
                }
                if (littleEndianUnsignedShort != 65534) {
                    return false;
                }
                buffer.setPosition(24);
                if (buffer.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getMostSignificantBits()) {
                    if (buffer.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getLeastSignificantBits()) {
                        return true;
                    }
                }
                return false;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw ParserException.createForMalformedContainer("Error parsing MS/ACM codec private", null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @EnsuresNonNull({"output"})
        public void assertOutputInitialized() {
            Assertions.checkNotNull(this.output);
        }

        @EnsuresNonNull({"codecPrivate"})
        private byte[] getCodecPrivate(String codecId) throws ParserException {
            byte[] bArr = this.codecPrivate;
            if (bArr != null) {
                return bArr;
            }
            String strValueOf = String.valueOf(codecId);
            throw ParserException.createForMalformedContainer(strValueOf.length() != 0 ? "Missing CodecPrivate for codec ".concat(strValueOf) : new String("Missing CodecPrivate for codec "), null);
        }
    }
}
