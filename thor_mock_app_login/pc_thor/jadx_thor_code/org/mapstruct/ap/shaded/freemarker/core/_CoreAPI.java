package org.mapstruct.ap.shaded.freemarker.core;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ImagesContract;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes3.dex */
public class _CoreAPI {
    public static final Set BUILT_IN_DIRECTIVE_NAMES;
    public static final String ERROR_MESSAGE_HR = "----";

    private _CoreAPI() {
    }

    static {
        TreeSet treeSet = new TreeSet();
        treeSet.add("assign");
        treeSet.add("attempt");
        treeSet.add("break");
        treeSet.add(NotificationCompat.CATEGORY_CALL);
        treeSet.add("case");
        treeSet.add("comment");
        treeSet.add("compress");
        treeSet.add("default");
        treeSet.add("else");
        treeSet.add("elseif");
        treeSet.add("escape");
        treeSet.add("fallback");
        treeSet.add("flush");
        treeSet.add("foreach");
        treeSet.add("ftl");
        treeSet.add("function");
        treeSet.add("global");
        treeSet.add("if");
        treeSet.add("import");
        treeSet.add("include");
        treeSet.add("list");
        treeSet.add(ImagesContract.LOCAL);
        treeSet.add("lt");
        treeSet.add("macro");
        treeSet.add("nested");
        treeSet.add("noescape");
        treeSet.add("noparse");
        treeSet.add("nt");
        treeSet.add("recover");
        treeSet.add("recurse");
        treeSet.add("return");
        treeSet.add("rt");
        treeSet.add("setting");
        treeSet.add("stop");
        treeSet.add("switch");
        treeSet.add("t");
        treeSet.add("transform");
        treeSet.add("visit");
        BUILT_IN_DIRECTIVE_NAMES = Collections.unmodifiableSet(treeSet);
    }

    public static Set getSupportedBuiltInNames() {
        return Collections.unmodifiableSet(BuiltIn.builtins.keySet());
    }

    public static void appendInstructionStackItem(TemplateElement templateElement, StringBuffer stringBuffer) {
        Environment.appendInstructionStackItem(templateElement, stringBuffer);
    }

    public static TemplateElement[] getInstructionStackSnapshot(Environment environment) {
        return environment.getInstructionStackSnapshot();
    }

    public static void outputInstructionStack(TemplateElement[] templateElementArr, boolean z, Writer writer) throws IOException {
        Environment.outputInstructionStack(templateElementArr, z, writer);
    }
}
