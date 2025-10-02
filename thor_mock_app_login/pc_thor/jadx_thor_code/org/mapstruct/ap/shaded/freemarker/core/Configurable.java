package org.mapstruct.ap.shaded.freemarker.core;

import androidx.room.FtsOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler;
import org.mapstruct.ap.shaded.freemarker.template.Version;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class Configurable {
    private static final String ALLOWED_CLASSES = "allowed_classes";
    public static final String ARITHMETIC_ENGINE_KEY = "arithmetic_engine";
    public static final String AUTO_FLUSH_KEY = "auto_flush";
    public static final String BOOLEAN_FORMAT_KEY = "boolean_format";
    public static final String CLASSIC_COMPATIBLE_KEY = "classic_compatible";
    static final String C_TRUE_FALSE = "true,false";
    public static final String DATETIME_FORMAT_KEY = "datetime_format";
    public static final String DATE_FORMAT_KEY = "date_format";
    private static final String DEFAULT = "default";
    private static final String JVM_DEFAULT = "JVM default";
    public static final String LOCALE_KEY = "locale";
    public static final String NEW_BUILTIN_CLASS_RESOLVER_KEY = "new_builtin_class_resolver";
    public static final String NUMBER_FORMAT_KEY = "number_format";
    public static final String OBJECT_WRAPPER_KEY = "object_wrapper";
    public static final String OUTPUT_ENCODING_KEY = "output_encoding";
    public static final String SHOW_ERROR_TIPS_KEY = "show_error_tips";
    public static final String SQL_DATE_AND_TIME_TIME_ZONE_KEY = "sql_date_and_time_time_zone";
    public static final String STRICT_BEAN_MODELS = "strict_bean_models";
    public static final String TEMPLATE_EXCEPTION_HANDLER_KEY = "template_exception_handler";
    public static final String TIME_FORMAT_KEY = "time_format";
    public static final String TIME_ZONE_KEY = "time_zone";
    private static final String TRUSTED_TEMPLATES = "trusted_templates";
    public static final String URL_ESCAPING_CHARSET_KEY = "url_escaping_charset";
    static /* synthetic */ Class class$freemarker$core$ArithmeticEngine;
    static /* synthetic */ Class class$freemarker$core$TemplateClassResolver;
    static /* synthetic */ Class class$freemarker$ext$beans$BeansWrapper;
    static /* synthetic */ Class class$freemarker$template$ObjectWrapper;
    static /* synthetic */ Class class$freemarker$template$TemplateExceptionHandler;
    private ArithmeticEngine arithmeticEngine;
    private Boolean autoFlush;
    private String booleanFormat;
    private Integer classicCompatible;
    private HashMap customAttributes;
    private String dateFormat;
    private String dateTimeFormat;
    private String falseStringValue;
    private Locale locale;
    private TemplateClassResolver newBuiltinClassResolver;
    private String numberFormat;
    private ObjectWrapper objectWrapper;
    private String outputEncoding;
    private boolean outputEncodingSet;
    private Configurable parent;
    private Properties properties;
    private Boolean showErrorTips;
    private TimeZone sqlDataAndTimeTimeZone;
    private boolean sqlDataAndTimeTimeZoneSet;
    private TemplateExceptionHandler templateExceptionHandler;
    private String timeFormat;
    private TimeZone timeZone;
    private String trueStringValue;
    private String urlEscapingCharset;
    private boolean urlEscapingCharsetSet;

    protected String getCorrectedNameForUnknownSetting(String str) {
        return null;
    }

    public Configurable() {
        this(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    }

    protected Configurable(Version version) {
        _TemplateAPI.checkVersionNotNullAndSupported(version);
        this.parent = null;
        this.locale = Locale.getDefault();
        this.timeZone = TimeZone.getDefault();
        this.sqlDataAndTimeTimeZone = null;
        this.numberFormat = "number";
        this.timeFormat = "";
        this.dateFormat = "";
        this.dateTimeFormat = "";
        this.classicCompatible = new Integer(0);
        this.templateExceptionHandler = TemplateExceptionHandler.DEBUG_HANDLER;
        this.arithmeticEngine = ArithmeticEngine.BIGDECIMAL_ENGINE;
        this.objectWrapper = Configuration.getDefaultObjectWrapper(version);
        this.autoFlush = Boolean.TRUE;
        this.newBuiltinClassResolver = TemplateClassResolver.UNRESTRICTED_RESOLVER;
        this.showErrorTips = Boolean.TRUE;
        Properties properties = new Properties();
        this.properties = properties;
        properties.setProperty(LOCALE_KEY, this.locale.toString());
        this.properties.setProperty(TIME_FORMAT_KEY, this.timeFormat);
        this.properties.setProperty(DATE_FORMAT_KEY, this.dateFormat);
        this.properties.setProperty(DATETIME_FORMAT_KEY, this.dateTimeFormat);
        this.properties.setProperty(TIME_ZONE_KEY, this.timeZone.getID());
        this.properties.setProperty(SQL_DATE_AND_TIME_TIME_ZONE_KEY, String.valueOf(this.sqlDataAndTimeTimeZone));
        this.properties.setProperty(NUMBER_FORMAT_KEY, this.numberFormat);
        this.properties.setProperty(CLASSIC_COMPATIBLE_KEY, this.classicCompatible.toString());
        this.properties.setProperty(TEMPLATE_EXCEPTION_HANDLER_KEY, this.templateExceptionHandler.getClass().getName());
        this.properties.setProperty(ARITHMETIC_ENGINE_KEY, this.arithmeticEngine.getClass().getName());
        this.properties.setProperty(AUTO_FLUSH_KEY, this.autoFlush.toString());
        this.properties.setProperty(NEW_BUILTIN_CLASS_RESOLVER_KEY, this.newBuiltinClassResolver.getClass().getName());
        this.properties.setProperty(SHOW_ERROR_TIPS_KEY, this.showErrorTips.toString());
        setBooleanFormat(C_TRUE_FALSE);
        this.customAttributes = new HashMap();
    }

    public Configurable(Configurable configurable) {
        this.parent = configurable;
        this.locale = null;
        this.numberFormat = null;
        this.classicCompatible = null;
        this.templateExceptionHandler = null;
        this.properties = new Properties(configurable.properties);
        this.customAttributes = new HashMap();
    }

    protected Object clone() throws CloneNotSupportedException {
        Configurable configurable = (Configurable) super.clone();
        configurable.properties = new Properties(this.properties);
        configurable.customAttributes = (HashMap) this.customAttributes.clone();
        return configurable;
    }

    public final Configurable getParent() {
        return this.parent;
    }

    final void setParent(Configurable configurable) {
        this.parent = configurable;
    }

    public void setClassicCompatible(boolean z) {
        Integer num = new Integer(z ? 1 : 0);
        this.classicCompatible = num;
        this.properties.setProperty(CLASSIC_COMPATIBLE_KEY, classicCompatibilityIntToString(num));
    }

    public void setClassicCompatibleAsInt(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException(new StringBuffer("Unsupported \"classicCompatibility\": ").append(i).toString());
        }
        this.classicCompatible = new Integer(i);
    }

    private String classicCompatibilityIntToString(Integer num) {
        if (num == null) {
            return null;
        }
        return num.intValue() == 0 ? BooleanUtils.FALSE : num.intValue() == 1 ? BooleanUtils.TRUE : num.toString();
    }

    public boolean isClassicCompatible() {
        Integer num = this.classicCompatible;
        return num != null ? num.intValue() != 0 : this.parent.isClassicCompatible();
    }

    public int getClassicCompatibleAsInt() {
        Integer num = this.classicCompatible;
        return num != null ? num.intValue() : this.parent.getClassicCompatibleAsInt();
    }

    public void setLocale(Locale locale) {
        NullArgumentException.check(LOCALE_KEY, locale);
        this.locale = locale;
        this.properties.setProperty(LOCALE_KEY, locale.toString());
    }

    public TimeZone getTimeZone() {
        TimeZone timeZone = this.timeZone;
        return timeZone != null ? timeZone : this.parent.getTimeZone();
    }

    public void setTimeZone(TimeZone timeZone) {
        NullArgumentException.check("timeZone", timeZone);
        this.timeZone = timeZone;
        this.properties.setProperty(TIME_ZONE_KEY, timeZone.getID());
    }

    public void setSQLDateAndTimeTimeZone(TimeZone timeZone) {
        this.sqlDataAndTimeTimeZone = timeZone;
        this.sqlDataAndTimeTimeZoneSet = true;
        this.properties.setProperty(SQL_DATE_AND_TIME_TIME_ZONE_KEY, timeZone != null ? timeZone.getID() : "null");
    }

    public TimeZone getSQLDateAndTimeTimeZone() {
        if (this.sqlDataAndTimeTimeZoneSet) {
            return this.sqlDataAndTimeTimeZone;
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getSQLDateAndTimeTimeZone();
        }
        return null;
    }

    public Locale getLocale() {
        Locale locale = this.locale;
        return locale != null ? locale : this.parent.getLocale();
    }

    public void setNumberFormat(String str) {
        NullArgumentException.check("numberFormat", str);
        this.numberFormat = str;
        this.properties.setProperty(NUMBER_FORMAT_KEY, str);
    }

    public String getNumberFormat() {
        String str = this.numberFormat;
        return str != null ? str : this.parent.getNumberFormat();
    }

    public void setBooleanFormat(String str) {
        NullArgumentException.check("booleanFormat", str);
        int iIndexOf = str.indexOf(44);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Setting value must be string that contains two comma-separated values for true and false, respectively.");
        }
        this.booleanFormat = str;
        this.properties.setProperty(BOOLEAN_FORMAT_KEY, str);
        if (str.equals(C_TRUE_FALSE)) {
            this.trueStringValue = null;
            this.falseStringValue = null;
        } else {
            this.trueStringValue = str.substring(0, iIndexOf);
            this.falseStringValue = str.substring(iIndexOf + 1);
        }
    }

    public String getBooleanFormat() {
        String str = this.booleanFormat;
        return str != null ? str : this.parent.getBooleanFormat();
    }

    String formatBoolean(boolean z, boolean z2) throws TemplateException {
        if (z) {
            String trueStringValue = getTrueStringValue();
            if (trueStringValue != null) {
                return trueStringValue;
            }
            if (z2) {
                return BooleanUtils.TRUE;
            }
            throw new _MiscTemplateException(getNullBooleanFormatErrorDescription());
        }
        String falseStringValue = getFalseStringValue();
        if (falseStringValue != null) {
            return falseStringValue;
        }
        if (z2) {
            return BooleanUtils.FALSE;
        }
        throw new _MiscTemplateException(getNullBooleanFormatErrorDescription());
    }

    private _ErrorDescriptionBuilder getNullBooleanFormatErrorDescription() {
        Object[] objArr = new Object[5];
        objArr[0] = "Can't convert boolean to string automatically, because the \"";
        objArr[1] = BOOLEAN_FORMAT_KEY;
        objArr[2] = "\" setting was ";
        objArr[3] = new _DelayedJQuote(getBooleanFormat());
        objArr[4] = getBooleanFormat().equals(C_TRUE_FALSE) ? ", which is the legacy default computer-language format, and hence isn't accepted." : ".";
        return new _ErrorDescriptionBuilder(objArr).tips(new Object[]{"If you just want \"true\"/\"false\" result as you are generting computer-language output, use \"?c\", like ${myBool?c}.", "You can write myBool?string('yes', 'no') and like to specify boolean formatting in place.", new Object[]{"If you need the same two values on most places, the programmers should set the \"", BOOLEAN_FORMAT_KEY, "\" setting to something like \"yes,no\"."}});
    }

    String getTrueStringValue() {
        if (this.booleanFormat != null) {
            return this.trueStringValue;
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getTrueStringValue();
        }
        return null;
    }

    String getFalseStringValue() {
        if (this.booleanFormat != null) {
            return this.falseStringValue;
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getFalseStringValue();
        }
        return null;
    }

    public void setTimeFormat(String str) {
        NullArgumentException.check("timeFormat", str);
        this.timeFormat = str;
        this.properties.setProperty(TIME_FORMAT_KEY, str);
    }

    public String getTimeFormat() {
        String str = this.timeFormat;
        return str != null ? str : this.parent.getTimeFormat();
    }

    public void setDateFormat(String str) {
        NullArgumentException.check("dateFormat", str);
        this.dateFormat = str;
        this.properties.setProperty(DATE_FORMAT_KEY, str);
    }

    public String getDateFormat() {
        String str = this.dateFormat;
        return str != null ? str : this.parent.getDateFormat();
    }

    public void setDateTimeFormat(String str) {
        NullArgumentException.check("dateTimeFormat", str);
        this.dateTimeFormat = str;
        this.properties.setProperty(DATETIME_FORMAT_KEY, str);
    }

    public String getDateTimeFormat() {
        String str = this.dateTimeFormat;
        return str != null ? str : this.parent.getDateTimeFormat();
    }

    public void setTemplateExceptionHandler(TemplateExceptionHandler templateExceptionHandler) {
        NullArgumentException.check("templateExceptionHandler", templateExceptionHandler);
        this.templateExceptionHandler = templateExceptionHandler;
        this.properties.setProperty(TEMPLATE_EXCEPTION_HANDLER_KEY, templateExceptionHandler.getClass().getName());
    }

    public TemplateExceptionHandler getTemplateExceptionHandler() {
        TemplateExceptionHandler templateExceptionHandler = this.templateExceptionHandler;
        return templateExceptionHandler != null ? templateExceptionHandler : this.parent.getTemplateExceptionHandler();
    }

    public void setArithmeticEngine(ArithmeticEngine arithmeticEngine) {
        NullArgumentException.check("arithmeticEngine", arithmeticEngine);
        this.arithmeticEngine = arithmeticEngine;
        this.properties.setProperty(ARITHMETIC_ENGINE_KEY, arithmeticEngine.getClass().getName());
    }

    public ArithmeticEngine getArithmeticEngine() {
        ArithmeticEngine arithmeticEngine = this.arithmeticEngine;
        return arithmeticEngine != null ? arithmeticEngine : this.parent.getArithmeticEngine();
    }

    public void setObjectWrapper(ObjectWrapper objectWrapper) {
        NullArgumentException.check("objectWrapper", objectWrapper);
        this.objectWrapper = objectWrapper;
        this.properties.setProperty(OBJECT_WRAPPER_KEY, objectWrapper.getClass().getName());
    }

    public ObjectWrapper getObjectWrapper() {
        ObjectWrapper objectWrapper = this.objectWrapper;
        return objectWrapper != null ? objectWrapper : this.parent.getObjectWrapper();
    }

    public void setOutputEncoding(String str) {
        this.outputEncoding = str;
        if (str != null) {
            this.properties.setProperty(OUTPUT_ENCODING_KEY, str);
        } else {
            this.properties.remove(OUTPUT_ENCODING_KEY);
        }
        this.outputEncodingSet = true;
    }

    public String getOutputEncoding() {
        if (this.outputEncodingSet) {
            return this.outputEncoding;
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getOutputEncoding();
        }
        return null;
    }

    public void setURLEscapingCharset(String str) {
        this.urlEscapingCharset = str;
        if (str != null) {
            this.properties.setProperty(URL_ESCAPING_CHARSET_KEY, str);
        } else {
            this.properties.remove(URL_ESCAPING_CHARSET_KEY);
        }
        this.urlEscapingCharsetSet = true;
    }

    public String getURLEscapingCharset() {
        if (this.urlEscapingCharsetSet) {
            return this.urlEscapingCharset;
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getURLEscapingCharset();
        }
        return null;
    }

    public void setNewBuiltinClassResolver(TemplateClassResolver templateClassResolver) {
        NullArgumentException.check("newBuiltinClassResolver", templateClassResolver);
        this.newBuiltinClassResolver = templateClassResolver;
        this.properties.setProperty(NEW_BUILTIN_CLASS_RESOLVER_KEY, templateClassResolver.getClass().getName());
    }

    public TemplateClassResolver getNewBuiltinClassResolver() {
        TemplateClassResolver templateClassResolver = this.newBuiltinClassResolver;
        return templateClassResolver != null ? templateClassResolver : this.parent.getNewBuiltinClassResolver();
    }

    public void setAutoFlush(boolean z) {
        this.autoFlush = Boolean.valueOf(z);
        this.properties.setProperty(AUTO_FLUSH_KEY, String.valueOf(z));
    }

    public boolean getAutoFlush() {
        Boolean bool = this.autoFlush;
        if (bool != null) {
            return bool.booleanValue();
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getAutoFlush();
        }
        return true;
    }

    public void setShowErrorTips(boolean z) {
        this.showErrorTips = Boolean.valueOf(z);
        this.properties.setProperty(SHOW_ERROR_TIPS_KEY, String.valueOf(z));
    }

    public boolean getShowErrorTips() {
        Boolean bool = this.showErrorTips;
        if (bool != null) {
            return bool.booleanValue();
        }
        Configurable configurable = this.parent;
        if (configurable != null) {
            return configurable.getShowErrorTips();
        }
        return true;
    }

    public void setSetting(String str, String str2) throws TemplateException {
        try {
            boolean z = false;
            if (LOCALE_KEY.equals(str)) {
                setLocale(StringUtil.deduceLocale(str2));
            } else if (NUMBER_FORMAT_KEY.equals(str)) {
                setNumberFormat(str2);
            } else if (TIME_FORMAT_KEY.equals(str)) {
                setTimeFormat(str2);
            } else if (DATE_FORMAT_KEY.equals(str)) {
                setDateFormat(str2);
            } else if (DATETIME_FORMAT_KEY.equals(str)) {
                setDateTimeFormat(str2);
            } else if (TIME_ZONE_KEY.equals(str)) {
                setTimeZone(parseTimeZoneSettingValue(str2));
            } else {
                HashSet hashSet = null;
                TimeZone timeZoneSettingValue = null;
                if (SQL_DATE_AND_TIME_TIME_ZONE_KEY.equals(str)) {
                    if (!str2.equals("null")) {
                        timeZoneSettingValue = parseTimeZoneSettingValue(str2);
                    }
                    setSQLDateAndTimeTimeZone(timeZoneSettingValue);
                } else if (CLASSIC_COMPATIBLE_KEY.equals(str)) {
                    char cCharAt = (str2 == null || str2.length() <= 0) ? (char) 0 : str2.charAt(0);
                    if (Character.isDigit(cCharAt) || cCharAt == '+' || cCharAt == '-') {
                        setClassicCompatibleAsInt(Integer.parseInt(str2));
                    } else {
                        setClassicCompatible(str2 != null ? StringUtil.getYesNo(str2) : false);
                    }
                } else if (TEMPLATE_EXCEPTION_HANDLER_KEY.equals(str)) {
                    if (str2.indexOf(46) == -1) {
                        if ("debug".equalsIgnoreCase(str2)) {
                            setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
                        } else if ("html_debug".equalsIgnoreCase(str2)) {
                            setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
                        } else if ("ignore".equalsIgnoreCase(str2)) {
                            setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
                        } else if ("rethrow".equalsIgnoreCase(str2)) {
                            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                        } else {
                            throw invalidSettingValueException(str, str2);
                        }
                    } else {
                        Class clsClass$ = class$freemarker$template$TemplateExceptionHandler;
                        if (clsClass$ == null) {
                            clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.template.TemplateExceptionHandler");
                            class$freemarker$template$TemplateExceptionHandler = clsClass$;
                        }
                        setTemplateExceptionHandler((TemplateExceptionHandler) _ObjectBuilderSettingEvaluator.eval(str2, clsClass$, _SettingEvaluationEnvironment.getCurrent()));
                    }
                } else if (ARITHMETIC_ENGINE_KEY.equals(str)) {
                    if (str2.indexOf(46) == -1) {
                        if ("bigdecimal".equalsIgnoreCase(str2)) {
                            setArithmeticEngine(ArithmeticEngine.BIGDECIMAL_ENGINE);
                        } else if ("conservative".equalsIgnoreCase(str2)) {
                            setArithmeticEngine(ArithmeticEngine.CONSERVATIVE_ENGINE);
                        } else {
                            throw invalidSettingValueException(str, str2);
                        }
                    } else {
                        Class clsClass$2 = class$freemarker$core$ArithmeticEngine;
                        if (clsClass$2 == null) {
                            clsClass$2 = class$("org.mapstruct.ap.shaded.freemarker.core.ArithmeticEngine");
                            class$freemarker$core$ArithmeticEngine = clsClass$2;
                        }
                        setArithmeticEngine((ArithmeticEngine) _ObjectBuilderSettingEvaluator.eval(str2, clsClass$2, _SettingEvaluationEnvironment.getCurrent()));
                    }
                } else if (OBJECT_WRAPPER_KEY.equals(str)) {
                    if (DEFAULT.equalsIgnoreCase(str2)) {
                        setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
                    } else if (FtsOptions.TOKENIZER_SIMPLE.equalsIgnoreCase(str2)) {
                        setObjectWrapper(ObjectWrapper.SIMPLE_WRAPPER);
                    } else if ("beans".equalsIgnoreCase(str2)) {
                        setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
                    } else if ("jython".equalsIgnoreCase(str2)) {
                        setObjectWrapper((ObjectWrapper) Class.forName("org.mapstruct.ap.shaded.freemarker.ext.jython.JythonWrapper").getField("INSTANCE").get(null));
                    } else {
                        Class clsClass$3 = class$freemarker$template$ObjectWrapper;
                        if (clsClass$3 == null) {
                            clsClass$3 = class$("org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper");
                            class$freemarker$template$ObjectWrapper = clsClass$3;
                        }
                        setObjectWrapper((ObjectWrapper) _ObjectBuilderSettingEvaluator.eval(str2, clsClass$3, _SettingEvaluationEnvironment.getCurrent()));
                    }
                } else if (BOOLEAN_FORMAT_KEY.equals(str)) {
                    setBooleanFormat(str2);
                } else if (OUTPUT_ENCODING_KEY.equals(str)) {
                    setOutputEncoding(str2);
                } else if (URL_ESCAPING_CHARSET_KEY.equals(str)) {
                    setURLEscapingCharset(str2);
                } else if (STRICT_BEAN_MODELS.equals(str)) {
                    setStrictBeanModels(StringUtil.getYesNo(str2));
                } else if (AUTO_FLUSH_KEY.equals(str)) {
                    setAutoFlush(StringUtil.getYesNo(str2));
                } else if (SHOW_ERROR_TIPS_KEY.equals(str)) {
                    setShowErrorTips(StringUtil.getYesNo(str2));
                } else if (!NEW_BUILTIN_CLASS_RESOLVER_KEY.equals(str)) {
                    z = true;
                } else if ("unrestricted".equals(str2)) {
                    setNewBuiltinClassResolver(TemplateClassResolver.UNRESTRICTED_RESOLVER);
                } else if ("safer".equals(str2)) {
                    setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
                } else if ("allows_nothing".equals(str2)) {
                    setNewBuiltinClassResolver(TemplateClassResolver.ALLOWS_NOTHING_RESOLVER);
                } else if (str2.indexOf(":") != -1) {
                    ArrayList asSegmentedList = parseAsSegmentedList(str2);
                    List list = null;
                    for (int i = 0; i < asSegmentedList.size(); i++) {
                        KeyValuePair keyValuePair = (KeyValuePair) asSegmentedList.get(i);
                        String str3 = (String) keyValuePair.getKey();
                        List list2 = (List) keyValuePair.getValue();
                        if (str3.equals(ALLOWED_CLASSES)) {
                            hashSet = new HashSet(list2);
                        } else {
                            if (!str3.equals(TRUSTED_TEMPLATES)) {
                                throw new ParseException(new StringBuffer().append("Unrecognized list segment key: ").append(StringUtil.jQuote(str3)).append(". Supported keys are: \"").append(ALLOWED_CLASSES).append("\", \"").append(TRUSTED_TEMPLATES).append("\"").toString(), 0, 0);
                            }
                            list = list2;
                        }
                    }
                    setNewBuiltinClassResolver(new OptInTemplateClassResolver(hashSet, list));
                } else if (str2.indexOf(46) != -1) {
                    Class clsClass$4 = class$freemarker$core$TemplateClassResolver;
                    if (clsClass$4 == null) {
                        clsClass$4 = class$("org.mapstruct.ap.shaded.freemarker.core.TemplateClassResolver");
                        class$freemarker$core$TemplateClassResolver = clsClass$4;
                    }
                    setNewBuiltinClassResolver((TemplateClassResolver) _ObjectBuilderSettingEvaluator.eval(str2, clsClass$4, _SettingEvaluationEnvironment.getCurrent()));
                } else {
                    throw invalidSettingValueException(str, str2);
                }
            }
            if (z) {
                throw unknownSettingException(str);
            }
        } catch (Exception e) {
            throw settingValueAssignmentException(str, str2, e);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private TimeZone parseTimeZoneSettingValue(String str) {
        if (JVM_DEFAULT.equalsIgnoreCase(str)) {
            return TimeZone.getDefault();
        }
        return TimeZone.getTimeZone(str);
    }

    public void setStrictBeanModels(boolean z) throws Throwable {
        ObjectWrapper objectWrapper = this.objectWrapper;
        if (!(objectWrapper instanceof BeansWrapper)) {
            StringBuffer stringBuffer = new StringBuffer("The value of the object_wrapper setting isn't a ");
            Class clsClass$ = class$freemarker$ext$beans$BeansWrapper;
            if (clsClass$ == null) {
                clsClass$ = class$("org.mapstruct.ap.shaded.freemarker.ext.beans.BeansWrapper");
                class$freemarker$ext$beans$BeansWrapper = clsClass$;
            }
            throw new IllegalStateException(stringBuffer.append(clsClass$.getName()).append(".").toString());
        }
        ((BeansWrapper) objectWrapper).setStrict(z);
    }

    public String getSetting(String str) {
        return this.properties.getProperty(str);
    }

    public Map getSettings() {
        return Collections.unmodifiableMap(this.properties);
    }

    protected Environment getEnvironment() {
        return this instanceof Environment ? (Environment) this : Environment.getCurrentEnvironment();
    }

    protected TemplateException unknownSettingException(String str) {
        return new UnknownSettingException(getEnvironment(), str, getCorrectedNameForUnknownSetting(str));
    }

    protected TemplateException settingValueAssignmentException(String str, String str2, Throwable th) {
        return new SettingValueAssignmentException(getEnvironment(), str, str2, th);
    }

    protected TemplateException invalidSettingValueException(String str, String str2) {
        return new _MiscTemplateException(getEnvironment(), new Object[]{"Invalid value for setting ", new _DelayedJQuote(str), ": ", new _DelayedJQuote(str2)});
    }

    public static class UnknownSettingException extends _MiscTemplateException {
        /* JADX WARN: Illegal instructions before constructor call */
        private UnknownSettingException(Environment environment, String str, String str2) {
            Object[] objArr = new Object[3];
            objArr[0] = "Unknown setting: ";
            objArr[1] = new _DelayedJQuote(str);
            objArr[2] = str2 == null ? "" : new Object[]{". You may meant: ", new _DelayedJQuote(str2)};
            super(environment, objArr);
        }
    }

    public static class SettingValueAssignmentException extends _MiscTemplateException {
        private SettingValueAssignmentException(Environment environment, String str, String str2, Throwable th) {
            super(th, environment, new Object[]{"Failed to set setting ", new _DelayedJQuote(str), " to value ", new _DelayedJQuote(str2), "; see cause exception."});
        }
    }

    public void setSettings(Properties properties) throws TemplateException {
        _SettingEvaluationEnvironment _settingevaluationenvironmentStartScope = _SettingEvaluationEnvironment.startScope();
        try {
            for (String str : properties.keySet()) {
                setSetting(str, properties.getProperty(str).trim());
            }
        } finally {
            _SettingEvaluationEnvironment.endScope(_settingevaluationenvironmentStartScope);
        }
    }

    public void setSettings(InputStream inputStream) throws IOException, TemplateException {
        Properties properties = new Properties();
        properties.load(inputStream);
        setSettings(properties);
    }

    void setCustomAttribute(Object obj, Object obj2) {
        synchronized (this.customAttributes) {
            this.customAttributes.put(obj, obj2);
        }
    }

    Object getCustomAttribute(Object obj, CustomAttribute customAttribute) {
        Object objCreate;
        synchronized (this.customAttributes) {
            objCreate = this.customAttributes.get(obj);
            if (objCreate == null && !this.customAttributes.containsKey(obj)) {
                objCreate = customAttribute.create();
                this.customAttributes.put(obj, objCreate);
            }
        }
        return objCreate;
    }

    public void setCustomAttribute(String str, Object obj) {
        synchronized (this.customAttributes) {
            this.customAttributes.put(str, obj);
        }
    }

    public String[] getCustomAttributeNames() {
        String[] strArr;
        synchronized (this.customAttributes) {
            LinkedList linkedList = new LinkedList(this.customAttributes.keySet());
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                if (!(it.next() instanceof String)) {
                    it.remove();
                }
            }
            strArr = (String[]) linkedList.toArray(new String[linkedList.size()]);
        }
        return strArr;
    }

    public void removeCustomAttribute(String str) {
        synchronized (this.customAttributes) {
            this.customAttributes.remove(str);
        }
    }

    public Object getCustomAttribute(String str) {
        Configurable configurable;
        synchronized (this.customAttributes) {
            Object obj = this.customAttributes.get(str);
            if (obj == null && this.customAttributes.containsKey(str)) {
                return null;
            }
            return (obj != null || (configurable = this.parent) == null) ? obj : configurable.getCustomAttribute(str);
        }
    }

    protected void doAutoImportsAndIncludes(Environment environment) throws TemplateException, IOException {
        Configurable configurable = this.parent;
        if (configurable != null) {
            configurable.doAutoImportsAndIncludes(environment);
        }
    }

    protected ArrayList parseAsList(String str) throws ParseException {
        return new SettingStringParser(str).parseAsList();
    }

    protected ArrayList parseAsSegmentedList(String str) throws ParseException {
        return new SettingStringParser(str).parseAsSegmentedList();
    }

    protected HashMap parseAsImportList(String str) throws ParseException {
        return new SettingStringParser(str).parseAsImportList();
    }

    private static class KeyValuePair {
        private final Object key;
        private final Object value;

        KeyValuePair(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }

        Object getKey() {
            return this.key;
        }

        Object getValue() {
            return this.value;
        }
    }

    private static class SettingStringParser {
        private int ln;
        private int p;
        private String text;

        private SettingStringParser(String str) {
            this.text = str;
            this.p = 0;
            this.ln = str.length();
        }

        ArrayList parseAsSegmentedList() throws ParseException {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = null;
            while (skipWS() != ' ') {
                String strFetchStringValue = fetchStringValue();
                char cSkipWS = skipWS();
                if (cSkipWS == ':') {
                    arrayList2 = new ArrayList();
                    arrayList.add(new KeyValuePair(strFetchStringValue, arrayList2));
                } else {
                    if (arrayList2 == null) {
                        throw new ParseException("The very first list item must be followed by \":\" so it will be the key for the following sub-list.", 0, 0);
                    }
                    arrayList2.add(strFetchStringValue);
                }
                if (cSkipWS == ' ') {
                    break;
                }
                if (cSkipWS != ',' && cSkipWS != ':') {
                    throw new ParseException(new StringBuffer("Expected \",\" or \":\" or the end of text but found \"").append(cSkipWS).append("\"").toString(), 0, 0);
                }
                this.p++;
            }
            return arrayList;
        }

        ArrayList parseAsList() throws ParseException {
            ArrayList arrayList = new ArrayList();
            while (skipWS() != ' ') {
                arrayList.add(fetchStringValue());
                char cSkipWS = skipWS();
                if (cSkipWS == ' ') {
                    break;
                }
                if (cSkipWS != ',') {
                    throw new ParseException(new StringBuffer("Expected \",\" or the end of text but found \"").append(cSkipWS).append("\"").toString(), 0, 0);
                }
                this.p++;
            }
            return arrayList;
        }

        HashMap parseAsImportList() throws ParseException {
            HashMap map = new HashMap();
            while (skipWS() != ' ') {
                String strFetchStringValue = fetchStringValue();
                if (skipWS() == ' ') {
                    throw new ParseException("Unexpected end of text: expected \"as\"", 0, 0);
                }
                String strFetchKeyword = fetchKeyword();
                if (!strFetchKeyword.equalsIgnoreCase("as")) {
                    throw new ParseException(new StringBuffer("Expected \"as\", but found ").append(StringUtil.jQuote(strFetchKeyword)).toString(), 0, 0);
                }
                if (skipWS() == ' ') {
                    throw new ParseException("Unexpected end of text: expected gate hash name", 0, 0);
                }
                map.put(fetchStringValue(), strFetchStringValue);
                char cSkipWS = skipWS();
                if (cSkipWS == ' ') {
                    break;
                }
                if (cSkipWS != ',') {
                    throw new ParseException(new StringBuffer("Expected \",\" or the end of text but found \"").append(cSkipWS).append("\"").toString(), 0, 0);
                }
                this.p++;
            }
            return map;
        }

        String fetchStringValue() throws ParseException {
            String strFetchWord = fetchWord();
            if (strFetchWord.startsWith("'") || strFetchWord.startsWith("\"")) {
                strFetchWord = strFetchWord.substring(1, strFetchWord.length() - 1);
            }
            return StringUtil.FTLStringLiteralDec(strFetchWord);
        }

        String fetchKeyword() throws ParseException {
            String strFetchWord = fetchWord();
            if (strFetchWord.startsWith("'") || strFetchWord.startsWith("\"")) {
                throw new ParseException(new StringBuffer("Keyword expected, but a string value found: ").append(strFetchWord).toString(), 0, 0);
            }
            return strFetchWord;
        }

        char skipWS() {
            while (true) {
                int i = this.p;
                if (i >= this.ln) {
                    return ' ';
                }
                char cCharAt = this.text.charAt(i);
                if (!Character.isWhitespace(cCharAt)) {
                    return cCharAt;
                }
                this.p++;
            }
        }

        private String fetchWord() throws ParseException {
            char cCharAt;
            int i;
            int i2 = this.p;
            if (i2 == this.ln) {
                throw new ParseException("Unexpeced end of text", 0, 0);
            }
            char cCharAt2 = this.text.charAt(i2);
            int i3 = this.p;
            if (cCharAt2 == '\'' || cCharAt2 == '\"') {
                this.p = i3 + 1;
                boolean z = false;
                while (true) {
                    int i4 = this.p;
                    if (i4 >= this.ln) {
                        break;
                    }
                    char cCharAt3 = this.text.charAt(i4);
                    if (z) {
                        z = false;
                    } else if (cCharAt3 == '\\') {
                        z = true;
                    } else if (cCharAt3 == cCharAt2) {
                        break;
                    }
                    this.p++;
                }
                int i5 = this.p;
                if (i5 == this.ln) {
                    throw new ParseException(new StringBuffer("Missing ").append(cCharAt2).toString(), 0, 0);
                }
                int i6 = i5 + 1;
                this.p = i6;
                return this.text.substring(i3, i6);
            }
            do {
                cCharAt = this.text.charAt(this.p);
                if (!Character.isLetterOrDigit(cCharAt) && cCharAt != '/' && cCharAt != '\\' && cCharAt != '_' && cCharAt != '.' && cCharAt != '-' && cCharAt != '!' && cCharAt != '*' && cCharAt != '?') {
                    break;
                }
                i = this.p + 1;
                this.p = i;
            } while (i < this.ln);
            int i7 = this.p;
            if (i3 == i7) {
                throw new ParseException(new StringBuffer("Unexpected character: ").append(cCharAt).toString(), 0, 0);
            }
            return this.text.substring(i3, i7);
        }
    }
}
