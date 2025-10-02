package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzew extends zzee<Object> {
    private final transient int offset;
    private final transient int size;
    private final transient Object[] zznd;

    zzew(Object[] objArr, int i, int i2) {
        this.zznd = objArr;
        this.offset = i;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.vision.zzeb
    final boolean zzcu() {
        return true;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzde.zzd(i, this.size);
        return this.zznd[(i * 2) + this.offset];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }
}
