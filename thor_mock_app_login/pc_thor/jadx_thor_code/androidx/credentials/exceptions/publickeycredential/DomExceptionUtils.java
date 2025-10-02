package androidx.credentials.exceptions.publickeycredential;

import androidx.core.app.NotificationCompat;
import androidx.credentials.exceptions.domerrors.AbortError;
import androidx.credentials.exceptions.domerrors.ConstraintError;
import androidx.credentials.exceptions.domerrors.DataCloneError;
import androidx.credentials.exceptions.domerrors.DataError;
import androidx.credentials.exceptions.domerrors.DomError;
import androidx.credentials.exceptions.domerrors.EncodingError;
import androidx.credentials.exceptions.domerrors.HierarchyRequestError;
import androidx.credentials.exceptions.domerrors.InUseAttributeError;
import androidx.credentials.exceptions.domerrors.InvalidCharacterError;
import androidx.credentials.exceptions.domerrors.InvalidModificationError;
import androidx.credentials.exceptions.domerrors.InvalidNodeTypeError;
import androidx.credentials.exceptions.domerrors.InvalidStateError;
import androidx.credentials.exceptions.domerrors.NamespaceError;
import androidx.credentials.exceptions.domerrors.NetworkError;
import androidx.credentials.exceptions.domerrors.NoModificationAllowedError;
import androidx.credentials.exceptions.domerrors.NotAllowedError;
import androidx.credentials.exceptions.domerrors.NotFoundError;
import androidx.credentials.exceptions.domerrors.NotReadableError;
import androidx.credentials.exceptions.domerrors.NotSupportedError;
import androidx.credentials.exceptions.domerrors.OperationError;
import androidx.credentials.exceptions.domerrors.OptOutError;
import androidx.credentials.exceptions.domerrors.QuotaExceededError;
import androidx.credentials.exceptions.domerrors.ReadOnlyError;
import androidx.credentials.exceptions.domerrors.SecurityError;
import androidx.credentials.exceptions.domerrors.SyntaxError;
import androidx.credentials.exceptions.domerrors.TimeoutError;
import androidx.credentials.exceptions.domerrors.TransactionInactiveError;
import androidx.credentials.exceptions.domerrors.UnknownError;
import androidx.credentials.exceptions.domerrors.VersionError;
import androidx.credentials.exceptions.domerrors.WrongDocumentError;
import androidx.credentials.internal.FrameworkClassParsingException;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DomExceptionUtils.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0001\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/exceptions/publickeycredential/DomExceptionUtils;", "", "()V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class DomExceptionUtils {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String SEPARATOR = "/";

    /* compiled from: DomExceptionUtils.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0005\u001a\u0002H\u0006\"\u0006\b\u0000\u0010\u0006\u0018\u00012\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\b\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u0002H\u0006H\u0081\b¢\u0006\u0004\b\u000b\u0010\fJ-\u0010\r\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u0002H\u0006H\u0002¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Landroidx/credentials/exceptions/publickeycredential/DomExceptionUtils$Companion;", "", "()V", "SEPARATOR", "", "generateDomException", ExifInterface.GPS_DIRECTION_TRUE, SessionDescription.ATTR_TYPE, "prefix", NotificationCompat.CATEGORY_MESSAGE, "t", "generateDomException$credentials_release", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "generateException", "domError", "Landroidx/credentials/exceptions/domerrors/DomError;", "(Landroidx/credentials/exceptions/domerrors/DomError;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final /* synthetic */ <T> T generateDomException$credentials_release(String type, String prefix, String msg, T t) throws FrameworkClassParsingException {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(prefix, "prefix");
            if (Intrinsics.areEqual(type, prefix + AbortError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_ABORT_ERROR)) {
                return (T) generateException(new AbortError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + ConstraintError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_CONSTRAINT_ERROR)) {
                return (T) generateException(new ConstraintError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + DataCloneError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_DATA_CLONE_ERROR)) {
                return (T) generateException(new DataCloneError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + DataError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_DATA_ERROR)) {
                return (T) generateException(new DataError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + EncodingError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_ENCODING_ERROR)) {
                return (T) generateException(new EncodingError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + HierarchyRequestError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_HIERARCHY_REQUEST_ERROR)) {
                return (T) generateException(new HierarchyRequestError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + InUseAttributeError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_IN_USE_ATTRIBUTE_ERROR)) {
                return (T) generateException(new InUseAttributeError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + InvalidCharacterError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_INVALID_CHARACTER_ERROR)) {
                return (T) generateException(new InvalidCharacterError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + InvalidModificationError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_INVALID_MODIFICATION_ERROR)) {
                return (T) generateException(new InvalidModificationError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + InvalidNodeTypeError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_INVALID_NODE_TYPE_ERROR)) {
                return (T) generateException(new InvalidNodeTypeError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + InvalidStateError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_INVALID_STATE_ERROR)) {
                return (T) generateException(new InvalidStateError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NamespaceError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NAMESPACE_ERROR)) {
                return (T) generateException(new NamespaceError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NetworkError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NETWORK_ERROR)) {
                return (T) generateException(new NetworkError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NoModificationAllowedError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NO_MODIFICATION_ALLOWED_ERROR)) {
                return (T) generateException(new NoModificationAllowedError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NotAllowedError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NOT_ALLOWED_ERROR)) {
                return (T) generateException(new NotAllowedError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NotFoundError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NOT_FOUND_ERROR)) {
                return (T) generateException(new NotFoundError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NotReadableError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NOT_READABLE_ERROR)) {
                return (T) generateException(new NotReadableError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + NotSupportedError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_NOT_SUPPORTED_ERROR)) {
                return (T) generateException(new NotSupportedError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + OperationError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_OPERATION_ERROR)) {
                return (T) generateException(new OperationError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + OptOutError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_OPT_OUT_ERROR)) {
                return (T) generateException(new OptOutError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + QuotaExceededError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_QUOTA_EXCEEDED_ERROR)) {
                return (T) generateException(new QuotaExceededError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + ReadOnlyError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_READ_ONLY_ERROR)) {
                return (T) generateException(new ReadOnlyError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + SecurityError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_SECURITY_ERROR)) {
                return (T) generateException(new SecurityError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + SyntaxError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_SYNTAX_ERROR)) {
                return (T) generateException(new SyntaxError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + TimeoutError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_TIMEOUT_ERROR)) {
                return (T) generateException(new TimeoutError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + TransactionInactiveError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_TRANSACTION_INACTIVE_ERROR)) {
                return (T) generateException(new TransactionInactiveError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + UnknownError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_UNKNOWN_ERROR)) {
                return (T) generateException(new UnknownError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + VersionError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_VERSION_ERROR)) {
                return (T) generateException(new VersionError(), msg, t);
            }
            if (Intrinsics.areEqual(type, prefix + WrongDocumentError.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_WRONG_DOCUMENT_ERROR)) {
                return (T) generateException(new WrongDocumentError(), msg, t);
            }
            throw new FrameworkClassParsingException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final <T> T generateException(DomError domError, String msg, T t) throws FrameworkClassParsingException {
            if (t instanceof CreatePublicKeyCredentialDomException) {
                return (T) new CreatePublicKeyCredentialDomException(domError, msg);
            }
            if (t instanceof GetPublicKeyCredentialDomException) {
                return (T) new GetPublicKeyCredentialDomException(domError, msg);
            }
            throw new FrameworkClassParsingException();
        }
    }
}
