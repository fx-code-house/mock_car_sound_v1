package org.mapstruct.ap.internal.model;

import java.util.List;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.SourceMethod;

/* loaded from: classes3.dex */
public abstract class MapperReference extends Field {
    public MapperReference(Type type, String str) {
        super(type, str);
    }

    public MapperReference(Type type, String str, boolean z) {
        super(type, str, z);
    }

    public static MapperReference findMapperReference(List<MapperReference> list, SourceMethod sourceMethod) {
        for (MapperReference mapperReference : list) {
            if (mapperReference.getType().equals(sourceMethod.getDeclaringMapper())) {
                mapperReference.setUsed(mapperReference.isUsed() || !sourceMethod.isStatic());
                mapperReference.setTypeRequiresImport(true);
                return mapperReference;
            }
        }
        return null;
    }
}
