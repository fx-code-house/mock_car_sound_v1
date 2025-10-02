package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class OptInTemplateClassResolver implements TemplateClassResolver {
    private final Set allowedClasses;
    private final Set trustedTemplateNames;
    private final List trustedTemplatePrefixes;

    public OptInTemplateClassResolver(Set set, List list) {
        this.allowedClasses = set == null ? Collections.EMPTY_SET : set;
        if (list != null) {
            this.trustedTemplateNames = new HashSet();
            this.trustedTemplatePrefixes = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String strSubstring = (String) it.next();
                strSubstring = strSubstring.startsWith("/") ? strSubstring.substring(1) : strSubstring;
                if (strSubstring.endsWith("*")) {
                    this.trustedTemplatePrefixes.add(strSubstring.substring(0, strSubstring.length() - 1));
                } else {
                    this.trustedTemplateNames.add(strSubstring);
                }
            }
            return;
        }
        this.trustedTemplateNames = Collections.EMPTY_SET;
        this.trustedTemplatePrefixes = Collections.EMPTY_LIST;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver
    public Class resolve(String str, Environment environment, Template template) throws TemplateException {
        String strSafeGetTemplateName = safeGetTemplateName(template);
        if (strSafeGetTemplateName != null && (this.trustedTemplateNames.contains(strSafeGetTemplateName) || hasMatchingPrefix(strSafeGetTemplateName))) {
            return TemplateClassResolver.SAFER_RESOLVER.resolve(str, environment, template);
        }
        if (!this.allowedClasses.contains(str)) {
            throw new _MiscTemplateException(environment, new Object[]{"Instantiating ", str, " is not allowed in the template for security reasons. (If you run into this problem when using ?new in a template, you may want to check the \"", Configurable.NEW_BUILTIN_CLASS_RESOLVER_KEY, "\" setting in the FreeMarker configuration.)"});
        }
        try {
            return ClassUtil.forName(str);
        } catch (ClassNotFoundException e) {
            throw new _MiscTemplateException(e, environment);
        }
    }

    protected String safeGetTemplateName(Template template) {
        String name;
        if (template == null || (name = template.getName()) == null) {
            return null;
        }
        String strReplace = name.indexOf(37) != -1 ? StringUtil.replace(StringUtil.replace(StringUtil.replace(StringUtil.replace(StringUtil.replace(StringUtil.replace(name, "%2e", ".", false, false), "%2E", ".", false, false), "%2f", "/", false, false), "%2F", "/", false, false), "%5c", "\\", false, false), "%5C", "\\", false, false) : name;
        int iIndexOf = strReplace.indexOf("..");
        if (iIndexOf != -1) {
            int i = iIndexOf - 1;
            char cCharAt = i >= 0 ? strReplace.charAt(i) : (char) 65535;
            int i2 = iIndexOf + 2;
            char cCharAt2 = i2 < strReplace.length() ? strReplace.charAt(i2) : (char) 65535;
            if ((cCharAt == 65535 || cCharAt == '/' || cCharAt == '\\') && (cCharAt2 == 65535 || cCharAt2 == '/' || cCharAt2 == '\\')) {
                return null;
            }
        }
        return name.startsWith("/") ? name.substring(1) : name;
    }

    private boolean hasMatchingPrefix(String str) {
        for (int i = 0; i < this.trustedTemplatePrefixes.size(); i++) {
            if (str.startsWith((String) this.trustedTemplatePrefixes.get(i))) {
                return true;
            }
        }
        return false;
    }
}
