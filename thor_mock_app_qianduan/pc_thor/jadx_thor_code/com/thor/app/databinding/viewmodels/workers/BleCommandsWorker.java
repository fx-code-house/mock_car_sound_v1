package com.thor.app.databinding.viewmodels.workers;

import android.content.Context;
import android.os.Handler;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.carsystems.thor.app.R;
import com.google.android.gms.common.util.Hex;
import com.google.firebase.messaging.Constants;
import com.thor.app.managers.BleManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.response.other.InstalledPresetsResponse;
import com.thor.businessmodule.model.MainPresetPackage;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;
import timber.log.Timber;

/* compiled from: BleCommandsWorker.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 -2\u00020\u0001:\u0001-B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0017\u0010\u0019\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001a\u001a\u00020\u0018H\u0002¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fJ\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010%\u001a\u00020!H\u0002J\u001a\u0010&\u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\b\u0010'\u001a\u0004\u0018\u00010\u0018H\u0002J\u0010\u0010&\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020(H\u0002J \u0010)\u001a\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0016\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010\u0018H\u0002J\b\u0010,\u001a\u00020!H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006."}, d2 = {"Lcom/thor/app/databinding/viewmodels/workers/BleCommandsWorker;", "Landroidx/work/Worker;", "context", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "getContext", "()Landroid/content/Context;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "Lkotlin/Lazy;", "mBleManager", "Lcom/thor/app/managers/BleManager;", "getMBleManager", "()Lcom/thor/app/managers/BleManager;", "doWork", "Landroidx/work/ListenableWorker$Result;", "getModeTypeFrom", "", "mode", "", "getPackageIdBy", Constants.FirelogAnalytics.PARAM_PACKAGE_NAME, "(Ljava/lang/String;)Ljava/lang/Integer;", "mapMainPresetToInstaledPreset", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPreset;", "preset", "Lcom/thor/businessmodule/model/MainPresetPackage;", "onNextPreset", "", "installedPresets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "onPreviousPreset", "onTurnOffPreset", "onTurnOnPreset", "userInput", "", "parseUserInput", "Lkotlin/Pair;", "input", "retryWork", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class BleCommandsWorker extends Worker {
    public static final String ACTION_NEXT_PRESET = "action_next_preset";
    public static final String ACTION_PREVIOUS_PRESET = "action_previous_preset";
    public static final String ACTION_TURN_OFF_PRESET = "action_turn_off_preset";
    public static final String ACTION_TURN_ON_PRESET = "action_turn_on_preset";
    public static final String INPUT_DATA_COMMAND = "command";
    public static final String INPUT_DATA_PRESET_INFO = "preset_info";
    public static final String INPUT_DATA_USER_COMMAND = "user_command";
    private final Context context;

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler;
    private final BleManager mBleManager;

    public final Context getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BleCommandsWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(workerParams, "workerParams");
        this.context = context;
        this.mBleManager = BleManager.INSTANCE.from(context);
        this.handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$handler$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Handler invoke() {
                return new Handler(this.this$0.getContext().getMainLooper());
            }
        });
    }

    public final BleManager getMBleManager() {
        return this.mBleManager;
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        Timber.INSTANCE.d("doWork: start", new Object[0]);
        if (!Settings.INSTANCE.isAccessSession()) {
            Timber.INSTANCE.d("doWork: un-authorized", new Object[0]);
            ListenableWorker.Result resultFailure = ListenableWorker.Result.failure();
            Intrinsics.checkNotNullExpressionValue(resultFailure, "failure()");
            return resultFailure;
        }
        if (this.mBleManager.isBleEnabledAndDeviceConnected()) {
            getHandler().removeCallbacksAndMessages(null);
            Observable<ByteArrayOutputStream> observableExecuteReadInstalledPresetsCommand = this.mBleManager.executeReadInstalledPresetsCommand();
            final AnonymousClass1 anonymousClass1 = new Function1<ByteArrayOutputStream, ObservableSource<? extends InstalledPresets>>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker.doWork.1
                @Override // kotlin.jvm.functions.Function1
                public final ObservableSource<? extends InstalledPresets> invoke(ByteArrayOutputStream it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    byte[] byteArray = it.toByteArray();
                    Timber.INSTANCE.d("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                    InstalledPresetsResponse installedPresetsResponse = new InstalledPresetsResponse(byteArray);
                    if (installedPresetsResponse.getStatus()) {
                        Timber.INSTANCE.d("Response is correct: %s", installedPresetsResponse.getInstalledPresets());
                        return Observable.just(installedPresetsResponse.getInstalledPresets());
                    }
                    return Observable.just(null);
                }
            };
            Observable<R> observableFlatMap = observableExecuteReadInstalledPresetsCommand.flatMap(new Function() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return BleCommandsWorker.doWork$lambda$0(anonymousClass1, obj);
                }
            });
            final AnonymousClass2 anonymousClass2 = new Function1<Observable<Throwable>, ObservableSource<?>>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker.doWork.2
                @Override // kotlin.jvm.functions.Function1
                public final ObservableSource<?> invoke(Observable<Throwable> flowable) {
                    Intrinsics.checkNotNullParameter(flowable, "flowable");
                    return flowable.delay(1500L, TimeUnit.MILLISECONDS);
                }
            };
            Observable observableRetryWhen = observableFlatMap.retryWhen(new Function() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return BleCommandsWorker.doWork$lambda$1(anonymousClass2, obj);
                }
            });
            final Function1<InstalledPresets, Unit> function1 = new Function1<InstalledPresets, Unit>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker.doWork.3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(InstalledPresets installedPresets) {
                    invoke2(installedPresets);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(InstalledPresets installedPresets) {
                    boolean z = false;
                    if (installedPresets == null) {
                        Timber.INSTANCE.e("InstalledPresets is null!", new Object[0]);
                    }
                    String string = BleCommandsWorker.this.getInputData().getString(BleCommandsWorker.INPUT_DATA_COMMAND);
                    if (string != null) {
                        switch (string.hashCode()) {
                            case 500685086:
                                if (string.equals(BleCommandsWorker.ACTION_PREVIOUS_PRESET)) {
                                    BleCommandsWorker.this.onPreviousPreset(installedPresets);
                                    break;
                                }
                                break;
                            case 634063142:
                                if (string.equals(BleCommandsWorker.ACTION_TURN_ON_PRESET)) {
                                    String string2 = BleCommandsWorker.this.getInputData().getString(BleCommandsWorker.INPUT_DATA_PRESET_INFO);
                                    if (string2 != null) {
                                        if (string2.length() > 0) {
                                            z = true;
                                        }
                                    }
                                    if (!z) {
                                        BleCommandsWorker bleCommandsWorker = BleCommandsWorker.this;
                                        bleCommandsWorker.onTurnOnPreset(installedPresets, bleCommandsWorker.getInputData().getString(BleCommandsWorker.INPUT_DATA_USER_COMMAND));
                                        break;
                                    } else {
                                        BleCommandsWorker bleCommandsWorker2 = BleCommandsWorker.this;
                                        bleCommandsWorker2.onTurnOnPreset(installedPresets, bleCommandsWorker2.getInputData().getString(BleCommandsWorker.INPUT_DATA_PRESET_INFO));
                                        break;
                                    }
                                }
                                break;
                            case 1117275304:
                                if (string.equals(BleCommandsWorker.ACTION_TURN_OFF_PRESET)) {
                                    BleCommandsWorker.this.onTurnOffPreset();
                                    break;
                                }
                                break;
                            case 1490151714:
                                if (string.equals(BleCommandsWorker.ACTION_NEXT_PRESET)) {
                                    BleCommandsWorker.this.onNextPreset(installedPresets);
                                    break;
                                }
                                break;
                        }
                    }
                }
            };
            Consumer consumer = new Consumer() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleCommandsWorker.doWork$lambda$2(function1, obj);
                }
            };
            final AnonymousClass4 anonymousClass4 = new Function1<Throwable, Unit>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker.doWork.4
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                    Timber.INSTANCE.w(th);
                }
            };
            observableRetryWhen.blockingSubscribe(consumer, new Consumer() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BleCommandsWorker.doWork$lambda$3(anonymousClass4, obj);
                }
            });
            Timber.INSTANCE.d("doWork: end", new Object[0]);
            ListenableWorker.Result resultSuccess = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess, "success()");
            return resultSuccess;
        }
        Timber.INSTANCE.d("doWork: retry", new Object[0]);
        this.mBleManager.startScan();
        retryWork();
        ListenableWorker.Result resultFailure2 = ListenableWorker.Result.failure();
        Intrinsics.checkNotNullExpressionValue(resultFailure2, "failure()");
        return resultFailure2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource doWork$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (ObservableSource) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ObservableSource doWork$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (ObservableSource) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doWork$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doWork$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void retryWork() {
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                BleCommandsWorker.retryWork$lambda$4(this.f$0);
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void retryWork$lambda$4(BleCommandsWorker this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doWork();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onTurnOnPreset(final InstalledPresets installedPresets, String userInput) {
        Timber.INSTANCE.d("onTurnOnPreset", new Object[0]);
        if (userInput == null) {
            Timber.INSTANCE.e("User input is empty!", new Object[0]);
            return;
        }
        Pair<Integer, Integer> userInput2 = parseUserInput(userInput);
        if (userInput2 == null) {
            Timber.INSTANCE.e("Can't parse user input!", new Object[0]);
            return;
        }
        final int iIntValue = userInput2.getFirst().intValue();
        final int iIntValue2 = userInput2.getSecond().intValue();
        ThorDatabase.Companion companion = ThorDatabase.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        Single<List<ShopSoundPackageEntity>> singleObserveOn = companion.from(applicationContext).shopSoundPackageDao().getEntities().observeOn(AndroidSchedulers.mainThread());
        final Function1<List<? extends ShopSoundPackageEntity>, Unit> function1 = new Function1<List<? extends ShopSoundPackageEntity>, Unit>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$onTurnOnPreset$result$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends ShopSoundPackageEntity> list) {
                invoke2((List<ShopSoundPackageEntity>) list);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:27:0x00c1  */
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final void invoke2(java.util.List<com.thor.app.room.entity.ShopSoundPackageEntity> r24) {
                /*
                    r23 = this;
                    r0 = r23
                    r1 = r24
                    timber.log.Timber$Forest r2 = timber.log.Timber.INSTANCE
                    r3 = 0
                    java.lang.Object[] r4 = new java.lang.Object[r3]
                    java.lang.String r5 = "loaded shopSounds from database"
                    r2.d(r5, r4)
                    java.util.ArrayList r2 = new java.util.ArrayList
                    r2.<init>()
                    com.thor.businessmodule.bluetooth.model.other.InstalledPresets r4 = r1
                    java.util.LinkedHashSet r4 = r4.getPresets()
                    java.lang.Iterable r4 = (java.lang.Iterable) r4
                    java.util.Iterator r4 = r4.iterator()
                L1f:
                    boolean r5 = r4.hasNext()
                    if (r5 == 0) goto L87
                    java.lang.Object r5 = r4.next()
                    com.thor.businessmodule.bluetooth.model.other.InstalledPreset r5 = (com.thor.businessmodule.bluetooth.model.other.InstalledPreset) r5
                    java.lang.String r6 = "mSoundPackages"
                    kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r6)
                    r6 = r1
                    java.lang.Iterable r6 = (java.lang.Iterable) r6
                    java.util.Iterator r6 = r6.iterator()
                L37:
                    boolean r7 = r6.hasNext()
                    if (r7 == 0) goto L1f
                    java.lang.Object r7 = r6.next()
                    com.thor.app.room.entity.ShopSoundPackageEntity r7 = (com.thor.app.room.entity.ShopSoundPackageEntity) r7
                    int r8 = r7.getId()
                    short r9 = r5.getPackageId()
                    if (r8 != r9) goto L37
                    com.thor.businessmodule.model.MainPresetPackage r8 = new com.thor.businessmodule.model.MainPresetPackage
                    r11 = 0
                    r12 = 0
                    r13 = 0
                    r14 = 0
                    r15 = 0
                    r16 = 0
                    r17 = 0
                    r18 = 0
                    r19 = 0
                    r20 = 0
                    r21 = 1023(0x3ff, float:1.434E-42)
                    r22 = 0
                    r10 = r8
                    r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
                    short r9 = r5.getPackageId()
                    java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                    r8.setPackageId(r9)
                    java.lang.String r7 = r7.getPkgName()
                    r8.setName(r7)
                    short r7 = r5.getMode()
                    java.lang.Short r7 = java.lang.Short.valueOf(r7)
                    r8.setModeType(r7)
                    r2.add(r8)
                    goto L37
                L87:
                    r1 = r2
                    java.lang.Iterable r1 = (java.lang.Iterable) r1
                    int r4 = r3
                    int r5 = r4
                    java.util.Iterator r1 = r1.iterator()
                L92:
                    boolean r6 = r1.hasNext()
                    r7 = 1
                    if (r6 == 0) goto Lc5
                    java.lang.Object r6 = r1.next()
                    r8 = r6
                    com.thor.businessmodule.model.MainPresetPackage r8 = (com.thor.businessmodule.model.MainPresetPackage) r8
                    java.lang.Integer r9 = r8.getPackageId()
                    if (r9 != 0) goto La7
                    goto Lc1
                La7:
                    int r9 = r9.intValue()
                    if (r9 != r4) goto Lc1
                    java.lang.Short r8 = r8.getModeType()
                    if (r8 == 0) goto Lbc
                    short r8 = r8.shortValue()
                    short r9 = (short) r5
                    if (r8 != r9) goto Lbc
                    r8 = r7
                    goto Lbd
                Lbc:
                    r8 = r3
                Lbd:
                    if (r8 == 0) goto Lc1
                    r8 = r7
                    goto Lc2
                Lc1:
                    r8 = r3
                Lc2:
                    if (r8 == 0) goto L92
                    goto Lc6
                Lc5:
                    r6 = 0
                Lc6:
                    com.thor.businessmodule.model.MainPresetPackage r6 = (com.thor.businessmodule.model.MainPresetPackage) r6
                    java.util.List r2 = (java.util.List) r2
                    int r1 = kotlin.collections.CollectionsKt.indexOf(r2, r6)
                    int r1 = r1 + r7
                    com.thor.app.databinding.viewmodels.workers.BleCommandsWorker r2 = r2
                    short r1 = (short) r1
                    com.thor.app.databinding.viewmodels.workers.BleCommandsWorker.access$onTurnOnPreset(r2, r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$onTurnOnPreset$result$1.invoke2(java.util.List):void");
            }
        };
        Consumer<? super List<ShopSoundPackageEntity>> consumer = new Consumer() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleCommandsWorker.onTurnOnPreset$lambda$5(function1, obj);
            }
        };
        final BleCommandsWorker$onTurnOnPreset$result$2 bleCommandsWorker$onTurnOnPreset$result$2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$onTurnOnPreset$result$2
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
        Intrinsics.checkNotNullExpressionValue(singleObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.databinding.viewmodels.workers.BleCommandsWorker$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleCommandsWorker.onTurnOnPreset$lambda$6(bleCommandsWorker$onTurnOnPreset$result$2, obj);
            }
        }), "private fun onTurnOnPres…, { Timber.e(it) })\n    }");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTurnOnPreset$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTurnOnPreset$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final InstalledPreset mapMainPresetToInstaledPreset(MainPresetPackage preset) {
        Integer versionId;
        Short modeType;
        Integer packageId;
        short sIntValue = 0;
        short sIntValue2 = (preset == null || (packageId = preset.getPackageId()) == null) ? (short) 0 : (short) packageId.intValue();
        short sShortValue = (preset == null || (modeType = preset.getModeType()) == null) ? (short) 0 : modeType.shortValue();
        if (preset != null && (versionId = preset.getVersionId()) != null) {
            sIntValue = (short) versionId.intValue();
        }
        return new InstalledPreset(sIntValue2, sShortValue, sIntValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onTurnOffPreset() {
        Timber.INSTANCE.d("onTurnOffPreset", new Object[0]);
        BleManager.executeActivatePresetCommand$default(this.mBleManager, (short) 0, new InstalledPreset((short) 0, (short) 0, (short) 0), null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onTurnOnPreset(short preset) {
        Timber.INSTANCE.d("onTurnOnPreset: " + ((int) preset), new Object[0]);
        BleManager.executeActivatePresetCommand$default(this.mBleManager, preset, new InstalledPreset((short) 0, (short) 0, (short) 0), null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onNextPreset(InstalledPresets installedPresets) {
        int i = 0;
        Timber.INSTANCE.d("onNextPreset", new Object[0]);
        int currentPresetId = installedPresets.getCurrentPresetId() + 1;
        if (currentPresetId <= installedPresets.getPresets().size()) {
            i = currentPresetId;
        } else if (installedPresets.getPresets().size() != 0) {
            i = 1;
        }
        onTurnOnPreset((short) i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPreviousPreset(InstalledPresets installedPresets) {
        Timber.INSTANCE.d("onPreviousPreset", new Object[0]);
        int currentPresetId = installedPresets.getCurrentPresetId() - 1;
        if (currentPresetId <= 0) {
            currentPresetId = installedPresets.getPresets().size();
        }
        onTurnOnPreset((short) currentPresetId);
    }

    private final Pair<Integer, Integer> parseUserInput(String input) {
        List listSplit$default;
        if (input == null) {
            return null;
        }
        String string = this.context.getString(R.string.command_turn_on_part);
        if (string == null) {
            string = "";
        }
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.stri…mmand_turn_on_part) ?: \"\"");
        if (string.length() == 0) {
            Timber.INSTANCE.e("Command part is null or empty!", new Object[0]);
        }
        CollectionsKt.emptyList();
        if (StringsKt.startsWith$default(input, string, false, 2, (Object) null)) {
            String strSubstring = input.substring(string.length());
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            listSplit$default = StringsKt.split$default((CharSequence) StringsKt.trim((CharSequence) strSubstring).toString(), new String[]{StringUtils.SPACE}, false, 0, 6, (Object) null);
        } else {
            listSplit$default = StringsKt.split$default((CharSequence) StringsKt.trim((CharSequence) input).toString(), new String[]{StringUtils.SPACE}, false, 0, 6, (Object) null);
        }
        int modeTypeFrom = getModeTypeFrom((String) CollectionsKt.last(listSplit$default));
        if (modeTypeFrom < 0) {
            Timber.INSTANCE.e("Unrecognized mode type!", new Object[0]);
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int size = listSplit$default.size();
        for (int i = 0; i < size; i++) {
            if (i != 0 && i != listSplit$default.size() - 1) {
                sb.append(StringUtils.SPACE);
            }
            if (i != listSplit$default.size() - 1 && !Intrinsics.areEqual(listSplit$default.get(i), StringUtils.SPACE)) {
                sb.append((String) listSplit$default.get(i));
            }
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "name.toString()");
        Integer packageIdBy = getPackageIdBy(string2);
        if (packageIdBy != null) {
            return new Pair<>(Integer.valueOf(packageIdBy.intValue()), Integer.valueOf(modeTypeFrom));
        }
        return null;
    }

    private final int getModeTypeFrom(String mode) {
        if (mode == null) {
            return -1;
        }
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        String lowerCase = mode.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String string = this.context.getString(R.string.text_sport);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.text_sport)");
        Locale ROOT = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        String lowerCase2 = string.toLowerCase(ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
        if (Intrinsics.areEqual(lowerCase, lowerCase2)) {
            return 2;
        }
        String string2 = this.context.getString(R.string.text_city);
        Intrinsics.checkNotNullExpressionValue(string2, "context.getString(R.string.text_city)");
        Locale ROOT2 = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
        String lowerCase3 = string2.toLowerCase(ROOT2);
        Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
        if (Intrinsics.areEqual(lowerCase, lowerCase3)) {
            return 1;
        }
        String string3 = this.context.getString(R.string.text_own);
        Intrinsics.checkNotNullExpressionValue(string3, "context.getString(R.string.text_own)");
        Locale ROOT3 = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT3, "ROOT");
        String lowerCase4 = string3.toLowerCase(ROOT3);
        Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
        return Intrinsics.areEqual(lowerCase, lowerCase4) ? 3 : -1;
    }

    private final Integer getPackageIdBy(String packageName) {
        String lowerCase;
        ThorDatabase.Companion companion = ThorDatabase.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        List<ShopSoundPackageEntity> listBlockingGet = companion.from(applicationContext).shopSoundPackageDao().getEntities().blockingGet();
        Integer numValueOf = null;
        if (listBlockingGet == null) {
            return null;
        }
        Iterator<ShopSoundPackageEntity> it = listBlockingGet.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ShopSoundPackageEntity next = it.next();
            String pkgName = next.getPkgName();
            if (pkgName != null) {
                Locale locale = Locale.getDefault();
                Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
                lowerCase = pkgName.toLowerCase(locale);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            } else {
                lowerCase = null;
            }
            Locale locale2 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale2, "getDefault()");
            String lowerCase2 = packageName.toLowerCase(locale2);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            if (Intrinsics.areEqual(lowerCase, lowerCase2)) {
                numValueOf = Integer.valueOf(next.getId());
                break;
            }
        }
        if (numValueOf == null) {
            Timber.INSTANCE.e("Can't find package id in sound packages!", new Object[0]);
        }
        return numValueOf;
    }
}
