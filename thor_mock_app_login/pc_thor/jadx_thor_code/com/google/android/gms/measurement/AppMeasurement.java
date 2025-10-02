package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzae;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzfu;
import com.google.android.gms.measurement.internal.zzgs;
import com.google.android.gms.measurement.internal.zzgw;
import com.google.android.gms.measurement.internal.zzgz;
import com.google.android.gms.measurement.internal.zzia;
import com.google.android.gms.measurement.internal.zzig;
import com.google.android.gms.measurement.internal.zzku;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
@Deprecated
/* loaded from: classes2.dex */
public class AppMeasurement {
    public static final String CRASH_ORIGIN = "crash";
    public static final String FCM_ORIGIN = "fcm";
    public static final String FIAM_ORIGIN = "fiam";
    private static volatile AppMeasurement zza;
    private final zzfu zzb;
    private final zzia zzc;
    private final boolean zzd;

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
    public static class ConditionalUserProperty {
        public boolean mActive;
        public String mAppId;
        public long mCreationTimestamp;
        public String mExpiredEventName;
        public Bundle mExpiredEventParams;
        public String mName;
        public String mOrigin;
        public long mTimeToLive;
        public String mTimedOutEventName;
        public Bundle mTimedOutEventParams;
        public String mTriggerEventName;
        public long mTriggerTimeout;
        public String mTriggeredEventName;
        public Bundle mTriggeredEventParams;
        public long mTriggeredTimestamp;
        public Object mValue;

