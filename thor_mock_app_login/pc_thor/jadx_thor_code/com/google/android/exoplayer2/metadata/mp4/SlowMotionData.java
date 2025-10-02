package com.google.android.exoplayer2.metadata.mp4;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes.dex */
public final class SlowMotionData implements Metadata.Entry {
    public static final Parcelable.Creator<SlowMotionData> CREATOR = new Parcelable.Creator<SlowMotionData>() { // from class: com.google.android.exoplayer2.metadata.mp4.SlowMotionData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SlowMotionData createFromParcel(Parcel in) {
            ArrayList arrayList = new ArrayList();
            in.readList(arrayList, Segment.class.getClassLoader());
            return new SlowMotionData(arrayList);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SlowMotionData[] newArray(int size) {
            return new SlowMotionData[size];
        }
    };
    public final List<Segment> segments;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Segment implements Parcelable {
        public static final Comparator<Segment> BY_START_THEN_END_THEN_DIVISOR = new Comparator() { // from class: com.google.android.exoplayer2.metadata.mp4.SlowMotionData$Segment$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                SlowMotionData.Segment segment = (SlowMotionData.Segment) obj;
                SlowMotionData.Segment segment2 = (SlowMotionData.Segment) obj2;
                return ComparisonChain.start().compare(segment.startTimeMs, segment2.startTimeMs).compare(segment.endTimeMs, segment2.endTimeMs).compare(segment.speedDivisor, segment2.speedDivisor).result();
            }
        };
        public static final Parcelable.Creator<Segment> CREATOR = new Parcelable.Creator<Segment>() { // from class: com.google.android.exoplayer2.metadata.mp4.SlowMotionData.Segment.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Segment createFromParcel(Parcel in) {
                return new Segment(in.readLong(), in.readLong(), in.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Segment[] newArray(int size) {
                return new Segment[size];
            }
        };
        public final long endTimeMs;
        public final int speedDivisor;
        public final long startTimeMs;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Segment(long startTimeMs, long endTimeMs, int speedDivisor) {
            Assertions.checkArgument(startTimeMs < endTimeMs);
            this.startTimeMs = startTimeMs;
            this.endTimeMs = endTimeMs;
            this.speedDivisor = speedDivisor;
        }

        public String toString() {
            return Util.formatInvariant("Segment: startTimeMs=%d, endTimeMs=%d, speedDivisor=%d", Long.valueOf(this.startTimeMs), Long.valueOf(this.endTimeMs), Integer.valueOf(this.speedDivisor));
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Segment segment = (Segment) o;
            return this.startTimeMs == segment.startTimeMs && this.endTimeMs == segment.endTimeMs && this.speedDivisor == segment.speedDivisor;
        }

        public int hashCode() {
            return Objects.hashCode(Long.valueOf(this.startTimeMs), Long.valueOf(this.endTimeMs), Integer.valueOf(this.speedDivisor));
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.startTimeMs);
            dest.writeLong(this.endTimeMs);
            dest.writeInt(this.speedDivisor);
        }
    }

    public SlowMotionData(List<Segment> segments) {
        this.segments = segments;
        Assertions.checkArgument(!doSegmentsOverlap(segments));
    }

    public String toString() {
        String strValueOf = String.valueOf(this.segments);
        return new StringBuilder(String.valueOf(strValueOf).length() + 21).append("SlowMotion: segments=").append(strValueOf).toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.segments.equals(((SlowMotionData) o).segments);
    }

    public int hashCode() {
        return this.segments.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.segments);
    }

    private static boolean doSegmentsOverlap(List<Segment> segments) {
        if (segments.isEmpty()) {
            return false;
        }
        long j = segments.get(0).endTimeMs;
        for (int i = 1; i < segments.size(); i++) {
            if (segments.get(i).startTimeMs < j) {
                return true;
            }
            j = segments.get(i).endTimeMs;
        }
        return false;
    }
}
