package com.thor.businessmodule.bluetooth.model.other;

import androidx.core.app.NotificationCompat;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

/* compiled from: DownloadGetStatusModel.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001(B3\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0005HÆ\u0003¢\u0006\u0002\u0010\u0010J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u001f\u001a\u00020\tHÆ\u0003J<\u0010 \u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001¢\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020\tHÖ\u0001J\t\u0010&\u001a\u00020'HÖ\u0001R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006)"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "", NotificationCompat.CATEGORY_STATUS, "Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel$Status;", "errorCode", "", TtmlNode.ATTR_ID, "Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;", NewHtcHomeBadger.COUNT, "", "(Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel$Status;Ljava/lang/Short;Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;I)V", "getCount", "()I", "setCount", "(I)V", "getErrorCode", "()Ljava/lang/Short;", "setErrorCode", "(Ljava/lang/Short;)V", "Ljava/lang/Short;", "getId", "()Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;", "setId", "(Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;)V", "getStatus", "()Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel$Status;", "setStatus", "(Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel$Status;)V", "component1", "component2", "component3", "component4", "copy", "(Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel$Status;Ljava/lang/Short;Lcom/thor/businessmodule/bluetooth/model/other/IdFileModel;I)Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel;", "equals", "", "other", "hashCode", "toString", "", "Status", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class DownloadGetStatusModel {
    private int count;
    private Short errorCode;
    private IdFileModel id;
    private Status status;

    public DownloadGetStatusModel() {
        this(null, null, null, 0, 15, null);
    }

    public static /* synthetic */ DownloadGetStatusModel copy$default(DownloadGetStatusModel downloadGetStatusModel, Status status, Short sh, IdFileModel idFileModel, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            status = downloadGetStatusModel.status;
        }
        if ((i2 & 2) != 0) {
            sh = downloadGetStatusModel.errorCode;
        }
        if ((i2 & 4) != 0) {
            idFileModel = downloadGetStatusModel.id;
        }
        if ((i2 & 8) != 0) {
            i = downloadGetStatusModel.count;
        }
        return downloadGetStatusModel.copy(status, sh, idFileModel, i);
    }

    /* renamed from: component1, reason: from getter */
    public final Status getStatus() {
        return this.status;
    }

    /* renamed from: component2, reason: from getter */
    public final Short getErrorCode() {
        return this.errorCode;
    }

    /* renamed from: component3, reason: from getter */
    public final IdFileModel getId() {
        return this.id;
    }

    /* renamed from: component4, reason: from getter */
    public final int getCount() {
        return this.count;
    }

    public final DownloadGetStatusModel copy(Status status, Short errorCode, IdFileModel id, int count) {
        return new DownloadGetStatusModel(status, errorCode, id, count);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DownloadGetStatusModel)) {
            return false;
        }
        DownloadGetStatusModel downloadGetStatusModel = (DownloadGetStatusModel) other;
        return this.status == downloadGetStatusModel.status && Intrinsics.areEqual(this.errorCode, downloadGetStatusModel.errorCode) && Intrinsics.areEqual(this.id, downloadGetStatusModel.id) && this.count == downloadGetStatusModel.count;
    }

    public int hashCode() {
        Status status = this.status;
        int iHashCode = (status == null ? 0 : status.hashCode()) * 31;
        Short sh = this.errorCode;
        int iHashCode2 = (iHashCode + (sh == null ? 0 : sh.hashCode())) * 31;
        IdFileModel idFileModel = this.id;
        return ((iHashCode2 + (idFileModel != null ? idFileModel.hashCode() : 0)) * 31) + Integer.hashCode(this.count);
    }

    public String toString() {
        return "DownloadGetStatusModel(status=" + this.status + ", errorCode=" + this.errorCode + ", id=" + this.id + ", count=" + this.count + ")";
    }

    public DownloadGetStatusModel(Status status, Short sh, IdFileModel idFileModel, int i) {
        this.status = status;
        this.errorCode = sh;
        this.id = idFileModel;
        this.count = i;
    }

    public /* synthetic */ DownloadGetStatusModel(Status status, Short sh, IdFileModel idFileModel, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : status, (i2 & 2) != 0 ? null : sh, (i2 & 4) != 0 ? null : idFileModel, (i2 & 8) != 0 ? 0 : i);
    }

    public final Status getStatus() {
        return this.status;
    }

    public final void setStatus(Status status) {
        this.status = status;
    }

    public final Short getErrorCode() {
        return this.errorCode;
    }

    public final void setErrorCode(Short sh) {
        this.errorCode = sh;
    }

    public final IdFileModel getId() {
        return this.id;
    }

    public final void setId(IdFileModel idFileModel) {
        this.id = idFileModel;
    }

    public final int getCount() {
        return this.count;
    }

    public final void setCount(int i) {
        this.count = i;
    }

    /* compiled from: DownloadGetStatusModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DownloadGetStatusModel$Status;", "", "idStatus", "", "(Ljava/lang/String;IS)V", "getIdStatus", "()S", "READY", "GROUP_STARTED", "FILE_STARTED", "DOWNLOADING", "FILE_COMMITTED", "GROUP_COMMITTED", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum Status {
        READY(0),
        GROUP_STARTED(1),
        FILE_STARTED(2),
        DOWNLOADING(3),
        FILE_COMMITTED(4),
        GROUP_COMMITTED(5);

        private final short idStatus;

        Status(short s) {
            this.idStatus = s;
        }

        public final short getIdStatus() {
            return this.idStatus;
        }
    }
}
