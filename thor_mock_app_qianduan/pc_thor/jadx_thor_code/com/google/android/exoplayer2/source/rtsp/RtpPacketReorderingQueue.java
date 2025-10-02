package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.source.rtsp.RtpPacketReorderingQueue;
import java.util.Comparator;
import java.util.TreeSet;

/* loaded from: classes.dex */
final class RtpPacketReorderingQueue {
    static final int MAX_SEQUENCE_LEAP_ALLOWED = 1000;
    private static final int MAX_SEQUENCE_NUMBER = 65535;
    private static final int QUEUE_SIZE_THRESHOLD_FOR_RESET = 5000;
    private int lastDequeuedSequenceNumber;
    private int lastReceivedSequenceNumber;
    private final TreeSet<RtpPacketContainer> packetQueue = new TreeSet<>(new Comparator() { // from class: com.google.android.exoplayer2.source.rtsp.RtpPacketReorderingQueue$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return RtpPacketReorderingQueue.calculateSequenceNumberShift(((RtpPacketReorderingQueue.RtpPacketContainer) obj).packet.sequenceNumber, ((RtpPacketReorderingQueue.RtpPacketContainer) obj2).packet.sequenceNumber);
        }
    });
    private boolean started;

    public RtpPacketReorderingQueue() {
        reset();
    }

    public synchronized void reset() {
        this.packetQueue.clear();
        this.started = false;
        this.lastDequeuedSequenceNumber = -1;
        this.lastReceivedSequenceNumber = -1;
    }

    public synchronized boolean offer(RtpPacket packet, long receivedTimestampMs) {
        if (this.packetQueue.size() >= 5000) {
            throw new IllegalStateException("Queue size limit of 5000 reached.");
        }
        int i = packet.sequenceNumber;
        if (!this.started) {
            reset();
            this.lastDequeuedSequenceNumber = prevSequenceNumber(i);
            this.started = true;
            addToQueue(new RtpPacketContainer(packet, receivedTimestampMs));
            return true;
        }
        if (Math.abs(calculateSequenceNumberShift(i, nextSequenceNumber(this.lastReceivedSequenceNumber))) < 1000) {
            if (calculateSequenceNumberShift(i, this.lastDequeuedSequenceNumber) <= 0) {
                return false;
            }
            addToQueue(new RtpPacketContainer(packet, receivedTimestampMs));
            return true;
        }
        this.lastDequeuedSequenceNumber = prevSequenceNumber(i);
        this.packetQueue.clear();
        addToQueue(new RtpPacketContainer(packet, receivedTimestampMs));
        return true;
    }

    public synchronized RtpPacket poll(long cutoffTimestampMs) {
        if (this.packetQueue.isEmpty()) {
            return null;
        }
        RtpPacketContainer rtpPacketContainerFirst = this.packetQueue.first();
        int i = rtpPacketContainerFirst.packet.sequenceNumber;
        if (i != nextSequenceNumber(this.lastDequeuedSequenceNumber) && cutoffTimestampMs < rtpPacketContainerFirst.receivedTimestampMs) {
            return null;
        }
        this.packetQueue.pollFirst();
        this.lastDequeuedSequenceNumber = i;
        return rtpPacketContainerFirst.packet;
    }

    private synchronized void addToQueue(RtpPacketContainer packet) {
        this.lastReceivedSequenceNumber = packet.packet.sequenceNumber;
        this.packetQueue.add(packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RtpPacketContainer {
        public final RtpPacket packet;
        public final long receivedTimestampMs;

        public RtpPacketContainer(RtpPacket packet, long receivedTimestampMs) {
            this.packet = packet;
            this.receivedTimestampMs = receivedTimestampMs;
        }
    }

    private static int nextSequenceNumber(int sequenceNumber) {
        return (sequenceNumber + 1) % 65535;
    }

    private static int prevSequenceNumber(int sequenceNumber) {
        return sequenceNumber == 0 ? WavUtil.TYPE_WAVE_FORMAT_EXTENSIBLE : (sequenceNumber - 1) % 65535;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int calculateSequenceNumberShift(int sequenceNumber, int previousSequenceNumber) {
        int iMin;
        int i = sequenceNumber - previousSequenceNumber;
        return (Math.abs(i) <= 1000 || (iMin = (Math.min(sequenceNumber, previousSequenceNumber) - Math.max(sequenceNumber, previousSequenceNumber)) + 65535) >= 1000) ? i : sequenceNumber < previousSequenceNumber ? iMin : -iMin;
    }
}
