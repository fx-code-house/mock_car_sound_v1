package com.google.firebase.appindexing;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.icing.zzhj;
import com.google.firebase.appindexing.builders.IndexableBuilder;
import com.google.firebase.appindexing.internal.Thing;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public interface Indexable {
    public static final int MAX_BYTE_SIZE = 30000;
    public static final int MAX_INDEXABLES_TO_BE_UPDATED_IN_ONE_CALL = 1000;
    public static final int MAX_NESTING_DEPTH = 5;
    public static final int MAX_NUMBER_OF_FIELDS = 20;
    public static final int MAX_REPEATED_SIZE = 100;
    public static final int MAX_STRING_LENGTH = 20000;
    public static final int MAX_URL_LENGTH = 256;

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public static class Builder extends IndexableBuilder<Builder> {
        public Builder() {
            this("Thing");
        }

        public Builder(String str) {
            super(str);
        }
    }

    /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
    public interface Metadata {

        /* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
        public static final class Builder {
            private boolean zzeo = zzhj.zza.zzdx().zzdv();
            private int score = zzhj.zza.zzdx().getScore();
            private String zzep = zzhj.zza.zzdx().zzdw();
            private final Bundle zzay = new Bundle();

            public final Builder setWorksOffline(boolean z) {
                this.zzeo = z;
                return this;
            }

            public final Builder setScore(int i) {
                Preconditions.checkArgument(i >= 0, new StringBuilder(53).append("Negative score values are invalid. Value: ").append(i).toString());
                this.score = i;
                return this;
            }

            public final Builder setScope(int i) {
                Preconditions.checkArgument(i > 0 && i <= 3, new StringBuilder(69).append("The scope of this indexable is not valid, scope value is ").append(i).append(".").toString());
                IndexableBuilder.zza(this.zzay, "scope", i);
                return this;
            }

            public final Builder setSliceUri(Uri uri) {
                Preconditions.checkNotNull(uri);
                IndexableBuilder.zza(this.zzay, "grantSlicePermission", true);
                IndexableBuilder.zza(this.zzay, "sliceUri", uri.toString());
                return this;
            }

            public final Thing.zza zzaa() {
                return new Thing.zza(this.zzeo, this.score, this.zzep, this.zzay);
            }
        }
    }
}
