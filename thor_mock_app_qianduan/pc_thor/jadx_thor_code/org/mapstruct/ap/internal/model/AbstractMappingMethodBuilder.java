package org.mapstruct.ap.internal.model;

import java.util.Iterator;
import org.mapstruct.ap.internal.model.AbstractMappingMethodBuilder;
import org.mapstruct.ap.internal.model.MappingMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class AbstractMappingMethodBuilder<B extends AbstractMappingMethodBuilder<B, M>, M extends MappingMethod> extends AbstractBaseBuilder<B> {
    private ForgedMethodHistory description;

    public abstract M build();

    protected abstract boolean shouldUsePropertyNamesInHistory();

    public AbstractMappingMethodBuilder(Class<B> cls) {
        super(cls);
    }

    Assignment forgeMapping(SourceRHS sourceRHS, Type type, Type type2) {
        if (!canGenerateAutoSubMappingBetween(type, type2)) {
            return null;
        }
        String safeVariableName = Strings.getSafeVariableName(getName(type, type2), this.ctx.getReservedNames());
        this.description = new ForgedMethodHistory(this.method instanceof ForgedMethod ? ((ForgedMethod) this.method).getHistory() : null, Strings.stubPropertyName(sourceRHS.getSourceType().getName()), Strings.stubPropertyName(type2.getName()), sourceRHS.getSourceType(), type2, shouldUsePropertyNamesInHistory(), sourceRHS.getSourceErrorMessagePart());
        return createForgedAssignment(sourceRHS, this.ctx.getTypeFactory().builderTypeFor(type2, this.method.getOptions().getBeanMapping().getBuilder()), ForgedMethod.forElementMapping(safeVariableName, type, type2, this.method, this.description, true));
    }

    private String getName(Type type, Type type2) {
        return Strings.decapitalize(getName(type) + "To" + getName(type2));
    }

    private String getName(Type type) {
        StringBuilder sb = new StringBuilder();
        Iterator<Type> it = type.getTypeParameters().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getIdentification());
        }
        sb.append(type.getIdentification());
        return sb.toString();
    }

    public ForgedMethodHistory getDescription() {
        return this.description;
    }
}
