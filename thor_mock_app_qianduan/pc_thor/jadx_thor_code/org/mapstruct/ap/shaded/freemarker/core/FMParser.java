package org.mapstruct.ap.shaded.freemarker.core;

import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import org.mapstruct.ap.shaded.freemarker.template.Configuration;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.mapstruct.ap.shaded.freemarker.template.TemplateBooleanModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateHashModelEx;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;
import org.mapstruct.ap.shaded.freemarker.template.TemplateScalarModel;
import org.mapstruct.ap.shaded.freemarker.template._TemplateAPI;
import org.mapstruct.ap.shaded.freemarker.template.utility.DeepUnwrap;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

/* loaded from: classes3.dex */
public class FMParser implements FMParserConstants {
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private static int[] jj_la1_2;
    private static int[] jj_la1_3;
    private static int[] jj_la1_4;
    private LinkedList escapes;
    private boolean inFunction;
    private boolean inMacro;
    private int incompatibleImprovements;
    private final JJCalls[] jj_2_rtns;
    private int jj_endpos;
    private Vector jj_expentries;
    private int[] jj_expentry;
    private int jj_gc;
    private int jj_gen;
    SimpleCharStream jj_input_stream;
    private int jj_kind;
    private int jj_la;
    private final int[] jj_la1;
    private Token jj_lastpos;
    private int[] jj_lasttokens;
    private final LookaheadSuccess jj_ls;
    public Token jj_nt;
    private int jj_ntk;
    private boolean jj_rescan;
    private Token jj_scanpos;
    private boolean jj_semLA;
    public boolean lookingAhead;
    private int loopNesting;
    private int mixedContentNesting;
    private boolean stripText;
    private boolean stripWhitespace;
    private int switchNesting;
    private Template template;
    public Token token;
    public FMParserTokenManager token_source;

    public final void disable_tracing() {
    }

    public final void enable_tracing() {
    }

    public static FMParser createExpressionParser(String str) {
        FMParserTokenManager fMParserTokenManager = new FMParserTokenManager(new SimpleCharStream(new StringReader(str), 1, 1, str.length()));
        fMParserTokenManager.SwitchTo(2);
        FMParser fMParser = new FMParser(fMParserTokenManager);
        fMParserTokenManager.setParser(fMParser);
        return fMParser;
    }

    public FMParser(Template template, Reader reader, boolean z, boolean z2) {
        this(reader);
        setTemplate(template);
        this.token_source.setParser(this);
        this.token_source.strictEscapeSyntax = z;
        this.stripWhitespace = z2;
    }

    public FMParser(Template template, Reader reader, boolean z, boolean z2, int i) {
        this(template, reader, z, z2, i, Configuration.PARSED_DEFAULT_INCOMPATIBLE_ENHANCEMENTS);
    }

    public FMParser(Template template, Reader reader, boolean z, boolean z2, int i, int i2) {
        this(template, reader, z, z2);
        if (i == 0) {
            this.token_source.autodetectTagSyntax = true;
        } else if (i == 1) {
            this.token_source.squBracTagSyntax = false;
        } else if (i == 2) {
            this.token_source.squBracTagSyntax = true;
        } else {
            throw new IllegalArgumentException("Illegal argument for tagSyntax");
        }
        this.token_source.incompatibleImprovements = i2;
        this.incompatibleImprovements = i2;
    }

    public FMParser(String str) {
        this(null, new StringReader(str), true, true);
    }

    void setTemplate(Template template) {
        this.template = template;
    }

    Template getTemplate() {
        return this.template;
    }

    public int _getLastTagSyntax() {
        return this.token_source.squBracTagSyntax ? 2 : 1;
    }

    private void notStringLiteral(Expression expression, String str) throws ParseException {
        if (expression instanceof StringLiteral) {
            throw new ParseException(new StringBuffer("Found string literal: ").append(expression).append(". Expecting: ").append(str).toString(), expression);
        }
    }

    private void notNumberLiteral(Expression expression, String str) throws ParseException {
        if (expression instanceof NumberLiteral) {
            throw new ParseException(new StringBuffer("Found number literal: ").append(expression.getCanonicalForm()).append(". Expecting ").append(str).toString(), expression);
        }
    }

    private void notBooleanLiteral(Expression expression, String str) throws ParseException {
        if (expression instanceof BooleanLiteral) {
            throw new ParseException(new StringBuffer("Found: ").append(expression.getCanonicalForm()).append(". Expecting ").append(str).toString(), expression);
        }
    }

    private void notHashLiteral(Expression expression, String str) throws ParseException {
        if (expression instanceof HashLiteral) {
            throw new ParseException(new StringBuffer("Found hash literal: ").append(expression.getCanonicalForm()).append(". Expecting ").append(str).toString(), expression);
        }
    }

    private void notListLiteral(Expression expression, String str) throws ParseException {
        if (expression instanceof ListLiteral) {
            throw new ParseException(new StringBuffer("Found list literal: ").append(expression.getCanonicalForm()).append(". Expecting ").append(str).toString(), expression);
        }
    }

    private void numberLiteralOnly(Expression expression) throws ParseException {
        notStringLiteral(expression, "number");
        notListLiteral(expression, "number");
        notHashLiteral(expression, "number");
        notBooleanLiteral(expression, "number");
    }

    private void stringLiteralOnly(Expression expression) throws ParseException {
        notNumberLiteral(expression, "string");
        notListLiteral(expression, "string");
        notHashLiteral(expression, "string");
        notBooleanLiteral(expression, "string");
    }

    private void booleanLiteralOnly(Expression expression) throws ParseException {
        notStringLiteral(expression, "boolean (true/false)");
        notListLiteral(expression, "boolean (true/false)");
        notHashLiteral(expression, "boolean (true/false)");
        notNumberLiteral(expression, "boolean (true/false)");
    }

    private Expression escapedExpression(Expression expression) {
        return !this.escapes.isEmpty() ? ((EscapeBlock) this.escapes.getFirst()).doEscape(expression) : expression;
    }

    private boolean getBoolean(Expression expression) throws ParseException {
        try {
            TemplateModel templateModelEval = expression.eval(null);
            if (templateModelEval instanceof TemplateBooleanModel) {
                try {
                    return ((TemplateBooleanModel) templateModelEval).getAsBoolean();
                } catch (TemplateModelException unused) {
                }
            }
            if (templateModelEval instanceof TemplateScalarModel) {
                try {
                    return StringUtil.getYesNo(((TemplateScalarModel) templateModelEval).getAsString());
                } catch (Exception e) {
                    throw new ParseException(new StringBuffer().append(e.getMessage()).append("\nExpecting boolean (true/false), found: ").append(expression.getCanonicalForm()).toString(), expression);
                }
            }
            throw new ParseException("Expecting boolean (true/false) parameter", expression);
        } catch (Exception e2) {
            throw new ParseException(new StringBuffer().append(e2.getMessage()).append("\nCould not evaluate expression: ").append(expression.getCanonicalForm()).toString(), expression, e2);
        }
    }

    public final Expression Expression() throws ParseException {
        return OrExpression();
    }

    public final Expression PrimaryExpression() throws ParseException {
        Expression expressionListLiteral;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 113) {
            expressionListLiteral = ListLiteral();
        } else if (iJj_ntk == 115) {
            expressionListLiteral = Parenthesis();
        } else if (iJj_ntk == 117) {
            expressionListLiteral = HashLiteral();
        } else if (iJj_ntk != 122) {
            switch (iJj_ntk) {
                case 81:
                case 82:
                    expressionListLiteral = StringLiteral(true);
                    break;
                case 83:
                case 84:
                    expressionListLiteral = BooleanLiteral();
                    break;
                case 85:
                case 86:
                    expressionListLiteral = NumberLiteral();
                    break;
                case 87:
                    expressionListLiteral = BuiltinVariable();
                    break;
                default:
                    this.jj_la1[0] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        } else {
            expressionListLiteral = Identifier();
        }
        while (jj_2_1(Integer.MAX_VALUE)) {
            expressionListLiteral = AddSubExpression(expressionListLiteral);
        }
        return expressionListLiteral;
    }

