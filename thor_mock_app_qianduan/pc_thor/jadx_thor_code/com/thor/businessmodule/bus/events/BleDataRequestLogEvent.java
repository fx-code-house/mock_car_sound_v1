package com.thor.businessmodule.bus.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BleDataRequestLogEvent.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "", "dataCrypto", "", "dataDeCrypto", "(Ljava/lang/String;Ljava/lang/String;)V", "getDataCrypto", "()Ljava/lang/String;", "getDataDeCrypto", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class BleDataRequestLogEvent {
    private final String dataCrypto;
    private final String dataDeCrypto;

    public static /* synthetic */ BleDataRequestLogEvent copy$default(BleDataRequestLogEvent bleDataRequestLogEvent, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = bleDataRequestLogEvent.dataCrypto;
        }
        if ((i & 2) != 0) {
            str2 = bleDataRequestLogEvent.dataDeCrypto;
        }
        return bleDataRequestLogEvent.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getDataCrypto() {
        return this.dataCrypto;
    }

    /* renamed from: component2, reason: from getter */
    public final String getDataDeCrypto() {
        return this.dataDeCrypto;
    }

    public final BleDataRequestLogEvent copy(String dataCrypto, String dataDeCrypto) {
        Intrinsics.checkNotNullParameter(dataCrypto, "dataCrypto");
        Intrinsics.checkNotNullParameter(dataDeCrypto, "dataDeCrypto");
        return new BleDataRequestLogEvent(dataCrypto, dataDeCrypto);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BleDataRequestLogEvent)) {
            return false;
        }
        BleDataRequestLogEvent bleDataRequestLogEvent = (BleDataRequestLogEvent) other;
        return Intrinsics.areEqual(this.dataCrypto, bleDataRequestLogEvent.dataCrypto) && Intrinsics.areEqual(this.dataDeCrypto, bleDataRequestLogEvent.dataDeCrypto);
    }

    public int hashCode() {
        return (this.dataCrypto.hashCode() * 31) + this.dataDeCrypto.hashCode();
    }

    public String toString() {
        return "BleDataRequestLogEvent(dataCrypto=" + this.dataCrypto + ", dataDeCrypto=" + this.dataDeCrypto + ")";
    }

    public BleDataRequestLogEvent(String dataCrypto, String dataDeCrypto) {
        Intrinsics.checkNotNullParameter(dataCrypto, "dataCrypto");
        Intrinsics.checkNotNullParameter(dataDeCrypto, "dataDeCrypto");
        this.dataCrypto = dataCrypto;
        this.dataDeCrypto = dataDeCrypto;
    }

    public final String getDataCrypto() {
        return this.dataCrypto;
    }

    public final String getDataDeCrypto() {
        return this.dataDeCrypto;
    }
}
