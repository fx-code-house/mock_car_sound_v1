package org.mapstruct.ap.spi;

/* loaded from: classes3.dex */
public class SuffixEnumTransformationStrategy implements EnumTransformationStrategy {
    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String getStrategyName() {
        return "suffix";
    }

    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String transform(String str, String str2) {
        return str + str2;
    }
}
