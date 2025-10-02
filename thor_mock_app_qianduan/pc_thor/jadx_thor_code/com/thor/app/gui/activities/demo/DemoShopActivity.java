package com.thor.app.gui.activities.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ActivityDemoShopBinding;
import com.google.firebase.messaging.Constants;
import com.thor.app.gui.adapters.demo.DemoShopSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.utils.theming.ThemingUtil;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import com.thor.networkmodule.network.OnLoadDataListener;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import timber.log.Timber;

/* compiled from: DemoShopActivity.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0003H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0002J\u0012\u0010\u0016\u001a\u00020\u00102\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0010H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u001a"}, d2 = {"Lcom/thor/app/gui/activities/demo/DemoShopActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/thor/networkmodule/network/OnLoadDataListener;", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "()V", "binding", "Lcom/carsystems/thor/app/databinding/ActivityDemoShopBinding;", "mAdapter", "Lcom/thor/app/gui/adapters/demo/DemoShopSoundPackagesRvAdapter;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "getUsersManager", "()Lcom/thor/app/managers/UsersManager;", "usersManager$delegate", "Lkotlin/Lazy;", "handleError", "", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "value", "initAdapter", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onLoadData", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class DemoShopActivity extends AppCompatActivity implements OnLoadDataListener<ShopSoundPackagesResponse> {
    private ActivityDemoShopBinding binding;
    private DemoShopSoundPackagesRvAdapter mAdapter;

    /* renamed from: usersManager$delegate, reason: from kotlin metadata */
    private final Lazy usersManager = LazyKt.lazy(new Function0<UsersManager>() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$usersManager$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final UsersManager invoke() {
            return UsersManager.INSTANCE.from(this.this$0);
        }
    });

    private final UsersManager getUsersManager() {
        return (UsersManager) this.usersManager.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoShopActivity demoShopActivity = this;
        ThemingUtil.INSTANCE.onActivityCreateSetTheme(demoShopActivity);
        ViewDataBinding contentView = DataBindingUtil.setContentView(demoShopActivity, R.layout.activity_demo_shop);
        Intrinsics.checkNotNullExpressionValue(contentView, "setContentView(this, R.layout.activity_demo_shop)");
        ActivityDemoShopBinding activityDemoShopBinding = (ActivityDemoShopBinding) contentView;
        this.binding = activityDemoShopBinding;
        ActivityDemoShopBinding activityDemoShopBinding2 = null;
        if (activityDemoShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding = null;
        }
        activityDemoShopBinding.toolbarWidget.setHomeButtonVisibility(true);
        ActivityDemoShopBinding activityDemoShopBinding3 = this.binding;
        if (activityDemoShopBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding3 = null;
        }
        activityDemoShopBinding3.toolbarWidget.setHomeOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DemoShopActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        ActivityDemoShopBinding activityDemoShopBinding4 = this.binding;
        if (activityDemoShopBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityDemoShopBinding2 = activityDemoShopBinding4;
        }
        activityDemoShopBinding2.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                DemoShopActivity.onCreate$lambda$1(this.f$0);
            }
        });
        initAdapter();
        onLoadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(DemoShopActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(DemoShopActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onLoadData();
    }

    private final void initAdapter() {
        this.mAdapter = new DemoShopSoundPackagesRvAdapter();
        ActivityDemoShopBinding activityDemoShopBinding = this.binding;
        DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter = null;
        if (activityDemoShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding = null;
        }
        activityDemoShopBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        ActivityDemoShopBinding activityDemoShopBinding2 = this.binding;
        if (activityDemoShopBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding2 = null;
        }
        RecyclerView recyclerView = activityDemoShopBinding2.recyclerView;
        DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter2 = this.mAdapter;
        if (demoShopSoundPackagesRvAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            demoShopSoundPackagesRvAdapter2 = null;
        }
        recyclerView.setAdapter(demoShopSoundPackagesRvAdapter2);
        DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter3 = this.mAdapter;
        if (demoShopSoundPackagesRvAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            demoShopSoundPackagesRvAdapter3 = null;
        }
        ActivityDemoShopBinding activityDemoShopBinding3 = this.binding;
        if (activityDemoShopBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding3 = null;
        }
        demoShopSoundPackagesRvAdapter3.setRecyclerView(activityDemoShopBinding3.recyclerView);
        ActivityDemoShopBinding activityDemoShopBinding4 = this.binding;
        if (activityDemoShopBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding4 = null;
        }
        NestedScrollView nestedScrollView = activityDemoShopBinding4.nestedScrollView;
        DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter4 = this.mAdapter;
        if (demoShopSoundPackagesRvAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            demoShopSoundPackagesRvAdapter = demoShopSoundPackagesRvAdapter4;
        }
        nestedScrollView.setOnScrollChangeListener(demoShopSoundPackagesRvAdapter.getOnNestedScrollListener());
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void onLoadData() {
        Observable<ShopSoundPackagesResponse> observableFetchDemoShopSoundPackages = getUsersManager().fetchDemoShopSoundPackages();
        if (observableFetchDemoShopSoundPackages != null) {
            final Function1<Disposable, Unit> function1 = new Function1<Disposable, Unit>() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$onLoadData$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Disposable disposable) {
                    invoke2(disposable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Disposable disposable) {
                    ActivityDemoShopBinding activityDemoShopBinding = this.this$0.binding;
                    if (activityDemoShopBinding == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("binding");
                        activityDemoShopBinding = null;
                    }
                    activityDemoShopBinding.swipeContainer.setRefreshing(true);
                }
            };
            Observable<ShopSoundPackagesResponse> observableDoOnTerminate = observableFetchDemoShopSoundPackages.doOnSubscribe(new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoShopActivity.onLoadData$lambda$6$lambda$2(function1, obj);
                }
            }).doOnTerminate(new Action() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Action
                public final void run() {
                    DemoShopActivity.onLoadData$lambda$6$lambda$3(this.f$0);
                }
            });
            final DemoShopActivity$onLoadData$1$3 demoShopActivity$onLoadData$1$3 = new DemoShopActivity$onLoadData$1$3(this);
            Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoShopActivity.onLoadData$lambda$6$lambda$4(demoShopActivity$onLoadData$1$3, obj);
                }
            };
            final DemoShopActivity$onLoadData$1$4 demoShopActivity$onLoadData$1$4 = new DemoShopActivity$onLoadData$1$4(this);
            observableDoOnTerminate.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.demo.DemoShopActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    DemoShopActivity.onLoadData$lambda$6$lambda$5(demoShopActivity$onLoadData$1$4, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$6$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$6$lambda$3(DemoShopActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityDemoShopBinding activityDemoShopBinding = this$0.binding;
        if (activityDemoShopBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityDemoShopBinding = null;
        }
        activityDemoShopBinding.swipeContainer.setRefreshing(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$6$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onLoadData$lambda$6$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleResponse(ShopSoundPackagesResponse value) {
        Intrinsics.checkNotNullParameter(value, "value");
        Timber.INSTANCE.i("handleResponse: %s", value);
        DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter = null;
        if (value.isSuccessful()) {
            List<ShopSoundPackage> soundItems = value.getSoundItems();
            DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter2 = this.mAdapter;
            if (demoShopSoundPackagesRvAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            } else {
                demoShopSoundPackagesRvAdapter = demoShopSoundPackagesRvAdapter2;
            }
            demoShopSoundPackagesRvAdapter.clearAndAddAll(soundItems);
            return;
        }
        DemoShopActivity demoShopActivity = this;
        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(demoShopActivity, value.getError(), Integer.valueOf(value.getCode()));
        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
        }
        DemoShopSoundPackagesRvAdapter demoShopSoundPackagesRvAdapter3 = this.mAdapter;
        if (demoShopSoundPackagesRvAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            demoShopSoundPackagesRvAdapter = demoShopSoundPackagesRvAdapter3;
        }
        demoShopSoundPackagesRvAdapter.clear();
        Toast.makeText(demoShopActivity, "Error: " + value.getError(), 1).show();
    }

    @Override // com.thor.networkmodule.network.OnLoadDataListener
    public void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog;
        Intrinsics.checkNotNullParameter(error, "error");
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog2 = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0);
            if (alertDialogCreateErrorAlertDialog2 != null) {
                alertDialogCreateErrorAlertDialog2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog = DialogManager.INSTANCE.createErrorAlertDialog(this, error.getMessage(), 0)) != null) {
            alertDialogCreateErrorAlertDialog.show();
        }
        Timber.INSTANCE.e(error);
    }
}
