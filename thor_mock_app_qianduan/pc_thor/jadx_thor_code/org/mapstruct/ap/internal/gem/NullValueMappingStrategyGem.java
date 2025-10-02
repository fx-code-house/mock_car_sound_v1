package org.mapstruct.ap.internal.gem;

/* loaded from: classes3.dex */
public enum NullValueMappingStrategyGem {
    RETURN_NULL(false),
    RETURN_DEFAULT(true);

    private final boolean returnDefault;

    NullValueMappingStrategyGem(boolean z) {
        this.returnDefault = z;
    }

    public boolean isReturnDefault() {
        return this.returnDefault;
    }
}
