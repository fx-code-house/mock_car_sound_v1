package org.mapstruct.ap.shaded.freemarker.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;
import org.mapstruct.ap.shaded.freemarker.core.Environment;
import org.mapstruct.ap.shaded.freemarker.core.Environment.Namespace;
import org.mapstruct.ap.shaded.freemarker.template.TemplateException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModel;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelIterator;

/* loaded from: classes3.dex */
public final class Macro extends TemplateElement implements TemplateModel {
    static final Macro DO_NOTHING_MACRO = new Macro(".pass", Collections.EMPTY_LIST, Collections.EMPTY_MAP, TextBlock.EMPTY_BLOCK);
    private String catchAllParamName;
    boolean isFunction;
    private final String name;
    private Map paramDefaults;
    private final String[] paramNames;
    final int TYPE_MACRO = 0;
    final int TYPE_FUNCTION = 1;

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    boolean isShownInStackTrace() {
        return false;
    }

    Macro(String str, List list, Map map, TemplateElement templateElement) {
        this.name = str;
        this.paramNames = (String[]) list.toArray(new String[list.size()]);
        this.paramDefaults = map;
        this.nestedBlock = templateElement;
    }

    public String getCatchAll() {
        return this.catchAllParamName;
    }

    public void setCatchAll(String str) {
        this.catchAllParamName = str;
    }

    public String[] getArgumentNames() {
        return (String[]) this.paramNames.clone();
    }

    String[] getArgumentNamesInternal() {
        return this.paramNames;
    }

    boolean hasArgNamed(String str) {
        return this.paramDefaults.containsKey(str);
    }

