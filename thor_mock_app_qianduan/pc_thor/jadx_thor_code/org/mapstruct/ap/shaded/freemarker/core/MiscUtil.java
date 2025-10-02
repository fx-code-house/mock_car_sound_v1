package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
class MiscUtil {
    static final String C_FALSE = "false";
    static final String C_TRUE = "true";

    private MiscUtil() {
    }

    static List sortMapOfExpressions(Map map) {
        ArrayList arrayList = new ArrayList(map.entrySet());
        Collections.sort(arrayList, new Comparator() { // from class: org.mapstruct.ap.shaded.freemarker.core.MiscUtil.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                Map.Entry entry = (Map.Entry) obj;
                Expression expression = (Expression) entry.getValue();
                Map.Entry entry2 = (Map.Entry) obj2;
                Expression expression2 = (Expression) entry2.getValue();
                int i = expression.beginLine - expression2.beginLine;
                if (i != 0) {
                    return i;
                }
                int i2 = expression.beginColumn - expression2.beginColumn;
                if (i2 != 0) {
                    return i2;
                }
                if (entry == entry2) {
                    return 0;
                }
                return ((String) entry.getKey()).compareTo((String) entry.getKey());
            }
        });
        return arrayList;
    }
}
