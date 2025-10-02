package com.google.android.gms.internal.vision;

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

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public final class zzbh {
    public static zzcy<zzbe> zzg(Context context) {
        String str = Build.TYPE;
        String str2 = Build.TAGS;
        boolean z = false;
        if ((str.equals("eng") || str.equals("userdebug")) && (str2.contains("dev-keys") || str2.contains("test-keys"))) {
            z = true;
        }
        if (!z) {
            return zzcy.zzcb();
        }
        if (zzas.zzu() && !context.isDeviceProtectedStorage()) {
            context = context.createDeviceProtectedStorageContext();
        }
        zzcy<File> zzcyVarZzh = zzh(context);
        return zzcyVarZzh.isPresent() ? zzcy.zzb(zza(zzcyVarZzh.get())) : zzcy.zzcb();
    }

    private static zzcy<File> zzh(Context context) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            StrictMode.allowThreadDiskWrites();
            File file = new File(context.getDir("phenotype_hermetic", 0), "overrides.txt");
            return file.exists() ? zzcy.zzb(file) : zzcy.zzcb();
        } catch (RuntimeException e) {
            Log.e("HermeticFileOverrides", "no data dir", e);
            return zzcy.zzcb();
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    private static zzbe zza(File file) throws IOException {
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
                        zzbe zzbeVar = new zzbe(map);
                        bufferedReader.close();
                        return zzbeVar;
                    }
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
