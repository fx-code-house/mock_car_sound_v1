package com.google.android.exoplayer2.util;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class BundleableUtils {
    public static Bundle toNullableBundle(Bundleable bundleable) {
        if (bundleable == null) {
            return null;
        }
        return bundleable.toBundle();
    }

    public static <T extends Bundleable> T fromNullableBundle(Bundleable.Creator<T> creator, Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return (T) creator.fromBundle(bundle);
    }

    public static <T extends Bundleable> T fromNullableBundle(Bundleable.Creator<T> creator, Bundle bundle, T t) {
        return bundle == null ? t : (T) creator.fromBundle(bundle);
    }

    public static <T extends Bundleable> ImmutableList<Bundle> toBundleList(List<T> bundleableList) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < bundleableList.size(); i++) {
            builder.add((ImmutableList.Builder) bundleableList.get(i).toBundle());
        }
        return builder.build();
    }

    public static <T extends Bundleable> ImmutableList<T> fromBundleList(Bundleable.Creator<T> creator, List<Bundle> bundleList) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < bundleList.size(); i++) {
            builder.add((ImmutableList.Builder) creator.fromBundle(bundleList.get(i)));
        }
        return builder.build();
    }

    public static <T extends Bundleable> ArrayList<Bundle> toBundleArrayList(List<T> bundleableList) {
        ArrayList<Bundle> arrayList = new ArrayList<>(bundleableList.size());
        for (int i = 0; i < bundleableList.size(); i++) {
            arrayList.add(bundleableList.get(i).toBundle());
        }
        return arrayList;
    }

    private BundleableUtils() {
    }
}
