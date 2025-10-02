package com.google.android.exoplayer2.source.dash.manifest;

import com.google.common.base.Objects;

/* loaded from: classes.dex */
public final class BaseUrl {
    public static final int DEFAULT_PRIORITY = 1;
    public static final int DEFAULT_WEIGHT = 1;
    public final int priority;
    public final String serviceLocation;
    public final String url;
    public final int weight;

    public BaseUrl(String url) {
        this(url, url, 1, 1);
    }

    public BaseUrl(String url, String serviceLocation, int priority, int weight) {
        this.url = url;
        this.serviceLocation = serviceLocation;
        this.priority = priority;
        this.weight = weight;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseUrl)) {
            return false;
        }
        BaseUrl baseUrl = (BaseUrl) o;
        return this.priority == baseUrl.priority && this.weight == baseUrl.weight && Objects.equal(this.url, baseUrl.url) && Objects.equal(this.serviceLocation, baseUrl.serviceLocation);
    }

    public int hashCode() {
        return Objects.hashCode(this.url, this.serviceLocation, Integer.valueOf(this.priority), Integer.valueOf(this.weight));
    }
}
