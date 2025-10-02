package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class SpliceScheduleCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceScheduleCommand> CREATOR = new Parcelable.Creator<SpliceScheduleCommand>() { // from class: com.google.android.exoplayer2.metadata.scte35.SpliceScheduleCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceScheduleCommand createFromParcel(Parcel in) {
            return new SpliceScheduleCommand(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceScheduleCommand[] newArray(int size) {
            return new SpliceScheduleCommand[size];
        }
    };
    public final List<Event> events;

    public static final class Event {
        public final boolean autoReturn;
        public final int availNum;
        public final int availsExpected;
        public final long breakDurationUs;
        public final List<ComponentSplice> componentSpliceList;
        public final boolean outOfNetworkIndicator;
        public final boolean programSpliceFlag;
        public final boolean spliceEventCancelIndicator;
        public final long spliceEventId;
        public final int uniqueProgramId;
        public final long utcSpliceTime;

        private Event(long spliceEventId, boolean spliceEventCancelIndicator, boolean outOfNetworkIndicator, boolean programSpliceFlag, List<ComponentSplice> componentSpliceList, long utcSpliceTime, boolean autoReturn, long breakDurationUs, int uniqueProgramId, int availNum, int availsExpected) {
            this.spliceEventId = spliceEventId;
            this.spliceEventCancelIndicator = spliceEventCancelIndicator;
            this.outOfNetworkIndicator = outOfNetworkIndicator;
            this.programSpliceFlag = programSpliceFlag;
            this.componentSpliceList = Collections.unmodifiableList(componentSpliceList);
            this.utcSpliceTime = utcSpliceTime;
            this.autoReturn = autoReturn;
            this.breakDurationUs = breakDurationUs;
            this.uniqueProgramId = uniqueProgramId;
            this.availNum = availNum;
            this.availsExpected = availsExpected;
        }

        private Event(Parcel in) {
            this.spliceEventId = in.readLong();
            this.spliceEventCancelIndicator = in.readByte() == 1;
            this.outOfNetworkIndicator = in.readByte() == 1;
            this.programSpliceFlag = in.readByte() == 1;
            int i = in.readInt();
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(ComponentSplice.createFromParcel(in));
            }
            this.componentSpliceList = Collections.unmodifiableList(arrayList);
            this.utcSpliceTime = in.readLong();
            this.autoReturn = in.readByte() == 1;
            this.breakDurationUs = in.readLong();
            this.uniqueProgramId = in.readInt();
            this.availNum = in.readInt();
            this.availsExpected = in.readInt();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Event parseFromSection(ParsableByteArray sectionData) {
            ArrayList arrayList;
            boolean z;
            long j;
            boolean z2;
            long j2;
            int i;
            int i2;
            int unsignedByte;
            boolean z3;
            boolean z4;
            long unsignedInt;
            long unsignedInt2 = sectionData.readUnsignedInt();
            boolean z5 = (sectionData.readUnsignedByte() & 128) != 0;
            ArrayList arrayList2 = new ArrayList();
            if (z5) {
                arrayList = arrayList2;
                z = false;
                j = C.TIME_UNSET;
                z2 = false;
                j2 = C.TIME_UNSET;
                i = 0;
                i2 = 0;
                unsignedByte = 0;
                z3 = false;
            } else {
                int unsignedByte2 = sectionData.readUnsignedByte();
                boolean z6 = (unsignedByte2 & 128) != 0;
                boolean z7 = (unsignedByte2 & 64) != 0;
                boolean z8 = (unsignedByte2 & 32) != 0;
                long unsignedInt3 = z7 ? sectionData.readUnsignedInt() : C.TIME_UNSET;
                if (!z7) {
                    int unsignedByte3 = sectionData.readUnsignedByte();
                    ArrayList arrayList3 = new ArrayList(unsignedByte3);
                    for (int i3 = 0; i3 < unsignedByte3; i3++) {
                        arrayList3.add(new ComponentSplice(sectionData.readUnsignedByte(), sectionData.readUnsignedInt()));
                    }
                    arrayList2 = arrayList3;
                }
                if (z8) {
                    long unsignedByte4 = sectionData.readUnsignedByte();
                    boolean z9 = (128 & unsignedByte4) != 0;
                    unsignedInt = ((((unsignedByte4 & 1) << 32) | sectionData.readUnsignedInt()) * 1000) / 90;
                    z4 = z9;
                } else {
                    z4 = false;
                    unsignedInt = C.TIME_UNSET;
                }
                int unsignedShort = sectionData.readUnsignedShort();
                int unsignedByte5 = sectionData.readUnsignedByte();
                z3 = z7;
                unsignedByte = sectionData.readUnsignedByte();
                j2 = unsignedInt;
                arrayList = arrayList2;
                long j3 = unsignedInt3;
                i = unsignedShort;
                i2 = unsignedByte5;
                j = j3;
                boolean z10 = z6;
                z2 = z4;
                z = z10;
            }
            return new Event(unsignedInt2, z5, z, z3, arrayList, j, z2, j2, i, i2, unsignedByte);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeLong(this.spliceEventId);
            parcel.writeByte(this.spliceEventCancelIndicator ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.outOfNetworkIndicator ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.programSpliceFlag ? (byte) 1 : (byte) 0);
            int size = this.componentSpliceList.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                this.componentSpliceList.get(i).writeToParcel(parcel);
            }
            parcel.writeLong(this.utcSpliceTime);
            parcel.writeByte(this.autoReturn ? (byte) 1 : (byte) 0);
            parcel.writeLong(this.breakDurationUs);
            parcel.writeInt(this.uniqueProgramId);
            parcel.writeInt(this.availNum);
            parcel.writeInt(this.availsExpected);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Event createFromParcel(Parcel in) {
            return new Event(in);
        }
    }

    public static final class ComponentSplice {
        public final int componentTag;
        public final long utcSpliceTime;

        private ComponentSplice(int componentTag, long utcSpliceTime) {
            this.componentTag = componentTag;
            this.utcSpliceTime = utcSpliceTime;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ComponentSplice createFromParcel(Parcel in) {
            return new ComponentSplice(in.readInt(), in.readLong());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel dest) {
            dest.writeInt(this.componentTag);
            dest.writeLong(this.utcSpliceTime);
        }
    }

    private SpliceScheduleCommand(List<Event> events) {
        this.events = Collections.unmodifiableList(events);
    }

    private SpliceScheduleCommand(Parcel in) {
        int i = in.readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(Event.createFromParcel(in));
        }
        this.events = Collections.unmodifiableList(arrayList);
    }

    static SpliceScheduleCommand parseFromSection(ParsableByteArray sectionData) {
        int unsignedByte = sectionData.readUnsignedByte();
        ArrayList arrayList = new ArrayList(unsignedByte);
        for (int i = 0; i < unsignedByte; i++) {
            arrayList.add(Event.parseFromSection(sectionData));
        }
        return new SpliceScheduleCommand(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int size = this.events.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            this.events.get(i).writeToParcel(dest);
        }
    }
}
