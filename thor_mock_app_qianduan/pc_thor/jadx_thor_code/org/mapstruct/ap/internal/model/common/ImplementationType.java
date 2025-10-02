package org.mapstruct.ap.internal.model.common;

/* loaded from: classes3.dex */
public class ImplementationType {
    private final boolean initialCapacityConstructor;
    private final boolean loadFactorAdjustment;
    private final Type type;

    private ImplementationType(Type type, boolean z, boolean z2) {
        this.type = type;
        this.initialCapacityConstructor = z;
        this.loadFactorAdjustment = z2;
    }

    public static ImplementationType withDefaultConstructor(Type type) {
        return new ImplementationType(type, false, false);
    }

    public static ImplementationType withInitialCapacity(Type type) {
        return new ImplementationType(type, true, false);
    }

    public static ImplementationType withLoadFactorAdjustment(Type type) {
        return new ImplementationType(type, true, true);
    }

    public ImplementationType createNew(Type type) {
        return new ImplementationType(type, this.initialCapacityConstructor, this.loadFactorAdjustment);
    }

    public Type getType() {
        return this.type;
    }

    public boolean hasInitialCapacityConstructor() {
        return this.initialCapacityConstructor;
    }

    public boolean isLoadFactorAdjustment() {
        return this.loadFactorAdjustment;
    }
}
