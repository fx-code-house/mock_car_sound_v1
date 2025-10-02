package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public interface SeekMap {
    long getDurationUs();

    SeekPoints getSeekPoints(long timeUs);

    boolean isSeekable();

    public static class Unseekable implements SeekMap {
        private final long durationUs;
        private final SeekPoints startSeekPoints;

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return false;
        }

        public Unseekable(long durationUs) {
            this(durationUs, 0L);
        }

        public Unseekable(long durationUs, long startPosition) {
            this.durationUs = durationUs;
            this.startSeekPoints = new SeekPoints(startPosition == 0 ? SeekPoint.START : new SeekPoint(0L, startPosition));
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return this.durationUs;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekPoints getSeekPoints(long timeUs) {
            return this.startSeekPoints;
        }
    }

    public static final class SeekPoints {
        public final SeekPoint first;
        public final SeekPoint second;

        public SeekPoints(SeekPoint point) {
            this(point, point);
        }

        public SeekPoints(SeekPoint first, SeekPoint second) {
            this.first = (SeekPoint) Assertions.checkNotNull(first);
            this.second = (SeekPoint) Assertions.checkNotNull(second);
        }

        public String toString() {
            String string;
            String strValueOf = String.valueOf(this.first);
            if (this.first.equals(this.second)) {
                string = "";
            } else {
                String strValueOf2 = String.valueOf(this.second);
                string = new StringBuilder(String.valueOf(strValueOf2).length() + 2).append(", ").append(strValueOf2).toString();
            }
            return new StringBuilder(String.valueOf(strValueOf).length() + 2 + String.valueOf(string).length()).append("[").append(strValueOf).append(string).append("]").toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SeekPoints seekPoints = (SeekPoints) obj;
            return this.first.equals(seekPoints.first) && this.second.equals(seekPoints.second);
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }
    }
}
