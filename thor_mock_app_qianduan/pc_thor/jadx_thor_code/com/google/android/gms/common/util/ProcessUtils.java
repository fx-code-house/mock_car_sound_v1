package com.google.android.gms.common.util;

import android.app.Application;
import android.os.Build;
import android.os.Process;
import android.os.StrictMode;
import com.google.android.gms.common.internal.Preconditions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
/* loaded from: classes2.dex */
public class ProcessUtils {

    @Nullable
    private static String zza;
    private static int zzb;

    private ProcessUtils() {
    }

    public static String getMyProcessName() throws Throwable {
        BufferedReader bufferedReader;
        if (zza == null) {
            if (Build.VERSION.SDK_INT >= 28) {
                zza = Application.getProcessName();
            } else {
                int iMyPid = zzb;
                if (iMyPid == 0) {
                    iMyPid = Process.myPid();
                    zzb = iMyPid;
                }
                String strTrim = null;
                strTrim = null;
                strTrim = null;
                BufferedReader bufferedReader2 = null;
                if (iMyPid > 0) {
                    try {
                        String str = "/proc/" + iMyPid + "/cmdline";
                        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                        try {
                            bufferedReader = new BufferedReader(new FileReader(str));
                        } finally {
                            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        }
                    } catch (IOException unused) {
                        bufferedReader = null;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        String line = bufferedReader.readLine();
                        Preconditions.checkNotNull(line);
                        strTrim = line.trim();
                    } catch (IOException unused2) {
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader2 = bufferedReader;
                        IOUtils.closeQuietly(bufferedReader2);
                        throw th;
                    }
                    IOUtils.closeQuietly(bufferedReader);
                }
                zza = strTrim;
            }
        }
        return zza;
    }
}
