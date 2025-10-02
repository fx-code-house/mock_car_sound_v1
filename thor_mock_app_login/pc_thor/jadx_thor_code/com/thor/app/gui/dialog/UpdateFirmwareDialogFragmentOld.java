package com.thor.app.gui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwnerKt;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentDialogUpdateFirmwareBinding;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.util.Hex;
import com.thor.app.bus.events.BluetoothDeviceConnectionChangedEvent;
import com.thor.app.bus.events.BluetoothDeviceRssiEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEventOld;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareErrorEvent;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareProgressEvent;
import com.thor.app.bus.events.bluetooth.firmware.UpdateFirmwareSuccessfulEvent;
import com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld;
import com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.CorotineScopeKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.ReloadUploadingFirmwareStatus;
import com.thor.businessmodule.bluetooth.request.other.WriteFirmwareBleRequest;
import com.thor.businessmodule.bluetooth.response.other.ApplyUpdateFirmwareBleResponse;
import com.thor.businessmodule.bluetooth.response.other.ReloadUploadingFirmwareBleResponse;
import com.thor.businessmodule.bus.events.BluetoothCommandErrorEvent;
import com.thor.businessmodule.viewmodel.main.UpdateFirmwareFragmentDialogViewModel;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.firmware.FirmwareProfileShort;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Response;
import timber.log.Timber;

