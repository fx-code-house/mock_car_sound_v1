package org.mapstruct.ap.shaded.freemarker.debug;

import java.util.EventObject;

/* loaded from: classes3.dex */
public class EnvironmentSuspendedEvent extends EventObject {
    private static final long serialVersionUID = 1;
    private final DebuggedEnvironment env;
    private final int line;
    private final String name;

    public EnvironmentSuspendedEvent(Object obj, String str, int i, DebuggedEnvironment debuggedEnvironment) {
        super(obj);
        this.name = str;
        this.line = i;
        this.env = debuggedEnvironment;
    }

    public String getName() {
        return this.name;
    }

    public int getLine() {
        return this.line;
    }

    public DebuggedEnvironment getEnvironment() {
        return this.env;
    }
}
