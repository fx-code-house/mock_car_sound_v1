package org.mapstruct.ap.spi;

/* loaded from: classes3.dex */
public class StripPrefixEnumTransformationStrategy implements EnumTransformationStrategy {
    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String getStrategyName() {
        return "stripPrefix";
    }

    @Override // org.mapstruct.ap.spi.EnumTransformationStrategy
    public String transform(String str, String str2) {
        return str.startsWith(str2) ? str.substring(str2.length()) : str;
    }
}
