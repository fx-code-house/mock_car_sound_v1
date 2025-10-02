package com.google.firebase.messaging;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.firebase.messaging.Constants;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: com.google.firebase:firebase-messaging@@21.0.0 */
/* loaded from: classes2.dex */
public class NotificationParams {
    private final Bundle data;

    public NotificationParams(Bundle bundle) {
        if (bundle == null) {
            throw new NullPointerException("data");
        }
        this.data = new Bundle(bundle);
    }

    Integer getNotificationCount() {
        Integer integer = getInteger(Constants.MessageNotificationKeys.NOTIFICATION_COUNT);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() >= 0) {
            return integer;
        }
        String strValueOf = String.valueOf(integer);
        Log.w(Constants.TAG, new StringBuilder(String.valueOf(strValueOf).length() + 67).append("notificationCount is invalid: ").append(strValueOf).append(". Skipping setting notificationCount.").toString());
        return null;
    }

    Integer getNotificationPriority() {
        Integer integer = getInteger(Constants.MessageNotificationKeys.NOTIFICATION_PRIORITY);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() >= -2 && integer.intValue() <= 2) {
            return integer;
        }
        String strValueOf = String.valueOf(integer);
        Log.w(Constants.TAG, new StringBuilder(String.valueOf(strValueOf).length() + 72).append("notificationPriority is invalid ").append(strValueOf).append(". Skipping setting notificationPriority.").toString());
        return null;
    }

    Integer getVisibility() {
        Integer integer = getInteger(Constants.MessageNotificationKeys.VISIBILITY);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() >= -1 && integer.intValue() <= 1) {
            return integer;
        }
        String strValueOf = String.valueOf(integer);
        Log.w("NotificationParams", new StringBuilder(String.valueOf(strValueOf).length() + 53).append("visibility is invalid: ").append(strValueOf).append(". Skipping setting visibility.").toString());
        return null;
    }

    public String getString(String str) {
        return this.data.getString(normalizePrefix(str));
    }

    private String normalizePrefix(String str) {
        if (!this.data.containsKey(str) && str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            String strKeyWithOldPrefix = keyWithOldPrefix(str);
            if (this.data.containsKey(strKeyWithOldPrefix)) {
                return strKeyWithOldPrefix;
            }
        }
        return str;
    }

    public boolean getBoolean(String str) {
        String string = getString(str);
        return IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(string) || Boolean.parseBoolean(string);
    }

    public Integer getInteger(String str) {
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(string));
        } catch (NumberFormatException unused) {
            String strUserFriendlyKey = userFriendlyKey(str);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strUserFriendlyKey).length() + 38 + String.valueOf(string).length()).append("Couldn't parse value of ").append(strUserFriendlyKey).append("(").append(string).append(") into an int").toString());
            return null;
        }
    }

    public Long getLong(String str) {
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return Long.valueOf(Long.parseLong(string));
        } catch (NumberFormatException unused) {
            String strUserFriendlyKey = userFriendlyKey(str);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strUserFriendlyKey).length() + 38 + String.valueOf(string).length()).append("Couldn't parse value of ").append(strUserFriendlyKey).append("(").append(string).append(") into a long").toString());
            return null;
        }
    }

    public String getLocalizationResourceForKey(String str) {
        String strValueOf = String.valueOf(str);
        return getString(Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX.length() != 0 ? strValueOf.concat(Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX) : new String(strValueOf));
    }

    public Object[] getLocalizationArgsForKey(String str) {
        String strValueOf = String.valueOf(str);
        JSONArray jSONArray = getJSONArray(Constants.MessageNotificationKeys.TEXT_ARGS_SUFFIX.length() != 0 ? strValueOf.concat(Constants.MessageNotificationKeys.TEXT_ARGS_SUFFIX) : new String(strValueOf));
        if (jSONArray == null) {
            return null;
        }
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.optString(i);
        }
        return strArr;
    }

    public JSONArray getJSONArray(String str) {
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new JSONArray(string);
        } catch (JSONException unused) {
            String strUserFriendlyKey = userFriendlyKey(str);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strUserFriendlyKey).length() + 50 + String.valueOf(string).length()).append("Malformed JSON for key ").append(strUserFriendlyKey).append(": ").append(string).append(", falling back to default").toString());
            return null;
        }
    }

    private static String userFriendlyKey(String str) {
        return str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX) ? str.substring(6) : str;
    }

    public Uri getLink() {
        String string = getString(Constants.MessageNotificationKeys.LINK_ANDROID);
        if (TextUtils.isEmpty(string)) {
            string = getString(Constants.MessageNotificationKeys.LINK);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return Uri.parse(string);
    }

    public String getSoundResourceName() {
        String string = getString(Constants.MessageNotificationKeys.SOUND_2);
        return TextUtils.isEmpty(string) ? getString(Constants.MessageNotificationKeys.SOUND) : string;
    }

    public long[] getVibrateTimings() throws JSONException {
        JSONArray jSONArray = getJSONArray(Constants.MessageNotificationKeys.VIBRATE_TIMINGS);
        if (jSONArray == null) {
            return null;
        }
        try {
            if (jSONArray.length() <= 1) {
                throw new JSONException("vibrateTimings have invalid length");
            }
            int length = jSONArray.length();
            long[] jArr = new long[length];
            for (int i = 0; i < length; i++) {
                jArr[i] = jSONArray.optLong(i);
            }
            return jArr;
        } catch (NumberFormatException | JSONException unused) {
            String strValueOf = String.valueOf(jSONArray);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strValueOf).length() + 74).append("User defined vibrateTimings is invalid: ").append(strValueOf).append(". Skipping setting vibrateTimings.").toString());
            return null;
        }
    }

    int[] getLightSettings() throws JSONException {
        JSONArray jSONArray = getJSONArray(Constants.MessageNotificationKeys.LIGHT_SETTINGS);
        if (jSONArray == null) {
            return null;
        }
        int[] iArr = new int[3];
        try {
            if (jSONArray.length() != 3) {
                throw new JSONException("lightSettings don't have all three fields");
            }
            iArr[0] = getLightColor(jSONArray.optString(0));
            iArr[1] = jSONArray.optInt(1);
            iArr[2] = jSONArray.optInt(2);
            return iArr;
        } catch (IllegalArgumentException e) {
            String strValueOf = String.valueOf(jSONArray);
            String message = e.getMessage();
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strValueOf).length() + 60 + String.valueOf(message).length()).append("LightSettings is invalid: ").append(strValueOf).append(". ").append(message).append(". Skipping setting LightSettings").toString());
            return null;
        } catch (JSONException unused) {
            String strValueOf2 = String.valueOf(jSONArray);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strValueOf2).length() + 58).append("LightSettings is invalid: ").append(strValueOf2).append(". Skipping setting LightSettings").toString());
            return null;
        }
    }

    public Bundle paramsWithReservedKeysRemoved() {
        Bundle bundle = new Bundle(this.data);
        for (String str : this.data.keySet()) {
            if (isReservedKey(str)) {
                bundle.remove(str);
            }
        }
        return bundle;
    }

    public Bundle paramsForAnalyticsIntent() {
        Bundle bundle = new Bundle(this.data);
        for (String str : this.data.keySet()) {
            if (!isAnalyticsKey(str)) {
                bundle.remove(str);
            }
        }
        return bundle;
    }

    public String getLocalizedString(Resources resources, String str, String str2) {
        String localizationResourceForKey = getLocalizationResourceForKey(str2);
        if (TextUtils.isEmpty(localizationResourceForKey)) {
            return null;
        }
        int identifier = resources.getIdentifier(localizationResourceForKey, "string", str);
        if (identifier == 0) {
            String strValueOf = String.valueOf(str2);
            String strUserFriendlyKey = userFriendlyKey(Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX.length() != 0 ? strValueOf.concat(Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX) : new String(strValueOf));
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strUserFriendlyKey).length() + 49 + String.valueOf(str2).length()).append(strUserFriendlyKey).append(" resource not found: ").append(str2).append(" Default value will be used.").toString());
            return null;
        }
        Object[] localizationArgsForKey = getLocalizationArgsForKey(str2);
        if (localizationArgsForKey == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, localizationArgsForKey);
        } catch (MissingFormatArgumentException e) {
            String strUserFriendlyKey2 = userFriendlyKey(str2);
            String string = Arrays.toString(localizationArgsForKey);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(strUserFriendlyKey2).length() + 58 + String.valueOf(string).length()).append("Missing format argument for ").append(strUserFriendlyKey2).append(": ").append(string).append(" Default value will be used.").toString(), e);
            return null;
        }
    }

    public String getPossiblyLocalizedString(Resources resources, String str, String str2) {
        String string = getString(str2);
        return !TextUtils.isEmpty(string) ? string : getLocalizedString(resources, str, str2);
    }

    public boolean hasImage() {
        return !TextUtils.isEmpty(getString(Constants.MessageNotificationKeys.IMAGE_URL));
    }

    public String getNotificationChannelId() {
        return getString(Constants.MessageNotificationKeys.CHANNEL);
    }

    private static boolean isAnalyticsKey(String str) {
        return str.startsWith(Constants.AnalyticsKeys.PREFIX) || str.equals(Constants.MessagePayloadKeys.FROM);
    }

    private static boolean isReservedKey(String str) {
        return str.startsWith(Constants.MessagePayloadKeys.RESERVED_CLIENT_LIB_PREFIX) || str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX) || str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX_OLD);
    }

    private static int getLightColor(String str) {
        int color = Color.parseColor(str);
        if (color != -16777216) {
            return color;
        }
        throw new IllegalArgumentException("Transparent color is invalid");
    }

    public boolean isNotification() {
        return getBoolean(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION);
    }

    public static boolean isNotification(Bundle bundle) {
        return IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(bundle.getString(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)) || IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(bundle.getString(keyWithOldPrefix(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)));
    }

    private static String keyWithOldPrefix(String str) {
        return !str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX) ? str : str.replace(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX, Constants.MessageNotificationKeys.NOTIFICATION_PREFIX_OLD);
    }
}
