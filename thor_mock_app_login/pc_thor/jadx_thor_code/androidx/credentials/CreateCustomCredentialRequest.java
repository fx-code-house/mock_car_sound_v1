package androidx.credentials;

import android.os.Bundle;
import androidx.credentials.CreateCredentialRequest;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreateCustomCredentialRequest.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001BO\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\b¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Landroidx/credentials/CreateCustomCredentialRequest;", "Landroidx/credentials/CreateCredentialRequest;", SessionDescription.ATTR_TYPE, "", "credentialData", "Landroid/os/Bundle;", "candidateQueryData", "isSystemProviderRequired", "", "displayInfo", "Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "isAutoSelectAllowed", "origin", "preferImmediatelyAvailableCredentials", "(Ljava/lang/String;Landroid/os/Bundle;Landroid/os/Bundle;ZLandroidx/credentials/CreateCredentialRequest$DisplayInfo;ZLjava/lang/String;Z)V", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class CreateCustomCredentialRequest extends CreateCredentialRequest {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreateCustomCredentialRequest(String type, Bundle credentialData, Bundle candidateQueryData, boolean z, CreateCredentialRequest.DisplayInfo displayInfo) {
        this(type, credentialData, candidateQueryData, z, displayInfo, false, null, false, 224, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(credentialData, "credentialData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(displayInfo, "displayInfo");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreateCustomCredentialRequest(String type, Bundle credentialData, Bundle candidateQueryData, boolean z, CreateCredentialRequest.DisplayInfo displayInfo, boolean z2) {
        this(type, credentialData, candidateQueryData, z, displayInfo, z2, null, false, PsExtractor.AUDIO_STREAM, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(credentialData, "credentialData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(displayInfo, "displayInfo");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreateCustomCredentialRequest(String type, Bundle credentialData, Bundle candidateQueryData, boolean z, CreateCredentialRequest.DisplayInfo displayInfo, boolean z2, String str) {
        this(type, credentialData, candidateQueryData, z, displayInfo, z2, str, false, 128, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(credentialData, "credentialData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(displayInfo, "displayInfo");
    }

    public /* synthetic */ CreateCustomCredentialRequest(String str, Bundle bundle, Bundle bundle2, boolean z, CreateCredentialRequest.DisplayInfo displayInfo, boolean z2, String str2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bundle, bundle2, z, displayInfo, (i & 32) != 0 ? false : z2, (i & 64) != 0 ? null : str2, (i & 128) != 0 ? false : z3);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreateCustomCredentialRequest(String type, Bundle credentialData, Bundle candidateQueryData, boolean z, CreateCredentialRequest.DisplayInfo displayInfo, boolean z2, String str, boolean z3) {
        super(type, credentialData, candidateQueryData, z, z2, displayInfo, str, z3);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(credentialData, "credentialData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(displayInfo, "displayInfo");
        if (!(type.length() > 0)) {
            throw new IllegalArgumentException("type should not be empty".toString());
        }
    }
}
