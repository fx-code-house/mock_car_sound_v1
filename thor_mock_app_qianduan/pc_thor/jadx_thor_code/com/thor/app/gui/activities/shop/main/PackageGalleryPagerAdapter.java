package com.thor.app.gui.activities.shop.main;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.carsystems.thor.app.R;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.thor.app.glide.GlideApp;
import com.thor.app.utils.extensions.StringKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: PackageGalleryPagerAdapter.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005B\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J \u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\u0018\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0013H\u0016R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u00040\bj\b\u0012\u0004\u0012\u00020\u0004`\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/thor/app/gui/activities/shop/main/PackageGalleryPagerAdapter;", "Landroidx/viewpager/widget/PagerAdapter;", "urls", "", "", "(Ljava/util/List;)V", "()V", "mUrls", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "add", "", ImagesContract.URL, "destroyItem", TtmlNode.RUBY_CONTAINER, "Landroid/view/ViewGroup;", "position", "", "item", "", "getCount", "instantiateItem", "isViewFromObject", "", "view", "Landroid/view/View;", "obj", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class PackageGalleryPagerAdapter extends PagerAdapter {
    private final ArrayList<String> mUrls;

    public PackageGalleryPagerAdapter() {
        this.mUrls = new ArrayList<>();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PackageGalleryPagerAdapter(List<String> urls) {
        this();
        Intrinsics.checkNotNullParameter(urls, "urls");
        this.mUrls.addAll(urls);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        Intrinsics.checkNotNullParameter(container, "container");
        Timber.INSTANCE.i("instantiateItem: %s, %s", Integer.valueOf(position), this.mUrls.get(position));
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_package_gallery, (ViewGroup) null);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);
        String str = this.mUrls.get(position);
        Intrinsics.checkNotNullExpressionValue(str, "mUrls[position]");
        GlideApp.with(container.getContext()).load(StringKt.getFullFileUrl(str)).diskCacheStrategy(DiskCacheStrategy.ALL).transition((TransitionOptions<?, ? super Drawable>) DrawableTransitionOptions.withCrossFade()).into(imageView);
        container.addView(itemView);
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object item) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(item, "item");
        container.removeView((View) item);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(obj, "obj");
        return view.equals(obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mUrls.size();
    }

    public final void add(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.mUrls.add(url);
        notifyDataSetChanged();
    }
}
