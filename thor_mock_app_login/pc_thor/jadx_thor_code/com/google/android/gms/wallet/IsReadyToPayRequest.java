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
public final class IsReadyToPayRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<IsReadyToPayRequest> CREATOR = new zzn();
    ArrayList<Integer> zzaj;
    private String zzbs;
    private String zzbt;
    ArrayList<Integer> zzbu;
    boolean zzbv;
    private String zzbw;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    @Deprecated
    public final class Builder {
        private Builder() {
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (IsReadyToPayRequest.this.zzaj == null) {
                IsReadyToPayRequest.this.zzaj = new ArrayList<>();
            }
            IsReadyToPayRequest.this.zzaj.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(Collection<Integer> collection) {
            Preconditions.checkArgument((collection == null || collection.isEmpty()) ? false : true, "allowedCardNetworks can't be null or empty. If you want the defaults, leave it unset.");
            if (IsReadyToPayRequest.this.zzaj == null) {
                IsReadyToPayRequest.this.zzaj = new ArrayList<>();
            }
            IsReadyToPayRequest.this.zzaj.addAll(collection);
            return this;
        }

        public final Builder addAllowedPaymentMethod(int i) {
            if (IsReadyToPayRequest.this.zzbu == null) {
                IsReadyToPayRequest.this.zzbu = new ArrayList<>();
            }
            IsReadyToPayRequest.this.zzbu.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedPaymentMethods(Collection<Integer> collection) {
            Preconditions.checkArgument((collection == null || collection.isEmpty()) ? false : true, "allowedPaymentMethods can't be null or empty. If you want the default, leave it unset.");
            if (IsReadyToPayRequest.this.zzbu == null) {
                IsReadyToPayRequest.this.zzbu = new ArrayList<>();
            }
            IsReadyToPayRequest.this.zzbu.addAll(collection);
            return this;
        }

        public final Builder setExistingPaymentMethodRequired(boolean z) {
            IsReadyToPayRequest.this.zzbv = z;
            return this;
        }

        public final IsReadyToPayRequest build() {
            return IsReadyToPayRequest.this;
        }
    }

    IsReadyToPayRequest() {
    }

    IsReadyToPayRequest(ArrayList<Integer> arrayList, String str, String str2, ArrayList<Integer> arrayList2, boolean z, String str3) {
        this.zzaj = arrayList;
        this.zzbs = str;
        this.zzbt = str2;
        this.zzbu = arrayList2;
        this.zzbv = z;
        this.zzbw = str3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntegerList(parcel, 2, this.zzaj, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzbs, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzbt, false);
        SafeParcelWriter.writeIntegerList(parcel, 6, this.zzbu, false);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzbv);
        SafeParcelWriter.writeString(parcel, 8, this.zzbw, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Deprecated
    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzaj;
    }

    @Deprecated
    public final ArrayList<Integer> getAllowedPaymentMethods() {
        return this.zzbu;
    }

    @Deprecated
    public final boolean isExistingPaymentMethodRequired() {
        return this.zzbv;
    }

    public static IsReadyToPayRequest fromJson(String str) {
        Builder builderNewBuilder = newBuilder();
        IsReadyToPayRequest.this.zzbw = (String) Preconditions.checkNotNull(str, "isReadyToPayRequestJson cannot be null!");
        return builderNewBuilder.build();
    }

    public final String toJson() {
        return this.zzbw;
    }

    @Deprecated
    public static Builder newBuilder() {
        return new Builder();
    }
}
