package org.mapstruct.ap.internal.model.source.builtin;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JaxbConstants;
import org.mapstruct.ap.internal.util.JodaTimeConstants;
import org.mapstruct.ap.internal.util.XmlConstants;

/* loaded from: classes3.dex */
public class BuiltInMappingMethods {
    private final List<BuiltInMethod> builtInMethods;

    public BuiltInMappingMethods(TypeFactory typeFactory) {
        boolean zIsXmlGregorianCalendarAvailable = isXmlGregorianCalendarAvailable(typeFactory);
        ArrayList arrayList = new ArrayList(20);
        this.builtInMethods = arrayList;
        if (zIsXmlGregorianCalendarAvailable) {
            arrayList.add(new DateToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToDate(typeFactory));
            arrayList.add(new StringToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToString(typeFactory));
            arrayList.add(new CalendarToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToCalendar(typeFactory));
            arrayList.add(new ZonedDateTimeToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToLocalDate(typeFactory));
            arrayList.add(new LocalDateToXmlGregorianCalendar(typeFactory));
            arrayList.add(new LocalDateTimeToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToLocalDateTime(typeFactory));
        }
        if (isJaxbAvailable(typeFactory)) {
            arrayList.add(new JaxbElemToValue(typeFactory));
        }
        arrayList.add(new ZonedDateTimeToCalendar(typeFactory));
        arrayList.add(new CalendarToZonedDateTime(typeFactory));
        if (isJodaTimeAvailable(typeFactory) && zIsXmlGregorianCalendarAvailable) {
            arrayList.add(new JodaDateTimeToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToJodaDateTime(typeFactory));
            arrayList.add(new JodaLocalDateTimeToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToJodaLocalDateTime(typeFactory));
            arrayList.add(new JodaLocalDateToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToJodaLocalDate(typeFactory));
            arrayList.add(new JodaLocalTimeToXmlGregorianCalendar(typeFactory));
            arrayList.add(new XmlGregorianCalendarToJodaLocalTime(typeFactory));
        }
    }

    private static boolean isJaxbAvailable(TypeFactory typeFactory) {
        return JaxbConstants.isJaxbElementPresent() && typeFactory.isTypeAvailable(JaxbConstants.JAXB_ELEMENT_FQN);
    }

    private static boolean isXmlGregorianCalendarAvailable(TypeFactory typeFactory) {
        return XmlConstants.isXmlGregorianCalendarPresent() && typeFactory.isTypeAvailable(XmlConstants.JAVAX_XML_DATATYPE_XMLGREGORIAN_CALENDAR);
    }

    private static boolean isJodaTimeAvailable(TypeFactory typeFactory) {
        return typeFactory.isTypeAvailable(JodaTimeConstants.DATE_TIME_FQN);
    }

    public List<BuiltInMethod> getBuiltInMethods() {
        return this.builtInMethods;
    }
}
