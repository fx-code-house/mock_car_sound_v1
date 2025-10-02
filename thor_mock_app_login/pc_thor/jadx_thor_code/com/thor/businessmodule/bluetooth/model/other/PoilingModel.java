package com.thor.businessmodule.bluetooth.model.other;

import androidx.core.app.NotificationCompat;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: PoilingModel.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\u0006\u0010\u0014\u001a\u00020\u0010J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0017"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/PoilingModel;", "", NotificationCompat.CATEGORY_STATUS, "", NotificationCompat.CATEGORY_PROGRESS, "(SS)V", "getProgress", "()S", "setProgress", "(S)V", "getStatus", "setStatus", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "isDefaultValuesSet", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class PoilingModel {

    @SerializedName(NotificationCompat.CATEGORY_PROGRESS)
    private short progress;

    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private short status;

    /* JADX WARN: Illegal instructions before constructor call */
    public PoilingModel() {
        short s = 0;
        this(s, s, 3, null);
    }

    public static /* synthetic */ PoilingModel copy$default(PoilingModel poilingModel, short s, short s2, int i, Object obj) {
        if ((i & 1) != 0) {
            s = poilingModel.status;
        }
        if ((i & 2) != 0) {
            s2 = poilingModel.progress;
        }
        return poilingModel.copy(s, s2);
    }

    /* renamed from: component1, reason: from getter */
    public final short getStatus() {
        return this.status;
    }

    /* renamed from: component2, reason: from getter */
    public final short getProgress() {
        return this.progress;
    }

    public final PoilingModel copy(short status, short progress) {
        return new PoilingModel(status, progress);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PoilingModel)) {
            return false;
        }
        PoilingModel poilingModel = (PoilingModel) other;
        return this.status == poilingModel.status && this.progress == poilingModel.progress;
    }

    public int hashCode() {
        return (Short.hashCode(this.status) * 31) + Short.hashCode(this.progress);
    }

    public String toString() {
        return "PoilingModel(status=" + ((int) this.status) + ", progress=" + ((int) this.progress) + ")";
    }

    public PoilingModel(short s, short s2) {
        this.status = s;
        this.progress = s2;
    }

    public /* synthetic */ PoilingModel(short s, short s2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (short) 0 : s, (i & 2) != 0 ? (short) 0 : s2);
    }

    public final short getStatus() {
        return this.status;
    }

    public final void setStatus(short s) {
        this.status = s;
    }

    public final short getProgress() {
        return this.progress;
    }

    public final void setProgress(short s) {
        this.progress = s;
    }

    public final boolean isDefaultValuesSet() {
        return this.status == 0 && this.progress == 0;
    }
}
