package com.thor.app.gui.fragments.preset;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentPresetSoundSettingsBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.util.Hex;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.thor.app.gui.dialog.dialogs.ProgressDialogFragment;
import com.thor.app.gui.dialog.dialogs.TipDialogFragment;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.ImageViewKt;
import com.thor.basemodule.gui.fragments.EventBusFragment;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresetRules;
import com.thor.businessmodule.bluetooth.model.other.InstalledSoundPackage;
import com.thor.businessmodule.bluetooth.model.other.PresetRule;
import com.thor.businessmodule.bluetooth.response.other.ReadInstalledSoundPackageRulesResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteFactoryPresetSettingBleResponse;
import com.thor.businessmodule.bus.events.BleDataRequestLogEvent;
import com.thor.businessmodule.bus.events.BleDataResponseLogEvent;
import com.thor.businessmodule.bus.events.BluetoothCommandErrorEvent;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.businessmodule.model.TypeDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: PresetSoundSettingsFragment.kt */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \\2\u00020\u0001:\u0001\\B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010'\u001a\u00020(J\u0006\u0010)\u001a\u00020(J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\b\u0010.\u001a\u00020+H\u0002J\b\u0010/\u001a\u00020+H\u0002J\u0006\u00100\u001a\u00020(J\u0006\u00101\u001a\u00020(J\u0018\u00102\u001a\u00020(2\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0002J\u0010\u00106\u001a\u00020(2\u0006\u00107\u001a\u00020\u000eH\u0002J\u0012\u00108\u001a\u00020(2\b\b\u0002\u00109\u001a\u00020:H\u0002J\u0010\u0010;\u001a\u0002042\u0006\u0010<\u001a\u00020\u000eH\u0002J\u0010\u0010=\u001a\u00020(2\u0006\u0010>\u001a\u00020?H\u0002J\b\u0010@\u001a\u00020(H\u0002J\b\u0010A\u001a\u00020(H\u0002J\u0006\u0010B\u001a\u00020:J\u0012\u0010C\u001a\u00020(2\b\u0010D\u001a\u0004\u0018\u00010EH\u0016J\b\u0010F\u001a\u00020(H\u0002J$\u0010G\u001a\u00020\u00122\u0006\u0010H\u001a\u00020I2\b\u0010J\u001a\u0004\u0018\u00010K2\b\u0010D\u001a\u0004\u0018\u00010EH\u0016J\u0010\u0010L\u001a\u00020(2\u0006\u0010M\u001a\u00020NH\u0007J\u0010\u0010L\u001a\u00020(2\u0006\u0010M\u001a\u00020OH\u0007J\u0010\u0010L\u001a\u00020(2\u0006\u0010M\u001a\u00020PH\u0007J\b\u0010Q\u001a\u00020(H\u0002J\b\u0010R\u001a\u00020(H\u0016J\u001a\u0010S\u001a\u00020(2\u0006\u0010T\u001a\u00020\u00122\b\u0010D\u001a\u0004\u0018\u00010EH\u0016J\u0010\u0010U\u001a\u00020(2\u0006\u0010V\u001a\u00020WH\u0002J\u0010\u0010X\u001a\u00020(2\u0006\u00107\u001a\u00020\u000eH\u0002J\u0006\u0010Y\u001a\u00020(J\b\u0010Z\u001a\u00020(H\u0002J\u0006\u0010[\u001a\u00020(R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\u000b\u001a\u00020\f8\u0002X\u0083\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u0017\u0010\u0018R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\n\u001a\u0004\b$\u0010%¨\u0006]"}, d2 = {"Lcom/thor/app/gui/fragments/preset/PresetSoundSettingsFragment;", "Lcom/thor/basemodule/gui/fragments/EventBusFragment;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/FragmentPresetSoundSettingsBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "disableTouchListener", "Landroid/view/View$OnTouchListener;", "errorCounter", "", "errorMaxCount", "mNumberChamberSheetBehaviour", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "Landroid/view/View;", "mPresetPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "mProgressDialog", "Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "getMProgressDialog", "()Lcom/thor/app/gui/dialog/dialogs/ProgressDialogFragment;", "mProgressDialog$delegate", "mRules", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresetRules;", "setNewValueChangeListener", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "setNewValueClickListener", "Landroid/view/View$OnClickListener;", "showDialogChangeListener", "showDialogClickListener", "userManager", "Lcom/thor/app/managers/UsersManager;", "getUserManager", "()Lcom/thor/app/managers/UsersManager;", "userManager$delegate", "checkFieldsForChangesEnabled", "", "checkFieldsForExistingRuleID", "createErrorLogAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "errorBytes", "", "createWarningModeDialog", "createWarningSettingsDisableDialog", "disableAllFieldsForEdit", "enableExistFieldsForEdit", "executeFactoryPresetSetting", "packageId", "", "modeIndex", "executeResetPreset", "modeType", "getIntentExtras", "updateCheckedMode", "", "getViewRuleId", "viewId", "handleInstalledRulesResponse", "response", "Lcom/thor/businessmodule/bluetooth/response/other/ReadInstalledSoundPackageRulesResponse;", "handleTipStates", "initNumberChamber", "isEditEnabled", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onChangeNumberChamber", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "onMessageEvent", "event", "Lcom/thor/businessmodule/bus/events/BleDataRequestLogEvent;", "Lcom/thor/businessmodule/bus/events/BleDataResponseLogEvent;", "Lcom/thor/businessmodule/bus/events/BluetoothCommandErrorEvent;", "onOpenNumberChamber", "onResume", "onViewCreated", "view", "openTipDialog", SessionDescription.ATTR_TYPE, "Lcom/thor/businessmodule/model/TypeDialog;", "setCheckedType", "setValuesFromPresetPackage", "setupListeners", "uncheckedStartEngineTextViews", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PresetSoundSettingsFragment extends EventBusFragment {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int MAX_VOLUME = 100;
    private FragmentPresetSoundSettingsBinding binding;
    private int errorCounter;
    private BottomSheetBehavior<View> mNumberChamberSheetBehaviour;
    private MainPresetPackage mPresetPackage;
    private InstalledPresetRules mRules;

    /* renamed from: mProgressDialog$delegate, reason: from kotlin metadata */
    private final Lazy mProgressDialog = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ProgressDialogFragment>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$mProgressDialog$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ProgressDialogFragment invoke() {
            return ProgressDialogFragment.newInstance();
        }
    });

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$bleManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final BleManager invoke() {
            return BleManager.INSTANCE.from(this.this$0.requireContext());
        }
    });

    /* renamed from: userManager$delegate, reason: from kotlin metadata */
    private final Lazy userManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$userManager$2
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
    private final int errorMaxCount = 7;
    private final View.OnTouchListener disableTouchListener = new View.OnTouchListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$disableTouchListener$1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View p0, MotionEvent p1) {
            return true;
        }
    };
    private final SeekBar.OnSeekBarChangeListener setNewValueChangeListener = new PresetSoundSettingsFragment$setNewValueChangeListener$1(this);
    private final View.OnClickListener setNewValueClickListener = new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda0
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            PresetSoundSettingsFragment.setNewValueClickListener$lambda$17(this.f$0, view);
        }
    };
    private final View.OnClickListener showDialogClickListener = new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda10
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            PresetSoundSettingsFragment.showDialogClickListener$lambda$18(this.f$0, view);
        }
    };
    private final SeekBar.OnSeekBarChangeListener showDialogChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$showDialogChangeListener$1
        private int startProgress;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar p0) {
        }

        public final int getStartProgress() {
            return this.startProgress;
        }

        public final void setStartProgress(int i) {
            this.startProgress = i;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar p0, int p1, boolean p2) {
            if (p0 == null) {
                return;
            }
            p0.setProgress(this.startProgress);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar p0) {
            Intrinsics.checkNotNull(p0);
            this.startProgress = p0.getProgress();
            this.this$0.createWarningSettingsDisableDialog().show();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public final short getViewRuleId(int viewId) {
        switch (viewId) {
            case R.id.seek_bar_idle_tone /* 2131362392 */:
                break;
            case R.id.seek_bar_idle_volume /* 2131362393 */:
                break;
            case R.id.seek_bar_uneven_tone /* 2131362396 */:
                break;
            case R.id.seek_bar_working_tone /* 2131362397 */:
                break;
            case R.id.text_view_dynamic_start /* 2131362516 */:
                break;
            case R.id.text_view_ready /* 2131362533 */:
                break;
            case R.id.text_view_starter_sound /* 2131362539 */:
                break;
        }
        return (short) 4;
    }

    @JvmStatic
    public static final PresetSoundSettingsFragment newInstance(MainPresetPackage mainPresetPackage) {
        return INSTANCE.newInstance(mainPresetPackage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProgressDialogFragment getMProgressDialog() {
        Object value = this.mProgressDialog.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-mProgressDialog>(...)");
        return (ProgressDialogFragment) value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    private final UsersManager getUserManager() {
        return (UsersManager) this.userManager.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(inflater, R.layout.fragment_preset_sound_settings, container, false);
        Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate, "inflate(\n            inf…          false\n        )");
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = (FragmentPresetSoundSettingsBinding) viewDataBindingInflate;
        this.binding = fragmentPresetSoundSettingsBinding;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        View root = fragmentPresetSoundSettingsBinding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = null;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        fragmentPresetSoundSettingsBinding.toolbarWidget.setHomeButtonVisibility(true);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding3 = null;
        }
        fragmentPresetSoundSettingsBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PresetSoundSettingsFragment.onViewCreated$lambda$0(this.f$0, view2);
            }
        });
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
        if (fragmentPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding2 = fragmentPresetSoundSettingsBinding4;
        }
        fragmentPresetSoundSettingsBinding2.toolbarWidget.enableBluetoothIndicator(true);
        initNumberChamber();
        setupListeners();
        handleTipStates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity activity = this$0.getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @Override // com.thor.basemodule.gui.fragments.EventBusFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) throws SecurityException {
        super.onActivityCreated(savedInstanceState);
        getIntentExtras$default(this, false, 1, null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        BottomSheetBehavior<View> bottomSheetBehavior = this.mNumberChamberSheetBehaviour;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNumberChamberSheetBehaviour");
            bottomSheetBehavior = null;
        }
        bottomSheetBehavior.setState(5);
    }

    static /* synthetic */ void getIntentExtras$default(PresetSoundSettingsFragment presetSoundSettingsFragment, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        presetSoundSettingsFragment.getIntentExtras(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getIntentExtras(boolean updateCheckedMode) {
        Integer versionId;
        Integer packageId;
        MainPresetPackage mainPresetPackage;
        Short modeType;
        Bundle arguments = getArguments();
        this.mPresetPackage = arguments != null ? (MainPresetPackage) arguments.getParcelable(MainPresetPackage.BUNDLE_NAME) : null;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        fragmentPresetSoundSettingsBinding.setPresetPackage(this.mPresetPackage);
        if (this.mPresetPackage == null) {
            return;
        }
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = this.binding;
        if (fragmentPresetSoundSettingsBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding2 = null;
        }
        fragmentPresetSoundSettingsBinding2.layoutPresetModes.setVisibility(8);
        MainPresetPackage mainPresetPackage2 = this.mPresetPackage;
        Short modeType2 = mainPresetPackage2 != null ? mainPresetPackage2.getModeType() : null;
        if (modeType2 != null && modeType2.shortValue() == 1) {
            FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
            if (fragmentPresetSoundSettingsBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentPresetSoundSettingsBinding3 = null;
            }
            fragmentPresetSoundSettingsBinding3.textViewSelectedType.setText(getString(R.string.text_city));
            FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
            if (fragmentPresetSoundSettingsBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentPresetSoundSettingsBinding4 = null;
            }
            TextView textView = fragmentPresetSoundSettingsBinding4.textViewCarName;
            Object[] objArr = new Object[2];
            MainPresetPackage mainPresetPackage3 = this.mPresetPackage;
            objArr[0] = mainPresetPackage3 != null ? mainPresetPackage3.getName() : null;
            objArr[1] = getString(R.string.text_city);
            textView.setText(getString(R.string.form_dash_separator, objArr));
        } else {
            if (modeType2 != null && modeType2.shortValue() == 2) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding5 = this.binding;
                if (fragmentPresetSoundSettingsBinding5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    fragmentPresetSoundSettingsBinding5 = null;
                }
                fragmentPresetSoundSettingsBinding5.textViewSelectedType.setText(getString(R.string.text_sport));
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding6 = this.binding;
                if (fragmentPresetSoundSettingsBinding6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    fragmentPresetSoundSettingsBinding6 = null;
                }
                TextView textView2 = fragmentPresetSoundSettingsBinding6.textViewCarName;
                Object[] objArr2 = new Object[2];
                MainPresetPackage mainPresetPackage4 = this.mPresetPackage;
                objArr2[0] = mainPresetPackage4 != null ? mainPresetPackage4.getName() : null;
                objArr2[1] = getString(R.string.text_sport);
                textView2.setText(getString(R.string.form_dash_separator, objArr2));
            } else {
                if (modeType2 != null && modeType2.shortValue() == 3) {
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding7 = this.binding;
                    if (fragmentPresetSoundSettingsBinding7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        fragmentPresetSoundSettingsBinding7 = null;
                    }
                    fragmentPresetSoundSettingsBinding7.layoutPresetModes.setVisibility(0);
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding8 = this.binding;
                    if (fragmentPresetSoundSettingsBinding8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        fragmentPresetSoundSettingsBinding8 = null;
                    }
                    fragmentPresetSoundSettingsBinding8.textViewSelectedType.setText(getString(R.string.text_own));
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding9 = this.binding;
                    if (fragmentPresetSoundSettingsBinding9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        fragmentPresetSoundSettingsBinding9 = null;
                    }
                    TextView textView3 = fragmentPresetSoundSettingsBinding9.textViewCarName;
                    Object[] objArr3 = new Object[2];
                    MainPresetPackage mainPresetPackage5 = this.mPresetPackage;
                    objArr3[0] = mainPresetPackage5 != null ? mainPresetPackage5.getName() : null;
                    objArr3[1] = getString(R.string.text_own);
                    textView3.setText(getString(R.string.form_dash_separator, objArr3));
                }
            }
        }
        if (updateCheckedMode && (mainPresetPackage = this.mPresetPackage) != null && (modeType = mainPresetPackage.getModeType()) != null) {
            setCheckedType(modeType.shortValue());
        }
        MainPresetPackage mainPresetPackage6 = this.mPresetPackage;
        Short shValueOf = (mainPresetPackage6 == null || (packageId = mainPresetPackage6.getPackageId()) == null) ? null : Short.valueOf((short) packageId.intValue());
        Intrinsics.checkNotNull(shValueOf);
        short sShortValue = shValueOf.shortValue();
        MainPresetPackage mainPresetPackage7 = this.mPresetPackage;
        Short shValueOf2 = (mainPresetPackage7 == null || (versionId = mainPresetPackage7.getVersionId()) == null) ? null : Short.valueOf((short) versionId.intValue());
        Intrinsics.checkNotNull(shValueOf2);
        short sShortValue2 = shValueOf2.shortValue();
        MainPresetPackage mainPresetPackage8 = this.mPresetPackage;
        Short modeType3 = mainPresetPackage8 != null ? mainPresetPackage8.getModeType() : null;
        Intrinsics.checkNotNull(modeType3);
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeReadInstalledSoundPackageRulesCommand(new InstalledSoundPackage(sShortValue, sShortValue2, modeType3.shortValue())).observeOn(AndroidSchedulers.mainThread());
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.getIntentExtras.2
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
                if (PresetSoundSettingsFragment.this.getMProgressDialog().isAdded()) {
                    return;
                }
                PresetSoundSettingsFragment.this.getMProgressDialog().show(PresetSoundSettingsFragment.this.requireFragmentManager(), ProgressDialogFragment.TAG);
            }
        };
        Observable<ByteArrayOutputStream> observableDoOnTerminate = observableObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetSoundSettingsFragment.getIntentExtras$lambda$2(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Action
            public final void run() {
                PresetSoundSettingsFragment.getIntentExtras$lambda$3(this.f$0);
            }
        });
        final Function1<ByteArrayOutputStream, Unit> function12 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.getIntentExtras.4
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
                ReadInstalledSoundPackageRulesResponse readInstalledSoundPackageRulesResponse = new ReadInstalledSoundPackageRulesResponse(byteArray);
                if (readInstalledSoundPackageRulesResponse.isSuccessful()) {
                    Timber.Companion companion = Timber.INSTANCE;
                    Object[] objArr4 = new Object[1];
                    byte[] bytes = readInstalledSoundPackageRulesResponse.getBytes();
                    if (bytes == null) {
                        bytes = new byte[0];
                    }
                    objArr4[0] = Hex.bytesToStringUppercase(bytes);
                    companion.i("Response is correct: %s", objArr4);
                    PresetSoundSettingsFragment.this.handleInstalledRulesResponse(readInstalledSoundPackageRulesResponse);
                    return;
                }
                Timber.INSTANCE.e("Response is not correct: %s", readInstalledSoundPackageRulesResponse.getErrorCode());
                BleManager bleManager = PresetSoundSettingsFragment.this.getBleManager();
                final PresetSoundSettingsFragment presetSoundSettingsFragment = PresetSoundSettingsFragment.this;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.getIntentExtras.4.1
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
                        PresetSoundSettingsFragment.getIntentExtras$default(presetSoundSettingsFragment, false, 1, null);
                    }
                });
                if (PresetSoundSettingsFragment.this.errorCounter >= PresetSoundSettingsFragment.this.errorMaxCount) {
                    Toast.makeText(PresetSoundSettingsFragment.this.getContext(), PresetSoundSettingsFragment.this.getString(R.string.text_error_connection_lost), 1).show();
                    FragmentActivity activity = PresetSoundSettingsFragment.this.getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                } else if (PresetSoundSettingsFragment.this.isAdded()) {
                    PresetSoundSettingsFragment.getIntentExtras$default(PresetSoundSettingsFragment.this, false, 1, null);
                }
                PresetSoundSettingsFragment.this.errorCounter++;
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetSoundSettingsFragment.getIntentExtras$lambda$4(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.getIntentExtras.5
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
                try {
                    Toast.makeText(PresetSoundSettingsFragment.this.getContext(), R.string.warning_unknown_error, 1).show();
                    Timber.INSTANCE.e(th);
                    FragmentActivity activity = PresetSoundSettingsFragment.this.getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                } catch (Exception e) {
                    Timber.INSTANCE.e(e);
                }
            }
        };
        Disposable it = observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetSoundSettingsFragment.getIntentExtras$lambda$5(function13, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        bleManager.addCommandDisposable(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getIntentExtras$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getIntentExtras$lambda$3(PresetSoundSettingsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getMProgressDialog().isResumed()) {
            this$0.getMProgressDialog().dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getIntentExtras$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getIntentExtras$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void setupListeners() {
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = null;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        fragmentPresetSoundSettingsBinding.textViewTypeOwn.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PresetSoundSettingsFragment.setupListeners$lambda$7(this.f$0, view);
            }
        });
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding3 = null;
        }
        fragmentPresetSoundSettingsBinding3.textViewTypeCity.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PresetSoundSettingsFragment.setupListeners$lambda$8(this.f$0, view);
            }
        });
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
        if (fragmentPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding4 = null;
        }
        fragmentPresetSoundSettingsBinding4.textViewTypeSport.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PresetSoundSettingsFragment.setupListeners$lambda$9(this.f$0, view);
            }
        });
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding5 = this.binding;
        if (fragmentPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding2 = fragmentPresetSoundSettingsBinding5;
        }
        fragmentPresetSoundSettingsBinding2.idleTipBtn.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PresetSoundSettingsFragment.setupListeners$lambda$10(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupListeners$lambda$7(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.executeResetPreset(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupListeners$lambda$8(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.executeResetPreset(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupListeners$lambda$9(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.executeResetPreset(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupListeners$lambda$10(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.openTipDialog(TypeDialog.SHOW_IDLE_TONE_TIP);
    }

    private final void handleTipStates() {
        if (Settings.INSTANCE.getIdleVolumeTipState()) {
            FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
            if (fragmentPresetSoundSettingsBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                fragmentPresetSoundSettingsBinding = null;
            }
            AppCompatImageButton appCompatImageButton = fragmentPresetSoundSettingsBinding.idleTipBtn;
            Intrinsics.checkNotNullExpressionValue(appCompatImageButton, "binding.idleTipBtn");
            ImageViewKt.setSvgColor(appCompatImageButton, R.color.colorText_dialog);
        }
    }

    private final void openTipDialog(TypeDialog type) {
        TipDialogFragment tipDialogFragment = new TipDialogFragment(type);
        tipDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$openTipDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
                Settings.INSTANCE.saveIdleVolumeTipState(true);
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.this$0.binding;
                if (fragmentPresetSoundSettingsBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    fragmentPresetSoundSettingsBinding = null;
                }
                AppCompatImageButton appCompatImageButton = fragmentPresetSoundSettingsBinding.idleTipBtn;
                Intrinsics.checkNotNullExpressionValue(appCompatImageButton, "binding.idleTipBtn");
                ImageViewKt.setSvgColor(appCompatImageButton, R.color.colorText_dialog);
            }
        });
        tipDialogFragment.show(getChildFragmentManager(), tipDialogFragment.getClass().getSimpleName());
    }

    private final void executeResetPreset(int modeType) {
        MainPresetPackage mainPresetPackage = this.mPresetPackage;
        Integer packageId = mainPresetPackage != null ? mainPresetPackage.getPackageId() : null;
        if (packageId != null) {
            executeFactoryPresetSetting((short) packageId.intValue(), (short) modeType);
            setCheckedType(modeType);
            return;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    private final void setCheckedType(int modeType) {
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = null;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        fragmentPresetSoundSettingsBinding.textViewTypeOwn.setChecked(modeType == 3);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding3 = null;
        }
        fragmentPresetSoundSettingsBinding3.textViewTypeCity.setChecked(modeType == 1);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
        if (fragmentPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding2 = fragmentPresetSoundSettingsBinding4;
        }
        fragmentPresetSoundSettingsBinding2.textViewTypeSport.setChecked(modeType == 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleInstalledRulesResponse(ReadInstalledSoundPackageRulesResponse response) {
        this.mRules = response.getInstalledPresetRules();
        setValuesFromPresetPackage();
        if (isEditEnabled()) {
            enableExistFieldsForEdit();
        } else {
            createWarningModeDialog().show();
            disableAllFieldsForEdit();
        }
    }

    public final void uncheckedStartEngineTextViews() {
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = null;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        fragmentPresetSoundSettingsBinding.textViewOff.setChecked(false);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding3 = null;
        }
        fragmentPresetSoundSettingsBinding3.textViewStarterSound.setChecked(false);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
        if (fragmentPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding2 = fragmentPresetSoundSettingsBinding4;
        }
        fragmentPresetSoundSettingsBinding2.textViewDynamicStart.setChecked(false);
    }

    public final void setValuesFromPresetPackage() {
        ArrayList<PresetRule> installedPresetRules;
        uncheckedStartEngineTextViews();
        InstalledPresetRules installedPresetRules2 = this.mRules;
        if (installedPresetRules2 == null || (installedPresetRules = installedPresetRules2.getInstalledPresetRules()) == null) {
            return;
        }
        for (PresetRule presetRule : installedPresetRules) {
            short id = presetRule.getId();
            FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = null;
            if (id == 0) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = this.binding;
                if (fragmentPresetSoundSettingsBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding2;
                }
                fragmentPresetSoundSettingsBinding.seekBarMainVolume.setProgress(presetRule.getValue());
            } else if (id == 48) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
                if (fragmentPresetSoundSettingsBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding3;
                }
                fragmentPresetSoundSettingsBinding.seekBarIdleVolume.setProgress(100 - presetRule.getValue());
            } else if (id == 1) {
                short value = presetRule.getValue();
                if (value == 0) {
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
                    if (fragmentPresetSoundSettingsBinding4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding4;
                    }
                    fragmentPresetSoundSettingsBinding.textViewOff.setChecked(true);
                } else if (value == 4) {
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding5 = this.binding;
                    if (fragmentPresetSoundSettingsBinding5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding5;
                    }
                    fragmentPresetSoundSettingsBinding.textViewStarterSound.setChecked(true);
                } else if (value == 1) {
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding6 = this.binding;
                    if (fragmentPresetSoundSettingsBinding6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding6;
                    }
                    fragmentPresetSoundSettingsBinding.textViewDynamicStart.setChecked(true);
                } else {
                    FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding7 = this.binding;
                    if (fragmentPresetSoundSettingsBinding7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                    } else {
                        fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding7;
                    }
                    fragmentPresetSoundSettingsBinding.textViewOff.setChecked(true);
                }
            } else if (id == 4) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding8 = this.binding;
                if (fragmentPresetSoundSettingsBinding8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding8;
                }
                fragmentPresetSoundSettingsBinding.seekBarUnevenTone.setProgress(presetRule.getValue());
            } else if (id == 24) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding9 = this.binding;
                if (fragmentPresetSoundSettingsBinding9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding9;
                }
                fragmentPresetSoundSettingsBinding.seekBarIdleTone.setProgress(presetRule.getValue());
            } else if (id == 25) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding10 = this.binding;
                if (fragmentPresetSoundSettingsBinding10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding10;
                }
                fragmentPresetSoundSettingsBinding.seekBarWorkingTone.setProgress(presetRule.getValue());
            } else if (id == 33) {
                FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding11 = this.binding;
                if (fragmentPresetSoundSettingsBinding11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding11;
                }
                fragmentPresetSoundSettingsBinding.textViewNumberChamber.setText(String.valueOf((int) presetRule.getValue()));
            }
        }
    }

    public final boolean isEditEnabled() {
        Short modeType;
        MainPresetPackage mainPresetPackage = this.mPresetPackage;
        return (mainPresetPackage == null || (modeType = mainPresetPackage.getModeType()) == null || modeType.shortValue() != 3) ? false : true;
    }

    public final void enableExistFieldsForEdit() {
        checkFieldsForExistingRuleID();
        checkFieldsForChangesEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x013e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void checkFieldsForExistingRuleID() {
        /*
            Method dump skipped, instructions count: 483
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.checkFieldsForExistingRuleID():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void checkFieldsForChangesEnabled() {
        /*
            Method dump skipped, instructions count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.checkFieldsForChangesEnabled():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkFieldsForChangesEnabled$lambda$13(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onOpenNumberChamber();
    }

    public final void disableAllFieldsForEdit() {
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = null;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        fragmentPresetSoundSettingsBinding.seekBarMainVolume.setOnTouchListener(this.disableTouchListener);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding3 = null;
        }
        fragmentPresetSoundSettingsBinding3.seekBarWorkingTone.setOnTouchListener(this.disableTouchListener);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
        if (fragmentPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding4 = null;
        }
        fragmentPresetSoundSettingsBinding4.seekBarIdleTone.setOnTouchListener(this.disableTouchListener);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding5 = this.binding;
        if (fragmentPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding5 = null;
        }
        fragmentPresetSoundSettingsBinding5.seekBarIdleVolume.setOnTouchListener(this.disableTouchListener);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding6 = this.binding;
        if (fragmentPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding6 = null;
        }
        fragmentPresetSoundSettingsBinding6.seekBarUnevenTone.setOnTouchListener(this.disableTouchListener);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding7 = this.binding;
        if (fragmentPresetSoundSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding7 = null;
        }
        fragmentPresetSoundSettingsBinding7.layoutChangeNumberChamber.setOnTouchListener(this.disableTouchListener);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding8 = this.binding;
        if (fragmentPresetSoundSettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding8 = null;
        }
        fragmentPresetSoundSettingsBinding8.textViewOff.setEnabled(false);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding9 = this.binding;
        if (fragmentPresetSoundSettingsBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding9 = null;
        }
        fragmentPresetSoundSettingsBinding9.textViewStarterSound.setEnabled(false);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding10 = this.binding;
        if (fragmentPresetSoundSettingsBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding2 = fragmentPresetSoundSettingsBinding10;
        }
        fragmentPresetSoundSettingsBinding2.textViewDynamicStart.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void setNewValueClickListener$lambda$17(final com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment r6, android.view.View r7) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.setNewValueClickListener$lambda$17(com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment, android.view.View):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setNewValueClickListener$lambda$17$lambda$16$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setNewValueClickListener$lambda$17$lambda$16$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialogClickListener$lambda$18(PresetSoundSettingsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.createWarningSettingsDisableDialog().show();
    }

    private final AlertDialog createWarningModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), 2131886084);
        builder.setMessage(R.string.message_edit_mode_disable).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AlertDialog createWarningSettingsDisableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), 2131886084);
        builder.setMessage(R.string.message_settings_disable).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    private final void initNumberChamber() {
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = this.binding;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = null;
        if (fragmentPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding = null;
        }
        BottomSheetBehavior<View> bottomSheetBehaviorFrom = BottomSheetBehavior.from(fragmentPresetSoundSettingsBinding.layoutNumberChamber);
        Intrinsics.checkNotNullExpressionValue(bottomSheetBehaviorFrom, "from(binding.layoutNumberChamber)");
        this.mNumberChamberSheetBehaviour = bottomSheetBehaviorFrom;
        String[] strArr = {SessionDescription.SUPPORTED_SDP_VERSION, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, "2", ExifInterface.GPS_MEASUREMENT_3D};
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding3 = null;
        }
        fragmentPresetSoundSettingsBinding3.viewPickerNumberChamber.setDisplayedValues(strArr);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding4 = this.binding;
        if (fragmentPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding4 = null;
        }
        fragmentPresetSoundSettingsBinding4.viewPickerNumberChamber.setMinValue(0);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding5 = this.binding;
        if (fragmentPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding5 = null;
        }
        fragmentPresetSoundSettingsBinding5.viewPickerNumberChamber.setMaxValue(3);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding6 = this.binding;
        if (fragmentPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding6 = null;
        }
        Integer intOrNull = StringsKt.toIntOrNull(fragmentPresetSoundSettingsBinding6.textViewNumberChamber.getText().toString());
        if (intOrNull == null) {
            intOrNull = 0;
        }
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding7 = this.binding;
        if (fragmentPresetSoundSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding2 = fragmentPresetSoundSettingsBinding7;
        }
        fragmentPresetSoundSettingsBinding2.viewPickerNumberChamber.setValue(intOrNull.intValue());
    }

    private final void onOpenNumberChamber() {
        Timber.INSTANCE.i("onOpenNumberChamber", new Object[0]);
        BottomSheetBehavior<View> bottomSheetBehavior = this.mNumberChamberSheetBehaviour;
        BottomSheetBehavior<View> bottomSheetBehavior2 = null;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNumberChamberSheetBehaviour");
            bottomSheetBehavior = null;
        }
        if (bottomSheetBehavior.getState() == 3) {
            BottomSheetBehavior<View> bottomSheetBehavior3 = this.mNumberChamberSheetBehaviour;
            if (bottomSheetBehavior3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mNumberChamberSheetBehaviour");
            } else {
                bottomSheetBehavior2 = bottomSheetBehavior3;
            }
            bottomSheetBehavior2.setState(5);
            return;
        }
        BottomSheetBehavior<View> bottomSheetBehavior4 = this.mNumberChamberSheetBehaviour;
        if (bottomSheetBehavior4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNumberChamberSheetBehaviour");
        } else {
            bottomSheetBehavior2 = bottomSheetBehavior4;
        }
        bottomSheetBehavior2.setState(3);
    }

    private final void onChangeNumberChamber() {
        BottomSheetBehavior<View> bottomSheetBehavior = this.mNumberChamberSheetBehaviour;
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding = null;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNumberChamberSheetBehaviour");
            bottomSheetBehavior = null;
        }
        bottomSheetBehavior.setState(5);
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding2 = this.binding;
        if (fragmentPresetSoundSettingsBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentPresetSoundSettingsBinding2 = null;
        }
        int value = fragmentPresetSoundSettingsBinding2.viewPickerNumberChamber.getValue();
        FragmentPresetSoundSettingsBinding fragmentPresetSoundSettingsBinding3 = this.binding;
        if (fragmentPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentPresetSoundSettingsBinding = fragmentPresetSoundSettingsBinding3;
        }
        fragmentPresetSoundSettingsBinding.textViewNumberChamber.setText(String.valueOf(value));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BluetoothCommandErrorEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Timber.INSTANCE.e("Error: %s", Hex.bytesToStringUppercase(event.getBytesError()));
        if (!event.getIsTrash()) {
            String strBytesToStringUppercase = Hex.bytesToStringUppercase(event.getBytesError());
            Intrinsics.checkNotNullExpressionValue(strBytesToStringUppercase, "bytesToStringUppercase(event.bytesError)");
            createErrorLogAlertDialog(strBytesToStringUppercase).show();
        }
        getBleManager().clear();
    }

    private final AlertDialog createErrorLogAlertDialog(String errorBytes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), 2131886083);
        builder.setTitle(R.string.warning_unknown_error).setMessage("0x" + errorBytes).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void executeFactoryPresetSetting(final short packageId, final short modeIndex) {
        Timber.INSTANCE.d("executeFactoryPresetSetting: " + ((int) packageId) + "; " + ((int) modeIndex), new Object[0]);
        Observable<ByteArrayOutputStream> observableObserveOn = getBleManager().executeWriteFactoryPresetSetting(packageId, modeIndex).observeOn(AndroidSchedulers.mainThread());
        final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.executeFactoryPresetSetting.1
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
                if (PresetSoundSettingsFragment.this.getMProgressDialog().isAdded()) {
                    return;
                }
                PresetSoundSettingsFragment.this.getMProgressDialog().show(PresetSoundSettingsFragment.this.requireFragmentManager(), ProgressDialogFragment.TAG);
            }
        };
        Observable<ByteArrayOutputStream> observableDoOnTerminate = observableObserveOn.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetSoundSettingsFragment.executeFactoryPresetSetting$lambda$19(function1, obj);
            }
        }).doOnTerminate(new Action() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Action
            public final void run() {
                PresetSoundSettingsFragment.executeFactoryPresetSetting$lambda$20(this.f$0);
            }
        });
        final AnonymousClass3 anonymousClass3 = new Function1<ByteArrayOutputStream, WriteFactoryPresetSettingBleResponse>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.executeFactoryPresetSetting.3
            @Override // kotlin.jvm.functions.Function1
            public final WriteFactoryPresetSettingBleResponse invoke(ByteArrayOutputStream it) {
                Intrinsics.checkNotNullParameter(it, "it");
                byte[] byteArray = it.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "it.toByteArray()");
                return new WriteFactoryPresetSettingBleResponse(byteArray);
            }
        };
        Observable<R> map = observableDoOnTerminate.map(new Function() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return PresetSoundSettingsFragment.executeFactoryPresetSetting$lambda$21(anonymousClass3, obj);
            }
        });
        final Function1<WriteFactoryPresetSettingBleResponse, Unit> function12 = new Function1<WriteFactoryPresetSettingBleResponse, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.executeFactoryPresetSetting.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(WriteFactoryPresetSettingBleResponse writeFactoryPresetSettingBleResponse) {
                invoke2(writeFactoryPresetSettingBleResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(WriteFactoryPresetSettingBleResponse writeFactoryPresetSettingBleResponse) {
                Timber.INSTANCE.d("executeFactoryPresetSetting: " + writeFactoryPresetSettingBleResponse, new Object[0]);
                if (!writeFactoryPresetSettingBleResponse.getStatus()) {
                    BleManager bleManager = PresetSoundSettingsFragment.this.getBleManager();
                    final PresetSoundSettingsFragment presetSoundSettingsFragment = PresetSoundSettingsFragment.this;
                    final short s = packageId;
                    final short s2 = modeIndex;
                    bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.executeFactoryPresetSetting.4.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                            presetSoundSettingsFragment.executeFactoryPresetSetting(s, s2);
                        }
                    });
                    return;
                }
                Timber.Companion companion = Timber.INSTANCE;
                Object[] objArr = new Object[1];
                byte[] bytes = writeFactoryPresetSettingBleResponse.getBytes();
                if (bytes == null) {
                    bytes = new byte[0];
                }
                objArr[0] = Hex.bytesToStringUppercase(bytes);
                companion.i("writeSettings data: %s", objArr);
                PresetSoundSettingsFragment.this.getIntentExtras(false);
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetSoundSettingsFragment.executeFactoryPresetSetting$lambda$22(function12, obj);
            }
        };
        final Function1<Throwable, Unit> function13 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.executeFactoryPresetSetting.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                BleManager bleManager = PresetSoundSettingsFragment.this.getBleManager();
                final PresetSoundSettingsFragment presetSoundSettingsFragment = PresetSoundSettingsFragment.this;
                final short s = packageId;
                final short s2 = modeIndex;
                bleManager.executeInitCrypto(new Function0<Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment.executeFactoryPresetSetting.5.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                        presetSoundSettingsFragment.executeFactoryPresetSetting(s, s2);
                    }
                });
            }
        };
        Disposable it = map.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PresetSoundSettingsFragment.executeFactoryPresetSetting$lambda$23(function13, obj);
            }
        });
        BleManager bleManager = getBleManager();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        bleManager.addCommandDisposable(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFactoryPresetSetting$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFactoryPresetSetting$lambda$20(PresetSoundSettingsFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.getMProgressDialog().isResumed()) {
            this$0.getMProgressDialog().dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final WriteFactoryPresetSettingBleResponse executeFactoryPresetSetting$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (WriteFactoryPresetSettingBleResponse) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFactoryPresetSetting$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeFactoryPresetSetting$lambda$23(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataRequestLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "<= D " + event.getDataDeCrypto() + " <= E " + event.getDataCrypto());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(BleDataResponseLogEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Log.i("NEW_LOG", "=> D " + event.getDataDeCrypto() + " => E " + event.getDataCrypto());
    }

    /* compiled from: PresetSoundSettingsFragment.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/fragments/preset/PresetSoundSettingsFragment$Companion;", "", "()V", "MAX_VOLUME", "", "newInstance", "Lcom/thor/app/gui/fragments/preset/PresetSoundSettingsFragment;", "presetPackage", "Lcom/thor/businessmodule/model/MainPresetPackage;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final PresetSoundSettingsFragment newInstance(MainPresetPackage presetPackage) {
            PresetSoundSettingsFragment presetSoundSettingsFragment = new PresetSoundSettingsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(MainPresetPackage.BUNDLE_NAME, presetPackage);
            presetSoundSettingsFragment.setArguments(bundle);
            return presetSoundSettingsFragment;
        }
    }
}
