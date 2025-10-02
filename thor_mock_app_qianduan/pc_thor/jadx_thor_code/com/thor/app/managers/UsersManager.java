package com.thor.app.managers;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import com.carsystems.thor.app.BuildConfig;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.Scopes;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.crashlytics.internal.common.AbstractSpiCall;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.thor.app.ThorApplication;
import com.thor.app.bus.events.shop.main.ShopSoundPackagesUpdateEvent;
import com.thor.app.gui.dialog.DialogManager;
import com.thor.app.room.ThorDatabase;
import com.thor.app.room.dao.ShopSoundPackageDao;
import com.thor.app.room.entity.ShopSoundPackageEntity;
import com.thor.app.rx.DefaultSchedulerTransformer;
import com.thor.app.settings.CarInfoPreference;
import com.thor.app.settings.Settings;
import com.thor.app.utils.extensions.StringKt;
import com.thor.app.utils.logs.loggers.FileLogger;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.settings.Variables;
import com.thor.networkmodule.model.ChangeCarInfo;
import com.thor.networkmodule.model.SignUpInfo;
import com.thor.networkmodule.model.StatisticsPack;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.firmware.FirmwareProfileList;
import com.thor.networkmodule.model.responses.BaseResponse;
import com.thor.networkmodule.model.responses.CanConfigurationsResponse;
import com.thor.networkmodule.model.responses.CarInfoAuthorizeResponse;
import com.thor.networkmodule.model.responses.NotificationsResponse;
import com.thor.networkmodule.model.responses.PasswordValidationResponse;
import com.thor.networkmodule.model.responses.ShopSoundPackagesResponse;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.SignUpResponse;
import com.thor.networkmodule.model.responses.car.CarFuelTypeResponse;
import com.thor.networkmodule.model.responses.googleauth.SingInFromGoogleResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetDetailsResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundSetsResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundsResponse;
import com.thor.networkmodule.model.responses.soundpackage.SoundPackageDescriptionResponse;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import com.thor.networkmodule.network.ApiService;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.io.ByteStreamsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;
import timber.log.Timber;

