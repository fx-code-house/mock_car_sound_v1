package com.thor.businessmodule.bluetooth.request.sgu;

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

/* compiled from: WriteSguSoundBleRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0005X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/sgu/WriteSguSoundBleRequest;", "", "file", "", "soundId", "", "([BS)V", "blocks", "", "getBlocks", "()Ljava/util/List;", BleCommandsWorker.INPUT_DATA_COMMAND, "getCommand", "()S", "parseBlocks", "wavFile", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteSguSoundBleRequest {
    private final List<byte[]> blocks;
    private final short command;

    public WriteSguSoundBleRequest(byte[] file, short s) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.command = (short) 39;
        this.blocks = parseBlocks(file, s);
    }

    public final short getCommand() {
        return this.command;
    }

    public final List<byte[]> getBlocks() {
        return this.blocks;
    }

    private final List<byte[]> parseBlocks(byte[] wavFile, short soundId) {
        int length;
        int block_size;
        ArrayList arrayList = new ArrayList();
        byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf(this.command));
        byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf(soundId));
        double dCeil = Math.ceil(wavFile.length / Variables.INSTANCE.getBLOCK_SIZE());
        int i = 1;
        Timber.i("Total blocks: %s, all: %s, size: %s", Double.valueOf(dCeil), Integer.valueOf(wavFile.length), Integer.valueOf(Variables.INSTANCE.getBLOCK_SIZE()));
        byte[] bArrConvertShortToByteArray3 = BleHelper.convertShortToByteArray(Short.valueOf((short) dCeil));
        byte[] bArrConvertShortToByteArray4 = BleHelper.convertShortToByteArray(Short.valueOf((short) Variables.INSTANCE.getBLOCK_SIZE()));
        int length2 = wavFile.length % Variables.INSTANCE.getBLOCK_SIZE();
        byte[] bArrConvertShortToByteArray5 = BleHelper.convertShortToByteArray(Short.valueOf((short) length2));
        Timber.i("Last block size: %s", Integer.valueOf(length2));
        while (true) {
            double d = i;
            if (d <= dCeil) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray2));
                arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray3));
                arrayList2.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf((short) i))));
                if (d < dCeil) {
                    arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray4));
                    length = Variables.INSTANCE.getBLOCK_SIZE() * i;
                    block_size = Variables.INSTANCE.getBLOCK_SIZE();
                } else {
                    arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray5));
                    length = wavFile.length;
                    if (wavFile.length % Variables.INSTANCE.getBLOCK_SIZE() != 0) {
                        block_size = wavFile.length % Variables.INSTANCE.getBLOCK_SIZE();
                    } else {
                        block_size = Variables.INSTANCE.getBLOCK_SIZE();
                    }
                }
                arrayList2.addAll(ArraysKt.toList(ArraysKt.copyOfRange(wavFile, length - block_size, length)));
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
