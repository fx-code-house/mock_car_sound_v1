package com.karumi.dexter.listener.single;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import com.karumi.dexter.listener.PermissionDeniedResponse;

/* loaded from: classes2.dex */
public class DialogOnDeniedPermissionListener extends BasePermissionListener {
    private final Context context;
    private final Drawable icon;
    private final String message;
    private final String positiveButtonText;
    private final String title;

    public static class Builder {
        private String buttonText;
        private final Context context;
        private Drawable icon;
        private String message;
        private String title;

        private Builder(Context context) {
            this.context = context;
        }

        public static Builder withContext(Context context) {
            return new Builder(context);
        }

        public DialogOnDeniedPermissionListener build() {
            String str = this.title;
            String str2 = str == null ? "" : str;
            String str3 = this.message;
            String str4 = str3 == null ? "" : str3;
            String str5 = this.buttonText;
            return new DialogOnDeniedPermissionListener(this.context, str2, str4, str5 == null ? "" : str5, this.icon);
        }

        public Builder withButtonText(int i) {
            this.buttonText = this.context.getString(i);
            return this;
        }

        public Builder withButtonText(String str) {
            this.buttonText = str;
            return this;
        }

        public Builder withIcon(int i) {
            this.icon = this.context.getResources().getDrawable(i);
            return this;
        }

        public Builder withIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public Builder withMessage(int i) {
            this.message = this.context.getString(i);
            return this;
        }

        public Builder withMessage(String str) {
            this.message = str;
            return this;
        }

        public Builder withTitle(int i) {
            this.title = this.context.getString(i);
            return this;
        }

        public Builder withTitle(String str) {
            this.title = str;
            return this;
        }
    }

    private DialogOnDeniedPermissionListener(Context context, String str, String str2, String str3, Drawable drawable) {
        this.context = context;
        this.title = str;
        this.message = str2;
        this.positiveButtonText = str3;
        this.icon = drawable;
    }

    @Override // com.karumi.dexter.listener.single.BasePermissionListener, com.karumi.dexter.listener.single.PermissionListener
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
        super.onPermissionDenied(permissionDeniedResponse);
        new AlertDialog.Builder(this.context).setTitle(this.title).setMessage(this.message).setPositiveButton(this.positiveButtonText, new DialogInterface.OnClickListener() { // from class: com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setIcon(this.icon).show();
    }
}
