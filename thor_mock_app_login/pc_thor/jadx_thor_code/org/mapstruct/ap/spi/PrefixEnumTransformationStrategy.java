package org.mapstruct.ap.spi;

/* loaded from: classes3.dex */
public class PrefixEnumTransformationStrategy implements EnumTransformationStrategy {
    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String getStrategyName() {
        return "prefix";
    }

    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String transform(String str, String str2) {
        return str2 + str;
    }
}
