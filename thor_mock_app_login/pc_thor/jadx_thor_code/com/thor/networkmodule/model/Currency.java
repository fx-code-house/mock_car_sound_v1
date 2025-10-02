package com.thor.networkmodule.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Currency.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/thor/networkmodule/model/Currency;", "", "unicode", "", "code", "(Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "getUnicode", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class Currency {
    private final String code;
    private final String unicode;

    public static /* synthetic */ Currency copy$default(Currency currency, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = currency.unicode;
        }
        if ((i & 2) != 0) {
            str2 = currency.code;
        }
        return currency.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String getUnicode() {
        return this.unicode;
    }

    /* renamed from: component2, reason: from getter */
    public final String getCode() {
        return this.code;
    }

    public final Currency copy(String unicode, String code) {
        Intrinsics.checkNotNullParameter(unicode, "unicode");
        Intrinsics.checkNotNullParameter(code, "code");
        return new Currency(unicode, code);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Currency)) {
            return false;
        }
        Currency currency = (Currency) other;
        return Intrinsics.areEqual(this.unicode, currency.unicode) && Intrinsics.areEqual(this.code, currency.code);
    }

    public int hashCode() {
        return (this.unicode.hashCode() * 31) + this.code.hashCode();
    }

    public String toString() {
        return "Currency(unicode=" + this.unicode + ", code=" + this.code + ")";
    }

    public Currency(String unicode, String code) {
        Intrinsics.checkNotNullParameter(unicode, "unicode");
        Intrinsics.checkNotNullParameter(code, "code");
        this.unicode = unicode;
        this.code = code;
    }

    public final String getUnicode() {
        return this.unicode;
    }

    public final String getCode() {
        return this.code;
    }
}
