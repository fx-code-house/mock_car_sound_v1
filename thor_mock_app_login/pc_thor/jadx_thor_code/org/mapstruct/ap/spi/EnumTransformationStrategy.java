package org.mapstruct.ap.spi;

/* loaded from: classes3.dex */
public interface EnumTransformationStrategy {
    String getStrategyName();

    default void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
    }

    String transform(String str, String str2);
}
