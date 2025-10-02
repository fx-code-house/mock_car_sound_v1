package com.thor.networkmodule.utils;

import com.thor.networkmodule.model.Currency;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CurrencyUtil.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\b¨\u0006\u000b"}, d2 = {"Lcom/thor/networkmodule/utils/CurrencyUtil;", "", "()V", "getCurrencyList", "Ljava/util/ArrayList;", "Lcom/thor/networkmodule/model/Currency;", "Lkotlin/collections/ArrayList;", "getCurrencySymbol", "", "currencyCode", "defaultCurrencySymbol", "networkmodule_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CurrencyUtil {
    public static final CurrencyUtil INSTANCE = new CurrencyUtil();

    private CurrencyUtil() {
    }

    public final String getCurrencySymbol(String currencyCode, String defaultCurrencySymbol) {
        Intrinsics.checkNotNullParameter(currencyCode, "currencyCode");
        Iterator<Currency> it = getCurrencyList().iterator();
        while (it.hasNext()) {
            Currency next = it.next();
            if (Intrinsics.areEqual(next.getCode(), currencyCode)) {
                return next.getUnicode();
            }
        }
        return defaultCurrencySymbol != null ? defaultCurrencySymbol : currencyCode;
    }

    private final ArrayList<Currency> getCurrencyList() {
        ArrayList<Currency> arrayList = new ArrayList<>();
        arrayList.add(new Currency("$", "AUD"));
        arrayList.add(new Currency("$", "USD"));
        arrayList.add(new Currency("৳", "BDT"));
        arrayList.add(new Currency("л", "BGN"));
        arrayList.add(new Currency("₽", "RUB"));
        arrayList.add(new Currency("€", "EUR"));
        arrayList.add(new Currency("₴", "UAH"));
        arrayList.add(new Currency("£", "GBP"));
        arrayList.add(new Currency("₫", "VND"));
        arrayList.add(new Currency("₵", "GHS"));
        arrayList.add(new Currency("$", "HKD"));
        arrayList.add(new Currency("£", "EGP"));
        arrayList.add(new Currency("₪", "ILS"));
        arrayList.add(new Currency("₹", "INR"));
        arrayList.add(new Currency("₨", "IDR"));
        arrayList.add(new Currency("₸", "KZT"));
        arrayList.add(new Currency("$", "CAD"));
        arrayList.add(new Currency("﷼", "QAR"));
        arrayList.add(new Currency("$", "COP"));
        arrayList.add(new Currency("₡", "CRC"));
        arrayList.add(new Currency("£", "LBP"));
        arrayList.add(new Currency("$", "MXN"));
        arrayList.add(new Currency("₤", "NGN"));
        arrayList.add(new Currency("$", "NZD"));
        arrayList.add(new Currency("₨", "PKR"));
        arrayList.add(new Currency("₲", "PYG"));
        arrayList.add(new Currency("₩", "KRW"));
        arrayList.add(new Currency("$", "SGD"));
        arrayList.add(new Currency("฿", "THB"));
        arrayList.add(new Currency("$", "TWD"));
        arrayList.add(new Currency("₺", "TRY"));
        arrayList.add(new Currency("₱", "PHP"));
        arrayList.add(new Currency("$", "CLP"));
        arrayList.add(new Currency("₨", "LKR"));
        return arrayList;
    }
}
