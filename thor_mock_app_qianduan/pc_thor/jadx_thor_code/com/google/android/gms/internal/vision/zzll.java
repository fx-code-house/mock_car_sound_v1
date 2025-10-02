package com.google.android.gms.internal.vision;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzadq' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.1 */
/* loaded from: classes2.dex */
public class zzll {
    public static final zzll zzadi;
    public static final zzll zzadj;
    public static final zzll zzadk;
    public static final zzll zzadl;
    public static final zzll zzadm;
    public static final zzll zzadn;
    public static final zzll zzado;
    public static final zzll zzadp;
    public static final zzll zzadq;
    public static final zzll zzadr;
    public static final zzll zzads;
    public static final zzll zzadt;
    public static final zzll zzadu;
    public static final zzll zzadv;
    public static final zzll zzadw;
    public static final zzll zzadx;
    public static final zzll zzady;
    public static final zzll zzadz;
    private static final /* synthetic */ zzll[] zzaec;
    private final zzlo zzaea;
    private final int zzaeb;

    public static zzll[] values() {
        return (zzll[]) zzaec.clone();
    }

    private zzll(String str, int i, zzlo zzloVar, int i2) {
        this.zzaea = zzloVar;
        this.zzaeb = i2;
    }

    public final zzlo zzjk() {
        return this.zzaea;
    }

    public final int zzjl() {
        return this.zzaeb;
    }

    /* synthetic */ zzll(String str, int i, zzlo zzloVar, int i2, zzli zzliVar) {
        this(str, i, zzloVar, i2);
    }

    static {
        zzll zzllVar = new zzll("DOUBLE", 0, zzlo.DOUBLE, 1);
        zzadi = zzllVar;
        zzll zzllVar2 = new zzll("FLOAT", 1, zzlo.FLOAT, 5);
        zzadj = zzllVar2;
        final int i = 2;
        zzll zzllVar3 = new zzll("INT64", 2, zzlo.LONG, 0);
        zzadk = zzllVar3;
        final int i2 = 3;
        zzll zzllVar4 = new zzll("UINT64", 3, zzlo.LONG, 0);
        zzadl = zzllVar4;
        zzll zzllVar5 = new zzll("INT32", 4, zzlo.INT, 0);
        zzadm = zzllVar5;
        zzll zzllVar6 = new zzll("FIXED64", 5, zzlo.LONG, 1);
        zzadn = zzllVar6;
        zzll zzllVar7 = new zzll("FIXED32", 6, zzlo.INT, 5);
        zzado = zzllVar7;
        zzll zzllVar8 = new zzll("BOOL", 7, zzlo.BOOLEAN, 0);
        zzadp = zzllVar8;
        final zzlo zzloVar = zzlo.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzll zzllVar9 = new zzll(str, i3, zzloVar, i) { // from class: com.google.android.gms.internal.vision.zzlk
            {
                int i4 = 8;
                int i5 = 2;
                zzli zzliVar = null;
            }
        };
        zzadq = zzllVar9;
        final zzlo zzloVar2 = zzlo.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzll zzllVar10 = new zzll(str2, i4, zzloVar2, i2) { // from class: com.google.android.gms.internal.vision.zzln
            {
                int i5 = 9;
                int i6 = 3;
                zzli zzliVar = null;
            }
        };
        zzadr = zzllVar10;
        final zzlo zzloVar3 = zzlo.MESSAGE;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        zzll zzllVar11 = new zzll(str3, i5, zzloVar3, i) { // from class: com.google.android.gms.internal.vision.zzlm
            {
                int i6 = 10;
                int i7 = 2;
                zzli zzliVar = null;
            }
        };
        zzads = zzllVar11;
        final zzlo zzloVar4 = zzlo.BYTE_STRING;
        final String str4 = "BYTES";
        final int i6 = 11;
        zzll zzllVar12 = new zzll(str4, i6, zzloVar4, i) { // from class: com.google.android.gms.internal.vision.zzlp
            {
                int i7 = 11;
                int i8 = 2;
                zzli zzliVar = null;
            }
        };
        zzadt = zzllVar12;
        zzll zzllVar13 = new zzll("UINT32", 12, zzlo.INT, 0);
        zzadu = zzllVar13;
        zzll zzllVar14 = new zzll("ENUM", 13, zzlo.ENUM, 0);
        zzadv = zzllVar14;
        zzll zzllVar15 = new zzll("SFIXED32", 14, zzlo.INT, 5);
        zzadw = zzllVar15;
        zzll zzllVar16 = new zzll("SFIXED64", 15, zzlo.LONG, 1);
        zzadx = zzllVar16;
        zzll zzllVar17 = new zzll("SINT32", 16, zzlo.INT, 0);
        zzady = zzllVar17;
        zzll zzllVar18 = new zzll("SINT64", 17, zzlo.LONG, 0);
        zzadz = zzllVar18;
        zzaec = new zzll[]{zzllVar, zzllVar2, zzllVar3, zzllVar4, zzllVar5, zzllVar6, zzllVar7, zzllVar8, zzllVar9, zzllVar10, zzllVar11, zzllVar12, zzllVar13, zzllVar14, zzllVar15, zzllVar16, zzllVar17, zzllVar18};
    }
}
