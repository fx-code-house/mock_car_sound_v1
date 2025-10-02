package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.LifecycleOwner;
import com.carsystems.thor.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.thor.app.databinding.adapters.ViewBindingAdapter;
import com.thor.app.gui.activities.main.MainActivityViewModel;
import com.thor.app.gui.widget.ShopModeSwitchView;
import com.thor.app.gui.widget.ToolbarWidget;

/* loaded from: classes.dex */
public class ActivityMainBindingImpl extends ActivityMainBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final CoordinatorLayout mboundView0;
    private final TextView mboundView3;
    private final TextView mboundView5;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(19);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(7, new String[]{"layout_main_menu"}, new int[]{8}, new int[]{R.layout.layout_main_menu});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.drawer_layout, 9);
        sparseIntArray.put(R.id.toolbar_widget, 10);
        sparseIntArray.put(R.id.switch_shop_mode, 11);
        sparseIntArray.put(R.id.fragment_container, 12);
        sparseIntArray.put(R.id.drive_select_switch, 13);
        sparseIntArray.put(R.id.icon_sgu_volume, 14);
        sparseIntArray.put(R.id.seek_bar_sgu_volume, 15);
        sparseIntArray.put(R.id.icon_repeat_sgu_config, 16);
        sparseIntArray.put(R.id.icon_close_sgu_congig, 17);
        sparseIntArray.put(R.id.progressBar, 18);
    }

    public ActivityMainBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }

    private ActivityMainBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (FrameLayout) bindings[4], (ConstraintLayout) bindings[1], (DrawerLayout) bindings[9], (SwitchMaterial) bindings[13], (FloatingActionButton) bindings[2], (FragmentContainerView) bindings[12], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[14], (LayoutMainMenuBinding) bindings[8], (NavigationView) bindings[7], (FrameLayout) bindings[18], (AppCompatSeekBar) bindings[15], (ConstraintLayout) bindings[6], (ShopModeSwitchView) bindings[11], (ToolbarWidget) bindings[10]);
        this.mDirtyFlags = -1L;
        this.buttonAddSound.setTag(null);
        this.buttonsContainer.setTag(null);
        this.floatingButtonAddSound.setTag(null);
        setContainedBinding(this.layoutMainMenu);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bindings[0];
        this.mboundView0 = coordinatorLayout;
        coordinatorLayout.setTag(null);
        TextView textView = (TextView) bindings[3];
        this.mboundView3 = textView;
        textView.setTag(null);
        TextView textView2 = (TextView) bindings[5];
        this.mboundView5 = textView2;
        textView2.setTag(null);
        this.navigationView.setTag(null);
        this.sguConfigLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
        }
        this.layoutMainMenu.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.layoutMainMenu.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (4 != variableId) {
            return false;
        }
        setModel((MainActivityViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.ActivityMainBinding
    public void setModel(MainActivityViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.layoutMainMenu.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsSguSoundConfig((ObservableBoolean) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeLayoutMainMenu((LayoutMainMenuBinding) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeModelMoreInfo((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsSguSoundConfig(ObservableBoolean ModelIsSguSoundConfig, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeLayoutMainMenu(LayoutMainMenuBinding LayoutMainMenu, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelMoreInfo(ObservableBoolean ModelMoreInfo, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean z;
        boolean z2;
        int i;
        long j2;
        long j3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        MainActivityViewModel mainActivityViewModel = this.mModel;
        int i2 = 0;
        if ((29 & j) != 0) {
            if ((j & 25) != 0) {
                ObservableBoolean isSguSoundConfig = mainActivityViewModel != null ? mainActivityViewModel.getIsSguSoundConfig() : null;
                updateRegistration(0, isSguSoundConfig);
                z = isSguSoundConfig != null ? isSguSoundConfig.get() : false;
                z2 = !z;
            } else {
                z = false;
                z2 = false;
            }
            long j4 = j & 28;
            if (j4 != 0) {
                ObservableBoolean moreInfo = mainActivityViewModel != null ? mainActivityViewModel.getMoreInfo() : null;
                updateRegistration(2, moreInfo);
                boolean z3 = moreInfo != null ? moreInfo.get() : false;
                if (j4 != 0) {
                    if (z3) {
                        j2 = j | 64;
                        j3 = 256;
                    } else {
                        j2 = j | 32;
                        j3 = 128;
                    }
                    j = j2 | j3;
                }
                i = z3 ? 8 : 0;
                if (!z3) {
                    i2 = 8;
                }
            } else {
                i = 0;
            }
        } else {
            z = false;
            z2 = false;
            i = 0;
        }
        if ((28 & j) != 0) {
            this.buttonAddSound.setVisibility(i2);
            this.floatingButtonAddSound.setVisibility(i);
            this.mboundView3.setVisibility(i2);
        }
        if ((25 & j) != 0) {
            ViewBindingAdapter.bindIsVisible(this.buttonsContainer, z2);
            ViewBindingAdapter.bindIsVisible(this.sguConfigLayout, z);
        }
        if ((j & 16) != 0) {
            TextView textView = this.mboundView5;
            TextViewBindingAdapter.setDrawableLeft(textView, AppCompatResources.getDrawable(textView.getContext(), R.drawable.ic_plus_white_24dp));
        }
        executeBindingsOn(this.layoutMainMenu);
    }
}