/* compiled from: UpdateFirmwareDialogFragmentOld.kt */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0006\u0018\u0000 =2\u00020\u00012\u00020\u0002:\u0001=B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0002J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0018H\u0002J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0012\u0010 \u001a\u00020\u00182\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0018H\u0002J\u0012\u0010$\u001a\u00020\u00182\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\u0012\u0010'\u001a\u00020\u00182\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J&\u0010(\u001a\u0004\u0018\u00010&2\u0006\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010-\u001a\u00020\u0018H\u0016J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u000200H\u0007J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u000201H\u0007J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u000202H\u0007J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u000203H\u0007J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u000204H\u0007J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010/\u001a\u000205H\u0007J\u0017\u00106\u001a\u00020\u00182\b\u00107\u001a\u0004\u0018\u000108H\u0002¢\u0006\u0002\u00109J\u001a\u0010:\u001a\u00020\u00182\u0006\u0010;\u001a\u00020&2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0017J\b\u0010<\u001a\u00020\u0018H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000f\u001a\u0004\b\u0014\u0010\u0015¨\u0006>"}, d2 = {"Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragmentOld;", "Lcom/thor/app/gui/dialog/dialogs/FullScreenDialogFragment;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/FragmentDialogUpdateFirmwareBinding;", "logger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "getLogger", "()Lcom/thor/app/utils/logs/loggers/FileLogger;", "mBleManager", "Lcom/thor/app/managers/BleManager;", "getMBleManager", "()Lcom/thor/app/managers/BleManager;", "mBleManager$delegate", "Lkotlin/Lazy;", "mFirmwareFile", "", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "doOnApplyUpdateFirmware", "", "doOnLoadData", "doOnUpdateFirmware", "doUpdateFirmware", "request", "Lcom/thor/businessmodule/bluetooth/request/other/WriteFirmwareBleRequest;", "executePoiling", "formatFlash", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCancelUpdateFirmware", "onClick", "v", "Landroid/view/View;", "onCreate", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onDestroyView", "onMessageEvent", "event", "Lcom/thor/app/bus/events/BluetoothDeviceConnectionChangedEvent;", "Lcom/thor/app/bus/events/BluetoothDeviceRssiEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/UpdateFirmwareErrorEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/UpdateFirmwareProgressEvent;", "Lcom/thor/app/bus/events/bluetooth/firmware/UpdateFirmwareSuccessfulEvent;", "Lcom/thor/businessmodule/bus/events/BluetoothCommandErrorEvent;", "onStartUploadFirmware", "blockNumber", "", "(Ljava/lang/Short;)V", "onViewCreated", "view", "startUploadFirmware", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UpdateFirmwareDialogFragmentOld extends FullScreenDialogFragment implements View.OnClickListener {
    private static final String BUNDLE_NAME;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private FragmentDialogUpdateFirmwareBinding binding;
    private byte[] mFirmwareFile;
    private final FileLogger logger = FileLogger.INSTANCE.newInstance(getContext(), getClass().getSimpleName());

    /* renamed from: mBleManager$delegate, reason: from kotlin metadata */
    private final Lazy mBleManager = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<BleManager>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$mBleManager$2
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
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$usersManager$2
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

    public final FileLogger getLogger() {
        return this.logger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getMBleManager() {
        return (BleManager) this.mBleManager.getValue();
    }

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0, types: [T, com.thor.networkmodule.model.firmware.FirmwareProfile] */
    /* JADX WARN: Type inference failed for: r3v4, types: [T, com.thor.networkmodule.model.firmware.FirmwareProfile] */
    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FirmwareProfileShort firmwareProfileShort;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        getMBleManager().setFromOldMode(false);
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
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = Settings.INSTANCE.getFirmwareProfile();
        Bundle arguments = getArguments();
        if (arguments != null && (firmwareProfileShort = (FirmwareProfileShort) arguments.getParcelable(BUNDLE_NAME)) != null) {
            objectRef.element = new FirmwareProfile(firmwareProfileShort.getIdFW(), firmwareProfileShort.getDevType(), firmwareProfileShort.getDevTypeName(), 0, firmwareProfileShort.getVersionFW(), firmwareProfileShort.getFwBinUrl(), false, 0, null, 448, null);
        }
        final HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        FileLogger fileLogger = this.logger;
        FirmwareProfile firmwareProfile = (FirmwareProfile) objectRef.element;
        fileLogger.i("FIRMWARE URL ON DIALOG: " + (firmwareProfile != null ? firmwareProfile.getFwBinUrl() : null), new Object[0]);
        UsersManager usersManager = getUsersManager();
        FirmwareProfile firmwareProfile2 = (FirmwareProfile) objectRef.element;
        Observable<Response<ResponseBody>> observableFetchFirmwareFile = usersManager.fetchFirmwareFile(firmwareProfile2 != null ? firmwareProfile2.getFwBinUrl() : null);
        if (observableFetchFirmwareFile != null) {
            final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld.onViewCreated.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                    invoke2(disposable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Disposable disposable) {
                    UpdateFirmwareDialogFragmentOld.this.doOnLoadData();
                }
            };
            Observable<Response<ResponseBody>> observableDoOnSubscribe = observableFetchFirmwareFile.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragmentOld.onViewCreated$lambda$2(function1, obj);
                }
            });
            if (observableDoOnSubscribe != null) {
                final Function1<Response<ResponseBody>, Unit> function12 = new Function1<Response<ResponseBody>, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld.onViewCreated.3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Response<ResponseBody> response) {
                        invoke2(response);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Response<ResponseBody> response) {
                        byte[] bArrCopyOf;
                        byte[] bArrBytes;
                        UpdateFirmwareDialogFragmentOld.this.getLogger().i("FIRMWARE FILE RESPONSE " + response, new Object[0]);
                        Timber.INSTANCE.i("Response: %s", response);
                        if (response.isSuccessful()) {
                            UpdateFirmwareDialogFragmentOld.this.getLogger().i("FIRMWARE FILE RESPONSE SUCCESSFUL", new Object[0]);
                            ResponseBody responseBodyBody = response.body();
                            if (responseBodyBody == null || (bArrBytes = responseBodyBody.bytes()) == null) {
                                bArrCopyOf = null;
                            } else {
                                bArrCopyOf = Arrays.copyOf(bArrBytes, bArrBytes.length);
                                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                            }
                            if (bArrCopyOf != null) {
                                HardwareProfile hardwareProfile2 = hardwareProfile;
                                Ref.ObjectRef<FirmwareProfile> objectRef2 = objectRef;
                                UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOld = UpdateFirmwareDialogFragmentOld.this;
                                Intrinsics.checkNotNull(hardwareProfile2);
                                short versionHardware = hardwareProfile2.getVersionHardware();
                                FirmwareProfile firmwareProfile3 = objectRef2.element;
                                Intrinsics.checkNotNull(firmwareProfile3);
                                WriteFirmwareBleRequest writeFirmwareBleRequest = new WriteFirmwareBleRequest(bArrCopyOf, versionHardware, (short) firmwareProfile3.getVersionFW());
                                Timber.INSTANCE.i("Before update firmware", new Object[0]);
                                updateFirmwareDialogFragmentOld.doUpdateFirmware(writeFirmwareBleRequest);
                                return;
                            }
                            return;
                        }
                        UpdateFirmwareDialogFragmentOld.this.getLogger().i("FIRMWARE FILE RESPONSE ERROR " + response.message(), new Object[0]);
                        Timber.INSTANCE.e("Response error: %s", response.message());
                        if (UpdateFirmwareDialogFragmentOld.this.isAdded()) {
                            Toast.makeText(UpdateFirmwareDialogFragmentOld.this.getContext(), R.string.text_update_firmware_error, 1).show();
                        }
                        UpdateFirmwareDialogFragmentOld.this.onCancelUpdateFirmware();
                    }
                };
                Consumer<? super Response<ResponseBody>> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        UpdateFirmwareDialogFragmentOld.onViewCreated$lambda$3(function12, obj);
                    }
                };
                final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld.onViewCreated.4
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
                        UpdateFirmwareDialogFragmentOld.this.getLogger().i("FIRMWARE FILE RESPONSE ERROR(throw) " + th, new Object[0]);
                        Timber.INSTANCE.e(th);
                        if (UpdateFirmwareDialogFragmentOld.this.isAdded()) {
                            Toast.makeText(UpdateFirmwareDialogFragmentOld.this.getContext(), R.string.text_update_firmware_error, 1).show();
                        }
                        UpdateFirmwareDialogFragmentOld.this.onCancelUpdateFirmware();
                    }
                };
                observableDoOnSubscribe.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        UpdateFirmwareDialogFragmentOld.onViewCreated$lambda$4(function13, obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void formatFlash(final WriteFirmwareBleRequest request) {
        getMBleManager().executeFormatFlashCommandOld(new Function1<Boolean, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld.formatFlash.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                if (z) {
                    UpdateFirmwareDialogFragmentOld.this.doUpdateFirmware(request);
                    return;
                }
                if (UpdateFirmwareDialogFragmentOld.this.isAdded()) {
                    try {
                        Toast.makeText(UpdateFirmwareDialogFragmentOld.this.getContext(), R.string.text_update_firmware_error, 1).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                UpdateFirmwareDialogFragmentOld.this.onCancelUpdateFirmware();
            }
        });
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void onCancelUpdateFirmware() {
        getMBleManager().clear();
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doUpdateFirmware(WriteFirmwareBleRequest request) {
        this.mFirmwareFile = request.getFirmware();
        Timber.INSTANCE.i("doUpdateFirmware", new Object[0]);
        doOnUpdateFirmware();
        if (getMBleManager().getMStateConnected()) {
            CompositeDisposable mCompositeDisposable = getMBleManager().getMCompositeDisposable();
            BleManager mBleManager = getMBleManager();
            HardwareProfile hardwareProfile = Settings.getHardwareProfile();
            Integer numValueOf = hardwareProfile != null ? Integer.valueOf(hardwareProfile.getVersionHardware()) : null;
            Intrinsics.checkNotNull(numValueOf);
            int iIntValue = numValueOf.intValue();
            FirmwareProfile firmwareProfile = Settings.INSTANCE.getFirmwareProfile();
            Integer numValueOf2 = firmwareProfile != null ? Integer.valueOf(firmwareProfile.getVersionFW()) : null;
            Intrinsics.checkNotNull(numValueOf2);
            Observable<ByteArrayOutputStream> observableObserveOn = mBleManager.executeReloadFirmware(1, iIntValue, numValueOf2.intValue()).observeOn(AndroidSchedulers.mainThread());
            final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld.doUpdateFirmware.1

                /* compiled from: UpdateFirmwareDialogFragmentOld.kt */
                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                /* renamed from: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$doUpdateFirmware$1$WhenMappings */
                public /* synthetic */ class WhenMappings {
                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                    static {
                        int[] iArr = new int[ReloadUploadingFirmwareStatus.Status.values().length];
                        try {
                            iArr[ReloadUploadingFirmwareStatus.Status.NEED_FULL_RELOAD.ordinal()] = 1;
                        } catch (NoSuchFieldError unused) {
                        }
                        try {
                            iArr[ReloadUploadingFirmwareStatus.Status.NEED_RELOAD_UPLOADING_FIRMWARE.ordinal()] = 2;
                        } catch (NoSuchFieldError unused2) {
                        }
                        try {
                            iArr[ReloadUploadingFirmwareStatus.Status.NEED_ACCEPT_UPLOADING.ordinal()] = 3;
                        } catch (NoSuchFieldError unused3) {
                        }
                        $EnumSwitchMapping$0 = iArr;
                    }
                }

                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                    invoke2(byteArrayOutputStream);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    UpdateFirmwareDialogFragmentOld.this.getLogger().i("doUpdateFirmware " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                    Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                    ReloadUploadingFirmwareBleResponse reloadUploadingFirmwareBleResponse = new ReloadUploadingFirmwareBleResponse(byteArray);
                    if (reloadUploadingFirmwareBleResponse.getStatus()) {
                        Timber.Companion companion = Timber.INSTANCE;
                        Object[] objArr = new Object[1];
                        byte[] bytes = reloadUploadingFirmwareBleResponse.getBytes();
                        if (bytes == null) {
                            bytes = new byte[0];
                        }
                        objArr[0] = Hex.bytesToStringUppercase(bytes);
                        companion.i("Response is correct: %s", objArr);
                        Timber.Companion companion2 = Timber.INSTANCE;
                        Object[] objArr2 = new Object[2];
                        ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus = reloadUploadingFirmwareBleResponse.getReloadUploadingFirmwareStatus();
                        objArr2[0] = reloadUploadingFirmwareStatus != null ? reloadUploadingFirmwareStatus.getStatus() : null;
                        ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus2 = reloadUploadingFirmwareBleResponse.getReloadUploadingFirmwareStatus();
                        objArr2[1] = reloadUploadingFirmwareStatus2 != null ? Short.valueOf(reloadUploadingFirmwareStatus2.getBlockNumber()) : null;
                        companion2.i("Reload Status: %s, block: %d", objArr2);
                        ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus3 = reloadUploadingFirmwareBleResponse.getReloadUploadingFirmwareStatus();
                        ReloadUploadingFirmwareStatus.Status status = reloadUploadingFirmwareStatus3 != null ? reloadUploadingFirmwareStatus3.getStatus() : null;
                        int i = status == null ? -1 : WhenMappings.$EnumSwitchMapping$0[status.ordinal()];
                        if (i == 1) {
                            UpdateFirmwareDialogFragmentOld.this.startUploadFirmware();
                            return;
                        }
                        if (i != 2) {
                            if (i != 3) {
                                return;
                            }
                            UpdateFirmwareDialogFragmentOld.this.doOnApplyUpdateFirmware();
                            return;
                        } else {
                            UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOld = UpdateFirmwareDialogFragmentOld.this;
                            ReloadUploadingFirmwareStatus reloadUploadingFirmwareStatus4 = reloadUploadingFirmwareBleResponse.getReloadUploadingFirmwareStatus();
                            updateFirmwareDialogFragmentOld.onStartUploadFirmware(reloadUploadingFirmwareStatus4 != null ? Short.valueOf((short) (reloadUploadingFirmwareStatus4.getBlockNumber() - 1)) : null);
                            return;
                        }
                    }
                    Timber.INSTANCE.e("Response is not correct: %s", reloadUploadingFirmwareBleResponse.getErrorCode());
                }
            };
            Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragmentOld.doUpdateFirmware$lambda$5(function1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld.doUpdateFirmware.2
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
            mCompositeDisposable.add(observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragmentOld.doUpdateFirmware$lambda$6(anonymousClass2, obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doUpdateFirmware$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doUpdateFirmware$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startUploadFirmware() {
        BleManager mBleManager = getMBleManager();
        byte[] bArr = this.mFirmwareFile;
        if (bArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirmwareFile");
            bArr = null;
        }
        mBleManager.executeUpdateFirmware(bArr, (short) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onStartUploadFirmware(Short blockNumber) {
        doOnUpdateFirmware();
        byte[] bArr = null;
        if (blockNumber != null) {
            BleManager mBleManager = getMBleManager();
            byte[] bArr2 = this.mFirmwareFile;
            if (bArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mFirmwareFile");
            } else {
                bArr = bArr2;
            }
            mBleManager.executeUpdateFirmware(bArr, blockNumber.shortValue());
            return;
        }
        BleManager mBleManager2 = getMBleManager();
        byte[] bArr3 = this.mFirmwareFile;
        if (bArr3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mFirmwareFile");
        } else {
            bArr = bArr3;
        }
        mBleManager2.executeUpdateFirmware(bArr, (short) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doOnLoadData() {
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
    public final void doOnApplyUpdateFirmware() {
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
        if (model2 != null && (info = model2.getInfo()) != null) {
            info.set(getString(R.string.text_apply_update_firmware));
        }
        Observable<ByteArrayOutputStream> observableObserveOn = getMBleManager().applyUpdateFirmware().observeOn(AndroidSchedulers.mainThread());
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$doOnApplyUpdateFirmware$commandDisposable$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Timber.INSTANCE.i("Take data: %s", Hex.bytesToStringUppercase(byteArray));
                this.this$0.getLogger().i("doOnApplyUpdateFirmware " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
                ApplyUpdateFirmwareBleResponse applyUpdateFirmwareBleResponse = new ApplyUpdateFirmwareBleResponse(byteArray);
                if (applyUpdateFirmwareBleResponse.getStatus()) {
                    Settings.saveHardwareProfile(null);
                    EventBus.getDefault().post(new ApplyUpdateFirmwareSuccessfulEventOld(true));
                    this.this$0.dismiss();
                } else {
                    Timber.INSTANCE.e("Response is not correct: %s", applyUpdateFirmwareBleResponse.getErrorCode());
                    Toast.makeText(this.this$0.getContext(), R.string.text_update_firmware_error, 1).show();
                    this.this$0.dismiss();
                }
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                UpdateFirmwareDialogFragmentOld.doOnApplyUpdateFirmware$lambda$7(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$doOnApplyUpdateFirmware$commandDisposable$2
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
                Timber.INSTANCE.e(th);
                Toast.makeText(this.this$0.getContext(), R.string.text_update_firmware_error, 1).show();
                this.this$0.dismiss();
            }
        };
        Disposable commandDisposable = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                UpdateFirmwareDialogFragmentOld.doOnApplyUpdateFirmware$lambda$8(function12, obj);
            }
        });
        BleManager mBleManager = getMBleManager();
        Intrinsics.checkNotNullExpressionValue(commandDisposable, "commandDisposable");
        mBleManager.addCommandDisposable(commandDisposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doOnApplyUpdateFirmware$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void doOnApplyUpdateFirmware$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: UpdateFirmwareDialogFragmentOld.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$executePoiling$1, reason: invalid class name and case insensitive filesystem */
    static final class C02581 extends Lambda implements Function0<Unit> {
        C02581() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            Observable<ByteArrayOutputStream> observableObserveOn = UpdateFirmwareDialogFragmentOld.this.getMBleManager().executePoilingCommandOld().observeOn(AndroidSchedulers.mainThread());
            final UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1 updateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1 = new UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1(UpdateFirmwareDialogFragmentOld.this);
            Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$executePoiling$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragmentOld.C02581.invoke$lambda$0(updateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1, obj);
                }
            };
            final UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOld = UpdateFirmwareDialogFragmentOld.this;
            final Function1<Throwable, Unit> function1 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$2
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
                    updateFirmwareDialogFragmentOld.dismiss();
                    Timber.INSTANCE.e(th);
                }
            };
            Disposable commandDisposable = observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$executePoiling$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UpdateFirmwareDialogFragmentOld.C02581.invoke$lambda$1(function1, obj);
                }
            });
            BleManager mBleManager = UpdateFirmwareDialogFragmentOld.this.getMBleManager();
            Intrinsics.checkNotNullExpressionValue(commandDisposable, "commandDisposable");
            mBleManager.addCommandDisposable(commandDisposable);
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void executePoiling() {
        CorotineScopeKt.executeAsyncTask(LifecycleOwnerKt.getLifecycleScope(this), new C02581());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateFirmwareProgressEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = this.binding;
        if (fragmentDialogUpdateFirmwareBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDialogUpdateFirmwareBinding = null;
        }
        fragmentDialogUpdateFirmwareBinding.updateProgress.setProgress(event.getProgress());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateFirmwareErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getMBleManager().clear();
        Toast.makeText(getContext(), R.string.text_update_firmware_error, 1).show();
        dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(UpdateFirmwareSuccessfulEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        doOnApplyUpdateFirmware();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothCommandErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        getMBleManager().clear();
        if (isVisible()) {
            Toast.makeText(getContext(), R.string.text_update_firmware_error, 1).show();
        }
        dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothDeviceConnectionChangedEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        if (event.isConnected()) {
            return;
        }
        getMBleManager().clear();
        if (isVisible()) {
            Toast.makeText(getContext(), R.string.text_error_connection_lost, 1).show();
        }
        dismiss();
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

    /* compiled from: UpdateFirmwareDialogFragmentOld.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragmentOld$Companion;", "", "()V", "BUNDLE_NAME", "", "getBUNDLE_NAME", "()Ljava/lang/String;", "newInstance", "Lcom/thor/app/gui/dialog/UpdateFirmwareDialogFragmentOld;", "firmwareProfileShort", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getBUNDLE_NAME() {
            return UpdateFirmwareDialogFragmentOld.BUNDLE_NAME;
        }

        public final UpdateFirmwareDialogFragmentOld newInstance() {
            return new UpdateFirmwareDialogFragmentOld();
        }

        public final UpdateFirmwareDialogFragmentOld newInstance(FirmwareProfileShort firmwareProfileShort) {
            Intrinsics.checkNotNullParameter(firmwareProfileShort, "firmwareProfileShort");
            UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOld = new UpdateFirmwareDialogFragmentOld();
            Bundle bundle = new Bundle();
            bundle.putParcelable(UpdateFirmwareDialogFragmentOld.INSTANCE.getBUNDLE_NAME(), firmwareProfileShort);
            updateFirmwareDialogFragmentOld.setArguments(bundle);
            return updateFirmwareDialogFragmentOld;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("UpdateFirmwareDialogFragmentOld", "UpdateFirmwareDialogFrag…ld::class.java.simpleName");
        BUNDLE_NAME = "UpdateFirmwareDialogFragmentOld";
    }
}
