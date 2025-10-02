package com.welie.blessed;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Phy.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/welie/blessed/Phy;", "", "tx", "Lcom/welie/blessed/PhyType;", "rx", "(Lcom/welie/blessed/PhyType;Lcom/welie/blessed/PhyType;)V", "getRx", "()Lcom/welie/blessed/PhyType;", "getTx", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class Phy {
    private final PhyType rx;
    private final PhyType tx;

    public static /* synthetic */ Phy copy$default(Phy phy, PhyType phyType, PhyType phyType2, int i, Object obj) {
        if ((i & 1) != 0) {
            phyType = phy.tx;
        }
        if ((i & 2) != 0) {
            phyType2 = phy.rx;
        }
        return phy.copy(phyType, phyType2);
    }

    /* renamed from: component1, reason: from getter */
    public final PhyType getTx() {
        return this.tx;
    }

    /* renamed from: component2, reason: from getter */
    public final PhyType getRx() {
        return this.rx;
    }

    public final Phy copy(PhyType tx, PhyType rx) {
        Intrinsics.checkNotNullParameter(tx, "tx");
        Intrinsics.checkNotNullParameter(rx, "rx");
        return new Phy(tx, rx);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Phy)) {
            return false;
        }
        Phy phy = (Phy) other;
        return this.tx == phy.tx && this.rx == phy.rx;
    }

    public int hashCode() {
        return (this.tx.hashCode() * 31) + this.rx.hashCode();
    }

    public String toString() {
        return "Phy(tx=" + this.tx + ", rx=" + this.rx + ")";
    }

    public Phy(PhyType tx, PhyType rx) {
        Intrinsics.checkNotNullParameter(tx, "tx");
        Intrinsics.checkNotNullParameter(rx, "rx");
        this.tx = tx;
        this.rx = rx;
    }

    public final PhyType getRx() {
        return this.rx;
    }

    public final PhyType getTx() {
        return this.tx;
    }
}
