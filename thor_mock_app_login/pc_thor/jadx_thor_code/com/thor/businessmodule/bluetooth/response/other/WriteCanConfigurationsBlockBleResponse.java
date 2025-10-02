package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: WriteCanConfigurationsBlockBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/WriteCanConfigurationsBlockBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", "bytes", "", "([B)V", "blockNumber", "", "getBlockNumber", "()S", "setBlockNumber", "(S)V", "canConfigurationsId", "getCanConfigurationsId", "setCanConfigurationsId", "canConfigurationsVersionId", "getCanConfigurationsVersionId", "setCanConfigurationsVersionId", "totalBlocks", "getTotalBlocks", "setTotalBlocks", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteCanConfigurationsBlockBleResponse extends BaseBleResponse {
    private short blockNumber;
    private short canConfigurationsId;
    private short canConfigurationsVersionId;
    private short totalBlocks;

    public WriteCanConfigurationsBlockBleResponse(byte[] bArr) {
        super(bArr);
        setCommand((short) 82);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final short getCanConfigurationsId() {
        return this.canConfigurationsId;
    }

    public final void setCanConfigurationsId(short s) {
        this.canConfigurationsId = s;
    }

    public final short getCanConfigurationsVersionId() {
        return this.canConfigurationsVersionId;
    }

    public final void setCanConfigurationsVersionId(short s) {
        this.canConfigurationsVersionId = s;
    }

    public final short getTotalBlocks() {
        return this.totalBlocks;
    }

    public final void setTotalBlocks(short s) {
        this.totalBlocks = s;
    }

    public final short getBlockNumber() {
        return this.blockNumber;
    }

    public final void setBlockNumber(short s) {
        this.blockNumber = s;
    }

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponse
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.canConfigurationsId = BleHelper.takeShort(bytes[2], bytes[3]);
            this.canConfigurationsVersionId = BleHelper.takeShort(bytes[4], bytes[5]);
            this.totalBlocks = BleHelper.takeShort(bytes[6], bytes[7]);
            this.blockNumber = BleHelper.takeShort(bytes[8], bytes[9]);
        }
    }
}
