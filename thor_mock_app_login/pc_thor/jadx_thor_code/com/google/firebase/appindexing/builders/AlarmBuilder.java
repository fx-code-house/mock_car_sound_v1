package com.google.firebase.appindexing.builders;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class AlarmBuilder extends IndexableBuilder<AlarmBuilder> {
    public static final String FRIDAY = "Friday";
    public static final String MONDAY = "Monday";
    public static final String SATURDAY = "Saturday";
    public static final String SUNDAY = "Sunday";
    public static final String THURSDAY = "Thursday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";

    AlarmBuilder() {
        super("Alarm");
    }

    public final AlarmBuilder setHour(int i) {
        if (i < 0 || i > 23) {
            throw new IllegalArgumentException("Invalid alarm hour");
        }
        return put("hour", i);
    }

    public final AlarmBuilder setMinute(int i) {
        if (i < 0 || i > 59) {
            throw new IllegalArgumentException("Invalid alarm minute");
        }
        return put("minute", i);
    }

    public final AlarmBuilder setMessage(String str) {
        return put("message", str);
    }

    public final AlarmBuilder setRingtone(String str) {
        return put("ringtone", str);
    }

    public final AlarmBuilder setVibrate(boolean z) {
        return put("vibrate", z);
    }

    public final AlarmBuilder setEnabled(boolean z) {
        return put("enabled", z);
    }

    public final AlarmBuilder setIdentifier(String str) {
        return put("identifier", str);
    }

    public final AlarmBuilder setDayOfWeek(String... strArr) {
        for (String str : strArr) {
            if (!SUNDAY.equals(str) && !MONDAY.equals(str) && !TUESDAY.equals(str) && !WEDNESDAY.equals(str) && !THURSDAY.equals(str) && !FRIDAY.equals(str) && !SATURDAY.equals(str)) {
                String strValueOf = String.valueOf(str);
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid weekday ".concat(strValueOf) : new String("Invalid weekday "));
            }
        }
        return put("dayOfWeek", strArr);
    }

    public final AlarmBuilder setAlarmInstances(AlarmInstanceBuilder... alarmInstanceBuilderArr) {
        return put("alarmInstances", alarmInstanceBuilderArr);
    }
}
