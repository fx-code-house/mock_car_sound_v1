package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
/* loaded from: classes2.dex */
public class CommonWalletObject extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CommonWalletObject> CREATOR = new zzb();
    String name;
    int state;
    String zzby;
    String zzca;
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

    /* compiled from: com.google.android.gms:play-services-wallet@@18.0.0 */
    public final class zza {
        private zza() {
        }

        public final zza zza(String str) {
            CommonWalletObject.this.zzby = str;
            return this;
        }

        public final zza zzb(String str) {
            CommonWalletObject.this.zzch = str;
            return this;
        }

        public final zza zzc(String str) {
            CommonWalletObject.this.name = str;
            return this;
        }

        public final zza zzd(String str) {
            CommonWalletObject.this.zzca = str;
            return this;
        }

        public final zza zze(String str) {
            CommonWalletObject.this.zzcd = str;
            return this;
        }

        public final zza zzf(String str) {
            CommonWalletObject.this.zzce = str;
            return this;
        }

        public final zza zzg(String str) {
            CommonWalletObject.this.zzcf = str;
            return this;
        }

        @Deprecated
        public final zza zzh(String str) {
            CommonWalletObject.this.zzcg = str;
            return this;
        }

        public final zza zza(int i) {
            CommonWalletObject.this.state = i;
            return this;
        }

        public final zza zza(Collection<WalletObjectMessage> collection) {
            CommonWalletObject.this.zzci.addAll(collection);
            return this;
        }

        public final zza zza(WalletObjectMessage walletObjectMessage) {
            CommonWalletObject.this.zzci.add(walletObjectMessage);
            return this;
        }

        public final zza zza(TimeInterval timeInterval) {
            CommonWalletObject.this.zzcj = timeInterval;
            return this;
        }

        public final zza zzb(Collection<LatLng> collection) {
            CommonWalletObject.this.zzck.addAll(collection);
            return this;
        }

        public final zza zza(LatLng latLng) {
            CommonWalletObject.this.zzck.add(latLng);
            return this;
        }

        @Deprecated
        public final zza zzi(String str) {
            CommonWalletObject.this.zzcl = str;
            return this;
        }

        @Deprecated
        public final zza zzj(String str) {
            CommonWalletObject.this.zzcm = str;
            return this;
        }

        public final zza zzc(Collection<LabelValueRow> collection) {
            CommonWalletObject.this.zzcn.addAll(collection);
            return this;
        }

        public final zza zza(LabelValueRow labelValueRow) {
            CommonWalletObject.this.zzcn.add(labelValueRow);
            return this;
        }

        public final zza zza(boolean z) {
            CommonWalletObject.this.zzco = z;
            return this;
        }

        public final zza zzd(Collection<UriData> collection) {
            CommonWalletObject.this.zzcp.addAll(collection);
            return this;
        }

        public final zza zza(UriData uriData) {
            CommonWalletObject.this.zzcp.add(uriData);
            return this;
        }

        public final zza zze(Collection<TextModuleData> collection) {
            CommonWalletObject.this.zzcq.addAll(collection);
            return this;
        }

        public final zza zza(TextModuleData textModuleData) {
            CommonWalletObject.this.zzcq.add(textModuleData);
            return this;
        }

        public final zza zzf(Collection<UriData> collection) {
            CommonWalletObject.this.zzcr.addAll(collection);
            return this;
        }

        public final zza zzb(UriData uriData) {
            CommonWalletObject.this.zzcr.add(uriData);
            return this;
        }

        public final CommonWalletObject zzf() {
            return CommonWalletObject.this;
        }
    }

    public static zza zze() {
        return new zza();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzby, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzch, false);
        SafeParcelWriter.writeString(parcel, 4, this.name, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzca, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzcd, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzce, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzcf, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzcg, false);
        SafeParcelWriter.writeInt(parcel, 10, this.state);
        SafeParcelWriter.writeTypedList(parcel, 11, this.zzci, false);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzcj, i, false);
        SafeParcelWriter.writeTypedList(parcel, 13, this.zzck, false);
        SafeParcelWriter.writeString(parcel, 14, this.zzcl, false);
        SafeParcelWriter.writeString(parcel, 15, this.zzcm, false);
        SafeParcelWriter.writeTypedList(parcel, 16, this.zzcn, false);
        SafeParcelWriter.writeBoolean(parcel, 17, this.zzco);
        SafeParcelWriter.writeTypedList(parcel, 18, this.zzcp, false);
        SafeParcelWriter.writeTypedList(parcel, 19, this.zzcq, false);
        SafeParcelWriter.writeTypedList(parcel, 20, this.zzcr, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    CommonWalletObject(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, ArrayList<WalletObjectMessage> arrayList, TimeInterval timeInterval, ArrayList<LatLng> arrayList2, String str9, String str10, ArrayList<LabelValueRow> arrayList3, boolean z, ArrayList<UriData> arrayList4, ArrayList<TextModuleData> arrayList5, ArrayList<UriData> arrayList6) {
        this.zzby = str;
        this.zzch = str2;
        this.name = str3;
        this.zzca = str4;
        this.zzcd = str5;
        this.zzce = str6;
        this.zzcf = str7;
        this.zzcg = str8;
        this.state = i;
        this.zzci = arrayList;
        this.zzcj = timeInterval;
        this.zzck = arrayList2;
        this.zzcl = str9;
        this.zzcm = str10;
        this.zzcn = arrayList3;
        this.zzco = z;
        this.zzcp = arrayList4;
        this.zzcq = arrayList5;
        this.zzcr = arrayList6;
    }

    CommonWalletObject() {
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

    public final String getClassId() {
        return this.zzch;
    }

    public final String getName() {
        return this.name;
    }

    public final String getIssuerName() {
        return this.zzca;
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
}
