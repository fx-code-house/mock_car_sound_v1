package androidx.navigation;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.navigation.Navigator;

@Navigator.Name(NotificationCompat.CATEGORY_NAVIGATION)
/* loaded from: classes.dex */
public class NavGraphNavigator extends Navigator<NavGraph> {
    private final NavigatorProvider mNavigatorProvider;

    @Override // androidx.navigation.Navigator
    public boolean popBackStack() {
        return true;
    }

    public NavGraphNavigator(NavigatorProvider navigatorProvider) {
        this.mNavigatorProvider = navigatorProvider;
    }

    @Override // androidx.navigation.Navigator
    public NavGraph createDestination() {
        return new NavGraph(this);
    }

    @Override // androidx.navigation.Navigator
    public NavDestination navigate(NavGraph navGraph, Bundle bundle, NavOptions navOptions, Navigator.Extras extras) {
        int startDestination = navGraph.getStartDestination();
        if (startDestination == 0) {
            throw new IllegalStateException("no start destination defined via app:startDestination for " + navGraph.getDisplayName());
        }
        NavDestination navDestinationFindNode = navGraph.findNode(startDestination, false);
        if (navDestinationFindNode == null) {
            throw new IllegalArgumentException("navigation destination " + navGraph.getStartDestDisplayName() + " is not a direct child of this NavGraph");
        }
        return this.mNavigatorProvider.getNavigator(navDestinationFindNode.getNavigatorName()).navigate(navDestinationFindNode, navDestinationFindNode.addInDefaultArgs(bundle), navOptions, extras);
    }
}
