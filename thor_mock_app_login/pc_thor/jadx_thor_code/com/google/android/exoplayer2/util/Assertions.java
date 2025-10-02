package com.google.android.exoplayer2.util;

import android.os.Looper;
import android.text.TextUtils;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes.dex */
public final class Assertions {
    private Assertions() {
    }

    @Pure
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    @Pure
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    @Pure
    public static int checkIndex(int index, int start, int limit) {
        if (index < start || index >= limit) {
            throw new IndexOutOfBoundsException();
        }
        return index;
    }

    @Pure
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    @Pure
    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    @EnsuresNonNull({"#1"})
    @Pure
    public static <T> T checkStateNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new IllegalStateException();
    }

    @EnsuresNonNull({"#1"})
    @Pure
    public static <T> T checkStateNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new IllegalStateException(String.valueOf(errorMessage));
    }

    @EnsuresNonNull({"#1"})
    @Pure
    public static <T> T checkNotNull(T reference) {
        reference.getClass();
        return reference;
    }

    @EnsuresNonNull({"#1"})
    @Pure
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    @EnsuresNonNull({"#1"})
    @Pure
    public static String checkNotEmpty(String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException();
        }
        return string;
    }

    @EnsuresNonNull({"#1"})
    @Pure
    public static String checkNotEmpty(String string, Object errorMessage) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return string;
    }

    @Pure
    public static void checkMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Not in applications main thread");
        }
    }
}
