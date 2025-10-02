package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.commons.lang3.StringUtils;

/* compiled from: com.google.android.gms:play-services-measurement-base@@18.0.0 */
/* loaded from: classes2.dex */
final class zzjk {
    static String zza(zzjj zzjjVar, String str) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(str);
        zza(zzjjVar, sb, 0);
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void zza(com.google.android.gms.internal.measurement.zzjj r13, java.lang.StringBuilder r14, int r15) throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 681
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjk.zza(com.google.android.gms.internal.measurement.zzjj, java.lang.StringBuilder, int):void");
    }

    static final void zza(StringBuilder sb, int i, String str, Object obj) throws SecurityException {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                zza(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zza(sb, i, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(' ');
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"").append(zzkp.zza(zzgp.zza((String) obj))).append(Typography.quote);
            return;
        }
        if (obj instanceof zzgp) {
            sb.append(": \"").append(zzkp.zza((zzgp) obj)).append(Typography.quote);
            return;
        }
        if (obj instanceof zzhy) {
            sb.append(" {");
            zza((zzhy) obj, sb, i + 2);
            sb.append(StringUtils.LF);
            while (i2 < i) {
                sb.append(' ');
                i2++;
            }
            sb.append("}");
            return;
        }
        if (obj instanceof Map.Entry) {
            sb.append(" {");
            Map.Entry entry = (Map.Entry) obj;
            int i4 = i + 2;
            zza(sb, i4, "key", entry.getKey());
            zza(sb, i4, "value", entry.getValue());
            sb.append(StringUtils.LF);
            while (i2 < i) {
                sb.append(' ');
                i2++;
            }
            sb.append("}");
            return;
        }
        sb.append(": ").append(obj.toString());
    }

    private static final String zza(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (Character.isUpperCase(cCharAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(cCharAt));
        }
        return sb.toString();
    }
}
