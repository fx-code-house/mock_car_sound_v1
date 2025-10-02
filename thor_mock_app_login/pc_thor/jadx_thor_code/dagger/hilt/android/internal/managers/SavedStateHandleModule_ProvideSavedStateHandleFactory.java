package dagger.hilt.android.internal.managers;

import androidx.lifecycle.SavedStateHandle;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: classes3.dex */
public final class SavedStateHandleModule_ProvideSavedStateHandleFactory implements Factory<SavedStateHandle> {
    private final Provider<SavedStateHandleHolder> savedStateHandleHolderProvider;

    public SavedStateHandleModule_ProvideSavedStateHandleFactory(Provider<SavedStateHandleHolder> savedStateHandleHolderProvider) {
        this.savedStateHandleHolderProvider = savedStateHandleHolderProvider;
    }

    @Override // javax.inject.Provider
    public SavedStateHandle get() {
        return provideSavedStateHandle(this.savedStateHandleHolderProvider.get());
    }

    public static SavedStateHandleModule_ProvideSavedStateHandleFactory create(Provider<SavedStateHandleHolder> savedStateHandleHolderProvider) {
        return new SavedStateHandleModule_ProvideSavedStateHandleFactory(savedStateHandleHolderProvider);
    }

    public static SavedStateHandle provideSavedStateHandle(SavedStateHandleHolder savedStateHandleHolder) {
        return (SavedStateHandle) Preconditions.checkNotNullFromProvides(SavedStateHandleModule.provideSavedStateHandle(savedStateHandleHolder));
    }
}
