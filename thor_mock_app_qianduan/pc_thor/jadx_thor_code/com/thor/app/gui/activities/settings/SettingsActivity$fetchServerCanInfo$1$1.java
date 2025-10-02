package com.thor.app.gui.activities.settings;

import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.settings.CarInfoPreference;
import com.thor.networkmodule.model.responses.CanConfigurationsResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import timber.log.Timber;

/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class SettingsActivity$fetchServerCanInfo$1$1 extends Lambda implements Function1<SignInResponse, Unit> {
    final /* synthetic */ SignInResponse $profile;
    final /* synthetic */ SettingsActivity this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SettingsActivity$fetchServerCanInfo$1$1(SettingsActivity settingsActivity, SignInResponse signInResponse) {
        super(1);
        this.this$0 = settingsActivity;
        this.$profile = signInResponse;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(SignInResponse signInResponse) {
        invoke2(signInResponse);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(SignInResponse signInResponse) {
        if (signInResponse.getStatus()) {
            CarInfoPreference.INSTANCE.setCarModelID(signInResponse.getCarModelId());
            CarInfoPreference.INSTANCE.setCarMarkId(signInResponse.getCarMarkId());
            CarInfoPreference.INSTANCE.setCarSerieId(signInResponse.getCarSerieId());
            CarInfoPreference.INSTANCE.setCarGenerationId(signInResponse.getCarGenerationId());
        }
        Observable<CanConfigurationsResponse> observableFetchCanFileUrl = this.this$0.getUsersManager().fetchCanFileUrl(this.$profile);
        if (observableFetchCanFileUrl != null) {
            final SettingsActivity settingsActivity = this.this$0;
            final Function1<CanConfigurationsResponse, Unit> function1 = new Function1<CanConfigurationsResponse, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$fetchServerCanInfo$1$1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(CanConfigurationsResponse canConfigurationsResponse) {
                    invoke2(canConfigurationsResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(CanConfigurationsResponse canConfigurationsResponse) {
                    Log.i("CarInfo", "Shown");
                    Timber.INSTANCE.i("CanConfigurationsResponse: %s", canConfigurationsResponse);
                    if (canConfigurationsResponse.isSuccessful()) {
                        SettingsActivity.showCarInfoDialog$default(settingsActivity, canConfigurationsResponse, false, 2, null);
                        return;
                    }
                    AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, settingsActivity, canConfigurationsResponse.getError(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
                    }
                    SettingsActivity.showCarInfoDialog$default(settingsActivity, null, true, 1, null);
                }
            };
            Consumer<? super CanConfigurationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$fetchServerCanInfo$1$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity$fetchServerCanInfo$1$1.invoke$lambda$0(function1, obj);
                }
            };
            final SettingsActivity settingsActivity2 = this.this$0;
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$fetchServerCanInfo$1$1.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                    AlertDialog alertDialogCreateErrorAlertDialog$default;
                    if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                        AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, settingsActivity2, th.getMessage(), null, 4, null);
                        if (alertDialogCreateErrorAlertDialog$default2 != null) {
                            alertDialogCreateErrorAlertDialog$default2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, settingsActivity2, th.getMessage(), null, 4, null)) != null) {
                        alertDialogCreateErrorAlertDialog$default.show();
                    }
                    Timber.INSTANCE.e(th);
                    try {
                        SettingsActivity.showCarInfoDialog$default(settingsActivity2, null, false, 3, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            observableFetchCanFileUrl.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.settings.SettingsActivity$fetchServerCanInfo$1$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SettingsActivity$fetchServerCanInfo$1$1.invoke$lambda$1(function12, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
