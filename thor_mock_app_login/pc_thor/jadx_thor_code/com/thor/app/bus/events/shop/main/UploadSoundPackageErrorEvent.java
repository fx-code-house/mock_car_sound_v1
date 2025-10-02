package com.thor.app.bus.events.shop.main;

import com.thor.networkmodule.model.shop.ShopSoundPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadSoundPackageErrorEvent.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/thor/app/bus/events/shop/main/UploadSoundPackageErrorEvent;", "", "isError", "", "shopSoundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "(ZLcom/thor/networkmodule/model/shop/ShopSoundPackage;)V", "()Z", "getShopSoundPackage", "()Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class UploadSoundPackageErrorEvent {
    private final boolean isError;
    private final ShopSoundPackage shopSoundPackage;

    public static /* synthetic */ UploadSoundPackageErrorEvent copy$default(UploadSoundPackageErrorEvent uploadSoundPackageErrorEvent, boolean z, ShopSoundPackage shopSoundPackage, int i, Object obj) {
        if ((i & 1) != 0) {
            z = uploadSoundPackageErrorEvent.isError;
        }
        if ((i & 2) != 0) {
            shopSoundPackage = uploadSoundPackageErrorEvent.shopSoundPackage;
        }
        return uploadSoundPackageErrorEvent.copy(z, shopSoundPackage);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsError() {
        return this.isError;
    }

    /* renamed from: component2, reason: from getter */
    public final ShopSoundPackage getShopSoundPackage() {
        return this.shopSoundPackage;
    }

    public final UploadSoundPackageErrorEvent copy(boolean isError, ShopSoundPackage shopSoundPackage) {
        Intrinsics.checkNotNullParameter(shopSoundPackage, "shopSoundPackage");
        return new UploadSoundPackageErrorEvent(isError, shopSoundPackage);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UploadSoundPackageErrorEvent)) {
            return false;
        }
        UploadSoundPackageErrorEvent uploadSoundPackageErrorEvent = (UploadSoundPackageErrorEvent) other;
        return this.isError == uploadSoundPackageErrorEvent.isError && Intrinsics.areEqual(this.shopSoundPackage, uploadSoundPackageErrorEvent.shopSoundPackage);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z = this.isError;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return (r0 * 31) + this.shopSoundPackage.hashCode();
    }

    public String toString() {
        return "UploadSoundPackageErrorEvent(isError=" + this.isError + ", shopSoundPackage=" + this.shopSoundPackage + ")";
    }

    public UploadSoundPackageErrorEvent(boolean z, ShopSoundPackage shopSoundPackage) {
        Intrinsics.checkNotNullParameter(shopSoundPackage, "shopSoundPackage");
        this.isError = z;
        this.shopSoundPackage = shopSoundPackage;
    }

    public final ShopSoundPackage getShopSoundPackage() {
        return this.shopSoundPackage;
    }

    public final boolean isError() {
        return this.isError;
    }
}
