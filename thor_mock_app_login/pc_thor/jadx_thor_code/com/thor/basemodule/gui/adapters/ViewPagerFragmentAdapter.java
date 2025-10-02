package com.thor.basemodule.gui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewPagerFragmentAdapter.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/thor/basemodule/gui/adapters/ViewPagerFragmentAdapter;", "Landroidx/viewpager2/adapter/FragmentStateAdapter;", FirebaseAnalytics.Param.ITEMS, "", "Landroidx/fragment/app/Fragment;", "activity", "Landroidx/fragment/app/FragmentActivity;", "(Ljava/util/List;Landroidx/fragment/app/FragmentActivity;)V", "addFragment", "", "fragment", "createFragment", "position", "", "getItemCount", "removeFragment", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private final List<Fragment> items;

    public /* synthetic */ ViewPagerFragmentAdapter(ArrayList arrayList, FragmentActivity fragmentActivity, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ArrayList() : arrayList, fragmentActivity);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewPagerFragmentAdapter(List<Fragment> items, FragmentActivity activity) {
        super(activity);
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.items = items;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    public Fragment createFragment(int position) {
        return this.items.get(position);
    }

    public final void addFragment(Fragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.items.add(fragment);
        notifyItemInserted(this.items.indexOf(fragment));
    }

    public final void removeFragment(int position) {
        if (!this.items.isEmpty()) {
            this.items.remove(position);
            notifyItemRemoved(position);
        }
    }
}
