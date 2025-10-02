package org.mapstruct.ap.shaded.freemarker.ext.beans;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;

/* loaded from: classes3.dex */
class UnsafeMethods {
    private static final Set UNSAFE_METHODS = createUnsafeMethodsSet();
    static /* synthetic */ Class class$freemarker$ext$beans$BeansWrapper;

    private UnsafeMethods() {
    }

    static boolean isUnsafeMethod(Method method) {
        return UNSAFE_METHODS.contains(method);
    }

    private static final Set createUnsafeMethodsSet() throws Throwable {
        Properties properties = new Properties();
        Class clsClass$ = class$freemarker$ext$beans$BeansWrapper;
        if (clsClass$ == null) {
            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper");
            class$freemarker$ext$beans$BeansWrapper = clsClass$;
        }
        InputStream resourceAsStream = clsClass$.getResourceAsStream("unsafeMethods.txt");
        if (resourceAsStream != null) {
            String str = null;
            try {
                try {
                    properties.load(resourceAsStream);
                    resourceAsStream.close();
                    HashSet hashSet = new HashSet((properties.size() * 4) / 3, 0.75f);
                    Map mapCreatePrimitiveClassesMap = createPrimitiveClassesMap();
                    for (String str2 : properties.keySet()) {
                        try {
                            try {
                                try {
                                    hashSet.add(parseMethodSpec(str2, mapCreatePrimitiveClassesMap));
                                } catch (ClassNotFoundException e) {
                                    if (ClassIntrospector.DEVELOPMENT_MODE) {
                                        throw e;
                                    }
                                }
                            } catch (NoSuchMethodException e2) {
                                if (ClassIntrospector.DEVELOPMENT_MODE) {
                                    throw e2;
                                }
                            }
                        } catch (Exception e3) {
                            e = e3;
                            str = str2;
                            throw new RuntimeException(new StringBuffer("Could not load unsafe method ").append(str).append(StringUtils.SPACE).append(e.getClass().getName()).append(StringUtils.SPACE).append(e.getMessage()).toString());
                        }
                    }
                    return hashSet;
                } catch (Throwable th) {
                    resourceAsStream.close();
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } else {
            return Collections.EMPTY_SET;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static Method parseMethodSpec(String str, Map map) throws NoSuchMethodException, ClassNotFoundException {
        int iIndexOf = str.indexOf(40);
        int iLastIndexOf = str.lastIndexOf(46, iIndexOf);
        Class clsForName = ClassUtil.forName(str.substring(0, iLastIndexOf));
        String strSubstring = str.substring(iLastIndexOf + 1, iIndexOf);
        StringTokenizer stringTokenizer = new StringTokenizer(str.substring(iIndexOf + 1, str.length() - 1), ",");
        int iCountTokens = stringTokenizer.countTokens();
        Class<?>[] clsArr = new Class[iCountTokens];
        for (int i = 0; i < iCountTokens; i++) {
            String strNextToken = stringTokenizer.nextToken();
            Class<?> cls = (Class) map.get(strNextToken);
            clsArr[i] = cls;
            if (cls == null) {
                clsArr[i] = ClassUtil.forName(strNextToken);
            }
        }
        return clsForName.getMethod(strSubstring, clsArr);
    }

    private static Map createPrimitiveClassesMap() {
        HashMap map = new HashMap();
        map.put("boolean", Boolean.TYPE);
        map.put("byte", Byte.TYPE);
        map.put("char", Character.TYPE);
        map.put("short", Short.TYPE);
        map.put("int", Integer.TYPE);
        map.put("long", Long.TYPE);
        map.put("float", Float.TYPE);
        map.put("double", Double.TYPE);
        return map;
    }
}
