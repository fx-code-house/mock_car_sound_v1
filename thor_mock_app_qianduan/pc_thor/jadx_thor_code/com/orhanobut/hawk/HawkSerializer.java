package com.orhanobut.hawk;

/* loaded from: classes2.dex */
class HawkSerializer implements Serializer {
    private static final char DELIMITER = '@';
    private static final String INFO_DELIMITER = "#";
    private static final char NEW_VERSION = 'V';
    private final LogInterceptor logInterceptor;

    HawkSerializer(LogInterceptor logInterceptor) {
        this.logInterceptor = logInterceptor;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a6  */
    @Override // com.orhanobut.hawk.Serializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public <T> java.lang.String serialize(java.lang.String r5, T r6) {
        /*
            r4 = this;
            java.lang.String r0 = "Cipher text"
            com.orhanobut.hawk.HawkUtils.checkNullOrEmpty(r0, r5)
            java.lang.String r0 = "Value"
            com.orhanobut.hawk.HawkUtils.checkNull(r0, r6)
            java.lang.Class<java.util.List> r0 = java.util.List.class
            java.lang.Class r1 = r6.getClass()
            boolean r0 = r0.isAssignableFrom(r1)
            java.lang.String r1 = ""
            if (r0 == 0) goto L33
            java.util.List r6 = (java.util.List) r6
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto L2e
            r0 = 0
            java.lang.Object r6 = r6.get(r0)
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getName()
            goto L2f
        L2e:
            r6 = r1
        L2f:
            r0 = 49
            goto Lb4
        L33:
            java.lang.Class<java.util.Map> r0 = java.util.Map.class
            java.lang.Class r2 = r6.getClass()
            boolean r0 = r0.isAssignableFrom(r2)
            if (r0 == 0) goto L7b
            java.util.Map r6 = (java.util.Map) r6
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto L74
            java.util.Set r6 = r6.entrySet()
            java.util.Iterator r6 = r6.iterator()
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L74
            java.lang.Object r6 = r6.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r0 = r6.getKey()
            java.lang.Class r0 = r0.getClass()
            java.lang.String r1 = r0.getName()
            java.lang.Object r6 = r6.getValue()
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getName()
            goto L75
        L74:
            r6 = r1
        L75:
            r0 = 50
            r3 = r1
            r1 = r6
            r6 = r3
            goto Lb4
        L7b:
            java.lang.Class<java.util.Set> r0 = java.util.Set.class
            java.lang.Class r2 = r6.getClass()
            boolean r0 = r0.isAssignableFrom(r2)
            if (r0 == 0) goto Laa
            java.util.Set r6 = (java.util.Set) r6
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto La6
            java.util.Iterator r6 = r6.iterator()
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto La6
            java.lang.Object r6 = r6.next()
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getName()
            goto La7
        La6:
            r6 = r1
        La7:
            r0 = 51
            goto Lb4
        Laa:
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getName()
            r0 = 48
        Lb4:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r6 = r2.append(r6)
            java.lang.String r2 = "#"
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r0 = "V@"
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.String r5 = r5.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.orhanobut.hawk.HawkSerializer.serialize(java.lang.String, java.lang.Object):java.lang.String");
    }

    @Override // com.orhanobut.hawk.Serializer
    public DataInfo deserialize(String str) throws ClassNotFoundException {
        Class<?> cls;
        String[] strArrSplit = str.split(INFO_DELIMITER);
        char cCharAt = strArrSplit[2].charAt(0);
        String str2 = strArrSplit[0];
        Class<?> cls2 = null;
        if (str2 == null || str2.length() == 0) {
            cls = null;
        } else {
            try {
                cls = Class.forName(str2);
            } catch (ClassNotFoundException e) {
                this.logInterceptor.onLog("HawkSerializer -> " + e.getMessage());
            }
        }
        String str3 = strArrSplit[1];
        if (str3 != null && str3.length() != 0) {
            try {
                cls2 = Class.forName(str3);
            } catch (ClassNotFoundException e2) {
                this.logInterceptor.onLog("HawkSerializer -> " + e2.getMessage());
            }
        }
        return new DataInfo(cCharAt, getCipherText(strArrSplit[strArrSplit.length - 1]), cls, cls2);
    }

    private String getCipherText(String str) {
        int iIndexOf = str.indexOf(64);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Text should contain delimiter");
        }
        return str.substring(iIndexOf + 1);
    }
}
