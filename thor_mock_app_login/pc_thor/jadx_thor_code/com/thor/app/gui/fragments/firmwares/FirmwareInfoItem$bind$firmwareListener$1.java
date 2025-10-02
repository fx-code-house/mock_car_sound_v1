package com.thor.app.gui.fragments.firmwares;

import com.thor.networkmodule.model.firmware.FirmwareProfileShort;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FirmwareInfoItem.kt */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/thor/app/gui/fragments/firmwares/FirmwareInfoItem$bind$firmwareListener$1", "Lcom/thor/app/gui/fragments/firmwares/OnFirmwareItemClickListener;", "onClick", "", "firmware", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FirmwareInfoItem$bind$firmwareListener$1 implements OnFirmwareItemClickListener {
    final /* synthetic */ FirmwareInfoItem this$0;

    FirmwareInfoItem$bind$firmwareListener$1(FirmwareInfoItem firmwareInfoItem) {
        this.this$0 = firmwareInfoItem;
    }

    @Override // com.thor.app.gui.fragments.firmwares.OnFirmwareItemClickListener
    public void onClick(FirmwareProfileShort firmware) {
        Intrinsics.checkNotNullParameter(firmware, "firmware");
        this.this$0.getOnClick().invoke(firmware);
    }
}
