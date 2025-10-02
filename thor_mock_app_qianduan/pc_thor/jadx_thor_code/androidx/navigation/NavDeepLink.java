package androidx.navigation;

import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class NavDeepLink {
    private static final Pattern SCHEME_PATTERN = Pattern.compile("^[a-zA-Z]+[+\\w\\-.]*:");
    private final String mAction;
    private final ArrayList<String> mArguments;
    private boolean mExactDeepLink;
    private boolean mIsParameterizedQuery;
    private final String mMimeType;
    private Pattern mMimeTypePattern;
    private final Map<String, ParamQuery> mParamArgMap;
    private Pattern mPattern;
    private final String mUri;

    NavDeepLink(String str, String str2, String str3) {
        this.mArguments = new ArrayList<>();
        this.mParamArgMap = new HashMap();
        this.mPattern = null;
        int i = 0;
        this.mExactDeepLink = false;
        this.mIsParameterizedQuery = false;
        this.mMimeTypePattern = null;
        this.mUri = str;
        this.mAction = str2;
        this.mMimeType = str3;
        if (str != null) {
            Uri uri = Uri.parse(str);
            this.mIsParameterizedQuery = uri.getQuery() != null;
            StringBuilder sb = new StringBuilder("^");
            if (!SCHEME_PATTERN.matcher(str).find()) {
                sb.append("http[s]?://");
            }
            Pattern patternCompile = Pattern.compile("\\{(.+?)\\}");
            if (this.mIsParameterizedQuery) {
                Matcher matcher = Pattern.compile("(\\?)").matcher(str);
                if (matcher.find()) {
                    buildPathRegex(str.substring(0, matcher.start()), sb, patternCompile);
                }
                this.mExactDeepLink = false;
                for (String str4 : uri.getQueryParameterNames()) {
                    StringBuilder sb2 = new StringBuilder();
                    String queryParameter = uri.getQueryParameter(str4);
                    Matcher matcher2 = patternCompile.matcher(queryParameter);
                    ParamQuery paramQuery = new ParamQuery();
                    int iEnd = i;
                    while (matcher2.find()) {
                        paramQuery.addArgumentName(matcher2.group(1));
                        sb2.append(Pattern.quote(queryParameter.substring(iEnd, matcher2.start())));
                        sb2.append("(.+?)?");
                        iEnd = matcher2.end();
                    }
                    if (iEnd < queryParameter.length()) {
                        sb2.append(Pattern.quote(queryParameter.substring(iEnd)));
                    }
                    paramQuery.setParamRegex(sb2.toString().replace(".*", "\\E.*\\Q"));
                    this.mParamArgMap.put(str4, paramQuery);
                    i = 0;
                }
            } else {
                this.mExactDeepLink = buildPathRegex(str, sb, patternCompile);
            }
            this.mPattern = Pattern.compile(sb.toString().replace(".*", "\\E.*\\Q"));
        }
        if (str3 != null) {
            if (!Pattern.compile("^[\\s\\S]+/[\\s\\S]+$").matcher(str3).matches()) {
                throw new IllegalArgumentException("The given mimeType " + str3 + " does not match to required \"type/subtype\" format");
            }
            MimeType mimeType = new MimeType(str3);
            this.mMimeTypePattern = Pattern.compile(("^(" + mimeType.mType + "|[*]+)/(" + mimeType.mSubType + "|[*]+)$").replace("*|[*]", "[\\s\\S]"));
        }
    }

    NavDeepLink(String str) {
        this(str, null, null);
    }

    private boolean buildPathRegex(String str, StringBuilder sb, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        boolean z = !str.contains(".*");
        int iEnd = 0;
        while (matcher.find()) {
            this.mArguments.add(matcher.group(1));
            sb.append(Pattern.quote(str.substring(iEnd, matcher.start())));
            sb.append("(.+?)");
            iEnd = matcher.end();
            z = false;
        }
        if (iEnd < str.length()) {
            sb.append(Pattern.quote(str.substring(iEnd)));
        }
        sb.append("($|(\\?(.)*))");
        return z;
    }

    boolean matches(Uri uri) {
        return matches(new NavDeepLinkRequest(uri, null, null));
    }

    boolean matches(NavDeepLinkRequest navDeepLinkRequest) {
        if (matchUri(navDeepLinkRequest.getUri()) && matchAction(navDeepLinkRequest.getAction())) {
            return matchMimeType(navDeepLinkRequest.getMimeType());
        }
        return false;
    }

    private boolean matchUri(Uri uri) {
        boolean z = uri == null;
        Pattern pattern = this.mPattern;
        if (z == (pattern != null)) {
            return false;
        }
        return uri == null || pattern.matcher(uri.toString()).matches();
    }

    private boolean matchAction(String str) {
        boolean z = str == null;
        String str2 = this.mAction;
        if (z == (str2 != null)) {
            return false;
        }
        return str == null || str2.equals(str);
    }

    private boolean matchMimeType(String str) {
        if ((str == null) == (this.mMimeType != null)) {
            return false;
        }
        return str == null || this.mMimeTypePattern.matcher(str).matches();
    }

    boolean isExactDeepLink() {
        return this.mExactDeepLink;
    }

    public String getUriPattern() {
        return this.mUri;
    }

    public String getAction() {
        return this.mAction;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    int getMimeTypeMatchRating(String str) {
        if (this.mMimeType == null || !this.mMimeTypePattern.matcher(str).matches()) {
            return -1;
        }
        return new MimeType(this.mMimeType).compareTo(new MimeType(str));
    }

    Bundle getMatchingArguments(Uri uri, Map<String, NavArgument> map) {
        Matcher matcher;
        Matcher matcher2 = this.mPattern.matcher(uri.toString());
        if (!matcher2.matches()) {
            return null;
        }
        Bundle bundle = new Bundle();
        int size = this.mArguments.size();
        int i = 0;
        while (i < size) {
            String str = this.mArguments.get(i);
            i++;
            if (parseArgument(bundle, str, Uri.decode(matcher2.group(i)), map.get(str))) {
                return null;
            }
        }
        if (this.mIsParameterizedQuery) {
            for (String str2 : this.mParamArgMap.keySet()) {
                ParamQuery paramQuery = this.mParamArgMap.get(str2);
                String queryParameter = uri.getQueryParameter(str2);
                if (queryParameter != null) {
                    matcher = Pattern.compile(paramQuery.getParamRegex()).matcher(queryParameter);
                    if (!matcher.matches()) {
                        return null;
                    }
                } else {
                    matcher = null;
                }
                for (int i2 = 0; i2 < paramQuery.size(); i2++) {
                    String strDecode = matcher != null ? Uri.decode(matcher.group(i2 + 1)) : null;
                    String argumentName = paramQuery.getArgumentName(i2);
                    NavArgument navArgument = map.get(argumentName);
                    if (strDecode != null && !strDecode.replaceAll("[{}]", "").equals(argumentName) && parseArgument(bundle, argumentName, strDecode, navArgument)) {
                        return null;
                    }
                }
            }
        }
        return bundle;
    }

    private boolean parseArgument(Bundle bundle, String str, String str2, NavArgument navArgument) {
        if (navArgument != null) {
            try {
                navArgument.getType().parseAndPut(bundle, str, str2);
                return false;
            } catch (IllegalArgumentException unused) {
                return true;
            }
        }
        bundle.putString(str, str2);
        return false;
    }

    private static class ParamQuery {
        private ArrayList<String> mArguments = new ArrayList<>();
        private String mParamRegex;

        ParamQuery() {
        }

        void setParamRegex(String str) {
            this.mParamRegex = str;
        }

        String getParamRegex() {
            return this.mParamRegex;
        }

        void addArgumentName(String str) {
            this.mArguments.add(str);
        }

        String getArgumentName(int i) {
            return this.mArguments.get(i);
        }

        public int size() {
            return this.mArguments.size();
        }
    }

    private static class MimeType implements Comparable<MimeType> {
        String mSubType;
        String mType;

        MimeType(String str) {
            String[] strArrSplit = str.split("/", -1);
            this.mType = strArrSplit[0];
            this.mSubType = strArrSplit[1];
        }

        @Override // java.lang.Comparable
        public int compareTo(MimeType mimeType) {
            int i = this.mType.equals(mimeType.mType) ? 2 : 0;
            return this.mSubType.equals(mimeType.mSubType) ? i + 1 : i;
        }
    }

    public static final class Builder {
        private String mAction;
        private String mMimeType;
        private String mUriPattern;

        Builder() {
        }

        public static Builder fromUriPattern(String str) {
            Builder builder = new Builder();
            builder.setUriPattern(str);
            return builder;
        }

        public static Builder fromAction(String str) {
            if (str.isEmpty()) {
                throw new IllegalArgumentException("The NavDeepLink cannot have an empty action.");
            }
            Builder builder = new Builder();
            builder.setAction(str);
            return builder;
        }

        public static Builder fromMimeType(String str) {
            Builder builder = new Builder();
            builder.setMimeType(str);
            return builder;
        }

        public Builder setUriPattern(String str) {
            this.mUriPattern = str;
            return this;
        }

        public Builder setAction(String str) {
            if (str.isEmpty()) {
                throw new IllegalArgumentException("The NavDeepLink cannot have an empty action.");
            }
            this.mAction = str;
            return this;
        }

        public Builder setMimeType(String str) {
            this.mMimeType = str;
            return this;
        }

        public NavDeepLink build() {
            return new NavDeepLink(this.mUriPattern, this.mAction, this.mMimeType);
        }
    }
}
