package com.thor.networkmodule.model.notifications;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationDetails.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lcom/thor/networkmodule/model/notifications/NotificationDetails;", "", "action", "", "idSoundPkg", "", "(Ljava/lang/String;I)V", "getAction", "()Ljava/lang/String;", "setAction", "(Ljava/lang/String;)V", "getIdSoundPkg", "()I", "setIdSoundPkg", "(I)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class NotificationDetails {

    @SerializedName("action")
    private String action;

    @SerializedName("id_sound_pkg")
    private int idSoundPkg;

    /* JADX WARN: Multi-variable type inference failed */
    public NotificationDetails() {
        this(null, 0, 3, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ NotificationDetails copy$default(NotificationDetails notificationDetails, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = notificationDetails.action;
        }
        if ((i2 & 2) != 0) {
            i = notificationDetails.idSoundPkg;
        }
        return notificationDetails.copy(str, i);
    }

    /* renamed from: component1, reason: from getter */
    public final String getAction() {
        return this.action;
    }

    /* renamed from: component2, reason: from getter */
    public final int getIdSoundPkg() {
        return this.idSoundPkg;
    }

    public final NotificationDetails copy(String action, int idSoundPkg) {
        Intrinsics.checkNotNullParameter(action, "action");
        return new NotificationDetails(action, idSoundPkg);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NotificationDetails)) {
            return false;
        }
        NotificationDetails notificationDetails = (NotificationDetails) other;
        return Intrinsics.areEqual(this.action, notificationDetails.action) && this.idSoundPkg == notificationDetails.idSoundPkg;
    }

    public int hashCode() {
        return (this.action.hashCode() * 31) + Integer.hashCode(this.idSoundPkg);
    }

    public String toString() {
        return "NotificationDetails(action=" + this.action + ", idSoundPkg=" + this.idSoundPkg + ")";
    }

    public NotificationDetails(String action, int i) {
        Intrinsics.checkNotNullParameter(action, "action");
        this.action = action;
        this.idSoundPkg = i;
    }

    public /* synthetic */ NotificationDetails(String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "" : str, (i2 & 2) != 0 ? 0 : i);
    }

    public final String getAction() {
        return this.action;
    }

    public final void setAction(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.action = str;
    }

    public final int getIdSoundPkg() {
        return this.idSoundPkg;
    }

    public final void setIdSoundPkg(int i) {
        this.idSoundPkg = i;
    }
}
