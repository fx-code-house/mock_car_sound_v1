package androidx.credentials.webauthn;

import android.os.Build;
import android.util.Base64;
import androidx.credentials.provider.CallingAppInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WebAuthnUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0001\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/webauthn/WebAuthnUtils;", "", "()V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class WebAuthnUtils {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* compiled from: WebAuthnUtils.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\b¨\u0006\f"}, d2 = {"Landroidx/credentials/webauthn/WebAuthnUtils$Companion;", "", "()V", "appInfoToOrigin", "", "info", "Landroidx/credentials/provider/CallingAppInfo;", "b64Decode", "", "str", "b64Encode", "data", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final byte[] b64Decode(String str) {
            Intrinsics.checkNotNullParameter(str, "str");
            byte[] bArrDecode = Base64.decode(str, 11);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(str, Base64.NO_PA…_WRAP or Base64.URL_SAFE)");
            return bArrDecode;
        }

        public final String b64Encode(byte[] data) {
            Intrinsics.checkNotNullParameter(data, "data");
            String strEncodeToString = Base64.encodeToString(data, 11);
            Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(data, Bas…_WRAP or Base64.URL_SAFE)");
            return strEncodeToString;
        }

        public final String appInfoToOrigin(CallingAppInfo info) {
            Intrinsics.checkNotNullParameter(info, "info");
            return Build.VERSION.SDK_INT >= 28 ? WebAuthnUtilsApi28.INSTANCE.appInfoToOrigin(info) : "";
        }
    }
}
