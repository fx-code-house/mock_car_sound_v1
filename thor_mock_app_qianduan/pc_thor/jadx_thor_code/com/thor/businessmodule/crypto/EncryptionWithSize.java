package com.thor.businessmodule.crypto;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.UByte;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: EncryptionWithSize.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\t\u001a\u00020\u0004ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ#\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0003\u001a\u00020\u0004X\u0082Tø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\n\u0002\u0010\u0005\u0082\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0011"}, d2 = {"Lcom/thor/businessmodule/crypto/EncryptionWithSize;", "", "()V", "maxSize", "Lkotlin/UShort;", ExifInterface.LATITUDE_SOUTH, "decode", "Lkotlin/Pair;", "Lcom/thor/businessmodule/crypto/EncryptionType;", "value", "decode-xj2QHRw", "(S)Lkotlin/Pair;", "encode", SessionDescription.ATTR_TYPE, "size", "encode-KH5fvcE", "(Lcom/thor/businessmodule/crypto/EncryptionType;S)S", "businessmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class EncryptionWithSize {
    public static final EncryptionWithSize INSTANCE = new EncryptionWithSize();
    private static final short maxSize = 8191;

    private EncryptionWithSize() {
    }

    /* renamed from: encode-KH5fvcE, reason: not valid java name */
    public final short m607encodeKH5fvcE(EncryptionType type, short size) throws EncryptionError {
        Intrinsics.checkNotNullParameter(type, "type");
        try {
            if (Intrinsics.compare(65535 & size, 8191) > 0) {
                throw EncryptionError.INSTANCE.getMessageSizeOutOfRange();
            }
            return UShort.m905constructorimpl((short) (UShort.m905constructorimpl((short) ((type.getValue() & 255) << 13)) | UShort.m905constructorimpl((short) (size & UShort.m905constructorimpl((short) 8191)))));
        } catch (Exception e) {
            Log.e("EncryptionWithSize", "encode: " + e.getMessage());
            return (short) 0;
        }
    }

    /* renamed from: decode-xj2QHRw, reason: not valid java name */
    public final Pair<EncryptionType, UShort> m606decodexj2QHRw(short value) throws EncryptionError {
        EncryptionType encryptionType;
        int i = value & 65535;
        byte bM642constructorimpl = UByte.m642constructorimpl((byte) ((i >>> 13) & 7));
        EncryptionType[] encryptionTypeArrValues = EncryptionType.values();
        int length = encryptionTypeArrValues.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                encryptionType = null;
                break;
            }
            encryptionType = encryptionTypeArrValues[i2];
            if (encryptionType.getValue() == bM642constructorimpl) {
                break;
            }
            i2++;
        }
        if (encryptionType == null) {
            throw EncryptionError.INSTANCE.getEncryptionTypeError();
        }
        return new Pair<>(encryptionType, UShort.m899boximpl(UShort.m905constructorimpl((short) (i & 8191))));
    }
}
