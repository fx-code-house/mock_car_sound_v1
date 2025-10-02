package com.thor.businessmodule.bluetooth.model.other;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;

/* compiled from: PresetRule.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/PresetRule;", "", TtmlNode.ATTR_ID, "", "value", "(SS)V", "getId", "()S", "getValue", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class PresetRule {
    public static final short ENABLED_RULES = 3;
    public static final short IDLE_TONE = 24;
    public static final short IDLE_VOLUME = 48;
    public static final short MAIN_VOLUME = 0;
    public static final short NUMBER_OF_CHAMBER = 33;
    public static final short START_ENGINE_TYPE = 1;
    public static final short START_ENGINE_TYPE_DYNAMIC_START = 1;
    public static final short START_ENGINE_TYPE_OFF = 0;
    public static final short START_ENGINE_TYPE_STARTER_SOUND = 4;
    public static final short UNEVEN = 4;
    public static final short WORK_SPEED = 25;
    private final short id;
    private final short value;

    public static /* synthetic */ PresetRule copy$default(PresetRule presetRule, short s, short s2, int i, Object obj) {
        if ((i & 1) != 0) {
            s = presetRule.id;
        }
        if ((i & 2) != 0) {
            s2 = presetRule.value;
        }
        return presetRule.copy(s, s2);
    }

    /* renamed from: component1, reason: from getter */
    public final short getId() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final short getValue() {
        return this.value;
    }

    public final PresetRule copy(short id, short value) {
        return new PresetRule(id, value);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PresetRule)) {
            return false;
        }
        PresetRule presetRule = (PresetRule) other;
        return this.id == presetRule.id && this.value == presetRule.value;
    }

    public int hashCode() {
        return (Short.hashCode(this.id) * 31) + Short.hashCode(this.value);
    }

    public String toString() {
        return "PresetRule(id=" + ((int) this.id) + ", value=" + ((int) this.value) + ")";
    }

    public PresetRule(short s, short s2) {
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
