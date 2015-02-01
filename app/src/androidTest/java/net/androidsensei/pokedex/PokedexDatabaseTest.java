package net.androidsensei.pokedex;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.androidsensei.pokedex.provider.PokedexSQLiteOpenHelper;
import net.androidsensei.pokedex.provider.pokemon.PokemonColumns;

public class PokedexDatabaseTest extends AndroidTestCase {

    public static final String LOG_TAG = PokedexDatabaseTest.class.getSimpleName();

    public void testCreateDb() throws Throwable {
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
        pokemonId = db.insert(PokemonColumns.TABLE_NAME, null, values);

        assertEquals(id,pokemonId);

        String[] columns = {
                PokemonColumns._ID,
                PokemonColumns.NAME,
                PokemonColumns.AVATAR,
                PokemonColumns.HEIGHT,
                PokemonColumns.WEIGHT,
        };

        Cursor cursor = db.query(
                PokemonColumns.TABLE_NAME,  // Table to Query
                columns,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
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
}
