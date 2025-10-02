package org.mapstruct.ap.internal.gem;

/* loaded from: classes3.dex */
public enum MappingInheritanceStrategyGem {
    EXPLICIT(false, false, false),
    AUTO_INHERIT_FROM_CONFIG(true, true, false),
    AUTO_INHERIT_REVERSE_FROM_CONFIG(true, false, true),
    AUTO_INHERIT_ALL_FROM_CONFIG(true, true, true);

    private final boolean applyForward;
    private final boolean applyReverse;
    private final boolean autoInherit;

    MappingInheritanceStrategyGem(boolean z, boolean z2, boolean z3) {
        this.autoInherit = z;
        this.applyForward = z2;
        this.applyReverse = z3;
    }

    public boolean isAutoInherit() {
        return this.autoInherit;
    }

    public boolean isApplyForward() {
        return this.applyForward;
    }

    public boolean isApplyReverse() {
        return this.applyReverse;
    }
}
