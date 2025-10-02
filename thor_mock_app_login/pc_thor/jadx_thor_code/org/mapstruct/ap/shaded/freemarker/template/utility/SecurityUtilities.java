package org.mapstruct.ap.shaded.freemarker.template.utility;

import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.mapstruct.ap.shaded.freemarker.log.Logger;

/* loaded from: classes3.dex */
public class SecurityUtilities {
    private static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.security");

    private SecurityUtilities() {
    }

    public static String getSystemProperty(final String str) {
        return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                return System.getProperty(str);
            }
        });
    }

    public static String getSystemProperty(final String str, final String str2) {
        try {
            return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities.2
                @Override // java.security.PrivilegedAction
                public Object run() {
                    return System.getProperty(str, str2);
                }
            });
        } catch (AccessControlException unused) {
            logger.warn(new StringBuffer("Insufficient permissions to read system property ").append(StringUtil.jQuoteNoXSS(str)).append(", using default value ").append(StringUtil.jQuoteNoXSS(str2)).toString());
            return str2;
        }
    }

    public static Integer getSystemProperty(final String str, final int i) {
        try {
            return (Integer) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities.3
                @Override // java.security.PrivilegedAction
                public Object run() {
                    return Integer.getInteger(str, i);
                }
            });
        } catch (AccessControlException unused) {
            logger.warn(new StringBuffer("Insufficient permissions to read system property ").append(StringUtil.jQuote(str)).append(", using default value ").append(i).toString());
            return new Integer(i);
        }
    }
}
