package com.google.firebase.appindexing.builders;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Date;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class ReservationBuilder extends IndexableBuilder<ReservationBuilder> {
    ReservationBuilder() {
        super("Reservation");
    }

    public final ReservationBuilder setStartDate(Date date) {
        Preconditions.checkNotNull(date);
        return put("startDate", date.getTime());
    }

    public final ReservationBuilder setPartySize(long j) {
        return put("partySize", j);
    }

    public final ReservationBuilder setReservationFor(LocalBusinessBuilder localBusinessBuilder) {
        return put("reservationFor", localBusinessBuilder);
    }
}
