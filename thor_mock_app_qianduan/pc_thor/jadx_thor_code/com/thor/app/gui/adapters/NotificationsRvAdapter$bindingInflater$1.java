package com.thor.app.gui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.carsystems.thor.app.databinding.ItemNotificationBinding;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationsRvAdapter.kt */
@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
/* synthetic */ class NotificationsRvAdapter$bindingInflater$1 extends FunctionReferenceImpl implements Function3<LayoutInflater, ViewGroup, Boolean, ItemNotificationBinding> {
    public static final NotificationsRvAdapter$bindingInflater$1 INSTANCE = new NotificationsRvAdapter$bindingInflater$1();

    NotificationsRvAdapter$bindingInflater$1() {
        super(3, ItemNotificationBinding.class, "inflate", "inflate(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Z)Lcom/carsystems/thor/app/databinding/ItemNotificationBinding;", 0);
    }

    public final ItemNotificationBinding invoke(LayoutInflater p0, ViewGroup viewGroup, boolean z) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ItemNotificationBinding.inflate(p0, viewGroup, z);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ ItemNotificationBinding invoke(LayoutInflater layoutInflater, ViewGroup viewGroup, Boolean bool) {
        return invoke(layoutInflater, viewGroup, bool.booleanValue());
    }
}
