package com.thor.businessmodule.bluetooth.model.sgu;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: UploadingSguSoundStatus.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\fB!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u0004\u0018\u00010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/sgu/UploadingSguSoundStatus;", "", "soundId", "", NotificationCompat.CATEGORY_STATUS, "blockNumber", "(SSS)V", "getBlockNumber", "()S", "getSoundId", "getStatus", "Lcom/thor/businessmodule/bluetooth/model/sgu/UploadingSguSoundStatus$Status;", "Status", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadingSguSoundStatus {
    private final short blockNumber;
    private final short soundId;
    private final short status;

    public UploadingSguSoundStatus(short s, short s2, short s3) {
        this.soundId = s;
        this.status = s2;
        this.blockNumber = s3;
    }

    public /* synthetic */ UploadingSguSoundStatus(short s, short s2, short s3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(s, (i & 2) != 0 ? (short) 0 : s2, (i & 4) != 0 ? (short) 0 : s3);
    }

    public final short getSoundId() {
        return this.soundId;
    }

    public final short getStatus() {
        return this.status;
    }

    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final Status getStatus() {
        short s = this.status;
        if (s == Status.NEED_FULL_LOAD.getValue()) {
            return Status.NEED_FULL_LOAD;
        }
        if (s == Status.NEED_CONTINUE_UPLOAD.getValue()) {
            return Status.NEED_CONTINUE_UPLOAD;
        }
        if (s == Status.NEED_ACCEPT_UPLOAD.getValue()) {
            return Status.NEED_ACCEPT_UPLOAD;
        }
        return null;
    }

    /* compiled from: UploadingSguSoundStatus.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/sgu/UploadingSguSoundStatus$Status;", "", "value", "", "(Ljava/lang/String;IS)V", "getValue", "()S", "NEED_FULL_LOAD", "NEED_CONTINUE_UPLOAD", "NEED_ACCEPT_UPLOAD", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum Status {
        NEED_FULL_LOAD(0),
        NEED_CONTINUE_UPLOAD(1),
        NEED_ACCEPT_UPLOAD(4);

        private final short value;

        Status(short s) {
            this.value = s;
        }

        public final short getValue() {
            return this.value;
        }
    }
}
