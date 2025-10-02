package com.google.firebase.appindexing.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzad {
    private static final DateFormat zzfz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

    public static String zza(Calendar calendar) {
        String str;
        DateFormat dateFormat = zzfz;
        synchronized (dateFormat) {
            dateFormat.setTimeZone(calendar.getTimeZone());
            str = dateFormat.format(calendar.getTime());
        }
        return str;
    }
}
