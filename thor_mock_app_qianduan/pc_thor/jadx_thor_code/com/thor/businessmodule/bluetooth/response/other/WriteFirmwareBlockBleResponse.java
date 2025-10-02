package com.thor.businessmodule.bluetooth.response.other;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: WriteFirmwareBlockBleResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u001a\u0010\u0014\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/WriteFirmwareBlockBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponseOld;", "bytes", "", "([B)V", "blockNumber", "", "getBlockNumber", "()S", "setBlockNumber", "(S)V", "deviceId", "getDeviceId", "setDeviceId", "totalBlocks", "getTotalBlocks", "setTotalBlocks", "versionFW", "getVersionFW", "setVersionFW", "versionHW", "getVersionHW", "setVersionHW", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteFirmwareBlockBleResponse extends BaseBleResponseOld {
    private short blockNumber;
    private short deviceId;
    private short totalBlocks;
    private short versionFW;
    private short versionHW;

    public WriteFirmwareBlockBleResponse(byte[] bArr) {
        super(bArr);
        setCommand((short) 80);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final short getDeviceId() {
        return this.deviceId;
    }

    public final void setDeviceId(short s) {
        this.deviceId = s;
    }

    public final short getVersionHW() {
        return this.versionHW;
    }

    public final void setVersionHW(short s) {
        this.versionHW = s;
    }

    public final short getVersionFW() {
        return this.versionFW;
    }

    public final void setVersionFW(short s) {
        this.versionFW = s;
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

    @Override // com.thor.businessmodule.bluetooth.response.other.BaseBleResponseOld
    public void doHandleResponse() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            this.deviceId = BleHelper.takeShort(bytes[2], bytes[3]);
            this.versionHW = BleHelper.takeShort(bytes[4], bytes[5]);
            this.versionFW = BleHelper.takeShort(bytes[6], bytes[7]);
            this.totalBlocks = BleHelper.takeShort(bytes[8], bytes[9]);
            this.blockNumber = BleHelper.takeShort(bytes[10], bytes[11]);
        }
    }
}
