package org.mapstruct.ap.internal.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class Nouns {
    private static final List<ReplaceRule> SINGULAR_DALI_RULES;
    private static final List<ReplaceRule> SINGULAR_RULES;

    private Nouns() {
    }

    static {
        String str = "$1";
        String str2 = "$1ex";
        String str3 = "$1us";
        String str4 = "$1is";
        String str5 = "$1y";
        String str6 = "$1sis";
        SINGULAR_RULES = Arrays.asList(new ReplaceRule("(equipment|information|rice|money|species|series|fish|sheep)$", str), new ReplaceRule("(f)eet$", "$1oot"), new ReplaceRule("(t)eeth$", "$1ooth"), new ReplaceRule("(g)eese$", "$1oose"), new ReplaceRule("(s)tadiums$", "$1tadium"), new ReplaceRule("(m)oves$", "$1ove"), new ReplaceRule("(s)exes$", str2), new ReplaceRule("(c)hildren$", "$1hild"), new ReplaceRule("(m)en$", "$1an"), new ReplaceRule("(p)eople$", "$1erson"), new ReplaceRule("(quiz)zes$", str), new ReplaceRule("(matr)ices$", "$1ix"), new ReplaceRule("(vert|ind)ices$", str2), new ReplaceRule("^(ox)en", str), new ReplaceRule("(alias|status)$", str), new ReplaceRule("(alias|status)es$", str), new ReplaceRule("(octop|vir)us$", str3), new ReplaceRule("(octop|vir)i$", str3), new ReplaceRule("(cris|ax|test)es$", str4), new ReplaceRule("(cris|ax|test)is$", str4), new ReplaceRule("(shoe)s$", str), new ReplaceRule("(o)es$", str), new ReplaceRule("(bus)es$", str), new ReplaceRule("([m|l])ice$", "$1ouse"), new ReplaceRule("(x|ch|ss|sh)es$", str), new ReplaceRule("(m)ovies$", "$1ovie"), new ReplaceRule("(s)eries$", "$1eries"), new ReplaceRule("([^aeiouy]|qu)ies$", str5), new ReplaceRule("([lr])ves$", "$1f"), new ReplaceRule("(tive)s$", str), new ReplaceRule("(hive)s$", str), new ReplaceRule("([^f])ves$", "$1fe"), new ReplaceRule("(^analy)sis$", str6), new ReplaceRule("(^analy)ses$", str6), new ReplaceRule("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis"), new ReplaceRule("([ti])a$", "$1um"), new ReplaceRule("(n)ews$", "$1ews"), new ReplaceRule("(s|si|u)s$", "$1s"), new ReplaceRule("s$", ""));
        SINGULAR_DALI_RULES = Arrays.asList(new ReplaceRule("(us|ss)$", str), new ReplaceRule("(ch|s)es$", str), new ReplaceRule("([^aeiouy])ies$", str5));
    }

    public static String singularize(String str) {
        Iterator<ReplaceRule> it = SINGULAR_RULES.iterator();
        while (it.hasNext()) {
            String strApply = it.next().apply(str);
            if (strApply != null) {
                return strApply;
            }
        }
        Iterator<ReplaceRule> it2 = SINGULAR_DALI_RULES.iterator();
        while (it2.hasNext()) {
            String strApply2 = it2.next().apply(str);
            if (strApply2 != null) {
                return strApply2;
            }
        }
        return str;
    }

    private static final class ReplaceRule {
        private final Pattern pattern;
        private final String regexp;
        private final String replacement;

        private ReplaceRule(String str, String str2) {
            this.regexp = str;
            this.replacement = str2;
            this.pattern = Pattern.compile(str, 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String apply(String str) {
            Matcher matcher = this.pattern.matcher(str);
            if (matcher.find()) {
                return matcher.replaceAll(this.replacement);
            }
            return null;
        }

        public String toString() {
            return "'" + this.regexp + "' -> '" + this.replacement;
        }
    }
}
