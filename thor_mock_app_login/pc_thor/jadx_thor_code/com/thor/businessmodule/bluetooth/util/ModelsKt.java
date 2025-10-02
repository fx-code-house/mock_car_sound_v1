package com.thor.businessmodule.bluetooth.util;

import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.settings.SoundPreset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: models.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0001¨\u0006\u0004"}, d2 = {"toListPresets", "", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPreset;", "Lcom/thor/businessmodule/bluetooth/model/other/settings/SoundPreset;", "businessmodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ModelsKt {
    public static final List<InstalledPreset> toListPresets(List<SoundPreset> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<SoundPreset> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (SoundPreset soundPreset : list2) {
            short soundPacketId = (short) soundPreset.getSoundPacketId();
            SoundPreset.SoundMode soundMode = soundPreset.getSoundMode();
            arrayList.add(new InstalledPreset(soundPacketId, soundMode != null ? (short) soundMode.getRawValue() : (short) 0, soundPreset.getStatus() ? (short) 1 : (short) 0));
        }
        return arrayList;
    }
}
