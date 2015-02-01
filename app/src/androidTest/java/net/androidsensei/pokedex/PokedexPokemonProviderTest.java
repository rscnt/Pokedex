package net.androidsensei.pokedex;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import net.androidsensei.pokedex.provider.PokedexProvider;
import net.androidsensei.pokedex.provider.PokedexSQLiteOpenHelper;
import net.androidsensei.pokedex.provider.pokemon.PokemonColumns;

public class PokedexPokemonProviderTest extends AndroidTestCase {

    public static final String LOG_TAG = PokedexPokemonProviderTest.class.getSimpleName();

    public void testDeleteDb() throws Throwable {
        mContext.deleteDatabase(PokedexSQLiteOpenHelper.DATABASE_FILE_NAME);
        SQLiteDatabase db = PokedexSQLiteOpenHelper.getInstance(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDb() {

        int id = 1;
        String nombre = "Charizar";
        String avatar = "http://img2.wikia.nocookie.net/__cb20140203022724/p__/protagonist/images/9/95/Charizard.png";
        double altura = 1.75;
        double peso = 220.25;
        String uuid = "123456";

        ContentValues values = new ContentValues();
        values.put(PokemonColumns._ID, id);
        values.put(PokemonColumns.NAME, nombre);
        values.put(PokemonColumns.UUID, uuid);
        values.put(PokemonColumns.AVATAR, avatar);
        values.put(PokemonColumns.HEIGHT, altura);
        values.put(PokemonColumns.WEIGHT, peso);

        PokedexSQLiteOpenHelper dbHelper = PokedexSQLiteOpenHelper.getInstance(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long pokemonId;
        Uri insertUri = mContext.getContentResolver().insert(PokemonColumns.CONTENT_URI,values);
        pokemonId = ContentUris.parseId(insertUri);

        assertEquals(id,pokemonId);

        Cursor cursor = mContext.getContentResolver().query(
                PokemonColumns.CONTENT_URI,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null // columns to group by
        );

        if (cursor.moveToFirst()) {
            int nombreIndex = cursor.getColumnIndex(PokemonColumns.NAME);
            String nombrePokemon = cursor.getString(nombreIndex);

            assertEquals(nombre, nombrePokemon);
        }else {
            fail("Epic Fail :(");
        }

        cursor.close();

        cursor = mContext.getContentResolver().query(
                PokemonColumns.buildPokemonUri(pokemonId),
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int nombreIndex = cursor.getColumnIndex(PokemonColumns.NAME);
            String nombrePokemon = cursor.getString(nombreIndex);

            assertEquals(nombre, nombrePokemon);
        }else {
            fail("Epic Fail :(");
        }

        cursor.close();
        dbHelper.close();
    }


    public void testGetType() {
        // content://net.androidsensei.pokedex/pokemon/
        String type = mContext.getContentResolver().getType(PokemonColumns.CONTENT_URI);
        // vnd.android.cursor.dir/net.androidsensei.pokedex/pokemon
        assertEquals(PokedexProvider.CONTENT_TYPE, type);

        long testPokemonId = 1;
        // content://net.androidsensei.pokedex/pokemon/1
        type = mContext.getContentResolver().getType(
                PokemonColumns.buildPokemonUri(testPokemonId));
        // vnd.android.cursor.item/net.androidsensei.pokedex/pokemon
        assertEquals(PokedexProvider.CONTENT_ITEM_TYPE, type);

    }
}
