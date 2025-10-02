package com.thor.app.managers;

import android.content.Context;
import androidx.core.app.NotificationCompat;
import com.carsystems.thor.datalayermodule.datalayer.SettingsFromService;
import com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BluetoothDevicesWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BooleanWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.IntegerWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.LoadingSoundPackageWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SendUpdatedSguJsonWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SettingsFromAppWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SguPackageFromAppWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SguPackageWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.SoundPackageWearableDataLayer;
import com.carsystems.thor.datalayermodule.datalayer.models.UploadingSoundPackage;
import com.google.android.gms.wearable.DataItem;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.utils.data.DataLayerHelper;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import timber.log.Timber;

/* compiled from: DataLayerManager.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 02\u00020\u0001:\u00010B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0014\u0010\n\u001a\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fJ\u000e\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0007J\u0006\u0010\u0011\u001a\u00020\u0007J\u000e\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u000fJ\u000e\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u000fJ\u000e\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u000fJ\u0018\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u001fJ\u0014\u0010\"\u001a\u00020\u00072\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\fJ\u000e\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u001dJ\u0016\u0010'\u001a\u00020\u00072\u000e\u0010(\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010)J\u0014\u0010+\u001a\u00020\u00072\f\u0010#\u001a\b\u0012\u0004\u0012\u00020*0\fJ\u0016\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u001dR\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/thor/app/managers/DataLayerManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mContext", "sendBluetoothDevice", "", "bluetoothDevice", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "sendBluetoothDevices", "bluetoothDevices", "", "sendBluetoothDevicesRefreshingState", "isRefreshing", "", "sendGetCurrentActivity", "sendGetCurrentSettings", "sendIsAccessSession", "isAccessSession", "sendIsLoadingSoundPackage", "isLoading", "sendIsRunningAppOnPhone", "state", "Lcom/thor/app/databinding/model/RunningAppOnPhoneStatus;", "sendLoadingClose", "isOccurredByErrorClose", "sendLoadingProgress", "soundPackageName", "", NotificationCompat.CATEGORY_PROGRESS, "", "sendMainPresetPackageIndex", FirebaseAnalytics.Param.INDEX, "sendMainPresetPackages", "mainPresets", "Lcom/thor/businessmodule/model/MainPresetPackage;", "sendSguJson", "json", "sendSguList", "list", "", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "sendSguPresetPackages", "sendСurrentAppSettings", "response", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "macAddress", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DataLayerManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Context mContext;

    public /* synthetic */ DataLayerManager(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    private DataLayerManager(Context context) {
        this.mContext = context;
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendGetCurrentActivity$1, reason: invalid class name and case insensitive filesystem */
    static final class C03881 extends Lambda implements Function1<Boolean, Unit> {
        C03881() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BooleanWearableDataLayer.CURRENT_ACTIVITY_PATH, BooleanWearableDataLayer.INSTANCE, true).doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendGetCurrentActivity$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03881.invoke$lambda$0(obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Object obj) {
            Timber.INSTANCE.i("Current activity command send from phone to watches: true", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendGetCurrentActivity$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendGetCurrentActivity() {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03881 c03881 = new C03881();
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendGetCurrentActivity$lambda$0(c03881, obj);
            }
        });
        final C03892 c03892 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendGetCurrentActivity.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendGetCurrentActivity$lambda$1(c03892, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendGetCurrentActivity$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendIsRunningAppOnPhone$1, reason: invalid class name and case insensitive filesystem */
    static final class C03961 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ RunningAppOnPhoneStatus $state;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03961(RunningAppOnPhoneStatus runningAppOnPhoneStatus) {
            super(1);
            this.$state = runningAppOnPhoneStatus;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(IntegerWearableDataLayer.RUNNING_APP_ON_PHONE_PATH, IntegerWearableDataLayer.INSTANCE, Integer.valueOf(this.$state.ordinal())).doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendIsRunningAppOnPhone$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03961.invoke$lambda$0(obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Object obj) {
            Timber.INSTANCE.i("Running app on phone", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendIsRunningAppOnPhone$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendIsRunningAppOnPhone(RunningAppOnPhoneStatus state) {
        Intrinsics.checkNotNullParameter(state, "state");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03961 c03961 = new C03961(state);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendIsRunningAppOnPhone$lambda$2(c03961, obj);
            }
        });
        final C03972 c03972 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendIsRunningAppOnPhone.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendIsRunningAppOnPhone$lambda$3(c03972, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendIsRunningAppOnPhone$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendСurrentAppSettings$1, reason: invalid class name and case insensitive filesystem */
    static final class C04121 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ String $macAddress;
        final /* synthetic */ SignInResponse $response;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04121(SignInResponse signInResponse, String str) {
            super(1);
            this.$response = signInResponse;
            this.$macAddress = str;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(SettingsFromAppWearableDataLayer.SETTINGS_FROM_APP_PATH, SettingsFromAppWearableDataLayer.INSTANCE, new SettingsFromService(this.$response, this.$macAddress)).doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendСurrentAppSettings$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C04121.invoke$lambda$0(obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Object obj) {
            Timber.INSTANCE.i("Running app on phone", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendСurrentAppSettings$lambda$4, reason: contains not printable characters */
    public static final void m556sendurrentAppSettings$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* renamed from: sendСurrentAppSettings, reason: contains not printable characters */
    public final void m558sendurrentAppSettings(SignInResponse response, String macAddress) {
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(macAddress, "macAddress");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C04121 c04121 = new C04121(response, macAddress);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.m556sendurrentAppSettings$lambda$4(c04121, obj);
            }
        });
        final C04132 c04132 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendСurrentAppSettings.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.m557sendurrentAppSettings$lambda$5(c04132, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendСurrentAppSettings$lambda$5, reason: contains not printable characters */
    public static final void m557sendurrentAppSettings$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendIsLoadingSoundPackage$1, reason: invalid class name and case insensitive filesystem */
    static final class C03941 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ boolean $isLoading;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03941(boolean z) {
            super(1);
            this.$isLoading = z;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                Single<DataItem> singleSendRxDataItem = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BooleanWearableDataLayer.UPLOADING_SOUND_PACKAGE_LOADING_PATH, BooleanWearableDataLayer.INSTANCE, Boolean.valueOf(this.$isLoading));
                final boolean z = this.$isLoading;
                singleSendRxDataItem.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendIsLoadingSoundPackage$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03941.invoke$lambda$0(z, obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(boolean z, Object obj) {
            Timber.INSTANCE.i("Loading sound package: " + z, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendIsLoadingSoundPackage$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendIsLoadingSoundPackage(boolean isLoading) {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03941 c03941 = new C03941(isLoading);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendIsLoadingSoundPackage$lambda$6(c03941, obj);
            }
        });
        final C03952 c03952 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendIsLoadingSoundPackage.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendIsLoadingSoundPackage$lambda$7(c03952, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendIsLoadingSoundPackage$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendIsAccessSession$1, reason: invalid class name and case insensitive filesystem */
    static final class C03921 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ boolean $isAccessSession;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03921(boolean z) {
            super(1);
            this.$isAccessSession = z;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                Single<DataItem> singleSendRxDataItem = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BooleanWearableDataLayer.ACCESS_SESSION, BooleanWearableDataLayer.INSTANCE, Boolean.valueOf(this.$isAccessSession));
                final boolean z = this.$isAccessSession;
                singleSendRxDataItem.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendIsAccessSession$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03921.invoke$lambda$0(z, obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(boolean z, Object obj) {
            Timber.INSTANCE.i("Is access session on phone: " + z, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendIsAccessSession$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendIsAccessSession(boolean isAccessSession) {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03921 c03921 = new C03921(isAccessSession);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendIsAccessSession$lambda$8(c03921, obj);
            }
        });
        final C03932 c03932 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendIsAccessSession.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendIsAccessSession$lambda$9(c03932, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendIsAccessSession$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendGetCurrentSettings$1, reason: invalid class name and case insensitive filesystem */
    static final class C03901 extends Lambda implements Function1<Boolean, Unit> {
        C03901() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BooleanWearableDataLayer.CURRENT_SETTINGS_PATH, BooleanWearableDataLayer.INSTANCE, true).doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendGetCurrentSettings$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03901.invoke$lambda$0(obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Object obj) {
            Timber.INSTANCE.i("Current activity command send from phone to watches: true", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendGetCurrentSettings$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendGetCurrentSettings() {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03901 c03901 = new C03901();
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendGetCurrentSettings$lambda$10(c03901, obj);
            }
        });
        final C03912 c03912 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendGetCurrentSettings.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendGetCurrentSettings$lambda$11(c03912, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendGetCurrentSettings$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendBluetoothDevices$1, reason: invalid class name and case insensitive filesystem */
    static final class C03841 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ Collection<BluetoothDeviceItem> $bluetoothDevices;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03841(Collection<BluetoothDeviceItem> collection) {
            super(1);
            this.$bluetoothDevices = collection;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                Single<DataItem> singleSendRxDataItems = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItems(BluetoothDevicesWearableDataLayer.BLUETOOTH_DEVICES_PATH, BluetoothDevicesWearableDataLayer.INSTANCE, this.$bluetoothDevices);
                final Collection<BluetoothDeviceItem> collection = this.$bluetoothDevices;
                singleSendRxDataItems.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendBluetoothDevices$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03841.invoke$lambda$0(collection, obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Collection bluetoothDevices, Object obj) {
            Intrinsics.checkNotNullParameter(bluetoothDevices, "$bluetoothDevices");
            Timber.INSTANCE.i("Bluetooth devices send from phone to watches: " + bluetoothDevices, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendBluetoothDevices$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendBluetoothDevices(Collection<BluetoothDeviceItem> bluetoothDevices) {
        Intrinsics.checkNotNullParameter(bluetoothDevices, "bluetoothDevices");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03841 c03841 = new C03841(bluetoothDevices);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendBluetoothDevices$lambda$12(c03841, obj);
            }
        });
        final C03852 c03852 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendBluetoothDevices.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendBluetoothDevices$lambda$13(c03852, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendBluetoothDevices$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendSguJson$1, reason: invalid class name and case insensitive filesystem */
    static final class C04061 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ String $json;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04061(String str) {
            super(1);
            this.$json = str;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                Single<DataItem> singleSendRxDataItem = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(SendUpdatedSguJsonWearableDataLayer.SGU_PRESET_JSON_PATH, SendUpdatedSguJsonWearableDataLayer.INSTANCE, this.$json);
                final String str = this.$json;
                singleSendRxDataItem.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendSguJson$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C04061.invoke$lambda$0(str, obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(String json, Object obj) {
            Intrinsics.checkNotNullParameter(json, "$json");
            Timber.INSTANCE.i("Json send from phone to watches: " + json, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSguJson$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendSguJson(String json) {
        Intrinsics.checkNotNullParameter(json, "json");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C04061 c04061 = new C04061(json);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda30
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendSguJson$lambda$14(c04061, obj);
            }
        });
        final C04072 c04072 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendSguJson.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda31
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendSguJson$lambda$15(c04072, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSguJson$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendSguList$1, reason: invalid class name and case insensitive filesystem */
    static final class C04081 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ List<SguSound> $list;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04081(List<SguSound> list) {
            super(1);
            this.$list = list;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                Single<DataItem> singleSendRxDataItems = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItems(SguPackageFromAppWearableDataLayer.SGU_FROM_APP_DATA_PATH, SguPackageFromAppWearableDataLayer.INSTANCE, this.$list);
                final List<SguSound> list = this.$list;
                singleSendRxDataItems.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendSguList$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C04081.invoke$lambda$0(list, obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(List list, Object obj) {
            Timber.INSTANCE.i("Sgu sounds send from phone to watches: " + list, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSguList$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendSguList(List<SguSound> list) {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C04081 c04081 = new C04081(list);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda28
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendSguList$lambda$16(c04081, obj);
            }
        });
        final C04092 c04092 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendSguList.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda29
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendSguList$lambda$17(c04092, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSguList$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendBluetoothDevice$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ BluetoothDeviceItem $bluetoothDevice;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(BluetoothDeviceItem bluetoothDeviceItem) {
            super(1);
            this.$bluetoothDevice = bluetoothDeviceItem;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                Single<DataItem> singleObserveOn = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BluetoothDevicesWearableDataLayer.BLUETOOTH_DEVICE_PATH, BluetoothDevicesWearableDataLayer.INSTANCE, this.$bluetoothDevice).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
                final BluetoothDeviceItem bluetoothDeviceItem = this.$bluetoothDevice;
                singleObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendBluetoothDevice$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.AnonymousClass1.invoke$lambda$0(bluetoothDeviceItem, obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(BluetoothDeviceItem bluetoothDevice, Object obj) {
            Intrinsics.checkNotNullParameter(bluetoothDevice, "$bluetoothDevice");
            Timber.INSTANCE.i("Bluetooth device send from phone to watches: " + bluetoothDevice, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendBluetoothDevice$lambda$18(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendBluetoothDevice(BluetoothDeviceItem bluetoothDevice) {
        Intrinsics.checkNotNullParameter(bluetoothDevice, "bluetoothDevice");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1(bluetoothDevice);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda23
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendBluetoothDevice$lambda$18(anonymousClass1, obj);
            }
        });
        final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendBluetoothDevice.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendBluetoothDevice$lambda$19(anonymousClass2, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendBluetoothDevice$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendBluetoothDevicesRefreshingState$1, reason: invalid class name and case insensitive filesystem */
    static final class C03861 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ boolean $isRefreshing;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03861(boolean z) {
            super(1);
            this.$isRefreshing = z;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.booleanValue()) {
                WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BooleanWearableDataLayer.BLUETOOTH_REFRESHING_DEVICES_PATH, BooleanWearableDataLayer.INSTANCE, Boolean.valueOf(this.$isRefreshing)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendBluetoothDevicesRefreshingState$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        DataLayerManager.C03861.invoke$lambda$0(obj);
                    }
                }).subscribe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Object obj) {
            Timber.INSTANCE.i("Bluetooth refreshing devices send from phone to watches: false", new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendBluetoothDevicesRefreshingState$lambda$20(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendBluetoothDevicesRefreshingState(boolean isRefreshing) {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03861 c03861 = new C03861(isRefreshing);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendBluetoothDevicesRefreshingState$lambda$20(c03861, obj);
            }
        });
        final C03872 c03872 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendBluetoothDevicesRefreshingState.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendBluetoothDevicesRefreshingState$lambda$21(c03872, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendBluetoothDevicesRefreshingState$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendMainPresetPackageIndex$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendMainPresetPackageIndex(final int index) {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final Function1<Boolean, Unit> function1 = new Function1<Boolean, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendMainPresetPackageIndex.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke2(bool);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Boolean bool) {
                WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(IntegerWearableDataLayer.MAIN_PRESET_PACKAGE_INDEX_PATH, IntegerWearableDataLayer.INSTANCE, Integer.valueOf(index)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
            }
        };
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendMainPresetPackageIndex$lambda$22(function1, obj);
            }
        });
        final C04032 c04032 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendMainPresetPackageIndex.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendMainPresetPackageIndex$lambda$23(c04032, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendMainPresetPackageIndex$lambda$23(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendMainPresetPackages$1, reason: invalid class name and case insensitive filesystem */
    static final class C04041 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ Collection<MainPresetPackage> $mainPresets;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04041(Collection<MainPresetPackage> collection) {
            super(1);
            this.$mainPresets = collection;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean bool) {
            Single<DataItem> singleObserveOn = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItems(SoundPackageWearableDataLayer.MAIN_PRESET_PACKAGES_PATH, SoundPackageWearableDataLayer.INSTANCE, this.$mainPresets).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            final Collection<MainPresetPackage> collection = this.$mainPresets;
            singleObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendMainPresetPackages$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C04041.invoke$lambda$0(collection, obj);
                }
            }).doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendMainPresetPackages$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C04041.invoke$lambda$1(obj);
                }
            }).subscribe();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Collection mainPresets, Object obj) {
            Intrinsics.checkNotNullParameter(mainPresets, "$mainPresets");
            Timber.INSTANCE.i("Sound packages send from phone to watches: " + mainPresets, new Object[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Object obj) {
            if (obj instanceof Exception) {
                Timber.INSTANCE.e((Throwable) obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendMainPresetPackages$lambda$24(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendMainPresetPackages(Collection<MainPresetPackage> mainPresets) {
        Intrinsics.checkNotNullParameter(mainPresets, "mainPresets");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C04041 c04041 = new C04041(mainPresets);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendMainPresetPackages$lambda$24(c04041, obj);
            }
        });
        final C04052 c04052 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendMainPresetPackages.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendMainPresetPackages$lambda$25(c04052, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendMainPresetPackages$lambda$25(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendSguPresetPackages$1, reason: invalid class name and case insensitive filesystem */
    static final class C04101 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ Collection<SguSound> $mainPresets;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04101(Collection<SguSound> collection) {
            super(1);
            this.$mainPresets = collection;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean bool) {
            Single<DataItem> singleObserveOn = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItems(SguPackageWearableDataLayer.SGU_PRESET_PACKAGES_PATH, SguPackageWearableDataLayer.INSTANCE, this.$mainPresets).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            final Collection<SguSound> collection = this.$mainPresets;
            singleObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendSguPresetPackages$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C04101.invoke$lambda$0(collection, obj);
                }
            }).doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendSguPresetPackages$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C04101.invoke$lambda$1(obj);
                }
            }).subscribe();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Collection mainPresets, Object obj) {
            Intrinsics.checkNotNullParameter(mainPresets, "$mainPresets");
            Timber.INSTANCE.i("Sound packages send from phone to watches: " + mainPresets, new Object[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Object obj) {
            if (obj instanceof Exception) {
                Timber.INSTANCE.e((Throwable) obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSguPresetPackages$lambda$26(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendSguPresetPackages(Collection<SguSound> mainPresets) {
        Intrinsics.checkNotNullParameter(mainPresets, "mainPresets");
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C04101 c04101 = new C04101(mainPresets);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendSguPresetPackages$lambda$26(c04101, obj);
            }
        });
        final C04112 c04112 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendSguPresetPackages.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendSguPresetPackages$lambda$27(c04112, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendSguPresetPackages$lambda$27(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendLoadingProgress(String soundPackageName, int progress) {
        UploadingSoundPackage uploadingSoundPackage = new UploadingSoundPackage(soundPackageName, progress);
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C04001 c04001 = new C04001(uploadingSoundPackage);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendLoadingProgress$lambda$28(c04001, obj);
            }
        });
        final C04012 c04012 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendLoadingProgress.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendLoadingProgress$lambda$29(c04012, obj);
            }
        }).subscribe();
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendLoadingProgress$1, reason: invalid class name and case insensitive filesystem */
    static final class C04001 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ UploadingSoundPackage $data;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C04001(UploadingSoundPackage uploadingSoundPackage) {
            super(1);
            this.$data = uploadingSoundPackage;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean bool) {
            Single<DataItem> singleObserveOn = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(LoadingSoundPackageWearableDataLayer.UPLOADING_SOUND_PACKAGE_PATH, LoadingSoundPackageWearableDataLayer.INSTANCE, this.$data).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            final UploadingSoundPackage uploadingSoundPackage = this.$data;
            singleObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendLoadingProgress$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C04001.invoke$lambda$0(uploadingSoundPackage, obj);
                }
            }).doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendLoadingProgress$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C04001.invoke$lambda$1(obj);
                }
            }).subscribe();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(UploadingSoundPackage data, Object obj) {
            Intrinsics.checkNotNullParameter(data, "$data");
            Timber.INSTANCE.i("Loading sound packages progress send from phone to watches: " + data, new Object[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Object obj) {
            if (obj instanceof Exception) {
                Timber.INSTANCE.e((Throwable) obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendLoadingProgress$lambda$28(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendLoadingProgress$lambda$29(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Boolean;)V"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.DataLayerManager$sendLoadingClose$1, reason: invalid class name and case insensitive filesystem */
    static final class C03981 extends Lambda implements Function1<Boolean, Unit> {
        final /* synthetic */ boolean $isOccurredByErrorClose;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03981(boolean z) {
            super(1);
            this.$isOccurredByErrorClose = z;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
            invoke2(bool);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Boolean bool) {
            Single<DataItem> singleObserveOn = WearableDataLayerSender.from(DataLayerManager.this.mContext).sendRxDataItem(BooleanWearableDataLayer.UPLOADING_SOUND_PACKAGE_CLOSE_PATH, BooleanWearableDataLayer.INSTANCE, Boolean.valueOf(this.$isOccurredByErrorClose)).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            final boolean z = this.$isOccurredByErrorClose;
            singleObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendLoadingClose$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C03981.invoke$lambda$0(z, obj);
                }
            }).doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$sendLoadingClose$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DataLayerManager.C03981.invoke$lambda$1(obj);
                }
            }).subscribe();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(boolean z, Object obj) {
            Timber.INSTANCE.i("Close loading progress send from phone to watches: " + z, new Object[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Object obj) {
            if (obj instanceof Exception) {
                Timber.INSTANCE.e((Throwable) obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendLoadingClose$lambda$30(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void sendLoadingClose(boolean isOccurredByErrorClose) {
        Single<Boolean> singleIsConnectedToWear = DataLayerHelper.newInstance(this.mContext).isConnectedToWear();
        final C03981 c03981 = new C03981(isOccurredByErrorClose);
        Single<Boolean> singleDoOnSuccess = singleIsConnectedToWear.doOnSuccess(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendLoadingClose$lambda$30(c03981, obj);
            }
        });
        final C03992 c03992 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.DataLayerManager.sendLoadingClose.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
            }
        };
        singleDoOnSuccess.doOnError(new Consumer() { // from class: com.thor.app.managers.DataLayerManager$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataLayerManager.sendLoadingClose$lambda$31(c03992, obj);
            }
        }).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void sendLoadingClose$lambda$31(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: DataLayerManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/managers/DataLayerManager$Companion;", "", "()V", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/managers/DataLayerManager;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DataLayerManager from(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new DataLayerManager(context, null);
        }
    }
}
