package com.google.firebase.appindexing.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.appindexing.FirebaseAppIndexingException;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.FirebaseAppIndexingTooManyArgumentsException;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class zzaf {
    public static FirebaseAppIndexingException zza(Status status, String str) {
        Preconditions.checkNotNull(status);
        String statusMessage = status.getStatusMessage();
        if (statusMessage != null && !statusMessage.isEmpty()) {
            str = statusMessage;
        }
        int statusCode = status.getStatusCode();
        if (statusCode == 17510) {
            return new FirebaseAppIndexingInvalidArgumentException(str);
        }
        if (statusCode == 17511) {
            return new FirebaseAppIndexingTooManyArgumentsException(str);
        }
        if (statusCode == 17602) {
            return new com.google.firebase.appindexing.zzh(str);
        }
        switch (statusCode) {
            case 17513:
                return new com.google.firebase.appindexing.zzb(str);
            case 17514:
                return new com.google.firebase.appindexing.zza(str);
            case 17515:
                return new com.google.firebase.appindexing.zzg(str);
            case 17516:
                return new com.google.firebase.appindexing.zze(str);
            case 17517:
                return new com.google.firebase.appindexing.zzf(str);
            case 17518:
                return new com.google.firebase.appindexing.zzd(str);
            case 17519:
                return new com.google.firebase.appindexing.zzc(str);
            default:
                return new FirebaseAppIndexingException(str);
        }
    }
}
