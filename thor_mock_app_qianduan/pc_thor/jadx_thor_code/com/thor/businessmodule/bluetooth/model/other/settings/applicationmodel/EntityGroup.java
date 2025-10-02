package com.thor.businessmodule.bluetooth.model.other.settings.applicationmodel;

import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: EntityGroup.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B#\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0003J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tHÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001b"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/EntityGroup;", "", "data", "", "([B)V", "groupId", "", "entitiesNumber", "entities", "", "Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/Entity;", "(IILjava/util/List;)V", "getEntities", "()Ljava/util/List;", "getEntitiesNumber", "()I", "getGroupId", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class EntityGroup {
    private final List<Entity> entities;
    private final int entitiesNumber;
    private final int groupId;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ EntityGroup copy$default(EntityGroup entityGroup, int i, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = entityGroup.groupId;
        }
        if ((i3 & 2) != 0) {
            i2 = entityGroup.entitiesNumber;
        }
        if ((i3 & 4) != 0) {
            list = entityGroup.entities;
        }
        return entityGroup.copy(i, i2, list);
    }

    /* renamed from: component1, reason: from getter */
    public final int getGroupId() {
        return this.groupId;
    }

    /* renamed from: component2, reason: from getter */
    public final int getEntitiesNumber() {
        return this.entitiesNumber;
    }

    public final List<Entity> component3() {
        return this.entities;
    }

    public final EntityGroup copy(int groupId, int entitiesNumber, List<Entity> entities) {
        Intrinsics.checkNotNullParameter(entities, "entities");
        return new EntityGroup(groupId, entitiesNumber, entities);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EntityGroup)) {
            return false;
        }
        EntityGroup entityGroup = (EntityGroup) other;
        return this.groupId == entityGroup.groupId && this.entitiesNumber == entityGroup.entitiesNumber && Intrinsics.areEqual(this.entities, entityGroup.entities);
    }

    public int hashCode() {
        return (((Integer.hashCode(this.groupId) * 31) + Integer.hashCode(this.entitiesNumber)) * 31) + this.entities.hashCode();
    }

    public String toString() {
        return "EntityGroup(groupId=" + this.groupId + ", entitiesNumber=" + this.entitiesNumber + ", entities=" + this.entities + ")";
    }

    public EntityGroup(int i, int i2, List<Entity> entities) {
        Intrinsics.checkNotNullParameter(entities, "entities");
        this.groupId = i;
        this.entitiesNumber = i2;
        this.entities = entities;
    }

    public final int getGroupId() {
        return this.groupId;
    }

    public final int getEntitiesNumber() {
        return this.entitiesNumber;
    }

    public final List<Entity> getEntities() {
        return this.entities;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public EntityGroup(byte[] data) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(data, "data");
        short shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(0, 2)));
        short shortLittleEndian2 = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(2, 4)));
        if (BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(2, 4))) == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            List<byte[]> listSplitByteArray = BleHelper.INSTANCE.splitByteArray(ArraysKt.sliceArray(data, RangesKt.until(4, data.length)), 4);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplitByteArray, 10));
            for (byte[] bArr : listSplitByteArray) {
                arrayList.add(new Entity(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr, RangesKt.until(0, 2))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr, RangesKt.until(2, 4)))));
            }
            arrayListEmptyList = arrayList;
        }
        this(shortLittleEndian, shortLittleEndian2, arrayListEmptyList);
    }
}
