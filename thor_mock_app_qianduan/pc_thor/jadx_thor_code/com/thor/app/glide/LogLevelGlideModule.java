package com.thor.app.glide;

import android.content.Context;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.AppGlideModule;

/* loaded from: classes2.dex */
public class LogLevelGlideModule extends AppGlideModule {
    @Override // com.bumptech.glide.module.AppGlideModule, com.bumptech.glide.module.AppliesOptions
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setLogLevel(6);
    }
}