    public final Expression Parenthesis() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(115);
        Expression Expression = Expression();
        Token tokenJj_consume_token2 = jj_consume_token(116);
        ParentheticalExpression parentheticalExpression = new ParentheticalExpression(Expression);
        parentheticalExpression.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
        return parentheticalExpression;
    }

    public final Expression UnaryExpression() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 100 || iJj_ntk == 101) {
            return UnaryPlusMinusExpression();
        }
        if (iJj_ntk == 109) {
            return NotExpression();
        }
        if (iJj_ntk != 113 && iJj_ntk != 115 && iJj_ntk != 117 && iJj_ntk != 122) {
            switch (iJj_ntk) {
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                    break;
                default:
                    this.jj_la1[1] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        return PrimaryExpression();
    }

    public final Expression NotExpression() throws ParseException {
        int iJj_ntk;
        ArrayList arrayList = new ArrayList();
        do {
            arrayList.add(jj_consume_token(109));
            iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
        } while (iJj_ntk == 109);
        this.jj_la1[2] = this.jj_gen;
        Expression expressionPrimaryExpression = PrimaryExpression();
        NotExpression notExpression = null;
        int i = 0;
        while (i < arrayList.size()) {
            notExpression = new NotExpression(expressionPrimaryExpression);
            notExpression.setLocation(this.template, (Token) arrayList.get((arrayList.size() - i) - 1), expressionPrimaryExpression);
            i++;
            expressionPrimaryExpression = notExpression;
        }
        return notExpression;
    }

    public final Expression UnaryPlusMinusExpression() throws ParseException {
        Token tokenJj_consume_token;
        boolean z;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 100) {
            tokenJj_consume_token = jj_consume_token(100);
            z = false;
        } else if (iJj_ntk == 101) {
            tokenJj_consume_token = jj_consume_token(101);
            z = true;
        } else {
            this.jj_la1[3] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        Expression expressionPrimaryExpression = PrimaryExpression();
        UnaryPlusMinusExpression unaryPlusMinusExpression = new UnaryPlusMinusExpression(expressionPrimaryExpression, z);
        unaryPlusMinusExpression.setLocation(this.template, tokenJj_consume_token, expressionPrimaryExpression);
        return unaryPlusMinusExpression;
    }

    public final Expression AdditiveExpression() throws ParseException {
        boolean z;
        Expression arithmeticExpression;
        Expression expressionMultiplicativeExpression = MultiplicativeExpression();
        while (jj_2_2(Integer.MAX_VALUE)) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk == 100) {
                jj_consume_token(100);
                z = true;
            } else if (iJj_ntk == 101) {
                jj_consume_token(101);
                z = false;
            } else {
                this.jj_la1[4] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            Expression expressionMultiplicativeExpression2 = MultiplicativeExpression();
            if (z) {
                arithmeticExpression = new AddConcatExpression(expressionMultiplicativeExpression, expressionMultiplicativeExpression2);
            } else {
                numberLiteralOnly(expressionMultiplicativeExpression);
                numberLiteralOnly(expressionMultiplicativeExpression2);
                arithmeticExpression = new ArithmeticExpression(expressionMultiplicativeExpression, expressionMultiplicativeExpression2, 0);
            }
            arithmeticExpression.setLocation(this.template, expressionMultiplicativeExpression, expressionMultiplicativeExpression2);
            expressionMultiplicativeExpression = arithmeticExpression;
        }
        return expressionMultiplicativeExpression;
    }

    public final Expression MultiplicativeExpression() throws ParseException {
        int i;
        Expression expressionUnaryExpression = UnaryExpression();
        while (jj_2_3(Integer.MAX_VALUE)) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk == 102) {
                jj_consume_token(102);
                i = 1;
            } else if (iJj_ntk == 105) {
                jj_consume_token(105);
                i = 2;
            } else if (iJj_ntk == 106) {
                jj_consume_token(106);
                i = 3;
            } else {
                this.jj_la1[5] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            Expression expressionUnaryExpression2 = UnaryExpression();
            numberLiteralOnly(expressionUnaryExpression);
            numberLiteralOnly(expressionUnaryExpression2);
            ArithmeticExpression arithmeticExpression = new ArithmeticExpression(expressionUnaryExpression, expressionUnaryExpression2, i);
            arithmeticExpression.setLocation(this.template, expressionUnaryExpression, expressionUnaryExpression2);
            expressionUnaryExpression = arithmeticExpression;
        }
        return expressionUnaryExpression;
    }

    public final Expression EqualityExpression() throws ParseException {
        Token tokenJj_consume_token;
        Expression expressionRelationalExpression = RelationalExpression();
        if (!jj_2_4(Integer.MAX_VALUE)) {
            return expressionRelationalExpression;
        }
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        switch (iJj_ntk) {
            case 93:
                tokenJj_consume_token = jj_consume_token(93);
                break;
            case 94:
                tokenJj_consume_token = jj_consume_token(94);
                break;
            case 95:
                tokenJj_consume_token = jj_consume_token(95);
                break;
            default:
                this.jj_la1[6] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        Expression expressionRelationalExpression2 = RelationalExpression();
        notHashLiteral(expressionRelationalExpression, "scalar");
        notHashLiteral(expressionRelationalExpression2, "scalar");
        notListLiteral(expressionRelationalExpression, "scalar");
        notListLiteral(expressionRelationalExpression2, "scalar");
        ComparisonExpression comparisonExpression = new ComparisonExpression(expressionRelationalExpression, expressionRelationalExpression2, tokenJj_consume_token.image);
        comparisonExpression.setLocation(this.template, expressionRelationalExpression, expressionRelationalExpression2);
        return comparisonExpression;
    }

    public final Expression RelationalExpression() throws ParseException {
        Token tokenJj_consume_token;
        Expression expressionRangeExpression = RangeExpression();
        if (!jj_2_5(Integer.MAX_VALUE)) {
            return expressionRangeExpression;
        }
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 128) {
            tokenJj_consume_token = jj_consume_token(128);
        } else if (iJj_ntk == 129) {
            tokenJj_consume_token = jj_consume_token(129);
        } else {
            switch (iJj_ntk) {
                case 96:
                    tokenJj_consume_token = jj_consume_token(96);
                    break;
                case 97:
                    tokenJj_consume_token = jj_consume_token(97);
                    break;
                case 98:
                    tokenJj_consume_token = jj_consume_token(98);
                    break;
                case 99:
                    tokenJj_consume_token = jj_consume_token(99);
                    break;
                default:
                    this.jj_la1[7] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        Expression expressionRangeExpression2 = RangeExpression();
        notHashLiteral(expressionRangeExpression, "scalar");
        notHashLiteral(expressionRangeExpression2, "scalar");
        notListLiteral(expressionRangeExpression, "scalar");
        notListLiteral(expressionRangeExpression2, "scalar");
        notStringLiteral(expressionRangeExpression, "number");
        notStringLiteral(expressionRangeExpression2, "number");
        ComparisonExpression comparisonExpression = new ComparisonExpression(expressionRangeExpression, expressionRangeExpression2, tokenJj_consume_token.image);
        comparisonExpression.setLocation(this.template, expressionRangeExpression, expressionRangeExpression2);
        return comparisonExpression;
    }

    public final Expression RangeExpression() throws ParseException {
        int i;
        Token token;
        int i2;
        Expression expressionAdditiveExpression = AdditiveExpression();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        switch (iJj_ntk) {
            case 88:
            case 89:
            case 90:
                int iJj_ntk2 = this.jj_ntk;
                if (iJj_ntk2 == -1) {
                    iJj_ntk2 = jj_ntk();
                }
                Expression expressionAdditiveExpression2 = null;
                switch (iJj_ntk2) {
                    case 88:
                        Token tokenJj_consume_token = jj_consume_token(88);
                        if (jj_2_6(Integer.MAX_VALUE)) {
                            expressionAdditiveExpression2 = AdditiveExpression();
                            i = 0;
                        } else {
                            i = 2;
                        }
                        int i3 = i;
                        token = tokenJj_consume_token;
                        i2 = i3;
                        break;
                    case 89:
                    case 90:
                        int iJj_ntk3 = this.jj_ntk;
                        if (iJj_ntk3 == -1) {
                            iJj_ntk3 = jj_ntk();
                        }
                        if (iJj_ntk3 == 89) {
                            jj_consume_token(89);
                            i2 = 1;
                        } else if (iJj_ntk3 == 90) {
                            jj_consume_token(90);
                            i2 = 3;
                        } else {
                            this.jj_la1[8] = this.jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                        }
                        expressionAdditiveExpression2 = AdditiveExpression();
                        token = null;
                        break;
                    default:
                        this.jj_la1[9] = this.jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                numberLiteralOnly(expressionAdditiveExpression);
                if (expressionAdditiveExpression2 != null) {
                    numberLiteralOnly(expressionAdditiveExpression2);
                }
                Range range = new Range(expressionAdditiveExpression, expressionAdditiveExpression2, i2);
                if (expressionAdditiveExpression2 != null) {
                    range.setLocation(this.template, expressionAdditiveExpression, expressionAdditiveExpression2);
                } else {
                    range.setLocation(this.template, expressionAdditiveExpression, token);
                }
                return range;
            default:
                this.jj_la1[10] = this.jj_gen;
                return expressionAdditiveExpression;
        }
    }

    public final Expression AndExpression() throws ParseException {
        Expression expressionEqualityExpression = EqualityExpression();
        while (jj_2_7(Integer.MAX_VALUE)) {
            jj_consume_token(107);
            Expression expressionEqualityExpression2 = EqualityExpression();
            booleanLiteralOnly(expressionEqualityExpression);
            booleanLiteralOnly(expressionEqualityExpression2);
            AndExpression andExpression = new AndExpression(expressionEqualityExpression, expressionEqualityExpression2);
            andExpression.setLocation(this.template, expressionEqualityExpression, expressionEqualityExpression2);
            expressionEqualityExpression = andExpression;
        }
        return expressionEqualityExpression;
    }

    public final Expression OrExpression() throws ParseException {
        Expression expressionAndExpression = AndExpression();
        while (jj_2_8(Integer.MAX_VALUE)) {
            jj_consume_token(108);
            Expression expressionAndExpression2 = AndExpression();
            booleanLiteralOnly(expressionAndExpression);
            booleanLiteralOnly(expressionAndExpression2);
            OrExpression orExpression = new OrExpression(expressionAndExpression, expressionAndExpression2);
            orExpression.setLocation(this.template, expressionAndExpression, expressionAndExpression2);
            expressionAndExpression = orExpression;
        }
        return expressionAndExpression;
    }

    public final ListLiteral ListLiteral() throws ParseException {
        new ArrayList();
        Token tokenJj_consume_token = jj_consume_token(113);
        ArrayList arrayListPositionalArgs = PositionalArgs();
        Token tokenJj_consume_token2 = jj_consume_token(114);
        ListLiteral listLiteral = new ListLiteral(arrayListPositionalArgs);
        listLiteral.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
        return listLiteral;
    }

    public final Expression NumberLiteral() throws ParseException {
        Token tokenJj_consume_token;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 85) {
            tokenJj_consume_token = jj_consume_token(85);
        } else if (iJj_ntk == 86) {
            tokenJj_consume_token = jj_consume_token(86);
        } else {
            this.jj_la1[11] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        NumberLiteral numberLiteral = new NumberLiteral(this.template.getArithmeticEngine().toNumber(tokenJj_consume_token.image));
        numberLiteral.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return numberLiteral;
    }

    public final Identifier Identifier() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(122);
        Identifier identifier = new Identifier(tokenJj_consume_token.image);
        identifier.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return identifier;
    }

    public final Expression IdentifierOrStringLiteral() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 81 || iJj_ntk == 82) {
            return StringLiteral(false);
        }
        if (iJj_ntk == 122) {
            return Identifier();
        }
        this.jj_la1[12] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
    }

    public final BuiltinVariable BuiltinVariable() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(87);
        Token tokenJj_consume_token2 = jj_consume_token(122);
        try {
            BuiltinVariable builtinVariable = new BuiltinVariable(tokenJj_consume_token2.image);
            builtinVariable.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
            return builtinVariable;
        } catch (ParseException e) {
            e.lineNumber = tokenJj_consume_token.beginLine;
            e.columnNumber = tokenJj_consume_token.beginColumn;
            e.endLineNumber = tokenJj_consume_token2.endLine;
            e.endColumnNumber = tokenJj_consume_token2.endColumn;
            throw e;
        }
    }

    public final Expression AddSubExpression(Expression expression) throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 87) {
            return DotVariable(expression);
        }
        if (iJj_ntk != 109) {
            if (iJj_ntk == 113) {
                return DynamicKey(expression);
            }
            if (iJj_ntk == 115) {
                return MethodArgs(expression);
            }
            if (iJj_ntk != 131) {
                if (iJj_ntk == 91) {
                    return BuiltIn(expression);
                }
                if (iJj_ntk == 92) {
                    return Exists(expression);
                }
                this.jj_la1[13] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        return DefaultTo(expression);
    }

    public final Expression DefaultTo(Expression expression) throws ParseException {
        Token tokenJj_consume_token;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        Expression Expression = null;
        if (iJj_ntk == 109) {
            tokenJj_consume_token = jj_consume_token(109);
            if (jj_2_9(Integer.MAX_VALUE)) {
                Expression = Expression();
            }
        } else if (iJj_ntk == 131) {
            tokenJj_consume_token = jj_consume_token(FMParserConstants.TERMINATING_EXCLAM);
        } else {
            this.jj_la1[14] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        DefaultToExpression defaultToExpression = new DefaultToExpression(expression, Expression);
        if (Expression == null) {
            defaultToExpression.setLocation(this.template, expression, tokenJj_consume_token);
        } else {
            defaultToExpression.setLocation(this.template, expression, Expression);
        }
        return defaultToExpression;
    }

    public final Expression Exists(Expression expression) throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(92);
        ExistsExpression existsExpression = new ExistsExpression(expression);
        existsExpression.setLocation(this.template, expression, tokenJj_consume_token);
        return existsExpression;
    }

    public final Expression BuiltIn(Expression expression) throws ParseException {
        jj_consume_token(91);
        Token tokenJj_consume_token = jj_consume_token(122);
        try {
            BuiltIn builtInNewBuiltIn = BuiltIn.newBuiltIn(this.incompatibleImprovements, expression, tokenJj_consume_token.image);
            builtInNewBuiltIn.setLocation(this.template, expression, tokenJj_consume_token);
            return builtInNewBuiltIn;
        } catch (ParseException e) {
            e.lineNumber = tokenJj_consume_token.beginLine;
            e.columnNumber = tokenJj_consume_token.beginColumn;
            e.endLineNumber = tokenJj_consume_token.endLine;
            e.endColumnNumber = tokenJj_consume_token.endColumn;
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0049 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final org.mapstruct.ap.shaded.freemarker.core.Expression DotVariable(org.mapstruct.ap.shaded.freemarker.core.Expression r6) throws org.mapstruct.ap.shaded.freemarker.core.ParseException {
        /*
            r5 = this;
            r0 = 87
            r5.jj_consume_token(r0)
            int r0 = r5.jj_ntk
            r1 = -1
            if (r0 != r1) goto Le
            int r0 = r5.jj_ntk()
        Le:
            r2 = 84
            r3 = 83
            if (r0 == r3) goto L49
            if (r0 == r2) goto L49
            r4 = 102(0x66, float:1.43E-43)
            if (r0 == r4) goto L43
            r4 = 103(0x67, float:1.44E-43)
            if (r0 == r4) goto L3d
            switch(r0) {
                case 96: goto L49;
                case 97: goto L49;
                case 98: goto L49;
                case 99: goto L49;
                default: goto L21;
            }
        L21:
            switch(r0) {
                case 119: goto L49;
                case 120: goto L49;
                case 121: goto L49;
                case 122: goto L35;
                default: goto L24;
            }
        L24:
            int[] r6 = r5.jj_la1
            r0 = 16
            int r2 = r5.jj_gen
            r6[r0] = r2
            r5.jj_consume_token(r1)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r6 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r6.<init>()
            throw r6
        L35:
            r0 = 122(0x7a, float:1.71E-43)
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto Lb3
        L3d:
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r4)
            goto Lb3
        L43:
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r4)
            goto Lb3
        L49:
            int r0 = r5.jj_ntk
            if (r0 != r1) goto L51
            int r0 = r5.jj_ntk()
        L51:
            if (r0 == r3) goto La2
            if (r0 == r2) goto L9d
            switch(r0) {
                case 96: goto L96;
                case 97: goto L8f;
                case 98: goto L88;
                case 99: goto L81;
                default: goto L58;
            }
        L58:
            switch(r0) {
                case 119: goto L7a;
                case 120: goto L73;
                case 121: goto L6c;
                default: goto L5b;
            }
        L5b:
            int[] r6 = r5.jj_la1
            r0 = 15
            int r2 = r5.jj_gen
            r6[r0] = r2
            r5.jj_consume_token(r1)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r6 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r6.<init>()
            throw r6
        L6c:
            r0 = 121(0x79, float:1.7E-43)
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L73:
            r0 = 120(0x78, float:1.68E-43)
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L7a:
            r0 = 119(0x77, float:1.67E-43)
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L81:
            r0 = 99
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L88:
            r0 = 98
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L8f:
            r0 = 97
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L96:
            r0 = 96
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r0)
            goto La6
        L9d:
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r2)
            goto La6
        La2:
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r5.jj_consume_token(r3)
        La6:
            java.lang.String r1 = r0.image
            r2 = 0
            char r1 = r1.charAt(r2)
            boolean r1 = java.lang.Character.isLetter(r1)
            if (r1 == 0) goto Lcb
        Lb3:
            java.lang.String r1 = "hash"
            r5.notListLiteral(r6, r1)
            r5.notStringLiteral(r6, r1)
            r5.notBooleanLiteral(r6, r1)
            org.mapstruct.ap.shaded.freemarker.core.Dot r1 = new org.mapstruct.ap.shaded.freemarker.core.Dot
            java.lang.String r2 = r0.image
            r1.<init>(r6, r2)
            org.mapstruct.ap.shaded.freemarker.template.Template r2 = r5.template
            r1.setLocation(r2, r6, r0)
            return r1
        Lcb:
            org.mapstruct.ap.shaded.freemarker.core.ParseException r6 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = r0.image
            java.lang.StringBuffer r1 = r1.append(r2)
            java.lang.String r2 = " is not a valid identifier."
            java.lang.StringBuffer r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            org.mapstruct.ap.shaded.freemarker.template.Template r2 = r5.template
            r6.<init>(r1, r2, r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParser.DotVariable(org.mapstruct.ap.shaded.freemarker.core.Expression):org.mapstruct.ap.shaded.freemarker.core.Expression");
    }

    public final Expression DynamicKey(Expression expression) throws ParseException {
        jj_consume_token(113);
        Expression Expression = Expression();
        Token tokenJj_consume_token = jj_consume_token(114);
        notBooleanLiteral(expression, "list or hash");
        notNumberLiteral(expression, "list or hash");
        DynamicKeyName dynamicKeyName = new DynamicKeyName(expression, Expression);
        dynamicKeyName.setLocation(this.template, expression, tokenJj_consume_token);
        return dynamicKeyName;
    }

    public final MethodCall MethodArgs(Expression expression) throws ParseException {
        new ArrayList();
        jj_consume_token(115);
        ArrayList arrayListPositionalArgs = PositionalArgs();
        Token tokenJj_consume_token = jj_consume_token(116);
        arrayListPositionalArgs.trimToSize();
        MethodCall methodCall = new MethodCall(expression, arrayListPositionalArgs);
        methodCall.setLocation(this.template, expression, tokenJj_consume_token);
        return methodCall;
    }

    public final StringLiteral StringLiteral(boolean z) throws ParseException {
        Token tokenJj_consume_token;
        boolean z2;
        String strFTLStringLiteralDec;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 81) {
            tokenJj_consume_token = jj_consume_token(81);
            z2 = false;
        } else if (iJj_ntk == 82) {
            tokenJj_consume_token = jj_consume_token(82);
            z2 = true;
        } else {
            this.jj_la1[17] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        String str = tokenJj_consume_token.image;
        String strSubstring = str.substring(1, str.length() - 1);
        if (z2) {
            strFTLStringLiteralDec = strSubstring.substring(1);
        } else {
            try {
                strFTLStringLiteralDec = StringUtil.FTLStringLiteralDec(strSubstring);
            } catch (ParseException e) {
                e.lineNumber = tokenJj_consume_token.beginLine;
                e.columnNumber = tokenJj_consume_token.beginColumn;
                e.endLineNumber = tokenJj_consume_token.endLine;
                e.endColumnNumber = tokenJj_consume_token.endColumn;
                throw e;
            }
        }
        StringLiteral stringLiteral = new StringLiteral(strFTLStringLiteralDec);
        stringLiteral.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        if (z && !z2 && (tokenJj_consume_token.image.indexOf("${") >= 0 || tokenJj_consume_token.image.indexOf("#{") >= 0)) {
            stringLiteral.checkInterpolation();
        }
        return stringLiteral;
    }

    public final Expression BooleanLiteral() throws ParseException {
        Token tokenJj_consume_token;
        BooleanLiteral booleanLiteral;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 83) {
            tokenJj_consume_token = jj_consume_token(83);
            booleanLiteral = new BooleanLiteral(false);
        } else if (iJj_ntk == 84) {
            tokenJj_consume_token = jj_consume_token(84);
            booleanLiteral = new BooleanLiteral(true);
        } else {
            this.jj_la1[18] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        booleanLiteral.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return booleanLiteral;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final org.mapstruct.ap.shaded.freemarker.core.HashLiteral HashLiteral() throws org.mapstruct.ap.shaded.freemarker.core.ParseException {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 117(0x75, float:1.64E-43)
            org.mapstruct.ap.shaded.freemarker.core.Token r3 = r8.jj_consume_token(r2)
            int r4 = r8.jj_ntk
            r5 = -1
            if (r4 != r5) goto L19
            int r4 = r8.jj_ntk()
        L19:
            r6 = 100
            if (r4 == r6) goto L3f
            r6 = 101(0x65, float:1.42E-43)
            if (r4 == r6) goto L3f
            r6 = 109(0x6d, float:1.53E-43)
            if (r4 == r6) goto L3f
            r6 = 113(0x71, float:1.58E-43)
            if (r4 == r6) goto L3f
            r6 = 115(0x73, float:1.61E-43)
            if (r4 == r6) goto L3f
            if (r4 == r2) goto L3f
            r2 = 122(0x7a, float:1.71E-43)
            if (r4 == r2) goto L3f
            switch(r4) {
                case 81: goto L3f;
                case 82: goto L3f;
                case 83: goto L3f;
                case 84: goto L3f;
                case 85: goto L3f;
                case 86: goto L3f;
                case 87: goto L3f;
                default: goto L36;
            }
        L36:
            int[] r2 = r8.jj_la1
            r4 = 22
            int r5 = r8.jj_gen
            r2[r4] = r5
            goto L8a
        L3f:
            org.mapstruct.ap.shaded.freemarker.core.Expression r2 = r8.Expression()
            int r4 = r8.jj_ntk
            if (r4 != r5) goto L4b
            int r4 = r8.jj_ntk()
        L4b:
            r6 = 112(0x70, float:1.57E-43)
            r7 = 110(0x6e, float:1.54E-43)
            if (r4 == r7) goto L68
            if (r4 != r6) goto L57
            r8.jj_consume_token(r6)
            goto L6b
        L57:
            int[] r0 = r8.jj_la1
            r1 = 19
            int r2 = r8.jj_gen
            r0[r1] = r2
            r8.jj_consume_token(r5)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r0 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r0.<init>()
            throw r0
        L68:
            r8.jj_consume_token(r7)
        L6b:
            org.mapstruct.ap.shaded.freemarker.core.Expression r4 = r8.Expression()
            r8.stringLiteralOnly(r2)
            r0.add(r2)
            r1.add(r4)
        L78:
            int r2 = r8.jj_ntk
            if (r2 != r5) goto L80
            int r2 = r8.jj_ntk()
        L80:
            if (r2 == r7) goto L9b
            int[] r2 = r8.jj_la1
            r4 = 20
            int r5 = r8.jj_gen
            r2[r4] = r5
        L8a:
            r2 = 118(0x76, float:1.65E-43)
            org.mapstruct.ap.shaded.freemarker.core.Token r2 = r8.jj_consume_token(r2)
            org.mapstruct.ap.shaded.freemarker.core.HashLiteral r4 = new org.mapstruct.ap.shaded.freemarker.core.HashLiteral
            r4.<init>(r0, r1)
            org.mapstruct.ap.shaded.freemarker.template.Template r0 = r8.template
            r4.setLocation(r0, r3, r2)
            return r4
        L9b:
            r8.jj_consume_token(r7)
            org.mapstruct.ap.shaded.freemarker.core.Expression r2 = r8.Expression()
            int r4 = r8.jj_ntk
            if (r4 != r5) goto Laa
            int r4 = r8.jj_ntk()
        Laa:
            if (r4 == r7) goto Lc3
            if (r4 != r6) goto Lb2
            r8.jj_consume_token(r6)
            goto Lc6
        Lb2:
            int[] r0 = r8.jj_la1
            r1 = 21
            int r2 = r8.jj_gen
            r0[r1] = r2
            r8.jj_consume_token(r5)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r0 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r0.<init>()
            throw r0
        Lc3:
            r8.jj_consume_token(r7)
        Lc6:
            org.mapstruct.ap.shaded.freemarker.core.Expression r4 = r8.Expression()
            r8.stringLiteralOnly(r2)
            r0.add(r2)
            r1.add(r4)
            goto L78
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParser.HashLiteral():org.mapstruct.ap.shaded.freemarker.core.HashLiteral");
    }

    public final DollarVariable StringOutput() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(71);
        Expression Expression = Expression();
        notHashLiteral(Expression, "string or something automatically convertible to string (number, date or boolean)");
        notListLiteral(Expression, "string or something automatically convertible to string (number, date or boolean)");
        Token tokenJj_consume_token2 = jj_consume_token(118);
        DollarVariable dollarVariable = new DollarVariable(Expression, escapedExpression(Expression));
        dollarVariable.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
        return dollarVariable;
    }

    public final NumericalOutput NumericalOutput() throws ParseException, NumberFormatException {
        Token tokenJj_consume_token;
        NumericalOutput numericalOutput;
        Token tokenJj_consume_token2 = jj_consume_token(72);
        Expression Expression = Expression();
        numberLiteralOnly(Expression);
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 111) {
            jj_consume_token(111);
            tokenJj_consume_token = jj_consume_token(122);
        } else {
            this.jj_la1[23] = this.jj_gen;
            tokenJj_consume_token = null;
        }
        Token tokenJj_consume_token3 = jj_consume_token(118);
        if (tokenJj_consume_token != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(tokenJj_consume_token.image, "mM", true);
            int i = -1;
            int i2 = -1;
            while (true) {
                char c = '-';
                while (stringTokenizer.hasMoreTokens()) {
                    String strNextToken = stringTokenizer.nextToken();
                    if (c != '-') {
                        if (c != 'M') {
                            if (c != 'm') {
                                throw new ParseException("Invalid formatting string", this.template, tokenJj_consume_token);
                            }
                            if (i2 != -1) {
                                throw new ParseException("Invalid formatting string", this.template, tokenJj_consume_token);
                            }
                            try {
                                i2 = Integer.parseInt(strNextToken);
                            } catch (NumberFormatException unused) {
                                throw new ParseException(new StringBuffer("Invalid number in the format specifier ").append(tokenJj_consume_token.image).toString(), this.template, tokenJj_consume_token);
                            } catch (ParseException unused2) {
                                throw new ParseException(new StringBuffer("Invalid format specifier ").append(tokenJj_consume_token.image).toString(), this.template, tokenJj_consume_token);
                            }
                        } else {
                            if (i != -1) {
                                throw new ParseException("Invalid formatting string", this.template, tokenJj_consume_token);
                            }
                            i = Integer.parseInt(strNextToken);
                        }
                    } else if (strNextToken.equals("m")) {
                        c = 'm';
                    } else {
                        if (!strNextToken.equals("M")) {
                            throw new ParseException();
                        }
                        c = 'M';
                    }
                }
                if (i == -1) {
                    if (i2 == -1) {
                        throw new ParseException("Invalid format specification, at least one of m and M must be specified!", this.template, tokenJj_consume_token);
                    }
                    i = i2;
                } else if (i2 == -1) {
                    i2 = 0;
                }
                if (i2 > i) {
                    throw new ParseException("Invalid format specification, min cannot be greater than max!", this.template, tokenJj_consume_token);
                }
                if (i2 > 50 || i > 50) {
                    throw new ParseException("Cannot specify more than 50 fraction digits", this.template, tokenJj_consume_token);
                }
                numericalOutput = new NumericalOutput(Expression, i2, i);
            }
        } else {
            numericalOutput = new NumericalOutput(Expression);
        }
        numericalOutput.setLocation(this.template, tokenJj_consume_token2, tokenJj_consume_token3);
        return numericalOutput;
    }

    public final TemplateElement If() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(8);
        Expression Expression = Expression();
        jj_consume_token(126);
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        ConditionalBlock conditionalBlock = new ConditionalBlock(Expression, templateElementOptionalBlock, 0);
        conditionalBlock.setLocation(this.template, tokenJj_consume_token, templateElementOptionalBlock);
        IfBlock ifBlock = new IfBlock(conditionalBlock);
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk != 9) {
                break;
            }
            Token tokenJj_consume_token2 = jj_consume_token(9);
            Expression Expression2 = Expression();
            LooseDirectiveEnd();
            TemplateElement templateElementOptionalBlock2 = OptionalBlock();
            ConditionalBlock conditionalBlock2 = new ConditionalBlock(Expression2, templateElementOptionalBlock2, 2);
            conditionalBlock2.setLocation(this.template, tokenJj_consume_token2, templateElementOptionalBlock2);
            ifBlock.addBlock(conditionalBlock2);
        }
        this.jj_la1[24] = this.jj_gen;
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 == 44) {
            Token tokenJj_consume_token3 = jj_consume_token(44);
            TemplateElement templateElementOptionalBlock3 = OptionalBlock();
            ConditionalBlock conditionalBlock3 = new ConditionalBlock(null, templateElementOptionalBlock3, 1);
            conditionalBlock3.setLocation(this.template, tokenJj_consume_token3, templateElementOptionalBlock3);
            ifBlock.addBlock(conditionalBlock3);
        } else {
            this.jj_la1[25] = this.jj_gen;
        }
        ifBlock.setLocation(this.template, tokenJj_consume_token, jj_consume_token(31));
        return ifBlock;
    }

    public final AttemptBlock Attempt() throws ParseException {
        Token tokenJj_consume_token;
        Token tokenJj_consume_token2 = jj_consume_token(6);
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        RecoveryBlock recoveryBlockRecover = Recover();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 33) {
            tokenJj_consume_token = jj_consume_token(33);
        } else if (iJj_ntk == 34) {
            tokenJj_consume_token = jj_consume_token(34);
        } else {
            this.jj_la1[26] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        AttemptBlock attemptBlock = new AttemptBlock(templateElementOptionalBlock, recoveryBlockRecover);
        attemptBlock.setLocation(this.template, tokenJj_consume_token2, tokenJj_consume_token);
        return attemptBlock;
    }

    public final RecoveryBlock Recover() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(7);
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        RecoveryBlock recoveryBlock = new RecoveryBlock(templateElementOptionalBlock);
        recoveryBlock.setLocation(this.template, tokenJj_consume_token, templateElementOptionalBlock);
        return recoveryBlock;
    }

    public final IteratorBlock List() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(10);
        this.loopNesting++;
        Expression Expression = Expression();
        jj_consume_token(120);
        Token tokenJj_consume_token2 = jj_consume_token(122);
        jj_consume_token(126);
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        Token tokenJj_consume_token3 = jj_consume_token(32);
        this.loopNesting--;
        IteratorBlock iteratorBlock = new IteratorBlock(Expression, tokenJj_consume_token2.image, templateElementOptionalBlock, false);
        iteratorBlock.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token3);
        return iteratorBlock;
    }

    public final IteratorBlock ForEach() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(11);
        this.loopNesting++;
        Token tokenJj_consume_token2 = jj_consume_token(122);
        jj_consume_token(119);
        Expression Expression = Expression();
        jj_consume_token(126);
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        Token tokenJj_consume_token3 = jj_consume_token(35);
        this.loopNesting--;
        IteratorBlock iteratorBlock = new IteratorBlock(Expression, tokenJj_consume_token2.image, templateElementOptionalBlock, true);
        iteratorBlock.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token3);
        return iteratorBlock;
    }

    public final VisitNode Visit() throws ParseException {
        Expression Expression;
        Token tokenJj_consume_token = jj_consume_token(22);
        Expression Expression2 = Expression();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 121) {
            jj_consume_token(121);
            Expression = Expression();
        } else {
            this.jj_la1[27] = this.jj_gen;
            Expression = null;
        }
        Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
        VisitNode visitNode = new VisitNode(Expression2, Expression);
        visitNode.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        return visitNode;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final org.mapstruct.ap.shaded.freemarker.core.RecurseNode Recurse() throws org.mapstruct.ap.shaded.freemarker.core.ParseException {
        /*
            r7 = this;
            int r0 = r7.jj_ntk
            r1 = -1
            if (r0 != r1) goto L9
            int r0 = r7.jj_ntk()
        L9:
            r2 = 56
            r3 = 0
            if (r0 == r2) goto L80
            r2 = 57
            if (r0 != r2) goto L6f
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r7.jj_consume_token(r2)
            int r2 = r7.jj_ntk
            if (r2 != r1) goto L1e
            int r2 = r7.jj_ntk()
        L1e:
            r4 = 100
            if (r2 == r4) goto L47
            r4 = 101(0x65, float:1.42E-43)
            if (r2 == r4) goto L47
            r4 = 109(0x6d, float:1.53E-43)
            if (r2 == r4) goto L47
            r4 = 113(0x71, float:1.58E-43)
            if (r2 == r4) goto L47
            r4 = 115(0x73, float:1.61E-43)
            if (r2 == r4) goto L47
            r4 = 117(0x75, float:1.64E-43)
            if (r2 == r4) goto L47
            r4 = 122(0x7a, float:1.71E-43)
            if (r2 == r4) goto L47
            switch(r2) {
                case 81: goto L47;
                case 82: goto L47;
                case 83: goto L47;
                case 84: goto L47;
                case 85: goto L47;
                case 86: goto L47;
                case 87: goto L47;
                default: goto L3d;
            }
        L3d:
            int[] r2 = r7.jj_la1
            r4 = 28
            int r5 = r7.jj_gen
            r2[r4] = r5
            r2 = r3
            goto L4b
        L47:
            org.mapstruct.ap.shaded.freemarker.core.Expression r2 = r7.Expression()
        L4b:
            int r4 = r7.jj_ntk
            if (r4 != r1) goto L53
            int r4 = r7.jj_ntk()
        L53:
            r1 = 121(0x79, float:1.7E-43)
            if (r4 == r1) goto L60
            int[] r1 = r7.jj_la1
            r4 = 29
            int r5 = r7.jj_gen
            r1[r4] = r5
            goto L67
        L60:
            r7.jj_consume_token(r1)
            org.mapstruct.ap.shaded.freemarker.core.Expression r3 = r7.Expression()
        L67:
            org.mapstruct.ap.shaded.freemarker.core.Token r1 = r7.LooseDirectiveEnd()
            r6 = r3
            r3 = r1
            r1 = r6
            goto L86
        L6f:
            int[] r0 = r7.jj_la1
            r2 = 30
            int r3 = r7.jj_gen
            r0[r2] = r3
            r7.jj_consume_token(r1)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r0 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r0.<init>()
            throw r0
        L80:
            org.mapstruct.ap.shaded.freemarker.core.Token r0 = r7.jj_consume_token(r2)
            r1 = r3
            r2 = r1
        L86:
            if (r3 != 0) goto L89
            r3 = r0
        L89:
            org.mapstruct.ap.shaded.freemarker.core.RecurseNode r4 = new org.mapstruct.ap.shaded.freemarker.core.RecurseNode
            r4.<init>(r2, r1)
            org.mapstruct.ap.shaded.freemarker.template.Template r1 = r7.template
            r4.setLocation(r1, r0, r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParser.Recurse():org.mapstruct.ap.shaded.freemarker.core.RecurseNode");
    }

    public final FallbackInstruction FallBack() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(58);
        if (!this.inMacro) {
            throw new ParseException("Cannot fall back outside a macro.", this.template, tokenJj_consume_token);
        }
        FallbackInstruction fallbackInstruction = new FallbackInstruction();
        fallbackInstruction.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return fallbackInstruction;
    }

    public final BreakInstruction Break() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(45);
        if (this.loopNesting < 1 && this.switchNesting < 1) {
            throw new ParseException(new StringBuffer().append(tokenJj_consume_token.image).append(" occurred outside a loop or a switch block.").toString(), this.template, tokenJj_consume_token);
        }
        BreakInstruction breakInstruction = new BreakInstruction();
        breakInstruction.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return breakInstruction;
    }

    public final ReturnInstruction Return() throws ParseException {
        Token tokenJj_consume_token;
        Expression Expression;
        Token tokenLooseDirectiveEnd;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 24) {
            tokenJj_consume_token = jj_consume_token(24);
            Expression = Expression();
            tokenLooseDirectiveEnd = LooseDirectiveEnd();
        } else if (iJj_ntk == 46) {
            tokenJj_consume_token = jj_consume_token(46);
            Expression = null;
            tokenLooseDirectiveEnd = tokenJj_consume_token;
        } else {
            this.jj_la1[31] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        if (this.inMacro) {
            if (Expression != null) {
                throw new ParseException("A macro cannot return a value", this.template, tokenJj_consume_token);
            }
        } else if (this.inFunction) {
            if (Expression == null) {
                throw new ParseException("A function must return a value", this.template, tokenJj_consume_token);
            }
        } else if (Expression == null) {
            throw new ParseException("A return instruction can only occur inside a macro or function", this.template, tokenJj_consume_token);
        }
        ReturnInstruction returnInstruction = new ReturnInstruction(Expression);
        returnInstruction.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        return returnInstruction;
    }

    public final StopInstruction Stop() throws ParseException {
        Token tokenJj_consume_token;
        Expression Expression;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 23) {
            tokenJj_consume_token = jj_consume_token(23);
            Expression = Expression();
            LooseDirectiveEnd();
        } else if (iJj_ntk == 47) {
            tokenJj_consume_token = jj_consume_token(47);
            Expression = null;
        } else {
            this.jj_la1[32] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        StopInstruction stopInstruction = new StopInstruction(Expression);
        stopInstruction.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return stopInstruction;
    }

    public final TemplateElement Nested() throws ParseException {
        Token tokenJj_consume_token;
        BodyInstruction bodyInstruction;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 54) {
            tokenJj_consume_token = jj_consume_token(54);
            bodyInstruction = new BodyInstruction(null);
            bodyInstruction.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        } else if (iJj_ntk == 55) {
            tokenJj_consume_token = jj_consume_token(55);
            ArrayList arrayListPositionalArgs = PositionalArgs();
            Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
            bodyInstruction = new BodyInstruction(arrayListPositionalArgs);
            bodyInstruction.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        } else {
            this.jj_la1[33] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        if (this.inMacro) {
            return bodyInstruction;
        }
        throw new ParseException(new StringBuffer("Cannot use a ").append(tokenJj_consume_token.image).append(" instruction outside a macro.").toString(), this.template, tokenJj_consume_token);
    }

    public final TemplateElement Flush() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(48);
        FlushInstruction flushInstruction = new FlushInstruction();
        flushInstruction.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return flushInstruction;
    }

    public final TemplateElement Trim() throws ParseException {
        Token tokenJj_consume_token;
        TrimInstruction trimInstruction;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        switch (iJj_ntk) {
            case 49:
                tokenJj_consume_token = jj_consume_token(49);
                trimInstruction = new TrimInstruction(true, true);
                break;
            case 50:
                tokenJj_consume_token = jj_consume_token(50);
                trimInstruction = new TrimInstruction(true, false);
                break;
            case 51:
                tokenJj_consume_token = jj_consume_token(51);
                trimInstruction = new TrimInstruction(false, true);
                break;
            case 52:
                tokenJj_consume_token = jj_consume_token(52);
                trimInstruction = new TrimInstruction(false, false);
                break;
            default:
                this.jj_la1[34] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        trimInstruction.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token);
        return trimInstruction;
    }

    public final TemplateElement Assign() throws ParseException {
        Token tokenJj_consume_token;
        int i;
        String string;
        Token tokenJj_consume_token2;
        ArrayList arrayList = new ArrayList();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        switch (iJj_ntk) {
            case 14:
                tokenJj_consume_token = jj_consume_token(14);
                i = 1;
                break;
            case 15:
                tokenJj_consume_token = jj_consume_token(15);
                i = 3;
                break;
            case 16:
                tokenJj_consume_token = jj_consume_token(16);
                if (!this.inMacro && !this.inFunction) {
                    throw new ParseException("Local variable assigned outside a macro.", this.template, tokenJj_consume_token);
                }
                i = 2;
                break;
            default:
                this.jj_la1[35] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
        }
        Expression expressionIdentifierOrStringLiteral = IdentifierOrStringLiteral();
        String asString = expressionIdentifierOrStringLiteral instanceof StringLiteral ? ((StringLiteral) expressionIdentifierOrStringLiteral).getAsString() : expressionIdentifierOrStringLiteral.toString();
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        Expression Expression = null;
        if (iJj_ntk2 != 93) {
            if (iJj_ntk2 == 119 || iJj_ntk2 == 126) {
                int iJj_ntk3 = this.jj_ntk;
                if (iJj_ntk3 == -1) {
                    iJj_ntk3 = jj_ntk();
                }
                if (iJj_ntk3 == 119) {
                    Token tokenJj_consume_token3 = jj_consume_token(119);
                    Expression = Expression();
                    if (i != 1) {
                        throw new ParseException("Cannot assign to namespace here.", this.template, tokenJj_consume_token3);
                    }
                } else {
                    this.jj_la1[38] = this.jj_gen;
                }
                Expression expression = Expression;
                jj_consume_token(126);
                TemplateElement templateElementOptionalBlock = OptionalBlock();
                int iJj_ntk4 = this.jj_ntk;
                if (iJj_ntk4 == -1) {
                    iJj_ntk4 = jj_ntk();
                }
                switch (iJj_ntk4) {
                    case 36:
                        tokenJj_consume_token2 = jj_consume_token(36);
                        if (i != 2) {
                            throw new ParseException("Mismatched assignment tags.", this.template, tokenJj_consume_token2);
                        }
                        break;
                    case 37:
                        tokenJj_consume_token2 = jj_consume_token(37);
                        if (i != 3) {
                            throw new ParseException("Mismatched assignment tags", this.template, tokenJj_consume_token2);
                        }
                        break;
                    case 38:
                        tokenJj_consume_token2 = jj_consume_token(38);
                        if (i != 1) {
                            throw new ParseException("Mismatched assignment tags.", this.template, tokenJj_consume_token2);
                        }
                        break;
                    default:
                        this.jj_la1[39] = this.jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                BlockAssignment blockAssignment = new BlockAssignment(templateElementOptionalBlock, asString, i, expression);
                blockAssignment.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
                return blockAssignment;
            }
            this.jj_la1[40] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        jj_consume_token(93);
        Expression Expression2 = Expression();
        Assignment assignment = new Assignment(asString, Expression2, i);
        assignment.setLocation(this.template, expressionIdentifierOrStringLiteral, Expression2);
        arrayList.add(assignment);
        while (jj_2_10(Integer.MAX_VALUE)) {
            int iJj_ntk5 = this.jj_ntk;
            if (iJj_ntk5 == -1) {
                iJj_ntk5 = jj_ntk();
            }
            if (iJj_ntk5 == 110) {
                jj_consume_token(110);
            } else {
                this.jj_la1[36] = this.jj_gen;
            }
            Expression expressionIdentifierOrStringLiteral2 = IdentifierOrStringLiteral();
            if (expressionIdentifierOrStringLiteral2 instanceof StringLiteral) {
                string = ((StringLiteral) expressionIdentifierOrStringLiteral2).getAsString();
            } else {
                string = expressionIdentifierOrStringLiteral2.toString();
            }
            jj_consume_token(93);
            Expression Expression3 = Expression();
            Assignment assignment2 = new Assignment(string, Expression3, i);
            assignment2.setLocation(this.template, expressionIdentifierOrStringLiteral2, Expression3);
            arrayList.add(assignment2);
        }
        int iJj_ntk6 = this.jj_ntk;
        if (iJj_ntk6 == -1) {
            iJj_ntk6 = jj_ntk();
        }
        if (iJj_ntk6 == 119) {
            Token tokenJj_consume_token4 = jj_consume_token(119);
            Expression = Expression();
            if (i != 1) {
                throw new ParseException("Cannot assign to namespace here.", this.template, tokenJj_consume_token4);
            }
        } else {
            this.jj_la1[37] = this.jj_gen;
        }
        Expression expression2 = Expression;
        Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
        AssignmentInstruction assignmentInstruction = new AssignmentInstruction(i);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            assignmentInstruction.addAssignment((Assignment) arrayList.get(i2));
        }
        assignmentInstruction.setNamespaceExp(expression2);
        assignmentInstruction.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        return assignmentInstruction;
    }

    public final Include Include() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(17);
        Expression Expression = Expression();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 111) {
            jj_consume_token(111);
        } else {
            this.jj_la1[41] = this.jj_gen;
        }
        Expression expression = null;
        Expression expression2 = null;
        Expression expression3 = null;
        while (true) {
            int iJj_ntk2 = this.jj_ntk;
            if (iJj_ntk2 == -1) {
                iJj_ntk2 = jj_ntk();
            }
            if (iJj_ntk2 != 122) {
                this.jj_la1[42] = this.jj_gen;
                Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
                Include include = new Include(this.template, Expression, expression, expression2, expression3);
                include.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
                return include;
            }
            Token tokenJj_consume_token2 = jj_consume_token(122);
            jj_consume_token(93);
            Expression Expression2 = Expression();
            String str = tokenJj_consume_token2.image;
            if (str.equalsIgnoreCase("parse")) {
                expression2 = Expression2;
            } else if (str.equalsIgnoreCase("encoding")) {
                expression = Expression2;
            } else {
                if (!str.equalsIgnoreCase("ignore_missing")) {
                    throw new ParseException(new StringBuffer("Unsupported named #include parameter: \"").append(str).append("\". Supported parameters are: \"parse\", \"encoding\", \"ignore_missing\".").toString(), this.template, tokenJj_consume_token2);
                }
                expression3 = Expression2;
            }
        }
    }

    public final LibraryLoad Import() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(18);
        Expression Expression = Expression();
        jj_consume_token(120);
        Token tokenJj_consume_token2 = jj_consume_token(122);
        Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
        LibraryLoad libraryLoad = new LibraryLoad(this.template, Expression, tokenJj_consume_token2.image);
        libraryLoad.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        this.template.addImport(libraryLoad);
        return libraryLoad;
    }

    public final Macro Macro() throws ParseException {
        Token tokenJj_consume_token;
        boolean z;
        Token tokenJj_consume_token2;
        Expression Expression;
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        int iJj_ntk = this.jj_ntk;
        int i = -1;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        boolean z2 = false;
        if (iJj_ntk == 19) {
            tokenJj_consume_token = jj_consume_token(19);
            z = true;
        } else if (iJj_ntk == 20) {
            tokenJj_consume_token = jj_consume_token(20);
            z = false;
        } else {
            this.jj_la1[43] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        if (this.inMacro || this.inFunction) {
            throw new ParseException("Macros cannot be nested.", this.template, tokenJj_consume_token);
        }
        if (z) {
            this.inFunction = true;
        } else {
            this.inMacro = true;
        }
        Expression expressionIdentifierOrStringLiteral = IdentifierOrStringLiteral();
        String asString = expressionIdentifierOrStringLiteral instanceof StringLiteral ? ((StringLiteral) expressionIdentifierOrStringLiteral).getAsString() : expressionIdentifierOrStringLiteral.toString();
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 == 115) {
            jj_consume_token(115);
        } else {
            this.jj_la1[44] = this.jj_gen;
        }
        boolean z3 = false;
        boolean z4 = false;
        String str = null;
        while (true) {
            int iJj_ntk3 = this.jj_ntk;
            if (iJj_ntk3 == i) {
                iJj_ntk3 = jj_ntk();
            }
            if (iJj_ntk3 != 122) {
                this.jj_la1[45] = this.jj_gen;
                int iJj_ntk4 = this.jj_ntk;
                if (iJj_ntk4 == i) {
                    iJj_ntk4 = jj_ntk();
                }
                if (iJj_ntk4 == 116) {
                    jj_consume_token(116);
                } else {
                    this.jj_la1[49] = this.jj_gen;
                }
                jj_consume_token(126);
                TemplateElement templateElementOptionalBlock = OptionalBlock();
                int iJj_ntk5 = this.jj_ntk;
                if (iJj_ntk5 == i) {
                    iJj_ntk5 = jj_ntk();
                }
                if (iJj_ntk5 == 39) {
                    tokenJj_consume_token2 = jj_consume_token(39);
                    if (!z) {
                        throw new ParseException("Expected macro end tag here.", this.template, tokenJj_consume_token);
                    }
                } else if (iJj_ntk5 == 40) {
                    tokenJj_consume_token2 = jj_consume_token(40);
                    if (z) {
                        throw new ParseException("Expected function end tag here.", this.template, tokenJj_consume_token);
                    }
                } else {
                    this.jj_la1[50] = this.jj_gen;
                    jj_consume_token(i);
                    throw new ParseException();
                }
                this.inFunction = z2;
                this.inMacro = z2;
                Macro macro = new Macro(asString, arrayList, map, templateElementOptionalBlock);
                macro.setCatchAll(str);
                macro.isFunction = z;
                macro.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
                this.template.addMacro(macro);
                return macro;
            }
            Token tokenJj_consume_token3 = jj_consume_token(122);
            int iJj_ntk6 = this.jj_ntk;
            if (iJj_ntk6 == i) {
                iJj_ntk6 = jj_ntk();
            }
            if (iJj_ntk6 == 104) {
                jj_consume_token(104);
                z3 = true;
            } else {
                this.jj_la1[46] = this.jj_gen;
            }
            int iJj_ntk7 = this.jj_ntk;
            if (iJj_ntk7 == i) {
                iJj_ntk7 = jj_ntk();
            }
            if (iJj_ntk7 == 93) {
                jj_consume_token(93);
                Expression = Expression();
                arrayList2.add(tokenJj_consume_token3.image);
                z4 = true;
            } else {
                this.jj_la1[47] = this.jj_gen;
                Expression = null;
            }
            int iJj_ntk8 = this.jj_ntk;
            if (iJj_ntk8 == i) {
                iJj_ntk8 = jj_ntk();
            }
            if (iJj_ntk8 == 110) {
                jj_consume_token(110);
            } else {
                this.jj_la1[48] = this.jj_gen;
            }
            if (str != null) {
                throw new ParseException("There may only be one \"catch-all\" parameter in a macro declaration, and it must be the last parameter.", this.template, tokenJj_consume_token3);
            }
            if (!z3) {
                arrayList.add(tokenJj_consume_token3.image);
                if (z4 && Expression == null) {
                    throw new ParseException("In a macro declaration, parameters without a default value must all occur before the parameters with default values.", this.template, tokenJj_consume_token3);
                }
                map.put(tokenJj_consume_token3.image, Expression);
            } else {
                if (Expression != null) {
                    throw new ParseException("\"Catch-all\" macro parameter may not have a default value.", this.template, tokenJj_consume_token3);
                }
                str = tokenJj_consume_token3.image;
            }
            i = -1;
            z2 = false;
        }
    }

    public final CompressedBlock Compress() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(27);
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        Token tokenJj_consume_token2 = jj_consume_token(41);
        CompressedBlock compressedBlock = new CompressedBlock(templateElementOptionalBlock);
        compressedBlock.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
        return compressedBlock;
    }

    public final TemplateElement UnifiedMacroTransform() throws ParseException {
        ArrayList arrayListPositionalArgs;
        HashMap mapNamedArgs;
        ArrayList arrayList;
        Token tokenJj_consume_token;
        Token tokenJj_consume_token2 = jj_consume_token(63);
        Expression Expression = Expression();
        TemplateElement templateElementOptionalBlock = null;
        String canonicalForm = ((Expression instanceof Identifier) || ((Expression instanceof Dot) && ((Dot) Expression).onlyHasIdentifiers())) ? Expression.getCanonicalForm() : null;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 130) {
            jj_consume_token(130);
        } else {
            this.jj_la1[51] = this.jj_gen;
        }
        if (jj_2_11(Integer.MAX_VALUE)) {
            mapNamedArgs = NamedArgs();
            arrayListPositionalArgs = null;
        } else {
            arrayListPositionalArgs = PositionalArgs();
            mapNamedArgs = null;
        }
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 == 111) {
            jj_consume_token(111);
            arrayList = new ArrayList();
            int iJj_ntk3 = this.jj_ntk;
            if (iJj_ntk3 == -1) {
                iJj_ntk3 = jj_ntk();
            }
            if (iJj_ntk3 == 122 || iJj_ntk3 == 130) {
                int iJj_ntk4 = this.jj_ntk;
                if (iJj_ntk4 == -1) {
                    iJj_ntk4 = jj_ntk();
                }
                if (iJj_ntk4 == 130) {
                    jj_consume_token(130);
                } else {
                    this.jj_la1[52] = this.jj_gen;
                }
                arrayList.add(jj_consume_token(122).image);
                while (true) {
                    int iJj_ntk5 = this.jj_ntk;
                    if (iJj_ntk5 == -1) {
                        iJj_ntk5 = jj_ntk();
                    }
                    if (iJj_ntk5 != 110 && iJj_ntk5 != 130) {
                        break;
                    }
                    int iJj_ntk6 = this.jj_ntk;
                    if (iJj_ntk6 == -1) {
                        iJj_ntk6 = jj_ntk();
                    }
                    if (iJj_ntk6 == 130) {
                        jj_consume_token(130);
                    } else {
                        this.jj_la1[54] = this.jj_gen;
                    }
                    jj_consume_token(110);
                    int iJj_ntk7 = this.jj_ntk;
                    if (iJj_ntk7 == -1) {
                        iJj_ntk7 = jj_ntk();
                    }
                    if (iJj_ntk7 == 130) {
                        jj_consume_token(130);
                    } else {
                        this.jj_la1[55] = this.jj_gen;
                    }
                    arrayList.add(jj_consume_token(122).image);
                }
                this.jj_la1[53] = this.jj_gen;
            } else {
                this.jj_la1[56] = this.jj_gen;
            }
        } else {
            this.jj_la1[57] = this.jj_gen;
            arrayList = null;
        }
        int iJj_ntk8 = this.jj_ntk;
        if (iJj_ntk8 == -1) {
            iJj_ntk8 = jj_ntk();
        }
        if (iJj_ntk8 == 126) {
            jj_consume_token(126);
            templateElementOptionalBlock = OptionalBlock();
            Token tokenJj_consume_token3 = jj_consume_token(64);
            String strTrim = tokenJj_consume_token3.image.substring(3).substring(0, r6.length() - 1).trim();
            if (strTrim.length() > 0 && !strTrim.equals(canonicalForm)) {
                if (canonicalForm == null) {
                    throw new ParseException("Expecting </@>", this.template, tokenJj_consume_token3);
                }
                throw new ParseException(new StringBuffer("Expecting </@> or </@").append(canonicalForm).append(">").toString(), this.template, tokenJj_consume_token3);
            }
            tokenJj_consume_token = tokenJj_consume_token3;
        } else if (iJj_ntk8 == 127) {
            tokenJj_consume_token = jj_consume_token(127);
        } else {
            this.jj_la1[58] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        UnifiedCall unifiedCall = arrayListPositionalArgs != null ? new UnifiedCall(Expression, arrayListPositionalArgs, templateElementOptionalBlock, arrayList) : new UnifiedCall(Expression, mapNamedArgs, templateElementOptionalBlock, arrayList);
        unifiedCall.setLocation(this.template, tokenJj_consume_token2, tokenJj_consume_token);
        return unifiedCall;
    }

    public final TemplateElement Call() throws ParseException {
        ArrayList arrayListPositionalArgs;
        HashMap mapNamedArgs;
        UnifiedCall unifiedCall;
        Token tokenJj_consume_token = jj_consume_token(25);
        String str = jj_consume_token(122).image;
        if (jj_2_13(Integer.MAX_VALUE)) {
            mapNamedArgs = NamedArgs();
            arrayListPositionalArgs = null;
        } else {
            if (jj_2_12(Integer.MAX_VALUE)) {
                jj_consume_token(115);
            }
            arrayListPositionalArgs = PositionalArgs();
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            if (iJj_ntk == 116) {
                jj_consume_token(116);
            } else {
                this.jj_la1[59] = this.jj_gen;
            }
            mapNamedArgs = null;
        }
        Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
        if (arrayListPositionalArgs != null) {
            unifiedCall = new UnifiedCall(new Identifier(str), arrayListPositionalArgs, (TemplateElement) null, (List) null);
        } else {
            unifiedCall = new UnifiedCall(new Identifier(str), mapNamedArgs, (TemplateElement) null, (List) null);
        }
        unifiedCall.legacySyntax = true;
        unifiedCall.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        return unifiedCall;
    }

    public final HashMap NamedArgs() throws ParseException {
        int iJj_ntk;
        HashMap map = new HashMap();
        do {
            Token tokenJj_consume_token = jj_consume_token(122);
            jj_consume_token(93);
            this.token_source.SwitchTo(4);
            this.token_source.inInvocation = true;
            map.put(tokenJj_consume_token.image, Expression());
            iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
        } while (iJj_ntk == 122);
        this.jj_la1[60] = this.jj_gen;
        this.token_source.inInvocation = false;
        return map;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.ArrayList PositionalArgs() throws org.mapstruct.ap.shaded.freemarker.core.ParseException {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int r1 = r12.jj_ntk
            r2 = -1
            if (r1 != r2) goto Le
            int r1 = r12.jj_ntk()
        Le:
            r3 = 122(0x7a, float:1.71E-43)
            r4 = 117(0x75, float:1.64E-43)
            r5 = 115(0x73, float:1.61E-43)
            r6 = 113(0x71, float:1.58E-43)
            r7 = 109(0x6d, float:1.53E-43)
            r8 = 101(0x65, float:1.42E-43)
            r9 = 100
            if (r1 == r9) goto L36
            if (r1 == r8) goto L36
            if (r1 == r7) goto L36
            if (r1 == r6) goto L36
            if (r1 == r5) goto L36
            if (r1 == r4) goto L36
            if (r1 == r3) goto L36
            switch(r1) {
                case 81: goto L36;
                case 82: goto L36;
                case 83: goto L36;
                case 84: goto L36;
                case 85: goto L36;
                case 86: goto L36;
                case 87: goto L36;
                default: goto L2d;
            }
        L2d:
            int[] r1 = r12.jj_la1
            r2 = 63
            int r3 = r12.jj_gen
            r1[r2] = r3
            goto L62
        L36:
            org.mapstruct.ap.shaded.freemarker.core.Expression r1 = r12.Expression()
            r0.add(r1)
        L3d:
            int r1 = r12.jj_ntk
            if (r1 != r2) goto L45
            int r1 = r12.jj_ntk()
        L45:
            r10 = 110(0x6e, float:1.54E-43)
            if (r1 == r9) goto L63
            if (r1 == r8) goto L63
            if (r1 == r7) goto L63
            if (r1 == r10) goto L63
            if (r1 == r6) goto L63
            if (r1 == r5) goto L63
            if (r1 == r4) goto L63
            if (r1 == r3) goto L63
            switch(r1) {
                case 81: goto L63;
                case 82: goto L63;
                case 83: goto L63;
                case 84: goto L63;
                case 85: goto L63;
                case 86: goto L63;
                case 87: goto L63;
                default: goto L5a;
            }
        L5a:
            int[] r1 = r12.jj_la1
            r2 = 61
            int r3 = r12.jj_gen
            r1[r2] = r3
        L62:
            return r0
        L63:
            int r1 = r12.jj_ntk
            if (r1 != r2) goto L6b
            int r1 = r12.jj_ntk()
        L6b:
            if (r1 == r10) goto L76
            int[] r1 = r12.jj_la1
            r10 = 62
            int r11 = r12.jj_gen
            r1[r10] = r11
            goto L79
        L76:
            r12.jj_consume_token(r10)
        L79:
            org.mapstruct.ap.shaded.freemarker.core.Expression r1 = r12.Expression()
            r0.add(r1)
            goto L3d
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParser.PositionalArgs():java.util.ArrayList");
    }

    public final Comment Comment() throws ParseException {
        Token tokenJj_consume_token;
        StringBuffer stringBuffer = new StringBuffer();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 28) {
            tokenJj_consume_token = jj_consume_token(28);
        } else if (iJj_ntk == 29) {
            tokenJj_consume_token = jj_consume_token(29);
        } else {
            this.jj_la1[64] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        Token tokenUnparsedContent = UnparsedContent(tokenJj_consume_token, stringBuffer);
        Comment comment = new Comment(stringBuffer.toString());
        comment.setLocation(this.template, tokenJj_consume_token, tokenUnparsedContent);
        return comment;
    }

    public final TextBlock NoParse() throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        Token tokenJj_consume_token = jj_consume_token(30);
        Token tokenUnparsedContent = UnparsedContent(tokenJj_consume_token, stringBuffer);
        TextBlock textBlock = new TextBlock(stringBuffer.toString(), true);
        textBlock.setLocation(this.template, tokenJj_consume_token, tokenUnparsedContent);
        return textBlock;
    }

    public final TransformBlock Transform() throws ParseException {
        Token tokenJj_consume_token;
        Token tokenJj_consume_token2 = jj_consume_token(21);
        Expression Expression = Expression();
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 111) {
            jj_consume_token(111);
        } else {
            this.jj_la1[65] = this.jj_gen;
        }
        TemplateElement templateElementOptionalBlock = null;
        HashMap map = null;
        while (true) {
            int iJj_ntk2 = this.jj_ntk;
            if (iJj_ntk2 == -1) {
                iJj_ntk2 = jj_ntk();
            }
            if (iJj_ntk2 != 122) {
                break;
            }
            Token tokenJj_consume_token3 = jj_consume_token(122);
            jj_consume_token(93);
            Expression Expression2 = Expression();
            if (map == null) {
                map = new HashMap();
            }
            map.put(tokenJj_consume_token3.image, Expression2);
        }
        this.jj_la1[66] = this.jj_gen;
        int iJj_ntk3 = this.jj_ntk;
        if (iJj_ntk3 == -1) {
            iJj_ntk3 = jj_ntk();
        }
        if (iJj_ntk3 == 126) {
            jj_consume_token(126);
            templateElementOptionalBlock = OptionalBlock();
            tokenJj_consume_token = jj_consume_token(42);
        } else if (iJj_ntk3 == 127) {
            tokenJj_consume_token = jj_consume_token(127);
        } else {
            this.jj_la1[67] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        TransformBlock transformBlock = new TransformBlock(Expression, map, templateElementOptionalBlock);
        transformBlock.setLocation(this.template, tokenJj_consume_token2, tokenJj_consume_token);
        return transformBlock;
    }

    public final SwitchBlock Switch() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(12);
        Expression Expression = Expression();
        jj_consume_token(126);
        this.switchNesting++;
        SwitchBlock switchBlock = new SwitchBlock(Expression);
        boolean z = false;
        while (jj_2_14(2)) {
            Case Case = Case();
            if (Case.condition == null) {
                if (z) {
                    throw new ParseException("You can only have one default case in a switch statement", this.template, tokenJj_consume_token);
                }
                z = true;
            }
            switchBlock.addCase(Case);
        }
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 68) {
            jj_consume_token(68);
        } else {
            this.jj_la1[68] = this.jj_gen;
        }
        Token tokenJj_consume_token2 = jj_consume_token(43);
        this.switchNesting--;
        switchBlock.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
        return switchBlock;
    }

    public final Case Case() throws ParseException {
        Token tokenJj_consume_token;
        Expression Expression;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 68) {
            jj_consume_token(68);
        } else {
            this.jj_la1[69] = this.jj_gen;
        }
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 == 13) {
            tokenJj_consume_token = jj_consume_token(13);
            Expression = Expression();
            jj_consume_token(126);
        } else if (iJj_ntk2 == 53) {
            tokenJj_consume_token = jj_consume_token(53);
            Expression = null;
        } else {
            this.jj_la1[70] = this.jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        Case r3 = new Case(Expression, templateElementOptionalBlock);
        r3.setLocation(this.template, tokenJj_consume_token, templateElementOptionalBlock);
        return r3;
    }

    public final EscapeBlock Escape() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(59);
        Token tokenJj_consume_token2 = jj_consume_token(122);
        jj_consume_token(120);
        Expression Expression = Expression();
        jj_consume_token(126);
        EscapeBlock escapeBlock = new EscapeBlock(tokenJj_consume_token2.image, Expression, escapedExpression(Expression));
        this.escapes.addFirst(escapeBlock);
        escapeBlock.setContent(OptionalBlock());
        this.escapes.removeFirst();
        escapeBlock.setLocation(this.template, tokenJj_consume_token, jj_consume_token(60));
        return escapeBlock;
    }

    public final NoEscapeBlock NoEscape() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(61);
        if (this.escapes.isEmpty()) {
            throw new ParseException("noescape with no matching escape encountered.", this.template, tokenJj_consume_token);
        }
        Object objRemoveFirst = this.escapes.removeFirst();
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        Token tokenJj_consume_token2 = jj_consume_token(62);
        this.escapes.addFirst(objRemoveFirst);
        NoEscapeBlock noEscapeBlock = new NoEscapeBlock(templateElementOptionalBlock);
        noEscapeBlock.setLocation(this.template, tokenJj_consume_token, tokenJj_consume_token2);
        return noEscapeBlock;
    }

    public final Token LooseDirectiveEnd() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 126) {
            return jj_consume_token(126);
        }
        if (iJj_ntk == 127) {
            return jj_consume_token(127);
        }
        this.jj_la1[71] = this.jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
    }

    public final PropertySetting Setting() throws ParseException {
        Token tokenJj_consume_token = jj_consume_token(26);
        Token tokenJj_consume_token2 = jj_consume_token(122);
        jj_consume_token(93);
        Expression Expression = Expression();
        Token tokenLooseDirectiveEnd = LooseDirectiveEnd();
        PropertySetting propertySetting = new PropertySetting(tokenJj_consume_token2.image, Expression);
        propertySetting.setLocation(this.template, tokenJj_consume_token, tokenLooseDirectiveEnd);
        return propertySetting;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final org.mapstruct.ap.shaded.freemarker.core.TemplateElement FreemarkerDirective() throws org.mapstruct.ap.shaded.freemarker.core.ParseException {
        /*
            r4 = this;
            int r0 = r4.jj_ntk
            r1 = -1
            if (r0 != r1) goto L9
            int r0 = r4.jj_ntk()
        L9:
            r2 = 6
            if (r0 == r2) goto Lbf
            r2 = 8
            if (r0 == r2) goto Lba
            r2 = 61
            if (r0 == r2) goto Lb5
            r2 = 63
            if (r0 == r2) goto Lb0
            switch(r0) {
                case 10: goto Lab;
                case 11: goto La6;
                case 12: goto La1;
                default: goto L1b;
            }
        L1b:
            switch(r0) {
                case 14: goto L9c;
                case 15: goto L9c;
                case 16: goto L9c;
                case 17: goto L97;
                case 18: goto L92;
                case 19: goto L8d;
                case 20: goto L8d;
                case 21: goto L88;
                case 22: goto L83;
                case 23: goto L7e;
                case 24: goto L79;
                case 25: goto L74;
                case 26: goto L6f;
                case 27: goto L6a;
                case 28: goto L65;
                case 29: goto L65;
                case 30: goto L5f;
                default: goto L1e;
            }
        L1e:
            switch(r0) {
                case 45: goto L59;
                case 46: goto L79;
                case 47: goto L7e;
                case 48: goto L53;
                case 49: goto L4d;
                case 50: goto L4d;
                case 51: goto L4d;
                case 52: goto L4d;
                default: goto L21;
            }
        L21:
            switch(r0) {
                case 54: goto L47;
                case 55: goto L47;
                case 56: goto L41;
                case 57: goto L41;
                case 58: goto L3b;
                case 59: goto L35;
                default: goto L24;
            }
        L24:
            int[] r0 = r4.jj_la1
            r2 = 72
            int r3 = r4.jj_gen
            r0[r2] = r3
            r4.jj_consume_token(r1)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r0 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r0.<init>()
            throw r0
        L35:
            org.mapstruct.ap.shaded.freemarker.core.EscapeBlock r0 = r4.Escape()
            goto Lc3
        L3b:
            org.mapstruct.ap.shaded.freemarker.core.FallbackInstruction r0 = r4.FallBack()
            goto Lc3
        L41:
            org.mapstruct.ap.shaded.freemarker.core.RecurseNode r0 = r4.Recurse()
            goto Lc3
        L47:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.Nested()
            goto Lc3
        L4d:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.Trim()
            goto Lc3
        L53:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.Flush()
            goto Lc3
        L59:
            org.mapstruct.ap.shaded.freemarker.core.BreakInstruction r0 = r4.Break()
            goto Lc3
        L5f:
            org.mapstruct.ap.shaded.freemarker.core.TextBlock r0 = r4.NoParse()
            goto Lc3
        L65:
            org.mapstruct.ap.shaded.freemarker.core.Comment r0 = r4.Comment()
            goto Lc3
        L6a:
            org.mapstruct.ap.shaded.freemarker.core.CompressedBlock r0 = r4.Compress()
            goto Lc3
        L6f:
            org.mapstruct.ap.shaded.freemarker.core.PropertySetting r0 = r4.Setting()
            goto Lc3
        L74:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.Call()
            goto Lc3
        L79:
            org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction r0 = r4.Return()
            goto Lc3
        L7e:
            org.mapstruct.ap.shaded.freemarker.core.StopInstruction r0 = r4.Stop()
            goto Lc3
        L83:
            org.mapstruct.ap.shaded.freemarker.core.VisitNode r0 = r4.Visit()
            goto Lc3
        L88:
            org.mapstruct.ap.shaded.freemarker.core.TransformBlock r0 = r4.Transform()
            goto Lc3
        L8d:
            org.mapstruct.ap.shaded.freemarker.core.Macro r0 = r4.Macro()
            goto Lc3
        L92:
            org.mapstruct.ap.shaded.freemarker.core.LibraryLoad r0 = r4.Import()
            goto Lc3
        L97:
            org.mapstruct.ap.shaded.freemarker.core.Include r0 = r4.Include()
            goto Lc3
        L9c:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.Assign()
            goto Lc3
        La1:
            org.mapstruct.ap.shaded.freemarker.core.SwitchBlock r0 = r4.Switch()
            goto Lc3
        La6:
            org.mapstruct.ap.shaded.freemarker.core.IteratorBlock r0 = r4.ForEach()
            goto Lc3
        Lab:
            org.mapstruct.ap.shaded.freemarker.core.IteratorBlock r0 = r4.List()
            goto Lc3
        Lb0:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.UnifiedMacroTransform()
            goto Lc3
        Lb5:
            org.mapstruct.ap.shaded.freemarker.core.NoEscapeBlock r0 = r4.NoEscape()
            goto Lc3
        Lba:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r0 = r4.If()
            goto Lc3
        Lbf:
            org.mapstruct.ap.shaded.freemarker.core.AttemptBlock r0 = r4.Attempt()
        Lc3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParser.FreemarkerDirective():org.mapstruct.ap.shaded.freemarker.core.TemplateElement");
    }

    public final TextBlock PCData() throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        Token tokenJj_consume_token = null;
        Token token = null;
        Token token2 = null;
        do {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            switch (iJj_ntk) {
                case 68:
                    token2 = tokenJj_consume_token;
                    tokenJj_consume_token = jj_consume_token(68);
                    break;
                case 69:
                    tokenJj_consume_token = jj_consume_token(69);
                    break;
                case 70:
                    tokenJj_consume_token = jj_consume_token(70);
                    break;
                default:
                    this.jj_la1[73] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            stringBuffer.append(tokenJj_consume_token.image);
            if (token == null) {
                token = tokenJj_consume_token;
            }
            if (token2 != null) {
                token2.next = null;
            }
        } while (jj_2_15(Integer.MAX_VALUE));
        if (this.stripText && this.mixedContentNesting == 1) {
            return TextBlock.EMPTY_BLOCK;
        }
        TextBlock textBlock = new TextBlock(stringBuffer.toString(), false);
        textBlock.setLocation(this.template, token, tokenJj_consume_token);
        return textBlock;
    }

    public final Token UnparsedContent(Token token, StringBuffer stringBuffer) throws ParseException {
        Token tokenJj_consume_token;
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            switch (iJj_ntk) {
                case FMParserConstants.TERSE_COMMENT_END /* 132 */:
                    tokenJj_consume_token = jj_consume_token(FMParserConstants.TERSE_COMMENT_END);
                    break;
                case 133:
                    tokenJj_consume_token = jj_consume_token(133);
                    break;
                case 134:
                    tokenJj_consume_token = jj_consume_token(134);
                    break;
                case 135:
                    tokenJj_consume_token = jj_consume_token(135);
                    break;
                default:
                    this.jj_la1[74] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            stringBuffer.append(tokenJj_consume_token.image);
            int iJj_ntk2 = this.jj_ntk;
            if (iJj_ntk2 == -1) {
                iJj_ntk2 = jj_ntk();
            }
            switch (iJj_ntk2) {
                case FMParserConstants.TERSE_COMMENT_END /* 132 */:
                case 133:
                case 134:
                case 135:
                default:
                    this.jj_la1[75] = this.jj_gen;
                    stringBuffer.setLength(stringBuffer.length() - tokenJj_consume_token.image.length());
                    if (tokenJj_consume_token.image.endsWith(";") || _TemplateAPI.getTemplateLanguageVersionAsInt(this.template) < _TemplateAPI.VERSION_INT_2_3_21) {
                        return tokenJj_consume_token;
                    }
                    throw new ParseException(new StringBuffer("Unclosed \"").append(token.image).append("\"").toString(), this.template, token);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0053 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final org.mapstruct.ap.shaded.freemarker.core.MixedContent MixedContent() throws org.mapstruct.ap.shaded.freemarker.core.ParseException, java.lang.NumberFormatException {
        /*
            r9 = this;
            org.mapstruct.ap.shaded.freemarker.core.MixedContent r0 = new org.mapstruct.ap.shaded.freemarker.core.MixedContent
            r0.<init>()
            int r1 = r9.mixedContentNesting
            int r1 = r1 + 1
            r9.mixedContentNesting = r1
            r1 = 0
        Lc:
            int r2 = r9.jj_ntk
            r3 = -1
            if (r2 != r3) goto L15
            int r2 = r9.jj_ntk()
        L15:
            r4 = 63
            r5 = 61
            r6 = 8
            r7 = 6
            if (r2 == r7) goto L53
            if (r2 == r6) goto L53
            if (r2 == r5) goto L53
            if (r2 == r4) goto L53
            switch(r2) {
                case 10: goto L53;
                case 11: goto L53;
                case 12: goto L53;
                default: goto L27;
            }
        L27:
            switch(r2) {
                case 14: goto L53;
                case 15: goto L53;
                case 16: goto L53;
                case 17: goto L53;
                case 18: goto L53;
                case 19: goto L53;
                case 20: goto L53;
                case 21: goto L53;
                case 22: goto L53;
                case 23: goto L53;
                case 24: goto L53;
                case 25: goto L53;
                case 26: goto L53;
                case 27: goto L53;
                case 28: goto L53;
                case 29: goto L53;
                case 30: goto L53;
                default: goto L2a;
            }
        L2a:
            switch(r2) {
                case 45: goto L53;
                case 46: goto L53;
                case 47: goto L53;
                case 48: goto L53;
                case 49: goto L53;
                case 50: goto L53;
                case 51: goto L53;
                case 52: goto L53;
                default: goto L2d;
            }
        L2d:
            switch(r2) {
                case 54: goto L53;
                case 55: goto L53;
                case 56: goto L53;
                case 57: goto L53;
                case 58: goto L53;
                case 59: goto L53;
                default: goto L30;
            }
        L30:
            switch(r2) {
                case 68: goto L4e;
                case 69: goto L4e;
                case 70: goto L4e;
                case 71: goto L49;
                case 72: goto L44;
                default: goto L33;
            }
        L33:
            int[] r0 = r9.jj_la1
            r1 = 76
            int r2 = r9.jj_gen
            r0[r1] = r2
            r9.jj_consume_token(r3)
            org.mapstruct.ap.shaded.freemarker.core.ParseException r0 = new org.mapstruct.ap.shaded.freemarker.core.ParseException
            r0.<init>()
            throw r0
        L44:
            org.mapstruct.ap.shaded.freemarker.core.NumericalOutput r2 = r9.NumericalOutput()
            goto L57
        L49:
            org.mapstruct.ap.shaded.freemarker.core.DollarVariable r2 = r9.StringOutput()
            goto L57
        L4e:
            org.mapstruct.ap.shaded.freemarker.core.TextBlock r2 = r9.PCData()
            goto L57
        L53:
            org.mapstruct.ap.shaded.freemarker.core.TemplateElement r2 = r9.FreemarkerDirective()
        L57:
            if (r1 != 0) goto L5a
            r1 = r2
        L5a:
            r0.addElement(r2)
            int r8 = r9.jj_ntk
            if (r8 != r3) goto L65
            int r8 = r9.jj_ntk()
        L65:
            if (r8 == r7) goto Lc
            if (r8 == r6) goto Lc
            if (r8 == r5) goto Lc
            if (r8 == r4) goto Lc
            switch(r8) {
                case 10: goto Lc;
                case 11: goto Lc;
                case 12: goto Lc;
                default: goto L70;
            }
        L70:
            switch(r8) {
                case 14: goto Lc;
                case 15: goto Lc;
                case 16: goto Lc;
                case 17: goto Lc;
                case 18: goto Lc;
                case 19: goto Lc;
                case 20: goto Lc;
                case 21: goto Lc;
                case 22: goto Lc;
                case 23: goto Lc;
                case 24: goto Lc;
                case 25: goto Lc;
                case 26: goto Lc;
                case 27: goto Lc;
                case 28: goto Lc;
                case 29: goto Lc;
                case 30: goto Lc;
                default: goto L73;
            }
        L73:
            switch(r8) {
                case 45: goto Lc;
                case 46: goto Lc;
                case 47: goto Lc;
                case 48: goto Lc;
                case 49: goto Lc;
                case 50: goto Lc;
                case 51: goto Lc;
                case 52: goto Lc;
                default: goto L76;
            }
        L76:
            switch(r8) {
                case 54: goto Lc;
                case 55: goto Lc;
                case 56: goto Lc;
                case 57: goto Lc;
                case 58: goto Lc;
                case 59: goto Lc;
                default: goto L79;
            }
        L79:
            switch(r8) {
                case 68: goto Lc;
                case 69: goto Lc;
                case 70: goto Lc;
                case 71: goto Lc;
                case 72: goto Lc;
                default: goto L7c;
            }
        L7c:
            int[] r3 = r9.jj_la1
            r4 = 77
            int r5 = r9.jj_gen
            r3[r4] = r5
            int r3 = r9.mixedContentNesting
            int r3 = r3 + (-1)
            r9.mixedContentNesting = r3
            org.mapstruct.ap.shaded.freemarker.template.Template r3 = r9.template
            r0.setLocation(r3, r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.shaded.freemarker.core.FMParser.MixedContent():org.mapstruct.ap.shaded.freemarker.core.MixedContent");
    }

    public final TemplateElement FreeMarkerText() throws ParseException, NumberFormatException {
        TemplateElement templateElementPCData;
        MixedContent mixedContent = new MixedContent();
        TemplateElement templateElement = null;
        while (true) {
            int iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
            switch (iJj_ntk) {
                case 68:
                case 69:
                case 70:
                    templateElementPCData = PCData();
                    break;
                case 71:
                    templateElementPCData = StringOutput();
                    break;
                case 72:
                    templateElementPCData = NumericalOutput();
                    break;
                default:
                    this.jj_la1[78] = this.jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            if (templateElement == null) {
                templateElement = templateElementPCData;
            }
            mixedContent.addElement(templateElementPCData);
            int iJj_ntk2 = this.jj_ntk;
            if (iJj_ntk2 == -1) {
                iJj_ntk2 = jj_ntk();
            }
            switch (iJj_ntk2) {
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                default:
                    this.jj_la1[79] = this.jj_gen;
                    mixedContent.setLocation(this.template, templateElement, templateElementPCData);
                    return mixedContent;
            }
        }
    }

    public final TemplateElement OptionalBlock() throws ParseException {
        TextBlock textBlock = TextBlock.EMPTY_BLOCK;
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk != 6 && iJj_ntk != 8 && iJj_ntk != 61 && iJj_ntk != 63) {
            switch (iJj_ntk) {
                case 10:
                case 11:
                case 12:
                    break;
                default:
                    switch (iJj_ntk) {
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                            break;
                        default:
                            switch (iJj_ntk) {
                                case 45:
                                case 46:
                                case 47:
                                case 48:
                                case 49:
                                case 50:
                                case 51:
                                case 52:
                                    break;
                                default:
                                    switch (iJj_ntk) {
                                        case 54:
                                        case 55:
                                        case 56:
                                        case 57:
                                        case 58:
                                        case 59:
                                            break;
                                        default:
                                            switch (iJj_ntk) {
                                                case 68:
                                                case 69:
                                                case 70:
                                                case 71:
                                                case 72:
                                                    break;
                                                default:
                                                    this.jj_la1[80] = this.jj_gen;
                                                    return textBlock;
                                            }
                                    }
                            }
                    }
            }
        }
        return MixedContent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void HeaderElement() throws ParseException {
        int iJj_ntk = this.jj_ntk;
        if (iJj_ntk == -1) {
            iJj_ntk = jj_ntk();
        }
        if (iJj_ntk == 68) {
            jj_consume_token(68);
        } else {
            this.jj_la1[81] = this.jj_gen;
        }
        int iJj_ntk2 = this.jj_ntk;
        if (iJj_ntk2 == -1) {
            iJj_ntk2 = jj_ntk();
        }
        if (iJj_ntk2 != 65) {
            if (iJj_ntk2 == 66) {
                jj_consume_token(66);
                return;
            } else {
                this.jj_la1[83] = this.jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        jj_consume_token(65);
        while (true) {
            int iJj_ntk3 = this.jj_ntk;
            if (iJj_ntk3 == -1) {
                iJj_ntk3 = jj_ntk();
            }
            if (iJj_ntk3 != 122) {
                this.jj_la1[82] = this.jj_gen;
                LooseDirectiveEnd();
                return;
            }
            Token tokenJj_consume_token = jj_consume_token(122);
            jj_consume_token(93);
            Expression Expression = Expression();
            String str = tokenJj_consume_token.image;
            String asString = null;
            try {
                TemplateModel templateModelEval = Expression.eval(null);
                if (templateModelEval instanceof TemplateScalarModel) {
                    try {
                        asString = ((TemplateScalarModel) Expression).getAsString();
                    } catch (TemplateModelException unused) {
                    }
                }
                if (this.template != null) {
                    if (str.equalsIgnoreCase("encoding")) {
                        if (asString == null) {
                            throw new ParseException("Expecting an encoding string.", Expression);
                        }
                        String encoding = this.template.getEncoding();
                        if (encoding != null && !encoding.equalsIgnoreCase(asString)) {
                            throw new Template.WrongEncodingException(asString);
                        }
                    } else if (str.equalsIgnoreCase("STRIP_WHITESPACE")) {
                        this.stripWhitespace = getBoolean(Expression);
                    } else if (str.equalsIgnoreCase("STRIP_TEXT")) {
                        this.stripText = getBoolean(Expression);
                    } else if (str.equalsIgnoreCase("STRICT_SYNTAX")) {
                        this.token_source.strictEscapeSyntax = getBoolean(Expression);
                    } else if (str.equalsIgnoreCase("ns_prefixes")) {
                        if (!(templateModelEval instanceof TemplateHashModelEx)) {
                            throw new ParseException("Expecting a hash of prefixes to namespace URI's.", Expression);
                        }
                        TemplateHashModelEx templateHashModelEx = (TemplateHashModelEx) templateModelEval;
                        try {
                            TemplateModelIterator it = templateHashModelEx.keys().iterator();
                            while (it.hasNext()) {
                                String asString2 = ((TemplateScalarModel) it.next()).getAsString();
                                TemplateModel templateModel = templateHashModelEx.get(asString2);
                                if (!(templateModel instanceof TemplateScalarModel)) {
                                    throw new ParseException("Non-string value in prefix to namespace hash.", Expression);
                                }
                                try {
                                    this.template.addPrefixNSMapping(asString2, ((TemplateScalarModel) templateModel).getAsString());
                                } catch (IllegalArgumentException e) {
                                    throw new ParseException(e.getMessage(), Expression);
                                }
                            }
                        } catch (TemplateModelException unused2) {
                        }
                    } else if (str.equalsIgnoreCase("attributes")) {
                        if (!(templateModelEval instanceof TemplateHashModelEx)) {
                            throw new ParseException("Expecting a hash of attribute names to values.", Expression);
                        }
                        TemplateHashModelEx templateHashModelEx2 = (TemplateHashModelEx) templateModelEval;
                        TemplateModelIterator it2 = templateHashModelEx2.keys().iterator();
                        while (it2.hasNext()) {
                            String asString3 = ((TemplateScalarModel) it2.next()).getAsString();
                            this.template.setCustomAttribute(asString3, DeepUnwrap.unwrap(templateHashModelEx2.get(asString3)));
                        }
                    } else {
                        throw new ParseException(new StringBuffer("Unknown FTL header parameter: ").append(tokenJj_consume_token.image).toString(), this.template, tokenJj_consume_token);
                    }
                }
            } catch (Exception e2) {
                throw new ParseException(new StringBuffer("Could not evaluate expression: ").append(Expression.getCanonicalForm()).append(" \nUnderlying cause: ").append(e2.getMessage()).toString(), Expression, e2);
            }
        }
    }

    public final Map ParamList() throws ParseException {
        int iJj_ntk;
        HashMap map = new HashMap();
        do {
            Identifier Identifier = Identifier();
            jj_consume_token(93);
            map.put(Identifier.toString(), Expression());
            int iJj_ntk2 = this.jj_ntk;
            if (iJj_ntk2 == -1) {
                iJj_ntk2 = jj_ntk();
            }
            if (iJj_ntk2 == 110) {
                jj_consume_token(110);
            } else {
                this.jj_la1[84] = this.jj_gen;
            }
            iJj_ntk = this.jj_ntk;
            if (iJj_ntk == -1) {
                iJj_ntk = jj_ntk();
            }
        } while (iJj_ntk == 122);
        this.jj_la1[85] = this.jj_gen;
        return map;
    }

    public final TemplateElement Root() throws ParseException {
        if (jj_2_16(Integer.MAX_VALUE)) {
            HeaderElement();
        }
        TemplateElement templateElementOptionalBlock = OptionalBlock();
        jj_consume_token(0);
        templateElementOptionalBlock.setParentRecursively(null);
        return templateElementOptionalBlock.postParseCleanup(this.stripWhitespace);
    }

    private final boolean jj_2_1(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_1();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(0, i);
        }
    }

    private final boolean jj_2_2(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_2();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(1, i);
        }
    }

    private final boolean jj_2_3(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_3();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(2, i);
        }
    }

    private final boolean jj_2_4(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_4();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(3, i);
        }
    }

    private final boolean jj_2_5(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_5();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(4, i);
        }
    }

    private final boolean jj_2_6(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_6();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(5, i);
        }
    }

    private final boolean jj_2_7(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_7();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(6, i);
        }
    }

    private final boolean jj_2_8(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_8();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(7, i);
        }
    }

    private final boolean jj_2_9(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_9();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(8, i);
        }
    }

    private final boolean jj_2_10(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_10();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(9, i);
        }
    }

    private final boolean jj_2_11(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_11();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(10, i);
        }
    }

    private final boolean jj_2_12(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_12();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(11, i);
        }
    }

    private final boolean jj_2_13(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_13();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(12, i);
        }
    }

    private final boolean jj_2_14(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_14();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(13, i);
        }
    }

    private final boolean jj_2_15(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_15();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(14, i);
        }
    }

    private final boolean jj_2_16(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return true ^ jj_3_16();
        } catch (LookaheadSuccess unused) {
            return true;
        } finally {
            jj_save(15, i);
        }
    }

    private final boolean jj_3R_45() {
        if (jj_3R_51()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (!jj_3R_52()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_144() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(20)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_171();
    }

    private final boolean jj_3R_159() {
        return jj_scan_token(59);
    }

    private final boolean jj_3R_161() {
        return jj_scan_token(22);
    }

    private final boolean jj_3R_44() {
        return jj_scan_token(106);
    }

    private final boolean jj_3R_43() {
        return jj_scan_token(105);
    }

    private final boolean jj_3R_42() {
        return jj_scan_token(102);
    }

    private final boolean jj_3_3() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(102)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(105)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(106);
    }

    private final boolean jj_3R_166() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(96)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(97)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(98)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(99)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(83)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(84)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(119)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(120)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(121);
    }

    private final boolean jj_3R_30() {
        return jj_scan_token(53);
    }

    private final boolean jj_3R_29() {
        return jj_scan_token(13) || jj_3R_24();
    }

    private final boolean jj_3R_33() {
        Token token = this.jj_scanpos;
        if (jj_3R_42()) {
            this.jj_scanpos = token;
            if (jj_3R_43()) {
                this.jj_scanpos = token;
                if (jj_3R_44()) {
                    return true;
                }
            }
        }
        return jj_3R_32();
    }

    private final boolean jj_3R_128() {
        if (jj_scan_token(87)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (!jj_scan_token(122)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(102)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(103)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_166();
    }

    private final boolean jj_3R_25() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(68)) {
            this.jj_scanpos = token;
        }
        Token token2 = this.jj_scanpos;
        if (jj_3R_29()) {
            this.jj_scanpos = token2;
            if (jj_3R_30()) {
                return true;
            }
        }
        return jj_3R_31();
    }

    private final boolean jj_3R_143() {
        return jj_scan_token(18);
    }

    private final boolean jj_3R_140() {
        return jj_scan_token(11);
    }

    private final boolean jj_3R_26() {
        Token token;
        if (jj_3R_32()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_33());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_139() {
        return jj_scan_token(10);
    }

    private final boolean jj_3_14() {
        return jj_3R_25();
    }

    private final boolean jj_3R_35() {
        return jj_scan_token(101);
    }

    private final boolean jj_3R_34() {
        return jj_scan_token(100);
    }

    private final boolean jj_3_2() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(100)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(101);
    }

    private final boolean jj_3R_131() {
        return jj_scan_token(91) || jj_scan_token(122);
    }

    private final boolean jj_3R_151() {
        return jj_scan_token(12);
    }

    private final boolean jj_3R_142() {
        return jj_scan_token(17);
    }

    private final boolean jj_3R_27() {
        Token token = this.jj_scanpos;
        if (jj_3R_34()) {
            this.jj_scanpos = token;
            if (jj_3R_35()) {
                return true;
            }
        }
        return jj_3R_26();
    }

    private final boolean jj_3_9() {
        return jj_3R_24();
    }

    private final boolean jj_3R_133() {
        return jj_scan_token(92);
    }

    private final boolean jj_3R_23() {
        Token token;
        if (jj_3R_26()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_27());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_182() {
        return jj_3R_24();
    }

    private final boolean jj_3R_54() {
        return jj_scan_token(101);
    }

    private final boolean jj_3R_164() {
        return jj_scan_token(6);
    }

    private final boolean jj_3R_167() {
        if (jj_scan_token(109)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (!jj_3R_182()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_48() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(100)) {
            this.jj_scanpos = token;
            if (jj_3R_54()) {
                return true;
            }
        }
        return jj_3R_50();
    }

    private final boolean jj_3R_132() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(FMParserConstants.TERMINATING_EXCLAM)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_167();
    }

    private final boolean jj_3R_38() {
        return jj_3R_47();
    }

    private final boolean jj_3R_150() {
        return jj_scan_token(21);
    }

    private final boolean jj_3R_31() {
        Token token = this.jj_scanpos;
        if (!jj_3R_38()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_95() {
        return jj_3R_133();
    }

    private final boolean jj_3R_55() {
        return jj_scan_token(109);
    }

    private final boolean jj_3R_94() {
        return jj_3R_132();
    }

    private final boolean jj_3R_93() {
        return jj_3R_131();
    }

    private final boolean jj_3R_49() {
        Token token;
        if (jj_3R_55()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_55());
        this.jj_scanpos = token;
        return jj_3R_50();
    }

    private final boolean jj_3R_92() {
        return jj_3R_130();
    }

    private final boolean jj_3R_91() {
        return jj_3R_129();
    }

    private final boolean jj_3R_90() {
        return jj_3R_128();
    }

    private final boolean jj_3R_149() {
        return jj_scan_token(30);
    }

    private final boolean jj_3R_79() {
        Token token = this.jj_scanpos;
        if (!jj_3R_90()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_91()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_92()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_93()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_94()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_95();
    }

    private final boolean jj_3R_41() {
        return jj_3R_50();
    }

    private final boolean jj_3R_40() {
        return jj_3R_49();
    }

    private final boolean jj_3_10() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(110)) {
            this.jj_scanpos = token;
        }
        Token token2 = this.jj_scanpos;
        if (jj_scan_token(122)) {
            this.jj_scanpos = token2;
            if (jj_scan_token(81)) {
                return true;
            }
        }
        return jj_scan_token(93);
    }

    private final boolean jj_3R_39() {
        return jj_3R_48();
    }

    private final boolean jj_3R_32() {
        Token token = this.jj_scanpos;
        if (!jj_3R_39()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_40()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_41();
    }

    private final boolean jj_3R_138() {
        return jj_scan_token(8);
    }

    private final boolean jj_3R_148() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(28)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(29);
    }

    private final boolean jj_3R_165() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(110)) {
            this.jj_scanpos = token;
        }
        return jj_3R_24();
    }

    private final boolean jj_3R_78() {
        return jj_scan_token(87) || jj_scan_token(122);
    }

    private final boolean jj_3R_127() {
        Token token;
        if (jj_3R_24()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_165());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_77() {
        return jj_scan_token(115) || jj_3R_24() || jj_scan_token(116);
    }

    private final boolean jj_3_1() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(87)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(113)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(115)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(91)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(109)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(FMParserConstants.TERMINATING_EXCLAM)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(92);
    }

    private final boolean jj_3R_70() {
        return jj_3R_84();
    }

    private final boolean jj_3R_89() {
        Token token = this.jj_scanpos;
        if (!jj_3R_127()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_69() {
        return jj_3R_83();
    }

    private final boolean jj_3R_68() {
        return jj_3R_82();
    }

    private final boolean jj_3R_67() {
        return jj_3R_81();
    }

    private final boolean jj_3R_64() {
        return jj_3R_79();
    }

    private final boolean jj_3R_53() {
        Token token = this.jj_scanpos;
        if (!jj_3R_67()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_68()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_69()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_70();
    }

    private final boolean jj_3R_63() {
        return jj_3R_78();
    }

    private final boolean jj_3R_170() {
        return jj_scan_token(16);
    }

    private final boolean jj_3R_62() {
        return jj_3R_77();
    }

    private final boolean jj_3R_169() {
        return jj_scan_token(15);
    }

    private final boolean jj_3R_61() {
        return jj_3R_76();
    }

    private final boolean jj_3R_47() {
        Token token;
        if (jj_3R_53()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_53());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_168() {
        return jj_scan_token(14);
    }

    private final boolean jj_3R_60() {
        return jj_3R_75();
    }

    private final boolean jj_3R_59() {
        return jj_3R_74();
    }

    private final boolean jj_3R_58() {
        return jj_3R_73();
    }

    private final boolean jj_3R_76() {
        return jj_scan_token(122);
    }

    private final boolean jj_3R_141() {
        Token token = this.jj_scanpos;
        if (!jj_3R_168()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_169()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_170();
    }

    private final boolean jj_3R_57() {
        return jj_3R_72();
    }

    private final boolean jj_3R_56() {
        return jj_3R_71();
    }

    private final boolean jj_3R_50() {
        Token token;
        Token token2 = this.jj_scanpos;
        if (jj_3R_56()) {
            this.jj_scanpos = token2;
            if (jj_3R_57()) {
                this.jj_scanpos = token2;
                if (jj_3R_58()) {
                    this.jj_scanpos = token2;
                    if (jj_3R_59()) {
                        this.jj_scanpos = token2;
                        if (jj_3R_60()) {
                            this.jj_scanpos = token2;
                            if (jj_3R_61()) {
                                this.jj_scanpos = token2;
                                if (jj_3R_62()) {
                                    this.jj_scanpos = token2;
                                    if (jj_3R_63()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_64());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3_12() {
        return jj_scan_token(115);
    }

    private final boolean jj_3R_178() {
        return jj_scan_token(52);
    }

    private final boolean jj_3R_177() {
        return jj_scan_token(51);
    }

    private final boolean jj_3R_71() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(85)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(86);
    }

    private final boolean jj_3R_176() {
        return jj_scan_token(50);
    }

    private final boolean jj_3_13() {
        return jj_scan_token(122) || jj_scan_token(93);
    }

    private final boolean jj_3R_175() {
        return jj_scan_token(49);
    }

    private final boolean jj_3R_24() {
        return jj_3R_28();
    }

    private final boolean jj_3R_157() {
        Token token = this.jj_scanpos;
        if (!jj_3R_175()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_176()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_177()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_178();
    }

    private final boolean jj_3R_75() {
        return jj_scan_token(113) || jj_3R_89() || jj_scan_token(114);
    }

    private final boolean jj_3R_83() {
        return jj_scan_token(72);
    }

    private final boolean jj_3_15() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(68)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(69)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(70);
    }

    private final boolean jj_3R_147() {
        return jj_scan_token(25);
    }

    private final boolean jj_3R_137() {
        return jj_scan_token(68);
    }

    private final boolean jj_3R_156() {
        return jj_scan_token(48);
    }

    private final boolean jj_3_8() {
        return jj_scan_token(108);
    }

    private final boolean jj_3R_98() {
        Token token = this.jj_scanpos;
        if (!jj_3R_137()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(69)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(70);
    }

    private final boolean jj_3R_37() {
        return jj_scan_token(108) || jj_3R_36();
    }

    private final boolean jj_3R_81() {
        Token token;
        if (jj_3R_98()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_98());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_82() {
        return jj_scan_token(71);
    }

    private final boolean jj_3R_28() {
        Token token;
        if (jj_3R_36()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_37());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_180() {
        return jj_scan_token(55);
    }

    private final boolean jj_3_7() {
        return jj_scan_token(107);
    }

    private final boolean jj_3R_125() {
        return jj_3R_164();
    }

    private final boolean jj_3R_124() {
        return jj_3R_163();
    }

    private final boolean jj_3R_179() {
        return jj_scan_token(54);
    }

    private final boolean jj_3R_123() {
        return jj_3R_162();
    }

    private final boolean jj_3R_122() {
        return jj_3R_161();
    }

    private final boolean jj_3R_158() {
        Token token = this.jj_scanpos;
        if (!jj_3R_179()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_180();
    }

    private final boolean jj_3R_121() {
        return jj_3R_160();
    }

    private final boolean jj_3R_46() {
        return jj_scan_token(107) || jj_3R_45();
    }

    private final boolean jj_3R_126() {
        if (jj_scan_token(110) || jj_3R_24()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(110)) {
            this.jj_scanpos = token;
            if (jj_scan_token(112)) {
                return true;
            }
        }
        return jj_3R_24();
    }

    private final boolean jj_3_11() {
        return jj_scan_token(122) || jj_scan_token(93);
    }

    private final boolean jj_3R_120() {
        return jj_3R_159();
    }

    private final boolean jj_3R_119() {
        return jj_3R_158();
    }

    private final boolean jj_3R_118() {
        return jj_3R_157();
    }

    private final boolean jj_3R_36() {
        Token token;
        if (jj_3R_45()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_46());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_117() {
        return jj_3R_156();
    }

    private final boolean jj_3R_116() {
        return jj_3R_155();
    }

    private final boolean jj_3R_174() {
        return jj_scan_token(23);
    }

    private final boolean jj_3R_115() {
        return jj_3R_154();
    }

    private final boolean jj_3R_85() {
        Token token;
        if (jj_3R_24()) {
            return true;
        }
        Token token2 = this.jj_scanpos;
        if (jj_scan_token(110)) {
            this.jj_scanpos = token2;
            if (jj_scan_token(112)) {
                return true;
            }
        }
        if (jj_3R_24()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_126());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_114() {
        return jj_3R_153();
    }

    private final boolean jj_3R_113() {
        return jj_3R_152();
    }

    private final boolean jj_3_6() {
        return jj_3R_23();
    }

    private final boolean jj_3R_112() {
        return jj_3R_151();
    }

    private final boolean jj_3R_155() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(47)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_174();
    }

    private final boolean jj_3R_72() {
        if (jj_scan_token(117)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_85()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(118);
    }

    private final boolean jj_3R_111() {
        return jj_3R_150();
    }

    private final boolean jj_3R_110() {
        return jj_3R_149();
    }

    private final boolean jj_3R_109() {
        return jj_3R_148();
    }

    private final boolean jj_3R_146() {
        return jj_scan_token(63);
    }

    private final boolean jj_3R_108() {
        return jj_3R_147();
    }

    private final boolean jj_3R_136() {
        return jj_3R_23();
    }

    private final boolean jj_3R_107() {
        return jj_3R_146();
    }

    private final boolean jj_3R_106() {
        return jj_3R_145();
    }

    private final boolean jj_3R_105() {
        return jj_3R_144();
    }

    private final boolean jj_3R_88() {
        return jj_scan_token(84);
    }

    private final boolean jj_3R_104() {
        return jj_3R_143();
    }

    private final boolean jj_3R_135() {
        return jj_scan_token(90);
    }

    private final boolean jj_3R_87() {
        return jj_scan_token(83);
    }

    private final boolean jj_3R_103() {
        return jj_3R_142();
    }

    private final boolean jj_3R_134() {
        return jj_scan_token(89);
    }

    private final boolean jj_3R_97() {
        if (jj_scan_token(88)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (!jj_3R_136()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_102() {
        return jj_3R_141();
    }

    private final boolean jj_3R_74() {
        Token token = this.jj_scanpos;
        if (!jj_3R_87()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_88();
    }

    private final boolean jj_3R_101() {
        return jj_3R_140();
    }

    private final boolean jj_3R_100() {
        return jj_3R_139();
    }

    private final boolean jj_3R_173() {
        return jj_scan_token(24);
    }

    private final boolean jj_3R_99() {
        return jj_3R_138();
    }

    private final boolean jj_3R_145() {
        return jj_scan_token(27);
    }

    private final boolean jj_3R_172() {
        return jj_scan_token(46);
    }

    private final boolean jj_3R_96() {
        Token token = this.jj_scanpos;
        if (jj_3R_134()) {
            this.jj_scanpos = token;
            if (jj_3R_135()) {
                return true;
            }
        }
        return jj_3R_23();
    }

    private final boolean jj_3R_84() {
        Token token = this.jj_scanpos;
        if (!jj_3R_99()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_100()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_101()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_102()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_103()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_104()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_105()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_106()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_107()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_108()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_109()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_110()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_111()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_112()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_113()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_114()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_115()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_116()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_117()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_118()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_119()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_120()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_121()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_122()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_123()) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_3R_124()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_125();
    }

    private final boolean jj_3R_154() {
        Token token = this.jj_scanpos;
        if (!jj_3R_172()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_173();
    }

    private final boolean jj_3R_80() {
        Token token = this.jj_scanpos;
        if (!jj_3R_96()) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_97();
    }

    private final boolean jj_3R_65() {
        if (jj_3R_23()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (!jj_3R_80()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_86() {
        return jj_scan_token(82);
    }

    private final boolean jj_3R_152() {
        return jj_scan_token(26);
    }

    private final boolean jj_3R_153() {
        return jj_scan_token(45);
    }

    private final boolean jj_3R_73() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(81)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_86();
    }

    private final boolean jj_3_5() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(129)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(99)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(128)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(98)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(97)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(97)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(96);
    }

    private final boolean jj_3R_130() {
        return jj_scan_token(115) || jj_3R_89() || jj_scan_token(116);
    }

    private final boolean jj_3_16() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(68)) {
            this.jj_scanpos = token;
        }
        Token token2 = this.jj_scanpos;
        if (!jj_scan_token(66)) {
            return false;
        }
        this.jj_scanpos = token2;
        return jj_scan_token(65);
    }

    private final boolean jj_3R_163() {
        return jj_scan_token(58);
    }

    private final boolean jj_3R_66() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(129)) {
            this.jj_scanpos = token;
            if (jj_scan_token(99)) {
                this.jj_scanpos = token;
                if (jj_scan_token(128)) {
                    this.jj_scanpos = token;
                    if (jj_scan_token(98)) {
                        this.jj_scanpos = token;
                        if (jj_scan_token(97)) {
                            this.jj_scanpos = token;
                            if (jj_scan_token(96)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return jj_3R_65();
    }

    private final boolean jj_3R_51() {
        if (jj_3R_65()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (!jj_3R_66()) {
            return false;
        }
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_160() {
        return jj_scan_token(61);
    }

    private final boolean jj_3R_181() {
        return jj_scan_token(57);
    }

    private final boolean jj_3R_129() {
        return jj_scan_token(113) || jj_3R_24() || jj_scan_token(114);
    }

    private final boolean jj_3_4() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(95)) {
            return false;
        }
        this.jj_scanpos = token;
        if (!jj_scan_token(93)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_scan_token(94);
    }

    private final boolean jj_3R_162() {
        Token token = this.jj_scanpos;
        if (!jj_scan_token(56)) {
            return false;
        }
        this.jj_scanpos = token;
        return jj_3R_181();
    }

    private final boolean jj_3R_171() {
        return jj_scan_token(19);
    }

    private final boolean jj_3R_52() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(95)) {
            this.jj_scanpos = token;
            if (jj_scan_token(93)) {
                this.jj_scanpos = token;
                if (jj_scan_token(94)) {
                    return true;
                }
            }
        }
        return jj_3R_51();
    }

    static {
        jj_la1_0();
        jj_la1_1();
        jj_la1_2();
        jj_la1_3();
        jj_la1_4();
    }

    private static void jj_la1_0() {
        jj_la1_0 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 512, 0, 0, 0, 0, 0, 0, 16777216, 8388608, 0, 0, 114688, 0, 0, 0, 0, 0, 0, 0, 1572864, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, C.ENCODING_PCM_32BIT, 0, 0, 0, 0, 0, 8192, 0, 2147474752, 0, 0, 0, 2147474752, 2147474752, 0, 0, 2147474752, 0, 0, 0, 0, 0};
    }

    private static void jj_la1_1() {
        jj_la1_1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4096, 6, 0, 0, 0, 50331648, 16384, 32768, 12582912, 1966080, 0, 0, 0, 0, 112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 384, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2097152, 0, -1344282624, 0, 0, 0, -1344282624, -1344282624, 0, 0, -1344282624, 0, 0, 0, 0, 0};
    }

    private static void jj_la1_2() {
        jj_la1_2 = new int[]{16646144, 16646144, 0, 0, 0, 0, -536870912, 0, 100663296, 117440512, 117440512, 6291456, 393216, 411041792, 0, 1572864, 1572864, 393216, 1572864, 0, 0, 0, 16646144, 0, 0, 0, 0, 0, 16646144, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 536870912, 0, 0, 0, 0, 0, 0, 536870912, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16646144, 0, 16646144, 0, 0, 0, 0, 16, 16, 0, 0, 0, 112, 0, 0, 496, 496, 496, 496, 496, 16, 0, 6, 0, 0};
    }

    private static void jj_la1_3() {
        jj_la1_3 = new int[]{69861376, 69869616, 8192, 48, 48, 1600, 0, 15, 0, 0, 0, 0, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, 663552, 8192, 58720271, 125829327, 0, 0, 81920, 16384, 81920, 69869616, 32768, 0, 0, 0, 33554432, 69869616, 33554432, 0, 0, 0, 0, 0, 0, 16384, 8388608, 8388608, 0, 1082130432, 32768, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, 0, 524288, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, 256, 0, 16384, 1048576, 0, 0, 0, 16384, 0, 0, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, 32768, -1073741824, 1048576, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, 69886000, 16384, 69869616, 0, 32768, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, -1073741824, 0, 0, 0, -1073741824, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL, 0, 16384, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL};
    }

    private static void jj_la1_4() {
        jj_la1_4 = new int[]{0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, PsExtractor.VIDEO_STREAM_MASK, PsExtractor.VIDEO_STREAM_MASK, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public FMParser(InputStream inputStream) {
        this.escapes = new LinkedList();
        int i = 0;
        this.lookingAhead = false;
        this.jj_la1 = new int[86];
        this.jj_2_rtns = new JJCalls[16];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new Vector();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.jj_input_stream = new SimpleCharStream(inputStream, 1, 1);
        this.token_source = new FMParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 86; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public void ReInit(InputStream inputStream) {
        this.jj_input_stream.ReInit(inputStream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        int i = 0;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 86; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public FMParser(Reader reader) {
        this.escapes = new LinkedList();
        int i = 0;
        this.lookingAhead = false;
        this.jj_la1 = new int[86];
        this.jj_2_rtns = new JJCalls[16];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new Vector();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.jj_input_stream = new SimpleCharStream(reader, 1, 1);
        this.token_source = new FMParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 86; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public void ReInit(Reader reader) {
        this.jj_input_stream.ReInit(reader, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        int i = 0;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 86; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public FMParser(FMParserTokenManager fMParserTokenManager) {
        this.escapes = new LinkedList();
        int i = 0;
        this.lookingAhead = false;
        this.jj_la1 = new int[86];
        this.jj_2_rtns = new JJCalls[16];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new Vector();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.token_source = fMParserTokenManager;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 86; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    public void ReInit(FMParserTokenManager fMParserTokenManager) {
        this.token_source = fMParserTokenManager;
        this.token = new Token();
        this.jj_ntk = -1;
        int i = 0;
        this.jj_gen = 0;
        for (int i2 = 0; i2 < 86; i2++) {
            this.jj_la1[i2] = -1;
        }
        while (true) {
            JJCalls[] jJCallsArr = this.jj_2_rtns;
            if (i >= jJCallsArr.length) {
                return;
            }
            jJCallsArr[i] = new JJCalls();
            i++;
        }
    }

    private final Token jj_consume_token(int i) throws ParseException {
        Token token = this.token;
        if (token.next != null) {
            this.token = this.token.next;
        } else {
            Token token2 = this.token;
            Token nextToken = this.token_source.getNextToken();
            token2.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        if (this.token.kind == i) {
            this.jj_gen++;
            int i2 = this.jj_gc + 1;
            this.jj_gc = i2;
            if (i2 > 100) {
                int i3 = 0;
                this.jj_gc = 0;
                while (true) {
                    JJCalls[] jJCallsArr = this.jj_2_rtns;
                    if (i3 >= jJCallsArr.length) {
                        break;
                    }
                    for (JJCalls jJCalls = jJCallsArr[i3]; jJCalls != null; jJCalls = jJCalls.next) {
                        if (jJCalls.gen < this.jj_gen) {
                            jJCalls.first = null;
                        }
                    }
                    i3++;
                }
            }
            return this.token;
        }
        this.token = token;
        this.jj_kind = i;
        throw generateParseException();
    }

    private static final class LookaheadSuccess extends Error {
        private LookaheadSuccess() {
        }
    }

    private final boolean jj_scan_token(int i) {
        Token token = this.jj_scanpos;
        if (token == this.jj_lastpos) {
            this.jj_la--;
            if (token.next == null) {
                Token token2 = this.jj_scanpos;
                Token nextToken = this.token_source.getNextToken();
                token2.next = nextToken;
                this.jj_scanpos = nextToken;
                this.jj_lastpos = nextToken;
            } else {
                Token token3 = this.jj_scanpos.next;
                this.jj_scanpos = token3;
                this.jj_lastpos = token3;
            }
        } else {
            this.jj_scanpos = token.next;
        }
        if (this.jj_rescan) {
            Token token4 = this.token;
            int i2 = 0;
            while (token4 != null && token4 != this.jj_scanpos) {
                i2++;
                token4 = token4.next;
            }
            if (token4 != null) {
                jj_add_error_token(i, i2);
            }
        }
        if (this.jj_scanpos.kind != i) {
            return true;
        }
        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw this.jj_ls;
        }
        return false;
    }

    public final Token getNextToken() {
        if (this.token.next != null) {
            this.token = this.token.next;
        } else {
            Token token = this.token;
            Token nextToken = this.token_source.getNextToken();
            token.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        this.jj_gen++;
        return this.token;
    }

    public final Token getToken(int i) {
        Token token = this.lookingAhead ? this.jj_scanpos : this.token;
        for (int i2 = 0; i2 < i; i2++) {
            if (token.next != null) {
                token = token.next;
            } else {
                Token nextToken = this.token_source.getNextToken();
                token.next = nextToken;
                token = nextToken;
            }
        }
        return token;
    }

    private final int jj_ntk() {
        Token token = this.token.next;
        this.jj_nt = token;
        if (token == null) {
            Token token2 = this.token;
            Token nextToken = this.token_source.getNextToken();
            token2.next = nextToken;
            int i = nextToken.kind;
            this.jj_ntk = i;
            return i;
        }
        int i2 = token.kind;
        this.jj_ntk = i2;
        return i2;
    }

    private void jj_add_error_token(int i, int i2) {
        if (i2 >= 100) {
            return;
        }
        int i3 = this.jj_endpos;
        if (i2 == i3 + 1) {
            int[] iArr = this.jj_lasttokens;
            this.jj_endpos = i3 + 1;
            iArr[i3] = i;
            return;
        }
        if (i3 != 0) {
            this.jj_expentry = new int[i3];
            for (int i4 = 0; i4 < this.jj_endpos; i4++) {
                this.jj_expentry[i4] = this.jj_lasttokens[i4];
            }
            Enumeration enumerationElements = this.jj_expentries.elements();
            boolean z = false;
            while (enumerationElements.hasMoreElements()) {
                int[] iArr2 = (int[]) enumerationElements.nextElement();
                if (iArr2.length == this.jj_expentry.length) {
                    int i5 = 0;
                    while (true) {
                        int[] iArr3 = this.jj_expentry;
                        if (i5 >= iArr3.length) {
                            z = true;
                            break;
                        } else {
                            if (iArr2[i5] != iArr3[i5]) {
                                z = false;
                                break;
                            }
                            i5++;
                        }
                    }
                    if (z) {
                        break;
                    }
                }
            }
            if (!z) {
                this.jj_expentries.addElement(this.jj_expentry);
            }
            if (i2 != 0) {
                int[] iArr4 = this.jj_lasttokens;
                this.jj_endpos = i2;
                iArr4[i2 - 1] = i;
            }
        }
    }

    public ParseException generateParseException() {
        this.jj_expentries.removeAllElements();
        boolean[] zArr = new boolean[136];
        for (int i = 0; i < 136; i++) {
            zArr[i] = false;
        }
        int i2 = this.jj_kind;
        if (i2 >= 0) {
            zArr[i2] = true;
            this.jj_kind = -1;
        }
        for (int i3 = 0; i3 < 86; i3++) {
            if (this.jj_la1[i3] == this.jj_gen) {
                for (int i4 = 0; i4 < 32; i4++) {
                    int i5 = 1 << i4;
                    if ((jj_la1_0[i3] & i5) != 0) {
                        zArr[i4] = true;
                    }
                    if ((jj_la1_1[i3] & i5) != 0) {
                        zArr[i4 + 32] = true;
                    }
                    if ((jj_la1_2[i3] & i5) != 0) {
                        zArr[i4 + 64] = true;
                    }
                    if ((jj_la1_3[i3] & i5) != 0) {
                        zArr[i4 + 96] = true;
                    }
                    if ((jj_la1_4[i3] & i5) != 0) {
                        zArr[i4 + 128] = true;
                    }
                }
            }
        }
        for (int i6 = 0; i6 < 136; i6++) {
            if (zArr[i6]) {
                int[] iArr = {i6};
                this.jj_expentry = iArr;
                this.jj_expentries.addElement(iArr);
            }
        }
        this.jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] iArr2 = new int[this.jj_expentries.size()][];
        for (int i7 = 0; i7 < this.jj_expentries.size(); i7++) {
            iArr2[i7] = (int[]) this.jj_expentries.elementAt(i7);
        }
        return new ParseException(this.token, iArr2, tokenImage);
    }

    private final void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 16; i++) {
            JJCalls jJCalls = this.jj_2_rtns[i];
            do {
                if (jJCalls.gen > this.jj_gen) {
                    this.jj_la = jJCalls.arg;
                    Token token = jJCalls.first;
                    this.jj_scanpos = token;
                    this.jj_lastpos = token;
                    switch (i) {
                        case 0:
                            jj_3_1();
                            break;
                        case 1:
                            jj_3_2();
                            break;
                        case 2:
                            jj_3_3();
                            break;
                        case 3:
                            jj_3_4();
                            break;
                        case 4:
                            jj_3_5();
                            break;
                        case 5:
                            jj_3_6();
                            break;
                        case 6:
                            jj_3_7();
                            break;
                        case 7:
                            jj_3_8();
                            break;
                        case 8:
                            jj_3_9();
                            break;
                        case 9:
                            jj_3_10();
                            break;
                        case 10:
                            jj_3_11();
                            break;
                        case 11:
                            jj_3_12();
                            break;
                        case 12:
                            jj_3_13();
                            break;
                        case 13:
                            jj_3_14();
                            break;
                        case 14:
                            jj_3_15();
                            break;
                        case 15:
                            jj_3_16();
                            break;
                    }
                }
                jJCalls = jJCalls.next;
            } while (jJCalls != null);
        }
        this.jj_rescan = false;
    }

    private final void jj_save(int i, int i2) {
        JJCalls jJCalls = this.jj_2_rtns[i];
        while (true) {
            if (jJCalls.gen <= this.jj_gen) {
                break;
            }
            if (jJCalls.next == null) {
                JJCalls jJCalls2 = new JJCalls();
                jJCalls.next = jJCalls2;
                jJCalls = jJCalls2;
                break;
            }
            jJCalls = jJCalls.next;
        }
        jJCalls.gen = (this.jj_gen + i2) - this.jj_la;
        jJCalls.first = this.token;
        jJCalls.arg = i2;
    }

    static final class JJCalls {
        int arg;
        Token first;
        int gen;
        JJCalls next;

        JJCalls() {
        }
    }
}
