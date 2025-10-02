package com.thor.app.utils;

import android.text.format.DateFormat;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import timber.log.Timber;

/* loaded from: classes3.dex */
public final class TimeHelper {
    public static final String TIME_FORMAT_DAY = "dd";
    public static final String TIME_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String TIME_FORMAT_mm_ss_SSS = "mm:ss:SSS";

    public static Date getDate(String time) {
        return getDateFromString(time, TIME_FORMAT_DEFAULT);
    }

    public static Date getDateFromString(String time, String pattern) {
        try {
            return ((SimpleDateFormat) new WeakReference(new SimpleDateFormat(pattern)).get()).parse(time);
        } catch (ParseException e) {
            Timber.e(e);
            return null;
        }
    }

    public static String getDay(Date date) {
        return (String) DateFormat.format(TIME_FORMAT_DAY, date);
    }

    public static String getMinutesWithSeconds() {
        return ((SimpleDateFormat) new WeakReference(new SimpleDateFormat(TIME_FORMAT_mm_ss_SSS)).get()).format(Calendar.getInstance().getTime());
    }
}
