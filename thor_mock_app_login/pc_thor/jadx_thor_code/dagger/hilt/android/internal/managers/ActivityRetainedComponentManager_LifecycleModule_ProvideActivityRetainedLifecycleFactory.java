package dagger.hilt.android.internal.managers;

import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: classes3.dex */
public final class ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory implements Factory<ActivityRetainedLifecycle> {
    @Override // javax.inject.Provider
    public ActivityRetainedLifecycle get() {
        return provideActivityRetainedLifecycle();
    }

    public static ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ActivityRetainedLifecycle provideActivityRetainedLifecycle() {
        return (ActivityRetainedLifecycle) Preconditions.checkNotNullFromProvides(ActivityRetainedComponentManager.LifecycleModule.provideActivityRetainedLifecycle());
    }

    private static final class InstanceHolder {
        private static final ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory INSTANCE = new ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory();

        private InstanceHolder() {
        }
    }
}
