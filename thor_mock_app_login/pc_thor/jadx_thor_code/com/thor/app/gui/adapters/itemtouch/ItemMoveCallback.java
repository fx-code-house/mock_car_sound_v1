package com.thor.app.gui.adapters.itemtouch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: ItemMoveCallback.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u000f\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u0018\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/thor/app/gui/adapters/itemtouch/ItemMoveCallback;", "Landroidx/recyclerview/widget/ItemTouchHelper$Callback;", "mListener", "Lcom/thor/app/gui/adapters/itemtouch/ItemMoveCallback$OnItemMoveListener;", "(Lcom/thor/app/gui/adapters/itemtouch/ItemMoveCallback$OnItemMoveListener;)V", "isLongPressDragEnabled", "", "clearView", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "getMovementFlags", "", "onMove", "target", "onSwiped", "direction", "setIsLongPressDragEnabled", "enabled", "OnItemMoveListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ItemMoveCallback extends ItemTouchHelper.Callback {
    private boolean isLongPressDragEnabled;
    private final OnItemMoveListener mListener;

    /* compiled from: ItemMoveCallback.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&¨\u0006\b"}, d2 = {"Lcom/thor/app/gui/adapters/itemtouch/ItemMoveCallback$OnItemMoveListener;", "", "onFinishMoving", "", "onItemMove", "fromPosition", "", "toPosition", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemMoveListener {
        void onFinishMoving();

        void onItemMove(int fromPosition, int toPosition);
    }

    public ItemMoveCallback(OnItemMoveListener mListener) {
        Intrinsics.checkNotNullParameter(mListener, "mListener");
        this.mListener = mListener;
        this.isLongPressDragEnabled = true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    /* renamed from: isLongPressDragEnabled, reason: from getter */
    public boolean getIsLongPressDragEnabled() {
        return this.isLongPressDragEnabled;
    }

    public final void setIsLongPressDragEnabled(boolean enabled) {
        this.isLongPressDragEnabled = enabled;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        return ItemTouchHelper.Callback.makeMovementFlags(3, 0);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        Intrinsics.checkNotNullParameter(target, "target");
        Timber.INSTANCE.i("onMove", new Object[0]);
        this.mListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        Timber.INSTANCE.i("onSwiped direction %s", Integer.valueOf(direction));
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        super.clearView(recyclerView, viewHolder);
        Timber.INSTANCE.i("clearView", new Object[0]);
        this.mListener.onFinishMoving();
    }
}
