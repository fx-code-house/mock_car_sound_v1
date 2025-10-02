package androidx.credentials.webauthn;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FidoDataTypes.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0017"}, d2 = {"Landroidx/credentials/webauthn/PublicKeyCredentialUserEntity;", "", AppMeasurementSdk.ConditionalUserProperty.NAME, "", TtmlNode.ATTR_ID, "", "displayName", "(Ljava/lang/String;[BLjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "getId", "()[B", "getName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class PublicKeyCredentialUserEntity {
    private final String displayName;
    private final byte[] id;
    private final String name;

    public static /* synthetic */ PublicKeyCredentialUserEntity copy$default(PublicKeyCredentialUserEntity publicKeyCredentialUserEntity, String str, byte[] bArr, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = publicKeyCredentialUserEntity.name;
        }
        if ((i & 2) != 0) {
            bArr = publicKeyCredentialUserEntity.id;
        }
        if ((i & 4) != 0) {
            str2 = publicKeyCredentialUserEntity.displayName;
        }
        return publicKeyCredentialUserEntity.copy(str, bArr, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final byte[] getId() {
        return this.id;
    }

    /* renamed from: component3, reason: from getter */
    public final String getDisplayName() {
        return this.displayName;
    }

    public final PublicKeyCredentialUserEntity copy(String name, byte[] id, String displayName) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        return new PublicKeyCredentialUserEntity(name, id, displayName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PublicKeyCredentialUserEntity)) {
            return false;
        }
        PublicKeyCredentialUserEntity publicKeyCredentialUserEntity = (PublicKeyCredentialUserEntity) other;
        return Intrinsics.areEqual(this.name, publicKeyCredentialUserEntity.name) && Intrinsics.areEqual(this.id, publicKeyCredentialUserEntity.id) && Intrinsics.areEqual(this.displayName, publicKeyCredentialUserEntity.displayName);
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + Arrays.hashCode(this.id)) * 31) + this.displayName.hashCode();
    }

    public String toString() {
        return "PublicKeyCredentialUserEntity(name=" + this.name + ", id=" + Arrays.toString(this.id) + ", displayName=" + this.displayName + ')';
    }

    public PublicKeyCredentialUserEntity(String name, byte[] id, String displayName) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        this.name = name;
        this.id = id;
        this.displayName = displayName;
    }

    public final String getName() {
        return this.name;
    }

    public final byte[] getId() {
        return this.id;
    }

    public final String getDisplayName() {
        return this.displayName;
    }
}
