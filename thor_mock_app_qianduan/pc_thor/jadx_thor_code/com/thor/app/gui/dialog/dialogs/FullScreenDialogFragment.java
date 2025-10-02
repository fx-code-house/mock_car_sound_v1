package com.thor.app.gui.dialog.dialogs;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class FullScreenDialogFragment extends DialogFragment {
    protected Drawable mDrawable;

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("onCreate", new Object[0]);
        setStyle(2, 0);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Timber.i("onCreateDialog", new Object[0]);
        Dialog dialogOnCreateDialog = super.onCreateDialog(savedInstanceState);
        dialogOnCreateDialog.getWindow().requestFeature(1);
        return dialogOnCreateDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Timber.i("onStart", new Object[0]);
        getDialog().getWindow().setLayout(-1, -1);
    }

    protected void setBackground(ViewGroup viewGroup) {
        Timber.i("setBackground", new Object[0]);
        if (viewGroup == null) {
            throw new IllegalArgumentException("ViewGroup cannot be null");
        }
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            viewGroup.setBackground(drawable);
        }
    }

    public void setDrawableForBackground(Drawable drawable) {
        this.mDrawable = drawable;
    }
}
