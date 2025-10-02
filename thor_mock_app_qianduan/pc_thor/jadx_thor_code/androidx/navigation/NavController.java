package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class NavController {
    private static final String KEY_BACK_STACK = "android-support-nav:controller:backStack";
    static final String KEY_DEEP_LINK_EXTRAS = "android-support-nav:controller:deepLinkExtras";
    static final String KEY_DEEP_LINK_HANDLED = "android-support-nav:controller:deepLinkHandled";
    static final String KEY_DEEP_LINK_IDS = "android-support-nav:controller:deepLinkIds";
    public static final String KEY_DEEP_LINK_INTENT = "android-support-nav:controller:deepLinkIntent";
    private static final String KEY_NAVIGATOR_STATE = "android-support-nav:controller:navigatorState";
    private static final String KEY_NAVIGATOR_STATE_NAMES = "android-support-nav:controller:navigatorState:names";
    private static final String TAG = "NavController";
    private Activity mActivity;
    private Parcelable[] mBackStackToRestore;
    private final Context mContext;
    private boolean mDeepLinkHandled;
    NavGraph mGraph;
    private NavInflater mInflater;
    private LifecycleOwner mLifecycleOwner;
    private Bundle mNavigatorStateToRestore;
    private NavControllerViewModel mViewModel;
    final Deque<NavBackStackEntry> mBackStack = new ArrayDeque();
    private NavigatorProvider mNavigatorProvider = new NavigatorProvider();
    private final CopyOnWriteArrayList<OnDestinationChangedListener> mOnDestinationChangedListeners = new CopyOnWriteArrayList<>();
    private final LifecycleObserver mLifecycleObserver = new LifecycleEventObserver() { // from class: androidx.navigation.NavController.1
        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (NavController.this.mGraph != null) {
                Iterator<NavBackStackEntry> it = NavController.this.mBackStack.iterator();
                while (it.hasNext()) {
                    it.next().handleLifecycleEvent(event);
                }
            }
        }
    };
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.navigation.NavController.2
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            NavController.this.popBackStack();
        }
    };
    private boolean mEnableOnBackPressedCallback = true;

    public interface OnDestinationChangedListener {
        void onDestinationChanged(NavController navController, NavDestination navDestination, Bundle bundle);
    }

    public NavController(Context context) {
        this.mContext = context;
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                break;
            }
            if (context instanceof Activity) {
                this.mActivity = (Activity) context;
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        this.mNavigatorProvider.addNavigator(new NavGraphNavigator(this.mNavigatorProvider));
        this.mNavigatorProvider.addNavigator(new ActivityNavigator(this.mContext));
    }

    public Deque<NavBackStackEntry> getBackStack() {
        return this.mBackStack;
    }

    Context getContext() {
        return this.mContext;
    }

    public NavigatorProvider getNavigatorProvider() {
        return this.mNavigatorProvider;
    }

    public void setNavigatorProvider(NavigatorProvider navigatorProvider) {
        if (!this.mBackStack.isEmpty()) {
            throw new IllegalStateException("NavigatorProvider must be set before setGraph call");
        }
        this.mNavigatorProvider = navigatorProvider;
    }

    public void addOnDestinationChangedListener(OnDestinationChangedListener onDestinationChangedListener) {
        if (!this.mBackStack.isEmpty()) {
            NavBackStackEntry navBackStackEntryPeekLast = this.mBackStack.peekLast();
            onDestinationChangedListener.onDestinationChanged(this, navBackStackEntryPeekLast.getDestination(), navBackStackEntryPeekLast.getArguments());
        }
        this.mOnDestinationChangedListeners.add(onDestinationChangedListener);
    }

    public void removeOnDestinationChangedListener(OnDestinationChangedListener onDestinationChangedListener) {
        this.mOnDestinationChangedListeners.remove(onDestinationChangedListener);
    }

    public boolean popBackStack() {
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        return popBackStack(getCurrentDestination().getId(), true);
    }

    public boolean popBackStack(int i, boolean z) {
        return popBackStackInternal(i, z) && dispatchOnDestinationChanged();
    }

    boolean popBackStackInternal(int i, boolean z) {
        boolean z2;
        boolean z3 = false;
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<NavBackStackEntry> itDescendingIterator = this.mBackStack.descendingIterator();
        while (true) {
            if (!itDescendingIterator.hasNext()) {
                z2 = false;
                break;
            }
            NavDestination destination = itDescendingIterator.next().getDestination();
            Navigator navigator = this.mNavigatorProvider.getNavigator(destination.getNavigatorName());
            if (z || destination.getId() != i) {
                arrayList.add(navigator);
            }
            if (destination.getId() == i) {
                z2 = true;
                break;
            }
        }
        if (!z2) {
            Log.i(TAG, "Ignoring popBackStack to destination " + NavDestination.getDisplayName(this.mContext, i) + " as it was not found on the current back stack");
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext() && ((Navigator) it.next()).popBackStack()) {
            NavBackStackEntry navBackStackEntryRemoveLast = this.mBackStack.removeLast();
            navBackStackEntryRemoveLast.setMaxLifecycle(Lifecycle.State.DESTROYED);
            NavControllerViewModel navControllerViewModel = this.mViewModel;
            if (navControllerViewModel != null) {
                navControllerViewModel.clear(navBackStackEntryRemoveLast.mId);
            }
            z3 = true;
        }
        updateOnBackPressedCallbackEnabled();
        return z3;
    }

    public boolean navigateUp() {
        if (getDestinationCountOnBackStack() == 1) {
            NavDestination currentDestination = getCurrentDestination();
            int id = currentDestination.getId();
            for (NavGraph parent = currentDestination.getParent(); parent != null; parent = parent.getParent()) {
                if (parent.getStartDestination() != id) {
                    Bundle bundle = new Bundle();
                    Activity activity = this.mActivity;
                    if (activity != null && activity.getIntent() != null && this.mActivity.getIntent().getData() != null) {
                        bundle.putParcelable(KEY_DEEP_LINK_INTENT, this.mActivity.getIntent());
                        NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLink = this.mGraph.matchDeepLink(new NavDeepLinkRequest(this.mActivity.getIntent()));
                        if (deepLinkMatchMatchDeepLink != null) {
                            bundle.putAll(deepLinkMatchMatchDeepLink.getDestination().addInDefaultArgs(deepLinkMatchMatchDeepLink.getMatchingArgs()));
                        }
                    }
                    new NavDeepLinkBuilder(this).setDestination(parent.getId()).setArguments(bundle).createTaskStackBuilder().startActivities();
                    Activity activity2 = this.mActivity;
                    if (activity2 != null) {
                        activity2.finish();
                    }
                    return true;
                }
                id = parent.getId();
            }
            return false;
        }
        return popBackStack();
    }

    private int getDestinationCountOnBackStack() {
        Iterator<NavBackStackEntry> it = this.mBackStack.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!(it.next().getDestination() instanceof NavGraph)) {
                i++;
            }
        }
        return i;
    }

    private boolean dispatchOnDestinationChanged() {
        NavDestination parent;
        while (!this.mBackStack.isEmpty() && (this.mBackStack.peekLast().getDestination() instanceof NavGraph) && popBackStackInternal(this.mBackStack.peekLast().getDestination().getId(), true)) {
        }
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        NavDestination destination = this.mBackStack.peekLast().getDestination();
        if (destination instanceof FloatingWindow) {
            Iterator<NavBackStackEntry> itDescendingIterator = this.mBackStack.descendingIterator();
            while (itDescendingIterator.hasNext()) {
                parent = itDescendingIterator.next().getDestination();
                if (!(parent instanceof NavGraph) && !(parent instanceof FloatingWindow)) {
                    break;
                }
            }
            parent = null;
        } else {
            parent = null;
        }
        HashMap map = new HashMap();
        Iterator<NavBackStackEntry> itDescendingIterator2 = this.mBackStack.descendingIterator();
        while (itDescendingIterator2.hasNext()) {
            NavBackStackEntry next = itDescendingIterator2.next();
            Lifecycle.State maxLifecycle = next.getMaxLifecycle();
            NavDestination destination2 = next.getDestination();
            if (destination != null && destination2.getId() == destination.getId()) {
                if (maxLifecycle != Lifecycle.State.RESUMED) {
                    map.put(next, Lifecycle.State.RESUMED);
                }
                destination = destination.getParent();
            } else if (parent != null && destination2.getId() == parent.getId()) {
                if (maxLifecycle == Lifecycle.State.RESUMED) {
                    next.setMaxLifecycle(Lifecycle.State.STARTED);
                } else if (maxLifecycle != Lifecycle.State.STARTED) {
                    map.put(next, Lifecycle.State.STARTED);
                }
                parent = parent.getParent();
            } else {
                next.setMaxLifecycle(Lifecycle.State.CREATED);
            }
        }
        for (NavBackStackEntry navBackStackEntry : this.mBackStack) {
            Lifecycle.State state = (Lifecycle.State) map.get(navBackStackEntry);
            if (state != null) {
                navBackStackEntry.setMaxLifecycle(state);
            } else {
                navBackStackEntry.updateState();
            }
        }
        NavBackStackEntry navBackStackEntryPeekLast = this.mBackStack.peekLast();
        Iterator<OnDestinationChangedListener> it = this.mOnDestinationChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onDestinationChanged(this, navBackStackEntryPeekLast.getDestination(), navBackStackEntryPeekLast.getArguments());
        }
        return true;
    }

    public NavInflater getNavInflater() {
        if (this.mInflater == null) {
            this.mInflater = new NavInflater(this.mContext, this.mNavigatorProvider);
        }
        return this.mInflater;
    }

    public void setGraph(int i) {
        setGraph(i, (Bundle) null);
    }

    public void setGraph(int i, Bundle bundle) {
        setGraph(getNavInflater().inflate(i), bundle);
    }

    public void setGraph(NavGraph navGraph) {
        setGraph(navGraph, (Bundle) null);
    }

    public void setGraph(NavGraph navGraph, Bundle bundle) {
        NavGraph navGraph2 = this.mGraph;
        if (navGraph2 != null) {
            popBackStackInternal(navGraph2.getId(), true);
        }
        this.mGraph = navGraph;
        onGraphCreated(bundle);
    }

    private void onGraphCreated(Bundle bundle) {
        Activity activity;
        ArrayList<String> stringArrayList;
        Bundle bundle2 = this.mNavigatorStateToRestore;
        if (bundle2 != null && (stringArrayList = bundle2.getStringArrayList(KEY_NAVIGATOR_STATE_NAMES)) != null) {
            Iterator<String> it = stringArrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                Navigator navigator = this.mNavigatorProvider.getNavigator(next);
                Bundle bundle3 = this.mNavigatorStateToRestore.getBundle(next);
                if (bundle3 != null) {
                    navigator.onRestoreState(bundle3);
                }
            }
        }
        Parcelable[] parcelableArr = this.mBackStackToRestore;
        boolean z = false;
        if (parcelableArr != null) {
            for (Parcelable parcelable : parcelableArr) {
                NavBackStackEntryState navBackStackEntryState = (NavBackStackEntryState) parcelable;
                NavDestination navDestinationFindDestination = findDestination(navBackStackEntryState.getDestinationId());
                if (navDestinationFindDestination == null) {
                    throw new IllegalStateException("Restoring the Navigation back stack failed: destination " + NavDestination.getDisplayName(this.mContext, navBackStackEntryState.getDestinationId()) + " cannot be found from the current destination " + getCurrentDestination());
                }
                Bundle args = navBackStackEntryState.getArgs();
                if (args != null) {
                    args.setClassLoader(this.mContext.getClassLoader());
                }
                this.mBackStack.add(new NavBackStackEntry(this.mContext, navDestinationFindDestination, args, this.mLifecycleOwner, this.mViewModel, navBackStackEntryState.getUUID(), navBackStackEntryState.getSavedState()));
            }
            updateOnBackPressedCallbackEnabled();
            this.mBackStackToRestore = null;
        }
        if (this.mGraph != null && this.mBackStack.isEmpty()) {
            if (!this.mDeepLinkHandled && (activity = this.mActivity) != null && handleDeepLink(activity.getIntent())) {
                z = true;
            }
            if (z) {
                return;
            }
            navigate(this.mGraph, bundle, (NavOptions) null, (Navigator.Extras) null);
            return;
        }
        dispatchOnDestinationChanged();
    }

    public boolean handleDeepLink(Intent intent) {
        NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLink;
        NavGraph navGraph;
        if (intent == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        int[] intArray = extras != null ? extras.getIntArray(KEY_DEEP_LINK_IDS) : null;
        Bundle bundle = new Bundle();
        Bundle bundle2 = extras != null ? extras.getBundle(KEY_DEEP_LINK_EXTRAS) : null;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        if ((intArray == null || intArray.length == 0) && intent.getData() != null && (deepLinkMatchMatchDeepLink = this.mGraph.matchDeepLink(new NavDeepLinkRequest(intent))) != null) {
            NavDestination destination = deepLinkMatchMatchDeepLink.getDestination();
            int[] iArrBuildDeepLinkIds = destination.buildDeepLinkIds();
            bundle.putAll(destination.addInDefaultArgs(deepLinkMatchMatchDeepLink.getMatchingArgs()));
            intArray = iArrBuildDeepLinkIds;
        }
        if (intArray == null || intArray.length == 0) {
            return false;
        }
        String strFindInvalidDestinationDisplayNameInDeepLink = findInvalidDestinationDisplayNameInDeepLink(intArray);
        if (strFindInvalidDestinationDisplayNameInDeepLink != null) {
            Log.i(TAG, "Could not find destination " + strFindInvalidDestinationDisplayNameInDeepLink + " in the navigation graph, ignoring the deep link from " + intent);
            return false;
        }
        bundle.putParcelable(KEY_DEEP_LINK_INTENT, intent);
        int flags = intent.getFlags();
        int i = 268435456 & flags;
        if (i != 0 && (flags & 32768) == 0) {
            intent.addFlags(32768);
            TaskStackBuilder.create(this.mContext).addNextIntentWithParentStack(intent).startActivities();
            Activity activity = this.mActivity;
            if (activity != null) {
                activity.finish();
                this.mActivity.overridePendingTransition(0, 0);
            }
            return true;
        }
        if (i != 0) {
            if (!this.mBackStack.isEmpty()) {
                popBackStackInternal(this.mGraph.getId(), true);
            }
            int i2 = 0;
            while (i2 < intArray.length) {
                int i3 = i2 + 1;
                int i4 = intArray[i2];
                NavDestination navDestinationFindDestination = findDestination(i4);
                if (navDestinationFindDestination == null) {
                    throw new IllegalStateException("Deep Linking failed: destination " + NavDestination.getDisplayName(this.mContext, i4) + " cannot be found from the current destination " + getCurrentDestination());
                }
                navigate(navDestinationFindDestination, bundle, new NavOptions.Builder().setEnterAnim(0).setExitAnim(0).build(), (Navigator.Extras) null);
                i2 = i3;
            }
            return true;
        }
        NavGraph navGraph2 = this.mGraph;
        int i5 = 0;
        while (i5 < intArray.length) {
            int i6 = intArray[i5];
            NavDestination navDestinationFindNode = i5 == 0 ? this.mGraph : navGraph2.findNode(i6);
            if (navDestinationFindNode == null) {
                throw new IllegalStateException("Deep Linking failed: destination " + NavDestination.getDisplayName(this.mContext, i6) + " cannot be found in graph " + navGraph2);
            }
            if (i5 != intArray.length - 1) {
                while (true) {
                    navGraph = (NavGraph) navDestinationFindNode;
                    if (!(navGraph.findNode(navGraph.getStartDestination()) instanceof NavGraph)) {
                        break;
                    }
                    navDestinationFindNode = navGraph.findNode(navGraph.getStartDestination());
                }
                navGraph2 = navGraph;
            } else {
                navigate(navDestinationFindNode, navDestinationFindNode.addInDefaultArgs(bundle), new NavOptions.Builder().setPopUpTo(this.mGraph.getId(), true).setEnterAnim(0).setExitAnim(0).build(), (Navigator.Extras) null);
            }
            i5++;
        }
        this.mDeepLinkHandled = true;
        return true;
    }

    private String findInvalidDestinationDisplayNameInDeepLink(int[] iArr) {
        NavGraph navGraph;
        NavGraph navGraph2 = this.mGraph;
        int i = 0;
        while (true) {
            NavDestination navDestinationFindNode = null;
            if (i >= iArr.length) {
                return null;
            }
            int i2 = iArr[i];
            if (i == 0) {
                if (this.mGraph.getId() == i2) {
                    navDestinationFindNode = this.mGraph;
                }
            } else {
                navDestinationFindNode = navGraph2.findNode(i2);
            }
            if (navDestinationFindNode == null) {
                return NavDestination.getDisplayName(this.mContext, i2);
            }
            if (i != iArr.length - 1) {
                while (true) {
                    navGraph = (NavGraph) navDestinationFindNode;
                    if (!(navGraph.findNode(navGraph.getStartDestination()) instanceof NavGraph)) {
                        break;
                    }
                    navDestinationFindNode = navGraph.findNode(navGraph.getStartDestination());
                }
                navGraph2 = navGraph;
            }
            i++;
        }
    }

    public NavGraph getGraph() {
        NavGraph navGraph = this.mGraph;
        if (navGraph != null) {
            return navGraph;
        }
        throw new IllegalStateException("You must call setGraph() before calling getGraph()");
    }

    public NavDestination getCurrentDestination() {
        NavBackStackEntry currentBackStackEntry = getCurrentBackStackEntry();
        if (currentBackStackEntry != null) {
            return currentBackStackEntry.getDestination();
        }
        return null;
    }

    NavDestination findDestination(int i) {
        NavGraph destination;
        NavGraph parent;
        NavGraph navGraph = this.mGraph;
        if (navGraph == null) {
            return null;
        }
        if (navGraph.getId() == i) {
            return this.mGraph;
        }
        if (this.mBackStack.isEmpty()) {
            destination = this.mGraph;
        } else {
            destination = this.mBackStack.getLast().getDestination();
        }
        if (destination instanceof NavGraph) {
            parent = destination;
        } else {
            parent = destination.getParent();
        }
        return parent.findNode(i);
    }

    public void navigate(int i) {
        navigate(i, (Bundle) null);
    }

    public void navigate(int i, Bundle bundle) {
        navigate(i, bundle, (NavOptions) null);
    }

    public void navigate(int i, Bundle bundle, NavOptions navOptions) {
        navigate(i, bundle, navOptions, (Navigator.Extras) null);
    }

    public void navigate(int i, Bundle bundle, NavOptions navOptions, Navigator.Extras extras) {
        NavDestination destination;
        int destinationId;
        if (this.mBackStack.isEmpty()) {
            destination = this.mGraph;
        } else {
            destination = this.mBackStack.getLast().getDestination();
        }
        if (destination == null) {
            throw new IllegalStateException("no current navigation node");
        }
        NavAction action = destination.getAction(i);
        Bundle bundle2 = null;
        if (action != null) {
            if (navOptions == null) {
                navOptions = action.getNavOptions();
            }
            destinationId = action.getDestinationId();
            Bundle defaultArguments = action.getDefaultArguments();
            if (defaultArguments != null) {
                bundle2 = new Bundle();
                bundle2.putAll(defaultArguments);
            }
        } else {
            destinationId = i;
        }
        if (bundle != null) {
            if (bundle2 == null) {
                bundle2 = new Bundle();
            }
            bundle2.putAll(bundle);
        }
        if (destinationId == 0 && navOptions != null && navOptions.getPopUpTo() != -1) {
            popBackStack(navOptions.getPopUpTo(), navOptions.isPopUpToInclusive());
            return;
        }
        if (destinationId == 0) {
            throw new IllegalArgumentException("Destination id == 0 can only be used in conjunction with a valid navOptions.popUpTo");
        }
        NavDestination navDestinationFindDestination = findDestination(destinationId);
        if (navDestinationFindDestination == null) {
            String displayName = NavDestination.getDisplayName(this.mContext, destinationId);
            if (action != null) {
                throw new IllegalArgumentException("Navigation destination " + displayName + " referenced from action " + NavDestination.getDisplayName(this.mContext, i) + " cannot be found from the current destination " + destination);
            }
            throw new IllegalArgumentException("Navigation action/destination " + displayName + " cannot be found from the current destination " + destination);
        }
        navigate(navDestinationFindDestination, bundle2, navOptions, extras);
    }

    public void navigate(Uri uri) {
        navigate(new NavDeepLinkRequest(uri, null, null));
    }

    public void navigate(Uri uri, NavOptions navOptions) {
        navigate(new NavDeepLinkRequest(uri, null, null), navOptions);
    }

    public void navigate(Uri uri, NavOptions navOptions, Navigator.Extras extras) {
        navigate(new NavDeepLinkRequest(uri, null, null), navOptions, extras);
    }

    public void navigate(NavDeepLinkRequest navDeepLinkRequest) {
        navigate(navDeepLinkRequest, (NavOptions) null);
    }

    public void navigate(NavDeepLinkRequest navDeepLinkRequest, NavOptions navOptions) {
        navigate(navDeepLinkRequest, navOptions, (Navigator.Extras) null);
    }

    public void navigate(NavDeepLinkRequest navDeepLinkRequest, NavOptions navOptions, Navigator.Extras extras) {
        NavDestination.DeepLinkMatch deepLinkMatchMatchDeepLink = this.mGraph.matchDeepLink(navDeepLinkRequest);
        if (deepLinkMatchMatchDeepLink != null) {
            navigate(deepLinkMatchMatchDeepLink.getDestination(), deepLinkMatchMatchDeepLink.getDestination().addInDefaultArgs(deepLinkMatchMatchDeepLink.getMatchingArgs()), navOptions, extras);
            return;
        }
        throw new IllegalArgumentException("Navigation destination that matches request " + navDeepLinkRequest + " cannot be found in the navigation graph " + this.mGraph);
    }

    private void navigate(NavDestination navDestination, Bundle bundle, NavOptions navOptions, Navigator.Extras extras) {
        boolean z = false;
        boolean zPopBackStackInternal = (navOptions == null || navOptions.getPopUpTo() == -1) ? false : popBackStackInternal(navOptions.getPopUpTo(), navOptions.isPopUpToInclusive());
        Navigator navigator = this.mNavigatorProvider.getNavigator(navDestination.getNavigatorName());
        Bundle bundleAddInDefaultArgs = navDestination.addInDefaultArgs(bundle);
        NavDestination navDestinationNavigate = navigator.navigate(navDestination, bundleAddInDefaultArgs, navOptions, extras);
        if (navDestinationNavigate != null) {
            if (!(navDestinationNavigate instanceof FloatingWindow)) {
                while (!this.mBackStack.isEmpty() && (this.mBackStack.peekLast().getDestination() instanceof FloatingWindow) && popBackStackInternal(this.mBackStack.peekLast().getDestination().getId(), true)) {
                }
            }
            ArrayDeque arrayDeque = new ArrayDeque();
            if (navDestination instanceof NavGraph) {
                NavDestination navDestination2 = navDestinationNavigate;
                while (true) {
                    NavGraph parent = navDestination2.getParent();
                    if (parent != null) {
                        arrayDeque.addFirst(new NavBackStackEntry(this.mContext, parent, bundleAddInDefaultArgs, this.mLifecycleOwner, this.mViewModel));
                        if (!this.mBackStack.isEmpty() && this.mBackStack.getLast().getDestination() == parent) {
                            popBackStackInternal(parent.getId(), true);
                        }
                    }
                    if (parent == null || parent == navDestination) {
                        break;
                    } else {
                        navDestination2 = parent;
                    }
                }
            }
            NavDestination destination = arrayDeque.isEmpty() ? navDestinationNavigate : ((NavBackStackEntry) arrayDeque.getFirst()).getDestination();
            while (destination != null && findDestination(destination.getId()) == null) {
                destination = destination.getParent();
                if (destination != null) {
                    arrayDeque.addFirst(new NavBackStackEntry(this.mContext, destination, bundleAddInDefaultArgs, this.mLifecycleOwner, this.mViewModel));
                }
            }
            NavDestination destination2 = arrayDeque.isEmpty() ? navDestinationNavigate : ((NavBackStackEntry) arrayDeque.getLast()).getDestination();
            while (!this.mBackStack.isEmpty() && (this.mBackStack.getLast().getDestination() instanceof NavGraph) && ((NavGraph) this.mBackStack.getLast().getDestination()).findNode(destination2.getId(), false) == null && popBackStackInternal(this.mBackStack.getLast().getDestination().getId(), true)) {
            }
            this.mBackStack.addAll(arrayDeque);
            if (this.mBackStack.isEmpty() || this.mBackStack.getFirst().getDestination() != this.mGraph) {
                this.mBackStack.addFirst(new NavBackStackEntry(this.mContext, this.mGraph, bundleAddInDefaultArgs, this.mLifecycleOwner, this.mViewModel));
            }
            this.mBackStack.add(new NavBackStackEntry(this.mContext, navDestinationNavigate, navDestinationNavigate.addInDefaultArgs(bundleAddInDefaultArgs), this.mLifecycleOwner, this.mViewModel));
        } else if (navOptions != null && navOptions.shouldLaunchSingleTop()) {
            NavBackStackEntry navBackStackEntryPeekLast = this.mBackStack.peekLast();
            if (navBackStackEntryPeekLast != null) {
                navBackStackEntryPeekLast.replaceArguments(bundleAddInDefaultArgs);
            }
            z = true;
        }
        updateOnBackPressedCallbackEnabled();
        if (zPopBackStackInternal || navDestinationNavigate != null || z) {
            dispatchOnDestinationChanged();
        }
    }

    public void navigate(NavDirections navDirections) {
        navigate(navDirections.getActionId(), navDirections.getArguments());
    }

    public void navigate(NavDirections navDirections, NavOptions navOptions) {
        navigate(navDirections.getActionId(), navDirections.getArguments(), navOptions);
    }

    public void navigate(NavDirections navDirections, Navigator.Extras extras) {
        navigate(navDirections.getActionId(), navDirections.getArguments(), (NavOptions) null, extras);
    }

    public NavDeepLinkBuilder createDeepLink() {
        return new NavDeepLinkBuilder(this);
    }

    public Bundle saveState() {
        Bundle bundle;
        ArrayList<String> arrayList = new ArrayList<>();
        Bundle bundle2 = new Bundle();
        for (Map.Entry<String, Navigator<? extends NavDestination>> entry : this.mNavigatorProvider.getNavigators().entrySet()) {
            String key = entry.getKey();
            Bundle bundleOnSaveState = entry.getValue().onSaveState();
            if (bundleOnSaveState != null) {
                arrayList.add(key);
                bundle2.putBundle(key, bundleOnSaveState);
            }
        }
        if (arrayList.isEmpty()) {
            bundle = null;
        } else {
            bundle = new Bundle();
            bundle2.putStringArrayList(KEY_NAVIGATOR_STATE_NAMES, arrayList);
            bundle.putBundle(KEY_NAVIGATOR_STATE, bundle2);
        }
        if (!this.mBackStack.isEmpty()) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            Parcelable[] parcelableArr = new Parcelable[this.mBackStack.size()];
            Iterator<NavBackStackEntry> it = this.mBackStack.iterator();
            int i = 0;
            while (it.hasNext()) {
                parcelableArr[i] = new NavBackStackEntryState(it.next());
                i++;
            }
            bundle.putParcelableArray(KEY_BACK_STACK, parcelableArr);
        }
        if (this.mDeepLinkHandled) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(KEY_DEEP_LINK_HANDLED, this.mDeepLinkHandled);
        }
        return bundle;
    }

    public void restoreState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        bundle.setClassLoader(this.mContext.getClassLoader());
        this.mNavigatorStateToRestore = bundle.getBundle(KEY_NAVIGATOR_STATE);
        this.mBackStackToRestore = bundle.getParcelableArray(KEY_BACK_STACK);
        this.mDeepLinkHandled = bundle.getBoolean(KEY_DEEP_LINK_HANDLED);
    }

    void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this.mLifecycleObserver);
    }

    void setOnBackPressedDispatcher(OnBackPressedDispatcher onBackPressedDispatcher) {
        if (this.mLifecycleOwner == null) {
            throw new IllegalStateException("You must call setLifecycleOwner() before calling setOnBackPressedDispatcher()");
        }
        this.mOnBackPressedCallback.remove();
        onBackPressedDispatcher.addCallback(this.mLifecycleOwner, this.mOnBackPressedCallback);
    }

    void enableOnBackPressed(boolean z) {
        this.mEnableOnBackPressedCallback = z;
        updateOnBackPressedCallbackEnabled();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateOnBackPressedCallbackEnabled() {
        /*
            r3 = this;
            androidx.activity.OnBackPressedCallback r0 = r3.mOnBackPressedCallback
            boolean r1 = r3.mEnableOnBackPressedCallback
            if (r1 == 0) goto Le
            int r1 = r3.getDestinationCountOnBackStack()
            r2 = 1
            if (r1 <= r2) goto Le
            goto Lf
        Le:
            r2 = 0
        Lf:
            r0.setEnabled(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.updateOnBackPressedCallbackEnabled():void");
    }

    void setViewModelStore(ViewModelStore viewModelStore) {
        if (!this.mBackStack.isEmpty()) {
            throw new IllegalStateException("ViewModelStore should be set before setGraph call");
        }
        this.mViewModel = NavControllerViewModel.getInstance(viewModelStore);
    }

    public ViewModelStoreOwner getViewModelStoreOwner(int i) {
        if (this.mViewModel == null) {
            throw new IllegalStateException("You must call setViewModelStore() before calling getViewModelStoreOwner().");
        }
        NavBackStackEntry backStackEntry = getBackStackEntry(i);
        if (backStackEntry.getDestination() instanceof NavGraph) {
            return backStackEntry;
        }
        throw new IllegalArgumentException("No NavGraph with ID " + i + " is on the NavController's back stack");
    }

    public NavBackStackEntry getBackStackEntry(int i) {
        NavBackStackEntry next;
        Iterator<NavBackStackEntry> itDescendingIterator = this.mBackStack.descendingIterator();
        while (true) {
            if (!itDescendingIterator.hasNext()) {
                next = null;
                break;
            }
            next = itDescendingIterator.next();
            if (next.getDestination().getId() == i) {
                break;
            }
        }
        if (next != null) {
            return next;
        }
        throw new IllegalArgumentException("No destination with ID " + i + " is on the NavController's back stack. The current destination is " + getCurrentDestination());
    }

    public NavBackStackEntry getCurrentBackStackEntry() {
        if (this.mBackStack.isEmpty()) {
            return null;
        }
        return this.mBackStack.getLast();
    }

    public NavBackStackEntry getPreviousBackStackEntry() {
        Iterator<NavBackStackEntry> itDescendingIterator = this.mBackStack.descendingIterator();
        if (itDescendingIterator.hasNext()) {
            itDescendingIterator.next();
        }
        while (itDescendingIterator.hasNext()) {
            NavBackStackEntry next = itDescendingIterator.next();
            if (!(next.getDestination() instanceof NavGraph)) {
                return next;
            }
        }
        return null;
    }
}
