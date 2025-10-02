package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;
import java.util.Objects;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.source.Method;

/* loaded from: classes3.dex */
public class SelectedMethod<T extends Method> {
    private T method;
    private List<ParameterBinding> parameterBindings;

    public SelectedMethod(T t) {
        this.method = t;
    }

    public T getMethod() {
        return this.method;
    }

    public List<ParameterBinding> getParameterBindings() {
        return this.parameterBindings;
    }

    public void setParameterBindings(List<ParameterBinding> list) {
        this.parameterBindings = list;
    }

    public String toString() {
        return this.method.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.method.equals(((SelectedMethod) obj).method);
    }

    public int hashCode() {
        return Objects.hash(this.method);
    }
}
