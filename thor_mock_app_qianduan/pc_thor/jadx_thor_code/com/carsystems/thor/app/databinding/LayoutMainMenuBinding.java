package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.viewmodel.menu.MainMenuViewModel;

/* loaded from: classes.dex */
public abstract class LayoutMainMenuBinding extends ViewDataBinding {
    public final View flagNotificationsUnread;
    public final ImageView imageViewClose;
    public final ImageView imageViewLogo;
    public final ConstraintLayout layoutLanguage;

    @Bindable
    protected MainMenuViewModel mMainMenu;
    public final NestedScrollView nestedScrollView;
    public final TextView textNotifications;
    public final TextView textViewAppVersion;
    public final TextView textViewChangeLanguage;
    public final TextView textViewDemoMode;
    public final TextView textViewInfo;
    public final TextView textViewLanguage;
    public final TextView textViewMyCar;
    public final TextView textViewNewDevice;
    public final TextView textViewSerialNumber;
    public final AppCompatTextView textViewSettings;
    public final AppCompatTextView textViewShareLogs;
    public final AppCompatTextView textViewSupportForm;
    public final TextView textViewUpdateSoftware;
    public final TextView textViewUserId;
    public final TextView textViewVersion;
    public final View viewDividerLanguageBottom;
    public final View viewDividerLanguageTop;

    public abstract void setMainMenu(MainMenuViewModel mainMenu);

    protected LayoutMainMenuBinding(Object _bindingComponent, View _root, int _localFieldCount, View flagNotificationsUnread, ImageView imageViewClose, ImageView imageViewLogo, ConstraintLayout layoutLanguage, NestedScrollView nestedScrollView, TextView textNotifications, TextView textViewAppVersion, TextView textViewChangeLanguage, TextView textViewDemoMode, TextView textViewInfo, TextView textViewLanguage, TextView textViewMyCar, TextView textViewNewDevice, TextView textViewSerialNumber, AppCompatTextView textViewSettings, AppCompatTextView textViewShareLogs, AppCompatTextView textViewSupportForm, TextView textViewUpdateSoftware, TextView textViewUserId, TextView textViewVersion, View viewDividerLanguageBottom, View viewDividerLanguageTop) {
        super(_bindingComponent, _root, _localFieldCount);
        this.flagNotificationsUnread = flagNotificationsUnread;
        this.imageViewClose = imageViewClose;
        this.imageViewLogo = imageViewLogo;
        this.layoutLanguage = layoutLanguage;
        this.nestedScrollView = nestedScrollView;
        this.textNotifications = textNotifications;
        this.textViewAppVersion = textViewAppVersion;
        this.textViewChangeLanguage = textViewChangeLanguage;
        this.textViewDemoMode = textViewDemoMode;
        this.textViewInfo = textViewInfo;
        this.textViewLanguage = textViewLanguage;
        this.textViewMyCar = textViewMyCar;
        this.textViewNewDevice = textViewNewDevice;
        this.textViewSerialNumber = textViewSerialNumber;
        this.textViewSettings = textViewSettings;
        this.textViewShareLogs = textViewShareLogs;
        this.textViewSupportForm = textViewSupportForm;
        this.textViewUpdateSoftware = textViewUpdateSoftware;
        this.textViewUserId = textViewUserId;
        this.textViewVersion = textViewVersion;
        this.viewDividerLanguageBottom = viewDividerLanguageBottom;
        this.viewDividerLanguageTop = viewDividerLanguageTop;
    }

    public MainMenuViewModel getMainMenu() {
        return this.mMainMenu;
    }

    public static LayoutMainMenuBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutMainMenuBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (LayoutMainMenuBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_main_menu, root, attachToRoot, component);
    }

    public static LayoutMainMenuBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutMainMenuBinding inflate(LayoutInflater inflater, Object component) {
        return (LayoutMainMenuBinding) ViewDataBinding.inflateInternal(inflater, R.layout.layout_main_menu, null, false, component);
    }

    public static LayoutMainMenuBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static LayoutMainMenuBinding bind(View view, Object component) {
        return (LayoutMainMenuBinding) bind(component, view, R.layout.layout_main_menu);
    }
}
