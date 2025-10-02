package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.collection.SparseArrayCompat;
import androidx.navigation.NavDeepLink;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class NavDestination {
    private static final HashMap<String, Class<?>> sClasses = new HashMap<>();
    private SparseArrayCompat<NavAction> mActions;
    private HashMap<String, NavArgument> mArguments;
    private ArrayList<NavDeepLink> mDeepLinks;
    private int mId;
    private String mIdName;
    private CharSequence mLabel;
    private final String mNavigatorName;
    private NavGraph mParent;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface ClassType {
        Class<?> value();
    }

    boolean supportsActions() {
        return true;
    }

    static class DeepLinkMatch implements Comparable<DeepLinkMatch> {
        private final NavDestination mDestination;
        private final boolean mHasMatchingAction;
        private final boolean mIsExactDeepLink;
        private final Bundle mMatchingArgs;
        private final int mMimeTypeMatchLevel;

        DeepLinkMatch(NavDestination navDestination, Bundle bundle, boolean z, boolean z2, int i) {
            this.mDestination = navDestination;
            this.mMatchingArgs = bundle;
            this.mIsExactDeepLink = z;
            this.mHasMatchingAction = z2;
            this.mMimeTypeMatchLevel = i;
        }

        NavDestination getDestination() {
            return this.mDestination;
        }

        Bundle getMatchingArgs() {
            return this.mMatchingArgs;
        }

        @Override // java.lang.Comparable
        public int compareTo(DeepLinkMatch deepLinkMatch) {
            boolean z = this.mIsExactDeepLink;
            if (z && !deepLinkMatch.mIsExactDeepLink) {
                return 1;
            }
            if (!z && deepLinkMatch.mIsExactDeepLink) {
                return -1;
            }
            Bundle bundle = this.mMatchingArgs;
            if (bundle != null && deepLinkMatch.mMatchingArgs == null) {
                return 1;
            }
            if (bundle == null && deepLinkMatch.mMatchingArgs != null) {
                return -1;
            }
            if (bundle != null) {
                int size = bundle.size() - deepLinkMatch.mMatchingArgs.size();
                if (size > 0) {
                    return 1;
                }
                if (size < 0) {
                    return -1;
                }
            }
            boolean z2 = this.mHasMatchingAction;
            if (z2 && !deepLinkMatch.mHasMatchingAction) {
                return 1;
            }
            if (z2 || !deepLinkMatch.mHasMatchingAction) {
                return this.mMimeTypeMatchLevel - deepLinkMatch.mMimeTypeMatchLevel;
            }
            return -1;
        }
    }

    protected static <C> Class<? extends C> parseClassFromName(Context context, String str, Class<? extends C> cls) {
        if (str.charAt(0) == '.') {
            str = context.getPackageName() + str;
        }
        HashMap<String, Class<?>> map = sClasses;
        Class<? extends C> cls2 = (Class<? extends C>) map.get(str);
        if (cls2 == null) {
            try {
                cls2 = (Class<? extends C>) Class.forName(str, true, context.getClassLoader());
                map.put(str, cls2);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
        if (cls.isAssignableFrom(cls2)) {
            return cls2;
        }
        throw new IllegalArgumentException(str + " must be a subclass of " + cls);
    }

    static String getDisplayName(Context context, int i) {
        if (i <= 16777215) {
            return Integer.toString(i);
        }
        try {
            return context.getResources().getResourceName(i);
        } catch (Resources.NotFoundException unused) {
            return Integer.toString(i);
        }
    }

    public final Map<String, NavArgument> getArguments() {
        HashMap<String, NavArgument> map = this.mArguments;
        return map == null ? Collections.emptyMap() : Collections.unmodifiableMap(map);
    }

    public NavDestination(Navigator<? extends NavDestination> navigator) {
        this(NavigatorProvider.getNameForNavigator(navigator.getClass()));
    }

    public NavDestination(String str) {
        this.mNavigatorName = str;
    }

    public void onInflate(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainAttributes = context.getResources().obtainAttributes(attributeSet, androidx.navigation.common.R.styleable.Navigator);
        setId(typedArrayObtainAttributes.getResourceId(androidx.navigation.common.R.styleable.Navigator_android_id, 0));
        this.mIdName = getDisplayName(context, this.mId);
        setLabel(typedArrayObtainAttributes.getText(androidx.navigation.common.R.styleable.Navigator_android_label));
        typedArrayObtainAttributes.recycle();
    }

    final void setParent(NavGraph navGraph) {
        this.mParent = navGraph;
    }

    public final NavGraph getParent() {
        return this.mParent;
    }

    public final int getId() {
        return this.mId;
    }

    public final void setId(int i) {
        this.mId = i;
        this.mIdName = null;
    }

    public String getDisplayName() {
        if (this.mIdName == null) {
            this.mIdName = Integer.toString(this.mId);
        }
        return this.mIdName;
    }

    public final void setLabel(CharSequence charSequence) {
        this.mLabel = charSequence;
    }

    public final CharSequence getLabel() {
        return this.mLabel;
    }

    public final String getNavigatorName() {
        return this.mNavigatorName;
    }

    public boolean hasDeepLink(Uri uri) {
        return hasDeepLink(new NavDeepLinkRequest(uri, null, null));
    }

    public boolean hasDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        return matchDeepLink(navDeepLinkRequest) != null;
    }

    public final void addDeepLink(String str) {
        addDeepLink(new NavDeepLink.Builder().setUriPattern(str).build());
    }

    public final void addDeepLink(NavDeepLink navDeepLink) {
        if (this.mDeepLinks == null) {
            this.mDeepLinks = new ArrayList<>();
        }
        this.mDeepLinks.add(navDeepLink);
    }

    DeepLinkMatch matchDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        ArrayList<NavDeepLink> arrayList = this.mDeepLinks;
        if (arrayList == null) {
            return null;
        }
        Iterator<NavDeepLink> it = arrayList.iterator();
        DeepLinkMatch deepLinkMatch = null;
        while (it.hasNext()) {
            NavDeepLink next = it.next();
            Uri uri = navDeepLinkRequest.getUri();
            Bundle matchingArguments = uri != null ? next.getMatchingArguments(uri, getArguments()) : null;
            String action = navDeepLinkRequest.getAction();
            boolean z = action != null && action.equals(next.getAction());
            String mimeType = navDeepLinkRequest.getMimeType();
            int mimeTypeMatchRating = mimeType != null ? next.getMimeTypeMatchRating(mimeType) : -1;
            if (matchingArguments != null || z || mimeTypeMatchRating > -1) {
                DeepLinkMatch deepLinkMatch2 = new DeepLinkMatch(this, matchingArguments, next.isExactDeepLink(), z, mimeTypeMatchRating);
                if (deepLinkMatch == null || deepLinkMatch2.compareTo(deepLinkMatch) > 0) {
                    deepLinkMatch = deepLinkMatch2;
                }
            }
        }
        return deepLinkMatch;
    }

    int[] buildDeepLinkIds() {
        ArrayDeque arrayDeque = new ArrayDeque();
        NavDestination navDestination = this;
        while (true) {
            NavGraph parent = navDestination.getParent();
            if (parent == null || parent.getStartDestination() != navDestination.getId()) {
                arrayDeque.addFirst(navDestination);
            }
            if (parent == null) {
                break;
            }
            navDestination = parent;
        }
        int[] iArr = new int[arrayDeque.size()];
        Iterator it = arrayDeque.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((NavDestination) it.next()).getId();
            i++;
        }
        return iArr;
    }

    public final NavAction getAction(int i) {
        SparseArrayCompat<NavAction> sparseArrayCompat = this.mActions;
        NavAction navAction = sparseArrayCompat == null ? null : sparseArrayCompat.get(i);
        if (navAction != null) {
            return navAction;
        }
        if (getParent() != null) {
            return getParent().getAction(i);
        }
        return null;
    }

    public final void putAction(int i, int i2) {
        putAction(i, new NavAction(i2));
    }

    public final void putAction(int i, NavAction navAction) {
        if (!supportsActions()) {
            throw new UnsupportedOperationException("Cannot add action " + i + " to " + this + " as it does not support actions, indicating that it is a terminal destination in your navigation graph and will never trigger actions.");
        }
        if (i == 0) {
            throw new IllegalArgumentException("Cannot have an action with actionId 0");
        }
        if (this.mActions == null) {
            this.mActions = new SparseArrayCompat<>();
        }
        this.mActions.put(i, navAction);
    }

    public final void removeAction(int i) {
        SparseArrayCompat<NavAction> sparseArrayCompat = this.mActions;
        if (sparseArrayCompat == null) {
            return;
        }
        sparseArrayCompat.remove(i);
    }

    public final void addArgument(String str, NavArgument navArgument) {
        if (this.mArguments == null) {
            this.mArguments = new HashMap<>();
        }
        this.mArguments.put(str, navArgument);
    }

    public final void removeArgument(String str) {
        HashMap<String, NavArgument> map = this.mArguments;
        if (map == null) {
            return;
        }
        map.remove(str);
    }

    Bundle addInDefaultArgs(Bundle bundle) {
        HashMap<String, NavArgument> map;
        if (bundle == null && ((map = this.mArguments) == null || map.isEmpty())) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        HashMap<String, NavArgument> map2 = this.mArguments;
        if (map2 != null) {
            for (Map.Entry<String, NavArgument> entry : map2.entrySet()) {
                entry.getValue().putDefaultValue(entry.getKey(), bundle2);
            }
        }
        if (bundle != null) {
            bundle2.putAll(bundle);
            HashMap<String, NavArgument> map3 = this.mArguments;
            if (map3 != null) {
                for (Map.Entry<String, NavArgument> entry2 : map3.entrySet()) {
                    if (!entry2.getValue().verify(entry2.getKey(), bundle)) {
                        throw new IllegalArgumentException("Wrong argument type for '" + entry2.getKey() + "' in argument bundle. " + entry2.getValue().getType().getName() + " expected.");
                    }
                }
            }
        }
        return bundle2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(");
        String str = this.mIdName;
        if (str == null) {
            sb.append("0x");
            sb.append(Integer.toHexString(this.mId));
        } else {
            sb.append(str);
        }
        sb.append(")");
        if (this.mLabel != null) {
            sb.append(" label=");
            sb.append(this.mLabel);
        }
        return sb.toString();
    }
}
