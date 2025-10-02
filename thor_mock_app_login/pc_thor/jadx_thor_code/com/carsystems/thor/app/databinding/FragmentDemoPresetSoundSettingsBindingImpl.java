package com.carsystems.thor.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.R;
import com.thor.businessmodule.model.DemoSoundPackage;
import com.thor.businessmodule.viewmodel.demo.DemoPresetSoundSettingsFragmentViewModel;

/* loaded from: classes.dex */
public class FragmentDemoPresetSoundSettingsBindingImpl extends FragmentDemoPresetSoundSettingsBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final CoordinatorLayout mboundView0;
    private final ImageView mboundView1;
    private final TextView mboundView2;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_header, 9);
        sparseIntArray.put(R.id.view_status_bar, 10);
        sparseIntArray.put(R.id.toolbar_layout, 11);
        sparseIntArray.put(R.id.image_view_back, 12);
        sparseIntArray.put(R.id.nested_scroll_view, 13);
        sparseIntArray.put(R.id.text_view_type_city, 14);
        sparseIntArray.put(R.id.text_view_type_sport, 15);
        sparseIntArray.put(R.id.text_view_type_own, 16);
        sparseIntArray.put(R.id.image_view_main_volume_left, 17);
        sparseIntArray.put(R.id.image_view_main_volume_right, 18);
        sparseIntArray.put(R.id.text_view_off, 19);
        sparseIntArray.put(R.id.text_view_starter_sound, 20);
        sparseIntArray.put(R.id.text_view_dynamic_start, 21);
        sparseIntArray.put(R.id.idle_text, 22);
        sparseIntArray.put(R.id.image_view_idling_volume_left, 23);
        sparseIntArray.put(R.id.image_view_idling_volume_right, 24);
        sparseIntArray.put(R.id.idle_tone, 25);
        sparseIntArray.put(R.id.idle_tip_btn, 26);
        sparseIntArray.put(R.id.image_view_idle_tone_left, 27);
        sparseIntArray.put(R.id.image_view_idle_tone_right, 28);
        sparseIntArray.put(R.id.image_view_working_tone_left, 29);
        sparseIntArray.put(R.id.image_view_working_tone_right, 30);
        sparseIntArray.put(R.id.image_view_uneven_tone_left, 31);
        sparseIntArray.put(R.id.image_view_uneven_tone_right, 32);
        sparseIntArray.put(R.id.layout_change_number_chamber, 33);
        sparseIntArray.put(R.id.image_view_number_chamber, 34);
        sparseIntArray.put(R.id.switch_start_session, 35);
        sparseIntArray.put(R.id.text_view_order, 36);
        sparseIntArray.put(R.id.layout_number_chamber, 37);
        sparseIntArray.put(R.id.text_view_ready, 38);
        sparseIntArray.put(R.id.view_divider_ready, 39);
        sparseIntArray.put(R.id.view_picker_number_chamber, 40);
    }

    public FragmentDemoPresetSoundSettingsBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }

    private FragmentDemoPresetSoundSettingsBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7, (TextView) bindings[22], (AppCompatImageButton) bindings[26], (TextView) bindings[25], (ImageView) bindings[12], (ImageView) bindings[27], (ImageView) bindings[28], (ImageView) bindings[23], (ImageView) bindings[24], (ImageView) bindings[17], (ImageView) bindings[18], (ImageView) bindings[34], (ImageView) bindings[31], (ImageView) bindings[32], (ImageView) bindings[29], (ImageView) bindings[30], (ConstraintLayout) bindings[33], (ConstraintLayout) bindings[9], (ConstraintLayout) bindings[37], (ScrollView) bindings[13], (AppCompatSeekBar) bindings[5], (AppCompatSeekBar) bindings[4], (AppCompatSeekBar) bindings[3], (AppCompatSeekBar) bindings[7], (AppCompatSeekBar) bindings[6], (Switch) bindings[35], (CheckedTextView) bindings[21], (TextView) bindings[8], (CheckedTextView) bindings[19], (TextView) bindings[36], (TextView) bindings[38], (CheckedTextView) bindings[20], (CheckedTextView) bindings[14], (CheckedTextView) bindings[16], (CheckedTextView) bindings[15], (FrameLayout) bindings[11], (View) bindings[39], (NumberPicker) bindings[40], (View) bindings[10]);
        this.mDirtyFlags = -1L;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bindings[0];
        this.mboundView0 = coordinatorLayout;
        coordinatorLayout.setTag(null);
        ImageView imageView = (ImageView) bindings[1];
        this.mboundView1 = imageView;
        imageView.setTag(null);
        TextView textView = (TextView) bindings[2];
        this.mboundView2 = textView;
        textView.setTag(null);
        this.seekBarIdleTone.setTag(null);
        this.seekBarIdleVolume.setTag(null);
        this.seekBarMainVolume.setTag(null);
        this.seekBarUnevenTone.setTag(null);
        this.seekBarWorkingTone.setTag(null);
        this.textViewNumberChamber.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256L;
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
        setModel((DemoPresetSoundSettingsFragmentViewModel) variable);
        return true;
    }

    @Override // com.carsystems.thor.app.databinding.FragmentDemoPresetSoundSettingsBinding
    public void setModel(DemoPresetSoundSettingsFragmentViewModel Model) {
        this.mModel = Model;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(4);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeModelProgressMainSound((ObservableInt) object, fieldId);
            case 1:
                return onChangeModelProgressUnevenTone((ObservableInt) object, fieldId);
            case 2:
                return onChangeModelNumberChamber((ObservableField) object, fieldId);
            case 3:
                return onChangeModelProgressIdleTone((ObservableInt) object, fieldId);
            case 4:
                return onChangeModelSoundPackage((ObservableField) object, fieldId);
            case 5:
                return onChangeModelProgressWorkingTone((ObservableInt) object, fieldId);
            case 6:
                return onChangeModelProgressIdleVolume((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeModelProgressMainSound(ObservableInt ModelProgressMainSound, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeModelProgressUnevenTone(ObservableInt ModelProgressUnevenTone, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeModelNumberChamber(ObservableField<String> ModelNumberChamber, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeModelProgressIdleTone(ObservableInt ModelProgressIdleTone, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeModelSoundPackage(ObservableField<DemoSoundPackage> ModelSoundPackage, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeModelProgressWorkingTone(ObservableInt ModelProgressWorkingTone, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeModelProgressIdleVolume(ObservableInt ModelProgressIdleVolume, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00e7  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.carsystems.thor.app.databinding.FragmentDemoPresetSoundSettingsBindingImpl.executeBindings():void");
    }
}
