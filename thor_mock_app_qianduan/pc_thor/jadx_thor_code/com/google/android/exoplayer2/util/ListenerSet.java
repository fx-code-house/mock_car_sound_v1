package com.google.android.exoplayer2.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.exoplayer2.util.FlagSet;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nonnull;

/* loaded from: classes.dex */
public final class ListenerSet<T> {
    private static final int MSG_ITERATION_FINISHED = 0;
    private final Clock clock;
    private final ArrayDeque<Runnable> flushingEvents;
    private final HandlerWrapper handler;
    private final IterationFinishedEvent<T> iterationFinishedEvent;
    private final CopyOnWriteArraySet<ListenerHolder<T>> listeners;
    private final ArrayDeque<Runnable> queuedEvents;
    private boolean released;

    public interface Event<T> {
        void invoke(T listener);
    }

    public interface IterationFinishedEvent<T> {
        void invoke(T listener, FlagSet eventFlags);
    }

    public ListenerSet(Looper looper, Clock clock, IterationFinishedEvent<T> iterationFinishedEvent) {
        this(new CopyOnWriteArraySet(), looper, clock, iterationFinishedEvent);
    }

    private ListenerSet(CopyOnWriteArraySet<ListenerHolder<T>> listeners, Looper looper, Clock clock, IterationFinishedEvent<T> iterationFinishedEvent) {
        this.clock = clock;
        this.listeners = listeners;
        this.iterationFinishedEvent = iterationFinishedEvent;
        this.flushingEvents = new ArrayDeque<>();
        this.queuedEvents = new ArrayDeque<>();
        this.handler = clock.createHandler(looper, new Handler.Callback() { // from class: com.google.android.exoplayer2.util.ListenerSet$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.handleMessage(message);
            }
        });
    }

    public ListenerSet<T> copy(Looper looper, IterationFinishedEvent<T> iterationFinishedEvent) {
        return new ListenerSet<>(this.listeners, looper, this.clock, iterationFinishedEvent);
    }

    public void add(T listener) {
        if (this.released) {
            return;
        }
        Assertions.checkNotNull(listener);
        this.listeners.add(new ListenerHolder<>(listener));
    }

    public void remove(T listener) {
        Iterator<ListenerHolder<T>> it = this.listeners.iterator();
        while (it.hasNext()) {
            ListenerHolder<T> next = it.next();
            if (next.listener.equals(listener)) {
                next.release(this.iterationFinishedEvent);
                this.listeners.remove(next);
            }
        }
    }

    public int size() {
        return this.listeners.size();
    }

    public void queueEvent(final int eventFlag, final Event<T> event) {
        final CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet(this.listeners);
        this.queuedEvents.add(new Runnable() { // from class: com.google.android.exoplayer2.util.ListenerSet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ListenerSet.lambda$queueEvent$0(copyOnWriteArraySet, eventFlag, event);
            }
        });
    }

    static /* synthetic */ void lambda$queueEvent$0(CopyOnWriteArraySet copyOnWriteArraySet, int i, Event event) {
        Iterator it = copyOnWriteArraySet.iterator();
        while (it.hasNext()) {
            ((ListenerHolder) it.next()).invoke(i, event);
        }
    }

    public void flushEvents() {
        if (this.queuedEvents.isEmpty()) {
            return;
        }
        if (!this.handler.hasMessages(0)) {
            HandlerWrapper handlerWrapper = this.handler;
            handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(0));
        }
        boolean z = !this.flushingEvents.isEmpty();
        this.flushingEvents.addAll(this.queuedEvents);
        this.queuedEvents.clear();
        if (z) {
            return;
        }
        while (!this.flushingEvents.isEmpty()) {
            this.flushingEvents.peekFirst().run();
            this.flushingEvents.removeFirst();
        }
    }

    public void sendEvent(int eventFlag, Event<T> event) {
        queueEvent(eventFlag, event);
        flushEvents();
    }

    public void release() {
        Iterator<ListenerHolder<T>> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().release(this.iterationFinishedEvent);
        }
        this.listeners.clear();
        this.released = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(Message message) {
        Iterator<ListenerHolder<T>> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().iterationFinished(this.iterationFinishedEvent);
            if (this.handler.hasMessages(0)) {
                return true;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ListenerHolder<T> {
        private FlagSet.Builder flagsBuilder = new FlagSet.Builder();

        @Nonnull
        public final T listener;
        private boolean needsIterationFinishedEvent;
        private boolean released;

        public ListenerHolder(@Nonnull T listener) {
            this.listener = listener;
        }

        public void release(IterationFinishedEvent<T> event) {
            this.released = true;
            if (this.needsIterationFinishedEvent) {
                event.invoke(this.listener, this.flagsBuilder.build());
            }
        }

        public void invoke(int eventFlag, Event<T> event) {
            if (this.released) {
                return;
            }
            if (eventFlag != -1) {
                this.flagsBuilder.add(eventFlag);
            }
            this.needsIterationFinishedEvent = true;
            event.invoke(this.listener);
        }

        public void iterationFinished(IterationFinishedEvent<T> event) {
            if (this.released || !this.needsIterationFinishedEvent) {
                return;
            }
            FlagSet flagSetBuild = this.flagsBuilder.build();
            this.flagsBuilder = new FlagSet.Builder();
            this.needsIterationFinishedEvent = false;
            event.invoke(this.listener, flagSetBuild);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return this.listener.equals(((ListenerHolder) other).listener);
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }
}
