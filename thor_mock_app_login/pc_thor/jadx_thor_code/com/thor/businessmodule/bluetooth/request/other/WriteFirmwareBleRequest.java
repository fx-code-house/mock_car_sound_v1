package com.thor.businessmodule.bluetooth.request.other;

import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.settings.Variables;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: WriteFirmwareBleRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\r\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e¨\u0006\u0016"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteFirmwareBleRequest;", "", "firmware", "", "versionHW", "", "versionFW", "([BSS)V", "blocks", "", "getBlocks", "()Ljava/util/List;", BleCommandsWorker.INPUT_DATA_COMMAND, "getCommand", "()S", "getFirmware", "()[B", "typeDevice", "getTypeDevice", "getVersionFW", "getVersionHW", "takeFirmwareBlocks", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteFirmwareBleRequest {
    private final List<byte[]> blocks;
    private final short command;
    private final byte[] firmware;
    private final short typeDevice;
    private final short versionFW;
    private final short versionHW;

    public WriteFirmwareBleRequest(byte[] firmware, short s, short s2) {
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        this.firmware = firmware;
        this.versionHW = s;
        this.versionFW = s2;
        this.command = (short) 80;
        this.typeDevice = (short) 1;
        this.blocks = takeFirmwareBlocks(firmware);
    }

    public final byte[] getFirmware() {
        return this.firmware;
    }

    public final short getVersionFW() {
        return this.versionFW;
    }

    public final short getVersionHW() {
        return this.versionHW;
    }

    public final short getCommand() {
        return this.command;
    }

    public final short getTypeDevice() {
        return this.typeDevice;
    }

    public final List<byte[]> getBlocks() {
        return this.blocks;
    }

    private final List<byte[]> takeFirmwareBlocks(byte[] firmware) {
        byte[] bArr;
        int length;
        int block_size;
        int block_size2;
        ArrayList arrayList = new ArrayList();
        byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf(this.command));
        byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf(this.typeDevice));
        byte[] bArrConvertShortToByteArray3 = BleHelper.convertShortToByteArray(Short.valueOf(this.versionHW));
        byte[] bArrConvertShortToByteArray4 = BleHelper.convertShortToByteArray(Short.valueOf(this.versionFW));
        double dCeil = Math.ceil(firmware.length / Variables.INSTANCE.getBLOCK_SIZE());
        int i = 1;
        Timber.i("Total blocks: %s, all: %s, size: %s", Double.valueOf(dCeil), Integer.valueOf(firmware.length), Integer.valueOf(Variables.INSTANCE.getBLOCK_SIZE()));
        byte[] bArrConvertShortToByteArray5 = BleHelper.convertShortToByteArray(Short.valueOf((short) dCeil));
        byte[] bArrConvertShortToByteArray6 = BleHelper.convertShortToByteArray(Short.valueOf((short) Variables.INSTANCE.getBLOCK_SIZE()));
        int length2 = firmware.length % Variables.INSTANCE.getBLOCK_SIZE();
        byte[] bArrConvertShortToByteArray7 = BleHelper.convertShortToByteArray(Short.valueOf((short) length2));
        Timber.i("Last block size: %s", Integer.valueOf(length2));
        while (true) {
            double d = i;
            if (d <= dCeil) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray2));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray3));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray4));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray5));
                arrayList2.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf((short) i))));
                if (d < dCeil) {
                    arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray6));
                    int block_size3 = Variables.INSTANCE.getBLOCK_SIZE() * i;
                    block_size2 = block_size3 - Variables.INSTANCE.getBLOCK_SIZE();
                    length = block_size3;
                    bArr = firmware;
                } else {
                    arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray7));
                    bArr = firmware;
                    length = bArr.length;
                    if (bArr.length % Variables.INSTANCE.getBLOCK_SIZE() != 0) {
                        block_size = bArr.length % Variables.INSTANCE.getBLOCK_SIZE();
                    } else {
                        block_size = Variables.INSTANCE.getBLOCK_SIZE();
                    }
                    block_size2 = length - block_size;
                }
                arrayList2.addAll(ArraysKt.toList(ArraysKt.copyOfRange(bArr, block_size2, length)));
                ArrayList arrayList3 = arrayList2;
                arrayList2.addAll(ArraysKt.toList(BleHelper.createCheckSumPartOld(CollectionsKt.toByteArray(arrayList3))));
                arrayList.add(CollectionsKt.toByteArray(arrayList3));
                i++;
            } else {
                return arrayList;
            }
        }
    }
}
