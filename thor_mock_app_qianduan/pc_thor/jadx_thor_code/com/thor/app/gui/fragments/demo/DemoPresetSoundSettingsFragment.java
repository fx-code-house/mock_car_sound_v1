package com.thor.app.gui.fragments.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.carsystems.thor.app.BuildConfig;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentDemoPresetSoundSettingsBinding;
import com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.thor.app.gui.activities.demo.DemoShopActivity;
import com.thor.app.gui.dialog.dialogs.TipDialogFragment;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.model.TypeDialog;
import com.thor.businessmodule.viewmodel.demo.DemoPresetSoundSettingsFragmentViewModel;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: DemoPresetSoundSettingsFragment.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0001)B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\bH\u0016J&\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u000eH\u0002J\b\u0010\u001c\u001a\u00020\u000eH\u0002J\b\u0010\u001d\u001a\u00020\u000eH\u0002J\b\u0010\u001e\u001a\u00020\u000eH\u0016J\b\u0010\u001f\u001a\u00020\u000eH\u0002J\b\u0010 \u001a\u00020\u000eH\u0002J\b\u0010!\u001a\u00020\u000eH\u0002J\b\u0010\"\u001a\u00020\u000eH\u0002J\b\u0010#\u001a\u00020\u000eH\u0016J\u001a\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0010\u0010&\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020(H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/thor/app/gui/fragments/demo/DemoPresetSoundSettingsFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/FragmentDemoPresetSoundSettingsBinding;", "mNumberChamberSheetBehaviour", "Lcom/google/android/material/bottomsheet/BottomSheetBehavior;", "Landroid/view/View;", "createInfoAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "getBaseUrlWithLocale", "", "initNumberChamber", "", "onChangeNumberChamber", "onCityTypeEnable", "onClick", "v", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDeviceOrder", "onOpenNumberChamber", "onOpenShopActivity", "onOwnTypeEnable", "onResume", "onSoundStartDynamic", "onSoundStartOff", "onSoundStartStarter", "onSportTypeEnable", "onStart", "onViewCreated", "view", "openTipDialog", SessionDescription.ATTR_TYPE, "Lcom/thor/businessmodule/model/TypeDialog;", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoPresetSoundSettingsFragment extends Fragment implements View.OnClickListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private FragmentDemoPresetSoundSettingsBinding binding;
    private BottomSheetBehavior<View> mNumberChamberSheetBehaviour;

    @JvmStatic
    public static final DemoPresetSoundSettingsFragment newInstance() {
        return INSTANCE.newInstance();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(inflater, R.layout.fragment_demo_preset_sound_settings, container, false);
        Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate, "inflate(\n            inf…          false\n        )");
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = (FragmentDemoPresetSoundSettingsBinding) viewDataBindingInflate;
        this.binding = fragmentDemoPresetSoundSettingsBinding;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        return fragmentDemoPresetSoundSettingsBinding.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intent intent;
        DemoSoundPackage demoSoundPackage;
        ObservableField<DemoSoundPackage> soundPackage;
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.setModel(new DemoPresetSoundSettingsFragmentViewModel());
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        DemoPresetSoundSettingsFragment demoPresetSoundSettingsFragment = this;
        fragmentDemoPresetSoundSettingsBinding3.imageViewBack.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding4 = null;
        }
        fragmentDemoPresetSoundSettingsBinding4.textViewOff.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding5 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding5 = null;
        }
        fragmentDemoPresetSoundSettingsBinding5.textViewStarterSound.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding6 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding6 = null;
        }
        fragmentDemoPresetSoundSettingsBinding6.textViewDynamicStart.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding7 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding7 = null;
        }
        fragmentDemoPresetSoundSettingsBinding7.textViewTypeCity.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding8 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding8 = null;
        }
        fragmentDemoPresetSoundSettingsBinding8.textViewTypeSport.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding9 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding9 = null;
        }
        fragmentDemoPresetSoundSettingsBinding9.textViewTypeOwn.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding10 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding10 = null;
        }
        fragmentDemoPresetSoundSettingsBinding10.textViewOrder.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding11 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding11 = null;
        }
        fragmentDemoPresetSoundSettingsBinding11.textViewReady.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding12 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding12 = null;
        }
        fragmentDemoPresetSoundSettingsBinding12.layoutChangeNumberChamber.setOnClickListener(demoPresetSoundSettingsFragment);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding13 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding13 = null;
        }
        fragmentDemoPresetSoundSettingsBinding13.idleTipBtn.setOnClickListener(demoPresetSoundSettingsFragment);
        onCityTypeEnable();
        initNumberChamber();
        FragmentActivity activity = getActivity();
        if (activity == null || (intent = activity.getIntent()) == null || (demoSoundPackage = (DemoSoundPackage) intent.getParcelableExtra(DemoSoundPackage.BUNDLE_NAME)) == null) {
            return;
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding14 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding14;
        }
        DemoPresetSoundSettingsFragmentViewModel model = fragmentDemoPresetSoundSettingsBinding2.getModel();
        if (model == null || (soundPackage = model.getSoundPackage()) == null) {
            return;
        }
        soundPackage.set(demoSoundPackage);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        createInfoAlertDialog().show();
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

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Integer numValueOf = v != null ? Integer.valueOf(v.getId()) : null;
        if (numValueOf != null && numValueOf.intValue() == R.id.image_view_back) {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
                return;
            }
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_off) {
            onSoundStartOff();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_starter_sound) {
            onSoundStartStarter();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_dynamic_start) {
            onSoundStartDynamic();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_type_city) {
            onCityTypeEnable();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_type_sport) {
            onSportTypeEnable();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_type_own) {
            onOwnTypeEnable();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_ready) {
            onChangeNumberChamber();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.text_view_order) {
            onDeviceOrder();
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == R.id.layout_change_number_chamber) {
            onOpenNumberChamber();
        } else if (numValueOf != null && numValueOf.intValue() == R.id.idle_tip_btn) {
            openTipDialog(TypeDialog.SHOW_IDLE_TONE_TIP);
        }
    }

    private final AlertDialog createInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), 2131886084);
        builder.setMessage(R.string.text_demo_preset_sound_message).setPositiveButton(android.R.string.yes, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }

    private final void onCityTypeEnable() {
        ObservableInt progressWorkingTone;
        ObservableInt progressMainSound;
        ObservableInt progressIdleVolume;
        ObservableInt progressIdleTone;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.textViewTypeCity.setChecked(true);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.textViewTypeSport.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding4 = null;
        }
        fragmentDemoPresetSoundSettingsBinding4.textViewTypeOwn.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding5 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding5 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model = fragmentDemoPresetSoundSettingsBinding5.getModel();
        if (model != null && (progressIdleTone = model.getProgressIdleTone()) != null) {
            progressIdleTone.set(30);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding6 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding6 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model2 = fragmentDemoPresetSoundSettingsBinding6.getModel();
        if (model2 != null && (progressIdleVolume = model2.getProgressIdleVolume()) != null) {
            progressIdleVolume.set(85);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding7 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding7 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model3 = fragmentDemoPresetSoundSettingsBinding7.getModel();
        if (model3 != null && (progressMainSound = model3.getProgressMainSound()) != null) {
            progressMainSound.set(80);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding8 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding8;
        }
        DemoPresetSoundSettingsFragmentViewModel model4 = fragmentDemoPresetSoundSettingsBinding2.getModel();
        if (model4 == null || (progressWorkingTone = model4.getProgressWorkingTone()) == null) {
            return;
        }
        progressWorkingTone.set(50);
    }

    private final void onSportTypeEnable() {
        ObservableInt progressWorkingTone;
        ObservableInt progressMainSound;
        ObservableInt progressIdleVolume;
        ObservableInt progressIdleTone;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.textViewTypeCity.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.textViewTypeSport.setChecked(true);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding4 = null;
        }
        fragmentDemoPresetSoundSettingsBinding4.textViewTypeOwn.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding5 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding5 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model = fragmentDemoPresetSoundSettingsBinding5.getModel();
        if (model != null && (progressIdleTone = model.getProgressIdleTone()) != null) {
            progressIdleTone.set(65);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding6 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding6 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model2 = fragmentDemoPresetSoundSettingsBinding6.getModel();
        if (model2 != null && (progressIdleVolume = model2.getProgressIdleVolume()) != null) {
            progressIdleVolume.set(30);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding7 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding7 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model3 = fragmentDemoPresetSoundSettingsBinding7.getModel();
        if (model3 != null && (progressMainSound = model3.getProgressMainSound()) != null) {
            progressMainSound.set(60);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding8 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding8;
        }
        DemoPresetSoundSettingsFragmentViewModel model4 = fragmentDemoPresetSoundSettingsBinding2.getModel();
        if (model4 == null || (progressWorkingTone = model4.getProgressWorkingTone()) == null) {
            return;
        }
        progressWorkingTone.set(40);
    }

    private final void onOwnTypeEnable() {
        ObservableInt progressWorkingTone;
        ObservableInt progressMainSound;
        ObservableInt progressIdleVolume;
        ObservableInt progressIdleTone;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.textViewTypeCity.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.textViewTypeSport.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding4 = null;
        }
        fragmentDemoPresetSoundSettingsBinding4.textViewTypeOwn.setChecked(true);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding5 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding5 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model = fragmentDemoPresetSoundSettingsBinding5.getModel();
        if (model != null && (progressIdleTone = model.getProgressIdleTone()) != null) {
            progressIdleTone.set(35);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding6 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding6 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model2 = fragmentDemoPresetSoundSettingsBinding6.getModel();
        if (model2 != null && (progressIdleVolume = model2.getProgressIdleVolume()) != null) {
            progressIdleVolume.set(45);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding7 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding7 = null;
        }
        DemoPresetSoundSettingsFragmentViewModel model3 = fragmentDemoPresetSoundSettingsBinding7.getModel();
        if (model3 != null && (progressMainSound = model3.getProgressMainSound()) != null) {
            progressMainSound.set(30);
        }
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding8 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding8;
        }
        DemoPresetSoundSettingsFragmentViewModel model4 = fragmentDemoPresetSoundSettingsBinding2.getModel();
        if (model4 == null || (progressWorkingTone = model4.getProgressWorkingTone()) == null) {
            return;
        }
        progressWorkingTone.set(60);
    }

    private final void onSoundStartOff() {
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.textViewOff.setChecked(true);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.textViewStarterSound.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding4;
        }
        fragmentDemoPresetSoundSettingsBinding2.textViewDynamicStart.setChecked(false);
    }

    private final void onSoundStartStarter() {
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.textViewOff.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.textViewStarterSound.setChecked(true);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding4;
        }
        fragmentDemoPresetSoundSettingsBinding2.textViewDynamicStart.setChecked(false);
    }

    private final void onSoundStartDynamic() {
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        fragmentDemoPresetSoundSettingsBinding.textViewOff.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.textViewStarterSound.setChecked(false);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding4;
        }
        fragmentDemoPresetSoundSettingsBinding2.textViewDynamicStart.setChecked(true);
    }

    private final void onChangeNumberChamber() {
        ObservableField<String> numberChamber;
        BottomSheetBehavior<View> bottomSheetBehavior = this.mNumberChamberSheetBehaviour;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = null;
        if (bottomSheetBehavior == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNumberChamberSheetBehaviour");
            bottomSheetBehavior = null;
        }
        bottomSheetBehavior.setState(5);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding2 = null;
        }
        int value = fragmentDemoPresetSoundSettingsBinding2.viewPickerNumberChamber.getValue();
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding = fragmentDemoPresetSoundSettingsBinding3;
        }
        DemoPresetSoundSettingsFragmentViewModel model = fragmentDemoPresetSoundSettingsBinding.getModel();
        if (model == null || (numberChamber = model.getNumberChamber()) == null) {
            return;
        }
        numberChamber.set(String.valueOf(value));
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

    private final void initNumberChamber() {
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding = this.binding;
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding2 = null;
        if (fragmentDemoPresetSoundSettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding = null;
        }
        BottomSheetBehavior<View> bottomSheetBehaviorFrom = BottomSheetBehavior.from(fragmentDemoPresetSoundSettingsBinding.layoutNumberChamber);
        Intrinsics.checkNotNullExpressionValue(bottomSheetBehaviorFrom, "from(binding.layoutNumberChamber)");
        this.mNumberChamberSheetBehaviour = bottomSheetBehaviorFrom;
        String[] strArr = {SessionDescription.SUPPORTED_SDP_VERSION, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE, "2", ExifInterface.GPS_MEASUREMENT_3D};
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding3 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding3 = null;
        }
        fragmentDemoPresetSoundSettingsBinding3.viewPickerNumberChamber.setDisplayedValues(strArr);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding4 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding4 = null;
        }
        fragmentDemoPresetSoundSettingsBinding4.viewPickerNumberChamber.setMinValue(0);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding5 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            fragmentDemoPresetSoundSettingsBinding5 = null;
        }
        fragmentDemoPresetSoundSettingsBinding5.viewPickerNumberChamber.setMaxValue(3);
        FragmentDemoPresetSoundSettingsBinding fragmentDemoPresetSoundSettingsBinding6 = this.binding;
        if (fragmentDemoPresetSoundSettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            fragmentDemoPresetSoundSettingsBinding2 = fragmentDemoPresetSoundSettingsBinding6;
        }
        fragmentDemoPresetSoundSettingsBinding2.viewPickerNumberChamber.setValue(3);
    }

    private final void onOpenShopActivity() {
        startActivity(new Intent(getContext(), (Class<?>) DemoShopActivity.class));
    }

    private final void onDeviceOrder() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(getBaseUrlWithLocale()));
        startActivity(intent);
    }

    private final String getBaseUrlWithLocale() {
        return Intrinsics.areEqual(Locale.getDefault().getDisplayLanguage(), "русский") ? BuildConfig.SITE_URL_RU : BuildConfig.SITE_URL;
    }

    private final void openTipDialog(TypeDialog type) {
        TipDialogFragment tipDialogFragment = new TipDialogFragment(type);
        tipDialogFragment.setOnConfirmListener(new OnConfirmDialogListener() { // from class: com.thor.app.gui.fragments.demo.DemoPresetSoundSettingsFragment$openTipDialog$$inlined$doOnConfirm$1
            @Override // com.fourksoft.base.ui.dialog.listener.OnConfirmDialogListener
            public void onConfirm(String info) {
            }
        });
        tipDialogFragment.show(getChildFragmentManager(), tipDialogFragment.getClass().getSimpleName());
    }

    /* compiled from: DemoPresetSoundSettingsFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lcom/thor/app/gui/fragments/demo/DemoPresetSoundSettingsFragment$Companion;", "", "()V", "newInstance", "Lcom/thor/app/gui/fragments/demo/DemoPresetSoundSettingsFragment;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final DemoPresetSoundSettingsFragment newInstance() {
            return new DemoPresetSoundSettingsFragment();
        }
    }
}
