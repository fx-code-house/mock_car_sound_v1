package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzca;
import com.google.android.gms.internal.measurement.zzcd;
import com.google.android.gms.internal.measurement.zzlo;
import com.google.android.gms.internal.measurement.zzml;
import com.google.android.gms.internal.measurement.zzne;
import com.google.android.gms.internal.measurement.zznv;
import com.google.android.gms.internal.measurement.zznw;
import com.google.common.net.HttpHeaders;
import com.google.firebase.messaging.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.time.DateUtils;

/* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
/* loaded from: classes2.dex */
public class zzkl implements zzgt {
    private static volatile zzkl zza;
    private final zzky zzaa;
    private zzfo zzb;
    private zzex zzc;
    private zzaf zzd;
    private zzfa zze;
    private zzkh zzf;
    private zzr zzg;
    private final zzkr zzh;
    private zzih zzi;
    private zzjr zzj;
    private final zzfu zzk;
    private boolean zzl;
    private boolean zzm;
    private long zzn;
    private List<Runnable> zzo;
    private int zzp;
    private int zzq;
    private boolean zzr;
    private boolean zzs;
    private boolean zzt;
    private FileLock zzu;
    private FileChannel zzv;
    private List<Long> zzw;
    private List<Long> zzx;
    private long zzy;
    private final Map<String, zzac> zzz;

    /* compiled from: com.google.android.gms:play-services-measurement@@18.0.0 */
    private class zza implements zzah {
        zzcd.zzg zza;
        List<Long> zzb;
        List<zzcd.zzc> zzc;
        private long zzd;

        private zza() {
        }

        @Override // com.google.android.gms.measurement.internal.zzah
        public final void zza(zzcd.zzg zzgVar) {
            Preconditions.checkNotNull(zzgVar);
            this.zza = zzgVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzah
        public final boolean zza(long j, zzcd.zzc zzcVar) {
            Preconditions.checkNotNull(zzcVar);
            if (this.zzc == null) {
                this.zzc = new ArrayList();
            }
            if (this.zzb == null) {
                this.zzb = new ArrayList();
            }
            if (this.zzc.size() > 0 && zza(this.zzc.get(0)) != zza(zzcVar)) {
                return false;
            }
            long jZzbp = this.zzd + zzcVar.zzbp();
            if (jZzbp >= Math.max(0, zzas.zzh.zza(null).intValue())) {
                return false;
            }
            this.zzd = jZzbp;
            this.zzc.add(zzcVar);
            this.zzb.add(Long.valueOf(j));
            return this.zzc.size() < Math.max(1, zzas.zzi.zza(null).intValue());
        }

        private static long zza(zzcd.zzc zzcVar) {
            return ((zzcVar.zze() / 1000) / 60) / 60;
        }

        /* synthetic */ zza(zzkl zzklVar, zzkk zzkkVar) {
            this();
        }
    }

    public static zzkl zza(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zza == null) {
            synchronized (zzkl.class) {
                if (zza == null) {
                    zza = new zzkl(new zzks(context));
                }
            }
        }
        return zza;
    }

    private zzkl(zzks zzksVar) {
        this(zzksVar, null);
    }

