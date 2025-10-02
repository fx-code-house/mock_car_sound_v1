package com.google.android.gms.internal.icing;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzbl {
    public static zzbx<zzbm> zzd(Context context) {
        String str = Build.TYPE;
        String str2 = Build.TAGS;
        String str3 = Build.HARDWARE;
        boolean z = false;
        if ((str.equals("eng") || str.equals("userdebug")) && ((str3.equals("goldfish") || str3.equals("ranchu") || str3.equals("robolectric")) && (str2.contains("dev-keys") || str2.contains("test-keys")))) {
            z = true;
        }
        if (!z) {
            return zzbx.zzw();
        }
        if (zzaz.zzk() && !context.isDeviceProtectedStorage()) {
            context = context.createDeviceProtectedStorageContext();
        }
        zzbx<File> zzbxVarZze = zze(context);
        return zzbxVarZze.isPresent() ? zzbx.zzb(zza(zzbxVarZze.get())) : zzbx.zzw();
    }

    private static zzbx<File> zze(Context context) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            StrictMode.allowThreadDiskWrites();
            File file = new File(context.getDir("phenotype_hermetic", 0), "overrides.txt");
            return file.exists() ? zzbx.zzb(file) : zzbx.zzw();
        } catch (RuntimeException e) {
            Log.e("HermeticFileOverrides", "no data dir", e);
            return zzbx.zzw();
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    private static zzbm zza(File file) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
                HashMap map = new HashMap();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        String[] strArrSplit = line.split(StringUtils.SPACE, 3);
                        if (strArrSplit.length != 3) {
                            String strValueOf = String.valueOf(line);
                            Log.e("HermeticFileOverrides", strValueOf.length() != 0 ? "Invalid: ".concat(strValueOf) : new String("Invalid: "));
                        } else {
                            String str = strArrSplit[0];
                            String strDecode = Uri.decode(strArrSplit[1]);
                            String strDecode2 = Uri.decode(strArrSplit[2]);
                            if (!map.containsKey(str)) {
                                map.put(str, new HashMap());
                            }
                            ((Map) map.get(str)).put(strDecode, strDecode2);
                        }
                    } else {
                        String strValueOf2 = String.valueOf(file);
                        Log.i("HermeticFileOverrides", new StringBuilder(String.valueOf(strValueOf2).length() + 7).append("Parsed ").append(strValueOf2).toString());
                        zzbm zzbmVar = new zzbm(map);
                        bufferedReader.close();
                        return zzbmVar;
                    }
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
