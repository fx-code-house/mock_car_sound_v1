package com.onesimcard.esim.ui.adapter.decorators;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpaceItemForLastDecoration.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B-\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/onesimcard/esim/ui/adapter/decorators/SpaceItemForLastDecoration;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "marginLeft", "", "marginTop", "marginRight", "marginBottom", "(IIII)V", "getItemOffsets", "", "outRect", "Landroid/graphics/Rect;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SpaceItemForLastDecoration extends RecyclerView.ItemDecoration {
    private final int marginBottom;
    private final int marginLeft;
    private final int marginRight;
    private final int marginTop;

    public SpaceItemForLastDecoration() {
        this(0, 0, 0, 0, 15, null);
    }

    public /* synthetic */ SpaceItemForLastDecoration(int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4);
    }

    public SpaceItemForLastDecoration(int i, int i2, int i3, int i4) {
        this.marginLeft = i;
        this.marginTop = i2;
        this.marginRight = i3;
        this.marginBottom = i4;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SpaceItemForLastDecoration(View view) {
        this(view.getHeight(), 0, 0, 0, 14, null);
        Intrinsics.checkNotNullParameter(view, "view");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        super.getItemOffsets(outRect, view, parent, state);
        int itemCount = state.getItemCount();
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if (itemCount <= 0 || childAdapterPosition != itemCount - 1) {
            return;
        }
        outRect.set(this.marginLeft, this.marginTop, this.marginRight, this.marginBottom);
    }
}
