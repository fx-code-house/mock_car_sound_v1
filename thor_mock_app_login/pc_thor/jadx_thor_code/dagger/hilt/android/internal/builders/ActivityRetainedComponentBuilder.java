package dagger.hilt.android.internal.builders;

import dagger.BindsInstance;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;

/* loaded from: classes3.dex */
public interface ActivityRetainedComponentBuilder {
    ActivityRetainedComponent build();

    ActivityRetainedComponentBuilder savedStateHandleHolder(@BindsInstance SavedStateHandleHolder savedStateHandleHolder);
}
