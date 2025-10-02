package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class UserAddressRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<UserAddressRequest> CREATOR = new zzd();
    List<CountrySpecification> zzf;

    public final class Builder {
        private Builder() {
        }

        public final Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (UserAddressRequest.this.zzf == null) {
                UserAddressRequest.this.zzf = new ArrayList();
            }
            UserAddressRequest.this.zzf.add(countrySpecification);
            return this;
        }

        public final Builder addAllowedCountrySpecifications(Collection<CountrySpecification> collection) {
            if (UserAddressRequest.this.zzf == null) {
                UserAddressRequest.this.zzf = new ArrayList();
            }
            UserAddressRequest.this.zzf.addAll(collection);
            return this;
        }

        public final UserAddressRequest build() {
            if (UserAddressRequest.this.zzf != null) {
                UserAddressRequest userAddressRequest = UserAddressRequest.this;
                userAddressRequest.zzf = Collections.unmodifiableList(userAddressRequest.zzf);
            }
            return UserAddressRequest.this;
        }
    }

    UserAddressRequest() {
    }

    UserAddressRequest(List<CountrySpecification> list) {
        this.zzf = list;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzf, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
