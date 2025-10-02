package com.thor.businessmodule.bluetooth.request.other;

import com.thor.businessmodule.bluetooth.model.other.InstalledPresetRules;
import com.thor.businessmodule.bluetooth.model.other.PresetRule;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WriteInstalledSoundPackageRulesRequest.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lcom/thor/businessmodule/bluetooth/request/other/WriteInstalledSoundPackageRulesRequest;", "Lcom/thor/businessmodule/bluetooth/request/other/BaseBleRequest;", "installedPresetsRules", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "presetRule", "Lcom/thor/businessmodule/bluetooth/model/other/PresetRule;", "(Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;Lcom/thor/businessmodule/bluetooth/model/other/PresetRule;)V", "getInstalledPresetsRules", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "getPresetRule", "()Lcom/thor/businessmodule/bluetooth/model/other/PresetRule;", "getBody", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class WriteInstalledSoundPackageRulesRequest extends BaseBleRequest {
    private final InstalledPresetRules installedPresetsRules;
    private final PresetRule presetRule;

    public WriteInstalledSoundPackageRulesRequest(InstalledPresetRules installedPresetsRules, PresetRule presetRule) {
        Intrinsics.checkNotNullParameter(installedPresetsRules, "installedPresetsRules");
        Intrinsics.checkNotNullParameter(presetRule, "presetRule");
        this.installedPresetsRules = installedPresetsRules;
        this.presetRule = presetRule;
        setCommand((short) 67);
    }

    public final InstalledPresetRules getInstalledPresetsRules() {
        return this.installedPresetsRules;
    }

    public final PresetRule getPresetRule() {
        return this.presetRule;
    }

    @Override // com.thor.businessmodule.bluetooth.request.other.BaseBleRequest, com.thor.businessmodule.bluetooth.request.other.BleRequest
    /* renamed from: getBody */
    public byte[] getIv() {
        ArrayList arrayList = new ArrayList();
        for (byte b : BleHelper.convertShortToByteArray(Short.valueOf(this.installedPresetsRules.getInstalledSoundPackage().getPackageId()))) {
            arrayList.add(Byte.valueOf(b));
        }
        for (byte b2 : BleHelper.convertShortToByteArray(Short.valueOf(this.installedPresetsRules.getInstalledSoundPackage().getVersionId()))) {
            arrayList.add(Byte.valueOf(b2));
        }
        for (byte b3 : BleHelper.convertShortToByteArray(Short.valueOf(this.installedPresetsRules.getInstalledSoundPackage().getMode()))) {
            arrayList.add(Byte.valueOf(b3));
        }
        for (byte b4 : BleHelper.convertShortToByteArray((short) 1)) {
            arrayList.add(Byte.valueOf(b4));
        }
        for (byte b5 : BleHelper.convertShortToByteArray(Short.valueOf(this.presetRule.getId()))) {
            arrayList.add(Byte.valueOf(b5));
        }
        for (byte b6 : BleHelper.convertShortToByteArray(Short.valueOf(this.presetRule.getValue()))) {
            arrayList.add(Byte.valueOf(b6));
        }
        return CollectionsKt.toByteArray(arrayList);
    }
}