/* compiled from: UsersManager.kt */
@Singleton
@Metadata(d1 = {"\u0000\u0082\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u0085\u00012\u00020\u0001:\u0002\u0085\u0001B\u0017\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u0013\u001a\u00020\u0014J>\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0017H\u0002J\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0011J\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\rJ\u001f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\u0006\u0010#\u001a\u00020\u0011H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020&2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(J\u0018\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010*\u001a\u00020+H\u0002J\u0006\u0010,\u001a\u00020-J\u0006\u0010.\u001a\u00020-J\u0016\u0010/\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\u0006\u00100\u001a\u00020\u0017J\u0016\u00101\u001a\n\u0012\u0004\u0012\u000202\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u00103\u001a\n\u0012\u0004\u0012\u000204\u0018\u00010\rJ\u0016\u00105\u001a\n\u0012\u0004\u0012\u000206\u0018\u00010\r2\u0006\u00107\u001a\u00020\u001fJ>\u00105\u001a\n\u0012\u0004\u0012\u000206\u0018\u00010\r2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0017J\u0016\u00108\u001a\n\u0012\u0004\u0012\u000209\u0018\u00010\r2\u0006\u00107\u001a\u00020\u001fJ\u0016\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001f0\r2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0011J\u000e\u0010;\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010\rJ\u0016\u0010=\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010\r2\u0006\u0010?\u001a\u00020\u0017J\u001e\u0010@\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0!\u0018\u00010\r2\b\u0010#\u001a\u0004\u0018\u00010\u0011J\u001e\u0010A\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0!\u0018\u00010\r2\b\u0010#\u001a\u0004\u0018\u00010\u0011J\u0016\u0010B\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010\r2\u0006\u0010D\u001a\u00020\u0017J\u000e\u0010E\u001a\n\u0012\u0004\u0012\u00020<\u0018\u00010\rJ\u0016\u0010F\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010\r2\u0006\u0010?\u001a\u00020\u0017J\u0006\u0010G\u001a\u00020-J\u000e\u0010H\u001a\n\u0012\u0004\u0012\u00020I\u0018\u00010\rJ\u001b\u0010J\u001a\u0004\u0018\u00010>2\u0006\u0010?\u001a\u00020\u0017H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010KJ\u000e\u0010L\u001a\n\u0012\u0004\u0012\u00020M\u0018\u00010\rJ\u0014\u0010N\u001a\b\u0012\u0004\u0012\u00020O0\r2\u0006\u0010P\u001a\u00020\u0011J\u0010\u0010Q\u001a\u00020\u00172\b\u0010R\u001a\u0004\u0018\u00010\u0011J\u0010\u0010S\u001a\u00020-2\u0006\u0010T\u001a\u00020UH\u0002J\u0010\u0010V\u001a\u00020-2\u0006\u0010W\u001a\u00020<H\u0007J\u0014\u0010X\u001a\b\u0012\u0004\u0012\u00020\u000f0\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010Y\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rJ\u001e\u0010Z\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\u0006\u0010'\u001a\u00020(2\u0006\u0010T\u001a\u00020\u0011J\u0018\u0010[\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\b\u0010\\\u001a\u0004\u0018\u00010]J\u0018\u0010[\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\b\u0010^\u001a\u0004\u0018\u00010_J\u0018\u0010[\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\u0006\u0010`\u001a\u00020\u0017H\u0002J\u001e\u0010a\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\u0006\u0010'\u001a\u00020(2\u0006\u0010b\u001a\u00020\u0011J\u0018\u0010c\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\u0006\u0010D\u001a\u00020\u0017H\u0002J\u0014\u0010d\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010`\u001a\u00020\u0017J\u0014\u0010e\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010`\u001a\u00020\u0017J\u001e\u0010f\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\u0006\u0010`\u001a\u00020\u00172\u0006\u0010g\u001a\u00020\u0017J\u0014\u0010h\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010`\u001a\u00020\u0017J\u0014\u0010i\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010`\u001a\u00020\u0017J\u0014\u0010j\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010`\u001a\u00020\u0017J,\u0010k\u001a\b\u0012\u0004\u0012\u00020\u000b0\r2\u0006\u0010`\u001a\u00020\u00172\u0006\u0010g\u001a\u00020\u00172\u0006\u0010l\u001a\u00020\u00112\u0006\u0010m\u001a\u00020\u0011J\u001c\u0010n\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020q0pJ \u0010r\u001a\b\u0012\u0004\u0012\u00020\u001f0\r2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010s\u001a\u00020+J\u0014\u0010t\u001a\b\u0012\u0004\u0012\u00020u0\r2\u0006\u0010v\u001a\u00020wJ4\u0010t\u001a\b\u0012\u0004\u0012\u00020u0\r2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0011J\u0014\u0010x\u001a\b\u0012\u0004\u0012\u00020y0\r2\u0006\u0010z\u001a\u00020\u0011J\u0016\u0010{\u001a\u00020-2\u000e\b\u0002\u0010|\u001a\b\u0012\u0004\u0012\u00020-0}J\u000e\u0010~\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rJ\u0018\u0010\u007f\u001a\u00020-2\u000e\u0010\u0080\u0001\u001a\t\u0012\u0005\u0012\u00030\u0081\u00010pH\u0002J\r\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\rJ\u0018\u0010\u0083\u0001\u001a\u00020-2\u0007\u0010\u0084\u0001\u001a\u00020&2\u0006\u0010T\u001a\u00020\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0086\u0001"}, d2 = {"Lcom/thor/app/managers/UsersManager;", "Lcom/thor/app/managers/Manager;", "context", "Landroid/content/Context;", "database", "Lcom/thor/app/room/ThorDatabase;", "(Landroid/content/Context;Lcom/thor/app/room/ThorDatabase;)V", "getDatabase", "()Lcom/thor/app/room/ThorDatabase;", "unAuthorizedConsumer", "Lio/reactivex/functions/Consumer;", "Lcom/thor/networkmodule/model/responses/BaseResponse;", "addNotificationToken", "Lio/reactivex/Observable;", "changeServerPasswordValidationResponse", "Lcom/thor/networkmodule/model/responses/PasswordValidationResponse;", "password", "", "changeUserCar", "changeCarInfo", "Lcom/thor/networkmodule/model/ChangeCarInfo;", "token", "userId", "", "carMarkId", "carModelId", "carGenerationId", "carSeriesId", "checkBlockStatus", "deviceSN", "connectToolsDevice", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "coroutineFetchFile", "Lretrofit2/Response;", "Lokhttp3/ResponseBody;", "shortUrl", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createTempFile", "Ljava/io/File;", "uri", "Landroid/net/Uri;", "createUnAuthorizedConsumer", "clearToken", "", "deleteAllSoundPackages", "", "deleteTempLogsFromCacheDirectory", "deleteUserNotifications", "notificationId", "fetchAllFirmwaresList", "Lcom/thor/networkmodule/model/firmware/FirmwareProfileList;", "fetchAllSguSounds", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundsResponse;", "fetchCanFileUrl", "Lcom/thor/networkmodule/model/responses/CanConfigurationsResponse;", Scopes.PROFILE, "fetchCarFuelType", "Lcom/thor/networkmodule/model/responses/car/CarFuelTypeResponse;", "fetchCarInfo", "fetchDemoShopSoundPackages", "Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;", "fetchDemoSoundPackageDescription", "Lcom/thor/networkmodule/model/responses/soundpackage/SoundPackageDescriptionResponse;", "packageId", "fetchFile", "fetchFirmwareFile", "fetchSguSoundSetDetails", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetDetailsResponse;", "soundSetId", "fetchShopSoundPackages", "fetchSoundPackageDescription", "fetchSoundPackages", "fetchSoundSguPackages", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSetsResponse;", "fetchSuspendSoundPackageDescription", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchUserNotifications", "Lcom/thor/networkmodule/model/responses/NotificationsResponse;", "getCarInfoFromAuthorize", "Lcom/thor/networkmodule/model/responses/CarInfoAuthorizeResponse;", "deviceSn", "getIntStatusFrom", NotificationCompat.CATEGORY_STATUS, "handleError", Constants.IPC_BUNDLE_KEY_SEND_ERROR, "", "handleShopSoundPackages", "response", "logsPasswordValidationResponse", "removeNotification", "sendErrorLogToApi", "sendGooglePaymentInfo", "soundSet", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "soundPackage", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "soundPackageId", "sendLogsToApi", "fileName", "sendSguGooglePaymentInfo", "sendStatisticsAboutAddSoundPackage", "sendStatisticsAboutDeleteSoundPackage", "sendStatisticsAboutSoundPackage", "presetType", "sendStatisticsAboutUploadSoundPackage", "sendStatisticsAboutWatchInfoSoundPackage", "sendStatisticsLoadFailed", "sendStatisticsParameterChange", "parameterInHex", "value", "sendUniversalDataStatistics", "statisticsPacks", "", "Lcom/thor/networkmodule/model/StatisticsPack;", "signIn", "removeOldSoundPackage", "signUp", "Lcom/thor/networkmodule/model/responses/SignUpResponse;", "signUpInfo", "Lcom/thor/networkmodule/model/SignUpInfo;", "singInGoogleAuth", "Lcom/thor/networkmodule/model/responses/googleauth/SingInFromGoogleResponse;", "googleToken", "updateFirmwareProfile", "result", "Lkotlin/Function0;", "updateNotificationToken", "updateShopSoundPackagesDatabase", "entities", "Lcom/thor/app/room/entity/ShopSoundPackageEntity;", "updateVersion", "writeErrorToTxtFile", "file", "Companion", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UsersManager extends Manager {
    public static final int ACTION_ADD_SOUND_PACKAGE = 1;
    public static final int ACTION_DELETE_SOUND_PACKAGE = 2;
    public static final int ACTION_UPLOAD_SOUND_PACKAGE = 3;
    public static final int ACTION_WATCH_INFO_SOUND_PACKAGE = 4;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final MediaType MEDIA_TXT = MediaType.INSTANCE.get("multipart/form-data");
    private final ThorDatabase database;
    private final Consumer<BaseResponse> unAuthorizedConsumer;

    private final Consumer<BaseResponse> createUnAuthorizedConsumer(boolean clearToken) {
        return new Consumer() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Intrinsics.checkNotNullParameter((BaseResponse) obj, "baseResponse");
            }
        };
    }

    @JvmStatic
    public static final UsersManager from(Context context) {
        return INSTANCE.from(context);
    }

    public final ThorDatabase getDatabase() {
        return this.database;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UsersManager(@ApplicationContext Context context, ThorDatabase database) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(database, "database");
        this.database = database;
        this.unAuthorizedConsumer = createUnAuthorizedConsumer$default(this, false, 1, null);
    }

    public static /* synthetic */ Observable signIn$default(UsersManager usersManager, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return usersManager.signIn(str, z);
    }

    public final Observable<SignInResponse> signIn(String deviceSN, boolean removeOldSoundPackage) {
        if (removeOldSoundPackage) {
            deleteAllSoundPackages();
        }
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        if (hardwareProfile == null || hardwareProfile.isDefaultValuesSet()) {
            ApiService apiService = getApiService();
            String userGoogleToken = Settings.getUserGoogleToken();
            String deviceId = getDeviceId();
            Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
            String languageCode = getLanguageCode();
            Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
            Observable observableCompose = apiService.rxPostUserAuthorizeGoogle(userGoogleToken, deviceId, AbstractSpiCall.ANDROID_CLIENT_TYPE, languageCode).compose(new DefaultSchedulerTransformer());
            Intrinsics.checkNotNullExpressionValue(observableCompose, "{\n            apiService…rTransformer())\n        }");
            return observableCompose;
        }
        ApiService apiService2 = getApiService();
        String appVersionName = getAppVersionName();
        Intrinsics.checkNotNullExpressionValue(appVersionName, "appVersionName");
        String deviceId2 = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId2, "deviceId");
        short versionFirmware = hardwareProfile.getVersionFirmware();
        String languageCode2 = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode2, "languageCode");
        String appType = getAppType();
        Intrinsics.checkNotNullExpressionValue(appType, "appType");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%05d", Arrays.copyOf(new Object[]{Short.valueOf(hardwareProfile.getSerialNumber())}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Observable observableCompose2 = apiService2.rxPostUserAuthorize(deviceSN, appVersionName, deviceId2, versionFirmware, languageCode2, appType, str, Settings.getUserId()).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose2, "{\n            apiService…rTransformer())\n        }");
        return observableCompose2;
    }

    public final Observable<SingInFromGoogleResponse> singInGoogleAuth(String googleToken) {
        Intrinsics.checkNotNullParameter(googleToken, "googleToken");
        ApiService apiService = getApiService();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = apiService.rxPostUserGoogleAuthorize(googleToken, deviceId, AbstractSpiCall.ANDROID_CLIENT_TYPE, languageCode).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostUserGoo…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<SignUpResponse> signUp(SignUpInfo signUpInfo) {
        Intrinsics.checkNotNullParameter(signUpInfo, "signUpInfo");
        deleteAllSoundPackages();
        int carMarkId = signUpInfo.getCarMarkId();
        int carModelId = signUpInfo.getCarModelId();
        int carGenerationId = signUpInfo.getCarGenerationId();
        int carSeriesId = signUpInfo.getCarSeriesId();
        String deviceSN = signUpInfo.getDeviceSN();
        Intrinsics.checkNotNull(deviceSN);
        return signUp(carMarkId, carModelId, carGenerationId, carSeriesId, deviceSN);
    }

    public final Observable<SignUpResponse> signUp(int carMarkId, int carModelId, int carGenerationId, int carSeriesId, String deviceSN) {
        Intrinsics.checkNotNullParameter(deviceSN, "deviceSN");
        ApiService apiService = getApiService();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String language = getLanguage();
        Intrinsics.checkNotNullExpressionValue(language, "language");
        String appType = getAppType();
        Intrinsics.checkNotNullExpressionValue(appType, "appType");
        Observable observableCompose = apiService.rxPostUserRegister(deviceId, carMarkId, carModelId, carGenerationId, carSeriesId, deviceSN, language, appType).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostUserReg…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<ShopSoundPackagesResponse> fetchShopSoundPackages() {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostGetShopSoundPackages(userId, deviceId, accessToken, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<ShopSoundPackagesResponse> fetchDemoShopSoundPackages() {
        return getApiService().rxGetDemoShopSoundPackages().compose(new DefaultSchedulerTransformer());
    }

    public final Observable<SoundPackageDescriptionResponse> fetchSoundPackageDescription(int packageId) {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostGetShopSoundPack(packageId, userId, deviceId, accessToken, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Object fetchSuspendSoundPackageDescription(int i, Continuation<? super SoundPackageDescriptionResponse> continuation) {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.coroutinePostGetShopSoundPack(i, userId, deviceId, accessToken, languageCode, continuation);
    }

    public final Observable<SoundPackageDescriptionResponse> fetchDemoSoundPackageDescription(int packageId) {
        ApiService apiService = getApiService();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostGetDemoShopSoundPack(packageId, languageCode).compose(new DefaultSchedulerTransformer());
    }

    public final Observable<BaseResponse> sendStatisticsAboutSoundPackage(int soundPackageId, int presetType) {
        String deviceSN;
        HardwareProfile hardwareProfile;
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile == null || (deviceSN = profile.getDeviceSN()) == null || (hardwareProfile = Settings.getHardwareProfile()) == null) {
            return null;
        }
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        short versionFirmware = hardwareProfile.getVersionFirmware();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%05d", Arrays.copyOf(new Object[]{Short.valueOf(hardwareProfile.getSerialNumber())}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        String strValueOf = String.valueOf((int) hardwareProfile.getVersionHardware());
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsAboutSoundPackage(userId, deviceId, deviceSN, versionFirmware, soundPackageId, presetType, str, strValueOf, languageCode);
    }

    public final Observable<BaseResponse> sendUniversalDataStatistics(List<StatisticsPack> statisticsPacks) {
        Intrinsics.checkNotNullParameter(statisticsPacks, "statisticsPacks");
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        if (hardwareProfile == null) {
            return null;
        }
        short serialNumber = hardwareProfile.getSerialNumber();
        int userId = Settings.getUserId();
        String packJson = new Gson().toJson(statisticsPacks);
        ApiService apiService = getApiService();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%05d", Arrays.copyOf(new Object[]{Short.valueOf(serialNumber)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Intrinsics.checkNotNullExpressionValue(packJson, "packJson");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostUniversalDataStatistics(userId, deviceId, str, packJson, languageCode);
    }

    public final Observable<BaseResponse> sendStatisticsAboutAddSoundPackage(int soundPackageId) {
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsAboutActionWithSoundPackage(userId, deviceId, soundPackageId, 1, languageCode);
    }

    public final Observable<BaseResponse> sendStatisticsAboutDeleteSoundPackage(int soundPackageId) {
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsAboutActionWithSoundPackage(userId, deviceId, soundPackageId, 2, languageCode);
    }

    public final Observable<BaseResponse> sendStatisticsAboutUploadSoundPackage(int soundPackageId) {
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsAboutActionWithSoundPackage(userId, deviceId, soundPackageId, 3, languageCode);
    }

    public final Observable<BaseResponse> sendStatisticsAboutWatchInfoSoundPackage(int soundPackageId) {
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsAboutActionWithSoundPackage(userId, deviceId, soundPackageId, 4, languageCode);
    }

    public final Observable<BaseResponse> sendStatisticsLoadFailed(int soundPackageId) {
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsLoadFailed(userId, deviceId, soundPackageId, languageCode);
    }

    public final Observable<BaseResponse> sendStatisticsParameterChange(int soundPackageId, int presetType, String parameterInHex, String value) {
        Intrinsics.checkNotNullParameter(parameterInHex, "parameterInHex");
        Intrinsics.checkNotNullParameter(value, "value");
        ApiService apiService = getApiService();
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostSendStatisticsParameterChange(userId, deviceId, soundPackageId, presetType, parameterInHex, value, languageCode);
    }

    public final void fetchSoundPackages() {
        if (Settings.INSTANCE.isAccessSession()) {
            ApiService apiService = getApiService();
            int userId = getUserId();
            String deviceId = getDeviceId();
            Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
            String accessToken = getAccessToken();
            Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
            String languageCode = getLanguageCode();
            Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
            Observable observableDoOnNext = apiService.rxPostGetShopSoundPackages(userId, deviceId, accessToken, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
            final AnonymousClass1 anonymousClass1 = new AnonymousClass1(this);
            Consumer consumer = new Consumer() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UsersManager.fetchSoundPackages$lambda$4(anonymousClass1, obj);
                }
            };
            final AnonymousClass2 anonymousClass2 = new AnonymousClass2(this);
            observableDoOnNext.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    UsersManager.fetchSoundPackages$lambda$5(anonymousClass2, obj);
                }
            });
            return;
        }
        FirebaseCrashlytics.getInstance().log("fetchSoundPackages: isAccessSession == false");
        FirebaseCrashlytics.getInstance().recordException(new Throwable("fetchSoundPackages: isAccessSession == false"));
    }

    /* compiled from: UsersManager.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.UsersManager$fetchSoundPackages$1, reason: invalid class name */
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<ShopSoundPackagesResponse, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, UsersManager.class, "handleShopSoundPackages", "handleShopSoundPackages(Lcom/thor/networkmodule/model/responses/ShopSoundPackagesResponse;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ShopSoundPackagesResponse shopSoundPackagesResponse) {
            invoke2(shopSoundPackagesResponse);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(ShopSoundPackagesResponse p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((UsersManager) this.receiver).handleShopSoundPackages(p0);
        }
    }

    /* compiled from: UsersManager.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.UsersManager$fetchSoundPackages$2, reason: invalid class name */
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, UsersManager.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((UsersManager) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchSoundPackages$lambda$4(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchSoundPackages$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void handleShopSoundPackages(ShopSoundPackagesResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        if (response.isSuccessful()) {
            ArrayList arrayList = new ArrayList();
            List<ShopSoundPackage> soundItems = response.getSoundItems();
            boolean z = false;
            if (soundItems != null) {
                int i = 0;
                for (ShopSoundPackage shopSoundPackage : soundItems) {
                    if (shopSoundPackage.isPurchased() || Settings.INSTANCE.isSubscriptionActive()) {
                        arrayList.add(new ShopSoundPackageEntity(shopSoundPackage.getId(), shopSoundPackage.getPkgName(), shopSoundPackage.getPkgVer(), shopSoundPackage.getPkgImageUrl(), getIntStatusFrom(shopSoundPackage.getPkgStatus()), (int) shopSoundPackage.getPrice(), shopSoundPackage.getModeImages()));
                    }
                    if (!shopSoundPackage.isPurchased() && (i = i + 1) >= 3) {
                        z = true;
                    }
                }
            }
            Variables.INSTANCE.setSUBSCRIPTION_FEATURE_REQUIREMENTS_SATISFIED(z);
            updateShopSoundPackagesDatabase(arrayList);
            return;
        }
        String error = response.getError();
        if (error != null) {
            FirebaseCrashlytics.getInstance().log(error);
        }
        FirebaseCrashlytics.getInstance().recordException(new Throwable(response.getError()));
    }

    private final void updateShopSoundPackagesDatabase(final List<ShopSoundPackageEntity> entities) {
        this.database.shopSoundPackageDao().deleteAllElements();
        ShopSoundPackageDao shopSoundPackageDao = this.database.shopSoundPackageDao();
        ShopSoundPackageEntity[] shopSoundPackageEntityArr = (ShopSoundPackageEntity[]) entities.toArray(new ShopSoundPackageEntity[0]);
        Completable completableInsert = shopSoundPackageDao.insert((ShopSoundPackageEntity[]) Arrays.copyOf(shopSoundPackageEntityArr, shopSoundPackageEntityArr.length));
        Action action = new Action() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() {
                UsersManager.updateShopSoundPackagesDatabase$lambda$9(entities);
            }
        };
        final C04162 c04162 = new Function1<Throwable, Unit>() { // from class: com.thor.app.managers.UsersManager.updateShopSoundPackagesDatabase.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable th) {
                Timber.INSTANCE.e(th);
                FirebaseCrashlytics.getInstance().recordException(th);
            }
        };
        this.compositeDisposable.add(completableInsert.subscribe(action, new Consumer() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                UsersManager.updateShopSoundPackagesDatabase$lambda$10(c04162, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateShopSoundPackagesDatabase$lambda$9(List entities) {
        Intrinsics.checkNotNullParameter(entities, "$entities");
        Timber.INSTANCE.i("Insert to database: %s", entities.toString());
        EventBus.getDefault().post(new ShopSoundPackagesUpdateEvent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateShopSoundPackagesDatabase$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final int getIntStatusFrom(String status) {
        if (status != null) {
            return (Intrinsics.areEqual(status, BooleanUtils.TRUE) || Intrinsics.areEqual(status, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) ? 1 : 0;
        }
        return 0;
    }

    public final void deleteAllSoundPackages() {
        ThorDatabase.Companion companion = ThorDatabase.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        companion.from(context).shopSoundPackageDao().deleteAllElements();
        ThorDatabase.Companion companion2 = ThorDatabase.INSTANCE;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        companion2.from(context2).installedSoundPackageDao().deleteAllElements();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void updateFirmwareProfile$default(UsersManager usersManager, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = new Function0<Unit>() { // from class: com.thor.app.managers.UsersManager.updateFirmwareProfile.1
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }
            };
        }
        usersManager.updateFirmwareProfile(function0);
    }

    public final void updateFirmwareProfile(final Function0<Unit> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getSerialNumber()) : null;
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String deviceSN = profile != null ? profile.getDeviceSN() : null;
        HardwareProfile hardwareProfile2 = Settings.getHardwareProfile();
        Short shValueOf2 = hardwareProfile2 != null ? Short.valueOf(hardwareProfile2.getVersionHardware()) : null;
        if (shValueOf != null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            deviceSN = String.format("%05d", Arrays.copyOf(new Object[]{shValueOf}, 1));
            Intrinsics.checkNotNullExpressionValue(deviceSN, "format(...)");
        }
        ApiService apiService = getApiService();
        Intrinsics.checkNotNullExpressionValue(apiService, "apiService");
        String strValueOf = String.valueOf(shValueOf2);
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = ApiService.DefaultImpls.rxGetFirmwareProfile$default(apiService, deviceSN, strValueOf, null, languageCode, 4, null).compose(new DefaultSchedulerTransformer());
        final Function1<FirmwareProfile, Unit> function1 = new Function1<FirmwareProfile, Unit>() { // from class: com.thor.app.managers.UsersManager.updateFirmwareProfile.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirmwareProfile firmwareProfile) {
                invoke2(firmwareProfile);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirmwareProfile firmwareProfile) {
                if (firmwareProfile.getStatus()) {
                    Settings.INSTANCE.saveFirmwareProfile(firmwareProfile);
                    result.invoke();
                } else {
                    AlertDialog alertDialogCreateErrorAlertDialogWithSendLogOption$default = DialogManager.createErrorAlertDialogWithSendLogOption$default(DialogManager.INSTANCE, this.getContext(), firmwareProfile.getError(), null, 4, null);
                    if (alertDialogCreateErrorAlertDialogWithSendLogOption$default != null) {
                        alertDialogCreateErrorAlertDialogWithSendLogOption$default.show();
                    }
                }
            }
        };
        Consumer consumer = new Consumer() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                UsersManager.updateFirmwareProfile$lambda$13(function1, obj);
            }
        };
        final AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
        this.compositeDisposable.add(observableCompose.subscribe(consumer, new Consumer() { // from class: com.thor.app.managers.UsersManager$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                UsersManager.updateFirmwareProfile$lambda$14(anonymousClass3, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateFirmwareProfile$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* compiled from: UsersManager.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.thor.app.managers.UsersManager$updateFirmwareProfile$3, reason: invalid class name */
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1<Throwable, Unit> {
        AnonymousClass3(Object obj) {
            super(1, obj, UsersManager.class, "handleError", "handleError(Ljava/lang/Throwable;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Throwable p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            ((UsersManager) this.receiver).handleError(p0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateFirmwareProfile$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final Observable<BaseResponse> updateVersion() {
        String userGoogleToken = Settings.getUserGoogleToken();
        String userGoogleUserId = Settings.getUserGoogleUserId();
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String deviceSN = profile != null ? profile.getDeviceSN() : null;
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getVersionHardware()) : null;
        HardwareProfile hardwareProfile2 = Settings.getHardwareProfile();
        Observable observableCompose = getApiService().rxUpdateVersion(userGoogleToken, userGoogleUserId, String.valueOf(deviceSN), String.valueOf(hardwareProfile2 != null ? Short.valueOf(hardwareProfile2.getVersionFirmware()) : null), String.valueOf(shValueOf)).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxUpdateVersi…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<Response<ResponseBody>> fetchFirmwareFile(String shortUrl) {
        Intrinsics.checkNotNull(shortUrl);
        return getApiService().rxGetFirmwareFile(StringKt.getFullFileUrl(shortUrl)).compose(new DefaultSchedulerTransformer());
    }

    public final Observable<Response<ResponseBody>> fetchFile(String shortUrl) {
        if (TextUtils.isEmpty(shortUrl)) {
            return null;
        }
        Intrinsics.checkNotNull(shortUrl);
        return getApiService().rxGetFile(StringKt.getFullFileUrl(shortUrl));
    }

    public final Object coroutineFetchFile(String str, Continuation<? super Response<ResponseBody>> continuation) {
        return getApiService().coroutineGetFile(StringKt.getFullFileUrl(str), continuation);
    }

    public final Observable<BaseResponse> sendGooglePaymentInfo(ShopSoundPackage soundPackage) {
        if (soundPackage == null) {
            return null;
        }
        return sendGooglePaymentInfo(soundPackage.getId());
    }

    private final Observable<BaseResponse> sendGooglePaymentInfo(int soundPackageId) {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        ApiService apiService = getApiService();
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostGooglePlayBilling(accessToken, userId, deviceId, soundPackageId, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<BaseResponse> sendGooglePaymentInfo(SguSoundSet soundSet) {
        if (soundSet == null) {
            return null;
        }
        return sendSguGooglePaymentInfo(soundSet.getId());
    }

    private final Observable<BaseResponse> sendSguGooglePaymentInfo(int soundSetId) {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        ApiService apiService = getApiService();
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostPurchaseSgu(accessToken, userId, deviceId, soundSetId, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<CanConfigurationsResponse> fetchCanFileUrl(SignInResponse profile) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        return fetchCanFileUrl(profile.getUserId(), Settings.getAccessToken(), profile.getCarMarkId(), profile.getCarModelId(), profile.getCarGenerationId(), profile.getCarSerieId());
    }

    public final Observable<CanConfigurationsResponse> fetchCanFileUrl(int userId, String token, int carMarkId, int carModelId, int carGenerationId, int carSeriesId) {
        String deviceSN;
        Intrinsics.checkNotNullParameter(token, "token");
        ApiService apiService = getApiService();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile == null || (deviceSN = profile.getDeviceSN()) == null) {
            deviceSN = "";
        }
        return apiService.rxPostGetCanFile(userId, deviceId, token, carMarkId, carModelId, carGenerationId, carSeriesId, languageCode, deviceSN).compose(new DefaultSchedulerTransformer());
    }

    public final Observable<CarFuelTypeResponse> fetchCarFuelType(SignInResponse profile) {
        Intrinsics.checkNotNullParameter(profile, "profile");
        Log.i("fetchCarFuelType", "fetchCarFuelType: " + profile.getCarModelId() + StringUtils.SPACE + profile.getCarSerieId());
        ApiService apiService = getApiService();
        int carModelId = profile.getCarModelId();
        int carSerieId = profile.getCarSerieId();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostGetCarFuelType(carModelId, carSerieId, languageCode).compose(new DefaultSchedulerTransformer());
    }

    public final Observable<BaseResponse> addNotificationToken() {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            token = "";
        }
        String str = token;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ApiService apiService = getApiService();
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String appType = getAppType();
        Intrinsics.checkNotNullExpressionValue(appType, "appType");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostNotificationToken(accessToken, userId, deviceId, str, appType, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<BaseResponse> updateNotificationToken() {
        return addNotificationToken();
    }

    public final Observable<BaseResponse> removeNotification() {
        if (!Settings.INSTANCE.isAccessSession()) {
            return null;
        }
        ApiService apiService = getApiService();
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String appType = getAppType();
        Intrinsics.checkNotNullExpressionValue(appType, "appType");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostNotificationRemove(accessToken, userId, deviceId, appType, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<BaseResponse> changeUserCar(ChangeCarInfo changeCarInfo) {
        Intrinsics.checkNotNullParameter(changeCarInfo, "changeCarInfo");
        Observable observableCompose = changeUserCar(changeCarInfo.getToken(), changeCarInfo.getUserId(), changeCarInfo.getCarMarkId(), changeCarInfo.getCarModelId(), changeCarInfo.getCarGenerationId(), changeCarInfo.getCarSeriesId()).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "changeUserCar(\n         …ltSchedulerTransformer())");
        return observableCompose;
    }

    private final Observable<BaseResponse> changeUserCar(String token, int userId, int carMarkId, int carModelId, int carGenerationId, int carSeriesId) {
        String deviceSN;
        ApiService apiService = getApiService();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile == null || (deviceSN = profile.getDeviceSN()) == null) {
            deviceSN = "";
        }
        Observable observableCompose = apiService.rxPostChangeCarModels(token, userId, deviceId, carMarkId, carModelId, languageCode, carGenerationId, carSeriesId, deviceSN).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostChangeC…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<SguSoundSetsResponse> fetchSoundSguPackages() {
        System.out.println((Object) getAccessToken());
        System.out.println(getUserId());
        System.out.println((Object) getLanguage());
        if (Settings.INSTANCE.isAccessSession()) {
            ApiService apiService = getApiService();
            String accessToken = getAccessToken();
            Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
            int userId = getUserId();
            String deviceId = getDeviceId();
            Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
            String language = getLanguage();
            Intrinsics.checkNotNullExpressionValue(language, "language");
            return apiService.rxPostGetSguSoundPack(accessToken, userId, deviceId, language).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
        }
        FirebaseCrashlytics.getInstance().log("fetchSoundSguPackages: isAccessSession == false");
        FirebaseCrashlytics.getInstance().recordException(new Throwable("fetchSoundSguPackages: isAccessSession == false"));
        return null;
    }

    public final Observable<SguSoundSetDetailsResponse> fetchSguSoundSetDetails(int soundSetId) {
        if (Settings.INSTANCE.isAccessSession()) {
            ApiService apiService = getApiService();
            String accessToken = getAccessToken();
            Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
            int userId = getUserId();
            String deviceId = getDeviceId();
            Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
            return apiService.rxPostGetSguSoundPackDetails(accessToken, userId, deviceId, soundSetId, Settings.getLanguageCode()).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
        }
        FirebaseCrashlytics.getInstance().log("fetchSoundSguPackages: isAccessSession == false");
        FirebaseCrashlytics.getInstance().recordException(new Throwable("fetchSoundSguPackages: isAccessSession == false"));
        return null;
    }

    public final Observable<SguSoundsResponse> fetchAllSguSounds() {
        if (Settings.INSTANCE.isAccessSession()) {
            ApiService apiService = getApiService();
            String accessToken = getAccessToken();
            Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
            int userId = getUserId();
            String deviceId = getDeviceId();
            Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
            return apiService.rxPostGetAllSguSounds(accessToken, userId, deviceId, Settings.getLanguageCode()).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
        }
        FirebaseCrashlytics.getInstance().log("fetchSoundSguPackages: isAccessSession == false");
        FirebaseCrashlytics.getInstance().recordException(new Throwable("fetchSoundSguPackages: isAccessSession == false"));
        return null;
    }

    public final Observable<NotificationsResponse> fetchUserNotifications() {
        ApiService apiService = getApiService();
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostUserNotifications(accessToken, userId, deviceId, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<BaseResponse> deleteUserNotifications(int notificationId) {
        ApiService apiService = getApiService();
        String accessToken = getAccessToken();
        Intrinsics.checkNotNullExpressionValue(accessToken, "accessToken");
        int userId = getUserId();
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxPostDeleteUserNotifications(accessToken, userId, deviceId, notificationId, languageCode).compose(new DefaultSchedulerTransformer()).doOnNext(this.unAuthorizedConsumer);
    }

    public final Observable<BaseResponse> checkBlockStatus(String userId, String deviceSN) {
        Intrinsics.checkNotNullParameter(userId, "userId");
        Intrinsics.checkNotNullParameter(deviceSN, "deviceSN");
        ApiService apiService = getApiService();
        String userGoogleToken = Settings.getUserGoogleToken();
        String deviceId = getDeviceId();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = apiService.rxPostBlockBoard(userGoogleToken, userId, deviceId, deviceSN, languageCode).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostBlockBo…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<SignInResponse> connectToolsDevice() {
        String deviceSN;
        String deviceId;
        String deviceSN2;
        ApiService apiService = getApiService();
        String userGoogleToken = Settings.getUserGoogleToken();
        String userGoogleUserId = Settings.getUserGoogleUserId();
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String str = (profile == null || (deviceSN2 = profile.getDeviceSN()) == null) ? "" : deviceSN2;
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        String str2 = (hardwareProfile == null || (deviceId = hardwareProfile.getDeviceId()) == null) ? "" : deviceId;
        int carMarkId = CarInfoPreference.getCarMarkId();
        int carModelID = CarInfoPreference.getCarModelID();
        int carGenerationId = CarInfoPreference.getCarGenerationId();
        int carSerieId = CarInfoPreference.getCarSerieId();
        HardwareProfile hardwareProfile2 = Settings.getHardwareProfile();
        String strValueOf = String.valueOf(hardwareProfile2 != null ? Short.valueOf(hardwareProfile2.getVersionFirmware()) : null);
        SignInResponse profile2 = Settings.INSTANCE.getProfile();
        String str3 = (profile2 == null || (deviceSN = profile2.getDeviceSN()) == null) ? "" : deviceSN;
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = apiService.rxPostToolsConnect(userGoogleToken, userGoogleUserId, AbstractSpiCall.ANDROID_CLIENT_TYPE, str, str2, carMarkId, carModelID, carGenerationId, carSerieId, str3, strValueOf, BuildConfig.VERSION_NAME, languageCode).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostToolsCo…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<SignInResponse> fetchCarInfo(String deviceSN) {
        ApiService apiService = getApiService();
        String accessToken = Settings.getAccessToken();
        String strValueOf = String.valueOf(Settings.getUserId());
        String deviceId = getDeviceId();
        Intrinsics.checkNotNullExpressionValue(deviceId, "deviceId");
        if (deviceSN == null) {
            deviceSN = "";
        }
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = apiService.rxPostToolsCarInfo(accessToken, strValueOf, deviceId, deviceSN, languageCode).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostToolsCa…ltSchedulerTransformer())");
        return observableCompose;
    }

    public final Observable<FirmwareProfileList> fetchAllFirmwaresList(String password) {
        Intrinsics.checkNotNullParameter(password, "password");
        HardwareProfile hardwareProfile = Settings.getHardwareProfile();
        Short shValueOf = hardwareProfile != null ? Short.valueOf(hardwareProfile.getSerialNumber()) : null;
        SignInResponse profile = Settings.INSTANCE.getProfile();
        String deviceSN = profile != null ? profile.getDeviceSN() : null;
        HardwareProfile hardwareProfile2 = Settings.getHardwareProfile();
        if (hardwareProfile2 != null) {
            Short.valueOf(hardwareProfile2.getVersionHardware());
        }
        if (shValueOf != null) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            deviceSN = String.format("%05d", Arrays.copyOf(new Object[]{shValueOf}, 1));
            Intrinsics.checkNotNullExpressionValue(deviceSN, "format(...)");
        }
        ApiService apiService = getApiService();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxGetFirmwareAllProfiles(deviceSN, password, languageCode).compose(new DefaultSchedulerTransformer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleError(Throwable error) {
        AlertDialog alertDialogCreateErrorAlertDialog$default;
        if (Intrinsics.areEqual(error.getMessage(), "HTTP 400")) {
            AlertDialog alertDialogCreateErrorAlertDialog$default2 = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, getContext(), error.getMessage(), null, 4, null);
            if (alertDialogCreateErrorAlertDialog$default2 != null) {
                alertDialogCreateErrorAlertDialog$default2.show();
            }
        } else if (Intrinsics.areEqual(error.getMessage(), "HTTP 500") && (alertDialogCreateErrorAlertDialog$default = DialogManager.createErrorAlertDialog$default(DialogManager.INSTANCE, getContext(), error.getMessage(), null, 4, null)) != null) {
            alertDialogCreateErrorAlertDialog$default.show();
        }
        Timber.INSTANCE.e(error);
        FirebaseCrashlytics.getInstance().recordException(error);
    }

    static /* synthetic */ Consumer createUnAuthorizedConsumer$default(UsersManager usersManager, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return usersManager.createUnAuthorizedConsumer(z);
    }

    public final File createTempFile(Context context, Uri uri) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        File file = new File(context.getCacheDir(), FileLogger.LOGS_FILE_NAME);
        if (!file.exists()) {
            file.mkdir();
        }
        File tempFile = File.createTempFile(FileLogger.LOGS_FILE_NAME, "", file);
        if (inputStreamOpenInputStream != null) {
            Intrinsics.checkNotNullExpressionValue(tempFile, "tempFile");
            ByteStreamsKt.copyTo$default(inputStreamOpenInputStream, new FileOutputStream(tempFile), 0, 2, null);
        }
        Intrinsics.checkNotNullExpressionValue(tempFile, "tempFile");
        return tempFile;
    }

    public final void deleteTempLogsFromCacheDirectory() {
        FilesKt.deleteRecursively(new File(getContext().getCacheDir(), FileLogger.LOGS_FILE_NAME));
    }

    public final Observable<BaseResponse> sendLogsToApi(Uri uri, String fileName) {
        String deviceSN;
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        deleteTempLogsFromCacheDirectory();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        RequestBody requestBodyCreate = RequestBody.INSTANCE.create(createTempFile(context, uri), MEDIA_TXT);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile != null && (deviceSN = profile.getDeviceSN()) != null) {
            ApiService apiService = getApiService();
            RequestBody requestBodyCreate$default = RequestBody.Companion.create$default(RequestBody.INSTANCE, deviceSN, (MediaType) null, 1, (Object) null);
            MultipartBody.Part partCreateFormData = MultipartBody.Part.INSTANCE.createFormData("logFile", "logFile", requestBodyCreate);
            String languageCode = getLanguageCode();
            Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
            return apiService.rxSendLogs(requestBodyCreate$default, partCreateFormData, languageCode);
        }
        throw new Exception("Device SN is null");
    }

    public final Observable<BaseResponse> sendErrorLogToApi(Uri uri, String error) {
        String deviceSN;
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(error, "error");
        Log.i("sendErrorLogToApi", "sendErrorLogToApi: " + error);
        deleteTempLogsFromCacheDirectory();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        File fileCreateTempFile = createTempFile(context, uri);
        writeErrorToTxtFile(fileCreateTempFile, error);
        RequestBody requestBodyCreate = RequestBody.INSTANCE.create(fileCreateTempFile, MEDIA_TXT);
        SignInResponse profile = Settings.INSTANCE.getProfile();
        if (profile != null && (deviceSN = profile.getDeviceSN()) != null) {
            Log.i("sendErrorLogToApi", "sendErrorLogToApi: " + deviceSN);
            ApiService apiService = getApiService();
            RequestBody requestBodyCreate$default = RequestBody.Companion.create$default(RequestBody.INSTANCE, deviceSN, (MediaType) null, 1, (Object) null);
            MultipartBody.Part partCreateFormData = MultipartBody.Part.INSTANCE.createFormData("logFile", "logFile", requestBodyCreate);
            String languageCode = getLanguageCode();
            Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
            return apiService.rxSendLogs(requestBodyCreate$default, partCreateFormData, languageCode);
        }
        throw new Exception("Device SN is null");
    }

    public final void writeErrorToTxtFile(File file, String error) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(error, "error");
        Log.i("writeErrorToTxtFile", "writeErrorToTxtFile: " + error);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
        BufferedWriter bufferedWriter2 = bufferedWriter;
        Intrinsics.checkNotNullExpressionValue(bufferedWriter2.append('\n'), "append(...)");
        Appendable appendableAppend = bufferedWriter2.append((CharSequence) "!!! MAIN ERROR !!!");
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(appendableAppend.append('\n'), "append(...)");
        Appendable appendableAppend2 = bufferedWriter2.append((CharSequence) error);
        Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
        Intrinsics.checkNotNullExpressionValue(appendableAppend2.append('\n'), "append(...)");
        bufferedWriter.close();
    }

    public final Observable<PasswordValidationResponse> logsPasswordValidationResponse(String password) {
        Intrinsics.checkNotNullParameter(password, "password");
        ApiService apiService = getApiService();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxLogsPasswordValidation(password, languageCode);
    }

    public final Observable<PasswordValidationResponse> changeServerPasswordValidationResponse(String password) {
        Intrinsics.checkNotNullParameter(password, "password");
        ApiService apiService = getApiService();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        return apiService.rxChangeServerPasswordValidationResponse(password, languageCode);
    }

    public final Observable<CarInfoAuthorizeResponse> getCarInfoFromAuthorize(String deviceSn) {
        Intrinsics.checkNotNullParameter(deviceSn, "deviceSn");
        ApiService apiService = getApiService();
        String languageCode = getLanguageCode();
        Intrinsics.checkNotNullExpressionValue(languageCode, "languageCode");
        Observable observableCompose = apiService.rxPostGetInfoFromAuthorize(deviceSn, deviceSn, languageCode).compose(new DefaultSchedulerTransformer());
        Intrinsics.checkNotNullExpressionValue(observableCompose, "apiService.rxPostGetInfo…ltSchedulerTransformer())");
        return observableCompose;
    }

    /* compiled from: UsersManager.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lcom/thor/app/managers/UsersManager$Companion;", "", "()V", "ACTION_ADD_SOUND_PACKAGE", "", "ACTION_DELETE_SOUND_PACKAGE", "ACTION_UPLOAD_SOUND_PACKAGE", "ACTION_WATCH_INFO_SOUND_PACKAGE", "MEDIA_TXT", "Lokhttp3/MediaType;", "getMEDIA_TXT", "()Lokhttp3/MediaType;", Constants.MessagePayloadKeys.FROM, "Lcom/thor/app/managers/UsersManager;", "context", "Landroid/content/Context;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MediaType getMEDIA_TXT() {
            return UsersManager.MEDIA_TXT;
        }

        @JvmStatic
        public final UsersManager from(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Context applicationContext = context.getApplicationContext();
            Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.thor.app.ThorApplication");
            return ((ThorApplication) applicationContext).getUsersManager();
        }
    }
}
