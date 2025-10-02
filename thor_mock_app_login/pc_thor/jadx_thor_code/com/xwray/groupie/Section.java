package com.xwray.groupie;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public class Section extends NestedGroup {
    private final ArrayList<Group> children;
    private Group footer;
    private Group header;
    private boolean hideWhenEmpty;
    private boolean isHeaderAndFooterVisible;
    private boolean isPlaceholderVisible;
    private ListUpdateCallback listUpdateCallback;
    private Group placeholder;

    public Section() {
        this(null, new ArrayList());
    }

    public Section(Group group) {
        this(group, new ArrayList());
    }

    public Section(Collection<? extends Group> collection) {
        this(null, collection);
    }

    public Section(Group group, Collection<? extends Group> collection) {
        this.children = new ArrayList<>();
        this.hideWhenEmpty = false;
        this.isHeaderAndFooterVisible = true;
        this.isPlaceholderVisible = false;
        this.listUpdateCallback = new ListUpdateCallback() { // from class: com.xwray.groupie.Section.1
            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public void onInserted(int i, int i2) {
                Section section = Section.this;
                section.notifyItemRangeInserted(section.getHeaderItemCount() + i, i2);
            }

            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public void onRemoved(int i, int i2) {
                Section section = Section.this;
                section.notifyItemRangeRemoved(section.getHeaderItemCount() + i, i2);
            }

            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public void onMoved(int i, int i2) {
                int headerItemCount = Section.this.getHeaderItemCount();
                Section.this.notifyItemMoved(i + headerItemCount, headerItemCount + i2);
            }

            @Override // androidx.recyclerview.widget.ListUpdateCallback
            public void onChanged(int i, int i2, Object obj) {
                Section section = Section.this;
                section.notifyItemRangeChanged(section.getHeaderItemCount() + i, i2, obj);
            }
        };
        this.header = group;
        if (group != null) {
            group.registerGroupDataObserver(this);
        }
        addAll(collection);
    }

    @Override // com.xwray.groupie.NestedGroup
    public void add(int i, Group group) {
        super.add(i, group);
        this.children.add(i, group);
        notifyItemRangeInserted(getHeaderItemCount() + GroupUtils.getItemCount(this.children.subList(0, i)), group.getItemCount());
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup
    public void addAll(Collection<? extends Group> collection) {
        if (collection.isEmpty()) {
            return;
        }
        super.addAll(collection);
        int itemCountWithoutFooter = getItemCountWithoutFooter();
        this.children.addAll(collection);
        notifyItemRangeInserted(itemCountWithoutFooter, GroupUtils.getItemCount(collection));
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup
    public void addAll(int i, Collection<? extends Group> collection) {
        if (collection.isEmpty()) {
            return;
        }
        super.addAll(i, collection);
        this.children.addAll(i, collection);
        notifyItemRangeInserted(getHeaderItemCount() + GroupUtils.getItemCount(this.children.subList(0, i)), GroupUtils.getItemCount(collection));
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup
    public void add(Group group) {
        super.add(group);
        int itemCountWithoutFooter = getItemCountWithoutFooter();
        this.children.add(group);
        notifyItemRangeInserted(itemCountWithoutFooter, group.getItemCount());
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup
    public void remove(Group group) {
        super.remove(group);
        int itemCountBeforeGroup = getItemCountBeforeGroup(group);
        this.children.remove(group);
        notifyItemRangeRemoved(itemCountBeforeGroup, group.getItemCount());
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup
    public void removeAll(Collection<? extends Group> collection) {
        if (collection.isEmpty()) {
            return;
        }
        super.removeAll(collection);
        for (Group group : collection) {
            int itemCountBeforeGroup = getItemCountBeforeGroup(group);
            this.children.remove(group);
            notifyItemRangeRemoved(itemCountBeforeGroup, group.getItemCount());
        }
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup
    public void replaceAll(Collection<? extends Group> collection) {
        if (collection.isEmpty()) {
            return;
        }
        super.replaceAll(collection);
        this.children.clear();
        this.children.addAll(collection);
        notifyDataSetInvalidated();
        refreshEmptyState();
    }

    public List<Group> getGroups() {
        return new ArrayList(this.children);
    }

    public void clear() {
        if (this.children.isEmpty()) {
            return;
        }
        removeAll(new ArrayList(this.children));
    }

    public void update(Collection<? extends Group> collection) {
        update(collection, true);
    }

    public void update(Collection<? extends Group> collection, boolean z) {
        update(collection, DiffUtil.calculateDiff(new DiffCallback(new ArrayList(this.children), collection), z));
    }

    public void update(Collection<? extends Group> collection, DiffUtil.DiffResult diffResult) {
        super.removeAll(this.children);
        this.children.clear();
        this.children.addAll(collection);
        super.addAll(collection);
        diffResult.dispatchUpdatesTo(this.listUpdateCallback);
        refreshEmptyState();
    }

    public void setPlaceholder(Group group) {
        if (group == null) {
            throw new NullPointerException("Placeholder can't be null.  Please use removePlaceholder() instead!");
        }
        if (this.placeholder != null) {
            removePlaceholder();
        }
        this.placeholder = group;
        refreshEmptyState();
    }

    public void removePlaceholder() {
        hidePlaceholder();
        this.placeholder = null;
    }

    private void showPlaceholder() {
        if (this.isPlaceholderVisible || this.placeholder == null) {
            return;
        }
        this.isPlaceholderVisible = true;
        notifyItemRangeInserted(getHeaderItemCount(), this.placeholder.getItemCount());
    }

    private void hidePlaceholder() {
        if (!this.isPlaceholderVisible || this.placeholder == null) {
            return;
        }
        this.isPlaceholderVisible = false;
        notifyItemRangeRemoved(getHeaderItemCount(), this.placeholder.getItemCount());
    }

    protected boolean isEmpty() {
        return this.children.isEmpty() || GroupUtils.getItemCount(this.children) == 0;
    }

    private void hideDecorations() {
        if (this.isHeaderAndFooterVisible || this.isPlaceholderVisible) {
            int headerItemCount = getHeaderItemCount() + getPlaceholderItemCount() + getFooterItemCount();
            this.isHeaderAndFooterVisible = false;
            this.isPlaceholderVisible = false;
            notifyItemRangeRemoved(0, headerItemCount);
        }
    }

    protected void refreshEmptyState() {
        if (isEmpty()) {
            if (this.hideWhenEmpty) {
                hideDecorations();
                return;
            } else {
                showPlaceholder();
                showHeadersAndFooters();
                return;
            }
        }
        hidePlaceholder();
        showHeadersAndFooters();
    }

    private void showHeadersAndFooters() {
        if (this.isHeaderAndFooterVisible) {
            return;
        }
        this.isHeaderAndFooterVisible = true;
        notifyItemRangeInserted(0, getHeaderItemCount());
        notifyItemRangeInserted(getItemCountWithoutFooter(), getFooterItemCount());
    }

    private int getBodyItemCount() {
        return this.isPlaceholderVisible ? getPlaceholderItemCount() : GroupUtils.getItemCount(this.children);
    }

    private int getItemCountWithoutFooter() {
        return getBodyItemCount() + getHeaderItemCount();
    }

    private int getHeaderCount() {
        return (this.header == null || !this.isHeaderAndFooterVisible) ? 0 : 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getHeaderItemCount() {
        if (getHeaderCount() == 0) {
            return 0;
        }
        return this.header.getItemCount();
    }

    private int getFooterItemCount() {
        if (getFooterCount() == 0) {
            return 0;
        }
        return this.footer.getItemCount();
    }

    private int getFooterCount() {
        return (this.footer == null || !this.isHeaderAndFooterVisible) ? 0 : 1;
    }

    private int getPlaceholderCount() {
        return this.isPlaceholderVisible ? 1 : 0;
    }

    @Override // com.xwray.groupie.NestedGroup
    public Group getGroup(int i) {
        if (isHeaderShown() && i == 0) {
            return this.header;
        }
        int headerCount = i - getHeaderCount();
        if (isPlaceholderShown() && headerCount == 0) {
            return this.placeholder;
        }
        int placeholderCount = headerCount - getPlaceholderCount();
        if (placeholderCount == this.children.size()) {
            if (isFooterShown()) {
                return this.footer;
            }
            throw new IndexOutOfBoundsException("Wanted group at position " + placeholderCount + " but there are only " + getGroupCount() + " groups");
        }
        return this.children.get(placeholderCount);
    }

    @Override // com.xwray.groupie.NestedGroup
    public int getGroupCount() {
        return getHeaderCount() + getFooterCount() + getPlaceholderCount() + this.children.size();
    }

    @Override // com.xwray.groupie.NestedGroup
    public int getPosition(Group group) {
        if (isHeaderShown() && group == this.header) {
            return 0;
        }
        int headerCount = 0 + getHeaderCount();
        if (isPlaceholderShown() && group == this.placeholder) {
            return headerCount;
        }
        int placeholderCount = headerCount + getPlaceholderCount();
        int iIndexOf = this.children.indexOf(group);
        if (iIndexOf >= 0) {
            return placeholderCount + iIndexOf;
        }
        int size = placeholderCount + this.children.size();
        if (isFooterShown() && this.footer == group) {
            return size;
        }
        return -1;
    }

    private boolean isHeaderShown() {
        return getHeaderCount() > 0;
    }

    private boolean isFooterShown() {
        return getFooterCount() > 0;
    }

    private boolean isPlaceholderShown() {
        return getPlaceholderCount() > 0;
    }

    public void setHeader(Group group) {
        if (group == null) {
            throw new NullPointerException("Header can't be null.  Please use removeHeader() instead!");
        }
        Group group2 = this.header;
        if (group2 != null) {
            group2.unregisterGroupDataObserver(this);
        }
        int headerItemCount = getHeaderItemCount();
        this.header = group;
        group.registerGroupDataObserver(this);
        notifyHeaderItemsChanged(headerItemCount);
    }

    public void removeHeader() {
        Group group = this.header;
        if (group == null) {
            return;
        }
        group.unregisterGroupDataObserver(this);
        int headerItemCount = getHeaderItemCount();
        this.header = null;
        notifyHeaderItemsChanged(headerItemCount);
    }

    private void notifyHeaderItemsChanged(int i) {
        int headerItemCount = getHeaderItemCount();
        if (i > 0) {
            notifyItemRangeRemoved(0, i);
        }
        if (headerItemCount > 0) {
            notifyItemRangeInserted(0, headerItemCount);
        }
    }

    public void setFooter(Group group) {
        if (group == null) {
            throw new NullPointerException("Footer can't be null.  Please use removeFooter() instead!");
        }
        Group group2 = this.footer;
        if (group2 != null) {
            group2.unregisterGroupDataObserver(this);
        }
        int footerItemCount = getFooterItemCount();
        this.footer = group;
        group.registerGroupDataObserver(this);
        notifyFooterItemsChanged(footerItemCount);
    }

    public void removeFooter() {
        Group group = this.footer;
        if (group == null) {
            return;
        }
        group.unregisterGroupDataObserver(this);
        int footerItemCount = getFooterItemCount();
        this.footer = null;
        notifyFooterItemsChanged(footerItemCount);
    }

    private void notifyFooterItemsChanged(int i) {
        int footerItemCount = getFooterItemCount();
        if (i > 0) {
            notifyItemRangeRemoved(getItemCountWithoutFooter(), i);
        }
        if (footerItemCount > 0) {
            notifyItemRangeInserted(getItemCountWithoutFooter(), footerItemCount);
        }
    }

    public void setHideWhenEmpty(boolean z) {
        if (this.hideWhenEmpty == z) {
            return;
        }
        this.hideWhenEmpty = z;
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemInserted(Group group, int i) {
        super.onItemInserted(group, i);
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRemoved(Group group, int i) {
        super.onItemRemoved(group, i);
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRangeInserted(Group group, int i, int i2) {
        super.onItemRangeInserted(group, i, i2);
        refreshEmptyState();
    }

    @Override // com.xwray.groupie.NestedGroup, com.xwray.groupie.GroupDataObserver
    public void onItemRangeRemoved(Group group, int i, int i2) {
        super.onItemRangeRemoved(group, i, i2);
        refreshEmptyState();
    }

    private int getPlaceholderItemCount() {
        Group group;
        if (!this.isPlaceholderVisible || (group = this.placeholder) == null) {
            return 0;
        }
        return group.getItemCount();
    }
}
