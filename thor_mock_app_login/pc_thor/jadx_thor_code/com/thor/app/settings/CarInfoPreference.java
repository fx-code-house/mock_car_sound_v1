package com.thor.app.settings;

import com.orhanobut.hawk.Hawk;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarInfoPreference.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0007J\b\u0010\n\u001a\u00020\tH\u0007J\b\u0010\u000b\u001a\u00020\tH\u0007J\b\u0010\f\u001a\u00020\tH\u0007J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\tJ\u000e\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\tJ\u000e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\tJ\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/thor/app/settings/CarInfoPreference;", "", "()V", "CAR_GENERATION_ID_PREF", "", "CAR_MARK_ID_PREF", "CAR_MODEL_ID_PREF", "CAR_SERIE_ID_PREF", "getCarGenerationId", "", "getCarMarkId", "getCarModelID", "getCarSerieId", "setCarGenerationId", "", "selectedCarGenerationId", "setCarMarkId", "selectedCarMarkID", "setCarModelID", "selectedCarModelID", "setCarSerieId", "selectedCarSerieId", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CarInfoPreference {
    public static final String CAR_GENERATION_ID_PREF = "car_generation";
    public static final String CAR_MARK_ID_PREF = "car_info_preference";
    public static final String CAR_MODEL_ID_PREF = "car_model_name";
    public static final String CAR_SERIE_ID_PREF = "car_serie_name";
    public static final CarInfoPreference INSTANCE = new CarInfoPreference();

    private CarInfoPreference() {
    }

    public final void setCarModelID(int selectedCarModelID) {
        Hawk.put(CAR_MODEL_ID_PREF, Integer.valueOf(selectedCarModelID));
    }

    @JvmStatic
    public static final int getCarModelID() {
        if (!Hawk.contains(CAR_MODEL_ID_PREF)) {
            return 0;
        }
        Object obj = Hawk.get(CAR_MODEL_ID_PREF);
        Intrinsics.checkNotNullExpressionValue(obj, "get(CAR_MODEL_ID_PREF)");
        return ((Number) obj).intValue();
    }

    public final void setCarMarkId(int selectedCarMarkID) {
        Hawk.put(CAR_MARK_ID_PREF, Integer.valueOf(selectedCarMarkID));
    }

    @JvmStatic
    public static final int getCarMarkId() {
        if (!Hawk.contains(CAR_MARK_ID_PREF)) {
            return 0;
        }
        Object obj = Hawk.get(CAR_MARK_ID_PREF);
        Intrinsics.checkNotNullExpressionValue(obj, "get(CAR_MARK_ID_PREF)");
        return ((Number) obj).intValue();
    }

    public final void setCarGenerationId(int selectedCarGenerationId) {
        Hawk.put(CAR_GENERATION_ID_PREF, Integer.valueOf(selectedCarGenerationId));
    }

    @JvmStatic
    public static final int getCarGenerationId() {
        if (!Hawk.contains(CAR_GENERATION_ID_PREF)) {
            return 0;
        }
        Object obj = Hawk.get(CAR_GENERATION_ID_PREF);
        Intrinsics.checkNotNullExpressionValue(obj, "get(CAR_GENERATION_ID_PREF)");
        return ((Number) obj).intValue();
    }

    public final void setCarSerieId(int selectedCarSerieId) {
        Hawk.put(CAR_SERIE_ID_PREF, Integer.valueOf(selectedCarSerieId));
    }

    @JvmStatic
    public static final int getCarSerieId() {
        if (!Hawk.contains(CAR_SERIE_ID_PREF)) {
            return 0;
        }
        Object obj = Hawk.get(CAR_SERIE_ID_PREF);
        Intrinsics.checkNotNullExpressionValue(obj, "get(CAR_SERIE_ID_PREF)");
        return ((Number) obj).intValue();
    }
}
