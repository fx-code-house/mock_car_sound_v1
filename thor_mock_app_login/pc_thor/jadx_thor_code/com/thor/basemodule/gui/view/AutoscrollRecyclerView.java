package com.thor.basemodule.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.thor.basemodule.utils.interpolation.UniformSpeedInterpolator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AutoscrollRecyclerView.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001a\u0018\u00002\u00020\u0001:\u0001@B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u001a\u001a\u00020\rJ\u001c\u0010\u001b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001eH\u0002J\u0006\u0010\u001f\u001a\u00020\rJ\u0012\u0010 \u001a\u00020!2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0006\u0010\"\u001a\u00020\rJ\b\u0010#\u001a\u00020!H\u0002J\b\u0010$\u001a\u00020!H\u0014J\u0010\u0010%\u001a\u00020\r2\u0006\u0010&\u001a\u00020'H\u0016J\u0018\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\tH\u0016J(\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\t2\u0006\u0010/\u001a\u00020\tH\u0014J\u0010\u00100\u001a\u00020\r2\u0006\u0010&\u001a\u00020'H\u0016J\u0016\u00101\u001a\u00020!2\u0006\u00102\u001a\u00020\t2\u0006\u00103\u001a\u00020\rJ\u000e\u00104\u001a\u00020!2\u0006\u0010\f\u001a\u00020\rJ\u0016\u00105\u001a\u00020!2\f\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001eH\u0016J\u000e\u00106\u001a\u00020!2\u0006\u00107\u001a\u00020\rJ\u000e\u00108\u001a\u00020!2\u0006\u00109\u001a\u00020\rJ\u000e\u0010:\u001a\u00020!2\u0006\u00103\u001a\u00020\rJ\b\u0010;\u001a\u00020!H\u0002J\u0006\u0010<\u001a\u00020!J\b\u0010=\u001a\u00020!H\u0002J\u001e\u0010>\u001a\u00020!2\f\u0010\u001d\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001e2\u0006\u0010?\u001a\u00020\rH\u0016R\u000e\u0010\u000b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006A"}, d2 = {"Lcom/thor/basemodule/gui/view/AutoscrollRecyclerView;", "Landroidx/recyclerview/widget/RecyclerView;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "defaultSpeed", "isStopAutoScroll", "", "mCanTouch", "mCurrentSpeed", "mInflate", "mInterpolator", "Landroid/view/animation/Interpolator;", "mIsOpenAuto", "mLoopEnabled", "mPointTouch", "mReady", "mReverse", "mSpeedDx", "mSpeedDy", "canTouch", "generateAdapter", "Lcom/thor/basemodule/gui/view/AutoscrollRecyclerView$NestingRecyclerViewAdapter;", "adapter", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "getReverse", "init", "", "isLoopEnabled", "notifyLayoutManager", "onFinishInflate", "onInterceptTouchEvent", "e", "Landroid/view/MotionEvent;", "onScrolled", "dx", "dy", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "openAutoScroll", "speed", "reverse", "pauseAutoScroll", "setAdapter", "setCanTouch", "b", "setLoopEnabled", "loopEnabled", "setReverse", "smoothScroll", "startAutoScroll", "startScroll", "swapAdapter", "removeAndRecycleExistingViews", "NestingRecyclerViewAdapter", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class AutoscrollRecyclerView extends RecyclerView {
    private final int defaultSpeed;
    private boolean isStopAutoScroll;
    private boolean mCanTouch;
    private int mCurrentSpeed;
    private boolean mInflate;
    private Interpolator mInterpolator;
    private boolean mIsOpenAuto;
    private boolean mLoopEnabled;
    private boolean mPointTouch;
    private boolean mReady;
    private boolean mReverse;
    private int mSpeedDx;
    private int mSpeedDy;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AutoscrollRecyclerView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AutoscrollRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoscrollRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.defaultSpeed = 14;
        this.mInterpolator = new UniformSpeedInterpolator();
        this.mCurrentSpeed = 14;
        this.mCanTouch = true;
        init(attributeSet);
    }

    private final void init(AttributeSet attrs) {
        this.mReady = false;
    }

    public final void startAutoScroll() {
        this.isStopAutoScroll = false;
        openAutoScroll(this.mCurrentSpeed, false);
    }

    public final void pauseAutoScroll(boolean isStopAutoScroll) {
        this.isStopAutoScroll = isStopAutoScroll;
    }

    public final void openAutoScroll(int speed, boolean reverse) {
        this.mReverse = reverse;
        this.mCurrentSpeed = speed;
        this.mIsOpenAuto = true;
        notifyLayoutManager();
        startScroll();
    }

    public final void setCanTouch(boolean b) {
        this.mCanTouch = b;
    }

    /* renamed from: canTouch, reason: from getter */
    public final boolean getMCanTouch() {
        return this.mCanTouch;
    }

    public final void setLoopEnabled(boolean loopEnabled) {
        this.mLoopEnabled = loopEnabled;
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            startScroll();
        }
    }

    /* renamed from: isLoopEnabled, reason: from getter */
    public final boolean getMLoopEnabled() {
        return this.mLoopEnabled;
    }

    public final void setReverse(boolean reverse) {
        this.mReverse = reverse;
        notifyLayoutManager();
        startScroll();
    }

    /* renamed from: getReverse, reason: from getter */
    public final boolean getMReverse() {
        return this.mReverse;
    }

    private final void startScroll() {
        if (this.mIsOpenAuto && getScrollState() != 2 && this.mInflate && this.mReady) {
            this.mSpeedDy = 0;
            this.mSpeedDx = 0;
            smoothScroll();
        }
    }

    private final void smoothScroll() {
        if (this.isStopAutoScroll) {
            return;
        }
        int iAbs = Math.abs(this.mCurrentSpeed);
        if (this.mReverse) {
            iAbs = -iAbs;
        }
        smoothScrollBy(iAbs, iAbs, this.mInterpolator);
    }

    private final void notifyLayoutManager() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setReverseLayout(this.mReverse);
        } else {
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                ((StaggeredGridLayoutManager) layoutManager).setReverseLayout(this.mReverse);
                return;
            }
            throw new UnsupportedOperationException("Unsupported layout manager " + layoutManager);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void swapAdapter(RecyclerView.Adapter<?> adapter, boolean removeAndRecycleExistingViews) {
        super.swapAdapter(generateAdapter(adapter), removeAndRecycleExistingViews);
        this.mReady = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter<?> adapter) {
        super.setAdapter(generateAdapter(adapter));
        this.mReady = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (!this.mCanTouch) {
            return true;
        }
        int action = e.getAction();
        if (action == 0) {
            this.mPointTouch = true;
        } else if ((action == 1 || action == 3) && this.mIsOpenAuto) {
            return true;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (!this.mCanTouch) {
            return true;
        }
        int action = e.getAction();
        if ((action == 1 || action == 3) && this.mIsOpenAuto) {
            this.mPointTouch = false;
            smoothScroll();
            return true;
        }
        return super.onTouchEvent(e);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startScroll();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInflate = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrolled(int dx, int dy) {
        boolean z;
        if (this.mPointTouch) {
            this.mSpeedDx = 0;
            this.mSpeedDy = 0;
            return;
        }
        if (dx == 0) {
            this.mSpeedDy += dy;
            z = true;
        } else {
            this.mSpeedDx += dx;
            z = false;
        }
        if (z) {
            if (Math.abs(this.mSpeedDy) >= Math.abs(this.mCurrentSpeed)) {
                this.mSpeedDy = 0;
                smoothScroll();
                return;
            }
            return;
        }
        if (Math.abs(this.mSpeedDx) >= Math.abs(this.mCurrentSpeed)) {
            this.mSpeedDx = 0;
            smoothScroll();
        }
    }

    private final NestingRecyclerViewAdapter<?> generateAdapter(RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            return new NestingRecyclerViewAdapter<>(this, adapter);
        }
        return null;
    }

    /* compiled from: AutoscrollRecyclerView.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000*\n\b\u0000\u0010\u0001*\u0004\u0018\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u001f\u0010\u001a\u001a\u00020\u001b2\b\b\u0001\u0010\u001c\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00020\u0013H\u0016¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0013H\u0016¢\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u001b2\u0006\u0010'\u001a\u00020\tH\u0016J\u0010\u0010(\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020%H\u0016R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000b¨\u0006)"}, d2 = {"Lcom/thor/basemodule/gui/view/AutoscrollRecyclerView$NestingRecyclerViewAdapter;", "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "mRecyclerView", "Lcom/thor/basemodule/gui/view/AutoscrollRecyclerView;", "mAdapter", "(Lcom/thor/basemodule/gui/view/AutoscrollRecyclerView;Landroidx/recyclerview/widget/RecyclerView$Adapter;)V", "loopEnable", "", "getLoopEnable", "()Z", "getMAdapter", "()Landroidx/recyclerview/widget/RecyclerView$Adapter;", "setMAdapter", "(Landroidx/recyclerview/widget/RecyclerView$Adapter;)V", "reverse", "getReverse", "generatePosition", "", "position", "getActualPosition", "getItemCount", "getItemId", "", "getItemViewType", "onBindViewHolder", "", "holder", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "registerAdapterDataObserver", "observer", "Landroidx/recyclerview/widget/RecyclerView$AdapterDataObserver;", "setHasStableIds", "hasStableIds", "unregisterAdapterDataObserver", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class NestingRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
        private RecyclerView.Adapter<VH> mAdapter;
        private final AutoscrollRecyclerView mRecyclerView;

        public final RecyclerView.Adapter<VH> getMAdapter() {
            return this.mAdapter;
        }

        public final void setMAdapter(RecyclerView.Adapter<VH> adapter) {
            Intrinsics.checkNotNullParameter(adapter, "<set-?>");
            this.mAdapter = adapter;
        }

        public NestingRecyclerViewAdapter(AutoscrollRecyclerView mRecyclerView, RecyclerView.Adapter<VH> mAdapter) {
            Intrinsics.checkNotNullParameter(mRecyclerView, "mRecyclerView");
            Intrinsics.checkNotNullParameter(mAdapter, "mAdapter");
            this.mRecyclerView = mRecyclerView;
            this.mAdapter = mAdapter;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            return (VH) this.mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            Intrinsics.checkNotNullParameter(observer, "observer");
            super.registerAdapterDataObserver(observer);
            this.mAdapter.registerAdapterDataObserver(observer);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            Intrinsics.checkNotNullParameter(observer, "observer");
            super.unregisterAdapterDataObserver(observer);
            this.mAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(VH holder, int position) {
            this.mAdapter.onBindViewHolder(holder, generatePosition(position));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
            this.mAdapter.setHasStableIds(hasStableIds);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (getLoopEnable()) {
                return Integer.MAX_VALUE;
            }
            return this.mAdapter.getItemCount();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int position) {
            return this.mAdapter.getItemViewType(generatePosition(position));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int position) {
            return this.mAdapter.getItemId(generatePosition(position));
        }

        private final int generatePosition(int position) {
            return getLoopEnable() ? getActualPosition(position) : position;
        }

        private final int getActualPosition(int position) {
            int itemCount = this.mAdapter.getItemCount();
            return position >= itemCount ? position % itemCount : position;
        }

        private final boolean getLoopEnable() {
            return this.mRecyclerView.mLoopEnabled;
        }

        public final boolean getReverse() {
            return this.mRecyclerView.mReverse;
        }
    }
}
