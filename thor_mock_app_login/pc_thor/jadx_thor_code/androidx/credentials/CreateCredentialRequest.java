package androidx.credentials;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.credentials.internal.FrameworkClassParsingException;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.firebase.messaging.Constants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreateCredentialRequest.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b&\u0018\u0000 \u00182\u00020\u0001:\u0002\u0018\u0019BI\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\r\u001a\u00020\b¢\u0006\u0002\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0014R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\r\u001a\u00020\b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016¨\u0006\u001a"}, d2 = {"Landroidx/credentials/CreateCredentialRequest;", "", SessionDescription.ATTR_TYPE, "", "credentialData", "Landroid/os/Bundle;", "candidateQueryData", "isSystemProviderRequired", "", "isAutoSelectAllowed", "displayInfo", "Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "origin", "preferImmediatelyAvailableCredentials", "(Ljava/lang/String;Landroid/os/Bundle;Landroid/os/Bundle;ZZLandroidx/credentials/CreateCredentialRequest$DisplayInfo;Ljava/lang/String;Z)V", "getCandidateQueryData", "()Landroid/os/Bundle;", "getCredentialData", "getDisplayInfo", "()Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "()Z", "getOrigin", "()Ljava/lang/String;", "getType", "Companion", "DisplayInfo", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class CreateCredentialRequest {
    public static final String BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED = "androidx.credentials.BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED";
    public static final String BUNDLE_KEY_PREFER_IMMEDIATELY_AVAILABLE_CREDENTIALS = "androidx.credentials.BUNDLE_KEY_PREFER_IMMEDIATELY_AVAILABLE_CREDENTIALS";

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Bundle candidateQueryData;
    private final Bundle credentialData;
    private final DisplayInfo displayInfo;
    private final boolean isAutoSelectAllowed;
    private final boolean isSystemProviderRequired;
    private final String origin;
    private final boolean preferImmediatelyAvailableCredentials;
    private final String type;

    @JvmStatic
    public static final CreateCredentialRequest createFrom(String str, Bundle bundle, Bundle bundle2, boolean z, String str2) {
        return INSTANCE.createFrom(str, bundle, bundle2, z, str2);
    }

    public CreateCredentialRequest(String type, Bundle credentialData, Bundle candidateQueryData, boolean z, boolean z2, DisplayInfo displayInfo, String str, boolean z3) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(credentialData, "credentialData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(displayInfo, "displayInfo");
        this.type = type;
        this.credentialData = credentialData;
        this.candidateQueryData = candidateQueryData;
        this.isSystemProviderRequired = z;
        this.isAutoSelectAllowed = z2;
        this.displayInfo = displayInfo;
        this.origin = str;
        this.preferImmediatelyAvailableCredentials = z3;
        credentialData.putBoolean("androidx.credentials.BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED", z2);
        credentialData.putBoolean("androidx.credentials.BUNDLE_KEY_PREFER_IMMEDIATELY_AVAILABLE_CREDENTIALS", z3);
        candidateQueryData.putBoolean("androidx.credentials.BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED", z2);
    }

    public final String getType() {
        return this.type;
    }

    public final Bundle getCredentialData() {
        return this.credentialData;
    }

    public final Bundle getCandidateQueryData() {
        return this.candidateQueryData;
    }

    /* renamed from: isSystemProviderRequired, reason: from getter */
    public final boolean getIsSystemProviderRequired() {
        return this.isSystemProviderRequired;
    }

    /* renamed from: isAutoSelectAllowed, reason: from getter */
    public final boolean getIsAutoSelectAllowed() {
        return this.isAutoSelectAllowed;
    }

    public final DisplayInfo getDisplayInfo() {
        return this.displayInfo;
    }

    public final String getOrigin() {
        return this.origin;
    }

    /* renamed from: preferImmediatelyAvailableCredentials, reason: from getter */
    public final boolean getPreferImmediatelyAvailableCredentials() {
        return this.preferImmediatelyAvailableCredentials;
    }

    /* compiled from: CreateCredentialRequest.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001b\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005B#\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB-\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000bJ\b\u0010\u0013\u001a\u00020\u0014H\u0007R\u0015\u0010\t\u001a\u0004\u0018\u00010\n8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011¨\u0006\u0016"}, d2 = {"Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "", "userId", "", "userDisplayName", "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)V", "preferDefaultProvider", "", "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/String;)V", "credentialTypeIcon", "Landroid/graphics/drawable/Icon;", "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/graphics/drawable/Icon;Ljava/lang/String;)V", "getCredentialTypeIcon", "()Landroid/graphics/drawable/Icon;", "getPreferDefaultProvider", "()Ljava/lang/String;", "getUserDisplayName", "()Ljava/lang/CharSequence;", "getUserId", "toBundle", "Landroid/os/Bundle;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DisplayInfo {
        public static final String BUNDLE_KEY_CREDENTIAL_TYPE_ICON = "androidx.credentials.BUNDLE_KEY_CREDENTIAL_TYPE_ICON";
        public static final String BUNDLE_KEY_DEFAULT_PROVIDER = "androidx.credentials.BUNDLE_KEY_DEFAULT_PROVIDER";
        public static final String BUNDLE_KEY_REQUEST_DISPLAY_INFO = "androidx.credentials.BUNDLE_KEY_REQUEST_DISPLAY_INFO";
        public static final String BUNDLE_KEY_USER_DISPLAY_NAME = "androidx.credentials.BUNDLE_KEY_USER_DISPLAY_NAME";
        public static final String BUNDLE_KEY_USER_ID = "androidx.credentials.BUNDLE_KEY_USER_ID";

        /* renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final Icon credentialTypeIcon;
        private final String preferDefaultProvider;
        private final CharSequence userDisplayName;
        private final CharSequence userId;

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public DisplayInfo(CharSequence userId) {
            this(userId, (CharSequence) null, 2, (DefaultConstructorMarker) (0 == true ? 1 : 0));
            Intrinsics.checkNotNullParameter(userId, "userId");
        }

        @JvmStatic
        public static final DisplayInfo parseFromCredentialDataBundle(Bundle bundle) {
            return INSTANCE.parseFromCredentialDataBundle(bundle);
        }

        public DisplayInfo(CharSequence userId, CharSequence charSequence, Icon icon, String str) {
            Intrinsics.checkNotNullParameter(userId, "userId");
            this.userId = userId;
            this.userDisplayName = charSequence;
            this.credentialTypeIcon = icon;
            this.preferDefaultProvider = str;
            if (!(userId.length() > 0)) {
                throw new IllegalArgumentException("userId should not be empty".toString());
            }
        }

        public final CharSequence getUserId() {
            return this.userId;
        }

        public final CharSequence getUserDisplayName() {
            return this.userDisplayName;
        }

        public final Icon getCredentialTypeIcon() {
            return this.credentialTypeIcon;
        }

        public final String getPreferDefaultProvider() {
            return this.preferDefaultProvider;
        }

        public /* synthetic */ DisplayInfo(CharSequence charSequence, CharSequence charSequence2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(charSequence, (i & 2) != 0 ? null : charSequence2);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public DisplayInfo(CharSequence userId, CharSequence charSequence) {
            this(userId, charSequence, (Icon) null, (String) null);
            Intrinsics.checkNotNullParameter(userId, "userId");
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public DisplayInfo(CharSequence userId, CharSequence charSequence, String str) {
            this(userId, charSequence, (Icon) null, str);
            Intrinsics.checkNotNullParameter(userId, "userId");
        }

        public final Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putCharSequence(BUNDLE_KEY_USER_ID, this.userId);
            if (!TextUtils.isEmpty(this.userDisplayName)) {
                bundle.putCharSequence(BUNDLE_KEY_USER_DISPLAY_NAME, this.userDisplayName);
            }
            if (!TextUtils.isEmpty(this.preferDefaultProvider)) {
                bundle.putString(BUNDLE_KEY_DEFAULT_PROVIDER, this.preferDefaultProvider);
            }
            return bundle;
        }

        /* compiled from: CreateCredentialRequest.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/credentials/CreateCredentialRequest$DisplayInfo$Companion;", "", "()V", "BUNDLE_KEY_CREDENTIAL_TYPE_ICON", "", "BUNDLE_KEY_DEFAULT_PROVIDER", "BUNDLE_KEY_REQUEST_DISPLAY_INFO", "BUNDLE_KEY_USER_DISPLAY_NAME", "BUNDLE_KEY_USER_ID", "parseFromCredentialDataBundle", "Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", Constants.MessagePayloadKeys.FROM, "Landroid/os/Bundle;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final DisplayInfo parseFromCredentialDataBundle(Bundle from) {
                Intrinsics.checkNotNullParameter(from, "from");
                try {
                    Bundle bundle = from.getBundle(DisplayInfo.BUNDLE_KEY_REQUEST_DISPLAY_INFO);
                    Intrinsics.checkNotNull(bundle);
                    CharSequence charSequence = bundle.getCharSequence(DisplayInfo.BUNDLE_KEY_USER_ID);
                    CharSequence charSequence2 = bundle.getCharSequence(DisplayInfo.BUNDLE_KEY_USER_DISPLAY_NAME);
                    Icon icon = (Icon) bundle.getParcelable(DisplayInfo.BUNDLE_KEY_CREDENTIAL_TYPE_ICON);
                    String string = bundle.getString(DisplayInfo.BUNDLE_KEY_DEFAULT_PROVIDER);
                    Intrinsics.checkNotNull(charSequence);
                    return new DisplayInfo(charSequence, charSequence2, icon, string);
                } catch (Exception unused) {
                    return null;
                }
            }
        }
    }

    /* compiled from: CreateCredentialRequest.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Landroidx/credentials/CreateCredentialRequest$Companion;", "", "()V", "BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED", "", "BUNDLE_KEY_PREFER_IMMEDIATELY_AVAILABLE_CREDENTIALS", "createFrom", "Landroidx/credentials/CreateCredentialRequest;", SessionDescription.ATTR_TYPE, "credentialData", "Landroid/os/Bundle;", "candidateQueryData", "requireSystemProvider", "", "origin", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ CreateCredentialRequest createFrom$default(Companion companion, String str, Bundle bundle, Bundle bundle2, boolean z, String str2, int i, Object obj) {
            if ((i & 16) != 0) {
                str2 = null;
            }
            return companion.createFrom(str, bundle, bundle2, z, str2);
        }

        @JvmStatic
        public final CreateCredentialRequest createFrom(String type, Bundle credentialData, Bundle candidateQueryData, boolean requireSystemProvider, String origin) throws FrameworkClassParsingException {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(credentialData, "credentialData");
            Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
            try {
                if (Intrinsics.areEqual(type, PasswordCredential.TYPE_PASSWORD_CREDENTIAL)) {
                    return CreatePasswordRequest.INSTANCE.createFrom$credentials_release(credentialData, origin, candidateQueryData);
                }
                if (Intrinsics.areEqual(type, PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL)) {
                    String string = credentialData.getString(PublicKeyCredential.BUNDLE_KEY_SUBTYPE);
                    if (string != null && string.hashCode() == 589054771 && string.equals(CreatePublicKeyCredentialRequest.BUNDLE_VALUE_SUBTYPE_CREATE_PUBLIC_KEY_CREDENTIAL_REQUEST)) {
                        return CreatePublicKeyCredentialRequest.INSTANCE.createFrom$credentials_release(credentialData, origin, candidateQueryData);
                    }
                    throw new FrameworkClassParsingException();
                }
                throw new FrameworkClassParsingException();
            } catch (FrameworkClassParsingException unused) {
                DisplayInfo fromCredentialDataBundle = DisplayInfo.INSTANCE.parseFromCredentialDataBundle(credentialData);
                if (fromCredentialDataBundle == null) {
                    return null;
                }
                return new CreateCustomCredentialRequest(type, credentialData, candidateQueryData, requireSystemProvider, fromCredentialDataBundle, credentialData.getBoolean("androidx.credentials.BUNDLE_KEY_IS_AUTO_SELECT_ALLOWED", false), origin, credentialData.getBoolean("androidx.credentials.BUNDLE_KEY_PREFER_IMMEDIATELY_AVAILABLE_CREDENTIALS", false));
            }
        }
    }
}
