package com.thor.businessmodule.crypto;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EncryptionWithSize.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u00062\u00060\u0001j\u0002`\u0002:\u0001\u0006B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/crypto/EncryptionError;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "message", "", "(Ljava/lang/String;)V", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class EncryptionError extends Exception {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final EncryptionError messageSizeOutOfRange = new EncryptionError("Message size out of range");
    private static final EncryptionError encryptionTypeError = new EncryptionError("Encryption type error");

    /* compiled from: EncryptionWithSize.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/crypto/EncryptionError$Companion;", "", "()V", "encryptionTypeError", "Lcom/thor/businessmodule/crypto/EncryptionError;", "getEncryptionTypeError", "()Lcom/thor/businessmodule/crypto/EncryptionError;", "messageSizeOutOfRange", "getMessageSizeOutOfRange", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final EncryptionError getMessageSizeOutOfRange() {
            return EncryptionError.messageSizeOutOfRange;
        }

        public final EncryptionError getEncryptionTypeError() {
            return EncryptionError.encryptionTypeError;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EncryptionError(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
    }
}
