package com.google.android.exoplayer2.util;

import android.util.SparseBooleanArray;

/* loaded from: classes.dex */
public final class FlagSet {
    private final SparseBooleanArray flags;

    public static final class Builder {
        private boolean buildCalled;
        private final SparseBooleanArray flags = new SparseBooleanArray();

        public Builder add(int flag) {
            Assertions.checkState(!this.buildCalled);
            this.flags.append(flag, true);
            return this;
        }

        public Builder addIf(int flag, boolean condition) {
            return condition ? add(flag) : this;
        }

        public Builder addAll(int... flags) {
            for (int i : flags) {
                add(i);
            }
            return this;
        }

        public Builder addAll(FlagSet flags) {
            for (int i = 0; i < flags.size(); i++) {
                add(flags.get(i));
            }
            return this;
        }

        public Builder remove(int flag) {
            Assertions.checkState(!this.buildCalled);
            this.flags.delete(flag);
            return this;
        }

        public Builder removeIf(int flag, boolean condition) {
            return condition ? remove(flag) : this;
        }

        public Builder removeAll(int... flags) {
            for (int i : flags) {
                remove(i);
            }
            return this;
        }

        public FlagSet build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            return new FlagSet(this.flags);
        }
    }

    private FlagSet(SparseBooleanArray flags) {
        this.flags = flags;
    }

    public boolean contains(int flag) {
        return this.flags.get(flag);
    }

    public boolean containsAny(int... flags) {
        for (int i : flags) {
            if (contains(i)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.flags.size();
    }

    public int get(int index) {
        Assertions.checkIndex(index, 0, size());
        return this.flags.keyAt(index);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof FlagSet) {
            return this.flags.equals(((FlagSet) o).flags);
        }
        return false;
    }

    public int hashCode() {
        return this.flags.hashCode();
    }
}
