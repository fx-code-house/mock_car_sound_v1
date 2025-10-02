package org.mapstruct.ap.shaded.freemarker.cache;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import org.mapstruct.ap.shaded.freemarker.cache.MultiTemplateLoader;
import org.mapstruct.ap.shaded.freemarker.core.Configurable;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.log.Logger;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.UndeclaredThrowableException;

/* loaded from: classes3.dex */
public class TemplateCache {
    private static final char ASTERISK = '*';
    private static final String ASTERISKSTR = "*";
    private static final String CURRENT_DIR_PATH = "/./";
    private static final String CURRENT_DIR_PATH_PREFIX = "./";
    private static final String LOCALE_SEPARATOR = "_";
    private static final String PARENT_DIR_PATH = "/../";
    private static final String PARENT_DIR_PATH_PREFIX = "../";
    private static final char SLASH = '/';
    static /* synthetic */ Class class$java$lang$Throwable;
    private Configuration config;
    private long delay;
    private final boolean isStorageConcurrent;
    private boolean localizedLookup;
    private final CacheStorage storage;
    private final TemplateLoader templateLoader;
    private static final Logger logger = Logger.getLogger("org.mapstruct.ap.shaded.freemarker.cache");
    private static final Method INIT_CAUSE = getInitCauseMethod();

    public TemplateCache() {
        this(createLegacyDefaultTemplateLoader());
    }

