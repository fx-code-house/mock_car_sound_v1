package com.thor.app.gui.activities.shop.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityShopSoundPackageBinding;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemDecorator;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForFirstDecoration;
import com.fourksoft.base.ui.adapter.decorators.SpaceItemForLastDecoration;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.Constants;
import com.thor.app.glide.GlideApp;
import com.thor.app.glide.GlideRequest;
import com.thor.app.gui.adapters.shop.subscription.SoundsPresentationAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.StringKt;
import com.thor.basemodule.extensions.MeasureKt;
import com.thor.basemodule.extensions.NumberKt;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import timber.log.Timber;

/* compiled from: SubscribtionPromoActivity.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\b\u0010\u001b\u001a\u00020\u0011H\u0002J\b\u0010\u001c\u001a\u00020\u0011H\u0002J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\u0012\u0010\u001e\u001a\u00020\u00112\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010!\u001a\u00020\u0011H\u0014J\b\u0010\"\u001a\u00020\u0011H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/thor/app/gui/activities/shop/main/SubscribtionPromoActivity;", "Lcom/thor/app/gui/activities/shop/main/SubscriptionCheckActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityShopSoundPackageBinding;", "disposables", "", "Lio/reactivex/disposables/Disposable;", "packsAdapter", "Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "getPacksAdapter", "()Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "packsAdapter$delegate", "Lkotlin/Lazy;", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "fillAdapterData", "", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "init", "initAdapter", "initIntentExtra", "initListeners", "initUI", "launchLoopingRecyclerAutoscroll", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onResume", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SubscribtionPromoActivity extends SubscriptionCheckActivity {
    private ActivityShopSoundPackageBinding binding;
    private final List<Disposable> disposables = new ArrayList();

    /* renamed from: packsAdapter$delegate, reason: from kotlin metadata */
    private final Lazy packsAdapter = LazyKt.lazy(new Function0<SoundsPresentationAdapter>() { // from class: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$packsAdapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SoundsPresentationAdapter invoke() {
            return new SoundsPresentationAdapter();
        }
    });
    private ShopSoundPackage soundPackage;

    private final SoundsPresentationAdapter getPacksAdapter() {
        return (SoundsPresentationAdapter) this.packsAdapter.getValue();
    }

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShopSoundPackageBinding activityShopSoundPackageBindingInflate = ActivityShopSoundPackageBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityShopSoundPackageBindingInflate, "inflate(layoutInflater)");
        this.binding = activityShopSoundPackageBindingInflate;
        if (activityShopSoundPackageBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBindingInflate = null;
        }
        setContentView(activityShopSoundPackageBindingInflate.getRoot());
        init();
    }

    private final void init() {
        initIntentExtra();
        initUI();
        initAdapter();
        initListeners();
    }

    private final void initUI() {
        String pkgImageUrl;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding = this.binding;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding2 = null;
        if (activityShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding = null;
        }
        AppCompatTextView appCompatTextView = activityShopSoundPackageBinding.presetName;
        ShopSoundPackage shopSoundPackage = this.soundPackage;
        appCompatTextView.setText(shopSoundPackage != null ? shopSoundPackage.getPkgName() : null);
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding3 = this.binding;
        if (activityShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding3 = null;
        }
        AppCompatButton appCompatButton = activityShopSoundPackageBinding3.actionBuy;
        Object[] objArr = new Object[1];
        ShopSoundPackage shopSoundPackage2 = this.soundPackage;
        Float fValueOf = shopSoundPackage2 != null ? Float.valueOf(NumberKt.roundTo(shopSoundPackage2.getPrice(), 2)) : null;
        ShopSoundPackage shopSoundPackage3 = this.soundPackage;
        objArr[0] = fValueOf + StringUtils.SPACE + (shopSoundPackage3 != null ? shopSoundPackage3.getCurrency() : null);
        appCompatButton.setText(getString(R.string.text_form_buy_one_pack, objArr));
        ShopSoundPackage shopSoundPackage4 = this.soundPackage;
        GlideRequest<Drawable> glideRequestError = GlideApp.with((FragmentActivity) this).load((shopSoundPackage4 == null || (pkgImageUrl = shopSoundPackage4.getPkgImageUrl()) == null) ? null : StringKt.getFullFileUrl(pkgImageUrl)).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_thor_logo_small);
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding4 = this.binding;
        if (activityShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopSoundPackageBinding2 = activityShopSoundPackageBinding4;
        }
        glideRequestError.into(activityShopSoundPackageBinding2.imagePack);
    }

    private final void initAdapter() {
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding = this.binding;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding2 = null;
        if (activityShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding = null;
        }
        activityShopSoundPackageBinding.autoscrollRecycler.setAdapter(getPacksAdapter());
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding3 = this.binding;
        if (activityShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding3 = null;
        }
        activityShopSoundPackageBinding3.autoscrollRecycler.addItemDecoration(new SpaceItemDecorator(MeasureKt.getDp(4), 0, MeasureKt.getDp(4), 0, 10, null));
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding4 = this.binding;
        if (activityShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding4 = null;
        }
        activityShopSoundPackageBinding4.autoscrollRecycler.addItemDecoration(new SpaceItemForFirstDecoration(MeasureKt.getDp(16), 0, 0, 0, 14, null));
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding5 = this.binding;
        if (activityShopSoundPackageBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopSoundPackageBinding2 = activityShopSoundPackageBinding5;
        }
        activityShopSoundPackageBinding2.autoscrollRecycler.addItemDecoration(new SpaceItemForLastDecoration(0, 0, MeasureKt.getDp(16), 0, 11, null));
        fillAdapterData();
    }

    private final void initListeners() {
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding = this.binding;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding2 = null;
        if (activityShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding = null;
        }
        activityShopSoundPackageBinding.actionClose.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscribtionPromoActivity.initListeners$lambda$0(this.f$0, view);
            }
        });
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding3 = this.binding;
        if (activityShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding3 = null;
        }
        activityShopSoundPackageBinding3.actionSubscribe.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscribtionPromoActivity.initListeners$lambda$1(this.f$0, view);
            }
        });
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding4 = this.binding;
        if (activityShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopSoundPackageBinding2 = activityShopSoundPackageBinding4;
        }
        activityShopSoundPackageBinding2.actionBuy.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscribtionPromoActivity.initListeners$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(SubscribtionPromoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(SubscribtionPromoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) SubscriptionsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this$0, new Pair[0]).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$2(SubscribtionPromoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0, (Class<?>) SoundPackageDescriptionActivity.class);
        intent.putExtra(ShopSoundPackage.INSTANCE.getBUNDLE_NAME(), this$0.soundPackage);
        this$0.startActivity(intent);
    }

    private final void initIntentExtra() {
        this.soundPackage = (ShopSoundPackage) getIntent().getParcelableExtra(ShopSoundPackage.INSTANCE.getBUNDLE_NAME());
    }

    private final void fillAdapterData() {
        Disposable disposableSubscribe;
        Observable<ShopSoundPackagesResponse> observableFetchShopSoundPackages;
        List<Disposable> list = this.disposables;
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
        if (usersManagerFrom == null || (observableFetchShopSoundPackages = usersManagerFrom.fetchShopSoundPackages()) == null) {
            disposableSubscribe = null;
        } else {
            final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
            Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SubscribtionPromoActivity.fillAdapterData$lambda$3(anonymousClass1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            disposableSubscribe = observableFetchShopSoundPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SubscribtionPromoActivity.fillAdapterData$lambda$4(anonymousClass2, obj);
                }
            });
        }
        list.add(disposableSubscribe);
    }

    /* compiled from: SubscribtionPromoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$fillAdapterData$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<ShopSoundPackagesResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, SubscribtionPromoActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
            invoke2(shopSoundPackagesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ShopSoundPackagesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SubscribtionPromoActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SubscribtionPromoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.main.SubscribtionPromoActivity$fillAdapterData$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, SubscribtionPromoActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SubscribtionPromoActivity) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillAdapterData$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fillAdapterData$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(ShopSoundPackagesResponse response) {
        if (response.isSuccessful()) {
            List<ShopSoundPackage> soundItems = response.getSoundItems();
            if (soundItems == null) {
                soundItems = CollectionsKt.emptyList();
            }
            List mutableList = CollectionsKt.toMutableList((Collection) soundItems);
            SoundsPresentationAdapter packsAdapter = getPacksAdapter();
            List list = mutableList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((ShopSoundPackage) it.next()).getPkgImageUrl());
            }
            packsAdapter.clearAndAddAll(arrayList);
            launchLoopingRecyclerAutoscroll();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, this, response.getError(), null, 4, null);
        if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
        }
        handleError(new Exception(response.getError()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, this, error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        Timber.INSTANCE.e(error);
        FirebaseCrashlytics.getInstance().recordException(error);
    }

    @Override // com.thor.app.gui.activities.shop.main.Hilt_SubscriptionCheckActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        for (Disposable disposable : this.disposables) {
            if (disposable != null) {
                disposable.dispose();
            }
        }
        super.onDestroy();
    }

    @Override // com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getPacksAdapter().notifyDataSetChanged();
        if (Settings.INSTANCE.isSubscriptionActive()) {
            finish();
        }
    }

    private final void launchLoopingRecyclerAutoscroll() {
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding = this.binding;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding2 = null;
        if (activityShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding = null;
        }
        activityShopSoundPackageBinding.autoscrollRecycler.setCanTouch(true);
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding3 = this.binding;
        if (activityShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding3 = null;
        }
        activityShopSoundPackageBinding3.autoscrollRecycler.setLoopEnabled(true);
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding4 = this.binding;
        if (activityShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopSoundPackageBinding2 = activityShopSoundPackageBinding4;
        }
        activityShopSoundPackageBinding2.autoscrollRecycler.startAutoScroll();
    }
}
