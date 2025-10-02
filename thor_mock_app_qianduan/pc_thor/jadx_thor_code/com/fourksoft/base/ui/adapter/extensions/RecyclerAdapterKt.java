package com.fourksoft.base.ui.adapter.extensions;

import android.view.View;
import com.fourksoft.base.ui.adapter.RecyclerCollectionAdapter;
import com.fourksoft.base.ui.adapter.listener.OnItemClickListener;
import com.fourksoft.base.ui.adapter.listener.OnItemLongClickListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionDeleteListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionEditListener;
import com.fourksoft.base.ui.adapter.listener.OnRecyclerActionListener;
import com.fourksoft.base.ui.adapter.type.ActionRecycler;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RecyclerAdapter.kt */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aª\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032:\b\u0006\u0010\u0004\u001a4\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u00052%\b\u0006\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\r2#\b\u0006\u0010\u000e\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001a\\\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0010\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000328\b\u0006\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u000b0\u0005H\u0086\bø\u0001\u0000\u001aG\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0016\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0006\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0018\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0006\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0006\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001a^\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032:\b\u0004\u0010\u0011\u001a4\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0005H\u0086\bø\u0001\u0000\u001aI\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032%\b\u0004\u0010\u0011\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001e"}, d2 = {"addItemClickListener", "Lcom/fourksoft/base/ui/adapter/listener/OnItemClickListener;", "M", "Lcom/fourksoft/base/ui/adapter/RecyclerCollectionAdapter;", "onItemViewClick", "Lkotlin/Function2;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "item", "Landroid/view/View;", "view", "", "onViewClick", "Lkotlin/Function1;", "onItemClick", "doOnAction", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionListener;", "action", "Lcom/fourksoft/base/ui/adapter/type/ActionRecycler;", SessionDescription.ATTR_TYPE, "model", "doOnActionDelete", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionDeleteListener;", "doOnActionEdit", "Lcom/fourksoft/base/ui/adapter/listener/OnRecyclerActionEditListener;", "doOnItemClick", "doOnItemLongClick", "Lcom/fourksoft/base/ui/adapter/listener/OnItemLongClickListener;", "doOnItemViewClick", "doOnViewClick", "basemodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RecyclerAdapterKt {
    public static /* synthetic */ OnItemClickListener addItemClickListener$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function2 onItemViewClick, Function1 onViewClick, Function1 onItemClick, int i, Object obj) {
        if ((i & 1) != 0) {
            onItemViewClick = new Function2<M, View, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.addItemClickListener.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m, View view) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2, View view) {
                    invoke2((AnonymousClass1<M>) obj2, view);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 2) != 0) {
            onViewClick = new Function1<View, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.addItemClickListener.2
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(View view) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(View view) {
                    invoke2(view);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 4) != 0) {
            onItemClick = new Function1<M, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.addItemClickListener.3
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((AnonymousClass3<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(onItemViewClick, "onItemViewClick");
        Intrinsics.checkNotNullParameter(onViewClick, "onViewClick");
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        RecyclerAdapterKt$addItemClickListener$listener$1 recyclerAdapterKt$addItemClickListener$listener$1 = new RecyclerAdapterKt$addItemClickListener$listener$1(onItemClick, onViewClick, onItemViewClick);
        recyclerCollectionAdapter.setOnItemClickListener(recyclerAdapterKt$addItemClickListener$listener$1);
        return recyclerAdapterKt$addItemClickListener$listener$1;
    }

    public static final <M> OnItemClickListener<M> addItemClickListener(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function2<? super M, ? super View, Unit> onItemViewClick, Function1<? super View, Unit> onViewClick, Function1<? super M, Unit> onItemClick) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(onItemViewClick, "onItemViewClick");
        Intrinsics.checkNotNullParameter(onViewClick, "onViewClick");
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        RecyclerAdapterKt$addItemClickListener$listener$1 recyclerAdapterKt$addItemClickListener$listener$1 = new RecyclerAdapterKt$addItemClickListener$listener$1(onItemClick, onViewClick, onItemViewClick);
        recyclerCollectionAdapter.setOnItemClickListener(recyclerAdapterKt$addItemClickListener$listener$1);
        return recyclerAdapterKt$addItemClickListener$listener$1;
    }

    public static /* synthetic */ OnItemLongClickListener doOnItemLongClick$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function1 action, int i, Object obj) {
        if ((i & 1) != 0) {
            action = new Function1<M, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.doOnItemLongClick.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((C01311<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnItemLongClick$listener$1 recyclerAdapterKt$doOnItemLongClick$listener$1 = new RecyclerAdapterKt$doOnItemLongClick$listener$1(action);
        recyclerCollectionAdapter.setOnItemLongClickListener(recyclerAdapterKt$doOnItemLongClick$listener$1);
        return recyclerAdapterKt$doOnItemLongClick$listener$1;
    }

    public static final <M> OnItemLongClickListener<M> doOnItemLongClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnItemLongClick$listener$1 recyclerAdapterKt$doOnItemLongClick$listener$1 = new RecyclerAdapterKt$doOnItemLongClick$listener$1(action);
        recyclerCollectionAdapter.setOnItemLongClickListener(recyclerAdapterKt$doOnItemLongClick$listener$1);
        return recyclerAdapterKt$doOnItemLongClick$listener$1;
    }

    public static /* synthetic */ OnRecyclerActionDeleteListener doOnActionDelete$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function1 action, int i, Object obj) {
        if ((i & 1) != 0) {
            action = new Function1<M, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.doOnActionDelete.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((C01291<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnActionDelete$listener$1 recyclerAdapterKt$doOnActionDelete$listener$1 = new RecyclerAdapterKt$doOnActionDelete$listener$1(action);
        recyclerCollectionAdapter.setOnActionDeleteListener(recyclerAdapterKt$doOnActionDelete$listener$1);
        return recyclerAdapterKt$doOnActionDelete$listener$1;
    }

    public static final <M> OnRecyclerActionDeleteListener<M> doOnActionDelete(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnActionDelete$listener$1 recyclerAdapterKt$doOnActionDelete$listener$1 = new RecyclerAdapterKt$doOnActionDelete$listener$1(action);
        recyclerCollectionAdapter.setOnActionDeleteListener(recyclerAdapterKt$doOnActionDelete$listener$1);
        return recyclerAdapterKt$doOnActionDelete$listener$1;
    }

    public static /* synthetic */ OnRecyclerActionEditListener doOnActionEdit$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function1 action, int i, Object obj) {
        if ((i & 1) != 0) {
            action = new Function1<M, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.doOnActionEdit.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((C01301<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnActionEdit$listener$1 recyclerAdapterKt$doOnActionEdit$listener$1 = new RecyclerAdapterKt$doOnActionEdit$listener$1(action);
        recyclerCollectionAdapter.setOnActionEditListener(recyclerAdapterKt$doOnActionEdit$listener$1);
        return recyclerAdapterKt$doOnActionEdit$listener$1;
    }

    public static final <M> OnRecyclerActionEditListener<M> doOnActionEdit(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnActionEdit$listener$1 recyclerAdapterKt$doOnActionEdit$listener$1 = new RecyclerAdapterKt$doOnActionEdit$listener$1(action);
        recyclerCollectionAdapter.setOnActionEditListener(recyclerAdapterKt$doOnActionEdit$listener$1);
        return recyclerAdapterKt$doOnActionEdit$listener$1;
    }

    public static /* synthetic */ OnRecyclerActionListener doOnAction$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function2 action, int i, Object obj) {
        if ((i & 1) != 0) {
            action = new Function2<ActionRecycler, M, Unit>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt.doOnAction.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ActionRecycler actionRecycler, M m) {
                    Intrinsics.checkNotNullParameter(actionRecycler, "<anonymous parameter 0>");
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(ActionRecycler actionRecycler, Object obj2) {
                    invoke2(actionRecycler, (ActionRecycler) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnAction$listener$1 recyclerAdapterKt$doOnAction$listener$1 = new RecyclerAdapterKt$doOnAction$listener$1(action);
        recyclerCollectionAdapter.setOnActionListener(recyclerAdapterKt$doOnAction$listener$1);
        return recyclerAdapterKt$doOnAction$listener$1;
    }

    public static final <M> OnRecyclerActionListener<M> doOnAction(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function2<? super ActionRecycler, ? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerAdapterKt$doOnAction$listener$1 recyclerAdapterKt$doOnAction$listener$1 = new RecyclerAdapterKt$doOnAction$listener$1(action);
        recyclerCollectionAdapter.setOnActionListener(recyclerAdapterKt$doOnAction$listener$1);
        return recyclerAdapterKt$doOnAction$listener$1;
    }

    public static final <M> OnItemClickListener<M> doOnItemViewClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function2<? super M, ? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        OnItemClickListener<M> onItemClickListener = new OnItemClickListener<M>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt$doOnItemViewClick$$inlined$addItemClickListener$default$1
            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(M item) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(M item, View view) {
                action.invoke(item, view);
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }

    public static final <M> OnItemClickListener<M> doOnViewClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        OnItemClickListener<M> onItemClickListener = new OnItemClickListener<M>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt$doOnViewClick$$inlined$addItemClickListener$default$1
            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(M item) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(M item, View view) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(View view) {
                action.invoke(view);
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }

    public static final <M> OnItemClickListener<M> doOnItemClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        OnItemClickListener<M> onItemClickListener = new OnItemClickListener<M>() { // from class: com.fourksoft.base.ui.adapter.extensions.RecyclerAdapterKt$doOnItemClick$$inlined$addItemClickListener$default$1
            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(M item, View view) {
            }

            @Override // com.fourksoft.base.ui.adapter.listener.OnItemClickListener
            public void onItemClick(M item) {
                action.invoke(item);
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }
}
