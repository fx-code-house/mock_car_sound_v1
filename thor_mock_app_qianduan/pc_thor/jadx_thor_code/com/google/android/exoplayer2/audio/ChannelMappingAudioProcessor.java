package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class ChannelMappingAudioProcessor extends BaseAudioProcessor {
    private int[] outputChannels;
    private int[] pendingOutputChannels;

    ChannelMappingAudioProcessor() {
    }

    public void setChannelMap(int[] outputChannels) {
        this.pendingOutputChannels = outputChannels;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat inputAudioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        int[] iArr = this.pendingOutputChannels;
        if (iArr == null) {
            return AudioProcessor.AudioFormat.NOT_SET;
        }
        if (inputAudioFormat.encoding != 2) {
            throw new AudioProcessor.UnhandledAudioFormatException(inputAudioFormat);
        }
        boolean z = inputAudioFormat.channelCount != iArr.length;
        int i = 0;
        while (i < iArr.length) {
            int i2 = iArr[i];
            if (i2 >= inputAudioFormat.channelCount) {
                throw new AudioProcessor.UnhandledAudioFormatException(inputAudioFormat);
            }
            z |= i2 != i;
            i++;
        }
        if (z) {
            return new AudioProcessor.AudioFormat(inputAudioFormat.sampleRate, iArr.length, 2);
        }
        return AudioProcessor.AudioFormat.NOT_SET;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer inputBuffer) {
        int[] iArr = (int[]) Assertions.checkNotNull(this.outputChannels);
        int iPosition = inputBuffer.position();
        int iLimit = inputBuffer.limit();
        ByteBuffer byteBufferReplaceOutputBuffer = replaceOutputBuffer(((iLimit - iPosition) / this.inputAudioFormat.bytesPerFrame) * this.outputAudioFormat.bytesPerFrame);
        while (iPosition < iLimit) {
            for (int i : iArr) {
                byteBufferReplaceOutputBuffer.putShort(inputBuffer.getShort((i * 2) + iPosition));
            }
            iPosition += this.inputAudioFormat.bytesPerFrame;
        }
        inputBuffer.position(iLimit);
        byteBufferReplaceOutputBuffer.flip();
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onFlush() {
        this.outputChannels = this.pendingOutputChannels;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    protected void onReset() {
        this.outputChannels = null;
        this.pendingOutputChannels = null;
    }
}
