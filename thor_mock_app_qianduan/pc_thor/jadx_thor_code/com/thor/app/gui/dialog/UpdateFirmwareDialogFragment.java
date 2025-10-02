package com.thor.app.gui.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentDialogUpdateFirmwareBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.service.UploadFilesService;
import com.thor.app.service.state.UploadServiceState;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.viewmodel.main.UpdateFirmwareFragmentDialogViewModel;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.firmware.FirmwareProfileShort;
import com.thor.networkmodule.model.responses.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: UpdateFirmwareDialogFragment.kt */
@Metadata(d1 = {"\u0000\u0089\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007*\u0001\u001e\u0018\u0000 D2\u00020\u00012\u00020\u0002:\u0001DB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010&\u001a\u00020'H\u0002J\b\u0010(\u001a\u00020'H\u0002J\u0010\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020\u0015H\u0002J\u0010\u0010+\u001a\u00020'2\u0006\u0010*\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020'H\u0002J\u0012\u0010.\u001a\u00020'2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\b\u00101\u001a\u00020'H\u0002J\u0012\u00102\u001a\u00020'2\b\u00103\u001a\u0004\u0018\u000104H\u0016J\u0012\u00105\u001a\u00020'2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J&\u00106\u001a\u0004\u0018\u0001042\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\b\u0010;\u001a\u00020'H\u0016J\u0010\u0010<\u001a\u00020'2\u0006\u0010=\u001a\u00020>H\u0007J\b\u0010?\u001a\u00020'H\u0016J\b\u0010@\u001a\u00020'H\u0016J\b\u0010A\u001a\u00020'H\u0016J\u001a\u0010B\u001a\u00020'2\u0006\u0010C\u001a\u0002042\b\u0010/\u001a\u0004\u0018\u000100H\u0017R\u0014\u0010\u0004\u001a\b\u0018\u00010\u0005R\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u000e\u0010 \u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010!\u001a\u00020\"8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u000e\u001a\u0004\b#\u0010$¨\u0006E"}, d2 = {"Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragment;", "Lcom/thor/app/gui/dialog/dialogs/FullScreenDialogFragment;", "Landroid/view/View$OnClickListener;", "()V", "binder", "Lcom/thor/app/service/UploadFilesService$UploadServiceBinder;", "Lcom/thor/app/service/UploadFilesService;", "binding", "Lcom/carsystems/thor/app/databinding/FragmentDialogUpdateFirmwareBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "database", "Lcom/thor/app/room/ThorDatabase;", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "database$delegate", "handleLocalCancelState", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "mBound", "", "serviceConnection", "com/thor/app/gui/dialog/UpdateFirmwareDialogFragment$serviceConnection$1", "Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragment$serviceConnection$1;", "showFragment", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "doOnLoadData", "", "doOnUpdateFirmware", "handleUploadCancel", "state", "holderUploadService", "Lcom/thor/app/service/state/UploadServiceState;", "logout", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCancelUpdateFirmware", "onClick", "v", "Landroid/view/View;", "onCreate", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onDestroyView", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "onPause", "onResume", "onStart", "onViewCreated", "view", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UpdateFirmwareDialogFragment extends FullScreenDialogFragment implements View.OnClickListener {
    private static final String BUNDLE_NAME;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private UploadFilesService.UploadServiceBinder binder;
    private FragmentDialogUpdateFirmwareBinding binding;
    private UploadServiceState.Stop handleLocalCancelState;
    private boolean mBound;
    private boolean showFragment;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.getContext());
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            UsersManager.Companion companion = UsersManager.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return companion.from(contextRequireContext);
        }
    });

    /* renamed from: database$delegate, reason: from kotlin metadata */
    private final Lazy database = LazyKt.lazy(new Function0<ThorDatabase>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$database$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ThorDatabase invoke() {
            ThorDatabase.Companion companion = ThorDatabase.INSTANCE;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            return companion.from(contextRequireContext);
        }
    });

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    private final UpdateFirmwareDialogFragment$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$serviceConnection$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            UpdateFirmwareDialogFragment updateFirmwareDialogFragment = this.this$0;
            Intrinsics.checkNotNull(service, "null cannot be cast to non-null type com.thor.app.service.UploadFilesService.UploadServiceBinder");
            updateFirmwareDialogFragment.binder = (UploadFilesService.UploadServiceBinder) service;
            this.this$0.mBound = true;
            UploadFilesService.UploadServiceBinder uploadServiceBinder = this.this$0.binder;
            UploadFilesService this$0 = uploadServiceBinder != null ? uploadServiceBinder.getThis$0() : null;
            if (this$0 == null) {
                return;
            }
            final UpdateFirmwareDialogFragment updateFirmwareDialogFragment2 = this.this$0;
            this$0.setProgressUpload(new Function1<UploadServiceState, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$serviceConnection$1$onServiceConnected$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(UploadServiceState uploadServiceState) {
                    invoke2(uploadServiceState);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(UploadServiceState state) {
                    Intrinsics.checkNotNullParameter(state, "state");
                    updateFirmwareDialogFragment2.holderUploadService(state);
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            this.this$0.mBound = false;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$8(View view) {
    }

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final ThorDatabase getDatabase() {
        return (ThorDatabase) this.database.getValue();
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void holderUploadService(final UploadServiceState state) {
        try {
            if (this.showFragment) {
                if (state instanceof UploadServiceState.ProgressUploading.UploadingFirmware) {
                    getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            UpdateFirmwareDialogFragment.holderUploadService$lambda$0(this.f$0, state);
                        }
                    });
                } else if (state instanceof UploadServiceState.Reconnect) {
                    getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            UpdateFirmwareDialogFragment.holderUploadService$lambda$1(this.f$0);
                        }
                    });
                } else if (state instanceof UploadServiceState.Stop) {
                    requireActivity().unbindService(this.serviceConnection);
                    this.handleLocalCancelState = (UploadServiceState.Stop) state;
                    handleUploadCancel((UploadServiceState.Stop) state);
                }
            } else if (state instanceof UploadServiceState.Stop) {
                requireActivity().unbindService(this.serviceConnection);
                this.handleLocalCancelState = (UploadServiceState.Stop) state;
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$0(UpdateFirmwareDialogFragment this$0, UploadServiceState state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this$0.binding;
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding2 = null;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        fragmentDialogUpdateFirmwareBinding.progressBar.setVisibility(8);
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding3 = this$0.binding;
        if (fragmentDialogUpdateFirmwareBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding3 = null;
        }
        fragmentDialogUpdateFirmwareBinding3.updateProgress.setVisibility(0);
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding4 = this$0.binding;
        if (fragmentDialogUpdateFirmwareBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogUpdateFirmwareBinding2 = fragmentDialogUpdateFirmwareBinding4;
        }
        fragmentDialogUpdateFirmwareBinding2.updateProgress.setProgress(((UploadServiceState.ProgressUploading.UploadingFirmware) state).getProgressFirmware());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$1(UpdateFirmwareDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this$0.binding;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        fragmentDialogUpdateFirmwareBinding.progressBar.setVisibility(0);
    }

    private final void handleUploadCancel(final UploadServiceState.Stop state) {
        if (state instanceof UploadServiceState.Stop.Error) {
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    UpdateFirmwareDialogFragment.handleUploadCancel$lambda$2(this.f$0, state);
                }
            });
            return;
        }
        if (state instanceof UploadServiceState.Stop.Cancel) {
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    UpdateFirmwareDialogFragment.handleUploadCancel$lambda$3(this.f$0);
                }
            });
            dismiss();
            return;
        }
        if (state instanceof UploadServiceState.Stop.Success) {
            getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    UpdateFirmwareDialogFragment.handleUploadCancel$lambda$4(this.f$0);
                }
            }, 4000L);
            return;
        }
        if (Intrinsics.areEqual(state, UploadServiceState.Stop.ForceStop.INSTANCE)) {
            try {
                requireActivity().unbindService(this.serviceConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    UpdateFirmwareDialogFragment.handleUploadCancel$lambda$5(this.f$0);
                }
            });
            if (!getBleManager().getMStateConnected()) {
                getBleManager().connect();
            }
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$2(final UpdateFirmwareDialogFragment this$0, UploadServiceState.Stop state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        UploadServiceState.Stop.Error error = (UploadServiceState.Stop.Error) state;
        Toast.makeText(this$0.requireContext(), error.getMsg(), 1).show();
        AlertDialog alertDialogCreateAlertDialogUpdate = DialogManager.INSTANCE.createAlertDialogUpdate(this$0.requireContext(), "Error", error.getMsg(), new Function0<Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$handleUploadCancel$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.dismiss();
            }
        });
        if (alertDialogCreateAlertDialogUpdate != null) {
            alertDialogCreateAlertDialogUpdate.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$3(UpdateFirmwareDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            Toast.makeText(this$0.requireContext(), "Cancel", 1).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$4(final UpdateFirmwareDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog alertDialogCreateAlertDialogUpdate = DialogManager.INSTANCE.createAlertDialogUpdate(this$0.requireContext(), "Done uploading", this$0.requireContext().getString(R.string.update_firmware), new Function0<Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$handleUploadCancel$3$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                this.this$0.logout();
            }
        });
        if (alertDialogCreateAlertDialogUpdate != null) {
            alertDialogCreateAlertDialogUpdate.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$5(UpdateFirmwareDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(this$0.requireContext(), "Cancel", 1).show();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        UploadServiceState.Stop stop;
        super.onResume();
        this.showFragment = true;
        if (UploadFilesService.INSTANCE.getServiceStatus() || (stop = this.handleLocalCancelState) == null || stop == null) {
            return;
        }
        handleUploadCancel(stop);
        this.handleLocalCancelState = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.showFragment = false;
    }

    @Override // com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mBound) {
            return;
        }
        requireActivity().bindService(new Intent(requireContext(), (Class<?>) UploadFilesService.class), this.serviceConnection, 1);
    }

    @Override // com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_update_firmware, container, false);
        Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate, "inflate(\n            inf…          false\n        )");
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = (FragmentDialogUpdateFirmwareBinding) viewDataBindingInflate;
        this.binding = fragmentDialogUpdateFirmwareBinding;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        return fragmentDialogUpdateFirmwareBinding.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this.binding;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        fragmentDialogUpdateFirmwareBinding.setModel(new UpdateFirmwareFragmentDialogViewModel());
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding2 = this.binding;
        if (fragmentDialogUpdateFirmwareBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding2 = null;
        }
        fragmentDialogUpdateFirmwareBinding2.textViewCancel.setOnClickListener(this);
        FirmwareProfile firmwareProfile = Settings.INSTANCE.getFirmwareProfile();
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding3 = this.binding;
        if (fragmentDialogUpdateFirmwareBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding3 = null;
        }
        fragmentDialogUpdateFirmwareBinding3.progressBar.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UpdateFirmwareDialogFragment.onViewCreated$lambda$8(view2);
            }
        });
        Bundle arguments = getArguments();
        if (arguments != null) {
            FirmwareProfileShort firmwareProfileShort = (FirmwareProfileShort) arguments.getParcelable(BUNDLE_NAME);
            if (firmwareProfileShort != null) {
                firmwareProfile = new FirmwareProfile(firmwareProfileShort.getIdFW(), firmwareProfileShort.getDevType(), firmwareProfileShort.getDevTypeName(), 0, firmwareProfileShort.getVersionFW(), firmwareProfileShort.getFwBinUrl(), false, 0, null, 448, null);
            }
            if (!UploadFilesService.INSTANCE.getServiceStatus()) {
                Intent intent = new Intent(requireContext(), (Class<?>) UploadFilesService.class);
                intent.setAction(UploadFilesService.ACTION_START_FW);
                intent.putExtra(UploadFilesService.DEVICE_CONNECTION_ADDRESS_KEY, Settings.INSTANCE.getDeviceMacAddress());
                intent.putExtra(UploadFilesService.DEVICE_CONNECTION_FIRMWARE_KEY, firmwareProfileShort != null ? firmwareProfileShort.getFwBinUrl() : null);
                requireActivity().startForegroundService(intent);
            }
        }
        if (!UploadFilesService.INSTANCE.getServiceStatus()) {
            Intent intent2 = new Intent(requireContext(), (Class<?>) UploadFilesService.class);
            intent2.setAction(UploadFilesService.ACTION_START_FW);
            intent2.putExtra(UploadFilesService.DEVICE_CONNECTION_ADDRESS_KEY, Settings.INSTANCE.getDeviceMacAddress());
            intent2.putExtra(UploadFilesService.DEVICE_CONNECTION_FIRMWARE_KEY, firmwareProfile != null ? firmwareProfile.getFwBinUrl() : null);
            requireActivity().startForegroundService(intent2);
        }
        doOnLoadData();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) throws SecurityException {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_cancel) {
            onCancelUpdateFirmware();
        }
    }

    private final void onCancelUpdateFirmware() {
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this.binding;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        fragmentDialogUpdateFirmwareBinding.progressBar.setVisibility(0);
        UploadFilesService.UploadServiceBinder uploadServiceBinder = this.binder;
        if (uploadServiceBinder != null) {
            uploadServiceBinder.stopService();
        }
    }

    private final void doOnLoadData() {
        ObservableField<String> info;
        ObservableBoolean isWaiting;
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this.binding;
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding2 = null;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        UpdateFirmwareFragmentDialogViewModel model = fragmentDialogUpdateFirmwareBinding.getModel();
        if (model != null && (isWaiting = model.getIsWaiting()) != null) {
            isWaiting.set(true);
        }
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding3 = this.binding;
        if (fragmentDialogUpdateFirmwareBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogUpdateFirmwareBinding2 = fragmentDialogUpdateFirmwareBinding3;
        }
        UpdateFirmwareFragmentDialogViewModel model2 = fragmentDialogUpdateFirmwareBinding2.getModel();
        if (model2 == null || (info = model2.getInfo()) == null) {
            return;
        }
        info.set(getString(R.string.text_download_software));
    }

    private final void doOnUpdateFirmware() {
        ObservableField<String> info;
        ObservableBoolean isUpdatingFirmware;
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this.binding;
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding2 = null;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        UpdateFirmwareFragmentDialogViewModel model = fragmentDialogUpdateFirmwareBinding.getModel();
        if (model != null && (isUpdatingFirmware = model.getIsUpdatingFirmware()) != null) {
            isUpdatingFirmware.set(true);
        }
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding3 = this.binding;
        if (fragmentDialogUpdateFirmwareBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogUpdateFirmwareBinding2 = fragmentDialogUpdateFirmwareBinding3;
        }
        UpdateFirmwareFragmentDialogViewModel model2 = fragmentDialogUpdateFirmwareBinding2.getModel();
        if (model2 == null || (info = model2.getInfo()) == null) {
            return;
        }
        info.set(getString(R.string.text_updating_software));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void logout() {
        Settings.saveIsCheckedEmrgencySituations(false);
        Observable<BaseResponse> observableRemoveNotification = getUsersManager().removeNotification();
        if (observableRemoveNotification != null) {
            final AnonymousClass1 anonymousClass1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment.logout.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                    invoke2(baseResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BaseResponse baseResponse) {
                    Timber.INSTANCE.i("Response: %s", baseResponse);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragment.logout$lambda$13(anonymousClass1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment.logout.2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }
            };
            observableRemoveNotification.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragment.logout$lambda$14(anonymousClass2, obj);
                }
            });
        }
        getBleManager().clear();
        getBleManager().disconnect(false);
        Settings.removeAllProperties();
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        Settings.clearCookies(contextRequireContext);
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext()");
        companion.from(contextRequireContext2).sendIsAccessSession(false);
        getDatabase().installedSoundPackageDao().deleteAllElements();
        getDatabase().shopSoundPackageDao().deleteAllElements();
        final Intent intent = new Intent(requireContext(), (Class<?>) SplashActivity.class);
        intent.setFlags(268468224);
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragment$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                UpdateFirmwareDialogFragment.logout$lambda$16(this.f$0, intent);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void logout$lambda$16(UpdateFirmwareDialogFragment this$0, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(intent, "$intent");
        this$0.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothDeviceRssiEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = null;
        if (event.getRssi() < -85) {
            FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding2 = this.binding;
            if (fragmentDialogUpdateFirmwareBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                fragmentDialogUpdateFirmwareBinding = fragmentDialogUpdateFirmwareBinding2;
            }
            fragmentDialogUpdateFirmwareBinding.textSignal.setVisibility(0);
            return;
        }
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding3 = this.binding;
        if (fragmentDialogUpdateFirmwareBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogUpdateFirmwareBinding = fragmentDialogUpdateFirmwareBinding3;
        }
        fragmentDialogUpdateFirmwareBinding.textSignal.setVisibility(8);
    }

    /* compiled from: UpdateFirmwareDialogFragment.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragment$Companion;", "", "()V", "BUNDLE_NAME", "", "getBUNDLE_NAME", "()Ljava/lang/String;", "newInstance", "Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragment;", "firmwareProfileShort", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getBUNDLE_NAME() {
            return UpdateFirmwareDialogFragment.BUNDLE_NAME;
        }

        public final UpdateFirmwareDialogFragment newInstance() {
            return new UpdateFirmwareDialogFragment();
        }

        public final UpdateFirmwareDialogFragment newInstance(FirmwareProfileShort firmwareProfileShort) {
            Intrinsics.checkNotNullParameter(firmwareProfileShort, "firmwareProfileShort");
            UpdateFirmwareDialogFragment updateFirmwareDialogFragment = new UpdateFirmwareDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(UpdateFirmwareDialogFragment.INSTANCE.getBUNDLE_NAME(), firmwareProfileShort);
            updateFirmwareDialogFragment.setArguments(bundle);
            return updateFirmwareDialogFragment;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("UpdateFirmwareDialogFragment", "UpdateFirmwareDialogFrag…nt::class.java.simpleName");
        BUNDLE_NAME = "UpdateFirmwareDialogFragment";
    }
}
