package com.google.android.gms.internal.icing;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.icing.zzal;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.zip.CRC32;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzw extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzw> CREATOR = new zzy();
    private final zzi zzaj;
    private final long zzak;
    private int zzal;
    private final String zzam;
    private final zzh zzan;
    private final boolean zzao;
    private int zzap;
    private int zzaq;
    private final String zzar;

    public zzw(String str, Intent intent, String str2, Uri uri, String str3, List<AppIndexApi.AppIndexingLink> list, int i) {
        this(zza(str, intent), System.currentTimeMillis(), 0, null, zza(intent, str2, uri, null, list).zzb(), false, -1, 1, null);
    }

    zzw(zzi zziVar, long j, int i, String str, zzh zzhVar, boolean z, int i2, int i3, String str2) {
        this.zzaj = zziVar;
        this.zzak = j;
        this.zzal = i;
        this.zzam = str;
        this.zzan = zzhVar;
        this.zzao = z;
        this.zzap = i2;
        this.zzaq = i3;
        this.zzar = str2;
    }

    public static zzi zza(String str, Intent intent) {
        return new zzi(str, "", zza(intent));
    }

    private static zzk zza(String str, String str2) {
        return new zzk(str2, new zzs(str).zzb(true).zzc(), str);
    }

    private static String zza(Intent intent) {
        String uri = intent.toUri(1);
        CRC32 crc32 = new CRC32();
        try {
            crc32.update(uri.getBytes("UTF-8"));
            return Long.toHexString(crc32.getValue());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static zzg zza(Intent intent, String str, Uri uri, String str2, List<AppIndexApi.AppIndexingLink> list) {
        String string;
        zzg zzgVar = new zzg();
        zzgVar.zza(new zzk(str, new zzs("title").zzc(true).zzd(AppMeasurementSdk.ConditionalUserProperty.NAME).zzc(), "text1"));
        if (uri != null) {
            zzgVar.zza(new zzk(uri.toString(), new zzs("web_url").zzb(true).zzd(ImagesContract.URL).zzc()));
        }
        if (list != null) {
            zzal.zza.C0021zza c0021zzaZzf = zzal.zza.zzf();
            int size = list.size();
            zzal.zza.zzb[] zzbVarArr = new zzal.zza.zzb[size];
            for (int i = 0; i < size; i++) {
                zzal.zza.zzb.C0022zza c0022zzaZzh = zzal.zza.zzb.zzh();
                AppIndexApi.AppIndexingLink appIndexingLink = list.get(i);
                c0022zzaZzh.zze(appIndexingLink.appIndexingUrl.toString()).zzd(appIndexingLink.viewId);
                if (appIndexingLink.webUrl != null) {
                    c0022zzaZzh.zzf(appIndexingLink.webUrl.toString());
                }
                zzbVarArr[i] = (zzal.zza.zzb) ((zzdx) c0022zzaZzh.zzbx());
            }
            c0021zzaZzf.zza(Arrays.asList(zzbVarArr));
            zzgVar.zza(new zzk(((zzal.zza) ((zzdx) c0021zzaZzf.zzbx())).toByteArray(), new zzs("outlinks").zzb(true).zzd(".private:outLinks").zzc("blob").zzc()));
        }
        String action = intent.getAction();
        if (action != null) {
            zzgVar.zza(zza("intent_action", action));
        }
        String dataString = intent.getDataString();
        if (dataString != null) {
            zzgVar.zza(zza("intent_data", dataString));
        }
        ComponentName component = intent.getComponent();
        if (component != null) {
            zzgVar.zza(zza("intent_activity", component.getClassName()));
        }
        Bundle extras = intent.getExtras();
        if (extras != null && (string = extras.getString("intent_extra_data_key")) != null) {
            zzgVar.zza(zza("intent_extra_data", string));
        }
        return zzgVar.zza(str2).zza(true);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzaj, i, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzak);
        SafeParcelWriter.writeInt(parcel, 3, this.zzal);
        SafeParcelWriter.writeString(parcel, 4, this.zzam, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzan, i, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzao);
        SafeParcelWriter.writeInt(parcel, 7, this.zzap);
        SafeParcelWriter.writeInt(parcel, 8, this.zzaq);
        SafeParcelWriter.writeString(parcel, 9, this.zzar, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String toString() {
        return String.format(Locale.US, "UsageInfo[documentId=%s, timestamp=%d, usageType=%d, status=%d]", this.zzaj, Long.valueOf(this.zzak), Integer.valueOf(this.zzal), Integer.valueOf(this.zzaq));
    }
}
