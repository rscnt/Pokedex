package net.androidsensei.pokedex.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import net.androidsensei.pokedex.BuildConfig;
import net.androidsensei.pokedex.provider.pokemon.PokemonColumns;

public class PokedexSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = PokedexSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "pokedex.db";
    private static final int DATABASE_VERSION = 1;
    private static PokedexSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final PokedexSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_POKEMON = "CREATE TABLE IF NOT EXISTS "
            + PokemonColumns.TABLE_NAME + " ( "
            + PokemonColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PokemonColumns.NAME + " TEXT, "
            + PokemonColumns.UUID + " TEXT NOT NULL, "
            + PokemonColumns.AVATAR + " INTEGER, "
            + PokemonColumns.HEIGHT + " REAL, "
            + PokemonColumns.WEIGHT + " REAL "
            + ", CONSTRAINT unique_uuid UNIQUE (uuid) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_POKEMON_AVATAR = "CREATE INDEX IDX_POKEMON_AVATAR "
            + " ON " + PokemonColumns.TABLE_NAME + " ( " + PokemonColumns.AVATAR + " );";

    // @formatter:on

    public static PokedexSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static PokedexSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static PokedexSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new PokedexSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private PokedexSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new PokedexSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static PokedexSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new PokedexSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private PokedexSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new PokedexSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_POKEMON);
        db.execSQL(SQL_CREATE_INDEX_POKEMON_AVATAR);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
