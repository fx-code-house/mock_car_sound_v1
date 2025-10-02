package com.thor.businessmodule.bluetooth.response.other;

import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import kotlin.Metadata;

/* compiled from: WriteSoundPackageFileBlockBleResponse.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000bR\u001a\u0010\u0012\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/thor/businessmodule/bluetooth/response/other/WriteSoundPackageFileBlockBleResponse;", "Lcom/thor/businessmodule/bluetooth/response/other/BaseBleResponse;", BleCommandsWorker.INPUT_DATA_COMMAND, "", "bytes", "", "(S[B)V", "blockNumber", "getBlockNumber", "()S", "setBlockNumber", "(S)V", "packageId", "getPackageId", "setPackageId", "totalBlocks", "getTotalBlocks", "setTotalBlocks", "versionId", "getVersionId", "setVersionId", "doHandleResponse", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteSoundPackageFileBlockBleResponse extends BaseBleResponse {
    private short blockNumber;
    private short packageId;
    private short totalBlocks;
    private short versionId;

    public WriteSoundPackageFileBlockBleResponse(short s, byte[] bArr) {
        super(bArr);
        setCommand(s);
        setStatus(checkStatusResponse());
        if (getStatus()) {
            doHandleResponse();
        }
    }

    public final short getPackageId() {
        return this.packageId;
    }

    public final void setPackageId(short s) {
        this.packageId = s;
    }

    public final short getVersionId() {
        return this.versionId;
    }

    public final void setVersionId(short s) {
        this.versionId = s;
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
        try {
            byte[] bytes = getBytes();
            if (bytes != null) {
                this.packageId = BleHelper.takeShort(bytes[2], bytes[3]);
                this.versionId = BleHelper.takeShort(bytes[4], bytes[5]);
                this.totalBlocks = BleHelper.takeShort(bytes[8], bytes[9]);
                this.blockNumber = BleHelper.takeShort(bytes[10], bytes[11]);
            }
        } catch (Exception unused) {
            doHandleError();
        }
    }
}
