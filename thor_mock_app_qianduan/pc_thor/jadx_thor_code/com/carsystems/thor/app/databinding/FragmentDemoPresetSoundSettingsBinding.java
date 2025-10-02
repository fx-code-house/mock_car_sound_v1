package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.viewmodel.demo.DemoPresetSoundSettingsFragmentViewModel;

/* loaded from: classes.dex */
public abstract class FragmentDemoPresetSoundSettingsBinding extends ViewDataBinding {
    public final TextView idleText;
    public final AppCompatImageButton idleTipBtn;
    public final TextView idleTone;
    public final ImageView imageViewBack;
    public final ImageView imageViewIdleToneLeft;
    public final ImageView imageViewIdleToneRight;
    public final ImageView imageViewIdlingVolumeLeft;
    public final ImageView imageViewIdlingVolumeRight;
    public final ImageView imageViewMainVolumeLeft;
    public final ImageView imageViewMainVolumeRight;
    public final ImageView imageViewNumberChamber;
    public final ImageView imageViewUnevenToneLeft;
    public final ImageView imageViewUnevenToneRight;
    public final ImageView imageViewWorkingToneLeft;
    public final ImageView imageViewWorkingToneRight;
    public final ConstraintLayout layoutChangeNumberChamber;
    public final ConstraintLayout layoutHeader;
    public final ConstraintLayout layoutNumberChamber;

    @Bindable
    protected DemoPresetSoundSettingsFragmentViewModel mModel;
    public final ScrollView nestedScrollView;
    public final AppCompatSeekBar seekBarIdleTone;
    public final AppCompatSeekBar seekBarIdleVolume;
    public final AppCompatSeekBar seekBarMainVolume;
    public final AppCompatSeekBar seekBarUnevenTone;
    public final AppCompatSeekBar seekBarWorkingTone;
    public final Switch switchStartSession;
    public final CheckedTextView textViewDynamicStart;
    public final TextView textViewNumberChamber;
    public final CheckedTextView textViewOff;
    public final TextView textViewOrder;
    public final TextView textViewReady;
    public final CheckedTextView textViewStarterSound;
    public final CheckedTextView textViewTypeCity;
    public final CheckedTextView textViewTypeOwn;
    public final CheckedTextView textViewTypeSport;
    public final FrameLayout toolbarLayout;
    public final View viewDividerReady;
    public final NumberPicker viewPickerNumberChamber;
    public final View viewStatusBar;

    public abstract void setModel(DemoPresetSoundSettingsFragmentViewModel model);

    protected FragmentDemoPresetSoundSettingsBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView idleText, AppCompatImageButton idleTipBtn, TextView idleTone, ImageView imageViewBack, ImageView imageViewIdleToneLeft, ImageView imageViewIdleToneRight, ImageView imageViewIdlingVolumeLeft, ImageView imageViewIdlingVolumeRight, ImageView imageViewMainVolumeLeft, ImageView imageViewMainVolumeRight, ImageView imageViewNumberChamber, ImageView imageViewUnevenToneLeft, ImageView imageViewUnevenToneRight, ImageView imageViewWorkingToneLeft, ImageView imageViewWorkingToneRight, ConstraintLayout layoutChangeNumberChamber, ConstraintLayout layoutHeader, ConstraintLayout layoutNumberChamber, ScrollView nestedScrollView, AppCompatSeekBar seekBarIdleTone, AppCompatSeekBar seekBarIdleVolume, AppCompatSeekBar seekBarMainVolume, AppCompatSeekBar seekBarUnevenTone, AppCompatSeekBar seekBarWorkingTone, Switch switchStartSession, CheckedTextView textViewDynamicStart, TextView textViewNumberChamber, CheckedTextView textViewOff, TextView textViewOrder, TextView textViewReady, CheckedTextView textViewStarterSound, CheckedTextView textViewTypeCity, CheckedTextView textViewTypeOwn, CheckedTextView textViewTypeSport, FrameLayout toolbarLayout, View viewDividerReady, NumberPicker viewPickerNumberChamber, View viewStatusBar) {
        super(_bindingComponent, _root, _localFieldCount);
        this.idleText = idleText;
        this.idleTipBtn = idleTipBtn;
        this.idleTone = idleTone;
        this.imageViewBack = imageViewBack;
        this.imageViewIdleToneLeft = imageViewIdleToneLeft;
        this.imageViewIdleToneRight = imageViewIdleToneRight;
        this.imageViewIdlingVolumeLeft = imageViewIdlingVolumeLeft;
        this.imageViewIdlingVolumeRight = imageViewIdlingVolumeRight;
        this.imageViewMainVolumeLeft = imageViewMainVolumeLeft;
        this.imageViewMainVolumeRight = imageViewMainVolumeRight;
        this.imageViewNumberChamber = imageViewNumberChamber;
        this.imageViewUnevenToneLeft = imageViewUnevenToneLeft;
        this.imageViewUnevenToneRight = imageViewUnevenToneRight;
        this.imageViewWorkingToneLeft = imageViewWorkingToneLeft;
        this.imageViewWorkingToneRight = imageViewWorkingToneRight;
        this.layoutChangeNumberChamber = layoutChangeNumberChamber;
        this.layoutHeader = layoutHeader;
        this.layoutNumberChamber = layoutNumberChamber;
        this.nestedScrollView = nestedScrollView;
        this.seekBarIdleTone = seekBarIdleTone;
        this.seekBarIdleVolume = seekBarIdleVolume;
        this.seekBarMainVolume = seekBarMainVolume;
        this.seekBarUnevenTone = seekBarUnevenTone;
        this.seekBarWorkingTone = seekBarWorkingTone;
        this.switchStartSession = switchStartSession;
        this.textViewDynamicStart = textViewDynamicStart;
        this.textViewNumberChamber = textViewNumberChamber;
        this.textViewOff = textViewOff;
        this.textViewOrder = textViewOrder;
        this.textViewReady = textViewReady;
        this.textViewStarterSound = textViewStarterSound;
        this.textViewTypeCity = textViewTypeCity;
        this.textViewTypeOwn = textViewTypeOwn;
        this.textViewTypeSport = textViewTypeSport;
        this.toolbarLayout = toolbarLayout;
        this.viewDividerReady = viewDividerReady;
        this.viewPickerNumberChamber = viewPickerNumberChamber;
        this.viewStatusBar = viewStatusBar;
    }

    public DemoPresetSoundSettingsFragmentViewModel getModel() {
        return this.mModel;
    }

    public static FragmentDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentDemoPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_demo_preset_sound_settings, root, attachToRoot, component);
    }

    public static FragmentDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDemoPresetSoundSettingsBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentDemoPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_demo_preset_sound_settings, null, false, component);
    }

    public static FragmentDemoPresetSoundSettingsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentDemoPresetSoundSettingsBinding bind(View view, Object component) {
        return (FragmentDemoPresetSoundSettingsBinding) bind(component, view, R.layout.fragment_demo_preset_sound_settings);
    }
}
