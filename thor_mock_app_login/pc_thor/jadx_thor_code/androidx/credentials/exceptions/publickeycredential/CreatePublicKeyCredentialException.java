package androidx.credentials.exceptions.publickeycredential;

import androidx.core.app.NotificationCompat;
import androidx.credentials.exceptions.CreateCredentialCustomException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.internal.FrameworkClassParsingException;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: CreatePublicKeyCredentialException.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0002\b\u0005\b\u0016\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u001b\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0016\u0010\u0002\u001a\u00020\u00038\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Landroidx/credentials/exceptions/publickeycredential/CreatePublicKeyCredentialException;", "Landroidx/credentials/exceptions/CreateCredentialException;", SessionDescription.ATTR_TYPE, "", "errorMessage", "", "(Ljava/lang/String;Ljava/lang/CharSequence;)V", "getType", "()Ljava/lang/String;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class CreatePublicKeyCredentialException extends CreateCredentialException {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String type;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CreatePublicKeyCredentialException(String type) {
        this(type, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(type, "type");
    }

    @JvmStatic
    public static final CreateCredentialException createFrom(String str, String str2) {
        return INSTANCE.createFrom(str, str2);
    }

    public /* synthetic */ CreatePublicKeyCredentialException(String str, CharSequence charSequence, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : charSequence);
    }

    @Override // androidx.credentials.exceptions.CreateCredentialException
    public String getType() {
        return this.type;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreatePublicKeyCredentialException(String type, CharSequence charSequence) {
        super(type, charSequence);
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        if (!(getType().length() > 0)) {
            throw new IllegalArgumentException("type must not be empty".toString());
        }
    }

    /* compiled from: CreatePublicKeyCredentialException.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007¨\u0006\b"}, d2 = {"Landroidx/credentials/exceptions/publickeycredential/CreatePublicKeyCredentialException$Companion;", "", "()V", "createFrom", "Landroidx/credentials/exceptions/CreateCredentialException;", SessionDescription.ATTR_TYPE, "", NotificationCompat.CATEGORY_MESSAGE, "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CreateCredentialException createFrom(String type, String msg) throws FrameworkClassParsingException {
            Intrinsics.checkNotNullParameter(type, "type");
            try {
                if (StringsKt.contains$default((CharSequence) type, (CharSequence) CreatePublicKeyCredentialDomException.TYPE_CREATE_PUBLIC_KEY_CREDENTIAL_DOM_EXCEPTION, false, 2, (Object) null)) {
                    return CreatePublicKeyCredentialDomException.INSTANCE.createFrom(type, msg);
                }
                throw new FrameworkClassParsingException();
            } catch (FrameworkClassParsingException unused) {
                return new CreateCredentialCustomException(type, msg);
            }
        }
    }
}
