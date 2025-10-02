package com.google.android.exoplayer2.metadata.emsg;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class EventMessageDecoder extends SimpleMetadataDecoder {
    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    protected Metadata decode(MetadataInputBuffer inputBuffer, ByteBuffer buffer) {
        return new Metadata(decode(new ParsableByteArray(buffer.array(), buffer.limit())));
    }

    public EventMessage decode(ParsableByteArray emsgData) {
        return new EventMessage((String) Assertions.checkNotNull(emsgData.readNullTerminatedString()), (String) Assertions.checkNotNull(emsgData.readNullTerminatedString()), emsgData.readLong(), emsgData.readLong(), Arrays.copyOfRange(emsgData.getData(), emsgData.getPosition(), emsgData.limit()));
    }
}
