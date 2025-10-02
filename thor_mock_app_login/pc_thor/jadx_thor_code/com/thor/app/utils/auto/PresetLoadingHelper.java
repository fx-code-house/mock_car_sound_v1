package com.thor.app.utils.auto;

import android.content.Context;
import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.gms.common.util.Hex;
import com.thor.app.managers.BleManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.AutoMediaKt;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.response.other.InstalledPresetsResponse;
import com.thor.businessmodule.model.MainPresetPackage;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: PresetLoadingHelper.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 $2\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0018\u0010\u0019\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001b\u0012\u0004\u0012\u00020\u00160\u001aH\u0003J\b\u0010\u001c\u001a\u0004\u0018\u00010\u0014J \u0010\u001d\u001a\u00020\u00162\u0018\u0010\u0019\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001b\u0012\u0004\u0012\u00020\u00160\u001aJ\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002J*\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#2\u0018\u0010\u0019\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001b\u0012\u0004\u0012\u00020\u00160\u001aH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/thor/app/utils/auto/PresetLoadingHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "mBleManager", "Lcom/thor/app/managers/BleManager;", "getMBleManager", "()Lcom/thor/app/managers/BleManager;", "mBleManager$delegate", "presetsMetadata", "", "Landroid/support/v4/media/MediaMetadataCompat;", "doHandlePresets", "", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "actionOnSuccess", "Lkotlin/Function1;", "", "getActivePresetMetadata", "onLoadPresets", "readInstalledPresets", "Lio/reactivex/Observable;", "Lcom/thor/businessmodule/bluetooth/response/other/InstalledPresetsResponse;", "retryLoadPresets", "delay", "", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PresetLoadingHelper {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final long NOT_CONNECTED_RETRY_DELAY = 5000;
    public static final long WRONG_RESPONSE_RETRY_DELAY = 1000;
    private static boolean mLoadedData;
    private static boolean mLoadingData;
    private final Context context;

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler;

    /* renamed from: mBleManager$delegate, reason: from kotlin metadata */
    private final Lazy mBleManager;
    private final List<MediaMetadataCompat> presetsMetadata;

    public PresetLoadingHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.presetsMetadata = new ArrayList();
        this.handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$handler$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Handler invoke() {
                return new Handler(this.this$0.getContext().getMainLooper());
            }
        });
        this.mBleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$mBleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.getContext());
            }
        });
    }

    public final Context getContext() {
        return this.context;
    }

    /* compiled from: PresetLoadingHelper.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/thor/app/utils/auto/PresetLoadingHelper$Companion;", "", "()V", "NOT_CONNECTED_RETRY_DELAY", "", "WRONG_RESPONSE_RETRY_DELAY", "mLoadedData", "", "mLoadingData", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    protected final BleManager getMBleManager() {
        return (BleManager) this.mBleManager.getValue();
    }

    public final void onLoadPresets(final Function1<? super List<MediaMetadataCompat>, Unit> actionOnSuccess) {
        Intrinsics.checkNotNullParameter(actionOnSuccess, "actionOnSuccess");
        Timber.INSTANCE.i("onLoadPresets", new Object[0]);
        boolean zIsBleEnabledAndDeviceConnected = getMBleManager().isBleEnabledAndDeviceConnected();
        Timber.INSTANCE.i("isConnected = " + zIsBleEnabledAndDeviceConnected + ", isLoading = " + mLoadingData, new Object[0]);
        if (zIsBleEnabledAndDeviceConnected && Settings.INSTANCE.isAccessSession()) {
            getMBleManager().stopScan();
            if (mLoadingData) {
                return;
            }
            mLoadingData = true;
            try {
                Observable<InstalledPresetsResponse> observableObserveOn = readInstalledPresets().observeOn(AndroidSchedulers.mainThread());
                final Function1<InstalledPresetsResponse, Unit> function1 = new Function1<InstalledPresetsResponse, Unit>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper.onLoadPresets.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(InstalledPresetsResponse installedPresetsResponse) {
                        invoke2(installedPresetsResponse);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(InstalledPresetsResponse installedPresetsResponse) {
                        Timber.INSTANCE.i("Response status: " + installedPresetsResponse.getStatus() + "; error: " + installedPresetsResponse.getErrorCode(), new Object[0]);
                        if (installedPresetsResponse.getStatus()) {
                            PresetLoadingHelper.this.getHandler().removeCallbacksAndMessages(null);
                            PresetLoadingHelper.this.doHandlePresets(installedPresetsResponse.getInstalledPresets(), actionOnSuccess);
                        } else {
                            Companion companion = PresetLoadingHelper.INSTANCE;
                            PresetLoadingHelper.mLoadingData = false;
                            PresetLoadingHelper.this.getMBleManager().clearTasks();
                            PresetLoadingHelper.this.retryLoadPresets(1000L, actionOnSuccess);
                        }
                    }
                };
                Consumer<? super InstalledPresetsResponse> consumer = new Consumer() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        PresetLoadingHelper.onLoadPresets$lambda$0(function1, obj);
                    }
                };
                final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper.onLoadPresets.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
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
                        Timber.INSTANCE.w(th);
                        PresetLoadingHelper.this.retryLoadPresets(1000L, actionOnSuccess);
                    }
                };
                Disposable it = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        PresetLoadingHelper.onLoadPresets$lambda$1(function12, obj);
                    }
                });
                BleManager mBleManager = getMBleManager();
                Intrinsics.checkNotNullExpressionValue(it, "it");
                mBleManager.addCommandDisposable(it);
                return;
            } catch (Exception unused) {
                getMBleManager().startScan();
                retryLoadPresets(5000L, actionOnSuccess);
                return;
            }
        }
        getMBleManager().startScan();
        retryLoadPresets(5000L, actionOnSuccess);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadPresets$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadPresets$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void retryLoadPresets(long delay, final Function1<? super List<MediaMetadataCompat>, Unit> actionOnSuccess) {
        mLoadingData = false;
        getMBleManager().clear();
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PresetLoadingHelper.retryLoadPresets$lambda$3(this.f$0, actionOnSuccess);
            }
        }, delay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void retryLoadPresets$lambda$3(PresetLoadingHelper this$0, Function1 actionOnSuccess) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(actionOnSuccess, "$actionOnSuccess");
        try {
            this$0.onLoadPresets(actionOnSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Observable<InstalledPresetsResponse> readInstalledPresets() {
        Observable<ByteArrayOutputStream> observableExecuteReadInstalledPresetsCommand = getMBleManager().executeReadInstalledPresetsCommand();
        final C04381 c04381 = new Function1<ByteArrayOutputStream, InstalledPresetsResponse>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper.readInstalledPresets.1
            @Override // kotlin.jvm.functions.Function1
            public final InstalledPresetsResponse invoke(ByteArrayOutputStream it) {
                Intrinsics.checkNotNullParameter(it, "it");
                byte[] byteArray = it.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                return new InstalledPresetsResponse(byteArray);
            }
        };
        Observable map = observableExecuteReadInstalledPresetsCommand.map(new Function() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return PresetLoadingHelper.readInstalledPresets$lambda$4(c04381, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(map, "mBleManager.executeReadI…ap response\n            }");
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final InstalledPresetsResponse readInstalledPresets$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (InstalledPresetsResponse) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doHandlePresets(final InstalledPresets installedPresets, final Function1<? super List<MediaMetadataCompat>, Unit> actionOnSuccess) {
        Timber.INSTANCE.i("doHandlePresets: %s", installedPresets);
        Single<List<ShopSoundPackageEntity>> singleObserveOn = ThorDatabase.INSTANCE.from(this.context).shopSoundPackageDao().getEntities().observeOn(AndroidSchedulers.mainThread());
        final Function1<List<? extends ShopSoundPackageEntity>, Unit> function1 = new Function1<List<? extends ShopSoundPackageEntity>, Unit>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper.doHandlePresets.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends ShopSoundPackageEntity> list) {
                invoke2((List<ShopSoundPackageEntity>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<ShopSoundPackageEntity> mSoundPackages) {
                try {
                    ArrayList arrayList = new ArrayList();
                    for (InstalledPreset installedPreset : installedPresets.getPresets()) {
                        Intrinsics.checkNotNullExpressionValue(mSoundPackages, "mSoundPackages");
                        for (ShopSoundPackageEntity shopSoundPackageEntity : mSoundPackages) {
                            if (shopSoundPackageEntity.getId() == installedPreset.getPackageId()) {
                                MainPresetPackage mainPresetPackage = new MainPresetPackage(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
                                mainPresetPackage.setPackageId(Integer.valueOf(installedPreset.getPackageId()));
                                mainPresetPackage.setName(shopSoundPackageEntity.getPkgName());
                                mainPresetPackage.setImageUrl(AutoMediaKt.getAutoImageUrlForMode(Short.valueOf(installedPreset.getMode()), shopSoundPackageEntity.getModeTypes()));
                                mainPresetPackage.setModeType(Short.valueOf(installedPreset.getMode()));
                                arrayList.add(mainPresetPackage);
                            }
                        }
                    }
                    this.presetsMetadata.clear();
                    List list = this.presetsMetadata;
                    ArrayList arrayList2 = arrayList;
                    PresetLoadingHelper presetLoadingHelper = this;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        arrayList3.add(AutoMediaKt.from(new MediaMetadataCompat.Builder(), presetLoadingHelper.getContext(), (MainPresetPackage) it.next()).build());
                    }
                    list.addAll(CollectionsKt.toList(arrayList3));
                    Companion companion = PresetLoadingHelper.INSTANCE;
                    PresetLoadingHelper.mLoadedData = true;
                    this.getMBleManager().setActivatedPresetIndex(installedPresets.getCurrentPresetId());
                    Object obj = arrayList.get(installedPresets.getCurrentPresetId());
                    Intrinsics.checkNotNullExpressionValue(obj, "tempPresets.get(installe….currentPresetId.toInt())");
                    MainPresetPackage mainPresetPackage2 = (MainPresetPackage) obj;
                    BleManager mBleManager = this.getMBleManager();
                    Integer packageId = mainPresetPackage2.getPackageId();
                    short sIntValue = packageId != null ? (short) packageId.intValue() : (short) 0;
                    Short modeType = mainPresetPackage2.getModeType();
                    short sShortValue = modeType != null ? modeType.shortValue() : (short) 0;
                    Integer versionId = mainPresetPackage2.getVersionId();
                    mBleManager.setActivatedPreset(new InstalledPreset(sIntValue, sShortValue, versionId != null ? (short) versionId.intValue() : (short) 0));
                    actionOnSuccess.invoke(this.presetsMetadata);
                } catch (Exception e) {
                    Timber.INSTANCE.e(e);
                }
            }
        };
        Consumer<? super List<ShopSoundPackageEntity>> consumer = new Consumer() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetLoadingHelper.doHandlePresets$lambda$5(function1, obj);
            }
        };
        final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.utils.auto.PresetLoadingHelper.doHandlePresets.2
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
        singleObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.utils.auto.PresetLoadingHelper$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetLoadingHelper.doHandlePresets$lambda$6(anonymousClass2, obj);
            }
        });
        mLoadingData = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doHandlePresets$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doHandlePresets$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final MediaMetadataCompat getActivePresetMetadata() {
        short mActivatedPresetIndex = getMBleManager().getMActivatedPresetIndex();
        if (mActivatedPresetIndex <= 0 || this.presetsMetadata.isEmpty() || mActivatedPresetIndex > this.presetsMetadata.size()) {
            return null;
        }
        return this.presetsMetadata.get(mActivatedPresetIndex - 1);
    }
}
