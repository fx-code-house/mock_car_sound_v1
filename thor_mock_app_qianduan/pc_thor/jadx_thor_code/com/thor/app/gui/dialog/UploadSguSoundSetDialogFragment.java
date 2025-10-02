package com.thor.app.gui.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentDialogUploadSguSoundSetBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.sgu.ActiveAdapterAfterSgu;
import com.thor.app.bus.events.shop.main.DownloadSettingsFileSuccessEvent;
import com.thor.app.bus.events.shop.sgu.UploadSguSoundPackageSuccessfulEvent;
import com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.service.UploadFilesService;
import com.thor.app.service.state.UploadServiceState;
import com.thor.app.settings.Settings;
import com.thor.app.utils.converters.JsonConverterKt;
import com.thor.app.utils.converters.mappers.MappersKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.viewmodel.main.DownloadSoundPackageFragmentDialogViewModel;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import com.thor.networkmodule.model.responses.sgu.SguSoundJsonWrapper;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: UploadSguSoundSetDialogFragment.kt */
@Metadata(d1 = {"\u0000\u0085\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t*\u0001\u001b\u0018\u0000 A2\u00020\u0001:\u0001AB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010#\u001a\u00020\u001fH\u0002J\b\u0010&\u001a\u00020%H\u0002J\u0010\u0010'\u001a\u00020%2\u0006\u0010(\u001a\u00020\u0012H\u0002J\u0010\u0010)\u001a\u00020%2\u0006\u0010(\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020%H\u0002J\u0012\u0010,\u001a\u00020%2\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J$\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u0001042\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J\b\u00105\u001a\u00020%H\u0016J\u0010\u00106\u001a\u00020%2\u0006\u00107\u001a\u000208H\u0007J\u0010\u00106\u001a\u00020%2\u0006\u00107\u001a\u000209H\u0007J\b\u0010:\u001a\u00020%H\u0016J\b\u0010;\u001a\u00020%H\u0016J\b\u0010<\u001a\u00020%H\u0002J\b\u0010=\u001a\u00020%H\u0016J\u001a\u0010>\u001a\u00020%2\u0006\u0010?\u001a\u0002002\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J\b\u0010@\u001a\u00020%H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0018\u00010\u0006R\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006B"}, d2 = {"Lcom/thor/app/gui/dialog/UploadSguSoundSetDialogFragment;", "Lcom/thor/app/gui/dialog/dialogs/FullScreenDialogFragment;", "()V", "_binding", "Lcom/carsystems/thor/app/databinding/FragmentDialogUploadSguSoundSetBinding;", "binder", "Lcom/thor/app/service/UploadFilesService$UploadServiceBinder;", "Lcom/thor/app/service/UploadFilesService;", "binding", "getBinding", "()Lcom/carsystems/thor/app/databinding/FragmentDialogUploadSguSoundSetBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "handleLocalCancelState", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "handler", "Landroid/os/Handler;", "getHandler", "()Landroid/os/Handler;", "handler$delegate", "mBound", "", "serviceConnection", "com/thor/app/gui/dialog/UploadSguSoundSetDialogFragment$serviceConnection$1", "Lcom/thor/app/gui/dialog/UploadSguSoundSetDialogFragment$serviceConnection$1;", "showFragment", "soundSet", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "totalUploadSize", "", "uploadCounter", "uploadSoundSet", "addDownloadedSoundsToFavorite", "", "handleBundleData", "handleUploadCancel", "state", "holderUploadService", "Lcom/thor/app/service/state/UploadServiceState;", "onCancelUploading", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onDestroy", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "Lcom/thor/app/bus/events/shop/main/DownloadSettingsFileSuccessEvent;", "onPause", "onResume", "onShareLogs", "onStart", "onViewCreated", "view", "updateInfo", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UploadSguSoundSetDialogFragment extends FullScreenDialogFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private FragmentDialogUploadSguSoundSetBinding _binding;
    private UploadFilesService.UploadServiceBinder binder;
    private UploadServiceState.Stop handleLocalCancelState;
    private boolean mBound;
    private boolean showFragment;
    private SguSoundSet soundSet;
    private int totalUploadSize;
    private SguSoundSet uploadSoundSet;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.requireContext());
        }
    });

    /* renamed from: handler$delegate, reason: from kotlin metadata */
    private final Lazy handler = LazyKt.lazy(new Function0<Handler>() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$handler$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Handler invoke() {
            return new Handler(Looper.getMainLooper());
        }
    });
    private int uploadCounter = 1;
    private UploadSguSoundSetDialogFragment$serviceConnection$1 serviceConnection = new ServiceConnection() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$serviceConnection$1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            UploadSguSoundSetDialogFragment uploadSguSoundSetDialogFragment = this.this$0;
            Intrinsics.checkNotNull(service, "null cannot be cast to non-null type com.thor.app.service.UploadFilesService.UploadServiceBinder");
            uploadSguSoundSetDialogFragment.binder = (UploadFilesService.UploadServiceBinder) service;
            this.this$0.mBound = true;
            try {
                UploadFilesService.UploadServiceBinder uploadServiceBinder = this.this$0.binder;
                UploadFilesService this$0 = uploadServiceBinder != null ? uploadServiceBinder.getThis$0() : null;
                if (this$0 == null) {
                    return;
                }
                final UploadSguSoundSetDialogFragment uploadSguSoundSetDialogFragment2 = this.this$0;
                this$0.setProgressUpload(new Function1<UploadServiceState, Unit>() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$serviceConnection$1$onServiceConnected$1
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
                        uploadSguSoundSetDialogFragment2.holderUploadService(state);
                    }
                });
            } catch (Exception e) {
                Log.e("UploadSguSoundSetDialogFragment", "Error onServiceConnected", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            this.this$0.mBound = false;
        }
    };

    private final FragmentDialogUploadSguSoundSetBinding getBinding() {
        FragmentDialogUploadSguSoundSetBinding fragmentDialogUploadSguSoundSetBinding = this._binding;
        if (fragmentDialogUploadSguSoundSetBinding != null) {
            return fragmentDialogUploadSguSoundSetBinding;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final Handler getHandler() {
        return (Handler) this.handler.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void holderUploadService(final UploadServiceState state) {
        if (this.showFragment) {
            if (state instanceof UploadServiceState.ProgressUploading.UploadingSgu) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        UploadSguSoundSetDialogFragment.holderUploadService$lambda$0(this.f$0, state);
                    }
                });
                return;
            } else if (state instanceof UploadServiceState.Reconnect) {
                getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        UploadSguSoundSetDialogFragment.holderUploadService$lambda$1(this.f$0);
                    }
                });
                return;
            } else {
                if (state instanceof UploadServiceState.Stop) {
                    handleUploadCancel((UploadServiceState.Stop) state);
                    return;
                }
                return;
            }
        }
        if (state instanceof UploadServiceState.Stop) {
            try {
                requireActivity().unbindService(this.serviceConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.handleLocalCancelState = (UploadServiceState.Stop) state;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$0(UploadSguSoundSetDialogFragment this$0, UploadServiceState state) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        this$0.getBinding().progressBar.setVisibility(8);
        this$0.getBinding().updateProgress.setVisibility(0);
        UploadServiceState.ProgressUploading.UploadingSgu uploadingSgu = (UploadServiceState.ProgressUploading.UploadingSgu) state;
        this$0.uploadCounter = this$0.totalUploadSize - uploadingSgu.getFileLeft();
        this$0.getBinding().updateProgress.setProgress(uploadingSgu.getProgressSgu());
        this$0.updateInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void holderUploadService$lambda$1(UploadSguSoundSetDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().progressBarPolling.setVisibility(0);
    }

    private final void handleUploadCancel(final UploadServiceState.Stop state) {
        if (state instanceof UploadServiceState.Stop.Error) {
            requireActivity().unbindService(this.serviceConnection);
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    UploadSguSoundSetDialogFragment.handleUploadCancel$lambda$2(this.f$0, state);
                }
            });
            return;
        }
        if (state instanceof UploadServiceState.Stop.Cancel) {
            try {
                requireActivity().unbindService(this.serviceConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    UploadSguSoundSetDialogFragment.handleUploadCancel$lambda$3(this.f$0);
                }
            });
            if (!getBleManager().getMStateConnected()) {
                getBleManager().connect();
            }
            dismiss();
            return;
        }
        if (state instanceof UploadServiceState.Stop.Success) {
            if (this.mBound) {
                try {
                    requireActivity().unbindService(this.serviceConnection);
                } catch (Exception e2) {
                    Log.e("UploadSguSoundSetDialogFragment", "Error unbind service", e2);
                }
            }
            SguSoundSet sguSoundSet = this.soundSet;
            if (sguSoundSet == null) {
                Intrinsics.throwUninitializedPropertyAccessException("soundSet");
                sguSoundSet = null;
            }
            addDownloadedSoundsToFavorite(sguSoundSet);
            return;
        }
        if (Intrinsics.areEqual(state, UploadServiceState.Stop.ForceStop.INSTANCE)) {
            try {
                requireActivity().unbindService(this.serviceConnection);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    UploadSguSoundSetDialogFragment.handleUploadCancel$lambda$4(this.f$0);
                }
            });
            if (!getBleManager().getMStateConnected()) {
                getBleManager().connect();
            }
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$2(final UploadSguSoundSetDialogFragment this$0, UploadServiceState.Stop state) {
        AlertDialog alertDialogCreateAlertDialogUpdate;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(state, "$state");
        UploadServiceState.Stop.Error error = (UploadServiceState.Stop.Error) state;
        Toast.makeText(this$0.requireContext(), error.getMsg(), 1).show();
        if (Intrinsics.areEqual(error.getMsg(), "Cancel") || (alertDialogCreateAlertDialogUpdate = DialogManager.INSTANCE.createAlertDialogUpdate(this$0.requireContext(), "Error", error.getMsg(), new Function0<Unit>() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$handleUploadCancel$1$1
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
                if (!this.this$0.getBleManager().getMStateConnected()) {
                    this.this$0.getBleManager().connect();
                }
                this.this$0.dismiss();
            }
        })) == null) {
            return;
        }
        alertDialogCreateAlertDialogUpdate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$3(UploadSguSoundSetDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(this$0.requireContext(), "Cancel", 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleUploadCancel$lambda$4(UploadSguSoundSetDialogFragment this$0) {
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
        this._binding = FragmentDialogUploadSguSoundSetBinding.inflate(inflater, container, false);
        View root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) throws Exception {
        ObservableField<String> packageName;
        Intrinsics.checkNotNullParameter(view, "view");
        setCancelable(false);
        getBinding().setModel(new DownloadSoundPackageFragmentDialogViewModel());
        DownloadSoundPackageFragmentDialogViewModel model = getBinding().getModel();
        if (model != null && (packageName = model.getPackageName()) != null) {
            SguSoundSet sguSoundSet = this.uploadSoundSet;
            packageName.set(sguSoundSet != null ? sguSoundSet.getSetName() : null);
        }
        getBinding().textViewCancel.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UploadSguSoundSetDialogFragment.onViewCreated$lambda$7(this.f$0, view2);
            }
        });
        EventBus.getDefault().register(this);
        handleBundleData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$7(UploadSguSoundSetDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onCancelUploading();
    }

    private static final void onViewCreated$lambda$8(UploadSguSoundSetDialogFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onShareLogs();
    }

    private final void onShareLogs() {
        Uri uriForFile = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName(), FileLogger.Companion.newInstance$default(FileLogger.INSTANCE, requireContext(), null, 2, null).getLogsFile());
        Intrinsics.checkNotNullExpressionValue(uriForFile, "getUriForFile(\n         …           file\n        )");
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", FileLogger.LOGS_FILE_NAME);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.addFlags(1);
        intent.setType("plain/*");
        startActivity(intent);
    }

    private final void handleBundleData() throws Exception {
        ObservableField<String> packageName;
        ObservableBoolean isDownloading;
        DownloadSoundPackageFragmentDialogViewModel model = getBinding().getModel();
        if (model != null && (isDownloading = model.getIsDownloading()) != null) {
            isDownloading.set(true);
        }
        Bundle arguments = getArguments();
        SguSoundSet sguSoundSet = null;
        SguSoundSet sguSoundSet2 = arguments != null ? (SguSoundSet) arguments.getParcelable(SguSoundSet.BUNDLE_NAME) : null;
        if (sguSoundSet2 == null) {
            throw new Exception("No required arguments was passed!");
        }
        this.soundSet = sguSoundSet2;
        DownloadSoundPackageFragmentDialogViewModel model2 = getBinding().getModel();
        if (model2 != null && (packageName = model2.getPackageName()) != null) {
            SguSoundSet sguSoundSet3 = this.soundSet;
            if (sguSoundSet3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("soundSet");
                sguSoundSet3 = null;
            }
            packageName.set(sguSoundSet3.getSetName());
        }
        Gson gson = new Gson();
        SguSoundSet sguSoundSet4 = this.soundSet;
        if (sguSoundSet4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundSet");
            sguSoundSet4 = null;
        }
        String json = gson.toJson(sguSoundSet4);
        SguSoundSet sguSoundSet5 = this.soundSet;
        if (sguSoundSet5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundSet");
        } else {
            sguSoundSet = sguSoundSet5;
        }
        Iterator<T> it = sguSoundSet.getFiles().iterator();
        while (it.hasNext()) {
            this.totalUploadSize += ((SguSound) it.next()).getSoundFiles().size();
        }
        if (UploadFilesService.INSTANCE.getServiceStatus()) {
            return;
        }
        Intent intent = new Intent(requireContext(), (Class<?>) UploadFilesService.class);
        intent.setAction(UploadFilesService.ACTION_START_SGU);
        intent.putExtra(UploadFilesService.DEVICE_CONNECTION_ADDRESS_KEY, Settings.INSTANCE.getDeviceMacAddress());
        intent.putExtra(UploadFilesService.DEVICE_CONNECTION_SGU_SOUND_SET_KEY, json);
        requireActivity().startForegroundService(intent);
    }

    private final void onCancelUploading() {
        getBinding().progressBarPolling.setVisibility(0);
        UploadFilesService.UploadServiceBinder uploadServiceBinder = this.binder;
        if (uploadServiceBinder != null) {
            uploadServiceBinder.stopService();
        }
    }

    private final void updateInfo() {
        getHandler().post(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                UploadSguSoundSetDialogFragment.updateInfo$lambda$11(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateInfo$lambda$11(UploadSguSoundSetDialogFragment this$0) {
        ObservableField<String> info;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DownloadSoundPackageFragmentDialogViewModel model = this$0.getBinding().getModel();
        if (model == null || (info = model.getInfo()) == null) {
            return;
        }
        info.set(this$0.getString(R.string.text_uploading_counter, Integer.valueOf(this$0.uploadCounter), Integer.valueOf(this$0.totalUploadSize)));
    }

    private final void addDownloadedSoundsToFavorite(SguSoundSet uploadSoundSet) {
        Object next;
        SguSoundJsonWrapper sguSoundList = JsonConverterKt.toSguSoundList(Settings.INSTANCE.getSguSoundFavoritesJSON());
        if (sguSoundList == null) {
            sguSoundList = new SguSoundJsonWrapper(new ArrayList());
        }
        List<SguSoundJson> presets = sguSoundList.getPresets();
        if (presets == null) {
            presets = CollectionsKt.toMutableList((Collection) CollectionsKt.emptyList());
        }
        for (SguSound sguSound : uploadSoundSet.getFiles()) {
            Iterator<T> it = presets.iterator();
            while (true) {
                if (it.hasNext()) {
                    next = it.next();
                    if (((SguSoundJson) next).getId() == sguSound.getId()) {
                        break;
                    }
                } else {
                    next = null;
                    break;
                }
            }
            if (next == null) {
                sguSound.setSound_set_id(Integer.valueOf(uploadSoundSet.getId()));
                SguSoundJson sguSoundJsonModel = MappersKt.toSguSoundJsonModel(sguSound);
                if (sguSoundJsonModel != null) {
                    presets.add(sguSoundJsonModel);
                }
            }
        }
        String sguSoundJson = JsonConverterKt.toSguSoundJson(new SguSoundJsonWrapper(presets));
        Settings.INSTANCE.saveSguSoundFavoritesJSON(sguSoundJson);
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
        companion.from(contextRequireContext).sendSguJson(sguSoundJson);
        DataLayerManager.Companion companion2 = DataLayerManager.INSTANCE;
        Context contextRequireContext2 = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext2, "requireContext()");
        companion2.from(contextRequireContext2).sendSguList(MappersKt.toSguSoundModels(presets));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothDeviceRssiEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.getRssi() < -85) {
            getBinding().textSignal.setVisibility(0);
        } else {
            getBinding().textSignal.setVisibility(8);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(DownloadSettingsFileSuccessEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("FIND", "11");
        BleManager.executeActivatePresetCommand$default(getBleManager(), getBleManager().getServiceActivePresetIndex(), getBleManager().getMActivatedPreset(), null, 4, null);
        EventBus.getDefault().post(new ActiveAdapterAfterSgu());
        getHandler().postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                UploadSguSoundSetDialogFragment.onMessageEvent$lambda$16(this.f$0);
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMessageEvent$lambda$16(final UploadSguSoundSetDialogFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context contextRequireContext = this$0.requireContext();
        Context contextRequireContext2 = this$0.requireContext();
        Object[] objArr = new Object[1];
        SguSoundSet sguSoundSet = this$0.soundSet;
        if (sguSoundSet == null) {
            Intrinsics.throwUninitializedPropertyAccessException("soundSet");
            sguSoundSet = null;
        }
        objArr[0] = sguSoundSet.getSetName();
        AlertDialog alertDialogCreateAlertDialogUpdate = dialogManager.createAlertDialogUpdate(contextRequireContext, "Done uploading", contextRequireContext2.getString(R.string.update_data_s, objArr), new Function0<Unit>() { // from class: com.thor.app.gui.dialog.UploadSguSoundSetDialogFragment$onMessageEvent$1$1
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
                if (!this.this$0.getBleManager().getMStateConnected()) {
                    this.this$0.getBleManager().connect();
                }
                EventBus eventBus = EventBus.getDefault();
                SguSoundSet sguSoundSet2 = this.this$0.soundSet;
                if (sguSoundSet2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("soundSet");
                    sguSoundSet2 = null;
                }
                eventBus.post(new UploadSguSoundPackageSuccessfulEvent(sguSoundSet2));
                this.this$0.dismiss();
            }
        });
        if (alertDialogCreateAlertDialogUpdate != null) {
            alertDialogCreateAlertDialogUpdate.show();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this._binding = null;
        EventBus.getDefault().unregister(this);
        getHandler().removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /* compiled from: UploadSguSoundSetDialogFragment.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/gui/dialog/UploadSguSoundSetDialogFragment$Companion;", "", "()V", "newInstance", "Lcom/thor/app/gui/dialog/UploadSguSoundSetDialogFragment;", "model", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UploadSguSoundSetDialogFragment newInstance(SguSoundSet model) {
            Intrinsics.checkNotNullParameter(model, "model");
            UploadSguSoundSetDialogFragment uploadSguSoundSetDialogFragment = new UploadSguSoundSetDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(SguSoundSet.BUNDLE_NAME, model);
            uploadSguSoundSetDialogFragment.setArguments(bundle);
            return uploadSguSoundSetDialogFragment;
        }
    }
}
