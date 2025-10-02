package com.thor.app.gui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.DiffUtil;
import com.carsystems.thor.app.databinding.ItemNotificationBinding;
import com.fourksoft.base.ui.adapter.BaseBindingRecyclerListAdapter;
import com.fourksoft.base.ui.adapter.BaseBindingViewHolder;
import com.fourksoft.base.ui.adapter.listener.OnItemClickListener;
import com.thor.networkmodule.model.notifications.NotificationDetails;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationsRvAdapter.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0014\u001a\u00020\u000f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u00162\u0006\u0010\u0017\u001a\u00020\u0002H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\u0014\u0010\u001b\u001a\u00020\u000f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001dR.\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00030\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR(\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lcom/thor/app/gui/adapters/NotificationsRvAdapter;", "Lcom/fourksoft/base/ui/adapter/BaseBindingRecyclerListAdapter;", "Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "Lcom/carsystems/thor/app/databinding/ItemNotificationBinding;", "()V", "bindingInflater", "Lkotlin/Function3;", "Landroid/view/LayoutInflater;", "Landroid/view/ViewGroup;", "", "getBindingInflater", "()Lkotlin/jvm/functions/Function3;", "onUpdateClickListener", "Lkotlin/Function1;", "", "", "getOnUpdateClickListener", "()Lkotlin/jvm/functions/Function1;", "setOnUpdateClickListener", "(Lkotlin/jvm/functions/Function1;)V", "bindViewHolder", "holder", "Lcom/fourksoft/base/ui/adapter/BaseBindingViewHolder;", "model", "getItemId", "", "adapterPosition", "updateData", "newList", "", "EventDiffCallback", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NotificationsRvAdapter extends BaseBindingRecyclerListAdapter<NotificationInfo, ItemNotificationBinding> {
    private Function1<? super Integer, Unit> onUpdateClickListener;

    @Override // com.fourksoft.base.ui.adapter.BaseBindingRecyclerListAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(BaseBindingViewHolder baseBindingViewHolder, NotificationInfo notificationInfo) {
        bindViewHolder2((BaseBindingViewHolder<ItemNotificationBinding>) baseBindingViewHolder, notificationInfo);
    }

    public final Function1<Integer, Unit> getOnUpdateClickListener() {
        return this.onUpdateClickListener;
    }

    public final void setOnUpdateClickListener(Function1<? super Integer, Unit> function1) {
        this.onUpdateClickListener = function1;
    }

    @Override // com.fourksoft.base.ui.adapter.BaseBindingRecyclerListAdapter
    protected Function3<LayoutInflater, ViewGroup, Boolean, ItemNotificationBinding> getBindingInflater() {
        return NotificationsRvAdapter$bindingInflater$1.INSTANCE;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(BaseBindingViewHolder<ItemNotificationBinding> holder, final NotificationInfo model) {
        AppCompatImageButton appCompatImageButton;
        View root;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(model, "model");
        ItemNotificationBinding itemNotificationBinding = (ItemNotificationBinding) holder.getBinding();
        if (itemNotificationBinding != null) {
            itemNotificationBinding.setModel(model);
        }
        ItemNotificationBinding itemNotificationBinding2 = (ItemNotificationBinding) holder.getBinding();
        if (itemNotificationBinding2 != null && (root = itemNotificationBinding2.getRoot()) != null) {
            root.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.NotificationsRvAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    NotificationsRvAdapter.bindViewHolder$lambda$0(this.f$0, model, view);
                }
            });
        }
        ItemNotificationBinding itemNotificationBinding3 = (ItemNotificationBinding) holder.getBinding();
        if (itemNotificationBinding3 == null || (appCompatImageButton = itemNotificationBinding3.notificationBtn) == null) {
            return;
        }
        appCompatImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.NotificationsRvAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationsRvAdapter.bindViewHolder$lambda$1(model, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindViewHolder$lambda$0(NotificationsRvAdapter this$0, NotificationInfo model, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(model, "$model");
        OnItemClickListener<NotificationInfo> mOnItemClickListener = this$0.getMOnItemClickListener();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick((OnItemClickListener<NotificationInfo>) model);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindViewHolder$lambda$1(NotificationInfo model, NotificationsRvAdapter this$0, View view) {
        Function1<? super Integer, Unit> function1;
        Intrinsics.checkNotNullParameter(model, "$model");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        NotificationDetails details = model.getDetails();
        Integer numValueOf = details != null ? Integer.valueOf(details.getIdSoundPkg()) : null;
        if (numValueOf == null || (function1 = this$0.onUpdateClickListener) == null) {
            return;
        }
        function1.invoke(numValueOf);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int adapterPosition) {
        return getItem(adapterPosition).getNotificationId();
    }

    public final void updateData(List<NotificationInfo> newList) {
        Intrinsics.checkNotNullParameter(newList, "newList");
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new EventDiffCallback(getMList(), newList));
        Intrinsics.checkNotNullExpressionValue(diffResultCalculateDiff, "calculateDiff(diffUtilCallBack)");
        diffResultCalculateDiff.dispatchUpdatesTo(this);
        getMList().clear();
        getMList().addAll(newList);
    }

    /* compiled from: NotificationsRvAdapter.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/thor/app/gui/adapters/NotificationsRvAdapter$EventDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$Callback;", "oldList", "", "Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "newList", "(Ljava/util/List;Ljava/util/List;)V", "areContentsTheSame", "", "oldItemPosition", "", "newItemPosition", "areItemsTheSame", "getChangePayload", "", "getNewListSize", "getOldListSize", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class EventDiffCallback extends DiffUtil.Callback {
        private final List<NotificationInfo> newList;
        private final List<NotificationInfo> oldList;

        public EventDiffCallback(List<NotificationInfo> oldList, List<NotificationInfo> newList) {
            Intrinsics.checkNotNullParameter(oldList, "oldList");
            Intrinsics.checkNotNullParameter(newList, "newList");
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getOldListSize() {
            return this.oldList.size();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getNewListSize() {
            return this.newList.size();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return this.oldList.get(oldItemPosition).getNotificationId() == this.newList.get(newItemPosition).getNotificationId();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return Intrinsics.areEqual(this.oldList.get(oldItemPosition).getTitle(), this.newList.get(newItemPosition).getTitle());
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
