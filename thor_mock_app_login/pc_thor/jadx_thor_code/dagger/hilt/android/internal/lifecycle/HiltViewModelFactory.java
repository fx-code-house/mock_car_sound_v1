package dagger.hilt.android.internal.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.savedstate.SavedStateRegistryOwner;
import dagger.Module;
import dagger.hilt.EntryPoints;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.multibindings.Multibinds;
import java.io.Closeable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.inject.Provider;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class HiltViewModelFactory implements ViewModelProvider.Factory {
    public static final CreationExtras.Key<Function1<Object, ViewModel>> CREATION_CALLBACK_KEY = new CreationExtras.Key<Function1<Object, ViewModel>>() { // from class: dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.1
    };
    private final ViewModelProvider.Factory delegateFactory;
    private final ViewModelProvider.Factory hiltViewModelFactory;
    private final Set<String> hiltViewModelKeys;

    interface ActivityCreatorEntryPoint {
        ViewModelComponentBuilder getViewModelComponentBuilder();

        Set<String> getViewModelKeys();
    }

    public interface ViewModelFactoriesEntryPoint {
        Map<String, Object> getHiltViewModelAssistedMap();

        Map<String, Provider<ViewModel>> getHiltViewModelMap();
    }

    @Module
    interface ViewModelModule {
        @Multibinds
        Map<String, Object> hiltViewModelAssistedMap();

        @Multibinds
        Map<String, ViewModel> hiltViewModelMap();
    }

    public HiltViewModelFactory(Set<String> hiltViewModelKeys, ViewModelProvider.Factory delegateFactory, final ViewModelComponentBuilder viewModelComponentBuilder) {
        this.hiltViewModelKeys = hiltViewModelKeys;
        this.delegateFactory = delegateFactory;
        this.hiltViewModelFactory = new ViewModelProvider.Factory() { // from class: dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.2
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public <T extends ViewModel> T create(Class<T> cls, CreationExtras creationExtras) {
                final RetainedLifecycleImpl retainedLifecycleImpl = new RetainedLifecycleImpl();
                T t = (T) createViewModel(viewModelComponentBuilder.savedStateHandle(SavedStateHandleSupport.createSavedStateHandle(creationExtras)).viewModelLifecycle(retainedLifecycleImpl).build(), cls, creationExtras);
                Objects.requireNonNull(retainedLifecycleImpl);
                t.addCloseable(new Closeable() { // from class: dagger.hilt.android.internal.lifecycle.HiltViewModelFactory$2$$ExternalSyntheticLambda0
                    @Override // java.io.Closeable, java.lang.AutoCloseable
                    public final void close() {
                        retainedLifecycleImpl.dispatchOnCleared();
                    }
                });
                return t;
            }

            private <T extends ViewModel> T createViewModel(ViewModelComponent component, Class<T> modelClass, CreationExtras extras) {
                Provider<ViewModel> provider = ((ViewModelFactoriesEntryPoint) EntryPoints.get(component, ViewModelFactoriesEntryPoint.class)).getHiltViewModelMap().get(modelClass.getName());
                Function1 function1 = (Function1) extras.get(HiltViewModelFactory.CREATION_CALLBACK_KEY);
                Object obj = ((ViewModelFactoriesEntryPoint) EntryPoints.get(component, ViewModelFactoriesEntryPoint.class)).getHiltViewModelAssistedMap().get(modelClass.getName());
                if (obj == null) {
                    if (function1 != null) {
                        throw new IllegalStateException("Found creation callback but class " + modelClass.getName() + " does not have an assisted factory specified in @HiltViewModel.");
                    }
                    if (provider == null) {
                        throw new IllegalStateException("Expected the @HiltViewModel-annotated class " + modelClass.getName() + " to be available in the multi-binding of @HiltViewModelMap but none was found.");
                    }
                    return (T) provider.get();
                }
                if (provider != null) {
                    throw new AssertionError("Found the @HiltViewModel-annotated class " + modelClass.getName() + " in both the multi-bindings of @HiltViewModelMap and @HiltViewModelAssistedMap.");
                }
                if (function1 == null) {
                    throw new IllegalStateException("Found @HiltViewModel-annotated class " + modelClass.getName() + " using @AssistedInject but no creation callback was provided in CreationExtras.");
                }
                return (T) function1.invoke(obj);
            }
        };
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> cls, CreationExtras creationExtras) {
        if (this.hiltViewModelKeys.contains(cls.getName())) {
            return (T) this.hiltViewModelFactory.create(cls, creationExtras);
        }
        return (T) this.delegateFactory.create(cls, creationExtras);
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> cls) {
        if (this.hiltViewModelKeys.contains(cls.getName())) {
            return (T) this.hiltViewModelFactory.create(cls);
        }
        return (T) this.delegateFactory.create(cls);
    }

    public static ViewModelProvider.Factory createInternal(Activity activity, SavedStateRegistryOwner owner, Bundle defaultArgs, ViewModelProvider.Factory delegateFactory) {
        return createInternal(activity, delegateFactory);
    }

    public static ViewModelProvider.Factory createInternal(Activity activity, ViewModelProvider.Factory delegateFactory) {
        ActivityCreatorEntryPoint activityCreatorEntryPoint = (ActivityCreatorEntryPoint) EntryPoints.get(activity, ActivityCreatorEntryPoint.class);
        return new HiltViewModelFactory(activityCreatorEntryPoint.getViewModelKeys(), delegateFactory, activityCreatorEntryPoint.getViewModelComponentBuilder());
    }
}
