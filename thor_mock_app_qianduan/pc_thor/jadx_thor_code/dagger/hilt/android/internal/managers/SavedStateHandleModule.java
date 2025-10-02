package dagger.hilt.android.internal.managers;

import androidx.lifecycle.SavedStateHandle;
import dagger.Module;
import dagger.Provides;

@Module
/* loaded from: classes3.dex */
abstract class SavedStateHandleModule {
    SavedStateHandleModule() {
    }

    @Provides
    static SavedStateHandle provideSavedStateHandle(SavedStateHandleHolder savedStateHandleHolder) {
        return savedStateHandleHolder.getSavedStateHandle();
    }
}
