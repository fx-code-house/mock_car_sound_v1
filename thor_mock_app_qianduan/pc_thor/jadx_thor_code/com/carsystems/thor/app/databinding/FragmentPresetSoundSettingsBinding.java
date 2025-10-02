package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.model.MainPresetPackage;

/* loaded from: classes.dex */
public abstract class FragmentPresetSoundSettingsBinding extends ViewDataBinding {
    public final TextView idleText;
    public final AppCompatImageButton idleTipBtn;
    public final TextView idleTone;
    public final AppCompatImageView imageHeader;
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
    public final CoordinatorLayout layoutMain;
    public final ConstraintLayout layoutNumberChamber;
    public final ConstraintLayout layoutPresetModes;

    @Bindable
    protected MainPresetPackage mPresetPackage;
    public final NestedScrollView nestedScrollView;
    public final AppCompatSeekBar seekBarIdleTone;
    public final AppCompatSeekBar seekBarIdleVolume;
    public final AppCompatSeekBar seekBarMainVolume;
    public final AppCompatSeekBar seekBarUnevenTone;
    public final AppCompatSeekBar seekBarWorkingTone;
    public final TextView textViewCarName;
    public final CheckedTextView textViewDynamicStart;
    public final TextView textViewNumberChamber;
    public final CheckedTextView textViewOff;
    public final TextView textViewReady;
    public final TextView textViewSelectedType;
    public final CheckedTextView textViewStarterSound;
    public final CheckedTextView textViewTypeCity;
    public final CheckedTextView textViewTypeOwn;
    public final CheckedTextView textViewTypeSport;
    public final ToolbarWidget toolbarWidget;
    public final View viewDividerReady;
    public final NumberPicker viewPickerNumberChamber;

    public abstract void setPresetPackage(MainPresetPackage presetPackage);

    protected FragmentPresetSoundSettingsBinding(Object _bindingComponent, View _root, int _localFieldCount, TextView idleText, AppCompatImageButton idleTipBtn, TextView idleTone, AppCompatImageView imageHeader, ImageView imageViewIdleToneLeft, ImageView imageViewIdleToneRight, ImageView imageViewIdlingVolumeLeft, ImageView imageViewIdlingVolumeRight, ImageView imageViewMainVolumeLeft, ImageView imageViewMainVolumeRight, ImageView imageViewNumberChamber, ImageView imageViewUnevenToneLeft, ImageView imageViewUnevenToneRight, ImageView imageViewWorkingToneLeft, ImageView imageViewWorkingToneRight, ConstraintLayout layoutChangeNumberChamber, CoordinatorLayout layoutMain, ConstraintLayout layoutNumberChamber, ConstraintLayout layoutPresetModes, NestedScrollView nestedScrollView, AppCompatSeekBar seekBarIdleTone, AppCompatSeekBar seekBarIdleVolume, AppCompatSeekBar seekBarMainVolume, AppCompatSeekBar seekBarUnevenTone, AppCompatSeekBar seekBarWorkingTone, TextView textViewCarName, CheckedTextView textViewDynamicStart, TextView textViewNumberChamber, CheckedTextView textViewOff, TextView textViewReady, TextView textViewSelectedType, CheckedTextView textViewStarterSound, CheckedTextView textViewTypeCity, CheckedTextView textViewTypeOwn, CheckedTextView textViewTypeSport, ToolbarWidget toolbarWidget, View viewDividerReady, NumberPicker viewPickerNumberChamber) {
        super(_bindingComponent, _root, _localFieldCount);
        this.idleText = idleText;
        this.idleTipBtn = idleTipBtn;
        this.idleTone = idleTone;
        this.imageHeader = imageHeader;
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
        this.layoutMain = layoutMain;
        this.layoutNumberChamber = layoutNumberChamber;
        this.layoutPresetModes = layoutPresetModes;
        this.nestedScrollView = nestedScrollView;
        this.seekBarIdleTone = seekBarIdleTone;
        this.seekBarIdleVolume = seekBarIdleVolume;
        this.seekBarMainVolume = seekBarMainVolume;
        this.seekBarUnevenTone = seekBarUnevenTone;
        this.seekBarWorkingTone = seekBarWorkingTone;
        this.textViewCarName = textViewCarName;
        this.textViewDynamicStart = textViewDynamicStart;
        this.textViewNumberChamber = textViewNumberChamber;
        this.textViewOff = textViewOff;
        this.textViewReady = textViewReady;
        this.textViewSelectedType = textViewSelectedType;
        this.textViewStarterSound = textViewStarterSound;
        this.textViewTypeCity = textViewTypeCity;
        this.textViewTypeOwn = textViewTypeOwn;
        this.textViewTypeSport = textViewTypeSport;
        this.toolbarWidget = toolbarWidget;
        this.viewDividerReady = viewDividerReady;
        this.viewPickerNumberChamber = viewPickerNumberChamber;
    }

    public MainPresetPackage getPresetPackage() {
        return this.mPresetPackage;
    }

    public static FragmentPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentPresetSoundSettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FragmentPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_preset_sound_settings, root, attachToRoot, component);
    }

    public static FragmentPresetSoundSettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentPresetSoundSettingsBinding inflate(LayoutInflater inflater, Object component) {
        return (FragmentPresetSoundSettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.fragment_preset_sound_settings, null, false, component);
    }

    public static FragmentPresetSoundSettingsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FragmentPresetSoundSettingsBinding bind(View view, Object component) {
        return (FragmentPresetSoundSettingsBinding) bind(component, view, R.layout.fragment_preset_sound_settings);
    }
}
