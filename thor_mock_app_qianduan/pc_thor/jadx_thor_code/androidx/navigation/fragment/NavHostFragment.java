package androidx.navigation.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

/* loaded from: classes.dex */
public class NavHostFragment extends Fragment implements NavHost {
    private static final String KEY_DEFAULT_NAV_HOST = "android-support-nav:fragment:defaultHost";
    private static final String KEY_GRAPH_ID = "android-support-nav:fragment:graphId";
    private static final String KEY_NAV_CONTROLLER_STATE = "android-support-nav:fragment:navControllerState";
    private static final String KEY_START_DESTINATION_ARGS = "android-support-nav:fragment:startDestinationArgs";
    private boolean mDefaultNavHost;
    private int mGraphId;
    private Boolean mIsPrimaryBeforeOnCreate = null;
    private NavHostController mNavController;
    private View mViewParent;

    public static NavController findNavController(Fragment fragment) {
        for (Fragment parentFragment = fragment; parentFragment != null; parentFragment = parentFragment.getParentFragment()) {
            if (parentFragment instanceof NavHostFragment) {
                return ((NavHostFragment) parentFragment).getNavController();
            }
            Fragment primaryNavigationFragment = parentFragment.getParentFragmentManager().getPrimaryNavigationFragment();
            if (primaryNavigationFragment instanceof NavHostFragment) {
                return ((NavHostFragment) primaryNavigationFragment).getNavController();
            }
        }
        View view = fragment.getView();
        if (view != null) {
            return Navigation.findNavController(view);
        }
        Dialog dialog = fragment instanceof DialogFragment ? ((DialogFragment) fragment).getDialog() : null;
        if (dialog != null && dialog.getWindow() != null) {
            return Navigation.findNavController(dialog.getWindow().getDecorView());
        }
        throw new IllegalStateException("Fragment " + fragment + " does not have a NavController set");
    }

    public static NavHostFragment create(int i) {
        return create(i, null);
    }

    public static NavHostFragment create(int i, Bundle bundle) {
        Bundle bundle2;
        if (i != 0) {
            bundle2 = new Bundle();
            bundle2.putInt(KEY_GRAPH_ID, i);
        } else {
            bundle2 = null;
        }
        if (bundle != null) {
            if (bundle2 == null) {
                bundle2 = new Bundle();
            }
            bundle2.putBundle(KEY_START_DESTINATION_ARGS, bundle);
        }
        NavHostFragment navHostFragment = new NavHostFragment();
        if (bundle2 != null) {
            navHostFragment.setArguments(bundle2);
        }
        return navHostFragment;
    }

    @Override // androidx.navigation.NavHost
    public final NavController getNavController() {
        NavHostController navHostController = this.mNavController;
        if (navHostController != null) {
            return navHostController;
        }
        throw new IllegalStateException("NavController is not available before onCreate()");
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.mDefaultNavHost) {
            getParentFragmentManager().beginTransaction().setPrimaryNavigationFragment(this).commit();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Bundle bundle2;
        NavHostController navHostController = new NavHostController(requireContext());
        this.mNavController = navHostController;
        navHostController.setLifecycleOwner(this);
        this.mNavController.setOnBackPressedDispatcher(requireActivity().getOnBackPressedDispatcher());
        NavHostController navHostController2 = this.mNavController;
        Boolean bool = this.mIsPrimaryBeforeOnCreate;
        navHostController2.enableOnBackPressed(bool != null && bool.booleanValue());
        this.mIsPrimaryBeforeOnCreate = null;
        this.mNavController.setViewModelStore(getViewModelStore());
        onCreateNavController(this.mNavController);
        if (bundle != null) {
            bundle2 = bundle.getBundle(KEY_NAV_CONTROLLER_STATE);
            if (bundle.getBoolean(KEY_DEFAULT_NAV_HOST, false)) {
                this.mDefaultNavHost = true;
                getParentFragmentManager().beginTransaction().setPrimaryNavigationFragment(this).commit();
            }
            this.mGraphId = bundle.getInt(KEY_GRAPH_ID);
        } else {
            bundle2 = null;
        }
        if (bundle2 != null) {
            this.mNavController.restoreState(bundle2);
        }
        int i = this.mGraphId;
        if (i != 0) {
            this.mNavController.setGraph(i);
        } else {
            Bundle arguments = getArguments();
            int i2 = arguments != null ? arguments.getInt(KEY_GRAPH_ID) : 0;
            Bundle bundle3 = arguments != null ? arguments.getBundle(KEY_START_DESTINATION_ARGS) : null;
            if (i2 != 0) {
                this.mNavController.setGraph(i2, bundle3);
            }
        }
        super.onCreate(bundle);
    }

    protected void onCreateNavController(NavController navController) {
        navController.getNavigatorProvider().addNavigator(new DialogFragmentNavigator(requireContext(), getChildFragmentManager()));
        navController.getNavigatorProvider().addNavigator(createFragmentNavigator());
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        ((DialogFragmentNavigator) this.mNavController.getNavigatorProvider().getNavigator(DialogFragmentNavigator.class)).onAttachFragment(fragment);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrimaryNavigationFragmentChanged(boolean z) {
        NavHostController navHostController = this.mNavController;
        if (navHostController != null) {
            navHostController.enableOnBackPressed(z);
        } else {
            this.mIsPrimaryBeforeOnCreate = Boolean.valueOf(z);
        }
    }

    @Deprecated
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new FragmentNavigator(requireContext(), getChildFragmentManager(), getContainerId());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentContainerView fragmentContainerView = new FragmentContainerView(layoutInflater.getContext());
        fragmentContainerView.setId(getContainerId());
        return fragmentContainerView;
    }

    private int getContainerId() {
        int id = getId();
        return (id == 0 || id == -1) ? R.id.nav_host_fragment_container : id;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (!(view instanceof ViewGroup)) {
            throw new IllegalStateException("created host view " + view + " is not a ViewGroup");
        }
        Navigation.setViewNavController(view, this.mNavController);
        if (view.getParent() != null) {
            View view2 = (View) view.getParent();
            this.mViewParent = view2;
            if (view2.getId() == getId()) {
                Navigation.setViewNavController(this.mViewParent, this.mNavController);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(context, attributeSet, bundle);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, androidx.navigation.R.styleable.NavHost);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(androidx.navigation.R.styleable.NavHost_navGraph, 0);
        if (resourceId != 0) {
            this.mGraphId = resourceId;
        }
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.NavHostFragment);
        if (typedArrayObtainStyledAttributes2.getBoolean(R.styleable.NavHostFragment_defaultNavHost, false)) {
            this.mDefaultNavHost = true;
        }
        typedArrayObtainStyledAttributes2.recycle();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Bundle bundleSaveState = this.mNavController.saveState();
        if (bundleSaveState != null) {
            bundle.putBundle(KEY_NAV_CONTROLLER_STATE, bundleSaveState);
        }
        if (this.mDefaultNavHost) {
            bundle.putBoolean(KEY_DEFAULT_NAV_HOST, true);
        }
        int i = this.mGraphId;
        if (i != 0) {
            bundle.putInt(KEY_GRAPH_ID, i);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        View view = this.mViewParent;
        if (view != null && Navigation.findNavController(view) == this.mNavController) {
            Navigation.setViewNavController(this.mViewParent, null);
        }
        this.mViewParent = null;
    }
}
