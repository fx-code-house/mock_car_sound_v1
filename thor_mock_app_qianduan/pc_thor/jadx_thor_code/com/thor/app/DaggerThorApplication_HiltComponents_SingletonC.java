package com.thor.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.thor.app.ThorApplication_HiltComponents;
import com.thor.app.billing.BillingManager;
import com.thor.app.databinding.viewmodels.UpdateViewModel;
import com.thor.app.databinding.viewmodels.UpdateViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.di.AppModule_ProvideBillingManagerFactory;
import com.thor.app.di.AppModule_ProvideBleManagerFactory;
import com.thor.app.di.AppModule_ProvideNotificationCountHelperFactory;
import com.thor.app.di.AppModule_ProvidePushNotificationManagerFactory;
import com.thor.app.di.AppModule_ProvideSettingsFactory;
import com.thor.app.di.AppModule_ProvideUsersManagerFactory;
import com.thor.app.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.thor.app.di.DatabaseModule_ProvideCurrentVersionDaoFactory;
import com.thor.app.di.NetworkModule_ProvideGsonConverterFactoryFactory;
import com.thor.app.di.NetworkModule_ProvideLoggingInterceptorFactory;
import com.thor.app.di.NetworkModule_ProvideNewApiServiceFactory;
import com.thor.app.di.NetworkModule_ProvideNewRetrofitFactory;
import com.thor.app.di.NetworkModule_ProvideOkHttpClientFactory;
import com.thor.app.di.NetworkModule_ProvideServerUrlFactory;
import com.thor.app.di.NetworkModule_ProviderHttpLoggingInterceptorLoggerFactory;
import com.thor.app.gui.activities.demo.DemoActivity;
import com.thor.app.gui.activities.googleautha.GoogleAuthActivity;
import com.thor.app.gui.activities.main.MainActivity;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.gui.activities.main.MainActivityViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.notifications.NotificationsActivity;
import com.thor.app.gui.activities.notifications.NotificationsViewModel;
import com.thor.app.gui.activities.notifications.NotificationsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity;
import com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel;
import com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.settings.SettingsActivity;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.shop.ShopActivity;
import com.thor.app.gui.activities.shop.ShopViewModel;
import com.thor.app.gui.activities.shop.ShopViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity;
import com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity_MembersInjector;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity_MembersInjector;
import com.thor.app.gui.activities.splash.SplashActivity;
import com.thor.app.gui.activities.splash.SplashActivityViewModel;
import com.thor.app.gui.activities.splash.SplashActivityViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.testers.FirmwareListActivity;
import com.thor.app.gui.activities.testers.FirmwareListViewModel;
import com.thor.app.gui.activities.testers.FirmwareListViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.activities.updatecheck.UpdateAppActivity;
import com.thor.app.gui.dialog.FormatProgressViewModel;
import com.thor.app.gui.dialog.FormatProgressViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.fragments.notification.NotificationOverviewFragment;
import com.thor.app.gui.fragments.notification.NotificationsFragment;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.fragments.shop.main.MainShopFragment;
import com.thor.app.gui.fragments.shop.main.MainShopFragment_MembersInjector;
import com.thor.app.gui.fragments.shop.main.MainShopViewModel;
import com.thor.app.gui.fragments.shop.main.MainShopViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.gui.fragments.shop.sgu.SguShopFragment;
import com.thor.app.gui.fragments.shop.sgu.SguShopViewModel;
import com.thor.app.gui.fragments.shop.sgu.SguShopViewModel_HiltModules_KeyModule_ProvideFactory;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.PushNotificationManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.CurrentVersionDao;
import com.thor.app.services.FmcService;
import com.thor.app.services.FmcService_MembersInjector;
import com.thor.app.settings.Settings;
import com.thor.app.utils.NotificationCountHelper;
import com.thor.networkmodule.network.ApiServiceNew;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes.dex */
public final class DaggerThorApplication_HiltComponents_SingletonC {
    private DaggerThorApplication_HiltComponents_SingletonC() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ApplicationContextModule applicationContextModule;

        private Builder() {
        }

