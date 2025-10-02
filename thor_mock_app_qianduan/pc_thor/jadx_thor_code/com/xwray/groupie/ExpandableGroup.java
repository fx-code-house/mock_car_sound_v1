package com.xwray.groupie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public class ExpandableGroup extends NestedGroup {
    private final List<Group> children;
    private boolean isExpanded;
    private final Group parent;

    public ExpandableGroup(Group group) {
        this.isExpanded = false;
        this.children = new ArrayList();
        this.parent = group;
        ((ExpandableItem) group).setExpandableGroup(this);
    }

    public ExpandableGroup(Group group, boolean z) {
        this.isExpanded = false;
        this.children = new ArrayList();
        this.parent = group;
        ((ExpandableItem) group).setExpandableGroup(this);
        this.isExpanded = z;
    }

    @Override // com.xwray.groupie.NestedGroup
    public void add(int i, Group group) {
        super.add(i, group);
        this.children.add(i, group);
        if (this.isExpanded) {
            notifyItemRangeInserted(GroupUtils.getItemCount(this.children.subList(0, i)) + 1, group.getItemCount());
        }
    }

    @Override // com.xwray.groupie.NestedGroup
    public void add(Group group) {
        super.add(group);
        if (this.isExpanded) {
            int itemCount = getItemCount();
            this.children.add(group);
            notifyItemRangeInserted(itemCount, group.getItemCount());
            return;
        }
        this.children.add(group);
    }

    @Override // com.xwray.groupie.NestedGroup
    public void addAll(Collection<? extends Group> collection) {
        if (collection.isEmpty()) {
            return;
        }
        super.addAll(collection);
        if (this.isExpanded) {
            int itemCount = getItemCount();
            this.children.addAll(collection);
            notifyItemRangeInserted(itemCount, GroupUtils.getItemCount(collection));
            return;
        }
        this.children.addAll(collection);
    }

    @Override // com.xwray.groupie.NestedGroup
    public void addAll(int i, Collection<? extends Group> collection) {
        if (collection.isEmpty()) {
            return;
        }
        super.addAll(i, collection);
        this.children.addAll(i, collection);
        if (this.isExpanded) {
            notifyItemRangeInserted(GroupUtils.getItemCount(this.children.subList(0, i)) + 1, GroupUtils.getItemCount(collection));
        }
    }

    @Override // com.xwray.groupie.NestedGroup
    public void remove(Group group) {
        if (this.children.contains(group)) {
            super.remove(group);
            if (this.isExpanded) {
                int itemCountBeforeGroup = getItemCountBeforeGroup(group);
                this.children.remove(group);
                notifyItemRangeRemoved(itemCountBeforeGroup, group.getItemCount());
                return;
            }
            this.children.remove(group);
        }
    }

    @Override // com.xwray.groupie.NestedGroup
    public void replaceAll(Collection<? extends Group> collection) {
        if (this.isExpanded) {
            super.replaceAll(collection);
            this.children.clear();
            this.children.addAll(collection);
            notifyDataSetInvalidated();
            return;
        }
        this.children.clear();
        this.children.addAll(collection);
    }

    @Override // com.xwray.groupie.NestedGroup
    public void removeAll(Collection<? extends Group> collection) {
        if (collection.isEmpty() || !this.children.containsAll(collection)) {
            return;
        }
        super.removeAll(collection);
        if (this.isExpanded) {
            this.children.removeAll(collection);
            for (Group group : collection) {
                int itemCountBeforeGroup = getItemCountBeforeGroup(group);
                this.children.remove(group);
                notifyItemRangeRemoved(itemCountBeforeGroup, group.getItemCount());
            }
            return;
        }
        this.children.removeAll(collection);
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    @Override // com.xwray.groupie.NestedGroup
    public Group getGroup(int i) {
        if (i == 0) {
            return this.parent;
        }
        return this.children.get(i - 1);
    }

    @Override // com.xwray.groupie.NestedGroup
    public int getPosition(Group group) {
        if (group == this.parent) {
            return 0;
        }
        int iIndexOf = this.children.indexOf(group);
        if (iIndexOf >= 0) {
            return iIndexOf + 1;
        }
        return -1;
    }

    @Override // com.xwray.groupie.NestedGroup
    public int getGroupCount() {
        return (this.isExpanded ? this.children.size() : 0) + 1;
    }

    public int getChildCount() {
        return this.children.size();
    }

    public void onToggleExpanded() {
        int itemCount = getItemCount();
        this.isExpanded = !this.isExpanded;
        int itemCount2 = getItemCount();
        if (itemCount > itemCount2) {
            notifyItemRangeRemoved(itemCount2, itemCount - itemCount2);
        } else {
            notifyItemRangeInserted(itemCount, itemCount2 - itemCount);
        }
    }

    public void setExpanded(boolean z) {
        if (this.isExpanded != z) {
            onToggleExpanded();
        }
    }

    private boolean dispatchChildChanges(Group group) {
        return this.isExpanded || group == this.parent;
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onChanged(Group group) {
        if (dispatchChildChanges(group)) {
            super.onChanged(group);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemInserted(Group group, int i) {
        if (dispatchChildChanges(group)) {
            super.onItemInserted(group, i);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemChanged(Group group, int i) {
        if (dispatchChildChanges(group)) {
            super.onItemChanged(group, i);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemChanged(Group group, int i, Object obj) {
        if (dispatchChildChanges(group)) {
            super.onItemChanged(group, i, obj);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRemoved(Group group, int i) {
        if (dispatchChildChanges(group)) {
            super.onItemRemoved(group, i);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRangeChanged(Group group, int i, int i2) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeChanged(group, i, i2);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRangeChanged(Group group, int i, int i2, Object obj) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeChanged(group, i, i2, obj);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRangeInserted(Group group, int i, int i2) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeInserted(group, i, i2);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRangeRemoved(Group group, int i, int i2) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeRemoved(group, i, i2);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemMoved(Group group, int i, int i2) {
        if (dispatchChildChanges(group)) {
            super.onItemMoved(group, i, i2);
        }
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onDataSetInvalidated() {
        if (this.isExpanded) {
            super.onDataSetInvalidated();
        }
    }
}
