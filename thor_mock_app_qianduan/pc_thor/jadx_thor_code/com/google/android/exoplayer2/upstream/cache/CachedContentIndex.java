package com.google.android.exoplayer2.upstream.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
class CachedContentIndex {
    static final String FILE_NAME_ATOMIC = "cached_content_index.exi";
    private static final int INCREMENTAL_METADATA_READ_LENGTH = 10485760;
    private final SparseArray<String> idToKey;
    private final HashMap<String, CachedContent> keyToContent;
    private final SparseBooleanArray newIds;
    private Storage previousStorage;
    private final SparseBooleanArray removedIds;
    private Storage storage;

    private interface Storage {
        void delete() throws IOException;

        boolean exists() throws IOException;

        void initialize(long uid);

        void load(HashMap<String, CachedContent> content, SparseArray<String> idToKey) throws IOException;

        void onRemove(CachedContent cachedContent, boolean neverStored);

        void onUpdate(CachedContent cachedContent);

        void storeFully(HashMap<String, CachedContent> content) throws IOException;

        void storeIncremental(HashMap<String, CachedContent> content) throws IOException;
    }

    public static boolean isIndexFile(String fileName) {
        return fileName.startsWith(FILE_NAME_ATOMIC);
    }

    public static void delete(DatabaseProvider databaseProvider, long uid) throws DatabaseIOException {
        DatabaseStorage.delete(databaseProvider, uid);
    }

    public CachedContentIndex(DatabaseProvider databaseProvider) {
        this(databaseProvider, null, null, false, false);
    }

    public CachedContentIndex(DatabaseProvider databaseProvider, File legacyStorageDir, byte[] legacyStorageSecretKey, boolean legacyStorageEncrypt, boolean preferLegacyStorage) {
        Assertions.checkState((databaseProvider == null && legacyStorageDir == null) ? false : true);
        this.keyToContent = new HashMap<>();
        this.idToKey = new SparseArray<>();
        this.removedIds = new SparseBooleanArray();
        this.newIds = new SparseBooleanArray();
        DatabaseStorage databaseStorage = databaseProvider != null ? new DatabaseStorage(databaseProvider) : null;
        LegacyStorage legacyStorage = legacyStorageDir != null ? new LegacyStorage(new File(legacyStorageDir, FILE_NAME_ATOMIC), legacyStorageSecretKey, legacyStorageEncrypt) : null;
        if (databaseStorage == null || (legacyStorage != null && preferLegacyStorage)) {
            this.storage = (Storage) Util.castNonNull(legacyStorage);
            this.previousStorage = databaseStorage;
        } else {
            this.storage = databaseStorage;
            this.previousStorage = legacyStorage;
        }
    }

    public void initialize(long uid) throws IOException {
        Storage storage;
        this.storage.initialize(uid);
        Storage storage2 = this.previousStorage;
        if (storage2 != null) {
            storage2.initialize(uid);
        }
        if (!this.storage.exists() && (storage = this.previousStorage) != null && storage.exists()) {
            this.previousStorage.load(this.keyToContent, this.idToKey);
            this.storage.storeFully(this.keyToContent);
        } else {
            this.storage.load(this.keyToContent, this.idToKey);
        }
        Storage storage3 = this.previousStorage;
        if (storage3 != null) {
            storage3.delete();
            this.previousStorage = null;
        }
    }

    public void store() throws IOException {
        this.storage.storeIncremental(this.keyToContent);
        int size = this.removedIds.size();
        for (int i = 0; i < size; i++) {
            this.idToKey.remove(this.removedIds.keyAt(i));
        }
        this.removedIds.clear();
        this.newIds.clear();
    }

    public CachedContent getOrAdd(String key) {
        CachedContent cachedContent = this.keyToContent.get(key);
        return cachedContent == null ? addNew(key) : cachedContent;
    }

    public CachedContent get(String key) {
        return this.keyToContent.get(key);
    }

    public Collection<CachedContent> getAll() {
        return Collections.unmodifiableCollection(this.keyToContent.values());
    }

    public int assignIdForKey(String key) {
        return getOrAdd(key).id;
    }

    public String getKeyForId(int id) {
        return this.idToKey.get(id);
    }

