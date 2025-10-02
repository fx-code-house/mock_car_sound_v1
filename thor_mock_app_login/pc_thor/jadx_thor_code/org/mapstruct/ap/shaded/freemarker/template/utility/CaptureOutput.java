package org.mapstruct.ap.shaded.freemarker.template.utility;

import com.google.android.gms.common.internal.ImagesContract;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
public class CaptureOutput implements TemplateTransformModel {
    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel
    public Writer getWriter(final Writer writer, Map map) throws TemplateModelException {
        final boolean z;
        final boolean z2;
        boolean z3;
        if (map == null) {
            throw new TemplateModelException("Must specify the name of the variable in which to capture the output with the 'var' or 'local' or 'global' parameter.");
        }
        final TemplateModel templateModel = (TemplateModel) map.get("namespace");
        Object obj = map.get("var");
        boolean z4 = false;
        if (obj == null) {
            obj = map.get(ImagesContract.LOCAL);
            if (obj == null) {
                obj = map.get("global");
                z3 = true;
            } else {
                z3 = false;
                z4 = true;
            }
            if (obj == null) {
                throw new TemplateModelException("Must specify the name of the variable in which to capture the output with the 'var' or 'local' or 'global' parameter.");
            }
            z = z4;
            z2 = z3;
        } else {
            z = false;
            z2 = false;
        }
        if (map.size() == 2) {
            if (templateModel == null) {
                throw new TemplateModelException("Second parameter can only be namespace");
            }
            if (z) {
                throw new TemplateModelException("Cannot specify namespace for a local assignment");
            }
            if (z2) {
                throw new TemplateModelException("Cannot specify namespace for a global assignment");
            }
            if (!(templateModel instanceof Environment.Namespace)) {
                throw new TemplateModelException(new StringBuffer("namespace parameter does not specify a namespace. It is a ").append(templateModel.getClass().getName()).toString());
            }
        } else if (map.size() != 1) {
            throw new TemplateModelException("Bad parameters. Use only one of 'var' or 'local' or 'global' parameters.");
        }
        if (!(obj instanceof TemplateScalarModel)) {
            throw new TemplateModelException("'var' or 'local' or 'global' parameter doesn't evaluate to a string");
        }
        final String asString = ((TemplateScalarModel) obj).getAsString();
        if (asString == null) {
            throw new TemplateModelException("'var' or 'local' or 'global' parameter evaluates to null string");
        }
        final StringBuffer stringBuffer = new StringBuffer();
        final Environment currentEnvironment = Environment.getCurrentEnvironment();
        return new Writer() { // from class: org.mapstruct.ap.shaded.freemarker.template.utility.CaptureOutput.1
            @Override // java.io.Writer
            public void write(char[] cArr, int i, int i2) {
                stringBuffer.append(cArr, i, i2);
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() throws IOException {
                writer.flush();
            }

            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                SimpleScalar simpleScalar = new SimpleScalar(stringBuffer.toString());
                try {
                    if (z) {
                        currentEnvironment.setLocalVariable(asString, simpleScalar);
                        return;
                    }
                    if (z2) {
                        currentEnvironment.setGlobalVariable(asString, simpleScalar);
                        return;
                    }
                    TemplateModel templateModel2 = templateModel;
                    if (templateModel2 == null) {
                        currentEnvironment.setVariable(asString, simpleScalar);
                    } else {
                        ((Environment.Namespace) templateModel2).put(asString, simpleScalar);
                    }
                } catch (IllegalStateException e) {
                    throw new IOException(new StringBuffer("Could not set variable ").append(asString).append(": ").append(e.getMessage()).toString());
                }
            }
        };
    }
}
