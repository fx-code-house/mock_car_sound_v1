package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
class MessageUtil {
    static final String EMBEDDED_MESSAGE_BEGIN = "---begin-message---\n";
    static final String EMBEDDED_MESSAGE_END = "\n---end-message---";
    static final String UNKNOWN_DATE_TO_STRING_ERROR_MESSAGE = "Can't convert the date-like value to string because it isn't known if it's a date (no time part), time or date-time value.";
    static final String UNKNOWN_DATE_TYPE_ERROR_TIP = "Use ?date, ?time, or ?datetime to tell FreeMarker the exact type.";
    static final String[] UNKNOWN_DATE_TO_STRING_TIPS = {UNKNOWN_DATE_TYPE_ERROR_TIP, "If you need a particular format only once, use ?string(pattern), like ?string('dd.MM.yyyy HH:mm:ss'), to specify which fields to display. "};

    private MessageUtil() {
    }

    static String formatLocationForSimpleParsingError(Template template, int i, int i2) {
        return formatLocation("in", template, i, i2);
    }

    static String formatLocationForSimpleParsingError(String str, int i, int i2) {
        return formatLocation("in", str, i, i2);
    }

    static String formatLocationForDependentParsingError(Template template, int i, int i2) {
        return formatLocation("on", template, i, i2);
    }

    static String formatLocationForDependentParsingError(String str, int i, int i2) {
        return formatLocation("on", str, i, i2);
    }

    static String formatLocationForEvaluationError(Template template, int i, int i2) {
        return formatLocation("at", template, i, i2);
    }

    static String formatLocationForEvaluationError(Macro macro, int i, int i2) {
        Template template = macro.getTemplate();
        return formatLocation("at", template != null ? template.getName() : null, macro.getName(), macro.isFunction(), i, i2);
    }

    static String formatLocationForEvaluationError(String str, int i, int i2) {
        return formatLocation("at", str, i, i2);
    }

    private static String formatLocation(String str, Template template, int i, int i2) {
        return formatLocation(str, template != null ? template.getName() : null, i, i2);
    }

    private static String formatLocation(String str, String str2, int i, int i2) {
        return formatLocation(str, str2, null, false, i, i2);
    }

    private static String formatLocation(String str, String str2, String str3, boolean z, int i, int i2) {
        String string;
        String string2;
        if (i < 0) {
            i += 1000000001;
            string = "?eval-ed string";
            str3 = null;
        } else {
            string = str2 != null ? new StringBuffer("template ").append(StringUtil.jQuoteNoXSS(str2)).toString() : "nameless template";
        }
        StringBuffer stringBufferAppend = new StringBuffer("in ").append(string);
        if (str3 != null) {
            string2 = new StringBuffer(" in ").append(z ? "function " : "macro ").append(StringUtil.jQuote(str3)).toString();
        } else {
            string2 = "";
        }
        return stringBufferAppend.append(string2).append(StringUtils.SPACE).append(str).append(" line ").append(i).append(", column ").append(i2).toString();
    }

    static String shorten(String str, int i) {
        boolean z;
        if (i < 5) {
            i = 5;
        }
        int iIndexOf = str.indexOf(10);
        boolean z2 = true;
        if (iIndexOf != -1) {
            str = str.substring(0, iIndexOf);
            z = true;
        } else {
            z = false;
        }
        int iIndexOf2 = str.indexOf(13);
        if (iIndexOf2 != -1) {
            str = str.substring(0, iIndexOf2);
            z = true;
        }
        if (str.length() > i) {
            str = str.substring(0, i - 3);
        } else {
            z2 = z;
        }
        if (!z2) {
            return str;
        }
        if (str.endsWith(".")) {
            if (str.endsWith("..")) {
                return str.endsWith("...") ? str : new StringBuffer().append(str).append(".").toString();
            }
            return new StringBuffer().append(str).append("..").toString();
        }
        return new StringBuffer().append(str).append("...").toString();
    }

    static StringBuffer appendExpressionAsUntearable(StringBuffer stringBuffer, Expression expression) {
        boolean z = ((expression instanceof NumberLiteral) || (expression instanceof StringLiteral) || (expression instanceof BooleanLiteral) || (expression instanceof ListLiteral) || (expression instanceof HashLiteral) || (expression instanceof Identifier) || (expression instanceof Dot) || (expression instanceof DynamicKeyName) || (expression instanceof MethodCall) || (expression instanceof BuiltIn)) ? false : true;
        if (z) {
            stringBuffer.append('(');
        }
        stringBuffer.append(expression.getCanonicalForm());
        if (z) {
            stringBuffer.append(')');
        }
        return stringBuffer;
    }

    static TemplateModelException newArgCntError(String str, int i, int i2) {
        return newArgCntError(str, i, i2, i2);
    }

