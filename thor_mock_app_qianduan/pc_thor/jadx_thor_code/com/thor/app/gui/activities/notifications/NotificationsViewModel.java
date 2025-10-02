package com.thor.app.gui.activities.notifications;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.firebase.messaging.Constants;
import com.thor.app.gui.activities.notifications.NotificationsViewModel;
import com.thor.app.managers.UsersManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.entity.InstalledSoundPackageEntity;
import com.thor.app.settings.Settings;
import com.thor.app.utils.NotificationCountHelper;
import com.thor.networkmodule.model.notifications.NotificationInfo;
import com.thor.networkmodule.model.notifications.NotificationType;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.NotificationsResponse;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import me.leolin.shortcutbadger.ShortcutBadger;
import timber.log.Timber;

/* compiled from: NotificationsViewModel.kt */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 D2\u00020\u0001:\u0001DB'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u00101\u001a\u0002022\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u000202J\u0006\u00106\u001a\u000202J\u0006\u00107\u001a\u000202J\u0012\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u001fJ\u0006\u00109\u001a\u000202J\b\u0010:\u001a\u000202H\u0002J\u0010\u0010;\u001a\u0002022\u0006\u0010<\u001a\u00020=H\u0002J\u0010\u0010>\u001a\u0002022\u0006\u0010?\u001a\u00020@H\u0002J\u0006\u0010A\u001a\u000202J\b\u0010B\u001a\u000202H\u0002J\b\u0010C\u001a\u000202H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\u001f¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010$R \u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0019\"\u0004\b(\u0010\u001bR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001f¢\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u001a\u0010+\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lcom/thor/app/gui/activities/notifications/NotificationsViewModel;", "Landroidx/lifecycle/ViewModel;", "usersManager", "Lcom/thor/app/managers/UsersManager;", "settings", "Lcom/thor/app/settings/Settings;", "database", "Lcom/thor/app/room/ThorDatabase;", "notificationCountHelper", "Lcom/thor/app/utils/NotificationCountHelper;", "(Lcom/thor/app/managers/UsersManager;Lcom/thor/app/settings/Settings;Lcom/thor/app/room/ThorDatabase;Lcom/thor/app/utils/NotificationCountHelper;)V", "_apiErrorUiState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/thor/app/gui/activities/notifications/NotificationsViewModel$Companion$ApiErrorUiState;", "_notificationsList", "", "Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "_selectedNotification", "get_selectedNotification", "()Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "set_selectedNotification", "(Lcom/thor/networkmodule/model/notifications/NotificationInfo;)V", "_shopSoundPackages", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "get_shopSoundPackages", "()Ljava/util/List;", "set_shopSoundPackages", "(Ljava/util/List;)V", "_uiState", "Lcom/thor/app/gui/activities/notifications/NotificationUiState;", "apiErrorUiState", "Landroidx/lifecycle/LiveData;", "getApiErrorUiState", "()Landroidx/lifecycle/LiveData;", "isBluetoothConnected", "Landroidx/databinding/ObservableBoolean;", "()Landroidx/databinding/ObservableBoolean;", "listToUpdate", "", "getListToUpdate", "setListToUpdate", "uiState", "getUiState", "updateFirmwareText", "", "getUpdateFirmwareText", "()Ljava/lang/String;", "setUpdateFirmwareText", "(Ljava/lang/String;)V", "clearNotification", "", TtmlNode.ATTR_ID, "", "deleteEvent", "disableFirmwareNotification", "fetchAllNotifications", "getNotificationsListLiveData", "getSoundPackagesInfo", "getTempDataToNotificationList", "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleResponse", "response", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "saveListToUpdate", "saveUnreadMessageCount", "updateShopSoundPackageStatus", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class NotificationsViewModel extends ViewModel {
    private final MutableLiveData<Companion.ApiErrorUiState> _apiErrorUiState;
    private final MutableLiveData<List<NotificationInfo>> _notificationsList;
    private NotificationInfo _selectedNotification;
    private List<ShopSoundPackage> _shopSoundPackages;
    private final MutableLiveData<NotificationUiState> _uiState;
    private final LiveData<Companion.ApiErrorUiState> apiErrorUiState;
    private final ThorDatabase database;
    private final ObservableBoolean isBluetoothConnected;
    private List<NotificationInfo> listToUpdate;
    private final NotificationCountHelper notificationCountHelper;
    private final Settings settings;
    private final LiveData<NotificationUiState> uiState;
    private String updateFirmwareText;
    private final UsersManager usersManager;

    /* JADX WARN: Multi-variable type inference failed */
    @Inject
    public NotificationsViewModel(UsersManager usersManager, Settings settings, ThorDatabase database, NotificationCountHelper notificationCountHelper) {
        Intrinsics.checkNotNullParameter(usersManager, "usersManager");
        Intrinsics.checkNotNullParameter(settings, "settings");
        Intrinsics.checkNotNullParameter(database, "database");
        Intrinsics.checkNotNullParameter(notificationCountHelper, "notificationCountHelper");
        this.usersManager = usersManager;
        this.settings = settings;
        this.database = database;
        this.notificationCountHelper = notificationCountHelper;
        MutableLiveData<NotificationUiState> mutableLiveData = new MutableLiveData<>();
        this._uiState = mutableLiveData;
        this.uiState = mutableLiveData;
        MutableLiveData<Companion.ApiErrorUiState> mutableLiveData2 = new MutableLiveData<>(new Companion.ApiErrorUiState(false, null, 2, null == true ? 1 : 0));
        this._apiErrorUiState = mutableLiveData2;
        this.apiErrorUiState = mutableLiveData2;
        this.updateFirmwareText = "";
        this.isBluetoothConnected = new ObservableBoolean(false);
        this._notificationsList = new MutableLiveData<>();
        this.listToUpdate = new ArrayList();
    }

    public final LiveData<NotificationUiState> getUiState() {
        return this.uiState;
    }

    public final LiveData<Companion.ApiErrorUiState> getApiErrorUiState() {
        return this.apiErrorUiState;
    }

    public final String getUpdateFirmwareText() {
        return this.updateFirmwareText;
    }

    public final void setUpdateFirmwareText(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.updateFirmwareText = str;
    }

    /* renamed from: isBluetoothConnected, reason: from getter */
    public final ObservableBoolean getIsBluetoothConnected() {
        return this.isBluetoothConnected;
    }

    public final NotificationInfo get_selectedNotification() {
        return this._selectedNotification;
    }

    public final void set_selectedNotification(NotificationInfo notificationInfo) {
        this._selectedNotification = notificationInfo;
    }

    public final List<ShopSoundPackage> get_shopSoundPackages() {
        return this._shopSoundPackages;
    }

    public final void set_shopSoundPackages(List<ShopSoundPackage> list) {
        this._shopSoundPackages = list;
    }

    public final List<NotificationInfo> getListToUpdate() {
        return this.listToUpdate;
    }

    public final void setListToUpdate(List<NotificationInfo> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.listToUpdate = list;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void saveListToUpdate() {
        /*
            r7 = this;
            androidx.lifecycle.MutableLiveData<java.util.List<com.thor.networkmodule.model.notifications.NotificationInfo>> r0 = r7._notificationsList
            java.lang.Object r0 = r0.getValue()
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L11
            java.util.Collection r0 = (java.util.Collection) r0
            java.util.List r0 = kotlin.collections.CollectionsKt.toMutableList(r0)
            goto L12
        L11:
            r0 = 0
        L12:
            r1 = 1
            if (r0 == 0) goto L53
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Collection r2 = (java.util.Collection) r2
            java.util.Iterator r0 = r0.iterator()
        L22:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L49
            java.lang.Object r3 = r0.next()
            r4 = r3
            com.thor.networkmodule.model.notifications.NotificationInfo r4 = (com.thor.networkmodule.model.notifications.NotificationInfo) r4
            com.thor.networkmodule.model.notifications.NotificationType r5 = r4.getNotificationType()
            com.thor.networkmodule.model.notifications.NotificationType r6 = com.thor.networkmodule.model.notifications.NotificationType.TYPE_FIRMWARE
            if (r5 == r6) goto L42
            com.thor.networkmodule.model.notifications.NotificationType r4 = r4.getNotificationType()
            com.thor.networkmodule.model.notifications.NotificationType r5 = com.thor.networkmodule.model.notifications.NotificationType.TYPE_SOUND_PACKAGE
            if (r4 != r5) goto L40
            goto L42
        L40:
            r4 = 0
            goto L43
        L42:
            r4 = r1
        L43:
            if (r4 == 0) goto L22
            r2.add(r3)
            goto L22
        L49:
            java.util.List r2 = (java.util.List) r2
            java.util.Collection r2 = (java.util.Collection) r2
            java.util.List r0 = kotlin.collections.CollectionsKt.toMutableList(r2)
            if (r0 != 0) goto L5a
        L53:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r0 = (java.util.List) r0
        L5a:
            r7.listToUpdate = r0
            java.util.Collection r0 = (java.util.Collection) r0
            boolean r0 = r0.isEmpty()
            r0 = r0 ^ r1
            if (r0 == 0) goto L6c
            androidx.lifecycle.MutableLiveData<com.thor.app.gui.activities.notifications.NotificationUiState> r0 = r7._uiState
            com.thor.app.gui.activities.notifications.NotificationUpdateAll r1 = com.thor.app.gui.activities.notifications.NotificationUpdateAll.INSTANCE
            r0.postValue(r1)
        L6c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.thor.app.gui.activities.notifications.NotificationsViewModel.saveListToUpdate():void");
    }

    public final LiveData<List<NotificationInfo>> getNotificationsListLiveData() {
        return this._notificationsList;
    }

    public final void fetchAllNotifications() {
        getTempDataToNotificationList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveUnreadMessageCount() {
        List<NotificationInfo> value = this._notificationsList.getValue();
        if (value != null) {
            int size = value.size();
            this.notificationCountHelper.setUnreadNotificationCount(size);
            ShortcutBadger.applyCount(this.usersManager.getContext(), size);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearNotification$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void clearNotification(int id) {
        Observable<BaseResponse> observableDeleteUserNotifications = this.usersManager.deleteUserNotifications(id);
        if (observableDeleteUserNotifications != null) {
            final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel.clearNotification.1
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
                        int unreadNotificationCount = NotificationsViewModel.this.notificationCountHelper.getUnreadNotificationCount() - 1;
                        NotificationsViewModel.this.notificationCountHelper.setUnreadNotificationCount(unreadNotificationCount);
                        ShortcutBadger.applyCount(NotificationsViewModel.this.usersManager.getContext(), unreadNotificationCount);
                        return;
                    }
                    NotificationsViewModel.this._apiErrorUiState.postValue(new Companion.ApiErrorUiState(true, baseResponse.getError()));
                }
            };
            Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsViewModel.clearNotification$lambda$2(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel.clearNotification.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    NotificationsViewModel notificationsViewModel = NotificationsViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    notificationsViewModel.handleError(it);
                }
            };
            Disposable disposableSubscribe = observableDeleteUserNotifications.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsViewModel.clearNotification$lambda$3(function12, obj);
                }
            });
            if (disposableSubscribe != null) {
                this.usersManager.addDisposable(disposableSubscribe);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearNotification$lambda$3(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void deleteEvent() {
        List<NotificationInfo> value = this._notificationsList.getValue();
        if (value != null) {
            for (NotificationInfo notificationInfo : value) {
                if (notificationInfo.getNotificationId() != 0) {
                    Observable<BaseResponse> observableDeleteUserNotifications = this.usersManager.deleteUserNotifications(notificationInfo.getNotificationId());
                    if (observableDeleteUserNotifications != null) {
                        final Function1<BaseResponse, Unit> function1 = new Function1<BaseResponse, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$deleteEvent$1$1
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
                                if (baseResponse.getStatus()) {
                                    return;
                                }
                                this.this$0._apiErrorUiState.postValue(new NotificationsViewModel.Companion.ApiErrorUiState(true, baseResponse.getError()));
                            }
                        };
                        Consumer<? super BaseResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda0
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                NotificationsViewModel.deleteEvent$lambda$8$lambda$5(function1, obj);
                            }
                        };
                        final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$deleteEvent$1$2
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                                invoke2(th);
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(Throwable it) {
                                NotificationsViewModel notificationsViewModel = this.this$0;
                                Intrinsics.checkNotNullExpressionValue(it, "it");
                                notificationsViewModel.handleError(it);
                            }
                        };
                        Disposable disposableSubscribe = observableDeleteUserNotifications.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda1
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                NotificationsViewModel.deleteEvent$lambda$8$lambda$6(function12, obj);
                            }
                        });
                        if (disposableSubscribe != null) {
                            this.usersManager.addDisposable(disposableSubscribe);
                        }
                    }
                } else {
                    disableFirmwareNotification();
                }
            }
        }
        getTempDataToNotificationList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteEvent$lambda$8$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteEvent$lambda$8$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.util.List] */
    private final void getTempDataToNotificationList() {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new ArrayList();
        if (Settings.INSTANCE.getIsNeedToUpdateFirmware()) {
            ((List) objectRef.element).add(new NotificationInfo(0, this.updateFirmwareText, "Доступна новая версия прошивки для вашего блока", "", "firmware", false, null, NotificationType.TYPE_FIRMWARE));
        }
        this._notificationsList.postValue(objectRef.element);
        Observable<NotificationsResponse> observableFetchUserNotifications = this.usersManager.fetchUserNotifications();
        if (observableFetchUserNotifications != null) {
            final Function1<NotificationsResponse, Unit> function1 = new Function1<NotificationsResponse, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel.getTempDataToNotificationList.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(NotificationsResponse notificationsResponse) {
                    invoke2(notificationsResponse);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(NotificationsResponse notificationsResponse) {
                    if (notificationsResponse == null || !notificationsResponse.isSuccessful()) {
                        if (notificationsResponse != null) {
                            this._apiErrorUiState.postValue(new Companion.ApiErrorUiState(true, notificationsResponse.getError()));
                            return;
                        }
                        return;
                    }
                    List<NotificationInfo> events = notificationsResponse.getEvents();
                    if (events != null) {
                        objectRef.element.addAll(events);
                    }
                    for (NotificationInfo notificationInfo : objectRef.element) {
                        notificationInfo.setNotificationType(NotificationType.INSTANCE.fromString(notificationInfo.getFormat()));
                    }
                    this._notificationsList.postValue(objectRef.element);
                    if (notificationsResponse.isSuccessful()) {
                        this.saveUnreadMessageCount();
                    }
                }
            };
            Consumer<? super NotificationsResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsViewModel.getTempDataToNotificationList$lambda$9(function1, obj);
                }
            };
            final Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel.getTempDataToNotificationList.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Throwable it) {
                    NotificationsViewModel notificationsViewModel = NotificationsViewModel.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    notificationsViewModel.handleError(it);
                }
            };
            Disposable disposableSubscribe = observableFetchUserNotifications.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsViewModel.getTempDataToNotificationList$lambda$10(function12, obj);
                }
            });
            if (disposableSubscribe != null) {
                this.usersManager.addDisposable(disposableSubscribe);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getTempDataToNotificationList$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getTempDataToNotificationList$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: NotificationsViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.notifications.NotificationsViewModel$getSoundPackagesInfo$1, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02001 extends FunctionReferenceImpl implements Function1<ShopSoundPackagesResponse, Unit> {
        C02001(Object obj) {
            super(1, obj, NotificationsViewModel.class, "handleResponse", "handleResponse(Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
            invoke2(shopSoundPackagesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ShopSoundPackagesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((NotificationsViewModel) this.receiver).handleResponse(p0);
        }
    }

    /* compiled from: NotificationsViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.notifications.NotificationsViewModel$getSoundPackagesInfo$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02012 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C02012(Object obj) {
            super(1, obj, NotificationsViewModel.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((NotificationsViewModel) this.receiver).handleError(p0);
        }
    }

    public final void getSoundPackagesInfo() {
        Observable<ShopSoundPackagesResponse> observableFetchShopSoundPackages = this.usersManager.fetchShopSoundPackages();
        if (observableFetchShopSoundPackages != null) {
            final C02001 c02001 = new C02001(this);
            Consumer<? super ShopSoundPackagesResponse> consumer = new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsViewModel.getSoundPackagesInfo$lambda$12(c02001, obj);
                }
            };
            final C02012 c02012 = new C02012(this);
            observableFetchShopSoundPackages.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    NotificationsViewModel.getSoundPackagesInfo$lambda$13(c02012, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getSoundPackagesInfo$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getSoundPackagesInfo$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleResponse(ShopSoundPackagesResponse response) {
        Timber.INSTANCE.i("handleResponse: %s", response);
        if (response.isSuccessful()) {
            if (response.getStatus()) {
                List<ShopSoundPackage> soundItems = response.getSoundItems();
                if (soundItems == null) {
                    soundItems = CollectionsKt.emptyList();
                }
                this._shopSoundPackages = CollectionsKt.toMutableList((Collection) soundItems);
                updateShopSoundPackageStatus();
                return;
            }
            this._apiErrorUiState.postValue(new Companion.ApiErrorUiState(true, response.getError()));
        }
    }

    private final void updateShopSoundPackageStatus() {
        Observable<List<InstalledSoundPackageEntity>> observable = this.database.installedSoundPackageDao().getEntities().toObservable();
        final Function1<List<? extends InstalledSoundPackageEntity>, Unit> function1 = new Function1<List<? extends InstalledSoundPackageEntity>, Unit>() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel.updateShopSoundPackageStatus.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends InstalledSoundPackageEntity> list) {
                invoke2((List<InstalledSoundPackageEntity>) list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<InstalledSoundPackageEntity> installedSoundPackages) {
                List<ShopSoundPackage> list;
                Intrinsics.checkNotNullExpressionValue(installedSoundPackages, "installedSoundPackages");
                if (!(!installedSoundPackages.isEmpty()) || (list = NotificationsViewModel.this.get_shopSoundPackages()) == null) {
                    return;
                }
                for (ShopSoundPackage shopSoundPackage : list) {
                    if (shopSoundPackage.isPurchased() || Settings.INSTANCE.isSubscriptionActive()) {
                        Iterator<InstalledSoundPackageEntity> it = installedSoundPackages.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                InstalledSoundPackageEntity next = it.next();
                                if (shopSoundPackage.getId() == next.getPackageId()) {
                                    shopSoundPackage.setLoadedOnBoard(true);
                                    if (next.getVersionId() < shopSoundPackage.getPkgVer()) {
                                        shopSoundPackage.setNeedUpdate(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        Consumer<? super List<InstalledSoundPackageEntity>> consumer = new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                NotificationsViewModel.updateShopSoundPackageStatus$lambda$14(function1, obj);
            }
        };
        final C02052 c02052 = new C02052(this);
        observable.subscribe(consumer, new Consumer() { // from class: com.thor.app.gui.activities.notifications.NotificationsViewModel$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                NotificationsViewModel.updateShopSoundPackageStatus$lambda$15(c02052, obj);
            }
        });
        this._uiState.postValue(NotificationGetInfoSuccess.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateShopSoundPackageStatus$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: NotificationsViewModel.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.gui.activities.notifications.NotificationsViewModel$updateShopSoundPackageStatus$2, reason: invalid class name and case insensitive filesystem */
    /* synthetic */ class C02052 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        C02052(Object obj) {
            super(1, obj, NotificationsViewModel.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((NotificationsViewModel) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateShopSoundPackageStatus$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400") || Intrinsics.areEqual(error.getMessage(), "HTTP 500")) {
            this._apiErrorUiState.postValue(new Companion.ApiErrorUiState(true, error.getMessage()));
        }
        Timber.INSTANCE.e(error);
    }

    public final void disableFirmwareNotification() {
        this.settings.saveIsNeedToUpdateFirmware(false);
        this.notificationCountHelper.setNotificationUpdateFirmwareSent(false);
    }
}
