package com.fourksoft.base.ui.adapter;

import com.fourksoft.base.ui.adapter.listener.OnItemClickListener;
import com.fourksoft.base.ui.adapter.listener.OnItemLongClickListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionDeleteListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionEditListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionListener;
import java.util.Collection;
import kotlin.Metadata;

/* compiled from: RecyclerCollectionAdapter.kt */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006H&J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\tJ\u001d\u0010\u0007\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u0004H&J\u0018\u0010\u000e\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006H&J\u0016\u0010\u000f\u001a\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H&J$\u0010\u000f\u001a\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011H&J\u0010\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006H&J\u0010\u0010\u0014\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0015H&J\u0015\u0010\u0016\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u000bH&¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H&J\u0015\u0010\u001a\u001a\u00020\u00042\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\tJ\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH&J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u000bH&¢\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u00042\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H&J\u0018\u0010\u001f\u001a\u00020\u00042\u000e\u0010 \u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010!H&J\u0018\u0010\"\u001a\u00020\u00042\u000e\u0010 \u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010#H&J\u0018\u0010$\u001a\u00020\u00042\u000e\u0010 \u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010%H&J\u0018\u0010&\u001a\u00020\u00042\u000e\u0010'\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010(H&J\u0018\u0010)\u001a\u00020\u00042\u000e\u0010*\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010+H&J\u0018\u0010,\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006H&J\u0015\u0010-\u001a\u00020\u00042\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\t¨\u0006."}, d2 = {"Lcom/fourksoft/base/ui/adapter/RecyclerCollectionAdapter;", "M", "", "addAll", "", "data", "", "addItem", "item", "(Ljava/lang/Object;)V", "position", "", "(ILjava/lang/Object;)V", "clear", "clearAndAddAll", "compareByDiffUtil", "oldData", "", "newData", "getAll", "getDiffUtilCallback", "Lcom/fourksoft/base/ui/adapter/BaseDiffUtilCallback;", "getItem", "(I)Ljava/lang/Object;", "isEmpty", "", "removeItem", "restoreItem", "(Ljava/lang/Object;I)V", "setDiffUtilCallback", "diffUtilCallback", "setOnActionDeleteListener", "onActionListener", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionDeleteListener;", "setOnActionEditListener", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionEditListener;", "setOnActionListener", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;", "setOnItemClickListener", "onItemClickListener", "Lcom/fourksoft/base/ui/adapter/listener/OnItemClickListener;", "setOnItemLongClickListener", "onItemLongClickListener", "Lcom/fourksoft/base/ui/adapter/listener/OnItemLongClickListener;", "updateAll", "updateItem", "basemodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public interface RecyclerCollectionAdapter<M> {
    void addAll(Collection<? extends M> data);

    void addItem(int position, M item);

    void addItem(M item);

    void clear();

    void clearAndAddAll(Collection<? extends M> data);

    void compareByDiffUtil(Collection<M> oldData);

    void compareByDiffUtil(Collection<M> oldData, Collection<M> newData);

    Collection<M> getAll();

    BaseDiffUtilCallback<M> getDiffUtilCallback();

    M getItem(int position);

    boolean isEmpty();

    void removeItem(int position);

    void removeItem(M item);

    void restoreItem(M item, int position);

    void setDiffUtilCallback(BaseDiffUtilCallback<M> diffUtilCallback);

    void setOnActionDeleteListener(OnRecyclerActionDeleteListener<M> onActionListener);

    void setOnActionEditListener(OnRecyclerActionEditListener<M> onActionListener);

    void setOnActionListener(OnRecyclerActionListener<M> onActionListener);

    void setOnItemClickListener(OnItemClickListener<M> onItemClickListener);

    void setOnItemLongClickListener(OnItemLongClickListener<M> onItemLongClickListener);

    void updateAll(Collection<? extends M> data);

    void updateItem(M item);
}
