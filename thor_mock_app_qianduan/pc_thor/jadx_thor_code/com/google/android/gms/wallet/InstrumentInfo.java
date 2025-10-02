package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class InstrumentInfo extends AbstractSafeParcelable {
    public static final int CARD_CLASS_CREDIT = 1;
    public static final int CARD_CLASS_DEBIT = 2;
    public static final int CARD_CLASS_PREPAID = 3;
    public static final int CARD_CLASS_UNKNOWN = 0;
    public static final Parcelable.Creator<InstrumentInfo> CREATOR = new zzl();
    private int zzaa;
    private String zzbq;
    private String zzbr;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface CardClass {
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getInstrumentType(), false);
        SafeParcelWriter.writeString(parcel, 3, getInstrumentDetails(), false);
        SafeParcelWriter.writeInt(parcel, 4, getCardClass());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public InstrumentInfo(String str, String str2, int i) {
        this.zzbq = str;
        this.zzbr = str2;
        this.zzaa = i;
    }

    private InstrumentInfo() {
    }

    public final String getInstrumentType() {
        return this.zzbq;
    }

    public final String getInstrumentDetails() {
        return this.zzbr;
    }

    public final int getCardClass() {
        int i = this.zzaa;
        if (i == 1 || i == 2 || i == 3) {
            return i;
        }
        return 0;
    }
}
