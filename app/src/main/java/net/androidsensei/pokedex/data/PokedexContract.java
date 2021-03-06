package net.androidsensei.pokedex.data;

import android.provider.BaseColumns;

/**
 * Created by hkadejo on 12-10-14.
 */
public class PokedexContract implements BaseColumns {

    public static final class PokemonEntry implements BaseColumns {

        public static final String TABLE_NAME = "pokemons";

        public static final String COLUMN_NOMBRE = "name";

        public static final String COLUMN_AVATAR = "avatar";

        public static final String COLUMN_UUID = "uuid";

        public static final String COLUMN_ALTURA = "height";

        public static final String COLUMN_PESO = "weight";

    }

    public static final class LugarEntry implements BaseColumns {

        public static final String TABLE_NAME = "lugares";

        public static final String COLUMN_POKEMON_ID = "pokemon_id";

        public static final String COLUMN_LATITUD = "latitud";

        public static final String COLUMN_LONGITUD = "longitud";

    }

}
