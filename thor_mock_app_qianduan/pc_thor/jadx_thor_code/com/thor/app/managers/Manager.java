package com.thor.app.managers;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.carsystems.thor.app.BuildConfig;
import com.carsystems.thor.app.R;
import com.google.android.material.snackbar.Snackbar;
import com.thor.app.network.Api;
import com.thor.app.settings.Settings;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.networkmodule.network.ApiService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.Locale;

/* loaded from: classes3.dex */
public abstract class Manager {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected final Context mContext;

    protected String getAppType() {
        return "2";
    }

    protected int getAppVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    protected String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public Manager(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    public Api getApi() {
        return Api.from(this.mContext);
    }

    public ApiService getApiService() {
        return getApi().getApiService();
    }

    protected String getAccessToken() {
        return Settings.getAccessToken();
    }

    protected int getUserId() {
        return Settings.getUserId();
    }

    protected String getDeviceId() {
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        return hardwareProfile == null ? "" : hardwareProfile.getDeviceId();
    }

    protected String getLanguageCode() {
        return Settings.getLanguageCode();
    }

    protected Locale getCurrentLocale() {
        return this.mContext.getResources().getConfiguration().getLocales().get(0);
    }

    protected String getLanguage() {
        String languageCode = Settings.getLanguageCode();
        return TextUtils.isEmpty(languageCode) ? getCurrentLocale().getLanguage() : languageCode;
    }

    public Snackbar makeSnackBarNetworkError(final View view) {
        Snackbar snackbarMake = Snackbar.make(view, this.mContext.getString(R.string.warning_service_is_unavailable), 0);
        snackbarMake.getView().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.colorPrimary));
        return snackbarMake;
    }

    public Snackbar makeSnackBarError(final View view, final String message) {
        Snackbar snackbarMake = Snackbar.make(view, message, 0);
        snackbarMake.getView().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.colorPrimary));
        return snackbarMake;
    }

    public Snackbar makeSnackBarError(final View view, int resId) {
        Snackbar snackbarMake = Snackbar.make(view, this.mContext.getString(resId), 0);
        snackbarMake.getView().setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.colorPrimary));
        return snackbarMake;
    }

    public void addDisposable(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    public void dispose() {
        this.compositeDisposable.dispose();
    }
}
