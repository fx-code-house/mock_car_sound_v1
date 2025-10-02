package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.wobs.CommonWalletObject;
import com.google.android.gms.wallet.wobs.LabelValueRow;
import com.google.android.gms.wallet.wobs.TextModuleData;
import com.google.android.gms.wallet.wobs.TimeInterval;
import com.google.android.gms.wallet.wobs.UriData;
import com.google.android.gms.wallet.wobs.WalletObjectMessage;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public final class OfferWalletObject extends AbstractSafeParcelable {
    public static final Parcelable.Creator<OfferWalletObject> CREATOR = new zzs();
    private final int versionCode;
    CommonWalletObject zzbh;
    String zzby;
    String zzcw;

    public static Builder newBuilder() {
        return new Builder();
    }

    public final int getVersionCode() {
        return this.versionCode;
    }

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class Builder {
        private CommonWalletObject.zza zzbo;

        private Builder() {
            this.zzbo = CommonWalletObject.zze();
        }

        public final Builder setId(String str) {
            this.zzbo.zza(str);
            OfferWalletObject.this.zzby = str;
            return this;
        }

        public final Builder setRedemptionCode(String str) {
            OfferWalletObject.this.zzcw = str;
            return this;
        }

        public final Builder setIssuerName(String str) {
            this.zzbo.zzd(str);
            return this;
        }

        public final Builder setTitle(String str) {
            this.zzbo.zzc(str);
            return this;
        }

        public final Builder setBarcodeAlternateText(String str) {
            this.zzbo.zze(str);
            return this;
        }

        public final Builder setBarcodeType(String str) {
            this.zzbo.zzf(str);
            return this;
        }

        public final Builder setBarcodeValue(String str) {
            this.zzbo.zzg(str);
            return this;
        }

        @Deprecated
        public final Builder setBarcodeLabel(String str) {
            this.zzbo.zzh(str);
            return this;
        }

        public final Builder setClassId(String str) {
            this.zzbo.zzb(str);
            return this;
        }

        public final Builder setState(int i) {
            this.zzbo.zza(i);
            return this;
        }

        public final Builder addMessages(Collection<WalletObjectMessage> collection) {
            this.zzbo.zza(collection);
            return this;
        }

        public final Builder addMessage(WalletObjectMessage walletObjectMessage) {
            this.zzbo.zza(walletObjectMessage);
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            this.zzbo.zza(timeInterval);
            return this;
        }

        public final Builder addLocations(Collection<LatLng> collection) {
            this.zzbo.zzb(collection);
            return this;
        }

        public final Builder addLocation(LatLng latLng) {
            this.zzbo.zza(latLng);
            return this;
        }

        @Deprecated
        public final Builder setInfoModuleDataHexFontColor(String str) {
            this.zzbo.zzi(str);
            return this;
        }

        @Deprecated
        public final Builder setInfoModuleDataHexBackgroundColor(String str) {
            this.zzbo.zzj(str);
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRows(Collection<LabelValueRow> collection) {
            this.zzbo.zzc(collection);
            return this;
        }

        public final Builder addInfoModuleDataLabelValueRow(LabelValueRow labelValueRow) {
            this.zzbo.zza(labelValueRow);
            return this;
        }

        public final Builder setInfoModuleDataShowLastUpdateTime(boolean z) {
            this.zzbo.zza(z);
            return this;
        }

        public final Builder addImageModuleDataMainImageUris(Collection<UriData> collection) {
            this.zzbo.zzd(collection);
            return this;
        }

        public final Builder addImageModuleDataMainImageUri(UriData uriData) {
            this.zzbo.zza(uriData);
            return this;
        }

        public final Builder addTextModulesData(Collection<TextModuleData> collection) {
            this.zzbo.zze(collection);
            return this;
        }

        public final Builder addTextModuleData(TextModuleData textModuleData) {
            this.zzbo.zza(textModuleData);
            return this;
        }

        public final Builder addLinksModuleDataUris(Collection<UriData> collection) {
            this.zzbo.zzf(collection);
            return this;
        }

        public final Builder addLinksModuleDataUri(UriData uriData) {
            this.zzbo.zzb(uriData);
            return this;
        }

        public final OfferWalletObject build() {
            OfferWalletObject.this.zzbh = this.zzbo.zzf();
            return OfferWalletObject.this;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getVersionCode());
        SafeParcelWriter.writeString(parcel, 2, this.zzby, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzcw, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbh, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    OfferWalletObject(int i, String str, String str2, CommonWalletObject commonWalletObject) {
        this.versionCode = i;
        this.zzcw = str2;
        if (i < 3) {
            this.zzbh = CommonWalletObject.zze().zza(str).zzf();
        } else {
            this.zzbh = commonWalletObject;
        }
    }

    OfferWalletObject() {
        this.versionCode = 3;
    }

    public final String getId() {
        return this.zzbh.getId();
    }

    public final String getRedemptionCode() {
        return this.zzcw;
    }

    public final String getClassId() {
        return this.zzbh.getClassId();
    }

    public final String getTitle() {
        return this.zzbh.getName();
    }

    public final String getIssuerName() {
        return this.zzbh.getIssuerName();
    }

    public final String getBarcodeAlternateText() {
        return this.zzbh.getBarcodeAlternateText();
    }

    public final String getBarcodeType() {
        return this.zzbh.getBarcodeType();
    }

    public final String getBarcodeValue() {
        return this.zzbh.getBarcodeValue();
    }

    @Deprecated
    public final String getBarcodeLabel() {
        return this.zzbh.getBarcodeLabel();
    }

    public final int getState() {
        return this.zzbh.getState();
    }

    public final ArrayList<WalletObjectMessage> getMessages() {
        return this.zzbh.getMessages();
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzbh.getValidTimeInterval();
    }

    public final ArrayList<LatLng> getLocations() {
        return this.zzbh.getLocations();
    }

    @Deprecated
    public final String getInfoModuleDataHexFontColor() {
        return this.zzbh.getInfoModuleDataHexFontColor();
    }

    @Deprecated
    public final String getInfoModuleDataHexBackgroundColor() {
        return this.zzbh.getInfoModuleDataHexBackgroundColor();
    }

    public final ArrayList<LabelValueRow> getInfoModuleDataLabelValueRows() {
        return this.zzbh.getInfoModuleDataLabelValueRows();
    }

    public final boolean getInfoModuleDataShowLastUpdateTime() {
        return this.zzbh.getInfoModuleDataShowLastUpdateTime();
    }

    public final ArrayList<UriData> getImageModuleDataMainImageUris() {
        return this.zzbh.getImageModuleDataMainImageUris();
    }

    public final ArrayList<TextModuleData> getTextModulesData() {
        return this.zzbh.getTextModulesData();
    }

    public final ArrayList<UriData> getLinksModuleDataUris() {
        return this.zzbh.getLinksModuleDataUris();
    }
}
