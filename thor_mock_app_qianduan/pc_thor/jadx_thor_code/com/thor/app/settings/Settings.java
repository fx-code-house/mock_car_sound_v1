package com.thor.app.settings;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.thor.businessmodule.bluetooth.model.other.HardwareProfile;
import com.thor.businessmodule.bluetooth.model.other.InstalledPresets;
import com.thor.businessmodule.bluetooth.model.other.settings.DeviceStatus;
import com.thor.networkmodule.model.firmware.FirmwareProfile;
import com.thor.networkmodule.model.responses.SignInResponse;
import com.thor.networkmodule.model.responses.sgu.SguSoundSet;
import com.thor.networkmodule.model.shop.ShopSoundPackage;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.core.Configurable;
import timber.log.Timber;

/* compiled from: Settings.kt */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b5\bÆ\u0002\u0018\u00002\u00020\u0001:\u0004\u009c\u0001\u009d\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0007J\b\u00102\u001a\u00020\u0004H\u0007J\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020\u0004J\u0006\u00106\u001a\u00020\u0004J\b\u00107\u001a\u0004\u0018\u000108J\u0006\u00109\u001a\u00020:J\b\u0010;\u001a\u0004\u0018\u00010<J\n\u0010=\u001a\u0004\u0018\u00010>H\u0007J\u0006\u0010?\u001a\u00020:J\b\u0010@\u001a\u0004\u0018\u00010)J\b\u0010A\u001a\u00020:H\u0007J\u0006\u0010B\u001a\u00020:J\b\u0010C\u001a\u00020\u0004H\u0007J\u0006\u0010D\u001a\u00020EJ\u0006\u0010F\u001a\u00020:J\b\u0010G\u001a\u0004\u0018\u00010HJ\b\u0010I\u001a\u000204H\u0007J\u0006\u0010J\u001a\u00020\u0004J\b\u0010K\u001a\u0004\u0018\u00010LJ\u0006\u0010M\u001a\u00020:J\b\u0010N\u001a\u0004\u0018\u00010OJ\b\u0010P\u001a\u00020\u0004H\u0007J\b\u0010Q\u001a\u00020\u0004H\u0007J\b\u0010R\u001a\u000204H\u0007J\u0006\u0010S\u001a\u00020:J\u0006\u0010T\u001a\u00020:J\u0006\u0010U\u001a\u00020:J\u0006\u0010V\u001a\u00020:J\u0006\u0010W\u001a\u00020:J\u0006\u0010X\u001a\u00020:J\u0006\u0010Y\u001a\u00020:J\u0006\u0010Z\u001a\u00020:J\u000e\u0010[\u001a\u00020:2\u0006\u0010\\\u001a\u000204J\u0006\u0010]\u001a\u00020:J\b\u0010^\u001a\u00020/H\u0007J\b\u0010_\u001a\u00020/H\u0007J\u000e\u0010`\u001a\u00020/2\u0006\u0010a\u001a\u00020\u0004J\u0006\u0010b\u001a\u00020/J\u0010\u0010c\u001a\u00020/2\u0006\u0010d\u001a\u00020\u0004H\u0007J\u000e\u0010e\u001a\u00020/2\u0006\u0010f\u001a\u000204J\u0010\u0010g\u001a\u00020/2\b\u0010h\u001a\u0004\u0018\u00010iJ\u000e\u0010j\u001a\u00020/2\u0006\u0010k\u001a\u00020:J\u000e\u0010l\u001a\u00020/2\u0006\u0010m\u001a\u00020:J\u0010\u0010n\u001a\u00020/2\b\u0010o\u001a\u0004\u0018\u00010\u0004J\u0010\u0010p\u001a\u00020/2\b\u0010q\u001a\u0004\u0018\u000108J\u000e\u0010r\u001a\u00020/2\u0006\u0010s\u001a\u00020:J\u0010\u0010t\u001a\u00020/2\b\u0010u\u001a\u0004\u0018\u00010<J\u000e\u0010v\u001a\u00020/2\u0006\u0010w\u001a\u00020:J\u0012\u0010x\u001a\u00020/2\b\u0010u\u001a\u0004\u0018\u00010>H\u0007J\u000e\u0010y\u001a\u00020/2\u0006\u0010s\u001a\u00020:J\u000e\u0010z\u001a\u00020/2\u0006\u0010(\u001a\u00020)J\u0010\u0010{\u001a\u00020/2\u0006\u0010|\u001a\u00020:H\u0007J\u000e\u0010}\u001a\u00020/2\u0006\u0010~\u001a\u00020:J\u000f\u0010\u007f\u001a\u00020/2\u0007\u0010\u0080\u0001\u001a\u00020\u0004J\u0007\u0010\u0081\u0001\u001a\u00020/J\u0010\u0010\u0082\u0001\u001a\u00020/2\u0007\u0010\u0083\u0001\u001a\u00020:J\u0010\u0010\u0084\u0001\u001a\u00020/2\u0007\u0010\u0085\u0001\u001a\u00020:J\u0010\u0010\u0086\u0001\u001a\u00020/2\u0007\u0010\u0087\u0001\u001a\u00020:J\u000f\u0010\u0088\u0001\u001a\u00020/2\u0006\u0010s\u001a\u00020:J\u0013\u0010\u0089\u0001\u001a\u00020/2\b\u0010u\u001a\u0004\u0018\u00010HH\u0007J\u0010\u0010\u008a\u0001\u001a\u00020/2\u0007\u0010\u008b\u0001\u001a\u000204J\u0010\u0010\u008c\u0001\u001a\u00020/2\u0007\u0010\u008d\u0001\u001a\u00020\u0004J\u0010\u0010\u008e\u0001\u001a\u00020/2\u0007\u0010\u008f\u0001\u001a\u00020LJ\u0010\u0010\u0090\u0001\u001a\u00020/2\u0007\u0010\u0091\u0001\u001a\u00020OJ\u0010\u0010\u0092\u0001\u001a\u00020/2\u0007\u0010\u0093\u0001\u001a\u00020:J\u0011\u0010\u0094\u0001\u001a\u00020/2\u0006\u0010d\u001a\u00020\u0004H\u0007J\u0012\u0010\u0095\u0001\u001a\u00020/2\u0007\u0010\u0096\u0001\u001a\u00020\u0004H\u0007J\u0012\u0010\u0097\u0001\u001a\u00020/2\u0007\u0010\u0096\u0001\u001a\u000204H\u0007J\u0010\u0010\u0098\u0001\u001a\u00020/2\u0007\u0010\u0099\u0001\u001a\u00020:J\u0010\u0010\u009a\u0001\u001a\u00020/2\u0007\u0010\u009b\u0001\u001a\u00020:R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010(\u001a\u0004\u0018\u00010)X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-¨\u0006\u009e\u0001"}, d2 = {"Lcom/thor/app/settings/Settings;", "", "()V", "LANGUAGE_CODE_DE", "", "LANGUAGE_CODE_EN", "LANGUAGE_CODE_RU", "PREF_ACCESS_TOKEN", "PREF_ACCESS_TOKEN_GOOGLE", "PREF_AUTH_GOOGLE_USER_ID", "PREF_BLE_DEVICE_MAC_ADDRESS", "PREF_CAR_FUEL_TYPE", "PREF_CURRENT_LOCALE", "PREF_DEVICE_STATUS", "PREF_DRIVE_SELECT_TIP", "PREF_FIRMWARE_PROFILE", "PREF_HARDWARE_PROFILE", "PREF_IDLE_VOLUME_TIP", "PREF_IS_CHECKED_EMERGENCY_SITUATIONS", "PREF_IS_DEVICE_HAS_CAN_FILE_DATA", "PREF_IS_DEVICE_LOCKED", "PREF_IS_FORMATTED_FLASH", "PREF_IS_LAUNCH_APP", "PREF_IS_LOADING_SOUND_PACKAGE", "PREF_IS_LOAD_SOUND_WAS_INTERRUPTED", "PREF_IS_SING_UP", "PREF_IS_SUBSCRIPTION_ACTIVE", "PREF_IS_UPDATED_FIRMWARE", "PREF_LANGUAGE_CODE", "PREF_LAST_REFRESH_LOGS_TIME", "PREF_NATIVE_CONTROL_TIP", "PREF_PRESETS_INSTALL", "PREF_PROFILE", "PREF_SELECTED_SERVER", "PREF_SGU_SOUNDS_FAVORITES", "PREF_SGU_SOUND_PACKAGE_PARCEL", "PREF_SOUND_PACKAGE_PARCEL", "PREF_SUBSCRIPTION_REMINDER", "PREF_UPDATE_FIRMWARE", "PREF_USER_ID", "presets", "Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "getPresets", "()Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;", "setPresets", "(Lcom/thor/businessmodule/bluetooth/model/other/InstalledPresets;)V", "clearCookies", "", "context", "Landroid/content/Context;", "getAccessToken", "getCarFuelType", "", "getCurrentLocale", "getDeviceMacAddress", "getDeviceStatus", "Lcom/thor/businessmodule/bluetooth/model/other/settings/DeviceStatus;", "getDriveSelectTipState", "", "getFirmwareProfile", "Lcom/thor/networkmodule/model/firmware/FirmwareProfile;", "getHardwareProfile", "Lcom/thor/businessmodule/bluetooth/model/other/HardwareProfile;", "getIdleVolumeTipState", "getInstallPresets", "getIsCheckedEmrgencySituations", "getIsNeedToUpdateFirmware", "getLanguageCode", "getLastRefreshLogsTime", "", "getNativeControlTipState", "getProfile", "Lcom/thor/networkmodule/model/responses/SignInResponse;", "getSelectedServer", "getSguSoundFavoritesJSON", "getSguSoundPackageParcel", "Lcom/thor/networkmodule/model/responses/sgu/SguSoundSet;", "getSignUp", "getSoundPackageParcel", "Lcom/thor/networkmodule/model/shop/ShopSoundPackage;", "getUserGoogleToken", "getUserGoogleUserId", "getUserId", "isAccessSession", "isDeviceHasCanFileData", "isDeviceLocked", "isFormattedFlash", "isLaunchedApp", "isLoadSoundInterrupted", "isLoadingSoundPackage", "isSubscriptionActive", "isSubscriptionNeedsRemind", "daysPassed", "isUpdatedFirmware", "removeAllProperties", "removeDeviceMacAddress", "removeProperty", "property", "resetSubscriptionRemindDate", "saveAccessToken", "token", "saveCarFuelType", "fuelId", "saveCurrentLocale", Configurable.LOCALE_KEY, "Ljava/util/Locale;", "saveDeviceHasCanFileData", "hasCanFileData", "saveDeviceLockState", "locked", "saveDeviceMacAddress", "macAddress", "saveDeviceStatus", "model", "saveDriveSelectTipState", "actived", "saveFirmwareProfile", Scopes.PROFILE, "saveFormattedFlash", "isFormatted", "saveHardwareProfile", "saveIdleVolumeTipState", "saveInstallPresets", "saveIsCheckedEmrgencySituations", "isChecked", "saveIsNeedToUpdateFirmware", "state", "saveLanguageCode", "languageCode", "saveLastRefreshLogsTime", "saveLaunchApp", "isLaunched", "saveLoadSoundInterrupted", "isInterrupted", "saveLoadingSoundPackage", "isLoading", "saveNativeControlTipState", "saveProfile", "saveSelectedServer", "selectedServerId", "saveSguSoundFavoritesJSON", "sguSoundFavorites", "saveSguSoundPackageParcel", "soundSet", "saveSoundPackageParcel", "shopSoundPackage", "saveSubscriptionActive", AppMeasurementSdk.ConditionalUserProperty.ACTIVE, "saveUserGoogleToken", "saveUserGoogleUserId", "userId", "saveUserId", "setSignUp", "isSignUp", "setUpdatedFirmware", "bool", "LanguageCode", "Property", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class Settings {
    public static final Settings INSTANCE = new Settings();
    public static final String LANGUAGE_CODE_DE = "de";
    public static final String LANGUAGE_CODE_EN = "en";
    public static final String LANGUAGE_CODE_RU = "ru";
    public static final String PREF_ACCESS_TOKEN = "pref_access_token";
    public static final String PREF_ACCESS_TOKEN_GOOGLE = "pref_access_token_google";
    public static final String PREF_AUTH_GOOGLE_USER_ID = "pref_google_user_id";
    public static final String PREF_BLE_DEVICE_MAC_ADDRESS = "pref_ble_device_mac_address";
    public static final String PREF_CAR_FUEL_TYPE = "pref_car_fuel_type";
    public static final String PREF_CURRENT_LOCALE = "pref_current_locale";
    public static final String PREF_DEVICE_STATUS = "pref_device_status";
    public static final String PREF_DRIVE_SELECT_TIP = "pref_drive_select_tip";
    public static final String PREF_FIRMWARE_PROFILE = "pref_firmware_profile";
    public static final String PREF_HARDWARE_PROFILE = "pref_hardware_profile";
    public static final String PREF_IDLE_VOLUME_TIP = "pref_idle_volume_tip";
    public static final String PREF_IS_CHECKED_EMERGENCY_SITUATIONS = "pref_is_checked_emergency_situations";
    public static final String PREF_IS_DEVICE_HAS_CAN_FILE_DATA = "pref_is_device_has_can_file_data";
    public static final String PREF_IS_DEVICE_LOCKED = "pref_is_device_locked";
    public static final String PREF_IS_FORMATTED_FLASH = "pref_is_formatted_flash";
    public static final String PREF_IS_LAUNCH_APP = "pref_is_launch_app";
    public static final String PREF_IS_LOADING_SOUND_PACKAGE = "pref_is_loading_sound_package";
    public static final String PREF_IS_LOAD_SOUND_WAS_INTERRUPTED = "pref_is_load_sound_was_interrupted";
    public static final String PREF_IS_SING_UP = "pref_is_sign_up";
    public static final String PREF_IS_SUBSCRIPTION_ACTIVE = "pref_is_subscription_active";
    public static final String PREF_IS_UPDATED_FIRMWARE = "pref_is_updated_firmware";
    public static final String PREF_LANGUAGE_CODE = "pref_language_code";
    public static final String PREF_LAST_REFRESH_LOGS_TIME = "pref_last_refresh_logs_time";
    public static final String PREF_NATIVE_CONTROL_TIP = "pref_native_control_tip";
    public static final String PREF_PRESETS_INSTALL = "pref_presets_install";
    public static final String PREF_PROFILE = "pref_profile";
    public static final String PREF_SELECTED_SERVER = "pref_selected_server";
    public static final String PREF_SGU_SOUNDS_FAVORITES = "pref_sgu_sound_favorites";
    public static final String PREF_SGU_SOUND_PACKAGE_PARCEL = "pref_sgu_sound_package_parcel";
    public static final String PREF_SOUND_PACKAGE_PARCEL = "pref_sound_package_parcel";
    public static final String PREF_SUBSCRIPTION_REMINDER = "pref_subscription_reminder";
    public static final String PREF_UPDATE_FIRMWARE = "pref_update_firmware";
    public static final String PREF_USER_ID = "pref_user_id";
    private static InstalledPresets presets;

    /* compiled from: Settings.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lcom/thor/app/settings/Settings$LanguageCode;", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface LanguageCode {
    }

    /* compiled from: Settings.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lcom/thor/app/settings/Settings$Property;", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface Property {
    }

    private Settings() {
    }

    public final InstalledPresets getPresets() {
        return presets;
    }

    public final void setPresets(InstalledPresets installedPresets) {
        presets = installedPresets;
    }

    @JvmStatic
    public static final String getAccessToken() {
        Object obj = Hawk.get(PREF_ACCESS_TOKEN, "");
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_ACCESS_TOKEN, \"\")");
        return (String) obj;
    }

    @JvmStatic
    public static final void saveAccessToken(String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        Hawk.put(PREF_ACCESS_TOKEN, token);
    }

    @JvmStatic
    public static final void saveUserGoogleUserId(String userId) {
        Intrinsics.checkNotNullParameter(userId, "userId");
        Hawk.put(PREF_AUTH_GOOGLE_USER_ID, userId);
    }

    @JvmStatic
    public static final String getUserGoogleUserId() {
        Object obj = Hawk.get(PREF_AUTH_GOOGLE_USER_ID, "");
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_AUTH_GOOGLE_USER_ID, \"\")");
        return (String) obj;
    }

    @JvmStatic
    public static final void saveUserGoogleToken(String token) {
        Intrinsics.checkNotNullParameter(token, "token");
        Hawk.put(PREF_ACCESS_TOKEN_GOOGLE, token);
    }

    @JvmStatic
    public static final String getUserGoogleToken() {
        Object obj = Hawk.get(PREF_ACCESS_TOKEN_GOOGLE, "");
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_ACCESS_TOKEN_GOOGLE, \"\")");
        return (String) obj;
    }

    public final boolean isAccessSession() {
        return Hawk.contains(PREF_ACCESS_TOKEN) && !TextUtils.isEmpty(getAccessToken());
    }

    @JvmStatic
    public static final boolean getIsCheckedEmrgencySituations() {
        if (!Hawk.contains(PREF_IS_CHECKED_EMERGENCY_SITUATIONS)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_CHECKED_EMERGENCY_SITUATIONS, false);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_CHECKED_EMERGENCY_SITUATIONS, false)");
        return ((Boolean) obj).booleanValue();
    }

    @JvmStatic
    public static final void saveIsCheckedEmrgencySituations(boolean isChecked) {
        Hawk.put(PREF_IS_CHECKED_EMERGENCY_SITUATIONS, Boolean.valueOf(isChecked));
    }

    @JvmStatic
    public static final String getLanguageCode() {
        Timber.INSTANCE.i("HAWK Get Language: %s", Hawk.get(PREF_LANGUAGE_CODE, LANGUAGE_CODE_EN));
        Object obj = Hawk.get(PREF_LANGUAGE_CODE, LANGUAGE_CODE_EN);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_LANGUAGE_CODE, \"en\")");
        return (String) obj;
    }

    public final void saveLanguageCode(String languageCode) {
        Intrinsics.checkNotNullParameter(languageCode, "languageCode");
        Timber.INSTANCE.i("HAWK Set language: %s", languageCode);
        Hawk.put(PREF_LANGUAGE_CODE, languageCode);
    }

    @JvmStatic
    public static final int getUserId() {
        Object obj = Hawk.get(PREF_USER_ID, 0);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_USER_ID, 0)");
        return ((Number) obj).intValue();
    }

    @JvmStatic
    public static final void saveUserId(int userId) {
        Hawk.put(PREF_USER_ID, Integer.valueOf(userId));
    }

    @JvmStatic
    public static final void saveProfile(SignInResponse profile) {
        if (profile == null) {
            return;
        }
        Hawk.put(PREF_PROFILE, new Gson().toJson(profile));
    }

    public final SignInResponse getProfile() {
        if (!Hawk.contains(PREF_PROFILE)) {
            return null;
        }
        String str = (String) Hawk.get(PREF_PROFILE, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (SignInResponse) new Gson().fromJson(str, SignInResponse.class);
    }

    public final String getDeviceMacAddress() {
        Object obj = Hawk.get(PREF_BLE_DEVICE_MAC_ADDRESS, "");
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_BLE_DEVICE_MAC_ADDRESS, \"\")");
        return (String) obj;
    }

    public final void saveDeviceMacAddress(String macAddress) {
        if (TextUtils.isEmpty(macAddress)) {
            return;
        }
        Hawk.put(PREF_BLE_DEVICE_MAC_ADDRESS, macAddress);
    }

    public final void setSignUp(boolean isSignUp) {
        Hawk.put(PREF_IS_SING_UP, Boolean.valueOf(isSignUp));
        Timber.INSTANCE.i("PREF_IS_SING_UP = " + getSignUp(), new Object[0]);
    }

    public final boolean getSignUp() {
        if (!Hawk.contains(PREF_IS_SING_UP)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_SING_UP);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_SING_UP)");
        return ((Boolean) obj).booleanValue();
    }

    public final void saveFirmwareProfile(FirmwareProfile profile) {
        if (profile == null) {
            return;
        }
        Hawk.put(PREF_FIRMWARE_PROFILE, new Gson().toJson(profile));
    }

    public final FirmwareProfile getFirmwareProfile() {
        if (!Hawk.contains(PREF_FIRMWARE_PROFILE)) {
            return null;
        }
        String str = (String) Hawk.get(PREF_FIRMWARE_PROFILE, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (FirmwareProfile) new Gson().fromJson(str, FirmwareProfile.class);
    }

    public final void saveDeviceStatus(DeviceStatus model) {
        if (model == null) {
            return;
        }
        Hawk.put(PREF_DEVICE_STATUS, new Gson().toJson(model));
    }

    public final DeviceStatus getDeviceStatus() {
        if (!Hawk.contains(PREF_DEVICE_STATUS)) {
            return null;
        }
        String str = (String) Hawk.get(PREF_DEVICE_STATUS, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (DeviceStatus) new Gson().fromJson(str, DeviceStatus.class);
    }

    public final void saveInstallPresets(InstalledPresets presets2) {
        Intrinsics.checkNotNullParameter(presets2, "presets");
        Hawk.put(PREF_PRESETS_INSTALL, new Gson().toJson(presets2));
    }

    public final InstalledPresets getInstallPresets() {
        if (!Hawk.contains(PREF_PRESETS_INSTALL)) {
            return null;
        }
        String profileJson = (String) Hawk.get(PREF_PRESETS_INSTALL, "");
        Intrinsics.checkNotNullExpressionValue(profileJson, "profileJson");
        if (profileJson.length() > 0) {
            return (InstalledPresets) new Gson().fromJson(profileJson, InstalledPresets.class);
        }
        return null;
    }

    @JvmStatic
    public static final void saveHardwareProfile(HardwareProfile profile) {
        if (profile == null) {
            return;
        }
        Hawk.put(PREF_HARDWARE_PROFILE, new Gson().toJson(profile));
    }

    @JvmStatic
    public static final HardwareProfile getHardwareProfile() {
        if (!Hawk.contains(PREF_HARDWARE_PROFILE)) {
            return null;
        }
        String str = (String) Hawk.get(PREF_HARDWARE_PROFILE, "");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (HardwareProfile) new Gson().fromJson(str, HardwareProfile.class);
    }

    public final boolean isFormattedFlash() {
        return Boolean.parseBoolean((String) Hawk.get(PREF_IS_FORMATTED_FLASH, BooleanUtils.FALSE));
    }

    public final void saveFormattedFlash(boolean isFormatted) {
        Hawk.put(PREF_IS_FORMATTED_FLASH, String.valueOf(isFormatted));
    }

    public final boolean isLaunchedApp() {
        if (!Hawk.contains(PREF_IS_LAUNCH_APP)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_LAUNCH_APP);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_LAUNCH_APP)");
        return ((Boolean) obj).booleanValue();
    }

    public final void saveSelectedServer(int selectedServerId) {
        Hawk.put(PREF_SELECTED_SERVER, Integer.valueOf(selectedServerId));
    }

    @JvmStatic
    public static final int getSelectedServer() {
        if (!Hawk.contains(PREF_SELECTED_SERVER)) {
            return 0;
        }
        Object obj = Hawk.get(PREF_SELECTED_SERVER);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_SELECTED_SERVER)");
        return ((Number) obj).intValue();
    }

    public final void saveLaunchApp(boolean isLaunched) {
        Hawk.put(PREF_IS_LAUNCH_APP, Boolean.valueOf(isLaunched));
    }

    public final void saveLoadingSoundPackage(boolean isLoading) {
        Hawk.put(PREF_IS_LOADING_SOUND_PACKAGE, Boolean.valueOf(isLoading));
    }

    public final boolean isLoadingSoundPackage() {
        if (!Hawk.contains(PREF_IS_LOADING_SOUND_PACKAGE)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_LOADING_SOUND_PACKAGE);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_LOADING_SOUND_PACKAGE)");
        return ((Boolean) obj).booleanValue();
    }

    public final boolean isUpdatedFirmware() {
        if (!Hawk.contains(PREF_IS_UPDATED_FIRMWARE)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_UPDATED_FIRMWARE);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_UPDATED_FIRMWARE)");
        return ((Boolean) obj).booleanValue();
    }

    public final void setUpdatedFirmware(boolean bool) {
        Hawk.put(PREF_IS_UPDATED_FIRMWARE, Boolean.valueOf(bool));
    }

    public final void saveLastRefreshLogsTime() {
        Hawk.put(PREF_LAST_REFRESH_LOGS_TIME, Long.valueOf(System.currentTimeMillis()));
    }

    public final long getLastRefreshLogsTime() {
        if (!Hawk.contains(PREF_LAST_REFRESH_LOGS_TIME)) {
            return 0L;
        }
        Object obj = Hawk.get(PREF_LAST_REFRESH_LOGS_TIME);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_LAST_REFRESH_LOGS_TIME)");
        return ((Number) obj).longValue();
    }

    public final void saveLoadSoundInterrupted(boolean isInterrupted) {
        Hawk.put(PREF_IS_LOAD_SOUND_WAS_INTERRUPTED, Boolean.valueOf(isInterrupted));
    }

    public final boolean isLoadSoundInterrupted() {
        if (!Hawk.contains(PREF_IS_LOAD_SOUND_WAS_INTERRUPTED)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_LOAD_SOUND_WAS_INTERRUPTED);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_LOAD_SOUND_WAS_INTERRUPTED)");
        return ((Boolean) obj).booleanValue();
    }

    public final void saveSoundPackageParcel(ShopSoundPackage shopSoundPackage) {
        Intrinsics.checkNotNullParameter(shopSoundPackage, "shopSoundPackage");
        Hawk.put(PREF_SOUND_PACKAGE_PARCEL, shopSoundPackage);
    }

    public final ShopSoundPackage getSoundPackageParcel() {
        if (Hawk.contains(PREF_SOUND_PACKAGE_PARCEL)) {
            return (ShopSoundPackage) Hawk.get(PREF_SOUND_PACKAGE_PARCEL);
        }
        return null;
    }

    public final void saveSguSoundPackageParcel(SguSoundSet soundSet) {
        Intrinsics.checkNotNullParameter(soundSet, "soundSet");
        Hawk.put(PREF_SGU_SOUND_PACKAGE_PARCEL, soundSet);
    }

    public final SguSoundSet getSguSoundPackageParcel() {
        if (Hawk.contains(PREF_SGU_SOUND_PACKAGE_PARCEL)) {
            return (SguSoundSet) Hawk.get(PREF_SGU_SOUND_PACKAGE_PARCEL);
        }
        return null;
    }

    public final void saveDeviceHasCanFileData(boolean hasCanFileData) {
        Log.i("DeviceStatus1", "saveDeviceHasCanFileData: " + hasCanFileData);
        Hawk.put(PREF_IS_DEVICE_HAS_CAN_FILE_DATA, Boolean.valueOf(hasCanFileData));
    }

    public final boolean isDeviceHasCanFileData() {
        if (!Hawk.contains(PREF_IS_DEVICE_HAS_CAN_FILE_DATA)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_DEVICE_HAS_CAN_FILE_DATA);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_DEVICE_HAS_CAN_FILE_DATA)");
        return ((Boolean) obj).booleanValue();
    }

    public final void saveDeviceLockState(boolean locked) {
        Hawk.put(PREF_IS_DEVICE_LOCKED, Boolean.valueOf(locked));
    }

    public final boolean isDeviceLocked() {
        if (!Hawk.contains(PREF_IS_DEVICE_LOCKED)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_DEVICE_LOCKED);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_DEVICE_LOCKED)");
        return ((Boolean) obj).booleanValue();
    }

    public final void saveSubscriptionActive(boolean active) {
        Hawk.put(PREF_IS_SUBSCRIPTION_ACTIVE, Boolean.valueOf(active));
    }

    public final boolean isSubscriptionActive() {
        if (!Hawk.contains(PREF_IS_SUBSCRIPTION_ACTIVE)) {
            return false;
        }
        Object obj = Hawk.get(PREF_IS_SUBSCRIPTION_ACTIVE);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IS_SUBSCRIPTION_ACTIVE)");
        return ((Boolean) obj).booleanValue();
    }

    public final boolean isSubscriptionNeedsRemind(int daysPassed) {
        Object obj = Hawk.get(PREF_SUBSCRIPTION_REMINDER, 0L);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_SUBSCRIPTION_REMINDER, 0L)");
        return System.currentTimeMillis() - ((Number) obj).longValue() >= TimeUnit.DAYS.toMillis((long) daysPassed);
    }

    public final void resetSubscriptionRemindDate() {
        Hawk.put(PREF_SUBSCRIPTION_REMINDER, Long.valueOf(System.currentTimeMillis()));
    }

    public final int getCarFuelType() {
        Object obj = Hawk.get(PREF_CAR_FUEL_TYPE, 0);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_CAR_FUEL_TYPE, 0)");
        return ((Number) obj).intValue();
    }

    public final void saveCarFuelType(int fuelId) {
        Hawk.put(PREF_CAR_FUEL_TYPE, Integer.valueOf(fuelId));
    }

    public final void removeProperty(String property) {
        Intrinsics.checkNotNullParameter(property, "property");
        Hawk.delete(property);
    }

    public final void saveDriveSelectTipState(boolean actived) {
        Hawk.put(PREF_DRIVE_SELECT_TIP, Boolean.valueOf(actived));
    }

    public final void saveNativeControlTipState(boolean actived) {
        Hawk.put(PREF_NATIVE_CONTROL_TIP, Boolean.valueOf(actived));
    }

    public final void saveIdleVolumeTipState(boolean actived) {
        Hawk.put(PREF_IDLE_VOLUME_TIP, Boolean.valueOf(actived));
    }

    public final boolean getDriveSelectTipState() {
        Object obj = Hawk.get(PREF_DRIVE_SELECT_TIP, false);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_DRIVE_SELECT_TIP, false)");
        return ((Boolean) obj).booleanValue();
    }

    public final boolean getNativeControlTipState() {
        Object obj = Hawk.get(PREF_NATIVE_CONTROL_TIP, false);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_NATIVE_CONTROL_TIP, false)");
        return ((Boolean) obj).booleanValue();
    }

    public final boolean getIdleVolumeTipState() {
        Object obj = Hawk.get(PREF_IDLE_VOLUME_TIP, false);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_IDLE_VOLUME_TIP, false)");
        return ((Boolean) obj).booleanValue();
    }

    public final String getSguSoundFavoritesJSON() {
        Object obj = Hawk.get(PREF_SGU_SOUNDS_FAVORITES, "");
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_SGU_SOUNDS_FAVORITES, \"\")");
        return (String) obj;
    }

    public final void saveSguSoundFavoritesJSON(String sguSoundFavorites) {
        Intrinsics.checkNotNullParameter(sguSoundFavorites, "sguSoundFavorites");
        Hawk.put(PREF_SGU_SOUNDS_FAVORITES, sguSoundFavorites);
    }

    public final void saveIsNeedToUpdateFirmware(boolean state) {
        Hawk.put(PREF_UPDATE_FIRMWARE, Boolean.valueOf(state));
    }

    public final boolean getIsNeedToUpdateFirmware() {
        Object obj = Hawk.get(PREF_UPDATE_FIRMWARE, false);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_UPDATE_FIRMWARE, false)");
        return ((Boolean) obj).booleanValue();
    }

    @JvmStatic
    public static final void removeAllProperties() {
        Settings settings = INSTANCE;
        settings.removeProperty(PREF_ACCESS_TOKEN);
        settings.removeProperty(PREF_USER_ID);
        settings.removeProperty(PREF_PROFILE);
        settings.removeProperty(PREF_FIRMWARE_PROFILE);
        settings.removeProperty(PREF_HARDWARE_PROFILE);
        settings.removeProperty(PREF_BLE_DEVICE_MAC_ADDRESS);
        settings.removeProperty(PREF_IS_FORMATTED_FLASH);
        settings.removeProperty(PREF_IS_LAUNCH_APP);
        settings.removeProperty(PREF_IS_LOAD_SOUND_WAS_INTERRUPTED);
        settings.removeProperty(PREF_SOUND_PACKAGE_PARCEL);
        settings.removeProperty(PREF_SGU_SOUND_PACKAGE_PARCEL);
        settings.removeProperty(PREF_IS_DEVICE_HAS_CAN_FILE_DATA);
        settings.removeProperty(PREF_IS_DEVICE_LOCKED);
        settings.removeProperty(PREF_SUBSCRIPTION_REMINDER);
    }

    @JvmStatic
    public static final void removeDeviceMacAddress() {
        INSTANCE.removeProperty(PREF_BLE_DEVICE_MAC_ADDRESS);
    }

    @JvmStatic
    public static final void clearCookies(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    public final void saveCurrentLocale(Locale locale) {
        if (Intrinsics.areEqual(locale, new Locale(LANGUAGE_CODE_RU))) {
            Hawk.put(PREF_CURRENT_LOCALE, LANGUAGE_CODE_RU);
            return;
        }
        if (Intrinsics.areEqual(locale, new Locale(LANGUAGE_CODE_EN))) {
            Hawk.put(PREF_CURRENT_LOCALE, LANGUAGE_CODE_EN);
        } else if (Intrinsics.areEqual(locale, new Locale(LANGUAGE_CODE_DE))) {
            Hawk.put(PREF_CURRENT_LOCALE, LANGUAGE_CODE_DE);
        } else {
            Hawk.put(PREF_CURRENT_LOCALE, LANGUAGE_CODE_EN);
        }
    }

    public final String getCurrentLocale() {
        Object obj = Hawk.get(PREF_CURRENT_LOCALE, LANGUAGE_CODE_EN);
        Intrinsics.checkNotNullExpressionValue(obj, "get(PREF_CURRENT_LOCALE, \"en\")");
        return (String) obj;
    }
}
