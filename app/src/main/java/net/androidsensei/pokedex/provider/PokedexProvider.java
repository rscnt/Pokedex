package net.androidsensei.pokedex.provider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import net.androidsensei.pokedex.BuildConfig;
import net.androidsensei.pokedex.provider.pokemon.PokemonColumns;

public class PokedexProvider extends ContentProvider {
    private static final String TAG = PokedexProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "net.androidsensei.pokedex.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    public static final String QUERY_NOTIFY = "QUERY_NOTIFY";
    public static final String QUERY_GROUP_BY = "QUERY_GROUP_BY";

    private static final int URI_TYPE_POKEMON = 0;
    private static final int URI_TYPE_POKEMON_ID = 1;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, PokemonColumns.TABLE_NAME, URI_TYPE_POKEMON);
        URI_MATCHER.addURI(AUTHORITY, PokemonColumns.TABLE_NAME + "/#", URI_TYPE_POKEMON_ID);
    }

    protected PokedexSQLiteOpenHelper mPokedexSQLiteOpenHelper;

    @Override
    public boolean onCreate() {
        if (DEBUG) {
            // Enable logging of SQL statements as they are executed.
            try {
                Class<?> sqliteDebugClass = Class.forName("android.database.sqlite.SQLiteDebug");
                Field field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_STATEMENTS");
                field.setAccessible(true);
                field.set(null, true);

                // Uncomment the following block if you also want logging of execution time (more verbose)
                // field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_TIME");
                // field.setAccessible(true);
                // field.set(null, true);
            } catch (Throwable t) {
                if (DEBUG) Log.w(TAG, "Could not enable SQLiteDebug logging", t);
            }
        }

        mPokedexSQLiteOpenHelper = PokedexSQLiteOpenHelper.getInstance(getContext());
        return true;
    }

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY +
            "/" + PokemonColumns.TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + AUTHORITY +
            "/" + PokemonColumns.TABLE_NAME;


    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_POKEMON:
                return TYPE_CURSOR_DIR + AUTHORITY + PokemonColumns.TABLE_NAME;
            case URI_TYPE_POKEMON_ID:
                return TYPE_CURSOR_ITEM + AUTHORITY + PokemonColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        String table = uri.getLastPathSegment();
        long rowId = mPokedexSQLiteOpenHelper.getWritableDatabase().insertOrThrow(table, null, values);
        if (rowId == -1) return null;
        String notify;
        if (rowId != -1 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri.buildUpon().appendEncodedPath(String.valueOf(rowId)).build();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        String table = uri.getLastPathSegment();
        SQLiteDatabase db = mPokedexSQLiteOpenHelper.getWritableDatabase();
        int res = 0;
        db.beginTransaction();
        try {
            for (ContentValues v : values) {
                long id = db.insert(table, null, v);
                db.yieldIfContendedSafely();
                if (id != -1) {
                    res++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return res;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mPokedexSQLiteOpenHelper.getWritableDatabase().update(queryParams.table, values, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        QueryParams queryParams = getQueryParams(uri, selection, null);
        int res = mPokedexSQLiteOpenHelper.getWritableDatabase().delete(queryParams.table, queryParams.selection, selectionArgs);
        String notify;
        if (res != 0 && ((notify = uri.getQueryParameter(QUERY_NOTIFY)) == null || "true".equals(notify))) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String groupBy = uri.getQueryParameter(QUERY_GROUP_BY);
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + groupBy);
        QueryParams queryParams = getQueryParams(uri, selection, projection);
        projection = ensureIdIsFullyQualified(projection, queryParams.table, queryParams.idColumn);
        Cursor res = mPokedexSQLiteOpenHelper.getReadableDatabase().query(queryParams.tablesWithJoins, projection, queryParams.selection, selectionArgs, groupBy,
                null, sortOrder == null ? queryParams.orderBy : sortOrder);
        res.setNotificationUri(getContext().getContentResolver(), uri);
        return res;
    }

    private String[] ensureIdIsFullyQualified(String[] projection, String tableName, String idColumn) {
        if (projection == null) return null;
        String[] res = new String[projection.length];
        for (int i = 0; i < projection.length; i++) {
            if (projection[i].equals(idColumn)) {
                res[i] = tableName + "." + idColumn + " AS " + BaseColumns._ID;
            } else {
                res[i] = projection[i];
            }
        }
        return res;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        HashSet<Uri> urisToNotify = new HashSet<Uri>(operations.size());
        for (ContentProviderOperation operation : operations) {
            urisToNotify.add(operation.getUri());
        }
        SQLiteDatabase db = mPokedexSQLiteOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            int numOperations = operations.size();
            ContentProviderResult[] results = new ContentProviderResult[numOperations];
            int i = 0;
            for (ContentProviderOperation operation : operations) {
                results[i] = operation.apply(this, results, i);
                if (operation.isYieldAllowed()) {
                    db.yieldIfContendedSafely();
                }
                i++;
            }
            db.setTransactionSuccessful();
            for (Uri uri : urisToNotify) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return results;
        } finally {
            db.endTransaction();
        }
    }

    private static class QueryParams {
        public String table;
        public String tablesWithJoins;
        public String idColumn;
        public String selection;
        public String orderBy;
    }

    private QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_POKEMON:
            case URI_TYPE_POKEMON_ID:
                res.table = PokemonColumns.TABLE_NAME;
                res.idColumn = PokemonColumns._ID;
                res.tablesWithJoins = PokemonColumns.TABLE_NAME;
                res.orderBy = PokemonColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_POKEMON_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }

    public static Uri notify(Uri uri, boolean notify) {
        return uri.buildUpon().appendQueryParameter(QUERY_NOTIFY, String.valueOf(notify)).build();
    }

    public static Uri groupBy(Uri uri, String groupBy) {
        return uri.buildUpon().appendQueryParameter(QUERY_GROUP_BY, groupBy).build();
    }
}
