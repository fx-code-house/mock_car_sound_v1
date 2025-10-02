package com.google.android.gms.internal.wearable;

import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
final class zzee extends zzef {
    zzee(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zza(Object obj, long j, byte b) {
        if (zzeg.zzb) {
            zzeg.zzD(obj, j, b);
        } else {
            zzeg.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final boolean zzb(Object obj, long j) {
        return zzeg.zzb ? zzeg.zzv(obj, j) : zzeg.zzw(obj, j);
    }

    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.wearable.zzeg.zzx(java.lang.Object, long, boolean):void */
    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.wearable.zzeg.zzy(java.lang.Object, long, boolean):void */
    /* JADX WARN: Method inline failed with exception
    java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 2
    	at java.base/java.util.ArrayList.shiftTailOverGap(ArrayList.java:831)
    	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1782)
    	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1751)
    	at jadx.core.dex.instructions.args.SSAVar.removeUse(SSAVar.java:139)
    	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:170)
    	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:137)
    	at jadx.core.dex.regions.conditions.IfCondition.replaceArg(IfCondition.java:270)
    	at jadx.core.dex.instructions.mods.TernaryInsn.replaceArg(TernaryInsn.java:67)
    	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:141)
    	at jadx.core.dex.visitors.InlineMethods.replaceRegs(InlineMethods.java:127)
    	at jadx.core.dex.visitors.InlineMethods.inlineMethod(InlineMethods.java:86)
    	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:78)
    	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:50)
     */
    /* JADX WARN: Unknown register number '(r5v0 'z' boolean)' in method call: com.google.android.gms.internal.wearable.zzeg.zzy(java.lang.Object, long, boolean):void */
    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zzc(Object obj, long j, boolean z) {
        if (zzeg.zzb) {
            zzeg.zzx(obj, j, z);
        } else {
            zzeg.zzy(obj, j, z);
        }
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final float zzd(Object obj, long j) {
        return Float.intBitsToFloat(zzk(obj, j));
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zze(Object obj, long j, float f) {
        zzl(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final double zzf(Object obj, long j) {
        return Double.longBitsToDouble(zzm(obj, j));
    }

    @Override // com.google.android.gms.internal.wearable.zzef
    public final void zzg(Object obj, long j, double d) {
        zzn(obj, j, Double.doubleToLongBits(d));
    }
}
