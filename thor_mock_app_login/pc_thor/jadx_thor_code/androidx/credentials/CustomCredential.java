package androidx.credentials;

import android.os.Bundle;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CustomCredential.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Landroidx/credentials/CustomCredential;", "Landroidx/credentials/Credential;", SessionDescription.ATTR_TYPE, "", "data", "Landroid/os/Bundle;", "(Ljava/lang/String;Landroid/os/Bundle;)V", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public class CustomCredential extends Credential {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomCredential(String type, Bundle data) {
        super(type, data);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!(type.length() > 0)) {
            throw new IllegalArgumentException("type should not be empty".toString());
        }
    }
}
