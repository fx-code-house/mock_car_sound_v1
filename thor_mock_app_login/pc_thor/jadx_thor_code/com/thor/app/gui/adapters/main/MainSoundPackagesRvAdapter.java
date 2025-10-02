package com.thor.app.gui.adapters.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import com.carsystems.thor.app.R;
import com.carsystems.thor.app.databinding.ItemMainSoundPackageBinding;
import com.carsystems.thor.app.databinding.ViewMainSoundPackageBinding;
import com.carsystems.thor.datalayermodule.datalayer.WearableDataLayerSender;
import com.carsystems.thor.datalayermodule.datalayer.datamaps.BooleanWearableDataLayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thor.app.bus.events.CheckPlaceholderHintEvent;
import com.thor.app.bus.events.OpenSettingsEvent;
import com.thor.app.bus.events.bluetooth.firmware.ApplyUpdateFirmwareSuccessfulEvent;
import com.thor.app.bus.events.mainpreset.OnClickMainPresetFromWearEvent;
import com.thor.app.bus.events.mainpreset.OnDamagePckEvent;
import com.thor.app.bus.events.mainpreset.StartDownloadDamagePck;
import com.thor.app.databinding.model.DriveSelectStatus;
import com.thor.app.gui.adapters.itemtouch.ItemMoveCallback;
import com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.gui.views.soundpackage.MainSoundPackageView;
import com.thor.app.managers.BleManager;
import com.thor.app.managers.DataLayerManager;
import com.thor.app.managers.UsersManager;
import com.thor.app.utils.security.DeviceLockingUtilsKt;
import com.thor.basemodule.extensions.ContextKt;
import com.thor.basemodule.gui.adapters.BaseDiffUtilCallback;
import com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter;
import com.thor.basemodule.gui.view.listener.OnSwipeTouchListener;
import com.thor.businessmodule.bluetooth.model.other.DriveMode;
import com.thor.businessmodule.bluetooth.model.other.InstalledPreset;
import com.thor.businessmodule.bluetooth.request.other.WriteDriveModesBleRequest;
import com.thor.businessmodule.bluetooth.response.other.ReadDriveModesBleResponse;
import com.thor.businessmodule.bluetooth.response.other.WriteDriveModesBleResponse;
import com.thor.businessmodule.model.MainPresetPackage;
import com.thor.businessmodule.viewmodel.views.MainPresetPackageViewModel;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

