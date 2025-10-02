package com.thor.businessmodule.bluetooth.model.other;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceSettings.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 \u00102\u00020\u0001:\u0002\u0010\u0011B\u001f\b\u0016\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\u0002\u0010\u0006B\u0005¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rR.\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\u0006¨\u0006\u0012"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DeviceSettings;", "", "settings", "Ljava/util/ArrayList;", "Lcom/thor/businessmodule/bluetooth/model/other/DeviceSettings$Setting;", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "()V", "parameters", "getParameters", "()Ljava/util/ArrayList;", "setParameters", "isDriveSelectActive", "", "isNativeControlActive", "isTwoSpeakerInstalled", "Companion", "Setting", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DeviceSettings {
    public static final short DRIVE_SELECT_ID = 1;
    public static final short NATIVE_CONTROL_ID = 2;
    public static final short SPEAKER_COUNT_ID = 3;
    private ArrayList<Setting> parameters;

    public DeviceSettings() {
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DeviceSettings(ArrayList<Setting> settings) {
        this();
        Intrinsics.checkNotNullParameter(settings, "settings");
        this.parameters = settings;
    }

    public final ArrayList<Setting> getParameters() {
        return this.parameters;
    }

    public final void setParameters(ArrayList<Setting> arrayList) {
        this.parameters = arrayList;
    }

    public final boolean isDriveSelectActive() {
        ArrayList<Setting> arrayList = this.parameters;
        if (arrayList == null) {
            return false;
        }
        for (Setting setting : arrayList) {
            if (setting.getId() == 1 && setting.getValue() == 1) {
                return true;
            }
        }
        return false;
    }

    public final boolean isNativeControlActive() {
        ArrayList<Setting> arrayList = this.parameters;
        if (arrayList == null) {
            return false;
        }
        for (Setting setting : arrayList) {
            if (setting.getId() == 2 && setting.getValue() == 1) {
                return true;
            }
        }
        return false;
    }

    public final boolean isTwoSpeakerInstalled() {
        ArrayList<Setting> arrayList = this.parameters;
        if (arrayList == null) {
            return false;
        }
        for (Setting setting : arrayList) {
            if (setting.getId() == 3 && setting.getValue() == 2) {
                return true;
            }
        }
        return false;
    }

    /* compiled from: DeviceSettings.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/DeviceSettings$Setting;", "", TtmlNode.ATTR_ID, "", "value", "(SS)V", "getId", "()S", "getValue", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Setting {
        private final short id;
        private final short value;

        public Setting(short s, short s2) {
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
}
