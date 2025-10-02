package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.LoyaltyPoints;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class LoyaltyWalletObject extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LoyaltyWalletObject> CREATOR = new zzp();
    int state;
    String zzby;
    String zzbz;
    String zzca;
    String zzcb;
    String zzcc;
    String zzcd;
    String zzce;
    String zzcf;

    @Deprecated
    String zzcg;
    String zzch;
    ArrayList<WalletObjectMessage> zzci;
    TimeInterval zzcj;
    ArrayList<LatLng> zzck;

    @Deprecated
    String zzcl;

    @Deprecated
    String zzcm;
    ArrayList<LabelValueRow> zzcn;
    boolean zzco;
    ArrayList<UriData> zzcp;
    ArrayList<TextModuleData> zzcq;
    ArrayList<UriData> zzcr;
    LoyaltyPoints zzcs;

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private Builder() {
        }

        public final Builder setId(String str) {
            LoyaltyWalletObject.this.zzby = str;
            return this;
        }

        public final Builder setAccountId(String str) {
            LoyaltyWalletObject.this.zzbz = str;
            return this;
        }

        public final Builder setIssuerName(String str) {
            LoyaltyWalletObject.this.zzca = str;
            return this;
        }

        public final Builder setProgramName(String str) {
            LoyaltyWalletObject.this.zzcb = str;
            return this;
        }

        public final Builder setAccountName(String str) {
            LoyaltyWalletObject.this.zzcc = str;
            return this;
        }

        public final Builder setBarcodeAlternateText(String str) {
            LoyaltyWalletObject.this.zzcd = str;
            return this;
        }

        public final Builder setBarcodeType(String str) {
            LoyaltyWalletObject.this.zzce = str;
            return this;
        }

        public final Builder setBarcodeValue(String str) {
            LoyaltyWalletObject.this.zzcf = str;
            return this;
        }

        @Deprecated
        public final Builder setBarcodeLabel(String str) {
            LoyaltyWalletObject.this.zzcg = str;
            return this;
        }

        public final Builder setClassId(String str) {
            LoyaltyWalletObject.this.zzch = str;
            return this;
        }

        public final Builder setState(int i) {
            LoyaltyWalletObject.this.state = i;
            return this;
        }

        public final Builder addMessages(Collection<WalletObjectMessage> collection) {
            LoyaltyWalletObject.this.zzci.addAll(collection);
            return this;
        }

        public final Builder addMessage(WalletObjectMessage walletObjectMessage) {
            LoyaltyWalletObject.this.zzci.add(walletObjectMessage);
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            LoyaltyWalletObject.this.zzcj = timeInterval;
            return this;
        }

        public final Builder addLocations(Collection<LatLng> collection) {
            LoyaltyWalletObject.this.zzck.addAll(collection);
            return this;
        }

        public final Builder addLocation(LatLng latLng) {
            LoyaltyWalletObject.this.zzck.add(latLng);
            return this;
        }

        @Deprecated
        public final Builder setInfoModuleDataHexFontColor(String str) {
            LoyaltyWalletObject.this.zzcl = str;
            return this;
        }

        @Deprecated
        public final Builder setInfoModuleDataHexBackgroundColor(String str) {
            LoyaltyWalletObject.this.zzcm = str;
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRows(Collection<LabelValueRow> collection) {
            LoyaltyWalletObject.this.zzcn.addAll(collection);
            return this;
        }

        public final Builder addInfoModuleDataLabeValueRow(LabelValueRow labelValueRow) {
            LoyaltyWalletObject.this.zzcn.add(labelValueRow);
            return this;
        }

        public final Builder setInfoModuleDataShowLastUpdateTime(boolean z) {
            LoyaltyWalletObject.this.zzco = z;
            return this;
        }

        public final Builder addImageModuleDataMainImageUris(Collection<UriData> collection) {
            LoyaltyWalletObject.this.zzcp.addAll(collection);
            return this;
        }

        public final Builder addImageModuleDataMainImageUri(UriData uriData) {
            LoyaltyWalletObject.this.zzcp.add(uriData);
            return this;
        }

        public final Builder addTextModulesData(Collection<TextModuleData> collection) {
            LoyaltyWalletObject.this.zzcq.addAll(collection);
            return this;
        }

        public final Builder addTextModuleData(TextModuleData textModuleData) {
            LoyaltyWalletObject.this.zzcq.add(textModuleData);
            return this;
        }

        public final Builder addLinksModuleDataUris(Collection<UriData> collection) {
            LoyaltyWalletObject.this.zzcr.addAll(collection);
            return this;
        }

        public final Builder addLinksModuleDataUri(UriData uriData) {
            LoyaltyWalletObject.this.zzcr.add(uriData);
            return this;
        }

        public final Builder setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
            LoyaltyWalletObject.this.zzcs = loyaltyPoints;
            return this;
        }

        public final LoyaltyWalletObject build() {
            return LoyaltyWalletObject.this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzby, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzbz, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzca, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzcb, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzcc, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzcd, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzce, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzcf, false);
        SafeParcelWriter.writeString(parcel, 10, this.zzcg, false);
        SafeParcelWriter.writeString(parcel, 11, this.zzch, false);
        SafeParcelWriter.writeInt(parcel, 12, this.state);
        SafeParcelWriter.writeTypedList(parcel, 13, this.zzci, false);
        SafeParcelWriter.writeParcelable(parcel, 14, this.zzcj, i, false);
        SafeParcelWriter.writeTypedList(parcel, 15, this.zzck, false);
        SafeParcelWriter.writeString(parcel, 16, this.zzcl, false);
        SafeParcelWriter.writeString(parcel, 17, this.zzcm, false);
        SafeParcelWriter.writeTypedList(parcel, 18, this.zzcn, false);
        SafeParcelWriter.writeBoolean(parcel, 19, this.zzco);
        SafeParcelWriter.writeTypedList(parcel, 20, this.zzcp, false);
        SafeParcelWriter.writeTypedList(parcel, 21, this.zzcq, false);
        SafeParcelWriter.writeTypedList(parcel, 22, this.zzcr, false);
        SafeParcelWriter.writeParcelable(parcel, 23, this.zzcs, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    LoyaltyWalletObject(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, ArrayList<WalletObjectMessage> arrayList, TimeInterval timeInterval, ArrayList<LatLng> arrayList2, String str11, String str12, ArrayList<LabelValueRow> arrayList3, boolean z, ArrayList<UriData> arrayList4, ArrayList<TextModuleData> arrayList5, ArrayList<UriData> arrayList6, LoyaltyPoints loyaltyPoints) {
        this.zzby = str;
        this.zzbz = str2;
        this.zzca = str3;
        this.zzcb = str4;
        this.zzcc = str5;
        this.zzcd = str6;
        this.zzce = str7;
        this.zzcf = str8;
        this.zzcg = str9;
        this.zzch = str10;
        this.state = i;
        this.zzci = arrayList;
        this.zzcj = timeInterval;
        this.zzck = arrayList2;
        this.zzcl = str11;
        this.zzcm = str12;
        this.zzcn = arrayList3;
        this.zzco = z;
        this.zzcp = arrayList4;
        this.zzcq = arrayList5;
        this.zzcr = arrayList6;
        this.zzcs = loyaltyPoints;
    }

    LoyaltyWalletObject() {
        this.zzci = ArrayUtils.newArrayList();
        this.zzck = ArrayUtils.newArrayList();
        this.zzcn = ArrayUtils.newArrayList();
        this.zzcp = ArrayUtils.newArrayList();
        this.zzcq = ArrayUtils.newArrayList();
        this.zzcr = ArrayUtils.newArrayList();
    }

    public final String getId() {
        return this.zzby;
    }

    public final String getAccountId() {
        return this.zzbz;
    }

    public final String getIssuerName() {
        return this.zzca;
    }

    public final String getProgramName() {
        return this.zzcb;
    }

    public final String getAccountName() {
        return this.zzcc;
    }

    public final String getBarcodeAlternateText() {
        return this.zzcd;
    }

    public final String getBarcodeType() {
        return this.zzce;
    }

    public final String getBarcodeValue() {
        return this.zzcf;
    }

    @Deprecated
    public final String getBarcodeLabel() {
        return this.zzcg;
    }

    public final String getClassId() {
        return this.zzch;
    }

    public final int getState() {
        return this.state;
    }

    public final ArrayList<WalletObjectMessage> getMessages() {
        return this.zzci;
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzcj;
    }

    public final ArrayList<LatLng> getLocations() {
        return this.zzck;
    }

    @Deprecated
    public final String getInfoModuleDataHexFontColor() {
        return this.zzcl;
    }

    @Deprecated
    public final String getInfoModuleDataHexBackgroundColor() {
        return this.zzcm;
    }

    public final ArrayList<LabelValueRow> getInfoModuleDataLabelValueRows() {
        return this.zzcn;
    }

    public final boolean getInfoModuleDataShowLastUpdateTime() {
        return this.zzco;
    }

    public final ArrayList<UriData> getImageModuleDataMainImageUris() {
        return this.zzcp;
    }

    public final ArrayList<TextModuleData> getTextModulesData() {
        return this.zzcq;
    }

    public final ArrayList<UriData> getLinksModuleDataUris() {
        return this.zzcr;
    }

    public final LoyaltyPoints getLoyaltyPoints() {
        return this.zzcs;
    }
}
