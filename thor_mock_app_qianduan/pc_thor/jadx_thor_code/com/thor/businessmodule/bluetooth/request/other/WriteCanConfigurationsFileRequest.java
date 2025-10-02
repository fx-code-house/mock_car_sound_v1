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

/* compiled from: WriteCanConfigurationsFileRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\r¨\u0006\u0014"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteCanConfigurationsFileRequest;", "", "canFile", "", "canConfigurationsId", "", "canConfigurationsVersionId", "([BSS)V", "blocks", "", "getBlocks", "()Ljava/util/List;", "getCanConfigurationsId", "()S", "getCanConfigurationsVersionId", "getCanFile", "()[B", BleCommandsWorker.INPUT_DATA_COMMAND, "getCommand", "takeCanConfigurationsBlocks", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteCanConfigurationsFileRequest {
    private final List<byte[]> blocks;
    private final short canConfigurationsId;
    private final short canConfigurationsVersionId;
    private final byte[] canFile;
    private final short command;

    public WriteCanConfigurationsFileRequest(byte[] canFile, short s, short s2) {
        Intrinsics.checkNotNullParameter(canFile, "canFile");
        this.canFile = canFile;
        this.canConfigurationsId = s;
        this.canConfigurationsVersionId = s2;
        this.command = (short) 82;
        this.blocks = takeCanConfigurationsBlocks(canFile);
    }

    public final byte[] getCanFile() {
        return this.canFile;
    }

    public final short getCanConfigurationsId() {
        return this.canConfigurationsId;
    }

    public final short getCanConfigurationsVersionId() {
        return this.canConfigurationsVersionId;
    }

    public final short getCommand() {
        return this.command;
    }

    public final List<byte[]> getBlocks() {
        return this.blocks;
    }

    private final List<byte[]> takeCanConfigurationsBlocks(byte[] canFile) {
        int length;
        int block_size;
        ArrayList arrayList = new ArrayList();
        byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf(this.command));
        byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf(this.canConfigurationsId));
        byte[] bArrConvertShortToByteArray3 = BleHelper.convertShortToByteArray(Short.valueOf(this.canConfigurationsVersionId));
        double dCeil = Math.ceil(canFile.length / Variables.INSTANCE.getBLOCK_SIZE());
        int i = 1;
        Timber.i("Total blocks: %s, all: %s, size: %s", Double.valueOf(dCeil), Integer.valueOf(canFile.length), Integer.valueOf(Variables.INSTANCE.getBLOCK_SIZE()));
        byte[] bArrConvertShortToByteArray4 = BleHelper.convertShortToByteArray(Short.valueOf((short) dCeil));
        byte[] bArrConvertShortToByteArray5 = BleHelper.convertShortToByteArray(Short.valueOf((short) Variables.INSTANCE.getBLOCK_SIZE()));
        int length2 = canFile.length % Variables.INSTANCE.getBLOCK_SIZE();
        byte[] bArrConvertShortToByteArray6 = BleHelper.convertShortToByteArray(Short.valueOf((short) length2));
        Timber.i("Last block size: %s", Integer.valueOf(length2));
        while (true) {
            double d = i;
            if (d <= dCeil) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray2));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray3));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray4));
                arrayList2.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf((short) i))));
                if (d < dCeil) {
                    arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray5));
                    length = Variables.INSTANCE.getBLOCK_SIZE() * i;
                    block_size = Variables.INSTANCE.getBLOCK_SIZE();
                } else {
                    if (length2 == 0) {
                        arrayList2.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf((short) Variables.INSTANCE.getBLOCK_SIZE()))));
                    } else {
                        arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray6));
                    }
                    length = canFile.length;
                    if (canFile.length % Variables.INSTANCE.getBLOCK_SIZE() != 0) {
                        block_size = canFile.length % Variables.INSTANCE.getBLOCK_SIZE();
                    } else {
                        block_size = Variables.INSTANCE.getBLOCK_SIZE();
                    }
                }
                arrayList2.addAll(ArraysKt.toList(ArraysKt.copyOfRange(canFile, length - block_size, length)));
                ArrayList arrayList3 = arrayList2;
                arrayList2.addAll(ArraysKt.toList(BleHelper.createCheckSumPart(CollectionsKt.toByteArray(arrayList3))));
                arrayList.add(CollectionsKt.toByteArray(arrayList3));
                i++;
            } else {
                return arrayList;
            }
        }
    }
}
