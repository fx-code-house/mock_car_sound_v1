package com.orhanobut.hawk;

/* loaded from: classes2.dex */
public interface HawkFacade {
    boolean contains(String str);

    long count();

    boolean delete(String str);

    boolean deleteAll();

    void destroy();

    <T> T get(String str);

    <T> T get(String str, T t);

    boolean isBuilt();

    <T> boolean put(String str, T t);

    public static class EmptyHawkFacade implements HawkFacade {
        @Override // com.orhanobut.hawk.HawkFacade
        public boolean isBuilt() {
            return false;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public <T> boolean put(String str, T t) {
            throwValidation();
            return false;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public <T> T get(String str) {
            throwValidation();
            return null;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public <T> T get(String str, T t) {
            throwValidation();
            return null;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public long count() {
            throwValidation();
            return 0L;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public boolean deleteAll() {
            throwValidation();
            return false;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public boolean delete(String str) {
            throwValidation();
            return false;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public boolean contains(String str) {
            throwValidation();
            return false;
        }

        @Override // com.orhanobut.hawk.HawkFacade
        public void destroy() {
            throwValidation();
        }

        private void throwValidation() {
            throw new IllegalStateException("Hawk is not built. Please call build() and wait the initialisation finishes.");
        }
    }
}
