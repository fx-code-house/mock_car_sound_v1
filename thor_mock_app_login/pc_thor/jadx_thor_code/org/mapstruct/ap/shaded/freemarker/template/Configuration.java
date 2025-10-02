package org.mapstruct.ap.shaded.freemarker.template;

import com.thor.app.settings.Settings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.cache.CacheStorage;
import org.mapstruct.ap.shaded.freemarker.cache.ClassTemplateLoader;
import org.mapstruct.ap.shaded.freemarker.cache.FileTemplateLoader;
import org.mapstruct.ap.shaded.freemarker.cache.MruCacheStorage;
import org.mapstruct.ap.shaded.freemarker.cache.TemplateCache;
import org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader;
import org.mapstruct.ap.shaded.freemarker.cache._CacheAPI;
import org.mapstruct.ap.shaded.freemarker.core.BugException;
import org.mapstruct.ap.shaded.freemarker.core.Configurable;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core._ConcurrentMapFactory;
import org.mapstruct.ap.shaded.freemarker.core._CoreAPI;
import org.mapstruct.ap.shaded.freemarker.core._ObjectBuilderSettingEvaluator;
import org.mapstruct.ap.shaded.freemarker.core._SettingEvaluationEnvironment;
import org.mapstruct.ap.shaded.freemarker.template.utility.CaptureOutput;
import org.mapstruct.ap.shaded.freemarker.template.utility.ClassUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.HtmlEscape;
import org.mapstruct.ap.shaded.freemarker.template.utility.NormalizeNewlines;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.mapstruct.ap.shaded.freemarker.template.utility.SecurityUtilities;
import org.mapstruct.ap.shaded.freemarker.template.utility.StandardCompress;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.XmlEscape;

/* loaded from: classes3.dex */
public class Configuration extends Configurable implements Cloneable {
    public static final int ANGLE_BRACKET_TAG_SYNTAX = 1;
    public static final int AUTO_DETECT_TAG_SYNTAX = 0;
    public static final String AUTO_IMPORT_KEY = "auto_import";
    public static final String AUTO_INCLUDE_KEY = "auto_include";
    public static final String CACHE_STORAGE_KEY = "cache_storage";
    public static final String DEFAULT_ENCODING_KEY = "default_encoding";
    public static final String DEFAULT_INCOMPATIBLE_ENHANCEMENTS;
    public static final Version DEFAULT_INCOMPATIBLE_IMPROVEMENTS;
    public static final String INCOMPATIBLE_ENHANCEMENTS = "incompatible_enhancements";
    public static final String INCOMPATIBLE_IMPROVEMENTS = "incompatible_improvements";
    public static final String LOCALIZED_LOOKUP_KEY = "localized_lookup";
    public static final int PARSED_DEFAULT_INCOMPATIBLE_ENHANCEMENTS;
    public static final int SQUARE_BRACKET_TAG_SYNTAX = 2;
    public static final String STRICT_SYNTAX_KEY = "strict_syntax";
    public static final String TAG_SYNTAX_KEY = "tag_syntax";
    public static final String TEMPLATE_LOADER_KEY = "template_loader";
    public static final String TEMPLATE_UPDATE_DELAY_KEY = "template_update_delay";
    public static final Version VERSION_2_3_0;
    public static final Version VERSION_2_3_19;
    public static final Version VERSION_2_3_20;
    public static final Version VERSION_2_3_21;
    private static final String VERSION_PROPERTIES_PATH = "org/mapstruct/ap/shaded/freemarker/version.properties";
    public static final String WHITESPACE_STRIPPING_KEY = "whitespace_stripping";
    static /* synthetic */ Class class$freemarker$cache$CacheStorage;
    static /* synthetic */ Class class$freemarker$cache$TemplateLoader;
    static /* synthetic */ Class class$freemarker$template$Configuration;
    static /* synthetic */ Class class$java$lang$String;
    private static Configuration defaultConfig;
    private static final Object defaultConfigLock;
    private static final Version version;
    private Map autoImportNsToTmpMap;
    private ArrayList autoImports;
    private ArrayList autoIncludes;
    private TemplateCache cache;
    private String defaultEncoding;
    private Version incompatibleImprovements;
    private Map localeToCharsetMap;
    private volatile boolean localizedLookup;
    private boolean objectWrapperWasSet;
    private HashMap rewrappableSharedVariables;
    private HashMap sharedVariables;
    private boolean strictSyntax;
    private int tagSyntax;
    private boolean templateLoaderWasSet;
    private boolean whitespaceStripping;

