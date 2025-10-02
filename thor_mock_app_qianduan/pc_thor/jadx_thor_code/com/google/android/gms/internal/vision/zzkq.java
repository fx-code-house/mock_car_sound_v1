package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
final class zzkq {
    static String zzd(zzgs zzgsVar) {
        zzkt zzktVar = new zzkt(zzgsVar);
        StringBuilder sb = new StringBuilder(zzktVar.size());
        for (int i = 0; i < zzktVar.size(); i++) {
            byte bZzau = zzktVar.zzau(i);
            if (bZzau == 34) {
                sb.append("\\\"");
            } else if (bZzau == 39) {
                sb.append("\\'");
            } else if (bZzau != 92) {
                switch (bZzau) {
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
                        if (bZzau < 32 || bZzau > 126) {
                            sb.append('\\');
                            sb.append((char) (((bZzau >>> 6) & 3) + 48));
                            sb.append((char) (((bZzau >>> 3) & 7) + 48));
                            sb.append((char) ((bZzau & 7) + 48));
                            break;
                        } else {
                            sb.append((char) bZzau);
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
