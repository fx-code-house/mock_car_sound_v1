package com.thor.app.gui.fragments.preset;

import android.content.Context;
import android.widget.SeekBar;
import androidx.appcompat.app.AlertDialog;
import com.google.android.gms.common.util.Hex;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.UsersManager;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresetRules;
import com.thor.businessmodule.bluetooth.model.other.PresetRule;
import com.thor.businessmodule.bluetooth.util.BleHelper;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.networkmodule.model.responses.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: PresetSoundSettingsFragment.kt */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u000b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\f"}, d2 = {"com/thor/app/gui/fragments/preset/PresetSoundSettingsFragment$setNewValueChangeListener$1", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "onProgressChanged", "", "p0", "Landroid/widget/SeekBar;", "p1", "", "p2", "", "onStartTrackingTouch", "onStopTrackingTouch", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PresetSoundSettingsFragment$setNewValueChangeListener$1 implements SeekBar.OnSeekBarChangeListener {
    final /* synthetic */ PresetSoundSettingsFragment this$0;

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar p0) {
    }

    PresetSoundSettingsFragment$setNewValueChangeListener$1(PresetSoundSettingsFragment presetSoundSettingsFragment) {
        this.this$0 = presetSoundSettingsFragment;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar p0, int p1, boolean p2) {
        int progress;
        Intrinsics.checkNotNull(p0);
        if (p0.getProgress() % 5 == 0) {
            if (this.this$0.getViewRuleId(p0.getId()) == 48) {
                progress = 100 - p0.getProgress();
            } else {
                progress = p0.getProgress();
            }
            System.out.println(progress);
            if (this.this$0.getContext() != null) {
                PresetSoundSettingsFragment presetSoundSettingsFragment = this.this$0;
                BleManager bleManager = presetSoundSettingsFragment.getBleManager();
                InstalledPresetRules installedPresetRules = presetSoundSettingsFragment.mRules;
                Intrinsics.checkNotNull(installedPresetRules);
                bleManager.executeWriteInstalledSoundPackageRules(installedPresetRules, new PresetRule(presetSoundSettingsFragment.getViewRuleId(p0.getId()), (short) progress));
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar p0) {
        int progress;
        Observable<BaseResponse> observableObserveOn;
        PresetSoundSettingsFragment presetSoundSettingsFragment = this.this$0;
        Intrinsics.checkNotNull(p0);
        if (presetSoundSettingsFragment.getViewRuleId(p0.getId()) == 48) {
            progress = 100 - p0.getProgress();
        } else {
            progress = p0.getProgress();
        }
        System.out.println(progress);
        Context context = this.this$0.getContext();
        if (context != null) {
            final PresetSoundSettingsFragment presetSoundSettingsFragment2 = this.this$0;
            BleManager bleManager = presetSoundSettingsFragment2.getBleManager();
            InstalledPresetRules installedPresetRules = presetSoundSettingsFragment2.mRules;
            Intrinsics.checkNotNull(installedPresetRules);
            bleManager.executeWriteInstalledSoundPackageRulesStop(installedPresetRules, new PresetRule(presetSoundSettingsFragment2.getViewRuleId(p0.getId()), (short) progress));
            UsersManager usersManagerFrom = UsersManager.INSTANCE.from(context);
            MainPresetPackage mainPresetPackage = presetSoundSettingsFragment2.mPresetPackage;
            Integer packageId = mainPresetPackage != null ? mainPresetPackage.getPackageId() : null;
            Intrinsics.checkNotNull(packageId);
            int iIntValue = packageId.intValue();
            MainPresetPackage mainPresetPackage2 = presetSoundSettingsFragment2.mPresetPackage;
            Short modeType = mainPresetPackage2 != null ? mainPresetPackage2.getModeType() : null;
            Intrinsics.checkNotNull(modeType);
            Observable<BaseResponse> observableSubscribeOn = usersManagerFrom.sendStatisticsParameterChange(iIntValue, modeType.shortValue(), "0x" + Hex.bytesToStringUppercase(BleHelper.convertShortToByteArray(Short.valueOf(presetSoundSettingsFragment2.getViewRuleId(p0.getId())))), String.valueOf(progress)).subscribeOn(Schedulers.io());
            if (observableSubscribeOn == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
                return;
            }
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$setNewValueChangeListener$1$onStopTrackingTouch$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                    invoke2(baseResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(BaseResponse baseResponse) {
                    AlertDialog alertDialogCreateErrorAlertDialog;
                    if (!baseResponse.isSuccessful() && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(presetSoundSettingsFragment2.getContext(), baseResponse.getError(), baseResponse.getCode())) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.i(baseResponse.toString(), new Object[0]);
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$setNewValueChangeListener$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PresetSoundSettingsFragment$setNewValueChangeListener$1.onStopTrackingTouch$lambda$3$lambda$1(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$setNewValueChangeListener$1$onStopTrackingTouch$1$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable th) {
                    AlertDialog alertDialogCreateErrorAlertDialog;
                    if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                        AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(presetSoundSettingsFragment2.getContext(), th.getMessage(), 0);
                        if (alertDialogCreateErrorAlertDialog2 != null) {
                            alertDialogCreateErrorAlertDialog2.show();
                        }
                    } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(presetSoundSettingsFragment2.getContext(), th.getMessage(), 0)) != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                    Timber.INSTANCE.e(th);
                }
            };
            observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.fragments.preset.PresetSoundSettingsFragment$setNewValueChangeListener$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    PresetSoundSettingsFragment$setNewValueChangeListener$1.onStopTrackingTouch$lambda$3$lambda$2(function12, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStopTrackingTouch$lambda$3$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStopTrackingTouch$lambda$3$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }
}
