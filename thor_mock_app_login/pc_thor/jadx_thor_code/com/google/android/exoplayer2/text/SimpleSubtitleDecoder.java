package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.decoder.SimpleDecoder;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class SimpleSubtitleDecoder extends SimpleDecoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> implements SubtitleDecoder {
    private final String name;

    protected abstract Subtitle decode(byte[] data, int size, boolean reset) throws SubtitleDecoderException;

    @Override // com.google.android.exoplayer2.text.SubtitleDecoder
    public void setPositionUs(long positionUs) {
    }

    protected SimpleSubtitleDecoder(String name) {
        super(new SubtitleInputBuffer[2], new SubtitleOutputBuffer[2]);
        this.name = name;
        setInitialInputBufferSize(1024);
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.decoder.SimpleDecoder
    public final SubtitleInputBuffer createInputBuffer() {
        return new SubtitleInputBuffer();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.decoder.SimpleDecoder
    public final SubtitleOutputBuffer createOutputBuffer() {
        return new SimpleSubtitleOutputBuffer(new OutputBuffer.Owner() { // from class: com.google.android.exoplayer2.text.SimpleSubtitleDecoder$$ExternalSyntheticLambda0
            @Override // com.google.android.exoplayer2.decoder.OutputBuffer.Owner
            public final void releaseOutputBuffer(OutputBuffer outputBuffer) {
                this.f$0.releaseOutputBuffer((SubtitleOutputBuffer) outputBuffer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.decoder.SimpleDecoder
    public final SubtitleDecoderException createUnexpectedDecodeException(Throwable error) {
        return new SubtitleDecoderException("Unexpected decode error", error);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.decoder.SimpleDecoder
    public final SubtitleDecoderException decode(SubtitleInputBuffer inputBuffer, SubtitleOutputBuffer outputBuffer, boolean reset) {
        try {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(inputBuffer.data);
            outputBuffer.setContent(inputBuffer.timeUs, decode(byteBuffer.array(), byteBuffer.limit(), reset), inputBuffer.subsampleOffsetUs);
            outputBuffer.clearFlag(Integer.MIN_VALUE);
            return null;
        } catch (SubtitleDecoderException e) {
            return e;
        }
    }
}