        public ConditionalUserProperty() {
        }

        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) throws Throwable {
            Preconditions.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            Object obj = conditionalUserProperty.mValue;
            if (obj != null) {
                Object objZza = zzig.zza(obj);
                this.mValue = objZza;
                if (objZza == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            if (conditionalUserProperty.mTimedOutEventParams != null) {
                this.mTimedOutEventParams = new Bundle(conditionalUserProperty.mTimedOutEventParams);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            if (conditionalUserProperty.mTriggeredEventParams != null) {
                this.mTriggeredEventParams = new Bundle(conditionalUserProperty.mTriggeredEventParams);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            if (conditionalUserProperty.mExpiredEventParams != null) {
                this.mExpiredEventParams = new Bundle(conditionalUserProperty.mExpiredEventParams);
            }
        }

        ConditionalUserProperty(Bundle bundle) {
            Preconditions.checkNotNull(bundle);
            this.mAppId = (String) zzgs.zza(bundle, "app_id", String.class, null);
            this.mOrigin = (String) zzgs.zza(bundle, "origin", String.class, null);
            this.mName = (String) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.NAME, String.class, null);
            this.mValue = zzgs.zza(bundle, "value", Object.class, null);
            this.mTriggerEventName = (String) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
            this.mTriggerTimeout = ((Long) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L)).longValue();
            this.mTimedOutEventName = (String) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
            this.mTimedOutEventParams = (Bundle) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
            this.mTriggeredEventName = (String) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
            this.mTriggeredEventParams = (Bundle) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
            this.mTimeToLive = ((Long) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L)).longValue();
            this.mExpiredEventName = (String) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
            this.mExpiredEventParams = (Bundle) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
            this.mActive = ((Boolean) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.class, false)).booleanValue();
            this.mCreationTimestamp = ((Long) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.class, 0L)).longValue();
            this.mTriggeredTimestamp = ((Long) zzgs.zza(bundle, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.class, 0L)).longValue();
        }

        final Bundle zza() {
            Bundle bundle = new Bundle();
            String str = this.mAppId;
            if (str != null) {
                bundle.putString("app_id", str);
            }
            String str2 = this.mOrigin;
            if (str2 != null) {
                bundle.putString("origin", str2);
            }
            String str3 = this.mName;
            if (str3 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.NAME, str3);
            }
            Object obj = this.mValue;
            if (obj != null) {
                zzgs.zza(bundle, obj);
            }
            String str4 = this.mTriggerEventName;
            if (str4 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, str4);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, this.mTriggerTimeout);
            String str5 = this.mTimedOutEventName;
            if (str5 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, str5);
            }
            Bundle bundle2 = this.mTimedOutEventParams;
            if (bundle2 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, bundle2);
            }
            String str6 = this.mTriggeredEventName;
            if (str6 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, str6);
            }
            Bundle bundle3 = this.mTriggeredEventParams;
            if (bundle3 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, bundle3);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, this.mTimeToLive);
            String str7 = this.mExpiredEventName;
            if (str7 != null) {
                bundle.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str7);
            }
            Bundle bundle4 = this.mExpiredEventParams;
            if (bundle4 != null) {
                bundle.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle4);
            }
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, this.mCreationTimestamp);
            bundle.putBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, this.mActive);
            bundle.putLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, this.mTriggeredTimestamp);
            return bundle;
        }
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
    public interface EventInterceptor extends zzgw {
        @Override // com.google.android.gms.measurement.internal.zzgw
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    /* compiled from: com.google.android.gms:play-services-measurement-impl@@18.0.0 */
    public interface OnEventListener extends zzgz {
        @Override // com.google.android.gms.measurement.internal.zzgz
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    @Deprecated
    public static AppMeasurement getInstance(Context context) {
        return zza(context, null, null);
    }

    private static AppMeasurement zza(Context context, String str, String str2) {
        if (zza == null) {
            synchronized (AppMeasurement.class) {
                if (zza == null) {
                    zzia zziaVarZza = zza(context, null);
                    if (zziaVarZza != null) {
                        zza = new AppMeasurement(zziaVarZza);
                    } else {
                        zza = new AppMeasurement(zzfu.zza(context, new zzae(0L, 0L, true, null, null, null, null), null));
                    }
                }
            }
        }
        return zza;
    }

    private static zzia zza(Context context, Bundle bundle) {
        try {
            return (zzia) Class.forName("com.google.firebase.analytics.FirebaseAnalytics").getDeclaredMethod("getScionFrontendApiImplementation", Context.class, Bundle.class).invoke(null, context, null);
        } catch (ClassNotFoundException | Exception unused) {
            return null;
        }
    }

    private AppMeasurement(zzfu zzfuVar) {
        Preconditions.checkNotNull(zzfuVar);
        this.zzb = zzfuVar;
        this.zzc = null;
        this.zzd = false;
    }

    private AppMeasurement(zzia zziaVar) {
        Preconditions.checkNotNull(zziaVar);
        this.zzc = zziaVar;
        this.zzb = null;
        this.zzd = true;
    }

    public void logEventInternal(String str, String str2, Bundle bundle) {
        if (this.zzd) {
            this.zzc.zza(str, str2, bundle);
        } else {
            this.zzb.zzg().zza(str, str2, bundle);
        }
    }

    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) throws IllegalStateException {
        if (this.zzd) {
            this.zzc.zza(str, str2, bundle, j);
        } else {
            this.zzb.zzg().zza(str, str2, bundle, true, false, j);
        }
    }

    public Map<String, Object> getUserProperties(boolean z) throws IllegalStateException {
        if (this.zzd) {
            return this.zzc.zza((String) null, (String) null, z);
        }
        List<zzku> listZza = this.zzb.zzg().zza(z);
        ArrayMap arrayMap = new ArrayMap(listZza.size());
        for (zzku zzkuVar : listZza) {
            arrayMap.put(zzkuVar.zza, zzkuVar.zza());
        }
        return arrayMap;
    }

    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        if (this.zzd) {
            this.zzc.zza(eventInterceptor);
        } else {
            this.zzb.zzg().zza(eventInterceptor);
        }
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) throws IllegalStateException {
        if (this.zzd) {
            this.zzc.zza(onEventListener);
        } else {
            this.zzb.zzg().zza(onEventListener);
        }
    }

    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) throws IllegalStateException {
        if (this.zzd) {
            this.zzc.zzb(onEventListener);
        } else {
            this.zzb.zzg().zzb(onEventListener);
        }
    }

    public String getCurrentScreenName() {
        if (this.zzd) {
            return this.zzc.zza();
        }
        return this.zzb.zzg().zzai();
    }

    public String getCurrentScreenClass() {
        if (this.zzd) {
            return this.zzc.zzb();
        }
        return this.zzb.zzg().zzaj();
    }

    public String getAppInstanceId() {
        if (this.zzd) {
            return this.zzc.zzc();
        }
        return this.zzb.zzg().zzag();
    }

    public String getGmpAppId() {
        if (this.zzd) {
            return this.zzc.zzd();
        }
        return this.zzb.zzg().zzak();
    }

    public long generateEventId() {
        if (this.zzd) {
            return this.zzc.zze();
        }
        return this.zzb.zzh().zzf();
    }

    public void beginAdUnitExposure(String str) throws IllegalStateException {
        if (this.zzd) {
            this.zzc.zza(str);
        } else {
            this.zzb.zzy().zza(str, this.zzb.zzl().elapsedRealtime());
        }
    }

    public void endAdUnitExposure(String str) throws IllegalStateException {
        if (this.zzd) {
            this.zzc.zzb(str);
        } else {
            this.zzb.zzy().zzb(str, this.zzb.zzl().elapsedRealtime());
        }
    }

    public void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) throws IllegalStateException {
        Preconditions.checkNotNull(conditionalUserProperty);
        if (this.zzd) {
            this.zzc.zza(conditionalUserProperty.zza());
        } else {
            this.zzb.zzg().zza(conditionalUserProperty.zza());
        }
    }

    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) throws IllegalStateException {
        if (this.zzd) {
            this.zzc.zzb(str, str2, bundle);
        } else {
            this.zzb.zzg().zzc(str, str2, bundle);
        }
    }

    protected Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        if (this.zzd) {
            return this.zzc.zza(str, str2, z);
        }
        return this.zzb.zzg().zza(str, str2, z);
    }

    public List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) throws IllegalStateException {
        List<Bundle> listZza;
        if (this.zzd) {
            listZza = this.zzc.zza(str, str2);
        } else {
            listZza = this.zzb.zzg().zza(str, str2);
        }
        ArrayList arrayList = new ArrayList(listZza == null ? 0 : listZza.size());
        Iterator<Bundle> it = listZza.iterator();
        while (it.hasNext()) {
            arrayList.add(new ConditionalUserProperty(it.next()));
        }
        return arrayList;
    }

    public int getMaxUserProperties(String str) {
        if (this.zzd) {
            return this.zzc.zzc(str);
        }
        this.zzb.zzg();
        Preconditions.checkNotEmpty(str);
        return 25;
    }

    public Boolean getBoolean() {
        if (this.zzd) {
            return (Boolean) this.zzc.zza(4);
        }
        return this.zzb.zzg().zzab();
    }

    public String getString() {
        if (this.zzd) {
            return (String) this.zzc.zza(0);
        }
        return this.zzb.zzg().zzac();
    }

    public Long getLong() {
        if (this.zzd) {
            return (Long) this.zzc.zza(1);
        }
        return this.zzb.zzg().zzad();
    }

    public Integer getInteger() {
        if (this.zzd) {
            return (Integer) this.zzc.zza(3);
        }
        return this.zzb.zzg().zzae();
    }

    public Double getDouble() {
        if (this.zzd) {
            return (Double) this.zzc.zza(2);
        }
        return this.zzb.zzg().zzaf();
    }
}
