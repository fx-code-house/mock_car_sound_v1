package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.mapstruct.ap.internal.gem.XmlElementDeclGem;
import org.mapstruct.ap.internal.gem.XmlElementRefGem;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;

/* loaded from: classes3.dex */
public class XmlElementDeclSelector implements MethodSelector {
    private final Types typeUtils;

    public XmlElementDeclSelector(Types types) {
        this.typeUtils = types;
    }

    @Override // org.mapstruct.ap.internal.model.source.selector.MethodSelector
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method method, List<SelectedMethod<T>> list, List<Type> list2, Type type, SelectionCriteria selectionCriteria) {
        XmlElementDeclGem xmlElementDeclGemInstanceOn;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        XmlElementRefInfo xmlElementRefInfoFindXmlElementRef = findXmlElementRef(method.getResultType(), selectionCriteria.getTargetPropertyName());
        for (SelectedMethod<T> selectedMethod : list) {
            if ((selectedMethod.getMethod() instanceof SourceMethod) && (xmlElementDeclGemInstanceOn = XmlElementDeclGem.instanceOn((Element) ((SourceMethod) selectedMethod.getMethod()).getExecutable())) != null) {
                String str = xmlElementDeclGemInstanceOn.name().get();
                TypeMirror value = xmlElementDeclGemInstanceOn.scope().getValue();
                boolean z = str != null && str.equals(xmlElementRefInfoFindXmlElementRef.nameValue());
                boolean z2 = value != null && this.typeUtils.isSameType(value, xmlElementRefInfoFindXmlElementRef.sourceType());
                if (z) {
                    if (z2) {
                        arrayList3.add(selectedMethod);
                    } else {
                        arrayList.add(selectedMethod);
                    }
                } else if (z2) {
                    arrayList2.add(selectedMethod);
                }
            }
        }
        return !arrayList3.isEmpty() ? arrayList3 : !arrayList2.isEmpty() ? arrayList2 : !arrayList.isEmpty() ? arrayList : list;
    }

    private XmlElementRefInfo findXmlElementRef(Type type, String str) {
        XmlElementRefGem xmlElementRefGemInstanceOn;
        TypeMirror typeMirror = type.getTypeMirror();
        XmlElementRefInfo xmlElementRefInfo = new XmlElementRefInfo(str, typeMirror);
        if (str == null) {
            return xmlElementRefInfo;
        }
        TypeElement typeElement = type.getTypeElement();
        while (typeElement != null) {
            for (Element element : typeElement.getEnclosedElements()) {
                if (element.getKind().equals(ElementKind.FIELD) && element.getSimpleName().contentEquals(str) && (xmlElementRefGemInstanceOn = XmlElementRefGem.instanceOn(element)) != null) {
                    return new XmlElementRefInfo(xmlElementRefGemInstanceOn.name().get(), typeMirror);
                }
            }
            typeMirror = typeElement.getSuperclass();
            typeElement = (TypeElement) this.typeUtils.asElement(typeMirror);
        }
        return xmlElementRefInfo;
    }

    private static class XmlElementRefInfo {
        private final String nameValue;
        private final TypeMirror sourceType;

        XmlElementRefInfo(String str, TypeMirror typeMirror) {
            this.nameValue = str;
            this.sourceType = typeMirror;
        }

        public String nameValue() {
            return this.nameValue;
        }

        public TypeMirror sourceType() {
            return this.sourceType;
        }
    }
}
