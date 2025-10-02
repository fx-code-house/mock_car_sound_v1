package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.util.Base64;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.net.URLDecoder;

/* loaded from: classes.dex */
public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    private int bytesRemaining;
    private byte[] data;
    private DataSpec dataSpec;
    private int readPosition;

    public DataSchemeDataSource() {
        super(false);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        transferInitializing(dataSpec);
        this.dataSpec = dataSpec;
        Uri uri = dataSpec.uri;
        String scheme = uri.getScheme();
        boolean zEquals = "data".equals(scheme);
        String strValueOf = String.valueOf(scheme);
        Assertions.checkArgument(zEquals, strValueOf.length() != 0 ? "Unsupported scheme: ".concat(strValueOf) : new String("Unsupported scheme: "));
        String[] strArrSplit = Util.split(uri.getSchemeSpecificPart(), ",");
        if (strArrSplit.length != 2) {
            String strValueOf2 = String.valueOf(uri);
            throw ParserException.createForMalformedDataOfUnknownType(new StringBuilder(String.valueOf(strValueOf2).length() + 23).append("Unexpected URI format: ").append(strValueOf2).toString(), null);
        }
        String str = strArrSplit[1];
        if (strArrSplit[0].contains(";base64")) {
            try {
                this.data = Base64.decode(str, 0);
            } catch (IllegalArgumentException e) {
                String strValueOf3 = String.valueOf(str);
                throw ParserException.createForMalformedDataOfUnknownType(strValueOf3.length() != 0 ? "Error while parsing Base64 encoded string: ".concat(strValueOf3) : new String("Error while parsing Base64 encoded string: "), e);
            }
        } else {
            this.data = Util.getUtf8Bytes(URLDecoder.decode(str, Charsets.US_ASCII.name()));
        }
        if (dataSpec.position > this.data.length) {
            this.data = null;
            throw new DataSourceException(2008);
        }
        int i = (int) dataSpec.position;
        this.readPosition = i;
        this.bytesRemaining = this.data.length - i;
        if (dataSpec.length != -1) {
            this.bytesRemaining = (int) Math.min(this.bytesRemaining, dataSpec.length);
        }
        transferStarted(dataSpec);
        return dataSpec.length != -1 ? dataSpec.length : this.bytesRemaining;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] buffer, int offset, int length) {
        if (length == 0) {
            return 0;
        }
        int i = this.bytesRemaining;
        if (i == 0) {
            return -1;
        }
        int iMin = Math.min(length, i);
        System.arraycopy(Util.castNonNull(this.data), this.readPosition, buffer, offset, iMin);
        this.readPosition += iMin;
        this.bytesRemaining -= iMin;
        bytesTransferred(iMin);
        return iMin;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        DataSpec dataSpec = this.dataSpec;
        if (dataSpec != null) {
            return dataSpec.uri;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
        if (this.data != null) {
            this.data = null;
            transferEnded();
        }
        this.dataSpec = null;
    }
}
