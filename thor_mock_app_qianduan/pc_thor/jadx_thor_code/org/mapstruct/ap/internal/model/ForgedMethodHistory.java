package org.mapstruct.ap.internal.model;

import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class ForgedMethodHistory {
    private String elementType;
    private final ForgedMethodHistory prevHistory;
    private final String sourceElement;
    private final Type sourceType;
    private final String targetPropertyName;
    private final Type targetType;
    private final boolean usePropertyNames;

    public ForgedMethodHistory(ForgedMethodHistory forgedMethodHistory, String str, String str2, Type type, Type type2, boolean z, String str3) {
        this.prevHistory = forgedMethodHistory;
        this.sourceElement = str;
        this.targetPropertyName = str2;
        this.sourceType = type;
        this.targetType = type2;
        this.usePropertyNames = z;
        this.elementType = str3;
    }

    public Type getTargetType() {
        return this.targetType;
    }

    public Type getSourceType() {
        return this.sourceType;
    }

    public String createSourcePropertyErrorMessage() {
        return conditionallyCapitalizedElementType() + " \"" + getSourceType().describe() + StringUtils.SPACE + stripBrackets(getDottedSourceElement()) + "\"";
    }

    private String conditionallyCapitalizedElementType() {
        if ("property".equals(this.elementType)) {
            return this.elementType;
        }
        return Strings.capitalize(this.elementType);
    }

    public String createTargetPropertyName() {
        return stripBrackets(getDottedTargetPropertyName());
    }

    private String getDottedSourceElement() {
        ForgedMethodHistory forgedMethodHistory = this.prevHistory;
        if (forgedMethodHistory == null) {
            return this.sourceElement;
        }
        if (this.usePropertyNames) {
            return getCorrectDottedPath(forgedMethodHistory.getDottedSourceElement(), this.sourceElement);
        }
        return forgedMethodHistory.getDottedSourceElement();
    }

    private String getDottedTargetPropertyName() {
        ForgedMethodHistory forgedMethodHistory = this.prevHistory;
        if (forgedMethodHistory == null) {
            return this.targetPropertyName;
        }
        if (this.usePropertyNames) {
            return getCorrectDottedPath(forgedMethodHistory.getDottedTargetPropertyName(), this.targetPropertyName);
        }
        return forgedMethodHistory.getDottedTargetPropertyName();
    }

    private String getCorrectDottedPath(String str, String str2) {
        if ("map key".equals(this.elementType)) {
            return stripBrackets(str) + "{:key}";
        }
        if ("map value".equals(this.elementType)) {
            return stripBrackets(str) + "{:value}";
        }
        return str + "." + str2;
    }

    private String stripBrackets(String str) {
        return (str.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI) || str.endsWith("{}")) ? str.substring(0, str.length() - 2) : str;
    }
}
