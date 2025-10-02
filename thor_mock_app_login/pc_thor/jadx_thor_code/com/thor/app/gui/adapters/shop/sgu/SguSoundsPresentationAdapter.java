package com.thor.app.gui.adapters.shop.sgu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.ItemSguSoundPreviewBinding;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import com.thor.networkmodule.model.responses.sgu.SguSound;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SguSoundsPresentationAdapter.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\n\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016J\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0002J\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0002¨\u0006\u0012"}, d2 = {"Lcom/thor/app/gui/adapters/shop/sgu/SguSoundsPresentationAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/networkmodule/model/responses/sgu/SguSound;", "Lcom/thor/app/gui/adapters/shop/sgu/SguSoundsPresentationAdapter$SubscriptionPackViewHolder;", "()V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onStartPlaying", "sound", "onStopPlaying", "SubscriptionPackViewHolder", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSoundsPresentationAdapter extends BaseRecyclerListAdapter<SguSound, SubscriptionPackViewHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SubscriptionPackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ItemSguSoundPreviewBinding itemSguSoundPreviewBindingInflate = ItemSguSoundPreviewBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemSguSoundPreviewBindingInflate, "inflate(inflater!!, parent, false)");
        View root = itemSguSoundPreviewBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new SubscriptionPackViewHolder(this, root);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0075  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onBindViewHolder(com.thor.app.gui.adapters.shop.sgu.SguSoundsPresentationAdapter.SubscriptionPackViewHolder r7, int r8) {
        /*
            r6 = this;
            java.lang.String r0 = "holder"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            com.carsystems.thor.app.databinding.ItemSguSoundPreviewBinding r0 = r7.getBinding()
            r1 = 0
            if (r0 == 0) goto Lf
            com.google.android.material.imageview.ShapeableImageView r0 = r0.image
            goto L10
        Lf:
            r0 = r1
        L10:
            if (r0 == 0) goto Lbf
            java.util.List<M> r2 = r6.mList
            java.lang.String r3 = "mList"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            r3 = 1
            r2 = r2 ^ r3
            if (r2 == 0) goto Lbf
            java.util.List<M> r2 = r6.mList
            java.lang.Object r8 = r2.get(r8)
            com.thor.networkmodule.model.responses.sgu.SguSound r8 = (com.thor.networkmodule.model.responses.sgu.SguSound) r8
            com.carsystems.thor.app.databinding.ItemSguSoundPreviewBinding r2 = r7.getBinding()
            android.widget.TextView r2 = r2.title
            java.lang.String r4 = r8.getName()
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            r2.setText(r4)
            boolean r2 = r8.getIsPlaying()
            if (r2 == 0) goto L44
            r2 = 2131099841(0x7f0600c1, float:1.7812047E38)
            goto L47
        L44:
            r2 = 2131099854(0x7f0600ce, float:1.7812073E38)
        L47:
            com.carsystems.thor.app.databinding.ItemSguSoundPreviewBinding r4 = r7.getBinding()
            android.widget.TextView r4 = r4.title
            com.carsystems.thor.app.databinding.ItemSguSoundPreviewBinding r5 = r7.getBinding()
            android.view.View r5 = r5.getRoot()
            android.content.Context r5 = r5.getContext()
            int r2 = androidx.core.content.ContextCompat.getColor(r5, r2)
            r4.setTextColor(r2)
            java.lang.String r2 = r8.getPreview()
            r4 = 0
            if (r2 == 0) goto L75
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
            int r2 = r2.length()
            if (r2 <= 0) goto L71
            r2 = r3
            goto L72
        L71:
            r2 = r4
        L72:
            if (r2 != r3) goto L75
            goto L76
        L75:
            r3 = r4
        L76:
            if (r3 == 0) goto L83
            java.lang.String r2 = r8.getPreview()
            if (r2 == 0) goto L8d
            java.lang.String r1 = com.thor.app.utils.extensions.StringKt.getFullFileUrl(r2)
            goto L8d
        L83:
            java.lang.String r2 = r8.getImage()
            if (r2 == 0) goto L8d
            java.lang.String r1 = com.thor.app.utils.extensions.StringKt.getFullFileUrl(r2)
        L8d:
            android.content.Context r2 = r0.getContext()
            com.thor.app.glide.GlideRequests r2 = com.thor.app.glide.GlideApp.with(r2)
            com.thor.app.glide.GlideRequest r1 = r2.load(r1)
            com.thor.app.glide.GlideRequest r1 = r1.dontTransform()
            com.bumptech.glide.load.engine.DiskCacheStrategy r2 = com.bumptech.glide.load.engine.DiskCacheStrategy.ALL
            com.thor.app.glide.GlideRequest r1 = r1.diskCacheStrategy(r2)
            r2 = 2131231197(0x7f0801dd, float:1.8078468E38)
            com.thor.app.glide.GlideRequest r1 = r1.error(r2)
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            r1.into(r0)
            com.carsystems.thor.app.databinding.ItemSguSoundPreviewBinding r7 = r7.getBinding()
            android.view.View r7 = r7.getRoot()
            com.thor.app.gui.adapters.shop.sgu.SguSoundsPresentationAdapter$$ExternalSyntheticLambda0 r0 = new com.thor.app.gui.adapters.shop.sgu.SguSoundsPresentationAdapter$$ExternalSyntheticLambda0
            r0.<init>()
            r7.setOnClickListener(r0)
        Lbf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.adapters.shop.sgu.SguSoundsPresentationAdapter.onBindViewHolder(com.thor.app.gui.adapters.shop.sgu.SguSoundsPresentationAdapter$SubscriptionPackViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(SguSoundsPresentationAdapter this$0, SguSound sguSound, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = this$0.mOnItemClickListener;
        if (onItemClickListener != 0) {
            onItemClickListener.onItemClick((RecyclerCollectionAdapter.OnItemClickListener<M>) sguSound);
        }
    }

    public final void onStartPlaying(SguSound sound) {
        Object next;
        Intrinsics.checkNotNullParameter(sound, "sound");
        Iterable<SguSound> mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        for (SguSound it : mList) {
            if (it.getIsPlaying()) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                onStopPlaying(it);
            }
        }
        Iterable mList2 = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList2, "mList");
        Iterator it2 = mList2.iterator();
        while (true) {
            if (it2.hasNext()) {
                next = it2.next();
                if (Intrinsics.areEqual((SguSound) next, sound)) {
                    break;
                }
            } else {
                next = null;
                break;
            }
        }
        SguSound sguSound = (SguSound) next;
        if (sguSound != null) {
            sguSound.setPlaying(true);
        }
        notifyItemChanged(this.mList.indexOf(sound));
    }

    public final void onStopPlaying(SguSound sound) {
        Object next;
        Intrinsics.checkNotNullParameter(sound, "sound");
        Iterable mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        Iterator it = mList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (Intrinsics.areEqual((SguSound) next, sound)) {
                    break;
                }
            }
        }
        SguSound sguSound = (SguSound) next;
        if (sguSound != null) {
            sguSound.setPlaying(false);
        }
        notifyItemChanged(this.mList.indexOf(sound));
    }

    /* compiled from: SguSoundsPresentationAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/shop/sgu/SguSoundsPresentationAdapter$SubscriptionPackViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/shop/sgu/SguSoundsPresentationAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemSguSoundPreviewBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemSguSoundPreviewBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class SubscriptionPackViewHolder extends RecyclerView.ViewHolder {
        private final ItemSguSoundPreviewBinding binding;
        final /* synthetic */ SguSoundsPresentationAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SubscriptionPackViewHolder(SguSoundsPresentationAdapter sguSoundsPresentationAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = sguSoundsPresentationAdapter;
            this.binding = (ItemSguSoundPreviewBinding) DataBindingUtil.bind(itemView);
        }

        public final ItemSguSoundPreviewBinding getBinding() {
            return this.binding;
        }
    }
}
