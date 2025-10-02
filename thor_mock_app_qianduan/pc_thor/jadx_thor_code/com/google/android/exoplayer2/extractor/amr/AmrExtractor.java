package com.google.android.exoplayer2.extractor.amr;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
public final class AmrExtractor implements Extractor {
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private static final int MAX_FRAME_SIZE_BYTES;
    private static final int NUM_SAME_SIZE_CONSTANT_BIT_RATE_THRESHOLD = 20;
    private static final int SAMPLE_RATE_NB = 8000;
    private static final int SAMPLE_RATE_WB = 16000;
    private static final int SAMPLE_TIME_PER_FRAME_US = 20000;
    private static final int[] frameSizeBytesByTypeWb;
    private int currentSampleBytesRemaining;
    private int currentSampleSize;
    private long currentSampleTimeUs;
    private ExtractorOutput extractorOutput;
    private long firstSamplePosition;
    private int firstSampleSize;
    private final int flags;
    private boolean hasOutputFormat;
    private boolean hasOutputSeekMap;
    private boolean isWideBand;
    private int numSamplesWithSameSize;
    private final byte[] scratch;
    private SeekMap seekMap;
    private long timeOffsetUs;
    private TrackOutput trackOutput;
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.amr.AmrExtractor$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return AmrExtractor.lambda$static$0();
        }
    };
    private static final int[] frameSizeBytesByTypeNb = {13, 14, 16, 18, 20, 21, 27, 32, 6, 7, 6, 6, 1, 1, 1, 1};
    private static final byte[] amrSignatureNb = Util.getUtf8Bytes("#!AMR\n");
    private static final byte[] amrSignatureWb = Util.getUtf8Bytes("#!AMR-WB\n");

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    static {
        int[] iArr = {18, 24, 33, 37, 41, 47, 51, 59, 61, 6, 1, 1, 1, 1, 1, 1};
        frameSizeBytesByTypeWb = iArr;
        MAX_FRAME_SIZE_BYTES = iArr[8];
    }

    static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new AmrExtractor()};
    }

    public AmrExtractor() {
        this(0);
    }

    public AmrExtractor(int flags) {
        this.flags = flags;
        this.scratch = new byte[1];
        this.firstSampleSize = -1;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput input) throws IOException {
        return readAmrHeader(input);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
        this.trackOutput = output.track(0, 1);
        output.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException {
        assertInitialized();
        if (input.getPosition() == 0 && !readAmrHeader(input)) {
            throw ParserException.createForMalformedContainer("Could not find AMR header.", null);
        }
        maybeOutputFormat();
        int sample = readSample(input);
        maybeOutputSeekMap(input.getLength(), sample);
        return sample;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long position, long timeUs) {
        this.currentSampleTimeUs = 0L;
        this.currentSampleSize = 0;
        this.currentSampleBytesRemaining = 0;
        if (position != 0) {
            SeekMap seekMap = this.seekMap;
            if (seekMap instanceof ConstantBitrateSeekMap) {
                this.timeOffsetUs = ((ConstantBitrateSeekMap) seekMap).getTimeUsAtPosition(position);
                return;
            }
        }
        this.timeOffsetUs = 0L;
    }

    static int frameSizeBytesByTypeNb(int frameType) {
        return frameSizeBytesByTypeNb[frameType];
    }

    static int frameSizeBytesByTypeWb(int frameType) {
        return frameSizeBytesByTypeWb[frameType];
    }

    static byte[] amrSignatureNb() {
        byte[] bArr = amrSignatureNb;
        return Arrays.copyOf(bArr, bArr.length);
    }

    static byte[] amrSignatureWb() {
        byte[] bArr = amrSignatureWb;
        return Arrays.copyOf(bArr, bArr.length);
    }

    private boolean readAmrHeader(ExtractorInput input) throws IOException {
        byte[] bArr = amrSignatureNb;
        if (peekAmrSignature(input, bArr)) {
            this.isWideBand = false;
            input.skipFully(bArr.length);
            return true;
        }
        byte[] bArr2 = amrSignatureWb;
        if (!peekAmrSignature(input, bArr2)) {
            return false;
        }
        this.isWideBand = true;
        input.skipFully(bArr2.length);
        return true;
    }

    private static boolean peekAmrSignature(ExtractorInput input, byte[] amrSignature) throws IOException {
        input.resetPeekPosition();
        byte[] bArr = new byte[amrSignature.length];
        input.peekFully(bArr, 0, amrSignature.length);
        return Arrays.equals(bArr, amrSignature);
    }

    @RequiresNonNull({"trackOutput"})
    private void maybeOutputFormat() {
        if (this.hasOutputFormat) {
            return;
        }
        this.hasOutputFormat = true;
        boolean z = this.isWideBand;
        this.trackOutput.format(new Format.Builder().setSampleMimeType(z ? MimeTypes.AUDIO_AMR_WB : MimeTypes.AUDIO_AMR_NB).setMaxInputSize(MAX_FRAME_SIZE_BYTES).setChannelCount(1).setSampleRate(z ? 16000 : 8000).build());
    }

    @RequiresNonNull({"trackOutput"})
    private int readSample(ExtractorInput extractorInput) throws IOException {
        if (this.currentSampleBytesRemaining == 0) {
            try {
                int iPeekNextSampleSize = peekNextSampleSize(extractorInput);
                this.currentSampleSize = iPeekNextSampleSize;
                this.currentSampleBytesRemaining = iPeekNextSampleSize;
                if (this.firstSampleSize == -1) {
                    this.firstSamplePosition = extractorInput.getPosition();
                    this.firstSampleSize = this.currentSampleSize;
                }
                if (this.firstSampleSize == this.currentSampleSize) {
                    this.numSamplesWithSameSize++;
                }
            } catch (EOFException unused) {
                return -1;
            }
        }
        int iSampleData = this.trackOutput.sampleData((DataReader) extractorInput, this.currentSampleBytesRemaining, true);
        if (iSampleData == -1) {
            return -1;
        }
        int i = this.currentSampleBytesRemaining - iSampleData;
        this.currentSampleBytesRemaining = i;
        if (i > 0) {
            return 0;
        }
        this.trackOutput.sampleMetadata(this.timeOffsetUs + this.currentSampleTimeUs, 1, this.currentSampleSize, 0, null);
        this.currentSampleTimeUs += SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US;
        return 0;
    }

    private int peekNextSampleSize(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.scratch, 0, 1);
        byte b = this.scratch[0];
        if ((b & 131) > 0) {
            throw ParserException.createForMalformedContainer(new StringBuilder(42).append("Invalid padding bits for frame header ").append((int) b).toString(), null);
        }
        return getFrameSizeInBytes((b >> 3) & 15);
    }

    private int getFrameSizeInBytes(int frameType) throws ParserException {
        if (isValidFrameType(frameType)) {
            return this.isWideBand ? frameSizeBytesByTypeWb[frameType] : frameSizeBytesByTypeNb[frameType];
        }
        String str = this.isWideBand ? "WB" : "NB";
        throw ParserException.createForMalformedContainer(new StringBuilder(str.length() + 35).append("Illegal AMR ").append(str).append(" frame type ").append(frameType).toString(), null);
    }

    private boolean isValidFrameType(int frameType) {
        return frameType >= 0 && frameType <= 15 && (isWideBandValidFrameType(frameType) || isNarrowBandValidFrameType(frameType));
    }

    private boolean isWideBandValidFrameType(int frameType) {
        return this.isWideBand && (frameType < 10 || frameType > 13);
    }

    private boolean isNarrowBandValidFrameType(int frameType) {
        return !this.isWideBand && (frameType < 12 || frameType > 14);
    }

    @RequiresNonNull({"extractorOutput"})
    private void maybeOutputSeekMap(long inputLength, int sampleReadResult) {
        int i;
        if (this.hasOutputSeekMap) {
            return;
        }
        if ((this.flags & 1) == 0 || inputLength == -1 || ((i = this.firstSampleSize) != -1 && i != this.currentSampleSize)) {
            SeekMap.Unseekable unseekable = new SeekMap.Unseekable(C.TIME_UNSET);
            this.seekMap = unseekable;
            this.extractorOutput.seekMap(unseekable);
            this.hasOutputSeekMap = true;
            return;
        }
        if (this.numSamplesWithSameSize >= 20 || sampleReadResult == -1) {
            SeekMap constantBitrateSeekMap = getConstantBitrateSeekMap(inputLength);
            this.seekMap = constantBitrateSeekMap;
            this.extractorOutput.seekMap(constantBitrateSeekMap);
            this.hasOutputSeekMap = true;
        }
    }

    private SeekMap getConstantBitrateSeekMap(long inputLength) {
        return new ConstantBitrateSeekMap(inputLength, this.firstSamplePosition, getBitrateFromFrameSize(this.firstSampleSize, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US), this.firstSampleSize);
    }

    @EnsuresNonNull({"extractorOutput", "trackOutput"})
    private void assertInitialized() {
        Assertions.checkStateNotNull(this.trackOutput);
        Util.castNonNull(this.extractorOutput);
    }

    private static int getBitrateFromFrameSize(int frameSize, long durationUsPerFrame) {
        return (int) (((frameSize * 8) * 1000000) / durationUsPerFrame);
    }
}
