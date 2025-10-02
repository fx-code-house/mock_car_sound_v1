package com.thor.app;

import com.thor.app.databinding.viewmodels.UpdateViewModel_HiltModules;
import com.thor.app.di.AppModule;
import com.thor.app.di.DatabaseModule;
import com.thor.app.di.NetworkModule;
import com.thor.app.gui.activities.demo.DemoActivity_GeneratedInjector;
import com.thor.app.gui.activities.googleautha.GoogleAuthActivity_GeneratedInjector;
import com.thor.app.gui.activities.main.MainActivityViewModel_HiltModules;
import com.thor.app.gui.activities.main.MainActivity_GeneratedInjector;
import com.thor.app.gui.activities.notifications.NotificationsActivity_GeneratedInjector;
import com.thor.app.gui.activities.notifications.NotificationsViewModel_HiltModules;
import com.thor.app.gui.activities.preset.sgu.AddSguPresetActivity_GeneratedInjector;
import com.thor.app.gui.activities.preset.sgu.AddSguPresetViewModel_HiltModules;
import com.thor.app.gui.activities.settings.SettingsActivityViewModel_HiltModules;
import com.thor.app.gui.activities.settings.SettingsActivity_GeneratedInjector;
import com.thor.app.gui.activities.shop.ShopActivity_GeneratedInjector;
import com.thor.app.gui.activities.shop.ShopViewModel_HiltModules;
import com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity_GeneratedInjector;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity_GeneratedInjector;
import com.thor.app.gui.activities.splash.SplashActivityViewModel_HiltModules;
import com.thor.app.gui.activities.splash.SplashActivity_GeneratedInjector;
import com.thor.app.gui.activities.testers.FirmwareListActivity_GeneratedInjector;
import com.thor.app.gui.activities.testers.FirmwareListViewModel_HiltModules;
import com.thor.app.gui.activities.updatecheck.UpdateAppActivity_GeneratedInjector;
import com.thor.app.gui.dialog.FormatProgressViewModel_HiltModules;
import com.thor.app.gui.fragments.notification.NotificationOverviewFragment_GeneratedInjector;
import com.thor.app.gui.fragments.notification.NotificationsFragment_GeneratedInjector;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsFragment_GeneratedInjector;
import com.thor.app.gui.fragments.presets.sgu.SguSoundsViewModel_HiltModules;
import com.thor.app.gui.fragments.shop.main.MainShopFragment_GeneratedInjector;
import com.thor.app.gui.fragments.shop.main.MainShopViewModel_HiltModules;
import com.thor.app.gui.fragments.shop.sgu.SguShopFragment_GeneratedInjector;
import com.thor.app.gui.fragments.shop.sgu.SguShopViewModel_HiltModules;
import com.thor.app.services.FmcService_GeneratedInjector;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.components.ViewComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.components.ViewWithFragmentComponent;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_DefaultViewModelFactories_ActivityModule;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ViewModelModule;
import dagger.hilt.android.internal.managers.ActivityComponentManager;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import dagger.hilt.android.internal.managers.HiltWrapper_SavedStateHandleModule;
import dagger.hilt.android.internal.managers.ServiceComponentManager;
import dagger.hilt.android.internal.managers.ViewComponentManager;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import javax.inject.Singleton;

/* loaded from: classes.dex */
public final class ThorApplication_HiltComponents {

