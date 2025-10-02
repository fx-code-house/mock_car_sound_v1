package com.google.android.exoplayer2.metadata;

import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class SimpleMetadataDecoder implements MetadataDecoder {
    protected abstract Metadata decode(MetadataInputBuffer inputBuffer, ByteBuffer buffer);

    @Override // com.google.android.exoplayer2.metadata.MetadataDecoder
    public final Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(inputBuffer.data);
        Assertions.checkArgument(byteBuffer.position() == 0 && byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0);
        if (inputBuffer.isDecodeOnly()) {
            return null;
        }
        return decode(inputBuffer, byteBuffer);
    }
}