    static TemplateModelException newArgCntError(String str, int i, int i2, int i3) {
        ArrayList arrayList = new ArrayList(20);
        arrayList.add(str);
        arrayList.add("(");
        if (i3 != 0) {
            arrayList.add("...");
        }
        arrayList.add(") expects ");
        if (i2 == i3) {
            if (i3 == 0) {
                arrayList.add(BooleanUtils.NO);
            } else {
                arrayList.add(new Integer(i3));
            }
        } else if (i3 - i2 == 1) {
            arrayList.add(new Integer(i2));
            arrayList.add(" or ");
            arrayList.add(new Integer(i3));
        } else {
            arrayList.add(new Integer(i2));
            if (i3 != Integer.MAX_VALUE) {
                arrayList.add(" to ");
                arrayList.add(new Integer(i3));
            } else {
                arrayList.add(" or more (unlimited)");
            }
        }
        arrayList.add(" argument");
        if (i3 > 1) {
            arrayList.add("s");
        }
        arrayList.add(" but has received ");
        if (i == 0) {
            arrayList.add("none");
        } else {
            arrayList.add(new Integer(i));
        }
        arrayList.add(".");
        return new _TemplateModelException(arrayList.toArray());
    }

    static TemplateModelException newMethodArgMustBeStringException(String str, int i, TemplateModel templateModel) {
        return newMethodArgUnexpectedTypeException(str, i, "string", templateModel);
    }

    static TemplateModelException newMethodArgMustBeNumberException(String str, int i, TemplateModel templateModel) {
        return newMethodArgUnexpectedTypeException(str, i, "number", templateModel);
    }

    static TemplateModelException newMethodArgMustBeBooleanException(String str, int i, TemplateModel templateModel) {
        return newMethodArgUnexpectedTypeException(str, i, "boolean", templateModel);
    }

    static TemplateModelException newMethodArgMustBeExtendedHashException(String str, int i, TemplateModel templateModel) {
        return newMethodArgUnexpectedTypeException(str, i, "extended hash", templateModel);
    }

    static TemplateModelException newMethodArgMustBeSequenceException(String str, int i, TemplateModel templateModel) {
        return newMethodArgUnexpectedTypeException(str, i, "sequence", templateModel);
    }

    static TemplateModelException newMethodArgMustBeSequenceOrCollectionException(String str, int i, TemplateModel templateModel) {
        return newMethodArgUnexpectedTypeException(str, i, "sequence or collection", templateModel);
    }

    static TemplateModelException newMethodArgUnexpectedTypeException(String str, int i, String str2, TemplateModel templateModel) {
        return new _TemplateModelException(new Object[]{str, "(...) expects ", new _DelayedAOrAn(str2), " as argument #", new Integer(i + 1), ", but received ", new _DelayedAOrAn(new _DelayedFTLTypeDescription(templateModel)), "."});
    }

    static TemplateModelException newMethodArgInvalidValueException(String str, int i, Object[] objArr) {
        return new _TemplateModelException(new Object[]{str, "(...) argument #", new Integer(i + 1), " had invalid value: ", objArr});
    }

    static TemplateModelException newMethodArgsInvalidValueException(String str, Object[] objArr) {
        return new _TemplateModelException(new Object[]{str, "(...) arguments have invalid value: ", objArr});
    }

    static TemplateException newInstantiatingClassNotAllowedException(String str, Environment environment) {
        return new _MiscTemplateException(environment, new Object[]{"Instantiating ", str, " is not allowed in the template for security reasons."});
    }

    static _TemplateModelException newCantFormatUnknownTypeDateException(Expression expression, UnknownDateTypeFormattingUnsupportedException unknownDateTypeFormattingUnsupportedException) {
        return new _TemplateModelException(unknownDateTypeFormattingUnsupportedException, (Environment) null, new _ErrorDescriptionBuilder(UNKNOWN_DATE_TO_STRING_ERROR_MESSAGE).blame(expression).tips(UNKNOWN_DATE_TO_STRING_TIPS));
    }

    static TemplateModelException newCantFormatDateException(Expression expression, UnformattableDateException unformattableDateException) {
        return new _TemplateModelException(unformattableDateException, (Environment) null, new _ErrorDescriptionBuilder(unformattableDateException.getMessage()).blame(expression));
    }

    static String getAOrAn(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "";
        }
        char lowerCase = Character.toLowerCase(str.charAt(0));
        if (lowerCase == 'a' || lowerCase == 'e' || lowerCase == 'i') {
            return "an";
        }
        if (lowerCase == 'h') {
            String lowerCase2 = str.toLowerCase();
            return (lowerCase2.startsWith("has") || lowerCase2.startsWith("hi")) ? "a" : lowerCase2.startsWith("ht") ? "an" : "a(n)";
        }
        if (lowerCase == 'u' || lowerCase == 'o') {
            return "a(n)";
        }
        char cCharAt = str.length() > 1 ? str.charAt(1) : (char) 0;
        return (lowerCase != 'x' || cCharAt == 'a' || cCharAt == 'e' || cCharAt == 'i' || cCharAt == 'a' || cCharAt == 'o' || cCharAt == 'u') ? "a" : "an";
    }
}
