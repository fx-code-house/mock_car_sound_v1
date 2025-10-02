package com.thor.app.gui.activities.shop.sgu;

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
import com.thor.app.gui.activities.shop.main.SubscriptionCheckActivity;
import com.thor.app.gui.activities.shop.main.SubscriptionsActivity;
import com.thor.app.gui.adapters.shop.subscription.SoundsPresentationAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.StringKt;
import com.thor.basemodule.extensions.MeasureKt;
import com.thor.basemodule.extensions.NumberKt;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetsResponse;
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

/* compiled from: SguSubscriptionPromoActivity.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\b\u0010\u001b\u001a\u00020\u0011H\u0002J\b\u0010\u001c\u001a\u00020\u0011H\u0002J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\u0012\u0010\u001e\u001a\u00020\u00112\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010!\u001a\u00020\u0011H\u0014J\b\u0010\"\u001a\u00020\u0011H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/thor/app/gui/activities/shop/sgu/SguSubscriptionPromoActivity;", "Lcom/thor/app/gui/activities/shop/main/SubscriptionCheckActivity;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityShopSoundPackageBinding;", "disposables", "", "Lio/reactivex/disposables/Disposable;", "packsAdapter", "Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "getPacksAdapter", "()Lcom/thor/app/gui/adapters/shop/subscription/SoundsPresentationAdapter;", "packsAdapter$delegate", "Lkotlin/Lazy;", "soundSet", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "fillAdapterData", "", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;", "init", "initAdapter", "initIntentExtra", "initListeners", "initUI", "launchLoopingRecyclerAutoscroll", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onResume", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class SguSubscriptionPromoActivity extends SubscriptionCheckActivity {
    private ActivityShopSoundPackageBinding binding;
    private final List<Disposable> disposables = new ArrayList();

    /* renamed from: packsAdapter$delegate, reason: from kotlin metadata */
    private final Lazy packsAdapter = LazyKt.lazy(new Function0<SoundsPresentationAdapter>() { // from class: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$packsAdapter$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SoundsPresentationAdapter invoke() {
            return new SoundsPresentationAdapter();
        }
    });
    private SguSoundSet soundSet;

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
        String setImageUrl;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding = this.binding;
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding2 = null;
        if (activityShopSoundPackageBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding = null;
        }
        AppCompatTextView appCompatTextView = activityShopSoundPackageBinding.presetName;
        SguSoundSet sguSoundSet = this.soundSet;
        appCompatTextView.setText(sguSoundSet != null ? sguSoundSet.getSetName() : null);
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding3 = this.binding;
        if (activityShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding3 = null;
        }
        AppCompatButton appCompatButton = activityShopSoundPackageBinding3.actionBuy;
        Object[] objArr = new Object[1];
        SguSoundSet sguSoundSet2 = this.soundSet;
        Float fValueOf = sguSoundSet2 != null ? Float.valueOf(NumberKt.roundTo(sguSoundSet2.getPrice(), 2)) : null;
        SguSoundSet sguSoundSet3 = this.soundSet;
        objArr[0] = fValueOf + StringUtils.SPACE + (sguSoundSet3 != null ? sguSoundSet3.getCurrency() : null);
        appCompatButton.setText(getString(R.string.text_form_buy_one_pack, objArr));
        SguSoundSet sguSoundSet4 = this.soundSet;
        GlideRequest<Drawable> glideRequestError = GlideApp.with((FragmentActivity) this).load((sguSoundSet4 == null || (setImageUrl = sguSoundSet4.getSetImageUrl()) == null) ? null : StringKt.getFullFileUrl(setImageUrl)).dontTransform().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_thor_logo_small);
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
        activityShopSoundPackageBinding.actionClose.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SguSubscriptionPromoActivity.initListeners$lambda$0(this.f$0, view);
            }
        });
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding3 = this.binding;
        if (activityShopSoundPackageBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityShopSoundPackageBinding3 = null;
        }
        activityShopSoundPackageBinding3.actionSubscribe.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SguSubscriptionPromoActivity.initListeners$lambda$1(this.f$0, view);
            }
        });
        ActivityShopSoundPackageBinding activityShopSoundPackageBinding4 = this.binding;
        if (activityShopSoundPackageBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityShopSoundPackageBinding2 = activityShopSoundPackageBinding4;
        }
        activityShopSoundPackageBinding2.actionBuy.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SguSubscriptionPromoActivity.initListeners$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$0(SguSubscriptionPromoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$1(SguSubscriptionPromoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) SubscriptionsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this$0, new Pair[0]).toBundle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initListeners$lambda$2(SguSubscriptionPromoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0, (Class<?>) SguSetDetailsActivity.class);
        intent.putExtra(SguSoundSet.BUNDLE_NAME, this$0.soundSet);
        this$0.startActivity(intent);
    }

    private final void initIntentExtra() {
        this.soundSet = (SguSoundSet) getIntent().getParcelableExtra(SguSoundSet.BUNDLE_NAME);
    }

    private final void fillAdapterData() {
        Disposable disposableSubscribe;
        Observable<SguSoundSetsResponse> observableFetchSoundSguPackages;
        List<Disposable> list = this.disposables;
        UsersManager usersManagerFrom = UsersManager.INSTANCE.from(this);
        if (usersManagerFrom == null || (observableFetchSoundSguPackages = usersManagerFrom.fetchSoundSguPackages()) == null) {
            disposableSubscribe = null;
        } else {
            final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
            Consumer<? super SguSoundSetsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSubscriptionPromoActivity.fillAdapterData$lambda$3(anonymousClass1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            disposableSubscribe = observableFetchSoundSguPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    SguSubscriptionPromoActivity.fillAdapterData$lambda$4(anonymousClass2, obj);
                }
            });
        }
        list.add(disposableSubscribe);
    }

    /* compiled from: SguSubscriptionPromoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$fillAdapterData$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<SguSoundSetsResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, SguSubscriptionPromoActivity.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SguSoundSetsResponse sguSoundSetsResponse) {
            invoke2(sguSoundSetsResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(SguSoundSetsResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SguSubscriptionPromoActivity) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: SguSubscriptionPromoActivity.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.shop.sgu.SguSubscriptionPromoActivity$fillAdapterData$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, SguSubscriptionPromoActivity.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((SguSubscriptionPromoActivity) this.receiver).handleError(p0);
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
    public final void handleResponse(SguSoundSetsResponse response) {
        if (response.isSuccessful()) {
            List<SguSoundSet> soundSetsItems = response.getSoundSetsItems();
            if (soundSetsItems == null) {
                soundSetsItems = CollectionsKt.emptyList();
            }
            List mutableList = CollectionsKt.toMutableList((Collection) soundSetsItems);
            SoundsPresentationAdapter packsAdapter = getPacksAdapter();
            List list = mutableList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((SguSoundSet) it.next()).getSetImageUrl());
            }
            packsAdapter.clearAndAddAll(arrayList);
            launchLoopingRecyclerAutoscroll();
            return;
        }
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(this, response.getError(), Integer.valueOf(response.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialog != null) {
                alertDialogCreateErrorAlertDialog.show();
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
