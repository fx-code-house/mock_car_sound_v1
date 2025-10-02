package com.thor.networkmodule.model.firmware;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FirmwareProfileList.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\n\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/firmware/FirmwareProfileList;", "", "firmwares", "", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "(Ljava/util/List;)V", "getFirmwares", "()Ljava/util/List;", "setFirmwares", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class FirmwareProfileList {

    @SerializedName("firmwares")
    private List<FirmwareProfileShort> firmwares;

    /* JADX WARN: Multi-variable type inference failed */
    public FirmwareProfileList() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirmwareProfileList copy$default(FirmwareProfileList firmwareProfileList, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = firmwareProfileList.firmwares;
        }
        return firmwareProfileList.copy(list);
    }

    public final List<FirmwareProfileShort> component1() {
        return this.firmwares;
    }

    public final FirmwareProfileList copy(List<FirmwareProfileShort> firmwares) {
        Intrinsics.checkNotNullParameter(firmwares, "firmwares");
        return new FirmwareProfileList(firmwares);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof FirmwareProfileList) && Intrinsics.areEqual(this.firmwares, ((FirmwareProfileList) other).firmwares);
    }

    public int hashCode() {
        return this.firmwares.hashCode();
    }

    public String toString() {
        return "FirmwareProfileList(firmwares=" + this.firmwares + ")";
    }

    public FirmwareProfileList(List<FirmwareProfileShort> firmwares) {
        Intrinsics.checkNotNullParameter(firmwares, "firmwares");
        this.firmwares = firmwares;
    }

    public /* synthetic */ FirmwareProfileList(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<FirmwareProfileShort> getFirmwares() {
        return this.firmwares;
    }

    public final void setFirmwares(List<FirmwareProfileShort> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.firmwares = list;
    }
}
