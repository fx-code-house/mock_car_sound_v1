package androidx.credentials.webauthn;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FidoDataTypes.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Landroidx/credentials/webauthn/PublicKeyCredentialRpEntity;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", TtmlNode.ATTR_ID, "(Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getName", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class PublicKeyCredentialRpEntity {
    private final String id;
    private final String name;

    public static /* synthetic */ PublicKeyCredentialRpEntity copy$default(PublicKeyCredentialRpEntity publicKeyCredentialRpEntity, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = publicKeyCredentialRpEntity.name;
        }
        if ((i & 2) != 0) {
            str2 = publicKeyCredentialRpEntity.id;
        }
        return publicKeyCredentialRpEntity.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final String getId() {
        return this.id;
    }

    public final PublicKeyCredentialRpEntity copy(String name, String id) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(id, "id");
        return new PublicKeyCredentialRpEntity(name, id);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PublicKeyCredentialRpEntity)) {
            return false;
        }
        PublicKeyCredentialRpEntity publicKeyCredentialRpEntity = (PublicKeyCredentialRpEntity) other;
        return Intrinsics.areEqual(this.name, publicKeyCredentialRpEntity.name) && Intrinsics.areEqual(this.id, publicKeyCredentialRpEntity.id);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.id.hashCode();
    }

    public String toString() {
        return "PublicKeyCredentialRpEntity(name=" + this.name + ", id=" + this.id + ')';
    }

    public PublicKeyCredentialRpEntity(String name, String id) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(id, "id");
        this.name = name;
        this.id = id;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }
}
