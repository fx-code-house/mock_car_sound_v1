package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.util.BleCommands;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.settings.Variables;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WriteBlockFileBleRequestNew.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\u0004H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteBlockFileBleRequestNew;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "data", "", "", "currentBlock", "", "(Ljava/util/List;I)V", "getCurrentBlock", "()I", "getData", "()Ljava/util/List;", "getBody", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteBlockFileBleRequestNew extends BaseBleRequest {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int currentBlock;
    private final List<byte[]> data;

    public final List<byte[]> getData() {
        return this.data;
    }

    public final int getCurrentBlock() {
        return this.currentBlock;
    }

    public WriteBlockFileBleRequestNew(List<byte[]> data, int i) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        this.currentBlock = i;
        setCommand(Short.valueOf(BleCommands.COMMAND_DOWNLOAD_WRITE_BLOCK));
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        return this.data.get(this.currentBlock);
    }

    /* compiled from: WriteBlockFileBleRequestNew.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0012\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteBlockFileBleRequestNew$Companion;", "", "()V", "takeDataBlock", "", "", "data", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<byte[]> takeDataBlock(byte[] data) {
            int length;
            int length2;
            int block_size;
            Intrinsics.checkNotNullParameter(data, "data");
            ArrayList arrayList = new ArrayList();
            double dCeil = Math.ceil(data.length / Variables.INSTANCE.getBLOCK_SIZE());
            byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf((short) Variables.INSTANCE.getBLOCK_SIZE()));
            if (data.length % Variables.INSTANCE.getBLOCK_SIZE() == 0) {
                length = Variables.INSTANCE.getBLOCK_SIZE();
            } else {
                length = data.length % Variables.INSTANCE.getBLOCK_SIZE();
            }
            byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf((short) length));
            int i = 1;
            while (true) {
                double d = i;
                if (d <= dCeil) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf((short) (i - 1)))));
                    if (d < dCeil) {
                        arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray));
                        length2 = Variables.INSTANCE.getBLOCK_SIZE() * i;
                        block_size = Variables.INSTANCE.getBLOCK_SIZE();
                    } else {
                        arrayList2.addAll(ArraysKt.toList(bArrConvertShortToByteArray2));
                        length2 = data.length;
                        if (data.length % Variables.INSTANCE.getBLOCK_SIZE() != 0) {
                            block_size = data.length % Variables.INSTANCE.getBLOCK_SIZE();
                        } else {
                            block_size = Variables.INSTANCE.getBLOCK_SIZE();
                        }
                    }
                    arrayList2.addAll(ArraysKt.toList(ArraysKt.copyOfRange(data, length2 - block_size, length2)));
                    arrayList.add(CollectionsKt.toByteArray(arrayList2));
                    i++;
                } else {
                    return arrayList;
                }
            }
        }
    }
}
