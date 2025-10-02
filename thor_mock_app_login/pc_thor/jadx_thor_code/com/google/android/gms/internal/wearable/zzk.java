package com.google.android.gms.internal.wearable;

import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.mapstruct.ap.internal.util.MessageConstants;

/* compiled from: com.google.android.gms:play-services-wearable@@17.1.0 */
/* loaded from: classes2.dex */
public final class zzk {
    public static zzj zza(DataMap dataMap) {
        ArrayList arrayList = new ArrayList();
        zzm zzmVarZzc = zzw.zzc();
        TreeSet treeSet = new TreeSet(dataMap.keySet());
        ArrayList arrayList2 = new ArrayList();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Object obj = dataMap.get(str);
            zzn zznVarZzc = zzv.zzc();
            zznVarZzc.zza(str);
            zznVarZzc.zzb(zzc(arrayList, obj));
            arrayList2.add(zznVarZzc.zzu());
        }
        zzmVarZzc.zza(arrayList2);
        return new zzj((zzw) zzmVarZzc.zzu(), arrayList);
    }

    public static DataMap zzb(zzj zzjVar) {
        DataMap dataMap = new DataMap();
        for (zzv zzvVar : zzjVar.zza.zza()) {
            zzd(zzjVar.zzb, dataMap, zzvVar.zza(), zzvVar.zzb());
        }
        return dataMap;
    }

    private static zzu zzc(List<Asset> list, Object obj) {
        zzo zzoVarZzc = zzu.zzc();
        zzoVarZzc.zza(zzr.BYTE_ARRAY);
        if (obj == null) {
            zzoVarZzc.zza(zzr.NULL_VALUE);
            return zzoVarZzc.zzu();
        }
        zzs zzsVarZzp = zzt.zzp();
        if (obj instanceof String) {
            zzoVarZzc.zza(zzr.STRING);
            zzsVarZzp.zzb((String) obj);
        } else if (obj instanceof Integer) {
            zzoVarZzc.zza(zzr.INT);
            zzsVarZzp.zzf(((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            zzoVarZzc.zza(zzr.LONG);
            zzsVarZzp.zze(((Long) obj).longValue());
        } else if (obj instanceof Double) {
            zzoVarZzc.zza(zzr.DOUBLE);
            zzsVarZzp.zzc(((Double) obj).doubleValue());
        } else if (obj instanceof Float) {
            zzoVarZzc.zza(zzr.FLOAT);
            zzsVarZzp.zzd(((Float) obj).floatValue());
        } else if (obj instanceof Boolean) {
            zzoVarZzc.zza(zzr.BOOLEAN);
            zzsVarZzp.zzh(((Boolean) obj).booleanValue());
        } else if (obj instanceof Byte) {
            zzoVarZzc.zza(zzr.BYTE);
            zzsVarZzp.zzg(((Byte) obj).byteValue());
        } else if (obj instanceof byte[]) {
            zzoVarZzc.zza(zzr.BYTE_ARRAY);
            zzsVarZzp.zza(zzau.zzl((byte[]) obj));
        } else if (obj instanceof String[]) {
            zzoVarZzc.zza(zzr.STRING_ARRAY);
            zzsVarZzp.zzk(Arrays.asList((String[]) obj));
        } else if (obj instanceof long[]) {
            zzoVarZzc.zza(zzr.LONG_ARRAY);
            zzsVarZzp.zzl(zzad.zza((long[]) obj));
        } else if (obj instanceof float[]) {
            zzoVarZzc.zza(zzr.FLOAT_ARRAY);
            zzsVarZzp.zzm(zzaa.zza((float[]) obj));
        } else if (obj instanceof Asset) {
            zzoVarZzc.zza(zzr.ASSET_INDEX);
            list.add((Asset) obj);
            zzsVarZzp.zzn(list.size() - 1);
        } else {
            int i = 0;
            if (obj instanceof DataMap) {
                zzoVarZzc.zza(zzr.DATA_BUNDLE);
                DataMap dataMap = (DataMap) obj;
                TreeSet treeSet = new TreeSet(dataMap.keySet());
                zzv[] zzvVarArr = new zzv[treeSet.size()];
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    zzn zznVarZzc = zzv.zzc();
                    zznVarZzc.zza(str);
                    zznVarZzc.zzb(zzc(list, dataMap.get(str)));
                    zzvVarArr[i] = zznVarZzc.zzu();
                    i++;
                }
                zzsVarZzp.zzi(Arrays.asList(zzvVarArr));
            } else {
                if (!(obj instanceof ArrayList)) {
                    String strValueOf = String.valueOf(obj.getClass().getSimpleName());
                    throw new RuntimeException(strValueOf.length() != 0 ? "newFieldValueFromValue: unexpected value ".concat(strValueOf) : new String("newFieldValueFromValue: unexpected value "));
                }
                zzoVarZzc.zza(zzr.ARRAY_LIST);
                ArrayList arrayList = (ArrayList) obj;
                zzr zzrVarZza = zzr.NULL_VALUE;
                int size = arrayList.size();
                Object obj2 = null;
                while (i < size) {
                    Object obj3 = arrayList.get(i);
                    zzu zzuVarZzc = zzc(list, obj3);
                    if (zzuVarZzc.zza() != zzr.NULL_VALUE && zzuVarZzc.zza() != zzr.STRING && zzuVarZzc.zza() != zzr.INT && zzuVarZzc.zza() != zzr.DATA_BUNDLE) {
                        String strValueOf2 = String.valueOf(obj3.getClass());
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf2).length() + 130);
                        sb.append("The only ArrayList element types supported by DataBundleUtil are String, Integer, Bundle, and null, but this ArrayList contains a ");
                        sb.append(strValueOf2);
                        throw new IllegalArgumentException(sb.toString());
                    }
                    if (zzrVarZza == zzr.NULL_VALUE && zzuVarZzc.zza() != zzr.NULL_VALUE) {
                        zzrVarZza = zzuVarZzc.zza();
                        obj2 = obj3;
                    } else if (zzuVarZzc.zza() != zzrVarZza) {
                        String strValueOf3 = String.valueOf(obj2.getClass());
                        String strValueOf4 = String.valueOf(obj3.getClass());
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf3).length() + 80 + String.valueOf(strValueOf4).length());
                        sb2.append("ArrayList elements must all be of the sameclass, but this one contains a ");
                        sb2.append(strValueOf3);
                        sb2.append(" and a ");
                        sb2.append(strValueOf4);
                        throw new IllegalArgumentException(sb2.toString());
                    }
                    zzsVarZzp.zzj(zzuVarZzc);
                    i++;
                }
            }
        }
        zzoVarZzc.zzb(zzsVarZzp);
        return zzoVarZzc.zzu();
    }

    private static void zzd(List<Asset> list, DataMap dataMap, String str, zzu zzuVar) {
        zzr zzrVarZza = zzuVar.zza();
        if (zzrVarZza == zzr.NULL_VALUE) {
            dataMap.putString(str, null);
            return;
        }
        zzt zztVarZzb = zzuVar.zzb();
        if (zzrVarZza == zzr.BYTE_ARRAY) {
            dataMap.putByteArray(str, zztVarZzb.zza().zzn());
            return;
        }
        int i = 0;
        if (zzrVarZza == zzr.STRING_ARRAY) {
            dataMap.putStringArray(str, (String[]) zztVarZzb.zzl().toArray(new String[0]));
            return;
        }
        if (zzrVarZza == zzr.LONG_ARRAY) {
            Object[] array = zztVarZzb.zzm().toArray();
            int length = array.length;
            long[] jArr = new long[length];
            while (i < length) {
                Object obj = array[i];
                obj.getClass();
                jArr[i] = ((Number) obj).longValue();
                i++;
            }
            dataMap.putLongArray(str, jArr);
            return;
        }
        if (zzrVarZza == zzr.FLOAT_ARRAY) {
            Object[] array2 = zztVarZzb.zzn().toArray();
            int length2 = array2.length;
            float[] fArr = new float[length2];
            while (i < length2) {
                Object obj2 = array2[i];
                obj2.getClass();
                fArr[i] = ((Number) obj2).floatValue();
                i++;
            }
            dataMap.putFloatArray(str, fArr);
            return;
        }
        if (zzrVarZza == zzr.STRING) {
            dataMap.putString(str, zztVarZzb.zzb());
            return;
        }
        if (zzrVarZza == zzr.DOUBLE) {
            dataMap.putDouble(str, zztVarZzb.zzc());
            return;
        }
        if (zzrVarZza == zzr.FLOAT) {
            dataMap.putFloat(str, zztVarZzb.zzd());
            return;
        }
        if (zzrVarZza == zzr.LONG) {
            dataMap.putLong(str, zztVarZzb.zze());
            return;
        }
        if (zzrVarZza == zzr.INT) {
            dataMap.putInt(str, zztVarZzb.zzf());
            return;
        }
        if (zzrVarZza == zzr.BYTE) {
            dataMap.putByte(str, (byte) zztVarZzb.zzg());
            return;
        }
        if (zzrVarZza == zzr.BOOLEAN) {
            dataMap.putBoolean(str, zztVarZzb.zzh());
            return;
        }
        if (zzrVarZza == zzr.ASSET_INDEX) {
            dataMap.putAsset(str, list.get((int) zztVarZzb.zzo()));
            return;
        }
        if (zzrVarZza == zzr.DATA_BUNDLE) {
            DataMap dataMap2 = new DataMap();
            for (zzv zzvVar : zztVarZzb.zzi()) {
                zzd(list, dataMap2, zzvVar.zza(), zzvVar.zzb());
            }
            dataMap.putDataMap(str, dataMap2);
            return;
        }
        if (zzrVarZza != zzr.ARRAY_LIST) {
            String strValueOf = String.valueOf(zzrVarZza);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 32);
            sb.append("populateBundle: unexpected type ");
            sb.append(strValueOf);
            throw new RuntimeException(sb.toString());
        }
        List<zzu> listZzj = zztVarZzb.zzj();
        zzr zzrVarZza2 = zzr.NULL_VALUE;
        for (zzu zzuVar2 : listZzj) {
            if (zzrVarZza2 != zzr.NULL_VALUE) {
                if (zzuVar2.zza() != zzrVarZza2) {
                    String strValueOf2 = String.valueOf(zzrVarZza2);
                    String strValueOf3 = String.valueOf(zzuVar2.zza());
                    int length3 = String.valueOf(str).length();
                    StringBuilder sb2 = new StringBuilder(length3 + 104 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length());
                    sb2.append("The ArrayList elements should all be the same type, but ArrayList with key ");
                    sb2.append(str);
                    sb2.append(" contains items of type ");
                    sb2.append(strValueOf2);
                    sb2.append(MessageConstants.AND);
                    sb2.append(strValueOf3);
                    throw new IllegalArgumentException(sb2.toString());
                }
            } else if (zzuVar2.zza() == zzr.DATA_BUNDLE || zzuVar2.zza() == zzr.STRING || zzuVar2.zza() == zzr.INT) {
                zzrVarZza2 = zzuVar2.zza();
            } else if (zzuVar2.zza() != zzr.NULL_VALUE) {
                String strValueOf4 = String.valueOf(zzuVar2.zza());
                StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf4).length() + 37 + String.valueOf(str).length());
                sb3.append("Unexpected TypedValue type: ");
                sb3.append(strValueOf4);
                sb3.append(" for key ");
                sb3.append(str);
                throw new IllegalArgumentException(sb3.toString());
            }
        }
        ArrayList<Integer> arrayList = new ArrayList<>(zztVarZzb.zzk());
        for (zzu zzuVar3 : zztVarZzb.zzj()) {
            if (zzuVar3.zza() == zzr.NULL_VALUE) {
                arrayList.add(null);
            } else if (zzrVarZza2 == zzr.DATA_BUNDLE) {
                DataMap dataMap3 = new DataMap();
                for (zzv zzvVar2 : zzuVar3.zzb().zzi()) {
                    zzd(list, dataMap3, zzvVar2.zza(), zzvVar2.zzb());
                }
                arrayList.add(dataMap3);
            } else if (zzrVarZza2 == zzr.STRING) {
                arrayList.add(zzuVar3.zzb().zzb());
            } else {
                if (zzrVarZza2 != zzr.INT) {
                    String strValueOf5 = String.valueOf(zzrVarZza2);
                    StringBuilder sb4 = new StringBuilder(String.valueOf(strValueOf5).length() + 28);
                    sb4.append("Unexpected typeOfArrayList: ");
                    sb4.append(strValueOf5);
                    throw new IllegalArgumentException(sb4.toString());
                }
                arrayList.add(Integer.valueOf(zzuVar3.zzb().zzf()));
            }
        }
        if (zzrVarZza2 == zzr.NULL_VALUE) {
            dataMap.putStringArrayList(str, arrayList);
            return;
        }
        if (zzrVarZza2 == zzr.DATA_BUNDLE) {
            dataMap.putDataMapArrayList(str, arrayList);
            return;
        }
        if (zzrVarZza2 == zzr.STRING) {
            dataMap.putStringArrayList(str, arrayList);
            return;
        }
        if (zzrVarZza2 == zzr.INT) {
            dataMap.putIntegerArrayList(str, arrayList);
            return;
        }
        String strValueOf6 = String.valueOf(zzrVarZza2);
        StringBuilder sb5 = new StringBuilder(String.valueOf(strValueOf6).length() + 28);
        sb5.append("Unexpected typeOfArrayList: ");
        sb5.append(strValueOf6);
        throw new IllegalStateException(sb5.toString());
    }
}
