package com.thor.businessmodule.bluetooth.model.other;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceParameters.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB\u001f\b\u0016\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006B\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0003j\b\u0012\u0004\u0012\u00020\u0012`\u0005J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\tJ\u0006\u0010\u0015\u001a\u00020\tJ\u0006\u0010\u0016\u001a\u00020\tJ\u0006\u0010\u0017\u001a\u00020\tJ\u0006\u0010\u0018\u001a\u00020\tR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\n\"\u0004\b\u000b\u0010\fR.\u0010\r\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0006¨\u0006\u001b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters;", "", "params", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters$Parameter;", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "()V", "isFirmawareDifferent", "", "()Z", "setFirmawareDifferent", "(Z)V", "parameters", "getParameters", "()Ljava/util/ArrayList;", "setParameters", "getEmergencySituationsParams", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters$PARAMS;", "getPriorityEmergencySituation", "isRequaredCanFileReload", "isDeviceHasCanFileData", "isDeviceLocked", "isNeedCanFileReload", "isNeedReloadSoundPackages", "PARAMS", "Parameter", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DeviceParameters {
    private boolean isFirmawareDifferent;
    private ArrayList<Parameter> parameters;

    public DeviceParameters() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeviceParameters(ArrayList<Parameter> params) {
        this();
        Intrinsics.checkNotNullParameter(params, "params");
        this.parameters = params;
    }

    public final ArrayList<Parameter> getParameters() {
        return this.parameters;
    }

    public final void setParameters(ArrayList<Parameter> arrayList) {
        this.parameters = arrayList;
    }

    /* renamed from: isFirmawareDifferent, reason: from getter */
    public final boolean getIsFirmawareDifferent() {
        return this.isFirmawareDifferent;
    }

    public final void setFirmawareDifferent(boolean z) {
        this.isFirmawareDifferent = z;
    }

    public final ArrayList<PARAMS> getEmergencySituationsParams() {
        short value;
        ArrayList<PARAMS> arrayList = new ArrayList<>();
        ArrayList<Parameter> arrayList2 = this.parameters;
        if (arrayList2 != null) {
            value = 0;
            for (Parameter parameter : arrayList2) {
                if (parameter.getId() == 1) {
                    value = parameter.getValue();
                }
            }
        } else {
            value = 0;
        }
        for (PARAMS params : PARAMS.values()) {
            if (((short) (params.getValue() & value)) == params.getValue()) {
                arrayList.add(params);
            }
        }
        return arrayList;
    }

    public final PARAMS getPriorityEmergencySituation(boolean isRequaredCanFileReload) {
        ArrayList<PARAMS> emergencySituationsParams = getEmergencySituationsParams();
        if (this.isFirmawareDifferent) {
            emergencySituationsParams.add(PARAMS.NEED_UPDATE_FIRMWARE);
        }
        if (isRequaredCanFileReload && !isNeedCanFileReload()) {
            emergencySituationsParams.add(PARAMS.NEED_RELOAD_CAN_PARAMETERS);
        }
        if (emergencySituationsParams.size() <= 0) {
            return null;
        }
        if (emergencySituationsParams.contains(PARAMS.RECOMMENDED_TO_RETURN_TO_FACTORY_SETTINGS)) {
            return PARAMS.RECOMMENDED_TO_RETURN_TO_FACTORY_SETTINGS;
        }
        if (emergencySituationsParams.contains(PARAMS.NEED_UPDATE_FIRMWARE)) {
            return PARAMS.NEED_UPDATE_FIRMWARE;
        }
        if (emergencySituationsParams.contains(PARAMS.NEED_RELOAD_CAN_PARAMETERS)) {
            return PARAMS.NEED_RELOAD_CAN_PARAMETERS;
        }
        return null;
    }

    public final boolean isNeedCanFileReload() {
        return getEmergencySituationsParams().contains(PARAMS.NEED_RELOAD_CAN_PARAMETERS);
    }

    public final boolean isDeviceLocked() {
        return getEmergencySituationsParams().contains(PARAMS.DEVICE_LOCKED);
    }

    public final boolean isDeviceHasCanFileData() {
        return !getEmergencySituationsParams().contains(PARAMS.CAN_HAS_NOT_ENGINE_SPEED_DATA);
    }

    public final boolean isNeedReloadSoundPackages() {
        ArrayList<PARAMS> emergencySituationsParams = getEmergencySituationsParams();
        return emergencySituationsParams.contains(PARAMS.NEED_RELOAD_PARAMETERS_OF_ALL_SAMPLES_AND_SOUND_PACKAGES) || emergencySituationsParams.contains(PARAMS.NEED_RELOAD_SOUND_PACKAGE_RULES);
    }

    /* compiled from: DeviceParameters.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters$Parameter;", "", TtmlNode.ATTR_ID, "", "value", "(SS)V", "getId", "()S", "getValue", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Parameter {
        private final short id;
        private final short value;

        public Parameter(short s, short s2) {
            this.id = s;
            this.value = s2;
        }

        public final short getId() {
            return this.id;
        }

        public final short getValue() {
            return this.value;
        }
    }

    /* compiled from: DeviceParameters.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0002\b\u000b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DeviceParameters$PARAMS;", "", "value", "", "(Ljava/lang/String;IS)V", "getValue", "()S", "NEED_RELOAD_SOUND_PACKAGE_RULES", "NEED_RELOAD_CAN_PARAMETERS", "NEED_RELOAD_PARAMETERS_OF_ALL_SAMPLES_AND_SOUND_PACKAGES", "NEED_UPDATE_FIRMWARE", "RECOMMENDED_TO_RETURN_TO_FACTORY_SETTINGS", "CAN_HAS_NOT_ENGINE_SPEED_DATA", "DEVICE_LOCKED", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum PARAMS {
        NEED_RELOAD_SOUND_PACKAGE_RULES(1),
        NEED_RELOAD_CAN_PARAMETERS(2),
        NEED_RELOAD_PARAMETERS_OF_ALL_SAMPLES_AND_SOUND_PACKAGES(4),
        NEED_UPDATE_FIRMWARE(5),
        RECOMMENDED_TO_RETURN_TO_FACTORY_SETTINGS(8),
        CAN_HAS_NOT_ENGINE_SPEED_DATA(16),
        DEVICE_LOCKED(32);

        private final short value;

        PARAMS(short s) {
            this.value = s;
        }

        public final short getValue() {
            return this.value;
        }
    }
}
