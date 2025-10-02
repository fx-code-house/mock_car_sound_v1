package com.thor.basemodule.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import timber.log.Timber;

/* loaded from: classes3.dex */
public final class ViewHelper {
    private ViewHelper() {
    }

    public static void hideKeyboard(Activity activity) {
        Timber.i("hideKeyboard", new Object[0]);
        if (activity == null) {
            throw new NullPointerException("Activity == null");
        }
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static void requestFocus(Activity activity, View view) {
        Timber.i("requestFocus", new Object[0]);
        if (activity == null) {
            throw new NullPointerException("Activity == null");
        }
        if (view == null) {
            throw new NullPointerException("View == null");
        }
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(5);
        }
    }
}
