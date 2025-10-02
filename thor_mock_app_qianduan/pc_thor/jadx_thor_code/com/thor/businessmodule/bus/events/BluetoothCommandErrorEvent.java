package com.thor.businessmodule.bus.events;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BluetoothCommandErrorEvent.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/thor/businessmodule/bus/events/BluetoothCommandErrorEvent;", "", "bytesError", "", "isTrash", "", "isForUploading", "([BZZ)V", "getBytesError", "()[B", "()Z", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BluetoothCommandErrorEvent {
    private final byte[] bytesError;
    private final boolean isForUploading;
    private final boolean isTrash;

    public BluetoothCommandErrorEvent(byte[] bytesError, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(bytesError, "bytesError");
        this.bytesError = bytesError;
        this.isTrash = z;
        this.isForUploading = z2;
    }

    public /* synthetic */ BluetoothCommandErrorEvent(byte[] bArr, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bArr, z, (i & 4) != 0 ? false : z2);
    }

    public final byte[] getBytesError() {
        return this.bytesError;
    }

    /* renamed from: isForUploading, reason: from getter */
    public final boolean getIsForUploading() {
        return this.isForUploading;
    }

    /* renamed from: isTrash, reason: from getter */
    public final boolean getIsTrash() {
        return this.isTrash;
    }
}
