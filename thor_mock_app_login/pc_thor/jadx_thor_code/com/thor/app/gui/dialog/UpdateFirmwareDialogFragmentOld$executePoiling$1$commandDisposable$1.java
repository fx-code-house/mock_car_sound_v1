package com.thor.app.gui.dialog;

import android.view.View;
import android.widget.Toast;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.FragmentDialogUpdateFirmwareBinding;
import com.google.android.gms.common.util.Hex;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEventOld;
import com.thor.businessmodule.bluetooth.model.other.PoilingModel;
import com.thor.businessmodule.bluetooth.response.other.PoilingBleResponseOld;
import com.thor.businessmodule.settings.Constants;
import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: UpdateFirmwareDialogFragmentOld.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Ljava/io/ByteArrayOutputStream;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
final class UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1 extends Lambda implements Function1<ByteArrayOutputStream, Unit> {
    final /* synthetic */ UpdateFirmwareDialogFragmentOld this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1(UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOld) {
        super(1);
        this.this$0 = updateFirmwareDialogFragmentOld;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
        invoke2(byteArrayOutputStream);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Timber.INSTANCE.i("Take data: " + Hex.bytesToStringUppercase(byteArray), new Object[0]);
        PoilingBleResponseOld poilingBleResponseOld = new PoilingBleResponseOld(byteArray);
        if (poilingBleResponseOld.getStatus()) {
            Timber.Companion companion = Timber.INSTANCE;
            PoilingModel poilingModel = poilingBleResponseOld.getPoilingModel();
            FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding = null;
            companion.i("Response is correct: " + (poilingModel != null ? Short.valueOf(poilingModel.getStatus()) : null), new Object[0]);
            PoilingModel poilingModel2 = poilingBleResponseOld.getPoilingModel();
            Integer numValueOf = poilingModel2 != null ? Integer.valueOf(poilingModel2.getStatus()) : null;
            if (numValueOf != null && numValueOf.intValue() == 0) {
                Timber.INSTANCE.i("Response is correct", new Object[0]);
                this.this$0.getMBleManager().disconnect(false);
                FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding2 = this.this$0.binding;
                if (fragmentDialogUpdateFirmwareBinding2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    fragmentDialogUpdateFirmwareBinding2 = null;
                }
                fragmentDialogUpdateFirmwareBinding2.textViewCancel.setEnabled(false);
                FragmentDialogUpdateFirmwareBinding fragmentDialogUpdateFirmwareBinding3 = this.this$0.binding;
                if (fragmentDialogUpdateFirmwareBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    fragmentDialogUpdateFirmwareBinding = fragmentDialogUpdateFirmwareBinding3;
                }
                View root = fragmentDialogUpdateFirmwareBinding.getRoot();
                final UpdateFirmwareDialogFragmentOld updateFirmwareDialogFragmentOld = this.this$0;
                root.postDelayed(new Runnable() { // from class: com.thor.app.gui.dialog.UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UpdateFirmwareDialogFragmentOld$executePoiling$1$commandDisposable$1.invoke$lambda$0(updateFirmwareDialogFragmentOld);
                    }
                }, Constants.SUCCESSFUL_FIRMWARE_UPDATE_END_DELAY);
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == 1) {
                Timber.Companion companion2 = Timber.INSTANCE;
                PoilingModel poilingModel3 = poilingBleResponseOld.getPoilingModel();
                companion2.i("Poiling status: " + (poilingModel3 != null ? Short.valueOf(poilingModel3.getProgress()) : null), new Object[0]);
                this.this$0.executePoiling();
                return;
            }
            if (numValueOf != null && numValueOf.intValue() == 2) {
                Toast.makeText(this.this$0.getContext(), R.string.text_update_firmware_error, 1).show();
                this.this$0.dismiss();
                return;
            }
            return;
        }
        Timber.INSTANCE.e("Response is not correct: " + poilingBleResponseOld.getErrorCode(), new Object[0]);
        this.this$0.executePoiling();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(UpdateFirmwareDialogFragmentOld this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EventBus.getDefault().post(new ApplyUpdateFirmwareSuccessfulEventOld(true));
        this$0.dismiss();
    }
}
