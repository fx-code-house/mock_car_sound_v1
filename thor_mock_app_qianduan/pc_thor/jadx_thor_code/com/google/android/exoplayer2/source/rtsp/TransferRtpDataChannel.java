package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.BaseDataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
final class TransferRtpDataChannel extends BaseDataSource implements RtpDataChannel, RtspMessageChannel.InterleavedBinaryDataListener {
    private static final String DEFAULT_TCP_TRANSPORT_FORMAT = "RTP/AVP/TCP;unicast;interleaved=%d-%d";
    private int channelNumber;
    private final LinkedBlockingQueue<byte[]> packetQueue;
    private final long pollTimeoutMs;
    private byte[] unreadData;

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public RtspMessageChannel.InterleavedBinaryDataListener getInterleavedBinaryDataListener() {
        return this;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return null;
    }

    public TransferRtpDataChannel(long pollTimeoutMs) {
        super(true);
        this.pollTimeoutMs = pollTimeoutMs;
        this.packetQueue = new LinkedBlockingQueue<>();
        this.unreadData = new byte[0];
        this.channelNumber = -1;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public String getTransport() {
        Assertions.checkState(this.channelNumber != -1);
        return Util.formatInvariant(DEFAULT_TCP_TRANSPORT_FORMAT, Integer.valueOf(this.channelNumber), Integer.valueOf(this.channelNumber + 1));
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public int getLocalPort() {
        return this.channelNumber;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) {
        this.channelNumber = dataSpec.uri.getPort();
        return -1L;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) throws InterruptedException {
        if (length == 0) {
            return 0;
        }
        int iMin = Math.min(length, this.unreadData.length);
        System.arraycopy(this.unreadData, 0, buffer, offset, iMin);
        int i = iMin + 0;
        byte[] bArr = this.unreadData;
        this.unreadData = Arrays.copyOfRange(bArr, iMin, bArr.length);
        if (i == length) {
            return i;
        }
        try {
            byte[] bArrPoll = this.packetQueue.poll(this.pollTimeoutMs, TimeUnit.MILLISECONDS);
            if (bArrPoll == null) {
                return -1;
            }
            int iMin2 = Math.min(length - i, bArrPoll.length);
            System.arraycopy(bArrPoll, 0, buffer, offset + i, iMin2);
            if (iMin2 < bArrPoll.length) {
                this.unreadData = Arrays.copyOfRange(bArrPoll, iMin2, bArrPoll.length);
            }
            return i + iMin2;
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.InterleavedBinaryDataListener
    public void onInterleavedBinaryDataReceived(byte[] data) {
        this.packetQueue.add(data);
    }
}
