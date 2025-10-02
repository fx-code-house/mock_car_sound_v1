package com.thor.app.gui.fragments.firmwares;

import android.view.View;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ItemListFirmwareInfoBinding;
import com.thor.networkmodule.model.firmware.FirmwareProfileShort;
import com.xwray.groupie.viewbinding.BindableItem;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FirmwareInfoItem.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\u0018\u0010\u0014\u001a\n \u0015*\u0004\u0018\u00010\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0017H\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lcom/thor/app/gui/fragments/firmwares/FirmwareInfoItem;", "Lcom/xwray/groupie/viewbinding/BindableItem;", "Lcom/carsystems/thor/app/databinding/ItemListFirmwareInfoBinding;", "model", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "onClick", "Lkotlin/Function1;", "", "(Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;Lkotlin/jvm/functions/Function1;)V", "getModel", "()Lcom/thor/networkmodule/model/firmware/FirmwareProfileShort;", "getOnClick", "()Lkotlin/jvm/functions/Function1;", "bind", "viewBinding", "position", "", "getId", "", "getLayout", "initializeViewBinding", "kotlin.jvm.PlatformType", "view", "Landroid/view/View;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class FirmwareInfoItem extends BindableItem<ItemListFirmwareInfoBinding> {
    private final FirmwareProfileShort model;
    private final Function1<FirmwareProfileShort, Unit> onClick;

    @Override // com.xwray.groupie.Item
    public int getLayout() {
        return R.layout.item_list_firmware_info;
    }

    public final FirmwareProfileShort getModel() {
        return this.model;
    }

    public final Function1<FirmwareProfileShort, Unit> getOnClick() {
        return this.onClick;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirmwareInfoItem(FirmwareProfileShort model, Function1<? super FirmwareProfileShort, Unit> onClick) {
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        this.model = model;
        this.onClick = onClick;
    }

    @Override // com.xwray.groupie.viewbinding.BindableItem
    public void bind(final ItemListFirmwareInfoBinding viewBinding, int position) {
        Intrinsics.checkNotNullParameter(viewBinding, "viewBinding");
        final FirmwareInfoItem$bind$firmwareListener$1 firmwareInfoItem$bind$firmwareListener$1 = new FirmwareInfoItem$bind$firmwareListener$1(this);
        viewBinding.setModel(this.model);
        viewBinding.getRoot().setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.fragments.firmwares.FirmwareInfoItem$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FirmwareInfoItem.bind$lambda$0(firmwareInfoItem$bind$firmwareListener$1, viewBinding, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bind$lambda$0(FirmwareInfoItem$bind$firmwareListener$1 firmwareListener, ItemListFirmwareInfoBinding viewBinding, View view) {
        Intrinsics.checkNotNullParameter(firmwareListener, "$firmwareListener");
        Intrinsics.checkNotNullParameter(viewBinding, "$viewBinding");
        FirmwareProfileShort model = viewBinding.getModel();
        if (model == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        Intrinsics.checkNotNullExpressionValue(model, "requireNotNull(viewBinding.model)");
        firmwareListener.onClick(model);
    }

    @Override // com.xwray.groupie.Item
    public long getId() {
        return this.model.getIdFW();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xwray.groupie.viewbinding.BindableItem
    public ItemListFirmwareInfoBinding initializeViewBinding(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        return ItemListFirmwareInfoBinding.bind(view);
    }
}
