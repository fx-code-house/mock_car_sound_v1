package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class ShippingAddressRequirements extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ShippingAddressRequirements> CREATOR = new zzag();
    ArrayList<String> zzdv;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder addAllowedCountryCode(String str) {
            Preconditions.checkNotEmpty(str, "allowedCountryCode can't be null or empty! If you don't have restrictions, just leave it unset.");
            if (ShippingAddressRequirements.this.zzdv == null) {
                ShippingAddressRequirements.this.zzdv = new ArrayList<>();
            }
            ShippingAddressRequirements.this.zzdv.add(str);
            return this;
        }

        public final Builder addAllowedCountryCodes(Collection<String> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("allowedCountryCodes can't be null or empty! If you don't have restrictions, just leave it unset.");
            }
            if (ShippingAddressRequirements.this.zzdv == null) {
                ShippingAddressRequirements.this.zzdv = new ArrayList<>();
            }
            ShippingAddressRequirements.this.zzdv.addAll(collection);
            return this;
        }

        public final ShippingAddressRequirements build() {
            return ShippingAddressRequirements.this;
        }
    }

    ShippingAddressRequirements(ArrayList<String> arrayList) {
        this.zzdv = arrayList;
    }

    private ShippingAddressRequirements() {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringList(parcel, 1, this.zzdv, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final ArrayList<String> getAllowedCountryCodes() {
        return this.zzdv;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
