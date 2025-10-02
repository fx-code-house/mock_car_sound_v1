package com.thor.businessmodule.viewmodel.demo;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.exifinterface.media.ExifInterface;
import com.thor.businessmodule.model.DemoSoundPackage;
import kotlin.Metadata;

/* compiled from: DemoPresetSoundSettingsFragmentViewModel.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0010\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR\u0011\u0010\u0012\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000bR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0007¨\u0006\u0017"}, d2 = {"Lcom/thor/businessmodule/viewmodel/demo/DemoPresetSoundSettingsFragmentViewModel;", "", "()V", "numberChamber", "Landroidx/databinding/ObservableField;", "", "getNumberChamber", "()Landroidx/databinding/ObservableField;", "progressIdleTone", "Landroidx/databinding/ObservableInt;", "getProgressIdleTone", "()Landroidx/databinding/ObservableInt;", "progressIdleVolume", "getProgressIdleVolume", "progressMainSound", "getProgressMainSound", "progressUnevenTone", "getProgressUnevenTone", "progressWorkingTone", "getProgressWorkingTone", "soundPackage", "Lcom/thor/businessmodule/model/DemoSoundPackage;", "getSoundPackage", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoPresetSoundSettingsFragmentViewModel {
    private final ObservableInt progressMainSound = new ObservableInt(0);
    private final ObservableInt progressIdleTone = new ObservableInt(0);
    private final ObservableInt progressIdleVolume = new ObservableInt(0);
    private final ObservableInt progressWorkingTone = new ObservableInt(0);
    private final ObservableInt progressUnevenTone = new ObservableInt(0);
    private final ObservableField<String> numberChamber = new ObservableField<>(ExifInterface.GPS_MEASUREMENT_3D);
    private final ObservableField<DemoSoundPackage> soundPackage = new ObservableField<>();

    public final ObservableInt getProgressMainSound() {
        return this.progressMainSound;
    }

    public final ObservableInt getProgressIdleTone() {
        return this.progressIdleTone;
    }

    public final ObservableInt getProgressIdleVolume() {
        return this.progressIdleVolume;
    }

    public final ObservableInt getProgressWorkingTone() {
        return this.progressWorkingTone;
    }

    public final ObservableInt getProgressUnevenTone() {
        return this.progressUnevenTone;
    }

    public final ObservableField<String> getNumberChamber() {
        return this.numberChamber;
    }

    public final ObservableField<DemoSoundPackage> getSoundPackage() {
        return this.soundPackage;
    }
}
