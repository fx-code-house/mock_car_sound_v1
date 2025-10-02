package kotlin.collections;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UArraySorting.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\t\u0010\n\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\f\u0010\r\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0013\u0010\u0014\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0015\u0010\u0016\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u0014\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0016\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0018\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", TtmlNode.LEFT, TtmlNode.RIGHT, "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m1080partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM700getw2LRezQ = UByteArray.m700getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = bM700getw2LRezQ & 255;
                if (Intrinsics.compare(UByteArray.m700getw2LRezQ(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m700getw2LRezQ(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM700getw2LRezQ2 = UByteArray.m700getw2LRezQ(bArr, i);
                UByteArray.m705setVurrAj0(bArr, i, UByteArray.m700getw2LRezQ(bArr, i2));
                UByteArray.m705setVurrAj0(bArr, i2, bM700getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m1084quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM1080partition4UcCI2c = m1080partition4UcCI2c(bArr, i, i2);
        int i3 = iM1080partition4UcCI2c - 1;
        if (i < i3) {
            m1084quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM1080partition4UcCI2c < i2) {
            m1084quickSort4UcCI2c(bArr, iM1080partition4UcCI2c, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m1081partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM963getMh2AYeg = UShortArray.m963getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = sM963getMh2AYeg & 65535;
                if (Intrinsics.compare(UShortArray.m963getMh2AYeg(sArr, i) & 65535, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m963getMh2AYeg(sArr, i2) & 65535, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM963getMh2AYeg2 = UShortArray.m963getMh2AYeg(sArr, i);
                UShortArray.m968set01HTLdE(sArr, i, UShortArray.m963getMh2AYeg(sArr, i2));
                UShortArray.m968set01HTLdE(sArr, i2, sM963getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m1085quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM1081partitionAa5vz7o = m1081partitionAa5vz7o(sArr, i, i2);
        int i3 = iM1081partitionAa5vz7o - 1;
        if (i < i3) {
            m1085quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM1081partitionAa5vz7o < i2) {
            m1085quickSortAa5vz7o(sArr, iM1081partitionAa5vz7o, i2);
        }
    }

    /* renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m1082partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM779getpVg5ArA = UIntArray.m779getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Integer.compareUnsigned(UIntArray.m779getpVg5ArA(iArr, i), iM779getpVg5ArA) < 0) {
                i++;
            }
            while (Integer.compareUnsigned(UIntArray.m779getpVg5ArA(iArr, i2), iM779getpVg5ArA) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM779getpVg5ArA2 = UIntArray.m779getpVg5ArA(iArr, i);
                UIntArray.m784setVXSXFK8(iArr, i, UIntArray.m779getpVg5ArA(iArr, i2));
                UIntArray.m784setVXSXFK8(iArr, i2, iM779getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m1086quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM1082partitionoBK06Vg = m1082partitionoBK06Vg(iArr, i, i2);
        int i3 = iM1082partitionoBK06Vg - 1;
        if (i < i3) {
            m1086quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM1082partitionoBK06Vg < i2) {
            m1086quickSortoBK06Vg(iArr, iM1082partitionoBK06Vg, i2);
        }
    }

    /* renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m1079partitionnroSd4(long[] jArr, int i, int i2) {
        long jM858getsVKNKU = ULongArray.m858getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (Long.compareUnsigned(ULongArray.m858getsVKNKU(jArr, i), jM858getsVKNKU) < 0) {
                i++;
            }
            while (Long.compareUnsigned(ULongArray.m858getsVKNKU(jArr, i2), jM858getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM858getsVKNKU2 = ULongArray.m858getsVKNKU(jArr, i);
                ULongArray.m863setk8EXiF4(jArr, i, ULongArray.m858getsVKNKU(jArr, i2));
                ULongArray.m863setk8EXiF4(jArr, i2, jM858getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m1083quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM1079partitionnroSd4 = m1079partitionnroSd4(jArr, i, i2);
        int i3 = iM1079partitionnroSd4 - 1;
        if (i < i3) {
            m1083quickSortnroSd4(jArr, i, i3);
        }
        if (iM1079partitionnroSd4 < i2) {
            m1083quickSortnroSd4(jArr, iM1079partitionnroSd4, i2);
        }
    }

    /* renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m1088sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1084quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m1089sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1085quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m1090sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1086quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m1087sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m1083quickSortnroSd4(array, i, i2 - 1);
    }
}
