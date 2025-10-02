package org.mapstruct.ap.shaded.freemarker.core;

import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import org.mapstruct.ap.shaded.freemarker.template.ObjectWrapper;
import org.mapstruct.ap.shaded.freemarker.template.SimpleNumber;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
class BuiltInsForStringsBasic {

    static class cap_firstBI extends BuiltInForString {
        cap_firstBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            int length = str.length();
            int i = 0;
            while (i < length && Character.isWhitespace(str.charAt(i))) {
                i++;
            }
            if (i < length) {
                StringBuffer stringBuffer = new StringBuffer(str);
                stringBuffer.setCharAt(i, Character.toUpperCase(str.charAt(i)));
                str = stringBuffer.toString();
            }
            return new SimpleScalar(str);
        }
    }

    static class capitalizeBI extends BuiltInForString {
        capitalizeBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.capitalize(str));
        }
    }

    static class chop_linebreakBI extends BuiltInForString {
        chop_linebreakBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(StringUtil.chomp(str));
        }
    }

    static class containsBI extends BuiltIn {
        containsBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private final String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                containsBI.this.checkMethodArgCount(list, 1);
                return this.s.indexOf(containsBI.this.getStringMethodArg(list, 0)) != -1 ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            return new BIMethod(this.target.evalAndCoerceToString(environment, "For sequences/collections (lists and such) use \"?seq_contains\" instead."));
        }
    }

    static class ends_withBI extends BuiltInForString {
        ends_withBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                ends_withBI.this.checkMethodArgCount(list, 1);
                return this.s.endsWith(ends_withBI.this.getStringMethodArg(list, 0)) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class ensure_ends_withBI extends BuiltInForString {
        ensure_ends_withBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                ensure_ends_withBI.this.checkMethodArgCount(list, 1);
                String stringMethodArg = ensure_ends_withBI.this.getStringMethodArg(list, 0);
                return new SimpleScalar(this.s.endsWith(stringMethodArg) ? this.s : new StringBuffer().append(this.s).append(stringMethodArg).toString());
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class ensure_starts_withBI extends BuiltInForString {
        ensure_starts_withBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                boolean zStartsWith;
                ensure_starts_withBI.this.checkMethodArgCount(list, 1, 3);
                String stringMethodArg = ensure_starts_withBI.this.getStringMethodArg(list, 0);
                if (list.size() > 1) {
                    String stringMethodArg2 = ensure_starts_withBI.this.getStringMethodArg(list, 1);
                    long flagString = list.size() > 2 ? RegexpHelper.parseFlagString(ensure_starts_withBI.this.getStringMethodArg(list, 2)) : 4294967296L;
                    if ((flagString & 4294967296L) == 0) {
                        RegexpHelper.checkNonRegexpFlags(ensure_starts_withBI.this.key, flagString, true);
                        if ((RegexpHelper.RE_FLAG_CASE_INSENSITIVE & flagString) == 0) {
                            zStartsWith = this.s.startsWith(stringMethodArg);
                        } else {
                            zStartsWith = this.s.toLowerCase().startsWith(stringMethodArg.toLowerCase());
                        }
                    } else {
                        zStartsWith = RegexpHelper.getPattern(stringMethodArg, (int) flagString).matcher(this.s).lookingAt();
                    }
                    stringMethodArg = stringMethodArg2;
                } else {
                    zStartsWith = this.s.startsWith(stringMethodArg);
                }
                return new SimpleScalar(zStartsWith ? this.s : new StringBuffer().append(stringMethodArg).append(this.s).toString());
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class index_ofBI extends BuiltIn {
        private final boolean findLast;

        private class BIMethod implements TemplateMethodModelEx {
            private final String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                int size = list.size();
                index_ofBI.this.checkMethodArgCount(size, 1, 2);
                String stringMethodArg = index_ofBI.this.getStringMethodArg(list, 0);
                if (size > 1) {
                    int iIntValue = index_ofBI.this.getNumberMethodArg(list, 1).intValue();
                    return new SimpleNumber(index_ofBI.this.findLast ? this.s.lastIndexOf(stringMethodArg, iIntValue) : this.s.indexOf(stringMethodArg, iIntValue));
                }
                return new SimpleNumber(index_ofBI.this.findLast ? this.s.lastIndexOf(stringMethodArg) : this.s.indexOf(stringMethodArg));
            }
        }

        index_ofBI(boolean z) {
            this.findLast = z;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws TemplateException {
            return new BIMethod(this.target.evalAndCoerceToString(environment, "For sequences/collections (lists and such) use \"?seq_index_of\" instead."));
        }
    }

    static class keep_afterBI extends BuiltInForString {
        keep_afterBI() {
        }

        class KeepAfterMethod implements TemplateMethodModelEx {
            private String s;

            KeepAfterMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                int iEnd;
                int size = list.size();
                keep_afterBI.this.checkMethodArgCount(size, 1, 2);
                String stringMethodArg = keep_afterBI.this.getStringMethodArg(list, 0);
                long flagString = size > 1 ? RegexpHelper.parseFlagString(keep_afterBI.this.getStringMethodArg(list, 1)) : 0L;
                if ((4294967296L & flagString) == 0) {
                    RegexpHelper.checkNonRegexpFlags(keep_afterBI.this.key, flagString, true);
                    if ((RegexpHelper.RE_FLAG_CASE_INSENSITIVE & flagString) == 0) {
                        iEnd = this.s.indexOf(stringMethodArg);
                    } else {
                        iEnd = this.s.toLowerCase().indexOf(stringMethodArg.toLowerCase());
                    }
                    if (iEnd >= 0) {
                        iEnd += stringMethodArg.length();
                    }
                } else {
                    Matcher matcher = RegexpHelper.getPattern(stringMethodArg, (int) flagString).matcher(this.s);
                    iEnd = matcher.find() ? matcher.end() : -1;
                }
                return iEnd == -1 ? SimpleScalar.EMPTY_STRING : new SimpleScalar(this.s.substring(iEnd));
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateModelException {
            return new KeepAfterMethod(str);
        }
    }

    static class keep_beforeBI extends BuiltInForString {
        keep_beforeBI() {
        }

        class KeepUntilMethod implements TemplateMethodModelEx {
            private String s;

            KeepUntilMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                int iStart;
                int size = list.size();
                keep_beforeBI.this.checkMethodArgCount(size, 1, 2);
                String stringMethodArg = keep_beforeBI.this.getStringMethodArg(list, 0);
                long flagString = size > 1 ? RegexpHelper.parseFlagString(keep_beforeBI.this.getStringMethodArg(list, 1)) : 0L;
                if ((4294967296L & flagString) == 0) {
                    RegexpHelper.checkNonRegexpFlags(keep_beforeBI.this.key, flagString, true);
                    if ((flagString & RegexpHelper.RE_FLAG_CASE_INSENSITIVE) == 0) {
                        iStart = this.s.indexOf(stringMethodArg);
                    } else {
                        iStart = this.s.toLowerCase().indexOf(stringMethodArg.toLowerCase());
                    }
                } else {
                    Matcher matcher = RegexpHelper.getPattern(stringMethodArg, (int) flagString).matcher(this.s);
                    iStart = matcher.find() ? matcher.start() : -1;
                }
                return iStart == -1 ? new SimpleScalar(this.s) : new SimpleScalar(this.s.substring(0, iStart));
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateModelException {
            return new KeepUntilMethod(str);
        }
    }

    static class lengthBI extends BuiltInForString {
        lengthBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new SimpleNumber(str.length());
        }
    }

    static class lower_caseBI extends BuiltInForString {
        lower_caseBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(str.toLowerCase(environment.getLocale()));
        }
    }

    static class padBI extends BuiltInForString {
        private final boolean leftPadder;

        private class BIMethod implements TemplateMethodModelEx {
            private final String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                String strRightPad;
                int size = list.size();
                padBI.this.checkMethodArgCount(size, 1, 2);
                int iIntValue = padBI.this.getNumberMethodArg(list, 0).intValue();
                if (size > 1) {
                    String stringMethodArg = padBI.this.getStringMethodArg(list, 1);
                    try {
                        if (padBI.this.leftPadder) {
                            strRightPad = StringUtil.leftPad(this.s, iIntValue, stringMethodArg);
                        } else {
                            strRightPad = StringUtil.rightPad(this.s, iIntValue, stringMethodArg);
                        }
                        return new SimpleScalar(strRightPad);
                    } catch (IllegalArgumentException e) {
                        if (stringMethodArg.length() == 0) {
                            throw new _TemplateModelException(new Object[]{"?", padBI.this.key, "(...) argument #2 can't be a 0-length string."});
                        }
                        throw new _TemplateModelException(e, new Object[]{"?", padBI.this.key, "(...) failed: ", e});
                    }
                }
                return new SimpleScalar(padBI.this.leftPadder ? StringUtil.leftPad(this.s, iIntValue) : StringUtil.rightPad(this.s, iIntValue));
            }
        }

        padBI(boolean z) {
            this.leftPadder = z;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class remove_beginningBI extends BuiltInForString {
        remove_beginningBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                remove_beginningBI.this.checkMethodArgCount(list, 1);
                String stringMethodArg = remove_beginningBI.this.getStringMethodArg(list, 0);
                return new SimpleScalar(this.s.startsWith(stringMethodArg) ? this.s.substring(stringMethodArg.length()) : this.s);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class remove_endingBI extends BuiltInForString {
        remove_endingBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                String strSubstring;
                remove_endingBI.this.checkMethodArgCount(list, 1);
                String stringMethodArg = remove_endingBI.this.getStringMethodArg(list, 0);
                if (this.s.endsWith(stringMethodArg)) {
                    String str = this.s;
                    strSubstring = str.substring(0, str.length() - stringMethodArg.length());
                } else {
                    strSubstring = this.s;
                }
                return new SimpleScalar(strSubstring);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class split_BI extends BuiltInForString {
        split_BI() {
        }

        class SplitMethod implements TemplateMethodModel {
            private String s;

            SplitMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                String[] strArrSplit;
                int size = list.size();
                split_BI.this.checkMethodArgCount(size, 1, 2);
                String str = (String) list.get(0);
                long flagString = size > 1 ? RegexpHelper.parseFlagString((String) list.get(1)) : 0L;
                if ((4294967296L & flagString) == 0) {
                    RegexpHelper.checkNonRegexpFlags("split", flagString);
                    strArrSplit = StringUtil.split(this.s, str, (flagString & RegexpHelper.RE_FLAG_CASE_INSENSITIVE) != 0);
                } else {
                    strArrSplit = RegexpHelper.getPattern(str, (int) flagString).split(this.s);
                }
                return ObjectWrapper.DEFAULT_WRAPPER.wrap(strArrSplit);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateModelException {
            return new SplitMethod(str);
        }
    }

    static class starts_withBI extends BuiltInForString {
        starts_withBI() {
        }

        private class BIMethod implements TemplateMethodModelEx {
            private String s;

            private BIMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                starts_withBI.this.checkMethodArgCount(list, 1);
                return this.s.startsWith(starts_withBI.this.getStringMethodArg(list, 0)) ? TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateException {
            return new BIMethod(str);
        }
    }

    static class substringBI extends BuiltInForString {
        substringBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(final String str, Environment environment) throws TemplateException {
            return new TemplateMethodModelEx() { // from class: org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsBasic.substringBI.1
                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModelEx, org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
                public Object exec(List list) throws TemplateModelException {
                    int size = list.size();
                    substringBI.this.checkMethodArgCount(size, 1, 2);
                    int iIntValue = substringBI.this.getNumberMethodArg(list, 0).intValue();
                    int length = str.length();
                    if (iIntValue < 0) {
                        throw newIndexLessThan0Exception(0, iIntValue);
                    }
                    if (iIntValue > length) {
                        throw newIndexGreaterThanLengthException(0, iIntValue, length);
                    }
                    if (size > 1) {
                        int iIntValue2 = substringBI.this.getNumberMethodArg(list, 1).intValue();
                        if (iIntValue2 < 0) {
                            throw newIndexLessThan0Exception(1, iIntValue2);
                        }
                        if (iIntValue2 > length) {
                            throw newIndexGreaterThanLengthException(1, iIntValue2, length);
                        }
                        if (iIntValue <= iIntValue2) {
                            return new SimpleScalar(str.substring(iIntValue, iIntValue2));
                        }
                        throw MessageUtil.newMethodArgsInvalidValueException(new StringBuffer("?").append(substringBI.this.key).toString(), new Object[]{"The begin index argument, ", new Integer(iIntValue), ", shouldn't be greater than the end index argument, ", new Integer(iIntValue2), "."});
                    }
                    return new SimpleScalar(str.substring(iIntValue));
                }

                private TemplateModelException newIndexGreaterThanLengthException(int i, int i2, int i3) throws TemplateModelException {
                    return MessageUtil.newMethodArgInvalidValueException(new StringBuffer("?").append(substringBI.this.key).toString(), i, new Object[]{"The index mustn't be greater than the length of the string, ", new Integer(i3), ", but it was ", new Integer(i2), "."});
                }

                private TemplateModelException newIndexLessThan0Exception(int i, int i2) throws TemplateModelException {
                    return MessageUtil.newMethodArgInvalidValueException(new StringBuffer("?").append(substringBI.this.key).toString(), i, new Object[]{"The index must be at least 0, but was ", new Integer(i2), "."});
                }
            };
        }
    }

    static class trimBI extends BuiltInForString {
        trimBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(str.trim());
        }
    }

    static class uncap_firstBI extends BuiltInForString {
        uncap_firstBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            int length = str.length();
            int i = 0;
            while (i < length && Character.isWhitespace(str.charAt(i))) {
                i++;
            }
            if (i < length) {
                StringBuffer stringBuffer = new StringBuffer(str);
                stringBuffer.setCharAt(i, Character.toLowerCase(str.charAt(i)));
                str = stringBuffer.toString();
            }
            return new SimpleScalar(str);
        }
    }

    static class upper_caseBI extends BuiltInForString {
        upper_caseBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            return new SimpleScalar(str.toUpperCase(environment.getLocale()));
        }
    }

    static class word_listBI extends BuiltInForString {
        word_listBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) {
            SimpleSequence simpleSequence = new SimpleSequence();
            StringTokenizer stringTokenizer = new StringTokenizer(str);
            while (stringTokenizer.hasMoreTokens()) {
                simpleSequence.add(stringTokenizer.nextToken());
            }
            return simpleSequence;
        }
    }

    private BuiltInsForStringsBasic() {
    }
}
