package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public final class RawResourceDataSource extends BaseDataSource {
    public static final String RAW_RESOURCE_SCHEME = "rawresource";
    private AssetFileDescriptor assetFileDescriptor;
    private long bytesRemaining;
    private InputStream inputStream;
    private boolean opened;
    private final String packageName;
    private final Resources resources;
    private Uri uri;

    public static class RawResourceDataSourceException extends DataSourceException {
        @Deprecated
        public RawResourceDataSourceException(String message) {
            super(message, null, 2000);
        }

        @Deprecated
        public RawResourceDataSourceException(Throwable cause) {
            super(cause, 2000);
        }

        public RawResourceDataSourceException(String message, Throwable cause, int errorCode) {
            super(message, cause, errorCode);
        }
    }

    public static Uri buildRawResourceUri(int rawResourceId) {
        return Uri.parse(new StringBuilder(26).append("rawresource:///").append(rawResourceId).toString());
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.resources = context.getResources();
        this.packageName = context.getPackageName();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws DataSourceException, Resources.NotFoundException, NumberFormatException {
        int identifier;
        Uri uri = dataSpec.uri;
        this.uri = uri;
        if (TextUtils.equals(RAW_RESOURCE_SCHEME, uri.getScheme()) || (TextUtils.equals("android.resource", uri.getScheme()) && uri.getPathSegments().size() == 1 && ((String) Assertions.checkNotNull(uri.getLastPathSegment())).matches("\\d+"))) {
            try {
                identifier = Integer.parseInt((String) Assertions.checkNotNull(uri.getLastPathSegment()));
            } catch (NumberFormatException unused) {
                throw new RawResourceDataSourceException("Resource identifier must be an integer.", null, 1004);
            }
        } else if (TextUtils.equals("android.resource", uri.getScheme())) {
            String strSubstring = (String) Assertions.checkNotNull(uri.getPath());
            if (strSubstring.startsWith("/")) {
                strSubstring = strSubstring.substring(1);
            }
            String host = uri.getHost();
            String strValueOf = String.valueOf(TextUtils.isEmpty(host) ? "" : String.valueOf(host).concat(":"));
            String strValueOf2 = String.valueOf(strSubstring);
            identifier = this.resources.getIdentifier(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf), "raw", this.packageName);
            if (identifier == 0) {
                throw new RawResourceDataSourceException("Resource not found.", null, PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND);
            }
        } else {
            throw new RawResourceDataSourceException("URI must either use scheme rawresource or android.resource", null, 1004);
        }
        transferInitializing(dataSpec);
        try {
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = this.resources.openRawResourceFd(identifier);
            this.assetFileDescriptor = assetFileDescriptorOpenRawResourceFd;
            if (assetFileDescriptorOpenRawResourceFd == null) {
                String strValueOf3 = String.valueOf(uri);
                throw new RawResourceDataSourceException(new StringBuilder(String.valueOf(strValueOf3).length() + 24).append("Resource is compressed: ").append(strValueOf3).toString(), null, 2000);
            }
            long length = assetFileDescriptorOpenRawResourceFd.getLength();
            FileInputStream fileInputStream = new FileInputStream(assetFileDescriptorOpenRawResourceFd.getFileDescriptor());
            this.inputStream = fileInputStream;
            if (length != -1) {
                try {
                    if (dataSpec.position > length) {
                        throw new RawResourceDataSourceException(null, null, 2008);
                    }
                } catch (RawResourceDataSourceException e) {
                    throw e;
                } catch (IOException e2) {
                    throw new RawResourceDataSourceException(null, e2, 2000);
                }
            }
            long startOffset = assetFileDescriptorOpenRawResourceFd.getStartOffset();
            long jSkip = fileInputStream.skip(dataSpec.position + startOffset) - startOffset;
            if (jSkip != dataSpec.position) {
                throw new RawResourceDataSourceException(null, null, 2008);
            }
            if (length == -1) {
                FileChannel channel = fileInputStream.getChannel();
                if (channel.size() == 0) {
                    this.bytesRemaining = -1L;
                } else {
                    long size = channel.size() - channel.position();
                    this.bytesRemaining = size;
                    if (size < 0) {
                        throw new RawResourceDataSourceException(null, null, 2008);
                    }
                }
            } else {
                long j = length - jSkip;
                this.bytesRemaining = j;
                if (j < 0) {
                    throw new DataSourceException(2008);
                }
            }
            if (dataSpec.length != -1) {
                long j2 = this.bytesRemaining;
                this.bytesRemaining = j2 == -1 ? dataSpec.length : Math.min(j2, dataSpec.length);
            }
            this.opened = true;
            transferStarted(dataSpec);
            return dataSpec.length != -1 ? dataSpec.length : this.bytesRemaining;
        } catch (Resources.NotFoundException e3) {
            throw new RawResourceDataSourceException(null, e3, PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (length == 0) {
            return 0;
        }
        long j = this.bytesRemaining;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                length = (int) Math.min(j, length);
            } catch (IOException e) {
                throw new RawResourceDataSourceException(null, e, 2000);
            }
        }
        int i = ((InputStream) Util.castNonNull(this.inputStream)).read(buffer, offset, length);
        if (i == -1) {
            if (this.bytesRemaining == -1) {
                return -1;
            }
            throw new RawResourceDataSourceException("End of stream reached having not read sufficient data.", new EOFException(), 2000);
        }
        long j2 = this.bytesRemaining;
        if (j2 != -1) {
            this.bytesRemaining = j2 - i;
        }
        bytesTransferred(i);
        return i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return this.uri;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws RawResourceDataSourceException {
        this.uri = null;
        try {
            try {
                InputStream inputStream = this.inputStream;
                if (inputStream != null) {
                    inputStream.close();
                }
                this.inputStream = null;
                try {
                    try {
                        AssetFileDescriptor assetFileDescriptor = this.assetFileDescriptor;
                        if (assetFileDescriptor != null) {
                            assetFileDescriptor.close();
                        }
                    } finally {
                        this.assetFileDescriptor = null;
                        if (this.opened) {
                            this.opened = false;
                            transferEnded();
                        }
                    }
                } catch (IOException e) {
                    throw new RawResourceDataSourceException(null, e, 2000);
                }
            } catch (IOException e2) {
                throw new RawResourceDataSourceException(null, e2, 2000);
            }
        } catch (Throwable th) {
            this.inputStream = null;
            try {
                try {
                    AssetFileDescriptor assetFileDescriptor2 = this.assetFileDescriptor;
                    if (assetFileDescriptor2 != null) {
                        assetFileDescriptor2.close();
                    }
                    this.assetFileDescriptor = null;
                    if (this.opened) {
                        this.opened = false;
                        transferEnded();
                    }
                    throw th;
                } catch (IOException e3) {
                    throw new RawResourceDataSourceException(null, e3, 2000);
                }
            } finally {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            }
        }
    }
}
