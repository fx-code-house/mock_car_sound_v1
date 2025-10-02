package com.thor.app.gui.adapters.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.ViewDemoSguSoundConfigBinding;
import com.thor.app.databinding.model.DriveSelectStatus;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.businessmodule.model.DemoSguPackage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: DemoSguRvAdapter.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u001eB\u0007\b\u0016¢\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u001c\u0010\u0013\u001a\u00020\u000e2\n\u0010\u0014\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001c\u0010\u0017\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0016H\u0016J\u0016\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u001c\u001a\u00020\u000e2\b\u0010\u001d\u001a\u0004\u0018\u00010\fR\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSguRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/businessmodule/model/DemoSguPackage;", "Lcom/thor/app/gui/adapters/demo/DemoSguRvAdapter$DemoSguViewHolder;", "()V", "driveSelectList", "Ljava/util/ArrayList;", "Lcom/thor/app/databinding/model/DriveSelectStatus;", "Lkotlin/collections/ArrayList;", "mDataLayerContext", "Landroid/content/Context;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "onActivate", "", "binding", "Lcom/carsystems/thor/app/databinding/ViewDemoSguSoundConfigBinding;", "view", "Landroid/view/View;", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onDeactivate", "setRecyclerView", "rv", "DemoSguViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoSguRvAdapter extends BaseRecyclerListAdapter<DemoSguPackage, DemoSguViewHolder> {
    private ArrayList<DriveSelectStatus> driveSelectList = new ArrayList<>();
    private Context mDataLayerContext;
    private RecyclerView mRecyclerView;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public DemoSguViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ViewDemoSguSoundConfigBinding viewDemoSguSoundConfigBindingInflate = ViewDemoSguSoundConfigBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewDemoSguSoundConfigBindingInflate, "inflate(inflater!!, parent, false)");
        View root = viewDemoSguSoundConfigBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new DemoSguViewHolder(this, root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final DemoSguViewHolder holder, final int position) {
        AppCompatImageView appCompatImageView;
        View root;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Timber.INSTANCE.i("onBindViewHolder " + position, new Object[0]);
        ViewDemoSguSoundConfigBinding binding = holder.getBinding();
        if (binding != null) {
            binding.setModel((DemoSguPackage) this.mList.get(position));
        }
        ViewDemoSguSoundConfigBinding binding2 = holder.getBinding();
        if (binding2 != null && (root = binding2.getRoot()) != null) {
            root.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.demo.DemoSguRvAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DemoSguRvAdapter.onBindViewHolder$lambda$0(this.f$0, holder, position, view);
                }
            });
        }
        ViewDemoSguSoundConfigBinding binding3 = holder.getBinding();
        if (binding3 == null || (appCompatImageView = binding3.imageConfig) == null) {
            return;
        }
        appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.demo.DemoSguRvAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DemoSguRvAdapter.onBindViewHolder$lambda$1(this.f$0, position, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(DemoSguRvAdapter this$0, DemoSguViewHolder holder, int i, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        ViewDemoSguSoundConfigBinding binding = holder.getBinding();
        Intrinsics.checkNotNullExpressionValue(it, "it");
        this$0.onActivate(binding, it);
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = this$0.mOnItemClickListener;
        if (onItemClickListener != 0) {
            onItemClickListener.onItemClick((RecyclerCollectionAdapter.OnItemClickListener<M>) this$0.mList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(DemoSguRvAdapter this$0, int i, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerCollectionAdapter.OnRecyclerActionConfigListener<M> onRecyclerActionConfigListener = this$0.mOnItemConfigClickListener;
        if (onRecyclerActionConfigListener != 0) {
            onRecyclerActionConfigListener.onConfig(this$0.mList.get(i));
        }
    }

    /* compiled from: DemoSguRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/demo/DemoSguRvAdapter$DemoSguViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/demo/DemoSguRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ViewDemoSguSoundConfigBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ViewDemoSguSoundConfigBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class DemoSguViewHolder extends RecyclerView.ViewHolder {
        private final ViewDemoSguSoundConfigBinding binding;
        final /* synthetic */ DemoSguRvAdapter this$0;

        public final ViewDemoSguSoundConfigBinding getBinding() {
            return this.binding;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DemoSguViewHolder(DemoSguRvAdapter demoSguRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = demoSguRvAdapter;
            this.binding = (ViewDemoSguSoundConfigBinding) DataBindingUtil.bind(this.itemView);
        }
    }

    public final void onActivate(final ViewDemoSguSoundConfigBinding binding, final View view) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Intrinsics.checkNotNullParameter(view, "view");
        view.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.adapters.demo.DemoSguRvAdapter$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DemoSguRvAdapter.onActivate$lambda$3(binding, this, view);
            }
        });
        binding.viewCoverRedGradient.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivate$lambda$3(final ViewDemoSguSoundConfigBinding binding, final DemoSguRvAdapter this$0, final View view) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(view, "$view");
        binding.imageViewCover.animate().scaleX(1.1f).scaleY(1.1f).setDuration(300L).withEndAction(new Runnable() { // from class: com.thor.app.gui.adapters.demo.DemoSguRvAdapter$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                DemoSguRvAdapter.onActivate$lambda$3$lambda$2(this.f$0, binding, view);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivate$lambda$3$lambda$2(DemoSguRvAdapter this$0, ViewDemoSguSoundConfigBinding binding, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(binding, "$binding");
        Intrinsics.checkNotNullParameter(view, "$view");
        this$0.onDeactivate(binding, view);
    }

    public final void onDeactivate(final ViewDemoSguSoundConfigBinding binding, View view) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Intrinsics.checkNotNullParameter(view, "view");
        Timber.INSTANCE.i("onDeactivate", new Object[0]);
        view.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.adapters.demo.DemoSguRvAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DemoSguRvAdapter.onDeactivate$lambda$5(binding);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDeactivate$lambda$5(final ViewDemoSguSoundConfigBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        binding.imageViewCover.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L).withEndAction(new Runnable() { // from class: com.thor.app.gui.adapters.demo.DemoSguRvAdapter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DemoSguRvAdapter.onDeactivate$lambda$5$lambda$4(binding);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDeactivate$lambda$5$lambda$4(ViewDemoSguSoundConfigBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        binding.viewCoverRedGradient.setVisibility(4);
    }

    public final void setRecyclerView(RecyclerView rv) {
        this.mRecyclerView = rv;
    }
}