    static {
        Date date;
        Version version2 = new Version(2, 3, 0);
        VERSION_2_3_0 = version2;
        VERSION_2_3_19 = new Version(2, 3, 19);
        VERSION_2_3_20 = new Version(2, 3, 20);
        VERSION_2_3_21 = new Version(2, 3, 21);
        DEFAULT_INCOMPATIBLE_IMPROVEMENTS = version2;
        DEFAULT_INCOMPATIBLE_ENHANCEMENTS = version2.toString();
        PARSED_DEFAULT_INCOMPATIBLE_ENHANCEMENTS = version2.intValue();
        try {
            Properties properties = new Properties();
            Class clsClass$ = class$freemarker$template$Configuration;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.Configuration");
                class$freemarker$template$Configuration = clsClass$;
            }
            InputStream resourceAsStream = clsClass$.getClassLoader().getResourceAsStream(VERSION_PROPERTIES_PATH);
            if (resourceAsStream == null) {
                throw new RuntimeException("Version file is missing.");
            }
            try {
                properties.load(resourceAsStream);
                resourceAsStream.close();
                String requiredVersionProperty = getRequiredVersionProperty(properties, "version");
                String requiredVersionProperty2 = getRequiredVersionProperty(properties, "buildTimestamp");
                if (requiredVersionProperty2.endsWith("Z")) {
                    requiredVersionProperty2 = new StringBuffer().append(requiredVersionProperty2.substring(0, requiredVersionProperty2.length() - 1)).append("+0000").toString();
                }
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(requiredVersionProperty2);
                } catch (ParseException unused) {
                    date = null;
                }
                version = new Version(requiredVersionProperty, Boolean.valueOf(getRequiredVersionProperty(properties, "isGAECompliant")), date);
                defaultConfigLock = new Object();
            } catch (Throwable th) {
                resourceAsStream.close();
                throw th;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load and parse freemarker/version.properties", e);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public Configuration() {
        this(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    }

    public Configuration(Version version2) {
        super(version2);
        this.strictSyntax = true;
        this.localizedLookup = true;
        this.whitespaceStripping = true;
        this.tagSyntax = 1;
        this.sharedVariables = new HashMap();
        this.rewrappableSharedVariables = null;
        this.defaultEncoding = SecurityUtilities.getSystemProperty("file.encoding");
        this.localeToCharsetMap = _ConcurrentMapFactory.newThreadSafeMap();
        this.autoImports = new ArrayList();
        this.autoIncludes = new ArrayList();
        this.autoImportNsToTmpMap = new HashMap();
        NullArgumentException.check("incompatibleImprovements", version2);
        this.incompatibleImprovements = version2;
        createTemplateCache();
        loadBuiltInSharedVariables();
    }

    private void createTemplateCache() {
        TemplateCache templateCache = new TemplateCache(getDefaultTemplateLoader(), this);
        this.cache = templateCache;
        templateCache.clear();
        this.cache.setDelay(5000L);
    }

    private void recreateTemplateCacheWith(TemplateLoader templateLoader, CacheStorage cacheStorage) {
        TemplateCache templateCache = this.cache;
        TemplateCache templateCache2 = new TemplateCache(templateLoader, cacheStorage, this);
        this.cache = templateCache2;
        templateCache2.clear();
        this.cache.setDelay(templateCache.getDelay());
        this.cache.setLocalizedLookup(this.localizedLookup);
    }

    private TemplateLoader getDefaultTemplateLoader() {
        if (this.incompatibleImprovements.intValue() < _TemplateAPI.VERSION_INT_2_3_21) {
            return _CacheAPI.createLegacyDefaultTemplateLoader();
        }
        return null;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public Object clone() {
        try {
            Configuration configuration = (Configuration) super.clone();
            configuration.sharedVariables = new HashMap(this.sharedVariables);
            configuration.localeToCharsetMap = new HashMap(this.localeToCharsetMap);
            configuration.autoImportNsToTmpMap = new HashMap(this.autoImportNsToTmpMap);
            configuration.autoImports = (ArrayList) this.autoImports.clone();
            configuration.autoIncludes = (ArrayList) this.autoIncludes.clone();
            configuration.recreateTemplateCacheWith(this.cache.getTemplateLoader(), this.cache.getCacheStorage());
            return configuration;
        } catch (CloneNotSupportedException e) {
            throw new BugException(e.getMessage());
        }
    }

    private void loadBuiltInSharedVariables() {
        this.sharedVariables.put("capture_output", new CaptureOutput());
        this.sharedVariables.put("compress", StandardCompress.INSTANCE);
        this.sharedVariables.put("html_escape", new HtmlEscape());
        this.sharedVariables.put("normalize_newlines", new NormalizeNewlines());
        this.sharedVariables.put("xml_escape", new XmlEscape());
    }

    public void loadBuiltInEncodingMap() {
        this.localeToCharsetMap.clear();
        this.localeToCharsetMap.put("ar", "ISO-8859-6");
        this.localeToCharsetMap.put("be", "ISO-8859-5");
        this.localeToCharsetMap.put("bg", "ISO-8859-5");
        this.localeToCharsetMap.put("ca", "ISO-8859-1");
        this.localeToCharsetMap.put("cs", "ISO-8859-2");
        this.localeToCharsetMap.put("da", "ISO-8859-1");
        this.localeToCharsetMap.put(Settings.LANGUAGE_CODE_DE, "ISO-8859-1");
        this.localeToCharsetMap.put("el", "ISO-8859-7");
        this.localeToCharsetMap.put(Settings.LANGUAGE_CODE_EN, "ISO-8859-1");
        this.localeToCharsetMap.put("es", "ISO-8859-1");
        this.localeToCharsetMap.put("et", "ISO-8859-1");
        this.localeToCharsetMap.put("fi", "ISO-8859-1");
        this.localeToCharsetMap.put("fr", "ISO-8859-1");
        this.localeToCharsetMap.put("hr", "ISO-8859-2");
        this.localeToCharsetMap.put("hu", "ISO-8859-2");
        this.localeToCharsetMap.put("is", "ISO-8859-1");
        this.localeToCharsetMap.put("it", "ISO-8859-1");
        this.localeToCharsetMap.put("iw", "ISO-8859-8");
        this.localeToCharsetMap.put("ja", "Shift_JIS");
        this.localeToCharsetMap.put("ko", "EUC-KR");
        this.localeToCharsetMap.put("lt", "ISO-8859-2");
        this.localeToCharsetMap.put("lv", "ISO-8859-2");
        this.localeToCharsetMap.put("mk", "ISO-8859-5");
        this.localeToCharsetMap.put("nl", "ISO-8859-1");
        this.localeToCharsetMap.put(BooleanUtils.NO, "ISO-8859-1");
        this.localeToCharsetMap.put("pl", "ISO-8859-2");
        this.localeToCharsetMap.put("pt", "ISO-8859-1");
        this.localeToCharsetMap.put("ro", "ISO-8859-2");
        this.localeToCharsetMap.put(Settings.LANGUAGE_CODE_RU, "ISO-8859-5");
        this.localeToCharsetMap.put("sh", "ISO-8859-5");
        this.localeToCharsetMap.put("sk", "ISO-8859-2");
        this.localeToCharsetMap.put("sl", "ISO-8859-2");
        this.localeToCharsetMap.put("sq", "ISO-8859-2");
        this.localeToCharsetMap.put("sr", "ISO-8859-5");
        this.localeToCharsetMap.put("sv", "ISO-8859-1");
        this.localeToCharsetMap.put("tr", "ISO-8859-9");
        this.localeToCharsetMap.put("uk", "ISO-8859-5");
        this.localeToCharsetMap.put("zh", "GB2312");
        this.localeToCharsetMap.put("zh_TW", "Big5");
    }

    public void clearEncodingMap() {
        this.localeToCharsetMap.clear();
    }

    public static Configuration getDefaultConfiguration() {
        Configuration configuration;
        synchronized (defaultConfigLock) {
            if (defaultConfig == null) {
                defaultConfig = new Configuration();
            }
            configuration = defaultConfig;
        }
        return configuration;
    }

    public static void setDefaultConfiguration(Configuration configuration) {
        synchronized (defaultConfigLock) {
            defaultConfig = configuration;
        }
    }

    public void setTemplateLoader(TemplateLoader templateLoader) {
        synchronized (this) {
            if (this.cache.getTemplateLoader() != templateLoader) {
                recreateTemplateCacheWith(templateLoader, this.cache.getCacheStorage());
                this.templateLoaderWasSet = true;
            }
        }
    }

    public TemplateLoader getTemplateLoader() {
        return this.cache.getTemplateLoader();
    }

    public void setCacheStorage(CacheStorage cacheStorage) {
        synchronized (this) {
            recreateTemplateCacheWith(this.cache.getTemplateLoader(), cacheStorage);
        }
    }

    public CacheStorage getCacheStorage() {
        CacheStorage cacheStorage;
        synchronized (this) {
            cacheStorage = this.cache.getCacheStorage();
        }
        return cacheStorage;
    }

    public void setDirectoryForTemplateLoading(File file) throws IOException {
        TemplateLoader templateLoader = getTemplateLoader();
        if ((templateLoader instanceof FileTemplateLoader) && ((FileTemplateLoader) templateLoader).baseDir.getCanonicalPath().equals(file.getCanonicalPath())) {
            return;
        }
        setTemplateLoader(new FileTemplateLoader(file));
    }

    public void setServletContextForTemplateLoading(Object obj, String str) throws Throwable {
        Object[] objArr;
        Class<?>[] clsArr;
        try {
            Class clsForName = ClassUtil.forName("org.mapstruct.ap.shaded.freemarker.cache.WebappTemplateLoader");
            Class<?> clsForName2 = ClassUtil.forName("javax.servlet.ServletContext");
            if (str == null) {
                clsArr = new Class[]{clsForName2};
                objArr = new Object[]{obj};
            } else {
                Class<?>[] clsArr2 = new Class[2];
                clsArr2[0] = clsForName2;
                Class<?> clsClass$ = class$java$lang$String;
                if (clsClass$ == null) {
                    clsClass$ = class$("java.lang.String");
                    class$java$lang$String = clsClass$;
                }
                clsArr2[1] = clsClass$;
                objArr = new Object[]{obj, str};
                clsArr = clsArr2;
            }
            setTemplateLoader((TemplateLoader) clsForName.getConstructor(clsArr).newInstance(objArr));
        } catch (Exception e) {
            throw new BugException(e);
        }
    }

    public void setClassForTemplateLoading(Class cls, String str) {
        setTemplateLoader(new ClassTemplateLoader(cls, str));
    }

    public void setTemplateUpdateDelay(int i) {
        this.cache.setDelay(i * 1000);
    }

    public void setStrictSyntaxMode(boolean z) {
        this.strictSyntax = z;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setObjectWrapper(ObjectWrapper objectWrapper) {
        ObjectWrapper objectWrapper2 = getObjectWrapper();
        super.setObjectWrapper(objectWrapper);
        this.objectWrapperWasSet = true;
        if (objectWrapper != objectWrapper2) {
            try {
                setSharedVariablesFromRewrappableSharedVariables();
            } catch (TemplateModelException e) {
                throw new RuntimeException("Failed to re-wrap earliearly set shared variables with the newly set object wrapper", e);
            }
        }
    }

    public boolean getStrictSyntaxMode() {
        return this.strictSyntax;
    }

    public void setIncompatibleImprovements(Version version2) {
        _TemplateAPI.checkVersionNotNullAndSupported(version2);
        boolean z = this.incompatibleImprovements.intValue() < _TemplateAPI.VERSION_INT_2_3_21;
        this.incompatibleImprovements = version2;
        if (z != (version2.intValue() < _TemplateAPI.VERSION_INT_2_3_21)) {
            if (!this.templateLoaderWasSet) {
                recreateTemplateCacheWith(getDefaultTemplateLoader(), this.cache.getCacheStorage());
            }
            if (this.objectWrapperWasSet) {
                return;
            }
            super.setObjectWrapper(getDefaultObjectWrapper(version2));
        }
    }

    public Version getIncompatibleImprovements() {
        return this.incompatibleImprovements;
    }

    public void setIncompatibleEnhancements(String str) {
        setIncompatibleImprovements(new Version(str));
    }

    public String getIncompatibleEnhancements() {
        return this.incompatibleImprovements.toString();
    }

    public int getParsedIncompatibleEnhancements() {
        return getIncompatibleImprovements().intValue();
    }

    public void setWhitespaceStripping(boolean z) {
        this.whitespaceStripping = z;
    }

    public boolean getWhitespaceStripping() {
        return this.whitespaceStripping;
    }

    public void setTagSyntax(int i) {
        if (i != 0 && i != 2 && i != 1) {
            throw new IllegalArgumentException("\"tag_syntax\" can only be set to one of these: Configuration.AUTO_DETECT_TAG_SYNTAX, Configuration.ANGLE_BRACKET_SYNTAX, or Configuration.SQAUARE_BRACKET_SYNTAX");
        }
        this.tagSyntax = i;
    }

    public int getTagSyntax() {
        return this.tagSyntax;
    }

    public Template getTemplate(String str) throws IOException {
        Locale locale = getLocale();
        return getTemplate(str, locale, getEncoding(locale), true);
    }

    public Template getTemplate(String str, Locale locale) throws IOException {
        return getTemplate(str, locale, getEncoding(locale), true);
    }

    public Template getTemplate(String str, String str2) throws IOException {
        return getTemplate(str, getLocale(), str2, true);
    }

    public Template getTemplate(String str, Locale locale, String str2) throws IOException {
        return getTemplate(str, locale, str2, true);
    }

    public Template getTemplate(String str, Locale locale, String str2, boolean z) throws IOException {
        return getTemplate(str, locale, str2, z, false);
    }

    public Template getTemplate(String str, Locale locale, String str2, boolean z, boolean z2) throws IOException {
        String string;
        String string2;
        Template template = this.cache.getTemplate(str, locale, str2, z);
        if (template != null) {
            return template;
        }
        if (z2) {
            return null;
        }
        TemplateLoader templateLoader = getTemplateLoader();
        if (templateLoader == null) {
            string = new StringBuffer("Don't know where to load template ").append(StringUtil.jQuote(str)).append(" from because the \"template_loader\" FreeMarker setting wasn't set.").toString();
        } else {
            String string3 = new StringBuffer("Template ").append(StringUtil.jQuote(str)).append(" not found. The quoted name was interpreted by this template loader: ").toString();
            try {
                string2 = templateLoader.toString();
            } catch (Throwable unused) {
                string2 = new StringBuffer().append(templateLoader.getClass().getName()).append(" object (toString failed)").toString();
            }
            string = new StringBuffer().append(string3).append(string2).append(".").toString();
            if (!this.templateLoaderWasSet) {
                string = new StringBuffer().append(string).append(" Note that the \"template_loader\" FreeMarker setting wasn't set, so it's on its default value, which is most certainly not intended and the cause of this problem.").toString();
            }
        }
        throw new FileNotFoundException(string);
    }

    public void setDefaultEncoding(String str) {
        this.defaultEncoding = str;
    }

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public String getEncoding(Locale locale) {
        if (this.localeToCharsetMap.isEmpty()) {
            return this.defaultEncoding;
        }
        String str = (String) this.localeToCharsetMap.get(locale.toString());
        if (str == null) {
            if (locale.getVariant().length() > 0) {
                String str2 = (String) this.localeToCharsetMap.get(new Locale(locale.getLanguage(), locale.getCountry()).toString());
                if (str2 != null) {
                    this.localeToCharsetMap.put(locale.toString(), str2);
                }
            }
            str = (String) this.localeToCharsetMap.get(locale.getLanguage());
            if (str != null) {
                this.localeToCharsetMap.put(locale.toString(), str);
            }
        }
        return str != null ? str : this.defaultEncoding;
    }

    public void setEncoding(Locale locale, String str) {
        this.localeToCharsetMap.put(locale.toString(), str);
    }

    public void setSharedVariable(String str, TemplateModel templateModel) {
        HashMap map;
        if (this.sharedVariables.put(str, templateModel) == null || (map = this.rewrappableSharedVariables) == null) {
            return;
        }
        map.remove(str);
    }

    public Set getSharedVariableNames() {
        return new HashSet(this.sharedVariables.keySet());
    }

    public void setSharedVariable(String str, Object obj) throws TemplateModelException {
        setSharedVariable(str, getObjectWrapper().wrap(obj));
    }

    public void setSharedVaribles(Map map) throws TemplateModelException {
        this.rewrappableSharedVariables = new HashMap(map);
        this.sharedVariables.clear();
        setSharedVariablesFromRewrappableSharedVariables();
    }

    private void setSharedVariablesFromRewrappableSharedVariables() throws TemplateModelException {
        TemplateModel templateModelWrap;
        HashMap map = this.rewrappableSharedVariables;
        if (map == null) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof TemplateModel) {
                templateModelWrap = (TemplateModel) value;
            } else {
                templateModelWrap = getObjectWrapper().wrap(value);
            }
            this.sharedVariables.put(str, templateModelWrap);
        }
    }

    public void setAllSharedVariables(TemplateHashModelEx templateHashModelEx) throws TemplateModelException {
        TemplateModelIterator it = templateHashModelEx.keys().iterator();
        TemplateModelIterator it2 = templateHashModelEx.values().iterator();
        while (it.hasNext()) {
            setSharedVariable(((TemplateScalarModel) it.next()).getAsString(), it2.next());
        }
    }

    public TemplateModel getSharedVariable(String str) {
        return (TemplateModel) this.sharedVariables.get(str);
    }

    public void clearSharedVariables() {
        this.sharedVariables.clear();
        loadBuiltInSharedVariables();
    }

    public void clearTemplateCache() {
        this.cache.clear();
    }

    public void removeTemplateFromCache(String str) throws IOException {
        Locale locale = getLocale();
        removeTemplateFromCache(str, locale, getEncoding(locale), true);
    }

    public void removeTemplateFromCache(String str, Locale locale) throws IOException {
        removeTemplateFromCache(str, locale, getEncoding(locale), true);
    }

    public void removeTemplateFromCache(String str, String str2) throws IOException {
        removeTemplateFromCache(str, getLocale(), str2, true);
    }

    public void removeTemplateFromCache(String str, Locale locale, String str2) throws IOException {
        removeTemplateFromCache(str, locale, str2, true);
    }

    public void removeTemplateFromCache(String str, Locale locale, String str2, boolean z) throws IOException {
        this.cache.removeTemplate(str, locale, str2, z);
    }

    public boolean getLocalizedLookup() {
        return this.cache.getLocalizedLookup();
    }

    public void setLocalizedLookup(boolean z) {
        this.localizedLookup = z;
        this.cache.setLocalizedLookup(z);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    public void setSetting(String str, String str2) throws Throwable {
        try {
            if ("TemplateUpdateInterval".equalsIgnoreCase(str)) {
                str = TEMPLATE_UPDATE_DELAY_KEY;
            } else if ("DefaultEncoding".equalsIgnoreCase(str)) {
                str = DEFAULT_ENCODING_KEY;
            }
            boolean z = false;
            if (DEFAULT_ENCODING_KEY.equals(str)) {
                setDefaultEncoding(str2);
            } else if (LOCALIZED_LOOKUP_KEY.equals(str)) {
                setLocalizedLookup(StringUtil.getYesNo(str2));
            } else if (STRICT_SYNTAX_KEY.equals(str)) {
                setStrictSyntaxMode(StringUtil.getYesNo(str2));
            } else if (WHITESPACE_STRIPPING_KEY.equals(str)) {
                setWhitespaceStripping(StringUtil.getYesNo(str2));
            } else if (CACHE_STORAGE_KEY.equals(str)) {
                if (str2.indexOf(46) == -1) {
                    int i = 0;
                    int i2 = 0;
                    for (Map.Entry entry : StringUtil.parseNameValuePairList(str2, String.valueOf(Integer.MAX_VALUE)).entrySet()) {
                        String str3 = (String) entry.getKey();
                        try {
                            int i3 = Integer.parseInt((String) entry.getValue());
                            if ("soft".equalsIgnoreCase(str3)) {
                                i = i3;
                            } else {
                                if (!"strong".equalsIgnoreCase(str3)) {
                                    throw invalidSettingValueException(str, str2);
                                }
                                i2 = i3;
                            }
                        } catch (NumberFormatException unused) {
                            throw invalidSettingValueException(str, str2);
                        }
                    }
                    if (i == 0 && i2 == 0) {
                        throw invalidSettingValueException(str, str2);
                    }
                    setCacheStorage(new MruCacheStorage(i2, i));
                } else {
                    Class clsClass$ = class$freemarker$cache$CacheStorage;
                    if (clsClass$ == null) {
                        clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.cache.CacheStorage");
                        class$freemarker$cache$CacheStorage = clsClass$;
                    }
                    setCacheStorage((CacheStorage) _ObjectBuilderSettingEvaluator.eval(str2, clsClass$, _SettingEvaluationEnvironment.getCurrent()));
                }
            } else if (TEMPLATE_UPDATE_DELAY_KEY.equals(str)) {
                setTemplateUpdateDelay(Integer.parseInt(str2));
            } else if (AUTO_INCLUDE_KEY.equals(str)) {
                setAutoIncludes(parseAsList(str2));
            } else if (AUTO_IMPORT_KEY.equals(str)) {
                setAutoImports(parseAsImportList(str2));
            } else if (TAG_SYNTAX_KEY.equals(str)) {
                if ("auto_detect".equals(str2)) {
                    setTagSyntax(0);
                } else if ("angle_bracket".equals(str2)) {
                    setTagSyntax(1);
                } else if ("square_bracket".equals(str2)) {
                    setTagSyntax(2);
                } else {
                    throw invalidSettingValueException(str, str2);
                }
            } else if (INCOMPATIBLE_IMPROVEMENTS.equals(str)) {
                setIncompatibleImprovements(new Version(str2));
            } else if (INCOMPATIBLE_ENHANCEMENTS.equals(str)) {
                setIncompatibleEnhancements(str2);
            } else if (TEMPLATE_LOADER_KEY.equals(str)) {
                Class clsClass$2 = class$freemarker$cache$TemplateLoader;
                if (clsClass$2 == null) {
                    clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.cache.TemplateLoader");
                    class$freemarker$cache$TemplateLoader = clsClass$2;
                }
                setTemplateLoader((TemplateLoader) _ObjectBuilderSettingEvaluator.eval(str2, clsClass$2, _SettingEvaluationEnvironment.getCurrent()));
            } else {
                z = true;
            }
            if (z) {
                super.setSetting(str, str2);
            }
        } catch (Exception e) {
            throw settingValueAssignmentException(str, str2, e);
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    protected String getCorrectedNameForUnknownSetting(String str) {
        return ("encoding".equals(str) || "charset".equals(str) || "default_charset".equals(str)) ? DEFAULT_ENCODING_KEY : super.getCorrectedNameForUnknownSetting(str);
    }

    public void addAutoImport(String str, String str2) {
        synchronized (this) {
            this.autoImports.remove(str);
            this.autoImports.add(str);
            this.autoImportNsToTmpMap.put(str, str2);
        }
    }

    public void removeAutoImport(String str) {
        synchronized (this) {
            this.autoImports.remove(str);
            this.autoImportNsToTmpMap.remove(str);
        }
    }

    public void setAutoImports(Map map) {
        synchronized (this) {
            this.autoImports = new ArrayList(map.keySet());
            if (map instanceof HashMap) {
                this.autoImportNsToTmpMap = (Map) ((HashMap) map).clone();
            } else if (map instanceof SortedMap) {
                this.autoImportNsToTmpMap = new TreeMap(map);
            } else {
                this.autoImportNsToTmpMap = new HashMap(map);
            }
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Configurable
    protected void doAutoImportsAndIncludes(Environment environment) throws IOException, TemplateException {
        for (int i = 0; i < this.autoImports.size(); i++) {
            String str = (String) this.autoImports.get(i);
            environment.importLib((String) this.autoImportNsToTmpMap.get(str), str);
        }
        for (int i2 = 0; i2 < this.autoIncludes.size(); i2++) {
            environment.include(getTemplate((String) this.autoIncludes.get(i2), environment.getLocale()));
        }
    }

    public void addAutoInclude(String str) {
        synchronized (this) {
            this.autoIncludes.remove(str);
            this.autoIncludes.add(str);
        }
    }

    public void setAutoIncludes(List list) {
        synchronized (this) {
            this.autoIncludes.clear();
            for (Object obj : list) {
                if (!(obj instanceof String)) {
                    throw new IllegalArgumentException("List items must be String-s.");
                }
                this.autoIncludes.add(obj);
            }
        }
    }

    public void removeAutoInclude(String str) {
        synchronized (this) {
            this.autoIncludes.remove(str);
        }
    }

    public static String getVersionNumber() {
        return version.toString();
    }

    public static Version getVersion() {
        return version;
    }

    public static ObjectWrapper getDefaultObjectWrapper(Version version2) {
        if (version2.intValue() < _TemplateAPI.VERSION_INT_2_3_21) {
            return ObjectWrapper.DEFAULT_WRAPPER;
        }
        return new DefaultObjectWrapperBuilder(version2).build();
    }

    public Set getSupportedBuiltInNames() {
        return _CoreAPI.getSupportedBuiltInNames();
    }

    public Set getSupportedBuiltInDirectiveNames() {
        return _CoreAPI.BUILT_IN_DIRECTIVE_NAMES;
    }

    private static String getRequiredVersionProperty(Properties properties, String str) {
        String property = properties.getProperty(str);
        if (property != null) {
            return property;
        }
        throw new RuntimeException(new StringBuffer("Version file is corrupt: \"").append(str).append("\" property is missing.").toString());
    }
}
