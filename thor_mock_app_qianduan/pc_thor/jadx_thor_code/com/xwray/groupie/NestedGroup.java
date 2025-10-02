package com.xwray.groupie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class NestedGroup implements Group, GroupDataObserver {
    private final GroupDataObservable observable = new GroupDataObservable();

    public abstract Group getGroup(int i);

    public abstract int getGroupCount();

    public abstract int getPosition(Group group);

    @Override // com.xwray.groupie.Group
    public int getItemCount() {
        int itemCount = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            itemCount += getGroup(i).getItemCount();
        }
        return itemCount;
    }

    protected int getItemCountBeforeGroup(Group group) {
        return getItemCountBeforeGroup(getPosition(group));
    }

    protected int getItemCountBeforeGroup(int i) {
        int itemCount = 0;
        for (int i2 = 0; i2 < i; i2++) {
            itemCount += getGroup(i2).getItemCount();
        }
        return itemCount;
    }

    @Override // com.xwray.groupie.Group
    public Item getItem(int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < getGroupCount()) {
            Group group = getGroup(i2);
            int itemCount = group.getItemCount() + i3;
            if (itemCount > i) {
                return group.getItem(i - i3);
            }
            i2++;
            i3 = itemCount;
        }
        throw new IndexOutOfBoundsException("Wanted item at " + i + " but there are only " + getItemCount() + " items");
    }

    @Override // com.xwray.groupie.Group
    public final int getPosition(Item item) {
        int itemCount = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            Group group = getGroup(i);
            int position = group.getPosition(item);
            if (position >= 0) {
                return position + itemCount;
            }
            itemCount += group.getItemCount();
        }
        return -1;
    }

    @Override // com.xwray.groupie.Group
    public final void registerGroupDataObserver(GroupDataObserver groupDataObserver) {
        this.observable.registerObserver(groupDataObserver);
    }

    @Override // com.xwray.groupie.Group
    public void unregisterGroupDataObserver(GroupDataObserver groupDataObserver) {
        this.observable.unregisterObserver(groupDataObserver);
    }

    public void add(Group group) {
        group.registerGroupDataObserver(this);
    }

    public void addAll(Collection<? extends Group> collection) {
        Iterator<? extends Group> it = collection.iterator();
        while (it.hasNext()) {
            it.next().registerGroupDataObserver(this);
        }
    }

    public void add(int i, Group group) {
        group.registerGroupDataObserver(this);
    }

    public void addAll(int i, Collection<? extends Group> collection) {
        Iterator<? extends Group> it = collection.iterator();
        while (it.hasNext()) {
            it.next().registerGroupDataObserver(this);
        }
    }

    public void remove(Group group) {
        group.unregisterGroupDataObserver(this);
    }

    public void removeAll(Collection<? extends Group> collection) {
        Iterator<? extends Group> it = collection.iterator();
        while (it.hasNext()) {
            it.next().unregisterGroupDataObserver(this);
        }
    }

    public void replaceAll(Collection<? extends Group> collection) {
        for (int groupCount = getGroupCount() - 1; groupCount >= 0; groupCount--) {
            getGroup(groupCount).unregisterGroupDataObserver(this);
        }
        Iterator<? extends Group> it = collection.iterator();
        while (it.hasNext()) {
            it.next().registerGroupDataObserver(this);
        }
    }

    public void onChanged(Group group) {
        this.observable.onItemRangeChanged(this, getItemCountBeforeGroup(group), group.getItemCount());
    }

    public void onItemInserted(Group group, int i) {
        this.observable.onItemInserted(this, getItemCountBeforeGroup(group) + i);
    }

    public void onItemChanged(Group group, int i) {
        this.observable.onItemChanged(this, getItemCountBeforeGroup(group) + i);
    }

    public void onItemChanged(Group group, int i, Object obj) {
        this.observable.onItemChanged(this, getItemCountBeforeGroup(group) + i, obj);
    }

    public void onItemRemoved(Group group, int i) {
        this.observable.onItemRemoved(this, getItemCountBeforeGroup(group) + i);
    }

    public void onItemRangeChanged(Group group, int i, int i2) {
        this.observable.onItemRangeChanged(this, getItemCountBeforeGroup(group) + i, i2);
    }

    public void onItemRangeChanged(Group group, int i, int i2, Object obj) {
        this.observable.onItemRangeChanged(this, getItemCountBeforeGroup(group) + i, i2, obj);
    }

    public void onItemRangeInserted(Group group, int i, int i2) {
        this.observable.onItemRangeInserted(this, getItemCountBeforeGroup(group) + i, i2);
    }

    public void onItemRangeRemoved(Group group, int i, int i2) {
        this.observable.onItemRangeRemoved(this, getItemCountBeforeGroup(group) + i, i2);
    }

    public void onItemMoved(Group group, int i, int i2) {
        int itemCountBeforeGroup = getItemCountBeforeGroup(group);
        this.observable.onItemMoved(this, i + itemCountBeforeGroup, itemCountBeforeGroup + i2);
    }

    public void onDataSetInvalidated() {
        this.observable.onDataSetInvalidated();
    }

    public void notifyItemRangeInserted(int i, int i2) {
        this.observable.onItemRangeInserted(this, i, i2);
    }

    public void notifyItemRangeRemoved(int i, int i2) {
        this.observable.onItemRangeRemoved(this, i, i2);
    }

    public void notifyItemMoved(int i, int i2) {
        this.observable.onItemMoved(this, i, i2);
    }

    public void notifyChanged() {
        this.observable.onChanged(this);
    }

    public void notifyItemInserted(int i) {
        this.observable.onItemInserted(this, i);
    }

    public void notifyItemChanged(int i) {
        this.observable.onItemChanged(this, i);
    }

    public void notifyItemChanged(int i, Object obj) {
        this.observable.onItemChanged(this, i, obj);
    }

    public void notifyItemRemoved(int i) {
        this.observable.onItemRemoved(this, i);
    }

    public void notifyItemRangeChanged(int i, int i2) {
        this.observable.onItemRangeChanged(this, i, i2);
    }

    public void notifyItemRangeChanged(int i, int i2, Object obj) {
        this.observable.onItemRangeChanged(this, i, i2, obj);
    }

    public void notifyDataSetInvalidated() {
        this.observable.onDataSetInvalidated();
    }

    private static class GroupDataObservable {
        final List<GroupDataObserver> observers;

        private GroupDataObservable() {
            this.observers = new ArrayList();
        }

        void onItemRangeChanged(Group group, int i, int i2) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemRangeChanged(group, i, i2);
            }
        }

        void onItemRangeChanged(Group group, int i, int i2, Object obj) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemRangeChanged(group, i, i2, obj);
            }
        }

        void onItemInserted(Group group, int i) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemInserted(group, i);
            }
        }

        void onItemChanged(Group group, int i) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemChanged(group, i);
            }
        }

        void onItemChanged(Group group, int i, Object obj) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemChanged(group, i, obj);
            }
        }

        void onItemRemoved(Group group, int i) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemRemoved(group, i);
            }
        }

        void onItemRangeInserted(Group group, int i, int i2) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemRangeInserted(group, i, i2);
            }
        }

        void onItemRangeRemoved(Group group, int i, int i2) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemRangeRemoved(group, i, i2);
            }
        }

        void onItemMoved(Group group, int i, int i2) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onItemMoved(group, i, i2);
            }
        }

        void onChanged(Group group) {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onChanged(group);
            }
        }

        void registerObserver(GroupDataObserver groupDataObserver) {
            synchronized (this.observers) {
                if (this.observers.contains(groupDataObserver)) {
                    throw new IllegalStateException("Observer " + groupDataObserver + " is already registered.");
                }
                this.observers.add(groupDataObserver);
            }
        }

        void unregisterObserver(GroupDataObserver groupDataObserver) {
            synchronized (this.observers) {
                this.observers.remove(this.observers.indexOf(groupDataObserver));
            }
        }

        void onDataSetInvalidated() {
            for (int size = this.observers.size() - 1; size >= 0; size--) {
                this.observers.get(size).onDataSetInvalidated();
            }
        }
    }
}
