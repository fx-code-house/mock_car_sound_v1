package com.welie.blessed;

import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.mapstruct.ap.shaded.freemarker.core.FMParserConstants;

/* compiled from: GattStatus.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b/\b\u0086\u0001\u0018\u0000 12\b\u0012\u0004\u0012\u00020\u00000\u0001:\u00011B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0¨\u00062"}, d2 = {"Lcom/welie/blessed/GattStatus;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "SUCCESS", "INVALID_HANDLE", "READ_NOT_PERMITTED", "WRITE_NOT_PERMITTED", "INVALID_PDU", "INSUFFICIENT_AUTHENTICATION", "REQUEST_NOT_SUPPORTED", "INVALID_OFFSET", "INSUFFICIENT_AUTHORIZATION", "PREPARE_QUEUE_FULL", "ATTRIBUTE_NOT_FOUND", "ATTRIBUTE_NOT_LONG", "INSUFFICIENT_ENCRYPTION_KEY_SIZE", "INVALID_ATTRIBUTE_VALUE_LENGTH", "UNLIKELY_ERROR", "INSUFFICIENT_ENCRYPTION", "UNSUPPORTED_GROUP_TYPE", "INSUFFICIENT_RESOURCES", "DATABASE_OUT_OF_SYNC", "VALUE_NOT_ALLOWED", "NO_RESOURCES", "INTERNAL_ERROR", "WRONG_STATE", "DB_FULL", "BUSY", "ERROR", "CMD_STARTED", "ILLEGAL_PARAMETER", "PENDING", "AUTHORIZATION_FAILED", "MORE", "INVALID_CFG", "SERVICE_STARTED", "ENCRYPTED_NO_MITM", "NOT_ENCRYPTED", "CONNECTION_CONGESTED", "CCCD_CFG_ERROR", "PROCEDURE_IN_PROGRESS", "VALUE_OUT_OF_RANGE", "CONNECTION_CANCELLED", "FAILURE", "UNKNOWN_STATUS_CODE", "Companion", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum GattStatus {
    SUCCESS(0),
    INVALID_HANDLE(1),
    READ_NOT_PERMITTED(2),
    WRITE_NOT_PERMITTED(3),
    INVALID_PDU(4),
    INSUFFICIENT_AUTHENTICATION(5),
    REQUEST_NOT_SUPPORTED(6),
    INVALID_OFFSET(7),
    INSUFFICIENT_AUTHORIZATION(8),
    PREPARE_QUEUE_FULL(9),
    ATTRIBUTE_NOT_FOUND(10),
    ATTRIBUTE_NOT_LONG(11),
    INSUFFICIENT_ENCRYPTION_KEY_SIZE(12),
    INVALID_ATTRIBUTE_VALUE_LENGTH(13),
    UNLIKELY_ERROR(14),
    INSUFFICIENT_ENCRYPTION(15),
    UNSUPPORTED_GROUP_TYPE(16),
    INSUFFICIENT_RESOURCES(17),
    DATABASE_OUT_OF_SYNC(18),
    VALUE_NOT_ALLOWED(19),
    NO_RESOURCES(128),
    INTERNAL_ERROR(129),
    WRONG_STATE(130),
    DB_FULL(FMParserConstants.TERMINATING_EXCLAM),
    BUSY(FMParserConstants.TERSE_COMMENT_END),
    ERROR(133),
    CMD_STARTED(134),
    ILLEGAL_PARAMETER(135),
    PENDING(136),
    AUTHORIZATION_FAILED(137),
    MORE(TsExtractor.TS_STREAM_TYPE_DTS),
    INVALID_CFG(139),
    SERVICE_STARTED(140),
    ENCRYPTED_NO_MITM(141),
    NOT_ENCRYPTED(142),
    CONNECTION_CONGESTED(143),
    CCCD_CFG_ERROR(253),
    PROCEDURE_IN_PROGRESS(254),
    VALUE_OUT_OF_RANGE(255),
    CONNECTION_CANCELLED(256),
    FAILURE(257),
    UNKNOWN_STATUS_CODE(65535);


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int value;

    @JvmStatic
    public static final GattStatus fromValue(int i) {
        return INSTANCE.fromValue(i);
    }

    GattStatus(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    /* compiled from: GattStatus.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/welie/blessed/GattStatus$Companion;", "", "()V", "fromValue", "Lcom/welie/blessed/GattStatus;", "value", "", "blessed_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final GattStatus fromValue(int value) {
            for (GattStatus gattStatus : GattStatus.values()) {
                if (gattStatus.getValue() == value) {
                    return gattStatus;
                }
            }
            return GattStatus.UNKNOWN_STATUS_CODE;
        }
    }
}
