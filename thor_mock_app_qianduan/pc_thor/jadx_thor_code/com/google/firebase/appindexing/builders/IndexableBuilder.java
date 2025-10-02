package com.google.firebase.appindexing.builders;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.IndexableBuilder;
import com.google.firebase.appindexing.internal.Thing;
import com.google.firebase.appindexing.internal.zzt;
import java.util.Arrays;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public class IndexableBuilder<T extends IndexableBuilder<?>> {
    private final String type;
    private String url;
    private final Bundle zzay;
    private Thing.zza zzer;

    protected IndexableBuilder(String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotEmpty(str);
        this.zzay = new Bundle();
        this.type = str;
    }

    public final T setName(String str) {
        Preconditions.checkNotNull(str);
        return (T) put(AppMeasurementSdk.ConditionalUserProperty.NAME, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final T setUrl(String str) {
        Preconditions.checkNotNull(str);
        this.url = str;
        return this;
    }

    public final T setImage(String str) {
        Preconditions.checkNotNull(str);
        return (T) put("image", str);
    }

    public final T setDescription(String str) {
        Preconditions.checkNotNull(str);
        return (T) put("description", str);
    }

    public final T setSameAs(String str) {
        Preconditions.checkNotNull(str);
        return (T) put("sameAs", str);
    }

    public final T setKeywords(String... strArr) {
        return (T) put("keywords", strArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T setMetadata(Indexable.Metadata.Builder builder) {
        Preconditions.checkState(this.zzer == null, "setMetadata may only be called once");
        Preconditions.checkNotNull(builder);
        this.zzer = builder.zzaa();
        return this;
    }

    public final Indexable build() {
        Bundle bundle = new Bundle(this.zzay);
        Thing.zza zzaVarZzaa = this.zzer;
        if (zzaVarZzaa == null) {
            zzaVarZzaa = new Indexable.Metadata.Builder().zzaa();
        }
        return new Thing(bundle, zzaVarZzaa, this.url, this.type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(String str, String... strArr) {
        zza(this.zzay, str, strArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(String str, Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
        zza(this.zzay, str, indexableArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(String str, boolean... zArr) {
        zza(this.zzay, str, zArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(String str, long... jArr) {
        zza(this.zzay, str, jArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <S extends IndexableBuilder> T put(String str, S... sArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(sArr);
        if (sArr.length > 0) {
            int length = sArr.length;
            Thing[] thingArr = new Thing[length];
            for (int i = 0; i < sArr.length; i++) {
                S s = sArr[i];
                if (s == null) {
                    zzt.zzn(new StringBuilder(60).append("Builder at ").append(i).append(" is null and is ignored by put method.").toString());
                } else {
                    thingArr[i] = (Thing) s.build();
                }
            }
            if (length > 0) {
                zza(this.zzay, str, thingArr);
            }
        } else {
            zzt.zzn("Builder array is empty and is ignored by put method.");
        }
        return this;
    }

    private static void zza(Bundle bundle, String str, Thing... thingArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(thingArr);
        if (thingArr.length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < thingArr.length; i2++) {
                thingArr[i] = thingArr[i2];
                if (thingArr[i2] == null) {
                    zzt.zzn(new StringBuilder(58).append("Thing at ").append(i2).append(" is null and is ignored by put method.").toString());
                } else {
                    i++;
                }
            }
            if (i > 0) {
                bundle.putParcelableArray(str, (Parcelable[]) zza((Thing[]) Arrays.copyOfRange(thingArr, 0, i)));
                return;
            }
            return;
        }
        zzt.zzn("Thing array is empty and is ignored by put method.");
    }

    public static void zza(Bundle bundle, String str, String... strArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(strArr);
        String[] strArr2 = (String[]) Arrays.copyOf(strArr, strArr.length);
        if (strArr2.length > 0) {
            int i = 0;
            for (int i2 = 0; i2 < Math.min(strArr2.length, 100); i2++) {
                String str2 = strArr2[i2];
                strArr2[i] = str2;
                if (strArr2[i2] == null) {
                    zzt.zzn(new StringBuilder(59).append("String at ").append(i2).append(" is null and is ignored by put method.").toString());
                } else {
                    int i3 = 20000;
                    if (str2.length() > 20000) {
                        zzt.zzn(new StringBuilder(53).append("String at ").append(i2).append(" is too long, truncating string.").toString());
                        String strSubstring = strArr2[i];
                        if (strSubstring.length() > 20000) {
                            if (Character.isHighSurrogate(strSubstring.charAt(19999)) && Character.isLowSurrogate(strSubstring.charAt(20000))) {
                                i3 = 19999;
                            }
                            strSubstring = strSubstring.substring(0, i3);
                        }
                        strArr2[i] = strSubstring;
                    }
                    i++;
                }
            }
            if (i > 0) {
                bundle.putStringArray(str, (String[]) zza((String[]) Arrays.copyOfRange(strArr2, 0, i)));
                return;
            }
            return;
        }
        zzt.zzn("String array is empty and is ignored by put method.");
    }

    public static void zza(Bundle bundle, String str, Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(indexableArr);
        Thing[] thingArr = new Thing[indexableArr.length];
        for (int i = 0; i < indexableArr.length; i++) {
            Indexable indexable = indexableArr[i];
            if (indexable != null && !(indexable instanceof Thing)) {
                throw new FirebaseAppIndexingInvalidArgumentException("Invalid Indexable encountered. Use Indexable.Builder or convenience methods under Indexables to create the Indexable.");
            }
            thingArr[i] = (Thing) indexable;
        }
        zza(bundle, str, thingArr);
    }

    public static void zza(Bundle bundle, String str, boolean... zArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(zArr);
        if (zArr.length > 0) {
            if (zArr.length >= 100) {
                zzt.zzn("Input Array of elements is too big, cutting off.");
                zArr = Arrays.copyOf(zArr, 100);
            }
            bundle.putBooleanArray(str, zArr);
            return;
        }
        zzt.zzn("Boolean array is empty and is ignored by put method.");
    }

    public static void zza(Bundle bundle, String str, long... jArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(jArr);
        if (jArr.length > 0) {
            if (jArr.length >= 100) {
                zzt.zzn("Input Array of elements is too big, cutting off.");
                jArr = Arrays.copyOf(jArr, 100);
            }
            bundle.putLongArray(str, jArr);
            return;
        }
        zzt.zzn("Long array is empty and is ignored by put method.");
    }

    private static <S> S[] zza(S[] sArr) {
        if (sArr.length < 100) {
            return sArr;
        }
        zzt.zzn("Input Array of elements is too big, cutting off.");
        return (S[]) Arrays.copyOf(sArr, 100);
    }
}
