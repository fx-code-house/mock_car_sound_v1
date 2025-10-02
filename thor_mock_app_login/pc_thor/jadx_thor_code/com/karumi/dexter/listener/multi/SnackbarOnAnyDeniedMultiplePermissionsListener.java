package com.karumi.dexter.listener.multi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.MultiplePermissionsReport;

/* loaded from: classes2.dex */
public class SnackbarOnAnyDeniedMultiplePermissionsListener extends BaseMultiplePermissionsListener {
    private final String buttonText;
    private final int duration;
    private final View.OnClickListener onButtonClickListener;
    private final Snackbar.Callback snackbarCallback;
    private final String text;
    private final View view;

    public static class Builder {
        private String buttonText;
        private int duration = 0;
        private View.OnClickListener onClickListener;
        private Snackbar.Callback snackbarCallback;
        private final String text;
        private final View view;

        private Builder(View view, String str) {
            this.view = view;
            this.text = str;
        }

        public static Builder with(View view, int i) {
            return with(view, view.getContext().getString(i));
        }

        public static Builder with(View view, String str) {
            return new Builder(view, str);
        }

        public SnackbarOnAnyDeniedMultiplePermissionsListener build() {
            return new SnackbarOnAnyDeniedMultiplePermissionsListener(this.view, this.text, this.buttonText, this.onClickListener, this.snackbarCallback, this.duration);
        }

        public Builder withButton(int i, View.OnClickListener onClickListener) {
            return withButton(this.view.getContext().getString(i), onClickListener);
        }

        public Builder withButton(String str, View.OnClickListener onClickListener) {
            this.buttonText = str;
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder withCallback(Snackbar.Callback callback) {
            this.snackbarCallback = callback;
            return this;
        }

        public Builder withDuration(int i) {
            this.duration = i;
            return this;
        }

        public Builder withOpenSettingsButton(int i) {
            return withOpenSettingsButton(this.view.getContext().getString(i));
        }

        public Builder withOpenSettingsButton(String str) {
            this.buttonText = str;
            this.onClickListener = new View.OnClickListener() { // from class: com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Context context = Builder.this.view.getContext();
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + context.getPackageName()));
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                }
            };
            return this;
        }
    }

    private SnackbarOnAnyDeniedMultiplePermissionsListener(View view, String str, String str2, View.OnClickListener onClickListener, Snackbar.Callback callback, int i) {
        this.view = view;
        this.text = str;
        this.buttonText = str2;
        this.onButtonClickListener = onClickListener;
        this.snackbarCallback = callback;
        this.duration = i;
    }

    private void showSnackbar() {
        View.OnClickListener onClickListener;
        Snackbar snackbarMake = Snackbar.make(this.view, this.text, this.duration);
        String str = this.buttonText;
        if (str != null && (onClickListener = this.onButtonClickListener) != null) {
            snackbarMake.setAction(str, onClickListener);
        }
        Snackbar.Callback callback = this.snackbarCallback;
        if (callback != null) {
            snackbarMake.addCallback(callback);
        }
        snackbarMake.show();
    }

    @Override // com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener, com.karumi.dexter.listener.multi.MultiplePermissionsListener
    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        super.onPermissionsChecked(multiplePermissionsReport);
        if (multiplePermissionsReport.areAllPermissionsGranted()) {
            return;
        }
        showSnackbar();
    }
}
