package androidx.navigation;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.mapstruct.ap.shaded.freemarker.template.Template;

/* compiled from: NavGraphBuilder.kt */
@NavDestinationDsl
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0017\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0001\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\b\u0010\u0011\u001a\u00020\u0002H\u0016J\u001e\u0010\u0010\u001a\u00020\u000f\"\b\b\u0000\u0010\u0012*\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0001J\r\u0010\u0014\u001a\u00020\u000f*\u00020\u000bH\u0086\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Landroidx/navigation/NavGraphBuilder;", "Landroidx/navigation/NavDestinationBuilder;", "Landroidx/navigation/NavGraph;", "provider", "Landroidx/navigation/NavigatorProvider;", TtmlNode.ATTR_ID, "", "startDestination", "(Landroidx/navigation/NavigatorProvider;II)V", "destinations", "", "Landroidx/navigation/NavDestination;", "getProvider", "()Landroidx/navigation/NavigatorProvider;", "addDestination", "", FirebaseAnalytics.Param.DESTINATION, "build", Template.DEFAULT_NAMESPACE_PREFIX, "navDestination", "unaryPlus", "navigation-common-ktx_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes.dex */
public class NavGraphBuilder extends NavDestinationBuilder<NavGraph> {
    private final List<NavDestination> destinations;
    private final NavigatorProvider provider;
    private int startDestination;

    public final NavigatorProvider getProvider() {
        return this.provider;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NavGraphBuilder(NavigatorProvider provider, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Navigator navigator = provider.getNavigator((Class<Navigator>) NavGraphNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        super(navigator, i);
        this.provider = provider;
        this.startDestination = i2;
        this.destinations = new ArrayList();
    }

    public final <D extends NavDestination> void destination(NavDestinationBuilder<? extends D> navDestination) {
        Intrinsics.checkParameterIsNotNull(navDestination, "navDestination");
        this.destinations.add(navDestination.build());
    }

    public final void unaryPlus(NavDestination unaryPlus) {
        Intrinsics.checkParameterIsNotNull(unaryPlus, "$this$unaryPlus");
        addDestination(unaryPlus);
    }

    public final void addDestination(NavDestination destination) {
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        this.destinations.add(destination);
    }

    @Override // androidx.navigation.NavDestinationBuilder
    public NavGraph build() {
        NavGraph navGraph = (NavGraph) super.build();
        navGraph.addDestinations(this.destinations);
        int i = this.startDestination;
        if (i == 0) {
            throw new IllegalStateException("You must set a startDestination");
        }
        navGraph.setStartDestination(i);
        return navGraph;
    }
}
