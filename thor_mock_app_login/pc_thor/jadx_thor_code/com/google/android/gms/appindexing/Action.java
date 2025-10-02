package com.google.android.gms.appindexing;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
@Deprecated
/* loaded from: classes2.dex */
public final class Action extends Thing {
    public static final String STATUS_TYPE_ACTIVE = "http://schema.org/ActiveActionStatus";
    public static final String STATUS_TYPE_COMPLETED = "http://schema.org/CompletedActionStatus";
    public static final String STATUS_TYPE_FAILED = "http://schema.org/FailedActionStatus";
    public static final String TYPE_ACTIVATE = "http://schema.org/ActivateAction";
    public static final String TYPE_ADD = "http://schema.org/AddAction";
    public static final String TYPE_BOOKMARK = "http://schema.org/BookmarkAction";
    public static final String TYPE_COMMUNICATE = "http://schema.org/CommunicateAction";
    public static final String TYPE_FILM = "http://schema.org/FilmAction";
    public static final String TYPE_LIKE = "http://schema.org/LikeAction";
    public static final String TYPE_LISTEN = "http://schema.org/ListenAction";
    public static final String TYPE_PHOTOGRAPH = "http://schema.org/PhotographAction";
    public static final String TYPE_RESERVE = "http://schema.org/ReserveAction";
    public static final String TYPE_SEARCH = "http://schema.org/SearchAction";
    public static final String TYPE_VIEW = "http://schema.org/ViewAction";
    public static final String TYPE_WANT = "http://schema.org/WantAction";
    public static final String TYPE_WATCH = "http://schema.org/WatchAction";

    private Action(Bundle bundle) {
        super(bundle);
    }

    public static Action newAction(String str, String str2, Uri uri, Uri uri2) {
        return (Action) new Builder(str).setObject(new Thing.Builder().setName(str2).setId(uri == null ? null : uri.toString()).setUrl(uri2).build()).build();
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    @Deprecated
    public static final class Builder extends Thing.Builder {
        public Builder(String str) {
            Preconditions.checkNotNull(str);
            super.put(SessionDescription.ATTR_TYPE, str);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder setName(String str) {
            return (Builder) super.put(AppMeasurementSdk.ConditionalUserProperty.NAME, str);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder setUrl(Uri uri) {
            if (uri != null) {
                super.put(ImagesContract.URL, uri.toString());
            }
            return this;
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder put(String str, String str2) {
            return (Builder) super.put(str, str2);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder put(String str, String[] strArr) {
            return (Builder) super.put(str, strArr);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder put(String str, Thing thing) {
            return (Builder) super.put(str, thing);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder put(String str, Thing[] thingArr) {
            return (Builder) super.put(str, thingArr);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Builder put(String str, boolean z) {
            return (Builder) super.put(str, z);
        }

        public final Builder setObject(Thing thing) {
            Preconditions.checkNotNull(thing);
            return (Builder) super.put("object", thing);
        }

        public final Builder setActionStatus(String str) {
            Preconditions.checkNotNull(str);
            return (Builder) super.put("actionStatus", str);
        }

        @Override // com.google.android.gms.appindexing.Thing.Builder
        public final Action build() {
            Preconditions.checkNotNull(this.zzax.get("object"), "setObject is required before calling build().");
            Preconditions.checkNotNull(this.zzax.get(SessionDescription.ATTR_TYPE), "setType is required before calling build().");
            Bundle bundle = (Bundle) this.zzax.getParcelable("object");
            Preconditions.checkNotNull(bundle.get(AppMeasurementSdk.ConditionalUserProperty.NAME), "Must call setObject() with a valid name. Example: setObject(new Thing.Builder().setName(name).setUrl(url))");
            Preconditions.checkNotNull(bundle.get(ImagesContract.URL), "Must call setObject() with a valid app URI. Example: setObject(new Thing.Builder().setName(name).setUrl(url))");
            return new Action(this.zzax);
        }
    }

    public static Action newAction(String str, String str2, Uri uri) {
        return newAction(str, str2, null, uri);
    }
}
