package androidx.navigation.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfigurationKt;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CollapsingToolbarLayout.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"setupWithNavController", "", "Lcom/google/android/material/appbar/CollapsingToolbarLayout;", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "navController", "Landroidx/navigation/NavController;", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "configuration", "Landroidx/navigation/ui/AppBarConfiguration;", "navigation-ui-ktx_release"}, k = 2, mv = {1, 1, 16})
/* loaded from: classes.dex */
public final class CollapsingToolbarLayoutKt {
    public static final void setupWithNavController(CollapsingToolbarLayout setupWithNavController, Toolbar toolbar, NavController navController, DrawerLayout drawerLayout) {
        Intrinsics.checkParameterIsNotNull(setupWithNavController, "$this$setupWithNavController");
        Intrinsics.checkParameterIsNotNull(toolbar, "toolbar");
        Intrinsics.checkParameterIsNotNull(navController, "navController");
        NavGraph graph = navController.getGraph();
        Intrinsics.checkExpressionValueIsNotNull(graph, "navController.graph");
        AppBarConfigurationKt.AnonymousClass1 anonymousClass1 = AppBarConfigurationKt.AnonymousClass1.INSTANCE;
        AppBarConfiguration.Builder openableLayout = new AppBarConfiguration.Builder(graph).setOpenableLayout(drawerLayout);
        Object appBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0 = anonymousClass1;
        if (anonymousClass1 != null) {
            appBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0 = new AppBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0(anonymousClass1);
        }
        AppBarConfiguration appBarConfigurationBuild = openableLayout.setFallbackOnNavigateUpListener((AppBarConfiguration.OnNavigateUpListener) appBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0).build();
        Intrinsics.checkExpressionValueIsNotNull(appBarConfigurationBuild, "AppBarConfiguration.Buil…eUpListener)\n    .build()");
        NavigationUI.setupWithNavController(setupWithNavController, toolbar, navController, appBarConfigurationBuild);
    }

    public static /* synthetic */ void setupWithNavController$default(CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, NavController navController, AppBarConfiguration appBarConfiguration, int i, Object obj) {
        if ((i & 4) != 0) {
            NavGraph graph = navController.getGraph();
            Intrinsics.checkExpressionValueIsNotNull(graph, "navController.graph");
            AppBarConfigurationKt.AnonymousClass1 anonymousClass1 = AppBarConfigurationKt.AnonymousClass1.INSTANCE;
            AppBarConfiguration.Builder openableLayout = new AppBarConfiguration.Builder(graph).setOpenableLayout(null);
            Object appBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0 = anonymousClass1;
            if (anonymousClass1 != null) {
                appBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0 = new AppBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0(anonymousClass1);
            }
            appBarConfiguration = openableLayout.setFallbackOnNavigateUpListener((AppBarConfiguration.OnNavigateUpListener) appBarConfigurationKt$sam$i$androidx_navigation_ui_AppBarConfiguration_OnNavigateUpListener$0).build();
            Intrinsics.checkExpressionValueIsNotNull(appBarConfiguration, "AppBarConfiguration.Buil…eUpListener)\n    .build()");
        }
        setupWithNavController(collapsingToolbarLayout, toolbar, navController, appBarConfiguration);
    }

    public static final void setupWithNavController(CollapsingToolbarLayout setupWithNavController, Toolbar toolbar, NavController navController, AppBarConfiguration configuration) {
        Intrinsics.checkParameterIsNotNull(setupWithNavController, "$this$setupWithNavController");
        Intrinsics.checkParameterIsNotNull(toolbar, "toolbar");
        Intrinsics.checkParameterIsNotNull(navController, "navController");
        Intrinsics.checkParameterIsNotNull(configuration, "configuration");
        NavigationUI.setupWithNavController(setupWithNavController, toolbar, navController, configuration);
    }
}
