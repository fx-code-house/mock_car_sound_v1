package com.thor.networkmodule.model.notifications;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: NotificationType.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, d2 = {"Lcom/thor/networkmodule/model/notifications/NotificationType;", "", "value", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "TYPE_INFORMATION", "TYPE_SOUND_PACKAGE", "TYPE_FIRMWARE", "Companion", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public enum NotificationType {
    TYPE_INFORMATION(TtmlNode.TAG_INFORMATION),
    TYPE_SOUND_PACKAGE("soundPack"),
    TYPE_FIRMWARE("firmware");


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<String, NotificationType> valuesMap;
    private final String value;

    NotificationType(String str) {
        this.value = str;
    }

    public final String getValue() {
        return this.value;
    }

    /* compiled from: NotificationType.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/thor/networkmodule/model/notifications/NotificationType$Companion;", "", "()V", "valuesMap", "", "", "Lcom/thor/networkmodule/model/notifications/NotificationType;", "fromString", "value", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NotificationType fromString(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            NotificationType notificationType = (NotificationType) NotificationType.valuesMap.get(value);
            if (notificationType != null) {
                return notificationType;
            }
            throw new IllegalStateException(("Invalid value " + value + " for NotificationType").toString());
        }
    }

    static {
        NotificationType[] notificationTypeArrValues = values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(notificationTypeArrValues.length), 16));
        for (NotificationType notificationType : notificationTypeArrValues) {
            linkedHashMap.put(notificationType.value, notificationType);
        }
        valuesMap = linkedHashMap;
    }
}
