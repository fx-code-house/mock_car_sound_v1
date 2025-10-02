package com.thor.app.gui.fragments.shop.sgu;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguShopUIState.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/thor/app/gui/fragments/shop/sgu/SguShopError;", "Lcom/thor/app/gui/fragments/shop/sgu/SguShopUIState;", "message", "", "showToUser", "", "(Ljava/lang/String;Z)V", "getMessage", "()Ljava/lang/String;", "getShowToUser", "()Z", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class SguShopError extends SguShopUIState {
    private final String message;
    private final boolean showToUser;

    public static /* synthetic */ SguShopError copy$default(SguShopError sguShopError, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = sguShopError.message;
        }
        if ((i & 2) != 0) {
            z = sguShopError.showToUser;
        }
        return sguShopError.copy(str, z);
    }

    /* renamed from: component1, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getShowToUser() {
        return this.showToUser;
    }

    public final SguShopError copy(String message, boolean showToUser) {
        return new SguShopError(message, showToUser);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SguShopError)) {
            return false;
        }
        SguShopError sguShopError = (SguShopError) other;
        return Intrinsics.areEqual(this.message, sguShopError.message) && this.showToUser == sguShopError.showToUser;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.message;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        boolean z = this.showToUser;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode + i;
    }

    public String toString() {
        return "SguShopError(message=" + this.message + ", showToUser=" + this.showToUser + ")";
    }

    public SguShopError(String str, boolean z) {
        super(null);
        this.message = str;
        this.showToUser = z;
    }

    public /* synthetic */ SguShopError(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z);
    }

    public final String getMessage() {
        return this.message;
    }

    public final boolean getShowToUser() {
        return this.showToUser;
    }
}
