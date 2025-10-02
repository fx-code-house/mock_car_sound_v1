package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public interface TimeLimiter {
    <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit) throws ExecutionException, TimeoutException;

    <T> T callWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException;

    <T> T newProxy(T t, Class<T> cls, long j, TimeUnit timeUnit);

    void runUninterruptiblyWithTimeout(Runnable runnable, long j, TimeUnit timeUnit) throws TimeoutException;

    void runWithTimeout(Runnable runnable, long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException;
}
