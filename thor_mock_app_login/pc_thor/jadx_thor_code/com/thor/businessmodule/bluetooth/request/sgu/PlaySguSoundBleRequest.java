package com.thor.businessmodule.bluetooth.request.sgu;

import com.thor.businessmodule.bluetooth.request.other.BaseBleRequest;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;

/* compiled from: PlaySguSoundBleRequest.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/sgu/PlaySguSoundBleRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "soundId", "", "repeatCount", "engineSoundMuteAmount", "volume", "(SSSS)V", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PlaySguSoundBleRequest extends BaseBleRequest {
    private final short engineSoundMuteAmount;
    private final short repeatCount;
    private final short soundId;
    private final short volume;

    public PlaySguSoundBleRequest(short s, short s2, short s3, short s4) {
        this.soundId = s;
        this.repeatCount = s2;
        this.engineSoundMuteAmount = s3;
        this.volume = s4;
        setCommand((short) 34);
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.soundId))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.repeatCount))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.engineSoundMuteAmount))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray(Short.valueOf(this.volume))));
        arrayList.addAll(ArraysKt.toList(BleHelper.convertShortToByteArray((short) 0)));
        return CollectionsKt.toByteArray(arrayList);
    }
}
