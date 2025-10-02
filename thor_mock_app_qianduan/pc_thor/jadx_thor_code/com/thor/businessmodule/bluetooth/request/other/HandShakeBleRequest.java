package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.crypto.EncryptionType;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: HandShakeBleRequest.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/HandShakeBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "iv", "", "([B)V", "getIv", "()[B", "getBody", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class HandShakeBleRequest extends BaseBleRequest {
    private final byte[] iv;

    public HandShakeBleRequest(byte[] iv) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        this.iv = iv;
        setCryptoType(EncryptionType.HANDSHAKE);
    }

    public final byte[] getIv() {
        return this.iv;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody, reason: from getter */
    public byte[] getIv() {
        return this.iv;
    }
}
