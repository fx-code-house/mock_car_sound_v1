package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Assertions;
import java.nio.ShortBuffer;
import java.util.Arrays;

/* loaded from: classes.dex */
final class Sonic {
    private static final int AMDF_FREQUENCY = 4000;
    private static final int BYTES_PER_SAMPLE = 2;
    private static final int MAXIMUM_PITCH = 400;
    private static final int MINIMUM_PITCH = 65;
    private final int channelCount;
    private final short[] downSampleBuffer;
    private short[] inputBuffer;
    private int inputFrameCount;
    private final int inputSampleRateHz;
    private int maxDiff;
    private final int maxPeriod;
    private final int maxRequiredFrameCount;
    private int minDiff;
    private final int minPeriod;
    private int newRatePosition;
    private int oldRatePosition;
    private short[] outputBuffer;
    private int outputFrameCount;
    private final float pitch;
    private short[] pitchBuffer;
    private int pitchFrameCount;
    private int prevMinDiff;
    private int prevPeriod;
    private final float rate;
    private int remainingInputToCopyFrameCount;
    private final float speed;

    public Sonic(int inputSampleRateHz, int channelCount, float speed, float pitch, int outputSampleRateHz) {
        this.inputSampleRateHz = inputSampleRateHz;
        this.channelCount = channelCount;
        this.speed = speed;
        this.pitch = pitch;
        this.rate = inputSampleRateHz / outputSampleRateHz;
        this.minPeriod = inputSampleRateHz / MAXIMUM_PITCH;
        int i = inputSampleRateHz / 65;
        this.maxPeriod = i;
        int i2 = i * 2;
        this.maxRequiredFrameCount = i2;
        this.downSampleBuffer = new short[i2];
        this.inputBuffer = new short[i2 * channelCount];
        this.outputBuffer = new short[i2 * channelCount];
        this.pitchBuffer = new short[i2 * channelCount];
    }

    public int getPendingInputBytes() {
        return this.inputFrameCount * this.channelCount * 2;
    }

