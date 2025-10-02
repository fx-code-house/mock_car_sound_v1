package com.welie.blessed;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ScanFailure.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\f\b\u0086\u0001\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000eB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000f"}, d2 = {"Lcom/welie/blessed/ScanFailure;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "ALREADY_STARTED", "APPLICATION_REGISTRATION_FAILED", "INTERNAL_ERROR", "FEATURE_UNSUPPORTED", "OUT_OF_HARDWARE_RESOURCES", "SCANNING_TOO_FREQUENTLY", "UNKNOWN", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum ScanFailure {
    ALREADY_STARTED(1),
    APPLICATION_REGISTRATION_FAILED(2),
    INTERNAL_ERROR(3),
    FEATURE_UNSUPPORTED(4),
    OUT_OF_HARDWARE_RESOURCES(5),
    SCANNING_TOO_FREQUENTLY(6),
    UNKNOWN(-1);


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int value;

    ScanFailure(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    /* compiled from: ScanFailure.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/welie/blessed/ScanFailure$Companion;", "", "()V", "fromValue", "Lcom/welie/blessed/ScanFailure;", "value", "", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ScanFailure fromValue(int value) {
            for (ScanFailure scanFailure : ScanFailure.values()) {
                if (scanFailure.getValue() == value) {
                    return scanFailure;
                }
            }
            return ScanFailure.UNKNOWN;
        }
    }
}
