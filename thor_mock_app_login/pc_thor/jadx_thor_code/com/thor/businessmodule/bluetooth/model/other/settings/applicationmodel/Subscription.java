package com.thor.businessmodule.bluetooth.model.other.settings.applicationmodel;

import android.util.Log;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.bluetooth.util.BleHelperKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: Subscription.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J7\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u001e"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/Subscription;", "", "expiryDate", "", "reserved", "", "entitiesNumber", "listEntity", "", "Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/Entity;", "(JIILjava/util/List;)V", "getEntitiesNumber", "()I", "getExpiryDate", "()J", "getListEntity", "()Ljava/util/List;", "getReserved", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "Companion", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class Subscription {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int entitiesNumber;
    private final long expiryDate;
    private final List<Entity> listEntity;
    private final int reserved;

    public static /* synthetic */ Subscription copy$default(Subscription subscription, long j, int i, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            j = subscription.expiryDate;
        }
        long j2 = j;
        if ((i3 & 2) != 0) {
            i = subscription.reserved;
        }
        int i4 = i;
        if ((i3 & 4) != 0) {
            i2 = subscription.entitiesNumber;
        }
        int i5 = i2;
        if ((i3 & 8) != 0) {
            list = subscription.listEntity;
        }
        return subscription.copy(j2, i4, i5, list);
    }

    /* renamed from: component1, reason: from getter */
    public final long getExpiryDate() {
        return this.expiryDate;
    }

    /* renamed from: component2, reason: from getter */
    public final int getReserved() {
        return this.reserved;
    }

    /* renamed from: component3, reason: from getter */
    public final int getEntitiesNumber() {
        return this.entitiesNumber;
    }

    public final List<Entity> component4() {
        return this.listEntity;
    }

    public final Subscription copy(long expiryDate, int reserved, int entitiesNumber, List<Entity> listEntity) {
        Intrinsics.checkNotNullParameter(listEntity, "listEntity");
        return new Subscription(expiryDate, reserved, entitiesNumber, listEntity);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Subscription)) {
            return false;
        }
        Subscription subscription = (Subscription) other;
        return this.expiryDate == subscription.expiryDate && this.reserved == subscription.reserved && this.entitiesNumber == subscription.entitiesNumber && Intrinsics.areEqual(this.listEntity, subscription.listEntity);
    }

    public int hashCode() {
        return (((((Long.hashCode(this.expiryDate) * 31) + Integer.hashCode(this.reserved)) * 31) + Integer.hashCode(this.entitiesNumber)) * 31) + this.listEntity.hashCode();
    }

    public String toString() {
        return "Subscription(expiryDate=" + this.expiryDate + ", reserved=" + this.reserved + ", entitiesNumber=" + this.entitiesNumber + ", listEntity=" + this.listEntity + ")";
    }

    public Subscription(long j, int i, int i2, List<Entity> listEntity) {
        Intrinsics.checkNotNullParameter(listEntity, "listEntity");
        this.expiryDate = j;
        this.reserved = i;
        this.entitiesNumber = i2;
        this.listEntity = listEntity;
    }

    public final long getExpiryDate() {
        return this.expiryDate;
    }

    public final int getReserved() {
        return this.reserved;
    }

    public final int getEntitiesNumber() {
        return this.entitiesNumber;
    }

    public final List<Entity> getListEntity() {
        return this.listEntity;
    }

    /* compiled from: Subscription.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/Subscription$Companion;", "", "()V", "get", "Lcom/thor/businessmodule/bluetooth/model/other/settings/applicationmodel/Subscription;", "data", "", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Subscription get(byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            byte[] bArrSliceArray = ArraysKt.sliceArray(data, RangesKt.until(0, 4));
            Log.i("Subscription", "dataEx: " + BleHelperKt.toHex(bArrSliceArray));
            long uInt32 = BleHelperKt.toUInt32(bArrSliceArray);
            short shortLittleEndian = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(4, 6)));
            short shortLittleEndian2 = BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(data, RangesKt.until(6, 8)));
            if (shortLittleEndian2 == 0) {
                return new Subscription(uInt32, shortLittleEndian, shortLittleEndian2, CollectionsKt.emptyList());
            }
            List<byte[]> listSplitByteArray = BleHelper.INSTANCE.splitByteArray(ArraysKt.sliceArray(data, RangesKt.until(8, data.length)), 4);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplitByteArray, 10));
            for (byte[] bArr : listSplitByteArray) {
                arrayList.add(new Entity(BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr, RangesKt.until(0, 2))), BleHelperKt.toShortLittleEndian(ArraysKt.sliceArray(bArr, RangesKt.until(2, 4)))));
            }
            return new Subscription(uInt32, shortLittleEndian, shortLittleEndian2, arrayList);
        }
    }
}
