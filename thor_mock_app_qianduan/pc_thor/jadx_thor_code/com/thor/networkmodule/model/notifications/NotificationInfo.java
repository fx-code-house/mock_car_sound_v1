package com.thor.networkmodule.model.notifications;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationInfo.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b(\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0005HÆ\u0003J\t\u0010+\u001a\u00020\u0005HÆ\u0003J\t\u0010,\u001a\u00020\u0005HÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003J\t\u0010.\u001a\u00020\nHÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\fHÆ\u0003J\t\u00100\u001a\u00020\u000eHÆ\u0003J[\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001J\u0013\u00102\u001a\u00020\n2\b\u00103\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00104\u001a\u00020\u0003HÖ\u0001J\t\u00105\u001a\u00020\u0005HÖ\u0001R \u0010\u000b\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\b\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0007\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001e\u0010\u0006\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0015\"\u0004\b\u001e\u0010\u0017R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001e\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0015\"\u0004\b(\u0010\u0017¨\u00066"}, d2 = {"Lcom/thor/networkmodule/model/notifications/NotificationInfo;", "", "notificationId", "", "title", "", "message", "image", "format", "isRead", "", "details", "Lcom/thor/networkmodule/model/notifications/NotificationDetails;", "notificationType", "Lcom/thor/networkmodule/model/notifications/NotificationType;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLcom/thor/networkmodule/model/notifications/NotificationDetails;Lcom/thor/networkmodule/model/notifications/NotificationType;)V", "getDetails", "()Lcom/thor/networkmodule/model/notifications/NotificationDetails;", "setDetails", "(Lcom/thor/networkmodule/model/notifications/NotificationDetails;)V", "getFormat", "()Ljava/lang/String;", "setFormat", "(Ljava/lang/String;)V", "getImage", "setImage", "()Z", "setRead", "(Z)V", "getMessage", "setMessage", "getNotificationId", "()I", "setNotificationId", "(I)V", "getNotificationType", "()Lcom/thor/networkmodule/model/notifications/NotificationType;", "setNotificationType", "(Lcom/thor/networkmodule/model/notifications/NotificationType;)V", "getTitle", "setTitle", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class NotificationInfo {

    @SerializedName("details")
    private NotificationDetails details;

    @SerializedName("format")
    private String format;

    @SerializedName("image")
    private String image;

    @SerializedName("isRead")
    private boolean isRead;

    @SerializedName("message")
    private String message;

    @SerializedName("notificationId")
    private int notificationId;

    @SerializedName(SessionDescription.ATTR_TYPE)
    private NotificationType notificationType;

    @SerializedName("title")
    private String title;

    /* renamed from: component1, reason: from getter */
    public final int getNotificationId() {
        return this.notificationId;
    }

    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* renamed from: component4, reason: from getter */
    public final String getImage() {
        return this.image;
    }

    /* renamed from: component5, reason: from getter */
    public final String getFormat() {
        return this.format;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getIsRead() {
        return this.isRead;
    }

    /* renamed from: component7, reason: from getter */
    public final NotificationDetails getDetails() {
        return this.details;
    }

    /* renamed from: component8, reason: from getter */
    public final NotificationType getNotificationType() {
        return this.notificationType;
    }

    public final NotificationInfo copy(int notificationId, String title, String message, String image, String format, boolean isRead, NotificationDetails details, NotificationType notificationType) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(notificationType, "notificationType");
        return new NotificationInfo(notificationId, title, message, image, format, isRead, details, notificationType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NotificationInfo)) {
            return false;
        }
        NotificationInfo notificationInfo = (NotificationInfo) other;
        return this.notificationId == notificationInfo.notificationId && Intrinsics.areEqual(this.title, notificationInfo.title) && Intrinsics.areEqual(this.message, notificationInfo.message) && Intrinsics.areEqual(this.image, notificationInfo.image) && Intrinsics.areEqual(this.format, notificationInfo.format) && this.isRead == notificationInfo.isRead && Intrinsics.areEqual(this.details, notificationInfo.details) && this.notificationType == notificationInfo.notificationType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((Integer.hashCode(this.notificationId) * 31) + this.title.hashCode()) * 31) + this.message.hashCode()) * 31) + this.image.hashCode()) * 31) + this.format.hashCode()) * 31;
        boolean z = this.isRead;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (iHashCode + i) * 31;
        NotificationDetails notificationDetails = this.details;
        return ((i2 + (notificationDetails == null ? 0 : notificationDetails.hashCode())) * 31) + this.notificationType.hashCode();
    }

    public String toString() {
        return "NotificationInfo(notificationId=" + this.notificationId + ", title=" + this.title + ", message=" + this.message + ", image=" + this.image + ", format=" + this.format + ", isRead=" + this.isRead + ", details=" + this.details + ", notificationType=" + this.notificationType + ")";
    }

    public NotificationInfo(int i, String title, String message, String image, String format, boolean z, NotificationDetails notificationDetails, NotificationType notificationType) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(notificationType, "notificationType");
        this.notificationId = i;
        this.title = title;
        this.message = message;
        this.image = image;
        this.format = format;
        this.isRead = z;
        this.details = notificationDetails;
        this.notificationType = notificationType;
    }

    public /* synthetic */ NotificationInfo(int i, String str, String str2, String str3, String str4, boolean z, NotificationDetails notificationDetails, NotificationType notificationType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? "" : str4, (i2 & 32) != 0 ? false : z, notificationDetails, notificationType);
    }

    public final int getNotificationId() {
        return this.notificationId;
    }

    public final void setNotificationId(int i) {
        this.notificationId = i;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.message = str;
    }

    public final String getImage() {
        return this.image;
    }

    public final void setImage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.image = str;
    }

    public final String getFormat() {
        return this.format;
    }

    public final void setFormat(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.format = str;
    }

    public final boolean isRead() {
        return this.isRead;
    }

    public final void setRead(boolean z) {
        this.isRead = z;
    }

    public final NotificationDetails getDetails() {
        return this.details;
    }

    public final void setDetails(NotificationDetails notificationDetails) {
        this.details = notificationDetails;
    }

    public final NotificationType getNotificationType() {
        return this.notificationType;
    }

    public final void setNotificationType(NotificationType notificationType) {
        Intrinsics.checkNotNullParameter(notificationType, "<set-?>");
        this.notificationType = notificationType;
    }
}
