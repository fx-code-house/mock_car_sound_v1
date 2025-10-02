package com.thor.app.utils.extensions;

import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: View.kt */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\"\u0010\u0004\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b\u001a*\u0010\t\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u000b\u001a\u00020\f\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u0002¨\u0006\u000e"}, d2 = {"hide", "", "Landroid/view/View;", "invisible", "setOnClickListenerWithDebounce", "delay", "", "action", "Lkotlin/Function0;", "setOnVeryLongClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "performClick", "", "show", "thor-1.8.7_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class ViewKt {
    public static /* synthetic */ void setOnVeryLongClickListener$default(View view, long j, Function0 function0, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        setOnVeryLongClickListener(view, j, function0, z);
    }

    public static final void setOnVeryLongClickListener(View view, long j, Function0<Unit> listener, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        view.setOnTouchListener(new View.OnTouchListener(j, listener, z) { // from class: com.thor.app.utils.extensions.ViewKt.setOnVeryLongClickListener.1
            final /* synthetic */ Function0<Unit> $listener;
            final /* synthetic */ boolean $performClick;
            private final long longClickDuration;

            {
                this.$listener = listener;
                this.$performClick = z;
                this.longClickDuration = j;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent event) {
                if (!(event != null && event.getAction() == 0)) {
                    if (view2 != null && view2.isPressed()) {
                        Object tag = view2.getTag();
                        Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) tag).booleanValue()) {
                            if ((event != null ? event.getEventTime() - event.getDownTime() : 0L) > this.longClickDuration) {
                                view2.setTag(false);
                                view2.performHapticFeedback(0);
                                this.$listener.invoke();
                            } else {
                                if ((event != null && event.getAction() == 1) && this.$performClick) {
                                    view2.performClick();
                                }
                            }
                        }
                    }
                } else if (view2 != null) {
                    view2.setTag(true);
                }
                return false;
            }
        });
    }

    public static /* synthetic */ void setOnClickListenerWithDebounce$default(View view, long j, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 1000;
        }
        setOnClickListenerWithDebounce(view, j, function0);
    }

    public static final void setOnClickListenerWithDebounce(final View view, final long j, final Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        view.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.utils.extensions.ViewKt$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ViewKt.setOnClickListenerWithDebounce$lambda$1(view, j, action, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setOnClickListenerWithDebounce$lambda$1(final View this_setOnClickListenerWithDebounce, long j, Function0 action, View view) {
        Intrinsics.checkNotNullParameter(this_setOnClickListenerWithDebounce, "$this_setOnClickListenerWithDebounce");
        Intrinsics.checkNotNullParameter(action, "$action");
        this_setOnClickListenerWithDebounce.setEnabled(false);
        this_setOnClickListenerWithDebounce.postDelayed(new Runnable() { // from class: com.thor.app.utils.extensions.ViewKt$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ViewKt.setOnClickListenerWithDebounce$lambda$1$lambda$0(this_setOnClickListenerWithDebounce);
            }
        }, j);
        action.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setOnClickListenerWithDebounce$lambda$1$lambda$0(View this_setOnClickListenerWithDebounce) {
        Intrinsics.checkNotNullParameter(this_setOnClickListenerWithDebounce, "$this_setOnClickListenerWithDebounce");
        this_setOnClickListenerWithDebounce.setEnabled(true);
    }

    public static final void show(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(0);
    }

    public static final void invisible(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(4);
    }

    public static final void hide(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(8);
    }
}
