package com.thor.app.gui.adapters.demo;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.ItemDemoEvDividerBinding;
import com.carsystems.thor.app.databinding.ItemDemoSoundPackageBinding;
import com.carsystems.thor.app.databinding.ItemTachometerWithEqualizeBinding;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.thor.app.gui.views.demo.DemoSoundPackageView;
import com.thor.app.gui.views.tachometer.TachometerWithEqualizeView;
import com.thor.app.managers.DemoSoundPackageManager;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.businessmodule.model.DemoSoundPackage;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DemoSoundPackagesRvAdapter.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0004%&'(B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fH\u0002J\b\u0010\u0011\u001a\u00020\rH\u0016J\b\u0010\u0012\u001a\u00020\rH\u0002J\u0016\u0010\u0013\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016J\u0006\u0010\u0017\u001a\u00020\tJ\b\u0010\u0018\u001a\u00020\rH\u0002J\u0010\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\u0018\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0015H\u0016J\u0018\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0015H\u0016J\u0006\u0010\"\u001a\u00020\rJ\u0010\u0010#\u001a\u00020\r2\b\u0010$\u001a\u0004\u0018\u00010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter$DemoSoundPackageItem;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "isCarUiMode", "", "(Z)V", "()Z", "mOnNestedScrollListener", "Landroidx/core/widget/NestedScrollView$OnScrollChangeListener;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "addEvDivider", "", "list", "", "Lcom/thor/businessmodule/model/DemoSoundPackage;", "clear", "deactivateAll", "fillAdapter", "getItemViewType", "", "position", "getOnNestedScrollListener", "initNestedScrollListener", "markPackageAsActive", "presetId", "", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "release", "setRecyclerView", "rv", "DemoEvDividerViewHolder", "DemoSoundPackageItem", "DemoSoundPackageViewHolder", "TachometerWithEqualizeViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoSoundPackagesRvAdapter extends BaseRecyclerListAdapter<DemoSoundPackageItem, RecyclerView.ViewHolder> {
    private final boolean isCarUiMode;
    private NestedScrollView.OnScrollChangeListener mOnNestedScrollListener;
    private RecyclerView mRecyclerView;

    public DemoSoundPackagesRvAdapter() {
        this(false, 1, null);
    }

    public /* synthetic */ DemoSoundPackagesRvAdapter(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    /* renamed from: isCarUiMode, reason: from getter */
    public final boolean getIsCarUiMode() {
        return this.isCarUiMode;
    }

    public DemoSoundPackagesRvAdapter(boolean z) {
        this.isCarUiMode = z;
        if (!z) {
            addItem(new DemoSoundPackageItem(0));
        }
        initNestedScrollListener();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        if (viewType == 0) {
            Intrinsics.checkNotNull(layoutInflater);
            View root = ItemTachometerWithEqualizeBinding.inflate(layoutInflater, parent, false).getRoot();
            Intrinsics.checkNotNullExpressionValue(root, "binding.root");
            return new TachometerWithEqualizeViewHolder(this, root);
        }
        if (viewType == 2) {
            Intrinsics.checkNotNull(layoutInflater);
            View root2 = ItemDemoEvDividerBinding.inflate(layoutInflater, parent, false).getRoot();
            Intrinsics.checkNotNullExpressionValue(root2, "binding.root");
            return new DemoEvDividerViewHolder(this, root2);
        }
        Intrinsics.checkNotNull(layoutInflater);
        View root3 = ItemDemoSoundPackageBinding.inflate(layoutInflater, parent, false).getRoot();
        Intrinsics.checkNotNullExpressionValue(root3, "binding.root");
        return new DemoSoundPackageViewHolder(this, root3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TachometerWithEqualizeView tachometerWithEqualizeView;
        DemoSoundPackageView demoSoundPackageView;
        DemoSoundPackageView demoSoundPackageView2;
        Intrinsics.checkNotNullParameter(holder, "holder");
        final DemoSoundPackageItem demoSoundPackageItem = (DemoSoundPackageItem) this.mList.get(position);
        int type = demoSoundPackageItem.getType();
        if (type == 0) {
            TachometerWithEqualizeViewHolder tachometerWithEqualizeViewHolder = (TachometerWithEqualizeViewHolder) holder;
            DemoSoundPackageManager demoSoundPackageManager = DemoSoundPackageManager.INSTANCE;
            ItemTachometerWithEqualizeBinding binding = tachometerWithEqualizeViewHolder.getBinding();
            demoSoundPackageManager.setTachometer(binding != null ? binding.viewTachometerWithEqualize : null);
            ItemTachometerWithEqualizeBinding binding2 = tachometerWithEqualizeViewHolder.getBinding();
            if (binding2 == null || (tachometerWithEqualizeView = binding2.viewTachometerWithEqualize) == null) {
                return;
            }
            DemoSoundPackage demoSoundPackage = demoSoundPackageItem.getDemoSoundPackage();
            tachometerWithEqualizeView.setPresetName(demoSoundPackage != null ? demoSoundPackage.getName() : null);
            return;
        }
        if (type != 1) {
            if (type != 2) {
                return;
            }
            return;
        }
        DemoSoundPackageViewHolder demoSoundPackageViewHolder = (DemoSoundPackageViewHolder) holder;
        ItemDemoSoundPackageBinding binding3 = demoSoundPackageViewHolder.getBinding();
        if (binding3 != null && (demoSoundPackageView2 = binding3.demoSoundPackageView) != null) {
            DemoSoundPackage demoSoundPackage2 = demoSoundPackageItem.getDemoSoundPackage();
            Intrinsics.checkNotNull(demoSoundPackage2);
            demoSoundPackageView2.setDemoSoundPackage(demoSoundPackage2);
        }
        ItemDemoSoundPackageBinding binding4 = demoSoundPackageViewHolder.getBinding();
        if (binding4 == null || (demoSoundPackageView = binding4.demoSoundPackageView) == null) {
            return;
        }
        demoSoundPackageView.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.demo.DemoSoundPackagesRvAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DemoSoundPackagesRvAdapter.onBindViewHolder$lambda$0(this.f$0, demoSoundPackageItem, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(DemoSoundPackagesRvAdapter this$0, DemoSoundPackageItem demoSoundPackageItem, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = this$0.mOnItemClickListener;
        if (onItemClickListener != 0) {
            onItemClickListener.onItemClick((RecyclerCollectionAdapter.OnItemClickListener<M>) demoSoundPackageItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return ((DemoSoundPackageItem) this.mList.get(position)).getType();
    }

    @Override // com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter, com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void clear() {
        if (getItemCount() > 1) {
            ListIterator listIterator = this.mList.listIterator();
            while (listIterator.hasNext()) {
                DemoSoundPackageItem demoSoundPackageItem = (DemoSoundPackageItem) listIterator.next();
                boolean z = false;
                if (demoSoundPackageItem != null && demoSoundPackageItem.getType() == 1) {
                    z = true;
                }
                if (z) {
                    listIterator.remove();
                }
            }
        }
        notifyDataSetChanged();
    }

    public final void fillAdapter(List<DemoSoundPackage> list) {
        if (list == null) {
            return;
        }
        if (list.isEmpty()) {
            clear();
            return;
        }
        clear();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            addItem(new DemoSoundPackageItem((DemoSoundPackage) it.next(), 1));
        }
    }

    public final NestedScrollView.OnScrollChangeListener getOnNestedScrollListener() {
        NestedScrollView.OnScrollChangeListener onScrollChangeListener = this.mOnNestedScrollListener;
        if (onScrollChangeListener != null) {
            return onScrollChangeListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mOnNestedScrollListener");
        return null;
    }

    public final void setRecyclerView(RecyclerView rv) {
        this.mRecyclerView = rv;
    }

    public final void release() {
        DemoSoundPackageManager.INSTANCE.release();
    }

    public final void markPackageAsActive(String presetId) {
        Object next;
        String id;
        Iterable mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        Iterator it = mList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            DemoSoundPackage demoSoundPackage = ((DemoSoundPackageItem) next).getDemoSoundPackage();
            if ((demoSoundPackage == null || (id = demoSoundPackage.getId()) == null) ? false : id.equals(presetId)) {
                break;
            }
        }
        DemoSoundPackageItem demoSoundPackageItem = (DemoSoundPackageItem) next;
        deactivateAll();
        RecyclerView recyclerView = this.mRecyclerView;
        KeyEvent.Callback childAt = recyclerView != null ? recyclerView.getChildAt(this.mList.indexOf(demoSoundPackageItem)) : null;
        DemoSoundPackageView demoSoundPackageView = childAt instanceof DemoSoundPackageView ? (DemoSoundPackageView) childAt : null;
        if (demoSoundPackageView != null) {
            demoSoundPackageView.onActivate();
        }
    }

    private final void deactivateAll() {
        Iterable<DemoSoundPackageItem> mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        for (DemoSoundPackageItem demoSoundPackageItem : mList) {
            RecyclerView recyclerView = this.mRecyclerView;
            View childAt = recyclerView != null ? recyclerView.getChildAt(this.mList.indexOf(demoSoundPackageItem)) : null;
            DemoSoundPackageView demoSoundPackageView = childAt instanceof DemoSoundPackageView ? (DemoSoundPackageView) childAt : null;
            if (demoSoundPackageView != null) {
                demoSoundPackageView.onDeactivate();
            }
        }
    }

    private final void initNestedScrollListener() {
        this.mOnNestedScrollListener = new NestedScrollView.OnScrollChangeListener() { // from class: com.thor.app.gui.adapters.demo.DemoSoundPackagesRvAdapter$$ExternalSyntheticLambda1
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                DemoSoundPackagesRvAdapter.initNestedScrollListener$lambda$8(this.f$0, nestedScrollView, i, i2, i3, i4);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNestedScrollListener$lambda$8(DemoSoundPackagesRvAdapter this$0, NestedScrollView p0, int i, int i2, int i3, int i4) {
        View childAt;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(p0, "p0");
        int itemCount = this$0.getItemCount();
        for (int i5 = 1; i5 < itemCount; i5++) {
            RecyclerView recyclerView = this$0.mRecyclerView;
            if (recyclerView != null && (childAt = recyclerView.getChildAt(i5)) != null) {
                DemoSoundPackageView demoSoundPackageView = childAt instanceof DemoSoundPackageView ? (DemoSoundPackageView) childAt : null;
                if (demoSoundPackageView != null) {
                    RecyclerView recyclerView2 = this$0.mRecyclerView;
                    Integer numValueOf = recyclerView2 != null ? Integer.valueOf(recyclerView2.getHeight()) : null;
                    Intrinsics.checkNotNull(numValueOf);
                    demoSoundPackageView.onNestedScrollChanged(i2, i4, numValueOf.intValue());
                }
            }
        }
    }

    /* compiled from: DemoSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter$TachometerWithEqualizeViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemTachometerWithEqualizeBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemTachometerWithEqualizeBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class TachometerWithEqualizeViewHolder extends RecyclerView.ViewHolder {
        private final ItemTachometerWithEqualizeBinding binding;
        final /* synthetic */ DemoSoundPackagesRvAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TachometerWithEqualizeViewHolder(DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = demoSoundPackagesRvAdapter;
            this.binding = (ItemTachometerWithEqualizeBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemTachometerWithEqualizeBinding getBinding() {
            return this.binding;
        }
    }

    /* compiled from: DemoSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter$DemoSoundPackageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemDemoSoundPackageBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemDemoSoundPackageBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class DemoSoundPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemDemoSoundPackageBinding binding;
        final /* synthetic */ DemoSoundPackagesRvAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DemoSoundPackageViewHolder(DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = demoSoundPackagesRvAdapter;
            this.binding = (ItemDemoSoundPackageBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemDemoSoundPackageBinding getBinding() {
            return this.binding;
        }
    }

    /* compiled from: DemoSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter$DemoEvDividerViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemDemoEvDividerBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemDemoEvDividerBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class DemoEvDividerViewHolder extends RecyclerView.ViewHolder {
        private final ItemDemoEvDividerBinding binding;
        final /* synthetic */ DemoSoundPackagesRvAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DemoEvDividerViewHolder(DemoSoundPackagesRvAdapter demoSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = demoSoundPackagesRvAdapter;
            this.binding = (ItemDemoEvDividerBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemDemoEvDividerBinding getBinding() {
            return this.binding;
        }
    }

    /* compiled from: DemoSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSoundPackagesRvAdapter$DemoSoundPackageItem;", "", "soundPackage", "Lcom/thor/businessmodule/model/DemoSoundPackage;", SessionDescription.ATTR_TYPE, "", "(Lcom/thor/businessmodule/model/DemoSoundPackage;I)V", "(I)V", "demoSoundPackage", "getDemoSoundPackage", "()Lcom/thor/businessmodule/model/DemoSoundPackage;", "setDemoSoundPackage", "(Lcom/thor/businessmodule/model/DemoSoundPackage;)V", "getType", "()I", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DemoSoundPackageItem {
        public static final int TYPE_DEMO_EV_DIVIDER = 2;
        public static final int TYPE_DEMO_SOUND_PACKAGE = 1;
        public static final int TYPE_TACHOMETER_WITH_EQUALIZE = 0;
        private DemoSoundPackage demoSoundPackage;
        private final int type;

        public DemoSoundPackageItem(int i) {
            this.type = i;
        }

        public final int getType() {
            return this.type;
        }

        public final DemoSoundPackage getDemoSoundPackage() {
            return this.demoSoundPackage;
        }

        public final void setDemoSoundPackage(DemoSoundPackage demoSoundPackage) {
            this.demoSoundPackage = demoSoundPackage;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public DemoSoundPackageItem(DemoSoundPackage soundPackage, int i) {
            this(i);
            Intrinsics.checkNotNullParameter(soundPackage, "soundPackage");
            this.demoSoundPackage = soundPackage;
        }
    }

    private final void addEvDivider(List<DemoSoundPackage> list) {
        Integer numValueOf;
        if (list != null) {
            Iterator<DemoSoundPackage> it = list.iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                } else {
                    if (it.next().getCategoryType() == DemoSoundPackage.FuelCategory.EV) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            numValueOf = Integer.valueOf(i);
        } else {
            numValueOf = null;
        }
        if (numValueOf != null) {
            numValueOf.intValue();
            addItem(numValueOf.intValue() + 1, new DemoSoundPackageItem(2));
        }
    }
}
