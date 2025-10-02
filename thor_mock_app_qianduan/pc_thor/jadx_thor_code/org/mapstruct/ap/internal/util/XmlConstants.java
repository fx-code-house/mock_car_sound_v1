package org.mapstruct.ap.internal.util;

/* loaded from: classes3.dex */
public final class XmlConstants {
    public static final String JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR = "javax.xml.datatype.XMLGregorianCalendar";
    private static final boolean IS_XML_GREGORIAN_CALENDAR_PRESENT = ClassUtils.isPresent(JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR, XmlConstants.class.getClassLoader());

    private XmlConstants() {
    }

    public static boolean isXmlGregorianCalendarPresent() {
        return IS_XML_GREGORIAN_CALENDAR_PRESENT;
    }
}
