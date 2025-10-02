package com.thor.app.service.state;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UploadServiceState.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0006\t\n\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState;", "", "()V", "Downloading", "Polling", "ProgressUploading", "Reconnect", "Start", "Stop", "Lcom/thor/app/service/state/UploadServiceState$Downloading;", "Lcom/thor/app/service/state/UploadServiceState$Polling;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading;", "Lcom/thor/app/service/state/UploadServiceState$Reconnect;", "Lcom/thor/app/service/state/UploadServiceState$Start;", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes3.dex */
public abstract class UploadServiceState {
    public /* synthetic */ UploadServiceState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* compiled from: UploadServiceState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Start;", "Lcom/thor/app/service/state/UploadServiceState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Start extends UploadServiceState {
        public static final Start INSTANCE = new Start();

        private Start() {
            super(null);
        }
    }

    private UploadServiceState() {
    }

    /* compiled from: UploadServiceState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Downloading;", "Lcom/thor/app/service/state/UploadServiceState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Downloading extends UploadServiceState {
        public static final Downloading INSTANCE = new Downloading();

        private Downloading() {
            super(null);
        }
    }

    /* compiled from: UploadServiceState.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0007\b\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading;", "Lcom/thor/app/service/state/UploadServiceState;", NotificationCompat.CATEGORY_PROGRESS, "", "(I)V", "getProgress", "()I", "UploadingFirmware", "UploadingSgu", "UploadingSoundV2", "UploadingSoundV3", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingFirmware;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSgu;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV3;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static abstract class ProgressUploading extends UploadServiceState {
        private final int progress;

        public /* synthetic */ ProgressUploading(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0005\u0006\u0007B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004\u0082\u0001\u0003\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading;", "progressSound", "", "(I)V", "UploadRules", "UploadRulesModel", "UploadSample", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2$UploadRules;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2$UploadRulesModel;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2$UploadSample;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static abstract class UploadingSoundV2 extends ProgressUploading {
            public /* synthetic */ UploadingSoundV2(int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(i);
            }

            /* compiled from: UploadServiceState.kt */
            @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2$UploadSample;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2;", "progressSample", "", "(I)V", "getProgressSample", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
            public static final /* data */ class UploadSample extends UploadingSoundV2 {
                private final int progressSample;

                public static /* synthetic */ UploadSample copy$default(UploadSample uploadSample, int i, int i2, Object obj) {
                    if ((i2 & 1) != 0) {
                        i = uploadSample.progressSample;
                    }
                    return uploadSample.copy(i);
                }

                /* renamed from: component1, reason: from getter */
                public final int getProgressSample() {
                    return this.progressSample;
                }

                public final UploadSample copy(int progressSample) {
                    return new UploadSample(progressSample);
                }

                public boolean equals(Object other) {
                    if (this == other) {
                        return true;
                    }
                    return (other instanceof UploadSample) && this.progressSample == ((UploadSample) other).progressSample;
                }

                public int hashCode() {
                    return Integer.hashCode(this.progressSample);
                }

                public String toString() {
                    return "UploadSample(progressSample=" + this.progressSample + ")";
                }

                public UploadSample(int i) {
                    super(i, null);
                    this.progressSample = i;
                }

                public final int getProgressSample() {
                    return this.progressSample;
                }
            }

            private UploadingSoundV2(int i) {
                super(i, null);
            }

            /* compiled from: UploadServiceState.kt */
            @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2$UploadRules;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2;", "progressRules", "", "(I)V", "getProgressRules", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
            public static final /* data */ class UploadRules extends UploadingSoundV2 {
                private final int progressRules;

                public static /* synthetic */ UploadRules copy$default(UploadRules uploadRules, int i, int i2, Object obj) {
                    if ((i2 & 1) != 0) {
                        i = uploadRules.progressRules;
                    }
                    return uploadRules.copy(i);
                }

                /* renamed from: component1, reason: from getter */
                public final int getProgressRules() {
                    return this.progressRules;
                }

                public final UploadRules copy(int progressRules) {
                    return new UploadRules(progressRules);
                }

                public boolean equals(Object other) {
                    if (this == other) {
                        return true;
                    }
                    return (other instanceof UploadRules) && this.progressRules == ((UploadRules) other).progressRules;
                }

                public int hashCode() {
                    return Integer.hashCode(this.progressRules);
                }

                public String toString() {
                    return "UploadRules(progressRules=" + this.progressRules + ")";
                }

                public UploadRules(int i) {
                    super(i, null);
                    this.progressRules = i;
                }

                public final int getProgressRules() {
                    return this.progressRules;
                }
            }

            /* compiled from: UploadServiceState.kt */
            @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2$UploadRulesModel;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV2;", "progressRulesModel", "", "(I)V", "getProgressRulesModel", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
            public static final /* data */ class UploadRulesModel extends UploadingSoundV2 {
                private final int progressRulesModel;

                public static /* synthetic */ UploadRulesModel copy$default(UploadRulesModel uploadRulesModel, int i, int i2, Object obj) {
                    if ((i2 & 1) != 0) {
                        i = uploadRulesModel.progressRulesModel;
                    }
                    return uploadRulesModel.copy(i);
                }

                /* renamed from: component1, reason: from getter */
                public final int getProgressRulesModel() {
                    return this.progressRulesModel;
                }

                public final UploadRulesModel copy(int progressRulesModel) {
                    return new UploadRulesModel(progressRulesModel);
                }

                public boolean equals(Object other) {
                    if (this == other) {
                        return true;
                    }
                    return (other instanceof UploadRulesModel) && this.progressRulesModel == ((UploadRulesModel) other).progressRulesModel;
                }

                public int hashCode() {
                    return Integer.hashCode(this.progressRulesModel);
                }

                public String toString() {
                    return "UploadRulesModel(progressRulesModel=" + this.progressRulesModel + ")";
                }

                public UploadRulesModel(int i) {
                    super(i, null);
                    this.progressRulesModel = i;
                }

                public final int getProgressRulesModel() {
                    return this.progressRulesModel;
                }
            }
        }

