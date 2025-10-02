package com.google.android.gms.internal.clearcut;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
abstract class zzfg {
    zzfg() {
    }

    static void zzc(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int iPosition = byteBuffer.position();
        int i = 0;
        while (i < length) {
            try {
                char cCharAt = charSequence.charAt(i);
                if (cCharAt >= 128) {
                    break;
                }
                byteBuffer.put(iPosition + i, (byte) cCharAt);
                i++;
            } catch (IndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(i)).append(" at index ").append(byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)).toString());
            }
        }
        if (i == length) {
            byteBuffer.position(iPosition + i);
            return;
        }
        iPosition += i;
        while (i < length) {
            char cCharAt2 = charSequence.charAt(i);
            if (cCharAt2 < 128) {
                byteBuffer.put(iPosition, (byte) cCharAt2);
            } else if (cCharAt2 < 2048) {
                int i2 = iPosition + 1;
                try {
                    byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> 6) | PsExtractor.AUDIO_STREAM));
                    byteBuffer.put(i2, (byte) ((cCharAt2 & '?') | 128));
                    iPosition = i2;
                } catch (IndexOutOfBoundsException unused2) {
                    iPosition = i2;
                    throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(i)).append(" at index ").append(byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)).toString());
                }
            } else {
                if (cCharAt2 >= 55296 && 57343 >= cCharAt2) {
                    int i3 = i + 1;
                    if (i3 != length) {
                        try {
                            char cCharAt3 = charSequence.charAt(i3);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                int i4 = iPosition + 1;
                                try {
                                    byteBuffer.put(iPosition, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                    int i5 = i4 + 1;
                                    byteBuffer.put(i4, (byte) (((codePoint >>> 12) & 63) | 128));
                                    int i6 = i5 + 1;
                                    byteBuffer.put(i5, (byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put(i6, (byte) ((codePoint & 63) | 128));
                                    iPosition = i6;
                                    i = i3;
                                } catch (IndexOutOfBoundsException unused3) {
                                    iPosition = i4;
                                    i = i3;
                                    throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(i)).append(" at index ").append(byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)).toString());
                                }
                            } else {
                                i = i3;
                            }
                        } catch (IndexOutOfBoundsException unused4) {
                        }
                    }
                    throw new zzfi(i, length);
                }
                int i7 = iPosition + 1;
                byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> '\f') | 224));
                iPosition = i7 + 1;
                byteBuffer.put(i7, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                byteBuffer.put(iPosition, (byte) ((cCharAt2 & '?') | 128));
            }
            i++;
            iPosition++;
        }
        byteBuffer.position(iPosition);
    }

    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract void zzb(CharSequence charSequence, ByteBuffer byteBuffer);

    final boolean zze(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
