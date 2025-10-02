package androidx.credentials.provider;

import android.os.Bundle;
import androidx.credentials.GetPasswordOption;
import androidx.credentials.PasswordCredential;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BeginGetPasswordOption.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004¢\u0006\u0002\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Landroidx/credentials/provider/BeginGetPasswordOption;", "Landroidx/credentials/provider/BeginGetCredentialOption;", "allowedUserIds", "", "", "candidateQueryData", "Landroid/os/Bundle;", TtmlNode.ATTR_ID, "(Ljava/util/Set;Landroid/os/Bundle;Ljava/lang/String;)V", "getAllowedUserIds", "()Ljava/util/Set;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class BeginGetPasswordOption extends BeginGetCredentialOption {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Set<String> allowedUserIds;

    @JvmStatic
    public static final BeginGetPasswordOption createForTest(Bundle bundle, String str) {
        return INSTANCE.createForTest(bundle, str);
    }

    public final Set<String> getAllowedUserIds() {
        return this.allowedUserIds;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BeginGetPasswordOption(Set<String> allowedUserIds, Bundle candidateQueryData, String id) {
        super(id, PasswordCredential.TYPE_PASSWORD_CREDENTIAL, candidateQueryData);
        Intrinsics.checkNotNullParameter(allowedUserIds, "allowedUserIds");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(id, "id");
        this.allowedUserIds = allowedUserIds;
    }

    /* compiled from: BeginGetPasswordOption.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\n¨\u0006\u000b"}, d2 = {"Landroidx/credentials/provider/BeginGetPasswordOption$Companion;", "", "()V", "createForTest", "Landroidx/credentials/provider/BeginGetPasswordOption;", "data", "Landroid/os/Bundle;", TtmlNode.ATTR_ID, "", "createFrom", "createFrom$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BeginGetPasswordOption createForTest(Bundle data, String id) {
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(id, "id");
            return createFrom$credentials_release(data, id);
        }

        public final BeginGetPasswordOption createFrom$credentials_release(Bundle data, String id) {
            Set setEmptySet;
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(id, "id");
            ArrayList<String> stringArrayList = data.getStringArrayList(GetPasswordOption.BUNDLE_KEY_ALLOWED_USER_IDS);
            if (stringArrayList == null || (setEmptySet = CollectionsKt.toSet(stringArrayList)) == null) {
                setEmptySet = SetsKt.emptySet();
            }
            return new BeginGetPasswordOption(setEmptySet, data, id);
        }
    }
}
