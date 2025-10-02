package com.carsystems.thor.datalayermodule.datalayer.models;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadingSoundPackage.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0003H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/carsystems/thor/datalayermodule/datalayer/models/UploadingSoundPackage;", "Ljava/io/Serializable;", AppMeasurementSdk.ConditionalUserProperty.NAME, "", NotificationCompat.CATEGORY_PROGRESS, "", "(Ljava/lang/String;I)V", "getName", "()Ljava/lang/String;", "getProgress", "()I", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "datalayermodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class UploadingSoundPackage implements Serializable {
    private final String name;
    private final int progress;

    /* JADX WARN: Multi-variable type inference failed */
    public UploadingSoundPackage() {
        this(null, 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ UploadingSoundPackage copy$default(UploadingSoundPackage uploadingSoundPackage, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = uploadingSoundPackage.name;
        }
        if ((i2 & 2) != 0) {
            i = uploadingSoundPackage.progress;
        }
        return uploadingSoundPackage.copy(str, i);
    }

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final int getProgress() {
        return this.progress;
    }

    public final UploadingSoundPackage copy(String name, int progress) {
        return new UploadingSoundPackage(name, progress);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UploadingSoundPackage)) {
            return false;
        }
        UploadingSoundPackage uploadingSoundPackage = (UploadingSoundPackage) other;
        return Intrinsics.areEqual(this.name, uploadingSoundPackage.name) && this.progress == uploadingSoundPackage.progress;
    }

    public int hashCode() {
        String str = this.name;
        return ((str == null ? 0 : str.hashCode()) * 31) + Integer.hashCode(this.progress);
    }

    public UploadingSoundPackage(String str, int i) {
        this.name = str;
        this.progress = i;
    }

    public /* synthetic */ UploadingSoundPackage(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : str, (i2 & 2) != 0 ? 0 : i);
    }

    public final String getName() {
        return this.name;
    }

    public final int getProgress() {
        return this.progress;
    }

    public String toString() {
        return "UploadingSoundPackage(name=" + this.name + ", progress=" + this.progress + ")";
    }
}
