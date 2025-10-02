package androidx.credentials.provider;

import android.os.Bundle;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BeginGetCustomCredentialOption.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/BeginGetCustomCredentialOption;", "Landroidx/credentials/provider/BeginGetCredentialOption;", TtmlNode.ATTR_ID, "", SessionDescription.ATTR_TYPE, "candidateQueryData", "Landroid/os/Bundle;", "(Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;)V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class BeginGetCustomCredentialOption extends BeginGetCredentialOption {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BeginGetCustomCredentialOption(String id, String type, Bundle candidateQueryData) {
        super(id, type, candidateQueryData);
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        if (!(id.length() > 0)) {
            throw new IllegalArgumentException("id should not be empty".toString());
        }
        if (!(type.length() > 0)) {
            throw new IllegalArgumentException("type should not be empty".toString());
        }
    }

    /* compiled from: BeginGetCustomCredentialOption.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0001¢\u0006\u0002\b\nJ%\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0001¢\u0006\u0002\b\f¨\u0006\r"}, d2 = {"Landroidx/credentials/provider/BeginGetCustomCredentialOption$Companion;", "", "()V", "createFrom", "Landroidx/credentials/provider/BeginGetCustomCredentialOption;", "data", "Landroid/os/Bundle;", TtmlNode.ATTR_ID, "", SessionDescription.ATTR_TYPE, "createFrom$credentials_release", "createFromEntrySlice", "createFromEntrySlice$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BeginGetCustomCredentialOption createFrom$credentials_release(Bundle data, String id, String type) {
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(type, "type");
            return new BeginGetCustomCredentialOption(id, type, data);
        }

        @JvmStatic
        public final BeginGetCustomCredentialOption createFromEntrySlice$credentials_release(Bundle data, String id, String type) {
            Intrinsics.checkNotNullParameter(data, "data");
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(type, "type");
            return new BeginGetCustomCredentialOption(id, type, data);
        }
    }
}
