package com.google.android.exoplayer2.audio;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.PlaybackParams;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.AudioTrackPositionTracker;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.thor.basemodule.gui.view.CircleBarView;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class DefaultAudioSink implements AudioSink {
    private static final int AC3_BUFFER_MULTIPLICATION_FACTOR = 2;
    private static final int AUDIO_TRACK_RETRY_DURATION_MS = 100;
    private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
    public static final float DEFAULT_PLAYBACK_SPEED = 1.0f;
    private static final boolean DEFAULT_SKIP_SILENCE = false;
    private static final int ERROR_NATIVE_DEAD_OBJECT = -32;
    private static final long MAX_BUFFER_DURATION_US = 750000;
    public static final float MAX_PITCH = 8.0f;
    public static final float MAX_PLAYBACK_SPEED = 8.0f;
    private static final long MIN_BUFFER_DURATION_US = 250000;
    public static final float MIN_PITCH = 0.1f;
    public static final float MIN_PLAYBACK_SPEED = 0.1f;
    private static final long OFFLOAD_BUFFER_DURATION_US = 50000000;
    public static final int OFFLOAD_MODE_DISABLED = 0;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_DISABLED = 3;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_NOT_REQUIRED = 2;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_REQUIRED = 1;
    private static final int OUTPUT_MODE_OFFLOAD = 1;
    private static final int OUTPUT_MODE_PASSTHROUGH = 2;
    private static final int OUTPUT_MODE_PCM = 0;
    private static final long PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final String TAG = "DefaultAudioSink";
    public static boolean failOnSpuriousAudioTimestamp = false;
    private AudioProcessor[] activeAudioProcessors;
    private MediaPositionParameters afterDrainParameters;
    private AudioAttributes audioAttributes;
    private final AudioCapabilities audioCapabilities;
    private final AudioProcessorChain audioProcessorChain;
    private int audioSessionId;
    private AudioTrack audioTrack;
    private PlaybackParameters audioTrackPlaybackParameters;
    private final AudioTrackPositionTracker audioTrackPositionTracker;
    private AuxEffectInfo auxEffectInfo;
    private ByteBuffer avSyncHeader;
    private int bytesUntilNextAvSync;
    private final ChannelMappingAudioProcessor channelMappingAudioProcessor;
    private Configuration configuration;
    private int drainingAudioProcessorIndex;
    private final boolean enableAudioTrackPlaybackParams;
    private final boolean enableFloatOutput;
    private boolean externalAudioSessionIdProvided;
    private int framesPerEncodedSample;
    private boolean handledEndOfStream;
    private final PendingExceptionHolder<AudioSink.InitializationException> initializationExceptionPendingExceptionHolder;
    private ByteBuffer inputBuffer;
    private int inputBufferAccessUnitCount;
    private boolean isWaitingForOffloadEndOfStreamHandled;
    private long lastFeedElapsedRealtimeMs;
    private AudioSink.Listener listener;
    private MediaPositionParameters mediaPositionParameters;
    private final ArrayDeque<MediaPositionParameters> mediaPositionParametersCheckpoints;
    private boolean offloadDisabledUntilNextConfiguration;
    private final int offloadMode;
    private StreamEventCallbackV29 offloadStreamEventCallbackV29;
    private ByteBuffer outputBuffer;
    private ByteBuffer[] outputBuffers;
    private Configuration pendingConfiguration;
    private boolean playing;
    private byte[] preV21OutputBuffer;
    private int preV21OutputBufferOffset;
    private final ConditionVariable releasingConditionVariable;
    private long startMediaTimeUs;
    private boolean startMediaTimeUsNeedsInit;
    private boolean startMediaTimeUsNeedsSync;
    private boolean stoppedAudioTrack;
    private long submittedEncodedFrames;
    private long submittedPcmBytes;
    private final AudioProcessor[] toFloatPcmAvailableAudioProcessors;
    private final AudioProcessor[] toIntPcmAvailableAudioProcessors;
    private final TrimmingAudioProcessor trimmingAudioProcessor;
    private boolean tunneling;
    private float volume;
    private final PendingExceptionHolder<AudioSink.WriteException> writeExceptionPendingExceptionHolder;
    private long writtenEncodedFrames;
    private long writtenPcmBytes;

    public interface AudioProcessorChain {
        PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

        boolean applySkipSilenceEnabled(boolean skipSilenceEnabled);

        AudioProcessor[] getAudioProcessors();

        long getMediaDuration(long playoutDuration);

        long getSkippedOutputFrameCount();
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface OffloadMode {
    }

    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        private InvalidAudioTrackTimestampException(String message) {
            super(message);
        }
    }

    public static class DefaultAudioProcessorChain implements AudioProcessorChain {
        private final AudioProcessor[] audioProcessors;
        private final SilenceSkippingAudioProcessor silenceSkippingAudioProcessor;
        private final SonicAudioProcessor sonicAudioProcessor;

        public DefaultAudioProcessorChain(AudioProcessor... audioProcessors) {
            this(audioProcessors, new SilenceSkippingAudioProcessor(), new SonicAudioProcessor());
        }

        public DefaultAudioProcessorChain(AudioProcessor[] audioProcessors, SilenceSkippingAudioProcessor silenceSkippingAudioProcessor, SonicAudioProcessor sonicAudioProcessor) {
            AudioProcessor[] audioProcessorArr = new AudioProcessor[audioProcessors.length + 2];
            this.audioProcessors = audioProcessorArr;
            System.arraycopy(audioProcessors, 0, audioProcessorArr, 0, audioProcessors.length);
            this.silenceSkippingAudioProcessor = silenceSkippingAudioProcessor;
            this.sonicAudioProcessor = sonicAudioProcessor;
            audioProcessorArr[audioProcessors.length] = silenceSkippingAudioProcessor;
            audioProcessorArr[audioProcessors.length + 1] = sonicAudioProcessor;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public AudioProcessor[] getAudioProcessors() {
            return this.audioProcessors;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters) {
            this.sonicAudioProcessor.setSpeed(playbackParameters.speed);
            this.sonicAudioProcessor.setPitch(playbackParameters.pitch);
            return playbackParameters;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public boolean applySkipSilenceEnabled(boolean skipSilenceEnabled) {
            this.silenceSkippingAudioProcessor.setEnabled(skipSilenceEnabled);
            return skipSilenceEnabled;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public long getMediaDuration(long playoutDuration) {
            return this.sonicAudioProcessor.getMediaDuration(playoutDuration);
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public long getSkippedOutputFrameCount() {
            return this.silenceSkippingAudioProcessor.getSkippedFrames();
        }
    }

    public DefaultAudioSink(AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessors) {
        this(audioCapabilities, audioProcessors, false);
    }

    public DefaultAudioSink(AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessors, boolean enableFloatOutput) {
        this(audioCapabilities, new DefaultAudioProcessorChain(audioProcessors), enableFloatOutput, false, 0);
    }

    public DefaultAudioSink(AudioCapabilities audioCapabilities, AudioProcessorChain audioProcessorChain, boolean enableFloatOutput, boolean enableAudioTrackPlaybackParams, int offloadMode) {
        this.audioCapabilities = audioCapabilities;
        this.audioProcessorChain = (AudioProcessorChain) Assertions.checkNotNull(audioProcessorChain);
        this.enableFloatOutput = Util.SDK_INT >= 21 && enableFloatOutput;
        this.enableAudioTrackPlaybackParams = Util.SDK_INT >= 23 && enableAudioTrackPlaybackParams;
        this.offloadMode = Util.SDK_INT >= 29 ? offloadMode : 0;
        this.releasingConditionVariable = new ConditionVariable(true);
        this.audioTrackPositionTracker = new AudioTrackPositionTracker(new PositionTrackerListener());
        ChannelMappingAudioProcessor channelMappingAudioProcessor = new ChannelMappingAudioProcessor();
        this.channelMappingAudioProcessor = channelMappingAudioProcessor;
        TrimmingAudioProcessor trimmingAudioProcessor = new TrimmingAudioProcessor();
        this.trimmingAudioProcessor = trimmingAudioProcessor;
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new ResamplingAudioProcessor(), channelMappingAudioProcessor, trimmingAudioProcessor);
        Collections.addAll(arrayList, audioProcessorChain.getAudioProcessors());
        this.toIntPcmAvailableAudioProcessors = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[0]);
        this.toFloatPcmAvailableAudioProcessors = new AudioProcessor[]{new FloatResamplingAudioProcessor()};
        this.volume = 1.0f;
        this.audioAttributes = AudioAttributes.DEFAULT;
        this.audioSessionId = 0;
        this.auxEffectInfo = new AuxEffectInfo(0, 0.0f);
        this.mediaPositionParameters = new MediaPositionParameters(PlaybackParameters.DEFAULT, false, 0L, 0L);
        this.audioTrackPlaybackParameters = PlaybackParameters.DEFAULT;
        this.drainingAudioProcessorIndex = -1;
        this.activeAudioProcessors = new AudioProcessor[0];
        this.outputBuffers = new ByteBuffer[0];
        this.mediaPositionParametersCheckpoints = new ArrayDeque<>();
        this.initializationExceptionPendingExceptionHolder = new PendingExceptionHolder<>(100L);
        this.writeExceptionPendingExceptionHolder = new PendingExceptionHolder<>(100L);
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setListener(AudioSink.Listener listener) {
        this.listener = listener;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean supportsFormat(Format format) {
        return getFormatSupport(format) != 0;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public int getFormatSupport(Format format) {
        if (!MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            return ((this.offloadDisabledUntilNextConfiguration || !useOffloadedPlayback(format, this.audioAttributes)) && !isPassthroughPlaybackSupported(format, this.audioCapabilities)) ? 0 : 2;
        }
        if (Util.isEncodingLinearPcm(format.pcmEncoding)) {
            return (format.pcmEncoding == 2 || (this.enableFloatOutput && format.pcmEncoding == 4)) ? 2 : 1;
        }
        Log.w(TAG, new StringBuilder(33).append("Invalid PCM encoding: ").append(format.pcmEncoding).toString());
        return 0;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public long getCurrentPositionUs(boolean sourceEnded) {
        if (!isAudioTrackInitialized() || this.startMediaTimeUsNeedsInit) {
            return Long.MIN_VALUE;
        }
        return applySkipping(applyMediaPositionParameters(Math.min(this.audioTrackPositionTracker.getCurrentPositionUs(sourceEnded), this.configuration.framesToDurationUs(getWrittenFrames()))));
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void configure(Format inputFormat, int specifiedBufferSize, int[] outputChannels) throws AudioSink.ConfigurationException {
        int pcmFrameSize;
        AudioProcessor[] audioProcessorArr;
        int iIntValue;
        int i;
        int i2;
        int iIntValue2;
        int i3;
        AudioProcessor[] audioProcessorArr2;
        int[] iArr;
        if (MimeTypes.AUDIO_RAW.equals(inputFormat.sampleMimeType)) {
            Assertions.checkArgument(Util.isEncodingLinearPcm(inputFormat.pcmEncoding));
            int pcmFrameSize2 = Util.getPcmFrameSize(inputFormat.pcmEncoding, inputFormat.channelCount);
            if (shouldUseFloatOutput(inputFormat.pcmEncoding)) {
                audioProcessorArr2 = this.toFloatPcmAvailableAudioProcessors;
            } else {
                audioProcessorArr2 = this.toIntPcmAvailableAudioProcessors;
            }
            this.trimmingAudioProcessor.setTrimFrameCount(inputFormat.encoderDelay, inputFormat.encoderPadding);
            if (Util.SDK_INT < 21 && inputFormat.channelCount == 8 && outputChannels == null) {
                iArr = new int[6];
                for (int i4 = 0; i4 < 6; i4++) {
                    iArr[i4] = i4;
                }
            } else {
                iArr = outputChannels;
            }
            this.channelMappingAudioProcessor.setChannelMap(iArr);
            AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(inputFormat.sampleRate, inputFormat.channelCount, inputFormat.pcmEncoding);
            for (AudioProcessor audioProcessor : audioProcessorArr2) {
                try {
                    AudioProcessor.AudioFormat audioFormatConfigure = audioProcessor.configure(audioFormat);
                    if (audioProcessor.isActive()) {
                        audioFormat = audioFormatConfigure;
                    }
                } catch (AudioProcessor.UnhandledAudioFormatException e) {
                    throw new AudioSink.ConfigurationException(e, inputFormat);
                }
            }
            int i5 = audioFormat.encoding;
            i = audioFormat.sampleRate;
            iIntValue2 = Util.getAudioTrackChannelConfig(audioFormat.channelCount);
            audioProcessorArr = audioProcessorArr2;
            iIntValue = i5;
            i2 = pcmFrameSize2;
            pcmFrameSize = Util.getPcmFrameSize(i5, audioFormat.channelCount);
            i3 = 0;
        } else {
            AudioProcessor[] audioProcessorArr3 = new AudioProcessor[0];
            int i6 = inputFormat.sampleRate;
            pcmFrameSize = -1;
            if (useOffloadedPlayback(inputFormat, this.audioAttributes)) {
                audioProcessorArr = audioProcessorArr3;
                iIntValue = MimeTypes.getEncoding((String) Assertions.checkNotNull(inputFormat.sampleMimeType), inputFormat.codecs);
                i3 = 1;
                iIntValue2 = Util.getAudioTrackChannelConfig(inputFormat.channelCount);
                i = i6;
                i2 = -1;
            } else {
                Pair<Integer, Integer> encodingAndChannelConfigForPassthrough = getEncodingAndChannelConfigForPassthrough(inputFormat, this.audioCapabilities);
                if (encodingAndChannelConfigForPassthrough == null) {
                    String strValueOf = String.valueOf(inputFormat);
                    throw new AudioSink.ConfigurationException(new StringBuilder(String.valueOf(strValueOf).length() + 37).append("Unable to configure passthrough for: ").append(strValueOf).toString(), inputFormat);
                }
                audioProcessorArr = audioProcessorArr3;
                iIntValue = ((Integer) encodingAndChannelConfigForPassthrough.first).intValue();
                i = i6;
                i2 = -1;
                iIntValue2 = ((Integer) encodingAndChannelConfigForPassthrough.second).intValue();
                i3 = 2;
            }
        }
        if (iIntValue == 0) {
            String strValueOf2 = String.valueOf(inputFormat);
            throw new AudioSink.ConfigurationException(new StringBuilder(String.valueOf(strValueOf2).length() + 48).append("Invalid output encoding (mode=").append(i3).append(") for: ").append(strValueOf2).toString(), inputFormat);
        }
        if (iIntValue2 == 0) {
            String strValueOf3 = String.valueOf(inputFormat);
            throw new AudioSink.ConfigurationException(new StringBuilder(String.valueOf(strValueOf3).length() + 54).append("Invalid output channel config (mode=").append(i3).append(") for: ").append(strValueOf3).toString(), inputFormat);
        }
        this.offloadDisabledUntilNextConfiguration = false;
        Configuration configuration = new Configuration(inputFormat, i2, i3, pcmFrameSize, i, iIntValue2, iIntValue, specifiedBufferSize, this.enableAudioTrackPlaybackParams, audioProcessorArr);
        if (isAudioTrackInitialized()) {
            this.pendingConfiguration = configuration;
        } else {
            this.configuration = configuration;
        }
    }

    private void setupAudioProcessors() {
        AudioProcessor[] audioProcessorArr = this.configuration.availableAudioProcessors;
        ArrayList arrayList = new ArrayList();
        for (AudioProcessor audioProcessor : audioProcessorArr) {
            if (audioProcessor.isActive()) {
                arrayList.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int size = arrayList.size();
        this.activeAudioProcessors = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[size]);
        this.outputBuffers = new ByteBuffer[size];
        flushAudioProcessors();
    }

    private void flushAudioProcessors() {
        int i = 0;
        while (true) {
            AudioProcessor[] audioProcessorArr = this.activeAudioProcessors;
            if (i >= audioProcessorArr.length) {
                return;
            }
            AudioProcessor audioProcessor = audioProcessorArr[i];
            audioProcessor.flush();
            this.outputBuffers[i] = audioProcessor.getOutput();
            i++;
        }
    }

    private void initializeAudioTrack() throws AudioSink.InitializationException {
        this.releasingConditionVariable.block();
        AudioTrack audioTrackBuildAudioTrack = buildAudioTrack();
        this.audioTrack = audioTrackBuildAudioTrack;
        if (isOffloadedPlayback(audioTrackBuildAudioTrack)) {
            registerStreamEventCallbackV29(this.audioTrack);
            if (this.offloadMode != 3) {
                this.audioTrack.setOffloadDelayPadding(this.configuration.inputFormat.encoderDelay, this.configuration.inputFormat.encoderPadding);
            }
        }
        this.audioSessionId = this.audioTrack.getAudioSessionId();
        this.audioTrackPositionTracker.setAudioTrack(this.audioTrack, this.configuration.outputMode == 2, this.configuration.outputEncoding, this.configuration.outputPcmFrameSize, this.configuration.bufferSize);
        setVolumeInternal();
        if (this.auxEffectInfo.effectId != 0) {
            this.audioTrack.attachAuxEffect(this.auxEffectInfo.effectId);
            this.audioTrack.setAuxEffectSendLevel(this.auxEffectInfo.sendLevel);
        }
        this.startMediaTimeUsNeedsInit = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void play() throws IllegalStateException {
        this.playing = true;
        if (isAudioTrackInitialized()) {
            this.audioTrackPositionTracker.start();
            this.audioTrack.play();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void handleDiscontinuity() {
        this.startMediaTimeUsNeedsSync = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean handleBuffer(ByteBuffer buffer, long presentationTimeUs, int encodedAccessUnitCount) throws Exception {
        ByteBuffer byteBuffer = this.inputBuffer;
        Assertions.checkArgument(byteBuffer == null || buffer == byteBuffer);
        if (this.pendingConfiguration != null) {
            if (!drainToEndOfStream()) {
                return false;
            }
            if (!this.pendingConfiguration.canReuseAudioTrack(this.configuration)) {
                playPendingData();
                if (hasPendingData()) {
                    return false;
                }
                flush();
            } else {
                this.configuration = this.pendingConfiguration;
                this.pendingConfiguration = null;
                if (isOffloadedPlayback(this.audioTrack) && this.offloadMode != 3) {
                    this.audioTrack.setOffloadEndOfStream();
                    this.audioTrack.setOffloadDelayPadding(this.configuration.inputFormat.encoderDelay, this.configuration.inputFormat.encoderPadding);
                    this.isWaitingForOffloadEndOfStreamHandled = true;
                }
            }
            applyAudioProcessorPlaybackParametersAndSkipSilence(presentationTimeUs);
        }
        if (!isAudioTrackInitialized()) {
            try {
                initializeAudioTrack();
            } catch (AudioSink.InitializationException e) {
                if (e.isRecoverable) {
                    throw e;
                }
                this.initializationExceptionPendingExceptionHolder.throwExceptionIfDeadlineIsReached(e);
                return false;
            }
        }
        this.initializationExceptionPendingExceptionHolder.clear();
        if (this.startMediaTimeUsNeedsInit) {
            this.startMediaTimeUs = Math.max(0L, presentationTimeUs);
            this.startMediaTimeUsNeedsSync = false;
            this.startMediaTimeUsNeedsInit = false;
            if (this.enableAudioTrackPlaybackParams && Util.SDK_INT >= 23) {
                setAudioTrackPlaybackParametersV23(this.audioTrackPlaybackParameters);
            }
            applyAudioProcessorPlaybackParametersAndSkipSilence(presentationTimeUs);
            if (this.playing) {
                play();
            }
        }
        if (!this.audioTrackPositionTracker.mayHandleBuffer(getWrittenFrames())) {
            return false;
        }
        if (this.inputBuffer == null) {
            Assertions.checkArgument(buffer.order() == ByteOrder.LITTLE_ENDIAN);
            if (!buffer.hasRemaining()) {
                return true;
            }
            if (this.configuration.outputMode != 0 && this.framesPerEncodedSample == 0) {
                int framesPerEncodedSample = getFramesPerEncodedSample(this.configuration.outputEncoding, buffer);
                this.framesPerEncodedSample = framesPerEncodedSample;
                if (framesPerEncodedSample == 0) {
                    return true;
                }
            }
            if (this.afterDrainParameters != null) {
                if (!drainToEndOfStream()) {
                    return false;
                }
                applyAudioProcessorPlaybackParametersAndSkipSilence(presentationTimeUs);
                this.afterDrainParameters = null;
            }
            long jInputFramesToDurationUs = this.startMediaTimeUs + this.configuration.inputFramesToDurationUs(getSubmittedFrames() - this.trimmingAudioProcessor.getTrimmedFrameCount());
            if (!this.startMediaTimeUsNeedsSync && Math.abs(jInputFramesToDurationUs - presentationTimeUs) > 200000) {
                this.listener.onAudioSinkError(new AudioSink.UnexpectedDiscontinuityException(presentationTimeUs, jInputFramesToDurationUs));
                this.startMediaTimeUsNeedsSync = true;
            }
            if (this.startMediaTimeUsNeedsSync) {
                if (!drainToEndOfStream()) {
                    return false;
                }
                long j = presentationTimeUs - jInputFramesToDurationUs;
                this.startMediaTimeUs += j;
                this.startMediaTimeUsNeedsSync = false;
                applyAudioProcessorPlaybackParametersAndSkipSilence(presentationTimeUs);
                AudioSink.Listener listener = this.listener;
                if (listener != null && j != 0) {
                    listener.onPositionDiscontinuity();
                }
            }
            if (this.configuration.outputMode == 0) {
                this.submittedPcmBytes += buffer.remaining();
            } else {
                this.submittedEncodedFrames += this.framesPerEncodedSample * encodedAccessUnitCount;
            }
            this.inputBuffer = buffer;
            this.inputBufferAccessUnitCount = encodedAccessUnitCount;
        }
        processBuffers(presentationTimeUs);
        if (!this.inputBuffer.hasRemaining()) {
            this.inputBuffer = null;
            this.inputBufferAccessUnitCount = 0;
            return true;
        }
        if (!this.audioTrackPositionTracker.isStalled(getWrittenFrames())) {
            return false;
        }
        Log.w(TAG, "Resetting stalled audio track");
        flush();
        return true;
    }

    private AudioTrack buildAudioTrack() throws AudioSink.InitializationException {
        try {
            return ((Configuration) Assertions.checkNotNull(this.configuration)).buildAudioTrack(this.tunneling, this.audioAttributes, this.audioSessionId);
        } catch (AudioSink.InitializationException e) {
            maybeDisableOffload();
            AudioSink.Listener listener = this.listener;
            if (listener != null) {
                listener.onAudioSinkError(e);
            }
            throw e;
        }
    }

    private void registerStreamEventCallbackV29(AudioTrack audioTrack) {
        if (this.offloadStreamEventCallbackV29 == null) {
            this.offloadStreamEventCallbackV29 = new StreamEventCallbackV29();
        }
        this.offloadStreamEventCallbackV29.register(audioTrack);
    }

    private void processBuffers(long avSyncPresentationTimeUs) throws Exception {
        ByteBuffer byteBuffer;
        int length = this.activeAudioProcessors.length;
        int i = length;
        while (i >= 0) {
            if (i > 0) {
                byteBuffer = this.outputBuffers[i - 1];
            } else {
                byteBuffer = this.inputBuffer;
                if (byteBuffer == null) {
                    byteBuffer = AudioProcessor.EMPTY_BUFFER;
                }
            }
            if (i == length) {
                writeBuffer(byteBuffer, avSyncPresentationTimeUs);
            } else {
                AudioProcessor audioProcessor = this.activeAudioProcessors[i];
                if (i > this.drainingAudioProcessorIndex) {
                    audioProcessor.queueInput(byteBuffer);
                }
                ByteBuffer output = audioProcessor.getOutput();
                this.outputBuffers[i] = output;
                if (output.hasRemaining()) {
                    i++;
                }
            }
            if (byteBuffer.hasRemaining()) {
                return;
            } else {
                i--;
            }
        }
    }

    private void writeBuffer(ByteBuffer buffer, long avSyncPresentationTimeUs) throws Exception {
        int iWriteNonBlockingV21;
        if (buffer.hasRemaining()) {
            ByteBuffer byteBuffer = this.outputBuffer;
            if (byteBuffer != null) {
                Assertions.checkArgument(byteBuffer == buffer);
            } else {
                this.outputBuffer = buffer;
                if (Util.SDK_INT < 21) {
                    int iRemaining = buffer.remaining();
                    byte[] bArr = this.preV21OutputBuffer;
                    if (bArr == null || bArr.length < iRemaining) {
                        this.preV21OutputBuffer = new byte[iRemaining];
                    }
                    int iPosition = buffer.position();
                    buffer.get(this.preV21OutputBuffer, 0, iRemaining);
                    buffer.position(iPosition);
                    this.preV21OutputBufferOffset = 0;
                }
            }
            int iRemaining2 = buffer.remaining();
            if (Util.SDK_INT < 21) {
                int availableBufferSize = this.audioTrackPositionTracker.getAvailableBufferSize(this.writtenPcmBytes);
                if (availableBufferSize > 0) {
                    iWriteNonBlockingV21 = this.audioTrack.write(this.preV21OutputBuffer, this.preV21OutputBufferOffset, Math.min(iRemaining2, availableBufferSize));
                    if (iWriteNonBlockingV21 > 0) {
                        this.preV21OutputBufferOffset += iWriteNonBlockingV21;
                        buffer.position(buffer.position() + iWriteNonBlockingV21);
                    }
                } else {
                    iWriteNonBlockingV21 = 0;
                }
            } else if (this.tunneling) {
                Assertions.checkState(avSyncPresentationTimeUs != C.TIME_UNSET);
                iWriteNonBlockingV21 = writeNonBlockingWithAvSyncV21(this.audioTrack, buffer, iRemaining2, avSyncPresentationTimeUs);
            } else {
                iWriteNonBlockingV21 = writeNonBlockingV21(this.audioTrack, buffer, iRemaining2);
            }
            this.lastFeedElapsedRealtimeMs = SystemClock.elapsedRealtime();
            if (iWriteNonBlockingV21 < 0) {
                boolean zIsAudioTrackDeadObject = isAudioTrackDeadObject(iWriteNonBlockingV21);
                if (zIsAudioTrackDeadObject) {
                    maybeDisableOffload();
                }
                AudioSink.WriteException writeException = new AudioSink.WriteException(iWriteNonBlockingV21, this.configuration.inputFormat, zIsAudioTrackDeadObject);
                AudioSink.Listener listener = this.listener;
                if (listener != null) {
                    listener.onAudioSinkError(writeException);
                }
                if (writeException.isRecoverable) {
                    throw writeException;
                }
                this.writeExceptionPendingExceptionHolder.throwExceptionIfDeadlineIsReached(writeException);
                return;
            }
            this.writeExceptionPendingExceptionHolder.clear();
            if (isOffloadedPlayback(this.audioTrack)) {
                long j = this.writtenEncodedFrames;
                if (j > 0) {
                    this.isWaitingForOffloadEndOfStreamHandled = false;
                }
                if (this.playing && this.listener != null && iWriteNonBlockingV21 < iRemaining2 && !this.isWaitingForOffloadEndOfStreamHandled) {
                    this.listener.onOffloadBufferFull(this.audioTrackPositionTracker.getPendingBufferDurationMs(j));
                }
            }
            if (this.configuration.outputMode == 0) {
                this.writtenPcmBytes += iWriteNonBlockingV21;
            }
            if (iWriteNonBlockingV21 == iRemaining2) {
                if (this.configuration.outputMode != 0) {
                    Assertions.checkState(buffer == this.inputBuffer);
                    this.writtenEncodedFrames += this.framesPerEncodedSample * this.inputBufferAccessUnitCount;
                }
                this.outputBuffer = null;
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void playToEndOfStream() throws IllegalStateException, AudioSink.WriteException {
        if (!this.handledEndOfStream && isAudioTrackInitialized() && drainToEndOfStream()) {
            playPendingData();
            this.handledEndOfStream = true;
        }
    }

    private void maybeDisableOffload() {
        if (this.configuration.outputModeIsOffload()) {
            this.offloadDisabledUntilNextConfiguration = true;
        }
    }

    private static boolean isAudioTrackDeadObject(int status) {
        return (Util.SDK_INT >= 24 && status == -6) || status == ERROR_NATIVE_DEAD_OBJECT;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0029 -> B:5:0x0009). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean drainToEndOfStream() throws java.lang.Exception {
        /*
            r9 = this;
            int r0 = r9.drainingAudioProcessorIndex
            r1 = 1
            r2 = 0
            r3 = -1
            if (r0 != r3) goto Lb
            r9.drainingAudioProcessorIndex = r2
        L9:
            r0 = r1
            goto Lc
        Lb:
            r0 = r2
        Lc:
            int r4 = r9.drainingAudioProcessorIndex
            com.google.android.exoplayer2.audio.AudioProcessor[] r5 = r9.activeAudioProcessors
            int r6 = r5.length
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r4 >= r6) goto L2f
            r4 = r5[r4]
            if (r0 == 0) goto L1f
            r4.queueEndOfStream()
        L1f:
            r9.processBuffers(r7)
            boolean r0 = r4.isEnded()
            if (r0 != 0) goto L29
            return r2
        L29:
            int r0 = r9.drainingAudioProcessorIndex
            int r0 = r0 + r1
            r9.drainingAudioProcessorIndex = r0
            goto L9
        L2f:
            java.nio.ByteBuffer r0 = r9.outputBuffer
            if (r0 == 0) goto L3b
            r9.writeBuffer(r0, r7)
            java.nio.ByteBuffer r0 = r9.outputBuffer
            if (r0 == 0) goto L3b
            return r2
        L3b:
            r9.drainingAudioProcessorIndex = r3
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.drainToEndOfStream():boolean");
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean isEnded() {
        return !isAudioTrackInitialized() || (this.handledEndOfStream && !hasPendingData());
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean hasPendingData() {
        return isAudioTrackInitialized() && this.audioTrackPositionTracker.hasPendingData(getWrittenFrames());
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        PlaybackParameters playbackParameters2 = new PlaybackParameters(Util.constrainValue(playbackParameters.speed, 0.1f, 8.0f), Util.constrainValue(playbackParameters.pitch, 0.1f, 8.0f));
        if (this.enableAudioTrackPlaybackParams && Util.SDK_INT >= 23) {
            setAudioTrackPlaybackParametersV23(playbackParameters2);
        } else {
            setAudioProcessorPlaybackParametersAndSkipSilence(playbackParameters2, getSkipSilenceEnabled());
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public PlaybackParameters getPlaybackParameters() {
        if (this.enableAudioTrackPlaybackParams) {
            return this.audioTrackPlaybackParameters;
        }
        return getAudioProcessorPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setSkipSilenceEnabled(boolean skipSilenceEnabled) {
        setAudioProcessorPlaybackParametersAndSkipSilence(getAudioProcessorPlaybackParameters(), skipSilenceEnabled);
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean getSkipSilenceEnabled() {
        return getMediaPositionParameters().skipSilence;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalStateException {
        if (this.audioAttributes.equals(audioAttributes)) {
            return;
        }
        this.audioAttributes = audioAttributes;
        if (this.tunneling) {
            return;
        }
        flush();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAudioSessionId(int audioSessionId) throws IllegalStateException {
        if (this.audioSessionId != audioSessionId) {
            this.audioSessionId = audioSessionId;
            this.externalAudioSessionIdProvided = audioSessionId != 0;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        if (this.auxEffectInfo.equals(auxEffectInfo)) {
            return;
        }
        int i = auxEffectInfo.effectId;
        float f = auxEffectInfo.sendLevel;
        if (this.audioTrack != null) {
            if (this.auxEffectInfo.effectId != i) {
                this.audioTrack.attachAuxEffect(i);
            }
            if (i != 0) {
                this.audioTrack.setAuxEffectSendLevel(f);
            }
        }
        this.auxEffectInfo = auxEffectInfo;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void enableTunnelingV21() throws IllegalStateException {
        Assertions.checkState(Util.SDK_INT >= 21);
        Assertions.checkState(this.externalAudioSessionIdProvided);
        if (this.tunneling) {
            return;
        }
        this.tunneling = true;
        flush();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void disableTunneling() throws IllegalStateException {
        if (this.tunneling) {
            this.tunneling = false;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setVolume(float volume) {
        if (this.volume != volume) {
            this.volume = volume;
            setVolumeInternal();
        }
    }

    private void setVolumeInternal() {
        if (isAudioTrackInitialized()) {
            if (Util.SDK_INT >= 21) {
                setVolumeInternalV21(this.audioTrack, this.volume);
            } else {
                setVolumeInternalV3(this.audioTrack, this.volume);
            }
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void pause() throws IllegalStateException {
        this.playing = false;
        if (isAudioTrackInitialized() && this.audioTrackPositionTracker.pause()) {
            this.audioTrack.pause();
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.exoplayer2.audio.DefaultAudioSink$1] */
    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void flush() throws IllegalStateException {
        if (isAudioTrackInitialized()) {
            resetSinkStateForFlush();
            if (this.audioTrackPositionTracker.isPlaying()) {
                this.audioTrack.pause();
            }
            if (isOffloadedPlayback(this.audioTrack)) {
                ((StreamEventCallbackV29) Assertions.checkNotNull(this.offloadStreamEventCallbackV29)).unregister(this.audioTrack);
            }
            final AudioTrack audioTrack = this.audioTrack;
            this.audioTrack = null;
            if (Util.SDK_INT < 21 && !this.externalAudioSessionIdProvided) {
                this.audioSessionId = 0;
            }
            Configuration configuration = this.pendingConfiguration;
            if (configuration != null) {
                this.configuration = configuration;
                this.pendingConfiguration = null;
            }
            this.audioTrackPositionTracker.reset();
            this.releasingConditionVariable.close();
            new Thread("ExoPlayer:AudioTrackReleaseThread") { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        audioTrack.flush();
                        audioTrack.release();
                    } finally {
                        DefaultAudioSink.this.releasingConditionVariable.open();
                    }
                }
            }.start();
        }
        this.writeExceptionPendingExceptionHolder.clear();
        this.initializationExceptionPendingExceptionHolder.clear();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void experimentalFlushWithoutAudioTrackRelease() throws IllegalStateException {
        if (Util.SDK_INT < 25) {
            flush();
            return;
        }
        this.writeExceptionPendingExceptionHolder.clear();
        this.initializationExceptionPendingExceptionHolder.clear();
        if (isAudioTrackInitialized()) {
            resetSinkStateForFlush();
            if (this.audioTrackPositionTracker.isPlaying()) {
                this.audioTrack.pause();
            }
            this.audioTrack.flush();
            this.audioTrackPositionTracker.reset();
            this.audioTrackPositionTracker.setAudioTrack(this.audioTrack, this.configuration.outputMode == 2, this.configuration.outputEncoding, this.configuration.outputPcmFrameSize, this.configuration.bufferSize);
            this.startMediaTimeUsNeedsInit = true;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void reset() throws IllegalStateException {
        flush();
        for (AudioProcessor audioProcessor : this.toIntPcmAvailableAudioProcessors) {
            audioProcessor.reset();
        }
        for (AudioProcessor audioProcessor2 : this.toFloatPcmAvailableAudioProcessors) {
            audioProcessor2.reset();
        }
        this.playing = false;
        this.offloadDisabledUntilNextConfiguration = false;
    }

    private void resetSinkStateForFlush() {
        this.submittedPcmBytes = 0L;
        this.submittedEncodedFrames = 0L;
        this.writtenPcmBytes = 0L;
        this.writtenEncodedFrames = 0L;
        this.isWaitingForOffloadEndOfStreamHandled = false;
        this.framesPerEncodedSample = 0;
        this.mediaPositionParameters = new MediaPositionParameters(getAudioProcessorPlaybackParameters(), getSkipSilenceEnabled(), 0L, 0L);
        this.startMediaTimeUs = 0L;
        this.afterDrainParameters = null;
        this.mediaPositionParametersCheckpoints.clear();
        this.inputBuffer = null;
        this.inputBufferAccessUnitCount = 0;
        this.outputBuffer = null;
        this.stoppedAudioTrack = false;
        this.handledEndOfStream = false;
        this.drainingAudioProcessorIndex = -1;
        this.avSyncHeader = null;
        this.bytesUntilNextAvSync = 0;
        this.trimmingAudioProcessor.resetTrimmedFrameCount();
        flushAudioProcessors();
    }

    private void setAudioTrackPlaybackParametersV23(PlaybackParameters audioTrackPlaybackParameters) {
        if (isAudioTrackInitialized()) {
            try {
                this.audioTrack.setPlaybackParams(new PlaybackParams().allowDefaults().setSpeed(audioTrackPlaybackParameters.speed).setPitch(audioTrackPlaybackParameters.pitch).setAudioFallbackMode(2));
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "Failed to set playback params", e);
            }
            audioTrackPlaybackParameters = new PlaybackParameters(this.audioTrack.getPlaybackParams().getSpeed(), this.audioTrack.getPlaybackParams().getPitch());
            this.audioTrackPositionTracker.setAudioTrackPlaybackSpeed(audioTrackPlaybackParameters.speed);
        }
        this.audioTrackPlaybackParameters = audioTrackPlaybackParameters;
    }

    private void setAudioProcessorPlaybackParametersAndSkipSilence(PlaybackParameters playbackParameters, boolean skipSilence) {
        MediaPositionParameters mediaPositionParameters = getMediaPositionParameters();
        if (playbackParameters.equals(mediaPositionParameters.playbackParameters) && skipSilence == mediaPositionParameters.skipSilence) {
            return;
        }
        MediaPositionParameters mediaPositionParameters2 = new MediaPositionParameters(playbackParameters, skipSilence, C.TIME_UNSET, C.TIME_UNSET);
        if (isAudioTrackInitialized()) {
            this.afterDrainParameters = mediaPositionParameters2;
        } else {
            this.mediaPositionParameters = mediaPositionParameters2;
        }
    }

    private PlaybackParameters getAudioProcessorPlaybackParameters() {
        return getMediaPositionParameters().playbackParameters;
    }

    private MediaPositionParameters getMediaPositionParameters() {
        MediaPositionParameters mediaPositionParameters = this.afterDrainParameters;
        if (mediaPositionParameters != null) {
            return mediaPositionParameters;
        }
        if (!this.mediaPositionParametersCheckpoints.isEmpty()) {
            return this.mediaPositionParametersCheckpoints.getLast();
        }
        return this.mediaPositionParameters;
    }

    private void applyAudioProcessorPlaybackParametersAndSkipSilence(long presentationTimeUs) {
        PlaybackParameters playbackParametersApplyPlaybackParameters;
        if (shouldApplyAudioProcessorPlaybackParameters()) {
            playbackParametersApplyPlaybackParameters = this.audioProcessorChain.applyPlaybackParameters(getAudioProcessorPlaybackParameters());
        } else {
            playbackParametersApplyPlaybackParameters = PlaybackParameters.DEFAULT;
        }
        PlaybackParameters playbackParameters = playbackParametersApplyPlaybackParameters;
        boolean zApplySkipSilenceEnabled = shouldApplyAudioProcessorPlaybackParameters() ? this.audioProcessorChain.applySkipSilenceEnabled(getSkipSilenceEnabled()) : false;
        this.mediaPositionParametersCheckpoints.add(new MediaPositionParameters(playbackParameters, zApplySkipSilenceEnabled, Math.max(0L, presentationTimeUs), this.configuration.framesToDurationUs(getWrittenFrames())));
        setupAudioProcessors();
        AudioSink.Listener listener = this.listener;
        if (listener != null) {
            listener.onSkipSilenceEnabledChanged(zApplySkipSilenceEnabled);
        }
    }

    private boolean shouldApplyAudioProcessorPlaybackParameters() {
        return (this.tunneling || !MimeTypes.AUDIO_RAW.equals(this.configuration.inputFormat.sampleMimeType) || shouldUseFloatOutput(this.configuration.inputFormat.pcmEncoding)) ? false : true;
    }

    private boolean shouldUseFloatOutput(int pcmEncoding) {
        return this.enableFloatOutput && Util.isEncodingHighResolutionPcm(pcmEncoding);
    }

    private long applyMediaPositionParameters(long positionUs) {
        while (!this.mediaPositionParametersCheckpoints.isEmpty() && positionUs >= this.mediaPositionParametersCheckpoints.getFirst().audioTrackPositionUs) {
            this.mediaPositionParameters = this.mediaPositionParametersCheckpoints.remove();
        }
        long j = positionUs - this.mediaPositionParameters.audioTrackPositionUs;
        if (this.mediaPositionParameters.playbackParameters.equals(PlaybackParameters.DEFAULT)) {
            return this.mediaPositionParameters.mediaTimeUs + j;
        }
        if (this.mediaPositionParametersCheckpoints.isEmpty()) {
            return this.mediaPositionParameters.mediaTimeUs + this.audioProcessorChain.getMediaDuration(j);
        }
        MediaPositionParameters first = this.mediaPositionParametersCheckpoints.getFirst();
        return first.mediaTimeUs - Util.getMediaDurationForPlayoutDuration(first.audioTrackPositionUs - positionUs, this.mediaPositionParameters.playbackParameters.speed);
    }

    private long applySkipping(long positionUs) {
        return positionUs + this.configuration.framesToDurationUs(this.audioProcessorChain.getSkippedOutputFrameCount());
    }

    private boolean isAudioTrackInitialized() {
        return this.audioTrack != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getSubmittedFrames() {
        if (this.configuration.outputMode == 0) {
            return this.submittedPcmBytes / this.configuration.inputPcmFrameSize;
        }
        return this.submittedEncodedFrames;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getWrittenFrames() {
        if (this.configuration.outputMode == 0) {
            return this.writtenPcmBytes / this.configuration.outputPcmFrameSize;
        }
        return this.writtenEncodedFrames;
    }

    private static boolean isPassthroughPlaybackSupported(Format format, AudioCapabilities audioCapabilities) {
        return getEncodingAndChannelConfigForPassthrough(format, audioCapabilities) != null;
    }

    private static Pair<Integer, Integer> getEncodingAndChannelConfigForPassthrough(Format format, AudioCapabilities audioCapabilities) {
        if (audioCapabilities == null) {
            return null;
        }
        int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
        int maxSupportedChannelCountForPassthroughV29 = 6;
        if (!(encoding == 5 || encoding == 6 || encoding == 18 || encoding == 17 || encoding == 7 || encoding == 8 || encoding == 14)) {
            return null;
        }
        if (encoding == 18 && !audioCapabilities.supportsEncoding(18)) {
            encoding = 6;
        } else if (encoding == 8 && !audioCapabilities.supportsEncoding(8)) {
            encoding = 7;
        }
        if (!audioCapabilities.supportsEncoding(encoding)) {
            return null;
        }
        if (encoding == 18) {
            if (Util.SDK_INT >= 29 && (maxSupportedChannelCountForPassthroughV29 = getMaxSupportedChannelCountForPassthroughV29(18, format.sampleRate)) == 0) {
                Log.w(TAG, "E-AC3 JOC encoding supported but no channel count supported");
                return null;
            }
        } else {
            maxSupportedChannelCountForPassthroughV29 = format.channelCount;
            if (maxSupportedChannelCountForPassthroughV29 > audioCapabilities.getMaxChannelCount()) {
                return null;
            }
        }
        int channelConfigForPassthrough = getChannelConfigForPassthrough(maxSupportedChannelCountForPassthroughV29);
        if (channelConfigForPassthrough == 0) {
            return null;
        }
        return Pair.create(Integer.valueOf(encoding), Integer.valueOf(channelConfigForPassthrough));
    }

    private static int getMaxSupportedChannelCountForPassthroughV29(int encoding, int sampleRate) {
        android.media.AudioAttributes audioAttributesBuild = new AudioAttributes.Builder().setUsage(1).setContentType(3).build();
        for (int i = 8; i > 0; i--) {
            if (AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setEncoding(encoding).setSampleRate(sampleRate).setChannelMask(Util.getAudioTrackChannelConfig(i)).build(), audioAttributesBuild)) {
                return i;
            }
        }
        return 0;
    }

    private static int getChannelConfigForPassthrough(int channelCount) {
        if (Util.SDK_INT <= 28) {
            if (channelCount == 7) {
                channelCount = 8;
            } else if (channelCount == 3 || channelCount == 4 || channelCount == 5) {
                channelCount = 6;
            }
        }
        if (Util.SDK_INT <= 26 && "fugu".equals(Util.DEVICE) && channelCount == 1) {
            channelCount = 2;
        }
        return Util.getAudioTrackChannelConfig(channelCount);
    }

    private boolean useOffloadedPlayback(Format format, AudioAttributes audioAttributes) {
        int encoding;
        int audioTrackChannelConfig;
        if (Util.SDK_INT < 29 || this.offloadMode == 0 || (encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs)) == 0 || (audioTrackChannelConfig = Util.getAudioTrackChannelConfig(format.channelCount)) == 0 || !AudioManager.isOffloadedPlaybackSupported(getAudioFormat(format.sampleRate, audioTrackChannelConfig, encoding), audioAttributes.getAudioAttributesV21())) {
            return false;
        }
        return ((format.encoderDelay != 0 || format.encoderPadding != 0) && (this.offloadMode == 1) && !isOffloadedGaplessPlaybackSupported()) ? false : true;
    }

    private static boolean isOffloadedPlayback(AudioTrack audioTrack) {
        return Util.SDK_INT >= 29 && audioTrack.isOffloadedPlayback();
    }

    private static boolean isOffloadedGaplessPlaybackSupported() {
        return Util.SDK_INT >= 30 && Util.MODEL.startsWith("Pixel");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getMaximumEncodedRateBytesPerSecond(int encoding) {
        switch (encoding) {
            case 5:
                return Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND;
            case 6:
            case 18:
                return Ac3Util.E_AC3_MAX_RATE_BYTES_PER_SECOND;
            case 7:
                return DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND;
            case 8:
                return DtsUtil.DTS_HD_MAX_RATE_BYTES_PER_SECOND;
            case 9:
                return MpegAudioUtil.MAX_RATE_BYTES_PER_SECOND;
            case 10:
                return AacUtil.AAC_LC_MAX_RATE_BYTES_PER_SECOND;
            case 11:
                return AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND;
            case 12:
                return AacUtil.AAC_HE_V2_MAX_RATE_BYTES_PER_SECOND;
            case 13:
            default:
                throw new IllegalArgumentException();
            case 14:
                return Ac3Util.TRUEHD_MAX_RATE_BYTES_PER_SECOND;
            case 15:
                return 8000;
            case 16:
                return AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
            case 17:
                return Ac4Util.MAX_RATE_BYTES_PER_SECOND;
        }
    }

    private static int getFramesPerEncodedSample(int encoding, ByteBuffer buffer) {
        switch (encoding) {
            case 5:
            case 6:
            case 18:
                return Ac3Util.parseAc3SyncframeAudioSampleCount(buffer);
            case 7:
            case 8:
                return DtsUtil.parseDtsAudioSampleCount(buffer);
            case 9:
                int mpegAudioFrameSampleCount = MpegAudioUtil.parseMpegAudioFrameSampleCount(Util.getBigEndianInt(buffer, buffer.position()));
                if (mpegAudioFrameSampleCount != -1) {
                    return mpegAudioFrameSampleCount;
                }
                throw new IllegalArgumentException();
            case 10:
                return 1024;
            case 11:
            case 12:
                return 2048;
            case 13:
            default:
                throw new IllegalStateException(new StringBuilder(38).append("Unexpected audio encoding: ").append(encoding).toString());
            case 14:
                int iFindTrueHdSyncframeOffset = Ac3Util.findTrueHdSyncframeOffset(buffer);
                if (iFindTrueHdSyncframeOffset == -1) {
                    return 0;
                }
                return Ac3Util.parseTrueHdSyncframeAudioSampleCount(buffer, iFindTrueHdSyncframeOffset) * 16;
            case 15:
                return 512;
            case 16:
                return 1024;
            case 17:
                return Ac4Util.parseAc4SyncframeAudioSampleCount(buffer);
        }
    }

    private static int writeNonBlockingV21(AudioTrack audioTrack, ByteBuffer buffer, int size) {
        return audioTrack.write(buffer, size, 1);
    }

    private int writeNonBlockingWithAvSyncV21(AudioTrack audioTrack, ByteBuffer buffer, int size, long presentationTimeUs) {
        if (Util.SDK_INT >= 26) {
            return audioTrack.write(buffer, size, 1, presentationTimeUs * 1000);
        }
        if (this.avSyncHeader == null) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
            this.avSyncHeader = byteBufferAllocate;
            byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
            this.avSyncHeader.putInt(1431633921);
        }
        if (this.bytesUntilNextAvSync == 0) {
            this.avSyncHeader.putInt(4, size);
            this.avSyncHeader.putLong(8, presentationTimeUs * 1000);
            this.avSyncHeader.position(0);
            this.bytesUntilNextAvSync = size;
        }
        int iRemaining = this.avSyncHeader.remaining();
        if (iRemaining > 0) {
            int iWrite = audioTrack.write(this.avSyncHeader, iRemaining, 1);
            if (iWrite < 0) {
                this.bytesUntilNextAvSync = 0;
                return iWrite;
            }
            if (iWrite < iRemaining) {
                return 0;
            }
        }
        int iWriteNonBlockingV21 = writeNonBlockingV21(audioTrack, buffer, size);
        if (iWriteNonBlockingV21 < 0) {
            this.bytesUntilNextAvSync = 0;
            return iWriteNonBlockingV21;
        }
        this.bytesUntilNextAvSync -= iWriteNonBlockingV21;
        return iWriteNonBlockingV21;
    }

    private static void setVolumeInternalV21(AudioTrack audioTrack, float volume) {
        audioTrack.setVolume(volume);
    }

    private static void setVolumeInternalV3(AudioTrack audioTrack, float volume) {
        audioTrack.setStereoVolume(volume, volume);
    }

    private void playPendingData() throws IllegalStateException {
        if (this.stoppedAudioTrack) {
            return;
        }
        this.stoppedAudioTrack = true;
        this.audioTrackPositionTracker.handleEndOfStream(getWrittenFrames());
        this.audioTrack.stop();
        this.bytesUntilNextAvSync = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StreamEventCallbackV29 {
        private final AudioTrack.StreamEventCallback callback;
        private final Handler handler = new Handler();

        public StreamEventCallbackV29() {
            this.callback = new AudioTrack.StreamEventCallback() { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink.StreamEventCallbackV29.1
                @Override // android.media.AudioTrack.StreamEventCallback
                public void onDataRequest(AudioTrack track, int size) {
                    Assertions.checkState(track == DefaultAudioSink.this.audioTrack);
                    if (DefaultAudioSink.this.listener == null || !DefaultAudioSink.this.playing) {
                        return;
                    }
                    DefaultAudioSink.this.listener.onOffloadBufferEmptying();
                }

                @Override // android.media.AudioTrack.StreamEventCallback
                public void onTearDown(AudioTrack track) {
                    Assertions.checkState(track == DefaultAudioSink.this.audioTrack);
                    if (DefaultAudioSink.this.listener == null || !DefaultAudioSink.this.playing) {
                        return;
                    }
                    DefaultAudioSink.this.listener.onOffloadBufferEmptying();
                }
            };
        }

        public void register(AudioTrack audioTrack) {
            final Handler handler = this.handler;
            Objects.requireNonNull(handler);
            audioTrack.registerStreamEventCallback(new Executor() { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink$StreamEventCallbackV29$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    handler.post(runnable);
                }
            }, this.callback);
        }

        public void unregister(AudioTrack audioTrack) {
            audioTrack.unregisterStreamEventCallback(this.callback);
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    private static final class MediaPositionParameters {
        public final long audioTrackPositionUs;
        public final long mediaTimeUs;
        public final PlaybackParameters playbackParameters;
        public final boolean skipSilence;

        private MediaPositionParameters(PlaybackParameters playbackParameters, boolean skipSilence, long mediaTimeUs, long audioTrackPositionUs) {
            this.playbackParameters = playbackParameters;
            this.skipSilence = skipSilence;
            this.mediaTimeUs = mediaTimeUs;
            this.audioTrackPositionUs = audioTrackPositionUs;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AudioFormat getAudioFormat(int sampleRate, int channelConfig, int encoding) {
        return new AudioFormat.Builder().setSampleRate(sampleRate).setChannelMask(channelConfig).setEncoding(encoding).build();
    }

    private final class PositionTrackerListener implements AudioTrackPositionTracker.Listener {
        private PositionTrackerListener() {
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onPositionFramesMismatch(long audioTimestampPositionFrames, long audioTimestampSystemTimeUs, long systemTimeUs, long playbackPositionUs) {
            long submittedFrames = DefaultAudioSink.this.getSubmittedFrames();
            String string = new StringBuilder(182).append("Spurious audio timestamp (frame position mismatch): ").append(audioTimestampPositionFrames).append(", ").append(audioTimestampSystemTimeUs).append(", ").append(systemTimeUs).append(", ").append(playbackPositionUs).append(", ").append(submittedFrames).append(", ").append(DefaultAudioSink.this.getWrittenFrames()).toString();
            if (DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                throw new InvalidAudioTrackTimestampException(string);
            }
            Log.w(DefaultAudioSink.TAG, string);
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onSystemTimeUsMismatch(long audioTimestampPositionFrames, long audioTimestampSystemTimeUs, long systemTimeUs, long playbackPositionUs) {
            long submittedFrames = DefaultAudioSink.this.getSubmittedFrames();
            String string = new StringBuilder(CircleBarView.TWO_QUARTER).append("Spurious audio timestamp (system clock mismatch): ").append(audioTimestampPositionFrames).append(", ").append(audioTimestampSystemTimeUs).append(", ").append(systemTimeUs).append(", ").append(playbackPositionUs).append(", ").append(submittedFrames).append(", ").append(DefaultAudioSink.this.getWrittenFrames()).toString();
            if (DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                throw new InvalidAudioTrackTimestampException(string);
            }
            Log.w(DefaultAudioSink.TAG, string);
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onInvalidLatency(long latencyUs) {
            Log.w(DefaultAudioSink.TAG, new StringBuilder(61).append("Ignoring impossibly large audio latency: ").append(latencyUs).toString());
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onPositionAdvancing(long playoutStartSystemTimeMs) {
            if (DefaultAudioSink.this.listener != null) {
                DefaultAudioSink.this.listener.onPositionAdvancing(playoutStartSystemTimeMs);
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onUnderrun(int bufferSize, long bufferSizeMs) {
            if (DefaultAudioSink.this.listener != null) {
                DefaultAudioSink.this.listener.onUnderrun(bufferSize, bufferSizeMs, SystemClock.elapsedRealtime() - DefaultAudioSink.this.lastFeedElapsedRealtimeMs);
            }
        }
    }

    private static final class Configuration {
        public final AudioProcessor[] availableAudioProcessors;
        public final int bufferSize;
        public final Format inputFormat;
        public final int inputPcmFrameSize;
        public final int outputChannelConfig;
        public final int outputEncoding;
        public final int outputMode;
        public final int outputPcmFrameSize;
        public final int outputSampleRate;

        public Configuration(Format inputFormat, int inputPcmFrameSize, int outputMode, int outputPcmFrameSize, int outputSampleRate, int outputChannelConfig, int outputEncoding, int specifiedBufferSize, boolean enableAudioTrackPlaybackParams, AudioProcessor[] availableAudioProcessors) {
            this.inputFormat = inputFormat;
            this.inputPcmFrameSize = inputPcmFrameSize;
            this.outputMode = outputMode;
            this.outputPcmFrameSize = outputPcmFrameSize;
            this.outputSampleRate = outputSampleRate;
            this.outputChannelConfig = outputChannelConfig;
            this.outputEncoding = outputEncoding;
            this.availableAudioProcessors = availableAudioProcessors;
            this.bufferSize = computeBufferSize(specifiedBufferSize, enableAudioTrackPlaybackParams);
        }

        public boolean canReuseAudioTrack(Configuration audioTrackConfiguration) {
            return audioTrackConfiguration.outputMode == this.outputMode && audioTrackConfiguration.outputEncoding == this.outputEncoding && audioTrackConfiguration.outputSampleRate == this.outputSampleRate && audioTrackConfiguration.outputChannelConfig == this.outputChannelConfig && audioTrackConfiguration.outputPcmFrameSize == this.outputPcmFrameSize;
        }

        public long inputFramesToDurationUs(long frameCount) {
            return (frameCount * 1000000) / this.inputFormat.sampleRate;
        }

        public long framesToDurationUs(long frameCount) {
            return (frameCount * 1000000) / this.outputSampleRate;
        }

        public long durationUsToFrames(long durationUs) {
            return (durationUs * this.outputSampleRate) / 1000000;
        }

        public AudioTrack buildAudioTrack(boolean tunneling, AudioAttributes audioAttributes, int audioSessionId) throws AudioSink.InitializationException {
            try {
                AudioTrack audioTrackCreateAudioTrack = createAudioTrack(tunneling, audioAttributes, audioSessionId);
                int state = audioTrackCreateAudioTrack.getState();
                if (state == 1) {
                    return audioTrackCreateAudioTrack;
                }
                try {
                    audioTrackCreateAudioTrack.release();
                } catch (Exception unused) {
                }
                throw new AudioSink.InitializationException(state, this.outputSampleRate, this.outputChannelConfig, this.bufferSize, this.inputFormat, outputModeIsOffload(), null);
            } catch (IllegalArgumentException | UnsupportedOperationException e) {
                throw new AudioSink.InitializationException(0, this.outputSampleRate, this.outputChannelConfig, this.bufferSize, this.inputFormat, outputModeIsOffload(), e);
            }
        }

        private AudioTrack createAudioTrack(boolean tunneling, AudioAttributes audioAttributes, int audioSessionId) {
            if (Util.SDK_INT >= 29) {
                return createAudioTrackV29(tunneling, audioAttributes, audioSessionId);
            }
            if (Util.SDK_INT >= 21) {
                return createAudioTrackV21(tunneling, audioAttributes, audioSessionId);
            }
            return createAudioTrackV9(audioAttributes, audioSessionId);
        }

        private AudioTrack createAudioTrackV29(boolean tunneling, AudioAttributes audioAttributes, int audioSessionId) {
            return new AudioTrack.Builder().setAudioAttributes(getAudioTrackAttributesV21(audioAttributes, tunneling)).setAudioFormat(DefaultAudioSink.getAudioFormat(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding)).setTransferMode(1).setBufferSizeInBytes(this.bufferSize).setSessionId(audioSessionId).setOffloadedPlayback(this.outputMode == 1).build();
        }

        private AudioTrack createAudioTrackV21(boolean tunneling, AudioAttributes audioAttributes, int audioSessionId) {
            return new AudioTrack(getAudioTrackAttributesV21(audioAttributes, tunneling), DefaultAudioSink.getAudioFormat(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding), this.bufferSize, 1, audioSessionId);
        }

        private AudioTrack createAudioTrackV9(AudioAttributes audioAttributes, int audioSessionId) {
            int streamTypeForAudioUsage = Util.getStreamTypeForAudioUsage(audioAttributes.usage);
            if (audioSessionId == 0) {
                return new AudioTrack(streamTypeForAudioUsage, this.outputSampleRate, this.outputChannelConfig, this.outputEncoding, this.bufferSize, 1);
            }
            return new AudioTrack(streamTypeForAudioUsage, this.outputSampleRate, this.outputChannelConfig, this.outputEncoding, this.bufferSize, 1, audioSessionId);
        }

        private int computeBufferSize(int specifiedBufferSize, boolean enableAudioTrackPlaybackParameters) {
            if (specifiedBufferSize != 0) {
                return specifiedBufferSize;
            }
            int i = this.outputMode;
            if (i == 0) {
                return getPcmDefaultBufferSize(enableAudioTrackPlaybackParameters ? 8.0f : 1.0f);
            }
            if (i == 1) {
                return getEncodedDefaultBufferSize(DefaultAudioSink.OFFLOAD_BUFFER_DURATION_US);
            }
            if (i == 2) {
                return getEncodedDefaultBufferSize(250000L);
            }
            throw new IllegalStateException();
        }

        private int getEncodedDefaultBufferSize(long bufferDurationUs) {
            int maximumEncodedRateBytesPerSecond = DefaultAudioSink.getMaximumEncodedRateBytesPerSecond(this.outputEncoding);
            if (this.outputEncoding == 5) {
                maximumEncodedRateBytesPerSecond *= 2;
            }
            return (int) ((bufferDurationUs * maximumEncodedRateBytesPerSecond) / 1000000);
        }

        private int getPcmDefaultBufferSize(float maxAudioTrackPlaybackSpeed) {
            int minBufferSize = AudioTrack.getMinBufferSize(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding);
            Assertions.checkState(minBufferSize != -2);
            int iConstrainValue = Util.constrainValue(minBufferSize * 4, ((int) durationUsToFrames(250000L)) * this.outputPcmFrameSize, Math.max(minBufferSize, ((int) durationUsToFrames(DefaultAudioSink.MAX_BUFFER_DURATION_US)) * this.outputPcmFrameSize));
            return maxAudioTrackPlaybackSpeed != 1.0f ? Math.round(iConstrainValue * maxAudioTrackPlaybackSpeed) : iConstrainValue;
        }

        private static android.media.AudioAttributes getAudioTrackAttributesV21(AudioAttributes audioAttributes, boolean tunneling) {
            if (tunneling) {
                return getAudioTrackTunnelingAttributesV21();
            }
            return audioAttributes.getAudioAttributesV21();
        }

        private static android.media.AudioAttributes getAudioTrackTunnelingAttributesV21() {
            return new AudioAttributes.Builder().setContentType(3).setFlags(16).setUsage(1).build();
        }

        public boolean outputModeIsOffload() {
            return this.outputMode == 1;
        }
    }

    private static final class PendingExceptionHolder<T extends Exception> {
        private T pendingException;
        private long throwDeadlineMs;
        private final long throwDelayMs;

        public PendingExceptionHolder(long throwDelayMs) {
            this.throwDelayMs = throwDelayMs;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: T extends java.lang.Exception */
        public void throwExceptionIfDeadlineIsReached(T exception) throws Exception {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (this.pendingException == null) {
                this.pendingException = exception;
                this.throwDeadlineMs = this.throwDelayMs + jElapsedRealtime;
            }
            if (jElapsedRealtime >= this.throwDeadlineMs) {
                T t = this.pendingException;
                if (t != exception) {
                    t.addSuppressed(exception);
                }
                T t2 = this.pendingException;
                clear();
                throw t2;
            }
        }

        public void clear() {
            this.pendingException = null;
        }
    }
}