/* compiled from: MainSoundPackagesRvAdapter.kt */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\n\n\u0002\b\f\u0018\u0000 q2\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u00012\u00020\u0004:\u0003qrsB\u0007\b\u0016¢\u0006\u0002\u0010\u0005B%\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0012H\u0002J\u0006\u0010%\u001a\u00020#J(\u0010&\u001a\u00020#2\u000e\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010(2\u000e\u0010)\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010(H\u0016J\u001c\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020\u001a2\b\u0010-\u001a\u0004\u0018\u00010\u0002H\u0002J\b\u0010.\u001a\u00020#H\u0002J\b\u0010/\u001a\u00020#H\u0002J\u001e\u00100\u001a\u00020#2\u0016\u00101\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010J\u000e\u00102\u001a\u00020#2\u0006\u0010,\u001a\u00020\u001aJ\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020\u0014J\u0010\u00106\u001a\u0002072\u0006\u0010$\u001a\u00020\u0012H\u0002J\u0006\u00108\u001a\u00020\u001cJ,\u00109\u001a\u00020#2\u001a\u0010:\u001a\u0016\u0012\u0004\u0012\u00020;\u0018\u00010\u000ej\n\u0012\u0004\u0012\u00020;\u0018\u0001`\u00102\u0006\u0010$\u001a\u00020\u0012H\u0002J\b\u0010<\u001a\u00020#H\u0002J\u0014\u0010=\u001a\u00020>2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00020@J\u0010\u0010A\u001a\u0002042\b\u0010-\u001a\u0004\u0018\u00010\u0002J\u0016\u0010B\u001a\u00020#2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010D\u001a\u00020\u0017J\u001e\u0010E\u001a\u00020#2\u0006\u0010D\u001a\u00020\u00172\u0006\u0010F\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u0012J\u001c\u0010G\u001a\u00020#2\n\u0010H\u001a\u00060\u0003R\u00020\u00002\u0006\u0010F\u001a\u00020\u0014H\u0016J\u0010\u0010I\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0012H\u0002J \u0010J\u001a\u00020#2\u0006\u0010D\u001a\u00020\u00172\u0006\u0010F\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u0012H\u0002J\u001c\u0010K\u001a\u00060\u0003R\u00020\u00002\u0006\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020\u0014H\u0016J\u0018\u0010O\u001a\u00020#2\u0006\u0010C\u001a\u00020\u00122\u0006\u0010D\u001a\u00020\u0017H\u0002J\u0018\u0010P\u001a\u00020#2\u0006\u0010D\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u0012H\u0002J\b\u0010Q\u001a\u00020#H\u0016J\u0018\u0010R\u001a\u00020#2\u0006\u0010S\u001a\u00020\u00142\u0006\u0010T\u001a\u00020\u0014H\u0016J\u0010\u0010U\u001a\u00020#2\u0006\u0010V\u001a\u00020WH\u0007J\u0010\u0010U\u001a\u00020#2\u0006\u0010V\u001a\u00020XH\u0007J\u0010\u0010U\u001a\u00020#2\u0006\u0010V\u001a\u00020YH\u0007J\u0012\u0010Z\u001a\u00020#2\b\u0010[\u001a\u0004\u0018\u00010\u0002H\u0002J\u000e\u0010\\\u001a\u00020#2\u0006\u0010]\u001a\u00020\u0014J\u001e\u0010^\u001a\u00020#2\u0006\u0010]\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\u0006\u0010_\u001a\u00020>J\b\u0010`\u001a\u00020#H\u0002J\u0010\u0010a\u001a\u00020#2\b\u0010-\u001a\u0004\u0018\u00010\u0002J \u0010b\u001a\u00020#2\u0006\u0010D\u001a\u00020\u00172\u0006\u0010F\u001a\u00020\u00142\u0006\u0010$\u001a\u00020\u0012H\u0002J\u001e\u0010c\u001a\u00020#2\u0016\u0010d\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u000ej\b\u0012\u0004\u0012\u00020\u0002`\u0010J\u0006\u0010e\u001a\u00020#J\u000e\u0010f\u001a\u00020#2\u0006\u0010g\u001a\u00020hJ\u000e\u0010i\u001a\u00020#2\u0006\u0010]\u001a\u00020hJ\u0006\u0010j\u001a\u00020#J\u0006\u0010k\u001a\u00020#J\u0010\u0010l\u001a\u00020#2\b\u0010m\u001a\u0004\u0018\u00010\u001eJ\u0010\u0010n\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0012H\u0003J\u0006\u0010o\u001a\u00020#J\u0006\u0010p\u001a\u00020#R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006t"}, d2 = {"Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;", "Lcom/thor/basemodule/gui/adapters/BaseRecyclerListAdapter;", "Lcom/thor/businessmodule/model/MainPresetPackage;", "Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter$MainSoundPackageViewHolder;", "Lcom/thor/app/gui/adapters/itemtouch/ItemMoveCallback$OnItemMoveListener;", "()V", "diffUtilCallback", "Lcom/thor/basemodule/gui/adapters/BaseDiffUtilCallback;", "bleManager", "Lcom/thor/app/managers/BleManager;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "(Lcom/thor/basemodule/gui/adapters/BaseDiffUtilCallback;Lcom/thor/app/managers/BleManager;Lcom/thor/app/managers/UsersManager;)V", "driveSelectList", "Ljava/util/ArrayList;", "Lcom/thor/app/databinding/model/DriveSelectStatus;", "Lkotlin/collections/ArrayList;", "lastActiveElementBinding", "Lcom/carsystems/thor/app/databinding/ViewMainSoundPackageBinding;", "lastActiveElementPosition", "", "Ljava/lang/Integer;", "lastActiveElementView", "Landroid/view/View;", "mBleManager", "mDataLayerContext", "Landroid/content/Context;", "mOnNestedScrollListener", "Landroidx/core/widget/NestedScrollView$OnScrollChangeListener;", "mRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "mUsersManager", "savedListOfPressetsFromWear", "", "changeDriveMode", "", "mainBinding", "clearActiveStates", "compareByDiffUtil", "oldData", "", "newData", "createDeleteConfirmAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "context", "preset", "deselectDeleteStates", "deselectList", "enableDriveSelect", "driveSelectStatuses", "enableSyncData", "getActivatedPreset", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPreset;", "getActivatedPresetIndex", "getNextStatus", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode$DRIVE_MODE;", "getOnNestedScrollListener", "handleDriveModes", "list", "Lcom/thor/businessmodule/bluetooth/model/other/DriveMode;", "initNestedScrollListener", "isDataSame", "", "collection", "", "mapMainPresetToInstaledPreset", "onActivate", "binding", "view", "onActivateCommand", "position", "onBindViewHolder", "holder", "onChangeDriveMode", "onClickMainLayout", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onDeactivate", "onDeactivateCommand", "onFinishMoving", "onItemMove", "fromPosition", "toPosition", "onMessageEvent", "event", "Lcom/thor/app/bus/events/bluetooth/firmware/ApplyUpdateFirmwareSuccessfulEvent;", "Lcom/thor/app/bus/events/mainpreset/OnClickMainPresetFromWearEvent;", "Lcom/thor/app/bus/events/mainpreset/OnDamagePckEvent;", "onOpenSettings", "item", "onReceiveClickFromWatches", FirebaseAnalytics.Param.INDEX, "onReceiveClickFromWatchesInService", "isRunningAppOnPhone", "onWritePresets", "removePreset", "reselectLastActiveItem", "saveListOfPackagesForFutureUse", "tempPresets", "sendChangedDataToWearable", "setActivatedPreset", "presetId", "", "setDeleteEnabledPreset", "setNextActivatedPreset", "setPreviousActivatedPreset", "setRecyclerView", "rv", "trySendStatistics", "updateSoundPackageInThread", "updateToCurrentDriveSelect", "Companion", "MainSoundPackageViewHolder", "OnSwipeListener", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class MainSoundPackagesRvAdapter extends BaseRecyclerListAdapter<MainPresetPackage, MainSoundPackageViewHolder> implements ItemMoveCallback.OnItemMoveListener {
    private static final long ANIMATION_DURATION = 240;
    private static final float SCALE_MAX = 1.3f;
    private static final float SCALE_MIN = 1.0f;
    private ArrayList<DriveSelectStatus> driveSelectList;
    private ViewMainSoundPackageBinding lastActiveElementBinding;
    private Integer lastActiveElementPosition;
    private View lastActiveElementView;
    private BleManager mBleManager;
    private Context mDataLayerContext;
    private NestedScrollView.OnScrollChangeListener mOnNestedScrollListener;
    private RecyclerView mRecyclerView;
    private UsersManager mUsersManager;
    private final List<MainPresetPackage> savedListOfPressetsFromWear;

    public MainSoundPackagesRvAdapter() throws SecurityException {
        this.driveSelectList = new ArrayList<>();
        this.savedListOfPressetsFromWear = new ArrayList();
        EventBus.getDefault().register(this);
        initNestedScrollListener();
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                if (MainSoundPackagesRvAdapter.this.mDataLayerContext != null) {
                    DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
                    Context context = MainSoundPackagesRvAdapter.this.mDataLayerContext;
                    if (context == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
                        context = null;
                    }
                    DataLayerManager dataLayerManagerFrom = companion.from(context);
                    List mList = MainSoundPackagesRvAdapter.this.mList;
                    Intrinsics.checkNotNullExpressionValue(mList, "mList");
                    dataLayerManagerFrom.sendMainPresetPackages(mList);
                }
            }
        });
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MainSoundPackagesRvAdapter(BaseDiffUtilCallback<MainPresetPackage> diffUtilCallback, BleManager bleManager, UsersManager usersManager) throws SecurityException {
        super(diffUtilCallback);
        Intrinsics.checkNotNullParameter(diffUtilCallback, "diffUtilCallback");
        Intrinsics.checkNotNullParameter(bleManager, "bleManager");
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        this.driveSelectList = new ArrayList<>();
        this.savedListOfPressetsFromWear = new ArrayList();
        EventBus.getDefault().register(this);
        initNestedScrollListener();
        this.mBleManager = bleManager;
        this.mUsersManager = usersManager;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                super.onChanged();
                MainSoundPackagesRvAdapter.this.sendChangedDataToWearable();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MainSoundPackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater layoutInflater = (LayoutInflater) new WeakReference(LayoutInflater.from(parent.getContext())).get();
        Intrinsics.checkNotNull(layoutInflater);
        ItemMainSoundPackageBinding itemMainSoundPackageBindingInflate = ItemMainSoundPackageBinding.inflate(layoutInflater, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemMainSoundPackageBindingInflate, "inflate(inflater!!, parent, false)");
        View root = itemMainSoundPackageBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        return new MainSoundPackageViewHolder(this, root);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MainSoundPackageViewHolder holder, int position) throws Resources.NotFoundException {
        MainSoundPackageView mainSoundPackageView;
        ViewMainSoundPackageBinding mainBinding;
        MainSoundPackageView mainSoundPackageView2;
        ViewMainSoundPackageBinding mainBinding2;
        MainPresetPackageViewModel model;
        ObservableBoolean isDamageEnabled;
        MainSoundPackageView mainSoundPackageView3;
        ViewMainSoundPackageBinding mainBinding3;
        MainSoundPackageView mainSoundPackageView4;
        ViewMainSoundPackageBinding mainBinding4;
        MainPresetPackageViewModel model2;
        ObservableBoolean isDamageEnabled2;
        MainSoundPackageView mainSoundPackageView5;
        ViewMainSoundPackageBinding mainBinding5;
        TextView textView;
        MainSoundPackageView mainSoundPackageView6;
        ViewMainSoundPackageBinding mainBinding6;
        FrameLayout frameLayout;
        MainSoundPackageView mainSoundPackageView7;
        ViewMainSoundPackageBinding mainBinding7;
        ImageView imageView;
        MainSoundPackageView mainSoundPackageView8;
        ViewMainSoundPackageBinding mainBinding8;
        FrameLayout frameLayout2;
        MainSoundPackageView mainSoundPackageView9;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Timber.INSTANCE.i("onBindViewHolder " + position, new Object[0]);
        ItemMainSoundPackageBinding binding = holder.getBinding();
        if (binding != null && (mainSoundPackageView9 = binding.mainSoundPackageView) != null) {
            MainPresetPackage item = getItem(holder.getAdapterPosition());
            Intrinsics.checkNotNullExpressionValue(item, "getItem(holder.adapterPosition)");
            mainSoundPackageView9.setMainPresetPackage(item);
        }
        ItemMainSoundPackageBinding binding2 = holder.getBinding();
        if (binding2 != null && (mainSoundPackageView8 = binding2.mainSoundPackageView) != null && (mainBinding8 = mainSoundPackageView8.getMainBinding()) != null && (frameLayout2 = mainBinding8.layoutCover) != null) {
            Context context = holder.getBinding().mainSoundPackageView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "holder.binding.mainSoundPackageView.context");
            ViewMainSoundPackageBinding mainBinding9 = holder.getBinding().mainSoundPackageView.getMainBinding();
            MainSoundPackageView mainSoundPackageView10 = holder.getBinding().mainSoundPackageView;
            Intrinsics.checkNotNullExpressionValue(mainSoundPackageView10, "holder.binding.mainSoundPackageView");
            frameLayout2.setOnTouchListener(new OnSwipeListener(this, context, mainBinding9, mainSoundPackageView10));
        }
        ItemMainSoundPackageBinding binding3 = holder.getBinding();
        if (binding3 != null && (mainSoundPackageView7 = binding3.mainSoundPackageView) != null && (mainBinding7 = mainSoundPackageView7.getMainBinding()) != null && (imageView = mainBinding7.imageViewSettings) != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainSoundPackagesRvAdapter.onBindViewHolder$lambda$0(this.f$0, holder, view);
                }
            });
        }
        ItemMainSoundPackageBinding binding4 = holder.getBinding();
        if (binding4 != null && (mainSoundPackageView6 = binding4.mainSoundPackageView) != null && (mainBinding6 = mainSoundPackageView6.getMainBinding()) != null && (frameLayout = mainBinding6.layoutDelete) != null) {
            frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws Resources.NotFoundException {
                    MainSoundPackagesRvAdapter.onBindViewHolder$lambda$1(holder, this, view);
                }
            });
        }
        ItemMainSoundPackageBinding binding5 = holder.getBinding();
        if (binding5 != null && (mainSoundPackageView5 = binding5.mainSoundPackageView) != null && (mainBinding5 = mainSoundPackageView5.getMainBinding()) != null && (textView = mainBinding5.textViewDriveMode) != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainSoundPackagesRvAdapter.onBindViewHolder$lambda$2(this.f$0, holder, view);
                }
            });
        }
        Log.i("MainSoundPackagesRvAdapter2", "onBindViewHolder: " + getItem(position));
        MainPresetPackageViewModel model3 = null;
        if (getItem(position).getDamageItem()) {
            ItemMainSoundPackageBinding binding6 = holder.getBinding();
            if (binding6 != null && (mainSoundPackageView4 = binding6.mainSoundPackageView) != null && (mainBinding4 = mainSoundPackageView4.getMainBinding()) != null && (model2 = mainBinding4.getModel()) != null && (isDamageEnabled2 = model2.getIsDamageEnabled()) != null) {
                isDamageEnabled2.set(true);
            }
            ItemMainSoundPackageBinding binding7 = holder.getBinding();
            if (binding7 != null && (mainSoundPackageView3 = binding7.mainSoundPackageView) != null && (mainBinding3 = mainSoundPackageView3.getMainBinding()) != null) {
                model3 = mainBinding3.getModel();
            }
            if (model3 == null) {
                return;
            }
            model3.setPresetName(String.valueOf(getItem(position).getName()));
            return;
        }
        ItemMainSoundPackageBinding binding8 = holder.getBinding();
        if (binding8 != null && (mainSoundPackageView2 = binding8.mainSoundPackageView) != null && (mainBinding2 = mainSoundPackageView2.getMainBinding()) != null && (model = mainBinding2.getModel()) != null && (isDamageEnabled = model.getIsDamageEnabled()) != null) {
            isDamageEnabled.set(false);
        }
        ItemMainSoundPackageBinding binding9 = holder.getBinding();
        if (binding9 != null && (mainSoundPackageView = binding9.mainSoundPackageView) != null && (mainBinding = mainSoundPackageView.getMainBinding()) != null) {
            model3 = mainBinding.getModel();
        }
        if (model3 == null) {
            return;
        }
        model3.setPresetName(String.valueOf(getItem(position).getName()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(MainSoundPackagesRvAdapter this$0, MainSoundPackageViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        BleManager bleManager = this$0.mBleManager;
        if (bleManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
            bleManager = null;
        }
        if (bleManager.getMStateConnected()) {
            this$0.onOpenSettings(holder.getBinding().mainSoundPackageView.getMainBinding().getPresetPackage());
            return;
        }
        DialogManager dialogManager = DialogManager.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "it.context");
        dialogManager.createNoConnectionToBoardAlertDialog(context).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(MainSoundPackageViewHolder holder, MainSoundPackagesRvAdapter this$0, View view) throws Resources.NotFoundException {
        ObservableBoolean isDeleteEnabled;
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        MainPresetPackageViewModel model = holder.getBinding().mainSoundPackageView.getMainBinding().getModel();
        boolean z = false;
        if (model != null && (isDeleteEnabled = model.getIsDeleteEnabled()) != null && isDeleteEnabled.get()) {
            z = true;
        }
        if (z) {
            BleManager bleManager = this$0.mBleManager;
            if (bleManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
                bleManager = null;
            }
            if (bleManager.getMStateConnected()) {
                holder.getBinding().mainSoundPackageView.onSwipeToRightAnimation();
                Context context = view.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "it.context");
                AlertDialog alertDialogCreateDeleteConfirmAlertDialog = this$0.createDeleteConfirmAlertDialog(context, holder.getBinding().mainSoundPackageView.getMainBinding().getPresetPackage());
                if (alertDialogCreateDeleteConfirmAlertDialog != null) {
                    alertDialogCreateDeleteConfirmAlertDialog.show();
                    return;
                }
                return;
            }
            DialogManager dialogManager = DialogManager.INSTANCE;
            Context context2 = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "it.context");
            dialogManager.createNoConnectionToBoardAlertDialog(context2).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(MainSoundPackagesRvAdapter this$0, MainSoundPackageViewHolder holder, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        this$0.onChangeDriveMode(holder.getBinding().mainSoundPackageView.getMainBinding());
    }

    private final AlertDialog createDeleteConfirmAlertDialog(Context context, final MainPresetPackage preset) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 2131886083);
        builder.setTitle(R.string.text_delete).setMessage(R.string.message_delete_confirm).setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.text_hide, new DialogInterface.OnClickListener() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainSoundPackagesRvAdapter.createDeleteConfirmAlertDialog$lambda$3(this.f$0, preset, dialogInterface, i);
            }
        });
        return builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createDeleteConfirmAlertDialog$lambda$3(MainSoundPackagesRvAdapter this$0, MainPresetPackage mainPresetPackage, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            Collection<MainPresetPackage> all = this$0.getAll();
            Intrinsics.checkNotNullExpressionValue(all, "all");
            if (!all.isEmpty()) {
                this$0.removePreset(mainPresetPackage);
            }
        } catch (Exception e) {
            Timber.INSTANCE.d(e);
        }
    }

    /* compiled from: MainSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\b\u0010\u000e\u001a\u00020\fH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016J\b\u0010\u0010\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter$OnSwipeListener;", "Lcom/thor/basemodule/gui/view/listener/OnSwipeTouchListener;", "context", "Landroid/content/Context;", "mainBinding", "Lcom/carsystems/thor/app/databinding/ViewMainSoundPackageBinding;", "view", "Lcom/thor/app/gui/views/soundpackage/MainSoundPackageView;", "(Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;Landroid/content/Context;Lcom/carsystems/thor/app/databinding/ViewMainSoundPackageBinding;Lcom/thor/app/gui/views/soundpackage/MainSoundPackageView;)V", "getView", "()Lcom/thor/app/gui/views/soundpackage/MainSoundPackageView;", "onSingleTap", "", "onSwipeBottom", "onSwipeLeft", "onSwipeRight", "onSwipeTop", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class OnSwipeListener extends OnSwipeTouchListener {
        private final ViewMainSoundPackageBinding mainBinding;
        final /* synthetic */ MainSoundPackagesRvAdapter this$0;
        private final MainSoundPackageView view;

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeBottom() {
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeTop() {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnSwipeListener(MainSoundPackagesRvAdapter mainSoundPackagesRvAdapter, Context context, ViewMainSoundPackageBinding mainBinding, MainSoundPackageView view) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(mainBinding, "mainBinding");
            Intrinsics.checkNotNullParameter(view, "view");
            this.this$0 = mainSoundPackagesRvAdapter;
            this.mainBinding = mainBinding;
            this.view = view;
        }

        public final MainSoundPackageView getView() {
            return this.view;
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeRight() throws Resources.NotFoundException {
            Timber.INSTANCE.i("onSwipeRight", new Object[0]);
            this.view.onSwipeToRightAnimation();
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSwipeLeft() throws Resources.NotFoundException {
            Timber.INSTANCE.i("onSwipeLeft", new Object[0]);
            MainPresetPackageViewModel model = this.mainBinding.getModel();
            ObservableBoolean isDeleteEnabled = model != null ? model.getIsDeleteEnabled() : null;
            Intrinsics.checkNotNull(isDeleteEnabled);
            if (!isDeleteEnabled.get()) {
                this.this$0.deselectDeleteStates();
            }
            this.view.onSwipeToLeftAnimation();
        }

        @Override // com.thor.basemodule.gui.view.listener.OnSwipeTouchListener
        public void onSingleTap() {
            BleManager bleManager = this.this$0.mBleManager;
            if (bleManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
                bleManager = null;
            }
            if (bleManager.getMStateConnected()) {
                MainSoundPackagesRvAdapter mainSoundPackagesRvAdapter = this.this$0;
                MainSoundPackageView mainSoundPackageView = this.view;
                Collection<MainPresetPackage> all = mainSoundPackagesRvAdapter.getAll();
                Intrinsics.checkNotNullExpressionValue(all, "all");
                mainSoundPackagesRvAdapter.onClickMainLayout(mainSoundPackageView, CollectionsKt.indexOf(all, this.mainBinding.getPresetPackage()), this.mainBinding);
                return;
            }
            DialogManager dialogManager = DialogManager.INSTANCE;
            Context context = this.view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "view.context");
            dialogManager.createNoConnectionToBoardAlertDialog(context).show();
        }
    }

    private final void onOpenSettings(MainPresetPackage item) {
        EventBus.getDefault().post(new OpenSettingsEvent(item));
    }

    private final void deselectList() {
        Collection<MainPresetPackage> all = getAll();
        Intrinsics.checkNotNullExpressionValue(all, "all");
        int i = 0;
        for (Object obj : all) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            RecyclerView recyclerView = this.mRecyclerView;
            Intrinsics.checkNotNull(recyclerView);
            RecyclerView recyclerView2 = this.mRecyclerView;
            Intrinsics.checkNotNull(recyclerView2);
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(recyclerView2.getChildAt(i));
            MainSoundPackageViewHolder mainSoundPackageViewHolder = childViewHolder instanceof MainSoundPackageViewHolder ? (MainSoundPackageViewHolder) childViewHolder : null;
            if (mainSoundPackageViewHolder != null && mainSoundPackageViewHolder.getBinding() != null) {
                ViewMainSoundPackageBinding mainBinding = mainSoundPackageViewHolder.getBinding().mainSoundPackageView.getMainBinding();
                ConstraintLayout constraintLayout = mainSoundPackageViewHolder.getBinding().mainSoundPackageView.getMainBinding().mainLayout;
                Intrinsics.checkNotNullExpressionValue(constraintLayout, "holder.binding.mainSound…tMainBinding().mainLayout");
                onDeactivate(mainBinding, constraintLayout);
            }
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deselectDeleteStates() throws Resources.NotFoundException {
        Collection<MainPresetPackage> all = getAll();
        Intrinsics.checkNotNullExpressionValue(all, "all");
        int i = 0;
        for (Object obj : all) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            RecyclerView recyclerView = this.mRecyclerView;
            Intrinsics.checkNotNull(recyclerView);
            RecyclerView recyclerView2 = this.mRecyclerView;
            Intrinsics.checkNotNull(recyclerView2);
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(recyclerView2.getChildAt(i));
            MainSoundPackageViewHolder mainSoundPackageViewHolder = childViewHolder instanceof MainSoundPackageViewHolder ? (MainSoundPackageViewHolder) childViewHolder : null;
            if (mainSoundPackageViewHolder != null && mainSoundPackageViewHolder.getBinding() != null) {
                mainSoundPackageViewHolder.getBinding().mainSoundPackageView.cleanSwipe();
            }
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onClickMainLayout(View view, int position, ViewMainSoundPackageBinding mainBinding) {
        ObservableBoolean isActivate;
        ObservableBoolean isDamageEnabled;
        boolean z = false;
        Timber.INSTANCE.i("onClickMainLayout", new Object[0]);
        MainPresetPackageViewModel model = mainBinding.getModel();
        if (model != null && (isDamageEnabled = model.getIsDamageEnabled()) != null && isDamageEnabled.get()) {
            z = true;
        }
        if (z) {
            DialogManager dialogManager = DialogManager.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "view.context");
            MainPresetPackageViewModel model2 = mainBinding.getModel();
            dialogManager.createDamagePckAlertDialog(context, String.valueOf(model2 != null ? model2.getPresetName() : null), new C02521(position)).show();
            return;
        }
        MainPresetPackageViewModel model3 = mainBinding.getModel();
        if (model3 == null || (isActivate = model3.getIsActivate()) == null) {
            return;
        }
        boolean z2 = isActivate.get();
        if (z2) {
            onDeactivateCommand(view, mainBinding);
            this.lastActiveElementView = null;
            this.lastActiveElementPosition = null;
            this.lastActiveElementBinding = null;
            return;
        }
        if (z2) {
            return;
        }
        deselectList();
        onActivateCommand(view, position, mainBinding);
    }

    /* compiled from: MainSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$onClickMainLayout$1, reason: invalid class name and case insensitive filesystem */
    static final class C02521 extends Lambda implements Function0<Unit> {
        final /* synthetic */ int $position;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C02521(int i) {
            super(0);
            this.$position = i;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            UsersManager usersManager = MainSoundPackagesRvAdapter.this.mUsersManager;
            UsersManager usersManager2 = null;
            if (usersManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mUsersManager");
                usersManager = null;
            }
            Observable<ShopSoundPackagesResponse> observableFetchShopSoundPackages = usersManager.fetchShopSoundPackages();
            if (observableFetchShopSoundPackages != null) {
                final MainSoundPackagesRvAdapter mainSoundPackagesRvAdapter = MainSoundPackagesRvAdapter.this;
                final int i = this.$position;
                final Function1<ShopSoundPackagesResponse, Unit> function1 = new Function1<ShopSoundPackagesResponse, Unit>() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$onClickMainLayout$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
                        invoke2(shopSoundPackagesResponse);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ShopSoundPackagesResponse shopSoundPackagesResponse) {
                        RecyclerView recyclerView;
                        Context context;
                        if (shopSoundPackagesResponse.isSuccessful()) {
                            MainPresetPackage item = mainSoundPackagesRvAdapter.getItem(i);
                            List<ShopSoundPackage> soundItems = shopSoundPackagesResponse.getSoundItems();
                            ShopSoundPackage shopSoundPackage = null;
                            if (soundItems != null) {
                                Iterator<T> it = soundItems.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Object next = it.next();
                                    int id = ((ShopSoundPackage) next).getId();
                                    Integer packageId = item.getPackageId();
                                    if (packageId != null && id == packageId.intValue()) {
                                        shopSoundPackage = next;
                                        break;
                                    }
                                }
                                shopSoundPackage = shopSoundPackage;
                            }
                            if (shopSoundPackage != null) {
                                EventBus.getDefault().post(new StartDownloadDamagePck(shopSoundPackage));
                                return;
                            }
                            return;
                        }
                        String error = shopSoundPackagesResponse.getError();
                        if (error == null || (recyclerView = mainSoundPackagesRvAdapter.mRecyclerView) == null || (context = recyclerView.getContext()) == null) {
                            return;
                        }
                        Intrinsics.checkNotNullExpressionValue(context, "context");
                        AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = DialogManager.INSTANCE.createErrorAlertDialogWithSendLogOption(context, error, 0);
                        if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
                            alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                        }
                    }
                };
                Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$onClickMainLayout$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainSoundPackagesRvAdapter.C02521.invoke$lambda$3$lambda$0(function1, obj);
                    }
                };
                final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$onClickMainLayout$1$1$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(Throwable th) {
                        if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                            DialogManager dialogManager = DialogManager.INSTANCE;
                            RecyclerView recyclerView = mainSoundPackagesRvAdapter.mRecyclerView;
                            AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption = dialogManager.createErrorAlertDialogWithSendLogOption(recyclerView != null ? recyclerView.getContext() : null, th.getMessage(), 0);
                            if (alertDialogCreateErrorAlertDialogWithSendLogOption != null) {
                                alertDialogCreateErrorAlertDialogWithSendLogOption.show();
                                return;
                            }
                            return;
                        }
                        if (Intrinsics.areEqual(th.getMessage(), "HTTP 500")) {
                            DialogManager dialogManager2 = DialogManager.INSTANCE;
                            RecyclerView recyclerView2 = mainSoundPackagesRvAdapter.mRecyclerView;
                            AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption2 = dialogManager2.createErrorAlertDialogWithSendLogOption(recyclerView2 != null ? recyclerView2.getContext() : null, th.getMessage(), 0);
                            if (alertDialogCreateErrorAlertDialogWithSendLogOption2 != null) {
                                alertDialogCreateErrorAlertDialogWithSendLogOption2.show();
                            }
                        }
                    }
                };
                Disposable disposableSubscribe = observableFetchShopSoundPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$onClickMainLayout$1$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MainSoundPackagesRvAdapter.C02521.invoke$lambda$3$lambda$1(function12, obj);
                    }
                });
                UsersManager usersManager3 = mainSoundPackagesRvAdapter.mUsersManager;
                if (usersManager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mUsersManager");
                } else {
                    usersManager2 = usersManager3;
                }
                usersManager2.addDisposable(disposableSubscribe);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3$lambda$1(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke(obj);
        }
    }

    @Subscribe
    public final void onMessageEvent(ApplyUpdateFirmwareSuccessfulEvent event) {
        Integer num;
        Intrinsics.checkNotNullParameter(event, "event");
        View view = this.lastActiveElementView;
        if (view == null || (num = this.lastActiveElementPosition) == null) {
            return;
        }
        int iIntValue = num.intValue();
        ViewMainSoundPackageBinding viewMainSoundPackageBinding = this.lastActiveElementBinding;
        if (viewMainSoundPackageBinding != null) {
            reselectLastActiveItem(view, iIntValue, viewMainSoundPackageBinding);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(OnDamagePckEvent event) {
        Object next;
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
        Iterable mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        Iterator it = mList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            Integer packageId = ((MainPresetPackage) next).getPackageId();
            if (packageId != null && packageId.intValue() == event.getPckId()) {
                break;
            }
        }
        MainPresetPackage mainPresetPackage = (MainPresetPackage) next;
        if (mainPresetPackage != null) {
            mainPresetPackage.setDamageItem(true);
            notifyItemChanged(getItemPosition(mainPresetPackage));
        }
    }

    private final void reselectLastActiveItem(View view, int position, ViewMainSoundPackageBinding mainBinding) {
        onActivateCommand(view, position, mainBinding);
    }

    public final void onActivateCommand(View view, int position, ViewMainSoundPackageBinding mainBinding) {
        BleManager bleManager;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(mainBinding, "mainBinding");
        this.lastActiveElementView = view;
        this.lastActiveElementPosition = Integer.valueOf(position);
        this.lastActiveElementBinding = mainBinding;
        Timber.INSTANCE.i("onActivateCommand", new Object[0]);
        BleManager bleManager2 = this.mBleManager;
        if (bleManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
            bleManager2 = null;
        }
        if (bleManager2.isBleEnabledAndDeviceConnected()) {
            trySendStatistics(mainBinding);
            int i = position + 1;
            System.out.println(i);
            onActivate(mainBinding, view);
            if (this.mDataLayerContext != null) {
                DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
                Context context = this.mDataLayerContext;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
                    context = null;
                }
                companion.from(context).sendMainPresetPackageIndex(i);
            }
            Log.i("FIND", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
            BleManager bleManager3 = this.mBleManager;
            if (bleManager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
                bleManager = null;
            } else {
                bleManager = bleManager3;
            }
            BleManager.executeActivatePresetCommand$default(bleManager, (short) i, mapMainPresetToInstaledPreset(mainBinding.getPresetPackage()), null, 4, null);
        }
    }

    public final void onActivate(final ViewMainSoundPackageBinding binding, View view) {
        ObservableBoolean isActivate;
        Intrinsics.checkNotNullParameter(binding, "binding");
        Intrinsics.checkNotNullParameter(view, "view");
        MainPresetPackageViewModel model = binding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(true);
        }
        MainPresetPackage presetPackage = binding.getPresetPackage();
        if (presetPackage != null) {
            presetPackage.setActivated(true);
        }
        view.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackagesRvAdapter.onActivate$lambda$14(binding);
            }
        });
        binding.viewCoverRedGradient.setBackgroundResource(R.drawable.bg_shape_sound_card_gradient_accent_vertical);
        binding.textViewSoundName.setAlpha(1.0f);
        binding.imageViewSettings.setAlpha(1.0f);
        binding.imageViewSettings.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivate$lambda$14(ViewMainSoundPackageBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        binding.imageViewCover.animate().scaleX(SCALE_MAX).scaleY(SCALE_MAX).setDuration(ANIMATION_DURATION).start();
    }

    private final void onDeactivateCommand(View view, ViewMainSoundPackageBinding mainBinding) {
        BleManager bleManager;
        Timber.INSTANCE.i("onDeactivateCommand", new Object[0]);
        BleManager bleManager2 = this.mBleManager;
        if (bleManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
            bleManager2 = null;
        }
        if (bleManager2.isBleEnabledAndDeviceConnected()) {
            onDeactivate(mainBinding, view);
            if (this.mDataLayerContext != null) {
                DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
                Context context = this.mDataLayerContext;
                if (context == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
                    context = null;
                }
                companion.from(context).sendMainPresetPackageIndex(0);
            }
            Log.i("FIND", "2");
            BleManager bleManager3 = this.mBleManager;
            if (bleManager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
                bleManager = null;
            } else {
                bleManager = bleManager3;
            }
            BleManager.executeActivatePresetCommand$default(bleManager, (short) 0, new InstalledPreset((short) 0, (short) 0, (short) 0), null, 4, null);
        }
    }

    private final void onDeactivate(final ViewMainSoundPackageBinding binding, View view) {
        ObservableBoolean isActivate;
        Timber.INSTANCE.i("onDeactivate", new Object[0]);
        MainPresetPackage presetPackage = binding.getPresetPackage();
        if (presetPackage != null) {
            presetPackage.setActivated(false);
        }
        MainPresetPackageViewModel model = binding.getModel();
        if (model != null && (isActivate = model.getIsActivate()) != null) {
            isActivate.set(false);
        }
        view.postOnAnimation(new Runnable() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MainSoundPackagesRvAdapter.onDeactivate$lambda$15(binding);
            }
        });
        binding.textViewSoundName.setAlpha(0.5f);
        binding.imageViewSettings.setAlpha(0.5f);
        binding.imageViewSettings.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onDeactivate$lambda$15(ViewMainSoundPackageBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "$binding");
        binding.imageViewCover.animate().scaleX(1.0f).scaleY(1.0f).setDuration(ANIMATION_DURATION).start();
    }

    @Override // com.thor.app.gui.adapters.itemtouch.ItemMoveCallback.OnItemMoveListener
    public void onItemMove(int fromPosition, int toPosition) {
        Timber.INSTANCE.i("onItemMove - from: %s, to: %s", Integer.valueOf(fromPosition), Integer.valueOf(toPosition));
        MainPresetPackage item = getItem(fromPosition);
        this.mList.set(fromPosition, getItem(toPosition));
        this.mList.set(toPosition, item);
        if (fromPosition >= toPosition && fromPosition > toPosition) {
            notifyItemMoved(toPosition, fromPosition);
        } else {
            notifyItemMoved(fromPosition, toPosition);
        }
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
    }

    @Override // com.thor.app.gui.adapters.itemtouch.ItemMoveCallback.OnItemMoveListener
    public void onFinishMoving() {
        Timber.INSTANCE.i("onFinishMoving", new Object[0]);
        updateToCurrentDriveSelect();
        onWritePresets();
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context context = this.mDataLayerContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
            context = null;
        }
        DataLayerManager dataLayerManagerFrom = companion.from(context);
        Collection<MainPresetPackage> mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        dataLayerManagerFrom.sendMainPresetPackages(mList);
    }

    public final void enableDriveSelect(ArrayList<DriveSelectStatus> driveSelectStatuses) {
        Intrinsics.checkNotNullParameter(driveSelectStatuses, "driveSelectStatuses");
        this.driveSelectList = driveSelectStatuses;
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            if (i < driveSelectStatuses.size()) {
                ((MainPresetPackage) this.mList.get(i)).setDriveSelectModeId(Integer.valueOf(driveSelectStatuses.get(i).getId()));
                ((MainPresetPackage) this.mList.get(i)).setDriveSelectMode(driveSelectStatuses.get(i).getName());
                ((MainPresetPackage) this.mList.get(i)).setDriveSelectModeStatus(driveSelectStatuses.get(i).getModeStatus());
                notifyItemChanged(i);
            } else if (((MainPresetPackage) this.mList.get(i)).getDriveSelectMode() != null) {
                String driveSelectMode = ((MainPresetPackage) this.mList.get(i)).getDriveSelectMode();
                Boolean boolValueOf = driveSelectMode != null ? Boolean.valueOf(driveSelectMode.length() > 0) : null;
                Intrinsics.checkNotNull(boolValueOf);
                if (boolValueOf.booleanValue()) {
                    ((MainPresetPackage) this.mList.get(i)).setDriveSelectModeId(0);
                    ((MainPresetPackage) this.mList.get(i)).setDriveSelectMode("");
                    notifyItemChanged(i);
                }
            }
        }
    }

    public final void updateToCurrentDriveSelect() {
        ArrayList<DriveSelectStatus> arrayList = this.driveSelectList;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            if (i < this.driveSelectList.size()) {
                ((MainPresetPackage) this.mList.get(i)).setDriveSelectModeId(Integer.valueOf(this.driveSelectList.get(i).getId()));
                ((MainPresetPackage) this.mList.get(i)).setDriveSelectMode(this.driveSelectList.get(i).getName());
                ((MainPresetPackage) this.mList.get(i)).setDriveSelectModeStatus(this.driveSelectList.get(i).getModeStatus());
                notifyItemChanged(i);
            } else if (((MainPresetPackage) this.mList.get(i)).getDriveSelectMode() != null) {
                String driveSelectMode = ((MainPresetPackage) this.mList.get(i)).getDriveSelectMode();
                Boolean boolValueOf = driveSelectMode != null ? Boolean.valueOf(driveSelectMode.length() > 0) : null;
                Intrinsics.checkNotNull(boolValueOf);
                if (boolValueOf.booleanValue()) {
                    ((MainPresetPackage) this.mList.get(i)).setDriveSelectModeId(0);
                    ((MainPresetPackage) this.mList.get(i)).setDriveSelectMode("");
                    notifyItemChanged(i);
                }
            }
        }
    }

    @Override // com.thor.basemodule.gui.adapters.BaseRecyclerListAdapter, com.thor.basemodule.gui.adapters.RecyclerCollectionAdapter
    public void compareByDiffUtil(Collection<MainPresetPackage> oldData, Collection<MainPresetPackage> newData) {
        super.compareByDiffUtil(oldData, newData);
        sendChangedDataToWearable();
    }

    public final void sendChangedDataToWearable() {
        if (this.mDataLayerContext != null) {
            DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
            Context context = this.mDataLayerContext;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
                context = null;
            }
            DataLayerManager dataLayerManagerFrom = companion.from(context);
            Collection<MainPresetPackage> mList = this.mList;
            Intrinsics.checkNotNullExpressionValue(mList, "mList");
            dataLayerManagerFrom.sendMainPresetPackages(mList);
        }
    }

    public final boolean isDataSame(List<MainPresetPackage> collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        Timber.INSTANCE.i("isDataSame", new Object[0]);
        if (this.mList.isEmpty() || this.mList.size() != collection.size()) {
            Timber.INSTANCE.i("isDataSame mList empty or different size", new Object[0]);
            return false;
        }
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            Timber.INSTANCE.i("Collection item: " + collection.get(i) + ", mList item: " + this.mList.get(i), new Object[0]);
            Timber.Companion companion = Timber.INSTANCE;
            Object[] objArr = new Object[2];
            objArr[0] = Boolean.valueOf(Intrinsics.areEqual(collection.get(i).getPackageId(), ((MainPresetPackage) this.mList.get(i)).getPackageId()));
            Short modeType = collection.get(i).getModeType();
            Integer numValueOf = modeType != null ? Integer.valueOf(modeType.shortValue()) : null;
            Short modeType2 = ((MainPresetPackage) this.mList.get(i)).getModeType();
            objArr[1] = Boolean.valueOf(Intrinsics.areEqual(numValueOf, modeType2 != null ? Integer.valueOf(modeType2.shortValue()) : null));
            companion.i("isDataSame: Package id is %b, modeType is %b", objArr);
            if (Intrinsics.areEqual(collection.get(i).getPackageId(), ((MainPresetPackage) this.mList.get(i)).getPackageId())) {
                Short modeType3 = collection.get(i).getModeType();
                Integer numValueOf2 = modeType3 != null ? Integer.valueOf(modeType3.shortValue()) : null;
                Short modeType4 = ((MainPresetPackage) this.mList.get(i)).getModeType();
                if (Intrinsics.areEqual(numValueOf2, modeType4 != null ? Integer.valueOf(modeType4.shortValue()) : null) && Intrinsics.areEqual(collection.get(i).getDriveSelectMode(), ((MainPresetPackage) this.mList.get(i)).getDriveSelectMode())) {
                }
            }
            return false;
        }
        return true;
    }

    public final void onReceiveClickFromWatches(int index) {
        BleManager bleManager;
        ItemMainSoundPackageBinding binding;
        MainSoundPackageView mainSoundPackageView;
        DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
        Context context = this.mDataLayerContext;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
            context = null;
        }
        companion.from(context).sendMainPresetPackageIndex(index);
        boolean z = false;
        if (1 <= index && index <= getAll().size()) {
            z = true;
        }
        if (z) {
            RecyclerView recyclerView = this.mRecyclerView;
            Intrinsics.checkNotNull(recyclerView);
            RecyclerView recyclerView2 = this.mRecyclerView;
            Intrinsics.checkNotNull(recyclerView2);
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(recyclerView2.getChildAt(index - 1));
            MainSoundPackageViewHolder mainSoundPackageViewHolder = childViewHolder instanceof MainSoundPackageViewHolder ? (MainSoundPackageViewHolder) childViewHolder : null;
            if (mainSoundPackageViewHolder == null || (binding = mainSoundPackageViewHolder.getBinding()) == null || (mainSoundPackageView = binding.mainSoundPackageView) == null) {
                return;
            }
            onClickMainLayout(mainSoundPackageView, mainSoundPackageViewHolder.getAdapterPosition(), mainSoundPackageView.getMainBinding());
            return;
        }
        deselectList();
        Log.i("FIND", ExifInterface.GPS_MEASUREMENT_3D);
        BleManager bleManager2 = this.mBleManager;
        if (bleManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
            bleManager = null;
        } else {
            bleManager = bleManager2;
        }
        BleManager.executeActivatePresetCommand$default(bleManager, (short) 0, mapMainPresetToInstaledPreset(new MainPresetPackage(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null)), null, 4, null);
    }

    public final void onReceiveClickFromWatchesInService(int index, BleManager bleManager, boolean isRunningAppOnPhone) {
        MainPresetPackage mainPresetPackage;
        Intrinsics.checkNotNullParameter(bleManager, "bleManager");
        Context context = null;
        if (bleManager.isBleEnabledAndDeviceConnected()) {
            DataLayerManager.Companion companion = DataLayerManager.INSTANCE;
            Context context2 = this.mDataLayerContext;
            if (context2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
            } else {
                context = context2;
            }
            companion.from(context).sendMainPresetPackageIndex(index);
            MainPresetPackage mainPresetPackage2 = new MainPresetPackage(null, null, null, null, null, false, null, null, null, false, AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES, null);
            try {
                mainPresetPackage = this.savedListOfPressetsFromWear.get(index);
            } catch (Exception e) {
                Timber.INSTANCE.i(e);
                mainPresetPackage = mainPresetPackage2;
            }
            Log.i("FIND", "4");
            BleManager.executeActivatePresetCommand$default(bleManager, (short) index, mapMainPresetToInstaledPreset(mainPresetPackage), null, 4, null);
            return;
        }
        bleManager.disconnect(false);
        Context context3 = this.mDataLayerContext;
        if (context3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mDataLayerContext");
        } else {
            context = context3;
        }
        WearableDataLayerSender.from(context).sendRxDataItem(BooleanWearableDataLayer.CONNECTION_WITH_SCHEMA, BooleanWearableDataLayer.INSTANCE, Boolean.valueOf(!isRunningAppOnPhone)).subscribe();
        bleManager.startScan();
    }

    public final void saveListOfPackagesForFutureUse(ArrayList<MainPresetPackage> tempPresets) {
        Intrinsics.checkNotNullParameter(tempPresets, "tempPresets");
        this.savedListOfPressetsFromWear.clear();
        this.savedListOfPressetsFromWear.addAll(tempPresets);
    }

    public final InstalledPreset mapMainPresetToInstaledPreset(MainPresetPackage preset) {
        Integer versionId;
        Short modeType;
        Integer packageId;
        short sIntValue = 0;
        short sIntValue2 = (preset == null || (packageId = preset.getPackageId()) == null) ? (short) 0 : (short) packageId.intValue();
        short sShortValue = (preset == null || (modeType = preset.getModeType()) == null) ? (short) 0 : modeType.shortValue();
        if (preset != null && (versionId = preset.getVersionId()) != null) {
            sIntValue = (short) versionId.intValue();
        }
        return new InstalledPreset(sIntValue2, sShortValue, sIntValue);
    }

    public final NestedScrollView.OnScrollChangeListener getOnNestedScrollListener() {
        NestedScrollView.OnScrollChangeListener onScrollChangeListener = this.mOnNestedScrollListener;
        if (onScrollChangeListener != null) {
            return onScrollChangeListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mOnNestedScrollListener");
        return null;
    }

    public final void setRecyclerView(RecyclerView rv) {
        this.mRecyclerView = rv;
    }

    public final void clearActiveStates() {
        Iterable<MainPresetPackage> mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        for (MainPresetPackage mainPresetPackage : mList) {
            mainPresetPackage.setActivated(false);
            notifyItemChanged(getItemPosition(mainPresetPackage));
        }
    }

    public final void setActivatedPreset(short presetId) {
        Timber.INSTANCE.i("setActivatedPreset: %s", Short.valueOf(presetId));
        if (presetId <= 0 || isEmpty() || presetId > getItemCount()) {
            return;
        }
        Iterable<MainPresetPackage> mList = this.mList;
        Intrinsics.checkNotNullExpressionValue(mList, "mList");
        for (MainPresetPackage mainPresetPackage : mList) {
            mainPresetPackage.setActivated(false);
            notifyItemChanged(getItemPosition(mainPresetPackage));
        }
        int i = presetId - 1;
        getItem(i).setActivated(true);
        notifyItemChanged(i);
    }

    public final int getActivatedPresetIndex() {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            if (((MainPresetPackage) this.mList.get(i)).isActivated()) {
                return i;
            }
        }
        return -1;
    }

    public final InstalledPreset getActivatedPreset() {
        int size = this.mList.size();
        for (int i = 0; i < size; i++) {
            if (((MainPresetPackage) this.mList.get(i)).isActivated()) {
                Integer packageId = ((MainPresetPackage) this.mList.get(i)).getPackageId();
                short sIntValue = packageId != null ? (short) packageId.intValue() : (short) 0;
                Short modeType = ((MainPresetPackage) this.mList.get(i)).getModeType();
                short sShortValue = modeType != null ? modeType.shortValue() : (short) 0;
                Integer versionId = ((MainPresetPackage) this.mList.get(i)).getVersionId();
                return new InstalledPreset(sIntValue, sShortValue, versionId != null ? (short) versionId.intValue() : (short) 0);
            }
        }
        return new InstalledPreset((short) 0, (short) 0, (short) 0);
    }

    public final void setNextActivatedPreset() {
        int activatedPresetIndex = getActivatedPresetIndex() + 1;
        if (activatedPresetIndex == -1 || activatedPresetIndex >= this.mList.size() - 1) {
            setActivatedPreset((short) 1);
        }
        setActivatedPreset((short) activatedPresetIndex);
    }

    public final void setPreviousActivatedPreset() {
        int activatedPresetIndex = getActivatedPresetIndex() - 1;
        if (activatedPresetIndex == -1 || activatedPresetIndex <= 0) {
            setActivatedPreset((short) this.mList.size());
        }
        setActivatedPreset((short) activatedPresetIndex);
    }

    public final void setDeleteEnabledPreset(short index) {
        MainSoundPackageView mainSoundPackageView;
        MainSoundPackageView mainSoundPackageView2;
        int i = 0;
        Timber.INSTANCE.i("setDeleteEnabledPreset: %d", Short.valueOf(index));
        RecyclerView recyclerView = this.mRecyclerView;
        Intrinsics.checkNotNull(recyclerView);
        int childCount = recyclerView.getChildCount() - 1;
        if (childCount >= 0) {
            while (true) {
                RecyclerView recyclerView2 = this.mRecyclerView;
                Intrinsics.checkNotNull(recyclerView2);
                RecyclerView recyclerView3 = this.mRecyclerView;
                Intrinsics.checkNotNull(recyclerView3);
                RecyclerView.ViewHolder childViewHolder = recyclerView2.getChildViewHolder(recyclerView3.getChildAt(i));
                Intrinsics.checkNotNull(childViewHolder, "null cannot be cast to non-null type com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.MainSoundPackageViewHolder");
                ItemMainSoundPackageBinding binding = ((MainSoundPackageViewHolder) childViewHolder).getBinding();
                if (binding != null && (mainSoundPackageView2 = binding.mainSoundPackageView) != null) {
                    mainSoundPackageView2.onSwipeToRightAnimation();
                }
                if (i == childCount) {
                    break;
                } else {
                    i++;
                }
            }
        }
        RecyclerView recyclerView4 = this.mRecyclerView;
        Intrinsics.checkNotNull(recyclerView4);
        RecyclerView recyclerView5 = this.mRecyclerView;
        Intrinsics.checkNotNull(recyclerView5);
        RecyclerView.ViewHolder childViewHolder2 = recyclerView4.getChildViewHolder(recyclerView5.getChildAt(index - 1));
        Intrinsics.checkNotNull(childViewHolder2, "null cannot be cast to non-null type com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.MainSoundPackageViewHolder");
        ItemMainSoundPackageBinding binding2 = ((MainSoundPackageViewHolder) childViewHolder2).getBinding();
        if (binding2 == null || (mainSoundPackageView = binding2.mainSoundPackageView) == null) {
            return;
        }
        mainSoundPackageView.onSwipeToLeftAnimation();
    }

    public final void removePreset(MainPresetPackage preset) {
        Timber.INSTANCE.i("removePreset: %s", preset);
        if (preset != null && preset.isActivated()) {
            preset.setActivated(false);
            notifyItemChanged(getItemPosition(preset));
        }
        removeItem((MainSoundPackagesRvAdapter) preset);
        notifyDataSetChanged();
        updateSoundPackageInThread();
        EventBus.getDefault().post(new CheckPlaceholderHintEvent());
    }

    public final void updateSoundPackageInThread() {
        onWritePresets();
    }

    private final void initNestedScrollListener() {
        this.mOnNestedScrollListener = new NestedScrollView.OnScrollChangeListener() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda0
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
                MainSoundPackagesRvAdapter.initNestedScrollListener$lambda$20(this.f$0, nestedScrollView, i, i2, i3, i4);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initNestedScrollListener$lambda$20(MainSoundPackagesRvAdapter this$0, NestedScrollView p0, int i, int i2, int i3, int i4) {
        View childAt;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(p0, "p0");
        int itemCount = this$0.getItemCount();
        for (int i5 = 0; i5 < itemCount; i5++) {
            RecyclerView recyclerView = this$0.mRecyclerView;
            if (recyclerView != null && (childAt = recyclerView.getChildAt(i5)) != null) {
                MainSoundPackageView mainSoundPackageView = (MainSoundPackageView) childAt;
                RecyclerView recyclerView2 = this$0.mRecyclerView;
                Integer numValueOf = recyclerView2 != null ? Integer.valueOf(recyclerView2.getHeight()) : null;
                Intrinsics.checkNotNull(numValueOf);
                mainSoundPackageView.onNestedScrollChanged(i2, i4, numValueOf.intValue());
            }
        }
    }

    @Subscribe
    public final void onMessageEvent(OnClickMainPresetFromWearEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Timber.INSTANCE.i("onMessageEvent: %s", event);
    }

    private final void trySendStatistics(ViewMainSoundPackageBinding mainBinding) {
        Observable<BaseResponse> observableSubscribeOn;
        Observable<BaseResponse> observableObserveOn;
        UsersManager usersManager = this.mUsersManager;
        if (usersManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mUsersManager");
            usersManager = null;
        }
        MainPresetPackage presetPackage = mainBinding.getPresetPackage();
        Integer packageId = presetPackage != null ? presetPackage.getPackageId() : null;
        Intrinsics.checkNotNull(packageId);
        int iIntValue = packageId.intValue();
        MainPresetPackage presetPackage2 = mainBinding.getPresetPackage();
        Short modeType = presetPackage2 != null ? presetPackage2.getModeType() : null;
        Intrinsics.checkNotNull(modeType);
        Observable<BaseResponse> observableSendStatisticsAboutSoundPackage = usersManager.sendStatisticsAboutSoundPackage(iIntValue, modeType.shortValue());
        if (observableSendStatisticsAboutSoundPackage == null || (observableSubscribeOn = observableSendStatisticsAboutSoundPackage.subscribeOn(Schedulers.io())) == null || (observableObserveOn = observableSubscribeOn.observeOn(AndroidSchedulers.mainThread())) == null) {
            return;
        }
        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.trySendStatistics.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseResponse baseResponse) {
                invoke2(baseResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseResponse baseResponse) {
                if (baseResponse.isSuccessful()) {
                    return;
                }
                if (baseResponse.getCode() == 888) {
                    RecyclerView recyclerView = MainSoundPackagesRvAdapter.this.mRecyclerView;
                    Activity parentActivity = ContextKt.getParentActivity(recyclerView != null ? recyclerView.getContext() : null);
                    if (parentActivity != null) {
                        DeviceLockingUtilsKt.onDeviceLocked(parentActivity);
                        return;
                    }
                    return;
                }
                DialogManager dialogManager = DialogManager.INSTANCE;
                RecyclerView recyclerView2 = MainSoundPackagesRvAdapter.this.mRecyclerView;
                AlertDialog alertDialogCreateErrorAlertDialog = dialogManager.createErrorAlertDialog(recyclerView2 != null ? recyclerView2.getContext() : null, baseResponse.getError(), baseResponse.getCode());
                if (alertDialogCreateErrorAlertDialog != null) {
                    alertDialogCreateErrorAlertDialog.show();
                }
            }
        };
        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundPackagesRvAdapter.trySendStatistics$lambda$21(function1, obj);
            }
        };
        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.trySendStatistics.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                if (Intrinsics.areEqual(th.getMessage(), "HTTP 400")) {
                    DialogManager dialogManager = DialogManager.INSTANCE;
                    RecyclerView recyclerView = MainSoundPackagesRvAdapter.this.mRecyclerView;
                    AlertDialog alertDialogCreateErrorAlertDialog = dialogManager.createErrorAlertDialog(recyclerView != null ? recyclerView.getContext() : null, th.getMessage(), 0);
                    if (alertDialogCreateErrorAlertDialog != null) {
                        alertDialogCreateErrorAlertDialog.show();
                    }
                } else if (Intrinsics.areEqual(th.getMessage(), "HTTP 500")) {
                    DialogManager dialogManager2 = DialogManager.INSTANCE;
                    RecyclerView recyclerView2 = MainSoundPackagesRvAdapter.this.mRecyclerView;
                    AlertDialog alertDialogCreateErrorAlertDialog2 = dialogManager2.createErrorAlertDialog(recyclerView2 != null ? recyclerView2.getContext() : null, th.getMessage(), 0);
                    if (alertDialogCreateErrorAlertDialog2 != null) {
                        alertDialogCreateErrorAlertDialog2.show();
                    }
                }
                Timber.INSTANCE.e(th);
            }
        };
        observableObserveOn.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundPackagesRvAdapter.trySendStatistics$lambda$22(function12, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trySendStatistics$lambda$21(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void trySendStatistics$lambda$22(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void onWritePresets() {
        Context context;
        Timber.INSTANCE.i("onWritePresets", new Object[0]);
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || (context = recyclerView.getContext()) == null) {
            return;
        }
        BleManager bleManagerFrom = BleManager.INSTANCE.from(context);
        Collection<MainPresetPackage> all = getAll();
        Intrinsics.checkNotNullExpressionValue(all, "all");
        int i = 0;
        boolean z = true;
        for (Object obj : all) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            MainPresetPackage mainPresetPackage = (MainPresetPackage) obj;
            if (mainPresetPackage.isActivated()) {
                Timber.INSTANCE.i("Activated preset index: %s", Integer.valueOf(i2));
                bleManagerFrom.setActivatedPresetIndex((short) i2);
                Integer packageId = mainPresetPackage.getPackageId();
                short sIntValue = packageId != null ? (short) packageId.intValue() : (short) 0;
                Short modeType = mainPresetPackage.getModeType();
                short sShortValue = modeType != null ? modeType.shortValue() : (short) 0;
                Integer versionId = mainPresetPackage.getVersionId();
                bleManagerFrom.setActivatedPreset(new InstalledPreset(sIntValue, sShortValue, versionId != null ? (short) versionId.intValue() : (short) 0));
                z = false;
            }
            i = i2;
        }
        Collection<MainPresetPackage> all2 = getAll();
        Intrinsics.checkNotNull(all2, "null cannot be cast to non-null type java.util.ArrayList<com.thor.businessmodule.model.MainPresetPackage>{ kotlin.collections.TypeAliasesKt.ArrayList<com.thor.businessmodule.model.MainPresetPackage> }");
        bleManagerFrom.executeWriteInstalledPresetsCommand((ArrayList) all2);
        if (z) {
            Log.i("FIND", "6");
            bleManagerFrom.setActivatedPresetIndex((short) 0);
            bleManagerFrom.setActivatedPreset(new InstalledPreset((short) 0, (short) 0, (short) 0));
            BleManager.executeActivatePresetCommand$default(bleManagerFrom, (short) 0, new InstalledPreset((short) 0, (short) 0, (short) 0), null, 4, null);
            return;
        }
        BleManager.executeActivatePresetCommand$default(bleManagerFrom, bleManagerFrom.getMActivatedPresetIndex(), bleManagerFrom.getMActivatedPreset(), null, 4, null);
    }

    private final void onChangeDriveMode(final ViewMainSoundPackageBinding mainBinding) {
        Timber.INSTANCE.i("onChangeDriveMode", new Object[0]);
        BleManager bleManager = this.mBleManager;
        BleManager bleManager2 = null;
        if (bleManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
            bleManager = null;
        }
        BleManager bleManager3 = this.mBleManager;
        if (bleManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
        } else {
            bleManager2 = bleManager3;
        }
        Observable<ByteArrayOutputStream> observableExecuteReadDriveModes = bleManager2.executeReadDriveModes();
        final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter.onChangeDriveMode.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                invoke2(byteArrayOutputStream);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                MainSoundPackagesRvAdapter.this.handleDriveModes(new ReadDriveModesBleResponse(byteArrayOutputStream.toByteArray()).getDriveModes(), mainBinding);
            }
        };
        Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundPackagesRvAdapter.onChangeDriveMode$lambda$25(function1, obj);
            }
        };
        final C02512 c02512 = new C02512(Timber.INSTANCE);
        Disposable disposableSubscribe = observableExecuteReadDriveModes.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainSoundPackagesRvAdapter.onChangeDriveMode$lambda$26(c02512, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(disposableSubscribe, "private fun onChangeDriv…imber::e)\n        )\n    }");
        bleManager.addCommandDisposable(disposableSubscribe);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onChangeDriveMode$lambda$25(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: MainSoundPackagesRvAdapter.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$onChangeDriveMode$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02512 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C02512(Object obj) {
            super(1, obj, Timber.Companion.class, "e", "e(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable th) {
            ((Timber.Companion) this.receiver).e(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onChangeDriveMode$lambda$26(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleDriveModes(ArrayList<DriveMode> list, final ViewMainSoundPackageBinding mainBinding) {
        Integer driveSelectModeId;
        if (list != null) {
            Iterator<DriveMode> it = list.iterator();
            while (it.hasNext()) {
                DriveMode next = it.next();
                MainPresetPackage presetPackage = mainBinding.getPresetPackage();
                if (presetPackage != null && (driveSelectModeId = presetPackage.getDriveSelectModeId()) != null && driveSelectModeId.intValue() == next.getModeId()) {
                    next.setModeValue(getNextStatus(mainBinding));
                }
            }
            WriteDriveModesBleRequest writeDriveModesBleRequest = new WriteDriveModesBleRequest(list);
            BleManager bleManager = this.mBleManager;
            if (bleManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mBleManager");
                bleManager = null;
            }
            Observable<ByteArrayOutputStream> observableExecuteWriteDriveModes = bleManager.executeWriteDriveModes(writeDriveModesBleRequest);
            final Function1<ByteArrayOutputStream, Unit> function1 = new Function1<ByteArrayOutputStream, Unit>() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$handleDriveModes$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ByteArrayOutputStream byteArrayOutputStream) {
                    invoke2(byteArrayOutputStream);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ByteArrayOutputStream byteArrayOutputStream) {
                    if (new WriteDriveModesBleResponse(byteArrayOutputStream.toByteArray()).isSuccessful()) {
                        this.this$0.changeDriveMode(mainBinding);
                    }
                }
            };
            Consumer<? super ByteArrayOutputStream> consumer = new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainSoundPackagesRvAdapter.handleDriveModes$lambda$30$lambda$28(function1, obj);
                }
            };
            final MainSoundPackagesRvAdapter$handleDriveModes$1$3 mainSoundPackagesRvAdapter$handleDriveModes$1$3 = new MainSoundPackagesRvAdapter$handleDriveModes$1$3(Timber.INSTANCE);
            observableExecuteWriteDriveModes.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.adapters.main.MainSoundPackagesRvAdapter$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MainSoundPackagesRvAdapter.handleDriveModes$lambda$30$lambda$29(mainSoundPackagesRvAdapter$handleDriveModes$1$3, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDriveModes$lambda$30$lambda$28(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDriveModes$lambda$30$lambda$29(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeDriveMode(ViewMainSoundPackageBinding mainBinding) {
        MainPresetPackage presetPackage = mainBinding.getPresetPackage();
        if (presetPackage != null) {
            presetPackage.setDriveSelectModeStatus(getNextStatus(mainBinding).getValue());
        }
        mainBinding.invalidateAll();
    }

    private final DriveMode.DRIVE_MODE getNextStatus(ViewMainSoundPackageBinding mainBinding) {
        MainPresetPackage presetPackage = mainBinding.getPresetPackage();
        String driveSelectModeStatus = presetPackage != null ? presetPackage.getDriveSelectModeStatus() : null;
        if (Intrinsics.areEqual(driveSelectModeStatus, DriveMode.DRIVE_MODE.PLAY_CURRENT_PRESET.getValue())) {
            return DriveMode.DRIVE_MODE.MUTE;
        }
        if (Intrinsics.areEqual(driveSelectModeStatus, DriveMode.DRIVE_MODE.PLAY_PRESET_AS_SELECTED_MODE.getValue())) {
            return DriveMode.DRIVE_MODE.PLAY_CURRENT_PRESET;
        }
        return DriveMode.DRIVE_MODE.PLAY_PRESET_AS_SELECTED_MODE;
    }

    public final void enableSyncData(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mDataLayerContext = context;
    }

    /* compiled from: MainSoundPackagesRvAdapter.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter$MainSoundPackageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/thor/app/gui/adapters/main/MainSoundPackagesRvAdapter;Landroid/view/View;)V", "binding", "Lcom/carsystems/thor/app/databinding/ItemMainSoundPackageBinding;", "getBinding", "()Lcom/carsystems/thor/app/databinding/ItemMainSoundPackageBinding;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class MainSoundPackageViewHolder extends RecyclerView.ViewHolder {
        private final ItemMainSoundPackageBinding binding;
        final /* synthetic */ MainSoundPackagesRvAdapter this$0;

        public final ItemMainSoundPackageBinding getBinding() {
            return this.binding;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MainSoundPackageViewHolder(MainSoundPackagesRvAdapter mainSoundPackagesRvAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = mainSoundPackagesRvAdapter;
            this.binding = (ItemMainSoundPackageBinding) DataBindingUtil.bind(itemView);
        }
    }
}
