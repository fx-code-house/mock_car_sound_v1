package com.thor.app.databinding.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import com.thor.app.bus.events.BluetoothDeviceAuthorizeEvent;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.greenrobot.eventbus.EventBus;
import timber.log.Timber;

/* compiled from: BluetoothDeviceDataBindingAdapter.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J\u001a\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007¨\u0006\u000b"}, d2 = {"Lcom/thor/app/databinding/adapters/BluetoothDeviceDataBindingAdapter;", "", "()V", "bluetoothDeviceAuthorize", "", "view", "Landroid/view/View;", "device", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "showBluetoothDeviceName", "Landroid/widget/TextView;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class BluetoothDeviceDataBindingAdapter {
    public static final BluetoothDeviceDataBindingAdapter INSTANCE = new BluetoothDeviceDataBindingAdapter();

    private BluetoothDeviceDataBindingAdapter() {
    }

    @BindingAdapter({"bluetoothDeviceAuthorize"})
    @JvmStatic
    public static final void bluetoothDeviceAuthorize(View view, final BluetoothDeviceItem device) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.setOnClickListener(null);
        if (device == null) {
            Timber.INSTANCE.e("BluetoothDeviceItem cannot be null", new Object[0]);
        } else {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.databinding.adapters.BluetoothDeviceDataBindingAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    BluetoothDeviceDataBindingAdapter.bluetoothDeviceAuthorize$lambda$0(device, view2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bluetoothDeviceAuthorize$lambda$0(BluetoothDeviceItem bluetoothDeviceItem, View view) {
        EventBus.getDefault().post(new BluetoothDeviceAuthorizeEvent(bluetoothDeviceItem));
    }

    @BindingAdapter({"showBluetoothDeviceName"})
    @JvmStatic
    public static final void showBluetoothDeviceName(TextView view, BluetoothDeviceItem device) {
        String name;
        Intrinsics.checkNotNullParameter(view, "view");
        if (device == null || (name = device.getName()) == null) {
            return;
        }
        String strSubstring = name.substring(0, 4);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        String strSubstring2 = name.substring(4);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%s %s", Arrays.copyOf(new Object[]{strSubstring, strSubstring2}, 2));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        view.setText(str);
    }
}