        private ProgressUploading(int i) {
            super(null);
            this.progress = i;
        }

        public final int getProgress() {
            return this.progress;
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSoundV3;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading;", "progressSound", "", "(I)V", "getProgressSound", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final /* data */ class UploadingSoundV3 extends ProgressUploading {
            private final int progressSound;

            public static /* synthetic */ UploadingSoundV3 copy$default(UploadingSoundV3 uploadingSoundV3, int i, int i2, Object obj) {
                if ((i2 & 1) != 0) {
                    i = uploadingSoundV3.progressSound;
                }
                return uploadingSoundV3.copy(i);
            }

            /* renamed from: component1, reason: from getter */
            public final int getProgressSound() {
                return this.progressSound;
            }

            public final UploadingSoundV3 copy(int progressSound) {
                return new UploadingSoundV3(progressSound);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof UploadingSoundV3) && this.progressSound == ((UploadingSoundV3) other).progressSound;
            }

            public int hashCode() {
                return Integer.hashCode(this.progressSound);
            }

            public String toString() {
                return "UploadingSoundV3(progressSound=" + this.progressSound + ")";
            }

            public UploadingSoundV3(int i) {
                super(i, null);
                this.progressSound = i;
            }

            public final int getProgressSound() {
                return this.progressSound;
            }
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingSgu;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading;", "progressSgu", "", "fileLeft", "(II)V", "getFileLeft", "()I", "getProgressSgu", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final /* data */ class UploadingSgu extends ProgressUploading {
            private final int fileLeft;
            private final int progressSgu;

            public static /* synthetic */ UploadingSgu copy$default(UploadingSgu uploadingSgu, int i, int i2, int i3, Object obj) {
                if ((i3 & 1) != 0) {
                    i = uploadingSgu.progressSgu;
                }
                if ((i3 & 2) != 0) {
                    i2 = uploadingSgu.fileLeft;
                }
                return uploadingSgu.copy(i, i2);
            }

            /* renamed from: component1, reason: from getter */
            public final int getProgressSgu() {
                return this.progressSgu;
            }

            /* renamed from: component2, reason: from getter */
            public final int getFileLeft() {
                return this.fileLeft;
            }

