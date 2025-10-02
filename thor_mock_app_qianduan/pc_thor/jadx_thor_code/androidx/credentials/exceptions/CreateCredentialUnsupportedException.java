package androidx.credentials.exceptions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: CreateCredentialUnsupportedException.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0003\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0013\b\u0007\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0006"}, d2 = {"Landroidx/credentials/exceptions/CreateCredentialUnsupportedException;", "Landroidx/credentials/exceptions/CreateCredentialException;", "errorMessage", "", "(Ljava/lang/CharSequence;)V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CreateCredentialUnsupportedException extends CreateCredentialException {
    public static final String TYPE_CREATE_CREDENTIAL_UNSUPPORTED_EXCEPTION = "androidx.credentials.TYPE_CREATE_CREDENTIAL_UNSUPPORTED_EXCEPTION";

    /* JADX WARN: Multi-variable type inference failed */
    public CreateCredentialUnsupportedException() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ CreateCredentialUnsupportedException(CharSequence charSequence, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : charSequence);
    }

    public CreateCredentialUnsupportedException(CharSequence charSequence) {
        super(TYPE_CREATE_CREDENTIAL_UNSUPPORTED_EXCEPTION, charSequence);
    }
}
