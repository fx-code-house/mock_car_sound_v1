package com.thor.app.utils.extensions;

import android.view.View;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Adapter.kt */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aä\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032:\b\u0006\u0010\u0004\u001a4\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u00052%\b\u0006\u0010\f\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\r28\b\u0006\u0010\u000e\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000b0\u00052#\b\u0006\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0013\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0006\u0010\u0014\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0016\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0006\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0004\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001aG\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0013\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032#\b\u0004\u0010\u0017\u001a\u001d\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001a^\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032:\b\u0004\u0010\u0017\u001a4\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0005H\u0086\bø\u0001\u0000\u001aI\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032%\b\u0004\u0010\u0017\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\rH\u0086\bø\u0001\u0000\u001a\\\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000328\b\u0004\u0010\u0017\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000b0\u0005H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001d"}, d2 = {"addItemClickListener", "Lcom/thor/basemodule/gui/adapters/RecyclerCollectionAdapter$OnItemClickListener;", "M", "Lcom/thor/basemodule/gui/adapters/RecyclerCollectionAdapter;", "onItemViewClick", "Lkotlin/Function2;", "Lkotlin/ParameterName;", AppMeasurementSdk.ConditionalUserProperty.NAME, "item", "Landroid/view/View;", "view", "", "onViewClick", "Lkotlin/Function1;", "onItemPositionClick", "", "position", "onItemClick", "addItemLongClickListener", "Lcom/thor/basemodule/gui/adapters/RecyclerCollectionAdapter$OnItemLongClickListener;", "onItemLongClick", "doOnActionConfig", "Lcom/thor/basemodule/gui/adapters/RecyclerCollectionAdapter$OnRecyclerActionConfigListener;", "action", "doOnItemClick", "doOnItemLongClick", "doOnItemViewClick", "doOnViewClick", "doOnViewPositionClick", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class AdapterKt {
    public static /* synthetic */ RecyclerCollectionAdapter.OnItemClickListener addItemClickListener$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function2 onItemViewClick, Function1 onViewClick, Function2 onItemPositionClick, Function1 onItemClick, int i, Object obj) {
        if ((i & 1) != 0) {
            onItemViewClick = new Function2<M, View, Unit>() { // from class: com.thor.app.utils.extensions.AdapterKt.addItemClickListener.1
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
            onViewClick = new Function1<View, Unit>() { // from class: com.thor.app.utils.extensions.AdapterKt.addItemClickListener.2
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
            onItemPositionClick = new Function2<M, Integer, Unit>() { // from class: com.thor.app.utils.extensions.AdapterKt.addItemClickListener.3
                public final void invoke(M m, int i2) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2, Integer num) {
                    invoke((AnonymousClass3<M>) obj2, num.intValue());
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 8) != 0) {
            onItemClick = new Function1<M, Unit>() { // from class: com.thor.app.utils.extensions.AdapterKt.addItemClickListener.4
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((AnonymousClass4<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(onItemViewClick, "onItemViewClick");
        Intrinsics.checkNotNullParameter(onViewClick, "onViewClick");
        Intrinsics.checkNotNullParameter(onItemPositionClick, "onItemPositionClick");
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        AdapterKt$addItemClickListener$listener$1 adapterKt$addItemClickListener$listener$1 = new AdapterKt$addItemClickListener$listener$1(onItemClick, onItemPositionClick, onViewClick, onItemViewClick);
        recyclerCollectionAdapter.setOnItemClickListener(adapterKt$addItemClickListener$listener$1);
        return adapterKt$addItemClickListener$listener$1;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemClickListener<M> addItemClickListener(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function2<? super M, ? super View, Unit> onItemViewClick, Function1<? super View, Unit> onViewClick, Function2<? super M, ? super Integer, Unit> onItemPositionClick, Function1<? super M, Unit> onItemClick) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(onItemViewClick, "onItemViewClick");
        Intrinsics.checkNotNullParameter(onViewClick, "onViewClick");
        Intrinsics.checkNotNullParameter(onItemPositionClick, "onItemPositionClick");
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        AdapterKt$addItemClickListener$listener$1 adapterKt$addItemClickListener$listener$1 = new AdapterKt$addItemClickListener$listener$1(onItemClick, onItemPositionClick, onViewClick, onItemViewClick);
        recyclerCollectionAdapter.setOnItemClickListener(adapterKt$addItemClickListener$listener$1);
        return adapterKt$addItemClickListener$listener$1;
    }

    public static /* synthetic */ RecyclerCollectionAdapter.OnItemLongClickListener addItemLongClickListener$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function1 onItemLongClick, int i, Object obj) {
        if ((i & 1) != 0) {
            onItemLongClick = new Function1<M, Unit>() { // from class: com.thor.app.utils.extensions.AdapterKt.addItemLongClickListener.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((C04391<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(onItemLongClick, "onItemLongClick");
        AdapterKt$addItemLongClickListener$listener$1 adapterKt$addItemLongClickListener$listener$1 = new AdapterKt$addItemLongClickListener$listener$1(onItemLongClick);
        recyclerCollectionAdapter.setOnItemLongClickListener(adapterKt$addItemLongClickListener$listener$1);
        return adapterKt$addItemLongClickListener$listener$1;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemLongClickListener<M> addItemLongClickListener(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function1<? super M, Unit> onItemLongClick) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(onItemLongClick, "onItemLongClick");
        AdapterKt$addItemLongClickListener$listener$1 adapterKt$addItemLongClickListener$listener$1 = new AdapterKt$addItemLongClickListener$listener$1(onItemLongClick);
        recyclerCollectionAdapter.setOnItemLongClickListener(adapterKt$addItemLongClickListener$listener$1);
        return adapterKt$addItemLongClickListener$listener$1;
    }

    public static /* synthetic */ RecyclerCollectionAdapter.OnRecyclerActionConfigListener doOnActionConfig$default(RecyclerCollectionAdapter recyclerCollectionAdapter, Function1 action, int i, Object obj) {
        if ((i & 1) != 0) {
            action = new Function1<M, Unit>() { // from class: com.thor.app.utils.extensions.AdapterKt.doOnActionConfig.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(M m) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((C04401<M>) obj2);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        AdapterKt$doOnActionConfig$listener$1 adapterKt$doOnActionConfig$listener$1 = new AdapterKt$doOnActionConfig$listener$1(action);
        recyclerCollectionAdapter.setOnActionConfigListener(adapterKt$doOnActionConfig$listener$1);
        return adapterKt$doOnActionConfig$listener$1;
    }

    public static final <M> RecyclerCollectionAdapter.OnRecyclerActionConfigListener<M> doOnActionConfig(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        AdapterKt$doOnActionConfig$listener$1 adapterKt$doOnActionConfig$listener$1 = new AdapterKt$doOnActionConfig$listener$1(action);
        recyclerCollectionAdapter.setOnActionConfigListener(adapterKt$doOnActionConfig$listener$1);
        return adapterKt$doOnActionConfig$listener$1;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemClickListener<M> doOnItemViewClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function2<? super M, ? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = new RecyclerCollectionAdapter.OnItemClickListener<M>() { // from class: com.thor.app.utils.extensions.AdapterKt$doOnItemViewClick$$inlined$addItemClickListener$default$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, int position) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, View view) {
                action.invoke(item, view);
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemClickListener<M> doOnViewClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = new RecyclerCollectionAdapter.OnItemClickListener<M>() { // from class: com.thor.app.utils.extensions.AdapterKt$doOnViewClick$$inlined$addItemClickListener$default$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, int position) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
                action.invoke(view);
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemClickListener<M> doOnViewPositionClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function2<? super M, ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = new RecyclerCollectionAdapter.OnItemClickListener<M>() { // from class: com.thor.app.utils.extensions.AdapterKt$doOnViewPositionClick$$inlined$addItemClickListener$default$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, int position) {
                action.invoke(item, Integer.valueOf(position));
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemClickListener<M> doOnItemClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, final Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        RecyclerCollectionAdapter.OnItemClickListener<M> onItemClickListener = new RecyclerCollectionAdapter.OnItemClickListener<M>() { // from class: com.thor.app.utils.extensions.AdapterKt$doOnItemClick$$inlined$addItemClickListener$default$1
            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, int position) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item, View view) {
            }

            @Override // com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter.OnItemClickListener
            public void onItemClick(M item) {
                action.invoke(item);
            }
        };
        recyclerCollectionAdapter.setOnItemClickListener(onItemClickListener);
        return onItemClickListener;
    }

    public static final <M> RecyclerCollectionAdapter.OnItemLongClickListener<M> doOnItemLongClick(RecyclerCollectionAdapter<M> recyclerCollectionAdapter, Function1<? super M, Unit> action) {
        Intrinsics.checkNotNullParameter(recyclerCollectionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        AdapterKt$addItemLongClickListener$listener$1 adapterKt$addItemLongClickListener$listener$1 = new AdapterKt$addItemLongClickListener$listener$1(action);
        recyclerCollectionAdapter.setOnItemLongClickListener(adapterKt$addItemLongClickListener$listener$1);
        return adapterKt$addItemLongClickListener$listener$1;
    }
}
