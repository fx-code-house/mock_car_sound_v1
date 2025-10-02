package com.thor.businessmodule.bluetooth.request.other;

import com.thor.app.databinding.viewmodels.workers.BleCommandsWorker;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.settings.Variables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: UploadSoundPackageFileBleRequest.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\u0006\u0010\u0014\u001a\u00020\u0007H\u0002R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u0015"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/UploadSoundPackageFileBleRequest;", "", BleCommandsWorker.INPUT_DATA_COMMAND, "", "packageId", "versionId", "file", "", "(SSS[B)V", "blocks", "", "getBlocks", "()Ljava/util/List;", "getCommand", "()S", "getFile", "()[B", "getPackageId", "getVersionId", "takeDataBlocks", "data", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadSoundPackageFileBleRequest {
    private final List<byte[]> blocks;
    private final short command;
    private final byte[] file;
    private final short packageId;
    private final short versionId;

    public UploadSoundPackageFileBleRequest(short s, short s2, short s3, byte[] file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.command = s;
        this.packageId = s2;
        this.versionId = s3;
        this.file = file;
        this.blocks = takeDataBlocks(file);
    }

    public final short getCommand() {
        return this.command;
    }

    public final short getPackageId() {
        return this.packageId;
    }

    public final short getVersionId() {
        return this.versionId;
    }

    public final byte[] getFile() {
        return this.file;
    }

    public final List<byte[]> getBlocks() {
        return this.blocks;
    }

    private final List<byte[]> takeDataBlocks(byte[] data) {
        int length;
        int block_size;
        int block_size2;
        int block_size3;
        UploadSoundPackageFileBleRequest uploadSoundPackageFileBleRequest = this;
        ArrayList arrayList = new ArrayList();
        byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf(uploadSoundPackageFileBleRequest.command));
        byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf(uploadSoundPackageFileBleRequest.packageId));
        byte[] bArrConvertShortToByteArray3 = BleHelper.convertShortToByteArray(Short.valueOf(uploadSoundPackageFileBleRequest.versionId));
        double dCeil = Math.ceil(uploadSoundPackageFileBleRequest.file.length / Variables.INSTANCE.getBLOCK_SIZE());
        Timber.i("Total blocks: %s, all: %s, size: %s", Double.valueOf(dCeil), Integer.valueOf(uploadSoundPackageFileBleRequest.file.length), Integer.valueOf(Variables.INSTANCE.getBLOCK_SIZE()));
        byte[] bArrConvertShortToByteArray4 = BleHelper.convertShortToByteArray(Short.valueOf((short) dCeil));
        byte[] bArrConvertShortToByteArray5 = BleHelper.convertShortToByteArray(Short.valueOf((short) Variables.INSTANCE.getBLOCK_SIZE()));
        if (uploadSoundPackageFileBleRequest.file.length % Variables.INSTANCE.getBLOCK_SIZE() == 0) {
            length = Variables.INSTANCE.getBLOCK_SIZE();
        } else {
            length = uploadSoundPackageFileBleRequest.file.length % Variables.INSTANCE.getBLOCK_SIZE();
        }
        byte[] bArrConvertShortToByteArray6 = BleHelper.convertShortToByteArray(Short.valueOf((short) length));
        Timber.i("Last block size: %s", Integer.valueOf(length));
        int i = 1;
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
                    block_size3 = Variables.INSTANCE.getBLOCK_SIZE() * i;
                    block_size2 = block_size3 - Variables.INSTANCE.getBLOCK_SIZE();
                } else {
                    arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray6));
                    byte[] bArr = uploadSoundPackageFileBleRequest.file;
                    int length2 = bArr.length;
                    if (bArr.length % Variables.INSTANCE.getBLOCK_SIZE() != 0) {
                        block_size = uploadSoundPackageFileBleRequest.file.length % Variables.INSTANCE.getBLOCK_SIZE();
                    } else {
                        block_size = Variables.INSTANCE.getBLOCK_SIZE();
                    }
                    block_size2 = length2 - block_size;
                    block_size3 = length2;
                }
                byte[] bArrCopyOfRange = ArraysKt.copyOfRange(uploadSoundPackageFileBleRequest.file, block_size2, block_size3);
                Timber.i("Start: %s, end: %s, size: %s", Integer.valueOf(block_size2), Integer.valueOf(block_size3), Integer.valueOf(bArrCopyOfRange.length));
                arrayList2.addAll(ArraysKt.toList(bArrCopyOfRange));
                String string = Arrays.toString(bArrCopyOfRange);
                Intrinsics.checkNotNullExpressionValue(string, "toString(this)");
                Timber.i("Block body: %s", string);
                ArrayList arrayList3 = arrayList2;
                arrayList2.addAll(ArraysKt.toList(BleHelper.createCheckSumPart(CollectionsKt.toByteArray(arrayList3))));
                arrayList.add(CollectionsKt.toByteArray(arrayList3));
                i++;
                uploadSoundPackageFileBleRequest = this;
            } else {
                return arrayList;
            }
        }
    }
}
