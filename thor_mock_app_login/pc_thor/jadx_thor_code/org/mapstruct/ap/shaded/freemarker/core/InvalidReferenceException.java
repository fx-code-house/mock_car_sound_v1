package org.mapstruct.ap.shaded.freemarker.core;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;

/* loaded from: classes3.dex */
public class InvalidReferenceException extends TemplateException {
    static final InvalidReferenceException FAST_INSTANCE = new InvalidReferenceException("Invalid reference. Details are unavilable, as this should have been handled by an FTL construct. If it wasn't, that's problably a bug in FreeMarker.", null);
    private static final String[] TIP = {"If the failing expression is known to be legally refer to something that's null or missing, either specify a default value like myOptionalVar!myDefault, or use ", "<#if myOptionalVar??>", "when-present", "<#else>", "when-missing", "</#if>", ". (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)??"};
    private static final String TIP_LAST_STEP_DOT = "It's the step after the last dot that caused this error, not those before it.";
    private static final String TIP_LAST_STEP_SQUARE_BRACKET = "It's the final [] step that caused this error, not those before it.";
    private static final String TIP_NO_DOLAR = "Variable references must not start with \"$\", unless the \"$\" is really part of the variable name.";

    public InvalidReferenceException(Environment environment) {
        super("Invalid reference: The expression has evaluated to null or refers to something that doesn't exist.", environment);
    }

    public InvalidReferenceException(String str, Environment environment) {
        super(str, environment);
    }

    InvalidReferenceException(_ErrorDescriptionBuilder _errordescriptionbuilder, Environment environment, Expression expression) {
        super(null, environment, expression, _errordescriptionbuilder);
    }

    static InvalidReferenceException getInstance(Expression expression, Environment environment) {
        String str;
        if (environment != null && environment.getFastInvalidReferenceExceptions()) {
            return FAST_INSTANCE;
        }
        if (expression != null) {
            _ErrorDescriptionBuilder _errordescriptionbuilderBlame = new _ErrorDescriptionBuilder("The following has evaluated to null or missing:").blame(expression);
            if (endsWithDollarVariable(expression)) {
                _errordescriptionbuilderBlame.tips(new Object[]{TIP_NO_DOLAR, TIP});
            } else if (expression instanceof Dot) {
                String rho = ((Dot) expression).getRHO();
                if ("size".equals(rho)) {
                    str = "To query the size of a collection or map use ?size, like myList?size";
                } else {
                    str = SessionDescription.ATTR_LENGTH.equals(rho) ? "To query the length of a string use ?length, like myString?size" : null;
                }
                _errordescriptionbuilderBlame.tips(str == null ? new Object[]{TIP_LAST_STEP_DOT, TIP} : new Object[]{TIP_LAST_STEP_DOT, str, TIP});
            } else if (expression instanceof DynamicKeyName) {
                _errordescriptionbuilderBlame.tips(new Object[]{TIP_LAST_STEP_SQUARE_BRACKET, TIP});
            } else {
                _errordescriptionbuilderBlame.tip((Object[]) TIP);
            }
            return new InvalidReferenceException(_errordescriptionbuilderBlame, environment, expression);
        }
        return new InvalidReferenceException(environment);
    }

    private static boolean endsWithDollarVariable(Expression expression) {
        return ((expression instanceof Identifier) && ((Identifier) expression).getName().startsWith("$")) || ((expression instanceof Dot) && ((Dot) expression).getRHO().startsWith("$"));
    }
}
