package com.welie.blessed;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: AdvertiseError.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\u0001\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\u000e"}, d2 = {"Lcom/welie/blessed/AdvertiseError;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "DATA_TOO_LARGE", "TOO_MANY_ADVERTISERS", "ALREADY_STARTED", "INTERNAL_ERROR", "FEATURE_UNSUPPORTED", "UNKNOWN_ERROR", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum AdvertiseError {
    DATA_TOO_LARGE(1),
    TOO_MANY_ADVERTISERS(2),
    ALREADY_STARTED(3),
    INTERNAL_ERROR(4),
    FEATURE_UNSUPPORTED(5),
    UNKNOWN_ERROR(-1);


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int value;

    @JvmStatic
    public static final AdvertiseError fromValue(int i) {
        return INSTANCE.fromValue(i);
    }

    AdvertiseError(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    /* compiled from: AdvertiseError.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/welie/blessed/AdvertiseError$Companion;", "", "()V", "fromValue", "Lcom/welie/blessed/AdvertiseError;", "value", "", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final AdvertiseError fromValue(int value) {
            for (AdvertiseError advertiseError : AdvertiseError.values()) {
                if (advertiseError.getValue() == value) {
                    return advertiseError;
                }
            }
            return AdvertiseError.UNKNOWN_ERROR;
        }
    }
}
