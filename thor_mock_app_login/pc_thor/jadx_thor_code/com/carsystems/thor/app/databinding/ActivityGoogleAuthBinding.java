package com.carsystems.thor.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

/* loaded from: classes.dex */
public abstract class ActivityGoogleAuthBinding extends ViewDataBinding {
    public final GoogleSignInButton googleAuthButton;
    public final AppCompatTextView googleAuthDescription;
    public final AppCompatTextView googleAuthTitle;
    public final FrameLayout progressBar;
    public final Button shareLog;

    protected ActivityGoogleAuthBinding(Object _bindingComponent, View _root, int _localFieldCount, GoogleSignInButton googleAuthButton, AppCompatTextView googleAuthDescription, AppCompatTextView googleAuthTitle, FrameLayout progressBar, Button shareLog) {
        super(_bindingComponent, _root, _localFieldCount);
        this.googleAuthButton = googleAuthButton;
        this.googleAuthDescription = googleAuthDescription;
        this.googleAuthTitle = googleAuthTitle;
        this.progressBar = progressBar;
        this.shareLog = shareLog;
    }

    public static ActivityGoogleAuthBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGoogleAuthBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActivityGoogleAuthBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_google_auth, root, attachToRoot, component);
    }

    public static ActivityGoogleAuthBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGoogleAuthBinding inflate(LayoutInflater inflater, Object component) {
        return (ActivityGoogleAuthBinding) ViewDataBinding.inflateInternal(inflater, R.layout.activity_google_auth, null, false, component);
    }

    public static ActivityGoogleAuthBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActivityGoogleAuthBinding bind(View view, Object component) {
        return (ActivityGoogleAuthBinding) bind(component, view, R.layout.activity_google_auth);
    }
}
