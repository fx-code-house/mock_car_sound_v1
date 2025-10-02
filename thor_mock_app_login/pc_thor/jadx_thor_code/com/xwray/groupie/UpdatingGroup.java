package com.xwray.groupie;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public class UpdatingGroup extends NestedGroup {
    private ListUpdateCallback listUpdateCallback = new ListUpdateCallback() { // from class: com.xwray.groupie.UpdatingGroup.1
        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int i, int i2) {
            UpdatingGroup.this.notifyItemRangeInserted(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int i, int i2) {
            UpdatingGroup.this.notifyItemRangeRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int i, int i2) {
            UpdatingGroup.this.notifyItemMoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            UpdatingGroup.this.notifyItemRangeChanged(i, i2);
        }
    };
    private List<Item> items = new ArrayList();

    public void update(List<? extends Item> list) {
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new UpdatingCallback(list));
        super.removeAll(this.items);
        this.items.clear();
        super.addAll(list);
        this.items.addAll(list);
        diffResultCalculateDiff.dispatchUpdatesTo(this.listUpdateCallback);
    }

    @Override // com.xwray.groupie.NestedGroup
    public Group getGroup(int i) {
        return this.items.get(i);
    }

    @Override // com.xwray.groupie.NestedGroup
    public int getGroupCount() {
        return this.items.size();
    }

    @Override // com.xwray.groupie.NestedGroup
    public int getPosition(Group group) {
        if (group instanceof Item) {
            return this.items.indexOf(group);
        }
        return -1;
    }

    private class UpdatingCallback extends DiffUtil.Callback {
        private List<? extends Item> newList;

        UpdatingCallback(List<? extends Item> list) {
            this.newList = list;
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getOldListSize() {
            return UpdatingGroup.this.items.size();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public int getNewListSize() {
            return this.newList.size();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areItemsTheSame(int i, int i2) {
            Item item = (Item) UpdatingGroup.this.items.get(i);
            Item item2 = this.newList.get(i2);
            return item.getViewType() == item2.getViewType() && item.getId() == item2.getId();
        }

        @Override // androidx.recyclerview.widget.DiffUtil.Callback
        public boolean areContentsTheSame(int i, int i2) {
            return ((Item) UpdatingGroup.this.items.get(i)).hasSameContentAs(this.newList.get(i2));
        }
    }
}
