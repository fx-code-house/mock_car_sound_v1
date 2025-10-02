package com.thor.businessmodule.bluetooth;

import kotlin.Metadata;

/* compiled from: SoundPackageRule.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/thor/businessmodule/bluetooth/SoundPackageRule;", "", "ruleId", "", "ruleValue", "(SS)V", "getRuleId", "()S", "getRuleValue", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SoundPackageRule {
    private final short ruleId;
    private final short ruleValue;

    public static /* synthetic */ SoundPackageRule copy$default(SoundPackageRule soundPackageRule, short s, short s2, int i, Object obj) {
        if ((i & 1) != 0) {
            s = soundPackageRule.ruleId;
        }
        if ((i & 2) != 0) {
            s2 = soundPackageRule.ruleValue;
        }
        return soundPackageRule.copy(s, s2);
    }

    /* renamed from: component1, reason: from getter */
    public final short getRuleId() {
        return this.ruleId;
    }

    /* renamed from: component2, reason: from getter */
    public final short getRuleValue() {
        return this.ruleValue;
    }

    public final SoundPackageRule copy(short ruleId, short ruleValue) {
        return new SoundPackageRule(ruleId, ruleValue);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SoundPackageRule)) {
            return false;
        }
        SoundPackageRule soundPackageRule = (SoundPackageRule) other;
        return this.ruleId == soundPackageRule.ruleId && this.ruleValue == soundPackageRule.ruleValue;
    }

    public int hashCode() {
        return (Short.hashCode(this.ruleId) * 31) + Short.hashCode(this.ruleValue);
    }

    public String toString() {
        return "SoundPackageRule(ruleId=" + ((int) this.ruleId) + ", ruleValue=" + ((int) this.ruleValue) + ")";
    }

    public SoundPackageRule(short s, short s2) {
        this.ruleId = s;
        this.ruleValue = s2;
    }

    public final short getRuleId() {
        return this.ruleId;
    }

    public final short getRuleValue() {
        return this.ruleValue;
    }
}
