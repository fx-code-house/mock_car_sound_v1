package org.mapstruct.ap.spi;

/* loaded from: classes3.dex */
public class StripSuffixEnumTransformationStrategy implements EnumTransformationStrategy {
    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String getStrategyName() {
        return "stripSuffix";
    }

    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String transform(String str, String str2) {
        return str.endsWith(str2) ? str.substring(0, str.length() - str2.length()) : str;
    }
}
