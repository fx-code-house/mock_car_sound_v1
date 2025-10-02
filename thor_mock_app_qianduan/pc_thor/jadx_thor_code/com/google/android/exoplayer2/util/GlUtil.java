package com.google.android.exoplayer2.util;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.text.TextUtils;
import androidx.work.Data;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class GlUtil {
    private static final String EXTENSION_PROTECTED_CONTENT = "EGL_EXT_protected_content";
    private static final String EXTENSION_SURFACELESS_CONTEXT = "EGL_KHR_surfaceless_context";
    private static final String TAG = "GlUtil";

    public static final class Attribute {
        private Buffer buffer;
        private final int index;
        private final int location;
        public final String name;
        private int size;

        public Attribute(int program, int index) {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(program, 35722, iArr, 0);
            int i = iArr[0];
            byte[] bArr = new byte[i];
            int[] iArr2 = new int[1];
            GLES20.glGetActiveAttrib(program, index, i, iArr2, 0, new int[1], 0, new int[1], 0, bArr, 0);
            String str = new String(bArr, 0, GlUtil.strlen(bArr));
            this.name = str;
            this.location = GLES20.glGetAttribLocation(program, str);
            this.index = index;
        }

        public void setBuffer(float[] buffer, int size) {
            this.buffer = GlUtil.createBuffer(buffer);
            this.size = size;
        }

        public void bind() {
            Buffer buffer = (Buffer) Assertions.checkNotNull(this.buffer, "call setBuffer before bind");
            GLES20.glBindBuffer(34962, 0);
            GLES20.glVertexAttribPointer(this.location, this.size, 5126, false, 0, buffer);
            GLES20.glEnableVertexAttribArray(this.index);
            GlUtil.checkGlError();
        }
    }

    public static final class Uniform {
        private final int location;
        public final String name;
        private int texId;
        private final int type;
        private int unit;
        private final float[] value;

        public Uniform(int program, int index) {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(program, 35719, iArr, 0);
            int[] iArr2 = new int[1];
            int i = iArr[0];
            byte[] bArr = new byte[i];
            GLES20.glGetActiveUniform(program, index, i, new int[1], 0, new int[1], 0, iArr2, 0, bArr, 0);
            String str = new String(bArr, 0, GlUtil.strlen(bArr));
            this.name = str;
            this.location = GLES20.glGetUniformLocation(program, str);
            this.type = iArr2[0];
            this.value = new float[16];
        }

        public void setSamplerTexId(int texId, int unit) {
            this.texId = texId;
            this.unit = unit;
        }

        public void setFloat(float value) {
            this.value[0] = value;
        }

        public void setFloats(float[] value) {
            System.arraycopy(value, 0, this.value, 0, value.length);
        }

        public void bind() {
            int i = this.type;
            if (i == 5126) {
                GLES20.glUniform1fv(this.location, 1, this.value, 0);
                GlUtil.checkGlError();
                return;
            }
            if (i == 35676) {
                GLES20.glUniformMatrix4fv(this.location, 1, false, this.value, 0);
                GlUtil.checkGlError();
                return;
            }
            if (this.texId == 0) {
                throw new IllegalStateException("call setSamplerTexId before bind");
            }
            GLES20.glActiveTexture(this.unit + 33984);
            int i2 = this.type;
            if (i2 == 36198) {
                GLES20.glBindTexture(36197, this.texId);
            } else if (i2 == 35678) {
                GLES20.glBindTexture(3553, this.texId);
            } else {
                throw new IllegalStateException(new StringBuilder(36).append("unexpected uniform type: ").append(this.type).toString());
            }
            GLES20.glUniform1i(this.location, this.unit);
            GLES20.glTexParameteri(3553, Data.MAX_DATA_BYTES, 9729);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            GlUtil.checkGlError();
        }
    }

    private GlUtil() {
    }

    public static boolean isProtectedContentExtensionSupported(Context context) {
        String strEglQueryString;
        if (Util.SDK_INT < 24) {
            return false;
        }
        if (Util.SDK_INT >= 26 || !("samsung".equals(Util.MANUFACTURER) || "XT1650".equals(Util.MODEL))) {
            return (Util.SDK_INT >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) && (strEglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373)) != null && strEglQueryString.contains(EXTENSION_PROTECTED_CONTENT);
        }
        return false;
    }

    public static boolean isSurfacelessContextExtensionSupported() {
        String strEglQueryString;
        return Util.SDK_INT >= 17 && (strEglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373)) != null && strEglQueryString.contains(EXTENSION_SURFACELESS_CONTEXT);
    }

    public static void checkGlError() {
        while (true) {
            int iGlGetError = GLES20.glGetError();
            if (iGlGetError == 0) {
                return;
            }
            String strValueOf = String.valueOf(GLU.gluErrorString(iGlGetError));
            Log.e(TAG, strValueOf.length() != 0 ? "glError ".concat(strValueOf) : new String("glError "));
        }
    }

    public static int compileProgram(String[] vertexCode, String[] fragmentCode) {
        return compileProgram(TextUtils.join(StringUtils.LF, vertexCode), TextUtils.join(StringUtils.LF, fragmentCode));
    }

    public static int compileProgram(String vertexCode, String fragmentCode) {
        int iGlCreateProgram = GLES20.glCreateProgram();
        checkGlError();
        addShader(35633, vertexCode, iGlCreateProgram);
        addShader(35632, fragmentCode, iGlCreateProgram);
        GLES20.glLinkProgram(iGlCreateProgram);
        int[] iArr = {0};
        GLES20.glGetProgramiv(iGlCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 1) {
            String strValueOf = String.valueOf(GLES20.glGetProgramInfoLog(iGlCreateProgram));
            throwGlError(strValueOf.length() != 0 ? "Unable to link shader program: \n".concat(strValueOf) : new String("Unable to link shader program: \n"));
        }
        checkGlError();
        return iGlCreateProgram;
    }

    public static Attribute[] getAttributes(int program) {
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(program, 35721, iArr, 0);
        int i = iArr[0];
        if (i != 2) {
            throw new IllegalStateException("expected two attributes");
        }
        Attribute[] attributeArr = new Attribute[i];
        for (int i2 = 0; i2 < iArr[0]; i2++) {
            attributeArr[i2] = new Attribute(program, i2);
        }
        return attributeArr;
    }

    public static Uniform[] getUniforms(int program) {
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(program, 35718, iArr, 0);
        Uniform[] uniformArr = new Uniform[iArr[0]];
        for (int i = 0; i < iArr[0]; i++) {
            uniformArr[i] = new Uniform(program, i);
        }
        return uniformArr;
    }

    public static FloatBuffer createBuffer(float[] data) {
        return (FloatBuffer) createBuffer(data.length).put(data).flip();
    }

    public static FloatBuffer createBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static int createExternalTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, IntBuffer.wrap(iArr));
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, Data.MAX_DATA_BYTES, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkGlError();
        return iArr[0];
    }

    private static void addShader(int type, String source, int program) {
        int iGlCreateShader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(iGlCreateShader, source);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = {0};
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 1) {
            String strGlGetShaderInfoLog = GLES20.glGetShaderInfoLog(iGlCreateShader);
            throwGlError(new StringBuilder(String.valueOf(strGlGetShaderInfoLog).length() + 10 + String.valueOf(source).length()).append(strGlGetShaderInfoLog).append(", source: ").append(source).toString());
        }
        GLES20.glAttachShader(program, iGlCreateShader);
        GLES20.glDeleteShader(iGlCreateShader);
        checkGlError();
    }

    private static void throwGlError(String errorMsg) {
        Log.e(TAG, errorMsg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int strlen(byte[] strVal) {
        for (int i = 0; i < strVal.length; i++) {
            if (strVal[i] == 0) {
                return i;
            }
        }
        return strVal.length;
    }
}
