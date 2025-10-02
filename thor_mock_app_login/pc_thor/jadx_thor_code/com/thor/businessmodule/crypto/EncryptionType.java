package com.thor.businessmodule.crypto;

import kotlin.Metadata;

/* compiled from: EncryptionWithSize.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0012\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\n\u0082\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/crypto/EncryptionType;", "", "value", "Lkotlin/UByte;", "(Ljava/lang/String;IB)V", "getValue-w2LRezQ", "()B", "B", "NONE", "ENCRYPTION", "HANDSHAKE", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum EncryptionType {
    NONE((byte) 0),
    ENCRYPTION((byte) 1),
    HANDSHAKE((byte) 2);

    private final byte value;

    EncryptionType(byte b) {
        this.value = b;
    }

    /* renamed from: getValue-w2LRezQ, reason: not valid java name and from getter */
    public final byte getValue() {
        return this.value;
    }
}
