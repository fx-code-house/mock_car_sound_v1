package com.thor.businessmodule.bluetooth.model.other.settings.applicationmodel;

import kotlin.Metadata;

/* compiled from: Entity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/Entity;", "", "entityType", "", "entityId", "(II)V", "getEntityId", "()I", "getEntityType", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class Entity {
    private final int entityId;
    private final int entityType;

    public static /* synthetic */ Entity copy$default(Entity entity, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = entity.entityType;
        }
        if ((i3 & 2) != 0) {
            i2 = entity.entityId;
        }
        return entity.copy(i, i2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getEntityType() {
        return this.entityType;
    }

    /* renamed from: component2, reason: from getter */
    public final int getEntityId() {
        return this.entityId;
    }

    public final Entity copy(int entityType, int entityId) {
        return new Entity(entityType, entityId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Entity)) {
            return false;
        }
        Entity entity = (Entity) other;
        return this.entityType == entity.entityType && this.entityId == entity.entityId;
    }

    public int hashCode() {
        return (Integer.hashCode(this.entityType) * 31) + Integer.hashCode(this.entityId);
    }

    public String toString() {
        return "Entity(entityType=" + this.entityType + ", entityId=" + this.entityId + ")";
    }

    public Entity(int i, int i2) {
        this.entityType = i;
        this.entityId = i2;
    }

    public final int getEntityType() {
        return this.entityType;
    }

    public final int getEntityId() {
        return this.entityId;
    }
}
