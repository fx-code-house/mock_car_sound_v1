package org.mapstruct.ap.shaded.freemarker.debug.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import org.mapstruct.ap.shaded.freemarker.debug.DebugModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateDateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateTransformModel;

/* loaded from: classes3.dex */
class RmiDebugModelImpl extends UnicastRemoteObject implements DebugModel {
    private static final long serialVersionUID = 1;
    private final TemplateModel model;
    private final int type;

    RmiDebugModelImpl(TemplateModel templateModel, int i) throws RemoteException {
        this.model = templateModel;
        this.type = calculateType(templateModel) + i;
    }

    private static DebugModel getDebugModel(TemplateModel templateModel) throws RemoteException {
        return (DebugModel) RmiDebuggedEnvironmentImpl.getCachedWrapperFor(templateModel);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public String getAsString() throws TemplateModelException {
        return ((TemplateScalarModel) this.model).getAsString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public Number getAsNumber() throws TemplateModelException {
        return ((TemplateNumberModel) this.model).getAsNumber();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public Date getAsDate() throws TemplateModelException {
        return ((TemplateDateModel) this.model).getAsDate();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public int getDateType() {
        return ((TemplateDateModel) this.model).getDateType();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public boolean getAsBoolean() throws TemplateModelException {
        return ((TemplateBooleanModel) this.model).getAsBoolean();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public int size() throws TemplateModelException {
        TemplateModel templateModel = this.model;
        if (templateModel instanceof TemplateSequenceModel) {
            return ((TemplateSequenceModel) templateModel).size();
        }
        return ((TemplateHashModelEx) templateModel).size();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public DebugModel get(int i) throws TemplateModelException, RemoteException {
        return getDebugModel(((TemplateSequenceModel) this.model).get(i));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public DebugModel[] get(int i, int i2) throws TemplateModelException, RemoteException {
        DebugModel[] debugModelArr = new DebugModel[i2 - i];
        TemplateSequenceModel templateSequenceModel = (TemplateSequenceModel) this.model;
        for (int i3 = i; i3 < i2; i3++) {
            debugModelArr[i3 - i] = getDebugModel(templateSequenceModel.get(i3));
        }
        return debugModelArr;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public DebugModel[] getCollection() throws TemplateModelException, RemoteException {
        ArrayList arrayList = new ArrayList();
        TemplateModelIterator it = ((TemplateCollectionModel) this.model).iterator();
        while (it.hasNext()) {
            arrayList.add(getDebugModel(it.next()));
        }
        return (DebugModel[]) arrayList.toArray(new DebugModel[arrayList.size()]);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public DebugModel get(String str) throws TemplateModelException, RemoteException {
        return getDebugModel(((TemplateHashModel) this.model).get(str));
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public DebugModel[] get(String[] strArr) throws TemplateModelException, RemoteException {
        DebugModel[] debugModelArr = new DebugModel[strArr.length];
        TemplateHashModel templateHashModel = (TemplateHashModel) this.model;
        for (int i = 0; i < strArr.length; i++) {
            debugModelArr[i] = getDebugModel(templateHashModel.get(strArr[i]));
        }
        return debugModelArr;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public String[] keys() throws TemplateModelException {
        TemplateHashModelEx templateHashModelEx = (TemplateHashModelEx) this.model;
        ArrayList arrayList = new ArrayList();
        TemplateModelIterator it = templateHashModelEx.keys().iterator();
        while (it.hasNext()) {
            arrayList.add(((TemplateScalarModel) it.next()).getAsString());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.debug.DebugModel
    public int getModelTypes() {
        return this.type;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [boolean, int] */
    private static int calculateType(TemplateModel templateModel) {
        int i;
        int i2;
        ?? r0 = templateModel instanceof TemplateScalarModel;
        int i3 = r0;
        if (templateModel instanceof TemplateNumberModel) {
            i3 = r0 + 2;
        }
        int i4 = i3;
        if (templateModel instanceof TemplateDateModel) {
            i4 = i3 + 4;
        }
        int i5 = i4;
        if (templateModel instanceof TemplateBooleanModel) {
            i5 = i4 + 8;
        }
        int i6 = i5;
        if (templateModel instanceof TemplateSequenceModel) {
            i6 = i5 + 16;
        }
        int i7 = i6;
        if (templateModel instanceof TemplateCollectionModel) {
            i7 = i6 + 32;
        }
        if (templateModel instanceof TemplateHashModelEx) {
            i = i7 + 128;
        } else {
            i = i7;
            if (templateModel instanceof TemplateHashModel) {
                i = i7 + 64;
            }
        }
        if (templateModel instanceof TemplateMethodModelEx) {
            i2 = i + 512;
        } else {
            i2 = i;
            if (templateModel instanceof TemplateMethodModel) {
                i2 = i + 256;
            }
        }
        return templateModel instanceof TemplateTransformModel ? i2 + 1024 : i2;
    }
}
