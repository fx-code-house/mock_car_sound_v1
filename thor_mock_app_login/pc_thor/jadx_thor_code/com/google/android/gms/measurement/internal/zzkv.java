package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzlz;
import com.google.android.gms.internal.measurement.zznv;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import javax.security.auth.x500.X500Principal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
/* loaded from: classes2.dex */
public final class zzkv extends zzgq {
    private static final String[] zza = {"firebase_", "google_", "ga_"};
    private static final String[] zzb = {"_err"};
    private SecureRandom zzc;
    private final AtomicLong zzd;
    private int zze;
    private Integer zzf;

    zzkv(zzfu zzfuVar) {
        super(zzfuVar);
        this.zzf = null;
        this.zzd = new AtomicLong(0L);
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    protected final boolean zzd() {
        return true;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    protected final void g_() throws IllegalStateException {
        zzc();
        SecureRandom secureRandom = new SecureRandom();
        long jNextLong = secureRandom.nextLong();
        if (jNextLong == 0) {
            jNextLong = secureRandom.nextLong();
            if (jNextLong == 0) {
                zzq().zzh().zza("Utils falling back to Random for random id");
            }
        }
        this.zzd.set(jNextLong);
    }

    public final long zzf() {
        long andIncrement;
        long j;
        if (this.zzd.get() == 0) {
            synchronized (this.zzd) {
                long jNextLong = new Random(System.nanoTime() ^ zzl().currentTimeMillis()).nextLong();
                int i = this.zze + 1;
                this.zze = i;
                j = jNextLong + i;
            }
            return j;
        }
        synchronized (this.zzd) {
            this.zzd.compareAndSet(-1L, 1L);
            andIncrement = this.zzd.getAndIncrement();
        }
        return andIncrement;
    }

    final SecureRandom zzg() {
        zzc();
        if (this.zzc == null) {
            this.zzc = new SecureRandom();
        }
        return this.zzc;
    }

    static boolean zza(String str) {
        Preconditions.checkNotEmpty(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    final Bundle zza(Uri uri) throws IllegalStateException {
        String queryParameter;
        String queryParameter2;
        String queryParameter3;
        String queryParameter4;
        if (uri == null) {
            return null;
        }
        try {
            if (uri.isHierarchical()) {
                queryParameter = uri.getQueryParameter("utm_campaign");
                queryParameter2 = uri.getQueryParameter("utm_source");
                queryParameter3 = uri.getQueryParameter("utm_medium");
                queryParameter4 = uri.getQueryParameter("gclid");
            } else {
                queryParameter = null;
                queryParameter2 = null;
                queryParameter3 = null;
                queryParameter4 = null;
            }
            if (TextUtils.isEmpty(queryParameter) && TextUtils.isEmpty(queryParameter2) && TextUtils.isEmpty(queryParameter3) && TextUtils.isEmpty(queryParameter4)) {
                return null;
            }
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString("campaign", queryParameter);
            }
            if (!TextUtils.isEmpty(queryParameter2)) {
                bundle.putString("source", queryParameter2);
            }
            if (!TextUtils.isEmpty(queryParameter3)) {
                bundle.putString("medium", queryParameter3);
            }
            if (!TextUtils.isEmpty(queryParameter4)) {
                bundle.putString("gclid", queryParameter4);
            }
            String queryParameter5 = uri.getQueryParameter("utm_term");
            if (!TextUtils.isEmpty(queryParameter5)) {
                bundle.putString(FirebaseAnalytics.Param.TERM, queryParameter5);
            }
            String queryParameter6 = uri.getQueryParameter("utm_content");
            if (!TextUtils.isEmpty(queryParameter6)) {
                bundle.putString(FirebaseAnalytics.Param.CONTENT, queryParameter6);
            }
            String queryParameter7 = uri.getQueryParameter(FirebaseAnalytics.Param.ACLID);
            if (!TextUtils.isEmpty(queryParameter7)) {
                bundle.putString(FirebaseAnalytics.Param.ACLID, queryParameter7);
            }
            String queryParameter8 = uri.getQueryParameter(FirebaseAnalytics.Param.CP1);
            if (!TextUtils.isEmpty(queryParameter8)) {
                bundle.putString(FirebaseAnalytics.Param.CP1, queryParameter8);
            }
            String queryParameter9 = uri.getQueryParameter("anid");
            if (!TextUtils.isEmpty(queryParameter9)) {
                bundle.putString("anid", queryParameter9);
            }
            return bundle;
        } catch (UnsupportedOperationException e) {
            zzq().zzh().zza("Install referrer url isn't a hierarchical URI", e);
            return null;
        }
    }

    static boolean zza(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    final boolean zza(String str, String str2) throws IllegalStateException {
        if (str2 == null) {
            zzq().zzg().zza("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzq().zzg().zza("Name is required and can't be empty. Type", str);
            return false;
        }
        int iCodePointAt = str2.codePointAt(0);
        if (!Character.isLetter(iCodePointAt)) {
            zzq().zzg().zza("Name must start with a letter. Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int iCharCount = Character.charCount(iCodePointAt);
        while (iCharCount < length) {
            int iCodePointAt2 = str2.codePointAt(iCharCount);
            if (iCodePointAt2 != 95 && !Character.isLetterOrDigit(iCodePointAt2)) {
                zzq().zzg().zza("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return true;
    }

    final boolean zzb(String str, String str2) throws IllegalStateException {
        if (str2 == null) {
            zzq().zzg().zza("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzq().zzg().zza("Name is required and can't be empty. Type", str);
            return false;
        }
        int iCodePointAt = str2.codePointAt(0);
        if (!Character.isLetter(iCodePointAt) && iCodePointAt != 95) {
            zzq().zzg().zza("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int iCharCount = Character.charCount(iCodePointAt);
        while (iCharCount < length) {
            int iCodePointAt2 = str2.codePointAt(iCharCount);
            if (iCodePointAt2 != 95 && !Character.isLetterOrDigit(iCodePointAt2)) {
                zzq().zzg().zza("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return true;
    }

    final boolean zza(String str, String[] strArr, String[] strArr2, String str2) throws IllegalStateException {
        boolean z;
        if (str2 == null) {
            zzq().zzg().zza("Name is required and can't be null. Type", str);
            return false;
        }
        Preconditions.checkNotNull(str2);
        String[] strArr3 = zza;
        int length = strArr3.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (str2.startsWith(strArr3[i])) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            zzq().zzg().zza("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr == null || !zza(str2, strArr) || (strArr2 != null && zza(str2, strArr2))) {
            return true;
        }
        zzq().zzg().zza("Name is reserved. Type, name", str, str2);
        return false;
    }

    final boolean zza(String str, String[] strArr, String str2) {
        return zza(str, strArr, (String[]) null, str2);
    }

    final boolean zza(String str, int i, String str2) throws IllegalStateException {
        if (str2 == null) {
            zzq().zzg().zza("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        }
        zzq().zzg().zza("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
        return false;
    }

    final int zza(String str, boolean z) {
        if (!zzb("event", str)) {
            return 2;
        }
        if (z) {
            if (!zza("event", zzgv.zza, zzgv.zzb, str)) {
                return 13;
            }
        } else if (!zza("event", zzgv.zza, str)) {
            return 13;
        }
        return !zza("event", 40, str) ? 2 : 0;
    }

    final int zzb(String str) {
        if (!zzb("user property", str)) {
            return 6;
        }
        if (zza("user property", zzgx.zza, str)) {
            return !zza("user property", 24, str) ? 6 : 0;
        }
        return 15;
    }

    private final int zzg(String str) {
        if (!zza("event param", str)) {
            return 3;
        }
        if (zza("event param", (String[]) null, str)) {
            return !zza("event param", 40, str) ? 3 : 0;
        }
        return 14;
    }

    private final int zzh(String str) {
        if (!zzb("event param", str)) {
            return 3;
        }
        if (zza("event param", (String[]) null, str)) {
            return !zza("event param", 40, str) ? 3 : 0;
        }
        return 14;
    }

    static boolean zza(Object obj) {
        return (obj instanceof Parcelable[]) || (obj instanceof ArrayList) || (obj instanceof Bundle);
    }

    final boolean zza(String str, String str2, int i, Object obj) throws IllegalStateException {
        if (obj != null && !(obj instanceof Long) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Byte) && !(obj instanceof Short) && !(obj instanceof Boolean) && !(obj instanceof Double)) {
            if (!(obj instanceof String) && !(obj instanceof Character) && !(obj instanceof CharSequence)) {
                return false;
            }
            String strValueOf = String.valueOf(obj);
            if (strValueOf.codePointCount(0, strValueOf.length()) > i) {
                zzq().zzj().zza("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(strValueOf.length()));
                return false;
            }
        }
        return true;
    }

    static boolean zza(Bundle bundle, int i) {
        int i2 = 0;
        if (bundle.size() <= i) {
            return false;
        }
        for (String str : new TreeSet(bundle.keySet())) {
            i2++;
            if (i2 > i) {
                bundle.remove(str);
            }
        }
        return true;
    }

    private final void zza(String str, String str2, String str3, Bundle bundle, List<String> list, boolean z) throws IllegalStateException {
        int iZzg;
        String str4;
        int iZza;
        if (bundle == null) {
            return;
        }
        int i = 0;
        for (String str5 : new TreeSet(bundle.keySet())) {
            if (list == null || !list.contains(str5)) {
                iZzg = z ? zzg(str5) : 0;
                if (iZzg == 0) {
                    iZzg = zzh(str5);
                }
            } else {
                iZzg = 0;
            }
            if (iZzg != 0) {
                zza(bundle, iZzg, str5, str5, iZzg == 3 ? str5 : null);
                bundle.remove(str5);
            } else {
                if (zza(bundle.get(str5))) {
                    zzq().zzj().zza("Nested Bundle parameters are not allowed; discarded. event name, param name, child param name", str2, str3, str5);
                    iZza = 22;
                    str4 = str5;
                } else {
                    str4 = str5;
                    iZza = zza(str, str2, str5, bundle.get(str5), bundle, list, z, false);
                }
                if (iZza != 0 && !"_ev".equals(str4)) {
                    zza(bundle, iZza, str4, str4, bundle.get(str4));
                    bundle.remove(str4);
                } else if (zza(str4) && !zza(str4, zzgu.zzd) && (i = i + 1) > 0) {
                    zzq().zzg().zza("Item cannot contain custom parameters", zzn().zza(str2), zzn().zza(bundle));
                    zzb(bundle, 23);
                    bundle.remove(str4);
                }
            }
        }
    }

    final boolean zza(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str)) {
            if (zzi(str)) {
                return true;
            }
            if (this.zzy.zzk()) {
                zzq().zzg().zza("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", zzeq.zza(str));
            }
            return false;
        }
        if (zznv.zzb() && zzs().zza(zzas.zzbi) && !TextUtils.isEmpty(str3)) {
            return true;
        }
        if (!TextUtils.isEmpty(str2)) {
            if (zzi(str2)) {
                return true;
            }
            zzq().zzg().zza("Invalid admob_app_id. Analytics disabled.", zzeq.zza(str2));
            return false;
        }
        if (this.zzy.zzk()) {
            zzq().zzg().zza("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
        }
        return false;
    }

    static boolean zza(String str, String str2, String str3, String str4) {
        boolean zIsEmpty = TextUtils.isEmpty(str);
        boolean zIsEmpty2 = TextUtils.isEmpty(str2);
        if (!zIsEmpty && !zIsEmpty2) {
            return !str.equals(str2);
        }
        if (zIsEmpty && zIsEmpty2) {
            return (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) ? !TextUtils.isEmpty(str4) : !str3.equals(str4);
        }
        if (zIsEmpty || !zIsEmpty2) {
            return TextUtils.isEmpty(str3) || !str3.equals(str4);
        }
        if (TextUtils.isEmpty(str4)) {
            return false;
        }
        return TextUtils.isEmpty(str3) || !str3.equals(str4);
    }

    private static boolean zzi(String str) {
        Preconditions.checkNotNull(str);
        return str.matches("^(1:\\d+:android:[a-f0-9]+|ca-app-pub-.*)$");
    }

    private final Object zza(int i, Object obj, boolean z, boolean z2) {
        Bundle bundleZza;
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf(((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf(((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1L : 0L);
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            return zza(String.valueOf(obj), i, z);
        }
        if (!z2 || (!(obj instanceof Bundle[]) && !(obj instanceof Parcelable[]))) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Parcelable parcelable : (Parcelable[]) obj) {
            if ((parcelable instanceof Bundle) && (bundleZza = zza((Bundle) parcelable)) != null && !bundleZza.isEmpty()) {
                arrayList.add(bundleZza);
            }
        }
        return arrayList.toArray(new Bundle[arrayList.size()]);
    }

    public static String zza(String str, int i, boolean z) {
        if (str == null) {
            return null;
        }
        if (str.codePointCount(0, str.length()) <= i) {
            return str;
        }
        if (z) {
            return String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...");
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final int zza(java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.Object r20, android.os.Bundle r21, java.util.List<java.lang.String> r22, boolean r23, boolean r24) throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkv.zza(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, android.os.Bundle, java.util.List, boolean, boolean):int");
    }

    final Object zza(String str, Object obj) {
        if ("_ev".equals(str)) {
            return zza(256, obj, true, true);
        }
        return zza(zzd(str) ? 256 : 100, obj, false, true);
    }

    static Bundle[] zzb(Object obj) {
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        }
        if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            return (Bundle[]) Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
        }
        if (!(obj instanceof ArrayList)) {
            return null;
        }
        ArrayList arrayList = (ArrayList) obj;
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    final Bundle zza(String str, String str2, Bundle bundle, List<String> list, boolean z, boolean z2) throws IllegalStateException {
        Set<String> setKeySet;
        int iZzg;
        int i;
        zzkv zzkvVar = this;
        boolean zZza = zza(str2, zzgv.zzd);
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        int iZzd = zzs().zzd();
        if (zzs().zze(str, zzas.zzay)) {
            setKeySet = new TreeSet<>(bundle.keySet());
        } else {
            setKeySet = bundle.keySet();
        }
        int i2 = 0;
        for (String str3 : setKeySet) {
            if (list == null || !list.contains(str3)) {
                iZzg = z ? zzkvVar.zzg(str3) : 0;
                if (iZzg == 0) {
                    iZzg = zzkvVar.zzh(str3);
                }
            } else {
                iZzg = 0;
            }
            if (iZzg != 0) {
                zza(bundle2, iZzg, str3, str3, iZzg == 3 ? str3 : null);
                bundle2.remove(str3);
                i = iZzd;
            } else {
                i = iZzd;
                int iZza = zza(str, str2, str3, bundle.get(str3), bundle2, list, z, zZza);
                if (iZza == 17) {
                    zza(bundle2, iZza, str3, str3, (Object) false);
                } else if (iZza != 0 && !"_ev".equals(str3)) {
                    zza(bundle2, iZza, iZza == 21 ? str2 : str3, str3, bundle.get(str3));
                    bundle2.remove(str3);
                }
                if (zza(str3)) {
                    int i3 = i2 + 1;
                    if (i3 > i) {
                        zzq().zzg().zza(new StringBuilder(48).append("Event can't contain more than ").append(i).append(" params").toString(), zzn().zza(str2), zzn().zza(bundle));
                        zzb(bundle2, 5);
                        bundle2.remove(str3);
                        i2 = i3;
                    } else {
                        i2 = i3;
                    }
                }
                iZzd = i;
                zzkvVar = this;
            }
            iZzd = i;
            zzkvVar = this;
        }
        return bundle2;
    }

    final void zza(zzeu zzeuVar, int i) {
        int i2 = 0;
        for (String str : new TreeSet(zzeuVar.zzb.keySet())) {
            if (zza(str) && (i2 = i2 + 1) > i) {
                zzq().zzg().zza(new StringBuilder(48).append("Event can't contain more than ").append(i).append(" params").toString(), zzn().zza(zzeuVar.zza), zzn().zza(zzeuVar.zzb));
                zzb(zzeuVar.zzb, 5);
                zzeuVar.zzb.remove(str);
            }
        }
    }

    final void zza(Bundle bundle, Bundle bundle2) {
        if (bundle2 == null) {
            return;
        }
        for (String str : bundle2.keySet()) {
            if (!bundle.containsKey(str)) {
                zzo().zza(bundle, str, bundle2.get(str));
            }
        }
    }

    private static void zza(Bundle bundle, int i, String str, String str2, Object obj) {
        if (zzb(bundle, i)) {
            bundle.putString("_ev", zza(str, 40, true));
            if (obj != null) {
                Preconditions.checkNotNull(bundle);
                if (obj != null) {
                    if ((obj instanceof String) || (obj instanceof CharSequence)) {
                        bundle.putLong("_el", String.valueOf(obj).length());
                    }
                }
            }
        }
    }

    private static boolean zzb(Bundle bundle, int i) {
        if (bundle == null || bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", i);
        return true;
    }

    private final int zzj(String str) {
        if ("_ldl".equals(str)) {
            return 2048;
        }
        if ("_id".equals(str)) {
            return 256;
        }
        return (zzs().zza(zzas.zzbg) && "_lgclid".equals(str)) ? 100 : 36;
    }

    final int zzb(String str, Object obj) {
        boolean zZza;
        if ("_ldl".equals(str)) {
            zZza = zza("user property referrer", str, zzj(str), obj);
        } else {
            zZza = zza("user property", str, zzj(str), obj);
        }
        return zZza ? 0 : 7;
    }

    final Object zzc(String str, Object obj) {
        if ("_ldl".equals(str)) {
            return zza(zzj(str), obj, true, false);
        }
        return zza(zzj(str), obj, false, false);
    }

    final void zza(Bundle bundle, String str, Object obj) throws IllegalStateException {
        if (bundle == null) {
            return;
        }
        if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof String) {
            bundle.putString(str, String.valueOf(obj));
            return;
        }
        if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (obj instanceof Bundle[]) {
            bundle.putParcelableArray(str, (Bundle[]) obj);
        } else if (str != null) {
            zzq().zzj().zza("Not putting event parameter. Invalid value type. name, type", zzn().zzb(str), obj != null ? obj.getClass().getSimpleName() : null);
        }
    }

    public final void zza(zzky zzkyVar, int i, String str, String str2, int i2) {
        zza(zzkyVar, (String) null, i, str, str2, i2);
    }

    final void zza(zzky zzkyVar, String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zzb(bundle, i);
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", i2);
        }
        if (zzlz.zzb() && zzs().zza(zzas.zzcn)) {
            zzkyVar.zza(str, bundle);
        } else {
            this.zzy.zzg().zza("auto", "_err", bundle);
        }
    }

    static MessageDigest zzh() throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        for (int i = 0; i < 2; i++) {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException unused) {
            }
            if (messageDigest != null) {
                return messageDigest;
            }
        }
        return null;
    }

    static long zza(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        int i = 0;
        Preconditions.checkState(bArr.length > 0);
        long j = 0;
        for (int length = bArr.length - 1; length >= 0 && length >= bArr.length - 8; length--) {
            j += (bArr[length] & 255) << i;
            i += 8;
        }
        return j;
    }

    static boolean zza(Context context, boolean z) {
        Preconditions.checkNotNull(context);
        return zzb(context, "com.google.android.gms.measurement.AppMeasurementJobService");
    }

    private static boolean zzb(Context context, String str) {
        ServiceInfo serviceInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 0)) != null) {
                if (serviceInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    final boolean zzc(String str) throws IllegalStateException {
        zzc();
        if (Wrappers.packageManager(zzm()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzq().zzv().zza("Permission not granted", str);
        return false;
    }

    static boolean zzd(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("_");
    }

    static boolean zzc(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null) {
            return false;
        }
        return str.equals(str2);
    }

    static boolean zza(Boolean bool, Boolean bool2) {
        if (bool == null && bool2 == null) {
            return true;
        }
        if (bool == null) {
            return false;
        }
        return bool.equals(bool2);
    }

    static boolean zza(List<String> list, List<String> list2) {
        if (list == null && list2 == null) {
            return true;
        }
        if (list == null) {
            return false;
        }
        return list.equals(list2);
    }

    final boolean zze(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return zzs().zzw().equals(str);
    }

    final Bundle zza(Bundle bundle) throws IllegalStateException {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object objZza = zza(str, bundle.get(str));
                if (objZza == null) {
                    zzq().zzj().zza("Param value can't be null", zzn().zzb(str));
                } else {
                    zza(bundle2, str, objZza);
                }
            }
        }
        return bundle2;
    }

    final zzaq zza(String str, String str2, Bundle bundle, String str3, long j, boolean z, boolean z2, boolean z3) throws IllegalStateException {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        if (zza(str2, z3) != 0) {
            zzq().zze().zza("Invalid conditional property event name", zzn().zzc(str2));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str3);
        Bundle bundleZza = zza(str, str2, bundle2, CollectionUtils.listOf("_o"), false, false);
        if (z) {
            bundleZza = zza(bundleZza);
        }
        return new zzaq(str2, new zzap(bundleZza), str3, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0076  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0069 -> B:18:0x0076). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final long zza(android.content.Context r7, java.lang.String r8) throws java.lang.IllegalStateException, java.security.NoSuchAlgorithmException {
        /*
            r6 = this;
            r6.zzc()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)
            android.content.pm.PackageManager r0 = r7.getPackageManager()
            java.security.MessageDigest r1 = zzh()
            r2 = -1
            if (r1 != 0) goto L23
            com.google.android.gms.measurement.internal.zzeq r7 = r6.zzq()
            com.google.android.gms.measurement.internal.zzes r7 = r7.zze()
            java.lang.String r8 = "Could not get MD5 instance"
            r7.zza(r8)
            goto L77
        L23:
            r4 = 0
            if (r0 == 0) goto L76
            boolean r8 = r6.zzc(r7, r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            if (r8 != 0) goto L76
            com.google.android.gms.common.wrappers.PackageManagerWrapper r7 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            android.content.Context r8 = r6.zzm()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            java.lang.String r8 = r8.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            r0 = 64
            android.content.pm.PackageInfo r7 = r7.getPackageInfo(r8, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            android.content.pm.Signature[] r8 = r7.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            if (r8 == 0) goto L5a
            android.content.pm.Signature[] r8 = r7.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            int r8 = r8.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            if (r8 <= 0) goto L5a
            android.content.pm.Signature[] r7 = r7.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            r8 = 0
            r7 = r7[r8]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            byte[] r7 = r7.toByteArray()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            byte[] r7 = r1.digest(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            long r2 = zza(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            goto L77
        L5a:
            com.google.android.gms.measurement.internal.zzeq r7 = r6.zzq()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            com.google.android.gms.measurement.internal.zzes r7 = r7.zzh()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            java.lang.String r8 = "Could not get signatures"
            r7.zza(r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L68
            goto L77
        L68:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzeq r8 = r6.zzq()
            com.google.android.gms.measurement.internal.zzes r8 = r8.zze()
            java.lang.String r0 = "Package name not found"
            r8.zza(r0, r7)
        L76:
            r2 = r4
        L77:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkv.zza(android.content.Context, java.lang.String):long");
    }

    private final boolean zzc(Context context, String str) throws IllegalStateException {
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 64);
            if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                return true;
            }
            return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
        } catch (PackageManager.NameNotFoundException e) {
            zzq().zze().zza("Package name not found", e);
            return true;
        } catch (CertificateException e2) {
            zzq().zze().zza("Error obtaining certificate", e2);
            return true;
        }
    }

    static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(parcelObtain, 0);
            return parcelObtain.marshall();
        } finally {
            parcelObtain.recycle();
        }
    }

    public static Bundle zzb(Bundle bundle) {
        if (bundle == null) {
            return new Bundle();
        }
        Bundle bundle2 = new Bundle(bundle);
        for (String str : bundle2.keySet()) {
            Object obj = bundle2.get(str);
            if (obj instanceof Bundle) {
                bundle2.putBundle(str, new Bundle((Bundle) obj));
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    while (i < parcelableArr.length) {
                        if (parcelableArr[i] instanceof Bundle) {
                            parcelableArr[i] = new Bundle((Bundle) parcelableArr[i]);
                        }
                        i++;
                    }
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    while (i < list.size()) {
                        Object obj2 = list.get(i);
                        if (obj2 instanceof Bundle) {
                            list.set(i, new Bundle((Bundle) obj2));
                        }
                        i++;
                    }
                }
            }
        }
        return bundle2;
    }

    private static boolean zza(String str, String[] strArr) {
        Preconditions.checkNotNull(strArr);
        for (String str2 : strArr) {
            if (zzc(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean zzf(String str) {
        for (String str2 : zzb) {
            if (str2.equals(str)) {
                return false;
            }
        }
        return true;
    }

    public final int zzi() {
        if (this.zzf == null) {
            this.zzf = Integer.valueOf(GoogleApiAvailabilityLight.getInstance().getApkVersion(zzm()) / 1000);
        }
        return this.zzf.intValue();
    }

    public final int zza(int i) {
        return GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(zzm(), 12451000);
    }

    public static long zza(long j, long j2) {
        return (j + (j2 * 60000)) / 86400000;
    }

    final void zza(Bundle bundle, long j) throws IllegalStateException {
        long j2 = bundle.getLong("_et");
        if (j2 != 0) {
            zzq().zzh().zza("Params already contained engagement", Long.valueOf(j2));
        }
        bundle.putLong("_et", j + j2);
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, String str) throws IllegalStateException {
        Bundle bundle = new Bundle();
        bundle.putString("r", str);
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning string value to wrapper", e);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, long j) throws IllegalStateException {
        Bundle bundle = new Bundle();
        bundle.putLong("r", j);
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning long value to wrapper", e);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, int i) throws IllegalStateException {
        Bundle bundle = new Bundle();
        bundle.putInt("r", i);
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning int value to wrapper", e);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, byte[] bArr) throws IllegalStateException {
        Bundle bundle = new Bundle();
        bundle.putByteArray("r", bArr);
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning byte array to wrapper", e);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, boolean z) throws IllegalStateException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("r", z);
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning boolean value to wrapper", e);
        }
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, Bundle bundle) throws IllegalStateException {
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning bundle value to wrapper", e);
        }
    }

    public static Bundle zza(List<zzku> list) {
        Bundle bundle = new Bundle();
        if (list == null) {
            return bundle;
        }
        for (zzku zzkuVar : list) {
            if (zzkuVar.zzd != null) {
                bundle.putString(zzkuVar.zza, zzkuVar.zzd);
            } else if (zzkuVar.zzc != null) {
                bundle.putLong(zzkuVar.zza, zzkuVar.zzc.longValue());
            } else if (zzkuVar.zzf != null) {
                bundle.putDouble(zzkuVar.zza, zzkuVar.zzf.doubleValue());
            }
        }
        return bundle;
    }

    public final void zza(com.google.android.gms.internal.measurement.zzw zzwVar, ArrayList<Bundle> arrayList) throws IllegalStateException {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("r", arrayList);
        try {
            zzwVar.zza(bundle);
        } catch (RemoteException e) {
            this.zzy.zzq().zzh().zza("Error returning bundle list to wrapper", e);
        }
    }

    public static ArrayList<Bundle> zzb(List<zzz> list) {
        if (list == null) {
            return new ArrayList<>(0);
        }
        ArrayList<Bundle> arrayList = new ArrayList<>(list.size());
        for (zzz zzzVar : list) {
            Bundle bundle = new Bundle();
            bundle.putString("app_id", zzzVar.zza);
            bundle.putString("origin", zzzVar.zzb);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, zzzVar.zzd);
            bundle.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, zzzVar.zzc.zza);
            zzgs.zza(bundle, zzzVar.zzc.zza());
            bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, zzzVar.zze);
            if (zzzVar.zzf != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, zzzVar.zzf);
            }
            if (zzzVar.zzg != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, zzzVar.zzg.zza);
                if (zzzVar.zzg.zzb != null) {
                    bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, zzzVar.zzg.zzb.zzb());
                }
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, zzzVar.zzh);
            if (zzzVar.zzi != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, zzzVar.zzi.zza);
                if (zzzVar.zzi.zzb != null) {
                    bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, zzzVar.zzi.zzb.zzb());
                }
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, zzzVar.zzc.zzb);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, zzzVar.zzj);
            if (zzzVar.zzk != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, zzzVar.zzk.zza);
                if (zzzVar.zzk.zzb != null) {
                    bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, zzzVar.zzk.zzb.zzb());
                }
            }
            arrayList.add(bundle);
        }
        return arrayList;
    }

    public final URL zza(long j, String str, String str2, long j2) {
        try {
            Preconditions.checkNotEmpty(str2);
            Preconditions.checkNotEmpty(str);
            String strConcat = String.format("https://www.googleadservices.com/pagead/conversion/app/deeplink?id_type=adid&sdk_version=%s&rdid=%s&bundleid=%s&retry=%s", String.format("v%s.%s", Long.valueOf(j), Integer.valueOf(zzi())), str2, str, Long.valueOf(j2));
            if (str.equals(zzs().zzx())) {
                strConcat = strConcat.concat("&ddl_test=1");
            }
            return new URL(strConcat);
        } catch (IllegalArgumentException | MalformedURLException e) {
            zzq().zze().zza("Failed to create BOW URL for Deferred Deep Link. exception", e.getMessage());
            return null;
        }
    }

    final boolean zza(String str, double d) throws IllegalStateException {
        try {
            SharedPreferences.Editor editorEdit = zzm().getSharedPreferences("google.analytics.deferred.deeplink.prefs", 0).edit();
            editorEdit.putString("deeplink", str);
            editorEdit.putLong("timestamp", Double.doubleToRawLongBits(d));
            return editorEdit.commit();
        } catch (Exception e) {
            zzq().zze().zza("Failed to persist Deferred Deep Link. exception", e);
            return false;
        }
    }

    public final boolean zzj() throws ClassNotFoundException {
        try {
            zzm().getClassLoader().loadClass("com.google.firebase.remoteconfig.FirebaseRemoteConfig");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static long zza(zzap zzapVar) {
        long length = 0;
        if (zzapVar == null) {
            return 0L;
        }
        Iterator<String> it = zzapVar.iterator();
        while (it.hasNext()) {
            if (zzapVar.zza(it.next()) instanceof Parcelable[]) {
                length += ((Parcelable[]) r3).length;
            }
        }
        return length;
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzak zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Clock zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ Context zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzeo zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzkv zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzfr zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzeq zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzfc zzr() {
        return super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final /* bridge */ /* synthetic */ zzab zzs() {
        return super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzgr, com.google.android.gms.measurement.internal.zzgt
    public final /* bridge */ /* synthetic */ zzw zzt() {
        return super.zzt();
    }
}
