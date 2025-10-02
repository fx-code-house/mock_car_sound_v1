package androidx.credentials.provider;

import android.os.Build;
import android.os.Bundle;
import androidx.credentials.provider.utils.BeginGetCredentialUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BeginGetCredentialRequest.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \r2\u00020\u0001:\u0002\f\rB!\b\u0007\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Landroidx/credentials/provider/BeginGetCredentialRequest;", "", "beginGetCredentialOptions", "", "Landroidx/credentials/provider/BeginGetCredentialOption;", "callingAppInfo", "Landroidx/credentials/provider/CallingAppInfo;", "(Ljava/util/List;Landroidx/credentials/provider/CallingAppInfo;)V", "getBeginGetCredentialOptions", "()Ljava/util/List;", "getCallingAppInfo", "()Landroidx/credentials/provider/CallingAppInfo;", "Api34Impl", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BeginGetCredentialRequest {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<BeginGetCredentialOption> beginGetCredentialOptions;
    private final CallingAppInfo callingAppInfo;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BeginGetCredentialRequest(List<? extends BeginGetCredentialOption> beginGetCredentialOptions) {
        this(beginGetCredentialOptions, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(beginGetCredentialOptions, "beginGetCredentialOptions");
    }

    @JvmStatic
    public static final Bundle asBundle(BeginGetCredentialRequest beginGetCredentialRequest) {
        return INSTANCE.asBundle(beginGetCredentialRequest);
    }

    @JvmStatic
    public static final BeginGetCredentialRequest fromBundle(Bundle bundle) {
        return INSTANCE.fromBundle(bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BeginGetCredentialRequest(List<? extends BeginGetCredentialOption> beginGetCredentialOptions, CallingAppInfo callingAppInfo) {
        Intrinsics.checkNotNullParameter(beginGetCredentialOptions, "beginGetCredentialOptions");
        this.beginGetCredentialOptions = beginGetCredentialOptions;
        this.callingAppInfo = callingAppInfo;
    }

    public /* synthetic */ BeginGetCredentialRequest(List list, CallingAppInfo callingAppInfo, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? null : callingAppInfo);
    }

    public final List<BeginGetCredentialOption> getBeginGetCredentialOptions() {
        return this.beginGetCredentialOptions;
    }

    public final CallingAppInfo getCallingAppInfo() {
        return this.callingAppInfo;
    }

    /* compiled from: BeginGetCredentialRequest.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/credentials/provider/BeginGetCredentialRequest$Api34Impl;", "", "()V", "REQUEST_KEY", "", "asBundle", "", "bundle", "Landroid/os/Bundle;", "request", "Landroidx/credentials/provider/BeginGetCredentialRequest;", "fromBundle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();
        private static final String REQUEST_KEY = "androidx.credentials.provider.BeginGetCredentialRequest";

        private Api34Impl() {
        }

        @JvmStatic
        public static final void asBundle(Bundle bundle, BeginGetCredentialRequest request) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            Intrinsics.checkNotNullParameter(request, "request");
            bundle.putParcelable(REQUEST_KEY, BeginGetCredentialUtil.INSTANCE.convertToFrameworkRequest(request));
        }

        @JvmStatic
        public static final BeginGetCredentialRequest fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest = (android.service.credentials.BeginGetCredentialRequest) bundle.getParcelable(REQUEST_KEY, android.service.credentials.BeginGetCredentialRequest.class);
            if (beginGetCredentialRequest != null) {
                return BeginGetCredentialUtil.INSTANCE.convertToJetpackRequest$credentials_release(beginGetCredentialRequest);
            }
            return null;
        }
    }

    /* compiled from: BeginGetCredentialRequest.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0007¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/BeginGetCredentialRequest$Companion;", "", "()V", "asBundle", "Landroid/os/Bundle;", "request", "Landroidx/credentials/provider/BeginGetCredentialRequest;", "fromBundle", "bundle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Bundle asBundle(BeginGetCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            Bundle bundle = new Bundle();
            if (Build.VERSION.SDK_INT >= 34) {
                Api34Impl.asBundle(bundle, request);
            }
            return bundle;
        }

        @JvmStatic
        public final BeginGetCredentialRequest fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            if (Build.VERSION.SDK_INT >= 34) {
                return Api34Impl.fromBundle(bundle);
            }
            return null;
        }
    }
}
