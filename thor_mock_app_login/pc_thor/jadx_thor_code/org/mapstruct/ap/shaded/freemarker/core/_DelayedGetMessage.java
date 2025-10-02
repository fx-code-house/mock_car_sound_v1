package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
public class _DelayedGetMessage extends _DelayedConversionToString {
    public _DelayedGetMessage(Throwable th) {
        super(th);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core._DelayedConversionToString
    protected String doConversion(Object obj) {
        return ((Throwable) obj).getMessage();
    }
}
