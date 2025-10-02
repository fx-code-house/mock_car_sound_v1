package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.MainSoundPackageDataBindingAdapter;
import com.thor.app.gui.activities.main.MainActivityViewModel;

/* loaded from: classes.dex */
public class FragmentMainSoundsBindingImpl extends FragmentMainSoundsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final FrameLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.swipe_container, 2);
        sparseIntArray.put(R.id.nested_scroll_view, 3);
        sparseIntArray.put(R.id.recycler_view, 4);
    }

    public FragmentMainSoundsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private FragmentMainSoundsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (NestedScrollView) bindings[3], (RecyclerView) bindings[4], (SwipeRefreshLayout) bindings[2], (TextView) bindings[1]);
        this.mDirtyFlags = -1L;
        FrameLayout frameLayout = (FrameLayout) bindings[0];
        this.mboundView0 = frameLayout;
        frameLayout.setTag(null);
        this.textViewNoDevices.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
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

    @Override // com.carsystems.thor.app.databinding.FragmentMainSoundsBinding
    public void setModel(MainActivityViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeModelIsBleConnected((ObservableBoolean) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeModelIsInstalledPresets((ObservableBoolean) object, fieldId);
    }

    private boolean onChangeModelIsBleConnected(ObservableBoolean ModelIsBleConnected, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelIsInstalledPresets(ObservableBoolean ModelIsInstalledPresets, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        boolean z;
        ObservableBoolean isBleConnected;
        ObservableBoolean isInstalledPresets;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        MainActivityViewModel mainActivityViewModel = this.mModel;
        long j2 = j & 15;
        if (j2 != 0) {
            if (mainActivityViewModel != null) {
                isBleConnected = mainActivityViewModel.getIsBleConnected();
                isInstalledPresets = mainActivityViewModel.getIsInstalledPresets();
            } else {
                isBleConnected = null;
                isInstalledPresets = null;
            }
            updateRegistration(0, isBleConnected);
            updateRegistration(1, isInstalledPresets);
            boolean z2 = isBleConnected != null ? isBleConnected.get() : false;
            z = isInstalledPresets != null ? isInstalledPresets.get() : false;
            z = z2;
        } else {
            z = false;
        }
        if (j2 != 0) {
            MainSoundPackageDataBindingAdapter.showPlaceholder(this.textViewNoDevices, z, z);
        }
    }
}
