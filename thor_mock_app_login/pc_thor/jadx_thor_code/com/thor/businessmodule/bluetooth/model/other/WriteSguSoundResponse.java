package com.thor.businessmodule.bluetooth.model.other;

import kotlin.Metadata;

/* compiled from: WriteSguSoundResponse.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/WriteSguSoundResponse;", "", "soundId", "", "totalBlocks", "blockNumber", "(SSS)V", "getBlockNumber", "()S", "getSoundId", "getTotalBlocks", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class WriteSguSoundResponse {
    private final short blockNumber;
    private final short soundId;
    private final short totalBlocks;

    public static /* synthetic */ WriteSguSoundResponse copy$default(WriteSguSoundResponse writeSguSoundResponse, short s, short s2, short s3, int i, Object obj) {
        if ((i & 1) != 0) {
            s = writeSguSoundResponse.soundId;
        }
        if ((i & 2) != 0) {
            s2 = writeSguSoundResponse.totalBlocks;
        }
        if ((i & 4) != 0) {
            s3 = writeSguSoundResponse.blockNumber;
        }
        return writeSguSoundResponse.copy(s, s2, s3);
    }

    /* renamed from: component1, reason: from getter */
    public final short getSoundId() {
        return this.soundId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getTotalBlocks() {
        return this.totalBlocks;
    }

    /* renamed from: component3, reason: from getter */
    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final WriteSguSoundResponse copy(short soundId, short totalBlocks, short blockNumber) {
        return new WriteSguSoundResponse(soundId, totalBlocks, blockNumber);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WriteSguSoundResponse)) {
            return false;
        }
        WriteSguSoundResponse writeSguSoundResponse = (WriteSguSoundResponse) other;
        return this.soundId == writeSguSoundResponse.soundId && this.totalBlocks == writeSguSoundResponse.totalBlocks && this.blockNumber == writeSguSoundResponse.blockNumber;
    }

    public int hashCode() {
        return (((Short.hashCode(this.soundId) * 31) + Short.hashCode(this.totalBlocks)) * 31) + Short.hashCode(this.blockNumber);
    }

    public String toString() {
        return "WriteSguSoundResponse(soundId=" + ((int) this.soundId) + ", totalBlocks=" + ((int) this.totalBlocks) + ", blockNumber=" + ((int) this.blockNumber) + ")";
    }

    public WriteSguSoundResponse(short s, short s2, short s3) {
        this.soundId = s;
        this.totalBlocks = s2;
        this.blockNumber = s3;
    }

    public final short getSoundId() {
        return this.soundId;
    }

    public final short getTotalBlocks() {
        return this.totalBlocks;
    }

    public final short getBlockNumber() {
        return this.blockNumber;
    }
}