    public void maybeRemove(String key) {
        CachedContent cachedContent = this.keyToContent.get(key);
        if (cachedContent != null && cachedContent.isEmpty() && cachedContent.isFullyUnlocked()) {
            this.keyToContent.remove(key);
            int i = cachedContent.id;
            boolean z = this.newIds.get(i);
            this.storage.onRemove(cachedContent, z);
            if (z) {
                this.idToKey.remove(i);
                this.newIds.delete(i);
            } else {
                this.idToKey.put(i, null);
                this.removedIds.put(i, true);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void removeEmpty() {
        UnmodifiableIterator it = ImmutableSet.copyOf((Collection) this.keyToContent.keySet()).iterator();
        while (it.hasNext()) {
            maybeRemove((String) it.next());
        }
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) {
        CachedContent orAdd = getOrAdd(key);
        if (orAdd.applyMetadataMutations(mutations)) {
            this.storage.onUpdate(orAdd);
        }
    }

    public ContentMetadata getContentMetadata(String key) {
        CachedContent cachedContent = get(key);
        return cachedContent != null ? cachedContent.getMetadata() : DefaultContentMetadata.EMPTY;
    }

    private CachedContent addNew(String key) {
        int newId = getNewId(this.idToKey);
        CachedContent cachedContent = new CachedContent(newId, key);
        this.keyToContent.put(key, cachedContent);
        this.idToKey.put(newId, key);
        this.newIds.put(newId, true);
        this.storage.onUpdate(cachedContent);
        return cachedContent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
            } catch (Throwable unused) {
            }
        }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    static int getNewId(SparseArray<String> idToKey) {
        int size = idToKey.size();
        int i = 0;
        int iKeyAt = size == 0 ? 0 : idToKey.keyAt(size - 1) + 1;
        if (iKeyAt >= 0) {
            return iKeyAt;
        }
        while (i < size && i == idToKey.keyAt(i)) {
            i++;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DefaultContentMetadata readContentMetadata(DataInputStream input) throws IOException {
        int i = input.readInt();
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < i; i2++) {
            String utf = input.readUTF();
            int i3 = input.readInt();
            if (i3 < 0) {
                throw new IOException(new StringBuilder(31).append("Invalid value size: ").append(i3).toString());
            }
            int iMin = Math.min(i3, INCREMENTAL_METADATA_READ_LENGTH);
            byte[] bArrCopyOf = Util.EMPTY_BYTE_ARRAY;
            int i4 = 0;
            while (i4 != i3) {
                int i5 = i4 + iMin;
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, i5);
                input.readFully(bArrCopyOf, i4, iMin);
                iMin = Math.min(i3 - i5, INCREMENTAL_METADATA_READ_LENGTH);
                i4 = i5;
            }
            map.put(utf, bArrCopyOf);
        }
        return new DefaultContentMetadata(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeContentMetadata(DefaultContentMetadata metadata, DataOutputStream output) throws IOException {
        Set<Map.Entry<String, byte[]>> setEntrySet = metadata.entrySet();
        output.writeInt(setEntrySet.size());
        for (Map.Entry<String, byte[]> entry : setEntrySet) {
            output.writeUTF(entry.getKey());
            byte[] value = entry.getValue();
            output.writeInt(value.length);
            output.write(value);
        }
    }

    private static class LegacyStorage implements Storage {
        private static final int FLAG_ENCRYPTED_INDEX = 1;
        private static final int VERSION = 2;
        private static final int VERSION_METADATA_INTRODUCED = 2;
        private final AtomicFile atomicFile;
        private ReusableBufferedOutputStream bufferedOutputStream;
        private boolean changed;
        private final Cipher cipher;
        private final boolean encrypt;
        private final SecureRandom random;
        private final SecretKeySpec secretKeySpec;

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void initialize(long uid) {
        }

        public LegacyStorage(File file, byte[] secretKey, boolean encrypt) {
            Cipher cipher;
            SecretKeySpec secretKeySpec;
            Assertions.checkState((secretKey == null && encrypt) ? false : true);
            if (secretKey != null) {
                Assertions.checkArgument(secretKey.length == 16);
                try {
                    cipher = CachedContentIndex.getCipher();
                    secretKeySpec = new SecretKeySpec(secretKey, "AES");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                Assertions.checkArgument(!encrypt);
                cipher = null;
                secretKeySpec = null;
            }
            this.encrypt = encrypt;
            this.cipher = cipher;
            this.secretKeySpec = secretKeySpec;
            this.random = encrypt ? new SecureRandom() : null;
            this.atomicFile = new AtomicFile(file);
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public boolean exists() {
            return this.atomicFile.exists();
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void delete() {
            this.atomicFile.delete();
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void load(HashMap<String, CachedContent> content, SparseArray<String> idToKey) {
            Assertions.checkState(!this.changed);
            if (readFile(content, idToKey)) {
                return;
            }
            content.clear();
            idToKey.clear();
            this.atomicFile.delete();
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void storeFully(HashMap<String, CachedContent> content) throws Throwable {
            writeFile(content);
            this.changed = false;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void storeIncremental(HashMap<String, CachedContent> content) throws Throwable {
            if (this.changed) {
                storeFully(content);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void onUpdate(CachedContent cachedContent) {
            this.changed = true;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void onRemove(CachedContent cachedContent, boolean neverStored) {
            this.changed = true;
        }

        private boolean readFile(HashMap<String, CachedContent> content, SparseArray<String> idToKey) throws Throwable {
            BufferedInputStream bufferedInputStream;
            DataInputStream dataInputStream;
            if (!this.atomicFile.exists()) {
                return true;
            }
            DataInputStream dataInputStream2 = null;
            try {
                bufferedInputStream = new BufferedInputStream(this.atomicFile.openRead());
                dataInputStream = new DataInputStream(bufferedInputStream);
            } catch (IOException unused) {
            } catch (Throwable th) {
                th = th;
            }
            try {
                int i = dataInputStream.readInt();
                if (i >= 0 && i <= 2) {
                    if ((dataInputStream.readInt() & 1) != 0) {
                        if (this.cipher != null) {
                            byte[] bArr = new byte[16];
                            dataInputStream.readFully(bArr);
                            try {
                                this.cipher.init(2, (Key) Util.castNonNull(this.secretKeySpec), new IvParameterSpec(bArr));
                                dataInputStream = new DataInputStream(new CipherInputStream(bufferedInputStream, this.cipher));
                            } catch (InvalidAlgorithmParameterException e) {
                                e = e;
                                throw new IllegalStateException(e);
                            } catch (InvalidKeyException e2) {
                                e = e2;
                                throw new IllegalStateException(e);
                            }
                        } else {
                            Util.closeQuietly(dataInputStream);
                            return false;
                        }
                    } else if (this.encrypt) {
                        this.changed = true;
                    }
                    int i2 = dataInputStream.readInt();
                    int iHashCachedContent = 0;
                    for (int i3 = 0; i3 < i2; i3++) {
                        CachedContent cachedContent = readCachedContent(i, dataInputStream);
                        content.put(cachedContent.key, cachedContent);
                        idToKey.put(cachedContent.id, cachedContent.key);
                        iHashCachedContent += hashCachedContent(cachedContent, i);
                    }
                    int i4 = dataInputStream.readInt();
                    boolean z = dataInputStream.read() == -1;
                    if (i4 == iHashCachedContent && z) {
                        Util.closeQuietly(dataInputStream);
                        return true;
                    }
                    Util.closeQuietly(dataInputStream);
                    return false;
                }
                Util.closeQuietly(dataInputStream);
                return false;
            } catch (IOException unused2) {
                dataInputStream2 = dataInputStream;
                if (dataInputStream2 != null) {
                    Util.closeQuietly(dataInputStream2);
                }
                return false;
            } catch (Throwable th2) {
                th = th2;
                dataInputStream2 = dataInputStream;
                if (dataInputStream2 != null) {
                    Util.closeQuietly(dataInputStream2);
                }
                throw th;
            }
        }

        private void writeFile(HashMap<String, CachedContent> content) throws Throwable {
            DataOutputStream dataOutputStream = null;
            try {
                OutputStream outputStreamStartWrite = this.atomicFile.startWrite();
                ReusableBufferedOutputStream reusableBufferedOutputStream = this.bufferedOutputStream;
                if (reusableBufferedOutputStream == null) {
                    this.bufferedOutputStream = new ReusableBufferedOutputStream(outputStreamStartWrite);
                } else {
                    reusableBufferedOutputStream.reset(outputStreamStartWrite);
                }
                ReusableBufferedOutputStream reusableBufferedOutputStream2 = this.bufferedOutputStream;
                DataOutputStream dataOutputStream2 = new DataOutputStream(reusableBufferedOutputStream2);
                try {
                    dataOutputStream2.writeInt(2);
                    int iHashCachedContent = 0;
                    dataOutputStream2.writeInt(this.encrypt ? 1 : 0);
                    if (this.encrypt) {
                        byte[] bArr = new byte[16];
                        ((SecureRandom) Util.castNonNull(this.random)).nextBytes(bArr);
                        dataOutputStream2.write(bArr);
                        try {
                            ((Cipher) Util.castNonNull(this.cipher)).init(1, (Key) Util.castNonNull(this.secretKeySpec), new IvParameterSpec(bArr));
                            dataOutputStream2.flush();
                            dataOutputStream2 = new DataOutputStream(new CipherOutputStream(reusableBufferedOutputStream2, this.cipher));
                        } catch (InvalidAlgorithmParameterException e) {
                            e = e;
                            throw new IllegalStateException(e);
                        } catch (InvalidKeyException e2) {
                            e = e2;
                            throw new IllegalStateException(e);
                        }
                    }
                    dataOutputStream2.writeInt(content.size());
                    for (CachedContent cachedContent : content.values()) {
                        writeCachedContent(cachedContent, dataOutputStream2);
                        iHashCachedContent += hashCachedContent(cachedContent, 2);
                    }
                    dataOutputStream2.writeInt(iHashCachedContent);
                    this.atomicFile.endWrite(dataOutputStream2);
                    Util.closeQuietly((Closeable) null);
                } catch (Throwable th) {
                    th = th;
                    dataOutputStream = dataOutputStream2;
                    Util.closeQuietly(dataOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        private int hashCachedContent(CachedContent cachedContent, int version) {
            int i;
            int iHashCode;
            int iHashCode2 = (cachedContent.id * 31) + cachedContent.key.hashCode();
            if (version < 2) {
                long contentLength = ContentMetadata.getContentLength(cachedContent.getMetadata());
                i = iHashCode2 * 31;
                iHashCode = (int) (contentLength ^ (contentLength >>> 32));
            } else {
                i = iHashCode2 * 31;
                iHashCode = cachedContent.getMetadata().hashCode();
            }
            return i + iHashCode;
        }

        private CachedContent readCachedContent(int version, DataInputStream input) throws IOException {
            DefaultContentMetadata contentMetadata;
            int i = input.readInt();
            String utf = input.readUTF();
            if (version >= 2) {
                contentMetadata = CachedContentIndex.readContentMetadata(input);
            } else {
                long j = input.readLong();
                ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
                ContentMetadataMutations.setContentLength(contentMetadataMutations, j);
                contentMetadata = DefaultContentMetadata.EMPTY.copyWithMutationsApplied(contentMetadataMutations);
            }
            return new CachedContent(i, utf, contentMetadata);
        }

        private void writeCachedContent(CachedContent cachedContent, DataOutputStream output) throws IOException {
            output.writeInt(cachedContent.id);
            output.writeUTF(cachedContent.key);
            CachedContentIndex.writeContentMetadata(cachedContent.getMetadata(), output);
        }
    }

    private static final class DatabaseStorage implements Storage {
        private static final String COLUMN_ID = "id";
        private static final int COLUMN_INDEX_ID = 0;
        private static final int COLUMN_INDEX_KEY = 1;
        private static final int COLUMN_INDEX_METADATA = 2;
        private static final String COLUMN_METADATA = "metadata";
        private static final String TABLE_PREFIX = "ExoPlayerCacheIndex";
        private static final String TABLE_SCHEMA = "(id INTEGER PRIMARY KEY NOT NULL,key TEXT NOT NULL,metadata BLOB NOT NULL)";
        private static final int TABLE_VERSION = 1;
        private static final String WHERE_ID_EQUALS = "id = ?";
        private final DatabaseProvider databaseProvider;
        private String hexUid;
        private final SparseArray<CachedContent> pendingUpdates = new SparseArray<>();
        private String tableName;
        private static final String COLUMN_KEY = "key";
        private static final String[] COLUMNS = {"id", COLUMN_KEY, "metadata"};

        public static void delete(DatabaseProvider databaseProvider, long uid) throws DatabaseIOException {
            delete(databaseProvider, Long.toHexString(uid));
        }

        public DatabaseStorage(DatabaseProvider databaseProvider) {
            this.databaseProvider = databaseProvider;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void initialize(long uid) {
            String hexString = Long.toHexString(uid);
            this.hexUid = hexString;
            this.tableName = getTableName(hexString);
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public boolean exists() throws DatabaseIOException {
            return VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 1, (String) Assertions.checkNotNull(this.hexUid)) != -1;
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void delete() throws DatabaseIOException {
            delete(this.databaseProvider, (String) Assertions.checkNotNull(this.hexUid));
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void load(HashMap<String, CachedContent> content, SparseArray<String> idToKey) throws IOException {
            Assertions.checkState(this.pendingUpdates.size() == 0);
            try {
                if (VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 1, (String) Assertions.checkNotNull(this.hexUid)) != 1) {
                    SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    try {
                        initializeTable(writableDatabase);
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                    } catch (Throwable th) {
                        writableDatabase.endTransaction();
                        throw th;
                    }
                }
                Cursor cursor = getCursor();
                while (cursor.moveToNext()) {
                    try {
                        CachedContent cachedContent = new CachedContent(cursor.getInt(0), (String) Assertions.checkNotNull(cursor.getString(1)), CachedContentIndex.readContentMetadata(new DataInputStream(new ByteArrayInputStream(cursor.getBlob(2)))));
                        content.put(cachedContent.key, cachedContent);
                        idToKey.put(cachedContent.id, cachedContent.key);
                    } finally {
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                content.clear();
                idToKey.clear();
                throw new DatabaseIOException(e);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void storeFully(HashMap<String, CachedContent> content) throws IOException {
            try {
                SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                try {
                    initializeTable(writableDatabase);
                    Iterator<CachedContent> it = content.values().iterator();
                    while (it.hasNext()) {
                        addOrUpdateRow(writableDatabase, it.next());
                    }
                    writableDatabase.setTransactionSuccessful();
                    this.pendingUpdates.clear();
                } finally {
                    writableDatabase.endTransaction();
                }
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void storeIncremental(HashMap<String, CachedContent> content) throws IOException {
            if (this.pendingUpdates.size() == 0) {
                return;
            }
            try {
                SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                for (int i = 0; i < this.pendingUpdates.size(); i++) {
                    try {
                        CachedContent cachedContentValueAt = this.pendingUpdates.valueAt(i);
                        if (cachedContentValueAt == null) {
                            deleteRow(writableDatabase, this.pendingUpdates.keyAt(i));
                        } else {
                            addOrUpdateRow(writableDatabase, cachedContentValueAt);
                        }
                    } finally {
                        writableDatabase.endTransaction();
                    }
                }
                writableDatabase.setTransactionSuccessful();
                this.pendingUpdates.clear();
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            }
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void onUpdate(CachedContent cachedContent) {
            this.pendingUpdates.put(cachedContent.id, cachedContent);
        }

        @Override // com.google.android.exoplayer2.upstream.cache.CachedContentIndex.Storage
        public void onRemove(CachedContent cachedContent, boolean neverStored) {
            if (neverStored) {
                this.pendingUpdates.delete(cachedContent.id);
            } else {
                this.pendingUpdates.put(cachedContent.id, null);
            }
        }

        private Cursor getCursor() {
            return this.databaseProvider.getReadableDatabase().query((String) Assertions.checkNotNull(this.tableName), COLUMNS, null, null, null, null, null);
        }

        private void initializeTable(SQLiteDatabase writableDatabase) throws SQLException, DatabaseIOException {
            VersionTable.setVersion(writableDatabase, 1, (String) Assertions.checkNotNull(this.hexUid), 1);
            dropTable(writableDatabase, (String) Assertions.checkNotNull(this.tableName));
            String str = this.tableName;
            writableDatabase.execSQL(new StringBuilder(String.valueOf(str).length() + 88).append("CREATE TABLE ").append(str).append(" (id INTEGER PRIMARY KEY NOT NULL,key TEXT NOT NULL,metadata BLOB NOT NULL)").toString());
        }

        private void deleteRow(SQLiteDatabase writableDatabase, int key) {
            writableDatabase.delete((String) Assertions.checkNotNull(this.tableName), WHERE_ID_EQUALS, new String[]{Integer.toString(key)});
        }

        private void addOrUpdateRow(SQLiteDatabase writableDatabase, CachedContent cachedContent) throws IOException, SQLException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            CachedContentIndex.writeContentMetadata(cachedContent.getMetadata(), new DataOutputStream(byteArrayOutputStream));
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Integer.valueOf(cachedContent.id));
            contentValues.put(COLUMN_KEY, cachedContent.key);
            contentValues.put("metadata", byteArray);
            writableDatabase.replaceOrThrow((String) Assertions.checkNotNull(this.tableName), null, contentValues);
        }

        private static void delete(DatabaseProvider databaseProvider, String hexUid) throws DatabaseIOException {
            try {
                String tableName = getTableName(hexUid);
                SQLiteDatabase writableDatabase = databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                try {
                    VersionTable.removeVersion(writableDatabase, 1, hexUid);
                    dropTable(writableDatabase, tableName);
                    writableDatabase.setTransactionSuccessful();
                } finally {
                    writableDatabase.endTransaction();
                }
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            }
        }

        private static void dropTable(SQLiteDatabase writableDatabase, String tableName) throws SQLException {
            String strValueOf = String.valueOf(tableName);
            writableDatabase.execSQL(strValueOf.length() != 0 ? "DROP TABLE IF EXISTS ".concat(strValueOf) : new String("DROP TABLE IF EXISTS "));
        }

        private static String getTableName(String hexUid) {
            String strValueOf = String.valueOf(hexUid);
            return strValueOf.length() != 0 ? TABLE_PREFIX.concat(strValueOf) : new String(TABLE_PREFIX);
        }
    }
}
