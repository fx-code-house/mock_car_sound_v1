package org.mapstruct.ap.shaded.freemarker.core;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.crashlytics.internal.settings.model.AppSettingsData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForDates;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForHashes;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForMultipleTypes;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForNodes;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForNumbers;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForSequences;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsBasic;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsEncoding;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsMisc;
import org.mapstruct.ap.shaded.freemarker.core.BuiltInsForStringsRegexp;
import org.mapstruct.ap.shaded.freemarker.core.ExistenceBuiltins;
import org.mapstruct.ap.shaded.freemarker.core.Expression;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateNumberModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
abstract class BuiltIn extends Expression implements Cloneable {
    static final HashMap builtins;
    protected String key;
    protected Expression target;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return 2;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    boolean isLiteral() {
        return false;
    }

    BuiltIn() {
    }

    static {
        HashMap map = new HashMap();
        builtins = map;
        map.put("abs", new BuiltInsForNumbers.absBI());
        map.put("ancestors", new BuiltInsForNodes.ancestorsBI());
        map.put("boolean", new BuiltInsForStringsMisc.booleanBI());
        map.put("byte", new BuiltInsForNumbers.byteBI());
        map.put("c", new BuiltInsForMultipleTypes.cBI());
        map.put("cap_first", new BuiltInsForStringsBasic.cap_firstBI());
        map.put("capitalize", new BuiltInsForStringsBasic.capitalizeBI());
        map.put("ceiling", new BuiltInsForNumbers.ceilingBI());
        map.put("children", new BuiltInsForNodes.childrenBI());
        map.put("chop_linebreak", new BuiltInsForStringsBasic.chop_linebreakBI());
        map.put("contains", new BuiltInsForStringsBasic.containsBI());
        map.put("date", new BuiltInsForMultipleTypes.dateBI(2));
        map.put("date_if_unknown", new BuiltInsForDates.dateType_if_unknownBI(2));
        map.put("datetime", new BuiltInsForMultipleTypes.dateBI(3));
        map.put("datetime_if_unknown", new BuiltInsForDates.dateType_if_unknownBI(3));
        map.put("default", new ExistenceBuiltins.defaultBI());
        map.put("double", new BuiltInsForNumbers.doubleBI());
        map.put("ends_with", new BuiltInsForStringsBasic.ends_withBI());
        map.put("ensure_ends_with", new BuiltInsForStringsBasic.ensure_ends_withBI());
        map.put("ensure_starts_with", new BuiltInsForStringsBasic.ensure_starts_withBI());
        map.put("eval", new BuiltInsForStringsMisc.evalBI());
        map.put("exists", new ExistenceBuiltins.existsBI());
        map.put("first", new BuiltInsForSequences.firstBI());
        map.put("float", new BuiltInsForNumbers.floatBI());
        map.put("floor", new BuiltInsForNumbers.floorBI());
        map.put("chunk", new BuiltInsForSequences.chunkBI());
        map.put("has_content", new ExistenceBuiltins.has_contentBI());
        map.put("html", new BuiltInsForStringsEncoding.htmlBI());
        map.put("if_exists", new ExistenceBuiltins.if_existsBI());
        map.put("index_of", new BuiltInsForStringsBasic.index_ofBI(false));
        map.put("int", new BuiltInsForNumbers.intBI());
        map.put("interpret", new Interpret());
        map.put("is_boolean", new BuiltInsForMultipleTypes.is_booleanBI());
        map.put("is_collection", new BuiltInsForMultipleTypes.is_collectionBI());
        BuiltInsForMultipleTypes.is_dateLikeBI is_datelikebi = new BuiltInsForMultipleTypes.is_dateLikeBI();
        map.put("is_date", is_datelikebi);
        map.put("is_date_like", is_datelikebi);
        map.put("is_date_only", new BuiltInsForMultipleTypes.is_dateOfTypeBI(2));
        map.put("is_unknown_date_like", new BuiltInsForMultipleTypes.is_dateOfTypeBI(0));
        map.put("is_datetime", new BuiltInsForMultipleTypes.is_dateOfTypeBI(3));
        map.put("is_directive", new BuiltInsForMultipleTypes.is_directiveBI());
        map.put("is_enumerable", new BuiltInsForMultipleTypes.is_enumerableBI());
        map.put("is_hash_ex", new BuiltInsForMultipleTypes.is_hash_exBI());
        map.put("is_hash", new BuiltInsForMultipleTypes.is_hashBI());
        map.put("is_infinite", new BuiltInsForNumbers.is_infiniteBI());
        map.put("is_indexable", new BuiltInsForMultipleTypes.is_indexableBI());
        map.put("is_macro", new BuiltInsForMultipleTypes.is_macroBI());
        map.put("is_method", new BuiltInsForMultipleTypes.is_methodBI());
        map.put("is_nan", new BuiltInsForNumbers.is_nanBI());
        map.put("is_node", new BuiltInsForMultipleTypes.is_nodeBI());
        map.put("is_number", new BuiltInsForMultipleTypes.is_numberBI());
        map.put("is_sequence", new BuiltInsForMultipleTypes.is_sequenceBI());
        map.put("is_string", new BuiltInsForMultipleTypes.is_stringBI());
        map.put("is_time", new BuiltInsForMultipleTypes.is_dateOfTypeBI(1));
        map.put("is_transform", new BuiltInsForMultipleTypes.is_transformBI());
        map.put("iso_utc", new BuiltInsForDates.iso_utc_or_local_BI(null, 6, true));
        map.put("iso_utc_fz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.TRUE, 6, true));
        map.put("iso_utc_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 6, true));
        map.put("iso_utc_ms", new BuiltInsForDates.iso_utc_or_local_BI(null, 7, true));
        map.put("iso_utc_ms_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 7, true));
        map.put("iso_utc_m", new BuiltInsForDates.iso_utc_or_local_BI(null, 5, true));
        map.put("iso_utc_m_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 5, true));
        map.put("iso_utc_h", new BuiltInsForDates.iso_utc_or_local_BI(null, 4, true));
        map.put("iso_utc_h_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 4, true));
        map.put("iso_local", new BuiltInsForDates.iso_utc_or_local_BI(null, 6, false));
        map.put("iso_local_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 6, false));
        map.put("iso_local_ms", new BuiltInsForDates.iso_utc_or_local_BI(null, 7, false));
        map.put("iso_local_ms_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 7, false));
        map.put("iso_local_m", new BuiltInsForDates.iso_utc_or_local_BI(null, 5, false));
        map.put("iso_local_m_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 5, false));
        map.put("iso_local_h", new BuiltInsForDates.iso_utc_or_local_BI(null, 4, false));
        map.put("iso_local_h_nz", new BuiltInsForDates.iso_utc_or_local_BI(Boolean.FALSE, 4, false));
        map.put("iso", new BuiltInsForDates.iso_BI(null, 6));
        map.put("iso_nz", new BuiltInsForDates.iso_BI(Boolean.FALSE, 6));
        map.put("iso_ms", new BuiltInsForDates.iso_BI(null, 7));
        map.put("iso_ms_nz", new BuiltInsForDates.iso_BI(Boolean.FALSE, 7));
        map.put("iso_m", new BuiltInsForDates.iso_BI(null, 5));
        map.put("iso_m_nz", new BuiltInsForDates.iso_BI(Boolean.FALSE, 5));
        map.put("iso_h", new BuiltInsForDates.iso_BI(null, 4));
        map.put("iso_h_nz", new BuiltInsForDates.iso_BI(Boolean.FALSE, 4));
        map.put("j_string", new BuiltInsForStringsEncoding.j_stringBI());
        map.put("join", new BuiltInsForSequences.joinBI());
        map.put("js_string", new BuiltInsForStringsEncoding.js_stringBI());
        map.put("json_string", new BuiltInsForStringsEncoding.json_stringBI());
        map.put("keep_after", new BuiltInsForStringsBasic.keep_afterBI());
        map.put("keep_before", new BuiltInsForStringsBasic.keep_beforeBI());
        map.put("keys", new BuiltInsForHashes.keysBI());
        map.put("last_index_of", new BuiltInsForStringsBasic.index_ofBI(true));
        map.put("last", new BuiltInsForSequences.lastBI());
        map.put("left_pad", new BuiltInsForStringsBasic.padBI(true));
        map.put(SessionDescription.ATTR_LENGTH, new BuiltInsForStringsBasic.lengthBI());
        map.put("long", new BuiltInsForNumbers.longBI());
        map.put("lower_case", new BuiltInsForStringsBasic.lower_caseBI());
        map.put("namespace", new BuiltInsForMultipleTypes.namespaceBI());
        map.put(AppSettingsData.STATUS_NEW, new NewBI());
        map.put("node_name", new BuiltInsForNodes.node_nameBI());
        map.put("node_namespace", new BuiltInsForNodes.node_namespaceBI());
        map.put("node_type", new BuiltInsForNodes.node_typeBI());
        map.put("number", new BuiltInsForStringsMisc.numberBI());
        map.put("number_to_date", new BuiltInsForNumbers.number_to_dateBI(2));
        map.put("number_to_time", new BuiltInsForNumbers.number_to_dateBI(1));
        map.put("number_to_datetime", new BuiltInsForNumbers.number_to_dateBI(3));
        map.put("parent", new BuiltInsForNodes.parentBI());
        map.put("reverse", new BuiltInsForSequences.reverseBI());
        map.put("right_pad", new BuiltInsForStringsBasic.padBI(false));
        map.put("root", new BuiltInsForNodes.rootBI());
        map.put("round", new BuiltInsForNumbers.roundBI());
        map.put("remove_ending", new BuiltInsForStringsBasic.remove_endingBI());
        map.put("remove_beginning", new BuiltInsForStringsBasic.remove_beginningBI());
        map.put("rtf", new BuiltInsForStringsEncoding.rtfBI());
        map.put("seq_contains", new BuiltInsForSequences.seq_containsBI());
        map.put("seq_index_of", new BuiltInsForSequences.seq_index_ofBI(1));
        map.put("seq_last_index_of", new BuiltInsForSequences.seq_index_ofBI(-1));
        map.put("short", new BuiltInsForNumbers.shortBI());
        map.put("size", new BuiltInsForMultipleTypes.sizeBI());
        map.put("sort_by", new BuiltInsForSequences.sort_byBI());
        map.put("sort", new BuiltInsForSequences.sortBI());
        map.put("split", new BuiltInsForStringsBasic.split_BI());
        map.put("starts_with", new BuiltInsForStringsBasic.starts_withBI());
        map.put("string", new BuiltInsForMultipleTypes.stringBI());
        map.put("substring", new BuiltInsForStringsBasic.substringBI());
        map.put("time", new BuiltInsForMultipleTypes.dateBI(1));
        map.put("time_if_unknown", new BuiltInsForDates.dateType_if_unknownBI(1));
        map.put("trim", new BuiltInsForStringsBasic.trimBI());
        map.put("uncap_first", new BuiltInsForStringsBasic.uncap_firstBI());
        map.put("upper_case", new BuiltInsForStringsBasic.upper_caseBI());
        map.put(ImagesContract.URL, new BuiltInsForStringsEncoding.urlBI());
        map.put("url_path", new BuiltInsForStringsEncoding.urlPathBI());
        map.put("values", new BuiltInsForHashes.valuesBI());
        map.put("web_safe", map.get("html"));
        map.put("word_list", new BuiltInsForStringsBasic.word_listBI());
        map.put("xhtml", new BuiltInsForStringsEncoding.xhtmlBI());
        map.put("xml", new BuiltInsForStringsEncoding.xmlBI());
        map.put("matches", new BuiltInsForStringsRegexp.matchesBI());
        map.put("groups", new BuiltInsForStringsRegexp.groupsBI());
        map.put("replace", new BuiltInsForStringsRegexp.replace_reBI());
    }

    static BuiltIn newBuiltIn(int i, Expression expression, String str) throws ParseException {
        HashMap map = builtins;
        BuiltIn builtIn = (BuiltIn) map.get(str);
        if (builtIn == null) {
            StringBuffer stringBuffer = new StringBuffer(new StringBuffer("Unknown built-in: ").append(StringUtil.jQuote(str)).append(". Help (latest version): http://freemarker.org/docs/ref_builtins.html; you're using FreeMarker ").append(Configuration.getVersion()).append(".\nThe alphabetical list of built-ins:").toString());
            ArrayList arrayList = new ArrayList(map.keySet().size());
            arrayList.addAll(map.keySet());
            Collections.sort(arrayList);
            Iterator it = arrayList.iterator();
            char c = 0;
            while (it.hasNext()) {
                String str2 = (String) it.next();
                char cCharAt = str2.charAt(0);
                if (cCharAt != c) {
                    stringBuffer.append('\n');
                    c = cCharAt;
                }
                stringBuffer.append(str2);
                if (it.hasNext()) {
                    stringBuffer.append(", ");
                }
            }
            throw new ParseException(stringBuffer.toString(), expression);
        }
        while (builtIn instanceof ICIChainMember) {
            ICIChainMember iCIChainMember = (ICIChainMember) builtIn;
            if (i < iCIChainMember.getMinimumICIVersion()) {
                builtIn = (BuiltIn) iCIChainMember.getPreviousICIChainMember();
            }
        }
        try {
            BuiltIn builtIn2 = (BuiltIn) builtIn.clone();
            builtIn2.target = expression;
            builtIn2.key = str;
            return builtIn2;
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    public String getCanonicalForm() {
        return new StringBuffer().append(this.target.getCanonicalForm()).append(getNodeTypeSymbol()).toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return new StringBuffer("?").append(this.key).toString();
    }

    protected final void checkMethodArgCount(List list, int i) throws TemplateModelException {
        checkMethodArgCount(list.size(), i);
    }

    protected final void checkMethodArgCount(int i, int i2) throws TemplateModelException {
        if (i != i2) {
            throw MessageUtil.newArgCntError(new StringBuffer("?").append(this.key).toString(), i, i2);
        }
    }

    protected final void checkMethodArgCount(List list, int i, int i2) throws TemplateModelException {
        checkMethodArgCount(list.size(), i, i2);
    }

    protected final void checkMethodArgCount(int i, int i2, int i3) throws TemplateModelException {
        if (i < i2 || i > i3) {
            throw MessageUtil.newArgCntError(new StringBuffer("?").append(this.key).toString(), i, i2, i3);
        }
    }

    protected final String getOptStringMethodArg(List list, int i) throws TemplateModelException {
        if (list.size() > i) {
            return getStringMethodArg(list, i);
        }
        return null;
    }

    protected final String getStringMethodArg(List list, int i) throws TemplateModelException {
        TemplateModel templateModel = (TemplateModel) list.get(i);
        if (!(templateModel instanceof TemplateScalarModel)) {
            throw MessageUtil.newMethodArgMustBeStringException(new StringBuffer("?").append(this.key).toString(), i, templateModel);
        }
        return EvalUtil.modelToString((TemplateScalarModel) templateModel, null, null);
    }

    protected final Number getNumberMethodArg(List list, int i) throws TemplateModelException {
        TemplateModel templateModel = (TemplateModel) list.get(i);
        if (!(templateModel instanceof TemplateNumberModel)) {
            throw MessageUtil.newMethodArgMustBeNumberException(new StringBuffer("?").append(this.key).toString(), i, templateModel);
        }
        return EvalUtil.modelToNumber((TemplateNumberModel) templateModel, null);
    }

    protected final TemplateModelException newMethodArgInvalidValueException(int i, Object[] objArr) {
        return MessageUtil.newMethodArgInvalidValueException(new StringBuffer("?").append(this.key).toString(), i, objArr);
    }

    protected final TemplateModelException newMethodArgsInvalidValueException(Object[] objArr) {
        return MessageUtil.newMethodArgsInvalidValueException(new StringBuffer("?").append(this.key).toString(), objArr);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.Expression
    protected final Expression deepCloneWithIdentifierReplaced_inner(String str, Expression expression, Expression.ReplacemenetState replacemenetState) {
        try {
            BuiltIn builtIn = (BuiltIn) clone();
            builtIn.target = this.target.deepCloneWithIdentifierReplaced(str, expression, replacemenetState);
            return builtIn;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(new StringBuffer("Internal error: ").append(e).toString());
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.target;
        }
        if (i == 1) {
            return this.key;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.LEFT_HAND_OPERAND;
        }
        if (i == 1) {
            return ParameterRole.RIGHT_HAND_OPERAND;
        }
        throw new IndexOutOfBoundsException();
    }
}
