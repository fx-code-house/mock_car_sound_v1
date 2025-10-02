package androidx.credentials.provider;

import android.os.Build;
import android.os.Bundle;
import androidx.credentials.provider.utils.BeginCreateCredentialUtil;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BeginCreateCredentialRequest.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000 \u00102\u00020\u0001:\u0002\u000f\u0010B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Landroidx/credentials/provider/BeginCreateCredentialRequest;", "", SessionDescription.ATTR_TYPE, "", "candidateQueryData", "Landroid/os/Bundle;", "callingAppInfo", "Landroidx/credentials/provider/CallingAppInfo;", "(Ljava/lang/String;Landroid/os/Bundle;Landroidx/credentials/provider/CallingAppInfo;)V", "getCallingAppInfo", "()Landroidx/credentials/provider/CallingAppInfo;", "getCandidateQueryData", "()Landroid/os/Bundle;", "getType", "()Ljava/lang/String;", "Api34Impl", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class BeginCreateCredentialRequest {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CallingAppInfo callingAppInfo;
    private final Bundle candidateQueryData;
    private final String type;

    @JvmStatic
    public static final Bundle asBundle(BeginCreateCredentialRequest beginCreateCredentialRequest) {
        return INSTANCE.asBundle(beginCreateCredentialRequest);
    }

    @JvmStatic
    public static final BeginCreateCredentialRequest fromBundle(Bundle bundle) {
        return INSTANCE.fromBundle(bundle);
    }

    public BeginCreateCredentialRequest(String type, Bundle candidateQueryData, CallingAppInfo callingAppInfo) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        this.type = type;
        this.candidateQueryData = candidateQueryData;
        this.callingAppInfo = callingAppInfo;
    }

    public final String getType() {
        return this.type;
    }

    public final Bundle getCandidateQueryData() {
        return this.candidateQueryData;
    }

    public final CallingAppInfo getCallingAppInfo() {
        return this.callingAppInfo;
    }

    /* compiled from: BeginCreateCredentialRequest.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/credentials/provider/BeginCreateCredentialRequest$Api34Impl;", "", "()V", "REQUEST_KEY", "", "asBundle", "", "bundle", "Landroid/os/Bundle;", "request", "Landroidx/credentials/provider/BeginCreateCredentialRequest;", "fromBundle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();
        private static final String REQUEST_KEY = "androidx.credentials.provider.BeginCreateCredentialRequest";

        private Api34Impl() {
        }

        @JvmStatic
        public static final void asBundle(Bundle bundle, BeginCreateCredentialRequest request) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(request, "request");
            bundle.putParcelable(REQUEST_KEY, BeginCreateCredentialUtil.INSTANCE.convertToFrameworkRequest(request));
        }

        @JvmStatic
        public static final BeginCreateCredentialRequest fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest = (android.service.credentials.BeginCreateCredentialRequest) bundle.getParcelable(REQUEST_KEY, android.service.credentials.BeginCreateCredentialRequest.class);
            if (beginCreateCredentialRequest != null) {
                return BeginCreateCredentialUtil.INSTANCE.convertToJetpackRequest$credentials_release(beginCreateCredentialRequest);
            }
            return null;
        }
    }

    /* compiled from: BeginCreateCredentialRequest.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0007¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/BeginCreateCredentialRequest$Companion;", "", "()V", "asBundle", "Landroid/os/Bundle;", "request", "Landroidx/credentials/provider/BeginCreateCredentialRequest;", "fromBundle", "bundle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Bundle asBundle(BeginCreateCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            Bundle bundle = new Bundle();
            if (Build.VERSION.SDK_INT >= 34) {
                Api34Impl.asBundle(bundle, request);
            }
            return bundle;
        }

        @JvmStatic
        public final BeginCreateCredentialRequest fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            if (Build.VERSION.SDK_INT >= 34) {
                return Api34Impl.fromBundle(bundle);
            }
            return null;
        }
    }
}
