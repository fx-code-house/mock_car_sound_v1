package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public final class AndroidSchedulers {
    private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable<Scheduler>() { // from class: io.reactivex.android.schedulers.AndroidSchedulers.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Scheduler call() throws Exception {
            return MainHolder.DEFAULT;
        }
    });

    private static final class MainHolder {
        static final Scheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);

        private MainHolder() {
        }
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
    }

    public static Scheduler from(Looper looper) {
        return from(looper, false);
    }

    public static Scheduler from(Looper looper, boolean z) {
        if (looper == null) {
            throw new NullPointerException("looper == null");
        }
        return new HandlerScheduler(new Handler(looper), z);
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }
}
