package com.carsystems.thor.app;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.carsystems.thor.app.databinding.ActivityAddPresetBindingImpl;
import com.carsystems.thor.app.databinding.ActivityAddSguPresetBindingImpl;
import com.carsystems.thor.app.databinding.ActivityBarcodeCaptureBindingImpl;
import com.carsystems.thor.app.databinding.ActivityBluetoothDevicesBindingImpl;
import com.carsystems.thor.app.databinding.ActivityChangeCarBindingImpl;
import com.carsystems.thor.app.databinding.ActivityDemoBindingImpl;
import com.carsystems.thor.app.databinding.ActivityDemoPresetSoundSettingsBindingImpl;
import com.carsystems.thor.app.databinding.ActivityDemoShopBindingImpl;
import com.carsystems.thor.app.databinding.ActivityDemoSoundPackageDescriptionOldBindingImpl;
import com.carsystems.thor.app.databinding.ActivityFirmwareListBindingImpl;
import com.carsystems.thor.app.databinding.ActivityGoogleAuthBindingImpl;
import com.carsystems.thor.app.databinding.ActivityLockedDeviceBindingImpl;
import com.carsystems.thor.app.databinding.ActivityMainBindingImpl;
import com.carsystems.thor.app.databinding.ActivityNotificationsBindingImpl;
import com.carsystems.thor.app.databinding.ActivityPresetSoundSettingsBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySettingsBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySguSoundPackageDescriptionBindingImpl;
import com.carsystems.thor.app.databinding.ActivityShopBindingImpl;
import com.carsystems.thor.app.databinding.ActivityShopOldBindingImpl;
import com.carsystems.thor.app.databinding.ActivityShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySignUpAddDeviceBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySignUpBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySignUpCarInfoBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySoundPackageDescriptionBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySplashBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySubscriptionBindingImpl;
import com.carsystems.thor.app.databinding.ActivitySupportBindingImpl;
import com.carsystems.thor.app.databinding.ActivityTestBindingImpl;
import com.carsystems.thor.app.databinding.ActivityUpdateAppBindingImpl;
import com.carsystems.thor.app.databinding.DialogFragmentCarInfoBindingImpl;
import com.carsystems.thor.app.databinding.DialogFragmentSimpleBindingImpl;
import com.carsystems.thor.app.databinding.DialogFragmentTestersBindingImpl;
import com.carsystems.thor.app.databinding.DialogFragmentTipBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDemoBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDemoPresetSoundSettingsBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDemoSguBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDialogDownloadPackageBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDialogFormatProgressBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDialogUpdateFirmwareBindingImpl;
import com.carsystems.thor.app.databinding.FragmentDialogUploadSguSoundSetBindingImpl;
import com.carsystems.thor.app.databinding.FragmentFirmwareListBindingImpl;
import com.carsystems.thor.app.databinding.FragmentMainShopBindingImpl;
import com.carsystems.thor.app.databinding.FragmentMainSoundsBindingImpl;
import com.carsystems.thor.app.databinding.FragmentNotificationOverviewBindingImpl;
import com.carsystems.thor.app.databinding.FragmentNotificationsBindingImpl;
import com.carsystems.thor.app.databinding.FragmentPresetSoundSettingsBindingImpl;
import com.carsystems.thor.app.databinding.FragmentSguShopBindingImpl;
import com.carsystems.thor.app.databinding.FragmentSguSoundsBindingImpl;
import com.carsystems.thor.app.databinding.ItemBluetoothDeviceBindingImpl;
import com.carsystems.thor.app.databinding.ItemDemoEvDividerBindingImpl;
import com.carsystems.thor.app.databinding.ItemDemoShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ItemDemoSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ItemListFirmwareInfoBindingImpl;
import com.carsystems.thor.app.databinding.ItemMainSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ItemNotificationBindingImpl;
import com.carsystems.thor.app.databinding.ItemSguShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ItemSguSoundConfigBindingImpl;
import com.carsystems.thor.app.databinding.ItemSguSoundFavBindingImpl;
import com.carsystems.thor.app.databinding.ItemSguSoundPreviewBindingImpl;
import com.carsystems.thor.app.databinding.ItemShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ItemShopSubscriptionCardBindingImpl;
import com.carsystems.thor.app.databinding.ItemSubscriptionPackBindingImpl;
import com.carsystems.thor.app.databinding.ItemTachometerWithEqualizeBindingImpl;
import com.carsystems.thor.app.databinding.LayoutMainMenuBindingImpl;
import com.carsystems.thor.app.databinding.LayoutSubscriptionPlanOptionBindingImpl;
import com.carsystems.thor.app.databinding.VehicleDialogLayoutBindingImpl;
import com.carsystems.thor.app.databinding.ViewDemoSguSoundConfigBindingImpl;
import com.carsystems.thor.app.databinding.ViewDemoShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ViewDemoSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ViewEqualizeBindingImpl;
import com.carsystems.thor.app.databinding.ViewMainSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ViewRssiSignalBindingImpl;
import com.carsystems.thor.app.databinding.ViewSguShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ViewSguSoundConfigBindingImpl;
import com.carsystems.thor.app.databinding.ViewSguSoundFavBindingImpl;
import com.carsystems.thor.app.databinding.ViewShopModeSwitchBindingImpl;
import com.carsystems.thor.app.databinding.ViewShopSoundPackageBindingImpl;
import com.carsystems.thor.app.databinding.ViewSoundPresetBindingImpl;
import com.carsystems.thor.app.databinding.ViewTachometerBindingImpl;
import com.carsystems.thor.app.databinding.ViewTachometerWithEqualizeBindingImpl;
import com.carsystems.thor.app.databinding.WidgetToolbarBindingImpl;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYADDPRESET = 1;
    private static final int LAYOUT_ACTIVITYADDSGUPRESET = 2;
    private static final int LAYOUT_ACTIVITYBARCODECAPTURE = 3;
    private static final int LAYOUT_ACTIVITYBLUETOOTHDEVICES = 4;
    private static final int LAYOUT_ACTIVITYCHANGECAR = 5;
    private static final int LAYOUT_ACTIVITYDEMO = 6;
    private static final int LAYOUT_ACTIVITYDEMOPRESETSOUNDSETTINGS = 7;
    private static final int LAYOUT_ACTIVITYDEMOSHOP = 8;
    private static final int LAYOUT_ACTIVITYDEMOSOUNDPACKAGEDESCRIPTIONOLD = 9;
    private static final int LAYOUT_ACTIVITYFIRMWARELIST = 10;
    private static final int LAYOUT_ACTIVITYGOOGLEAUTH = 11;
    private static final int LAYOUT_ACTIVITYLOCKEDDEVICE = 12;
    private static final int LAYOUT_ACTIVITYMAIN = 13;
    private static final int LAYOUT_ACTIVITYNOTIFICATIONS = 14;
    private static final int LAYOUT_ACTIVITYPRESETSOUNDSETTINGS = 15;
    private static final int LAYOUT_ACTIVITYSETTINGS = 16;
    private static final int LAYOUT_ACTIVITYSGUSOUNDPACKAGEDESCRIPTION = 17;
    private static final int LAYOUT_ACTIVITYSHOP = 18;
    private static final int LAYOUT_ACTIVITYSHOPOLD = 19;
    private static final int LAYOUT_ACTIVITYSHOPSOUNDPACKAGE = 20;
    private static final int LAYOUT_ACTIVITYSIGNUP = 21;
    private static final int LAYOUT_ACTIVITYSIGNUPADDDEVICE = 22;
    private static final int LAYOUT_ACTIVITYSIGNUPCARINFO = 23;
    private static final int LAYOUT_ACTIVITYSOUNDPACKAGEDESCRIPTION = 24;
    private static final int LAYOUT_ACTIVITYSPLASH = 25;
    private static final int LAYOUT_ACTIVITYSUBSCRIPTION = 26;
    private static final int LAYOUT_ACTIVITYSUPPORT = 27;
    private static final int LAYOUT_ACTIVITYTEST = 28;
    private static final int LAYOUT_ACTIVITYUPDATEAPP = 29;
    private static final int LAYOUT_DIALOGFRAGMENTCARINFO = 30;
    private static final int LAYOUT_DIALOGFRAGMENTSIMPLE = 31;
    private static final int LAYOUT_DIALOGFRAGMENTTESTERS = 32;
    private static final int LAYOUT_DIALOGFRAGMENTTIP = 33;
    private static final int LAYOUT_FRAGMENTDEMO = 34;
    private static final int LAYOUT_FRAGMENTDEMOPRESETSOUNDSETTINGS = 35;
    private static final int LAYOUT_FRAGMENTDEMOSGU = 36;
    private static final int LAYOUT_FRAGMENTDIALOGDOWNLOADPACKAGE = 37;
    private static final int LAYOUT_FRAGMENTDIALOGFORMATPROGRESS = 38;
    private static final int LAYOUT_FRAGMENTDIALOGUPDATEFIRMWARE = 39;
    private static final int LAYOUT_FRAGMENTDIALOGUPLOADSGUSOUNDSET = 40;
    private static final int LAYOUT_FRAGMENTFIRMWARELIST = 41;
    private static final int LAYOUT_FRAGMENTMAINSHOP = 42;
    private static final int LAYOUT_FRAGMENTMAINSOUNDS = 43;
    private static final int LAYOUT_FRAGMENTNOTIFICATIONOVERVIEW = 44;
    private static final int LAYOUT_FRAGMENTNOTIFICATIONS = 45;
    private static final int LAYOUT_FRAGMENTPRESETSOUNDSETTINGS = 46;
    private static final int LAYOUT_FRAGMENTSGUSHOP = 47;
    private static final int LAYOUT_FRAGMENTSGUSOUNDS = 48;
    private static final int LAYOUT_ITEMBLUETOOTHDEVICE = 49;
    private static final int LAYOUT_ITEMDEMOEVDIVIDER = 50;
    private static final int LAYOUT_ITEMDEMOSHOPSOUNDPACKAGE = 51;
    private static final int LAYOUT_ITEMDEMOSOUNDPACKAGE = 52;
    private static final int LAYOUT_ITEMLISTFIRMWAREINFO = 53;
    private static final int LAYOUT_ITEMMAINSOUNDPACKAGE = 54;
    private static final int LAYOUT_ITEMNOTIFICATION = 55;
    private static final int LAYOUT_ITEMSGUSHOPSOUNDPACKAGE = 56;
    private static final int LAYOUT_ITEMSGUSOUNDCONFIG = 57;
    private static final int LAYOUT_ITEMSGUSOUNDFAV = 58;
    private static final int LAYOUT_ITEMSGUSOUNDPREVIEW = 59;
    private static final int LAYOUT_ITEMSHOPSOUNDPACKAGE = 60;
    private static final int LAYOUT_ITEMSHOPSUBSCRIPTIONCARD = 61;
    private static final int LAYOUT_ITEMSUBSCRIPTIONPACK = 62;
    private static final int LAYOUT_ITEMTACHOMETERWITHEQUALIZE = 63;
    private static final int LAYOUT_LAYOUTMAINMENU = 64;
    private static final int LAYOUT_LAYOUTSUBSCRIPTIONPLANOPTION = 65;
    private static final int LAYOUT_VEHICLEDIALOGLAYOUT = 66;
    private static final int LAYOUT_VIEWDEMOSGUSOUNDCONFIG = 67;
    private static final int LAYOUT_VIEWDEMOSHOPSOUNDPACKAGE = 68;
    private static final int LAYOUT_VIEWDEMOSOUNDPACKAGE = 69;
    private static final int LAYOUT_VIEWEQUALIZE = 70;
    private static final int LAYOUT_VIEWMAINSOUNDPACKAGE = 71;
    private static final int LAYOUT_VIEWRSSISIGNAL = 72;
    private static final int LAYOUT_VIEWSGUSHOPSOUNDPACKAGE = 73;
    private static final int LAYOUT_VIEWSGUSOUNDCONFIG = 74;
    private static final int LAYOUT_VIEWSGUSOUNDFAV = 75;
    private static final int LAYOUT_VIEWSHOPMODESWITCH = 76;
    private static final int LAYOUT_VIEWSHOPSOUNDPACKAGE = 77;
    private static final int LAYOUT_VIEWSOUNDPRESET = 78;
    private static final int LAYOUT_VIEWTACHOMETER = 79;
    private static final int LAYOUT_VIEWTACHOMETERWITHEQUALIZE = 80;
    private static final int LAYOUT_WIDGETTOOLBAR = 81;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(81);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.activity_add_preset, 1);
        sparseIntArray.put(R.layout.activity_add_sgu_preset, 2);
        sparseIntArray.put(R.layout.activity_barcode_capture, 3);
        sparseIntArray.put(R.layout.activity_bluetooth_devices, 4);
        sparseIntArray.put(R.layout.activity_change_car, 5);
        sparseIntArray.put(R.layout.activity_demo, 6);
        sparseIntArray.put(R.layout.activity_demo_preset_sound_settings, 7);
        sparseIntArray.put(R.layout.activity_demo_shop, 8);
        sparseIntArray.put(R.layout.activity_demo_sound_package_description_old, 9);
        sparseIntArray.put(R.layout.activity_firmware_list, 10);
        sparseIntArray.put(R.layout.activity_google_auth, 11);
        sparseIntArray.put(R.layout.activity_locked_device, 12);
        sparseIntArray.put(R.layout.activity_main, 13);
        sparseIntArray.put(R.layout.activity_notifications, 14);
        sparseIntArray.put(R.layout.activity_preset_sound_settings, 15);
        sparseIntArray.put(R.layout.activity_settings, 16);
        sparseIntArray.put(R.layout.activity_sgu_sound_package_description, 17);
        sparseIntArray.put(R.layout.activity_shop, 18);
        sparseIntArray.put(R.layout.activity_shop_old, 19);
        sparseIntArray.put(R.layout.activity_shop_sound_package, 20);
        sparseIntArray.put(R.layout.activity_sign_up, 21);
        sparseIntArray.put(R.layout.activity_sign_up_add_device, 22);
        sparseIntArray.put(R.layout.activity_sign_up_car_info, 23);
        sparseIntArray.put(R.layout.activity_sound_package_description, 24);
        sparseIntArray.put(R.layout.activity_splash, 25);
        sparseIntArray.put(R.layout.activity_subscription, 26);
        sparseIntArray.put(R.layout.activity_support, 27);
        sparseIntArray.put(R.layout.activity_test, 28);
        sparseIntArray.put(R.layout.activity_update_app, 29);
        sparseIntArray.put(R.layout.dialog_fragment_car_info, 30);
        sparseIntArray.put(R.layout.dialog_fragment_simple, 31);
        sparseIntArray.put(R.layout.dialog_fragment_testers, 32);
        sparseIntArray.put(R.layout.dialog_fragment_tip, 33);
        sparseIntArray.put(R.layout.fragment_demo, 34);
        sparseIntArray.put(R.layout.fragment_demo_preset_sound_settings, 35);
        sparseIntArray.put(R.layout.fragment_demo_sgu, 36);
        sparseIntArray.put(R.layout.fragment_dialog_download_package, 37);
        sparseIntArray.put(R.layout.fragment_dialog_format_progress, 38);
        sparseIntArray.put(R.layout.fragment_dialog_update_firmware, 39);
        sparseIntArray.put(R.layout.fragment_dialog_upload_sgu_sound_set, 40);
        sparseIntArray.put(R.layout.fragment_firmware_list, 41);
        sparseIntArray.put(R.layout.fragment_main_shop, 42);
        sparseIntArray.put(R.layout.fragment_main_sounds, 43);
        sparseIntArray.put(R.layout.fragment_notification_overview, 44);
        sparseIntArray.put(R.layout.fragment_notifications, 45);
        sparseIntArray.put(R.layout.fragment_preset_sound_settings, 46);
        sparseIntArray.put(R.layout.fragment_sgu_shop, 47);
        sparseIntArray.put(R.layout.fragment_sgu_sounds, 48);
        sparseIntArray.put(R.layout.item_bluetooth_device, 49);
        sparseIntArray.put(R.layout.item_demo_ev_divider, 50);
        sparseIntArray.put(R.layout.item_demo_shop_sound_package, 51);
        sparseIntArray.put(R.layout.item_demo_sound_package, 52);
        sparseIntArray.put(R.layout.item_list_firmware_info, 53);
        sparseIntArray.put(R.layout.item_main_sound_package, 54);
        sparseIntArray.put(R.layout.item_notification, 55);
        sparseIntArray.put(R.layout.item_sgu_shop_sound_package, 56);
        sparseIntArray.put(R.layout.item_sgu_sound_config, 57);
        sparseIntArray.put(R.layout.item_sgu_sound_fav, 58);
        sparseIntArray.put(R.layout.item_sgu_sound_preview, 59);
        sparseIntArray.put(R.layout.item_shop_sound_package, 60);
        sparseIntArray.put(R.layout.item_shop_subscription_card, 61);
        sparseIntArray.put(R.layout.item_subscription_pack, 62);
        sparseIntArray.put(R.layout.item_tachometer_with_equalize, 63);
        sparseIntArray.put(R.layout.layout_main_menu, 64);
        sparseIntArray.put(R.layout.layout_subscription_plan_option, 65);
        sparseIntArray.put(R.layout.vehicle_dialog_layout, 66);
        sparseIntArray.put(R.layout.view_demo_sgu_sound_config, 67);
        sparseIntArray.put(R.layout.view_demo_shop_sound_package, 68);
        sparseIntArray.put(R.layout.view_demo_sound_package, 69);
        sparseIntArray.put(R.layout.view_equalize, 70);
        sparseIntArray.put(R.layout.view_main_sound_package, 71);
        sparseIntArray.put(R.layout.view_rssi_signal, 72);
        sparseIntArray.put(R.layout.view_sgu_shop_sound_package, 73);
        sparseIntArray.put(R.layout.view_sgu_sound_config, 74);
        sparseIntArray.put(R.layout.view_sgu_sound_fav, 75);
        sparseIntArray.put(R.layout.view_shop_mode_switch, 76);
        sparseIntArray.put(R.layout.view_shop_sound_package, 77);
        sparseIntArray.put(R.layout.view_sound_preset, 78);
        sparseIntArray.put(R.layout.view_tachometer, 79);
        sparseIntArray.put(R.layout.view_tachometer_with_equalize, 80);
        sparseIntArray.put(R.layout.widget_toolbar, 81);
    }

    private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 1:
                if ("layout/activity_add_preset_0".equals(tag)) {
                    return new ActivityAddPresetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_add_preset is invalid. Received: " + tag);
            case 2:
                if ("layout/activity_add_sgu_preset_0".equals(tag)) {
                    return new ActivityAddSguPresetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_add_sgu_preset is invalid. Received: " + tag);
            case 3:
                if ("layout/activity_barcode_capture_0".equals(tag)) {
                    return new ActivityBarcodeCaptureBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_barcode_capture is invalid. Received: " + tag);
            case 4:
                if ("layout/activity_bluetooth_devices_0".equals(tag)) {
                    return new ActivityBluetoothDevicesBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_bluetooth_devices is invalid. Received: " + tag);
            case 5:
                if ("layout/activity_change_car_0".equals(tag)) {
                    return new ActivityChangeCarBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_change_car is invalid. Received: " + tag);
            case 6:
                if ("layout/activity_demo_0".equals(tag)) {
                    return new ActivityDemoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_demo is invalid. Received: " + tag);
            case 7:
                if ("layout/activity_demo_preset_sound_settings_0".equals(tag)) {
                    return new ActivityDemoPresetSoundSettingsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_demo_preset_sound_settings is invalid. Received: " + tag);
            case 8:
                if ("layout/activity_demo_shop_0".equals(tag)) {
                    return new ActivityDemoShopBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_demo_shop is invalid. Received: " + tag);
            case 9:
                if ("layout/activity_demo_sound_package_description_old_0".equals(tag)) {
                    return new ActivityDemoSoundPackageDescriptionOldBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_demo_sound_package_description_old is invalid. Received: " + tag);
            case 10:
                if ("layout/activity_firmware_list_0".equals(tag)) {
                    return new ActivityFirmwareListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_firmware_list is invalid. Received: " + tag);
            case 11:
                if ("layout/activity_google_auth_0".equals(tag)) {
                    return new ActivityGoogleAuthBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_google_auth is invalid. Received: " + tag);
            case 12:
                if ("layout/activity_locked_device_0".equals(tag)) {
                    return new ActivityLockedDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_locked_device is invalid. Received: " + tag);
            case 13:
                if ("layout/activity_main_0".equals(tag)) {
                    return new ActivityMainBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
            case 14:
                if ("layout/activity_notifications_0".equals(tag)) {
                    return new ActivityNotificationsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_notifications is invalid. Received: " + tag);
            case 15:
                if ("layout/activity_preset_sound_settings_0".equals(tag)) {
                    return new ActivityPresetSoundSettingsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_preset_sound_settings is invalid. Received: " + tag);
            case 16:
                if ("layout/activity_settings_0".equals(tag)) {
                    return new ActivitySettingsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_settings is invalid. Received: " + tag);
            case 17:
                if ("layout/activity_sgu_sound_package_description_0".equals(tag)) {
                    return new ActivitySguSoundPackageDescriptionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_sgu_sound_package_description is invalid. Received: " + tag);
            case 18:
                if ("layout/activity_shop_0".equals(tag)) {
                    return new ActivityShopBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_shop is invalid. Received: " + tag);
            case 19:
                if ("layout/activity_shop_old_0".equals(tag)) {
                    return new ActivityShopOldBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_shop_old is invalid. Received: " + tag);
            case 20:
                if ("layout/activity_shop_sound_package_0".equals(tag)) {
                    return new ActivityShopSoundPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_shop_sound_package is invalid. Received: " + tag);
            case 21:
                if ("layout/activity_sign_up_0".equals(tag)) {
                    return new ActivitySignUpBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_sign_up is invalid. Received: " + tag);
            case 22:
                if ("layout/activity_sign_up_add_device_0".equals(tag)) {
                    return new ActivitySignUpAddDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_sign_up_add_device is invalid. Received: " + tag);
            case 23:
                if ("layout/activity_sign_up_car_info_0".equals(tag)) {
                    return new ActivitySignUpCarInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_sign_up_car_info is invalid. Received: " + tag);
            case 24:
                if ("layout/activity_sound_package_description_0".equals(tag)) {
                    return new ActivitySoundPackageDescriptionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_sound_package_description is invalid. Received: " + tag);
            case 25:
                if ("layout/activity_splash_0".equals(tag)) {
                    return new ActivitySplashBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
            case 26:
                if ("layout/activity_subscription_0".equals(tag)) {
                    return new ActivitySubscriptionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_subscription is invalid. Received: " + tag);
            case 27:
                if ("layout/activity_support_0".equals(tag)) {
                    return new ActivitySupportBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_support is invalid. Received: " + tag);
            case 28:
                if ("layout/activity_test_0".equals(tag)) {
                    return new ActivityTestBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_test is invalid. Received: " + tag);
            case 29:
                if ("layout/activity_update_app_0".equals(tag)) {
                    return new ActivityUpdateAppBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_update_app is invalid. Received: " + tag);
            case 30:
                if ("layout/dialog_fragment_car_info_0".equals(tag)) {
                    return new DialogFragmentCarInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_fragment_car_info is invalid. Received: " + tag);
            case 31:
                if ("layout/dialog_fragment_simple_0".equals(tag)) {
                    return new DialogFragmentSimpleBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_fragment_simple is invalid. Received: " + tag);
            case 32:
                if ("layout/dialog_fragment_testers_0".equals(tag)) {
                    return new DialogFragmentTestersBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_fragment_testers is invalid. Received: " + tag);
            case 33:
                if ("layout/dialog_fragment_tip_0".equals(tag)) {
                    return new DialogFragmentTipBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for dialog_fragment_tip is invalid. Received: " + tag);
            case 34:
                if ("layout/fragment_demo_0".equals(tag)) {
                    return new FragmentDemoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_demo is invalid. Received: " + tag);
            case 35:
                if ("layout/fragment_demo_preset_sound_settings_0".equals(tag)) {
                    return new FragmentDemoPresetSoundSettingsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_demo_preset_sound_settings is invalid. Received: " + tag);
            case 36:
                if ("layout/fragment_demo_sgu_0".equals(tag)) {
                    return new FragmentDemoSguBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_demo_sgu is invalid. Received: " + tag);
            case 37:
                if ("layout/fragment_dialog_download_package_0".equals(tag)) {
                    return new FragmentDialogDownloadPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_dialog_download_package is invalid. Received: " + tag);
            case 38:
                if ("layout/fragment_dialog_format_progress_0".equals(tag)) {
                    return new FragmentDialogFormatProgressBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_dialog_format_progress is invalid. Received: " + tag);
            case 39:
                if ("layout/fragment_dialog_update_firmware_0".equals(tag)) {
                    return new FragmentDialogUpdateFirmwareBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_dialog_update_firmware is invalid. Received: " + tag);
            case 40:
                if ("layout/fragment_dialog_upload_sgu_sound_set_0".equals(tag)) {
                    return new FragmentDialogUploadSguSoundSetBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_dialog_upload_sgu_sound_set is invalid. Received: " + tag);
            case 41:
                if ("layout/fragment_firmware_list_0".equals(tag)) {
                    return new FragmentFirmwareListBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_firmware_list is invalid. Received: " + tag);
            case 42:
                if ("layout/fragment_main_shop_0".equals(tag)) {
                    return new FragmentMainShopBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_main_shop is invalid. Received: " + tag);
            case 43:
                if ("layout/fragment_main_sounds_0".equals(tag)) {
                    return new FragmentMainSoundsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_main_sounds is invalid. Received: " + tag);
            case 44:
                if ("layout/fragment_notification_overview_0".equals(tag)) {
                    return new FragmentNotificationOverviewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_notification_overview is invalid. Received: " + tag);
            case 45:
                if ("layout/fragment_notifications_0".equals(tag)) {
                    return new FragmentNotificationsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_notifications is invalid. Received: " + tag);
            case 46:
                if ("layout/fragment_preset_sound_settings_0".equals(tag)) {
                    return new FragmentPresetSoundSettingsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_preset_sound_settings is invalid. Received: " + tag);
            case 47:
                if ("layout/fragment_sgu_shop_0".equals(tag)) {
                    return new FragmentSguShopBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_sgu_shop is invalid. Received: " + tag);
            case 48:
                if ("layout/fragment_sgu_sounds_0".equals(tag)) {
                    return new FragmentSguSoundsBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for fragment_sgu_sounds is invalid. Received: " + tag);
            case 49:
                if ("layout/item_bluetooth_device_0".equals(tag)) {
                    return new ItemBluetoothDeviceBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_bluetooth_device is invalid. Received: " + tag);
            case 50:
                if ("layout/item_demo_ev_divider_0".equals(tag)) {
                    return new ItemDemoEvDividerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_demo_ev_divider is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component, View view, int internalId, Object tag) {
        switch (internalId) {
            case 51:
                if ("layout/item_demo_shop_sound_package_0".equals(tag)) {
                    return new ItemDemoShopSoundPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_demo_shop_sound_package is invalid. Received: " + tag);
            case 52:
                if ("layout/item_demo_sound_package_0".equals(tag)) {
                    return new ItemDemoSoundPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_demo_sound_package is invalid. Received: " + tag);
            case 53:
                if ("layout/item_list_firmware_info_0".equals(tag)) {
                    return new ItemListFirmwareInfoBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_list_firmware_info is invalid. Received: " + tag);
            case 54:
                if ("layout/item_main_sound_package_0".equals(tag)) {
                    return new ItemMainSoundPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_main_sound_package is invalid. Received: " + tag);
            case 55:
                if ("layout/item_notification_0".equals(tag)) {
                    return new ItemNotificationBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_notification is invalid. Received: " + tag);
            case 56:
                if ("layout/item_sgu_shop_sound_package_0".equals(tag)) {
                    return new ItemSguShopSoundPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sgu_shop_sound_package is invalid. Received: " + tag);
            case 57:
                if ("layout/item_sgu_sound_config_0".equals(tag)) {
                    return new ItemSguSoundConfigBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sgu_sound_config is invalid. Received: " + tag);
            case 58:
                if ("layout/item_sgu_sound_fav_0".equals(tag)) {
                    return new ItemSguSoundFavBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sgu_sound_fav is invalid. Received: " + tag);
            case 59:
                if ("layout/item_sgu_sound_preview_0".equals(tag)) {
                    return new ItemSguSoundPreviewBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_sgu_sound_preview is invalid. Received: " + tag);
            case 60:
                if ("layout/item_shop_sound_package_0".equals(tag)) {
                    return new ItemShopSoundPackageBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_shop_sound_package is invalid. Received: " + tag);
            case 61:
                if ("layout/item_shop_subscription_card_0".equals(tag)) {
                    return new ItemShopSubscriptionCardBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_shop_subscription_card is invalid. Received: " + tag);
            case 62:
                if ("layout/item_subscription_pack_0".equals(tag)) {
                    return new ItemSubscriptionPackBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_subscription_pack is invalid. Received: " + tag);
            case 63:
                if ("layout/item_tachometer_with_equalize_0".equals(tag)) {
                    return new ItemTachometerWithEqualizeBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for item_tachometer_with_equalize is invalid. Received: " + tag);
            case 64:
                if ("layout/layout_main_menu_0".equals(tag)) {
                    return new LayoutMainMenuBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_main_menu is invalid. Received: " + tag);
            case 65:
                if ("layout/layout_subscription_plan_option_0".equals(tag)) {
                    return new LayoutSubscriptionPlanOptionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for layout_subscription_plan_option is invalid. Received: " + tag);
            case 66:
                if ("layout/vehicle_dialog_layout_0".equals(tag)) {
                    return new VehicleDialogLayoutBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for vehicle_dialog_layout is invalid. Received: " + tag);
            case 67:
                if ("layout/view_demo_sgu_sound_config_0".equals(tag)) {
                    return new ViewDemoSguSoundConfigBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_demo_sgu_sound_config is invalid. Received: " + tag);
            case 68:
                if ("layout/view_demo_shop_sound_package_0".equals(tag)) {
                    return new ViewDemoShopSoundPackageBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_demo_shop_sound_package is invalid. Received: " + tag);
            case 69:
                if ("layout/view_demo_sound_package_0".equals(tag)) {
                    return new ViewDemoSoundPackageBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_demo_sound_package is invalid. Received: " + tag);
            case 70:
                if ("layout/view_equalize_0".equals(tag)) {
                    return new ViewEqualizeBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_equalize is invalid. Received: " + tag);
            case 71:
                if ("layout/view_main_sound_package_0".equals(tag)) {
                    return new ViewMainSoundPackageBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_main_sound_package is invalid. Received: " + tag);
            case 72:
                if ("layout/view_rssi_signal_0".equals(tag)) {
                    return new ViewRssiSignalBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_rssi_signal is invalid. Received: " + tag);
            case 73:
                if ("layout/view_sgu_shop_sound_package_0".equals(tag)) {
                    return new ViewSguShopSoundPackageBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_sgu_shop_sound_package is invalid. Received: " + tag);
            case 74:
                if ("layout/view_sgu_sound_config_0".equals(tag)) {
                    return new ViewSguSoundConfigBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_sgu_sound_config is invalid. Received: " + tag);
            case 75:
                if ("layout/view_sgu_sound_fav_0".equals(tag)) {
                    return new ViewSguSoundFavBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_sgu_sound_fav is invalid. Received: " + tag);
            case 76:
                if ("layout/view_shop_mode_switch_0".equals(tag)) {
                    return new ViewShopModeSwitchBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_shop_mode_switch is invalid. Received: " + tag);
            case 77:
                if ("layout/view_shop_sound_package_0".equals(tag)) {
                    return new ViewShopSoundPackageBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_shop_sound_package is invalid. Received: " + tag);
            case 78:
                if ("layout/view_sound_preset_0".equals(tag)) {
                    return new ViewSoundPresetBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_sound_preset is invalid. Received: " + tag);
            case 79:
                if ("layout/view_tachometer_0".equals(tag)) {
                    return new ViewTachometerBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for view_tachometer is invalid. Received: " + tag);
            case 80:
                if ("layout/view_tachometer_with_equalize_0".equals(tag)) {
                    return new ViewTachometerWithEqualizeBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for view_tachometer_with_equalize is invalid. Received: " + tag);
            case 81:
                if ("layout/widget_toolbar_0".equals(tag)) {
                    return new WidgetToolbarBindingImpl(component, new View[]{view});
                }
                throw new IllegalArgumentException("The tag for widget_toolbar is invalid. Received: " + tag);
            default:
                return null;
        }
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        int i = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (i <= 0) {
            return null;
        }
        Object tag = view.getTag();
        if (tag == null) {
            throw new RuntimeException("view must have a tag");
        }
        int i2 = (i - 1) / 50;
        if (i2 == 0) {
            return internalGetViewDataBinding0(component, view, i, tag);
        }
        if (i2 != 1) {
            return null;
        }
        return internalGetViewDataBinding1(component, view, i, tag);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
        int i;
        if (views != null && views.length != 0 && (i = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId)) > 0) {
            Object tag = views[0].getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
            switch (i) {
                case 68:
                    if ("layout/view_demo_shop_sound_package_0".equals(tag)) {
                        return new ViewDemoShopSoundPackageBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_demo_shop_sound_package is invalid. Received: " + tag);
                case 69:
                    if ("layout/view_demo_sound_package_0".equals(tag)) {
                        return new ViewDemoSoundPackageBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_demo_sound_package is invalid. Received: " + tag);
                case 70:
                    if ("layout/view_equalize_0".equals(tag)) {
                        return new ViewEqualizeBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_equalize is invalid. Received: " + tag);
                case 71:
                    if ("layout/view_main_sound_package_0".equals(tag)) {
                        return new ViewMainSoundPackageBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_main_sound_package is invalid. Received: " + tag);
                case 73:
                    if ("layout/view_sgu_shop_sound_package_0".equals(tag)) {
                        return new ViewSguShopSoundPackageBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_sgu_shop_sound_package is invalid. Received: " + tag);
                case 74:
                    if ("layout/view_sgu_sound_config_0".equals(tag)) {
                        return new ViewSguSoundConfigBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_sgu_sound_config is invalid. Received: " + tag);
                case 75:
                    if ("layout/view_sgu_sound_fav_0".equals(tag)) {
                        return new ViewSguSoundFavBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_sgu_sound_fav is invalid. Received: " + tag);
                case 77:
                    if ("layout/view_shop_sound_package_0".equals(tag)) {
                        return new ViewShopSoundPackageBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_shop_sound_package is invalid. Received: " + tag);
                case 78:
                    if ("layout/view_sound_preset_0".equals(tag)) {
                        return new ViewSoundPresetBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_sound_preset is invalid. Received: " + tag);
                case 80:
                    if ("layout/view_tachometer_with_equalize_0".equals(tag)) {
                        return new ViewTachometerWithEqualizeBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for view_tachometer_with_equalize is invalid. Received: " + tag);
                case 81:
                    if ("layout/widget_toolbar_0".equals(tag)) {
                        return new WidgetToolbarBindingImpl(component, views);
                    }
                    throw new IllegalArgumentException("The tag for widget_toolbar is invalid. Received: " + tag);
            }
        }
        return null;
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String tag) {
        Integer num;
        if (tag == null || (num = InnerLayoutIdLookup.sKeys.get(tag)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int localId) {
        return InnerBrLookup.sKeys.get(localId);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.thor.basemodule.DataBinderMapperImpl());
        arrayList.add(new com.thor.businessmodule.DataBinderMapperImpl());
        return arrayList;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(12);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
            sparseArray.put(1, "changeCarInfo");
            sparseArray.put(2, "mainMenu");
            sparseArray.put(3, "modeType");
            sparseArray.put(4, "model");
            sparseArray.put(5, "packageInfo");
            sparseArray.put(6, "presetPackage");
            sparseArray.put(7, "signUpInfo");
            sparseArray.put(8, "skuDetails");
            sparseArray.put(9, "soundPackage");
            sparseArray.put(10, SessionDescription.ATTR_TYPE);
            sparseArray.put(11, "viewModel");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> map = new HashMap<>(81);
            sKeys = map;
            map.put("layout/activity_add_preset_0", Integer.valueOf(R.layout.activity_add_preset));
            map.put("layout/activity_add_sgu_preset_0", Integer.valueOf(R.layout.activity_add_sgu_preset));
            map.put("layout/activity_barcode_capture_0", Integer.valueOf(R.layout.activity_barcode_capture));
            map.put("layout/activity_bluetooth_devices_0", Integer.valueOf(R.layout.activity_bluetooth_devices));
            map.put("layout/activity_change_car_0", Integer.valueOf(R.layout.activity_change_car));
            map.put("layout/activity_demo_0", Integer.valueOf(R.layout.activity_demo));
            map.put("layout/activity_demo_preset_sound_settings_0", Integer.valueOf(R.layout.activity_demo_preset_sound_settings));
            map.put("layout/activity_demo_shop_0", Integer.valueOf(R.layout.activity_demo_shop));
            map.put("layout/activity_demo_sound_package_description_old_0", Integer.valueOf(R.layout.activity_demo_sound_package_description_old));
            map.put("layout/activity_firmware_list_0", Integer.valueOf(R.layout.activity_firmware_list));
            map.put("layout/activity_google_auth_0", Integer.valueOf(R.layout.activity_google_auth));
            map.put("layout/activity_locked_device_0", Integer.valueOf(R.layout.activity_locked_device));
            map.put("layout/activity_main_0", Integer.valueOf(R.layout.activity_main));
            map.put("layout/activity_notifications_0", Integer.valueOf(R.layout.activity_notifications));
            map.put("layout/activity_preset_sound_settings_0", Integer.valueOf(R.layout.activity_preset_sound_settings));
            map.put("layout/activity_settings_0", Integer.valueOf(R.layout.activity_settings));
            map.put("layout/activity_sgu_sound_package_description_0", Integer.valueOf(R.layout.activity_sgu_sound_package_description));
            map.put("layout/activity_shop_0", Integer.valueOf(R.layout.activity_shop));
            map.put("layout/activity_shop_old_0", Integer.valueOf(R.layout.activity_shop_old));
            map.put("layout/activity_shop_sound_package_0", Integer.valueOf(R.layout.activity_shop_sound_package));
            map.put("layout/activity_sign_up_0", Integer.valueOf(R.layout.activity_sign_up));
            map.put("layout/activity_sign_up_add_device_0", Integer.valueOf(R.layout.activity_sign_up_add_device));
            map.put("layout/activity_sign_up_car_info_0", Integer.valueOf(R.layout.activity_sign_up_car_info));
            map.put("layout/activity_sound_package_description_0", Integer.valueOf(R.layout.activity_sound_package_description));
            map.put("layout/activity_splash_0", Integer.valueOf(R.layout.activity_splash));
            map.put("layout/activity_subscription_0", Integer.valueOf(R.layout.activity_subscription));
            map.put("layout/activity_support_0", Integer.valueOf(R.layout.activity_support));
            map.put("layout/activity_test_0", Integer.valueOf(R.layout.activity_test));
            map.put("layout/activity_update_app_0", Integer.valueOf(R.layout.activity_update_app));
            map.put("layout/dialog_fragment_car_info_0", Integer.valueOf(R.layout.dialog_fragment_car_info));
            map.put("layout/dialog_fragment_simple_0", Integer.valueOf(R.layout.dialog_fragment_simple));
            map.put("layout/dialog_fragment_testers_0", Integer.valueOf(R.layout.dialog_fragment_testers));
            map.put("layout/dialog_fragment_tip_0", Integer.valueOf(R.layout.dialog_fragment_tip));
            map.put("layout/fragment_demo_0", Integer.valueOf(R.layout.fragment_demo));
            map.put("layout/fragment_demo_preset_sound_settings_0", Integer.valueOf(R.layout.fragment_demo_preset_sound_settings));
            map.put("layout/fragment_demo_sgu_0", Integer.valueOf(R.layout.fragment_demo_sgu));
            map.put("layout/fragment_dialog_download_package_0", Integer.valueOf(R.layout.fragment_dialog_download_package));
            map.put("layout/fragment_dialog_format_progress_0", Integer.valueOf(R.layout.fragment_dialog_format_progress));
            map.put("layout/fragment_dialog_update_firmware_0", Integer.valueOf(R.layout.fragment_dialog_update_firmware));
            map.put("layout/fragment_dialog_upload_sgu_sound_set_0", Integer.valueOf(R.layout.fragment_dialog_upload_sgu_sound_set));
            map.put("layout/fragment_firmware_list_0", Integer.valueOf(R.layout.fragment_firmware_list));
            map.put("layout/fragment_main_shop_0", Integer.valueOf(R.layout.fragment_main_shop));
            map.put("layout/fragment_main_sounds_0", Integer.valueOf(R.layout.fragment_main_sounds));
            map.put("layout/fragment_notification_overview_0", Integer.valueOf(R.layout.fragment_notification_overview));
            map.put("layout/fragment_notifications_0", Integer.valueOf(R.layout.fragment_notifications));
            map.put("layout/fragment_preset_sound_settings_0", Integer.valueOf(R.layout.fragment_preset_sound_settings));
            map.put("layout/fragment_sgu_shop_0", Integer.valueOf(R.layout.fragment_sgu_shop));
            map.put("layout/fragment_sgu_sounds_0", Integer.valueOf(R.layout.fragment_sgu_sounds));
            map.put("layout/item_bluetooth_device_0", Integer.valueOf(R.layout.item_bluetooth_device));
            map.put("layout/item_demo_ev_divider_0", Integer.valueOf(R.layout.item_demo_ev_divider));
            map.put("layout/item_demo_shop_sound_package_0", Integer.valueOf(R.layout.item_demo_shop_sound_package));
            map.put("layout/item_demo_sound_package_0", Integer.valueOf(R.layout.item_demo_sound_package));
            map.put("layout/item_list_firmware_info_0", Integer.valueOf(R.layout.item_list_firmware_info));
            map.put("layout/item_main_sound_package_0", Integer.valueOf(R.layout.item_main_sound_package));
            map.put("layout/item_notification_0", Integer.valueOf(R.layout.item_notification));
            map.put("layout/item_sgu_shop_sound_package_0", Integer.valueOf(R.layout.item_sgu_shop_sound_package));
            map.put("layout/item_sgu_sound_config_0", Integer.valueOf(R.layout.item_sgu_sound_config));
            map.put("layout/item_sgu_sound_fav_0", Integer.valueOf(R.layout.item_sgu_sound_fav));
            map.put("layout/item_sgu_sound_preview_0", Integer.valueOf(R.layout.item_sgu_sound_preview));
            map.put("layout/item_shop_sound_package_0", Integer.valueOf(R.layout.item_shop_sound_package));
            map.put("layout/item_shop_subscription_card_0", Integer.valueOf(R.layout.item_shop_subscription_card));
            map.put("layout/item_subscription_pack_0", Integer.valueOf(R.layout.item_subscription_pack));
            map.put("layout/item_tachometer_with_equalize_0", Integer.valueOf(R.layout.item_tachometer_with_equalize));
            map.put("layout/layout_main_menu_0", Integer.valueOf(R.layout.layout_main_menu));
            map.put("layout/layout_subscription_plan_option_0", Integer.valueOf(R.layout.layout_subscription_plan_option));
            map.put("layout/vehicle_dialog_layout_0", Integer.valueOf(R.layout.vehicle_dialog_layout));
            map.put("layout/view_demo_sgu_sound_config_0", Integer.valueOf(R.layout.view_demo_sgu_sound_config));
            map.put("layout/view_demo_shop_sound_package_0", Integer.valueOf(R.layout.view_demo_shop_sound_package));
            map.put("layout/view_demo_sound_package_0", Integer.valueOf(R.layout.view_demo_sound_package));
            map.put("layout/view_equalize_0", Integer.valueOf(R.layout.view_equalize));
            map.put("layout/view_main_sound_package_0", Integer.valueOf(R.layout.view_main_sound_package));
            map.put("layout/view_rssi_signal_0", Integer.valueOf(R.layout.view_rssi_signal));
            map.put("layout/view_sgu_shop_sound_package_0", Integer.valueOf(R.layout.view_sgu_shop_sound_package));
            map.put("layout/view_sgu_sound_config_0", Integer.valueOf(R.layout.view_sgu_sound_config));
            map.put("layout/view_sgu_sound_fav_0", Integer.valueOf(R.layout.view_sgu_sound_fav));
            map.put("layout/view_shop_mode_switch_0", Integer.valueOf(R.layout.view_shop_mode_switch));
            map.put("layout/view_shop_sound_package_0", Integer.valueOf(R.layout.view_shop_sound_package));
            map.put("layout/view_sound_preset_0", Integer.valueOf(R.layout.view_sound_preset));
            map.put("layout/view_tachometer_0", Integer.valueOf(R.layout.view_tachometer));
            map.put("layout/view_tachometer_with_equalize_0", Integer.valueOf(R.layout.view_tachometer_with_equalize));
            map.put("layout/widget_toolbar_0", Integer.valueOf(R.layout.widget_toolbar));
        }
    }
}
