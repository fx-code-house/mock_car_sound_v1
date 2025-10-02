package com.thor.app.auto.media;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.carsystems.thor.app.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AutoNotificationManager.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"NOTIFICATION_LARGE_ICON_SIZE", "", "NOW_PLAYING_CHANNEL_ID", "", "NOW_PLAYING_NOTIFICATION_ID", "glideOptions", "Lcom/bumptech/glide/request/RequestOptions;", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class AutoNotificationManagerKt {
    public static final int NOTIFICATION_LARGE_ICON_SIZE = 144;
    public static final String NOW_PLAYING_CHANNEL_ID = "com.thor.app.auto.media.NOW_PLAYING";
    public static final int NOW_PLAYING_NOTIFICATION_ID = 45881;
    private static final RequestOptions glideOptions;

    static {
        RequestOptions requestOptionsDiskCacheStrategy = new RequestOptions().fallback(R.drawable.ic_notification).diskCacheStrategy(DiskCacheStrategy.DATA);
        Intrinsics.checkNotNullExpressionValue(requestOptionsDiskCacheStrategy, "RequestOptions()\n    .fa…y(DiskCacheStrategy.DATA)");
        glideOptions = requestOptionsDiskCacheStrategy;
    }
}
