package com.thor.app.managers;

import android.content.Context;
import com.google.firebase.messaging.Constants;
import com.thor.app.ThorApplication;
import com.thor.app.rx.DefaultSchedulerTransformer;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.networkmodule.model.responses.DriveSelectResponse;
import com.thor.networkmodule.model.responses.car.CarGenerationsResponse;
import com.thor.networkmodule.model.responses.car.CarMarksResponse;
import com.thor.networkmodule.model.responses.car.CarModelsResponse;
import com.thor.networkmodule.model.responses.car.CarSeriesResponse;
import com.thor.networkmodule.network.ApiService;
import io.reactivex.Observable;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CarManager.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\n\u001a\u00020\u000bJ\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\u0010\u001a\u00020\u000bJ\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00062\u0006\u0010\u0013\u001a\u00020\u000b¨\u0006\u0015"}, d2 = {"Lcom/thor/app/managers/CarManager;", "Lcom/thor/app/managers/Manager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "fetchDriveSelect", "Lio/reactivex/Observable;", "Lcom/thor/networkmodule/model/responses/DriveSelectResponse;", "fetchGenerations", "Lcom/thor/networkmodule/model/responses/car/CarGenerationsResponse;", "carModelId", "", "fetchMarks", "Lcom/thor/networkmodule/model/responses/car/CarMarksResponse;", "fetchModels", "Lcom/thor/networkmodule/model/responses/car/CarModelsResponse;", "carMarkId", "fetchSeries", "Lcom/thor/networkmodule/model/responses/car/CarSeriesResponse;", "carGenerationId", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CarManager extends Manager {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final CarManager from(Context context) {
        return INSTANCE.from(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarManager(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final Observable<CarMarksResponse> fetchMarks() {
        Observable observableCompose = getApiService().rxGetCarMarks().compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxGetCarMarks…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<CarModelsResponse> fetchModels(int carMarkId) {
        Observable observableCompose = getApiService().rxPostGetCarModels(carMarkId).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostGetCarM…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<CarGenerationsResponse> fetchGenerations(int carModelId) {
        Observable observableCompose = getApiService().rxPostGetCarGenerations(carModelId).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostGetCarG…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<CarSeriesResponse> fetchSeries(int carGenerationId) {
        Observable observableCompose = getApiService().rxPostGetCarSeries(carGenerationId).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostGetCarS…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<DriveSelectResponse> fetchDriveSelect() {
        String deviceId;
        ApiService apiService = getApiService();
        String accessToken = Settings.getAccessToken();
        int userId = Settings.getUserId();
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        if (hardwareProfile == null || (deviceId = hardwareProfile.getDeviceId()) == null) {
            deviceId = "";
        }
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = apiService.rxPostGetDriveSelect(accessToken, userId, deviceId, languageCode).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostGetDriv…ltSchedulerTransformer())");
        return observableCompose;
    }

    /* compiled from: CarManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lcom/thor/app/managers/CarManager$Companion;", "", "()V", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/managers/CarManager;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CarManager from(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.thor.app.ThorApplication");
            return ((ThorApplication) applicationContext).getCarManager();
        }
    }
}
