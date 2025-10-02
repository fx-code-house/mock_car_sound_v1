package androidx.credentials.provider;

import android.app.slice.Slice;
import android.app.slice.SliceSpec;
import androidx.credentials.PasswordCredential;
import androidx.credentials.PublicKeyCredential;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CredentialEntry.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0017X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Landroidx/credentials/provider/CredentialEntry;", "", SessionDescription.ATTR_TYPE, "", "beginGetCredentialOption", "Landroidx/credentials/provider/BeginGetCredentialOption;", "(Ljava/lang/String;Landroidx/credentials/provider/BeginGetCredentialOption;)V", "getBeginGetCredentialOption", "()Landroidx/credentials/provider/BeginGetCredentialOption;", "getType", "()Ljava/lang/String;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class CredentialEntry {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BeginGetCredentialOption beginGetCredentialOption;
    private final String type;

    public CredentialEntry(String type, BeginGetCredentialOption beginGetCredentialOption) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(beginGetCredentialOption, "beginGetCredentialOption");
        this.type = type;
        this.beginGetCredentialOption = beginGetCredentialOption;
    }

    public String getType() {
        return this.type;
    }

    public final BeginGetCredentialOption getBeginGetCredentialOption() {
        return this.beginGetCredentialOption;
    }

    /* compiled from: CredentialEntry.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0001¢\u0006\u0002\b\u0007J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u0004H\u0001¢\u0006\u0002\b\n¨\u0006\u000b"}, d2 = {"Landroidx/credentials/provider/CredentialEntry$Companion;", "", "()V", "createFrom", "Landroidx/credentials/provider/CredentialEntry;", "slice", "Landroid/app/slice/Slice;", "createFrom$credentials_release", "toSlice", "entry", "toSlice$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CredentialEntry createFrom$credentials_release(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            try {
                SliceSpec spec = slice.getSpec();
                String type = spec != null ? spec.getType() : null;
                if (Intrinsics.areEqual(type, PasswordCredential.TYPE_PASSWORD_CREDENTIAL)) {
                    PasswordCredentialEntry passwordCredentialEntryFromSlice = PasswordCredentialEntry.INSTANCE.fromSlice(slice);
                    Intrinsics.checkNotNull(passwordCredentialEntryFromSlice);
                    return passwordCredentialEntryFromSlice;
                }
                if (Intrinsics.areEqual(type, PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL)) {
                    PublicKeyCredentialEntry publicKeyCredentialEntryFromSlice = PublicKeyCredentialEntry.INSTANCE.fromSlice(slice);
                    Intrinsics.checkNotNull(publicKeyCredentialEntryFromSlice);
                    return publicKeyCredentialEntryFromSlice;
                }
                CustomCredentialEntry customCredentialEntryFromSlice = CustomCredentialEntry.INSTANCE.fromSlice(slice);
                Intrinsics.checkNotNull(customCredentialEntryFromSlice);
                return customCredentialEntryFromSlice;
            } catch (Exception unused) {
                return CustomCredentialEntry.INSTANCE.fromSlice(slice);
            }
        }

        @JvmStatic
        public final Slice toSlice$credentials_release(CredentialEntry entry) {
            Intrinsics.checkNotNullParameter(entry, "entry");
            if (entry instanceof PasswordCredentialEntry) {
                return PasswordCredentialEntry.INSTANCE.toSlice((PasswordCredentialEntry) entry);
            }
            if (entry instanceof PublicKeyCredentialEntry) {
                return PublicKeyCredentialEntry.INSTANCE.toSlice((PublicKeyCredentialEntry) entry);
            }
            if (entry instanceof CustomCredentialEntry) {
                return CustomCredentialEntry.INSTANCE.toSlice((CustomCredentialEntry) entry);
            }
            return null;
        }
    }
}
