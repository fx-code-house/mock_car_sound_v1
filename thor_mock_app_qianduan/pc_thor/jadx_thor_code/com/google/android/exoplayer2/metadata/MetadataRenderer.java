package com.google.android.exoplayer2.metadata;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class MetadataRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MSG_INVOKE_RENDERER = 0;
    private static final String TAG = "MetadataRenderer";
    private final MetadataInputBuffer buffer;
    private MetadataDecoder decoder;
    private final MetadataDecoderFactory decoderFactory;
    private boolean inputStreamEnded;
    private final MetadataOutput output;
    private final Handler outputHandler;
    private boolean outputStreamEnded;
    private Metadata pendingMetadata;
    private long pendingMetadataTimestampUs;
    private long subsampleOffsetUs;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    public MetadataRenderer(MetadataOutput output, Looper outputLooper) {
        this(output, outputLooper, MetadataDecoderFactory.DEFAULT);
    }

    public MetadataRenderer(MetadataOutput output, Looper outputLooper, MetadataDecoderFactory decoderFactory) {
        super(5);
        this.output = (MetadataOutput) Assertions.checkNotNull(output);
        this.outputHandler = outputLooper == null ? null : Util.createHandler(outputLooper, this);
        this.decoderFactory = (MetadataDecoderFactory) Assertions.checkNotNull(decoderFactory);
        this.buffer = new MetadataInputBuffer();
        this.pendingMetadataTimestampUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return RendererCapabilities.create(format.exoMediaCryptoType == null ? 4 : 2);
        }
        return RendererCapabilities.create(0);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onStreamChanged(Format[] formats, long startPositionUs, long offsetUs) {
        this.decoder = this.decoderFactory.createDecoder(formats[0]);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long positionUs, boolean joining) {
        this.pendingMetadata = null;
        this.pendingMetadataTimestampUs = C.TIME_UNSET;
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long positionUs, long elapsedRealtimeUs) {
        boolean zOutputMetadata = true;
        while (zOutputMetadata) {
            readMetadata();
            zOutputMetadata = outputMetadata(positionUs);
        }
    }

    private void decodeWrappedMetadata(Metadata metadata, List<Metadata.Entry> decodedEntries) {
        for (int i = 0; i < metadata.length(); i++) {
            Format wrappedMetadataFormat = metadata.get(i).getWrappedMetadataFormat();
            if (wrappedMetadataFormat != null && this.decoderFactory.supportsFormat(wrappedMetadataFormat)) {
                MetadataDecoder metadataDecoderCreateDecoder = this.decoderFactory.createDecoder(wrappedMetadataFormat);
                byte[] bArr = (byte[]) Assertions.checkNotNull(metadata.get(i).getWrappedMetadataBytes());
                this.buffer.clear();
                this.buffer.ensureSpaceForWrite(bArr.length);
                ((ByteBuffer) Util.castNonNull(this.buffer.data)).put(bArr);
                this.buffer.flip();
                Metadata metadataDecode = metadataDecoderCreateDecoder.decode(this.buffer);
                if (metadataDecode != null) {
                    decodeWrappedMetadata(metadataDecode, decodedEntries);
                }
            } else {
                decodedEntries.add(metadata.get(i));
            }
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.pendingMetadata = null;
        this.pendingMetadataTimestampUs = C.TIME_UNSET;
        this.decoder = null;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
        if (msg.what == 0) {
            invokeRendererInternal((Metadata) msg.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    private void readMetadata() {
        if (this.inputStreamEnded || this.pendingMetadata != null) {
            return;
        }
        this.buffer.clear();
        FormatHolder formatHolder = getFormatHolder();
        int source = readSource(formatHolder, this.buffer, 0);
        if (source != -4) {
            if (source == -5) {
                this.subsampleOffsetUs = ((Format) Assertions.checkNotNull(formatHolder.format)).subsampleOffsetUs;
                return;
            }
            return;
        }
        if (this.buffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            return;
        }
        this.buffer.subsampleOffsetUs = this.subsampleOffsetUs;
        this.buffer.flip();
        Metadata metadataDecode = ((MetadataDecoder) Util.castNonNull(this.decoder)).decode(this.buffer);
        if (metadataDecode != null) {
            ArrayList arrayList = new ArrayList(metadataDecode.length());
            decodeWrappedMetadata(metadataDecode, arrayList);
            if (arrayList.isEmpty()) {
                return;
            }
            this.pendingMetadata = new Metadata(arrayList);
            this.pendingMetadataTimestampUs = this.buffer.timeUs;
        }
    }

    private boolean outputMetadata(long positionUs) {
        boolean z;
        Metadata metadata = this.pendingMetadata;
        if (metadata == null || this.pendingMetadataTimestampUs > positionUs) {
            z = false;
        } else {
            invokeRenderer(metadata);
            this.pendingMetadata = null;
            this.pendingMetadataTimestampUs = C.TIME_UNSET;
            z = true;
        }
        if (this.inputStreamEnded && this.pendingMetadata == null) {
            this.outputStreamEnded = true;
        }
        return z;
    }

    private void invokeRenderer(Metadata metadata) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, metadata).sendToTarget();
        } else {
            invokeRendererInternal(metadata);
        }
    }

    private void invokeRendererInternal(Metadata metadata) {
        this.output.onMetadata(metadata);
    }
}
