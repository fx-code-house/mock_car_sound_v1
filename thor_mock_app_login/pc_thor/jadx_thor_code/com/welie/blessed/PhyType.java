package com.welie.blessed;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: PhyType.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0001\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000e"}, d2 = {"Lcom/welie/blessed/PhyType;", "", "value", "", "mask", "(Ljava/lang/String;III)V", "getMask", "()I", "getValue", "LE_1M", "LE_2M", "LE_CODED", "UNKNOWN_PHY_TYPE", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum PhyType {
    LE_1M(1, 1),
    LE_2M(2, 2),
    LE_CODED(3, 4),
    UNKNOWN_PHY_TYPE(-1, -1);


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int mask;
    private final int value;

    PhyType(int i, int i2) {
        this.value = i;
        this.mask = i2;
    }

    public final int getMask() {
        return this.mask;
    }

    public final int getValue() {
        return this.value;
    }

    /* compiled from: PhyType.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/welie/blessed/PhyType$Companion;", "", "()V", "fromValue", "Lcom/welie/blessed/PhyType;", "value", "", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PhyType fromValue(int value) {
            for (PhyType phyType : PhyType.values()) {
                if (phyType.getValue() == value) {
                    return phyType;
                }
            }
            return PhyType.UNKNOWN_PHY_TYPE;
        }
    }
}
