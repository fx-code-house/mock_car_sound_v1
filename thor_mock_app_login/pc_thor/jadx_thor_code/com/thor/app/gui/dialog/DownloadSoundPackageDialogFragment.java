package com.thor.app.gui.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
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
import com.carsystems.thor.app.databinding.FragmentDialogDownloadPackageBinding;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.shop.main.UploadSoundPackageSuccessfulEvent;
import com.thor.app.databinding.model.RunningAppOnPhoneStatus;
import com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.service.UploadFilesService;
import com.thor.app.service.state.UploadServiceState;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.viewmodel.main.DownloadSoundPackageFragmentDialogViewModel;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageFile;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
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

/* compiled from: DownloadSoundPackageDialogFragment.kt */
@Metadata(d1 = {"\u0000¡\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007*\u0001\"\u0018\u0000 L2\u00020\u00012\u00020\u0002:\u0001LB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020+H\u0002J\b\u0010-\u001a\u00020+H\u0002J\b\u0010.\u001a\u00020+H\u0002J\u0010\u0010/\u001a\u00020+2\u0006\u00100\u001a\u00020\u0010H\u0002J\u0010\u00101\u001a\u00020+2\u0006\u00100\u001a\u000202H\u0002J\u0012\u00103\u001a\u00020+2\b\u00104\u001a\u0004\u0018\u000105H\u0016J\u0010\u00106\u001a\u00020+2\u0006\u00107\u001a\u000208H\u0002J\u0012\u00109\u001a\u00020+2\b\u0010:\u001a\u0004\u0018\u00010;H\u0016J\u0012\u0010<\u001a\u00020+2\b\u00104\u001a\u0004\u0018\u000105H\u0016J&\u0010=\u001a\u0004\u0018\u00010;2\u0006\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010A2\b\u00104\u001a\u0004\u0018\u000105H\u0016J\b\u0010B\u001a\u00020+H\u0016J\u0010\u0010C\u001a\u00020+2\u0006\u0010D\u001a\u00020EH\u0007J\u0010\u0010C\u001a\u00020+2\u0006\u0010D\u001a\u00020FH\u0007J\b\u0010G\u001a\u00020+H\u0016J\b\u0010H\u001a\u00020+H\u0016J\b\u0010I\u001a\u00020+H\u0016J\u001a\u0010J\u001a\u00020+2\u0006\u0010K\u001a\u00020;2\b\u00104\u001a\u0004\u0018\u000105H\u0016R\u0014\u0010\u0004\u001a\b\u0018\u00010\u0005R\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0004\n\u0002\u0010#R\u000e\u0010$\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010%\u001a\u00020&8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\u000e\u001a\u0004\b'\u0010(¨\u0006M"}, d2 = {"Lcom/thor/app/gui/dialog/DownloadSoundPackageDialogFragment;", "Lcom/thor/app/gui/dialog/dialogs/FullScreenDialogFragment;", "Landroid/view/View$OnClickListener;", "()V", "binder", "Lcom/thor/app/service/UploadFilesService$UploadServiceBinder;", "Lcom/thor/app/service/UploadFilesService;", "binding", "Lcom/carsystems/thor/app/databinding/FragmentDialogDownloadPackageBinding;", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "getFileLogger", "()Lcom/thor/app/utils/logs/loggers/FileLogger;", "fileLogger$delegate", "Lkotlin/Lazy;", "handleLocalCancelState", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "mBleManager", "Lcom/thor/app/managers/BleManager;", "getMBleManager", "()Lcom/thor/app/managers/BleManager;", "mBleManager$delegate", "mBound", "", "mSoundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "savedActivePresetIndex", "", "serviceConnection", "com/thor/app/gui/dialog/DownloadSoundPackageDialogFragment$serviceConnection$1", "Lcom/thor/app/gui/dialog/DownloadSoundPackageDialogFragment$serviceConnection$1;", "showFragment", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "doOnStartUpload", "", "doOnUploadRulesSoundPackage", "doOnUploadRulesSoundSamples", "doOnUploadSoundSamples", "handleUploadCancel", "state", "holderUploadService", "Lcom/thor/app/service/state/UploadServiceState;", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCancelDownload", "cancelReason", "Lcom/thor/app/gui/dialog/DownloadSoundPackageDialogFragment$Companion$CancelReason;", "onClick", "v", "Landroid/view/View;", "onCreate", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onDestroyView", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "onPause", "onResume", "onStart", "onViewCreated", "view", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DownloadSoundPackageDialogFragment extends FullScreenDialogFragment implements View.OnClickListener {
    public static final String BUNDLE_SAMPLE_RULE = "bundle_sample_rule";
    public static final String BUNDLE_SOUND_PACKAGE_RULE = "bundle_sound_package_rule";
    public static final String BUNDLE_SOUND_SAMPLE = "bundle_sound_sample";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private UploadFilesService.UploadServiceBinder binder;
    private FragmentDialogDownloadPackageBinding binding;
    private UploadServiceState.Stop handleLocalCancelState;
    private boolean mBound;
    private ShopSoundPackage mSoundPackage;
    private short savedActivePresetIndex;
    private boolean showFragment;

    /* renamed from: mBleManager$delegate, reason: from kotlin metadata */
    private final Lazy mBleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$mBleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.getContext());
        }
    });

    /* renamed from: fileLogger$delegate, reason: from kotlin metadata */
    private final Lazy fileLogger = LazyKt.lazy(new Function0<FileLogger>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$fileLogger$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final FileLogger invoke() {
            return FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, this.this$0.requireContext(), null, 2, null);
        }
    });

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$usersManager$2
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

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    private final DownloadSoundPackageDialogFragment$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$serviceConnection$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragment = this.this$0;
            Intrinsics.checkNotNull(service, "null cannot be cast to non-null type com.thor.app.service.UploadFilesService.UploadServiceBinder");
            downloadSoundPackageDialogFragment.binder = (UploadFilesService.UploadServiceBinder) service;
            this.this$0.mBound = true;
            UploadFilesService.UploadServiceBinder uploadServiceBinder = this.this$0.binder;
            UploadFilesService this$0 = uploadServiceBinder != null ? uploadServiceBinder.getThis$0() : null;
            if (this$0 == null) {
                return;
            }
            final DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragment2 = this.this$0;
            this$0.setProgressUpload(new Function1<UploadServiceState, Unit>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$serviceConnection$1$onServiceConnected$1
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
                    downloadSoundPackageDialogFragment2.holderUploadService(state);
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            this.this$0.mBound = false;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getMBleManager() {
        return (BleManager) this.mBleManager.getValue();
    }

    private final FileLogger getFileLogger() {
        return (FileLogger) this.fileLogger.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void holderUploadService(final UploadServiceState state) {
        if (this.showFragment) {
            if (state instanceof UploadServiceState.Downloading) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadSoundPackageDialogFragment.holderUploadService$lambda$0(this.f$0);
                    }
                });
                return;
            }
            if (state instanceof UploadServiceState.ProgressUploading.UploadingSoundV2.UploadSample) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadSoundPackageDialogFragment.holderUploadService$lambda$1(this.f$0, state);
                    }
                });
                return;
            }
            if (state instanceof UploadServiceState.ProgressUploading.UploadingSoundV3) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadSoundPackageDialogFragment.holderUploadService$lambda$2(this.f$0, state);
                    }
                });
                return;
            }
            if (state instanceof UploadServiceState.ProgressUploading.UploadingSoundV2.UploadRules) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadSoundPackageDialogFragment.holderUploadService$lambda$3(this.f$0, state);
                    }
                });
                return;
            }
            if (state instanceof UploadServiceState.ProgressUploading.UploadingSoundV2.UploadRulesModel) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadSoundPackageDialogFragment.holderUploadService$lambda$4(this.f$0, state);
                    }
                });
                return;
            }
            if (state instanceof UploadServiceState.Reconnect) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        DownloadSoundPackageDialogFragment.holderUploadService$lambda$5(this.f$0);
                    }
                });
                return;
            } else {
                if (state instanceof UploadServiceState.Stop) {
                    try {
                        requireActivity().unbindService(this.serviceConnection);
                    } catch (Exception unused) {
                    }
                    UploadServiceState.Stop stop = (UploadServiceState.Stop) state;
                    this.handleLocalCancelState = stop;
                    handleUploadCancel(stop);
                    return;
                }
                return;
            }
        }
        if (state instanceof UploadServiceState.Stop) {
            try {
                requireActivity().unbindService(this.serviceConnection);
            } catch (Exception unused2) {
            }
            this.handleLocalCancelState = (UploadServiceState.Stop) state;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$0(DownloadSoundPackageDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doOnStartUpload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$1(DownloadSoundPackageDialogFragment this$0, UploadServiceState state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        this$0.doOnUploadSoundSamples();
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this$0.binding;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        fragmentDialogDownloadPackageBinding.updateProgress.setProgress(((UploadServiceState.ProgressUploading.UploadingSoundV2.UploadSample) state).getProgress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$2(DownloadSoundPackageDialogFragment this$0, UploadServiceState state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        this$0.doOnUploadSoundSamples();
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this$0.binding;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        fragmentDialogDownloadPackageBinding.updateProgress.setProgress(((UploadServiceState.ProgressUploading.UploadingSoundV3) state).getProgress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$3(DownloadSoundPackageDialogFragment this$0, UploadServiceState state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        this$0.doOnUploadRulesSoundSamples();
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this$0.binding;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        fragmentDialogDownloadPackageBinding.updateProgress.setProgress(((UploadServiceState.ProgressUploading.UploadingSoundV2.UploadRules) state).getProgress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$4(DownloadSoundPackageDialogFragment this$0, UploadServiceState state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        this$0.doOnUploadRulesSoundPackage();
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this$0.binding;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        fragmentDialogDownloadPackageBinding.updateProgress.setProgress(((UploadServiceState.ProgressUploading.UploadingSoundV2.UploadRulesModel) state).getProgress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$5(DownloadSoundPackageDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this$0.binding;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        fragmentDialogDownloadPackageBinding.progressBarPolling.setVisibility(0);
    }

    private final void handleUploadCancel(final UploadServiceState.Stop state) {
        if (state instanceof UploadServiceState.Stop.Error) {
            getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadSoundPackageDialogFragment.handleUploadCancel$lambda$6(this.f$0, state);
                }
            }, 4000L);
            return;
        }
        if (state instanceof UploadServiceState.Stop.Cancel) {
            getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadSoundPackageDialogFragment.handleUploadCancel$lambda$7(this.f$0);
                }
            }, 4000L);
            return;
        }
        if (state instanceof UploadServiceState.Stop.Success) {
            getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadSoundPackageDialogFragment.handleUploadCancel$lambda$8(this.f$0);
                }
            }, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        } else if (Intrinsics.areEqual(state, UploadServiceState.Stop.ForceStop.INSTANCE)) {
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    DownloadSoundPackageDialogFragment.handleUploadCancel$lambda$9(this.f$0);
                }
            });
            if (!getMBleManager().getMStateConnected()) {
                getMBleManager().connect();
            }
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$6(final DownloadSoundPackageDialogFragment this$0, UploadServiceState.Stop state) {
        AlertDialog alertDialogCreateAlertDialogUpdate;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        if (this$0.isAdded()) {
            UploadServiceState.Stop.Error error = (UploadServiceState.Stop.Error) state;
            Toast.makeText(this$0.requireContext(), error.getMsg(), 1).show();
            if (Intrinsics.areEqual(error.getMsg(), "Cancel") || (alertDialogCreateAlertDialogUpdate = DialogManager.INSTANCE.createAlertDialogUpdate(this$0.requireContext(), "Error", error.getMsg(), new Function0<Unit>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$handleUploadCancel$1$1
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
                    if (!this.this$0.getMBleManager().getMStateConnected()) {
                        this.this$0.getMBleManager().connect();
                    }
                    this.this$0.dismiss();
                }
            })) == null) {
                return;
            }
            alertDialogCreateAlertDialogUpdate.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$7(DownloadSoundPackageDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isAdded()) {
            if (!this$0.getMBleManager().getMStateConnected()) {
                this$0.getMBleManager().connect();
            }
            try {
                Toast.makeText(this$0.requireContext(), "Cancel", 1).show();
                this$0.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$8(final DownloadSoundPackageDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isAdded()) {
            DialogManager dialogManager = DialogManager.INSTANCE;
            Context contextRequireContext = this$0.requireContext();
            Context contextRequireContext2 = this$0.requireContext();
            Object[] objArr = new Object[1];
            ShopSoundPackage shopSoundPackage = this$0.mSoundPackage;
            if (shopSoundPackage == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPackage");
                shopSoundPackage = null;
            }
            objArr[0] = shopSoundPackage.getPkgName();
            AlertDialog alertDialogCreateAlertDialogUpdate = dialogManager.createAlertDialogUpdate(contextRequireContext, "Done uploading", contextRequireContext2.getString(R.string.update_data_s, objArr), new Function0<Unit>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$handleUploadCancel$3$1
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
                    if (!this.this$0.getMBleManager().getMStateConnected()) {
                        this.this$0.getMBleManager().connect();
                    }
                    EventBus eventBus = EventBus.getDefault();
                    ShopSoundPackage shopSoundPackage2 = this.this$0.mSoundPackage;
                    if (shopSoundPackage2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mSoundPackage");
                        shopSoundPackage2 = null;
                    }
                    eventBus.post(new UploadSoundPackageSuccessfulEvent(shopSoundPackage2));
                    this.this$0.dismiss();
                }
            });
            if (alertDialogCreateAlertDialogUpdate != null) {
                alertDialogCreateAlertDialogUpdate.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$9(DownloadSoundPackageDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            Toast.makeText(this$0.requireContext(), "Cancel", 1).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            Parcelable parcelable = arguments.getParcelable(ShopSoundPackage.INSTANCE.getBUNDLE_NAME());
            Intrinsics.checkNotNull(parcelable);
            this.mSoundPackage = (ShopSoundPackage) parcelable;
        }
        this.savedActivePresetIndex = getMBleManager().getMActivatedPresetIndex();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_download_package, container, false);
        Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate, "inflate(\n            inf…          false\n        )");
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = (FragmentDialogDownloadPackageBinding) viewDataBindingInflate;
        this.binding = fragmentDialogDownloadPackageBinding;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        return fragmentDialogDownloadPackageBinding.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ObservableField<String> packageName;
        ObservableField<String> packageName2;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this.binding;
        ShopSoundPackage shopSoundPackage = null;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        fragmentDialogDownloadPackageBinding.setModel(new DownloadSoundPackageFragmentDialogViewModel());
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding2 = this.binding;
        if (fragmentDialogDownloadPackageBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding2 = null;
        }
        DownloadSoundPackageFragmentDialogViewModel model = fragmentDialogDownloadPackageBinding2.getModel();
        if (model != null && (packageName2 = model.getPackageName()) != null) {
            ShopSoundPackage shopSoundPackage2 = this.mSoundPackage;
            if (shopSoundPackage2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPackage");
                shopSoundPackage2 = null;
            }
            packageName2.set(shopSoundPackage2.getPkgName());
        }
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding3 = this.binding;
        if (fragmentDialogDownloadPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding3 = null;
        }
        fragmentDialogDownloadPackageBinding3.textViewCancel.setOnClickListener(this);
        Settings.INSTANCE.saveLoadingSoundPackage(true);
        Context context = getContext();
        if (context != null) {
            DataLayerManager.INSTANCE.from(context).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.LOADING_PACKAGE);
            DataLayerManager dataLayerManagerFrom = DataLayerManager.INSTANCE.from(context);
            FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding4 = this.binding;
            if (fragmentDialogDownloadPackageBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentDialogDownloadPackageBinding4 = null;
            }
            DownloadSoundPackageFragmentDialogViewModel model2 = fragmentDialogDownloadPackageBinding4.getModel();
            dataLayerManagerFrom.sendLoadingProgress((model2 == null || (packageName = model2.getPackageName()) == null) ? null : packageName.get(), 0);
        }
        if (!UploadFilesService.INSTANCE.getServiceStatus()) {
            Intent intent = new Intent(requireContext(), (Class<?>) UploadFilesService.class);
            intent.setAction(UploadFilesService.ACTION_START_PRESET);
            intent.putExtra(UploadFilesService.DEVICE_CONNECTION_ADDRESS_KEY, Settings.INSTANCE.getDeviceMacAddress());
            ShopSoundPackage shopSoundPackage3 = this.mSoundPackage;
            if (shopSoundPackage3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSoundPackage");
            } else {
                shopSoundPackage = shopSoundPackage3;
            }
            intent.putExtra(UploadFilesService.DEVICE_CONNECTION_PACKAGE_ID_KEY, shopSoundPackage.getId());
            requireActivity().startForegroundService(intent);
        }
        doOnStartUpload();
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
        Context context = getContext();
        if (context != null) {
            DataLayerManager.INSTANCE.from(context).sendIsRunningAppOnPhone(RunningAppOnPhoneStatus.OFF);
            DataLayerManager.INSTANCE.from(context).sendLoadingClose(false);
        }
        Settings.INSTANCE.saveLoadingSoundPackage(false);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_cancel) {
            onCancelDownload(Companion.CancelReason.USER);
        }
    }

    private final void onCancelDownload(Companion.CancelReason cancelReason) {
        if (this.showFragment) {
            FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this.binding;
            if (fragmentDialogDownloadPackageBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentDialogDownloadPackageBinding = null;
            }
            fragmentDialogDownloadPackageBinding.progressBarPolling.setVisibility(0);
            UploadFilesService.UploadServiceBinder uploadServiceBinder = this.binder;
            if (uploadServiceBinder != null) {
                uploadServiceBinder.stopService();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isConnected()) {
            return;
        }
        UsersManager usersManager = getUsersManager();
        ShopSoundPackage shopSoundPackage = this.mSoundPackage;
        if (shopSoundPackage == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSoundPackage");
            shopSoundPackage = null;
        }
        Observable<BaseResponse> observableSendStatisticsLoadFailed = usersManager.sendStatisticsLoadFailed(shopSoundPackage.getId());
        if (observableSendStatisticsLoadFailed != null && (observableSubscribeOn = observableSendStatisticsLoadFailed.subscribeOn(Schedulers.io())) != null && (observableObserveOn = observableSubscribeOn.observeOn(Schedulers.io())) != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment.onMessageEvent.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                    invoke2(baseResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BaseResponse baseResponse) {
                    AlertDialog alertDialogCreateErrorAlertDialog;
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(DownloadSoundPackageDialogFragment.this.getContext(), baseResponse.getError(), baseResponse.getCode())) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DownloadSoundPackageDialogFragment.onMessageEvent$lambda$16(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment.onMessageEvent.2
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
                    AlertDialog alertDialogCreateErrorAlertDialog;
                    if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(DownloadSoundPackageDialogFragment.this.getContext(), th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(DownloadSoundPackageDialogFragment.this.getContext(), th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.DownloadSoundPackageDialogFragment$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DownloadSoundPackageDialogFragment.onMessageEvent$lambda$17(function12, obj);
                }
            });
        }
        onCancelDownload(Companion.CancelReason.DISCONNECT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$16(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothDeviceRssiEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = null;
        if (event.getRssi() < -85) {
            FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding2 = this.binding;
            if (fragmentDialogDownloadPackageBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                fragmentDialogDownloadPackageBinding = fragmentDialogDownloadPackageBinding2;
            }
            fragmentDialogDownloadPackageBinding.textSignal.setVisibility(0);
            return;
        }
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding3 = this.binding;
        if (fragmentDialogDownloadPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogDownloadPackageBinding = fragmentDialogDownloadPackageBinding3;
        }
        fragmentDialogDownloadPackageBinding.textSignal.setVisibility(8);
    }

    private final void doOnStartUpload() {
        ObservableField<String> info;
        ObservableBoolean isDownloading;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this.binding;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding2 = null;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        DownloadSoundPackageFragmentDialogViewModel model = fragmentDialogDownloadPackageBinding.getModel();
        if (model != null && (isDownloading = model.getIsDownloading()) != null) {
            isDownloading.set(true);
        }
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding3 = this.binding;
        if (fragmentDialogDownloadPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogDownloadPackageBinding2 = fragmentDialogDownloadPackageBinding3;
        }
        DownloadSoundPackageFragmentDialogViewModel model2 = fragmentDialogDownloadPackageBinding2.getModel();
        if (model2 == null || (info = model2.getInfo()) == null) {
            return;
        }
        info.set(getString(R.string.text_download_sound_package));
    }

    private final void doOnUploadSoundSamples() {
        ObservableField<String> info;
        ObservableBoolean isUploading;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this.binding;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding2 = null;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        DownloadSoundPackageFragmentDialogViewModel model = fragmentDialogDownloadPackageBinding.getModel();
        if (model != null && (isUploading = model.getIsUploading()) != null) {
            isUploading.set(true);
        }
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding3 = this.binding;
        if (fragmentDialogDownloadPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogDownloadPackageBinding2 = fragmentDialogDownloadPackageBinding3;
        }
        DownloadSoundPackageFragmentDialogViewModel model2 = fragmentDialogDownloadPackageBinding2.getModel();
        if (model2 == null || (info = model2.getInfo()) == null) {
            return;
        }
        info.set(getString(R.string.text_download_sound_samples));
    }

    private final void doOnUploadRulesSoundSamples() {
        ObservableField<String> info;
        ObservableBoolean isUploading;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this.binding;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding2 = null;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        DownloadSoundPackageFragmentDialogViewModel model = fragmentDialogDownloadPackageBinding.getModel();
        if (model != null && (isUploading = model.getIsUploading()) != null) {
            isUploading.set(true);
        }
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding3 = this.binding;
        if (fragmentDialogDownloadPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogDownloadPackageBinding2 = fragmentDialogDownloadPackageBinding3;
        }
        DownloadSoundPackageFragmentDialogViewModel model2 = fragmentDialogDownloadPackageBinding2.getModel();
        if (model2 == null || (info = model2.getInfo()) == null) {
            return;
        }
        info.set(getString(R.string.text_download_rules_of_sound_samples));
    }

    private final void doOnUploadRulesSoundPackage() {
        ObservableField<String> info;
        ObservableBoolean isUploading;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding = this.binding;
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding2 = null;
        if (fragmentDialogDownloadPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogDownloadPackageBinding = null;
        }
        DownloadSoundPackageFragmentDialogViewModel model = fragmentDialogDownloadPackageBinding.getModel();
        if (model != null && (isUploading = model.getIsUploading()) != null) {
            isUploading.set(true);
        }
        FragmentDialogDownloadPackageBinding fragmentDialogDownloadPackageBinding3 = this.binding;
        if (fragmentDialogDownloadPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDialogDownloadPackageBinding2 = fragmentDialogDownloadPackageBinding3;
        }
        DownloadSoundPackageFragmentDialogViewModel model2 = fragmentDialogDownloadPackageBinding2.getModel();
        if (model2 == null || (info = model2.getInfo()) == null) {
            return;
        }
        info.set(getString(R.string.text_download_rules_of_sound_package));
    }

    /* compiled from: DownloadSoundPackageDialogFragment.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u000eB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001c\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/thor/app/gui/dialog/DownloadSoundPackageDialogFragment$Companion;", "", "()V", "BUNDLE_SAMPLE_RULE", "", "BUNDLE_SOUND_PACKAGE_RULE", "BUNDLE_SOUND_SAMPLE", "newInstance", "Lcom/thor/app/gui/dialog/DownloadSoundPackageDialogFragment;", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "files", "", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageFile;", "CancelReason", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {

        /* compiled from: DownloadSoundPackageDialogFragment.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lcom/thor/app/gui/dialog/DownloadSoundPackageDialogFragment$Companion$CancelReason;", "", "(Ljava/lang/String;I)V", "BLE_ERROR", "SERVER_ERROR", "DISCONNECT", "USER", "NOT_CONNECTED", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public enum CancelReason {
            BLE_ERROR,
            SERVER_ERROR,
            DISCONNECT,
            USER,
            NOT_CONNECTED
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DownloadSoundPackageDialogFragment newInstance(ShopSoundPackage soundPackage, List<SoundPackageFile> files) {
            Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
            Intrinsics.checkNotNullParameter(files, "files");
            DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragment = new DownloadSoundPackageDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(ShopSoundPackage.INSTANCE.getBUNDLE_NAME(), soundPackage);
            bundle.putParcelable(DownloadSoundPackageDialogFragment.BUNDLE_SOUND_SAMPLE, files.get(0));
            bundle.putParcelable(DownloadSoundPackageDialogFragment.BUNDLE_SAMPLE_RULE, files.get(1));
            bundle.putParcelable(DownloadSoundPackageDialogFragment.BUNDLE_SOUND_PACKAGE_RULE, files.get(2));
            downloadSoundPackageDialogFragment.setArguments(bundle);
            return downloadSoundPackageDialogFragment;
        }

        public final DownloadSoundPackageDialogFragment newInstance(ShopSoundPackage soundPackage) {
            Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
            DownloadSoundPackageDialogFragment downloadSoundPackageDialogFragment = new DownloadSoundPackageDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(ShopSoundPackage.INSTANCE.getBUNDLE_NAME(), soundPackage);
            downloadSoundPackageDialogFragment.setArguments(bundle);
            return downloadSoundPackageDialogFragment;
        }
    }
}
