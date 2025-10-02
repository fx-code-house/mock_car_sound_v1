package com.google.android.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class SpliceInsertCommand extends SpliceCommand {
    public static final Parcelable.Creator<SpliceInsertCommand> CREATOR = new Parcelable.Creator<SpliceInsertCommand>() { // from class: com.google.android.exoplayer2.metadata.scte35.SpliceInsertCommand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceInsertCommand createFromParcel(Parcel in) {
            return new SpliceInsertCommand(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SpliceInsertCommand[] newArray(int size) {
            return new SpliceInsertCommand[size];
        }
    };
    public final boolean autoReturn;
    public final int availNum;
    public final int availsExpected;
    public final long breakDurationUs;
    public final List<ComponentSplice> componentSpliceList;
    public final boolean outOfNetworkIndicator;
    public final boolean programSpliceFlag;
    public final long programSplicePlaybackPositionUs;
    public final long programSplicePts;
    public final boolean spliceEventCancelIndicator;
    public final long spliceEventId;
    public final boolean spliceImmediateFlag;
    public final int uniqueProgramId;

    private SpliceInsertCommand(long spliceEventId, boolean spliceEventCancelIndicator, boolean outOfNetworkIndicator, boolean programSpliceFlag, boolean spliceImmediateFlag, long programSplicePts, long programSplicePlaybackPositionUs, List<ComponentSplice> componentSpliceList, boolean autoReturn, long breakDurationUs, int uniqueProgramId, int availNum, int availsExpected) {
        this.spliceEventId = spliceEventId;
        this.spliceEventCancelIndicator = spliceEventCancelIndicator;
        this.outOfNetworkIndicator = outOfNetworkIndicator;
        this.programSpliceFlag = programSpliceFlag;
        this.spliceImmediateFlag = spliceImmediateFlag;
        this.programSplicePts = programSplicePts;
        this.programSplicePlaybackPositionUs = programSplicePlaybackPositionUs;
        this.componentSpliceList = Collections.unmodifiableList(componentSpliceList);
        this.autoReturn = autoReturn;
        this.breakDurationUs = breakDurationUs;
        this.uniqueProgramId = uniqueProgramId;
        this.availNum = availNum;
        this.availsExpected = availsExpected;
    }

    private SpliceInsertCommand(Parcel in) {
        this.spliceEventId = in.readLong();
        this.spliceEventCancelIndicator = in.readByte() == 1;
        this.outOfNetworkIndicator = in.readByte() == 1;
        this.programSpliceFlag = in.readByte() == 1;
        this.spliceImmediateFlag = in.readByte() == 1;
        this.programSplicePts = in.readLong();
        this.programSplicePlaybackPositionUs = in.readLong();
        int i = in.readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(ComponentSplice.createFromParcel(in));
        }
        this.componentSpliceList = Collections.unmodifiableList(arrayList);
        this.autoReturn = in.readByte() == 1;
        this.breakDurationUs = in.readLong();
        this.uniqueProgramId = in.readInt();
        this.availNum = in.readInt();
        this.availsExpected = in.readInt();
    }

    static SpliceInsertCommand parseFromSection(ParsableByteArray sectionData, long ptsAdjustment, TimestampAdjuster timestampAdjuster) {
        List list;
        boolean z;
        boolean z2;
        long j;
        boolean z3;
        long j2;
        int unsignedShort;
        int unsignedByte;
        int unsignedByte2;
        boolean z4;
        boolean z5;
        long unsignedInt;
        long unsignedInt2 = sectionData.readUnsignedInt();
        boolean z6 = (sectionData.readUnsignedByte() & 128) != 0;
        List listEmptyList = Collections.emptyList();
        if (z6) {
            list = listEmptyList;
            z = false;
            z2 = false;
            j = C.TIME_UNSET;
            z3 = false;
            j2 = C.TIME_UNSET;
            unsignedShort = 0;
            unsignedByte = 0;
            unsignedByte2 = 0;
            z4 = false;
        } else {
            int unsignedByte3 = sectionData.readUnsignedByte();
            boolean z7 = (unsignedByte3 & 128) != 0;
            boolean z8 = (unsignedByte3 & 64) != 0;
            boolean z9 = (unsignedByte3 & 32) != 0;
            boolean z10 = (unsignedByte3 & 16) != 0;
            long spliceTime = (!z8 || z10) ? C.TIME_UNSET : TimeSignalCommand.parseSpliceTime(sectionData, ptsAdjustment);
            if (!z8) {
                int unsignedByte4 = sectionData.readUnsignedByte();
                ArrayList arrayList = new ArrayList(unsignedByte4);
                for (int i = 0; i < unsignedByte4; i++) {
                    int unsignedByte5 = sectionData.readUnsignedByte();
                    long spliceTime2 = !z10 ? TimeSignalCommand.parseSpliceTime(sectionData, ptsAdjustment) : C.TIME_UNSET;
                    arrayList.add(new ComponentSplice(unsignedByte5, spliceTime2, timestampAdjuster.adjustTsTimestamp(spliceTime2)));
                }
                listEmptyList = arrayList;
            }
            if (z9) {
                long unsignedByte6 = sectionData.readUnsignedByte();
                boolean z11 = (128 & unsignedByte6) != 0;
                unsignedInt = ((((unsignedByte6 & 1) << 32) | sectionData.readUnsignedInt()) * 1000) / 90;
                z5 = z11;
            } else {
                z5 = false;
                unsignedInt = C.TIME_UNSET;
            }
            unsignedShort = sectionData.readUnsignedShort();
            z4 = z8;
            unsignedByte = sectionData.readUnsignedByte();
            unsignedByte2 = sectionData.readUnsignedByte();
            list = listEmptyList;
            long j3 = spliceTime;
            z3 = z5;
            j2 = unsignedInt;
            z2 = z10;
            z = z7;
            j = j3;
        }
        return new SpliceInsertCommand(unsignedInt2, z6, z, z4, z2, j, timestampAdjuster.adjustTsTimestamp(j), list, z3, j2, unsignedShort, unsignedByte, unsignedByte2);
    }

    public static final class ComponentSplice {
        public final long componentSplicePlaybackPositionUs;
        public final long componentSplicePts;
        public final int componentTag;

        private ComponentSplice(int componentTag, long componentSplicePts, long componentSplicePlaybackPositionUs) {
            this.componentTag = componentTag;
            this.componentSplicePts = componentSplicePts;
            this.componentSplicePlaybackPositionUs = componentSplicePlaybackPositionUs;
        }

        public void writeToParcel(Parcel dest) {
            dest.writeInt(this.componentTag);
            dest.writeLong(this.componentSplicePts);
            dest.writeLong(this.componentSplicePlaybackPositionUs);
        }

        public static ComponentSplice createFromParcel(Parcel in) {
            return new ComponentSplice(in.readInt(), in.readLong(), in.readLong());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.spliceEventId);
        parcel.writeByte(this.spliceEventCancelIndicator ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.outOfNetworkIndicator ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.programSpliceFlag ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.spliceImmediateFlag ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.programSplicePts);
        parcel.writeLong(this.programSplicePlaybackPositionUs);
        int size = this.componentSpliceList.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.componentSpliceList.get(i2).writeToParcel(parcel);
        }
        parcel.writeByte(this.autoReturn ? (byte) 1 : (byte) 0);
        parcel.writeLong(this.breakDurationUs);
        parcel.writeInt(this.uniqueProgramId);
        parcel.writeInt(this.availNum);
        parcel.writeInt(this.availsExpected);
    }
}
