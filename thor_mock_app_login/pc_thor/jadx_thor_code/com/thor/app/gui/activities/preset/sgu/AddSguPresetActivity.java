package com.thor.app.gui.activities.preset.sgu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityAddSguPresetBinding;
import com.thor.app.bus.events.SendLogsEvent;
import com.thor.app.gui.activities.preset.sgu.items.SguSoundFavItem;
import com.thor.app.managers.UsersManager;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.xwray.groupie.GroupieAdapter;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: AddSguPresetActivity.kt */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0012\u0010\u001d\u001a\u00020\u001a2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002J\b\u0010 \u001a\u00020\u001aH\u0002J\b\u0010!\u001a\u00020\u001aH\u0002J\b\u0010\"\u001a\u00020\u001aH\u0002J\b\u0010#\u001a\u00020\u001aH\u0002J\u0012\u0010$\u001a\u00020\u001a2\b\u0010%\u001a\u0004\u0018\u00010&H\u0014J\u0010\u0010'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020,H\u0007J\u0010\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020-H\u0007J\u0010\u0010*\u001a\u00020\u001a2\u0006\u0010+\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020)H\u0002J\u0016\u00100\u001a\u00020\u001a2\f\u00101\u001a\b\u0012\u0004\u0012\u00020)02H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\b\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\b\u001a\u0004\b\u0016\u0010\u0017¨\u00063"}, d2 = {"Lcom/thor/app/gui/activities/preset/sgu/AddSguPresetActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/xwray/groupie/GroupieAdapter;", "getAdapter", "()Lcom/xwray/groupie/GroupieAdapter;", "adapter$delegate", "Lkotlin/Lazy;", "binding", "Lcom/carsystems/thor/app/databinding/ActivityAddSguPresetBinding;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "soundSet", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "viewModel", "Lcom/thor/app/gui/activities/preset/sgu/AddSguPresetViewModel;", "getViewModel", "()Lcom/thor/app/gui/activities/preset/sgu/AddSguPresetViewModel;", "viewModel$delegate", "handleAddEvent", "", "isAdded", "", "handleError", "message", "", "handleExtras", "initAdapter", "initToolbar", "observeUiState", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onFavSound", "sound", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "onMessageEvent", "event", "Lcom/thor/app/bus/events/SendLogsEvent;", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "onPlaySound", "updateData", "data", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@AndroidEntryPoint
/* loaded from: classes3.dex */
public final class AddSguPresetActivity extends Hilt_AddSguPresetActivity {
    private ActivityAddSguPresetBinding binding;
    private SguSoundSet soundSet;

    /* renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* renamed from: adapter$delegate, reason: from kotlin metadata */
    private final Lazy adapter = LazyKt.lazy(new Function0<GroupieAdapter>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$adapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final GroupieAdapter invoke() {
            return new GroupieAdapter();
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });
    private final FileLogger fileLogger = new FileLogger(this, null, 2, null);

    public AddSguPresetActivity() {
        final AddSguPresetActivity addSguPresetActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(AddSguPresetViewModel.class), new Function0<ViewModelStore>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = addSguPresetActivity.getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "viewModelStore");
                return viewModelStore;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = addSguPresetActivity.getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        }, new Function0<CreationExtras>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$special$$inlined$viewModels$default$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function02 = function0;
                if (function02 != null && (creationExtras = (CreationExtras) function02.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = addSguPresetActivity.getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "this.defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        });
    }

    private final AddSguPresetViewModel getViewModel() {
        return (AddSguPresetViewModel) this.viewModel.getValue();
    }

    private final GroupieAdapter getAdapter() {
        return (GroupieAdapter) this.adapter.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // com.thor.app.gui.activities.preset.sgu.Hilt_AddSguPresetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(this);
        ActivityAddSguPresetBinding activityAddSguPresetBindingInflate = ActivityAddSguPresetBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityAddSguPresetBindingInflate, "inflate(layoutInflater)");
        this.binding = activityAddSguPresetBindingInflate;
        if (activityAddSguPresetBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddSguPresetBindingInflate = null;
        }
        setContentView(activityAddSguPresetBindingInflate.getRoot());
        initToolbar();
        initAdapter();
        observeUiState();
        handleExtras();
    }

    private final void handleExtras() {
        SguSoundSet sguSoundSet = (SguSoundSet) getIntent().getParcelableExtra(SguSoundSet.BUNDLE_NAME);
        if (sguSoundSet == null) {
            throw new NullPointerException("SGU set was not passed");
        }
        this.soundSet = sguSoundSet;
        AddSguPresetViewModel viewModel = getViewModel();
        SguSoundSet sguSoundSet2 = this.soundSet;
        if (sguSoundSet2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundSet");
            sguSoundSet2 = null;
        }
        viewModel.fetchSoundFavoriteStatus(sguSoundSet2.getFiles());
    }

    private final void observeUiState() {
        final Function1<AddSguPresetUiState, Unit> function1 = new Function1<AddSguPresetUiState, Unit>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity.observeUiState.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AddSguPresetUiState addSguPresetUiState) {
                invoke2(addSguPresetUiState);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AddSguPresetUiState addSguPresetUiState) {
                if (addSguPresetUiState instanceof SguSoundsLoaded) {
                    AddSguPresetActivity.this.updateData(((SguSoundsLoaded) addSguPresetUiState).getSounds());
                } else if (addSguPresetUiState instanceof AddSguPresetError) {
                    AddSguPresetActivity.this.handleError(((AddSguPresetError) addSguPresetUiState).getMessage());
                } else if (addSguPresetUiState instanceof SguSoundAddedOrRemoved) {
                    AddSguPresetActivity.this.handleAddEvent(((SguSoundAddedOrRemoved) addSguPresetUiState).isAdded());
                }
            }
        };
        getViewModel().getUiState().observe(this, new Observer() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AddSguPresetActivity.observeUiState$lambda$0(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeUiState$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleAddEvent(boolean isAdded) {
        if (isAdded) {
            Toast.makeText(this, R.string.text_added_to_my_sounds, 0).show();
        } else {
            Toast.makeText(this, R.string.text_removed_from_my_sounds, 0).show();
        }
    }

    private final void initToolbar() {
        ActivityAddSguPresetBinding activityAddSguPresetBinding = this.binding;
        ActivityAddSguPresetBinding activityAddSguPresetBinding2 = null;
        if (activityAddSguPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddSguPresetBinding = null;
        }
        activityAddSguPresetBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivityAddSguPresetBinding activityAddSguPresetBinding3 = this.binding;
        if (activityAddSguPresetBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddSguPresetBinding3 = null;
        }
        activityAddSguPresetBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AddSguPresetActivity.initToolbar$lambda$1(this.f$0, view);
            }
        });
        ActivityAddSguPresetBinding activityAddSguPresetBinding4 = this.binding;
        if (activityAddSguPresetBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityAddSguPresetBinding2 = activityAddSguPresetBinding4;
        }
        activityAddSguPresetBinding2.toolbarWidget.enableBluetoothIndicator(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initToolbar$lambda$1(AddSguPresetActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void initAdapter() {
        getAdapter().setHasStableIds(true);
        ActivityAddSguPresetBinding activityAddSguPresetBinding = this.binding;
        if (activityAddSguPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityAddSguPresetBinding = null;
        }
        activityAddSguPresetBinding.recyclerView.setAdapter(getAdapter());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateData(List<SguSound> data) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : data) {
            if (!((SguSound) obj).getSoundFiles().isEmpty()) {
                arrayList.add(obj);
            }
        }
        GroupieAdapter adapter = getAdapter();
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(new SguSoundFavItem((SguSound) it.next(), new AddSguPresetActivity$updateData$1$1(this), new AddSguPresetActivity$updateData$1$2(this)));
        }
        adapter.replaceAll(arrayList3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onFavSound(SguSound sound) {
        AddSguPresetViewModel viewModel = getViewModel();
        SguSoundSet sguSoundSet = this.soundSet;
        if (sguSoundSet == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundSet");
            sguSoundSet = null;
        }
        int id = sguSoundSet.getId();
        Context baseContext = getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "this.baseContext");
        viewModel.changeSoundFavoriteStatus(sound, id, baseContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onPlaySound(SguSound sound) {
        getViewModel().playSound(sound);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(String message) {
        Timber.INSTANCE.e(message, new Object[0]);
        Toast.makeText(this, message, 1).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataRequestLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "<= D " + event.getDataDeCrypto() + " <= E " + event.getDataCrypto());
        this.fileLogger.i("<= D " + event.getDataDeCrypto(), new Object[0]);
        this.fileLogger.i("<= E " + event.getDataCrypto(), new Object[0]);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataResponseLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "=> D " + event.getDataDeCrypto() + " => E " + event.getDataCrypto());
        this.fileLogger.i("=> D " + event.getDataDeCrypto(), new Object[0]);
        this.fileLogger.i("=> E " + event.getDataCrypto(), new Object[0]);
    }

    @Subscribe
    public final void onMessageEvent(SendLogsEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        AddSguPresetActivity addSguPresetActivity = this;
        Uri uriForFile = FileProvider.getUriForFile(addSguPresetActivity, getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, addSguPresetActivity, null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), Dispatchers.getIO(), null, new C02061(uriForFile, event, null), 2, null);
    }

    /* compiled from: AddSguPresetActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$onMessageEvent$1", f = "AddSguPresetActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$onMessageEvent$1, reason: invalid class name and case insensitive filesystem */
    static final class C02061 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SendLogsEvent $event;
        final /* synthetic */ Uri $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02061(Uri uri, SendLogsEvent sendLogsEvent, Continuation<? super C02061> continuation) {
            super(2, continuation);
            this.$path = uri;
            this.$event = sendLogsEvent;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return AddSguPresetActivity.this.new C02061(this.$path, this.$event, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C02061) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Observable<BaseResponse> observableSendErrorLogToApi = AddSguPresetActivity.this.getUsersManager().sendErrorLogToApi(this.$path, this.$event.getErrorMessage());
                if (observableSendErrorLogToApi != null) {
                    final C00631 c00631 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity.onMessageEvent.1.1
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(Disposable disposable) {
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                            invoke2(disposable);
                            return Unit.INSTANCE;
                        }
                    };
                    Observable<BaseResponse> observableDoOnSubscribe = observableSendErrorLogToApi.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$onMessageEvent$1$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj2) {
                            c00631.invoke(obj2);
                        }
                    });
                    if (observableDoOnSubscribe != null) {
                        final AnonymousClass2 anonymousClass2 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity.onMessageEvent.1.2
                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                                invoke2(baseResponse);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(BaseResponse baseResponse) {
                                Log.i("SEND_LOGS", "Response: " + baseResponse);
                            }
                        };
                        observableDoOnSubscribe.subscribe(new Consumer() { // from class: com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity$onMessageEvent$1$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj2) {
                                anonymousClass2.invoke(obj2);
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
