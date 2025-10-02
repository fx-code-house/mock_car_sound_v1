package com.thor.businessmodule.bluetooth.model.other;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;

/* compiled from: CanInfo.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/CanInfo;", "", TtmlNode.ATTR_ID, "", "version", "(SS)V", "getId", "()S", "setId", "(S)V", "getVersion", "setVersion", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class CanInfo {

    @SerializedName("can_id")
    private short id;

    @SerializedName("can_version_id")
    private short version;

    public static /* synthetic */ CanInfo copy$default(CanInfo canInfo, short s, short s2, int i, Object obj) {
        if ((i & 1) != 0) {
            s = canInfo.id;
        }
        if ((i & 2) != 0) {
            s2 = canInfo.version;
        }
        return canInfo.copy(s, s2);
    }

    /* renamed from: component1, reason: from getter */
    public final short getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final short getVersion() {
        return this.version;
    }

    public final CanInfo copy(short id, short version) {
        return new CanInfo(id, version);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CanInfo)) {
            return false;
        }
        CanInfo canInfo = (CanInfo) other;
        return this.id == canInfo.id && this.version == canInfo.version;
    }

    public int hashCode() {
        return (Short.hashCode(this.id) * 31) + Short.hashCode(this.version);
    }

    public String toString() {
        return "CanInfo(id=" + ((int) this.id) + ", version=" + ((int) this.version) + ")";
    }

    public CanInfo(short s, short s2) {
        this.id = s;
        this.version = s2;
    }

    public final short getId() {
        return this.id;
    }

    public final void setId(short s) {
        this.id = s;
    }

    public final short getVersion() {
        return this.version;
    }

    public final void setVersion(short s) {
        this.version = s;
    }
}