    @Subcomponent(modules = {HiltWrapper_ActivityModule.class, HiltWrapper_DefaultViewModelFactories_ActivityModule.class, FragmentCBuilderModule.class, ViewCBuilderModule.class})
    public static abstract class ActivityC implements DemoActivity_GeneratedInjector, GoogleAuthActivity_GeneratedInjector, MainActivity_GeneratedInjector, NotificationsActivity_GeneratedInjector, AddSguPresetActivity_GeneratedInjector, SettingsActivity_GeneratedInjector, ShopActivity_GeneratedInjector, SubscriptionCheckActivity_GeneratedInjector, SubscriptionsActivity_GeneratedInjector, SplashActivity_GeneratedInjector, FirmwareListActivity_GeneratedInjector, UpdateAppActivity_GeneratedInjector, ActivityComponent, DefaultViewModelFactories.ActivityEntryPoint, HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint, FragmentComponentManager.FragmentComponentBuilderEntryPoint, ViewComponentManager.ViewComponentBuilderEntryPoint, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends ActivityComponentBuilder {
        }
    }

    @Module(subcomponents = {ActivityC.class})
    interface ActivityCBuilderModule {
        @Binds
        ActivityComponentBuilder bind(ActivityC.Builder builder);
    }

    @Subcomponent(modules = {AddSguPresetViewModel_HiltModules.KeyModule.class, FirmwareListViewModel_HiltModules.KeyModule.class, FormatProgressViewModel_HiltModules.KeyModule.class, HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class, HiltWrapper_SavedStateHandleModule.class, MainActivityViewModel_HiltModules.KeyModule.class, MainShopViewModel_HiltModules.KeyModule.class, NotificationsViewModel_HiltModules.KeyModule.class, SettingsActivityViewModel_HiltModules.KeyModule.class, SguShopViewModel_HiltModules.KeyModule.class, SguSoundsViewModel_HiltModules.KeyModule.class, ShopViewModel_HiltModules.KeyModule.class, SplashActivityViewModel_HiltModules.KeyModule.class, ActivityCBuilderModule.class, ViewModelCBuilderModule.class, UpdateViewModel_HiltModules.KeyModule.class})
    public static abstract class ActivityRetainedC implements ActivityRetainedComponent, ActivityComponentManager.ActivityComponentBuilderEntryPoint, HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends ActivityRetainedComponentBuilder {
        }
    }

    @Module(subcomponents = {ActivityRetainedC.class})
    interface ActivityRetainedCBuilderModule {
        @Binds
        ActivityRetainedComponentBuilder bind(ActivityRetainedC.Builder builder);
    }

    @Subcomponent(modules = {ViewWithFragmentCBuilderModule.class})
    public static abstract class FragmentC implements NotificationOverviewFragment_GeneratedInjector, NotificationsFragment_GeneratedInjector, SguSoundsFragment_GeneratedInjector, MainShopFragment_GeneratedInjector, SguShopFragment_GeneratedInjector, FragmentComponent, DefaultViewModelFactories.FragmentEntryPoint, ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends FragmentComponentBuilder {
        }
    }

    @Module(subcomponents = {FragmentC.class})
    interface FragmentCBuilderModule {
        @Binds
        FragmentComponentBuilder bind(FragmentC.Builder builder);
    }

    @Subcomponent
    public static abstract class ServiceC implements FmcService_GeneratedInjector, ServiceComponent, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends ServiceComponentBuilder {
        }
    }

    @Module(subcomponents = {ServiceC.class})
    interface ServiceCBuilderModule {
        @Binds
        ServiceComponentBuilder bind(ServiceC.Builder builder);
    }

    @Component(modules = {AppModule.class, ApplicationContextModule.class, DatabaseModule.class, HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class, NetworkModule.class, ActivityRetainedCBuilderModule.class, ServiceCBuilderModule.class})
    @Singleton
    public static abstract class SingletonC implements ThorApplication_GeneratedInjector, FragmentGetContextFix.FragmentGetContextFixEntryPoint, HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint, ServiceComponentManager.ServiceComponentBuilderEntryPoint, SingletonComponent, GeneratedComponent {
    }

    @Subcomponent
    public static abstract class ViewC implements ViewComponent, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends ViewComponentBuilder {
        }
    }

    @Module(subcomponents = {ViewC.class})
    interface ViewCBuilderModule {
        @Binds
        ViewComponentBuilder bind(ViewC.Builder builder);
    }

    @Subcomponent(modules = {AddSguPresetViewModel_HiltModules.BindsModule.class, FirmwareListViewModel_HiltModules.BindsModule.class, FormatProgressViewModel_HiltModules.BindsModule.class, HiltWrapper_HiltViewModelFactory_ViewModelModule.class, MainActivityViewModel_HiltModules.BindsModule.class, MainShopViewModel_HiltModules.BindsModule.class, NotificationsViewModel_HiltModules.BindsModule.class, SettingsActivityViewModel_HiltModules.BindsModule.class, SguShopViewModel_HiltModules.BindsModule.class, SguSoundsViewModel_HiltModules.BindsModule.class, ShopViewModel_HiltModules.BindsModule.class, SplashActivityViewModel_HiltModules.BindsModule.class, UpdateViewModel_HiltModules.BindsModule.class})
    public static abstract class ViewModelC implements ViewModelComponent, HiltViewModelFactory.ViewModelFactoriesEntryPoint, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends ViewModelComponentBuilder {
        }
    }

    @Module(subcomponents = {ViewModelC.class})
    interface ViewModelCBuilderModule {
        @Binds
        ViewModelComponentBuilder bind(ViewModelC.Builder builder);
    }

    @Subcomponent
    public static abstract class ViewWithFragmentC implements ViewWithFragmentComponent, GeneratedComponent {

        @Subcomponent.Builder
        interface Builder extends ViewWithFragmentComponentBuilder {
        }
    }

    @Module(subcomponents = {ViewWithFragmentC.class})
    interface ViewWithFragmentCBuilderModule {
        @Binds
        ViewWithFragmentComponentBuilder bind(ViewWithFragmentC.Builder builder);
    }

    private ThorApplication_HiltComponents() {
    }
}
