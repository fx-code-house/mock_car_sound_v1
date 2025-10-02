package com.thor.businessmodule.bluetooth.request.sgu;

import com.thor.businessmodule.bluetooth.request.other.BaseBleRequest;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WriteSguSoundSequenceBleRequest.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\n\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\tH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/sgu/WriteSguSoundSequenceBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "soundIds", "", "", "(Ljava/util/List;)V", "getSoundIds", "()Ljava/util/List;", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteSguSoundSequenceBleRequest extends BaseBleRequest {
    private final List<Short> soundIds;

    public WriteSguSoundSequenceBleRequest(List<Short> soundIds) {
        Intrinsics.checkNotNullParameter(soundIds, "soundIds");
        this.soundIds = soundIds;
        setCommand((short) 37);
    }

    public final List<Short> getSoundIds() {
        return this.soundIds;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        for (byte b : BleHelper.convertShortToByteArray(Short.valueOf((short) this.soundIds.size()))) {
            arrayList.add(Byte.valueOf(b));
        }
        Iterator<T> it = this.soundIds.iterator();
        while (it.hasNext()) {
            for (byte b2 : BleHelper.convertShortToByteArray(Short.valueOf(((Number) it.next()).shortValue()))) {
                arrayList.add(Byte.valueOf(b2));
            }
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
