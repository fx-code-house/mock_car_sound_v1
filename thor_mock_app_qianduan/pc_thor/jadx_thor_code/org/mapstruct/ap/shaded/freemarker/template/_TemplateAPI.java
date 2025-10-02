package org.mapstruct.ap.shaded.freemarker.template;

import org.mapstruct.ap.shaded.freemarker.core.TemplateObject;
import org.mapstruct.ap.shaded.freemarker.template.utility.NullArgumentException;

/* loaded from: classes3.dex */
public class _TemplateAPI {
    public static final int VERSION_INT_2_3_0 = Configuration.VERSION_2_3_0.intValue();
    public static final int VERSION_INT_2_3_19 = Configuration.VERSION_2_3_19.intValue();
    public static final int VERSION_INT_2_3_20 = Configuration.VERSION_2_3_20.intValue();
    public static final int VERSION_INT_2_3_21 = Configuration.VERSION_2_3_21.intValue();

    public static void checkVersionNotNullAndSupported(Version version) {
        NullArgumentException.check("incompatibleImprovements", version);
        int iIntValue = version.intValue();
        if (iIntValue > Configuration.getVersion().intValue()) {
            throw new IllegalArgumentException(new StringBuffer("The FreeMarker version requested by \"incompatibleImprovements\" was ").append(version).append(", but the installed FreeMarker version is only ").append(Configuration.getVersion()).append(". You may need to upgrade FreeMarker in your project.").toString());
        }
        if (iIntValue < 200300) {
            throw new IllegalArgumentException("\"incompatibleImprovements\" must be at least 2.3.0.");
        }
    }

    public static int getTemplateLanguageVersionAsInt(TemplateObject templateObject) {
        return getTemplateLanguageVersionAsInt(templateObject.getTemplate());
    }

    public static int getTemplateLanguageVersionAsInt(Template template) {
        return template.getTemplateLanguageVersion().intValue();
    }

    public static void DefaultObjectWrapperFactory_clearInstanceCache() {
        DefaultObjectWrapperBuilder.clearInstanceCache();
    }
}
