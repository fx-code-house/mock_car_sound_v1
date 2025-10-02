package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/* loaded from: classes.dex */
public final class SonicAudioProcessor implements AudioProcessor {
    private static final float CLOSE_THRESHOLD = 1.0E-4f;
    private static final int MIN_BYTES_FOR_DURATION_SCALING_CALCULATION = 1024;
    public static final int SAMPLE_RATE_NO_CHANGE = -1;
    private ByteBuffer buffer;
    private long inputBytes;
    private boolean inputEnded;
    private ByteBuffer outputBuffer;
    private long outputBytes;
    private int pendingOutputSampleRate;
    private boolean pendingSonicRecreation;
    private ShortBuffer shortBuffer;
    private Sonic sonic;
    private float speed = 1.0f;
    private float pitch = 1.0f;
    private AudioProcessor.AudioFormat pendingInputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat pendingOutputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat inputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
    private AudioProcessor.AudioFormat outputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;

    public SonicAudioProcessor() {
        ByteBuffer byteBuffer = EMPTY_BUFFER;
        this.buffer = byteBuffer;
        this.shortBuffer = byteBuffer.asShortBuffer();
        this.outputBuffer = EMPTY_BUFFER;
        this.pendingOutputSampleRate = -1;
    }

    public void setSpeed(float speed) {
        if (this.speed != speed) {
            this.speed = speed;
            this.pendingSonicRecreation = true;
        }
    }

    public void setPitch(float pitch) {
        if (this.pitch != pitch) {
            this.pitch = pitch;
            this.pendingSonicRecreation = true;
        }
    }

    public void setOutputSampleRateHz(int sampleRateHz) {
        this.pendingOutputSampleRate = sampleRateHz;
    }

    public long getMediaDuration(long playoutDuration) {
        if (this.outputBytes >= 1024) {
            long pendingInputBytes = this.inputBytes - ((Sonic) Assertions.checkNotNull(this.sonic)).getPendingInputBytes();
            if (this.outputAudioFormat.sampleRate == this.inputAudioFormat.sampleRate) {
                return Util.scaleLargeTimestamp(playoutDuration, pendingInputBytes, this.outputBytes);
            }
            return Util.scaleLargeTimestamp(playoutDuration, pendingInputBytes * this.outputAudioFormat.sampleRate, this.outputBytes * this.inputAudioFormat.sampleRate);
        }
        return (long) (this.speed * playoutDuration);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public AudioProcessor.AudioFormat configure(AudioProcessor.AudioFormat inputAudioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (inputAudioFormat.encoding != 2) {
            throw new AudioProcessor.UnhandledAudioFormatException(inputAudioFormat);
        }
        int i = this.pendingOutputSampleRate;
        if (i == -1) {
            i = inputAudioFormat.sampleRate;
        }
        this.pendingInputAudioFormat = inputAudioFormat;
        AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(i, inputAudioFormat.channelCount, 2);
        this.pendingOutputAudioFormat = audioFormat;
        this.pendingSonicRecreation = true;
        return audioFormat;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.pendingOutputAudioFormat.sampleRate != -1 && (Math.abs(this.speed - 1.0f) >= 1.0E-4f || Math.abs(this.pitch - 1.0f) >= 1.0E-4f || this.pendingOutputAudioFormat.sampleRate != this.pendingInputAudioFormat.sampleRate);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer inputBuffer) {
        if (inputBuffer.hasRemaining()) {
            Sonic sonic = (Sonic) Assertions.checkNotNull(this.sonic);
            ShortBuffer shortBufferAsShortBuffer = inputBuffer.asShortBuffer();
            int iRemaining = inputBuffer.remaining();
            this.inputBytes += iRemaining;
            sonic.queueInput(shortBufferAsShortBuffer);
            inputBuffer.position(inputBuffer.position() + iRemaining);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueEndOfStream() {
        Sonic sonic = this.sonic;
        if (sonic != null) {
            sonic.queueEndOfStream();
        }
        this.inputEnded = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public ByteBuffer getOutput() {
        int outputSize;
        Sonic sonic = this.sonic;
        if (sonic != null && (outputSize = sonic.getOutputSize()) > 0) {
            if (this.buffer.capacity() < outputSize) {
                ByteBuffer byteBufferOrder = ByteBuffer.allocateDirect(outputSize).order(ByteOrder.nativeOrder());
                this.buffer = byteBufferOrder;
                this.shortBuffer = byteBufferOrder.asShortBuffer();
            } else {
                this.buffer.clear();
                this.shortBuffer.clear();
            }
            sonic.getOutput(this.shortBuffer);
            this.outputBytes += outputSize;
            this.buffer.limit(outputSize);
            this.outputBuffer = this.buffer;
        }
        ByteBuffer byteBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return byteBuffer;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isEnded() {
        Sonic sonic;
        return this.inputEnded && ((sonic = this.sonic) == null || sonic.getOutputSize() == 0);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void flush() {
        if (isActive()) {
            this.inputAudioFormat = this.pendingInputAudioFormat;
            this.outputAudioFormat = this.pendingOutputAudioFormat;
            if (this.pendingSonicRecreation) {
                this.sonic = new Sonic(this.inputAudioFormat.sampleRate, this.inputAudioFormat.channelCount, this.speed, this.pitch, this.outputAudioFormat.sampleRate);
            } else {
                Sonic sonic = this.sonic;
                if (sonic != null) {
                    sonic.flush();
                }
            }
        }
        this.outputBuffer = EMPTY_BUFFER;
        this.inputBytes = 0L;
        this.outputBytes = 0L;
        this.inputEnded = false;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void reset() {
        this.speed = 1.0f;
        this.pitch = 1.0f;
        this.pendingInputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
        this.pendingOutputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
        this.inputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
        this.outputAudioFormat = AudioProcessor.AudioFormat.NOT_SET;
        ByteBuffer byteBuffer = EMPTY_BUFFER;
        this.buffer = byteBuffer;
        this.shortBuffer = byteBuffer.asShortBuffer();
        this.outputBuffer = EMPTY_BUFFER;
        this.pendingOutputSampleRate = -1;
        this.pendingSonicRecreation = false;
        this.sonic = null;
        this.inputBytes = 0L;
        this.outputBytes = 0L;
        this.inputEnded = false;
    }
}
