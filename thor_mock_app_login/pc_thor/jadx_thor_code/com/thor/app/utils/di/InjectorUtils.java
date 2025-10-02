package com.thor.app.utils.di;

import android.content.ComponentName;
import android.content.Context;
import com.thor.app.auto.common.MusicServiceConnection;
import com.thor.app.auto.media.MusicService;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InjectorUtils.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/thor/app/utils/di/InjectorUtils;", "", "()V", "provideMusicServiceConnection", "Lcom/thor/app/auto/common/MusicServiceConnection;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class InjectorUtils {
    public static final InjectorUtils INSTANCE = new InjectorUtils();

    private InjectorUtils() {
    }

    public final MusicServiceConnection provideMusicServiceConnection(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return MusicServiceConnection.INSTANCE.getInstance(context, new ComponentName(context, (Class<?>) MusicService.class));
    }
}
