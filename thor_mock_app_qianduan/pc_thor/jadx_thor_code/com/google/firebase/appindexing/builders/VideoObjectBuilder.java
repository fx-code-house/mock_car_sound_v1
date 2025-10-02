package com.google.firebase.appindexing.builders;

import java.util.Date;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class VideoObjectBuilder extends IndexableBuilder<VideoObjectBuilder> {
    VideoObjectBuilder() {
        super("VideoObject");
    }

    public final VideoObjectBuilder setAuthor(PersonBuilder personBuilder) {
        return put("author", personBuilder);
    }

    public final VideoObjectBuilder setDuration(long j) {
        return put("duration", j);
    }

    public final VideoObjectBuilder setDurationWatched(long j) {
        return put("durationWatched", j);
    }

    public final VideoObjectBuilder setUploadDate(Date date) {
        return put("uploadDate", date.getTime());
    }

    public final VideoObjectBuilder setSeriesName(String str) {
        return put("seriesName", str);
    }

    public final VideoObjectBuilder setLocationCreated(PlaceBuilder placeBuilder) {
        return put("locationCreated", placeBuilder);
    }
}
