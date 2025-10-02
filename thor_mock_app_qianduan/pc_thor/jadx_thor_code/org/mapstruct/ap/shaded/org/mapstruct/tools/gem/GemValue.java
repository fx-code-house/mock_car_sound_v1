package org.mapstruct.ap.shaded.org.mapstruct.tools.gem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

/* loaded from: classes3.dex */
public class GemValue<T> {
    private final AnnotationValue annotationValue;
    private final T defaultValue;
    private final T value;

    public static <V> GemValue<V> create(AnnotationValue annotationValue, AnnotationValue annotationValue2, Class<V> cls) {
        ValueAnnotationValueVisitor valueAnnotationValueVisitor = new ValueAnnotationValueVisitor(Function.identity());
        return new GemValue<>(visit(annotationValue, valueAnnotationValueVisitor, cls), visit(annotationValue2, valueAnnotationValueVisitor, cls), annotationValue);
    }

    public static <V> GemValue<List<V>> createArray(AnnotationValue annotationValue, AnnotationValue annotationValue2, Class<V> cls) {
        ValueAnnotationValueListVisitor valueAnnotationValueListVisitor = new ValueAnnotationValueListVisitor(Function.identity());
        return new GemValue<>(visitList(annotationValue, valueAnnotationValueListVisitor, cls), visitList(annotationValue2, valueAnnotationValueListVisitor, cls), annotationValue);
    }

    public static GemValue<String> createEnum(AnnotationValue annotationValue, AnnotationValue annotationValue2) {
        ValueAnnotationValueVisitor valueAnnotationValueVisitor = new ValueAnnotationValueVisitor(new Function() { // from class: org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((VariableElement) obj).getSimpleName().toString();
            }
        });
        return new GemValue<>((String) visit(annotationValue, valueAnnotationValueVisitor, VariableElement.class), (String) visit(annotationValue2, valueAnnotationValueVisitor, VariableElement.class), annotationValue);
    }

    public static GemValue<List<String>> createEnumArray(AnnotationValue annotationValue, AnnotationValue annotationValue2) {
        ValueAnnotationValueListVisitor valueAnnotationValueListVisitor = new ValueAnnotationValueListVisitor(new Function() { // from class: org.mapstruct.ap.shaded.org.mapstruct.tools.gem.GemValue$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((VariableElement) obj).getSimpleName().toString();
            }
        });
        return new GemValue<>(visitList(annotationValue, valueAnnotationValueListVisitor, VariableElement.class), visitList(annotationValue2, valueAnnotationValueListVisitor, VariableElement.class), annotationValue);
    }

    public static <V> GemValue<V> create(AnnotationValue annotationValue, AnnotationValue annotationValue2, Function<AnnotationMirror, V> function) {
        ValueAnnotationValueVisitor valueAnnotationValueVisitor = new ValueAnnotationValueVisitor(function);
        return new GemValue<>(visit(annotationValue, valueAnnotationValueVisitor, AnnotationMirror.class), visit(annotationValue2, valueAnnotationValueVisitor, AnnotationMirror.class), annotationValue);
    }

    public static <V> GemValue<List<V>> createArray(AnnotationValue annotationValue, AnnotationValue annotationValue2, Function<AnnotationMirror, V> function) {
        ValueAnnotationValueListVisitor valueAnnotationValueListVisitor = new ValueAnnotationValueListVisitor(function);
        return new GemValue<>(visitList(annotationValue, valueAnnotationValueListVisitor, AnnotationMirror.class), visitList(annotationValue2, valueAnnotationValueListVisitor, AnnotationMirror.class), annotationValue);
    }

    private static <V, R> R visit(AnnotationValue annotationValue, AnnotationValueVisitor<R, Class<V>> annotationValueVisitor, Class<V> cls) {
        if (annotationValue == null) {
            return null;
        }
        return (R) annotationValue.accept(annotationValueVisitor, cls);
    }

    private static <V, R> List<R> visitList(AnnotationValue annotationValue, AnnotationValueVisitor<List<R>, Class<V>> annotationValueVisitor, Class<V> cls) {
        if (annotationValue == null) {
            return null;
        }
        return (List) annotationValue.accept(annotationValueVisitor, cls);
    }

    private GemValue(T t, T t2, AnnotationValue annotationValue) {
        this.value = t;
        this.defaultValue = t2;
        this.annotationValue = annotationValue;
    }

    public T get() {
        T t = this.value;
        return t != null ? t : this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public AnnotationValue getAnnotationValue() {
        return this.annotationValue;
    }

    public boolean hasValue() {
        return this.value != null;
    }

    public boolean isValid() {
        return (this.value == null && this.defaultValue == null) ? false : true;
    }

    private static class ValueAnnotationValueVisitor<V, R> extends SimpleAnnotationValueVisitor8<R, Class<V>> {
        private final Function<V, R> valueMapper;

        private ValueAnnotationValueVisitor(Function<V, R> function) {
            this.valueMapper = function;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public R defaultAction(Object obj, Class<V> cls) {
            if (obj != null && cls.isInstance(obj)) {
                return this.valueMapper.apply(cls.cast(obj));
            }
            return null;
        }
    }

    private static class ValueAnnotationValueListVisitor<V, R> extends SimpleAnnotationValueVisitor8<List<R>, Class<V>> {
        private final ValueAnnotationValueVisitor<V, R> arrayVisitor;

        public /* bridge */ /* synthetic */ Object visitArray(List list, Object obj) {
            return visitArray((List<? extends AnnotationValue>) list, (Class) obj);
        }

        private ValueAnnotationValueListVisitor(Function<V, R> function) {
            this.arrayVisitor = new ValueAnnotationValueVisitor<>(function);
        }

        public List<R> visitArray(List<? extends AnnotationValue> list, Class<V> cls) {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<? extends AnnotationValue> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().accept(this.arrayVisitor, cls));
            }
            return arrayList;
        }
    }
}
