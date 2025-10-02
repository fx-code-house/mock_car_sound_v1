package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.navigation.NavArgument;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavOptions;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class NavInflater {
    public static final String APPLICATION_ID_PLACEHOLDER = "${applicationId}";
    private static final String TAG_ACTION = "action";
    private static final String TAG_ARGUMENT = "argument";
    private static final String TAG_DEEP_LINK = "deepLink";
    private static final String TAG_INCLUDE = "include";
    private static final ThreadLocal<TypedValue> sTmpValue = new ThreadLocal<>();
    private Context mContext;
    private NavigatorProvider mNavigatorProvider;

    public NavInflater(Context context, NavigatorProvider navigatorProvider) {
        this.mContext = context;
        this.mNavigatorProvider = navigatorProvider;
    }

    public NavGraph inflate(int i) throws Resources.NotFoundException {
        int next;
        Resources resources = this.mContext.getResources();
        XmlResourceParser xml = resources.getXml(i);
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
        do {
            try {
                try {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Exception inflating " + resources.getResourceName(i) + " line " + xml.getLineNumber(), e);
                }
            } finally {
                xml.close();
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        String name = xml.getName();
        NavDestination navDestinationInflate = inflate(resources, xml, attributeSetAsAttributeSet, i);
        if (!(navDestinationInflate instanceof NavGraph)) {
            throw new IllegalArgumentException("Root element <" + name + "> did not inflate into a NavGraph");
        }
        return (NavGraph) navDestinationInflate;
    }

    private NavDestination inflate(Resources resources, XmlResourceParser xmlResourceParser, AttributeSet attributeSet, int i) throws XmlPullParserException, IOException {
        int depth;
        NavDestination navDestinationCreateDestination = this.mNavigatorProvider.getNavigator(xmlResourceParser.getName()).createDestination();
        navDestinationCreateDestination.onInflate(this.mContext, attributeSet);
        int depth2 = xmlResourceParser.getDepth() + 1;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || ((depth = xmlResourceParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2) {
                String name = xmlResourceParser.getName();
                if (TAG_ARGUMENT.equals(name)) {
                    inflateArgumentForDestination(resources, navDestinationCreateDestination, attributeSet, i);
                } else if (TAG_DEEP_LINK.equals(name)) {
                    inflateDeepLink(resources, navDestinationCreateDestination, attributeSet);
                } else if ("action".equals(name)) {
                    inflateAction(resources, navDestinationCreateDestination, attributeSet, xmlResourceParser, i);
                } else if (TAG_INCLUDE.equals(name) && (navDestinationCreateDestination instanceof NavGraph)) {
                    TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, R.styleable.NavInclude);
                    ((NavGraph) navDestinationCreateDestination).addDestination(inflate(typedArrayObtainAttributes.getResourceId(R.styleable.NavInclude_graph, 0)));
                    typedArrayObtainAttributes.recycle();
                } else if (navDestinationCreateDestination instanceof NavGraph) {
                    ((NavGraph) navDestinationCreateDestination).addDestination(inflate(resources, xmlResourceParser, attributeSet, i));
                }
            }
        }
        return navDestinationCreateDestination;
    }

    private void inflateArgumentForDestination(Resources resources, NavDestination navDestination, AttributeSet attributeSet, int i) throws XmlPullParserException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, androidx.navigation.common.R.styleable.NavArgument);
        String string = typedArrayObtainAttributes.getString(androidx.navigation.common.R.styleable.NavArgument_android_name);
        if (string == null) {
            throw new XmlPullParserException("Arguments must have a name");
        }
        navDestination.addArgument(string, inflateArgument(typedArrayObtainAttributes, resources, i));
        typedArrayObtainAttributes.recycle();
    }

    private void inflateArgumentForBundle(Resources resources, Bundle bundle, AttributeSet attributeSet, int i) throws XmlPullParserException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, androidx.navigation.common.R.styleable.NavArgument);
        String string = typedArrayObtainAttributes.getString(androidx.navigation.common.R.styleable.NavArgument_android_name);
        if (string == null) {
            throw new XmlPullParserException("Arguments must have a name");
        }
        NavArgument navArgumentInflateArgument = inflateArgument(typedArrayObtainAttributes, resources, i);
        if (navArgumentInflateArgument.isDefaultValuePresent()) {
            navArgumentInflateArgument.putDefaultValue(string, bundle);
        }
        typedArrayObtainAttributes.recycle();
    }

    private NavArgument inflateArgument(TypedArray typedArray, Resources resources, int i) throws XmlPullParserException {
        NavArgument.Builder builder = new NavArgument.Builder();
        builder.setIsNullable(typedArray.getBoolean(androidx.navigation.common.R.styleable.NavArgument_nullable, false));
        ThreadLocal<TypedValue> threadLocal = sTmpValue;
        TypedValue typedValue = threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        String string = typedArray.getString(androidx.navigation.common.R.styleable.NavArgument_argType);
        Object value = null;
        NavType<?> navTypeFromArgType = string != null ? NavType.fromArgType(string, resources.getResourcePackageName(i)) : null;
        if (typedArray.getValue(androidx.navigation.common.R.styleable.NavArgument_android_defaultValue, typedValue)) {
            if (navTypeFromArgType == NavType.ReferenceType) {
                if (typedValue.resourceId != 0) {
                    value = Integer.valueOf(typedValue.resourceId);
                } else if (typedValue.type == 16 && typedValue.data == 0) {
                    value = 0;
                } else {
                    throw new XmlPullParserException("unsupported value '" + ((Object) typedValue.string) + "' for " + navTypeFromArgType.getName() + ". Must be a reference to a resource.");
                }
            } else if (typedValue.resourceId != 0) {
                if (navTypeFromArgType == null) {
                    navTypeFromArgType = NavType.ReferenceType;
                    value = Integer.valueOf(typedValue.resourceId);
                } else {
                    throw new XmlPullParserException("unsupported value '" + ((Object) typedValue.string) + "' for " + navTypeFromArgType.getName() + ". You must use a \"" + NavType.ReferenceType.getName() + "\" type to reference other resources.");
                }
            } else if (navTypeFromArgType == NavType.StringType) {
                value = typedArray.getString(androidx.navigation.common.R.styleable.NavArgument_android_defaultValue);
            } else {
                int i2 = typedValue.type;
                if (i2 == 3) {
                    String string2 = typedValue.string.toString();
                    if (navTypeFromArgType == null) {
                        navTypeFromArgType = NavType.inferFromValue(string2);
                    }
                    value = navTypeFromArgType.parseValue(string2);
                } else if (i2 == 4) {
                    navTypeFromArgType = checkNavType(typedValue, navTypeFromArgType, NavType.FloatType, string, "float");
                    value = Float.valueOf(typedValue.getFloat());
                } else if (i2 == 5) {
                    navTypeFromArgType = checkNavType(typedValue, navTypeFromArgType, NavType.IntType, string, "dimension");
                    value = Integer.valueOf((int) typedValue.getDimension(resources.getDisplayMetrics()));
                } else if (i2 == 18) {
                    navTypeFromArgType = checkNavType(typedValue, navTypeFromArgType, NavType.BoolType, string, "boolean");
                    value = Boolean.valueOf(typedValue.data != 0);
                } else if (typedValue.type >= 16 && typedValue.type <= 31) {
                    navTypeFromArgType = checkNavType(typedValue, navTypeFromArgType, NavType.IntType, string, "integer");
                    value = Integer.valueOf(typedValue.data);
                } else {
                    throw new XmlPullParserException("unsupported argument type " + typedValue.type);
                }
            }
        }
        if (value != null) {
            builder.setDefaultValue(value);
        }
        if (navTypeFromArgType != null) {
            builder.setType(navTypeFromArgType);
        }
        return builder.build();
    }

    private static NavType checkNavType(TypedValue typedValue, NavType navType, NavType navType2, String str, String str2) throws XmlPullParserException {
        if (navType == null || navType == navType2) {
            return navType != null ? navType : navType2;
        }
        throw new XmlPullParserException("Type is " + str + " but found " + str2 + ": " + typedValue.data);
    }

    private void inflateDeepLink(Resources resources, NavDestination navDestination, AttributeSet attributeSet) throws XmlPullParserException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, androidx.navigation.common.R.styleable.NavDeepLink);
        String string = typedArrayObtainAttributes.getString(androidx.navigation.common.R.styleable.NavDeepLink_uri);
        String string2 = typedArrayObtainAttributes.getString(androidx.navigation.common.R.styleable.NavDeepLink_action);
        String string3 = typedArrayObtainAttributes.getString(androidx.navigation.common.R.styleable.NavDeepLink_mimeType);
        if (TextUtils.isEmpty(string) && TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
            throw new XmlPullParserException("Every <deepLink> must include at least one of app:uri, app:action, or app:mimeType");
        }
        NavDeepLink.Builder builder = new NavDeepLink.Builder();
        if (string != null) {
            builder.setUriPattern(string.replace(APPLICATION_ID_PLACEHOLDER, this.mContext.getPackageName()));
        }
        if (!TextUtils.isEmpty(string2)) {
            builder.setAction(string2.replace(APPLICATION_ID_PLACEHOLDER, this.mContext.getPackageName()));
        }
        if (string3 != null) {
            builder.setMimeType(string3.replace(APPLICATION_ID_PLACEHOLDER, this.mContext.getPackageName()));
        }
        navDestination.addDeepLink(builder.build());
        typedArrayObtainAttributes.recycle();
    }

    private void inflateAction(Resources resources, NavDestination navDestination, AttributeSet attributeSet, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException, IOException {
        int depth;
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, androidx.navigation.common.R.styleable.NavAction);
        int resourceId = typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_android_id, 0);
        NavAction navAction = new NavAction(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_destination, 0));
        NavOptions.Builder builder = new NavOptions.Builder();
        builder.setLaunchSingleTop(typedArrayObtainAttributes.getBoolean(androidx.navigation.common.R.styleable.NavAction_launchSingleTop, false));
        builder.setPopUpTo(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_popUpTo, -1), typedArrayObtainAttributes.getBoolean(androidx.navigation.common.R.styleable.NavAction_popUpToInclusive, false));
        builder.setEnterAnim(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_enterAnim, -1));
        builder.setExitAnim(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_exitAnim, -1));
        builder.setPopEnterAnim(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_popEnterAnim, -1));
        builder.setPopExitAnim(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.NavAction_popExitAnim, -1));
        navAction.setNavOptions(builder.build());
        Bundle bundle = new Bundle();
        int depth2 = xmlResourceParser.getDepth() + 1;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || ((depth = xmlResourceParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && TAG_ARGUMENT.equals(xmlResourceParser.getName())) {
                inflateArgumentForBundle(resources, bundle, attributeSet, i);
            }
        }
        if (!bundle.isEmpty()) {
            navAction.setDefaultArguments(bundle);
        }
        navDestination.putAction(resourceId, navAction);
        typedArrayObtainAttributes.recycle();
    }
}
