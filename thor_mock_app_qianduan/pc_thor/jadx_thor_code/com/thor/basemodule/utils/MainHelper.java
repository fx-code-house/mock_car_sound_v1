package com.thor.basemodule.utils;

import android.content.Context;
import com.thor.basemodule.R;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class MainHelper {
    public static void showResourceLayoutType(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        Timber.i("Configuration: " + context.getResources().getConfiguration().toString(), new Object[0]);
        Timber.i("Resource layout: " + context.getString(R.string.app_resource_values_type), new Object[0]);
    }
}