    public void queueInput(ShortBuffer buffer) {
        int iRemaining = buffer.remaining();
        int i = this.channelCount;
        int i2 = iRemaining / i;
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.inputBuffer, this.inputFrameCount, i2);
        this.inputBuffer = sArrEnsureSpaceForAdditionalFrames;
        buffer.get(sArrEnsureSpaceForAdditionalFrames, this.inputFrameCount * this.channelCount, ((i * i2) * 2) / 2);
        this.inputFrameCount += i2;
        processStreamInput();
    }

    public void getOutput(ShortBuffer buffer) {
        int iMin = Math.min(buffer.remaining() / this.channelCount, this.outputFrameCount);
        buffer.put(this.outputBuffer, 0, this.channelCount * iMin);
        int i = this.outputFrameCount - iMin;
        this.outputFrameCount = i;
        short[] sArr = this.outputBuffer;
        int i2 = this.channelCount;
        System.arraycopy(sArr, iMin * i2, sArr, 0, i * i2);
    }

    public void queueEndOfStream() {
        int i;
        int i2 = this.inputFrameCount;
        float f = this.speed;
        float f2 = this.pitch;
        int i3 = this.outputFrameCount + ((int) ((((i2 / (f / f2)) + this.pitchFrameCount) / (this.rate * f2)) + 0.5f));
        this.inputBuffer = ensureSpaceForAdditionalFrames(this.inputBuffer, i2, (this.maxRequiredFrameCount * 2) + i2);
        int i4 = 0;
        while (true) {
            i = this.maxRequiredFrameCount;
            int i5 = this.channelCount;
            if (i4 >= i * 2 * i5) {
                break;
            }
            this.inputBuffer[(i5 * i2) + i4] = 0;
            i4++;
        }
        this.inputFrameCount += i * 2;
        processStreamInput();
        if (this.outputFrameCount > i3) {
            this.outputFrameCount = i3;
        }
        this.inputFrameCount = 0;
        this.remainingInputToCopyFrameCount = 0;
        this.pitchFrameCount = 0;
    }

    public void flush() {
        this.inputFrameCount = 0;
        this.outputFrameCount = 0;
        this.pitchFrameCount = 0;
        this.oldRatePosition = 0;
        this.newRatePosition = 0;
        this.remainingInputToCopyFrameCount = 0;
        this.prevPeriod = 0;
        this.prevMinDiff = 0;
        this.minDiff = 0;
        this.maxDiff = 0;
    }

    public int getOutputSize() {
        return this.outputFrameCount * this.channelCount * 2;
    }

    private short[] ensureSpaceForAdditionalFrames(short[] buffer, int frameCount, int additionalFrameCount) {
        int length = buffer.length;
        int i = this.channelCount;
        int i2 = length / i;
        return frameCount + additionalFrameCount <= i2 ? buffer : Arrays.copyOf(buffer, (((i2 * 3) / 2) + additionalFrameCount) * i);
    }

    private void removeProcessedInputFrames(int positionFrames) {
        int i = this.inputFrameCount - positionFrames;
        short[] sArr = this.inputBuffer;
        int i2 = this.channelCount;
        System.arraycopy(sArr, positionFrames * i2, sArr, 0, i2 * i);
        this.inputFrameCount = i;
    }

    private void copyToOutput(short[] samples, int positionFrames, int frameCount) {
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, frameCount);
        this.outputBuffer = sArrEnsureSpaceForAdditionalFrames;
        int i = this.channelCount;
        System.arraycopy(samples, positionFrames * i, sArrEnsureSpaceForAdditionalFrames, this.outputFrameCount * i, i * frameCount);
        this.outputFrameCount += frameCount;
    }

    private int copyInputToOutput(int positionFrames) {
        int iMin = Math.min(this.maxRequiredFrameCount, this.remainingInputToCopyFrameCount);
        copyToOutput(this.inputBuffer, positionFrames, iMin);
        this.remainingInputToCopyFrameCount -= iMin;
        return iMin;
    }

    private void downSampleInput(short[] samples, int position, int skip) {
        int i = this.maxRequiredFrameCount / skip;
        int i2 = this.channelCount;
        int i3 = skip * i2;
        int i4 = position * i2;
        for (int i5 = 0; i5 < i; i5++) {
            int i6 = 0;
            for (int i7 = 0; i7 < i3; i7++) {
                i6 += samples[(i5 * i3) + i4 + i7];
            }
            this.downSampleBuffer[i5] = (short) (i6 / i3);
        }
    }

    private int findPitchPeriodInRange(short[] samples, int position, int minPeriod, int maxPeriod) {
        int i = position * this.channelCount;
        int i2 = 255;
        int i3 = 1;
        int i4 = 0;
        int i5 = 0;
        while (minPeriod <= maxPeriod) {
            int iAbs = 0;
            for (int i6 = 0; i6 < minPeriod; i6++) {
                iAbs += Math.abs(samples[i + i6] - samples[(i + minPeriod) + i6]);
            }
            if (iAbs * i4 < i3 * minPeriod) {
                i4 = minPeriod;
                i3 = iAbs;
            }
            if (iAbs * i2 > i5 * minPeriod) {
                i2 = minPeriod;
                i5 = iAbs;
            }
            minPeriod++;
        }
        this.minDiff = i3 / i4;
        this.maxDiff = i5 / i2;
        return i4;
    }

    private boolean previousPeriodBetter(int minDiff, int maxDiff) {
        return minDiff != 0 && this.prevPeriod != 0 && maxDiff <= minDiff * 3 && minDiff * 2 > this.prevMinDiff * 3;
    }

    private int findPitchPeriod(short[] samples, int position) {
        int iFindPitchPeriodInRange;
        int i = this.inputSampleRateHz;
        int i2 = i > 4000 ? i / 4000 : 1;
        if (this.channelCount == 1 && i2 == 1) {
            iFindPitchPeriodInRange = findPitchPeriodInRange(samples, position, this.minPeriod, this.maxPeriod);
        } else {
            downSampleInput(samples, position, i2);
            int iFindPitchPeriodInRange2 = findPitchPeriodInRange(this.downSampleBuffer, 0, this.minPeriod / i2, this.maxPeriod / i2);
            if (i2 != 1) {
                int i3 = iFindPitchPeriodInRange2 * i2;
                int i4 = i2 * 4;
                int i5 = i3 - i4;
                int i6 = i3 + i4;
                int i7 = this.minPeriod;
                if (i5 < i7) {
                    i5 = i7;
                }
                int i8 = this.maxPeriod;
                if (i6 > i8) {
                    i6 = i8;
                }
                if (this.channelCount == 1) {
                    iFindPitchPeriodInRange = findPitchPeriodInRange(samples, position, i5, i6);
                } else {
                    downSampleInput(samples, position, 1);
                    iFindPitchPeriodInRange = findPitchPeriodInRange(this.downSampleBuffer, 0, i5, i6);
                }
            } else {
                iFindPitchPeriodInRange = iFindPitchPeriodInRange2;
            }
        }
        int i9 = previousPeriodBetter(this.minDiff, this.maxDiff) ? this.prevPeriod : iFindPitchPeriodInRange;
        this.prevMinDiff = this.minDiff;
        this.prevPeriod = iFindPitchPeriodInRange;
        return i9;
    }

    private void moveNewSamplesToPitchBuffer(int originalOutputFrameCount) {
        int i = this.outputFrameCount - originalOutputFrameCount;
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.pitchBuffer, this.pitchFrameCount, i);
        this.pitchBuffer = sArrEnsureSpaceForAdditionalFrames;
        short[] sArr = this.outputBuffer;
        int i2 = this.channelCount;
        System.arraycopy(sArr, originalOutputFrameCount * i2, sArrEnsureSpaceForAdditionalFrames, this.pitchFrameCount * i2, i2 * i);
        this.outputFrameCount = originalOutputFrameCount;
        this.pitchFrameCount += i;
    }

    private void removePitchFrames(int frameCount) {
        if (frameCount == 0) {
            return;
        }
        short[] sArr = this.pitchBuffer;
        int i = this.channelCount;
        System.arraycopy(sArr, frameCount * i, sArr, 0, (this.pitchFrameCount - frameCount) * i);
        this.pitchFrameCount -= frameCount;
    }

    private short interpolate(short[] in, int inPos, int oldSampleRate, int newSampleRate) {
        short s = in[inPos];
        short s2 = in[inPos + this.channelCount];
        int i = this.newRatePosition * oldSampleRate;
        int i2 = this.oldRatePosition;
        int i3 = i2 * newSampleRate;
        int i4 = (i2 + 1) * newSampleRate;
        int i5 = i4 - i;
        int i6 = i4 - i3;
        return (short) (((s * i5) + ((i6 - i5) * s2)) / i6);
    }

    private void adjustRate(float rate, int originalOutputFrameCount) {
        int i;
        int i2;
        if (this.outputFrameCount == originalOutputFrameCount) {
            return;
        }
        int i3 = this.inputSampleRateHz;
        int i4 = (int) (i3 / rate);
        while (true) {
            if (i4 <= 16384 && i3 <= 16384) {
                break;
            }
            i4 /= 2;
            i3 /= 2;
        }
        moveNewSamplesToPitchBuffer(originalOutputFrameCount);
        int i5 = 0;
        while (true) {
            int i6 = this.pitchFrameCount;
            if (i5 < i6 - 1) {
                while (true) {
                    i = this.oldRatePosition;
                    int i7 = (i + 1) * i4;
                    i2 = this.newRatePosition;
                    if (i7 <= i2 * i3) {
                        break;
                    }
                    this.outputBuffer = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, 1);
                    int i8 = 0;
                    while (true) {
                        int i9 = this.channelCount;
                        if (i8 < i9) {
                            this.outputBuffer[(this.outputFrameCount * i9) + i8] = interpolate(this.pitchBuffer, (i9 * i5) + i8, i3, i4);
                            i8++;
                        }
                    }
                    this.newRatePosition++;
                    this.outputFrameCount++;
                }
                int i10 = i + 1;
                this.oldRatePosition = i10;
                if (i10 == i3) {
                    this.oldRatePosition = 0;
                    Assertions.checkState(i2 == i4);
                    this.newRatePosition = 0;
                }
                i5++;
            } else {
                removePitchFrames(i6 - 1);
                return;
            }
        }
    }

    private int skipPitchPeriod(short[] samples, int position, float speed, int period) {
        int i;
        if (speed >= 2.0f) {
            i = (int) (period / (speed - 1.0f));
        } else {
            this.remainingInputToCopyFrameCount = (int) ((period * (2.0f - speed)) / (speed - 1.0f));
            i = period;
        }
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, i);
        this.outputBuffer = sArrEnsureSpaceForAdditionalFrames;
        overlapAdd(i, this.channelCount, sArrEnsureSpaceForAdditionalFrames, this.outputFrameCount, samples, position, samples, position + period);
        this.outputFrameCount += i;
        return i;
    }

    private int insertPitchPeriod(short[] samples, int position, float speed, int period) {
        int i;
        if (speed < 0.5f) {
            i = (int) ((period * speed) / (1.0f - speed));
        } else {
            this.remainingInputToCopyFrameCount = (int) ((period * ((2.0f * speed) - 1.0f)) / (1.0f - speed));
            i = period;
        }
        int i2 = period + i;
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, i2);
        this.outputBuffer = sArrEnsureSpaceForAdditionalFrames;
        int i3 = this.channelCount;
        System.arraycopy(samples, position * i3, sArrEnsureSpaceForAdditionalFrames, this.outputFrameCount * i3, i3 * period);
        overlapAdd(i, this.channelCount, this.outputBuffer, this.outputFrameCount + period, samples, position + period, samples, position);
        this.outputFrameCount += i2;
        return i;
    }

    private void changeSpeed(float speed) {
        int iInsertPitchPeriod;
        int i = this.inputFrameCount;
        if (i < this.maxRequiredFrameCount) {
            return;
        }
        int i2 = 0;
        do {
            if (this.remainingInputToCopyFrameCount > 0) {
                iInsertPitchPeriod = copyInputToOutput(i2);
            } else {
                int iFindPitchPeriod = findPitchPeriod(this.inputBuffer, i2);
                if (speed > 1.0d) {
                    iInsertPitchPeriod = iFindPitchPeriod + skipPitchPeriod(this.inputBuffer, i2, speed, iFindPitchPeriod);
                } else {
                    iInsertPitchPeriod = insertPitchPeriod(this.inputBuffer, i2, speed, iFindPitchPeriod);
                }
            }
            i2 += iInsertPitchPeriod;
        } while (this.maxRequiredFrameCount + i2 <= i);
        removeProcessedInputFrames(i2);
    }

    private void processStreamInput() {
        int i = this.outputFrameCount;
        float f = this.speed;
        float f2 = this.pitch;
        float f3 = f / f2;
        float f4 = this.rate * f2;
        double d = f3;
        if (d > 1.00001d || d < 0.99999d) {
            changeSpeed(f3);
        } else {
            copyToOutput(this.inputBuffer, 0, this.inputFrameCount);
            this.inputFrameCount = 0;
        }
        if (f4 != 1.0f) {
            adjustRate(f4, i);
        }
    }

    private static void overlapAdd(int frameCount, int channelCount, short[] out, int outPosition, short[] rampDown, int rampDownPosition, short[] rampUp, int rampUpPosition) {
        for (int i = 0; i < channelCount; i++) {
            int i2 = (outPosition * channelCount) + i;
            int i3 = (rampUpPosition * channelCount) + i;
            int i4 = (rampDownPosition * channelCount) + i;
            for (int i5 = 0; i5 < frameCount; i5++) {
                out[i2] = (short) (((rampDown[i4] * (frameCount - i5)) + (rampUp[i3] * i5)) / frameCount);
                i2 += channelCount;
                i4 += channelCount;
                i3 += channelCount;
            }
        }
    }
}
