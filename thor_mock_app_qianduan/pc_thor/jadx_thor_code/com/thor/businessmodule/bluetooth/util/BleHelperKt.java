package com.thor.businessmodule.bluetooth.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;

/* compiled from: BleHelper.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0018\n\u0002\u0010\u0005\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u0003\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\b\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0005\u001a\n\u0010\n\u001a\u00020\u0002*\u00020\u0001\u001a\n\u0010\u000b\u001a\u00020\u0002*\u00020\u0001\u001a\n\u0010\f\u001a\u00020\u0005*\u00020\u0001\u001a\n\u0010\r\u001a\u00020\u000e*\u00020\u0001\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u000e*\u00020\u0001¨\u0006\u0012"}, d2 = {"decodeHex", "", "", "removeFirstNElements", "n", "", "toBitArray", "", "", "toByteArrayOneBit", "toHex", "toHexString", "toInt", "toLongLittleEndian", "", "toShortLittleEndian", "", "toUInt32", "businessmodule_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class BleHelperKt {
    public static final byte[] toByteArrayOneBit(int i) {
        return new byte[]{(byte) (i & 255)};
    }

    public static final String toHexString(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return ArraysKt.joinToString$default(bArr, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.thor.businessmodule.bluetooth.util.BleHelperKt.toHexString.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }

            public final CharSequence invoke(byte b) {
                String str = String.format("%02x", Byte.valueOf(b));
                Intrinsics.checkNotNullExpressionValue(str, "format(\"%02x\", it)");
                return str;
            }
        }, 30, (Object) null);
    }

    public static final byte[] decodeHex(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (StringsKt.startsWith$default(str, "0x", false, 2, (Object) null)) {
            str = str.substring(2);
            Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).substring(startIndex)");
        }
        if (!(str.length() % 2 == 0)) {
            throw new IllegalStateException("Must have an even length".toString());
        }
        List<String> listChunked = StringsKt.chunked(str, 2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listChunked, 10));
        Iterator<T> it = listChunked.iterator();
        while (it.hasNext()) {
            arrayList.add(Byte.valueOf((byte) Integer.parseInt((String) it.next(), CharsKt.checkRadix(16))));
        }
        return CollectionsKt.toByteArray(arrayList);
    }

    public static final int toInt(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        if (bArr.length != 4) {
            return 0;
        }
        return (bArr[3] & 255) | ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8);
    }

    public static final short toShortLittleEndian(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        return byteBufferWrap.getShort();
    }

    public static final long toLongLittleEndian(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        return byteBufferWrap.getLong();
    }

    public static final long toUInt32(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        if (bArr.length != 4) {
            throw new IllegalArgumentException("ByteArray must be of size 4 to convert to UInt32");
        }
        return ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (255 & bArr[3]);
    }

    public static final byte[] removeFirstNElements(byte[] bArr, int i) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return ArraysKt.sliceArray(bArr, RangesKt.until(i, bArr.length));
    }

    public static final String toHex(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return ArraysKt.joinToString$default(bArr, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.thor.businessmodule.bluetooth.util.BleHelperKt.toHex.1
            public final CharSequence invoke(byte b) {
                String str = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(this, *args)");
                return str;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                return invoke(b.byteValue());
            }
        }, 30, (Object) null);
    }

    public static final boolean[] toBitArray(byte b) {
        boolean[] zArr = new boolean[8];
        for (int i = 7; -1 < i; i--) {
            boolean z = true;
            if (((b >> (7 - i)) & 1) != 1) {
                z = false;
            }
            zArr[i] = z;
        }
        return zArr;
    }
}