    public String getName() {
        return this.name;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    void accept(Environment environment) {
        environment.visitMacroDef(this);
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateElement
    protected String dump(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        if (z) {
            stringBuffer.append(Typography.less);
        }
        stringBuffer.append(getNodeTypeSymbol());
        stringBuffer.append(' ');
        stringBuffer.append(this.name);
        stringBuffer.append(this.isFunction ? '(' : ' ');
        int length = this.paramNames.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                if (this.isFunction) {
                    stringBuffer.append(", ");
                } else {
                    stringBuffer.append(' ');
                }
            }
            String str = this.paramNames[i];
            stringBuffer.append(str);
            Map map = this.paramDefaults;
            if (map != null && map.get(str) != null) {
                stringBuffer.append('=');
                Expression expression = (Expression) this.paramDefaults.get(str);
                if (this.isFunction) {
                    stringBuffer.append(expression.getCanonicalForm());
                } else {
                    MessageUtil.appendExpressionAsUntearable(stringBuffer, expression);
                }
            }
        }
        if (this.catchAllParamName != null) {
            if (length != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.catchAllParamName);
            stringBuffer.append("...");
        }
        if (this.isFunction) {
            stringBuffer.append(')');
        }
        if (z) {
            stringBuffer.append(Typography.greater);
            if (this.nestedBlock != null) {
                stringBuffer.append(this.nestedBlock.getCanonicalForm());
            }
            stringBuffer.append("</").append(getNodeTypeSymbol()).append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    String getNodeTypeSymbol() {
        return this.isFunction ? "#function" : "#macro";
    }

    public boolean isFunction() {
        return this.isFunction;
    }

    class Context implements LocalContext {
        TemplateElement body;
        Environment.Namespace bodyNamespace;
        List bodyParameterNames;
        Environment.Namespace localVars;
        ArrayList prevLocalContextStack;
        Context prevMacroContext;

        Context(Environment environment, TemplateElement templateElement, List list) {
            environment.getClass();
            this.localVars = environment.new Namespace();
            this.prevMacroContext = environment.getCurrentMacroContext();
            this.bodyNamespace = environment.getCurrentNamespace();
            this.prevLocalContextStack = environment.getLocalContextStack();
            this.body = templateElement;
            this.bodyParameterNames = list;
        }

        Macro getMacro() {
            return Macro.this;
        }

        void runMacro(Environment environment) throws TemplateException, IOException {
            sanityCheck(environment);
            if (Macro.this.nestedBlock != null) {
                environment.visit(Macro.this.nestedBlock);
            }
        }

        void sanityCheck(Environment environment) throws TemplateException {
            InvalidReferenceException invalidReferenceException;
            Expression expression;
            boolean z;
            TemplateModel templateModelEval;
            do {
                invalidReferenceException = null;
                expression = null;
                boolean z2 = false;
                z = false;
                for (int i = 0; i < Macro.this.paramNames.length; i++) {
                    String str = Macro.this.paramNames[i];
                    if (this.localVars.get(str) == null) {
                        Expression expression2 = (Expression) Macro.this.paramDefaults.get(str);
                        if (expression2 != null) {
                            try {
                                templateModelEval = expression2.eval(environment);
                            } catch (InvalidReferenceException e) {
                                if (!z) {
                                    invalidReferenceException = e;
                                }
                            }
                            if (templateModelEval != null) {
                                this.localVars.put(str, templateModelEval);
                                z2 = true;
                            } else if (!z) {
                                expression = expression2;
                                z = true;
                            }
                        } else if (!environment.isClassicCompatible()) {
                            boolean zContainsKey = this.localVars.containsKey(str);
                            Object[] objArr = new Object[8];
                            objArr[0] = "When calling macro ";
                            objArr[1] = new _DelayedJQuote(Macro.this.name);
                            objArr[2] = ", required parameter ";
                            objArr[3] = new _DelayedJQuote(str);
                            objArr[4] = " (parameter #";
                            objArr[5] = new Integer(i + 1);
                            objArr[6] = ") was ";
                            objArr[7] = zContainsKey ? "specified, but had null/missing value." : "not specified.";
                            throw new _MiscTemplateException(environment, new _ErrorDescriptionBuilder(objArr).tip(zContainsKey ? new Object[]{"If the parameter value expression on the caller side is known to be legally null/missing, you may want to specify a default value for it with the \"!\" operator, like paramValue!defaultValue."} : new Object[]{"If the omission was deliberate, you may consider making the parameter optional in the macro by specifying a default value for it, like ", "<#macro macroName paramName=defaultExpr>", ")"}));
                        }
                    }
                }
                if (!z2) {
                    break;
                }
            } while (z);
            if (z) {
                if (invalidReferenceException != null) {
                    throw invalidReferenceException;
                }
                if (!environment.isClassicCompatible()) {
                    throw InvalidReferenceException.getInstance(expression, environment);
                }
            }
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
        public TemplateModel getLocalVariable(String str) throws TemplateModelException {
            return this.localVars.get(str);
        }

        Environment.Namespace getLocals() {
            return this.localVars;
        }

        void setLocalVar(String str, TemplateModel templateModel) {
            this.localVars.put(str, templateModel);
        }

        @Override // org.mapstruct.ap.shaded.freemarker.core.LocalContext
        public Collection getLocalVariableNames() throws TemplateModelException {
            HashSet hashSet = new HashSet();
            TemplateModelIterator it = this.localVars.keys().iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().toString());
            }
            return hashSet;
        }
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    int getParameterCount() {
        return (this.paramNames.length * 2) + 1 + 1 + 1;
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    Object getParameterValue(int i) {
        if (i == 0) {
            return this.name;
        }
        String[] strArr = this.paramNames;
        int length = (strArr.length * 2) + 1;
        if (i < length) {
            String str = strArr[(i - 1) / 2];
            return i % 2 != 0 ? str : this.paramDefaults.get(str);
        }
        if (i == length) {
            return this.catchAllParamName;
        }
        if (i == length + 1) {
            return new Integer(this.isFunction ? 1 : 0);
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // org.mapstruct.ap.shaded.freemarker.core.TemplateObject
    ParameterRole getParameterRole(int i) {
        if (i == 0) {
            return ParameterRole.ASSIGNMENT_TARGET;
        }
        int length = (this.paramNames.length * 2) + 1;
        if (i < length) {
            if (i % 2 != 0) {
                return ParameterRole.PARAMETER_NAME;
            }
            return ParameterRole.PARAMETER_DEFAULT;
        }
        if (i == length) {
            return ParameterRole.CATCH_ALL_PARAMETER_NAME;
        }
        if (i == length + 1) {
            return ParameterRole.AST_NODE_SUBTYPE;
        }
        throw new IndexOutOfBoundsException();
    }
}