    private zzkl(zzks zzksVar, zzfu zzfuVar) throws IllegalStateException {
        this.zzl = false;
        this.zzaa = new zzko(this);
        Preconditions.checkNotNull(zzksVar);
        zzfu zzfuVarZza = zzfu.zza(zzksVar.zza, null, null);
        this.zzk = zzfuVarZza;
        this.zzy = -1L;
        zzkr zzkrVar = new zzkr(this);
        zzkrVar.zzak();
        this.zzh = zzkrVar;
        zzex zzexVar = new zzex(this);
        zzexVar.zzak();
        this.zzc = zzexVar;
        zzfo zzfoVar = new zzfo(this);
        zzfoVar.zzak();
        this.zzb = zzfoVar;
        this.zzz = new HashMap();
        zzfuVarZza.zzp().zza(new zzkk(this, zzksVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzks zzksVar) throws IllegalStateException {
        this.zzk.zzp().zzc();
        zzaf zzafVar = new zzaf(this);
        zzafVar.zzak();
        this.zzd = zzafVar;
        this.zzk.zza().zza(this.zzb);
        zzjr zzjrVar = new zzjr(this);
        zzjrVar.zzak();
        this.zzj = zzjrVar;
        zzr zzrVar = new zzr(this);
        zzrVar.zzak();
        this.zzg = zzrVar;
        zzih zzihVar = new zzih(this);
        zzihVar.zzak();
        this.zzi = zzihVar;
        zzkh zzkhVar = new zzkh(this);
        zzkhVar.zzak();
        this.zzf = zzkhVar;
        this.zze = new zzfa(this);
        if (this.zzp != this.zzq) {
            this.zzk.zzq().zze().zza("Not all upload components initialized", Integer.valueOf(this.zzp), Integer.valueOf(this.zzq));
        }
        this.zzl = true;
    }

    protected final void zza() throws IllegalStateException {
        this.zzk.zzp().zzc();
        zze().zzu();
        if (this.zzk.zzb().zzc.zza() == 0) {
            this.zzk.zzb().zzc.zza(this.zzk.zzl().currentTimeMillis());
        }
        zzab();
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final zzw zzt() {
        return this.zzk.zzt();
    }

    public final zzab zzb() {
        return this.zzk.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final zzeq zzq() {
        return this.zzk.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final zzfr zzp() {
        return this.zzk.zzp();
    }

    public final zzfo zzc() {
        zzb(this.zzb);
        return this.zzb;
    }

    public final zzex zzd() {
        zzb(this.zzc);
        return this.zzc;
    }

    public final zzaf zze() {
        zzb(this.zzd);
        return this.zzd;
    }

    private final zzfa zzv() {
        zzfa zzfaVar = this.zze;
        if (zzfaVar != null) {
            return zzfaVar;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzkh zzw() {
        zzb(this.zzf);
        return this.zzf;
    }

    public final zzr zzf() {
        zzb(this.zzg);
        return this.zzg;
    }

    public final zzih zzg() {
        zzb(this.zzi);
        return this.zzi;
    }

    public final zzkr zzh() {
        zzb(this.zzh);
        return this.zzh;
    }

    public final zzjr zzi() {
        return this.zzj;
    }

    public final zzeo zzj() {
        return this.zzk.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final Context zzm() {
        return this.zzk.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzgt
    public final Clock zzl() {
        return this.zzk.zzl();
    }

    public final zzkv zzk() {
        return this.zzk.zzh();
    }

    private final void zzx() {
        this.zzk.zzp().zzc();
    }

    final void zzn() {
        if (!this.zzl) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    private static void zzb(zzki zzkiVar) {
        if (zzkiVar == null) {
            throw new IllegalStateException("Upload Component not created");
        }
        if (zzkiVar.zzai()) {
            return;
        }
        String strValueOf = String.valueOf(zzkiVar.getClass());
        throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 27).append("Component not initialized: ").append(strValueOf).toString());
    }

    final void zza(String str, zzac zzacVar) throws IllegalStateException {
        if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) {
            zzx();
            zzn();
            this.zzz.put(str, zzacVar);
            zzaf zzafVarZze = zze();
            if (zzml.zzb() && zzafVarZze.zzs().zza(zzas.zzci)) {
                Preconditions.checkNotNull(str);
                Preconditions.checkNotNull(zzacVar);
                zzafVarZze.zzc();
                zzafVarZze.zzaj();
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("consent_state", zzacVar.zza());
                try {
                    if (zzafVarZze.c_().insertWithOnConflict("consent_settings", null, contentValues, 5) == -1) {
                        zzafVarZze.zzq().zze().zza("Failed to insert/update consent setting (got -1). appId", zzeq.zza(str));
                    }
                } catch (SQLiteException e) {
                    zzafVarZze.zzq().zze().zza("Error storing consent setting. appId, error", zzeq.zza(str), e);
                }
            }
        }
    }

    final zzac zza(String str) throws IllegalStateException {
        zzac zzacVarZzj = zzac.zza;
        if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) {
            zzx();
            zzn();
            zzacVarZzj = this.zzz.get(str);
            if (zzacVarZzj == null) {
                zzacVarZzj = zze().zzj(str);
                if (zzacVarZzj == null) {
                    zzacVarZzj = zzac.zza;
                }
                zza(str, zzacVarZzj);
            }
        }
        return zzacVarZzj;
    }

    private final long zzy() {
        long jCurrentTimeMillis = this.zzk.zzl().currentTimeMillis();
        zzfc zzfcVarZzb = this.zzk.zzb();
        zzfcVarZzb.zzab();
        zzfcVarZzb.zzc();
        long jZza = zzfcVarZzb.zzg.zza();
        if (jZza == 0) {
            jZza = zzfcVarZzb.zzo().zzg().nextInt(86400000) + 1;
            zzfcVarZzb.zzg.zza(jZza);
        }
        return ((((jCurrentTimeMillis + jZza) / 1000) / 60) / 60) / 24;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void zza(com.google.android.gms.measurement.internal.zzaq r36, java.lang.String r37) throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkl.zza(com.google.android.gms.measurement.internal.zzaq, java.lang.String):void");
    }

    private final void zzb(zzaq zzaqVar, zzn zznVar) throws IllegalStateException {
        if (zznw.zzb() && this.zzk.zza().zza(zzas.zzbz)) {
            zzeu zzeuVarZza = zzeu.zza(zzaqVar);
            this.zzk.zzh().zza(zzeuVarZza.zzb, zze().zzi(zznVar.zza));
            this.zzk.zzh().zza(zzeuVarZza, this.zzk.zza().zza(zznVar.zza));
            zzaqVar = zzeuVarZza.zza();
        }
        if (this.zzk.zza().zza(zzas.zzbd) && Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zzaqVar.zza) && "referrer API v2".equals(zzaqVar.zzb.zzd("_cis"))) {
            String strZzd = zzaqVar.zzb.zzd("gclid");
            if (!TextUtils.isEmpty(strZzd)) {
                zza(new zzku("_lgclid", zzaqVar.zzd, strZzd, "auto"), zznVar);
            }
        }
        zza(zzaqVar, zznVar);
    }

    final void zza(zzaq zzaqVar, zzn zznVar) throws IllegalStateException {
        List<zzz> listZza;
        List<zzz> listZza2;
        List<zzz> listZza3;
        zzaq zzaqVar2 = zzaqVar;
        Preconditions.checkNotNull(zznVar);
        Preconditions.checkNotEmpty(zznVar.zza);
        zzx();
        zzn();
        String str = zznVar.zza;
        long j = zzaqVar2.zzd;
        zzh();
        if (zzkr.zza(zzaqVar, zznVar)) {
            if (!zznVar.zzh) {
                zzc(zznVar);
                return;
            }
            if (zznVar.zzu != null) {
                if (zznVar.zzu.contains(zzaqVar2.zza)) {
                    Bundle bundleZzb = zzaqVar2.zzb.zzb();
                    bundleZzb.putLong("ga_safelisted", 1L);
                    zzaqVar2 = new zzaq(zzaqVar2.zza, new zzap(bundleZzb), zzaqVar2.zzc, zzaqVar2.zzd);
                } else {
                    this.zzk.zzq().zzv().zza("Dropping non-safelisted event. appId, event name, origin", str, zzaqVar2.zza, zzaqVar2.zzc);
                    return;
                }
            }
            zze().zze();
            try {
                zzaf zzafVarZze = zze();
                Preconditions.checkNotEmpty(str);
                zzafVarZze.zzc();
                zzafVarZze.zzaj();
                if (j < 0) {
                    zzafVarZze.zzq().zzh().zza("Invalid time querying timed out conditional properties", zzeq.zza(str), Long.valueOf(j));
                    listZza = Collections.emptyList();
                } else {
                    listZza = zzafVarZze.zza("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzz zzzVar : listZza) {
                    if (zzzVar != null) {
                        this.zzk.zzq().zzw().zza("User property timed out", zzzVar.zza, this.zzk.zzi().zzc(zzzVar.zzc.zza), zzzVar.zzc.zza());
                        if (zzzVar.zzg != null) {
                            zzc(new zzaq(zzzVar.zzg, j), zznVar);
                        }
                        zze().zze(str, zzzVar.zzc.zza);
                    }
                }
                zzaf zzafVarZze2 = zze();
                Preconditions.checkNotEmpty(str);
                zzafVarZze2.zzc();
                zzafVarZze2.zzaj();
                if (j < 0) {
                    zzafVarZze2.zzq().zzh().zza("Invalid time querying expired conditional properties", zzeq.zza(str), Long.valueOf(j));
                    listZza2 = Collections.emptyList();
                } else {
                    listZza2 = zzafVarZze2.zza("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(listZza2.size());
                for (zzz zzzVar2 : listZza2) {
                    if (zzzVar2 != null) {
                        this.zzk.zzq().zzw().zza("User property expired", zzzVar2.zza, this.zzk.zzi().zzc(zzzVar2.zzc.zza), zzzVar2.zzc.zza());
                        zze().zzb(str, zzzVar2.zzc.zza);
                        if (zzzVar2.zzk != null) {
                            arrayList.add(zzzVar2.zzk);
                        }
                        zze().zze(str, zzzVar2.zzc.zza);
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zzc(new zzaq((zzaq) obj, j), zznVar);
                }
                zzaf zzafVarZze3 = zze();
                String str2 = zzaqVar2.zza;
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotEmpty(str2);
                zzafVarZze3.zzc();
                zzafVarZze3.zzaj();
                if (j < 0) {
                    zzafVarZze3.zzq().zzh().zza("Invalid time querying triggered conditional properties", zzeq.zza(str), zzafVarZze3.zzn().zza(str2), Long.valueOf(j));
                    listZza3 = Collections.emptyList();
                } else {
                    listZza3 = zzafVarZze3.zza("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                ArrayList arrayList2 = new ArrayList(listZza3.size());
                for (zzz zzzVar3 : listZza3) {
                    if (zzzVar3 != null) {
                        zzku zzkuVar = zzzVar3.zzc;
                        zzkw zzkwVar = new zzkw(zzzVar3.zza, zzzVar3.zzb, zzkuVar.zza, j, zzkuVar.zza());
                        if (zze().zza(zzkwVar)) {
                            this.zzk.zzq().zzw().zza("User property triggered", zzzVar3.zza, this.zzk.zzi().zzc(zzkwVar.zzc), zzkwVar.zze);
                        } else {
                            this.zzk.zzq().zze().zza("Too many active user properties, ignoring", zzeq.zza(zzzVar3.zza), this.zzk.zzi().zzc(zzkwVar.zzc), zzkwVar.zze);
                        }
                        if (zzzVar3.zzi != null) {
                            arrayList2.add(zzzVar3.zzi);
                        }
                        zzzVar3.zzc = new zzku(zzkwVar);
                        zzzVar3.zze = true;
                        zze().zza(zzzVar3);
                    }
                }
                zzc(zzaqVar2, zznVar);
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    zzc(new zzaq((zzaq) obj2, j), zznVar);
                }
                zze().b_();
            } finally {
                zze().zzg();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:297:0x0973 A[Catch: all -> 0x09c2, TryCatch #0 {all -> 0x09c2, blocks: (B:39:0x013b, B:42:0x014a, B:44:0x0154, B:49:0x0160, B:56:0x0172, B:59:0x017e, B:61:0x0195, B:66:0x01ae, B:71:0x01e3, B:73:0x01e9, B:75:0x01f7, B:77:0x0203, B:79:0x020d, B:81:0x0218, B:84:0x021f, B:93:0x02b0, B:95:0x02ba, B:99:0x02f3, B:103:0x0305, B:105:0x0352, B:107:0x0357, B:108:0x0370, B:112:0x0381, B:114:0x0395, B:116:0x039a, B:117:0x03b3, B:121:0x03d8, B:125:0x03fd, B:126:0x0416, B:129:0x0425, B:132:0x0448, B:133:0x0464, B:135:0x046e, B:137:0x047a, B:139:0x0480, B:140:0x048b, B:142:0x0497, B:143:0x04ae, B:145:0x04d8, B:148:0x04f1, B:151:0x0536, B:153:0x055f, B:155:0x0599, B:156:0x059e, B:158:0x05a6, B:159:0x05ab, B:161:0x05b3, B:162:0x05b8, B:164:0x05c1, B:165:0x05c7, B:167:0x05d4, B:168:0x05d9, B:170:0x05df, B:172:0x05ed, B:173:0x0604, B:175:0x060a, B:177:0x061a, B:179:0x0624, B:181:0x062c, B:182:0x0631, B:184:0x063b, B:186:0x0645, B:188:0x064d, B:194:0x066a, B:196:0x0672, B:197:0x0677, B:199:0x0686, B:200:0x0689, B:202:0x069f, B:204:0x06ad, B:230:0x075b, B:232:0x07a3, B:233:0x07a8, B:235:0x07b0, B:237:0x07b6, B:239:0x07c4, B:241:0x07cb, B:243:0x07d1, B:240:0x07c8, B:244:0x07d6, B:246:0x07e2, B:248:0x07f1, B:250:0x07ff, B:252:0x080e, B:254:0x081e, B:256:0x082c, B:259:0x083d, B:261:0x0872, B:262:0x0877, B:258:0x0832, B:251:0x0807, B:263:0x0883, B:265:0x0889, B:267:0x0897, B:272:0x08ae, B:274:0x08b8, B:275:0x08bf, B:276:0x08ca, B:278:0x08d0, B:280:0x0901, B:281:0x0911, B:283:0x0919, B:284:0x091f, B:286:0x0925, B:295:0x096d, B:297:0x0973, B:300:0x098f, B:289:0x0933, B:291:0x0958, B:299:0x0977, B:269:0x089d, B:271:0x08a7, B:206:0x06b3, B:208:0x06bd, B:210:0x06c7, B:212:0x06cb, B:214:0x06d6, B:215:0x06e3, B:217:0x06f5, B:219:0x06f9, B:221:0x06ff, B:223:0x070f, B:225:0x0721, B:229:0x0758, B:226:0x073b, B:228:0x0741, B:189:0x0653, B:191:0x065d, B:193:0x0665, B:152:0x0551, B:86:0x0249, B:87:0x0267, B:92:0x0295, B:91:0x0284, B:78:0x0208, B:69:0x01bc, B:70:0x01d9), top: B:306:0x013b, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02f3 A[Catch: all -> 0x09c2, TRY_LEAVE, TryCatch #0 {all -> 0x09c2, blocks: (B:39:0x013b, B:42:0x014a, B:44:0x0154, B:49:0x0160, B:56:0x0172, B:59:0x017e, B:61:0x0195, B:66:0x01ae, B:71:0x01e3, B:73:0x01e9, B:75:0x01f7, B:77:0x0203, B:79:0x020d, B:81:0x0218, B:84:0x021f, B:93:0x02b0, B:95:0x02ba, B:99:0x02f3, B:103:0x0305, B:105:0x0352, B:107:0x0357, B:108:0x0370, B:112:0x0381, B:114:0x0395, B:116:0x039a, B:117:0x03b3, B:121:0x03d8, B:125:0x03fd, B:126:0x0416, B:129:0x0425, B:132:0x0448, B:133:0x0464, B:135:0x046e, B:137:0x047a, B:139:0x0480, B:140:0x048b, B:142:0x0497, B:143:0x04ae, B:145:0x04d8, B:148:0x04f1, B:151:0x0536, B:153:0x055f, B:155:0x0599, B:156:0x059e, B:158:0x05a6, B:159:0x05ab, B:161:0x05b3, B:162:0x05b8, B:164:0x05c1, B:165:0x05c7, B:167:0x05d4, B:168:0x05d9, B:170:0x05df, B:172:0x05ed, B:173:0x0604, B:175:0x060a, B:177:0x061a, B:179:0x0624, B:181:0x062c, B:182:0x0631, B:184:0x063b, B:186:0x0645, B:188:0x064d, B:194:0x066a, B:196:0x0672, B:197:0x0677, B:199:0x0686, B:200:0x0689, B:202:0x069f, B:204:0x06ad, B:230:0x075b, B:232:0x07a3, B:233:0x07a8, B:235:0x07b0, B:237:0x07b6, B:239:0x07c4, B:241:0x07cb, B:243:0x07d1, B:240:0x07c8, B:244:0x07d6, B:246:0x07e2, B:248:0x07f1, B:250:0x07ff, B:252:0x080e, B:254:0x081e, B:256:0x082c, B:259:0x083d, B:261:0x0872, B:262:0x0877, B:258:0x0832, B:251:0x0807, B:263:0x0883, B:265:0x0889, B:267:0x0897, B:272:0x08ae, B:274:0x08b8, B:275:0x08bf, B:276:0x08ca, B:278:0x08d0, B:280:0x0901, B:281:0x0911, B:283:0x0919, B:284:0x091f, B:286:0x0925, B:295:0x096d, B:297:0x0973, B:300:0x098f, B:289:0x0933, B:291:0x0958, B:299:0x0977, B:269:0x089d, B:271:0x08a7, B:206:0x06b3, B:208:0x06bd, B:210:0x06c7, B:212:0x06cb, B:214:0x06d6, B:215:0x06e3, B:217:0x06f5, B:219:0x06f9, B:221:0x06ff, B:223:0x070f, B:225:0x0721, B:229:0x0758, B:226:0x073b, B:228:0x0741, B:189:0x0653, B:191:0x065d, B:193:0x0665, B:152:0x0551, B:86:0x0249, B:87:0x0267, B:92:0x0295, B:91:0x0284, B:78:0x0208, B:69:0x01bc, B:70:0x01d9), top: B:306:0x013b, inners: #1, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void zzc(com.google.android.gms.measurement.internal.zzaq r28, com.google.android.gms.measurement.internal.zzn r29) throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 2508
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkl.zzc(com.google.android.gms.measurement.internal.zzaq, com.google.android.gms.measurement.internal.zzn):void");
    }

    private final String zza(zzac zzacVar) {
        if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci) && !zzacVar.zze()) {
            return null;
        }
        return zzz();
    }

    @Deprecated
    private final String zzz() {
        byte[] bArr = new byte[16];
        this.zzk.zzh().zzg().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    final void zzo() {
        zzf zzfVarZzb;
        String strZzad;
        zzx();
        zzn();
        this.zzt = true;
        try {
            Boolean boolZzaf = this.zzk.zzv().zzaf();
            if (boolZzaf == null) {
                this.zzk.zzq().zzh().zza("Upload data called on the client side before use of service was decided");
                return;
            }
            if (boolZzaf.booleanValue()) {
                this.zzk.zzq().zze().zza("Upload called in the client side when service should be used");
                return;
            }
            if (this.zzn > 0) {
                zzab();
                return;
            }
            zzx();
            if (this.zzw != null) {
                this.zzk.zzq().zzw().zza("Uploading requested multiple times");
                return;
            }
            if (!zzd().zze()) {
                this.zzk.zzq().zzw().zza("Network not connected, ignoring upload request");
                zzab();
                return;
            }
            long jCurrentTimeMillis = this.zzk.zzl().currentTimeMillis();
            int iZzb = this.zzk.zza().zzb(null, zzas.zzap);
            long jZzv = jCurrentTimeMillis - zzab.zzv();
            for (int i = 0; i < iZzb && zza((String) null, jZzv); i++) {
            }
            long jZza = this.zzk.zzb().zzc.zza();
            if (jZza != 0) {
                this.zzk.zzq().zzv().zza("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(jCurrentTimeMillis - jZza)));
            }
            String strD_ = zze().d_();
            if (!TextUtils.isEmpty(strD_)) {
                if (this.zzy == -1) {
                    this.zzy = zze().zzz();
                }
                List<Pair<zzcd.zzg, Long>> listZza = zze().zza(strD_, this.zzk.zza().zzb(strD_, zzas.zzf), Math.max(0, this.zzk.zza().zzb(strD_, zzas.zzg)));
                if (!listZza.isEmpty()) {
                    if (!zzml.zzb() || !this.zzk.zza().zza(zzas.zzci) || zza(strD_).zzc()) {
                        Iterator<Pair<zzcd.zzg, Long>> it = listZza.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                strZzad = null;
                                break;
                            }
                            zzcd.zzg zzgVar = (zzcd.zzg) it.next().first;
                            if (!TextUtils.isEmpty(zzgVar.zzad())) {
                                strZzad = zzgVar.zzad();
                                break;
                            }
                        }
                        if (strZzad != null) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= listZza.size()) {
                                    break;
                                }
                                zzcd.zzg zzgVar2 = (zzcd.zzg) listZza.get(i2).first;
                                if (!TextUtils.isEmpty(zzgVar2.zzad()) && !zzgVar2.zzad().equals(strZzad)) {
                                    listZza = listZza.subList(0, i2);
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                    zzcd.zzf.zza zzaVarZzb = zzcd.zzf.zzb();
                    int size = listZza.size();
                    ArrayList arrayList = new ArrayList(listZza.size());
                    boolean z = this.zzk.zza().zzh(strD_) && !(zzml.zzb() && this.zzk.zza().zza(zzas.zzci) && !zza(strD_).zzc());
                    boolean z2 = (zzml.zzb() && this.zzk.zza().zza(zzas.zzci) && !zza(strD_).zzc()) ? false : true;
                    boolean z3 = (zzml.zzb() && this.zzk.zza().zza(zzas.zzci) && !zza(strD_).zze()) ? false : true;
                    int i3 = 0;
                    while (i3 < size) {
                        zzcd.zzg.zza zzaVarZzbo = ((zzcd.zzg) listZza.get(i3).first).zzbo();
                        zzcd.zzg.zza zzaVar = zzaVarZzbo;
                        zzcd.zzg.zza zzaVar2 = zzaVarZzbo;
                        arrayList.add((Long) listZza.get(i3).second);
                        ArrayList arrayList2 = arrayList;
                        zzaVar2.zzg(33025L).zza(jCurrentTimeMillis).zzb(false);
                        if (!z) {
                            zzaVar2.zzr();
                        }
                        if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) {
                            if (!z2) {
                                zzaVar2.zzk();
                                zzaVar2.zzl();
                            }
                            if (!z3) {
                                zzaVar2.zzm();
                            }
                        }
                        if (this.zzk.zza().zze(strD_, zzas.zzaw)) {
                            zzaVar2.zzl(zzh().zza(((zzcd.zzg) ((com.google.android.gms.internal.measurement.zzhy) zzaVar2.zzy())).zzbk()));
                        }
                        zzaVarZzb.zza(zzaVar2);
                        i3++;
                        arrayList = arrayList2;
                    }
                    ArrayList arrayList3 = arrayList;
                    String strZza = this.zzk.zzq().zza(2) ? zzh().zza((zzcd.zzf) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzb.zzy())) : null;
                    zzh();
                    byte[] bArrZzbk = ((zzcd.zzf) ((com.google.android.gms.internal.measurement.zzhy) zzaVarZzb.zzy())).zzbk();
                    String strZza2 = zzas.zzp.zza(null);
                    try {
                        URL url = new URL(strZza2);
                        Preconditions.checkArgument(!arrayList3.isEmpty());
                        if (this.zzw != null) {
                            this.zzk.zzq().zze().zza("Set uploading progress before finishing the previous upload");
                        } else {
                            this.zzw = new ArrayList(arrayList3);
                        }
                        this.zzk.zzb().zzd.zza(jCurrentTimeMillis);
                        this.zzk.zzq().zzw().zza("Uploading data. app, uncompressed size, data", size > 0 ? zzaVarZzb.zza(0).zzx() : "?", Integer.valueOf(bArrZzbk.length), strZza);
                        this.zzs = true;
                        zzex zzexVarZzd = zzd();
                        zzkn zzknVar = new zzkn(this, strD_);
                        zzexVarZzd.zzc();
                        zzexVarZzd.zzaj();
                        Preconditions.checkNotNull(url);
                        Preconditions.checkNotNull(bArrZzbk);
                        Preconditions.checkNotNull(zzknVar);
                        zzexVarZzd.zzp().zzc(new zzfb(zzexVarZzd, strD_, url, bArrZzbk, null, zzknVar));
                    } catch (MalformedURLException unused) {
                        this.zzk.zzq().zze().zza("Failed to parse upload URL. Not uploading. appId", zzeq.zza(strD_), strZza2);
                    }
                }
            } else {
                this.zzy = -1L;
                String strZza3 = zze().zza(jCurrentTimeMillis - zzab.zzv());
                if (!TextUtils.isEmpty(strZza3) && (zzfVarZzb = zze().zzb(strZza3)) != null) {
                    zza(zzfVarZzb);
                }
            }
        } finally {
            this.zzt = false;
            zzac();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x027b A[Catch: all -> 0x103d, TRY_ENTER, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0282 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0290 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0470  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0474  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0672 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0733  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0749 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:328:0x08d7  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x08ef A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0909 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0993 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:464:0x0c93 A[Catch: all -> 0x103d, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0ca6  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0ca9 A[Catch: all -> 0x103d, TRY_LEAVE, TryCatch #15 {all -> 0x103d, blocks: (B:3:0x000f, B:18:0x0084, B:115:0x027e, B:117:0x0282, B:123:0x0290, B:124:0x02ba, B:127:0x02d2, B:130:0x02fc, B:132:0x0333, B:138:0x0349, B:140:0x0353, B:340:0x0958, B:142:0x037d, B:144:0x0383, B:146:0x0399, B:148:0x03a7, B:151:0x03c7, B:153:0x03cd, B:155:0x03dd, B:157:0x03eb, B:159:0x03fb, B:160:0x040a, B:162:0x040f, B:165:0x0425, B:195:0x0489, B:198:0x0493, B:200:0x04a1, B:205:0x04f5, B:201:0x04c3, B:203:0x04d1, B:209:0x0502, B:212:0x0537, B:213:0x0565, B:215:0x0599, B:217:0x059f, B:239:0x0672, B:240:0x067e, B:243:0x0688, B:249:0x06ab, B:246:0x069a, B:252:0x06b1, B:254:0x06bd, B:256:0x06c9, B:272:0x0718, B:275:0x0735, B:277:0x0749, B:279:0x0753, B:282:0x0766, B:284:0x077a, B:286:0x0788, B:330:0x08df, B:332:0x08e9, B:334:0x08ef, B:335:0x0909, B:337:0x091c, B:338:0x0936, B:339:0x093e, B:291:0x07a6, B:293:0x07b4, B:296:0x07c9, B:298:0x07dd, B:300:0x07eb, B:303:0x07fd, B:305:0x0815, B:307:0x0821, B:310:0x0834, B:312:0x0848, B:314:0x0893, B:316:0x089a, B:318:0x08a0, B:320:0x08aa, B:322:0x08b1, B:324:0x08b7, B:326:0x08c1, B:327:0x08d1, B:260:0x06ea, B:264:0x06fe, B:266:0x0704, B:269:0x070f, B:220:0x05ab, B:222:0x05e0, B:223:0x05fd, B:225:0x0603, B:227:0x0611, B:231:0x0628, B:228:0x061d, B:234:0x062f, B:236:0x0636, B:237:0x0655, B:172:0x0447, B:175:0x0451, B:178:0x045b, B:345:0x0974, B:347:0x0982, B:349:0x098b, B:360:0x09bd, B:350:0x0993, B:352:0x099c, B:354:0x09a2, B:357:0x09ae, B:359:0x09b8, B:363:0x09c4, B:364:0x09d0, B:366:0x09d6, B:372:0x09ef, B:373:0x09fa, B:378:0x0a07, B:382:0x0a2e, B:384:0x0a4d, B:386:0x0a5b, B:388:0x0a61, B:390:0x0a6b, B:391:0x0a9d, B:393:0x0aa3, B:395:0x0ab1, B:399:0x0abc, B:396:0x0ab6, B:400:0x0abf, B:401:0x0ace, B:403:0x0ad4, B:405:0x0ae4, B:406:0x0aeb, B:408:0x0af7, B:409:0x0afe, B:410:0x0b01, B:412:0x0b07, B:414:0x0b19, B:415:0x0b1c, B:423:0x0b8f, B:425:0x0baa, B:426:0x0bbb, B:428:0x0bbf, B:430:0x0bcb, B:431:0x0bd3, B:433:0x0bd7, B:435:0x0bdf, B:436:0x0bed, B:437:0x0bf8, B:443:0x0c39, B:444:0x0c41, B:446:0x0c47, B:448:0x0c59, B:450:0x0c5d, B:464:0x0c93, B:467:0x0ca9, B:452:0x0c6b, B:454:0x0c6f, B:456:0x0c79, B:458:0x0c7d, B:379:0x0a0c, B:381:0x0a12, B:52:0x0131, B:71:0x01ce, B:80:0x0208, B:88:0x0228, B:114:0x027b, B:98:0x024b, B:44:0x00e7, B:55:0x0139), top: B:609:0x000f, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0cd0 A[Catch: all -> 0x101c, TRY_ENTER, TryCatch #13 {all -> 0x101c, blocks: (B:417:0x0b59, B:418:0x0b6e, B:420:0x0b74, B:510:0x0e4d, B:439:0x0c03, B:469:0x0cd0, B:471:0x0cdc, B:473:0x0cf0, B:474:0x0d2e, B:478:0x0d46, B:480:0x0d4d, B:482:0x0d5e, B:484:0x0d62, B:486:0x0d66, B:488:0x0d6a, B:489:0x0d76, B:490:0x0d7b, B:492:0x0d81, B:494:0x0da0, B:495:0x0da9, B:509:0x0e4a, B:496:0x0dbd, B:498:0x0dc4, B:502:0x0de8, B:504:0x0e12, B:505:0x0e20, B:506:0x0e32, B:508:0x0e3c, B:499:0x0dcf, B:511:0x0e57, B:513:0x0e64, B:514:0x0e6b, B:515:0x0e73, B:517:0x0e79, B:520:0x0e91), top: B:607:0x0b59 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011e A[Catch: SQLiteException -> 0x0252, all -> 0x1031, TRY_LEAVE, TryCatch #7 {all -> 0x1031, blocks: (B:15:0x007c, B:20:0x0088, B:21:0x008c, B:48:0x00f6, B:50:0x011e, B:54:0x0135, B:55:0x0139, B:56:0x014b, B:58:0x0151, B:60:0x015d, B:62:0x0167, B:64:0x0173, B:66:0x0199, B:112:0x0268, B:65:0x018a, B:96:0x0238, B:41:0x00df, B:46:0x00ee), top: B:603:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:576:0x1020  */
    /* JADX WARN: Removed duplicated region for block: B:605:0x0135 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean zza(java.lang.String r43, long r44) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 4168
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkl.zza(java.lang.String, long):boolean");
    }

    private final void zza(zzcd.zzg.zza zzaVar, long j, boolean z) throws IllegalStateException {
        zzkw zzkwVar;
        boolean z2;
        String str = z ? "_se" : "_lte";
        zzkw zzkwVarZzc = zze().zzc(zzaVar.zzj(), str);
        if (zzkwVarZzc == null || zzkwVarZzc.zze == null) {
            zzkwVar = new zzkw(zzaVar.zzj(), "auto", str, this.zzk.zzl().currentTimeMillis(), Long.valueOf(j));
        } else {
            zzkwVar = new zzkw(zzaVar.zzj(), "auto", str, this.zzk.zzl().currentTimeMillis(), Long.valueOf(((Long) zzkwVarZzc.zze).longValue() + j));
        }
        zzcd.zzk zzkVar = (zzcd.zzk) ((com.google.android.gms.internal.measurement.zzhy) zzcd.zzk.zzj().zza(str).zza(this.zzk.zzl().currentTimeMillis()).zzb(((Long) zzkwVar.zze).longValue()).zzy());
        int iZza = zzkr.zza(zzaVar, str);
        if (iZza >= 0) {
            zzaVar.zza(iZza, zzkVar);
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            zzaVar.zza(zzkVar);
        }
        if (j > 0) {
            zze().zza(zzkwVar);
            this.zzk.zzq().zzw().zza("Updated engagement user property. scope, value", z ? "session-scoped" : "lifetime", zzkwVar.zze);
        }
    }

    private final boolean zza(zzcd.zzc.zza zzaVar, zzcd.zzc.zza zzaVar2) {
        Preconditions.checkArgument("_e".equals(zzaVar.zzd()));
        zzh();
        zzcd.zze zzeVarZza = zzkr.zza((zzcd.zzc) ((com.google.android.gms.internal.measurement.zzhy) zzaVar.zzy()), "_sc");
        String strZzd = zzeVarZza == null ? null : zzeVarZza.zzd();
        zzh();
        zzcd.zze zzeVarZza2 = zzkr.zza((zzcd.zzc) ((com.google.android.gms.internal.measurement.zzhy) zzaVar2.zzy()), "_pc");
        String strZzd2 = zzeVarZza2 != null ? zzeVarZza2.zzd() : null;
        if (strZzd2 == null || !strZzd2.equals(strZzd)) {
            return false;
        }
        zzb(zzaVar, zzaVar2);
        return true;
    }

    private final void zzb(zzcd.zzc.zza zzaVar, zzcd.zzc.zza zzaVar2) {
        Preconditions.checkArgument("_e".equals(zzaVar.zzd()));
        zzh();
        zzcd.zze zzeVarZza = zzkr.zza((zzcd.zzc) ((com.google.android.gms.internal.measurement.zzhy) zzaVar.zzy()), "_et");
        if (!zzeVarZza.zze() || zzeVarZza.zzf() <= 0) {
            return;
        }
        long jZzf = zzeVarZza.zzf();
        zzh();
        zzcd.zze zzeVarZza2 = zzkr.zza((zzcd.zzc) ((com.google.android.gms.internal.measurement.zzhy) zzaVar2.zzy()), "_et");
        if (zzeVarZza2 != null && zzeVarZza2.zzf() > 0) {
            jZzf += zzeVarZza2.zzf();
        }
        zzh();
        zzkr.zza(zzaVar2, "_et", Long.valueOf(jZzf));
        zzh();
        zzkr.zza(zzaVar, "_fr", (Object) 1L);
    }

    private static void zza(zzcd.zzc.zza zzaVar, String str) {
        List<zzcd.zze> listZza = zzaVar.zza();
        for (int i = 0; i < listZza.size(); i++) {
            if (str.equals(listZza.get(i).zzb())) {
                zzaVar.zzb(i);
                return;
            }
        }
    }

    private static void zza(zzcd.zzc.zza zzaVar, int i, String str) {
        List<zzcd.zze> listZza = zzaVar.zza();
        for (int i2 = 0; i2 < listZza.size(); i2++) {
            if ("_err".equals(listZza.get(i2).zzb())) {
                return;
            }
        }
        zzaVar.zza((zzcd.zze) ((com.google.android.gms.internal.measurement.zzhy) zzcd.zze.zzm().zza("_err").zza(Long.valueOf(i).longValue()).zzy())).zza((zzcd.zze) ((com.google.android.gms.internal.measurement.zzhy) zzcd.zze.zzm().zza("_ev").zzb(str).zzy()));
    }

    final void zza(int i, Throwable th, byte[] bArr, String str) throws IllegalStateException {
        zzaf zzafVarZze;
        long jLongValue;
        zzx();
        zzn();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } finally {
                this.zzs = false;
                zzac();
            }
        }
        List<Long> list = this.zzw;
        this.zzw = null;
        boolean z = true;
        if ((i == 200 || i == 204) && th == null) {
            try {
                this.zzk.zzb().zzc.zza(this.zzk.zzl().currentTimeMillis());
                this.zzk.zzb().zzd.zza(0L);
                zzab();
                this.zzk.zzq().zzw().zza("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zze().zze();
                try {
                    for (Long l : list) {
                        try {
                            zzafVarZze = zze();
                            jLongValue = l.longValue();
                            zzafVarZze.zzc();
                            zzafVarZze.zzaj();
                            try {
                            } catch (SQLiteException e) {
                                zzafVarZze.zzq().zze().zza("Failed to delete a bundle in a queue table", e);
                                throw e;
                            }
                        } catch (SQLiteException e2) {
                            List<Long> list2 = this.zzx;
                            if (list2 == null || !list2.contains(l)) {
                                throw e2;
                            }
                        }
                        if (zzafVarZze.c_().delete("queue", "rowid=?", new String[]{String.valueOf(jLongValue)}) != 1) {
                            throw new SQLiteException("Deleted fewer rows from queue than expected");
                        }
                    }
                    zze().b_();
                    zze().zzg();
                    this.zzx = null;
                    if (zzd().zze() && zzaa()) {
                        zzo();
                    } else {
                        this.zzy = -1L;
                        zzab();
                    }
                    this.zzn = 0L;
                } catch (Throwable th2) {
                    zze().zzg();
                    throw th2;
                }
            } catch (SQLiteException e3) {
                this.zzk.zzq().zze().zza("Database error while trying to delete uploaded bundles", e3);
                this.zzn = this.zzk.zzl().elapsedRealtime();
                this.zzk.zzq().zzw().zza("Disable upload, time", Long.valueOf(this.zzn));
            }
        } else {
            this.zzk.zzq().zzw().zza("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzk.zzb().zzd.zza(this.zzk.zzl().currentTimeMillis());
            if (i != 503 && i != 429) {
                z = false;
            }
            if (z) {
                this.zzk.zzb().zze.zza(this.zzk.zzl().currentTimeMillis());
            }
            zze().zza(list);
            zzab();
        }
    }

    private final boolean zzaa() {
        zzx();
        zzn();
        return zze().zzx() || !TextUtils.isEmpty(zze().d_());
    }

    private final void zza(zzf zzfVar) throws IllegalStateException {
        ArrayMap arrayMap;
        zzx();
        if (zznv.zzb() && this.zzk.zza().zze(zzfVar.zzc(), zzas.zzbi)) {
            if (TextUtils.isEmpty(zzfVar.zze()) && TextUtils.isEmpty(zzfVar.zzg()) && TextUtils.isEmpty(zzfVar.zzf())) {
                zza(zzfVar.zzc(), 204, null, null, null);
                return;
            }
        } else if (TextUtils.isEmpty(zzfVar.zze()) && TextUtils.isEmpty(zzfVar.zzf())) {
            zza(zzfVar.zzc(), 204, null, null, null);
            return;
        }
        String strZza = this.zzk.zza().zza(zzfVar);
        try {
            URL url = new URL(strZza);
            this.zzk.zzq().zzw().zza("Fetching remote configuration", zzfVar.zzc());
            zzca.zzb zzbVarZza = zzc().zza(zzfVar.zzc());
            String strZzb = zzc().zzb(zzfVar.zzc());
            if (zzbVarZza == null || TextUtils.isEmpty(strZzb)) {
                arrayMap = null;
            } else {
                arrayMap = new ArrayMap();
                arrayMap.put(HttpHeaders.IF_MODIFIED_SINCE, strZzb);
            }
            this.zzr = true;
            zzex zzexVarZzd = zzd();
            String strZzc = zzfVar.zzc();
            zzkm zzkmVar = new zzkm(this);
            zzexVarZzd.zzc();
            zzexVarZzd.zzaj();
            Preconditions.checkNotNull(url);
            Preconditions.checkNotNull(zzkmVar);
            zzexVarZzd.zzp().zzc(new zzfb(zzexVarZzd, strZzc, url, null, arrayMap, zzkmVar));
        } catch (MalformedURLException unused) {
            this.zzk.zzq().zze().zza("Failed to parse config URL. Not fetching. appId", zzeq.zza(zzfVar.zzc()), strZza);
        }
    }

    final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) throws IllegalStateException {
        zzx();
        zzn();
        Preconditions.checkNotEmpty(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } finally {
                this.zzr = false;
                zzac();
            }
        }
        this.zzk.zzq().zzw().zza("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zze().zze();
        try {
            zzf zzfVarZzb = zze().zzb(str);
            boolean z = true;
            boolean z2 = (i == 200 || i == 204 || i == 304) && th == null;
            if (zzfVarZzb == null) {
                this.zzk.zzq().zzh().zza("App does not exist in onConfigFetched. appId", zzeq.zza(str));
            } else if (z2 || i == 404) {
                List<String> list = map != null ? map.get(HttpHeaders.LAST_MODIFIED) : null;
                String str2 = (list == null || list.size() <= 0) ? null : list.get(0);
                if (i == 404 || i == 304) {
                    if (zzc().zza(str) == null && !zzc().zza(str, null, null)) {
                        return;
                    }
                } else if (!zzc().zza(str, bArr, str2)) {
                    return;
                }
                zzfVarZzb.zzh(this.zzk.zzl().currentTimeMillis());
                zze().zza(zzfVarZzb);
                if (i == 404) {
                    this.zzk.zzq().zzj().zza("Config not found. Using empty config. appId", str);
                } else {
                    this.zzk.zzq().zzw().zza("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                }
                if (zzd().zze() && zzaa()) {
                    zzo();
                } else {
                    zzab();
                }
            } else {
                zzfVarZzb.zzi(this.zzk.zzl().currentTimeMillis());
                zze().zza(zzfVarZzb);
                this.zzk.zzq().zzw().zza("Fetching config failed. code, error", Integer.valueOf(i), th);
                zzc().zzc(str);
                this.zzk.zzb().zzd.zza(this.zzk.zzl().currentTimeMillis());
                if (i != 503 && i != 429) {
                    z = false;
                }
                if (z) {
                    this.zzk.zzb().zze.zza(this.zzk.zzl().currentTimeMillis());
                }
                zzab();
            }
            zze().b_();
        } finally {
            zze().zzg();
        }
    }

    private final void zzab() throws IllegalStateException {
        long jMax;
        long jMax2;
        zzx();
        zzn();
        if (this.zzn > 0) {
            long jAbs = DateUtils.MILLIS_PER_HOUR - Math.abs(this.zzk.zzl().elapsedRealtime() - this.zzn);
            if (jAbs > 0) {
                this.zzk.zzq().zzw().zza("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(jAbs));
                zzv().zzb();
                zzw().zze();
                return;
            }
            this.zzn = 0L;
        }
        if (!this.zzk.zzaf() || !zzaa()) {
            this.zzk.zzq().zzw().zza("Nothing to upload or uploading impossible");
            zzv().zzb();
            zzw().zze();
            return;
        }
        long jCurrentTimeMillis = this.zzk.zzl().currentTimeMillis();
        long jMax3 = Math.max(0L, zzas.zzz.zza(null).longValue());
        boolean z = zze().zzy() || zze().e_();
        if (z) {
            String strZzw = this.zzk.zza().zzw();
            if (!TextUtils.isEmpty(strZzw) && !".none.".equals(strZzw)) {
                jMax = Math.max(0L, zzas.zzu.zza(null).longValue());
            } else {
                jMax = Math.max(0L, zzas.zzt.zza(null).longValue());
            }
        } else {
            jMax = Math.max(0L, zzas.zzs.zza(null).longValue());
        }
        long jZza = this.zzk.zzb().zzc.zza();
        long jZza2 = this.zzk.zzb().zzd.zza();
        long j = jMax;
        long jMax4 = Math.max(zze().zzv(), zze().zzw());
        if (jMax4 == 0) {
            jMax2 = 0;
        } else {
            long jAbs2 = jCurrentTimeMillis - Math.abs(jMax4 - jCurrentTimeMillis);
            long jAbs3 = jCurrentTimeMillis - Math.abs(jZza - jCurrentTimeMillis);
            long jAbs4 = jCurrentTimeMillis - Math.abs(jZza2 - jCurrentTimeMillis);
            long jMax5 = Math.max(jAbs3, jAbs4);
            jMax2 = jAbs2 + jMax3;
            if (z && jMax5 > 0) {
                jMax2 = Math.min(jAbs2, jMax5) + j;
            }
            if (!zzh().zza(jMax5, j)) {
                jMax2 = jMax5 + j;
            }
            if (jAbs4 != 0 && jAbs4 >= jAbs2) {
                for (int i = 0; i < Math.min(20, Math.max(0, zzas.zzab.zza(null).intValue())); i++) {
                    jMax2 += Math.max(0L, zzas.zzaa.zza(null).longValue()) * (1 << i);
                    if (jMax2 > jAbs4) {
                        break;
                    }
                }
                jMax2 = 0;
            }
        }
        if (jMax2 == 0) {
            this.zzk.zzq().zzw().zza("Next upload time is 0");
            zzv().zzb();
            zzw().zze();
            return;
        }
        if (!zzd().zze()) {
            this.zzk.zzq().zzw().zza("No network");
            zzv().zza();
            zzw().zze();
            return;
        }
        long jZza3 = this.zzk.zzb().zze.zza();
        long jMax6 = Math.max(0L, zzas.zzq.zza(null).longValue());
        if (!zzh().zza(jZza3, jMax6)) {
            jMax2 = Math.max(jMax2, jZza3 + jMax6);
        }
        zzv().zzb();
        long jCurrentTimeMillis2 = jMax2 - this.zzk.zzl().currentTimeMillis();
        if (jCurrentTimeMillis2 <= 0) {
            jCurrentTimeMillis2 = Math.max(0L, zzas.zzv.zza(null).longValue());
            this.zzk.zzb().zzc.zza(this.zzk.zzl().currentTimeMillis());
        }
        this.zzk.zzq().zzw().zza("Upload scheduled in approximately ms", Long.valueOf(jCurrentTimeMillis2));
        zzw().zza(jCurrentTimeMillis2);
    }

    final void zza(Runnable runnable) {
        zzx();
        if (this.zzo == null) {
            this.zzo = new ArrayList();
        }
        this.zzo.add(runnable);
    }

    private final void zzac() throws IllegalStateException {
        zzx();
        if (this.zzr || this.zzs || this.zzt) {
            this.zzk.zzq().zzw().zza("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzr), Boolean.valueOf(this.zzs), Boolean.valueOf(this.zzt));
            return;
        }
        this.zzk.zzq().zzw().zza("Stopping uploading service(s)");
        List<Runnable> list = this.zzo;
        if (list == null) {
            return;
        }
        Iterator<Runnable> it = list.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        this.zzo.clear();
    }

    private final Boolean zzb(zzf zzfVar) {
        try {
            if (zzfVar.zzm() != -2147483648L) {
                if (zzfVar.zzm() == Wrappers.packageManager(this.zzk.zzm()).getPackageInfo(zzfVar.zzc(), 0).versionCode) {
                    return true;
                }
            } else {
                String str = Wrappers.packageManager(this.zzk.zzm()).getPackageInfo(zzfVar.zzc(), 0).versionName;
                if (zzfVar.zzl() != null && zzfVar.zzl().equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    final void zzr() {
        zzx();
        zzn();
        if (this.zzm) {
            return;
        }
        this.zzm = true;
        if (zzad()) {
            int iZza = zza(this.zzv);
            int iZzae = this.zzk.zzx().zzae();
            zzx();
            if (iZza > iZzae) {
                this.zzk.zzq().zze().zza("Panic: can't downgrade version. Previous, current version", Integer.valueOf(iZza), Integer.valueOf(iZzae));
            } else if (iZza < iZzae) {
                if (zza(iZzae, this.zzv)) {
                    this.zzk.zzq().zzw().zza("Storage version upgraded. Previous, current version", Integer.valueOf(iZza), Integer.valueOf(iZzae));
                } else {
                    this.zzk.zzq().zze().zza("Storage version upgrade failed. Previous, current version", Integer.valueOf(iZza), Integer.valueOf(iZzae));
                }
            }
        }
    }

    private final boolean zzad() throws IllegalStateException, IOException {
        FileLock fileLock;
        zzx();
        if (this.zzk.zza().zza(zzas.zzbh) && (fileLock = this.zzu) != null && fileLock.isValid()) {
            this.zzk.zzq().zzw().zza("Storage concurrent access okay");
            return true;
        }
        try {
            FileChannel channel = new RandomAccessFile(new File(this.zzk.zzm().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzv = channel;
            FileLock fileLockTryLock = channel.tryLock();
            this.zzu = fileLockTryLock;
            if (fileLockTryLock != null) {
                this.zzk.zzq().zzw().zza("Storage concurrent access okay");
                return true;
            }
            this.zzk.zzq().zze().zza("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            this.zzk.zzq().zze().zza("Failed to acquire storage lock", e);
            return false;
        } catch (IOException e2) {
            this.zzk.zzq().zze().zza("Failed to access storage lock file", e2);
            return false;
        } catch (OverlappingFileLockException e3) {
            this.zzk.zzq().zzh().zza("Storage lock already acquired", e3);
            return false;
        }
    }

    private final int zza(FileChannel fileChannel) throws IllegalStateException, IOException {
        zzx();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzk.zzq().zze().zza("Bad channel to read from");
            return 0;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0L);
            int i = fileChannel.read(byteBufferAllocate);
            if (i == 4) {
                byteBufferAllocate.flip();
                return byteBufferAllocate.getInt();
            }
            if (i != -1) {
                this.zzk.zzq().zzh().zza("Unexpected data length. Bytes read", Integer.valueOf(i));
            }
            return 0;
        } catch (IOException e) {
            this.zzk.zzq().zze().zza("Failed to read from channel", e);
            return 0;
        }
    }

    private final boolean zza(int i, FileChannel fileChannel) throws IllegalStateException, IOException {
        zzx();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzk.zzq().zze().zza("Bad channel to read from");
            return false;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.putInt(i);
        byteBufferAllocate.flip();
        try {
            fileChannel.truncate(0L);
            this.zzk.zza().zza(zzas.zzbr);
            fileChannel.write(byteBufferAllocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                this.zzk.zzq().zze().zza("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            this.zzk.zzq().zze().zza("Failed to write to channel", e);
            return false;
        }
    }

    final void zza(zzn zznVar) throws IllegalStateException {
        if (this.zzw != null) {
            ArrayList arrayList = new ArrayList();
            this.zzx = arrayList;
            arrayList.addAll(this.zzw);
        }
        zzaf zzafVarZze = zze();
        String str = zznVar.zza;
        Preconditions.checkNotEmpty(str);
        zzafVarZze.zzc();
        zzafVarZze.zzaj();
        try {
            SQLiteDatabase sQLiteDatabaseC_ = zzafVarZze.c_();
            String[] strArr = {str};
            int iDelete = sQLiteDatabaseC_.delete("apps", "app_id=?", strArr) + 0 + sQLiteDatabaseC_.delete("events", "app_id=?", strArr) + sQLiteDatabaseC_.delete("user_attributes", "app_id=?", strArr) + sQLiteDatabaseC_.delete("conditional_properties", "app_id=?", strArr) + sQLiteDatabaseC_.delete("raw_events", "app_id=?", strArr) + sQLiteDatabaseC_.delete("raw_events_metadata", "app_id=?", strArr) + sQLiteDatabaseC_.delete("queue", "app_id=?", strArr) + sQLiteDatabaseC_.delete("audience_filter_values", "app_id=?", strArr) + sQLiteDatabaseC_.delete("main_event_params", "app_id=?", strArr) + sQLiteDatabaseC_.delete("default_event_params", "app_id=?", strArr);
            if (iDelete > 0) {
                zzafVarZze.zzq().zzw().zza("Reset analytics data. app, records", str, Integer.valueOf(iDelete));
            }
        } catch (SQLiteException e) {
            zzafVarZze.zzq().zze().zza("Error resetting analytics data. appId, error", zzeq.zza(str), e);
        }
        if (zznVar.zzh) {
            zzb(zznVar);
        }
    }

    final void zza(zzku zzkuVar, zzn zznVar) throws IllegalStateException {
        long jLongValue;
        zzx();
        zzn();
        if (zze(zznVar)) {
            if (!zznVar.zzh) {
                zzc(zznVar);
                return;
            }
            int iZzb = this.zzk.zzh().zzb(zzkuVar.zza);
            int length = 0;
            if (iZzb != 0) {
                this.zzk.zzh();
                this.zzk.zzh().zza(this.zzaa, zznVar.zza, iZzb, "_ev", zzkv.zza(zzkuVar.zza, 24, true), zzkuVar.zza != null ? zzkuVar.zza.length() : 0);
                return;
            }
            int iZzb2 = this.zzk.zzh().zzb(zzkuVar.zza, zzkuVar.zza());
            if (iZzb2 != 0) {
                this.zzk.zzh();
                String strZza = zzkv.zza(zzkuVar.zza, 24, true);
                Object objZza = zzkuVar.zza();
                if (objZza != null && ((objZza instanceof String) || (objZza instanceof CharSequence))) {
                    length = String.valueOf(objZza).length();
                }
                this.zzk.zzh().zza(this.zzaa, zznVar.zza, iZzb2, "_ev", strZza, length);
                return;
            }
            Object objZzc = this.zzk.zzh().zzc(zzkuVar.zza, zzkuVar.zza());
            if (objZzc == null) {
                return;
            }
            if ("_sid".equals(zzkuVar.zza)) {
                long j = zzkuVar.zzb;
                String str = zzkuVar.zze;
                zzkw zzkwVarZzc = zze().zzc(zznVar.zza, "_sno");
                if (zzkwVarZzc != null && (zzkwVarZzc.zze instanceof Long)) {
                    jLongValue = ((Long) zzkwVarZzc.zze).longValue();
                } else {
                    if (zzkwVarZzc != null) {
                        this.zzk.zzq().zzh().zza("Retrieved last session number from database does not contain a valid (long) value", zzkwVarZzc.zze);
                    }
                    zzam zzamVarZza = zze().zza(zznVar.zza, "_s");
                    if (zzamVarZza != null) {
                        jLongValue = zzamVarZza.zzc;
                        this.zzk.zzq().zzw().zza("Backfill the session number. Last used session number", Long.valueOf(jLongValue));
                    } else {
                        jLongValue = 0;
                    }
                }
                zza(new zzku("_sno", j, Long.valueOf(jLongValue + 1), str), zznVar);
            }
            zzkw zzkwVar = new zzkw(zznVar.zza, zzkuVar.zze, zzkuVar.zza, zzkuVar.zzb, objZzc);
            this.zzk.zzq().zzw().zza("Setting user property", this.zzk.zzi().zzc(zzkwVar.zzc), objZzc);
            zze().zze();
            try {
                zzc(zznVar);
                boolean zZza = zze().zza(zzkwVar);
                zze().b_();
                if (!zZza) {
                    this.zzk.zzq().zze().zza("Too many unique user properties are set. Ignoring user property", this.zzk.zzi().zzc(zzkwVar.zzc), zzkwVar.zze);
                    this.zzk.zzh().zza(this.zzaa, zznVar.zza, 9, (String) null, (String) null, 0);
                }
            } finally {
                zze().zzg();
            }
        }
    }

    final void zzb(zzku zzkuVar, zzn zznVar) throws IllegalStateException {
        zzx();
        zzn();
        if (zze(zznVar)) {
            if (!zznVar.zzh) {
                zzc(zznVar);
                return;
            }
            if ("_npa".equals(zzkuVar.zza) && zznVar.zzs != null) {
                this.zzk.zzq().zzv().zza("Falling back to manifest metadata value for ad personalization");
                zza(new zzku("_npa", this.zzk.zzl().currentTimeMillis(), Long.valueOf(zznVar.zzs.booleanValue() ? 1L : 0L), "auto"), zznVar);
                return;
            }
            this.zzk.zzq().zzv().zza("Removing user property", this.zzk.zzi().zzc(zzkuVar.zza));
            zze().zze();
            try {
                zzc(zznVar);
                zze().zzb(zznVar.zza, zzkuVar.zza);
                zze().b_();
                this.zzk.zzq().zzv().zza("User property removed", this.zzk.zzi().zzc(zzkuVar.zza));
            } finally {
                zze().zzg();
            }
        }
    }

    final void zza(zzki zzkiVar) {
        this.zzp++;
    }

    final void zzs() {
        this.zzq++;
    }

    final zzfu zzu() {
        return this.zzk;
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x020c A[Catch: all -> 0x04ae, TryCatch #2 {all -> 0x04ae, blocks: (B:24:0x009f, B:26:0x00ad, B:44:0x010f, B:46:0x011b, B:48:0x0132, B:49:0x015a, B:51:0x01ab, B:54:0x01be, B:57:0x01d2, B:59:0x01dd, B:64:0x01ec, B:66:0x01f4, B:68:0x01fa, B:72:0x0209, B:74:0x020c, B:76:0x0230, B:78:0x0235, B:84:0x0255, B:87:0x0268, B:89:0x02bc, B:91:0x02c4, B:93:0x02c8, B:94:0x02cb, B:96:0x02ec, B:135:0x03c8, B:136:0x03cb, B:147:0x043c, B:149:0x044c, B:151:0x0466, B:152:0x046d, B:156:0x049f, B:98:0x0305, B:103:0x0330, B:105:0x0338, B:107:0x0342, B:111:0x0356, B:115:0x0364, B:119:0x036f, B:122:0x0381, B:127:0x03ac, B:129:0x03b2, B:130:0x03b7, B:132:0x03bd, B:125:0x0394, B:112:0x035c, B:101:0x0318, B:139:0x03e3, B:141:0x0419, B:143:0x0421, B:145:0x0425, B:146:0x0428, B:153:0x0482, B:155:0x0486, B:81:0x0245, B:30:0x00bc, B:32:0x00c0, B:36:0x00d1, B:38:0x00eb, B:40:0x00f5, B:43:0x00ff), top: B:166:0x009f, inners: #0, #1, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final void zzb(com.google.android.gms.measurement.internal.zzn r22) throws java.lang.IllegalStateException {
        /*
            Method dump skipped, instructions count: 1207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkl.zzb(com.google.android.gms.measurement.internal.zzn):void");
    }

    private final zzn zzb(String str) throws IllegalStateException {
        zzf zzfVarZzb = zze().zzb(str);
        if (zzfVarZzb == null || TextUtils.isEmpty(zzfVarZzb.zzl())) {
            this.zzk.zzq().zzv().zza("No app data available; dropping", str);
            return null;
        }
        Boolean boolZzb = zzb(zzfVarZzb);
        if (boolZzb != null && !boolZzb.booleanValue()) {
            this.zzk.zzq().zze().zza("App version does not match; dropping. appId", zzeq.zza(str));
            return null;
        }
        return new zzn(str, zzfVarZzb.zze(), zzfVarZzb.zzl(), zzfVarZzb.zzm(), zzfVarZzb.zzn(), zzfVarZzb.zzo(), zzfVarZzb.zzp(), (String) null, zzfVarZzb.zzr(), false, zzfVarZzb.zzi(), zzfVarZzb.zzae(), 0L, 0, zzfVarZzb.zzaf(), zzfVarZzb.zzag(), false, zzfVarZzb.zzf(), zzfVarZzb.zzah(), zzfVarZzb.zzq(), zzfVarZzb.zzai(), (zznv.zzb() && this.zzk.zza().zze(str, zzas.zzbi)) ? zzfVarZzb.zzg() : null, (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) ? zza(str).zza() : "");
    }

    final void zza(zzz zzzVar) throws IllegalStateException {
        zzn zznVarZzb = zzb(zzzVar.zza);
        if (zznVarZzb != null) {
            zza(zzzVar, zznVarZzb);
        }
    }

    final void zza(zzz zzzVar, zzn zznVar) {
        Preconditions.checkNotNull(zzzVar);
        Preconditions.checkNotEmpty(zzzVar.zza);
        Preconditions.checkNotNull(zzzVar.zzb);
        Preconditions.checkNotNull(zzzVar.zzc);
        Preconditions.checkNotEmpty(zzzVar.zzc.zza);
        zzx();
        zzn();
        if (zze(zznVar)) {
            if (!zznVar.zzh) {
                zzc(zznVar);
                return;
            }
            zzz zzzVar2 = new zzz(zzzVar);
            boolean z = false;
            zzzVar2.zze = false;
            zze().zze();
            try {
                zzz zzzVarZzd = zze().zzd(zzzVar2.zza, zzzVar2.zzc.zza);
                if (zzzVarZzd != null && !zzzVarZzd.zzb.equals(zzzVar2.zzb)) {
                    this.zzk.zzq().zzh().zza("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzk.zzi().zzc(zzzVar2.zzc.zza), zzzVar2.zzb, zzzVarZzd.zzb);
                }
                if (zzzVarZzd != null && zzzVarZzd.zze) {
                    zzzVar2.zzb = zzzVarZzd.zzb;
                    zzzVar2.zzd = zzzVarZzd.zzd;
                    zzzVar2.zzh = zzzVarZzd.zzh;
                    zzzVar2.zzf = zzzVarZzd.zzf;
                    zzzVar2.zzi = zzzVarZzd.zzi;
                    zzzVar2.zze = zzzVarZzd.zze;
                    zzzVar2.zzc = new zzku(zzzVar2.zzc.zza, zzzVarZzd.zzc.zzb, zzzVar2.zzc.zza(), zzzVarZzd.zzc.zze);
                } else if (TextUtils.isEmpty(zzzVar2.zzf)) {
                    zzzVar2.zzc = new zzku(zzzVar2.zzc.zza, zzzVar2.zzd, zzzVar2.zzc.zza(), zzzVar2.zzc.zze);
                    z = true;
                    zzzVar2.zze = true;
                }
                if (zzzVar2.zze) {
                    zzku zzkuVar = zzzVar2.zzc;
                    zzkw zzkwVar = new zzkw(zzzVar2.zza, zzzVar2.zzb, zzkuVar.zza, zzkuVar.zzb, zzkuVar.zza());
                    if (zze().zza(zzkwVar)) {
                        this.zzk.zzq().zzv().zza("User property updated immediately", zzzVar2.zza, this.zzk.zzi().zzc(zzkwVar.zzc), zzkwVar.zze);
                    } else {
                        this.zzk.zzq().zze().zza("(2)Too many active user properties, ignoring", zzeq.zza(zzzVar2.zza), this.zzk.zzi().zzc(zzkwVar.zzc), zzkwVar.zze);
                    }
                    if (z && zzzVar2.zzi != null) {
                        zzc(new zzaq(zzzVar2.zzi, zzzVar2.zzd), zznVar);
                    }
                }
                if (zze().zza(zzzVar2)) {
                    this.zzk.zzq().zzv().zza("Conditional property added", zzzVar2.zza, this.zzk.zzi().zzc(zzzVar2.zzc.zza), zzzVar2.zzc.zza());
                } else {
                    this.zzk.zzq().zze().zza("Too many conditional properties, ignoring", zzeq.zza(zzzVar2.zza), this.zzk.zzi().zzc(zzzVar2.zzc.zza), zzzVar2.zzc.zza());
                }
                zze().b_();
            } finally {
                zze().zzg();
            }
        }
    }

    final void zzb(zzz zzzVar) throws IllegalStateException {
        zzn zznVarZzb = zzb(zzzVar.zza);
        if (zznVarZzb != null) {
            zzb(zzzVar, zznVarZzb);
        }
    }

    final void zzb(zzz zzzVar, zzn zznVar) {
        Preconditions.checkNotNull(zzzVar);
        Preconditions.checkNotEmpty(zzzVar.zza);
        Preconditions.checkNotNull(zzzVar.zzc);
        Preconditions.checkNotEmpty(zzzVar.zzc.zza);
        zzx();
        zzn();
        if (zze(zznVar)) {
            if (!zznVar.zzh) {
                zzc(zznVar);
                return;
            }
            zze().zze();
            try {
                zzc(zznVar);
                zzz zzzVarZzd = zze().zzd(zzzVar.zza, zzzVar.zzc.zza);
                if (zzzVarZzd != null) {
                    this.zzk.zzq().zzv().zza("Removing conditional user property", zzzVar.zza, this.zzk.zzi().zzc(zzzVar.zzc.zza));
                    zze().zze(zzzVar.zza, zzzVar.zzc.zza);
                    if (zzzVarZzd.zze) {
                        zze().zzb(zzzVar.zza, zzzVar.zzc.zza);
                    }
                    if (zzzVar.zzk != null) {
                        zzc(this.zzk.zzh().zza(zzzVar.zza, zzzVar.zzk.zza, zzzVar.zzk.zzb != null ? zzzVar.zzk.zzb.zzb() : null, zzzVarZzd.zzb, zzzVar.zzk.zzd, true, false, zzlo.zzb() && this.zzk.zza().zza(zzas.zzcl)), zznVar);
                    }
                } else {
                    this.zzk.zzq().zzh().zza("Conditional user property doesn't exist", zzeq.zza(zzzVar.zza), this.zzk.zzi().zzc(zzzVar.zzc.zza));
                }
                zze().b_();
            } finally {
                zze().zzg();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final com.google.android.gms.measurement.internal.zzf zza(com.google.android.gms.measurement.internal.zzn r9, com.google.android.gms.measurement.internal.zzf r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 585
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzkl.zza(com.google.android.gms.measurement.internal.zzn, com.google.android.gms.measurement.internal.zzf, java.lang.String):com.google.android.gms.measurement.internal.zzf");
    }

    final zzf zzc(zzn zznVar) {
        zzx();
        zzn();
        Preconditions.checkNotNull(zznVar);
        Preconditions.checkNotEmpty(zznVar.zza);
        zzf zzfVarZzb = zze().zzb(zznVar.zza);
        zzac zzacVarZzb = zzac.zza;
        if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) {
            zzacVarZzb = zza(zznVar.zza).zzb(zzac.zza(zznVar.zzw));
        }
        String strZza = (zzml.zzb() && this.zzk.zza().zza(zzas.zzci) && !zzacVarZzb.zzc()) ? "" : this.zzj.zza(zznVar.zza);
        if (zzne.zzb() && this.zzk.zza().zza(zzas.zzbn)) {
            if (zzfVarZzb == null) {
                zzfVarZzb = new zzf(this.zzk, zznVar.zza);
                if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) {
                    if (zzacVarZzb.zze()) {
                        zzfVarZzb.zza(zza(zzacVarZzb));
                    }
                    if (zzacVarZzb.zzc()) {
                        zzfVarZzb.zze(strZza);
                    }
                } else {
                    zzfVarZzb.zza(zzz());
                    zzfVarZzb.zze(strZza);
                }
            } else if ((!zzml.zzb() || !this.zzk.zza().zza(zzas.zzci) || zzacVarZzb.zzc()) && !strZza.equals(zzfVarZzb.zzh())) {
                zzfVarZzb.zze(strZza);
                if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci)) {
                    zzfVarZzb.zza(zza(zzacVarZzb));
                } else {
                    zzfVarZzb.zza(zzz());
                }
            } else if (zzml.zzb() && this.zzk.zza().zza(zzas.zzci) && TextUtils.isEmpty(zzfVarZzb.zzd()) && zzacVarZzb.zze()) {
                zzfVarZzb.zza(zza(zzacVarZzb));
            }
            zzfVarZzb.zzb(zznVar.zzb);
            zzfVarZzb.zzc(zznVar.zzr);
            if (zznv.zzb() && this.zzk.zza().zze(zzfVarZzb.zzc(), zzas.zzbi)) {
                zzfVarZzb.zzd(zznVar.zzv);
            }
            if (!TextUtils.isEmpty(zznVar.zzk)) {
                zzfVarZzb.zzf(zznVar.zzk);
            }
            if (zznVar.zze != 0) {
                zzfVarZzb.zzd(zznVar.zze);
            }
            if (!TextUtils.isEmpty(zznVar.zzc)) {
                zzfVarZzb.zzg(zznVar.zzc);
            }
            zzfVarZzb.zzc(zznVar.zzj);
            if (zznVar.zzd != null) {
                zzfVarZzb.zzh(zznVar.zzd);
            }
            zzfVarZzb.zze(zznVar.zzf);
            zzfVarZzb.zza(zznVar.zzh);
            if (!TextUtils.isEmpty(zznVar.zzg)) {
                zzfVarZzb.zzi(zznVar.zzg);
            }
            if (!this.zzk.zza().zza(zzas.zzbx)) {
                zzfVarZzb.zzp(zznVar.zzl);
            }
            zzfVarZzb.zzb(zznVar.zzo);
            zzfVarZzb.zzc(zznVar.zzp);
            zzfVarZzb.zza(zznVar.zzs);
            zzfVarZzb.zzf(zznVar.zzt);
            if (zzfVarZzb.zza()) {
                zze().zza(zzfVarZzb);
            }
            return zzfVarZzb;
        }
        return zza(zznVar, zzfVarZzb, strZza);
    }

    final String zzd(zzn zznVar) throws IllegalStateException {
        try {
            return (String) this.zzk.zzp().zza(new zzkp(this, zznVar)).get(30000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.zzk.zzq().zze().zza("Failed to get app instance id. appId", zzeq.zza(zznVar.zza), e);
            return null;
        }
    }

    final void zza(boolean z) throws IllegalStateException {
        zzab();
    }

    private final boolean zze(zzn zznVar) {
        return (zznv.zzb() && this.zzk.zza().zze(zznVar.zza, zzas.zzbi)) ? (TextUtils.isEmpty(zznVar.zzb) && TextUtils.isEmpty(zznVar.zzv) && TextUtils.isEmpty(zznVar.zzr)) ? false : true : (TextUtils.isEmpty(zznVar.zzb) && TextUtils.isEmpty(zznVar.zzr)) ? false : true;
    }
}
