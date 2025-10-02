package com.thor.app.gui.adapters.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.ItemSguSoundConfigBinding;
import com.carsystems.thor.app.databinding.ViewSguSoundConfigBinding;
import com.thor.app.databinding.model.DriveSelectStatus;
import com.thor.app.gui.adapters.itemtouch.ItemMoveCallback;
import com.thor.app.gui.views.sgu.SguSoundConfigView;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.converters.JsonConverterKt;
import com.thor.app.utils.converters.mappers.MappersKt;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import com.thor.networkmodule.model.responses.sgu.SguSoundJson;
import com.thor.networkmodule.model.responses.sgu.SguSoundJsonWrapper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: SguSoundPackegesRvAdapter.kt */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u00012\u00020\u0004:\u0001-B\u0007\b\u0016¢\u0006\u0002\u0010\u0005B\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eJ\u0006\u0010\u0016\u001a\u00020\u0010J\b\u0010\u0017\u001a\u00020\u0014H\u0002J\u0016\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\u001c\u0010\u001d\u001a\u00020\u00142\n\u0010\u001e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u001f\u001a\u00020 H\u0016J\u001c\u0010!\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020 H\u0016J\u0016\u0010%\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010&\u001a\u00020\u0014H\u0016J\u0018\u0010'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020 H\u0016J\b\u0010*\u001a\u00020\u0014H\u0002J\u0010\u0010+\u001a\u00020\u00142\b\u0010,\u001a\u0004\u0018\u00010\u0012R\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/thor/app/gui/adapters/main/SguSoundPackagesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "Lcom/thor/app/gui/adapters/main/SguSoundPackagesRvAdapter$SguSoundPackageViewHolder;", "Lcom/thor/app/gui/adapters/itemtouch/ItemMoveCallback$OnItemMoveListener;", "()V", "diffUtilCallback", "Lcom/thor/app/gui/adapters/main/SguSoundPackagesDiffUtilCallback;", "(Lcom/thor/app/gui/adapters/main/SguSoundPackagesDiffUtilCallback;)V", "driveSelectList", "Ljava/util/ArrayList;", "Lcom/thor/app/databinding/model/DriveSelectStatus;", "Lkotlin/collections/ArrayList;", "mDataLayerContext", "Landroid/content/Context;", "mOnNestedScrollListener", "Landroidx/core/widget/NestedScrollView$OnScrollChangeListener;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "enableSyncData", "", "context", "getOnNestedScrollListener", "initNestedScrollListener", "onActivate", "binding", "Lcom/carsystems/thor/app/databinding/ViewSguSoundConfigBinding;", "view", "Landroid/view/View;", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onDeactivate", "onFinishMoving", "onItemMove", "fromPosition", "toPosition", "onWritePresets", "setRecyclerView", "rv", "SguSoundPackageViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundPackagesRvAdapter extends BaseRecyclerListAdapter<SguSound, SguSoundPackageViewHolder> implements ItemMoveCallback.OnItemMoveListener {
    private ArrayList<DriveSelectStatus> driveSelectList;
    private Context mDataLayerContext;
    private NestedScrollView.OnScrollChangeListener mOnNestedScrollListener;
    private RecyclerView mRecyclerView;

    public SguSoundPackagesRvAdapter() {
        this.driveSelectList = new ArrayList<>();
        initNestedScrollListener();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SguSoundPackagesRvAdapter(SguSoundPackagesDiffUtilCallback diffUtilCallback) {
        super(diffUtilCallback);
        Intrinsics.checkNotNullParameter(diffUtilCallback, "diffUtilCallback");
        this.driveSelectList = new ArrayList<>();
        initNestedScrollListener();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SguSoundPackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ItemSguSoundConfigBinding itemSguSoundConfigBindingInflate = ItemSguSoundConfigBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemSguSoundConfigBindingInflate, "inflate(inflater!!, parent, false)");
        View root = itemSguSoundConfigBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new SguSoundPackageViewHolder(this, root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final SguSoundPackageViewHolder holder, final int position) {
        SguSoundConfigView sguSoundConfigView;
        AppCompatImageView configView;
        View root;
        SguSoundConfigView sguSoundConfigView2;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Timber.INSTANCE.i("onBindViewHolder " + position, new Object[0]);
        ItemSguSoundConfigBinding binding = holder.getBinding();
        if (binding != null && (sguSoundConfigView2 = binding.viewSoundPreset) != null) {
            SguSound item = getItem(position);
            Intrinsics.checkNotNullExpressionValue(item, "getItem(position)");
            sguSoundConfigView2.setSoundPackage(item);
        }
        ItemSguSoundConfigBinding binding2 = holder.getBinding();
        if (binding2 != null && (root = binding2.getRoot()) != null) {
            root.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SguSoundPackagesRvAdapter.onBindViewHolder$lambda$0(this.f$0, holder, position, view);
                }
            });
        }
        ItemSguSoundConfigBinding binding3 = holder.getBinding();
        if (binding3 == null || (sguSoundConfigView = binding3.viewSoundPreset) == null || (configView = sguSoundConfigView.getConfigView()) == null) {
            return;
        }
        configView.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SguSoundPackagesRvAdapter.onBindViewHolder$lambda$1(this.f$0, position, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(SguSoundPackagesRvAdapter this$0, SguSoundPackageViewHolder holder, int i, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        ViewSguSoundConfigBinding binding = holder.getBinding().viewSoundPreset.getBinding();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        this$0.onActivate(binding, it);
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = this$0.mOnItemClickListener;
        if (onItemClickListener != 0) {
            onItemClickListener.onItemClick((RecyclerCollectionAdapter.OnItemClickListener<M>) this$0.mList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(SguSoundPackagesRvAdapter this$0, int i, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerCollectionAdapter.OnRecyclerActionConfigListener<M> onRecyclerActionConfigListener = this$0.mOnItemConfigClickListener;
        if (onRecyclerActionConfigListener != 0) {
            onRecyclerActionConfigListener.onConfig(this$0.mList.get(i));
        }
    }

    /* compiled from: SguSoundPackegesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/main/SguSoundPackagesRvAdapter$SguSoundPackageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/main/SguSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemSguSoundConfigBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemSguSoundConfigBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class SguSoundPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemSguSoundConfigBinding binding;
        final /* synthetic */ SguSoundPackagesRvAdapter this$0;

        public final ItemSguSoundConfigBinding getBinding() {
            return this.binding;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SguSoundPackageViewHolder(SguSoundPackagesRvAdapter sguSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = sguSoundPackagesRvAdapter;
            this.binding = (ItemSguSoundConfigBinding) DataBindingUtil.bind(this.itemView);
        }
    }

    public final void onActivate(final ViewSguSoundConfigBinding binding, final View view) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Intrinsics.checkNotNullParameter(view, "view");
        view.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SguSoundPackagesRvAdapter.onActivate$lambda$3(binding, this, view);
            }
        });
        binding.viewCoverRedGradient.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivate$lambda$3(final ViewSguSoundConfigBinding binding, final SguSoundPackagesRvAdapter this$0, final View view) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        binding.imageViewCover.animate().scaleX(1.1f).scaleY(1.1f).setDuration(300L).withEndAction(new Runnable() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SguSoundPackagesRvAdapter.onActivate$lambda$3$lambda$2(this.f$0, binding, view);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivate$lambda$3$lambda$2(SguSoundPackagesRvAdapter this$0, ViewSguSoundConfigBinding binding, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(binding, "$binding");
        Intrinsics.checkNotNullParameter(view, "$view");
        this$0.onDeactivate(binding, view);
    }

    public final void onDeactivate(final ViewSguSoundConfigBinding binding, View view) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Intrinsics.checkNotNullParameter(view, "view");
        Timber.INSTANCE.i("onDeactivate", new Object[0]);
        view.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SguSoundPackagesRvAdapter.onDeactivate$lambda$5(binding);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDeactivate$lambda$5(final ViewSguSoundConfigBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        binding.imageViewCover.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L).withEndAction(new Runnable() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SguSoundPackagesRvAdapter.onDeactivate$lambda$5$lambda$4(binding);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDeactivate$lambda$5$lambda$4(ViewSguSoundConfigBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        binding.viewCoverRedGradient.setVisibility(4);
    }

    @Override // com.thor.app.gui.adapters.itemtouch.ItemMoveCallback.OnItemMoveListener
    public void onItemMove(int fromPosition, int toPosition) {
        Timber.INSTANCE.i("onItemMove - from: %s, to: %s", Integer.valueOf(fromPosition), Integer.valueOf(toPosition));
        SguSound item = getItem(fromPosition);
        this.mList.set(fromPosition, getItem(toPosition));
        this.mList.set(toPosition, item);
        if (fromPosition >= toPosition && fromPosition > toPosition) {
            notifyItemMoved(toPosition, fromPosition);
        } else {
            notifyItemMoved(fromPosition, toPosition);
        }
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
    }

    @Override // com.thor.app.gui.adapters.itemtouch.ItemMoveCallback.OnItemMoveListener
    public void onFinishMoving() {
        onWritePresets();
    }

    public final void setRecyclerView(RecyclerView rv) {
        this.mRecyclerView = rv;
    }

    public final NestedScrollView.OnScrollChangeListener getOnNestedScrollListener() {
        NestedScrollView.OnScrollChangeListener onScrollChangeListener = this.mOnNestedScrollListener;
        if (onScrollChangeListener != null) {
            return onScrollChangeListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mOnNestedScrollListener");
        return null;
    }

    private final void initNestedScrollListener() {
        this.mOnNestedScrollListener = new NestedScrollView.OnScrollChangeListener() { // from class: com.thor.app.gui.adapters.main.SguSoundPackagesRvAdapter$$ExternalSyntheticLambda0
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                SguSoundPackagesRvAdapter.initNestedScrollListener$lambda$7(this.f$0, nestedScrollView, i, i2, i3, i4);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNestedScrollListener$lambda$7(SguSoundPackagesRvAdapter this$0, NestedScrollView p0, int i, int i2, int i3, int i4) {
        View childAt;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(p0, "p0");
        int itemCount = this$0.getItemCount();
        for (int i5 = 0; i5 < itemCount; i5++) {
            RecyclerView recyclerView = this$0.mRecyclerView;
            if (recyclerView != null && (childAt = recyclerView.getChildAt(i5)) != null) {
                SguSoundConfigView sguSoundConfigView = (SguSoundConfigView) childAt;
                RecyclerView recyclerView2 = this$0.mRecyclerView;
                Integer numValueOf = recyclerView2 != null ? Integer.valueOf(recyclerView2.getHeight()) : null;
                Intrinsics.checkNotNull(numValueOf);
                sguSoundConfigView.onNestedScrollChanged(i2, i4, numValueOf.intValue());
            }
        }
    }

    private final void onWritePresets() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || recyclerView.getContext() == null) {
            return;
        }
        List<SguSoundJson> sguSoundJsonModels = MappersKt.toSguSoundJsonModels(this.mList);
        Context context = null;
        List mutableList = sguSoundJsonModels != null ? CollectionsKt.toMutableList((Collection) sguSoundJsonModels) : null;
        if (mutableList != null) {
            String sguSoundJson = JsonConverterKt.toSguSoundJson(new SguSoundJsonWrapper(mutableList));
            Settings.INSTANCE.saveSguSoundFavoritesJSON(sguSoundJson);
            if (this.mDataLayerContext != null) {
                DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
                Context context2 = this.mDataLayerContext;
                if (context2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
                    context2 = null;
                }
                companion.from(context2).sendSguJson(sguSoundJson);
                DataLayerManager.Companion companion2 = DataLayerManager.INSTANCE;
                Context context3 = this.mDataLayerContext;
                if (context3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
                } else {
                    context = context3;
                }
                companion2.from(context).sendSguList(MappersKt.toSguSoundModels(mutableList));
            }
        }
    }

    public final void enableSyncData(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mDataLayerContext = context;
    }
}
