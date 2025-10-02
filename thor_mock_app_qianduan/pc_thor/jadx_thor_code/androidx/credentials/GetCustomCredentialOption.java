package androidx.credentials;

import android.content.ComponentName;
import android.os.Bundle;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GetCustomCredentialOption.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001BA\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Landroidx/credentials/GetCustomCredentialOption;", "Landroidx/credentials/CredentialOption;", SessionDescription.ATTR_TYPE, "", "requestData", "Landroid/os/Bundle;", "candidateQueryData", "isSystemProviderRequired", "", "isAutoSelectAllowed", "allowedProviders", "", "Landroid/content/ComponentName;", "(Ljava/lang/String;Landroid/os/Bundle;Landroid/os/Bundle;ZZLjava/util/Set;)V", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class GetCustomCredentialOption extends CredentialOption {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z) {
        this(type, requestData, candidateQueryData, z, false, null, 48, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z, boolean z2) {
        this(type, requestData, candidateQueryData, z, z2, null, 32, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
    }

    public /* synthetic */ GetCustomCredentialOption(String str, Bundle bundle, Bundle bundle2, boolean z, boolean z2, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bundle, bundle2, z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? SetsKt.emptySet() : set);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z, boolean z2, Set<ComponentName> allowedProviders) {
        super(type, requestData, candidateQueryData, z, z2, allowedProviders);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(allowedProviders, "allowedProviders");
        if (!(type.length() > 0)) {
            throw new IllegalArgumentException("type should not be empty".toString());
        }
    }
}
