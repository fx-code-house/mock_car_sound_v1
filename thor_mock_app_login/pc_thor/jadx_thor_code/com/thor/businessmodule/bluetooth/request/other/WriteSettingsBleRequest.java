package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.model.other.DeviceSettings;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WriteSettingsBleRequest.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteSettingsBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "settings", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceSettings$Setting;", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "getSettings", "()Ljava/util/ArrayList;", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteSettingsBleRequest extends BaseBleRequest {
    private final ArrayList<DeviceSettings.Setting> settings;

    public WriteSettingsBleRequest(ArrayList<DeviceSettings.Setting> settings) {
        Intrinsics.checkNotNullParameter(settings, "settings");
        this.settings = settings;
        setCommand((short) 59);
    }

    public final ArrayList<DeviceSettings.Setting> getSettings() {
        return this.settings;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        if (this.settings.size() > 0) {
            for (byte b : BleHelper.convertShortToByteArray(Short.valueOf((short) this.settings.size()))) {
                arrayList.add(Byte.valueOf(b));
            }
            int size = this.settings.size();
            for (int i = 0; i < size; i++) {
                byte[] bArrConvertShortToByteArray = BleHelper.convertShortToByteArray(Short.valueOf(this.settings.get(i).getId()));
                byte[] bArrConvertShortToByteArray2 = BleHelper.convertShortToByteArray(Short.valueOf(this.settings.get(i).getValue()));
                for (byte b2 : bArrConvertShortToByteArray) {
                    arrayList.add(Byte.valueOf(b2));
                }
                for (byte b3 : bArrConvertShortToByteArray2) {
                    arrayList.add(Byte.valueOf(b3));
                }
            }
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
