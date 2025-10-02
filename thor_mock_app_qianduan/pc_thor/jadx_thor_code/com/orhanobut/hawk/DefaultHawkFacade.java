package com.orhanobut.hawk;

/* loaded from: classes2.dex */
public class DefaultHawkFacade implements HawkFacade {
    private final Converter converter;
    private final Encryption encryption;
    private final LogInterceptor logInterceptor;
    private final Serializer serializer;
    private final Storage storage;

    @Override // com.orhanobut.hawk.HawkFacade
    public void destroy() {
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public boolean isBuilt() {
        return true;
    }

    public DefaultHawkFacade(HawkBuilder hawkBuilder) {
        Encryption encryption = hawkBuilder.getEncryption();
        this.encryption = encryption;
        this.storage = hawkBuilder.getStorage();
        this.converter = hawkBuilder.getConverter();
        this.serializer = hawkBuilder.getSerializer();
        LogInterceptor logInterceptor = hawkBuilder.getLogInterceptor();
        this.logInterceptor = logInterceptor;
        logInterceptor.onLog("Hawk.init -> Encryption : " + encryption.getClass().getSimpleName());
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public <T> boolean put(String str, T t) {
        HawkUtils.checkNull("Key", str);
        log("Hawk.put -> key: " + str + ", value: " + t);
        if (t == null) {
            log("Hawk.put -> Value is null. Any existing value will be deleted with the given key");
            return delete(str);
        }
        String string = this.converter.toString(t);
        log("Hawk.put -> Converted to " + string);
        if (string == null) {
            log("Hawk.put -> Converter failed");
            return false;
        }
        String strEncrypt = null;
        try {
            strEncrypt = this.encryption.encrypt(str, string);
            log("Hawk.put -> Encrypted to  " + strEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strEncrypt == null) {
            log("Hawk.put -> Encryption failed");
            return false;
        }
        String strSerialize = this.serializer.serialize(strEncrypt, t);
        log("Hawk.put -> Serialized to" + strSerialize);
        if (strSerialize == null) {
            log("Hawk.put -> Serialization failed");
            return false;
        }
        if (this.storage.put(str, strSerialize)) {
            log("Hawk.put -> Stored successfully");
            return true;
        }
        log("Hawk.put -> Store operation failed");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.orhanobut.hawk.HawkFacade
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public <T> T get(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r0 = "Hawk.get -> Converted to : "
            java.lang.String r1 = "Hawk.get -> Decrypted to : "
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Hawk.get -> key: "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            r6.log(r2)
            r2 = 0
            if (r7 != 0) goto L1f
            java.lang.String r7 = "Hawk.get -> null key, returning null value "
            r6.log(r7)
            return r2
        L1f:
            com.orhanobut.hawk.Storage r3 = r6.storage
            java.lang.Object r3 = r3.get(r7)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Hawk.get -> Fetched from storage : "
            r4.<init>(r5)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            r6.log(r4)
            if (r3 != 0) goto L41
            java.lang.String r7 = "Hawk.get -> Fetching from storage failed"
            r6.log(r7)
            return r2
        L41:
            com.orhanobut.hawk.Serializer r4 = r6.serializer
            com.orhanobut.hawk.DataInfo r3 = r4.deserialize(r3)
            java.lang.String r4 = "Hawk.get -> Deserialized"
            r6.log(r4)
            if (r3 != 0) goto L54
            java.lang.String r7 = "Hawk.get -> Deserialization failed"
            r6.log(r7)
            return r2
        L54:
            com.orhanobut.hawk.Encryption r4 = r6.encryption     // Catch: java.lang.Exception -> L6f
            java.lang.String r5 = r3.cipherText     // Catch: java.lang.Exception -> L6f
            java.lang.String r7 = r4.decrypt(r7, r5)     // Catch: java.lang.Exception -> L6f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L6d
            r4.<init>(r1)     // Catch: java.lang.Exception -> L6d
            java.lang.StringBuilder r1 = r4.append(r7)     // Catch: java.lang.Exception -> L6d
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L6d
            r6.log(r1)     // Catch: java.lang.Exception -> L6d
            goto L87
        L6d:
            r1 = move-exception
            goto L71
        L6f:
            r1 = move-exception
            r7 = r2
        L71:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Hawk.get -> Decrypt failed: "
            r4.<init>(r5)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r1 = r1.toString()
            r6.log(r1)
        L87:
            if (r7 != 0) goto L8f
            java.lang.String r7 = "Hawk.get -> Decrypt failed"
            r6.log(r7)
            return r2
        L8f:
            com.orhanobut.hawk.Converter r1 = r6.converter     // Catch: java.lang.Exception -> La6
            java.lang.Object r2 = r1.fromString(r7, r3)     // Catch: java.lang.Exception -> La6
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> La6
            r7.<init>(r0)     // Catch: java.lang.Exception -> La6
            java.lang.StringBuilder r7 = r7.append(r2)     // Catch: java.lang.Exception -> La6
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Exception -> La6
            r6.log(r7)     // Catch: java.lang.Exception -> La6
            goto Lab
        La6:
            java.lang.String r7 = "Hawk.get -> Converter failed"
            r6.log(r7)
        Lab:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.orhanobut.hawk.DefaultHawkFacade.get(java.lang.String):java.lang.Object");
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public <T> T get(String str, T t) {
        T t2 = (T) get(str);
        return t2 == null ? t : t2;
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public long count() {
        return this.storage.count();
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public boolean deleteAll() {
        return this.storage.deleteAll();
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public boolean delete(String str) {
        return this.storage.delete(str);
    }

    @Override // com.orhanobut.hawk.HawkFacade
    public boolean contains(String str) {
        return this.storage.contains(str);
    }

    private void log(String str) {
        this.logInterceptor.onLog(str);
    }
}