        public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
            this.applicationContextModule = (ApplicationContextModule) Preconditions.checkNotNull(applicationContextModule);
            return this;
        }

        public ThorApplication_HiltComponents.SingletonC build() {
            Preconditions.checkBuilderRequirement(this.applicationContextModule, ApplicationContextModule.class);
            return new SingletonCImpl(this.applicationContextModule);
        }
    }

    private static final class ActivityRetainedCBuilder implements ThorApplication_HiltComponents.ActivityRetainedC.Builder {
        private SavedStateHandleHolder savedStateHandleHolder;
        private final SingletonCImpl singletonCImpl;

        private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
            this.singletonCImpl = singletonCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder
        public ActivityRetainedCBuilder savedStateHandleHolder(SavedStateHandleHolder savedStateHandleHolder) {
            this.savedStateHandleHolder = (SavedStateHandleHolder) Preconditions.checkNotNull(savedStateHandleHolder);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder
        public ThorApplication_HiltComponents.ActivityRetainedC build() {
            Preconditions.checkBuilderRequirement(this.savedStateHandleHolder, SavedStateHandleHolder.class);
            return new ActivityRetainedCImpl(this.singletonCImpl, this.savedStateHandleHolder);
        }
    }

    private static final class ActivityCBuilder implements ThorApplication_HiltComponents.ActivityC.Builder {
        private Activity activity;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final SingletonCImpl singletonCImpl;

        private ActivityCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl) {
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.ActivityComponentBuilder
        public ActivityCBuilder activity(Activity activity) {
            this.activity = (Activity) Preconditions.checkNotNull(activity);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ActivityComponentBuilder
        public ThorApplication_HiltComponents.ActivityC build() {
            Preconditions.checkBuilderRequirement(this.activity, Activity.class);
            return new ActivityCImpl(this.singletonCImpl, this.activityRetainedCImpl, this.activity);
        }
    }

    private static final class FragmentCBuilder implements ThorApplication_HiltComponents.FragmentC.Builder {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private Fragment fragment;
        private final SingletonCImpl singletonCImpl;

        private FragmentCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            this.activityCImpl = activityCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.FragmentComponentBuilder
        public FragmentCBuilder fragment(Fragment fragment) {
            this.fragment = (Fragment) Preconditions.checkNotNull(fragment);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.FragmentComponentBuilder
        public ThorApplication_HiltComponents.FragmentC build() {
            Preconditions.checkBuilderRequirement(this.fragment, Fragment.class);
            return new FragmentCImpl(this.singletonCImpl, this.activityRetainedCImpl, this.activityCImpl, this.fragment);
        }
    }

    private static final class ViewWithFragmentCBuilder implements ThorApplication_HiltComponents.ViewWithFragmentC.Builder {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final FragmentCImpl fragmentCImpl;
        private final SingletonCImpl singletonCImpl;
        private View view;

        private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl, FragmentCImpl fragmentCImpl) {
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            this.activityCImpl = activityCImpl;
            this.fragmentCImpl = fragmentCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder
        public ViewWithFragmentCBuilder view(View view) {
            this.view = (View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder
        public ThorApplication_HiltComponents.ViewWithFragmentC build() {
            Preconditions.checkBuilderRequirement(this.view, View.class);
            return new ViewWithFragmentCImpl(this.singletonCImpl, this.activityRetainedCImpl, this.activityCImpl, this.fragmentCImpl, this.view);
        }
    }

    private static final class ViewCBuilder implements ThorApplication_HiltComponents.ViewC.Builder {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final SingletonCImpl singletonCImpl;
        private View view;

        private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            this.activityCImpl = activityCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.ViewComponentBuilder
        public ViewCBuilder view(View view) {
            this.view = (View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ViewComponentBuilder
        public ThorApplication_HiltComponents.ViewC build() {
            Preconditions.checkBuilderRequirement(this.view, View.class);
            return new ViewCImpl(this.singletonCImpl, this.activityRetainedCImpl, this.activityCImpl, this.view);
        }
    }

    private static final class ViewModelCBuilder implements ThorApplication_HiltComponents.ViewModelC.Builder {
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private SavedStateHandle savedStateHandle;
        private final SingletonCImpl singletonCImpl;
        private ViewModelLifecycle viewModelLifecycle;

        private ViewModelCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl) {
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.ViewModelComponentBuilder
        public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
            this.savedStateHandle = (SavedStateHandle) Preconditions.checkNotNull(handle);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ViewModelComponentBuilder
        public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
            this.viewModelLifecycle = (ViewModelLifecycle) Preconditions.checkNotNull(viewModelLifecycle);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ViewModelComponentBuilder
        public ThorApplication_HiltComponents.ViewModelC build() {
            Preconditions.checkBuilderRequirement(this.savedStateHandle, SavedStateHandle.class);
            Preconditions.checkBuilderRequirement(this.viewModelLifecycle, ViewModelLifecycle.class);
            return new ViewModelCImpl(this.singletonCImpl, this.activityRetainedCImpl, this.savedStateHandle, this.viewModelLifecycle);
        }
    }

    private static final class ServiceCBuilder implements ThorApplication_HiltComponents.ServiceC.Builder {
        private Service service;
        private final SingletonCImpl singletonCImpl;

        private ServiceCBuilder(SingletonCImpl singletonCImpl) {
            this.singletonCImpl = singletonCImpl;
        }

        @Override // dagger.hilt.android.internal.builders.ServiceComponentBuilder
        public ServiceCBuilder service(Service service) {
            this.service = (Service) Preconditions.checkNotNull(service);
            return this;
        }

        @Override // dagger.hilt.android.internal.builders.ServiceComponentBuilder
        public ThorApplication_HiltComponents.ServiceC build() {
            Preconditions.checkBuilderRequirement(this.service, Service.class);
            return new ServiceCImpl(this.singletonCImpl, this.service);
        }
    }

    private static final class ViewWithFragmentCImpl extends ThorApplication_HiltComponents.ViewWithFragmentC {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final FragmentCImpl fragmentCImpl;
        private final SingletonCImpl singletonCImpl;
        private final ViewWithFragmentCImpl viewWithFragmentCImpl;

        private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl, FragmentCImpl fragmentCImpl, View viewParam) {
            this.viewWithFragmentCImpl = this;
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            this.activityCImpl = activityCImpl;
            this.fragmentCImpl = fragmentCImpl;
        }
    }

    private static final class FragmentCImpl extends ThorApplication_HiltComponents.FragmentC {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final FragmentCImpl fragmentCImpl;
        private final SingletonCImpl singletonCImpl;

        @Override // com.thor.app.gui.fragments.notification.NotificationOverviewFragment_GeneratedInjector
        public void injectNotificationOverviewFragment(NotificationOverviewFragment notificationOverviewFragment) {
        }

        @Override // com.thor.app.gui.fragments.notification.NotificationsFragment_GeneratedInjector
        public void injectNotificationsFragment(NotificationsFragment notificationsFragment) {
        }

        @Override // com.thor.app.gui.fragments.shop.sgu.SguShopFragment_GeneratedInjector
        public void injectSguShopFragment(SguShopFragment sguShopFragment) {
        }

        @Override // com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment_GeneratedInjector
        public void injectSguSoundsFragment(SguSoundsFragment sguSoundsFragment) {
        }

        private FragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl, Fragment fragmentParam) {
            this.fragmentCImpl = this;
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            this.activityCImpl = activityCImpl;
        }

        @Override // com.thor.app.gui.fragments.shop.main.MainShopFragment_GeneratedInjector
        public void injectMainShopFragment(MainShopFragment mainShopFragment) {
            injectMainShopFragment2(mainShopFragment);
        }

        @Override // dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories.FragmentEntryPoint
        public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
            return this.activityCImpl.getHiltInternalFactoryFactory();
        }

        @Override // dagger.hilt.android.internal.managers.ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint
        public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
            return new ViewWithFragmentCBuilder(this.singletonCImpl, this.activityRetainedCImpl, this.activityCImpl, this.fragmentCImpl);
        }

        private MainShopFragment injectMainShopFragment2(MainShopFragment instance) {
            MainShopFragment_MembersInjector.injectDatabase(instance, (ThorDatabase) this.singletonCImpl.provideAppDatabaseProvider.get());
            MainShopFragment_MembersInjector.injectUsersManager(instance, (UsersManager) this.singletonCImpl.provideUsersManagerProvider.get());
            return instance;
        }
    }

    private static final class ViewCImpl extends ThorApplication_HiltComponents.ViewC {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final SingletonCImpl singletonCImpl;
        private final ViewCImpl viewCImpl;

        private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl, View viewParam) {
            this.viewCImpl = this;
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            this.activityCImpl = activityCImpl;
        }
    }

    private static final class ActivityCImpl extends ThorApplication_HiltComponents.ActivityC {
        private final ActivityCImpl activityCImpl;
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private final SingletonCImpl singletonCImpl;

        @Override // com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity_GeneratedInjector
        public void injectAddSguPresetActivity(AddSguPresetActivity addSguPresetActivity) {
        }

        @Override // com.thor.app.gui.activities.demo.DemoActivity_GeneratedInjector
        public void injectDemoActivity(DemoActivity demoActivity) {
        }

        @Override // com.thor.app.gui.activities.testers.FirmwareListActivity_GeneratedInjector
        public void injectFirmwareListActivity(FirmwareListActivity firmwareListActivity) {
        }

        @Override // com.thor.app.gui.activities.googleautha.GoogleAuthActivity_GeneratedInjector
        public void injectGoogleAuthActivity(GoogleAuthActivity googleAuthActivity) {
        }

        @Override // com.thor.app.gui.activities.notifications.NotificationsActivity_GeneratedInjector
        public void injectNotificationsActivity(NotificationsActivity notificationsActivity) {
        }

        @Override // com.thor.app.gui.activities.splash.SplashActivity_GeneratedInjector
        public void injectSplashActivity(SplashActivity splashActivity) {
        }

        private ActivityCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
            this.activityCImpl = this;
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
        }

        @Override // com.thor.app.gui.activities.main.MainActivity_GeneratedInjector
        public void injectMainActivity(MainActivity mainActivity) {
            injectMainActivity2(mainActivity);
        }

        @Override // com.thor.app.gui.activities.settings.SettingsActivity_GeneratedInjector
        public void injectSettingsActivity(SettingsActivity settingsActivity) {
            injectSettingsActivity2(settingsActivity);
        }

        @Override // com.thor.app.gui.activities.shop.ShopActivity_GeneratedInjector
        public void injectShopActivity(ShopActivity shopActivity) {
            injectShopActivity2(shopActivity);
        }

        @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity_GeneratedInjector
        public void injectSubscriptionCheckActivity(SubscriptionCheckActivity subscriptionCheckActivity) {
            injectSubscriptionCheckActivity2(subscriptionCheckActivity);
        }

        @Override // com.thor.app.gui.activities.shop.main.SubscriptionsActivity_GeneratedInjector
        public void injectSubscriptionsActivity(SubscriptionsActivity subscriptionsActivity) {
            injectSubscriptionsActivity2(subscriptionsActivity);
        }

        @Override // com.thor.app.gui.activities.updatecheck.UpdateAppActivity_GeneratedInjector
        public void injectUpdateAppActivity(UpdateAppActivity updateAppActivity) {
            injectUpdateAppActivity2(updateAppActivity);
        }

        @Override // dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories.ActivityEntryPoint
        public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
            return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(this.singletonCImpl, this.activityRetainedCImpl));
        }

        @Override // dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.ActivityCreatorEntryPoint
        public Set<String> getViewModelKeys() {
            return ImmutableSet.of(AddSguPresetViewModel_HiltModules_KeyModule_ProvideFactory.provide(), FirmwareListViewModel_HiltModules_KeyModule_ProvideFactory.provide(), FormatProgressViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MainActivityViewModel_HiltModules_KeyModule_ProvideFactory.provide(), MainShopViewModel_HiltModules_KeyModule_ProvideFactory.provide(), NotificationsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SettingsActivityViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SguShopViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SguSoundsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ShopViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SplashActivityViewModel_HiltModules_KeyModule_ProvideFactory.provide(), UpdateViewModel_HiltModules_KeyModule_ProvideFactory.provide());
        }

        @Override // dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.ActivityCreatorEntryPoint
        public ViewModelComponentBuilder getViewModelComponentBuilder() {
            return new ViewModelCBuilder(this.singletonCImpl, this.activityRetainedCImpl);
        }

        @Override // dagger.hilt.android.internal.managers.FragmentComponentManager.FragmentComponentBuilderEntryPoint
        public FragmentComponentBuilder fragmentComponentBuilder() {
            return new FragmentCBuilder(this.singletonCImpl, this.activityRetainedCImpl, this.activityCImpl);
        }

        @Override // dagger.hilt.android.internal.managers.ViewComponentManager.ViewComponentBuilderEntryPoint
        public ViewComponentBuilder viewComponentBuilder() {
            return new ViewCBuilder(this.singletonCImpl, this.activityRetainedCImpl, this.activityCImpl);
        }

        private MainActivity injectMainActivity2(MainActivity instance) {
            SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
            return instance;
        }

        private SettingsActivity injectSettingsActivity2(SettingsActivity instance) {
            SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
            return instance;
        }

        private ShopActivity injectShopActivity2(ShopActivity instance) {
            SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
            return instance;
        }

        private SubscriptionCheckActivity injectSubscriptionCheckActivity2(SubscriptionCheckActivity instance) {
            SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
            return instance;
        }

        private SubscriptionsActivity injectSubscriptionsActivity2(SubscriptionsActivity instance) {
            SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
            SubscriptionsActivity_MembersInjector.injectUsersManager(instance, (UsersManager) this.singletonCImpl.provideUsersManagerProvider.get());
            return instance;
        }

        private UpdateAppActivity injectUpdateAppActivity2(UpdateAppActivity instance) {
            SubscriptionCheckActivity_MembersInjector.injectBillingManager(instance, (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
            return instance;
        }
    }

    private static final class ViewModelCImpl extends ThorApplication_HiltComponents.ViewModelC {
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private Provider<AddSguPresetViewModel> addSguPresetViewModelProvider;
        private Provider<FirmwareListViewModel> firmwareListViewModelProvider;
        private Provider<FormatProgressViewModel> formatProgressViewModelProvider;
        private Provider<MainActivityViewModel> mainActivityViewModelProvider;
        private Provider<MainShopViewModel> mainShopViewModelProvider;
        private Provider<NotificationsViewModel> notificationsViewModelProvider;
        private Provider<SettingsActivityViewModel> settingsActivityViewModelProvider;
        private Provider<SguShopViewModel> sguShopViewModelProvider;
        private Provider<SguSoundsViewModel> sguSoundsViewModelProvider;
        private Provider<ShopViewModel> shopViewModelProvider;
        private final SingletonCImpl singletonCImpl;
        private Provider<SplashActivityViewModel> splashActivityViewModelProvider;
        private Provider<UpdateViewModel> updateViewModelProvider;
        private final ViewModelCImpl viewModelCImpl;

        private ViewModelCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam, ViewModelLifecycle viewModelLifecycleParam) {
            this.viewModelCImpl = this;
            this.singletonCImpl = singletonCImpl;
            this.activityRetainedCImpl = activityRetainedCImpl;
            initialize(savedStateHandleParam, viewModelLifecycleParam);
        }

        private void initialize(final SavedStateHandle savedStateHandleParam, final ViewModelLifecycle viewModelLifecycleParam) {
            this.addSguPresetViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 0);
            this.firmwareListViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 1);
            this.formatProgressViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 2);
            this.mainActivityViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 3);
            this.mainShopViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 4);
            this.notificationsViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 5);
            this.settingsActivityViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 6);
            this.sguShopViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 7);
            this.sguSoundsViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 8);
            this.shopViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 9);
            this.splashActivityViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 10);
            this.updateViewModelProvider = new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, this.viewModelCImpl, 11);
        }

        @Override // dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.ViewModelFactoriesEntryPoint
        public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
            return ImmutableMap.builderWithExpectedSize(12).put("com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel", this.addSguPresetViewModelProvider).put("com.thor.app.gui.activities.testers.FirmwareListViewModel", this.firmwareListViewModelProvider).put("com.thor.app.gui.dialog.FormatProgressViewModel", this.formatProgressViewModelProvider).put("com.thor.app.gui.activities.main.MainActivityViewModel", this.mainActivityViewModelProvider).put("com.thor.app.gui.fragments.shop.main.MainShopViewModel", this.mainShopViewModelProvider).put("com.thor.app.gui.activities.notifications.NotificationsViewModel", this.notificationsViewModelProvider).put("com.thor.app.gui.activities.settings.SettingsActivityViewModel", this.settingsActivityViewModelProvider).put("com.thor.app.gui.fragments.shop.sgu.SguShopViewModel", this.sguShopViewModelProvider).put("com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel", this.sguSoundsViewModelProvider).put("com.thor.app.gui.activities.shop.ShopViewModel", this.shopViewModelProvider).put("com.thor.app.gui.activities.splash.SplashActivityViewModel", this.splashActivityViewModelProvider).put("com.thor.app.databinding.viewmodels.UpdateViewModel", this.updateViewModelProvider).build();
        }

        @Override // dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.ViewModelFactoriesEntryPoint
        public Map<String, Object> getHiltViewModelAssistedMap() {
            return ImmutableMap.of();
        }

        private static final class SwitchingProvider<T> implements Provider<T> {
            private final ActivityRetainedCImpl activityRetainedCImpl;
            private final int id;
            private final SingletonCImpl singletonCImpl;
            private final ViewModelCImpl viewModelCImpl;

            SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, ViewModelCImpl viewModelCImpl, int id) {
                this.singletonCImpl = singletonCImpl;
                this.activityRetainedCImpl = activityRetainedCImpl;
                this.viewModelCImpl = viewModelCImpl;
                this.id = id;
            }

            @Override // javax.inject.Provider
            public T get() {
                switch (this.id) {
                    case 0:
                        return (T) new AddSguPresetViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (BleManager) this.singletonCImpl.provideBleManagerProvider.get(), (ThorDatabase) this.singletonCImpl.provideAppDatabaseProvider.get());
                    case 1:
                        return (T) new FirmwareListViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get());
                    case 2:
                        return (T) new FormatProgressViewModel();
                    case 3:
                        return (T) new MainActivityViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (CurrentVersionDao) this.singletonCImpl.provideCurrentVersionDaoProvider.get());
                    case 4:
                        return (T) new MainShopViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (ThorDatabase) this.singletonCImpl.provideAppDatabaseProvider.get(), (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get());
                    case 5:
                        return (T) new NotificationsViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (Settings) this.singletonCImpl.provideSettingsProvider.get(), (ThorDatabase) this.singletonCImpl.provideAppDatabaseProvider.get(), (NotificationCountHelper) this.singletonCImpl.provideNotificationCountHelperProvider.get());
                    case 6:
                        return (T) new SettingsActivityViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get());
                    case 7:
                        return (T) new SguShopViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (BillingManager) this.singletonCImpl.provideBillingManagerProvider.get(), (BleManager) this.singletonCImpl.provideBleManagerProvider.get());
                    case 8:
                        return (T) new SguSoundsViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule), (UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (ThorDatabase) this.singletonCImpl.provideAppDatabaseProvider.get(), (BleManager) this.singletonCImpl.provideBleManagerProvider.get());
                    case 9:
                        return (T) new ShopViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get());
                    case 10:
                        return (T) new SplashActivityViewModel((CurrentVersionDao) this.singletonCImpl.provideCurrentVersionDaoProvider.get(), (ApiServiceNew) this.singletonCImpl.provideNewApiServiceProvider.get());
                    case 11:
                        return (T) new UpdateViewModel((UsersManager) this.singletonCImpl.provideUsersManagerProvider.get(), (CurrentVersionDao) this.singletonCImpl.provideCurrentVersionDaoProvider.get(), (ApiServiceNew) this.singletonCImpl.provideNewApiServiceProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    default:
                        throw new AssertionError(this.id);
                }
            }
        }
    }

    private static final class ActivityRetainedCImpl extends ThorApplication_HiltComponents.ActivityRetainedC {
        private final ActivityRetainedCImpl activityRetainedCImpl;
        private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;
        private final SingletonCImpl singletonCImpl;

        private ActivityRetainedCImpl(SingletonCImpl singletonCImpl, SavedStateHandleHolder savedStateHandleHolderParam) {
            this.activityRetainedCImpl = this;
            this.singletonCImpl = singletonCImpl;
            initialize(savedStateHandleHolderParam);
        }

        private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
            this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, this.activityRetainedCImpl, 0));
        }

        @Override // dagger.hilt.android.internal.managers.ActivityComponentManager.ActivityComponentBuilderEntryPoint
        public ActivityComponentBuilder activityComponentBuilder() {
            return new ActivityCBuilder(this.singletonCImpl, this.activityRetainedCImpl);
        }

        @Override // dagger.hilt.android.internal.managers.ActivityRetainedComponentManager.ActivityRetainedLifecycleEntryPoint
        public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
            return this.provideActivityRetainedLifecycleProvider.get();
        }

        private static final class SwitchingProvider<T> implements Provider<T> {
            private final ActivityRetainedCImpl activityRetainedCImpl;
            private final int id;
            private final SingletonCImpl singletonCImpl;

            SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl, int id) {
                this.singletonCImpl = singletonCImpl;
                this.activityRetainedCImpl = activityRetainedCImpl;
                this.id = id;
            }

            @Override // javax.inject.Provider
            public T get() {
                if (this.id == 0) {
                    return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();
                }
                throw new AssertionError(this.id);
            }
        }
    }

    private static final class ServiceCImpl extends ThorApplication_HiltComponents.ServiceC {
        private final ServiceCImpl serviceCImpl;
        private final SingletonCImpl singletonCImpl;

        private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
            this.serviceCImpl = this;
            this.singletonCImpl = singletonCImpl;
        }

        @Override // com.thor.app.services.FmcService_GeneratedInjector
        public void injectFmcService(FmcService fmcService) {
            injectFmcService2(fmcService);
        }

        private FmcService injectFmcService2(FmcService instance) {
            FmcService_MembersInjector.injectPushNotificationManager(instance, (PushNotificationManager) this.singletonCImpl.providePushNotificationManagerProvider.get());
            return instance;
        }
    }

    private static final class SingletonCImpl extends ThorApplication_HiltComponents.SingletonC {
        private final ApplicationContextModule applicationContextModule;
        private Provider<ThorDatabase> provideAppDatabaseProvider;
        private Provider<BillingManager> provideBillingManagerProvider;
        private Provider<BleManager> provideBleManagerProvider;
        private Provider<CurrentVersionDao> provideCurrentVersionDaoProvider;
        private Provider<GsonConverterFactory> provideGsonConverterFactoryProvider;
        private Provider<HttpLoggingInterceptor> provideLoggingInterceptorProvider;
        private Provider<ApiServiceNew> provideNewApiServiceProvider;
        private Provider<Retrofit> provideNewRetrofitProvider;
        private Provider<NotificationCountHelper> provideNotificationCountHelperProvider;
        private Provider<OkHttpClient> provideOkHttpClientProvider;
        private Provider<PushNotificationManager> providePushNotificationManagerProvider;
        private Provider<String> provideServerUrlProvider;
        private Provider<Settings> provideSettingsProvider;
        private Provider<UsersManager> provideUsersManagerProvider;
        private Provider<HttpLoggingInterceptor.Logger> providerHttpLoggingInterceptorLoggerProvider;
        private final SingletonCImpl singletonCImpl;

        @Override // com.thor.app.ThorApplication_GeneratedInjector
        public void injectThorApplication(ThorApplication thorApplication) {
        }

        private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
            this.singletonCImpl = this;
            this.applicationContextModule = applicationContextModuleParam;
            initialize(applicationContextModuleParam);
        }

        private void initialize(final ApplicationContextModule applicationContextModuleParam) {
            this.provideBillingManagerProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 0));
            this.provideUsersManagerProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 1));
            this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 2));
            this.provideBleManagerProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 3));
            this.provideCurrentVersionDaoProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 4));
            this.provideSettingsProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 5));
            this.provideNotificationCountHelperProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 6));
            this.provideServerUrlProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 9));
            this.provideGsonConverterFactoryProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 10));
            this.providerHttpLoggingInterceptorLoggerProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 13));
            this.provideLoggingInterceptorProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 12));
            this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 11));
            this.provideNewRetrofitProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 8));
            this.provideNewApiServiceProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 7));
            this.providePushNotificationManagerProvider = DoubleCheck.provider(new SwitchingProvider(this.singletonCImpl, 14));
        }

        @Override // dagger.hilt.android.flags.FragmentGetContextFix.FragmentGetContextFixEntryPoint
        public Set<Boolean> getDisableFragmentGetContextFix() {
            return ImmutableSet.of();
        }

        @Override // dagger.hilt.android.internal.managers.ActivityRetainedComponentManager.ActivityRetainedComponentBuilderEntryPoint
        public ActivityRetainedComponentBuilder retainedComponentBuilder() {
            return new ActivityRetainedCBuilder(this.singletonCImpl);
        }

        @Override // dagger.hilt.android.internal.managers.ServiceComponentManager.ServiceComponentBuilderEntryPoint
        public ServiceComponentBuilder serviceComponentBuilder() {
            return new ServiceCBuilder(this.singletonCImpl);
        }

        private static final class SwitchingProvider<T> implements Provider<T> {
            private final int id;
            private final SingletonCImpl singletonCImpl;

            SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
                this.singletonCImpl = singletonCImpl;
                this.id = id;
            }

            @Override // javax.inject.Provider
            public T get() {
                switch (this.id) {
                    case 0:
                        return (T) AppModule_ProvideBillingManagerFactory.provideBillingManager(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 1:
                        return (T) AppModule_ProvideUsersManagerFactory.provideUsersManager(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 2:
                        return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 3:
                        return (T) AppModule_ProvideBleManagerFactory.provideBleManager(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 4:
                        return (T) DatabaseModule_ProvideCurrentVersionDaoFactory.provideCurrentVersionDao((ThorDatabase) this.singletonCImpl.provideAppDatabaseProvider.get());
                    case 5:
                        return (T) AppModule_ProvideSettingsFactory.provideSettings(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 6:
                        return (T) AppModule_ProvideNotificationCountHelperFactory.provideNotificationCountHelper(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 7:
                        return (T) NetworkModule_ProvideNewApiServiceFactory.provideNewApiService((Retrofit) this.singletonCImpl.provideNewRetrofitProvider.get());
                    case 8:
                        return (T) NetworkModule_ProvideNewRetrofitFactory.provideNewRetrofit((String) this.singletonCImpl.provideServerUrlProvider.get(), (GsonConverterFactory) this.singletonCImpl.provideGsonConverterFactoryProvider.get(), (OkHttpClient) this.singletonCImpl.provideOkHttpClientProvider.get());
                    case 9:
                        return (T) NetworkModule_ProvideServerUrlFactory.provideServerUrl((Settings) this.singletonCImpl.provideSettingsProvider.get());
                    case 10:
                        return (T) NetworkModule_ProvideGsonConverterFactoryFactory.provideGsonConverterFactory();
                    case 11:
                        return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient((HttpLoggingInterceptor) this.singletonCImpl.provideLoggingInterceptorProvider.get());
                    case 12:
                        return (T) NetworkModule_ProvideLoggingInterceptorFactory.provideLoggingInterceptor((HttpLoggingInterceptor.Logger) this.singletonCImpl.providerHttpLoggingInterceptorLoggerProvider.get());
                    case 13:
                        return (T) NetworkModule_ProviderHttpLoggingInterceptorLoggerFactory.providerHttpLoggingInterceptorLogger(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule));
                    case 14:
                        return (T) AppModule_ProvidePushNotificationManagerFactory.providePushNotificationManager(ApplicationContextModule_ProvideContextFactory.provideContext(this.singletonCImpl.applicationContextModule), (NotificationCountHelper) this.singletonCImpl.provideNotificationCountHelperProvider.get());
                    default:
                        throw new AssertionError(this.id);
                }
            }
        }
    }
}
