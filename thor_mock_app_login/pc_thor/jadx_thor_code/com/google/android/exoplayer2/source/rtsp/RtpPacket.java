package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class RtpPacket {
    public static final int CSRC_SIZE = 4;
    private static final byte[] EMPTY = new byte[0];
    public static final int MAX_SEQUENCE_NUMBER = 65535;
    public static final int MAX_SIZE = 65507;
    public static final int MIN_HEADER_SIZE = 12;
    public static final int MIN_SEQUENCE_NUMBER = 0;
    public static final int RTP_VERSION = 2;
    public final byte[] csrc;
    public final byte csrcCount;
    public final boolean extension;
    public final boolean marker;
    public final boolean padding;
    public final byte[] payloadData;
    public final byte payloadType;
    public final int sequenceNumber;
    public final int ssrc;
    public final long timestamp;
    public final byte version;

    public static final class Builder {
        private boolean marker;
        private boolean padding;
        private byte payloadType;
        private int sequenceNumber;
        private int ssrc;
        private long timestamp;
        private byte[] csrc = RtpPacket.EMPTY;
        private byte[] payloadData = RtpPacket.EMPTY;

        public Builder setPadding(boolean padding) {
            this.padding = padding;
            return this;
        }

        public Builder setMarker(boolean marker) {
            this.marker = marker;
            return this;
        }

        public Builder setPayloadType(byte payloadType) {
            this.payloadType = payloadType;
            return this;
        }

        public Builder setSequenceNumber(int sequenceNumber) {
            Assertions.checkArgument(sequenceNumber >= 0 && sequenceNumber <= 65535);
            this.sequenceNumber = sequenceNumber & 65535;
            return this;
        }

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setSsrc(int ssrc) {
            this.ssrc = ssrc;
            return this;
        }

        public Builder setCsrc(byte[] csrc) {
            Assertions.checkNotNull(csrc);
            this.csrc = csrc;
            return this;
        }

        public Builder setPayloadData(byte[] payloadData) {
            Assertions.checkNotNull(payloadData);
            this.payloadData = payloadData;
            return this;
        }

        public RtpPacket build() {
            return new RtpPacket(this);
        }
    }

    public static RtpPacket parse(ParsableByteArray packetBuffer) {
        byte[] bArr;
        if (packetBuffer.bytesLeft() < 12) {
            return null;
        }
        int unsignedByte = packetBuffer.readUnsignedByte();
        byte b = (byte) (unsignedByte >> 6);
        boolean z = ((unsignedByte >> 5) & 1) == 1;
        byte b2 = (byte) (unsignedByte & 15);
        if (b != 2) {
            return null;
        }
        int unsignedByte2 = packetBuffer.readUnsignedByte();
        boolean z2 = ((unsignedByte2 >> 7) & 1) == 1;
        byte b3 = (byte) (unsignedByte2 & 127);
        int unsignedShort = packetBuffer.readUnsignedShort();
        long unsignedInt = packetBuffer.readUnsignedInt();
        int i = packetBuffer.readInt();
        if (b2 > 0) {
            bArr = new byte[b2 * 4];
            for (int i2 = 0; i2 < b2; i2++) {
                packetBuffer.readBytes(bArr, i2 * 4, 4);
            }
        } else {
            bArr = EMPTY;
        }
        byte[] bArr2 = new byte[packetBuffer.bytesLeft()];
        packetBuffer.readBytes(bArr2, 0, packetBuffer.bytesLeft());
        return new Builder().setPadding(z).setMarker(z2).setPayloadType(b3).setSequenceNumber(unsignedShort).setTimestamp(unsignedInt).setSsrc(i).setCsrc(bArr).setPayloadData(bArr2).build();
    }

    public static RtpPacket parse(byte[] buffer, int length) {
        return parse(new ParsableByteArray(buffer, length));
    }

    private RtpPacket(Builder builder) {
        this.version = (byte) 2;
        this.padding = builder.padding;
        this.extension = false;
        this.marker = builder.marker;
        this.payloadType = builder.payloadType;
        this.sequenceNumber = builder.sequenceNumber;
        this.timestamp = builder.timestamp;
        this.ssrc = builder.ssrc;
        byte[] bArr = builder.csrc;
        this.csrc = bArr;
        this.csrcCount = (byte) (bArr.length / 4);
        this.payloadData = builder.payloadData;
    }

    public int writeToBuffer(byte[] bArr, int i, int i2) {
        int length = (this.csrcCount * 4) + 12 + this.payloadData.length;
        if (i2 < length || bArr.length - i < length) {
            return -1;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr, i, i2);
        byte b = (byte) (((this.padding ? 1 : 0) << 5) | 128 | ((this.extension ? 1 : 0) << 4) | (this.csrcCount & Ascii.SI));
        byteBufferWrap.put(b).put((byte) (((this.marker ? 1 : 0) << 7) | (this.payloadType & Byte.MAX_VALUE))).putShort((short) this.sequenceNumber).putInt((int) this.timestamp).putInt(this.ssrc).put(this.csrc).put(this.payloadData);
        return length;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RtpPacket rtpPacket = (RtpPacket) o;
        return this.payloadType == rtpPacket.payloadType && this.sequenceNumber == rtpPacket.sequenceNumber && this.marker == rtpPacket.marker && this.timestamp == rtpPacket.timestamp && this.ssrc == rtpPacket.ssrc;
    }

    public int hashCode() {
        int i = (((((527 + this.payloadType) * 31) + this.sequenceNumber) * 31) + (this.marker ? 1 : 0)) * 31;
        long j = this.timestamp;
        return ((i + ((int) (j ^ (j >>> 32)))) * 31) + this.ssrc;
    }

    public String toString() {
        return Util.formatInvariant("RtpPacket(payloadType=%d, seq=%d, timestamp=%d, ssrc=%x, marker=%b)", Byte.valueOf(this.payloadType), Integer.valueOf(this.sequenceNumber), Long.valueOf(this.timestamp), Integer.valueOf(this.ssrc), Boolean.valueOf(this.marker));
    }
}
