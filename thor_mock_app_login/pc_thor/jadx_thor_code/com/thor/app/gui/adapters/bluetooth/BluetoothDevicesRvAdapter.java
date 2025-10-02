package com.thor.app.gui.adapters.bluetooth;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ItemBluetoothDeviceBinding;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.networkmodule.model.bluetooth.BluetoothDeviceItem;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothDevicesRvAdapter.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002J\u0012\u0010\b\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0002H\u0016J\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\nJ\u001c\u0010\u000b\u001a\u00020\u00062\n\u0010\f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u000f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0016¨\u0006\u0014"}, d2 = {"Lcom/thor/app/gui/adapters/bluetooth/BluetoothDevicesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/networkmodule/model/bluetooth/BluetoothDeviceItem;", "Lcom/thor/app/gui/adapters/bluetooth/BluetoothDevicesRvAdapter$BluetoothDeviceItemViewHolder;", "()V", "addBluetoothDevice", "", "item", "addItem", "getItems", "", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "BluetoothDeviceItemViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothDevicesRvAdapter extends BaseRecyclerListAdapter<BluetoothDeviceItem, BluetoothDeviceItemViewHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BluetoothDeviceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ItemBluetoothDeviceBinding itemBluetoothDeviceBindingInflate = ItemBluetoothDeviceBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemBluetoothDeviceBindingInflate, "inflate(inflater!!, parent, false)");
        View root = itemBluetoothDeviceBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new BluetoothDeviceItemViewHolder(this, root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BluetoothDeviceItemViewHolder holder, int position) {
        Resources resources;
        FrameLayout frameLayout;
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (position == 0) {
            ItemBluetoothDeviceBinding binding = holder.getBinding();
            Integer numValueOf = null;
            ViewGroup.LayoutParams layoutParams = (binding == null || (frameLayout = binding.layoutMain) == null) ? null : frameLayout.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            Context context = holder.getBinding().layoutMain.getContext();
            if (context != null && (resources = context.getResources()) != null) {
                numValueOf = Integer.valueOf(resources.getDimensionPixelSize(R.dimen.margin_dp_16));
            }
            Intrinsics.checkNotNull(numValueOf);
            layoutParams2.topMargin = numValueOf.intValue();
            holder.getBinding().layoutMain.setLayoutParams(layoutParams2);
        }
        ItemBluetoothDeviceBinding binding2 = holder.getBinding();
        if (binding2 == null) {
            return;
        }
        binding2.setModel((BluetoothDeviceItem) this.mList.get(position));
    }

    @Override // com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter, com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void addItem(BluetoothDeviceItem item) {
        removeItem((BluetoothDevicesRvAdapter) item);
        super.addItem((BluetoothDevicesRvAdapter) item);
    }

    public final void addBluetoothDevice(BluetoothDeviceItem item) {
        if (getAll().contains(item)) {
            return;
        }
        super.addItem((BluetoothDevicesRvAdapter) item);
    }

    public final List<BluetoothDeviceItem> getItems() {
        List mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        return mList;
    }

    /* compiled from: BluetoothDevicesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/bluetooth/BluetoothDevicesRvAdapter$BluetoothDeviceItemViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/bluetooth/BluetoothDevicesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemBluetoothDeviceBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemBluetoothDeviceBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class BluetoothDeviceItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemBluetoothDeviceBinding binding;
        final /* synthetic */ BluetoothDevicesRvAdapter this$0;

        public final ItemBluetoothDeviceBinding getBinding() {
            return this.binding;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BluetoothDeviceItemViewHolder(BluetoothDevicesRvAdapter bluetoothDevicesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = bluetoothDevicesRvAdapter;
            this.binding = (ItemBluetoothDeviceBinding) DataBindingUtil.bind(itemView);
        }
    }
}
