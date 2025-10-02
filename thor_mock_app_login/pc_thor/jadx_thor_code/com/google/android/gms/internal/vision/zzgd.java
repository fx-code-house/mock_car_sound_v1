package com.google.android.gms.internal.vision;

import kotlin.text.Typography;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public enum zzgd implements zzih {
    UNKNOWN_FORMAT(0),
    CONTACT_INFO(1),
    EMAIL(2),
    ISBN(3),
    PHONE(4),
    PRODUCT(5),
    SMS(6),
    TEXT(7),
    URL(8),
    WIFI(9),
    GEO(10),
    CALENDAR_EVENT(11),
    DRIVER_LICENSE(12),
    BOARDING_PASS(13);

    private static final zzig<zzgd> zzhq = new zzig<zzgd>() { // from class: com.google.android.gms.internal.vision.zzgc
        @Override // com.google.android.gms.internal.vision.zzig
        public final /* synthetic */ zzih zzh(int i) {
            return zzgd.zzaj(i);
        }
    };
    private final int value;

    @Override // com.google.android.gms.internal.vision.zzih
    public final int zzak() {
        return this.value;
    }

    public static zzgd zzaj(int i) {
        switch (i) {
            case 0:
                return UNKNOWN_FORMAT;
            case 1:
                return CONTACT_INFO;
            case 2:
                return EMAIL;
            case 3:
                return ISBN;
            case 4:
                return PHONE;
            case 5:
                return PRODUCT;
            case 6:
                return SMS;
            case 7:
                return TEXT;
            case 8:
                return URL;
            case 9:
                return WIFI;
            case 10:
                return GEO;
            case 11:
                return CALENDAR_EVENT;
            case 12:
                return DRIVER_LICENSE;
            case 13:
                return BOARDING_PASS;
            default:
                return null;
        }
    }

    public static zzij zzal() {
        return zzgf.zzht;
    }

    @Override // java.lang.Enum
    public final String toString() {
        StringBuilder sb = new StringBuilder("<");
        sb.append(getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" number=").append(this.value);
        return sb.append(" name=").append(name()).append(Typography.greater).toString();
    }

    zzgd(int i) {
        this.value = i;
    }
}
