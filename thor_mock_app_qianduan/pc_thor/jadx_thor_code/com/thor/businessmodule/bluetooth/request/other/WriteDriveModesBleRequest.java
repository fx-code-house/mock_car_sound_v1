package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: WriteDriveModesBleRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteDriveModesBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "driveModes", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode;", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "getDriveModes", "()Ljava/util/ArrayList;", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteDriveModesBleRequest extends BaseBleRequest {
    private final ArrayList<DriveMode> driveModes;

    public WriteDriveModesBleRequest(ArrayList<DriveMode> driveModes) {
        Intrinsics.checkNotNullParameter(driveModes, "driveModes");
        this.driveModes = driveModes;
        setCommand((short) 53);
    }

    public final ArrayList<DriveMode> getDriveModes() {
        return this.driveModes;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        if (this.driveModes.size() > 0) {
            String value = this.driveModes.get(0).getModeValue().getValue();
            int size = this.driveModes.size();
            int i = 1;
            for (int i2 = 1; i2 < size; i2++) {
                value = value + this.driveModes.get(i2).getModeValue().getValue();
            }
            int size2 = 16 - this.driveModes.size();
            if (1 <= size2) {
                while (true) {
                    value = value + "00";
                    if (i == size2) {
                        break;
                    }
                    i++;
                }
            }
            for (byte b : BleHelper.convertIntToByteArray(Integer.valueOf(Integer.parseUnsignedInt(value, 2)))) {
                Timber.i("Byte: " + Integer.toBinaryString(b), new Object[0]);
                arrayList.add(Byte.valueOf(b));
            }
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
