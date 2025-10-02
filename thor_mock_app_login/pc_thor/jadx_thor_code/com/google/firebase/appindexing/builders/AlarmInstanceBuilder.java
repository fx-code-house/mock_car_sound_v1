package com.google.firebase.appindexing.builders;

import com.google.firebase.appindexing.internal.zzad;
import java.util.Calendar;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class AlarmInstanceBuilder extends IndexableBuilder<AlarmInstanceBuilder> {
    public static final String DISMISSED = "Dismissed";
    public static final String FIRED = "Fired";
    public static final String MISSED = "Missed";
    public static final String SCHEDULED = "Scheduled";
    public static final String SNOOZED = "Snoozed";
    public static final String UNKNOWN = "Unknown";

    AlarmInstanceBuilder() {
        super("AlarmInstance");
    }

    public AlarmInstanceBuilder setScheduledTime(Calendar calendar) {
        return put("scheduledTime", zzad.zza(calendar));
    }

    public AlarmInstanceBuilder setAlarmStatus(String str) {
        if (!FIRED.equals(str) && !SNOOZED.equals(str) && !"Missed".equals(str) && !DISMISSED.equals(str) && !SCHEDULED.equals(str) && !"Unknown".equals(str)) {
            String strValueOf = String.valueOf(str);
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid alarm status ".concat(strValueOf) : new String("Invalid alarm status "));
        }
        return put("alarmStatus", str);
    }
}
