package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mapstruct.ap.internal.model.common.Parameter;

/* loaded from: classes3.dex */
public class ParameterProvidedMethods {
    private static final ParameterProvidedMethods EMPTY = new ParameterProvidedMethods(Collections.emptyMap());
    private final Map<SourceMethod, Parameter> methodToProvidingParameter;
    private final Map<Parameter, List<SourceMethod>> parameterToProvidedMethods;

    private ParameterProvidedMethods(Map<Parameter, List<SourceMethod>> map) {
        this.parameterToProvidedMethods = map;
        this.methodToProvidingParameter = new IdentityHashMap();
        for (Map.Entry<Parameter, List<SourceMethod>> entry : map.entrySet()) {
            Iterator<SourceMethod> it = entry.getValue().iterator();
            while (it.hasNext()) {
                this.methodToProvidingParameter.put(it.next(), entry.getKey());
            }
        }
    }

    public List<SourceMethod> getAllProvidedMethodsInParameterOrder(List<Parameter> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<Parameter> it = list.iterator();
        while (it.hasNext()) {
            List<SourceMethod> list2 = this.parameterToProvidedMethods.get(it.next());
            if (list2 != null) {
                arrayList.addAll(list2);
            }
        }
        return arrayList;
    }

    public Parameter getParameterForProvidedMethod(Method method) {
        return this.methodToProvidingParameter.get(method);
    }

    public boolean isEmpty() {
        return this.methodToProvidingParameter.isEmpty();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ParameterProvidedMethods empty() {
        return EMPTY;
    }

    public static class Builder {
        private Map<Parameter, List<SourceMethod>> contextProvidedMethods;

        private Builder() {
            this.contextProvidedMethods = new HashMap();
        }

        public void addMethodsForParameter(Parameter parameter, List<SourceMethod> list) {
            this.contextProvidedMethods.put(parameter, list);
        }

        public ParameterProvidedMethods build() {
            return new ParameterProvidedMethods(this.contextProvidedMethods);
        }
    }
}
