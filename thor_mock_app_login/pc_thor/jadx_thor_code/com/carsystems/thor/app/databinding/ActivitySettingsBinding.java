package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel;
import com.thor.app.gui.views.RssiSignalView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public abstract class ActivitySettingsBinding extends ViewDataBinding {
    public final ConstraintLayout actionDriveSelect;
    public final ConstraintLayout actionFormatFlashButton;
    public final LinearLayout actionMyAuto;
    public final ConstraintLayout actionNativeButtonControl;
    public final LinearLayout actionNewDevice;
    public final LinearLayout actionTwoSpeakerInstalled;
    public final ConstraintLayout bluetoothSignal;
    public final RssiSignalView customView;
    public final SwitchMaterial driveSelectSwitch;
    public final AppCompatTextView driveSelectText;
    public final AppCompatImageButton driveSelectTip;
    public final AppCompatTextView formatFlashText;
    public final AppCompatImageButton formatFlashTip;

    @Bindable
    protected SettingsActivityViewModel mModel;
    public final SwitchMaterial nativeControlSwitch;
    public final AppCompatTextView nativeControlText;
    public final AppCompatImageButton nativeControlTip;
    public final FrameLayout progressBar;
    public final ToolbarWidget toolbarWidget;
    public final SwitchMaterial twoSpeakerSwitch;

    public abstract void setModel(SettingsActivityViewModel model);

    protected ActivitySettingsBinding(Object _bindingComponent, View _root, int _localFieldCount, ConstraintLayout actionDriveSelect, ConstraintLayout actionFormatFlashButton, LinearLayout actionMyAuto, ConstraintLayout actionNativeButtonControl, LinearLayout actionNewDevice, LinearLayout actionTwoSpeakerInstalled, ConstraintLayout bluetoothSignal, RssiSignalView customView, SwitchMaterial driveSelectSwitch, AppCompatTextView driveSelectText, AppCompatImageButton driveSelectTip, AppCompatTextView formatFlashText, AppCompatImageButton formatFlashTip, SwitchMaterial nativeControlSwitch, AppCompatTextView nativeControlText, AppCompatImageButton nativeControlTip, FrameLayout progressBar, ToolbarWidget toolbarWidget, SwitchMaterial twoSpeakerSwitch) {
        super(_bindingComponent, _root, _localFieldCount);
        this.actionDriveSelect = actionDriveSelect;
        this.actionFormatFlashButton = actionFormatFlashButton;
        this.actionMyAuto = actionMyAuto;
        this.actionNativeButtonControl = actionNativeButtonControl;
        this.actionNewDevice = actionNewDevice;
        this.actionTwoSpeakerInstalled = actionTwoSpeakerInstalled;
        this.bluetoothSignal = bluetoothSignal;
        this.customView = customView;
        this.driveSelectSwitch = driveSelectSwitch;
        this.driveSelectText = driveSelectText;
        this.driveSelectTip = driveSelectTip;
        this.formatFlashText = formatFlashText;
        this.formatFlashTip = formatFlashTip;
        this.nativeControlSwitch = nativeControlSwitch;
        this.nativeControlText = nativeControlText;
        this.nativeControlTip = nativeControlTip;
        this.progressBar = progressBar;
        this.toolbarWidget = toolbarWidget;
        this.twoSpeakerSwitch = twoSpeakerSwitch;
    }

    public SettingsActivityViewModel getModel() {
        return this.mModel;
    }

    public static ActivitySettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySettingsBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivitySettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_settings, root, attachToRoot, component);
    }

    public static ActivitySettingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySettingsBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivitySettingsBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_settings, null, false, component);
    }

    public static ActivitySettingsBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivitySettingsBinding bind(View view, Object component) {
        return (ActivitySettingsBinding) bind(component, view, R.layout.activity_settings);
    }
}
