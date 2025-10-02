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
public final class CardRequirements extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CardRequirements> CREATOR = new zze();
    ArrayList<Integer> zzaj;
    boolean zzak;
    boolean zzal;
    int zzam;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (CardRequirements.this.zzaj == null) {
                CardRequirements.this.zzaj = new ArrayList<>();
            }
            CardRequirements.this.zzaj.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(Collection<Integer> collection) {
            Preconditions.checkArgument((collection == null || collection.isEmpty()) ? false : true, "allowedCardNetworks can't be null or empty! You must provide a valid value from WalletConstants.CardNetwork.");
            if (CardRequirements.this.zzaj == null) {
                CardRequirements.this.zzaj = new ArrayList<>();
            }
            CardRequirements.this.zzaj.addAll(collection);
            return this;
        }

        public final Builder setAllowPrepaidCards(boolean z) {
            CardRequirements.this.zzak = z;
            return this;
        }

        public final Builder setBillingAddressRequired(boolean z) {
            CardRequirements.this.zzal = z;
            return this;
        }

        public final Builder setBillingAddressFormat(int i) {
            CardRequirements.this.zzam = i;
            return this;
        }

        public final CardRequirements build() {
            Preconditions.checkNotNull(CardRequirements.this.zzaj, "Allowed card networks must be non-empty! You can set it through addAllowedCardNetwork() or addAllowedCardNetworks() in the CardRequirements Builder.");
            return CardRequirements.this;
        }
    }

    CardRequirements(ArrayList<Integer> arrayList, boolean z, boolean z2, int i) {
        this.zzaj = arrayList;
        this.zzak = z;
        this.zzal = z2;
        this.zzam = i;
    }

    private CardRequirements() {
        this.zzak = true;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntegerList(parcel, 1, this.zzaj, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzak);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzal);
        SafeParcelWriter.writeInt(parcel, 4, this.zzam);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzaj;
    }

    public final boolean allowPrepaidCards() {
        return this.zzak;
    }

    public final boolean isBillingAddressRequired() {
        return this.zzal;
    }

    public final int getBillingAddressFormat() {
        return this.zzam;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
