package org.mapstruct.ap.internal.util;

/* loaded from: classes3.dex */
public final class JaxbConstants {
    public static final String JAXB_ELEMENT_FQN = "javax.xml.bind.JAXBElement";
    private static final boolean IS_JAXB_ELEMENT_PRESENT = ClassUtils.isPresent(JAXB_ELEMENT_FQN, JaxbConstants.class.getClassLoader());

    private JaxbConstants() {
    }

    public static boolean isJaxbElementPresent() {
        return IS_JAXB_ELEMENT_PRESENT;
    }
}
