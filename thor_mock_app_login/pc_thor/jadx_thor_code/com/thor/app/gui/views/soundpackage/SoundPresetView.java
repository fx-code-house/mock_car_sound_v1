package com.thor.app.gui.views.soundpackage;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ViewSoundPresetBinding;
import com.thor.app.bus.events.mainpreset.DoneWritePreset;
import com.thor.app.bus.events.mainpreset.StartWritePreset;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.networkmodule.model.ModeType;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: SoundPresetView.kt */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u00020\u001a2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010\u001c\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0016\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\nR\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/thor/app/gui/views/soundpackage/SoundPresetView;", "Landroidx/cardview/widget/CardView;", "Landroid/view/View$OnClickListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewSoundPresetBinding;", "bleManager", "Lcom/thor/app/managers/BleManager;", "getBleManager", "()Lcom/thor/app/managers/BleManager;", "bleManager$delegate", "Lkotlin/Lazy;", "counet", "fileLogger", "Lcom/thor/app/utils/logs/loggers/FileLogger;", "createPresetisExistAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "doAddNewPreset", "", "initView", "onClick", "v", "Landroid/view/View;", "setSoundPackage", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "modeType", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SoundPresetView extends CardView implements View.OnClickListener {
    private ViewSoundPresetBinding binding;

    /* renamed from: bleManager$delegate, reason: from kotlin metadata */
    private final Lazy bleManager;
    private int counet;
    private FileLogger fileLogger;

    private final BleManager getBleManager() {
        return (BleManager) this.bleManager.getValue();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SoundPresetView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SoundPresetView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SoundPresetView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.bleManager = LazyKt.lazy(new Function0<BleManager>() { // from class: com.thor.app.gui.views.soundpackage.SoundPresetView$bleManager$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BleManager invoke() {
                return BleManager.INSTANCE.from(this.this$0.getContext());
            }
        });
        initView(attributeSet);
    }

    private final void initView(AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewSoundPresetBinding viewSoundPresetBindingInflate = ViewSoundPresetBinding.inflate(layoutInflater, this, true);
        Intrinsics.checkNotNullExpressionValue(viewSoundPresetBindingInflate, "inflate(layoutInflater!!, this, true)");
        this.binding = viewSoundPresetBindingInflate;
        if (isInEditMode()) {
            return;
        }
        this.fileLogger = new FileLogger(getContext(), null, 2, null);
        setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (getBleManager().isBleEnabledAndDeviceConnected()) {
            doAddNewPreset();
            return;
        }
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        dialogManager.createNoConnectionToBoardAlertDialog(context).show();
    }

    public final void setSoundPackage(ShopSoundPackage soundPackage, int modeType) {
        Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
        Timber.INSTANCE.i("setSoundPackage: %s, %s", soundPackage, Integer.valueOf(modeType));
        ViewSoundPresetBinding viewSoundPresetBinding = this.binding;
        if (viewSoundPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSoundPresetBinding = null;
        }
        viewSoundPresetBinding.setSoundPackage(soundPackage);
        if (modeType == 1) {
            ViewSoundPresetBinding viewSoundPresetBinding2 = this.binding;
            if (viewSoundPresetBinding2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewSoundPresetBinding2 = null;
            }
            ArrayList<ModeType> modeImages = soundPackage.getModeImages();
            viewSoundPresetBinding2.setModeType(modeImages != null ? modeImages.get(0) : null);
            return;
        }
        if (modeType == 2) {
            ViewSoundPresetBinding viewSoundPresetBinding3 = this.binding;
            if (viewSoundPresetBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                viewSoundPresetBinding3 = null;
            }
            ArrayList<ModeType> modeImages2 = soundPackage.getModeImages();
            viewSoundPresetBinding3.setModeType(modeImages2 != null ? modeImages2.get(1) : null);
            return;
        }
        if (modeType != 3) {
            return;
        }
        ViewSoundPresetBinding viewSoundPresetBinding4 = this.binding;
        if (viewSoundPresetBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSoundPresetBinding4 = null;
        }
        ArrayList<ModeType> modeImages3 = soundPackage.getModeImages();
        viewSoundPresetBinding4.setModeType(modeImages3 != null ? modeImages3.get(2) : null);
    }

    private final void doAddNewPreset() {
        EventBus.getDefault().post(new StartWritePreset());
        InstalledPresets installPresets = Settings.INSTANCE.getInstallPresets();
        if (installPresets == null) {
            installPresets = new InstalledPresets((short) 0, (short) 0, null, 7, null);
        }
        Timber.INSTANCE.i("doAddNewPreset", new Object[0]);
        ViewSoundPresetBinding viewSoundPresetBinding = this.binding;
        if (viewSoundPresetBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSoundPresetBinding = null;
        }
        ShopSoundPackage soundPackage = viewSoundPresetBinding.getSoundPackage();
        Short shValueOf = soundPackage != null ? Short.valueOf((short) soundPackage.getId()) : null;
        Intrinsics.checkNotNull(shValueOf);
        short sShortValue = shValueOf.shortValue();
        ViewSoundPresetBinding viewSoundPresetBinding2 = this.binding;
        if (viewSoundPresetBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            viewSoundPresetBinding2 = null;
        }
        ModeType modeType = viewSoundPresetBinding2.getModeType();
        Short shValueOf2 = modeType != null ? Short.valueOf((short) modeType.getType()) : null;
        Intrinsics.checkNotNull(shValueOf2);
        InstalledPreset installedPreset = new InstalledPreset(sShortValue, shValueOf2.shortValue(), (short) 1);
        if (installPresets.getPresets().contains(installedPreset)) {
            EventBus.getDefault().post(new DoneWritePreset());
            createPresetisExistAlertDialog().show();
        } else {
            installPresets.getPresets().add(installedPreset);
            installPresets.setAmount((short) installPresets.getPresets().size());
            Log.i("FIND_ERROR", "1 doAddNewPreset");
            BleManager.executeWriteInstalledAddPreset$default(getBleManager(), installPresets, (short) 0, 2, null);
        }
    }

    private final AlertDialog createPresetisExistAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), 2131886084);
        builder.setMessage(R.string.message_installed_preset_is_exist).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "alertBuilder.create()");
        return alertDialogCreate;
    }
}
