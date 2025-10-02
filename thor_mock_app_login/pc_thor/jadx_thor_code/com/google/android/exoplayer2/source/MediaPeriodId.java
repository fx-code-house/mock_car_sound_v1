package com.google.android.exoplayer2.source;

/* loaded from: classes.dex */
public class MediaPeriodId {
    public final int adGroupIndex;
    public final int adIndexInAdGroup;
    public final int nextAdGroupIndex;
    public final Object periodUid;
    public final long windowSequenceNumber;

    public MediaPeriodId(Object periodUid) {
        this(periodUid, -1L);
    }

    public MediaPeriodId(Object periodUid, long windowSequenceNumber) {
        this(periodUid, -1, -1, windowSequenceNumber, -1);
    }

    public MediaPeriodId(Object periodUid, long windowSequenceNumber, int nextAdGroupIndex) {
        this(periodUid, -1, -1, windowSequenceNumber, nextAdGroupIndex);
    }

    public MediaPeriodId(Object periodUid, int adGroupIndex, int adIndexInAdGroup, long windowSequenceNumber) {
        this(periodUid, adGroupIndex, adIndexInAdGroup, windowSequenceNumber, -1);
    }

    protected MediaPeriodId(MediaPeriodId mediaPeriodId) {
        this.periodUid = mediaPeriodId.periodUid;
        this.adGroupIndex = mediaPeriodId.adGroupIndex;
        this.adIndexInAdGroup = mediaPeriodId.adIndexInAdGroup;
        this.windowSequenceNumber = mediaPeriodId.windowSequenceNumber;
        this.nextAdGroupIndex = mediaPeriodId.nextAdGroupIndex;
    }

    private MediaPeriodId(Object periodUid, int adGroupIndex, int adIndexInAdGroup, long windowSequenceNumber, int nextAdGroupIndex) {
        this.periodUid = periodUid;
        this.adGroupIndex = adGroupIndex;
        this.adIndexInAdGroup = adIndexInAdGroup;
        this.windowSequenceNumber = windowSequenceNumber;
        this.nextAdGroupIndex = nextAdGroupIndex;
    }

    public MediaPeriodId copyWithPeriodUid(Object newPeriodUid) {
        return this.periodUid.equals(newPeriodUid) ? this : new MediaPeriodId(newPeriodUid, this.adGroupIndex, this.adIndexInAdGroup, this.windowSequenceNumber, this.nextAdGroupIndex);
    }

    public MediaPeriodId copyWithWindowSequenceNumber(long windowSequenceNumber) {
        return this.windowSequenceNumber == windowSequenceNumber ? this : new MediaPeriodId(this.periodUid, this.adGroupIndex, this.adIndexInAdGroup, windowSequenceNumber, this.nextAdGroupIndex);
    }

    public boolean isAd() {
        return this.adGroupIndex != -1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaPeriodId)) {
            return false;
        }
        MediaPeriodId mediaPeriodId = (MediaPeriodId) obj;
        return this.periodUid.equals(mediaPeriodId.periodUid) && this.adGroupIndex == mediaPeriodId.adGroupIndex && this.adIndexInAdGroup == mediaPeriodId.adIndexInAdGroup && this.windowSequenceNumber == mediaPeriodId.windowSequenceNumber && this.nextAdGroupIndex == mediaPeriodId.nextAdGroupIndex;
    }

    public int hashCode() {
        return ((((((((527 + this.periodUid.hashCode()) * 31) + this.adGroupIndex) * 31) + this.adIndexInAdGroup) * 31) + ((int) this.windowSequenceNumber)) * 31) + this.nextAdGroupIndex;
    }
}
