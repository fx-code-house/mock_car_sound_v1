package com.google.firebase.crashlytics.internal.proto;

import android.app.ActivityManager;
import com.google.firebase.crashlytics.internal.Logger;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.crashlytics.internal.stacktrace.TrimmedThrowableData;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class SessionProtobufHelper {
    private static final String SIGNAL_DEFAULT = "0";
    private static final ByteString SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8("0");
    private static final ByteString UNITY_PLATFORM_BYTE_STRING = ByteString.copyFromUtf8(CrashlyticsReport.DEVELOPMENT_PLATFORM_UNITY);

    private SessionProtobufHelper() {
    }

    public static void writeBeginSession(CodedOutputStream codedOutputStream, String str, String str2, long j) throws Exception {
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(str2));
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(str));
        codedOutputStream.writeUInt64(3, j);
    }

    public static void writeSessionApp(CodedOutputStream codedOutputStream, String str, String str2, String str3, String str4, int i, String str5) throws Exception {
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(str);
        ByteString byteStringCopyFromUtf82 = ByteString.copyFromUtf8(str2);
        ByteString byteStringCopyFromUtf83 = ByteString.copyFromUtf8(str3);
        ByteString byteStringCopyFromUtf84 = ByteString.copyFromUtf8(str4);
        ByteString byteStringCopyFromUtf85 = str5 != null ? ByteString.copyFromUtf8(str5) : null;
        codedOutputStream.writeTag(7, 2);
        codedOutputStream.writeRawVarint32(getSessionAppSize(byteStringCopyFromUtf8, byteStringCopyFromUtf82, byteStringCopyFromUtf83, byteStringCopyFromUtf84, i, byteStringCopyFromUtf85));
        codedOutputStream.writeBytes(1, byteStringCopyFromUtf8);
        codedOutputStream.writeBytes(2, byteStringCopyFromUtf82);
        codedOutputStream.writeBytes(3, byteStringCopyFromUtf83);
        codedOutputStream.writeBytes(6, byteStringCopyFromUtf84);
        if (byteStringCopyFromUtf85 != null) {
            codedOutputStream.writeBytes(8, UNITY_PLATFORM_BYTE_STRING);
            codedOutputStream.writeBytes(9, byteStringCopyFromUtf85);
        }
        codedOutputStream.writeEnum(10, i);
    }

    public static void writeSessionOS(CodedOutputStream codedOutputStream, String str, String str2, boolean z) throws Exception {
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(str);
        ByteString byteStringCopyFromUtf82 = ByteString.copyFromUtf8(str2);
        codedOutputStream.writeTag(8, 2);
        codedOutputStream.writeRawVarint32(getSessionOSSize(byteStringCopyFromUtf8, byteStringCopyFromUtf82, z));
        codedOutputStream.writeEnum(1, 3);
        codedOutputStream.writeBytes(2, byteStringCopyFromUtf8);
        codedOutputStream.writeBytes(3, byteStringCopyFromUtf82);
        codedOutputStream.writeBool(4, z);
    }

    public static void writeSessionDevice(CodedOutputStream codedOutputStream, int i, String str, int i2, long j, long j2, boolean z, int i3, String str2, String str3) throws Exception {
        ByteString byteStringStringToByteString = stringToByteString(str);
        ByteString byteStringStringToByteString2 = stringToByteString(str3);
        ByteString byteStringStringToByteString3 = stringToByteString(str2);
        codedOutputStream.writeTag(9, 2);
        codedOutputStream.writeRawVarint32(getSessionDeviceSize(i, byteStringStringToByteString, i2, j, j2, z, i3, byteStringStringToByteString3, byteStringStringToByteString2));
        codedOutputStream.writeEnum(3, i);
        codedOutputStream.writeBytes(4, byteStringStringToByteString);
        codedOutputStream.writeUInt32(5, i2);
        codedOutputStream.writeUInt64(6, j);
        codedOutputStream.writeUInt64(7, j2);
        codedOutputStream.writeBool(10, z);
        codedOutputStream.writeUInt32(12, i3);
        if (byteStringStringToByteString3 != null) {
            codedOutputStream.writeBytes(13, byteStringStringToByteString3);
        }
        if (byteStringStringToByteString2 != null) {
            codedOutputStream.writeBytes(14, byteStringStringToByteString2);
        }
    }

    public static void writeSessionUser(CodedOutputStream codedOutputStream, String str, String str2, String str3) throws Exception {
        if (str == null) {
            str = "";
        }
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(str);
        ByteString byteStringStringToByteString = stringToByteString(str2);
        ByteString byteStringStringToByteString2 = stringToByteString(str3);
        int iComputeBytesSize = CodedOutputStream.computeBytesSize(1, byteStringCopyFromUtf8) + 0;
        if (str2 != null) {
            iComputeBytesSize += CodedOutputStream.computeBytesSize(2, byteStringStringToByteString);
        }
        if (str3 != null) {
            iComputeBytesSize += CodedOutputStream.computeBytesSize(3, byteStringStringToByteString2);
        }
        codedOutputStream.writeTag(6, 2);
        codedOutputStream.writeRawVarint32(iComputeBytesSize);
        codedOutputStream.writeBytes(1, byteStringCopyFromUtf8);
        if (str2 != null) {
            codedOutputStream.writeBytes(2, byteStringStringToByteString);
        }
        if (str3 != null) {
            codedOutputStream.writeBytes(3, byteStringStringToByteString2);
        }
    }

    public static void writeSessionEvent(CodedOutputStream codedOutputStream, long j, String str, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, Map<String, String> map, byte[] bArr, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int i2, String str2, String str3, Float f, int i3, boolean z, long j2, long j3) throws Exception {
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(str2);
        ByteString byteStringCopyFrom = null;
        ByteString byteStringCopyFromUtf82 = str3 == null ? null : ByteString.copyFromUtf8(str3.replace("-", ""));
        if (bArr != null) {
            byteStringCopyFrom = ByteString.copyFrom(bArr);
        } else {
            Logger.getLogger().d("No log data to include with this event.");
        }
        ByteString byteString = byteStringCopyFrom;
        codedOutputStream.writeTag(10, 2);
        codedOutputStream.writeRawVarint32(getSessionEventSize(j, str, trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, map, runningAppProcessInfo, i2, byteStringCopyFromUtf8, byteStringCopyFromUtf82, f, i3, z, j2, j3, byteString));
        codedOutputStream.writeUInt64(1, j);
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(str));
        writeSessionEventApp(codedOutputStream, trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteStringCopyFromUtf8, byteStringCopyFromUtf82, map, runningAppProcessInfo, i2);
        writeSessionEventDevice(codedOutputStream, f, i3, z, i2, j2, j3);
        writeSessionEventLog(codedOutputStream, byteString);
    }

    public static void writeSessionAppClsId(CodedOutputStream codedOutputStream, String str) throws Exception {
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(str);
        codedOutputStream.writeTag(7, 2);
        int iComputeBytesSize = CodedOutputStream.computeBytesSize(2, byteStringCopyFromUtf8);
        codedOutputStream.writeRawVarint32(CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(iComputeBytesSize) + iComputeBytesSize);
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(iComputeBytesSize);
        codedOutputStream.writeBytes(2, byteStringCopyFromUtf8);
    }

    private static void writeSessionEventApp(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int i2) throws Exception {
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(getEventAppSize(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2, map, runningAppProcessInfo, i2));
        writeSessionEventAppExecution(codedOutputStream, trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2);
        if (map != null && !map.isEmpty()) {
            writeSessionEventAppCustomAttributes(codedOutputStream, map);
        }
        if (runningAppProcessInfo != null) {
            codedOutputStream.writeBool(3, runningAppProcessInfo.importance != 100);
        }
        codedOutputStream.writeUInt32(4, i2);
    }

    private static void writeSessionEventAppExecution(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2) throws Exception {
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(getEventAppExecutionSize(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2));
        writeThread(codedOutputStream, thread, stackTraceElementArr, 4, true);
        int length = threadArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            writeThread(codedOutputStream, threadArr[i2], list.get(i2), 0, false);
        }
        writeSessionEventAppExecutionException(codedOutputStream, trimmedThrowableData, 1, i, 2);
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(getEventAppExecutionSignalSize());
        ByteString byteString3 = SIGNAL_DEFAULT_BYTE_STRING;
        codedOutputStream.writeBytes(1, byteString3);
        codedOutputStream.writeBytes(2, byteString3);
        codedOutputStream.writeUInt64(3, 0L);
        codedOutputStream.writeTag(4, 2);
        codedOutputStream.writeRawVarint32(getBinaryImageSize(byteString, byteString2));
        codedOutputStream.writeUInt64(1, 0L);
        codedOutputStream.writeUInt64(2, 0L);
        codedOutputStream.writeBytes(3, byteString);
        if (byteString2 != null) {
            codedOutputStream.writeBytes(4, byteString2);
        }
    }

    private static void writeSessionEventAppCustomAttributes(CodedOutputStream codedOutputStream, Map<String, String> map) throws Exception {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            codedOutputStream.writeTag(2, 2);
            codedOutputStream.writeRawVarint32(getEventAppCustomAttributeSize(entry.getKey(), entry.getValue()));
            codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(entry.getKey()));
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(value));
        }
    }

    private static void writeSessionEventAppExecutionException(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, int i, int i2, int i3) throws Exception {
        codedOutputStream.writeTag(i3, 2);
        codedOutputStream.writeRawVarint32(getEventAppExecutionExceptionSize(trimmedThrowableData, 1, i2));
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(trimmedThrowableData.className));
        String str = trimmedThrowableData.localizedMessage;
        if (str != null) {
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8(str));
        }
        int i4 = 0;
        for (StackTraceElement stackTraceElement : trimmedThrowableData.stacktrace) {
            writeFrame(codedOutputStream, 4, stackTraceElement, true);
        }
        TrimmedThrowableData trimmedThrowableData2 = trimmedThrowableData.cause;
        if (trimmedThrowableData2 != null) {
            if (i < i2) {
                writeSessionEventAppExecutionException(codedOutputStream, trimmedThrowableData2, i + 1, i2, 6);
                return;
            }
            while (trimmedThrowableData2 != null) {
                trimmedThrowableData2 = trimmedThrowableData2.cause;
                i4++;
            }
            codedOutputStream.writeUInt32(7, i4);
        }
    }

    private static void writeThread(CodedOutputStream codedOutputStream, Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) throws Exception {
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(getThreadSize(thread, stackTraceElementArr, i, z));
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(thread.getName()));
        codedOutputStream.writeUInt32(2, i);
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            writeFrame(codedOutputStream, 3, stackTraceElement, z);
        }
    }

    private static void writeFrame(CodedOutputStream codedOutputStream, int i, StackTraceElement stackTraceElement, boolean z) throws Exception {
        codedOutputStream.writeTag(i, 2);
        codedOutputStream.writeRawVarint32(getFrameSize(stackTraceElement, z));
        if (stackTraceElement.isNativeMethod()) {
            codedOutputStream.writeUInt64(1, Math.max(stackTraceElement.getLineNumber(), 0));
        } else {
            codedOutputStream.writeUInt64(1, 0L);
        }
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
        if (stackTraceElement.getFileName() != null) {
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8(stackTraceElement.getFileName()));
        }
        if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
            codedOutputStream.writeUInt64(4, stackTraceElement.getLineNumber());
        }
        codedOutputStream.writeUInt32(5, z ? 4 : 0);
    }

    private static void writeSessionEventDevice(CodedOutputStream codedOutputStream, Float f, int i, boolean z, int i2, long j, long j2) throws Exception {
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(getEventDeviceSize(f, i, z, i2, j, j2));
        if (f != null) {
            codedOutputStream.writeFloat(1, f.floatValue());
        }
        codedOutputStream.writeSInt32(2, i);
        codedOutputStream.writeBool(3, z);
        codedOutputStream.writeUInt32(4, i2);
        codedOutputStream.writeUInt64(5, j);
        codedOutputStream.writeUInt64(6, j2);
    }

    private static void writeSessionEventLog(CodedOutputStream codedOutputStream, ByteString byteString) throws Exception {
        if (byteString != null) {
            codedOutputStream.writeTag(6, 2);
            codedOutputStream.writeRawVarint32(getEventLogSize(byteString));
            codedOutputStream.writeBytes(1, byteString);
        }
    }

    private static int getSessionAppSize(ByteString byteString, ByteString byteString2, ByteString byteString3, ByteString byteString4, int i, ByteString byteString5) {
        int iComputeBytesSize = CodedOutputStream.computeBytesSize(1, byteString) + 0 + CodedOutputStream.computeBytesSize(2, byteString2) + CodedOutputStream.computeBytesSize(3, byteString3) + CodedOutputStream.computeBytesSize(6, byteString4);
        if (byteString5 != null) {
            iComputeBytesSize = iComputeBytesSize + CodedOutputStream.computeBytesSize(8, UNITY_PLATFORM_BYTE_STRING) + CodedOutputStream.computeBytesSize(9, byteString5);
        }
        return iComputeBytesSize + CodedOutputStream.computeEnumSize(10, i);
    }

    private static int getSessionOSSize(ByteString byteString, ByteString byteString2, boolean z) {
        return CodedOutputStream.computeEnumSize(1, 3) + 0 + CodedOutputStream.computeBytesSize(2, byteString) + CodedOutputStream.computeBytesSize(3, byteString2) + CodedOutputStream.computeBoolSize(4, z);
    }

    private static int getSessionDeviceSize(int i, ByteString byteString, int i2, long j, long j2, boolean z, int i3, ByteString byteString2, ByteString byteString3) {
        return CodedOutputStream.computeEnumSize(3, i) + 0 + (byteString == null ? 0 : CodedOutputStream.computeBytesSize(4, byteString)) + CodedOutputStream.computeUInt32Size(5, i2) + CodedOutputStream.computeUInt64Size(6, j) + CodedOutputStream.computeUInt64Size(7, j2) + CodedOutputStream.computeBoolSize(10, z) + CodedOutputStream.computeUInt32Size(12, i3) + (byteString2 == null ? 0 : CodedOutputStream.computeBytesSize(13, byteString2)) + (byteString3 != null ? CodedOutputStream.computeBytesSize(14, byteString3) : 0);
    }

    private static int getBinaryImageSize(ByteString byteString, ByteString byteString2) {
        int iComputeUInt64Size = CodedOutputStream.computeUInt64Size(1, 0L) + 0 + CodedOutputStream.computeUInt64Size(2, 0L) + CodedOutputStream.computeBytesSize(3, byteString);
        return byteString2 != null ? iComputeUInt64Size + CodedOutputStream.computeBytesSize(4, byteString2) : iComputeUInt64Size;
    }

    private static int getSessionEventSize(long j, String str, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int i2, ByteString byteString, ByteString byteString2, Float f, int i3, boolean z, long j2, long j3, ByteString byteString3) {
        int iComputeUInt64Size = CodedOutputStream.computeUInt64Size(1, j) + 0 + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(str));
        int eventAppSize = getEventAppSize(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2, map, runningAppProcessInfo, i2);
        int iComputeTagSize = iComputeUInt64Size + CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(eventAppSize) + eventAppSize;
        int eventDeviceSize = getEventDeviceSize(f, i3, z, i2, j2, j3);
        int iComputeTagSize2 = iComputeTagSize + CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(eventDeviceSize) + eventDeviceSize;
        if (byteString3 == null) {
            return iComputeTagSize2;
        }
        int eventLogSize = getEventLogSize(byteString3);
        return iComputeTagSize2 + CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(eventLogSize) + eventLogSize;
    }

    private static int getEventAppSize(TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int i2) {
        int eventAppExecutionSize = getEventAppExecutionSize(trimmedThrowableData, thread, stackTraceElementArr, threadArr, list, i, byteString, byteString2);
        int iComputeTagSize = CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(eventAppExecutionSize) + eventAppExecutionSize + 0;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                int eventAppCustomAttributeSize = getEventAppCustomAttributeSize(entry.getKey(), entry.getValue());
                iComputeTagSize += CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(eventAppCustomAttributeSize) + eventAppCustomAttributeSize;
            }
        }
        if (runningAppProcessInfo != null) {
            iComputeTagSize += CodedOutputStream.computeBoolSize(3, runningAppProcessInfo.importance != 100);
        }
        return iComputeTagSize + CodedOutputStream.computeUInt32Size(4, i2);
    }

    private static int getEventAppExecutionSize(TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] stackTraceElementArr, Thread[] threadArr, List<StackTraceElement[]> list, int i, ByteString byteString, ByteString byteString2) {
        int threadSize = getThreadSize(thread, stackTraceElementArr, 4, true);
        int iComputeTagSize = CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize) + threadSize + 0;
        int length = threadArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            int threadSize2 = getThreadSize(threadArr[i2], list.get(i2), 0, false);
            iComputeTagSize += CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize2) + threadSize2;
        }
        int eventAppExecutionExceptionSize = getEventAppExecutionExceptionSize(trimmedThrowableData, 1, i);
        int iComputeTagSize2 = iComputeTagSize + CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(eventAppExecutionExceptionSize) + eventAppExecutionExceptionSize;
        int eventAppExecutionSignalSize = getEventAppExecutionSignalSize();
        int iComputeTagSize3 = iComputeTagSize2 + CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(eventAppExecutionSignalSize) + eventAppExecutionSignalSize;
        int binaryImageSize = getBinaryImageSize(byteString, byteString2);
        return iComputeTagSize3 + CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(binaryImageSize) + binaryImageSize;
    }

    private static int getEventAppCustomAttributeSize(String str, String str2) {
        int iComputeBytesSize = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(str));
        if (str2 == null) {
            str2 = "";
        }
        return iComputeBytesSize + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(str2));
    }

    private static int getEventDeviceSize(Float f, int i, boolean z, int i2, long j, long j2) {
        return (f != null ? 0 + CodedOutputStream.computeFloatSize(1, f.floatValue()) : 0) + CodedOutputStream.computeSInt32Size(2, i) + CodedOutputStream.computeBoolSize(3, z) + CodedOutputStream.computeUInt32Size(4, i2) + CodedOutputStream.computeUInt64Size(5, j) + CodedOutputStream.computeUInt64Size(6, j2);
    }

    private static int getEventLogSize(ByteString byteString) {
        return CodedOutputStream.computeBytesSize(1, byteString);
    }

    private static int getEventAppExecutionExceptionSize(TrimmedThrowableData trimmedThrowableData, int i, int i2) {
        int i3 = 0;
        int iComputeBytesSize = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(trimmedThrowableData.className)) + 0;
        String str = trimmedThrowableData.localizedMessage;
        if (str != null) {
            iComputeBytesSize += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(str));
        }
        for (StackTraceElement stackTraceElement : trimmedThrowableData.stacktrace) {
            int frameSize = getFrameSize(stackTraceElement, true);
            iComputeBytesSize += CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(frameSize) + frameSize;
        }
        TrimmedThrowableData trimmedThrowableData2 = trimmedThrowableData.cause;
        if (trimmedThrowableData2 == null) {
            return iComputeBytesSize;
        }
        if (i < i2) {
            int eventAppExecutionExceptionSize = getEventAppExecutionExceptionSize(trimmedThrowableData2, i + 1, i2);
            return iComputeBytesSize + CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(eventAppExecutionExceptionSize) + eventAppExecutionExceptionSize;
        }
        while (trimmedThrowableData2 != null) {
            trimmedThrowableData2 = trimmedThrowableData2.cause;
            i3++;
        }
        return iComputeBytesSize + CodedOutputStream.computeUInt32Size(7, i3);
    }

    private static int getEventAppExecutionSignalSize() {
        ByteString byteString = SIGNAL_DEFAULT_BYTE_STRING;
        return CodedOutputStream.computeBytesSize(1, byteString) + 0 + CodedOutputStream.computeBytesSize(2, byteString) + CodedOutputStream.computeUInt64Size(3, 0L);
    }

    private static int getFrameSize(StackTraceElement stackTraceElement, boolean z) {
        int iComputeUInt64Size;
        if (stackTraceElement.isNativeMethod()) {
            iComputeUInt64Size = CodedOutputStream.computeUInt64Size(1, Math.max(stackTraceElement.getLineNumber(), 0));
        } else {
            iComputeUInt64Size = CodedOutputStream.computeUInt64Size(1, 0L);
        }
        int iComputeBytesSize = iComputeUInt64Size + 0 + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
        if (stackTraceElement.getFileName() != null) {
            iComputeBytesSize += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(stackTraceElement.getFileName()));
        }
        if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
            iComputeBytesSize += CodedOutputStream.computeUInt64Size(4, stackTraceElement.getLineNumber());
        }
        return iComputeBytesSize + CodedOutputStream.computeUInt32Size(5, z ? 2 : 0);
    }

    private static int getThreadSize(Thread thread, StackTraceElement[] stackTraceElementArr, int i, boolean z) {
        int iComputeBytesSize = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(thread.getName())) + CodedOutputStream.computeUInt32Size(2, i);
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            int frameSize = getFrameSize(stackTraceElement, z);
            iComputeBytesSize += CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(frameSize) + frameSize;
        }
        return iComputeBytesSize;
    }

    private static ByteString stringToByteString(String str) {
        if (str == null) {
            return null;
        }
        return ByteString.copyFromUtf8(str);
    }
}
