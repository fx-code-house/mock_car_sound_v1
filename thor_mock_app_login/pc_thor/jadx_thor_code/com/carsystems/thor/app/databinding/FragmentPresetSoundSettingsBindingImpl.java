package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.app.databinding.adapters.MainSoundPackageDataBindingAdapter;
import com.thor.app.gui.widget.ToolbarWidget;
import com.thor.businessmodule.model.MainPresetPackage;

/* loaded from: classes.dex */
public class FragmentPresetSoundSettingsBindingImpl extends FragmentPresetSoundSettingsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar_widget, 10);
        sparseIntArray.put(R.id.text_view_car_name, 11);
        sparseIntArray.put(R.id.nested_scroll_view, 12);
        sparseIntArray.put(R.id.text_view_selected_type, 13);
        sparseIntArray.put(R.id.layout_preset_modes, 14);
        sparseIntArray.put(R.id.image_view_main_volume_left, 15);
        sparseIntArray.put(R.id.image_view_main_volume_right, 16);
        sparseIntArray.put(R.id.text_view_off, 17);
        sparseIntArray.put(R.id.text_view_starter_sound, 18);
        sparseIntArray.put(R.id.text_view_dynamic_start, 19);
        sparseIntArray.put(R.id.idle_text, 20);
        sparseIntArray.put(R.id.image_view_idling_volume_left, 21);
        sparseIntArray.put(R.id.image_view_idling_volume_right, 22);
        sparseIntArray.put(R.id.idle_tone, 23);
        sparseIntArray.put(R.id.idle_tip_btn, 24);
        sparseIntArray.put(R.id.image_view_idle_tone_left, 25);
        sparseIntArray.put(R.id.image_view_idle_tone_right, 26);
        sparseIntArray.put(R.id.image_view_working_tone_left, 27);
        sparseIntArray.put(R.id.image_view_working_tone_right, 28);
        sparseIntArray.put(R.id.image_view_uneven_tone_left, 29);
        sparseIntArray.put(R.id.image_view_uneven_tone_right, 30);
        sparseIntArray.put(R.id.layout_change_number_chamber, 31);
        sparseIntArray.put(R.id.text_view_number_chamber, 32);
        sparseIntArray.put(R.id.image_view_number_chamber, 33);
        sparseIntArray.put(R.id.layout_number_chamber, 34);
        sparseIntArray.put(R.id.text_view_ready, 35);
        sparseIntArray.put(R.id.view_divider_ready, 36);
        sparseIntArray.put(R.id.view_picker_number_chamber, 37);
    }

    public FragmentPresetSoundSettingsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 38, sIncludes, sViewsWithIds));
    }

    private FragmentPresetSoundSettingsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (TextView) bindings[20], (AppCompatImageButton) bindings[24], (TextView) bindings[23], (AppCompatImageView) bindings[1], (ImageView) bindings[25], (ImageView) bindings[26], (ImageView) bindings[21], (ImageView) bindings[22], (ImageView) bindings[15], (ImageView) bindings[16], (ImageView) bindings[33], (ImageView) bindings[29], (ImageView) bindings[30], (ImageView) bindings[27], (ImageView) bindings[28], (ConstraintLayout) bindings[31], (CoordinatorLayout) bindings[0], (ConstraintLayout) bindings[34], (ConstraintLayout) bindings[14], (NestedScrollView) bindings[12], (AppCompatSeekBar) bindings[7], (AppCompatSeekBar) bindings[6], (AppCompatSeekBar) bindings[5], (AppCompatSeekBar) bindings[9], (AppCompatSeekBar) bindings[8], (TextView) bindings[11], (CheckedTextView) bindings[19], (TextView) bindings[32], (CheckedTextView) bindings[17], (TextView) bindings[35], (TextView) bindings[13], (CheckedTextView) bindings[18], (CheckedTextView) bindings[2], (CheckedTextView) bindings[4], (CheckedTextView) bindings[3], (ToolbarWidget) bindings[10], (View) bindings[36], (NumberPicker) bindings[37]);
        this.mDirtyFlags = -1L;
        this.imageHeader.setTag(null);
        this.layoutMain.setTag(null);
        this.seekBarIdleTone.setTag(null);
        this.seekBarIdleVolume.setTag(null);
        this.seekBarMainVolume.setTag(null);
        this.seekBarUnevenTone.setTag(null);
        this.seekBarWorkingTone.setTag(null);
        this.textViewTypeCity.setTag(null);
        this.textViewTypeOwn.setTag(null);
        this.textViewTypeSport.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        if (6 != variableId) {
            return false;
        }
        setPresetPackage((MainPresetPackage) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.FragmentPresetSoundSettingsBinding
    public void setPresetPackage(MainPresetPackage PresetPackage) {
        this.mPresetPackage = PresetPackage;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        MainPresetPackage mainPresetPackage = this.mPresetPackage;
        if ((j & 3) != 0) {
            MainSoundPackageDataBindingAdapter.mainSoundPackageShowCover(this.imageHeader, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.seekBarIdleTone, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.seekBarIdleVolume, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.seekBarMainVolume, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.seekBarUnevenTone, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.seekBarWorkingTone, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.textViewTypeCity, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.textViewTypeOwn, mainPresetPackage);
            MainSoundPackageDataBindingAdapter.setColorByTypePreset(this.textViewTypeSport, mainPresetPackage);
        }
    }
}
