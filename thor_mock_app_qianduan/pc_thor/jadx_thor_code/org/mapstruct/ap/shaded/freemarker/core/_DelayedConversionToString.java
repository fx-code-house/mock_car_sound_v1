package org.mapstruct.ap.shaded.freemarker.core;

/* loaded from: classes3.dex */
public abstract class _DelayedConversionToString {
    private static final String NOT_SET = new String();
    private Object object;
    private String stringValue = NOT_SET;

    protected abstract String doConversion(Object obj);

    public _DelayedConversionToString(Object obj) {
        this.object = obj;
    }

    public synchronized String toString() {
        if (this.stringValue == NOT_SET) {
            this.stringValue = doConversion(this.object);
            this.object = null;
        }
        return this.stringValue;
    }
}
