package com.thor.businessmodule.bluetooth.model.other;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;

/* compiled from: ReloadUploadingSoundPackageStatus.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u001aB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010\f\u001a\u0004\u0018\u00010\u0015J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u001b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingSoundPackageStatus;", "", "soundPackageId", "", "soundPackageVersionId", NotificationCompat.CATEGORY_STATUS, "blockNumber", "(SSSS)V", "getBlockNumber", "()S", "getSoundPackageId", "getSoundPackageVersionId", "getStatus", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingSoundPackageStatus$Status;", "hashCode", "", "toString", "", "Status", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ReloadUploadingSoundPackageStatus {
    private final short blockNumber;
    private final short soundPackageId;
    private final short soundPackageVersionId;
    private final short status;

    public static /* synthetic */ ReloadUploadingSoundPackageStatus copy$default(ReloadUploadingSoundPackageStatus reloadUploadingSoundPackageStatus, short s, short s2, short s3, short s4, int i, Object obj) {
        if ((i & 1) != 0) {
            s = reloadUploadingSoundPackageStatus.soundPackageId;
        }
        if ((i & 2) != 0) {
            s2 = reloadUploadingSoundPackageStatus.soundPackageVersionId;
        }
        if ((i & 4) != 0) {
            s3 = reloadUploadingSoundPackageStatus.status;
        }
        if ((i & 8) != 0) {
            s4 = reloadUploadingSoundPackageStatus.blockNumber;
        }
        return reloadUploadingSoundPackageStatus.copy(s, s2, s3, s4);
    }

    /* renamed from: component1, reason: from getter */
    public final short getSoundPackageId() {
        return this.soundPackageId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getSoundPackageVersionId() {
        return this.soundPackageVersionId;
    }

    /* renamed from: component3, reason: from getter */
    public final short getStatus() {
        return this.status;
    }

    /* renamed from: component4, reason: from getter */
    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final ReloadUploadingSoundPackageStatus copy(short soundPackageId, short soundPackageVersionId, short status, short blockNumber) {
        return new ReloadUploadingSoundPackageStatus(soundPackageId, soundPackageVersionId, status, blockNumber);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReloadUploadingSoundPackageStatus)) {
            return false;
        }
        ReloadUploadingSoundPackageStatus reloadUploadingSoundPackageStatus = (ReloadUploadingSoundPackageStatus) other;
        return this.soundPackageId == reloadUploadingSoundPackageStatus.soundPackageId && this.soundPackageVersionId == reloadUploadingSoundPackageStatus.soundPackageVersionId && this.status == reloadUploadingSoundPackageStatus.status && this.blockNumber == reloadUploadingSoundPackageStatus.blockNumber;
    }

    public int hashCode() {
        return (((((Short.hashCode(this.soundPackageId) * 31) + Short.hashCode(this.soundPackageVersionId)) * 31) + Short.hashCode(this.status)) * 31) + Short.hashCode(this.blockNumber);
    }

    public String toString() {
        return "ReloadUploadingSoundPackageStatus(soundPackageId=" + ((int) this.soundPackageId) + ", soundPackageVersionId=" + ((int) this.soundPackageVersionId) + ", status=" + ((int) this.status) + ", blockNumber=" + ((int) this.blockNumber) + ")";
    }

    public ReloadUploadingSoundPackageStatus(short s, short s2, short s3, short s4) {
        this.soundPackageId = s;
        this.soundPackageVersionId = s2;
        this.status = s3;
        this.blockNumber = s4;
    }

    public final short getSoundPackageId() {
        return this.soundPackageId;
    }

    public final short getSoundPackageVersionId() {
        return this.soundPackageVersionId;
    }

    /* renamed from: getStatus, reason: collision with other method in class */
    public final short m599getStatus() {
        return this.status;
    }

    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final Status getStatus() {
        short s = this.status;
        if (s == Status.NEED_FULL_RELOAD.getValue()) {
            return Status.NEED_FULL_RELOAD;
        }
        if (s == Status.NEED_RELOAD_SAMPLES.getValue()) {
            return Status.NEED_RELOAD_SAMPLES;
        }
        if (s == Status.NEED_RELOAD_SAMPLES_RULES.getValue()) {
            return Status.NEED_RELOAD_SAMPLES_RULES;
        }
        if (s == Status.NEED_RELOAD_MODE_RULES.getValue()) {
            return Status.NEED_RELOAD_MODE_RULES;
        }
        if (s == Status.NEED_ACCEPT_UPLOADING.getValue()) {
            return Status.NEED_ACCEPT_UPLOADING;
        }
        return null;
    }

    /* compiled from: ReloadUploadingSoundPackageStatus.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/ReloadUploadingSoundPackageStatus$Status;", "", "value", "", "(Ljava/lang/String;IS)V", "getValue", "()S", "NEED_FULL_RELOAD", "NEED_RELOAD_SAMPLES", "NEED_RELOAD_SAMPLES_RULES", "NEED_RELOAD_MODE_RULES", "NEED_ACCEPT_UPLOADING", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum Status {
        NEED_FULL_RELOAD(0),
        NEED_RELOAD_SAMPLES(1),
        NEED_RELOAD_SAMPLES_RULES(2),
        NEED_RELOAD_MODE_RULES(3),
        NEED_ACCEPT_UPLOADING(4);

        private final short value;

        Status(short s) {
            this.value = s;
        }

        public final short getValue() {
            return this.value;
        }
    }
}
