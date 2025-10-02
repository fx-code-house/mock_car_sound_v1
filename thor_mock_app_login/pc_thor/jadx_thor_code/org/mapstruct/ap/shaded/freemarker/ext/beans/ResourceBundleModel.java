package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;

/* loaded from: classes3.dex */
public class ResourceBundleModel extends BeanModel implements TemplateMethodModelEx {
    static final ModelFactory FACTORY = new ModelFactory() { // from class: org.mapstruct.ap.shaded.freemarker.ext.beans.ResourceBundleModel.1
        @Override // org.mapstruct.ap.shaded.freemarker.ext.util.ModelFactory
        public TemplateModel create(Object obj, ObjectWrapper objectWrapper) {
            return new ResourceBundleModel((ResourceBundle) obj, (BeansWrapper) objectWrapper);
        }
    };
    private Hashtable formats;

    public ResourceBundleModel(ResourceBundle resourceBundle, BeansWrapper beansWrapper) {
        super(resourceBundle, beansWrapper);
        this.formats = null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel
    protected TemplateModel invokeGenericGet(Map map, Class cls, String str) throws TemplateModelException {
        try {
            return wrap(((ResourceBundle) this.object).getObject(str));
        } catch (MissingResourceException unused) {
            throw new TemplateModelException(new StringBuffer("No such key: ").append(str).toString());
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModel
    public boolean isEmpty() {
        return !((ResourceBundle) this.object).getKeys().hasMoreElements() && super.isEmpty();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel, org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx
    public int size() {
        return keySet().size();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.ext.beans.BeanModel
    protected Set keySet() {
        Set setKeySet = super.keySet();
        Enumeration<String> keys = ((ResourceBundle) this.object).getKeys();
        while (keys.hasMoreElements()) {
            setKeySet.add(keys.nextElement());
        }
        return setKeySet;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 1) {
            throw new TemplateModelException("No message key was specified");
        }
        Iterator it = list.iterator();
        String string = unwrap((TemplateModel) it.next()).toString();
        try {
            if (!it.hasNext()) {
                return wrap(((ResourceBundle) this.object).getObject(string));
            }
            int size = list.size() - 1;
            Object[] objArr = new Object[size];
            for (int i = 0; i < size; i++) {
                objArr[i] = unwrap((TemplateModel) it.next());
            }
            return new StringModel(format(string, objArr), this.wrapper);
        } catch (MissingResourceException unused) {
            throw new TemplateModelException(new StringBuffer("No such key: ").append(string).toString());
        } catch (Exception e) {
            throw new TemplateModelException(e.getMessage());
        }
    }

    public String format(String str, Object[] objArr) throws MissingResourceException {
        String str2;
        if (this.formats == null) {
            this.formats = new Hashtable();
        }
        MessageFormat messageFormat = (MessageFormat) this.formats.get(str);
        if (messageFormat == null) {
            messageFormat = new MessageFormat(((ResourceBundle) this.object).getString(str));
            messageFormat.setLocale(getBundle().getLocale());
            this.formats.put(str, messageFormat);
        }
        synchronized (messageFormat) {
            str2 = messageFormat.format(objArr);
        }
        return str2;
    }

    public ResourceBundle getBundle() {
        return (ResourceBundle) this.object;
    }
}
