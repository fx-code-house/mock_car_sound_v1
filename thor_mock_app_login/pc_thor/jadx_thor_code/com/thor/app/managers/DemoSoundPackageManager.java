package com.thor.app.managers;

import com.thor.app.gui.views.demo.DemoSoundPackageView;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.businessmodule.model.DemoSoundPackage;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoSoundPackageManager.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\bJ\u0010\u0010\u0012\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/thor/app/managers/DemoSoundPackageManager;", "", "()V", "mSoundPackageView", "Lcom/thor/app/gui/views/demo/DemoSoundPackageView;", "mTachometerView", "Lcom/thor/app/gui/views/tachometer/TachometerWithEqualizeView;", "enableSoundPackage", "", "view", "onPlayTachometer", "track", "", "onStopTachometer", "onUpdateUiMode", "fuelCategory", "Lcom/thor/businessmodule/model/DemoSoundPackage$FuelCategory;", "release", "setTachometer", "tachometer", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoSoundPackageManager {
    public static final DemoSoundPackageManager INSTANCE = new DemoSoundPackageManager();
    private static DemoSoundPackageView mSoundPackageView;
    private static TachometerWithEqualizeView mTachometerView;

    private DemoSoundPackageManager() {
    }

    public final void enableSoundPackage(DemoSoundPackageView view) {
        if (view == null) {
            return;
        }
        DemoSoundPackageView demoSoundPackageView = mSoundPackageView;
        if (demoSoundPackageView == null) {
            mSoundPackageView = view;
            if (view != null) {
                view.onActivateAnimated();
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(demoSoundPackageView, view)) {
            DemoSoundPackageView demoSoundPackageView2 = mSoundPackageView;
            if (demoSoundPackageView2 != null) {
                demoSoundPackageView2.onDeactivateAnimated();
            }
            mSoundPackageView = null;
            return;
        }
        if (Intrinsics.areEqual(mSoundPackageView, view)) {
            return;
        }
        DemoSoundPackageView demoSoundPackageView3 = mSoundPackageView;
        if (demoSoundPackageView3 != null) {
            demoSoundPackageView3.onDeactivateAnimated();
        }
        mSoundPackageView = view;
        if (view != null) {
            view.onActivateAnimated();
        }
    }

    public final void release() {
        mSoundPackageView = null;
        mTachometerView = null;
    }

    public final void setTachometer(TachometerWithEqualizeView tachometer) {
        mTachometerView = tachometer;
    }

    public final void onStopTachometer() {
        TachometerWithEqualizeView tachometerWithEqualizeView = mTachometerView;
        if (tachometerWithEqualizeView != null) {
            tachometerWithEqualizeView.onStop();
        }
    }

    public final void onPlayTachometer(String track) {
        Intrinsics.checkNotNullParameter(track, "track");
        TachometerWithEqualizeView tachometerWithEqualizeView = mTachometerView;
        if (tachometerWithEqualizeView != null) {
            tachometerWithEqualizeView.onPlay(track);
        }
    }

    public final void onUpdateUiMode(DemoSoundPackage.FuelCategory fuelCategory) {
        Intrinsics.checkNotNullParameter(fuelCategory, "fuelCategory");
        TachometerWithEqualizeView tachometerWithEqualizeView = mTachometerView;
        if (tachometerWithEqualizeView != null) {
            tachometerWithEqualizeView.updateDemoUiMode(fuelCategory);
        }
    }
}