    protected static TemplateLoader createLegacyDefaultTemplateLoader() {
        try {
            return new FileTemplateLoader();
        } catch (Exception e) {
            logger.warn("Could not create a file template loader for current directory", e);
            return null;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TemplateCache(TemplateLoader templateLoader) {
        this(templateLoader, (Configuration) null);
    }

    public TemplateCache(TemplateLoader templateLoader, CacheStorage cacheStorage) {
        this(templateLoader, cacheStorage, null);
    }

    public TemplateCache(TemplateLoader templateLoader, Configuration configuration) {
        this(templateLoader, new SoftCacheStorage(), configuration);
    }

    public TemplateCache(TemplateLoader templateLoader, CacheStorage cacheStorage, Configuration configuration) {
        this.delay = 5000L;
        this.localizedLookup = true;
        this.templateLoader = templateLoader;
        if (cacheStorage == null) {
            throw new IllegalArgumentException("storage == null");
        }
        this.storage = cacheStorage;
        this.isStorageConcurrent = (cacheStorage instanceof ConcurrentCacheStorage) && ((ConcurrentCacheStorage) cacheStorage).isConcurrent();
        this.config = configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.config = configuration;
        clear();
    }

    public TemplateLoader getTemplateLoader() {
        return this.templateLoader;
    }

    public CacheStorage getCacheStorage() {
        return this.storage;
    }

    public Template getTemplate(String str, Locale locale, String str2, boolean z) throws IOException {
        TemplateLoader templateLoader;
        if (str == null) {
            throw new NullArgumentException(AppMeasurementSdk.ConditionalUserProperty.NAME);
        }
        if (locale == null) {
            throw new NullArgumentException(Configurable.LOCALE_KEY);
        }
        if (str2 == null) {
            throw new NullArgumentException("encoding");
        }
        String strNormalizeName = normalizeName(str);
        if (strNormalizeName == null || (templateLoader = this.templateLoader) == null) {
            return null;
        }
        return getTemplate(templateLoader, strNormalizeName, locale, str2, z);
    }

    /* JADX WARN: Not initialized variable reg: 16, insn: 0x0306: MOVE (r13 I:??[OBJECT, ARRAY]) = (r16 I:??[OBJECT, ARRAY]), block:B:175:0x0306 */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02a1 A[Catch: IOException -> 0x02b0, RuntimeException -> 0x02b2, all -> 0x02b4, TryCatch #13 {all -> 0x02b4, blocks: (B:116:0x0258, B:122:0x0285, B:124:0x0297, B:126:0x02a1, B:127:0x02a5), top: B:182:0x0258 }] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x02f5 A[Catch: all -> 0x0305, TRY_ENTER, TryCatch #23 {all -> 0x0305, blocks: (B:166:0x02f5, B:167:0x02f8, B:172:0x0301, B:173:0x0304), top: B:184:0x004d }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0301 A[Catch: all -> 0x0305, TryCatch #23 {all -> 0x0305, blocks: (B:166:0x02f5, B:167:0x02f8, B:172:0x0301, B:173:0x0304), top: B:184:0x004d }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0258 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.mapstruct.ap.shaded.freemarker.template.Template getTemplate(org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader r22, java.lang.String r23, java.util.Locale r24, java.lang.String r25, boolean r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 785
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.cache.TemplateCache.getTemplate(org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader, java.lang.String, java.util.Locale, java.lang.String, boolean):org.mapstruct.ap.shaded.freemarker.template.Template");
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static final Method getInitCauseMethod() throws Throwable {
        try {
            Class clsClass$ = class$java$lang$Throwable;
            if (clsClass$ == null) {
                clsClass$ = class$("java.lang.Throwable");
                class$java$lang$Throwable = clsClass$;
            }
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$2 = class$java$lang$Throwable;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.Throwable");
                class$java$lang$Throwable = clsClass$2;
            }
            clsArr[0] = clsClass$2;
            return clsClass$.getMethod("initCause", clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private void throwLoadFailedException(Exception exc) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        Method method = INIT_CAUSE;
        if (method != null) {
            IOException iOException = new IOException("There was an error loading the template on an earlier attempt; it's attached as a cause");
            try {
                method.invoke(iOException, exc);
                throw iOException;
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new UndeclaredThrowableException(e2);
            }
        }
        throw new IOException(new StringBuffer("There was an error loading the template on an earlier attempt: ").append(exc.getClass().getName()).append(": ").append(exc.getMessage()).toString());
    }

    private void storeNegativeLookup(TemplateKey templateKey, CachedTemplate cachedTemplate, Exception exc) {
        cachedTemplate.templateOrException = exc;
        cachedTemplate.source = null;
        cachedTemplate.lastModified = 0L;
        storeCached(templateKey, cachedTemplate);
    }

    private void storeCached(TemplateKey templateKey, CachedTemplate cachedTemplate) {
        if (this.isStorageConcurrent) {
            this.storage.put(templateKey, cachedTemplate);
            return;
        }
        synchronized (this.storage) {
            this.storage.put(templateKey, cachedTemplate);
        }
    }

    /* JADX WARN: Finally extract failed */
    private Template loadTemplate(TemplateLoader templateLoader, String str, Locale locale, String str2, boolean z, Object obj) throws IOException {
        Template template;
        Reader reader = templateLoader.getReader(obj, str2);
        try {
            if (z) {
                try {
                    template = new Template(str, reader, this.config, str2);
                } catch (Template.WrongEncodingException e) {
                    str2 = e.specifiedEncoding;
                    reader.close();
                    reader = templateLoader.getReader(obj, str2);
                    template = new Template(str, reader, this.config, str2);
                }
                template.setLocale(locale);
            } else {
                StringWriter stringWriter = new StringWriter();
                char[] cArr = new char[4096];
                while (true) {
                    int i = reader.read(cArr);
                    if (i > 0) {
                        stringWriter.write(cArr, 0, i);
                    } else if (i == -1) {
                        break;
                    }
                }
                template = Template.getPlainTextTemplate(str, stringWriter.toString(), this.config);
                template.setLocale(locale);
            }
            template.setEncoding(str2);
            reader.close();
            return template;
        } catch (Throwable th) {
            reader.close();
            throw th;
        }
    }

    public long getDelay() {
        long j;
        synchronized (this) {
            j = this.delay;
        }
        return j;
    }

    public void setDelay(long j) {
        synchronized (this) {
            this.delay = j;
        }
    }

    public boolean getLocalizedLookup() {
        boolean z;
        synchronized (this) {
            z = this.localizedLookup;
        }
        return z;
    }

    public void setLocalizedLookup(boolean z) {
        synchronized (this) {
            this.localizedLookup = z;
        }
    }

    public void clear() {
        synchronized (this.storage) {
            this.storage.clear();
            TemplateLoader templateLoader = this.templateLoader;
            if (templateLoader instanceof StatefulTemplateLoader) {
                ((StatefulTemplateLoader) templateLoader).resetState();
            }
        }
    }

    public void removeTemplate(String str, Locale locale, String str2, boolean z) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("Argument \"name\" can't be null");
        }
        if (locale == null) {
            throw new IllegalArgumentException("Argument \"locale\" can't be null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("Argument \"encoding\" can't be null");
        }
        String strNormalizeName = normalizeName(str);
        if (strNormalizeName == null || this.templateLoader == null) {
            return;
        }
        Logger logger2 = logger;
        String strBuildDebugName = logger2.isDebugEnabled() ? buildDebugName(strNormalizeName, locale, str2, z) : null;
        TemplateKey templateKey = new TemplateKey(strNormalizeName, locale, str2, z);
        if (this.isStorageConcurrent) {
            this.storage.remove(templateKey);
        } else {
            synchronized (this.storage) {
                this.storage.remove(templateKey);
            }
        }
        logger2.debug(new StringBuffer().append(strBuildDebugName).append(" was removed from the cache, if it was there").toString());
    }

    private String buildDebugName(String str, Locale locale, String str2, boolean z) {
        return new StringBuffer().append(StringUtil.jQuoteNoXSS(str)).append("[").append(StringUtil.jQuoteNoXSS(locale)).append(",").append(str2).append(z ? ",parsed] " : ",unparsed]").toString();
    }

    public static String getFullTemplatePath(Environment environment, String str, String str2) {
        if (environment.isClassicCompatible() || str2.indexOf("://") > 0) {
            return str2;
        }
        if (str2.startsWith("/")) {
            int iIndexOf = str.indexOf("://");
            if (iIndexOf > 0) {
                return new StringBuffer().append(str.substring(0, iIndexOf + 2)).append(str2).toString();
            }
            return str2.substring(1);
        }
        return new StringBuffer().append(str).append(str2).toString();
    }

    private Object findTemplateSource(String str, Locale locale) throws IOException {
        if (this.localizedLookup) {
            int iLastIndexOf = str.lastIndexOf(46);
            String strSubstring = iLastIndexOf == -1 ? str : str.substring(0, iLastIndexOf);
            String strSubstring2 = iLastIndexOf == -1 ? "" : str.substring(iLastIndexOf);
            String string = new StringBuffer(LOCALE_SEPARATOR).append(locale.toString()).toString();
            StringBuffer stringBuffer = new StringBuffer(str.length() + string.length());
            stringBuffer.append(strSubstring);
            while (true) {
                stringBuffer.setLength(strSubstring.length());
                Object objAcquireTemplateSource = acquireTemplateSource(stringBuffer.append(string).append(strSubstring2).toString());
                if (objAcquireTemplateSource != null) {
                    return objAcquireTemplateSource;
                }
                int iLastIndexOf2 = string.lastIndexOf(95);
                if (iLastIndexOf2 == -1) {
                    return null;
                }
                string = string.substring(0, iLastIndexOf2);
            }
        } else {
            return acquireTemplateSource(str);
        }
    }

    private Object acquireTemplateSource(String str) throws IOException {
        if (str.indexOf(42) == -1) {
            return modifyForConfIcI(this.templateLoader.findTemplateSource(str));
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        ArrayList arrayList = new ArrayList();
        int size = -1;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (strNextToken.equals(ASTERISKSTR)) {
                if (size != -1) {
                    arrayList.remove(size);
                }
                size = arrayList.size();
            }
            arrayList.add(strNextToken);
        }
        if (size == -1) {
            return modifyForConfIcI(this.templateLoader.findTemplateSource(str));
        }
        String strConcatPath = concatPath(arrayList, 0, size);
        String strConcatPath2 = concatPath(arrayList, size + 1, arrayList.size());
        if (strConcatPath2.endsWith("/")) {
            strConcatPath2 = strConcatPath2.substring(0, strConcatPath2.length() - 1);
        }
        StringBuffer stringBufferAppend = new StringBuffer(str.length()).append(strConcatPath);
        int length = strConcatPath.length();
        boolean zIsDebugEnabled = logger.isDebugEnabled();
        while (true) {
            String string = stringBufferAppend.append(strConcatPath2).toString();
            if (zIsDebugEnabled) {
                logger.debug(new StringBuffer("Trying to find template source ").append(StringUtil.jQuoteNoXSS(string)).toString());
            }
            Object objModifyForConfIcI = modifyForConfIcI(this.templateLoader.findTemplateSource(string));
            if (objModifyForConfIcI != null) {
                return objModifyForConfIcI;
            }
            if (length == 0) {
                return null;
            }
            length = strConcatPath.lastIndexOf(47, length - 2) + 1;
            stringBufferAppend.setLength(length);
        }
    }

    private Object modifyForConfIcI(Object obj) {
        if (obj == null) {
            return null;
        }
        if (this.config.getIncompatibleImprovements().intValue() < _TemplateAPI.VERSION_INT_2_3_21) {
            return obj;
        }
        if (obj instanceof URLTemplateSource) {
            URLTemplateSource uRLTemplateSource = (URLTemplateSource) obj;
            if (uRLTemplateSource.getUseCaches() == null) {
                uRLTemplateSource.setUseCaches(false);
            }
        } else if (obj instanceof MultiTemplateLoader.MultiSource) {
            modifyForConfIcI(((MultiTemplateLoader.MultiSource) obj).getWrappedSource());
        }
        return obj;
    }

    private String concatPath(List list, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer((i2 - i) * 16);
        while (i < i2) {
            stringBuffer.append(list.get(i)).append(SLASH);
            i++;
        }
        return stringBuffer.toString();
    }

    private static String normalizeName(String str) {
        if (str.indexOf(0) != -1) {
            return null;
        }
        while (true) {
            int iIndexOf = str.indexOf(PARENT_DIR_PATH);
            if (iIndexOf == 0) {
                return null;
            }
            if (iIndexOf == -1) {
                if (str.startsWith(PARENT_DIR_PATH_PREFIX)) {
                    return null;
                }
                while (true) {
                    int iIndexOf2 = str.indexOf(CURRENT_DIR_PATH);
                    if (iIndexOf2 == -1) {
                        break;
                    }
                    str = new StringBuffer().append(str.substring(0, iIndexOf2)).append(str.substring((iIndexOf2 + 3) - 1)).toString();
                }
                if (str.startsWith(CURRENT_DIR_PATH_PREFIX)) {
                    str = str.substring(2);
                }
                return (str.length() <= 1 || str.charAt(0) != '/') ? str : str.substring(1);
            }
            str = new StringBuffer().append(str.substring(0, str.lastIndexOf(47, iIndexOf - 1) + 1)).append(str.substring(iIndexOf + 4)).toString();
        }
    }

    private static final class TemplateKey {
        private final String encoding;
        private final Locale locale;
        private final String name;
        private final boolean parse;

        TemplateKey(String str, Locale locale, String str2, boolean z) {
            this.name = str;
            this.locale = locale;
            this.encoding = str2;
            this.parse = z;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TemplateKey)) {
                return false;
            }
            TemplateKey templateKey = (TemplateKey) obj;
            return this.parse == templateKey.parse && this.name.equals(templateKey.name) && this.locale.equals(templateKey.locale) && this.encoding.equals(templateKey.encoding);
        }

        public int hashCode() {
            return ((this.name.hashCode() ^ this.locale.hashCode()) ^ this.encoding.hashCode()) ^ Boolean.valueOf(!this.parse).hashCode();
        }
    }

    private static final class CachedTemplate implements Cloneable, Serializable {
        private static final long serialVersionUID = 1;
        long lastChecked;
        long lastModified;
        Object source;
        Object templateOrException;

        private CachedTemplate() {
        }

        public CachedTemplate cloneCachedTemplate() {
            try {
                return (CachedTemplate) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new UndeclaredThrowableException(e);
            }
        }
    }
}