            public final UploadingSgu copy(int progressSgu, int fileLeft) {
                return new UploadingSgu(progressSgu, fileLeft);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof UploadingSgu)) {
                    return false;
                }
                UploadingSgu uploadingSgu = (UploadingSgu) other;
                return this.progressSgu == uploadingSgu.progressSgu && this.fileLeft == uploadingSgu.fileLeft;
            }

            public int hashCode() {
                return (Integer.hashCode(this.progressSgu) * 31) + Integer.hashCode(this.fileLeft);
            }

            public String toString() {
                return "UploadingSgu(progressSgu=" + this.progressSgu + ", fileLeft=" + this.fileLeft + ")";
            }

            public UploadingSgu(int i, int i2) {
                super(i, null);
                this.progressSgu = i;
                this.fileLeft = i2;
            }

            public final int getFileLeft() {
                return this.fileLeft;
            }

            public final int getProgressSgu() {
                return this.progressSgu;
            }
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u0003HÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$ProgressUploading$UploadingFirmware;", "Lcom/thor/app/service/state/UploadServiceState$ProgressUploading;", "progressFirmware", "", "(I)V", "getProgressFirmware", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final /* data */ class UploadingFirmware extends ProgressUploading {
            private final int progressFirmware;

            public static /* synthetic */ UploadingFirmware copy$default(UploadingFirmware uploadingFirmware, int i, int i2, Object obj) {
                if ((i2 & 1) != 0) {
                    i = uploadingFirmware.progressFirmware;
                }
                return uploadingFirmware.copy(i);
            }

            /* renamed from: component1, reason: from getter */
            public final int getProgressFirmware() {
                return this.progressFirmware;
            }

            public final UploadingFirmware copy(int progressFirmware) {
                return new UploadingFirmware(progressFirmware);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof UploadingFirmware) && this.progressFirmware == ((UploadingFirmware) other).progressFirmware;
            }

            public int hashCode() {
                return Integer.hashCode(this.progressFirmware);
            }

            public String toString() {
                return "UploadingFirmware(progressFirmware=" + this.progressFirmware + ")";
            }

            public UploadingFirmware(int i) {
                super(i, null);
                this.progressFirmware = i;
            }

            public final int getProgressFirmware() {
                return this.progressFirmware;
            }
        }
    }

    /* compiled from: UploadServiceState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Reconnect;", "Lcom/thor/app/service/state/UploadServiceState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Reconnect extends UploadServiceState {
        public static final Reconnect INSTANCE = new Reconnect();

        private Reconnect() {
            super(null);
        }
    }

    /* compiled from: UploadServiceState.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Polling;", "Lcom/thor/app/service/state/UploadServiceState;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Polling extends UploadServiceState {
        public static final Polling INSTANCE = new Polling();

        private Polling() {
            super(null);
        }
    }

    /* compiled from: UploadServiceState.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Stop;", "Lcom/thor/app/service/state/UploadServiceState;", "()V", "Cancel", "Error", "ForceStop", "Success", "Lcom/thor/app/service/state/UploadServiceState$Stop$Cancel;", "Lcom/thor/app/service/state/UploadServiceState$Stop$Error;", "Lcom/thor/app/service/state/UploadServiceState$Stop$ForceStop;", "Lcom/thor/app/service/state/UploadServiceState$Stop$Success;", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static abstract class Stop extends UploadServiceState {
        public /* synthetic */ Stop(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Stop() {
            super(null);
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Stop$Success;", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Success extends Stop {
            public static final Success INSTANCE = new Success();

            private Success() {
                super(null);
            }
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Stop$Error;", "Lcom/thor/app/service/state/UploadServiceState$Stop;", NotificationCompat.CATEGORY_MESSAGE, "", "(Ljava/lang/String;)V", "getMsg", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final /* data */ class Error extends Stop {
            private final String msg;

            public static /* synthetic */ Error copy$default(Error error, String str, int i, Object obj) {
                if ((i & 1) != 0) {
                    str = error.msg;
                }
                return error.copy(str);
            }

            /* renamed from: component1, reason: from getter */
            public final String getMsg() {
                return this.msg;
            }

            public final Error copy(String msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                return new Error(msg);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof Error) && Intrinsics.areEqual(this.msg, ((Error) other).msg);
            }

            public int hashCode() {
                return this.msg.hashCode();
            }

            public String toString() {
                return "Error(msg=" + this.msg + ")";
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Error(String msg) {
                super(null);
                Intrinsics.checkNotNullParameter(msg, "msg");
                this.msg = msg;
            }

            public final String getMsg() {
                return this.msg;
            }
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Stop$Cancel;", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class Cancel extends Stop {
            public static final Cancel INSTANCE = new Cancel();

            private Cancel() {
                super(null);
            }
        }

        /* compiled from: UploadServiceState.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/thor/app/service/state/UploadServiceState$Stop$ForceStop;", "Lcom/thor/app/service/state/UploadServiceState$Stop;", "()V", "thor-1.8.7_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public static final class ForceStop extends Stop {
            public static final ForceStop INSTANCE = new ForceStop();

            private ForceStop() {
                super(null);
            }
        }
    }
}
