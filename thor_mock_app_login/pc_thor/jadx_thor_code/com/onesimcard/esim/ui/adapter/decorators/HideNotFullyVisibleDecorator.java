package com.onesimcard.esim.ui.adapter.decorators;

import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;

/* compiled from: HideNotFullyVisibleDecorator.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/onesimcard/esim/ui/adapter/decorators/HideNotFullyVisibleDecorator;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "()V", "onDraw", "", "c", "Landroid/graphics/Canvas;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class HideNotFullyVisibleDecorator extends RecyclerView.ItemDecoration {
    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onDraw(android.graphics.Canvas r5, androidx.recyclerview.widget.RecyclerView r6, androidx.recyclerview.widget.RecyclerView.State r7) {
        /*
            r4 = this;
            java.lang.String r0 = "c"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "parent"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "state"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            super.onDraw(r5, r6, r7)
            int r5 = r6.getChildCount()
            r7 = 0
            r0 = r7
        L18:
            if (r0 >= r5) goto L39
            android.view.View r1 = r6.getChildAt(r0)
            androidx.recyclerview.widget.RecyclerView$LayoutManager r2 = r6.getLayoutManager()
            if (r2 == 0) goto L2c
            r3 = 1
            boolean r2 = r2.isViewPartiallyVisible(r1, r3, r7)
            if (r2 != r3) goto L2c
            goto L2d
        L2c:
            r3 = r7
        L2d:
            if (r3 == 0) goto L31
            r2 = r7
            goto L33
        L31:
            r2 = 8
        L33:
            r1.setVisibility(r2)
            int r0 = r0 + 1
            goto L18
        L39:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesimcard.esim.ui.adapter.decorators.HideNotFullyVisibleDecorator.onDraw(android.graphics.Canvas, androidx.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RecyclerView$State):void");
    }
}
