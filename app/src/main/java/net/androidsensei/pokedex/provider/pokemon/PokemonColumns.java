package net.androidsensei.pokedex.provider.pokemon;

import android.net.Uri;
import android.provider.BaseColumns;

import net.androidsensei.pokedex.provider.PokedexProvider;
import net.androidsensei.pokedex.provider.pokemon.PokemonColumns;

/**
 * A pocket monster.
 */
public class PokemonColumns implements BaseColumns {
    public static final String TABLE_NAME = "pokemon";
    public static final Uri CONTENT_URI = Uri.parse(PokedexProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * The name of the pokemon, for instance. Charizard.
     */
    public static final String NAME = "name";

    /**
     * UUID.
     */
    public static final String UUID = "uuid";

    public static final String AVATAR = "avatar";

    public static final String HEIGHT = "height";

    public static final String WEIGHT = "weight";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            UUID,
            AVATAR,
            HEIGHT,
            WEIGHT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == NAME || c.contains("." + NAME)) return true;
            if (c == UUID || c.contains("." + UUID)) return true;
            if (c == AVATAR || c.contains("." + AVATAR)) return true;
            if (c == HEIGHT || c.contains("." + HEIGHT)) return true;
            if (c == WEIGHT || c.contains("." + WEIGHT)) return true;
        }
        return false;
    }

}
