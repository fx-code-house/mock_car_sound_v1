package com.thor.app.gui.widget;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.databinding.VehicleDialogLayoutBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VehicleListViewDialog.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006!"}, d2 = {"Lcom/thor/app/gui/widget/VehicleListViewDialog;", "Landroid/app/Dialog;", "Landroid/view/View$OnClickListener;", "activity", "Landroid/app/Activity;", "adapter", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "(Landroid/app/Activity;Landroidx/recyclerview/widget/RecyclerView$Adapter;)V", "getActivity", "()Landroid/app/Activity;", "setActivity", "(Landroid/app/Activity;)V", "getAdapter", "()Landroidx/recyclerview/widget/RecyclerView$Adapter;", "setAdapter", "(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V", "binding", "Lcom/carsystems/thor/app/databinding/VehicleDialogLayoutBinding;", "mLayoutManager", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "getRecyclerView$thor_1_8_7_release", "()Landroidx/recyclerview/widget/RecyclerView;", "setRecyclerView$thor_1_8_7_release", "(Landroidx/recyclerview/widget/RecyclerView;)V", "onClick", "", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class VehicleListViewDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private RecyclerView.Adapter<?> adapter;
    private VehicleDialogLayoutBinding binding;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
    }

    public final Activity getActivity() {
        return this.activity;
    }

    public final RecyclerView.Adapter<?> getAdapter() {
        return this.adapter;
    }

    public final void setActivity(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "<set-?>");
        this.activity = activity;
    }

    public final void setAdapter(RecyclerView.Adapter<?> adapter) {
        Intrinsics.checkNotNullParameter(adapter, "<set-?>");
        this.adapter = adapter;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VehicleListViewDialog(Activity activity, RecyclerView.Adapter<?> adapter) {
        super(activity);
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        this.activity = activity;
        this.adapter = adapter;
    }

    /* renamed from: getRecyclerView$thor_1_8_7_release, reason: from getter */
    public final RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public final void setRecyclerView$thor_1_8_7_release(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        VehicleDialogLayoutBinding vehicleDialogLayoutBindingInflate = VehicleDialogLayoutBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(vehicleDialogLayoutBindingInflate, "inflate(layoutInflater)");
        this.binding = vehicleDialogLayoutBindingInflate;
        VehicleDialogLayoutBinding vehicleDialogLayoutBinding = null;
        if (vehicleDialogLayoutBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            vehicleDialogLayoutBindingInflate = null;
        }
        setContentView(vehicleDialogLayoutBindingInflate.getRoot());
        Window window = getWindow();
        Intrinsics.checkNotNull(window);
        window.setBackgroundDrawableResource(R.color.transparent);
        int i = new DisplayMetrics().heightPixels;
        Window window2 = getWindow();
        Intrinsics.checkNotNull(window2);
        WindowManager.LayoutParams attributes = window2.getAttributes();
        attributes.width = -1;
        attributes.gravity = 80;
        attributes.flags &= -3;
        window2.setAttributes(attributes);
        VehicleDialogLayoutBinding vehicleDialogLayoutBinding2 = this.binding;
        if (vehicleDialogLayoutBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            vehicleDialogLayoutBinding = vehicleDialogLayoutBinding2;
        }
        this.recyclerView = vehicleDialogLayoutBinding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity);
        this.mLayoutManager = linearLayoutManager;
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            return;
        }
        recyclerView2.setAdapter(this.adapter);
    }
}
