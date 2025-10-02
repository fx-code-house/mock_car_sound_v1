package com.thor.app.gui.dialog.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carsystems.thor.app.R;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class ProgressDialogFragment extends FullScreenDialogFragment {
    public static final String TAG = "ProgressDialogFragment";

    public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }

    @Override // com.thor.app.gui.dialog.dialogs.FullScreenDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.i("onCreateView", new Object[0]);
        return inflater.inflate(R.layout.fragment_dialog_progress, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.i("onViewCreated", new Object[0]);
        setCancelable(false);
    }
}
