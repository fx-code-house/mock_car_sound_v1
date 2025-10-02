package com.google.android.gms.internal.icing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
final class zzgi {
    static String zzd(zzct zzctVar) {
        zzgl zzglVar = new zzgl(zzctVar);
        StringBuilder sb = new StringBuilder(zzglVar.size());
        for (int i = 0; i < zzglVar.size(); i++) {
            byte bZzk = zzglVar.zzk(i);
            if (bZzk == 34) {
                sb.append("\\\"");
            } else if (bZzk == 39) {
                sb.append("\\'");
            } else if (bZzk != 92) {
                switch (bZzk) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bZzk < 32 || bZzk > 126) {
                            sb.append('\\');
                            sb.append((char) (((bZzk >>> 6) & 3) + 48));
                            sb.append((char) (((bZzk >>> 3) & 7) + 48));
                            sb.append((char) ((bZzk & 7) + 48));
                            break;
                        } else {
                            sb.append((char) bZzk);
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
