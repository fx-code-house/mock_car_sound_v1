package org.mapstruct.ap.shaded.freemarker.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mapstruct.ap.shaded.freemarker.template.SimpleScalar;
import org.mapstruct.ap.shaded.freemarker.template.SimpleSequence;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
class BuiltInsForStringsRegexp {
    static /* synthetic */ Class class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel;
    static /* synthetic */ Class class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel$MatchWithGroups;

    static class groupsBI extends BuiltIn {
        groupsBI() {
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
        TemplateModel _eval(Environment environment) throws Throwable {
            Class clsClass$;
            Class clsClass$2;
            TemplateModel templateModelEval = this.target.eval(environment);
            assertNonNull(templateModelEval, environment);
            if (templateModelEval instanceof RegexMatchModel) {
                return ((RegexMatchModel) templateModelEval).getGroups();
            }
            if (templateModelEval instanceof RegexMatchModel.MatchWithGroups) {
                return ((RegexMatchModel.MatchWithGroups) templateModelEval).groupsSeq;
            }
            Expression expression = this.target;
            Class[] clsArr = new Class[2];
            if (BuiltInsForStringsRegexp.class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel == null) {
                clsClass$ = BuiltInsForStringsRegexp.class$("org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsRegexp$RegexMatchModel");
                BuiltInsForStringsRegexp.class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel = clsClass$;
            } else {
                clsClass$ = BuiltInsForStringsRegexp.class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel;
            }
            clsArr[0] = clsClass$;
            if (BuiltInsForStringsRegexp.class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel$MatchWithGroups == null) {
                clsClass$2 = BuiltInsForStringsRegexp.class$("org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsRegexp$RegexMatchModel$MatchWithGroups");
                BuiltInsForStringsRegexp.class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel$MatchWithGroups = clsClass$2;
            } else {
                clsClass$2 = BuiltInsForStringsRegexp.class$freemarker$core$BuiltInsForStringsRegexp$RegexMatchModel$MatchWithGroups;
            }
            clsArr[1] = clsClass$2;
            throw new UnexpectedTypeException(expression, templateModelEval, "regular expression matcher", clsArr, environment);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static class matchesBI extends BuiltInForString {
        matchesBI() {
        }

        class MatcherBuilder implements TemplateMethodModel {
            String matchString;

            MatcherBuilder(String str) throws TemplateModelException {
                this.matchString = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                int size = list.size();
                matchesBI.this.checkMethodArgCount(size, 1, 2);
                String str = (String) list.get(0);
                long flagString = size > 1 ? RegexpHelper.parseFlagString((String) list.get(1)) : 0L;
                if ((8589934592L & flagString) != 0) {
                    RegexpHelper.logFlagWarning(new StringBuffer("?").append(matchesBI.this.key).append(" doesn't support the \"f\" flag.").toString());
                }
                return new RegexMatchModel(RegexpHelper.getPattern(str, (int) flagString), this.matchString);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateModelException {
            return new MatcherBuilder(str);
        }
    }

    static class replace_reBI extends BuiltInForString {
        replace_reBI() {
        }

        class ReplaceMethod implements TemplateMethodModel {
            private String s;

            ReplaceMethod(String str) {
                this.s = str;
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateMethodModel
            public Object exec(List list) throws TemplateModelException {
                String strReplaceAll;
                int size = list.size();
                replace_reBI.this.checkMethodArgCount(size, 2, 3);
                String str = (String) list.get(0);
                String str2 = (String) list.get(1);
                long flagString = size > 2 ? RegexpHelper.parseFlagString((String) list.get(2)) : 0L;
                if ((4294967296L & flagString) == 0) {
                    RegexpHelper.checkNonRegexpFlags("replace", flagString);
                    strReplaceAll = StringUtil.replace(this.s, str, str2, (RegexpHelper.RE_FLAG_CASE_INSENSITIVE & flagString) != 0, (flagString & 8589934592L) != 0);
                } else {
                    Matcher matcher = RegexpHelper.getPattern(str, (int) flagString).matcher(this.s);
                    if ((flagString & 8589934592L) != 0) {
                        strReplaceAll = matcher.replaceFirst(str2);
                    } else {
                        strReplaceAll = matcher.replaceAll(str2);
                    }
                }
                return new SimpleScalar(strReplaceAll);
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.BuiltInForString
        TemplateModel calculateResult(String str, Environment environment) throws TemplateModelException {
            return new ReplaceMethod(str);
        }
    }

    static class RegexMatchModel implements TemplateBooleanModel, TemplateCollectionModel, TemplateSequenceModel {
        private TemplateSequenceModel entireInputMatchGroups;
        private Boolean entireInputMatched;
        private Matcher firedEntireInputMatcher;
        final String input;
        private ArrayList matchingInputParts;
        final Pattern pattern;

        static class MatchWithGroups implements TemplateScalarModel {
            final SimpleSequence groupsSeq;
            final String matchedInputPart;

            MatchWithGroups(String str, Matcher matcher) {
                this.matchedInputPart = str.substring(matcher.start(), matcher.end());
                int iGroupCount = matcher.groupCount() + 1;
                this.groupsSeq = new SimpleSequence(iGroupCount);
                for (int i = 0; i < iGroupCount; i++) {
                    this.groupsSeq.add(matcher.group(i));
                }
            }

            @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel
            public String getAsString() {
                return this.matchedInputPart;
            }
        }

        RegexMatchModel(Pattern pattern, String str) {
            this.pattern = pattern;
            this.input = str;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public TemplateModel get(int i) throws TemplateModelException {
            ArrayList matchingInputPartsAndStoreResults = this.matchingInputParts;
            if (matchingInputPartsAndStoreResults == null) {
                matchingInputPartsAndStoreResults = getMatchingInputPartsAndStoreResults();
            }
            return (TemplateModel) matchingInputPartsAndStoreResults.get(i);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel
        public boolean getAsBoolean() {
            Boolean bool = this.entireInputMatched;
            return bool != null ? bool.booleanValue() : isEntrieInputMatchesAndStoreResults();
        }

        TemplateModel getGroups() {
            TemplateSequenceModel templateSequenceModel = this.entireInputMatchGroups;
            if (templateSequenceModel != null) {
                return templateSequenceModel;
            }
            final Matcher matcher = this.firedEntireInputMatcher;
            if (matcher == null) {
                isEntrieInputMatchesAndStoreResults();
                matcher = this.firedEntireInputMatcher;
            }
            TemplateSequenceModel templateSequenceModel2 = new TemplateSequenceModel() { // from class: org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsRegexp.RegexMatchModel.1
                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
                public TemplateModel get(int i) throws TemplateModelException {
                    try {
                        return new SimpleScalar(matcher.group(i));
                    } catch (Exception e) {
                        throw new _TemplateModelException(e, "Failed to read match group");
                    }
                }

                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
                public int size() throws TemplateModelException {
                    try {
                        return matcher.groupCount() + 1;
                    } catch (Exception e) {
                        throw new _TemplateModelException(e, "Failed to get match group count");
                    }
                }
            };
            this.entireInputMatchGroups = templateSequenceModel2;
            return templateSequenceModel2;
        }

        private ArrayList getMatchingInputPartsAndStoreResults() throws TemplateModelException {
            ArrayList arrayList = new ArrayList();
            Matcher matcher = this.pattern.matcher(this.input);
            while (matcher.find()) {
                arrayList.add(new MatchWithGroups(this.input, matcher));
            }
            this.matchingInputParts = arrayList;
            return arrayList;
        }

        private boolean isEntrieInputMatchesAndStoreResults() {
            Matcher matcher = this.pattern.matcher(this.input);
            boolean zMatches = matcher.matches();
            this.firedEntireInputMatcher = matcher;
            this.entireInputMatched = Boolean.valueOf(zMatches);
            return zMatches;
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateCollectionModel
        public TemplateModelIterator iterator() {
            final ArrayList arrayList = this.matchingInputParts;
            if (arrayList == null) {
                return new TemplateModelIterator(this.pattern.matcher(this.input)) { // from class: org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsRegexp.RegexMatchModel.2
                    boolean hasFindInfo;
                    private int nextIdx = 0;
                    private final /* synthetic */ Matcher val$matcher;

                    {
                        this.val$matcher = matcher;
                        this.hasFindInfo = matcher.find();
                    }

                    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
                    public boolean hasNext() {
                        ArrayList arrayList2 = RegexMatchModel.this.matchingInputParts;
                        if (arrayList2 == null) {
                            return this.hasFindInfo;
                        }
                        return this.nextIdx < arrayList2.size();
                    }

                    @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
                    public TemplateModel next() throws TemplateModelException {
                        ArrayList arrayList2 = RegexMatchModel.this.matchingInputParts;
                        if (arrayList2 == null) {
                            if (!this.hasFindInfo) {
                                throw new _TemplateModelException("There were no more matches");
                            }
                            MatchWithGroups matchWithGroups = new MatchWithGroups(RegexMatchModel.this.input, this.val$matcher);
                            this.nextIdx++;
                            this.hasFindInfo = this.val$matcher.find();
                            return matchWithGroups;
                        }
                        try {
                            int i = this.nextIdx;
                            this.nextIdx = i + 1;
                            return (TemplateModel) arrayList2.get(i);
                        } catch (IndexOutOfBoundsException e) {
                            throw new _TemplateModelException(e, "There were no more matches");
                        }
                    }
                };
            }
            return new TemplateModelIterator() { // from class: org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsRegexp.RegexMatchModel.3
                private int nextIdx = 0;

                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
                public boolean hasNext() {
                    return this.nextIdx < arrayList.size();
                }

                @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator
                public TemplateModel next() throws TemplateModelException {
                    try {
                        ArrayList arrayList2 = arrayList;
                        int i = this.nextIdx;
                        this.nextIdx = i + 1;
                        return (TemplateModel) arrayList2.get(i);
                    } catch (IndexOutOfBoundsException e) {
                        throw new _TemplateModelException(e, "There were no more matches");
                    }
                }
            };
        }

        @Override // org.mapstruct.ap.shaded.freemarker.template.TemplateSequenceModel
        public int size() throws TemplateModelException {
            ArrayList matchingInputPartsAndStoreResults = this.matchingInputParts;
            if (matchingInputPartsAndStoreResults == null) {
                matchingInputPartsAndStoreResults = getMatchingInputPartsAndStoreResults();
            }
            return matchingInputPartsAndStoreResults.size();
        }
    }

    private BuiltInsForStringsRegexp() {
    }
}
