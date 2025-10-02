package com.orhanobut.hawk;

import android.content.Context;
import com.orhanobut.hawk.HawkFacade;

/* loaded from: classes2.dex */
public final class Hawk {
    static HawkFacade hawkFacade = new HawkFacade.EmptyHawkFacade();

    private Hawk() {
    }

    public static HawkBuilder init(Context context) {
        HawkUtils.checkNull("Context", context);
        hawkFacade = null;
        return new HawkBuilder(context);
    }

    static void build(HawkBuilder hawkBuilder) {
        hawkFacade = new DefaultHawkFacade(hawkBuilder);
    }

    public static <T> boolean put(String str, T t) {
        return hawkFacade.put(str, t);
    }

    public static <T> T get(String str) {
        return (T) hawkFacade.get(str);
    }

    public static <T> T get(String str, T t) {
        return (T) hawkFacade.get(str, t);
    }

    public static long count() {
        return hawkFacade.count();
    }

    public static boolean deleteAll() {
        return hawkFacade.deleteAll();
    }

    public static boolean delete(String str) {
        return hawkFacade.delete(str);
    }

    public static boolean contains(String str) {
        return hawkFacade.contains(str);
    }

    public static boolean isBuilt() {
        return hawkFacade.isBuilt();
    }

    public static void destroy() {
        hawkFacade.destroy();
    }
}
