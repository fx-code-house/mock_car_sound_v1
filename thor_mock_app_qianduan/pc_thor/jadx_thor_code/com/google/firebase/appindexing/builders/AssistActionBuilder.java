package com.google.firebase.appindexing.builders;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.appindexing.Action;

/* compiled from: com.google.firebase:firebase-appindexing@@19.1.0 */
/* loaded from: classes2.dex */
public final class AssistActionBuilder extends Action.Builder {
    private String zzeq;

    public AssistActionBuilder() {
        super("AssistAction");
    }

    public final AssistActionBuilder setActionToken(String str) {
        Preconditions.checkNotNull(str);
        this.zzeq = str;
        return this;
    }

    @Override // com.google.firebase.appindexing.Action.Builder
    public final Action build() {
        Preconditions.checkNotNull(this.zzeq, "setActionToken is required before calling build().");
        Preconditions.checkNotNull(zzy(), "setActionStatus is required before calling build().");
        put("actionToken", this.zzeq);
        if (getName() == null) {
            setName("AssistAction");
        }
        if (getUrl() == null) {
            String strValueOf = String.valueOf(this.zzeq);
            setUrl(strValueOf.length() != 0 ? "https://developers.google.com/actions?invocation=".concat(strValueOf) : new String("https://developers.google.com/actions?invocation="));
        }
        return super.build();
    }
}
